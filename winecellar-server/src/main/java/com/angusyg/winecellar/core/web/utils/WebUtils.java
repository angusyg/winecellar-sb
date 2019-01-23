package com.angusyg.winecellar.core.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Util to work with Requests
 */
public class WebUtils {
  /**
   * Returns complete URL of Request (Method + URL + Query string)
   * @param req request
   * @return complete URL of request
   */
  public static String extractCompleteURLFromRequest(HttpServletRequest req) {
    StringBuilder requestURL = new StringBuilder(req.getMethod());
    requestURL.append(" ").append(req.getRequestURL());
    String queryString = req.getQueryString();
    if (queryString != null) {
      requestURL.append('?').append(queryString).toString();
    }
    return requestURL.toString();
  }

  /**
   * Build a map with Request data infos
   * @param req request
   * @return map of request infos
   */
  public static Map<String, String> buildRequestData(HttpServletRequest req) {
    Map<String, String> data = new HashMap<>();
    data.put("URL", extractCompleteURLFromRequest(req));
    return data;
  }
}
