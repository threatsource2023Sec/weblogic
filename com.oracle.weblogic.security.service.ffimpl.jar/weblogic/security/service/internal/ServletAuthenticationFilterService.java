package weblogic.security.service.internal;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface ServletAuthenticationFilterService {
   Filter[] getServletAuthenticationFilters(ServletContext var1) throws ServletException;

   void destroyServletAuthenticationFilters(Filter[] var1);
}
