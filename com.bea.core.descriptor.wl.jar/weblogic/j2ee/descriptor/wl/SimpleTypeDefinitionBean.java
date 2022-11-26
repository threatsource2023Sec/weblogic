package weblogic.j2ee.descriptor.wl;

public interface SimpleTypeDefinitionBean {
   String getBaseType();

   void setBaseType(String var1);

   MemberConstraintBean getConstraint();

   MemberConstraintBean createConstraint();

   boolean getRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   String[] getDefaultValue();

   void setDefaultValue(String[] var1);
}
