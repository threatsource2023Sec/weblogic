package com.bea.httppubsub.descriptor;

public interface AuthConstraintBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getRoleNames();

   void addRoleName(String var1);

   void removeRoleName(String var1);

   void setRoleNames(String[] var1);
}