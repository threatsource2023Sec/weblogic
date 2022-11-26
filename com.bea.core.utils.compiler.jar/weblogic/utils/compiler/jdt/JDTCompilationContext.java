package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import weblogic.utils.compiler.JavaCompilationContext;

public class JDTCompilationContext implements JavaCompilationContext {
   private String classPath;
   private String[] classNames;
   private String outputRoot;
   private String[] fileNames;
   private char[][] contents;
   private INameEnvironment fileSystem;
   private String smap;
   private boolean reportWarning;

   public JDTCompilationContext(String classpath, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, boolean reportWarning) {
      this.classPath = classpath;
      this.classNames = classNames;
      this.outputRoot = outputRoot;
      this.fileNames = fileNames;
      this.contents = contents;
      this.reportWarning = reportWarning;
   }

   public JDTCompilationContext(INameEnvironment fileSystem, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, String smap, boolean reportWarning) {
      this.fileSystem = fileSystem;
      this.classNames = classNames;
      this.outputRoot = outputRoot;
      this.fileNames = fileNames;
      this.contents = contents;
      this.smap = smap;
      this.reportWarning = reportWarning;
   }

   public JDTCompilationContext() {
   }

   public void setClassPath(String classPath) {
      this.classPath = classPath;
   }

   public void setClassNames(String[] classNames) {
      this.classNames = classNames;
   }

   public void setContents(char[][] contents) {
      this.contents = contents;
   }

   public void setFileNames(String[] fileNames) {
      this.fileNames = fileNames;
   }

   public void setOutputRoot(String outputRoot) {
      this.outputRoot = outputRoot;
   }

   public void setFileSystem(INameEnvironment fs) {
      this.fileSystem = fs;
   }

   public void setSmap(String smap) {
      this.smap = smap;
   }

   public void setReportWaning(boolean reportWarning) {
      this.reportWarning = reportWarning;
   }

   public String[] getClassNames() {
      return this.classNames;
   }

   public String getClassPath() {
      return this.classPath;
   }

   public char[][] getContents() {
      return this.contents;
   }

   public String[] getFileNames() {
      return this.fileNames;
   }

   public String getOutputRoot() {
      return this.outputRoot;
   }

   public INameEnvironment getFileSystem() {
      return this.fileSystem;
   }

   public String getSmap() {
      return this.smap;
   }

   public boolean getReportWaning() {
      return this.reportWarning;
   }
}
