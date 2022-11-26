package weblogic.diagnostics.instrumentation.tool;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import weblogic.diagnostics.instrumentation.engine.InstrumentationEngineConfiguration;
import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;
import weblogic.diagnostics.instrumentation.engine.MonitorSpecification;

public class InstrumentorTask extends Task {
   private File src;
   private File dstDir;
   private final Vector filesets = new Vector();
   private boolean failOnError = true;
   private String monitors;
   private String classPath;
   private String summaryFileName = null;
   private Set expectedMonitorSet = null;
   private int errCount;
   private int warnCount;
   private InstrumentationEngineConfiguration engineConf;

   public InstrumentorTask() {
      InstrumentorEngine.setOffline(true);
      this.engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
   }

   public void setSrc(File src) {
      this.src = src;
   }

   public void setDestdir(File dstDir) {
      this.dstDir = dstDir;
   }

   public void setMonitors(String monitors) {
      this.monitors = monitors;
   }

   public void setClasspath(String classPath) {
      this.classPath = classPath;
   }

   public void setSummaryFile(String summaryFileName) {
      this.summaryFileName = summaryFileName;
   }

   public void setExpectedMonitors(String expectedMonitors) {
      if (expectedMonitors != null && expectedMonitors.length() != 0) {
         String[] list = expectedMonitors.split(",");
         this.expectedMonitorSet = new HashSet();

         for(int j = 0; j < list.length; ++j) {
            this.expectedMonitorSet.add(list[j]);
         }

      } else {
         this.expectedMonitorSet = null;
      }
   }

   public void setFailonerror(boolean failOnError) {
      this.failOnError = failOnError;
   }

   public void addFileset(FileSet set) {
      this.filesets.addElement(set);
   }

   public void execute() throws BuildException {
      long t0 = System.currentTimeMillis();
      if (this.src == null && this.filesets.size() == 0) {
         throw new BuildException("There must be a src attribute and/or a fileset child element", this.location);
      } else if (this.dstDir == null) {
         throw new BuildException("The destdir attribute must be set", this.location);
      } else if (!this.dstDir.exists() && !this.dstDir.mkdirs()) {
         throw new BuildException("Unable to create directory: " + this.dstDir);
      } else {
         this.resetCounts();
         List monitorList = this.getMonitorList();
         MonitorSpecification[] mSpecs = new MonitorSpecification[monitorList.size()];
         Properties settings = null;
         if (this.summaryFileName != null) {
            settings = new Properties();
            settings.setProperty("SummaryFile", this.summaryFileName);
         }

         InstrumentorEngine engine = new InstrumentorEngine((MonitorSpecification[])((MonitorSpecification[])monitorList.toArray(mSpecs)), settings);
         ClassLoader ccl = this.getClassLoader();
         if (!this.expectedMonitorSet.isEmpty()) {
            engine.setTrackMatchedMonitors(true);
         }

         if (this.src != null) {
            this.errCount = this.instrumentFile(engine, ccl, this.src);
         }

         for(int i = 0; i < this.filesets.size(); ++i) {
            FileSet fs = (FileSet)this.filesets.elementAt(i);
            DirectoryScanner ds = fs.getDirectoryScanner(this.project);
            File dir = fs.getDir(this.project);
            String[] sources = ds.getIncludedFiles();

            for(int j = 0; j < sources.length; ++j) {
               this.errCount += this.instrumentFile(engine, ccl, new File(dir, sources[j]));
            }
         }

         long t1 = System.currentTimeMillis();
         System.out.println("\n" + engine.getInstrumentationStatistics());
         System.out.println("Total processing time: " + (t1 - t0) + " ms");
         String missingExpectedMonitors = null;
         if (this.expectedMonitorSet != null) {
            Set matchedMonitors = engine.getMatchedMonitorSet();
            Iterator var17 = this.expectedMonitorSet.iterator();

            label72:
            while(true) {
               String expected;
               do {
                  if (!var17.hasNext()) {
                     break label72;
                  }

                  expected = (String)var17.next();
               } while(matchedMonitors != null && matchedMonitors.contains(expected));

               if (missingExpectedMonitors == null) {
                  missingExpectedMonitors = expected;
               } else {
                  missingExpectedMonitors = missingExpectedMonitors + ", " + expected;
               }
            }
         }

         engine.closeSummary();
         if (this.errCount > 0 && this.failOnError) {
            throw new BuildException("Instrumentation failed, " + this.errCount + " errors", this.location);
         } else {
            if (missingExpectedMonitors != null) {
               if (this.failOnError) {
                  throw new BuildException("Instrumentation failed, Expected monitors were not instrumented : " + missingExpectedMonitors, this.location);
               }

               System.out.println("Expected monitors were not instrumented : " + missingExpectedMonitors);
            }

         }
      }
   }

