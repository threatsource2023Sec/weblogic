package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.Type;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.metadatacache.Cache;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public class SharedJarLibraryDefinition extends SharedLibraryDefinition {
   public SharedJarLibraryDefinition(LibraryData libData, File tmpDir) {
      super(libData, Type.JAR, tmpDir);
   }

   List getTldLocations() throws MetadataCacheException {
      ClassFinder cf = null;
      ClassFinder rf = null;
      List tldUris = new ArrayList();

      try {
         cf = this.getClassFinder();
         WarUtils.findTlds(this.getLocation(), tldUris);
      } catch (Exception var8) {
         throw new MetadataCacheException(var8);
      } finally {
         if (cf != null) {
            cf.close();
         }

         if (rf != null) {
            ((ClassFinder)rf).close();
         }

      }

      return tldUris;
   }

   protected Set getAnnotatedTagClasses(String type) throws MetadataCacheException {
      ClassFinder cf = null;

      Set var4;
      try {
         cf = this.getClassFinder();
         Map tldInfo = this.getTldInfo();
         var4 = WarUtils.getTagClasses(cf, tldInfo, true, type);
      } catch (IOException var8) {
         throw new MetadataCacheException(var8);
      } finally {
         if (cf != null) {
            cf.close();
         }

      }

      return var4;
   }

   protected List getFacesConfigLocations() throws MetadataCacheException {
      ClassFinder cf = null;
      ClassFinder rf = null;

      List var3;
      try {
         cf = this.getClassFinder();
         var3 = WarUtils.findMetaInfFacesConfigs(cf);
      } catch (Exception var7) {
         throw new MetadataCacheException(var7);
      } finally {
         if (cf != null) {
            cf.close();
         }

         if (rf != null) {
            ((ClassFinder)rf).close();
         }

      }

      return var3;
   }

   protected ClassInfoFinder getClassLevelInfos() throws MetadataCacheException {
      ClassFinder finder = null;

      ClassInfoFinder var2;
      try {
         finder = this.getClassFinder();
         var2 = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(finder).setKeepAnnotatedClassesOnly(false));
      } catch (Exception var6) {
         throw new MetadataCacheException(var6);
      } finally {
         if (finder != null) {
            finder.close();
         }

      }

      return var2;
   }

   public void init() throws LibraryProcessingException {
      if (this.getAutoRef().length > 0) {
         throw new LibraryProcessingException("jar libraries may not be auto-ref");
      } else {
         try {
            this.initializeCacheEntries();
            Cache.LibMetadataCache.initCache(this);
         } catch (MetadataCacheException var2) {
            throw new LibraryProcessingException(var2);
         }
      }
   }

   protected ClassFinder getClassFinder() throws IOException {
      return new JarClassFinder(this.getLocation());
   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      LibraryLoggingUtils.checkNoContextRootSet(libRef, Type.JAR);

      try {
         ClassFinder finder = this.getClassFinder();
         libraryClassFinder.addFinderFirst(finder);
         instanceAppLibClassFinder.addFinderFirst(finder);
      } catch (IOException var7) {
         throw new LibraryProcessingException(var7);
      }
   }
}
