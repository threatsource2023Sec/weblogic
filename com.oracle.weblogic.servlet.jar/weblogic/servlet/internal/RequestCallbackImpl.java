package weblogic.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.io.FilenameEncoder;

final class RequestCallbackImpl implements RequestCallback {
   private HttpServletResponse response;
   private HttpServletRequest request;
   private ServletResponseImpl respImpl;
   private static final String JSP_ERROR_SENT_ATTRIBUTE = "wl.jsp.errorSent";

   public RequestCallbackImpl(HttpServletRequest req, HttpServletResponse resp, ServletResponseImpl impl) {
      this.request = req;
      this.response = resp;
      this.respImpl = impl;
   }

   private void reportJSPFailure(String htmlMsg) throws IOException {
      if (!this.respImpl.isOutputStreamInitialized()) {
         if (this.request.getAttribute("wl.jsp.errorSent") == null) {
            this.request.setAttribute("wl.jsp.errorSent", Boolean.TRUE);
            String enc = System.getProperty("file.encoding");
            ChunkOutput co = null;
            ServletOutputStream sos = this.respImpl.getOutputStreamNoCheck();
            if (sos != null && sos instanceof ServletOutputStreamImpl) {
               co = ((ServletOutputStreamImpl)sos).getOutput().getOutput();
            }

            if (co != null && co instanceof CharChunkOutput) {
               this.respImpl.setHeaderInternal("Content-Type", "text/html");
            } else {
               this.respImpl.setHeaderInternal("Content-Type", "text/html; charset=" + enc);
            }

            PrintWriter out = this.response.getWriter();
            HttpServletResponse var10001 = this.response;
            this.response.setStatus(500);
            out.print(htmlMsg);
            out.flush();
         }
      }
   }

   public void reportJSPTranslationFailure(String simpleMsg, String htmlMsg) throws IOException {
      this.reportJSPFailure(htmlMsg);
   }

   public void reportJSPCompilationFailure(String simpleMsg, String htmlMsg) throws IOException {
      this.reportJSPFailure(htmlMsg);
   }

   public String getIncludeURI() {
      StringBuilder sb = new StringBuilder(30);
      String servletPath = (String)this.request.getAttribute("javax.servlet.include.servlet_path");
      String pathInfo = (String)this.request.getAttribute("javax.servlet.include.path_info");
      if (servletPath == null && pathInfo == null) {
         servletPath = this.request.getServletPath();
         pathInfo = this.request.getPathInfo();
      }

      if (servletPath != null) {
         sb.append(servletPath);
      }

      if (pathInfo != null) {
         sb.append(pathInfo);
      }

      String unescapedURI = sb.toString();
      unescapedURI = FilenameEncoder.resolveRelativeURIPath(HttpParsing.unescape(unescapedURI));
      return unescapedURI;
   }
}
