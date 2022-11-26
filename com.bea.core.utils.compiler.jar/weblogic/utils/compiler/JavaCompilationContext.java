package weblogic.utils.compiler;

public interface JavaCompilationContext {
   void setClassPath(String var1);

   void setOutputRoot(String var1);

   void setFileNames(String[] var1);

   void setClassNames(String[] var1);

   void setContents(char[][] var1);

   void setSmap(String var1);

   void setReportWaning(boolean var1);

   String getClassPath();

   String getOutputRoot();

   String[] getFileNames();

   String[] getClassNames();

   char[][] getContents();

   String getSmap();

   boolean getReportWaning();
}
