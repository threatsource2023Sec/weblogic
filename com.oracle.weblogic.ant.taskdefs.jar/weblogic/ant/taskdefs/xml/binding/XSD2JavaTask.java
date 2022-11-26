package weblogic.ant.taskdefs.xml.binding;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

public class XSD2JavaTask extends Task {
   private List mFileSets = null;
   private String mSchema = "";
   private String mPackage = null;
   private String mDestDir = null;
   private boolean mReadOnly = false;
   private boolean mNoValidation = false;
   private boolean mExtension = false;
   private String mSrc = null;
   private String mBinding = null;
   private static final String XJC_COMPILER = "com.sun.tools.xjc.Driver";
   private static final String SEP;
   private static final String DEPOT_ENV = "depot";
   private static final String SRC_ENV = "src";
   private static final String DEV = "dev";
   private static final String[] JAXB_JARS;

   public void setExtension(boolean b) {
      this.mExtension = b;
   }

   public void setSchema(String schema) {
      this.mSchema = schema;
   }

   public void setPackage(String pakkage) {
      this.mPackage = pakkage;
   }

   public void setDestDir(String dir) {
      this.mDestDir = dir;
   }

   public void setNoValidation(boolean nv) {
      this.mNoValidation = nv;
   }

   public void setReadOnly(boolean ro) {
      this.mReadOnly = ro;
   }

   public void setBinding(String binding) {
      this.mBinding = binding;
   }

   public void addFileSet(FileSet fs) {
      if (this.mFileSets == null) {
         this.mFileSets = new ArrayList();
      }

      this.mFileSets.add(fs);
   }

   public void execute() throws BuildException {
      String src = this.getSrcDir() + SEP;
      Java javaTask = new Java();
      javaTask.setTaskName("xjc");
      javaTask.setProject(this.getProject());
      Path cp = javaTask.createClasspath();

      for(int i = 0; i < JAXB_JARS.length; ++i) {
         cp.addExisting(new Path(this.getProject(), src + JAXB_JARS[i]));
      }

      cp.addExisting(Path.systemClasspath);
      javaTask.setClasspath(cp);
      javaTask.setFork(true);
      javaTask.setClassname("com.sun.tools.xjc.Driver");
      if (this.mDestDir != null) {
         javaTask.createArg().setLine("-d " + this.mDestDir);
      }

      if (this.mPackage != null) {
         javaTask.createArg().setLine("-p " + this.mPackage);
      }

      if (this.mBinding != null) {
         javaTask.createArg().setLine("-b " + this.mBinding);
      }

      if (this.mExtension) {
         javaTask.createArg().setLine("-extension");
      }

      if (this.mReadOnly) {
         javaTask.createArg().setLine("-readOnly");
      }

      if (this.mNoValidation) {
         javaTask.createArg().setLine("-nv");
      }

      if (this.mFileSets != null) {
         Iterator i = this.mFileSets.iterator();

         while(i.hasNext()) {
            FileSet fs = (FileSet)i.next();
            DirectoryScanner ds = fs.getDirectoryScanner(this.getProject());
            String[] files = ds.getIncludedFiles();

            for(int j = 0; j < files.length; ++j) {
               File f = new File(ds.getBasedir(), files[j]);
               this.mSchema = this.mSchema + " " + f;
            }
         }
      }

      javaTask.createArg().setLine(this.mSchema);
      this.log("Running xjc on '" + this.mSchema + "'");
      javaTask.execute();
   }

   private final String getSrcDir() {
      if (this.mSrc != null) {
         return this.mSrc;
      } else {
         Vector env = Execute.getProcEnvironment();
         if (env != null) {
            String depot = null;
            String src = null;

            for(int i = 0; i < env.size(); ++i) {
               String entry = (String)env.get(i);
               int eq = entry.indexOf("=");
               if (eq != -1 && eq != entry.length() - 1) {
                  String name = entry.substring(0, eq).trim();
                  if (name.equalsIgnoreCase("depot")) {
                     depot = entry.substring(eq + 1);
                  } else if (name.equalsIgnoreCase("src")) {
                     src = entry.substring(eq + 1);
                  }
               }
            }

            if (depot != null && src != null) {
               return depot + SEP + "dev" + SEP + src;
            }
         }

         this.log("WARNING: could not determine src root, using '.'");
         return ".";
      }
   }

   static {
      SEP = File.separator;
      JAXB_JARS = new String[]{"3rdparty" + SEP + "jaxb" + SEP + "jaxb-api.jar", "3rdparty" + SEP + "jaxb" + SEP + "jaxb-libs.jar", "3rdparty" + SEP + "jaxb" + SEP + "jaxb-ri.jar", "3rdparty" + SEP + "jaxb" + SEP + "jaxb-xjc.jar", "3rdparty" + SEP + "jaxb" + SEP + "sax.jar", "3rdparty" + SEP + "jaxb" + SEP + "xercesImpl.jar"};
   }
}
