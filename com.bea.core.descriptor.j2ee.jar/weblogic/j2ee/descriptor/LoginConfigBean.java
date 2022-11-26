package weblogic.j2ee.descriptor;

public interface LoginConfigBean {
   String getAuthMethod();

   void setAuthMethod(String var1);

   String getRealmName();

   void setRealmName(String var1);

   FormLoginConfigBean getFormLoginConfig();

   String getId();

   void setId(String var1);
}
