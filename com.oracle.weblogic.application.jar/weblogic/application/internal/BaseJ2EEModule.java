package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.utils.EarUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.utils.jars.VirtualJarFile;

public abstract class BaseJ2EEModule {
   private static final DebugLogger deploymentLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   protected String altUri;

   protected final ComponentMBean findComponentMBeanInternal(ApplicationContextInternal appCtx, String uri, Class type) throws ModuleException {
      ComponentMBean c = ComponentMBeanHelper.findComponentMBeanByURI(appCtx.getApplicationMBean(), uri, type);
      if (c != null) {
         return c;
      } else {
         LibraryRuntimeMBean[] libs = appCtx.getRuntime().getLibraryRuntimes();
         if (libs != null) {
            for(int i = 0; i < libs.length; ++i) {
               c = ComponentMBeanHelper.findComponentMBeanByURI(libs[i].getComponents(), uri, type);
               if (c != null) {
                  return c;
               }
            }
         }

         return null;
      }
   }

   protected final ComponentMBean findComponentMBean(ApplicationContextInternal appCtx, String uri, Class type) throws ModuleException {
      ComponentMBean c = this.findComponentMBeanInternal(appCtx, uri, type);
      if (c != null) {
         return c;
      } else {
         throw new ModuleException("No ComponentMBean was found in Application " + appCtx.getApplicationName() + " with the URI " + uri);
      }
   }

   protected File resolveAltDD(ApplicationContextInternal appCtx, String uri) throws ModuleException {
      File altDDFile = null;
      if (!appCtx.isEar()) {
         altDDFile = EarUtils.getExternalAltDDFile(appCtx);
         if (altDDFile != null) {
            return altDDFile;
         }
      }

      VirtualJarFile earVjf = null;

      File var5;
      try {
         this.altUri = EarUtils.getAltDDUri(appCtx.getApplicationDD(), uri);
         if (this.altUri == null) {
            var5 = null;
            return var5;
         }

         earVjf = appCtx.getApplicationFileManager().getVirtualJarFile();
         altDDFile = EarUtils.resolveAltDD(earVjf, this.altUri);
         if (altDDFile == null) {
            throw new IOException("Unable to find the alt-dd for module " + uri + " with the alt-dd " + this.altUri);
         }

         var5 = altDDFile;
      } catch (IOException var15) {
         throw new ModuleException(var15);
      } finally {
         try {
            if (earVjf != null) {
               earVjf.close();
            }
         } catch (IOException var14) {
         }

      }

      return var5;
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[0];
   }

   protected boolean acceptModuleUri(ApplicationContextInternal appCtx, String moduleUri, String uri) {
      String prefix = moduleUri + "/";
      debug("module uri prefix: " + prefix);
      debug("module uri: " + uri);
      return !appCtx.isEar() || uri.startsWith(prefix);
   }

   protected String mangle(ApplicationContextInternal appCtx, String moduleUri, String uri) {
      return appCtx.isEar() ? moduleUri + "/" + uri : uri;
   }

   protected String unmangle(ApplicationContextInternal appCtx, String moduleUri, String uri) {
      return appCtx.isEar() && uri.startsWith(moduleUri) ? uri.substring(moduleUri.length()) : uri;
   }

   private static void debug(String s) {
      deploymentLogger.debug("[BaseJ2EEModule] " + s);
   }
}
