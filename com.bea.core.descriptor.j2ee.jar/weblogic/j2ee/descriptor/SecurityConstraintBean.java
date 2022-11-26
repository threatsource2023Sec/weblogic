package weblogic.j2ee.descriptor;

public interface SecurityConstraintBean {
   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   WebResourceCollectionBean[] getWebResourceCollections();

   WebResourceCollectionBean createWebResourceCollection();

   void destroyWebResourceCollection(WebResourceCollectionBean var1);

   AuthConstraintBean getAuthConstraint();

   AuthConstraintBean createAuthConstraint();

   void destroyAuthConstraint(AuthConstraintBean var1);

   UserDataConstraintBean getUserDataConstraint();

   UserDataConstraintBean createUserDataConstraint();

   void destroyUserDataConstraint(UserDataConstraintBean var1);

   String getId();

   void setId(String var1);
}
