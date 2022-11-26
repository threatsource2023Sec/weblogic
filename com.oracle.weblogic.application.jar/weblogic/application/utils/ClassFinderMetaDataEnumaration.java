package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.FileSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.classloaders.ZipSource;

public class ClassFinderMetaDataEnumaration implements Enumeration {
   private final ClassAnnotationMetaDataFilter annotationFilter;
   private final Enumeration delegate;
   private MetaDataFilter.Resource entry;

   public ClassFinderMetaDataEnumaration(ClassFinder finder, ClassAnnotationMetaDataFilter annotationFilter) {
      this.annotationFilter = annotationFilter;
      this.delegate = finder.entries();
   }

   public boolean hasMoreElements() {
      while(this.entry == null && this.delegate.hasMoreElements()) {
         final Source s = (Source)this.delegate.nextElement();
         if (s.getURL().getFile().endsWith(".class")) {
            this.entry = new MetaDataFilter.Resource() {
               public String getName() {
                  if (s instanceof FileSource) {
                     FileSource fs = (FileSource)s;

                     try {
                        int pos = (new File(fs.getCodeBase())).getCanonicalPath().length();
                        return fs.getFile().getCanonicalPath().substring(pos + 1);
                     } catch (IOException var3) {
                        var3.printStackTrace();
                     }
                  } else if (s instanceof ZipSource) {
                     return ((ZipSource)s).getEntry().getName();
                  }

                  return s.getURL().getFile();
               }

               public byte[] getContent() throws IOException {
                  return (new URLSource(s.getURL())).getBytes();
               }
            };

            try {
               if (this.annotationFilter.matches(this.entry) == null) {
                  this.entry = null;
               }
            } catch (IOException var3) {
               this.entry = null;
               return false;
            }
         }
      }

      return this.entry != null;
   }

   public String nextElement() {
      if (!this.hasMoreElements()) {
         throw new NoSuchElementException();
      } else {
         String name = this.entry.getName();
         this.entry = null;
         name = name.replace('\\', '.');
         name = name.replace('/', '.');
         name = name.substring(0, name.length() - 6);
         return name;
      }
   }
}
