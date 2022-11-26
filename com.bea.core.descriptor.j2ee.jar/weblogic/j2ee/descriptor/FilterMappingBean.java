package weblogic.j2ee.descriptor;

public interface FilterMappingBean {
   String getFilterName();

   void setFilterName(String var1);

   String[] getUrlPatterns();

   void setUrlPatterns(String[] var1);

   String[] getServletNames();

   void setServletNames(String[] var1);

   String[] getDispatchers();

   void addDispatcher(String var1);

   void removeDispatcher(String var1);

   void setDispatchers(String[] var1);

   String getId();

   void setId(String var1);
}
