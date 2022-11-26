package weblogic.application.compiler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.compiler.flow.AppMergerFlow;
import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.application.compiler.flow.InitLibrariesFlow;
import weblogic.application.compiler.flow.InitPlanFlow;
import weblogic.application.compiler.flow.PrepareInputFlow;
import weblogic.application.compiler.flow.SetupFlow;
import weblogic.application.utils.LibraryUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.BadOptionException;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class AppMerge extends Tool {
   private final CompilerCtx ctx;
   private final FlowDriver driver;
   private final CompilerFlow[] flows;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public AppMerge(String[] args) {
      super(args);
      this.ctx = new CompilerCtxImpl();
      String workingDirName = ".appmergegen_" + System.currentTimeMillis();
      this.flows = new CompilerFlow[]{new SetupFlow(this.ctx, workingDirName), new PrepareInputFlow(this.ctx), new InitPlanFlow(this.ctx), new InitLibrariesFlow(this.ctx, true), new AppMergerFlow(this.ctx)};
      this.driver = new FlowDriver();
   }

   public static DeploymentViewOptions createDeploymentViewOptions(EditableDeployableObjectFactory objectFactory) {
      return new DeploymentViewOptions(objectFactory);
   }

   public AppMerge(String[] args, DeploymentViewOptions viewOptions) {
      this(args);
      viewOptions.transferOptions(this.ctx);
   }

   public void prepare() {
      this.setRequireExtraArgs(true);
      this.setUsageName("weblogic.appmerge");
      this.opts.setUsageArgs("<ear, jar or war file or directory>");
      this.opts.addOption("output", "file", "Specifies an alternate output archive or directory.  If not set, output will be placed in the source archive or directory.");
      this.opts.addOption("plan", "file", "Specifies an optional deployment plan.");
      this.opts.addOption("plandir", "directory", "Specifies an optional deployment plan directory.");
      this.opts.markPrivate("plandir");
      this.opts.addFlag("verbose", "More output");
      this.opts.addFlag("nopackage", "do not create archived package");
      this.opts.addFlag("readonly", "Don't write out merged app to disk. Used with direct merge() invocation.");
      this.opts.markPrivate("readonly");
      this.opts.markPrivate("nopackage");
      this.opts.addFlag("ignoreMissingLibRefs", "Don't verify that all the libraries referred in the application are specified in command line library options. This may cause failures to load up application in case such libraries were needed");
      this.opts.markPrivate("ignoreMissingLibRefs");
      this.opts.addOption("classpath", "path", "Classpath to use.");
      this.opts.markPrivate("classpath");
      this.opts.addFlag("writeInferredDescriptors", "Write out the descriptors with inferred information including annotations.");
      this.opts.addOption("lightweight", "deployed application name", "Generate Deployment View by using lightweight flow which directly get information from the server which application already deployed on.");
      this.opts.markPrivate("lightweight");
      this.opts.addOption("altappdd", "alternate descriptor", "Uses this alternate descriptor. Supported for both application and single module deployments.");
      this.opts.addFlag("genversion", "Generates the checksum or hash version of the application");
      this.opts.markPrivate("genversion");
      this.opts.addOption("algorithm", "hash algorithm for version", "The algorithm to use for generating the checksum or hash version of the application");
      this.opts.markPrivate("algorithm");
      LibraryUtils.addLibraryUsage(this.opts);
   }

   public void runBody() throws ToolFailureException {
      boolean genVersion = false;

      try {
         genVersion = this.opts.getBooleanOption("genversion");
         if (genVersion) {
            this.ctx.setGenerateVersion(true);
            String algorithm = this.opts.getOption("algorithm");
            if (algorithm != null) {
               this.ctx.setVersionGeneratorAlgorithm(algorithm);
            }
         }

         this.opts.setOption("noexit", "true");
         this.ctx.setOpts(this.opts);
      } catch (BadOptionException var3) {
      }

      try {
         this.driver.nextState(this.flows);
      } catch (ToolFailureException var4) {
         J2EELogger.logAppMergeFailedWithError();
         if ((this.ctx.isVerbose() || debugLogger.isDebugEnabled()) && var4.getCause() != null) {
            var4.getCause().printStackTrace();
         }

         throw var4;
      }

      if (this.opts.getBooleanOption("verbose") && genVersion) {
         J2EELogger.logApplicationHashVersion(this.ctx.getApplicationVersion());
      }

   }

   public String getApplicationVersion() throws Exception {
      this.run();
      this.cleanup();
      return this.ctx.getApplicationVersion();
   }

   public EditableDeployableObject merge() throws Exception {
      this.run();
      return this.ctx.getDeployableApplication();
   }

   public void cleanup() throws ToolFailureException {
      try {
         this.driver.previousState(this.flows);
      } catch (ToolFailureException var2) {
         if ((this.ctx.isVerbose() || debugLogger.isDebugEnabled()) && var2.getCause() != null) {
            var2.getCause().printStackTrace();
         }

         throw var2;
      }
   }

   public static String getApplicationVersion(String algorithm, String application, boolean ignoreMissingLibRefs, boolean verbose, String... libraries) {
      String[] command = createMergeCommand(algorithm, application, ignoreMissingLibRefs, verbose, libraries);
      AppMerge appMerge = new AppMerge(command);

      try {
         return appMerge.getApplicationVersion();
      } catch (RuntimeException var8) {
         throw new RuntimeException("Error occured while getting application version for: " + application + " with libraries " + Arrays.toString(libraries) + "  verify the deployment unit is a valid library type (war, ejb, ear, plain jar)", var8);
      } catch (Exception var9) {
         throw new RuntimeException("Error occured while getting application version for: " + application + " with libraries " + Arrays.toString(libraries) + "  verify the deployment unit is a valid library type (war, ejb, ear, plain jar)", var9);
      }
   }

   private static String[] createMergeCommand(String algorithm, String application, boolean ignoreMissingLibRefs, boolean verbose, String... libraries) {
      List cmd = new LinkedList();
      cmd.add("-noexit");
      cmd.add("-nopackage");
      cmd.add("-readonly");
      cmd.add("-genversion");
      if (algorithm != null) {
         cmd.add("-algorithm");
         cmd.add(algorithm);
      }

      if (ignoreMissingLibRefs) {
         cmd.add("-ignoreMissingLibRefs");
      }

      if (verbose) {
         cmd.add("-verbose");
      }

      StringBuffer ls = new StringBuffer();
      if (libraries != null && libraries.length > 0) {
         boolean firstTime = true;
         String[] var8 = libraries;
         int var9 = libraries.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String library = var8[var10];
            if (firstTime) {
               firstTime = false;
               cmd.add("-library");
               ls.append(library);
            } else {
               ls.append("," + library);
            }
         }

         if (!firstTime) {
            cmd.add(ls.toString());
         }
      }

      cmd.add(application);
      return (String[])((String[])cmd.toArray(new String[cmd.size()]));
   }

   public static void main(String[] args) throws Exception {
      AppMerge merger = new AppMerge(args);
      merger.run();
      merger.cleanup();
   }
}
