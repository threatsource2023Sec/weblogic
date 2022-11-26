package weblogic.validation;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ByteArraySource;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;

public class ValidationDescriptorClassFinder extends AbstractClassFinder {
   private static final DebugLogger debugClassloading = DebugLogger.getDebugLogger("DebugValidationClassLoading");
   private static final boolean debugClassloadingEnabled;

   public void close() {
   }

   public Enumeration entries() {
      return new Enumeration() {
         public boolean hasMoreElements() {
            return false;
         }

         public Source nextElement() {
            throw new NoSuchElementException();
         }
      };
   }

   public String getClassPath() {
      return "";
   }

   public Source getClassSource(String arg0) {
      return null;
   }

   public ClassFinder getManifestFinder() {
      return null;
   }

   public Source getSource(String path) {
      if (debugClassloadingEnabled) {
         debugClassloading.debug("Request for source: " + path);
      }

      if (path != null && (path.startsWith("META-INF") && !path.startsWith("META-INF/services") || path.startsWith("WEB-INF")) && (path.endsWith("validation.xml") || ValidationContextManager.getInstance().hasConstraintMapping(path))) {
         ValidationContext vc = ValidationContextManager.getInstance().getValidationContext();
         if (vc != null && (path.endsWith("validation.xml") || vc.hasConstraintMappingResource(path))) {
            URL url = vc.getURLForPath(path);
            if (url != null) {
               try {
                  if (debugClassloadingEnabled) {
                     debugClassloading.debug("Attempting to read source: " + path);
                  }

                  URLSource s = new URLSource(url);
                  return new ByteArraySource(s.getBytes(), url);
               } catch (IOException var5) {
                  if (debugClassloadingEnabled) {
                     debugClassloading.debug("Failed to read source: " + path, var5);
                  }

                  ValidationLogger.errorUnableToReadSource(url, var5);
               }
            }
         }
      }

      return null;
   }

   public Enumeration getSources(final String path) {
      return new Enumeration() {
         private Source source = ValidationDescriptorClassFinder.this.getSource(path);
         private boolean hasMoreElements;

         {
            this.hasMoreElements = this.source != null;
         }

         public boolean hasMoreElements() {
            return this.hasMoreElements;
         }

         public Source nextElement() {
            if (!this.hasMoreElements) {
               throw new NoSuchElementException();
            } else {
               this.hasMoreElements = false;
               return this.source;
            }
         }
      };
   }

   static {
      debugClassloadingEnabled = debugClassloading.isDebugEnabled();
   }
}
