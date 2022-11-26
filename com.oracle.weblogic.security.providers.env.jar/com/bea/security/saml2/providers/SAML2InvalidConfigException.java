package com.bea.security.saml2.providers;

import weblogic.security.spi.ProviderInitializationException;

public class SAML2InvalidConfigException extends ProviderInitializationException {
   String paramName;
   String paramValue;
   String paramContext;

   public SAML2InvalidConfigException(String paramName) {
      this(paramName, (String)null, (String)null);
   }

   public SAML2InvalidConfigException(String paramName, String paramValue) {
      this(paramName, paramValue, (String)null);
   }

   public SAML2InvalidConfigException(String paramName, String paramValue, String paramContext) {
      super(constructMsg(paramName, paramValue, paramContext));
      this.paramName = null;
      this.paramValue = null;
      this.paramContext = null;
      this.paramName = paramName;
      this.paramValue = paramValue;
      this.paramContext = paramContext;
   }

   private static String constructMsg(String paramName, String paramValue, String paramContext) {
      String msg = null;
      if (paramValue == null) {
         msg = "Missing " + paramName;
      } else {
         msg = "Invalid " + paramName + " value '" + paramValue + "'";
      }

      if (paramContext != null) {
         msg = msg + " for '" + paramContext + "'";
      }

      return msg;
   }

   public String getParamContext() {
      return this.paramContext;
   }

   public void setParamContext(String paramContext) {
      this.paramContext = paramContext;
   }

   public String getParamName() {
      return this.paramName;
   }

   public void setParamName(String paramName) {
      this.paramName = paramName;
   }

   public String getParamValue() {
      return this.paramValue;
   }

   public void setParamValue(String paramValue) {
      this.paramValue = paramValue;
   }
}
