package org.glassfish.grizzly.http.server.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import org.glassfish.grizzly.ssl.SSLSupport;
import org.glassfish.grizzly.ssl.SSLSupportImpl;

public class RequestUtils {
   private static final Logger LOGGER = Grizzly.logger(RequestUtils.class);

   public static Object populateCertificateAttribute(Request request) {
      Object certificates = null;
      if (request.getRequest().isSecure()) {
         if (!request.getRequest().isUpgrade()) {
            try {
               request.getInputBuffer().fillFully(request.getHttpFilter().getConfiguration().getMaxBufferedPostSize());
            } catch (IOException var4) {
               throw new IllegalStateException("Can't complete SSL re-negotation", var4);
            }
         }

         GrizzlyFuture certFuture = (new SSLBaseFilter.CertificateEvent(true)).trigger(request.getContext());

         try {
            certificates = certFuture.get(30L, TimeUnit.SECONDS);
         } catch (Exception var5) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unable to obtain certificates from peer.", var5);
            }
         }

         request.setAttribute("javax.servlet.request.X509Certificate", certificates);
      }

      return certificates;
   }

   public static void populateSSLAttributes(Request request) {
      if (request.isSecure()) {
         try {
            SSLSupport sslSupport = new SSLSupportImpl(request.getContext().getConnection());
            Object sslO = sslSupport.getCipherSuite();
            if (sslO != null) {
               request.setAttribute("javax.servlet.request.cipher_suite", sslO);
            }

            Object sslO = sslSupport.getPeerCertificateChain(false);
            if (sslO != null) {
               request.setAttribute("javax.servlet.request.X509Certificate", sslO);
            }

            Object sslO = sslSupport.getKeySize();
            if (sslO != null) {
               request.setAttribute("javax.servlet.request.key_size", sslO);
            }

            sslO = sslSupport.getSessionId();
            if (sslO != null) {
               request.setAttribute("javax.servlet.request.ssl_session_id", sslO);
            }
         } catch (Exception var3) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unable to populate SSL attributes", var3);
            }
         }
      }

   }

   public static void handleSendFile(Request request) {
      Object f = request.getAttribute("org.glassfish.grizzly.http.SEND_FILE");
      if (f != null) {
         Response response = request.getResponse();
         if (response.isCommitted()) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUESTUTILS_SENDFILE_FAILED());
            }

            return;
         }

         File file = (File)f;
         Long offset = (Long)request.getAttribute("org.glassfish.grizzly.http.FILE_START_OFFSET");
         Long len = (Long)request.getAttribute("org.glassfish.grizzly.http.FILE_WRITE_LEN");
         if (offset == null) {
            offset = 0L;
         }

         if (len == null) {
            len = file.length();
         }

         response.getOutputBuffer().sendfile(file, offset, len, (CompletionHandler)null);
      }

   }
}
