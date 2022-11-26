package kodo.conf.descriptor;

public interface LogFactoryImplBean extends LogBean {
   String getDiagnosticContext();

   void setDiagnosticContext(String var1);

   String getDefaultLevel();

   void setDefaultLevel(String var1);

   String getFile();

   void setFile(String var1);
}
