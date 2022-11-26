package weblogic.j2ee.descriptor;

public interface WebResourceCollectionBean {
   String getWebResourceName();

   void setWebResourceName(String var1);

   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getUrlPatterns();

   void addUrlPattern(String var1);

   void removeUrlPattern(String var1);

   void setUrlPatterns(String[] var1);

   String[] getHttpMethods();

   void addHttpMethod(String var1);

   void removeHttpMethod(String var1);

   void setHttpMethods(String[] var1);

   String[] getHttpMethodOmissions();

   void addHttpMethodOmission(String var1);

   void removeHttpMethodOmission(String var1);

   void setHttpMethodOmissions(String[] var1);

   String getId();

   void setId(String var1);
}
