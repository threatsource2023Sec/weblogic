package weblogic.application.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.DescriptorUpdater;
import weblogic.application.internal.library.BasicLibraryData;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryConstants;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryProvider;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.LibraryContextRootOverrideBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.utils.FileUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class LibraryUtils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugLibraries");

   private LibraryUtils() {
   }

   public static boolean isDebugOn() {
      return debugLogger.isDebugEnabled();
   }

   public static void debug(String s) {
      debugLogger.debug(EarUtils.addClassName(s));
   }

   public static LibraryReferencer initReferencer(String name, RuntimeMBean runtime, String error) {
      return new LibraryReferencer(name, runtime, error);
   }

   public static LibraryReferencer initReferencer(ApplicationContextInternal ctx, String error) {
      return initReferencer(ApplicationVersionUtils.getDisplayName(ctx.getApplicationId()), ctx.getRuntime(), error);
   }

   public static LibraryReferencer initAppReferencer(ApplicationContextInternal ctx) {
      return initReferencer(ApplicationVersionUtils.getDisplayName(ctx.getApplicationId()), ctx.getRuntime(), getAppLibRefError());
   }

   public static LibraryReferencer initAppReferencer() {
      return initReferencer((String)null, (RuntimeMBean)null, getAppLibRefError());
   }

   public static LibraryReferencer initAppReferencer(String appName) {
      return initReferencer(appName, (RuntimeMBean)null, getAppLibRefError(appName));
   }

   public static LibraryReferencer initOptPackReferencer(ApplicationContextInternal ctx) {
      return initReferencer(ApplicationVersionUtils.getDisplayName(ctx.getApplicationId()), ctx.getRuntime(), "Unresolved Optional Package references (in META-INF/MANIFEST.MF):");
   }

   public static LibraryReferencer initOptPackReferencer() {
      return initReferencer((String)null, (RuntimeMBean)null, "Unresolved Optional Package references (in META-INF/MANIFEST.MF):");
   }

   private static String getAppLibRefError() {
      return getAppLibRefError((String)null);
   }

   private static String getAppLibRefError(String appName) {
      StringBuffer sb = new StringBuffer();
      sb.append("Unresolved application library references");
      if (appName != null) {
         sb.append(", for application ").append(appName);
      }

      sb.append(", defined in weblogic-application.xml:");
      return sb.toString();
   }

   public static String nullOrString(DeweyDecimal d) {
      return d == null ? null : d.toString();
   }

   public static String getName(LibraryMBean mbean) {
      return mbean.getApplicationName();
   }

   public static String getSpecVersion(LibraryMBean mbean) {
      return ApplicationVersionUtils.getLibSpecVersion(ApplicationVersionUtils.getVersionId(mbean.getName()));
   }

   public static String getImplVersion(LibraryMBean mbean) {
      return ApplicationVersionUtils.getLibImplVersion(ApplicationVersionUtils.getVersionId(mbean.getName()));
   }

   public static String toString(BasicLibraryData d) {
      return toString(d.getName(), nullOrString(d.getSpecificationVersion()), d.getImplementationVersion());
   }

   public static String toString(LibraryMBean mbean) {
      return toString(getName(mbean), getSpecVersion(mbean), getImplVersion(mbean));
   }

   public static void resetAppDDs(ApplicationDescriptor appDesc, DescriptorUpdater update) throws LoggableLibraryProcessingException {
      try {
         update.setApplicationDescriptor(appDesc);
      } catch (IOException var3) {
         LibraryLoggingUtils.errorMerging(var3);
      } catch (XMLStreamException var4) {
         LibraryLoggingUtils.errorMerging(var4);
      }

   }

   public static void importAppLibraries(LibraryManager mgr, LibraryContext ctx, DescriptorUpdater descUpdate) throws LoggableLibraryProcessingException {
      importAppLibraries(mgr, ctx, descUpdate, false);
   }

   public static void importAppLibraries(LibraryManager mgr, LibraryContext ctx, DescriptorUpdater descUpdate, boolean verbose) throws LoggableLibraryProcessingException {
      ApplicationDescriptor orgDesc = ctx.getApplicationDescriptor();
      populateContextOverrides(ctx);

      try {
         descUpdate.setApplicationDescriptor(new ApplicationDescriptor());
         ctx.notifyDescriptorUpdate();
         Library[] l = mgr.getReferencedLibraries();
         LibraryReference[] r = mgr.getLibraryReferences();
         MultiClassFinder libClassFinder = new MultiClassFinder();
         MultiClassFinder instanceAppLibClassFinder = new MultiClassFinder();
         MultiClassFinder sharedAppLibClassFinder = new MultiClassFinder();

         for(int i = l.length - 1; i >= 0; --i) {
            processLibraryReference((J2EELibraryReference)r[i], l[i], ctx, verbose, libClassFinder, instanceAppLibClassFinder, sharedAppLibClassFinder);
         }

         ctx.addClassFinder(libClassFinder);
         ctx.addInstanceAppLibClassFinder(instanceAppLibClassFinder);
         ctx.addSharedAppLibClassFinder(sharedAppLibClassFinder);
         ApplicationBean appBean = ctx.getApplicationDD();
         if (appBean != null) {
            overrideContextRoot(appBean, ctx.getContextRootOverrideMap());
         }

         LibraryLoggingUtils.updateDescriptor(ctx.getApplicationDescriptor(), appBean);
         ctx.notifyDescriptorUpdate();
         if (orgDesc != null) {
            LibraryLoggingUtils.mergeDescriptors(orgDesc, ctx.getApplicationDescriptor());
            descUpdate.setApplicationDescriptor(orgDesc);
            ctx.notifyDescriptorUpdate();
         }

         if (isDebugOn()) {
            debug("Dumping the merged descriptors descriptor for application " + ctx.getRefappName());
            debug(ctx.getApplicationDescriptor().dumpAllApplicationDescriptors());
         }
      } catch (IOException var11) {
         LibraryLoggingUtils.errorMerging(var11);
      } catch (XMLStreamException var12) {
         LibraryLoggingUtils.errorMerging(var12);
      }

   }

   private static void processLibraryReference(J2EELibraryReference ref, Library lib, LibraryContext ctx, boolean verbose, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder sharedAppLibClassFinder) throws LoggableLibraryProcessingException {
      LibraryLoggingUtils.checkIsAppLibrary(lib);
      ApplicationLibrary appLib = (ApplicationLibrary)lib;
      LibraryLoggingUtils.importLibrary(appLib, ref, ctx, verbose, libraryClassFinder, instanceAppLibClassFinder, sharedAppLibClassFinder);
   }

   public static String toString(String name, String spec, String impl) {
      StringBuffer sb = new StringBuffer();
      sb.append(LibraryConstants.LIBRARY_NAME).append(": ").append(name);
      if (spec != null) {
         sb.append(", ").append(LibraryConstants.SPEC_VERSION_NAME).append(": ").append(spec);
      }

      if (impl != null) {
         sb.append(", ").append(LibraryConstants.IMPL_VERSION_NAME).append(": ").append(impl);
      }

      return sb.toString();
   }

   public static J2EELibraryReference[] initLibRefs(File srcDir) throws LibraryProcessingException {
      WeblogicApplicationBean wlBean = null;

      try {
         ApplicationDescriptor appDesc = new ApplicationDescriptor(VirtualJarFactory.createVirtualJar(srcDir));
         wlBean = appDesc.getWeblogicApplicationDescriptor();
      } catch (Exception var3) {
         throw new LibraryProcessingException(var3);
      }

      return wlBean != null && wlBean.getLibraryRefs() != null ? LibraryLoggingUtils.initLibRefs(wlBean.getLibraryRefs()) : new J2EELibraryReference[0];
   }

   public static LibraryReference[] iniOptPackRefs(File srcDir) throws LibraryProcessingException {
      VirtualJarFile vjf = null;

      LibraryReference[] var3;
      try {
         vjf = VirtualJarFactory.createVirtualJar(srcDir);
         Manifest mf = vjf.getManifest();
         if (mf != null) {
            var3 = LibraryReferenceFactory.getOptPackReference(srcDir.getAbsolutePath(), mf.getMainAttributes());
            return var3;
         }

         var3 = null;
      } catch (IOException var13) {
         throw new LibraryProcessingException(var13);
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var12) {
            }
         }

      }

      return var3;
   }

   public static LibraryReference[] initAllOptPacks(File srcDir) throws LibraryProcessingException {
      Collection rtn = new ArrayList();
      File[] maniFiles = FileUtils.find(srcDir, new FileFilter() {
         public boolean accept(File f) {
            return f.isFile() && "MANIFEST.MF".equals(f.getName()) && "META-INF".equals(f.getParentFile().getName());
         }
      });

      for(int i = 0; i < maniFiles.length; ++i) {
         LibraryReference[] refs = iniOptPackRefs(maniFiles[i].getParentFile().getParentFile());
         if (refs != null) {
            rtn.addAll(Arrays.asList(refs));
         }
      }

      return (LibraryReference[])((LibraryReference[])rtn.toArray(new LibraryReference[rtn.size()]));
   }

   public static void addLibraryUsage(Getopt2 opts) {
      String sep = "@";
      String name = "name";
      String spec = "libspecver";
      String impl = "libimplver";
      opts.addOption("library", "file", "Comma-separated list of libraries. Each library may optionally set its name and versions, if not already set in its manifest, using the following syntax: <file>[" + sep + name + "=<string>" + sep + spec + "=<version> " + sep + impl + "=<version|string>]");
      opts.addOption("librarydir", "dir", "Registers all files in specified directory as libraries.");
   }

   public static VirtualJarFile[] getLibraryVjarsWithDescriptor(LibraryProvider libraryProvider, String descriptorUri) throws IOException {
      ArrayList libList = new ArrayList();
      if (libraryProvider != null) {
         Library[] libs = libraryProvider.getReferencedLibraries();

         for(int i = 0; i < libs.length; ++i) {
            Library lib = libs[i];
            VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(lib.getLocation());
            if (vjf.getEntry(descriptorUri) != null) {
               libList.add(vjf);
            } else {
               vjf.close();
            }
         }
      }

      return (VirtualJarFile[])((VirtualJarFile[])libList.toArray(new VirtualJarFile[0]));
   }

   private static void populateContextOverrides(LibraryContext ctx) throws LoggableLibraryProcessingException {
      ApplicationDescriptor orgDesc = ctx.getApplicationDescriptor();

      try {
         if (orgDesc == null) {
            return;
         }

         WeblogicApplicationBean wlBean = orgDesc.getWeblogicApplicationDescriptor();
         if (wlBean == null) {
            return;
         }

         LibraryContextRootOverrideBean[] co = wlBean.getLibraryContextRootOverrides();
         Map contextOverrides = new HashMap();
         if (co != null) {
            if (isDebugOn()) {
               debug("Adding LibraryContextRootOverrides ...");
            }

            for(int i = 0; i < co.length; ++i) {
               if (isDebugOn()) {
                  debug("Adding LibraryContextRootOverrideBean with context-root '" + co[i].getContextRoot() + "' Override value: '" + co[i].getOverrideValue() + "' ");
               }

               if (co[i].getContextRoot() != null && co[i].getOverrideValue() != null) {
                  contextOverrides.put(co[i].getContextRoot(), co[i].getOverrideValue());
               }
            }
         }

         if (!contextOverrides.isEmpty()) {
            ctx.setContextRootOverrideMap(contextOverrides);
         }
      } catch (XMLStreamException var6) {
         LibraryLoggingUtils.errorMerging(var6);
      } catch (IOException var7) {
         LibraryLoggingUtils.errorMerging(var7);
      }

   }

   private static void overrideContextRoot(ApplicationBean appBean, Map overrides) {
      if (overrides != null && !overrides.isEmpty()) {
         ModuleBean[] mods = appBean.getModules();

         for(int i = 0; i < mods.length; ++i) {
            if (mods[i].getWeb() != null) {
               String contextRoot = mods[i].getWeb().getContextRoot();
               if (contextRoot != null && overrides.get(contextRoot) != null) {
                  String override = (String)overrides.get(contextRoot);
                  if (isDebugOn()) {
                     debug("Overriding context-root '" + mods[i].getWeb().getContextRoot() + "' with value '" + override + "' from descriptor ");
                  }

                  mods[i].getWeb().setContextRoot(override);
                  if (isDebugOn()) {
                     debug("Context root from Descriptor '" + mods[i].getWeb().getContextRoot() + "'");
                  }
               }
            }
         }

      }
   }
}
