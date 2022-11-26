package weblogic.j2ee.descriptor;

public interface SecurityPermissionBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getSecurityPermissionSpec();

   void setSecurityPermissionSpec(String var1);

   String getId();

   void setId(String var1);
}
