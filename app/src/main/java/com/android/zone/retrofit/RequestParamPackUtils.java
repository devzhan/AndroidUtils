package com.android.zone.retrofit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * FileName: RequestParamPackUtils
 * Author: xiuwen.zhan
 * Date: 2018/9/4 19:18
 */
public class RequestParamPackUtils {
    /**
     *
     * @param key 服务器接收的文件的key
     * @param files 文件列表
     * @return
     */
    public static Map<String, RequestBody> packFileParams(String key ,ArrayList<File> files) {
        Map<String, RequestBody> paramsMap = new HashMap<>();
        if (files.size()!=0){

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
//                String fileName = MD5Utils.md5(file.getName()+ i);
                String fileName = file.getName()+ i;
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                paramsMap.put(key+"\"; filename=\"" + fileName, fileBody);
            }
        }else {
            //文件个数为0时候封装拼接的参数
            RequestBody fileBody = RequestBody.create(null, "");
            paramsMap.put(""+"\"; filename=\"" + "", fileBody);
        }
        return paramsMap;
    }

    /**
     *
     * @param param 请求参数
     * @return
     */
    public static RequestBody packRequestParam(String param) {
        //这里的text/plain表示参数都是文本
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }
}
