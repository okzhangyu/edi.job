package org.edi.job.businessone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.edi.freamwork.data.Result;
import org.edi.freamwork.httpclient.HttpRequest;
import org.edi.job.data.DocumentSyncResult;
import org.edi.job.data.JobData;
import org.edi.job.data.JobOpResultDescription;
import org.edi.stocktask.bo.stockreport.StockReport;
import org.edi.stocktask.repository.BORepositoryStockReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Fancy
 * @date 2018/8/15
 */
@JobHandler(value="stockDocumentJobHandler")
@Component
public class StockDocumentJobHandler extends IJobHandler {

    @Autowired
    private BORepositoryStockReport boRepositoryStockReport;


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try
        {
            StockReport stockReport;
            List<StockReport> stockReports = boRepositoryStockReport.fetchUnSyncStockReport();
            if(stockReports != null && stockReports.size() > 0){
                XxlJobLogger.log(String.format(JobOpResultDescription.SBO_GET_ORDERS,stockReports.size()));
                Gson gson = new Gson();
                String orderJson = gson .toJson(stockReports);
                String resultMsg = HttpRequest.post(orderJson);
                XxlJobLogger.log(resultMsg);
                Result<DocumentSyncResult> resultOpResult = gson.fromJson(resultMsg,new TypeToken<Result<DocumentSyncResult>>(){}.getType());
                if(!JobData.OK.equals(resultOpResult.getCode())){
                    XxlJobLogger.log(JobOpResultDescription.SBO_DOCUMENT_CALL_SERVICE_ERROR,resultOpResult.getMessage());
                }
                for (DocumentSyncResult item:resultOpResult.getData()) {
                    try{
                        if(!JobData.OK.equals(item.getCode())){
                            XxlJobLogger.log(String.format(JobOpResultDescription.SBO_CREATE_ORDER_FAILED_INFO,item.getUniquekey(),item.getMessage()));
                        }else{
                            XxlJobLogger.log(String.format(JobOpResultDescription.SBO_CREATE_ORDER_SUCCESS_INFO,item.getUniquekey(),item.getReturnEntry()));
                        }


                    }catch (Exception e){
                        XxlJobLogger.log(JobOpResultDescription.SBO_DOCUMENT_CALLBACK_EXCEPTION,item.getUniquekey(),e.getMessage());
                    }
                }
            }
            return SUCCESS;
        }catch (Exception e){
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }
}
