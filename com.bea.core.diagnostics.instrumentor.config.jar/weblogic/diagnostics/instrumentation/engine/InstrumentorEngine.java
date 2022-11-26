package weblogic.diagnostics.instrumentation.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationStatistics;
import weblogic.diagnostics.instrumentation.engine.base.InstrumentorEngineBase;
import weblogic.utils.PropertyHelper;

public class InstrumentorEngine extends InstrumentorEngineBase {
   private InstrumentationEngineConfiguration engineConf;

   public InstrumentorEngine(MonitorSpecification[] monitorSpecifications) {
      this("InstrumentorEngine", monitorSpecifications);
   }

   public InstrumentorEngine(String name, MonitorSpecification[] monitorSpecifications) {
      this(name, monitorSpecifications, false);
   }

   public InstrumentorEngine(String name, MonitorSpecification[] monitorSpecifications, boolean allowHotswap) {
      this.monitorSpecifications = monitorSpecifications;
      this.allowHotswap = allowHotswap;
      this.engineName = name;
      if (allowHotswap && PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.InstrumentorEngine.HotSwapWithNoAux")) {
         this.hotSwapWithNoAux = true;
      } else {
         this.hotSwapWithNoAux = false;
      }

      if (PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.InstrumentorEngine.Compute16")) {
         this.v16ComputeFrames = true;
      } else {
         this.v16ComputeFrames = false;
      }

      this.instrumentationStatistics = new InstrumentationStatistics();
      this.engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
      this.supportClassName = this.engineConf.getInstrumentationSupportClassName();
      if (this.supportClassName == null) {
         this.supportClassName = "weblogic/diagnostics/instrumentation/InstrumentationSupport";
      }

      this.supportClassName = this.supportClassName.replace('.', '/');
   }

   public InstrumentorEngine(String name, MonitorSpecification[] monitorSpecifications, boolean allowHotswap, String supportClassName) {
      this(name, monitorSpecifications, allowHotswap);
      if (supportClassName != null) {
         supportClassName = supportClassName.replace('.', '/');
         this.supportClassName = supportClassName;
      }

   }

   public InstrumentorEngine(MonitorSpecification[] monitorSpecifications, Properties settings) {
      this("InstrumentorEngine", monitorSpecifications, settings);
   }

   public InstrumentorEngine(String name, MonitorSpecification[] monitorSpecifications, Properties settings) {
      super(name, monitorSpecifications, settings);
      if (PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.InstrumentorEngine.Compute16")) {
         this.v16ComputeFrames = true;
      } else {
         this.v16ComputeFrames = false;
      }

      this.engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
      this.supportClassName = this.engineConf.getInstrumentationSupportClassName();
      if (this.supportClassName == null) {
         this.supportClassName = "weblogic/diagnostics/instrumentation/InstrumentationSupport";
      }

      this.supportClassName = this.supportClassName.replace('.', '/');
   }

   private static void showUsage() {
      System.out.println("Usage: java weblogic.diagnostics.instrumentation.engine.InstrumentorEngine <args> input_files_and_directories");
      System.out.println("  arguments:");
      System.out.println("  [-include comma-separated-patterns]");
      System.out.println("  [-exclude comma-separated-patterns]");
      System.out.println("  [-all-server-monitors]");
      System.out.println("  [-all-application-monitors]");
      System.out.println("  -monitors comma-separated-monitor-list");
      System.out.println("  [-expectedMonitors comma-separated-monitor-list]");
      System.out.println("  [-outdir output_directory]");
      System.out.println("  [-summaryFile summary_file]");
      System.out.println("  [-failOnAborts]");
      System.out.println("  [-quiet]");
   }

