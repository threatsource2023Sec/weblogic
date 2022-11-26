package weblogic.servlet.security.internal;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import weblogic.servlet.internal.ServletStub;
import weblogic.servlet.internal.async.AsyncContextImpl;
import weblogic.servlet.logging.HttpAccountingInfo;

public interface ServletObjectsFacade {
   boolean isRequestForProxyServlet(HttpServletRequest var1);

   String getOriginalAuthorizationHeader(HttpServletRequest var1);

   void setResponseHeader(HttpServletRequest var1, String var2, String var3);

   void addResponseCookie(HttpServletRequest var1, Cookie var2);

   void updateSessionId(HttpServletRequest var1);

   void addRoleLinkTo(ServletConfig var1, String var2, String var3);

   String getRoleLink(ServletConfig var1, String var2);

   boolean isDynamicallyGenerated(ServletConfig var1);

   String getProtocol(HttpServletRequest var1);

   boolean isInternalDispatch(HttpServletRequest var1);

   String getExpectHeader(HttpServletRequest var1);

   void send100ContinueResponse(HttpServletRequest var1) throws IOException;

   HttpAccountingInfo getHttpAccountingInfo(HttpServletRequest var1);

   ServletStub getServletStub(HttpServletRequest var1);

   boolean isAsyncMode(HttpServletRequest var1);

   AsyncContextImpl getAsyncContext(HttpServletRequest var1);

   ArrayList[] getHeadersAsLists(HttpServletRequest var1);

   String getURLForRedirect(HttpServletRequest var1);

   Cookie getResponseCookie(HttpServletRequest var1, String var2);

   void setRequestData(HttpServletRequest var1, byte[] var2);

   byte[] getCookieHeader(HttpServletRequest var1);

   void replaceRequestHeaders(HttpServletRequest var1, ArrayList var2, ArrayList var3);

   String processProxyPathHeaders(HttpServletRequest var1, String var2);
}
