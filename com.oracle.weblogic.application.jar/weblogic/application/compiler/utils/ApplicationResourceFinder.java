package weblogic.application.compiler.utils;

import java.util.Enumeration;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public class ApplicationResourceFinder extends AbstractClassFinder {
   private final ClassFinder delegate;
   private final String prefix;

   public ApplicationResourceFinder(String URI, ClassFinder delegate) {
      this.prefix = URI + "#";
      this.delegate = delegate;
   }

   public Source getSource(String name) {
      return this.delegate.getSource(this.prefix + name);
   }

   public Enumeration getSources(String name) {
      return this.delegate.getSources(this.prefix + name);
   }

   public Source getClassSource(String name) {
      return this.delegate.getClassSource(name);
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
