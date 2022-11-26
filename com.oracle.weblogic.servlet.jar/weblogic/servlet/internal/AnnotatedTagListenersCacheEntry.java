package weblogic.servlet.internal;

import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataType;

public class AnnotatedTagListenersCacheEntry extends ClassesCacheEntry {
   public AnnotatedTagListenersCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition);
   }

   public MetadataType getType() {
      return MetadataType.TAG_LISTENERS;
   }

   public Object getCachableObject() throws MetadataCacheException {
      return this.library.getAnnotatedTagClasses("listener-class");
   }
}
