package org.edi.job.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fancy
 * @date 2018/8/16
 */
public class HttpRequest {
    private static final String url = "http://login3.avacloud.com.cn/edi.businessone_Web/v1/documents";
    public static String httpPost(String requestJson) throws Exception {

        return "";
    }
}
