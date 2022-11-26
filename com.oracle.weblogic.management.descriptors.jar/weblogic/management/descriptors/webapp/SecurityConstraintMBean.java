package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface SecurityConstraintMBean extends WebElementMBean {
   String getDisplayName();

   void setDisplayName(String var1);

   String getDisplayString();

   WebResourceCollectionMBean[] getWebResourceCollection();

   void setWebResourceCollection(WebResourceCollectionMBean[] var1);

   void addWebResourceCollection(WebResourceCollectionMBean var1);

   void removeWebResourceCollection(WebResourceCollectionMBean var1);

   AuthConstraintMBean getAuthConstraint();

   void setAuthConstraint(AuthConstraintMBean var1);

   UserDataConstraintMBean getUserDataConstraint();

   void setUserDataConstraint(UserDataConstraintMBean var1);
}
