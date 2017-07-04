import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/6/22
 * Time: 11:16
 */
public class HttpClinetUtil {
    private static PoolingHttpClientConnectionManager cm;
    private static RequestConfig requestConfig;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);// 整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
        }

        if(requestConfig == null){
            requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(3000)  //设置连接超时时间，单位毫秒。
                    .setConnectionRequestTimeout(1000) //设置从connect Manager获取Connection 超时时间，单位毫秒。
                    .setSocketTimeout(30000) //请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
                    .build();
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();
    }

    /**
     *
     * @param url
     * @return
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        System.out.println(ub.build());
        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

        return getResult(httpPost);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }

        return pairs;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            System.out.println(request.getRequestLine());
            CloseableHttpResponse response = httpClient.execute(request);
            System.out.println(response);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return EMPTY_STR;
    }


    public final static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createSignature(Map<String, Object> paramMap, String secretKey){
        if(paramMap == null || paramMap.size() == 0) return "";

        //1.获取map中所有的键
        Set<String> keySet = paramMap.keySet();

        //2.对所有的key进行排序
        List<String> keyList = new ArrayList<>();
        keyList.addAll(keySet);
        Collections.sort(keyList);

        //3.组装字符串
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < keyList.size(); ++i){
            String key = keyList.get(i);
            String value = (String)paramMap.get(key);
            sb.append(key).append("=").append(value);
            if(i != keyList.size() - 1) sb.append("&");
        }
        sb.append(secretKey);
        return MD5(sb.toString());
    }

    //&signature=f0d24efa7423efeec297ad4ed45d7879
//GWTime=&RespCode=00&RespMsg=&acqID=99020344&charSet=UTF-8&merID=800039253992422&orderNum=201706280325373c029777&signType=MD5&transID=xP8hrceSuLzNMRzy&transTime=20170628170900&transType=REFD&version=VER000000002a36b2aa7519e4f73aecdf752df1a3ea5

    public static void main(String[] args) throws URISyntaxException, UnsupportedEncodingException {
/*        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("version","VER000000002");
        paramMap.put("charSet","UTF-8");
        paramMap.put("transType","REFD");
        paramMap.put("orderNum","201706280325373c029777");
        paramMap.put("returnAmount","0.2");
        paramMap.put("merID","800039253992422");
        paramMap.put("acqID","99020344");
        paramMap.put("merReserve","order refund");
        paramMap.put("paymentSchema","FC");
        paramMap.put("transTime","20170628170900");
        paramMap.put("signType","MD5");

        long start = System.currentTimeMillis();
        String signature = createSignature(paramMap, "a36b2aa7519e4f73aecdf752df1a3ea5");
        paramMap.put("signature", signature);
       try{
            System.out.println(httpGetRequest("http://testapi.allpayx.com:8000/epayment/common", paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);*/

        //System.out.println(MD5("GWTime=&RespCode=00&RespMsg=&acqID=99020344&charSet=UTF-8&merID=800039253992422&orderNum=201706280325373c029777&signType=MD5&transID=xP8hrceSuLzNMRzy&transTime=20170628170900&transType=REFD&version=VER000000002a36b2aa7519e4f73aecdf752df1a3ea5"));
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));


    /*    List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1);


        Set<Integer> set = new HashSet<>();
        set.addAll(list);

        System.out.println(set);*/




    }
}
