package com.epam.cashregister.services.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AjaxResponseWriter {

    public static void write(HttpServletResponse response, int httpStatusCode, Map<String, String> dataMap) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatusCode);
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> set : dataMap.entrySet())
            jsonObject.put(set.getKey(), set.getValue());
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.close();
    }

}
