package weblogic.servlet.logging;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.utils.PlatformConstants;
import weblogic.utils.string.SimpleCachingDateFormat;

public final class CLFLogger implements Logger {
   private final byte[] LINE_SEP;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final CLFDateFormat format;
   private final CLFDateFormatWithMillis formatWithMillis;
   private final LogManagerHttp logManager;
   private boolean logMillis;

   public CLFLogger(LogManagerHttp lmh, WebServerMBean mbean) {
      this.LINE_SEP = PlatformConstants.EOL.getBytes();
      this.format = new CLFDateFormat();
      this.formatWithMillis = new CLFDateFormatWithMillis();
      this.logManager = lmh;
      this.logMillis = mbean.getWebServerLog().isLogMilliSeconds();
   }

   public int log(ServletRequestImpl req, ServletResponseImpl res) {
      FormatStringBuffer buf = new FormatStringBuffer(128);
      if (ManagementService.getRuntimeAccess(kernelId).getServer().isReverseDNSAllowed()) {
         buf.appendValueOrDash(req.getRemoteHost());
      } else {
         buf.appendValueOrDash(req.getRemoteAddr());
      }

      buf.appendSpaceDashSpace();
      buf.appendValueOrDash(req.getHttpAccountingInfo().getRemoteUser());
      buf.append(' ');
      if (this.logMillis) {
         buf.append(this.formatWithMillis.getDateAsBytes(req));
         buf.append(' ');
      } else {
         buf.append(this.format.getDateAsBytes(req));
         buf.append(' ');
      }

      buf.append('"');
      String url = null;
      String uri = req.getRequestURI();
      String queryString = null;
      if (uri != null && uri.length() < LoggerUtils.MAX_LOGGING_URI_LENGTH && (queryString = req.getQueryString()) != null && queryString.length() > 0) {
         url = LoggerUtils.encodeAndTruncate(uri + "?" + queryString);
      } else {
         url = LoggerUtils.encodeAndTruncate(uri);
      }

      buf.append(req.getMethod());
      buf.append(' ');
      buf.append(url);
      buf.append(' ');
      buf.append(req.getProtocol());
      buf.append('"');
      buf.append(' ');
      buf.appendStatusCode(res.getStatus()).append(' ');
      buf.append(Long.toString(res.getContentLengthLong()));
      buf.append(' ');
      buf.append(this.LINE_SEP);
      OutputStream httpdlog = this.logManager.getLogStream();

      try {
         httpdlog.write(buf.getBytes(), 0, buf.size());
      } catch (IOException var9) {
      }

      return buf.size();
   }

   public void markRotated() {
   }

   public void needToWriteELFHeaders() {
   }

   private static class CLFDateFormatWithMillis {
      private Date date = new Date();
      private SimpleDateFormat format = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss.SSS Z]");

      CLFDateFormatWithMillis() {
         this.format.setTimeZone(TimeZone.getDefault());
      }

      byte[] getDateAsBytes(ServletRequestImpl req) {
         long utc = req.getHttpAccountingInfo().getInvokeTime();
         this.date.setTime(utc);
         return this.format.format(this.date).getBytes();
      }
   }

   private static class CLFDateFormat extends SimpleCachingDateFormat {
      private byte[] cachedBytes;
      private String cachedString;

      CLFDateFormat() {
         super("[dd/MMM/yyyy:HH:mm:ss Z]");
      }

      byte[] getDateAsBytes(ServletRequestImpl req) {
         long utc = req.getHttpAccountingInfo().getInvokeTime();
         String s = super.getDate(utc);
         if (s != this.cachedString) {
            this.cachedBytes = s.getBytes();
            this.cachedString = s;
         }

         return this.cachedBytes;
      }
   }
}
