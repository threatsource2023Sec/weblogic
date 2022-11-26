package weblogic.diagnostics.descriptor;

public interface WLDFInstrumentationMonitorBean extends WLDFBean {
   String getDescription();

   void setDescription(String var1);

   boolean isEnabled();

   void setEnabled(boolean var1);

   String getDyeMask();

   void setDyeMask(String var1);

   boolean isDyeFilteringEnabled();

   void setDyeFilteringEnabled(boolean var1);

   String getProperties();

   void setProperties(String var1);

   String[] getActions();

   void setActions(String[] var1);

   String getLocationType();

   void setLocationType(String var1);

   String getPointcut();

   void setPointcut(String var1);

   String[] getIncludes();

   void setIncludes(String[] var1);

   String[] getExcludes();

   void setExcludes(String[] var1);
}
