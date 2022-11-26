package weblogic.application.internal.library;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import weblogic.application.Type;
import weblogic.application.internal.Controls;
import weblogic.application.internal.classloading.ShareabilityException;
import weblogic.application.internal.classloading.ShareabilityManager;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.ClassInfoFinderMetadataEntry;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataEntry;
import weblogic.application.metadatacache.MetadataType;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public class JarLibraryDefinition extends LibraryDefinition implements Library, ApplicationLibrary {
   private Map cacheEntries;
   private ClassInfoFinderMetadataEntry classInfoFinderEntry;
   private final File tmpDir;

   public JarLibraryDefinition(LibraryData d, File tmpDir) {
      super(d, Type.JAR);
      this.tmpDir = tmpDir;
   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      LibraryLoggingUtils.checkNoContextRootSet(libRef, Type.JAR);

      try {
         JarClassFinder finder = this.getClassFinder(this.getLocation());
         libraryClassFinder.addFinderFirst(finder);
         (new ShareabilityManager((ShareableBean[])null)).sortJarClassFinderConstituents(finder, false, shaerdAppLibClassFinder, instanceAppLibClassFinder, false);
         if (Controls.annoscancache.enabled) {
            ctx.addLibraryClassInfoFinderFirst((ClassInfoFinder)Cache.LibMetadataCache.lookupCachedObject(this.classInfoFinderEntry));
         }

      } catch (IOException var7) {
         throw new LibraryProcessingException(var7);
      } catch (ShareabilityException var8) {
         throw new LibraryProcessingException(var8);
      } catch (MetadataCacheException var9) {
         throw new LibraryProcessingException(var9);
      }
   }

   private JarClassFinder getClassFinder(File file) throws IOException {
      return new JarClassFinder(file);
   }

   public void init() throws LibraryProcessingException {
      if (this.getAutoRef().length > 0) {
         throw new LibraryProcessingException("jar libraries may not be auto-ref");
      } else {
         try {
            if (Controls.annoscancache.enabled) {
               this.cacheEntries = new HashMap();
               this.classInfoFinderEntry = new ClassInfoFinderMetadataEntry(this.getLibData().getLocation(), this.tmpDir, this.getClassFinder(this.getLocation()).getClassPath());
               this.cacheEntries.put(MetadataType.CLASSLEVEL_INFOS, this.classInfoFinderEntry);
               Cache.LibMetadataCache.initCache(this);
            }

         } catch (IOException var2) {
            throw new LibraryProcessingException(var2);
         } catch (MetadataCacheException var3) {
            throw new LibraryProcessingException(var3);
         }
      }
   }

   public void remove() throws LibraryProcessingException {
      if (Controls.annoscancache.enabled && this.tmpDir != null) {
         FileUtils.remove(this.tmpDir);
         File parent = this.tmpDir.getParentFile();
         if (parent.exists() && this.isEmptyDir(parent)) {
            FileUtils.remove(parent);
         }
      }

   }

   private boolean isEmptyDir(File dir) {
      String[] files = dir.list();
      return files == null || files.length <= 0;
   }

   public MetadataEntry[] findAllCachableEntry() {
      return (MetadataEntry[])this.cacheEntries.values().toArray(new MetadataEntry[this.cacheEntries.size()]);
   }

   public MetadataEntry getCachableEntry(MetadataType type) {
      return (MetadataEntry)this.cacheEntries.get(type);
   }

   public File getTemporaryDirectory() {
      return this.tmpDir;
   }
}
