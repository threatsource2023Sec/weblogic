package weblogic.servlet.internal;

import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataType;

public class AnnotatedTagHandlersCacheEntry extends ClassesCacheEntry {
   public AnnotatedTagHandlersCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition);
   }

   public MetadataType getType() {
      return MetadataType.TAG_HANDLERS;
   }

   public Object getCachableObject() throws MetadataCacheException {
      return this.library.getAnnotatedTagClasses("tag-class");
   }
}
