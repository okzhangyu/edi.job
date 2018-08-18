package org.edi.job.data;

import org.edi.freamwork.data.operation.OpResultDescription;

/**
 * @author Fancy
 * @date 2018/8/16
 */
public class JobOpResultDescription extends OpResultDescription {

    public final static String SBO_GET_ORDERS = "获取到%d条未清任务汇报";

    public final static String SBO_CREATE_ORDER_SUCCESS_INFO= "[%1$s]号任务汇报生成成功，B1单据号[%2$s]";

    public final static String SBO_CREATE_ORDER_FAILED_INFO = "[%1$s]号任务汇报生成失败，失败原因：%2$s";

    public final static String SBO_DOCUMENT_CALLBACK_EXCEPTION="%1号汇报回写发生异常:%2";

    public final static String SBO_DOCUMENT_CALLBACK_SUCCESS="%1号汇报回写成功";

    public final static String SBO_DOCUMENT_CALL_SERVICE_ERROR="调用单据生成服务发生错误：%1";
}
