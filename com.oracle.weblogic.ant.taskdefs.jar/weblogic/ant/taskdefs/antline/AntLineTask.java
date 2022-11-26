package weblogic.ant.taskdefs.antline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;

public class AntLineTask extends Task {
   static boolean debug = System.getProperty(DEBUG_PROPERTY()) != null;
   private String packageName;
   private String commandName;
   private String taskName;
   private File destDir = new File(".");
   private boolean keepGenerated = true;
   private List options = new ArrayList();

   static String DEBUG_PROPERTY() {
      return "antline.debug";
   }

   public void setPackagename(String val) {
      this.packageName = val;
   }

   public void setCommandname(String val) {
      this.commandName = val;
   }

   public void setTaskName(String val) {
      this.taskName = val;
   }

   public void setDestdir(File val) {
      this.destDir = val;
   }

   public void setKeepgenerated(boolean val) {
      this.keepGenerated = val;
   }

   public Option createOption() {
      Option opt = new Option();
      this.options.add(opt);
      return opt;
   }

   public void execute() {
      this.validateAttributes();
      this.log("Generating command line tool " + this.packageName + "." + this.commandName + " ...");
      String thisPackageLoc = "/" + this.getMyPackage().replace('.', '/');
      File javaFile = null;
      PrintStream javaOut = null;

      try {
         GenBase toolgen = new GenBase(thisPackageLoc + "/tool.cg", false);
         toolgen.setVar("packageName", this.packageName);
         toolgen.setVar("cmdName", this.commandName);
         toolgen.setVar("taskName", this.taskName);
         toolgen.setVar("options", this.options);
         StringBuffer addOptions = new StringBuffer();
         Iterator i = this.options.iterator();

         while(i.hasNext()) {
            Option opt = (Option)i.next();
            String optName = "\"" + opt.getOptname() + "\"";
            String antName = "\"" + opt.getAntname() + "\"";
            String arg = quotedValOrNullText(opt.getArg());
            String desc = quotedValOrNullText(opt.getDescription());
            String converter = quotedValOrNullText(opt.getConverter());
            addOptions.append("    tool.addOption(");
            addOptions.append(optName);
            addOptions.append(", ");
            addOptions.append(antName);
            addOptions.append(", ");
            addOptions.append(arg);
            addOptions.append(", ");
            addOptions.append(desc);
            addOptions.append(", ");
            addOptions.append(converter);
            addOptions.append(");\n");
         }

         toolgen.setVar("addOptions", addOptions.toString());
         javaFile = new File(this.destDir, this.packageName.replace('.', '/') + "/" + this.commandName + ".java");
         javaFile.getParentFile().mkdirs();
         if (debug) {
            System.out.println("Outputting Java file " + javaFile);
         }

         javaOut = new PrintStream(new FileOutputStream(javaFile));
         toolgen.setOutput(javaOut);
         toolgen.gen();
      } catch (Exception var22) {
         throw new BuildException(var22);
      } finally {
         try {
            if (javaOut != null) {
               javaOut.close();
            }
         } catch (Exception var21) {
            throw new BuildException(var21);
         }

      }

      this.log("Compiling " + javaFile + " ...");
      this.javacFile(javaFile, this.destDir);
      if (!this.keepGenerated) {
         System.out.println("Deleting file " + javaFile);

         try {
            javaFile.delete();
         } catch (Exception var20) {
            throw new BuildException(var20);
         }
      }

   }

   private void validateAttributes() throws BuildException {
      if (this.packageName == null) {
         throw new BuildException("the packageName attribute must be set");
      } else if (this.commandName == null) {
         throw new BuildException("the commandName attribute must be set");
      } else if (this.taskName == null) {
         throw new BuildException("the taskName attribute must be set");
      } else {
         Iterator i = this.options.iterator();

         while(i.hasNext()) {
            Option opt = (Option)i.next();
            opt.validateAttributes();
         }

      }
   }

   private void javacFile(File srcFile, File destDir) throws BuildException {
      Javac javacTask = new Javac();
      javacTask.setProject(this.getProject());
      javacTask.setTaskName(this.getTaskName());

      try {
         javacTask.setSrcdir(new Path(this.getProject(), srcFile.getParentFile().getCanonicalPath()));
      } catch (IOException var5) {
         throw new BuildException(var5);
      }

      javacTask.setIncludes(srcFile.getName());
      javacTask.setDestdir(destDir);
      javacTask.execute();
   }

   private String getMyPackage() {
      String className = this.getClass().getName();
      return className.substring(0, className.lastIndexOf(46));
   }

   private static String quotedValOrNullText(String val) {
      return val == null ? "null" : "\"" + cleanupText(val) + "\"";
   }

   private static String cleanupText(String val) {
      StringBuffer result = new StringBuffer();
      boolean inWS = false;
      val = val.trim();

      for(int i = 0; i < val.length(); ++i) {
         char c = val.charAt(i);
         if (Character.isWhitespace(c)) {
            if (!inWS) {
               result.append(' ');
            }

            inWS = true;
         } else {
            if (c == '"') {
               result.append('\\');
            }

            result.append(c);
            inWS = false;
         }
      }

      return result.toString();
   }
}
