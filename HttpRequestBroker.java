package com.akseer.puzzlerz.akseer;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hammad on 1/13/2018.
 */

public class HttpRequestBroker {

   private static OkHttpClient client = new OkHttpClient();

   public static String makePostRequest(String url, Map<String,String> headers) throws IOException {

      Headers headers1 = Headers.of(headers);
      RequestBody requestBody = new MultipartBody.Builder()
              .setType(MultipartBody.FORM)
              .addFormDataPart("category_id", "4")
              .addFormDataPart("category_type_id", "1")

              .build();

      Request request = new Request.Builder()
              .url(url)
              .headers(headers1)
              .post(requestBody)
              .build();

      Response response = client.newCall(request).execute();
      return response.body().string();
   }
}
