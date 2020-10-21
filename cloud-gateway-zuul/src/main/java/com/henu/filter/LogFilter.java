package com.henu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.jnlp.FileContents;
import javax.servlet.http.HttpServletRequest;


@Component
public class LogFilter extends ZuulFilter {

    Logger logger= LoggerFactory.getLogger(LogFilter.class);
   //zuul过滤器类型
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
   //过滤器优先级级别
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }
   //启动过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }
    //处理逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request=context.getRequest();

        String s=context.get(FilterConstants.REQUEST_URI_KEY).toString();
        logger.info(request.getRemoteAddr()+"访问了："+request.getRequestURI()+"微服务真实路径："+s);
        return null;
    }
}
