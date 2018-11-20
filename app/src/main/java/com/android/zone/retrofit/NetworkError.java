/**
 * Copyright (c) 1998-2012 TENCENT Inc. All Rights Reserved.
 * 
 * FileName: NetworkError.java
 * 
 * Description: 网络错误常量值定义文件
 * 
 * History:
 * 1.0	devilxie	2012-09-07	Create
 */
package com.android.zone.retrofit;

import java.net.HttpURLConnection;

/**
 * 网络错误类，定义了各种常见的网络错误
 * @author devilxie
 * @version 1.0
 *
 */
public class NetworkError {
	/**
	 * 网络成功
	 */
	public final static int SUCCESS = 0;
	/**
	 * 网络未知错误
	 */
	public final static int FAIL_UNKNOWN = -1;
	/**
	 * 连接超时错误
	 */
	public final static int FAIL_CONNECT_TIMEOUT = -2;
	/**
	 * 资源未找到错误
	 */
	public final static int FAIL_NOT_FOUND = -3;
	/**
	 * 网络读写错误
	 */
	public final static int FAIL_IO_ERROR = -4;
	/**
	 * 用户中断
	 */
	public final static int CANCEL = -5;

	/**
	 * 无网络
	 */
	public final static int NO_AVAILABLE_NETWORK = -6;

	/**
	 * SOCKET 读写超时
	 */
	public final static int SOCKET_TIMEOUT = -7;

	/**
	 * status code, 403: Forbidden
	 */
	public final static int AUTH_EXPIRED = -8;

	/**
	 * status code, 416: RANGE OUT
	 */
	public final static int RANGE_INVALID = -9;

	/**
	 * 协议不支持
	 */
	public final static int SCHEME_NOT_SUPPORT = -10;

	public static int toNetworkErrorWithHttpStatus(int code) {
		if (code == HttpURLConnection.HTTP_ACCEPTED || code == HttpURLConnection.HTTP_OK ||
				code == HttpURLConnection.HTTP_PARTIAL)
			return NetworkError.SUCCESS;

		if (code >= 400 && code < 500) {
			if (code == HttpURLConnection.HTTP_NOT_FOUND) {
				return NetworkError.FAIL_NOT_FOUND;
			}

			else if (code == HttpURLConnection.HTTP_FORBIDDEN
					|| code == HttpURLConnection.HTTP_BAD_REQUEST
					|| code == HttpURLConnection.HTTP_UNAUTHORIZED
					|| code == HttpURLConnection.HTTP_BAD_METHOD
					|| code == HttpURLConnection.HTTP_GONE)
				return NetworkError.AUTH_EXPIRED;
			else if (code == 416) {
				return NetworkError.RANGE_INVALID;
			}
			return NetworkError.FAIL_NOT_FOUND;

		} else if (code >= 500) {
			return NetworkError.SOCKET_TIMEOUT;
		}

		return NetworkError.FAIL_UNKNOWN;
	}
}
