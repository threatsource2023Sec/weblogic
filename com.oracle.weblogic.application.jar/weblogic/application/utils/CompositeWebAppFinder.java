package weblogic.application.utils;

import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public final class CompositeWebAppFinder extends MultiClassFinder {
   private final MultiClassFinder webappFinder = new MultiClassFinder();
   private volatile MultiClassFinder librariesFinder = null;

   public CompositeWebAppFinder() {
      super.addFinder(this.webappFinder);
   }

   public void addFinder(ClassFinder finder) {
      if (finder == null) {
         throw new IllegalArgumentException("Cannot add null finder");
      } else {
         this.webappFinder.addFinder(finder);
      }
   }

   public void addFinderFirst(ClassFinder finder) {
      if (finder == null) {
         throw new IllegalArgumentException("Cannot add null finder");
      } else {
         this.webappFinder.addFinderFirst(finder);
      }
   }

   public void addLibraryFinder(ClassFinder finder) {
      if (finder == null) {
         throw new IllegalArgumentException("Cannot add null finder");
      } else {
         this.initLibraryFinder();
         this.librariesFinder.addFinder(finder);
      }
   }

   public ClassFinder getWebappFinder() {
      return this.webappFinder;
   }

   public ClassFinder getLibraryFinder() {
      return this.librariesFinder;
   }

   private void initLibraryFinder() {
      if (this.librariesFinder == null) {
         this.librariesFinder = new MultiClassFinder();
         super.addFinder(this.librariesFinder);
      }

   }
}
