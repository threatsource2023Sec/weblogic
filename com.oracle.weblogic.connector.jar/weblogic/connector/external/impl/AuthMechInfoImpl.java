package weblogic.connector.external.impl;

import weblogic.connector.external.AuthMechInfo;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;

public class AuthMechInfoImpl implements AuthMechInfo {
   AuthenticationMechanismBean authMechBean;

   public AuthMechInfoImpl(AuthenticationMechanismBean authMechBean) {
      this.authMechBean = authMechBean;
   }

   public String getDescription() {
      String[] descriptions = this.authMechBean.getDescriptions();
      return descriptions.length > 0 ? descriptions[0] : "";
   }

   public String[] getDescriptions() {
      return this.authMechBean.getDescriptions();
   }

   public String getType() {
      return this.authMechBean.getAuthenticationMechanismType();
   }

   public String getCredentialInterface() {
      return this.authMechBean.getCredentialInterface();
   }
}
