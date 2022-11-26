package weblogic.application.library;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.internal.library.LibraryRegistry;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.PathUtils;
import weblogic.utils.FileUtils;

public final class LibraryInitializer {
   private static final LibraryRegistry libraryRegistry = LibraryRegistry.getRegistry();
   private final ApplicationFactoryManager libFactories;
   private final File baseExtractDir;
   private final Collection libdirs;
   private boolean verbose;
   private boolean silent;
   private final boolean relaxedManifestVerification;

   public LibraryInitializer() {
      this(new File(System.getProperty("java.io.tmpdir"), "libraries"), ApplicationFactoryManager.getApplicationFactoryManager(), false);
   }

   public LibraryInitializer(File baseExtractDir, boolean relaxedManifestVerification) {
      this(baseExtractDir, ApplicationFactoryManager.getApplicationFactoryManager(), relaxedManifestVerification);
   }

   public LibraryInitializer(File baseExtractDir, ApplicationFactoryManager libFactories) {
      this(baseExtractDir, libFactories, false);
   }

   private LibraryInitializer(File baseExtractDir, ApplicationFactoryManager libFactories, boolean relaxedManifestVerification) {
      this.libdirs = new HashSet();
      this.verbose = false;
      this.silent = false;
      this.baseExtractDir = baseExtractDir;
      this.libFactories = libFactories;
      this.relaxedManifestVerification = relaxedManifestVerification;
   }

   public void setVerbose() {
      this.verbose = true;
   }

   public void setSilent() {
      this.silent = true;
   }

   public Library[] getAllLibraries() {
      Collection rtn = libraryRegistry.getAll("DOMAIN");
      return (Library[])((Library[])rtn.toArray(new Library[rtn.size()]));
   }

   public void cleanup() {
      LibraryLoggingUtils.cleanupLibrariesAndRemove();
      FileUtils.remove(this.baseExtractDir);
   }

   public void registerLibrary(File f, LibraryData arg) throws LoggableLibraryProcessingException {
      LibraryLoggingUtils.checkLibraryExists(f);
      LibraryData data = LibraryLoggingUtils.initLibraryData(f);
      data = this.mergeData(data, arg, f);
      LibraryDefinition def = this.getLibraryDefinition(data);
      LibraryLoggingUtils.registerLibrary(def, "DOMAIN", this.verbose);
   }

   public void initRegisteredLibraries() throws LoggableLibraryProcessingException {
      Collection rtn = libraryRegistry.getAll("DOMAIN");
      Iterator iter = rtn.iterator();

      while(iter.hasNext()) {
         LibraryDefinition def = (LibraryDefinition)iter.next();
         LibraryLoggingUtils.initLibraryDefinition(def);
      }

   }

   public void registerLibdir(String dir) throws LoggableLibraryProcessingException {
      File libdir = new File(dir);
      LibraryLoggingUtils.checkLibdirIsValid(libdir);

      try {
         if (!this.libdirs.add(libdir.getCanonicalFile())) {
            return;
         }
      } catch (IOException var7) {
         throw new AssertionError(var7);
      }

      File[] files = libdir.listFiles();

      for(int i = 0; i < files.length; ++i) {
         try {
            this.registerLibrary(files[i], LibraryData.newEmptyInstance(files[i]));
         } catch (LoggableLibraryProcessingException var6) {
            if (!this.silent) {
               var6.getLoggable().log();
            }
         }
      }

   }

   private LibraryDefinition getLibraryDefinition(LibraryData data) throws LoggableLibraryProcessingException {
      File extractDir = new File(this.baseExtractDir, PathUtils.generateTempPath((String)null, data.getName(), data.getSpecificationVersion() + data.getImplementationVersion()));
      LibraryDefinition rtn = LibraryLoggingUtils.getLibraryDefinition(data, extractDir, this.libFactories.getLibraryFactories());
      return rtn;
   }

   private LibraryData mergeData(LibraryData manifest, LibraryData arg, File f) throws LoggableLibraryProcessingException {
      if (arg.getName() == null && manifest.getName() == null) {
         arg = LibraryData.cloneWithNewName(getName(f), arg);
      }

      manifest = manifest.importData(arg);
      arg = arg.importData(manifest);
      if (this.relaxedManifestVerification) {
         LibraryLoggingUtils.handleAppcLibraryInfoMismatch(manifest, arg, f, LibraryConstants.LIBRARY_NAME);
      } else {
         LibraryLoggingUtils.handleAppcLibraryInfoMismatch(manifest, arg, f);
      }

      return manifest;
   }

   private static String getName(File f) {
      if (f.isDirectory()) {
         return f.getName();
      } else {
         return f.getName().indexOf(".") < 0 ? f.getName() : f.getName().substring(0, f.getName().lastIndexOf("."));
      }
   }
}
