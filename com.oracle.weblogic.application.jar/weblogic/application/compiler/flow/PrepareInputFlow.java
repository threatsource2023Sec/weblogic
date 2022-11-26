package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.io.AA;
import weblogic.application.utils.CachedApplicationArchiveFactory;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.Getopt2;
import weblogic.utils.application.WarDetector;
import weblogic.utils.compiler.ToolFailureException;

public final class PrepareInputFlow extends CompilerFlow {
   private File sourceFile;
   private boolean verbose;
   private Getopt2 opts;

   public PrepareInputFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.opts = this.ctx.getOpts();
      this.prepareInput(this.ctx);
   }

   public void cleanup() {
      this.ctx.getAppClassLoader().close();
   }

   private void prepareInput(CompilerCtx ctx) throws ToolFailureException {
      if (this.opts.args().length > 1) {
         this.opts.usageError("weblogic.appc");
         Loggable l = J2EELogger.logTooManyArgsForAppcLoggable();
         throw new ToolFailureException(l.getMessage());
      } else {
         this.verbose = this.opts.hasOption("verbose");
         ctx.setVerbose(this.verbose);
         if (this.opts.hasOption("readonly")) {
            ctx.setReadOnlyInvocation();
         }

         String sourceFileName = this.getSourceFileName(this.opts);

         try {
            this.sourceFile = (new File(sourceFileName)).getCanonicalFile();
         } catch (IOException var7) {
            throw new ToolFailureException("Error processing source " + sourceFileName, var7);
         }

         ctx.setSourceFile(this.sourceFile);
         if (this.opts.hasOption("lightweight")) {
            ctx.setLightWeightAppName(this.opts.getOption("lightweight"));
         }

         ctx.init();
         if (!this.sourceFile.exists()) {
            Loggable l = J2EELogger.logAppcSourceArgDoesNotExistLoggable(sourceFileName);
            throw new ToolFailureException(l.getMessage());
         } else if (this.opts.hasOption("jsps") && this.opts.getBooleanOption("compileAllTagFiles")) {
            throw new ToolFailureException("jsps & compileAllTagFiles flags cannot be used together with Appc");
         } else if (this.opts.hasOption("jsps") && this.opts.getOption("jsps").indexOf(42) >= 0 && !"*".equals(this.opts.getOption("jsps")) && !"\"*\"".equals(this.opts.getOption("jsps")) && !"'*'".equals(this.opts.getOption("jsps"))) {
            throw new ToolFailureException("Unsupported wildcard pattern was passed to -jsps");
         } else if (this.opts.hasOption("compiler") && !"javac".equals(this.opts.getOption("compiler")) && !"jdt".equals(this.opts.getOption("compiler")) && !"none".equals(this.opts.getOption("compiler"))) {
            throw new ToolFailureException("-compiler only supports javac, jdt and none");
         } else {
            this.prepareOutputFiles(this.opts, ctx);
            if (AA.useApplicationArchive(ctx.getSourceFile())) {
               try {
                  ctx.setApplicationArchive(CachedApplicationArchiveFactory.instance.create(ctx.getSourceFile(), ctx.getOutputDir()));
               } catch (IOException var6) {
                  throw new ToolFailureException("Unable to create application archive", var6);
               }
            }

            String planArg;
            if (this.opts.hasOption("classpath")) {
               planArg = this.opts.getOption("classpath");
               ctx.setClasspathArg(planArg);
            }

            if (this.opts.hasOption("plan")) {
               planArg = this.opts.getOption("plan");
               ctx.setPlanName(planArg);
               File planFile = new File(planArg);
               ctx.setPlanFile(planFile);
               Loggable l;
               if (!planFile.exists() || !planFile.isFile()) {
                  if (!this.opts.hasOption("ignorePlanValidation")) {
                     l = J2EELogger.logAppcPlanArgDoesNotExistLoggable(planArg);
                     throw new ToolFailureException(l.getMessage());
                  }

                  ctx.setPlanName((String)null);
                  ctx.setPlanFile((File)null);
                  J2EELogger.logIgnoringPlanFile(planArg);
               }

               if (!planArg.endsWith(".xml") && ctx.getPlanName() != null) {
                  l = J2EELogger.logAppcPlanArgWrongTypeLoggable();
                  throw new ToolFailureException(l.getMessage());
               }
            }

            if (this.opts.hasOption("readonly") && this.opts.hasOption("writeInferredDescriptors")) {
               throw new ToolFailureException("readonly & writeInferredDescriptors flags cannot be used together with AppMerge");
            } else {
               if (this.opts.hasOption("ignoreMissingLibRefs")) {
                  ctx.setVerifyLibraryReferences(false);
               }

               if (this.opts.hasOption("writeInferredDescriptors")) {
                  ctx.setWriteInferredDescriptors();
               }

               if (this.opts.hasOption("manifest")) {
                  ctx.setManifestFile(new File(this.opts.getOption("manifest")));
               }

            }
         }
      }
   }

   private String getSourceFileName(Getopt2 opts) {
      String[] args = opts.args();
      return args[0];
   }

   private void prepareOutputFiles(Getopt2 opts, CompilerCtx ctx) throws ToolFailureException {
      String targetFileName = this.sourceFile.getPath();
      boolean archiveOutput = this.sourceFile.isFile();
      boolean output = opts.hasOption("output");
      boolean d = opts.hasOption("d");
      String lowerCaseFileName;
      if (output || d) {
         if (output) {
            targetFileName = opts.getOption("output");
         } else {
            targetFileName = opts.getOption("d");
         }

         if ((new File(targetFileName)).exists()) {
            J2EELogger.logOutputLocationExists(targetFileName);
         }

         lowerCaseFileName = targetFileName.toLowerCase();
         if (!lowerCaseFileName.endsWith(".jar") && !WarDetector.instance.suffixed(lowerCaseFileName) && !lowerCaseFileName.endsWith(".rar") && !lowerCaseFileName.endsWith(".ear")) {
            archiveOutput = false;
         } else {
            archiveOutput = true;
         }

         if (ctx.getOpts().hasOption("nopackage")) {
            archiveOutput = false;
         }

         if ((opts.hasOption("jsps") || opts.hasOption("moduleUri")) && !archiveOutput) {
            ctx.setPartialOutputTarget(targetFileName);
         }
      }

      lowerCaseFileName = null;
      File outputDir;
      if (archiveOutput) {
         File tmpDir = ToolsFactoryManager.getToolsEnvironment().getTemporaryDirectory();
         outputDir = AppcUtils.makeOutputDir(ctx.getTempDir().getName() + "_" + this.sourceFile.getName(), tmpDir, true);
         if (this.verbose) {
            J2EELogger.logCreatedWorkingDirectory(outputDir.toString());
         }
      } else if (ctx.getPartialOutputTarget() != null && this.sourceFile.isDirectory()) {
         outputDir = this.sourceFile;
      } else if (ctx.isReadOnlyInvocation()) {
         outputDir = new File(targetFileName);
      } else {
         outputDir = AppcUtils.makeOutputDir(targetFileName, (File)null, false);
      }

      ctx.setOutputDir(outputDir);
      ctx.setTargetArchive(archiveOutput ? targetFileName : null);
   }
}
