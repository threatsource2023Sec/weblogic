package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryInitializer;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.ToolFailureException;

public class InitLibrariesFlow extends CompilerFlow {
   private LibraryInitializer libraryInitializer = null;

   public InitLibrariesFlow(CompilerCtx ctx, boolean useCache) {
      super(ctx);
      if (!useCache) {
         Cache.LibMetadataCache.disable();
      }

   }

   public void compile() throws ToolFailureException {
      Getopt2 opts = this.ctx.getOpts();
      if (this.initLibraries(opts)) {
         File baseExtractDir = new File(ToolsFactoryManager.getToolsEnvironment().getTemporaryDirectory(), "appc_libraries_" + System.currentTimeMillis());
         this.libraryInitializer = new LibraryInitializer(baseExtractDir, this.ctx.isGenerateVersion());
         if (this.ctx.isVerbose()) {
            this.libraryInitializer.setVerbose();
         }

         if (opts.hasOption("librarydir")) {
            try {
               this.libraryInitializer.registerLibdir(opts.getOption("librarydir"));
            } catch (LoggableLibraryProcessingException var5) {
               throw new ToolFailureException(var5.getLoggable().getMessage(), var5);
            }

            opts.removeOption("librarydir");
         }

         if (opts.hasOption("library")) {
            this.registerLibraries(opts.getOption("library"));
            opts.removeOption("library");
         }

         try {
            this.libraryInitializer.initRegisteredLibraries();
         } catch (LoggableLibraryProcessingException var4) {
            throw new ToolFailureException(var4.getLoggable().getMessage(), var4);
         }
      }

   }

   private boolean initLibraries(Getopt2 opts) {
      return opts.hasOption("library") || opts.hasOption("librarydir");
   }

   private void registerLibraries(String libraries) throws ToolFailureException {
      String[] arg = StringUtils.splitCompletely(libraries, ",");
      boolean registrationSuccessful = true;

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
            LibraryData argDef = this.parseLibraryArg(f, rest);
            this.libraryInitializer.registerLibrary(f, argDef);
         } catch (LoggableLibraryProcessingException var10) {
            var10.getLoggable().log();
            registrationSuccessful = false;
         }
      }

      if (!registrationSuccessful) {
         Loggable l = J2EELogger.logAppcLibraryRegistrationFailedLoggable();
         throw new ToolFailureException(l.getMessage());
      }
   }

   private LibraryData parseLibraryArg(File f, String arg) throws LoggableLibraryProcessingException {
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

   public void cleanup() {
      if (this.ctx.unregisterLibrariesOnExit() && !Boolean.getBoolean("weblogic.application.compiler.flow.InitLibrariesFlow.KeepLibrariesOnExit") && this.libraryInitializer != null) {
         this.libraryInitializer.cleanup();
      }

   }
}
