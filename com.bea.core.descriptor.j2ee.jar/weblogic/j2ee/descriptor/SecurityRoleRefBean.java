package weblogic.j2ee.descriptor;

public interface SecurityRoleRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getRoleName();

   void setRoleName(String var1);

   String getRoleLink();

   void setRoleLink(String var1);

   String getId();

   void setId(String var1);
}
