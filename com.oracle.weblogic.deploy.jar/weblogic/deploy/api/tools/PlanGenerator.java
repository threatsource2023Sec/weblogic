package weblogic.deploy.api.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.deploy.api.internal.PlanGenTextFormatter;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;

public class PlanGenerator {
   private static final String HELP = "help";
   private static final String DEBUG = "debug";
   private static final String PLAN = "plan";
   private static final String USEPLAN = "useplan";
   private static final String INSTALLROOT = "root";
   private static final String VARIABLES = "variables";
   private static final String DEPENDENCIES = "dependencies";
   private static final String DECLARATIONS = "declarations";
   private static final String CONFIGURABLES = "configurables";
   private static final String DYNAMIC = "dynamics";
   private static final String ALL = "all";
   private static final String NONE = "none";
   private static final String LIBRARY = "library";
   private static final String LIBRARYDIR = "librarydir";
   private static final String STANDARD = "standard";
   private static final String ANY = "any";
   private static final String MODULE = "module";
   private static PlanGenTextFormatter cat = PlanGenTextFormatter.getInstance();
   private static boolean debug = false;
   private SessionHelper helper;
   private WebLogicDeploymentManager dm;
   private File app;
   private File plan = null;
   private WebLogicDeploymentConfiguration dc;
   private int exportArg = 0;
   private boolean globalVars = false;
   private static int EXPORT_NONE = -1;
   private Getopt2 opts;
   private static final String DEFAULT_PLAN = "plan.xml";
   private static final String NOEXIT = "noexit";
   private Set libraries = null;
   private boolean standard = false;
   private String module = null;

   private PlanGenerator(Getopt2 opts) {
      this.opts = opts;
   }

   private void run() throws Exception {
      this.dm = SessionHelper.getDisconnectedDeploymentManager();
      this.helper = SessionHelper.getInstance(this.dm);
      this.setVariables();
      this.setExport();
      this.setStandard();
      this.setModule();
      this.setRoot();
      this.setApp();
      this.setUsePlan();
      this.setPlan();
      this.setLibraries();
      this.setLibrarDirs();
      this.registerLibraries();
      this.showInputs();
      this.helper.initializeConfiguration();
      this.dc = this.helper.getConfiguration();
      this.dc.getPlan().setGlobalVariables(this.globalVars);
      this.validateOptions();
      if (this.exportArg != EXPORT_NONE) {
         System.out.println(cat.exporting());
         this.dc.export(this.exportArg, this.standard, this.module);
      }

   }

   private void validateOptions() {
      WebLogicDeployableObject dObject = this.helper.getDeployableObject();
      ModuleType moduleType = dObject.getType();
      boolean isEar = ModuleType.EAR.getValue() == moduleType.getValue();
      if (this.module != null && !isEar) {
         throw new IllegalArgumentException(cat.notEar("module"));
      }
   }

   private void registerLibraries() {
      if (this.libraries != null) {
         Iterator iterator = this.libraries.iterator();

         while(iterator.hasNext()) {
            LibraryData data = (LibraryData)iterator.next();
            this.helper.registerLibrary(data.getLocation(), data.getName(), data.getSpecificationVersion() == null ? null : data.getSpecificationVersion().toString(), data.getImplementationVersion());
         }

      }
   }

   private void setLibrarDirs() {
      if (this.opts.hasOption("librarydir")) {
         File libdir = new File(this.opts.getOption("librarydir"));

         try {
            LibraryLoggingUtils.checkLibdirIsValid(libdir);
         } catch (LoggableLibraryProcessingException var4) {
            throw new IllegalArgumentException(var4);
         }

         File[] files = libdir.listFiles();

         for(int i = 0; i < files.length; ++i) {
            this.addLibrary(LibraryData.newEmptyInstance(files[i]));
         }

      }
   }

   private void setLibraries() {
      if (this.opts.hasOption("library")) {
         String[] arg = StringUtils.splitCompletely(this.opts.getOption("library"), ",");

         for(int i = 0; i < arg.length; ++i) {
            String filePart = arg[i];
            String rest = arg[i];
            int ci = arg[i].indexOf("@");
            if (ci > -1) {
               filePart = arg[i].substring(0, ci);
               rest = arg[i].substring(ci);
            }

            File f = new File(filePart);

            try {
               this.addLibrary(this.parseLibraryArg(f, rest));
            } catch (LoggableLibraryProcessingException var8) {
               throw new IllegalArgumentException(var8);
            }
         }

      }
   }

