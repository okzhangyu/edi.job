package org.edi.job.util;

import org.edi.stocktask.bo.stockreport.StockReport;

import java.util.List;

/**
 * @author Fancy
 * @date 2018/8/17
 */
public class ListUtil {

    public static StockReport find(List<StockReport> stockReports,Integer docEntry){
        for (StockReport item:stockReports ) {
            if(item.getDocEntry().equals(docEntry)){
                return item;
            }
        }
        return null;
    }
}
