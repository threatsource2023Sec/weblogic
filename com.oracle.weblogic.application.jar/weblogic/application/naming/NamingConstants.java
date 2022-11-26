package weblogic.application.naming;

import java.util.Random;

public interface NamingConstants {
   String WLInternalNS = (new Random() {
      public String toString() {
         char[] charPool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
         int randomPartLength = 64;
         char[] randomText = new char[randomPartLength];

         for(int i = 0; i < randomPartLength; ++i) {
            randomText[i] = charPool[this.nextInt(charPool.length)];
         }

         return "_WL_internal_" + new String(randomText);
      }
   }).toString();
   String AppNS = "app";
   String JavaAppNS = "java:app";
   String INTERNAL_APP_NS = WLInternalNS + "/app";
   String ModuleNS = "module";
   String JavaModuleNS = "java:module";
   String INTERNAL_MODULE_NS = WLInternalNS + "/module";
   String COMP_NS = "comp";
   String JavaCompNS = "java:comp";
   String INTERNAL_COMP_NS = WLInternalNS + "/comp";
   String Global = "global";
   String GlobalNS = "java:global";
   String InternalGlobalNS = WLInternalNS + "/global";
   String WL_INTERNAL_SUB_CTX = "_WL_internal";
   String envSubCtx = "env";
   String ModuleName = "ModuleName";
   String AppName = "AppName";
   String GlobalJavaAppCtx = "__WL_GlobalJavaApp";
   String ACTIVE_ID_PROPERTY_NAME = "__WL_Active_Application_Version";
   String ApplicationId = "ApplicationId";
   String BEA_CTX = "bea";
   String MODULE_NAME_BINDING = "bea/ModuleName";
   String EJB_CONTEXT_BINDING = "java:comp/EJBContext";
   String TIMERSERVICE_BINDING = "java:comp/TimerService";
   String WEB_SERVICE_CONTEXT_BINDING = "java:comp/WebServiceContext";
   String VALIDATOR_BINDING = "java:comp/Validator";
   String VALIDATOR_FACTORY_BINDING = "java:comp/ValidatorFactory";
   String DEFAULT_WM_BINDING = "java:comp/env/wm/default";
   String DEFAULT_DATA_SOURCE_BINDING = "java:comp/DefaultDataSource";
   String WLS_CONNECTOR_RESREF = "wls-connector-resref";
}
