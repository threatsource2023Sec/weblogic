package weblogic.application.compiler.flow;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryProvider;
import weblogic.application.utils.ApplicationHashService;
import weblogic.application.utils.ApplicationHasher;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public class GenVersionFlow extends CompilerFlow {
   private static final FileFilter FILTER = new VersionFileFilter();

   public GenVersionFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      ApplicationHashService ahs = (ApplicationHashService)LocatorUtilities.getService(ApplicationHashService.class);
      ApplicationHasher hasher = ahs.createApplicationHasher(this.ctx.getVersionGeneratorAlgorithm(), FILTER);
      VirtualJarFile vjf = this.ctx.getVSource();
      if (vjf != null) {
         addVirtualJarFile(hasher, this.ctx.getVSource());
      } else {
         if (this.ctx.getSourceFile() == null) {
            throw new ToolFailureException("Could not find virtual or real source for " + this.ctx.getApplicationId());
         }

         addFile(hasher, this.ctx.getSourceFile());
      }

      ToolsModule[] tms = this.ctx.getModules();
      ToolsModule[] var5 = tms;
      int var6 = tms.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ToolsModule tm = var5[var7];
         ModuleState ms = this.ctx.getModuleState(tm);
         LibraryProvider lp = this.ctx.getLibraryProvider(tm.getURI());
         if (lp != null) {
            Library[] libraries = lp.getReferencedLibraries();
            if (libraries != null) {
               Library[] var12 = libraries;
               int var13 = libraries.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Library library = var12[var14];
                  addFile(hasher, library.getLocation());
               }
            }
         }

         if (ms.isLibrary()) {
            addVirtualJarFile(hasher, ms.getVirtualJarFile());
         }
      }

      String virtualHash = hasher.finishHash();
      if (this.ctx.isVerbose()) {
         J2EELogger.logApplicationIncludedFiles(hasher.getExplodedFilesInHash().toString());
      }

      this.ctx.setApplicationVersion(virtualHash);
   }

   private static void addVirtualJarFile(ApplicationHasher hasher, VirtualJarFile virtualJar) {
      if (virtualJar != null) {
         File libraryFile = new File(virtualJar.getName());
         hasher.addLibrariesOrApplication(libraryFile, new File[0]);
      }
   }

   private static void addFile(ApplicationHasher hasher, File jar) {
      if (jar != null) {
         hasher.addLibrariesOrApplication(jar, new File[0]);
      }
   }

   public void cleanup() throws ToolFailureException {
   }

   private static class VersionFileFilter implements FileFilter {
      private static final Set BANNED = new HashSet(Arrays.asList(".cache.ser", ".beamarker.dat"));

      private VersionFileFilter() {
      }

      public boolean accept(File pathname) {
         String baseName = pathname.getName();
         return !BANNED.contains(baseName);
      }

      // $FF: synthetic method
      VersionFileFilter(Object x0) {
         this();
      }
   }
}
