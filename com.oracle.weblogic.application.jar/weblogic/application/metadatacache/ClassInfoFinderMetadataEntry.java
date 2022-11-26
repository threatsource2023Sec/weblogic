package weblogic.application.metadatacache;

import java.io.File;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.internal.Controls;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;

public class ClassInfoFinderMetadataEntry extends ClassesMetadataEntry {
   private final String classpath;
   private final ClassInfoFinderFactory.Params params;
   private File cacheDir;

   public ClassInfoFinderMetadataEntry(File srcLocation, File cacheLocationDir, String classpath) {
      super(srcLocation, cacheLocationDir);
      this.classpath = classpath;
      this.params = null;
   }

   public ClassInfoFinderMetadataEntry(File srcLocation, File cacheLocationDir, ClassInfoFinderFactory.Params params) {
      super(srcLocation, cacheLocationDir);
      this.classpath = null;
      this.params = params;
   }

   public MetadataType getType() {
      return MetadataType.CLASSLEVEL_INFOS;
   }

   protected Object getValidatingParams() {
      return this.params;
   }

   public Object getCachableObject() throws MetadataCacheException {
      if (Controls.annoscancache.enabled) {
         try {
            return this.params != null ? ClassInfoFinderFactory.FACTORY.newInstance(this.params) : ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams((ClassFinder)(new ClasspathClassFinder2(this.classpath))));
         } catch (AnnotationProcessingException var2) {
            throw new MetadataCacheException(var2);
         }
      } else {
         return null;
      }
   }

   public File getCacheDir() {
      if (this.cacheDir != null) {
         return this.cacheDir;
      } else {
         this.cacheDir = new File(this.getLocation(), this.getType().getName());
         return this.cacheDir;
      }
   }
}
