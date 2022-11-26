package kodo.conf.descriptor;

public interface ExportProfilingBean extends ProfilingBean {
   int getIntervalMillis();

   void setIntervalMillis(int var1);

   String getBaseName();

   void setBaseName(String var1);
}
