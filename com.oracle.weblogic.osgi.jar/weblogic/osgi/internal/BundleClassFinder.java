package weblogic.osgi.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import org.osgi.framework.Bundle;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.MappingEnumerator;

public class BundleClassFinder extends AbstractClassFinder {
   private static final String CLASS_APPENDAGE = ".class";
   private static final String CLASS_SEP = "\\.";
   private static final String FILE_SEP = "/";
   private final Bundle basis;

   public BundleClassFinder(Bundle myBundle) {
      this.basis = myBundle;
   }

   public Source getSource(String name) {
      URL url = this.basis.getResource(name);
      if (url == null) {
         return null;
      } else {
         Source retVal = null;

         try {
            retVal = new SharedURLBundleSource(url, this.basis);
         } catch (IOException var5) {
         }

         return retVal;
      }
   }

   public Enumeration getSources(String name) {
      Enumeration urls;
      try {
         urls = this.basis.getResources(name);
      } catch (IOException var4) {
         return EmptyEnumerator.getEmptyEnumerator();
      }

      if (urls == null) {
         return EmptyEnumerator.getEmptyEnumerator();
      } else {
         MappingEnumerator retVal = new MappingEnumerator(urls) {
            protected Source map(URL o) {
               Source retVal = null;

               try {
                  retVal = new SharedURLBundleSource(o, BundleClassFinder.this.basis);
               } catch (IOException var4) {
               }

               return retVal;
            }
         };
         return retVal;
      }
   }

   public Source getClassSource(String name) {
      String fixedName = name.replaceAll("\\.", "/");
      fixedName = fixedName.concat(".class");
      return this.getSource(fixedName);
   }

   public String getClassPath() {
      return "OSGiBundle(" + this.basis.getSymbolicName() + ")";
   }

   public ClassFinder getManifestFinder() {
      throw new UnsupportedOperationException(Debug.notSupported("OSGiForApps", "getManifestFinder"));
   }

   public Enumeration entries() {
      throw new UnsupportedOperationException(Debug.notSupported("OSGiForApps", "entries"));
   }

   public void close() {
   }

   public String toString() {
      return "BundleClassFinder(" + this.basis.getSymbolicName() + "," + System.identityHashCode(this) + ")";
   }
}
