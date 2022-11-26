package weblogic.utils.classloaders;

import java.util.Enumeration;

public final class ModuleClassFinder extends AbstractClassFinder {
   private final ClassFinder delegate;
   private final String moduleURI;

   public ModuleClassFinder(ClassFinder delegate, String moduleURI) {
      this.delegate = delegate;
      this.moduleURI = moduleURI + "#";
   }

   public Source getSource(String name) {
      Source s = this.delegate.getSource(name);
      if (s == null && name.startsWith(this.moduleURI)) {
         s = this.delegate.getSource(name.substring(this.moduleURI.length(), name.length()));
      }

      return s;
   }

   public Enumeration getSources(String name) {
      Enumeration e = this.delegate.getSources(name);
      if ((e == null || !e.hasMoreElements()) && name.startsWith(this.moduleURI)) {
         e = this.delegate.getSources(name.substring(this.moduleURI.length(), name.length()));
      }

      return e;
   }

   public Source getClassSource(String name) {
      Source s = this.delegate.getClassSource(name);
      if (s == null && name.startsWith(this.moduleURI)) {
         s = this.delegate.getClassSource(name.substring(this.moduleURI.length(), name.length()));
      }

      return s;
   }

   public String getClassPath() {
      return this.delegate.getClassPath();
   }

   public ClassFinder getManifestFinder() {
      return this.delegate.getManifestFinder();
   }

   public Enumeration entries() {
      return this.delegate.entries();
   }

   public void close() {
      this.delegate.close();
   }
}
