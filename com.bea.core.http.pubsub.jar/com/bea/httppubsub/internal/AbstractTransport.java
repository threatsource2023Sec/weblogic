package com.bea.httppubsub.internal;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.servlet.http.AbstractAsyncServlet;
import weblogic.servlet.http.RequestResponseKey;

public abstract class AbstractTransport implements Transport, AuthenticatedUser {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   protected RequestResponseKey rrk;
   protected boolean isCommentFiltered;
   protected boolean isNormalPolling = false;
   protected String cookiePath;
   private List messages;

   protected AbstractTransport(RequestResponseKey rrk, String cookiePath) {
      this.rrk = rrk;
      this.cookiePath = cookiePath;
   }

   public void setCommentFiltered(boolean isCommentFiltered) {
      this.isCommentFiltered = isCommentFiltered;
   }

   public void send() throws IOException {
      HttpServletResponse response = this.rrk.getResponse();
      response.setContentType(this.getResponseContentType());
      if (logger.isDebugEnabled()) {
         logger.debug("<<< Content-Type set to: " + this.getResponseContentType());
      }

      OutputStream os = response.getOutputStream();
      byte[] header = this.getHeaderBytes();
      byte[] tailer = this.getTailerBytes();
      byte[] seperator = this.getSeperatorBytes();
      os.write(header);
      int index = 0;
      Iterator it = this.messages.iterator();

      while(it.hasNext()) {
         AbstractBayeuxMessage message = (AbstractBayeuxMessage)it.next();
         if (logger.isDebugEnabled()) {
            logger.debug("<<< " + message.toJSONResponseString());
         }

         os.write(message.getJSONResponseUTF8Bytes());
         if (index++ != this.messages.size() - 1) {
            os.write(seperator);
         }
      }

      os.write(tailer);
      os.flush();
   }

   public void send(List messages) throws IOException {
      if (!this.isValid()) {
         if (logger.isDebugEnabled()) {
            logger.debug("<<< invalid transport: " + this);
         }

      } else {
         this.messages = messages;
         AbstractAsyncServlet.notify(this.rrk, this);
      }
   }

   public boolean isValid() {
      return this.rrk != null && this.rrk.isValid();
   }

   protected abstract String getHeader();

   protected abstract byte[] getHeaderBytes();

   protected abstract String getTailer();

   protected abstract byte[] getTailerBytes();

   protected abstract String getSeperator();

   protected abstract byte[] getSeperatorBytes();

   protected abstract Client.ConnectionType getConnectionType();

   protected abstract String getResponseContentType();

   public String getUserName() {
      return this.rrk.getRequest().getRemoteUser();
   }

   public Principal getUserPrincipal() {
      return this.rrk.getRequest().getUserPrincipal();
   }

   public boolean isUserInRole(String role) {
      return this.rrk.getRequest().isUserInRole(role);
   }

   public HttpSession getSession() {
      return this.rrk.getRequest().getSession(false);
   }

   public String getBrowserId() {
      if (!this.rrk.isValid()) {
         return null;
      } else {
         Cookie[] cookies = this.rrk.getRequest().getCookies();
         int port;
         if (cookies != null) {
            Cookie[] var2 = cookies;
            int var3 = cookies.length;

            for(port = 0; port < var3; ++port) {
               Cookie cookie = var2[port];
               if ("Bayeux_HTTP_ID".equals(cookie.getName())) {
                  if (logger.isDebugEnabled()) {
                     logger.debug("Found cookie Bayeux_HTTP_ID from incoming request.");
                  }

                  return cookie.getValue();
               }
            }
         }

         String addr = this.rrk.getRequest().getRemoteAddr();
         String host = this.rrk.getRequest().getRemoteHost();
         port = this.rrk.getRequest().getRemotePort();
         String browserId = Long.toHexString((long)host.hashCode()) + Long.toHexString((long)addr.hashCode()) + Long.toHexString((long)port) + Long.toHexString(System.currentTimeMillis());
         Cookie cookie = new Cookie("Bayeux_HTTP_ID", browserId);
         cookie.setPath(this.cookiePath);
         cookie.setMaxAge(-1);
         this.rrk.getResponse().addCookie(cookie);
         if (logger.isDebugEnabled()) {
            logger.debug("Set cookie Bayeux_HTTP_ID to: " + browserId);
         }

         return browserId;
      }
   }

   public void setNormalPolling(boolean isNormalPolling) {
      this.isNormalPolling = isNormalPolling;
   }

   public boolean isNormalPolling() {
      return this.isNormalPolling;
   }
}
