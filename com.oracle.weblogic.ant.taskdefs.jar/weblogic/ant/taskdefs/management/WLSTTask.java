package weblogic.ant.taskdefs.management;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.util.FileUtils;
import weblogic.utils.StringUtils;

public class WLSTTask extends Java {
   private static final int PYTHON_TAB_SIZE = 8;
   private static final String WLST_INTERPRETER_CLASS = "weblogic.management.scripting.WLSTInterpreterInvoker";
   private String propertiesFile = null;
   private String fileName;
   private String arguments;
   private boolean debug = false;
   private boolean failOnError = true;
   private boolean executeScriptBeforeFile = true;
   private WLSTScript script = null;
   private Path classpath;
   private String errorProperty;
   private String scriptTempFile;

   public WLSTTask() {
      this.classpath = Path.systemClasspath;
   }

   public void init() {
      this.setProject(this.getProject());
      this.setClassname("weblogic.management.scripting.WLSTInterpreterInvoker");
      this.setFork(true);
      this.setClasspath(this.classpath);
      this.setFailonerror(this.failOnError);
      this.setInputString("");
   }

   /** @deprecated */
   @Deprecated
   public void setProperties(String props) {
      this.propertiesFile = props;
   }

   public void setPropertiesFile(String props) {
      this.propertiesFile = props;
   }

   public void setFileName(String fn) {
      this.fileName = fn;
   }

   public void setArguments(String args) {
      this.arguments = args;
   }

   public void setDebug(boolean dbg) {
      this.debug = dbg;
   }

   public void setFailOnError(boolean dbg) {
      this.failOnError = dbg;
   }

   public void setExecuteScriptBeforeFile(boolean dbg) {
      this.executeScriptBeforeFile = dbg;
   }

   public void addScript(WLSTScript lines) {
      this.script = lines;
   }

   public void addClasspath(Path cp) {
      if (this.classpath == null) {
         this.classpath = new Path(this.getProject());
      }

      this.classpath.append(cp);
   }

   public void setErrorProperty(String ep) {
      this.errorProperty = ep;
      super.setErrorProperty(ep);
   }

   public void execute() throws BuildException {
      this.setupCommand();
      this.printDebug("Executing the command line : \n" + this.getCommandLine().toString());

      try {
         super.execute();
      } finally {
         try {
            if (!this.debug && this.scriptTempFile != null) {
               (new File(this.scriptTempFile)).delete();
            }
         } catch (Exception var7) {
         }

      }

   }

   private void setupCommand() throws BuildException {
      this.setProperty("debug", this.debug);
      this.setProperty("failOnError", this.failOnError);
      this.setProperty("executeScriptBeforeFile", this.executeScriptBeforeFile);
      String error;
      if (this.propertiesFile != null && !(new File(this.propertiesFile)).exists()) {
         error = "Error: The properties file " + this.propertiesFile + " does not exits.";
         this.printError(error);
      }

      this.setProperty("propertiesFile", this.propertiesFile);
      if (this.script != null) {
         this.scriptTempFile = this.handleEmbededScript(this.script);
      }

      if (this.scriptTempFile == null) {
         if (this.fileName == null) {
            error = "Error: The fileName attribute is required if no nested script is used.";
            this.printError(error);
         }

         if (this.fileName != null && !(new File(this.fileName)).exists()) {
            error = "Error: File specified " + this.fileName + " does not exist.";
            this.printError(error);
         }
      }

      this.setProperty("fileName", this.fileName);
      this.setProperty("scriptTempFile", this.scriptTempFile);
      this.setArgument(this.arguments);
   }

   private void setArgument(String arg) {
      if (arg != null) {
         this.getCommandLine().createArgument().setValue(arg);
      }

   }

   private void setProperty(String property, Object value) {
      if (value != null) {
         this.createJvmarg().setValue("-D" + property + "=" + value.toString());
      }

   }

   private String handleEmbededScript(WLSTScript script) {
      PrintWriter out = null;
      String outPath = null;

      String var5;
      try {
         File outFile = this.getTempFile("wlsttempfile", ".py");
         out = new PrintWriter(new FileOutputStream(outFile));
         out = this.readEmbeddedScript(out, script.getScript());
         out.flush();
         outPath = outFile.getCanonicalPath();
         var5 = outPath;
      } catch (IOException var14) {
         throw new BuildException(var14);
      } finally {
         try {
            if (out != null) {
               out.close();
            }
         } catch (Exception var13) {
         }

      }

      return var5;
   }

   private File getTempFile(String prefix, String suffix) {
      FileUtils fileUtils = FileUtils.getFileUtils();
      return fileUtils.createTempFile(prefix, suffix, (File)null);
   }

   private PrintWriter readEmbeddedScript(PrintWriter out, String theScript) throws IOException {
      LineNumberReader lineReader = null;

      try {
         String firstLine = null;
         lineReader = new LineNumberReader(new StringReader(theScript));
         String line = lineReader.readLine();

         for(int trimLength = 0; line != null; line = lineReader.readLine()) {
            if (!StringUtils.isWhitespace(line)) {
               if (firstLine == null) {
                  firstLine = line;
                  trimLength = this.calculateTrimLength(line);
                  this.printDebug("All lines will be trimmed by " + trimLength);
               }

               line = this.trimLeadingWhitespace(line, trimLength);
               out.println(line);
            }
         }
      } finally {
         try {
            if (lineReader != null) {
               lineReader.close();
            }
         } catch (Exception var12) {
         }

      }

      return out;
   }

   private int calculateTrimLength(String line) {
      char[] value = line.toCharArray();
      int whitespaceFound = 0;

      for(int i = 0; i < value.length; ++i) {
         if (value[i] == ' ') {
            ++whitespaceFound;
         } else {
            if (value[i] != '\t') {
               break;
            }

            whitespaceFound += 8 - whitespaceFound % 8;
         }
      }

      return whitespaceFound;
   }

   private String trimLeadingWhitespace(String line, int firstLineWhitespaceLength) {
      char[] value = line.toCharArray();
      int trimLength = 0;
      int whitespaceFound = 0;

      for(int i = 0; whitespaceFound < firstLineWhitespaceLength; ++i) {
         if (value[i] == ' ') {
            ++trimLength;
            ++whitespaceFound;
         } else {
            if (value[i] != '\t') {
               String error = "Syntax error. Script indentation must be by tabs or multiples of 8 spaces.";
               System.out.println(error);
               throw new IllegalStateException(error);
            }

            ++trimLength;
            whitespaceFound += 8 - whitespaceFound % 8;
         }
      }

      this.printDebug("Line: " + line + ". Final trim length: " + trimLength + ", String length: " + line.length());
      return line.substring(trimLength, line.length());
   }

   private void printError(String error) {
      System.out.println(error);
      if (this.failOnError) {
         this.getProject().setProperty(this.errorProperty, error);
         throw new BuildException(error);
      }
   }

   private void printDebug(String s) {
      if (this.debug) {
         System.out.println("<WLSTTask> " + s);
      }

   }
}
