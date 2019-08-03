package com.volanty.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        log.info("Method[{}], Path[{}], HttpStatusResponse[{}], Params[{}]",
                request.getMethod(), request.getRequestURI(),
                response.getStatus(),
                getParameterFromMap(request.getParameterMap()));
        super.afterCompletion(request, response, handler, ex);
    }

    private String getParameterFromMap(Map<String, String[]> params) {
        final Set<String> keys = params.keySet();
        final StringBuffer sb = new StringBuffer();
        for(String key: keys) {
            final String[] value = params.get(key);
            sb.append(key).append("=").append(value[0]);
        }

        return  sb.toString();
    }
}
