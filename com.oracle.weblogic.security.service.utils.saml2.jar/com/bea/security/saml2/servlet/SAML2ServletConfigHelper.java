package com.bea.security.saml2.servlet;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.jdkutils.ServletAccess;
import com.bea.common.security.jdkutils.ServletInfoSpi;
import com.bea.common.security.jdkutils.ServletInfoV2Spi;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.SAML2Service;

public class SAML2ServletConfigHelper {
   public static final String SERVLET_INFO_KEY_PARAM = "ServletInfoKey";
   public static final String SERVLET_INFO_KEY_DEFAULT = "default";
   private String debugName = null;
   private static String staticKey = null;
   private String configKey = null;
   private SAML2Service service = null;
   private LoggerSpi logger = null;
   private IdentityService identityService = null;

   private SAML2ServletConfigHelper() {
   }

   protected SAML2ServletConfigHelper(String debugName) {
      this.debugName = debugName;
      if (debugName == null || debugName.equals("")) {
         debugName = "UnknownServletOrFilter";
      }

   }

   public static synchronized void setStaticServletInfoKey(String key) {
      if (key != null && !key.equals("")) {
         staticKey = key;
      }

   }

   protected synchronized void setConfigKey(String key) {
      if (key != null && !key.equals("")) {
         this.configKey = key;
      }

      this.debug("setConfigKey called with key '" + key + "'");
   }

   protected LoggerSpi getLogger() {
      if (this.logger == null) {
         this.initServices();
      }

      return this.logger;
   }

   protected SAML2Service getSAML2Service() {
      if (this.service == null) {
         this.initServices();
      }

      return this.service;
   }

   protected IdentityService getIdentityService() {
      if (this.identityService == null) {
         this.initServices();
      }

      return this.identityService;
   }

   protected void debug(String msg) {
      this.getLogger();
      this.debugInternal(msg);
   }

   private void debugInternal(String msg) {
      if (this.logger != null) {
         this.logger.debug(this.debugName + ": " + msg);
      }

   }

   private synchronized void initServices() {
      this.debugInternal("Static key is '" + staticKey + "'");
      this.debugInternal("Config key is '" + this.configKey + "'");
      String key = this.configKey;
      if ((key == null || key.equals("default")) && staticKey != null) {
         key = staticKey;
      }

      if (key != null) {
         this.debugInternal("Using key '" + key + "'");
         ServletInfoSpi servletInfo = ServletAccess.getInstance().getServletInfo(key);
         if (servletInfo == null) {
            this.debugInternal("ServletInfoSpi is null");
         } else {
            this.debugInternal("Got ServletInfoSpi");
            this.logger = (LoggerSpi)servletInfo.getLogger("SecuritySAML2Service");
            this.debugInternal("Initialized logger service");
            if (servletInfo instanceof ServletInfoV2Spi) {
               this.service = (SAML2Service)((ServletInfoV2Spi)servletInfo).getSAML2ServletFilterService();
               this.identityService = (IdentityService)((ServletInfoV2Spi)servletInfo).getIdentityService();
               this.debugInternal("Initialized SAML2 service");
            } else {
               this.debugInternal("ServletInfo not instance of ServletInfoV2Spi");
            }

         }
      }
   }
}
