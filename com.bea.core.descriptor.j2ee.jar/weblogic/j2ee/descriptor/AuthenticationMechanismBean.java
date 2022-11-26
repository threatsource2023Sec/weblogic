package weblogic.j2ee.descriptor;

public interface AuthenticationMechanismBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getAuthenticationMechanismType();

   void setAuthenticationMechanismType(String var1);

   String getCredentialInterface();

   void setCredentialInterface(String var1);

   String getId();

   void setId(String var1);
}
