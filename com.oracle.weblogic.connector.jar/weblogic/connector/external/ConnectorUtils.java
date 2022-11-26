package weblogic.connector.external;

import weblogic.connector.external.impl.RAComplianceCheckerImpl;
import weblogic.connector.external.impl.RAInfoImpl;
import weblogic.connector.security.outbound.SecurityContext;

public class ConnectorUtils {
   public static final EndpointActivationUtils endpointActivation = weblogic.connector.external.impl.EndpointActivationUtils.getAccessor();
   public static final RAInfo raInfo;

   public static RAComplianceChecker createRAComplianceChecker() {
      return new RAComplianceCheckerImpl();
   }

   public static String getEISResourceId(String resAppId, String resModName, String resEisType, String resKey) {
      return SecurityContext.getEISResourceId(resAppId, resModName, resEisType, resKey);
   }

   static {
      raInfo = RAInfoImpl.factoryHelper;
   }
}
