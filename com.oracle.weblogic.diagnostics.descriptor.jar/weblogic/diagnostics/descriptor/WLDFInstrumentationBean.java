package weblogic.diagnostics.descriptor;

public interface WLDFInstrumentationBean extends WLDFBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   String[] getIncludes();

   void setIncludes(String[] var1);

   String[] getExcludes();

   void setExcludes(String[] var1);

   WLDFInstrumentationMonitorBean[] getWLDFInstrumentationMonitors();

   WLDFInstrumentationMonitorBean createWLDFInstrumentationMonitor(String var1);

   void destroyWLDFInstrumentationMonitor(WLDFInstrumentationMonitorBean var1);
}
