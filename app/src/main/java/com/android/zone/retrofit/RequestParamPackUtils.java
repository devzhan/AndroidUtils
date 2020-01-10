package com.android.zone.retrofit;

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