   private void addLibrary(LibraryData data) {
      if (this.libraries == null) {
         this.libraries = new HashSet();
      }

      this.libraries.add(data);
   }

   private LibraryData parseLibraryArg(File f, String arg) throws LoggableLibraryProcessingException {
      LibraryLoggingUtils.checkLibraryExists(f);
      if (arg.indexOf("@") == -1) {
         return LibraryData.newEmptyInstance(f);
      } else {
         String[] token = StringUtils.splitCompletely(arg, "@");
         String name = null;
         String spec = null;
         String impl = null;

         for(int i = 0; i < token.length; ++i) {
            if (token[i].indexOf("=") != -1) {
               String[] nameVal = StringUtils.splitCompletely(token[i], "=");
               if (nameVal[0].equalsIgnoreCase("name")) {
                  name = nameVal[1];
               }

               if (nameVal[0].equalsIgnoreCase("libspecver")) {
                  spec = nameVal[1];
               }

               if (nameVal[0].equalsIgnoreCase("libimplver")) {
                  impl = nameVal[1];
               }
            }
         }

         return LibraryLoggingUtils.initLibraryData(name, spec, impl, f);
      }
   }

   private void setPlan() {
      if (this.opts.hasOption("plan")) {
         this.plan = new File(this.opts.getOption("plan"));
      } else {
         this.plan = this.helper.getPlan();
      }

      if (this.plan == null) {
         this.setImplicitPlan();
      }

      if (this.plan == null) {
         this.plan = new File("plan.xml");
      }

      if (this.plan.exists() && !this.opts.hasOption("useplan")) {
         this.helper.setPlan(this.plan);
      }

   }

   private void setImplicitPlan() {
      if (this.helper.getApplicationRoot() != null) {
         File pd = new File(this.helper.getApplicationRoot(), "plan");
         if (!pd.exists()) {
            pd.mkdirs();
         }

         if (pd.isDirectory()) {
            this.plan = new File(pd, "plan.xml");
         }
      }

   }

   private void setUsePlan() {
      if (this.opts.hasOption("useplan")) {
         if (debug) {
            Debug.say(" UsePlan set. So, setting plan on helper to : " + this.opts.getOption("useplan"));
         }

         this.helper.setPlan(new File(this.opts.getOption("useplan")));
         if (debug) {
            Debug.say(" Plan on helper set to : " + this.helper.getPlan());
         }
      }

   }

   private void setApp() {
      if (this.opts.args().length > 0) {
         this.helper.setApplication(new File(this.opts.args()[0]));
      }

      this.app = this.helper.getApplication();
      if (this.app == null) {
         throw new IllegalArgumentException(cat.noApp());
      }
   }

   private void setRoot() {
      if (this.opts.hasOption("root")) {
         this.helper.setApplicationRoot(new File(this.opts.getOption("root")));
      }

   }

   private void showInputs() {
      if (this.helper.getPlan() != null) {
         System.out.println(cat.genningWithPlan(this.app.getPath(), this.helper.getPlan().getPath()));
      } else {
         System.out.println(cat.genning(this.app.getPath()));
      }

      System.out.println(cat.exportOptions(this.exportAsString(this.exportArg)));
      if (this.libraries != null) {
         System.out.println(cat.merging());
         Iterator iterator = this.libraries.iterator();

         while(iterator.hasNext()) {
            LibraryData data = (LibraryData)iterator.next();
            System.out.println(cat.libLocation(data.getLocation().getPath()));
            System.out.println(cat.libName(data.getName()));
            System.out.println(cat.libSpec(data.getSpecificationVersion() == null ? null : data.getSpecificationVersion().toString()));
            System.out.println(cat.libImpl(data.getImplementationVersion()));
            System.out.println();
         }
      }

   }

