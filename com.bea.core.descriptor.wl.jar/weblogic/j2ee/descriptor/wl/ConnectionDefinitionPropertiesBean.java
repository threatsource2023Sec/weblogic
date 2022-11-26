package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;

public interface ConnectionDefinitionPropertiesBean extends SettableBean {
   PoolParamsBean getPoolParams();

   boolean isPoolParamsSet();

   LoggingBean getLogging();

   boolean isLoggingSet();

   String getTransactionSupport();

   void setTransactionSupport(String var1);

   AuthenticationMechanismBean[] getAuthenticationMechanisms();

   AuthenticationMechanismBean createAuthenticationMechanism();

   void destroyAuthenticationMechanism(AuthenticationMechanismBean var1);

   boolean isReauthenticationSupport();

   void setReauthenticationSupport(boolean var1);

   ConfigPropertiesBean getProperties();

   boolean isPropertiesSet();

   String getResAuth();

   void setResAuth(String var1);

   String getId();

   void setId(String var1);
}
