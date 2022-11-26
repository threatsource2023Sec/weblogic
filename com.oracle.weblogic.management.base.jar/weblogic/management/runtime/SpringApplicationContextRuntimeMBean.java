package weblogic.management.runtime;

public interface SpringApplicationContextRuntimeMBean extends RuntimeMBean {
   String getDisplayName();

   String getParentContext();

   long getStartupDate();

   double getAveragePrototypeBeanCreationTime();

   long getPrototypeBeansCreatedCount();

   double getAverageSingletonBeanCreationTime();

   long getSingletonBeansCreatedCount();

   String[] getCustomScopeNames();

   double getAverageCustomScopeBeanCreationTime(String var1) throws IllegalArgumentException;

   long getCustomScopeBeansCreatedCount(String var1) throws IllegalArgumentException;

   double getAverageRefreshTime();

   long getRefreshCount();

   double getAverageGetBeanTime();

   long getGetBeanCount();

   double getAverageGetBeansOfTypeTime();

   long getGetBeansOfTypeCount();

   double getAverageGetBeanNamesForTypeTime();

   long getGetBeanNamesForTypeCount();
}
