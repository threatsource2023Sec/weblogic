package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface WebResourceCollectionMBean extends WebElementMBean {
   String getResourceName();

   void setResourceName(String var1);

   String getDescription();

   void setDescription(String var1);

   String[] getUrlPatterns();

   void setUrlPatterns(String[] var1);

   void addUrlPattern(String var1);

   void removeUrlPattern(String var1);

   String[] getHttpMethods();

   void setHttpMethods(String[] var1);

   void addHttpMethod(String var1);

   void removeHttpMethod(String var1);
}
