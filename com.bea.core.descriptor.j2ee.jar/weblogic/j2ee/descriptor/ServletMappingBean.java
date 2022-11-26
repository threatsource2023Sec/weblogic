package weblogic.j2ee.descriptor;

public interface ServletMappingBean {
   String getServletName();

   void setServletName(String var1);

   String[] getUrlPatterns();

   void setUrlPatterns(String[] var1);

   void addUrlPattern(String var1);

   void removeUrlPattern(String var1);

   String getId();

   void setId(String var1);
}
