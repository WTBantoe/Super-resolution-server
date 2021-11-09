package com.sr.aop;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

/**
 * @Author cyh
 * @Date 2021/11/5 15:00
 */
public class AuthFilter implements Filter
{

    private String env = "";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        env = filterConfig.getInitParameter("env");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        // 修改cookie
        ModifyHttpServletRequestWrapper mParametersWrapper = new ModifyHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        if (env.equals("dev") || env.equals("test"))
        {
            Cookie[] cookies = mParametersWrapper.getCookies();
            boolean hasToken = false;
            if (!(cookies == null) && !(cookies.length == 0))
            {
                for (Cookie cookie : cookies)
                {
                    if (cookie.getName().equals("token"))
                    {
                        hasToken = true;
                        break;
                    }
                }
            }
            if (!hasToken)
            {
                mParametersWrapper.putCookie("token", "");
            }
        }
        filterChain.doFilter(mParametersWrapper, servletResponse);
    }

    @Override
    public void destroy()
    {

    }

    public static class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper
    {
        private Map<String, String> mapCookies;

        ModifyHttpServletRequestWrapper(HttpServletRequest request)
        {
            super(request);
            this.mapCookies = new HashMap<>();
        }

        public void putCookie(String name, String value)
        {
            this.mapCookies.put(name, value);
        }

        public Cookie[] getCookies()
        {
            HttpServletRequest request = (HttpServletRequest) getRequest();
            Cookie[] cookies = request.getCookies();
            if (mapCookies == null || mapCookies.isEmpty())
            {
                return cookies;
            }
            List<Cookie> cookieList;
            if (cookies == null || cookies.length == 0)
            {
                cookieList = new ArrayList<>();
                for (Map.Entry<String, String> entry : mapCookies.entrySet())
                {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key))
                    {
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
                if (cookieList.isEmpty())
                {
                    return cookies;
                }
            }
            else
            {
                cookieList = new ArrayList<>(Arrays.asList(cookies));
                for (Map.Entry<String, String> entry : mapCookies.entrySet())
                {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key))
                    {
                        for (int i = 0; i < cookieList.size(); i++)
                        {
                            if (cookieList.get(i).getName().equals(key))
                            {
                                cookieList.remove(i);
                            }
                        }
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
            }
            return cookieList.toArray(new Cookie[0]);
        }
    }
}
