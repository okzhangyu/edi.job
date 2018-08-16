package org.edi.job.businessone;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.edi.freamwork.data.operation.IOpResult;
import org.edi.job.data.JobOpResultDescription;
import org.edi.stocktask.bo.stockreport.StockReport;
import org.edi.stocktask.repository.BORepositoryStockReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Fancy
 * @date 2018/8/15
 */
public class StockDocumentJob extends IJobHandler {

    @Autowired
    private BORepositoryStockReport boRepositoryStockReport;

    //private DocumentServiceFactory documentServiceFactory = new DocumentServiceFactory();

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try
        {
            List<StockReport> stockReports = boRepositoryStockReport.fetchUnSyncStockReport();
            if(stockReports != null && stockReports.size() > 0){
                XxlJobLogger.log(String.format(JobOpResultDescription.SBO_GET_ORDERS,stockReports.size()));

                //IStockDocumentService service;
//                for (StockReport stockReport:stockReports) {
//                    service = documentServiceFactory.getServiceInstance(stockReport);
//                    IOpResult result =service.createDocuments(stockReport);
//                    if(B1OpResultCode.OK==result.getCode()){
//                        XxlJobLogger.log(String.format(B1OpResultDescription.SBO_CREATE_ORDER_SUCCESS_INFO,stockReport.getDocEntry(),result.getThirdId()));
//                        stockReport.setB1DocEntry(result.getThirdId());
//                        stockReport.setDocumentStatus("C");
//                        XxlJobLogger.log(stockReport.toString());
//                        boRepositoryStockReport.updateStockReportDocStatus(stockReport);
//                    }else{
//                        XxlJobLogger.log(String.format(B1OpResultDescription.SBO_CREATE_ORDER_FAILED_INFO,stockReport.getDocEntry(),result.getMessage()));
//                    }
//                }
            }
            return SUCCESS;
        }catch (Exception e){
            //XxlJobLogger.log(B1OpResultDescription.SBO_CREATE_ORDER_EXCEPTION,e);
            return ReturnT.FAIL;
        }
    }
}
