package org.edi.job.util;

import com.google.gson.Gson;
import javafx.scene.chart.Chart;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.edi.freamwork.data.operation.OpResult;
import org.edi.job.data.DocumentSyncResult;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fancy
 * @date 2018/8/16
 */
public class HttpRequest {
    private static final String url = "http://login3.avacloud.com.cn:9091/edi.businessone_Web/v1/documents";

    public static String post(String requestJson) throws Exception {
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new Gson();
            HttpResponse httpResponse;
            httpPost.addHeader("content-type", "application/json;charset=utf-8");
            httpPost.addHeader("Accept", "application/json;charset=utf-8");

            StringEntity entity = new StringEntity(requestJson);
            httpPost.setEntity(entity);
            //httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            httpResponse = httpClient.execute(httpPost);
            String response = EntityUtils.toString(httpResponse.getEntity());
            return response;
        }catch (Exception e){
            throw e;
        }
    }
}