   private String exportAsString(int arg) {
      switch (arg) {
         case 0:
            return "dependencies";
         case 1:
            return "declarations";
         case 2:
            return "configurables";
         case 3:
            return "all";
         case 4:
            return "dynamics";
         case 5:
            return "any";
         default:
            return "none";
      }
   }

   private void setExport() {
      if (this.opts.hasOption("dependencies")) {
         this.exportArg = 0;
      } else if (this.opts.hasOption("declarations")) {
         this.exportArg = 1;
      } else if (this.opts.hasOption("configurables")) {
         this.exportArg = 2;
      } else if (this.opts.hasOption("all")) {
         this.exportArg = 3;
      } else if (this.opts.hasOption("none")) {
         this.exportArg = EXPORT_NONE;
      } else if (this.opts.hasOption("dynamics")) {
         this.exportArg = 4;
      } else if (this.opts.hasOption("any")) {
         this.exportArg = 5;
      }

   }

   private void setVariables() {
      if (this.opts.hasOption("variables")) {
         String v = this.opts.getOption("variables");
         if ("global".equals(v)) {
            this.globalVars = true;
         } else {
            if (!"unique".equals(v)) {
               throw new IllegalArgumentException(cat.badVariables(v));
            }

            this.globalVars = false;
         }
      }

   }

   private void setStandard() {
      if (this.opts.hasOption("standard")) {
         this.standard = true;
      }

   }

   private void setModule() {
      if (this.opts.hasOption("module")) {
         this.module = this.opts.getOption("module");
      }

   }

   private void save() throws IOException, ConfigurationException {
      System.out.println(cat.saving(this.plan.getCanonicalPath()));
      this.helper.setPlan(this.plan);
      this.helper.savePlan();
   }

   private void close() {
      if (this.helper != null) {
         this.helper.close();
      }

   }

   public static void main(String[] args) throws Exception {
      Getopt2 opts = new Getopt2();
      PlanGenerator p = null;
      boolean error = false;

      try {
         opts.addFlag("debug", cat.debug());
         opts.addFlag("noexit", cat.noexit());
         opts.addOption("plan", "myplan.xml", cat.plan());
         opts.addOption("useplan", "myplan.xml", cat.useplan());
         opts.addOption("root", "/weblogic/install/myapp", "application install root");
         opts.addFlag("dependencies", cat.dependencies());
         opts.addFlag("declarations", cat.declarations());
         opts.addFlag("configurables", cat.configurables());
         opts.addFlag("dynamics", cat.dynamics());
         opts.addFlag("all", cat.all());
         opts.addFlag("any", cat.any());
         opts.addFlag("standard", cat.standard());
         opts.addOption("module", (String)null, cat.module());
         opts.addFlag("none", cat.none());
         opts.addOption("variables", "global", cat.variables());
         opts.addOption("library", "/mylibs/lib.ear@name=mylib,/mylibs/lib2.ear@name=otherlib@libspecver=1@libimplver=2", cat.libraries());
         opts.addOption("librarydir", "/mylibs", cat.libraryDir());
         opts.setUsageArgs(cat.application());
         opts.grok(args);
         if (opts.hasOption("help") || args.length == 0) {
            usage(opts);
            return;
         }

         if (opts.hasOption("debug")) {
            setDebug();
         }

         p = new PlanGenerator(opts);
         p.run();
         p.save();
      } catch (Throwable var10) {
         if (opts.hasOption("noexit")) {
            if (var10 instanceof Exception) {
               throw (Exception)var10;
            }

            throw new Exception(var10);
         }

         if (debug) {
            var10.printStackTrace();
         } else {
            String m = var10.getMessage();
            if (m != null) {
               System.out.println(var10.getMessage());
            } else {
               var10.printStackTrace();
            }
         }

         error = true;
      } finally {
         if (p != null) {
            p.close();
         }

         if (error && !opts.hasOption("noexit")) {
            System.exit(1);
         }

      }

   }

   private static void usage(Getopt2 opts) {
      opts.usageError("weblogic.PlanGenerator");
      System.out.println(cat.usage());
   }

   private static void setDebug() {
      debug = true;
      System.setProperty("weblogic.deployer.debug", "all");
   }
}
