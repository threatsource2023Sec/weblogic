package weblogic.j2ee.descriptor;

public interface FilterBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getFilterName();

   void setFilterName(String var1);

   String getFilterClass();

   void setFilterClass(String var1);

   boolean isFilterClassSet();

   boolean isAsyncSupported();

   void setAsyncSupported(boolean var1);

   boolean isAsyncSupportedSet();

   ParamValueBean[] getInitParams();

   ParamValueBean createInitParam();

   void destroyInitParam(ParamValueBean var1);

   ParamValueBean lookupInitParam(String var1);

   ParamValueBean createInitParam(String var1);

   String getId();

   void setId(String var1);
}
