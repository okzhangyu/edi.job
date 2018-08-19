package org.edi.job.businessone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.edi.freamwork.data.operation.IOpResult;
import org.edi.freamwork.data.operation.OpResult;
import org.edi.job.data.DocumentSyncResult;
import org.edi.job.data.JobData;
import org.edi.job.data.JobOpResultDescription;
import org.edi.job.util.HttpRequest;
import org.edi.job.util.ListUtil;
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
                OpResult<DocumentSyncResult> resultOpResult = gson.fromJson(resultMsg,new TypeToken<OpResult<DocumentSyncResult>>(){}.getType());
                if(!JobData.OK.equals(resultOpResult.getCode())){
                    XxlJobLogger.log(JobOpResultDescription.SBO_DOCUMENT_CALL_SERVICE_ERROR,resultOpResult.getMessage());
                }
                for (DocumentSyncResult item:resultOpResult.getResultObject()) {
                    try{
                        if(!JobData.OK.equals(item.getCode())){
                            XxlJobLogger.log(String.format(JobOpResultDescription.SBO_CREATE_ORDER_FAILED_INFO,item.getUniquekey(),item.getMessage()));
                        }else{
                            XxlJobLogger.log(String.format(JobOpResultDescription.SBO_CREATE_ORDER_SUCCESS_INFO,item.getUniquekey(),item.getReturnEntry()));
//                            stockReport = ListUtil.find(stockReports,Integer.valueOf(item.getUniquekey()));
//                            if(stockReport !=null) {
//                                stockReport.setB1DocEntry(item.getReturnEntry());
//                                stockReport.setDocumentStatus("C");
//                                boRepositoryStockReport.updateStockReportDocStatus(stockReport);
//                                XxlJobLogger.log(JobOpResultDescription.SBO_DOCUMENT_CALLBACK_SUCCESS,item.getUniquekey());
//                            }
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
