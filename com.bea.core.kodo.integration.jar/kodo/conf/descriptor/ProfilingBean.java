package kodo.conf.descriptor;

public interface ProfilingBean {
   NoneProfilingBean getNoneProfiling();

   NoneProfilingBean createNoneProfiling();

   void destroyNoneProfiling();

   LocalProfilingBean getLocalProfiling();

   LocalProfilingBean createLocalProfiling();

   void destroyLocalProfiling();

   ExportProfilingBean getExportProfiling();

   ExportProfilingBean createExportProfiling();

   void destroyExportProfiling();

   GUIProfilingBean getGUIProfiling();

   GUIProfilingBean createGUIProfiling();

   void destroyGUIProfiling();

   Class[] getProfilingTypes();

   ProfilingBean getProfiling();

   ProfilingBean createProfiling(Class var1);

   void destroyProfiling();
}
