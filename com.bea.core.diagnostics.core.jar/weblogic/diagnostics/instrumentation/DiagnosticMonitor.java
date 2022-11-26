package weblogic.diagnostics.instrumentation;

public interface DiagnosticMonitor extends DyeFilterable {
   String getName();

   void setName(String var1);

   String getDescription();

   void setDescription(String var1);

   String getType();

   boolean isServerScopeAllowed();

   boolean isServerManaged();

   void setServerManaged(boolean var1);

   String getDiagnosticVolume();

   void setDiagnosticVolume(String var1);

   String getEventClassName();

   Class getEventClass();

   void setEventClassName(String var1);

   boolean isComponentScopeAllowed();

   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isEnabledAndNotDyeFiltered();

   boolean isArgumentsCaptureNeeded();

   String[] getAttributeNames();

   String getAttribute(String var1);

   void setAttribute(String var1, String var2);

   String[] getIncludes();

   void setIncludes(String[] var1);

   String[] getExcludes();

   void setExcludes(String[] var1);
}
