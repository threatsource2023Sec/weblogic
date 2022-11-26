package weblogic.servlet.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.utils.XSSUtils;

public class Utils {
   public static String encodeXSS(String text) {
      return XSSUtils.encodeXSS(text);
   }

   public static String getConfiguredAuthMethod(ServletContext servletContext) {
      return ((WebAppServletContext)servletContext).getSecurityManager().getAuthMethod();
   }

   public static String getConfiguredAuthMethod(HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      return reqi.getContext().getSecurityManager().getAuthMethod();
   }

   public static boolean isSSLRequired(ServletContext servletContext, String relativeURI, String method) {
      return ((WebAppServletContext)servletContext).isSSLRequired(relativeURI, method);
   }

   public static boolean isSSLRequired(HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      return reqi.getContext().isSSLRequired(WebAppSecurity.getRelativeURI(request), request.getMethod());
   }
}