   public static void main(String[] args) {
      String outputDirName = null;
      List monitorList = new ArrayList();
      List inputFileList = new ArrayList();
      String[] includes = null;
      String[] excludes = null;
      String summaryFileName = null;
      Set expectedMonitorSet = null;
      boolean failOnAborts = false;
      boolean quiet = false;
      setOffline(true);
      InstrumentationEngineConfiguration engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();

      for(int i = 0; i < args.length; ++i) {
         String arg = args[i];
         String val;
         String[] list;
         int j;
         if (arg.equals("-monitors")) {
            ++i;
            val = args[i];
            list = val.split(",");

            for(j = 0; j < list.length; ++j) {
               MonitorSpecification mSpec = engineConf.getMonitorSpecification(list[j]);
               if (mSpec != null) {
                  monitorList.add(mSpec);
               } else {
                  DiagnosticsLogger.logMonitorNotFound(list[j]);
               }
            }
         } else {
            Iterator it;
            MonitorSpecification mSpec;
            if (arg.equals("-all-server-monitors")) {
               it = engineConf.getAllMonitorSpecifications();

               while(it.hasNext()) {
                  mSpec = (MonitorSpecification)it.next();
                  if (mSpec.isServerScoped()) {
                     monitorList.add(mSpec);
                  }
               }
            } else if (arg.equals("-all-application-monitors")) {
               it = engineConf.getAllMonitorSpecifications();

               while(it.hasNext()) {
                  mSpec = (MonitorSpecification)it.next();
                  if (mSpec.isApplicationScoped()) {
                     monitorList.add(mSpec);
                  }
               }
            } else if (arg.equals("-all-monitors")) {
               it = engineConf.getAllMonitorSpecifications();

               while(it.hasNext()) {
                  mSpec = (MonitorSpecification)it.next();
                  monitorList.add(mSpec);
               }
            } else if (arg.equals("-include")) {
               ++i;
               includes = args[i].split(",");
            } else if (arg.equals("-exclude")) {
               ++i;
               excludes = args[i].split(",");
            } else if (arg.equals("-outdir")) {
               ++i;
               outputDirName = args[i];
            } else if (arg.equals("-summaryFile")) {
               ++i;
               summaryFileName = args[i];
            } else if (arg.equals("-expectedMonitors")) {
               ++i;
               val = args[i];
               if (val.length() > 0) {
                  list = val.split(",");
                  expectedMonitorSet = new HashSet();

                  for(j = 0; j < list.length; ++j) {
                     expectedMonitorSet.add(list[j]);
                  }
               }
            } else if (arg.equals("-failOnAborts")) {
               failOnAborts = true;
            } else if (arg.equals("-quiet")) {
               quiet = true;
            } else {
               inputFileList.add(arg);
            }
         }
      }

      if (monitorList.size() != 0 && outputDirName != null && inputFileList.size() != 0) {
         MonitorSpecification[] mSpecs = new MonitorSpecification[monitorList.size()];
         Properties settings = null;
         if (summaryFileName != null) {
            settings = new Properties();
            settings.setProperty("SummaryFile", summaryFileName);
         }

         InstrumentorEngine engine = new InstrumentorEngine("InstrumentorEngine", (MonitorSpecification[])((MonitorSpecification[])monitorList.toArray(mSpecs)), settings);
         if (expectedMonitorSet != null && !expectedMonitorSet.isEmpty()) {
            engine.setTrackMatchedMonitors(true);
         }

         try {
            if (includes != null) {
               engine.setIncludePatterns(includes);
            }
         } catch (PatternSyntaxException var23) {
            DiagnosticsLogger.logInvalidInclusionPatternError("InstrumentorEngine");
         }

         try {
            if (excludes != null) {
               engine.setExcludePatterns(excludes);
            }
         } catch (PatternSyntaxException var22) {
            DiagnosticsLogger.logInvalidExclusionPatternError("InstrumentorEngine");
         }

         long t0 = System.currentTimeMillis();
         String missingExpectedMonitors;
         if (engine.isEnabled()) {
            ClassLoader ccl = InstrumentorEngine.class.getClassLoader();
            Iterator it = inputFileList.iterator();

            while(it.hasNext()) {
               missingExpectedMonitors = (String)it.next();
               File outputDir = new File(outputDirName);
               if (!outputDir.exists() && !outputDir.mkdirs()) {
                  throw new RuntimeException("Unable to create output directory: " + outputDir);
               }

               engine.doInstrument(ccl, new File(missingExpectedMonitors), outputDir);
            }
         }

         long t1 = System.currentTimeMillis();
         missingExpectedMonitors = null;
         if (expectedMonitorSet != null) {
            Set matchedMonitors = engine.getMatchedMonitorSet();
            Iterator var20 = expectedMonitorSet.iterator();

            label131:
            while(true) {
               String expected;
               do {
                  if (!var20.hasNext()) {
                     break label131;
                  }

                  expected = (String)var20.next();
               } while(matchedMonitors != null && matchedMonitors.contains(expected));

               if (missingExpectedMonitors == null) {
                  missingExpectedMonitors = expected;
               } else {
                  missingExpectedMonitors = missingExpectedMonitors + ", " + expected;
               }
            }
         }

         engine.closeSummary();
         InstrumentationStatistics stats = engine.getInstrumentationStatistics();
         if (!quiet) {
            System.out.println("Runtime info " + stats);
            System.out.println("Total processing time: " + (t1 - t0));
         }

         if (missingExpectedMonitors != null) {
            throw new RuntimeException(DiagnosticsLogger.getExpectedMonitorsNotInstrumented(missingExpectedMonitors));
         }

         if (failOnAborts && stats.getClassweaveAbortCount() > 0) {
            throw new RuntimeException("Instrumentor had " + stats.getClassweaveAbortCount() + " weaving aborts, treating as a failure because -failOnAborts specified");
         }
      } else {
         showUsage();
      }

   }
}
