package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;

public class Jspc extends CompilerTask {
   private String src;
   private String jspname;
   private String contextpath;
   private String superclass;
   private String encoding;
   private String packagename;
   private String docroot;
   private boolean skipJavac;
   private String compiler;
   private String compileall;

   public void setCompileAll(String compileall) {
      this.compileall = compileall;
   }

   public void setCompiler(String compiler) {
      this.compiler = compiler;
   }

   public void setSrc(String src) {
      this.src = src;
   }

   public void setJspname(String jspname) {
      this.jspname = jspname;
   }

   public void setContextPath(String contextpath) {
      this.contextpath = contextpath;
   }

   public void setSuperClass(String superclass) {
      this.superclass = superclass;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setPackage(String packagename) {
      this.packagename = packagename;
   }

   public void setDocRoot(String docroot) {
      this.docroot = docroot;
   }

   public void setSkipJavac(boolean skipJavac) {
      this.skipJavac = skipJavac;
   }

   public void execute() throws BuildException {
      Vector flags = super.getFlags();
      if (this.compileall != null) {
         flags.addElement("-compileAll");
         flags.addElement(this.compileall);
      }

      if (this.compiler != null) {
         flags.addElement("-compiler");
         flags.addElement(this.compiler);
      }

      if (this.contextpath != null) {
         flags.addElement("-contextPath");
         flags.addElement(this.contextpath);
      }

      if (this.superclass != null) {
         flags.addElement("-superclass");
         flags.addElement(this.superclass);
      }

      if (this.encoding != null) {
         flags.addElement("-encoding");
         flags.addElement(this.encoding);
      }

      if (this.packagename != null) {
         flags.addElement("-package");
         flags.addElement(this.packagename);
      }

      if (this.docroot != null) {
         flags.addElement("-docroot");
         flags.addElement(this.docroot);
      }

      if (this.skipJavac) {
         flags.addElement("-skipJavac");
      }

      int numFlags = flags.size();
      File srcDir = this.src != null ? this.project.resolveFile(this.src) : new File(".");
      File destDir = this.project.resolveFile(this.destdir);
      if (this.jspname == null) {
         DirectoryScanner ds = this.getDirectoryScanner(srcDir);
         String[] files = ds.getIncludedFiles();
         this.scanDir(srcDir, destDir, files, flags);
      } else if (this.shouldCompile(this.jspname, srcDir, destDir)) {
         flags.addElement(this.jspname);
      }

      if (flags.size() > numFlags) {
         String[] args = this.getArgs(flags);
         this.log("Compiling " + (args.length - numFlags) + " jsps to " + this.destdir, 2);
         this.invokeMain("weblogic.jspc", args);
      }

   }

   protected void scanDir(File srcDir, File destdir, String[] files, Vector compileList) {
      for(int i = 0; i < files.length; ++i) {
         if (files[i].endsWith(".jsp") && this.shouldCompile(files[i], srcDir, destdir)) {
            compileList.addElement(files[i]);
         }
      }

   }

   private File getTarget(File destDir, String jspFile) {
      StringBuffer path = new StringBuffer("");
      if (this.packagename != null) {
         path.append(this.packagename.replace('.', File.separatorChar));
      } else {
         path.append("jsp_servlet");
      }

      String baseName = jspFile.substring(0, jspFile.lastIndexOf(".jsp"));
      StringTokenizer st = new StringTokenizer(baseName, "/\\");

      while(st.hasMoreTokens()) {
         String element = st.nextToken();
         path.append(File.separatorChar);
         path.append("_");
         path.append(element);
      }

      path.append(".class");
      return new File(destDir, path.toString());
   }

   private boolean shouldCompile(String jsp, File srcdir, File destdir) {
      File jspFile = new File(srcdir, jsp);
      File jspClass = this.getTarget(destdir, jsp);
      if (!jspClass.exists()) {
         return true;
      } else {
         if (jspClass.lastModified() > System.currentTimeMillis()) {
            this.log("Warning: file modified in the future: " + jspClass, 1);
         }

         return jspFile.lastModified() > jspClass.lastModified();
      }
   }
}
