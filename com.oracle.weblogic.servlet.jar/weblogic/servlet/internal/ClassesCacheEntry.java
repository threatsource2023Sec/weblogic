package weblogic.servlet.internal;

import weblogic.application.metadatacache.ClassesMetadataEntry;

abstract class ClassesCacheEntry extends ClassesMetadataEntry {
   protected SharedLibraryDefinition library;

   public ClassesCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition.getLibData().getLocation(), sharedLibraryDefinition.getLibTempDir());
      this.library = sharedLibraryDefinition;
   }

   public boolean hasStaleChecked() {
      return this.library.hasStaleChecked();
   }
}
