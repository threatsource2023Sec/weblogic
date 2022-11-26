package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.Attributes;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.Type;
import weblogic.application.internal.library.BasicLibraryData;
import weblogic.application.internal.library.LibraryManagerAggregate;
import weblogic.application.internal.library.LibraryRegistrationException;
import weblogic.application.internal.library.LibraryRegistry;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public final class LibraryLoggingUtils {
   private static final boolean optPackEnabled = Boolean.getBoolean("weblogic.application.RequireOptionalPackages");
   private static final LibraryRegistry libraryRegistry = LibraryRegistry.getRegistry();

   public static void verifyLibraryReferences(LibraryManager mgr) throws LoggableLibraryProcessingException {
      LibraryManagerAggregate aggr = new LibraryManagerAggregate();
      aggr.addLibraryManager(mgr);
      verifyLibraryReferences(aggr, true);
   }

   public static void verifyLibraryReferences(LibraryManagerAggregate mgrs, boolean isError) throws LoggableLibraryProcessingException {
      verifyLibraryReferences(mgrs.getOptionalPackagesManager(), mgrs, isError);
   }

   public static void verifyLibraryReferences(LibraryManagerAggregate mgrs) throws LoggableLibraryProcessingException {
      verifyLibraryReferences(mgrs, true);
   }

   private static void verifyLibraryReferences(LibraryManager optPackMgr, LibraryManagerAggregate mgrs, boolean isError) throws LoggableLibraryProcessingException {
      if (optPackMgr != null) {
         handleOptPackErrorLevel(optPackMgr);
      }

      if (mgrs.hasUnresolvedRefs()) {
         if (isError) {
            throw new LoggableLibraryProcessingException(J2EELogger.logUnresolvedLibraryReferencesLoggable(mgrs.getUnresolvedRefsError()));
         } else {
            throw new LoggableLibraryProcessingException(J2EELogger.logUnresolvedLibraryReferencesWarningLoggable(mgrs.getUnresolvedRefsError()));
         }
      }
   }

   private static void handleOptPackErrorLevel(LibraryManager m) {
      if (!optPackEnabled && m.hasUnresolvedReferences()) {
         J2EELogger.logUnresolvedOptionalPackages(m.getUnresolvedReferencesAsString());
         m.resetUnresolvedReferences();
      }

   }

   public static void checkNoContextRootSet(J2EELibraryReference ref, Type libType) {
      if (libType != Type.WAR && ref.getContextRoot() != null) {
         J2EELogger.logContextPathSetForNonWarLibRef(ref.toString(), libType.toString());
      }

   }

   public static LibraryData initLibraryData(String name, String spec, String impl, File f) throws LoggableLibraryProcessingException {
      LibraryData rtn = null;

      try {
         rtn = LibraryData.newInstance(name, spec, impl, f);
         return rtn;
      } catch (IOException var6) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInitErrorLoggable(f.getAbsolutePath(), var6.getMessage()), var6);
      } catch (IllegalSpecVersionTypeException var7) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryWithIllegalSpecVersionLoggable(f.getAbsolutePath(), var7.getSpecVersion()), var7);
      }
   }

   public static LibraryData initLibraryData(File f, Attributes attributes) throws LoggableLibraryProcessingException {
      LibraryData rtn = null;

      try {
         rtn = LibraryData.initFromManifest(f, attributes);
         return rtn;
      } catch (IllegalSpecVersionTypeException var4) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryWithIllegalSpecVersionLoggable(f.getAbsolutePath(), var4.getSpecVersion()), var4);
      }
   }

   public static LibraryData initLibraryData(File f) throws LoggableLibraryProcessingException {
      LibraryData rtn = null;

      try {
         rtn = LibraryData.initFromManifest(f);
         return rtn;
      } catch (IOException var3) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInitErrorLoggable(f.getAbsolutePath(), var3.getMessage()), var3);
      } catch (IllegalSpecVersionTypeException var4) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryWithIllegalSpecVersionLoggable(f.getAbsolutePath(), var4.getSpecVersion()), var4);
      }
   }

   public static LibraryData initLibraryData(LibraryMBean mbean, File f) throws LoggableLibraryProcessingException {
      LibraryData rtn = null;

      try {
         rtn = LibraryData.initFromMBean(mbean, f);
         return rtn;
      } catch (IOException var4) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInitErrorLoggable(LibraryUtils.toString(mbean), var4.getMessage()), var4);
      } catch (IllegalSpecVersionTypeException var5) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryWithIllegalMBeanSpecVersionLoggable(LibraryUtils.toString(mbean), var5.getSpecVersion()), var5);
      }
   }

   public static J2EELibraryReference[] initLibRefs(LibraryRefBean[] libRefsDD) throws LoggableLibraryProcessingException {
      J2EELibraryReference[] rtn = null;

      try {
         rtn = LibraryReferenceFactory.getAppLibReference(libRefsDD);
         return rtn;
      } catch (IllegalSpecVersionTypeException var3) {
         throw new LoggableLibraryProcessingException(J2EELogger.logIllegalAppLibSpecVersionRefLoggable(var3.getSpecVersion()), var3);
      }
   }

   public static void handleAppcLibraryInfoMismatch(BasicLibraryData manifest, BasicLibraryData other, File f, String... attrNamesToVerify) throws LoggableLibraryProcessingException {
      Collection mismatch = manifest.verifyDataConsistency(other, attrNamesToVerify);
      if (!mismatch.isEmpty()) {
         throw new LoggableLibraryProcessingException(J2EELogger.logAppcLibraryInfoMismatchLoggable(f.getAbsolutePath(), mismatch.toString()));
      }
   }

   public static void handleLibraryInfoMismatch(BasicLibraryData manifest, BasicLibraryData other, String... attrNamesToVerify) throws LoggableLibraryProcessingException {
      Collection mismatch = manifest.verifyDataConsistency(other, attrNamesToVerify);
      if (!mismatch.isEmpty()) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInfoMismatchLoggable(manifest.toString(), mismatch.toString()));
      }
   }

   public static LibraryDefinition getLibraryDefinition(LibraryData data, File extractDir, Iterator libFactories) throws LoggableLibraryProcessingException {
      LibraryDefinition rtn = null;

      try {
         while(libFactories.hasNext()) {
            LibraryFactory fac = (LibraryFactory)libFactories.next();
            LibraryDefinition def = fac.createLibrary(data, extractDir);
            if (def != null) {
               return def;
            }
         }
      } catch (LoggableLibraryProcessingException var6) {
         throw var6;
      } catch (LibraryProcessingException var7) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInitErrorLoggable(data.getLocation().getAbsolutePath(), StackTraceUtils.throwable2StackTrace(var7)), var7);
      }

      if (rtn == null) {
         throw new LoggableLibraryProcessingException(J2EELogger.logUnknownLibraryTypeLoggable(data.getLocation().getAbsolutePath()));
      } else {
         return (LibraryDefinition)rtn;
      }
   }

   public static void initLibraryDefinition(LibraryDefinition def) throws LoggableLibraryProcessingException {
      try {
         def.init();
      } catch (LoggableLibraryProcessingException var2) {
         throw var2;
      } catch (LibraryProcessingException var3) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryInitErrorLoggable(def.toString(), StackTraceUtils.throwable2StackTrace(var3)), var3);
      }
   }

   public static void registerLibrary(LibraryDefinition def, String registryName, boolean verbose) throws LoggableLibraryProcessingException {
      try {
         libraryRegistry.register(def, registryName);
         if (verbose) {
            J2EELogger.logRegisteredLibrary(def.toString() + " (" + def.getType() + ")");
         }

      } catch (LibraryRegistrationException var4) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryRegistrationErrorLoggable(def.toString(), var4.getMessage()), var4);
      }
   }

   public static void cleanupLibrariesAndRemove() {
      warnCleanupLibraries(true, true);
   }

   private static void warnCleanupLibraries(boolean removeFromReg, boolean silent) {
      Collection c = libraryRegistry.getAll("DOMAIN");
      Iterator iter = c.iterator();

      while(iter.hasNext()) {
         LibraryDefinition def = (LibraryDefinition)iter.next();
         warnCleanupLibrary(def, silent);
         if (removeFromReg) {
            libraryRegistry.remove(def, "DOMAIN");
         }
      }

   }

   private static void warnCleanupLibrary(LibraryDefinition def, boolean silent) {
      try {
         removeLibrary(def);
      } catch (LoggableLibraryProcessingException var3) {
         if (!silent) {
            var3.getLoggable().log();
         }
      } catch (LibraryProcessingException var4) {
         if (!silent) {
            J2EELogger.logLibraryCleanupWarning(def.toString(), StackTraceUtils.throwable2StackTrace(var4));
         }
      }

   }

   public static void partialCleanupAndRemove() {
      Collection c = libraryRegistry.getAll("DOMAIN");
      Iterator iter = c.iterator();

      while(iter.hasNext()) {
         LibraryDefinition def = (LibraryDefinition)iter.next();
         cleanupOnly(def, true);
         libraryRegistry.remove(def, "DOMAIN");
      }

   }

   private static void cleanupOnly(LibraryDefinition def, boolean silent) {
      try {
         def.cleanup();
      } catch (LoggableLibraryProcessingException var3) {
         if (!silent) {
            var3.getLoggable().log();
         }
      } catch (LibraryProcessingException var4) {
         if (!silent) {
            J2EELogger.logLibraryCleanupWarning(def.toString(), StackTraceUtils.throwable2StackTrace(var4));
         }
      }

   }

   public static void errorRemoveLibrary(LibraryDefinition def) throws LoggableLibraryProcessingException {
      try {
         removeLibrary(def);
      } catch (LoggableLibraryProcessingException var2) {
         throw var2;
      } catch (LibraryProcessingException var3) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryCleanupErrorLoggable(def.toString(), StackTraceUtils.throwable2StackTrace(var3)), var3);
      }
   }

   private static void removeLibrary(LibraryDefinition def) throws LibraryProcessingException {
      def.cleanup();
      def.remove();
   }

   public static void checkLibraryExists(File f) throws LoggableLibraryProcessingException {
      if (!f.exists()) {
         throw new LoggableLibraryProcessingException(J2EELogger.logCannotFindLibraryLoggable(f.getAbsolutePath()));
      }
   }

   public static void checkLibdirIsValid(File libdir) throws LoggableLibraryProcessingException {
      if (!libdir.exists() || !libdir.isDirectory()) {
         throw new LoggableLibraryProcessingException(J2EELogger.logCannotProcessLibdirLoggable(libdir.getAbsolutePath()));
      }
   }

   public static void importLibrary(ApplicationLibrary lib, J2EELibraryReference ref, LibraryContext ctx, boolean verbose, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder sharedAppLibClassFinder) throws LoggableLibraryProcessingException {
      try {
         lib.importLibrary(ref, ctx, libraryClassFinder, instanceAppLibClassFinder, sharedAppLibClassFinder);
         if (verbose) {
            J2EELogger.logLibraryImport(lib.toString(), ctx.getRefappName());
         }

      } catch (LoggableLibraryProcessingException var8) {
         throw var8;
      } catch (LibraryProcessingException var9) {
         throw new LoggableLibraryProcessingException(J2EELogger.logErrorImportingLibraryLoggable(lib.toString(), StackTraceUtils.throwable2StackTrace(var9)), var9);
      }
   }

   public static void mergeDescriptors(ApplicationDescriptor base, VirtualJarFile toMerge) throws LoggableLibraryProcessingException {
      try {
         base.mergeDescriptors(toMerge);
      } catch (IOException var3) {
         errorMerging(var3);
      } catch (XMLStreamException var4) {
         errorMerging(var4);
      }

   }

   public static void mergeDescriptors(ApplicationDescriptor base, ApplicationDescriptor toMerge) throws LoggableLibraryProcessingException {
      try {
         base.mergeDescriptors(toMerge);
      } catch (IOException var3) {
         errorMerging(var3);
      } catch (XMLStreamException var4) {
         errorMerging(var4);
      }

   }

   public static void updateDescriptor(ApplicationDescriptor base, ApplicationBean modifiedBean) throws LoggableLibraryProcessingException {
      try {
         base.updateApplicationDescriptor(modifiedBean);
      } catch (IOException var3) {
         errorMerging(var3);
      } catch (XMLStreamException var4) {
         errorMerging(var4);
      }

   }

   public static void errorMerging(Exception ex) throws LoggableLibraryProcessingException {
      throw new LoggableLibraryProcessingException(J2EELogger.logDescriptorMergeErrorLoggable(StackTraceUtils.throwable2StackTrace(ex)), ex);
   }

   public static void checkIsAppLibrary(Library lib) throws LoggableLibraryProcessingException {
      if (!(lib instanceof ApplicationLibrary)) {
         throw new LoggableLibraryProcessingException(J2EELogger.logLibraryIsNotAppLibraryLoggable(lib.toString()));
      }
   }

   public static void warnMissingExtensionName(String name, String src) {
      J2EELogger.logCannotFindExtensionNameWarning(name, src);
   }

   public static BasicLibraryData initOptionalPackageRefLibData(String name, String spec, String impl, String src) {
      BasicLibraryData rtn = null;

      try {
         rtn = new BasicLibraryData(name, spec, impl);
         return rtn;
      } catch (IllegalSpecVersionTypeException var6) {
         J2EELogger.logIllegalOptPackSpecVersionRefWarning(name, spec, src);
         return null;
      }
   }

   public static String registryToString() {
      StringBuffer sb = new StringBuffer();
      Iterator iter = libraryRegistry.getAll("DOMAIN").iterator();

      while(iter.hasNext()) {
         sb.append(iter.next().toString());
         if (iter.hasNext()) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }
}
