package weblogic.security.providers.saml;

import com.bea.common.security.ProvidersLogger;
import weblogic.security.spi.ProviderInitializationException;

public class SAMLInvalidConfigException extends ProviderInitializationException {
   String paramName;
   String paramValue;
   String paramContext;

   public SAMLInvalidConfigException(String paramName) {
      this(paramName, (String)null, (String)null);
   }

   public SAMLInvalidConfigException(String paramName, String paramValue) {
      this(paramName, paramValue, (String)null);
   }

   public SAMLInvalidConfigException(String paramName, String paramValue, String paramContext) {
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
         msg = ProvidersLogger.getMissingParameter(paramName);
      } else {
         msg = ProvidersLogger.getIllegalValue(paramName, paramValue);
      }

      if (paramContext != null) {
         msg = ProvidersLogger.getIllegalValueForContext(paramName, paramValue, paramContext);
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
