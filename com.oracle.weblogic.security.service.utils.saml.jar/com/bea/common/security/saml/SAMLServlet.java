package com.bea.common.security.saml;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.jdkutils.ServletAccess;
import com.bea.common.security.jdkutils.ServletInfoSpi;
import com.bea.common.security.service.SAMLSingleSignOnService;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SAMLServlet extends HttpServlet {
   public static final String SERVLET_INFO_KEY_PARAM = "ServletInfoKey";
   public static final String SERVICE_NAME_PARAM = "ServiceName";
   public static final String SERVICE_NAME_ITS = "samlits";
   public static final String SERVICE_NAME_ARS = "samlars";
   public static final String SERVICE_NAME_ACS = "samlacs";
   public static final String[] SERVICE_NAMES = new String[]{"samlits", "samlars", "samlacs"};
   private String serviceTypeName = null;
   private ServletContext context = null;
   private SAMLSingleSignOnService samlService = null;
   private LoggerSpi log = null;

   private final boolean isDebugEnabled() {
      return this.log != null && this.log.isDebugEnabled();
   }

   private final void debug(String msg) {
      this.log.debug("SAMLServlet (" + this.serviceTypeName + "): " + msg);
   }

   public void init(ServletConfig sc) throws ServletException {
      this.context = sc.getServletContext();
      this.setServiceType(sc.getInitParameter("ServiceName"));
      String samlServletInfoKey = sc.getInitParameter("ServletInfoKey");
      if (samlServletInfoKey == null) {
         throw new ServletException("No ServletInfoKey parameter specified");
      } else {
         ServletInfoSpi servletInfo = ServletAccess.getInstance().getServletInfo(samlServletInfoKey);
         this.samlService = (SAMLSingleSignOnService)servletInfo.getSAMLServletFilterService();
         if (this.samlService == null) {
            throw new ServletException("Unable to get SAMLSingleSignOnService from servlet registry");
         } else {
            this.log = (LoggerSpi)servletInfo.getLogger("SecuritySAMLService");
            if (this.log == null) {
               throw new ServletException("Unable to get LoggerSpi from servlet registry");
            } else {
               if (this.log.isDebugEnabled()) {
                  this.debug("Initialized SAML " + this.serviceTypeName + " service");
               }

            }
         }
      }
   }

   public void destroy() {
   }

   private void logURIs(String method, HttpServletRequest req) {
      String requestURI = req.getRequestURI();
      this.debug(method + "(): Request URI is '" + requestURI + "'");
      String contextURI = req.getContextPath();
      String servletURI = requestURI.substring(contextURI.length());
      this.debug(method + "(): Servlet URI is '" + servletURI + "'");
   }

   private void handleException(String method, String httpRequest, Throwable ex, HttpServletResponse resp) {
      if (this.isDebugEnabled()) {
         String message;
         if (ex instanceof IOException) {
            message = "IOException while handling request, returning (no response)";
         } else if (ex instanceof ServletException) {
            message = "ServletException while handling request, returning INTERNAL_SERVER_ERROR";
         } else {
            message = "Unexpected throwable while handling request, returning INTERNAL_SERVER_ERROR";
         }

         this.debug(method + "(): " + message + ": " + ex);
      }

      try {
         if (!(ex instanceof IOException)) {
            resp.sendError(500);
         }
      } catch (IOException var6) {
         this.debug(method + "(): Unexpected IOException while trying to write response: " + ex);
      }

   }

   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      if (this.isDebugEnabled()) {
         this.logURIs("doGet", req);
      }

      try {
         if (this.samlService == null) {
            resp.sendError(500);
         } else if (this.serviceTypeName == "samlits") {
            this.samlService.doITSGet(req, resp, this.context);
         } else if (this.serviceTypeName == "samlacs") {
            this.samlService.doACSGet(req, resp);
         } else {
            this.debug("doGet(): Unexpected GET for " + this.serviceTypeName + " service, returning NOT_FOUND");
            resp.sendError(404);
         }

      } catch (Throwable var4) {
         this.handleException("doGet", "GET", var4, resp);
      }
   }

   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      if (this.isDebugEnabled()) {
         this.logURIs("doPost", req);
      }

      try {
         if (this.samlService == null) {
            resp.sendError(500);
         } else if (this.serviceTypeName == "samlars") {
            this.samlService.doARSPost(req, resp);
         } else {
            if (this.serviceTypeName != "samlacs") {
               if (this.isDebugEnabled()) {
                  this.debug("doPost(): Unexpected POST for " + this.serviceTypeName + " service, returning NOT_FOUND");
               }

               resp.sendError(404);
               return;
            }

            this.samlService.doACSPost(req, resp);
         }

      } catch (Throwable var4) {
         this.handleException("doPost", "POST", var4, resp);
      }
   }

   private void setServiceType(String serviceName) throws ServletException {
      if (serviceName != null && serviceName.length() != 0) {
         for(int i = 0; i < SERVICE_NAMES.length; ++i) {
            if (serviceName.equals(SERVICE_NAMES[i])) {
               this.serviceTypeName = SERVICE_NAMES[i];
               return;
            }
         }

         throw new ServletException("SAMLServlet: Invalid ServiceName parameter '" + serviceName + "'");
      } else {
         throw new ServletException("SAMLServlet: No ServiceName parameter");
      }
   }
}
