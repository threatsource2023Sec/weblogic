package weblogic.utils.classloaders;

import java.util.Enumeration;

public class DelegateFinder extends AbstractClassFinder {
   private final ClassFinder delegate;

   public DelegateFinder(ClassFinder finder) {
      this.delegate = finder;
   }

   public ClassFinder getFinder() {
      return this.delegate;
   }

   public Source getSource(String name) {
      return this.delegate.getSource(name);
   }

   public Enumeration getSources(String name) {
      return this.delegate.getSources(name);
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
