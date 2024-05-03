package dev.lvpq.sell_book.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;

public class CustomHttpRequest extends HttpServletRequestWrapper {

    private Map customHeaderMap = null;

    public CustomHttpRequest(HttpServletRequest request) {
        super(request);
        customHeaderMap = new HashMap();
    }
    public void addHeader(String name,String value){
        customHeaderMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerValue == null) {
            Object value = customHeaderMap.get(name);
            if (value != null) {
                headerValue = (String) value;
            }
        }
        return headerValue;
    }


}