   private int instrumentFile(InstrumentorEngine engine, ClassLoader ccl, File file) {
      int errCount = 0;
      int size;
      if (file.isDirectory()) {
         String[] list = file.list();
         size = list != null ? list.length : 0;

         for(int i = 0; i < size; ++i) {
            if (!list[i].equals(".") && !list[i].equals("..")) {
               File f = new File(file, list[i]);
               errCount += this.instrumentFile(engine, ccl, f);
            }
         }
      } else {
         String name = file.getName();
         size = name.lastIndexOf(46);
         if (size > 0) {
            String ext = name.substring(size + 1).toLowerCase();
            if (ext.equals("class") || ext.equals("jar") || ext.equals("zip")) {
               System.out.println("Instrumenting: " + file);
               engine.doInstrument(ccl, file, this.dstDir);
            }
         }
      }

      return errCount;
   }

   private ClassLoader getClassLoader() {
      ClassLoader ccl = InstrumentorTask.class.getClassLoader();
      if (this.classPath != null && this.classPath.trim().length() > 0) {
         String[] entries = this.classPath.split(";|:");
         ArrayList list = new ArrayList();
         int size = entries != null ? entries.length : 0;

         for(int i = 0; i < size; ++i) {
            File f = new File(entries[i].trim());
            if (f.exists()) {
               try {
                  list.add(f.toURL());
               } catch (Exception var8) {
                  this.warn("Ignoring malformed entry in classpath: " + f);
               }
            }
         }

         if (list.size() > 0) {
            URL[] urls = new URL[list.size()];
            urls = (URL[])((URL[])list.toArray(urls));
            ccl = new URLClassLoader(urls, (ClassLoader)ccl);
         }
      }

      return (ClassLoader)ccl;
   }

   private List getMonitorList() throws BuildException {
      ArrayList monitorList = new ArrayList();
      this.monitors = this.monitors != null ? this.monitors.trim() : "";
      if (this.monitors.length() == 0) {
         throw new BuildException("The value of monitors attribute must be `all' or a comma separated list of monitors", this.location);
      } else {
         if (this.monitors.equals("all")) {
            Iterator it = this.engineConf.getAllMonitorSpecifications();

            while(it.hasNext()) {
               MonitorSpecification mSpec = (MonitorSpecification)it.next();
               if (mSpec.isServerScoped()) {
                  monitorList.add(mSpec);
               }
            }
         } else {
            String[] list = this.monitors.split(",");

            for(int j = 0; j < list.length; ++j) {
               String monName = list[j].trim();
               MonitorSpecification mSpec = this.engineConf.getMonitorSpecification(monName);
               if (mSpec != null) {
                  monitorList.add(mSpec);
               } else {
                  this.error("Unknown monitor " + monName);
               }
            }
         }

         if (this.errCount > 0) {
            throw new BuildException("One or more unknown monitors specified.");
         } else if (monitorList.size() == 0) {
            throw new BuildException("No monitors specified", this.location);
         } else {
            return monitorList;
         }
      }
   }

   private void resetCounts() {
      this.errCount = 0;
      this.warnCount = 0;
   }

   private void error(String msg) {
      ++this.errCount;
      System.out.println("Error: " + msg);
   }

   private void warn(String msg) {
      ++this.warnCount;
      System.out.println("Warning: " + msg);
   }

   private void info(String msg) {
      System.out.println("Info: " + msg);
   }
}
