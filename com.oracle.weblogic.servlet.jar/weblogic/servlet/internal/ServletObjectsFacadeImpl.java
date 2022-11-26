package weblogic.servlet.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.internal.async.AsyncContextImpl;
import weblogic.servlet.logging.HttpAccountingInfo;
import weblogic.servlet.security.internal.ServletObjectsFacade;
import weblogic.servlet.spi.SubjectHandle;

public class ServletObjectsFacadeImpl implements ServletObjectsFacade {
   public boolean isRequestForProxyServlet(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getServletStub().isProxyServlet();
   }

   public String getOriginalAuthorizationHeader(HttpServletRequest request) {
      return ServletRequestImpl.getOriginalRequest(request).getRequestHeaders().getAuthorization();
   }

   public void setResponseHeader(HttpServletRequest req, String headerName, String headerValue) {
      ServletResponseImpl res = ServletRequestImpl.getOriginalRequest(req).getResponse();
      res.setHeaderInternal(headerName, headerValue);
   }

   public void addResponseCookie(HttpServletRequest request, Cookie cookie) {
      ServletResponseImpl res = ServletRequestImpl.getOriginalRequest(request).getResponse();
      res.addCookieInternal(cookie);
   }

   public void updateSessionId(HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      reqi.getSessionHelper().updateSessionId();
   }

   public void addRoleLinkTo(ServletConfig servletConfig, String roleName, String roleLink) {
      ((ServletStubImpl)servletConfig).getSecurityHelper().addRoleLink(roleName, roleLink);
   }

   public String getRoleLink(ServletConfig servletConfig, String rolename) {
      return ((ServletStubImpl)servletConfig).getSecurityHelper().getRoleLink(rolename);
   }

   public boolean isDynamicallyGenerated(ServletConfig servletConfig) {
      return ((ServletStubImpl)servletConfig).isDynamicallyGenerated();
   }

   public String getProtocol(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getInputHelper().getRequestParser().getProtocol();
   }

   public boolean isInternalDispatch(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getConnection().isInternalDispatch();
   }

   public String getExpectHeader(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getRequestHeaders().getExpect();
   }

   public void send100ContinueResponse(HttpServletRequest req) throws IOException {
      ServletRequestImpl.getOriginalRequest(req).send100ContinueResponse();
   }

   public HttpAccountingInfo getHttpAccountingInfo(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getHttpAccountingInfo();
   }

   public ServletStub getServletStub(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getServletStub();
   }

   public boolean isAsyncMode(HttpServletRequest request) {
      return ServletRequestImpl.getOriginalRequest(request).isAsyncMode();
   }

   public AsyncContextImpl getAsyncContext(HttpServletRequest request) {
      return (AsyncContextImpl)ServletRequestImpl.getOriginalRequest(request).getAsyncContext();
   }

   public ArrayList[] getHeadersAsLists(HttpServletRequest req) {
      ArrayList headerNameList = (ArrayList)ServletRequestImpl.getOriginalRequest(req).getRequestHeaders().getHeaderNamesAsArrayList().clone();
      ArrayList headerValueList = (ArrayList)ServletRequestImpl.getOriginalRequest(req).getRequestHeaders().getHeaderValuesAsArrayList().clone();
      return new ArrayList[]{headerNameList, headerValueList};
   }

   public String getURLForRedirect(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getResponse().getURLForRedirect(req.getRequestURI());
   }

   public Throwable forwardRequest(HttpServletRequest req, HttpServletResponse res, RequestDispatcher rd, SubjectHandle subject) {
      PrivilegedAction forwardAction = new ForwardAction(rd, req, res);
      return (Throwable)subject.run((PrivilegedAction)forwardAction);
   }

   public Cookie getResponseCookie(HttpServletRequest request, String cookieName) {
      return ServletRequestImpl.getOriginalRequest(request).getResponse().getCookie(cookieName);
   }

   public void setRequestData(HttpServletRequest req, byte[] inBytes) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      String originalRequestMethod = reqi.getMethod();
      reqi.setMethod("POST");
      if (inBytes != null) {
         reqi.setInputStream((ServletInputStream)(new ServletInputStreamImpl(new ByteArrayInputStream(inBytes))));
         if (originalRequestMethod.equals("POST")) {
            reqi.getResponse().disableKeepAlive();
         }
      }

   }

   public byte[] getCookieHeader(HttpServletRequest req) {
      return ServletRequestImpl.getOriginalRequest(req).getRequestHeaders().getHeaderAsBytes("Cookie");
   }

   public void replaceRequestHeaders(HttpServletRequest req, ArrayList headerNames, ArrayList headerValues) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      reqi.getRequestHeaders().reset();
      reqi.getRequestHeaders().setHeaders(headerNames, headerValues);
   }

   public String processProxyPathHeaders(HttpServletRequest req, String path) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      return reqi.getResponse().processProxyPathHeaders(path);
   }
}
