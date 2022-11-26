package weblogic.application.ddconvert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import weblogic.application.ApplicationFileManager;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.ApplicationArchiveEntry;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class ConvertCtx {
   private final ApplicationArchive archive;
   private final ApplicationFileManager afm;
   private final File outputDir;
   private final boolean isVerbose;
   private final boolean isQuiet;
   private final EditableDescriptorManager edm = new EditableDescriptorManager();

   ConvertCtx(ApplicationArchive archive, ApplicationFileManager afm, File outputDir, boolean isVerbose, boolean isQuiet) {
      this.archive = archive;
      this.afm = afm;
      this.outputDir = outputDir;
      this.isVerbose = isVerbose;
      this.isQuiet = isQuiet;
   }

   public ApplicationArchive getApplicationArchive() {
      return this.archive;
   }

   public boolean hasApplicationArchive() {
      return this.archive != null;
   }

   public File getOutputDir() {
      return this.outputDir;
   }

   /** @deprecated */
   @Deprecated
   public VirtualJarFile getAppVJF() throws IOException {
      return this.afm.getVirtualJarFile();
   }

   /** @deprecated */
   @Deprecated
   public VirtualJarFile getModuleVJF(String uri) throws IOException {
      return this.afm.getVirtualJarFile(uri);
   }

   public boolean isVerbose() {
      return this.isVerbose;
   }

   public boolean isQuiet() {
      return this.isQuiet;
   }

   public EditableDescriptorManager getDescriptorManager() {
      return this.edm;
   }

   /** @deprecated */
   @Deprecated
   public GenericClassLoader newClassLoader(VirtualJarFile vjar) {
      return new VJarResourceLoader(vjar);
   }

   public GenericClassLoader newClassLoader(ApplicationArchive application) {
      return new VJarResourceLoader(application);
   }

   public static void debug(String s) {
      System.err.println("[DDConverter] " + s);
   }

   private static class VJarResourceLoader extends GenericClassLoader {
      private final VirtualJarFile vjar;
      private final ApplicationArchive application;

      VJarResourceLoader(VirtualJarFile vjar) {
         super((ClassLoader)null);
         this.vjar = vjar;
         this.application = null;
      }

      VJarResourceLoader(ApplicationArchive application) {
         super((ClassLoader)null);
         this.vjar = null;
         this.application = application;
      }

      public URL getResource(String name) {
         if (this.vjar != null) {
            return this.vjar.getResource(name);
         } else {
            ApplicationArchiveEntry entry = this.application.getEntry(name);
            return entry != null ? null : null;
         }
      }

      public Class loadClass(String className) {
         throw new AssertionError("VJarResourceLoader only loads resources");
      }

      static {
         ClassLoader.registerAsParallelCapable();
      }
   }
}
