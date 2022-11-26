package weblogic.servlet.internal;

import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataType;

public class ClassLevelClassInfos extends ClassesCacheEntry {
   public ClassLevelClassInfos(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition);
   }

   public MetadataType getType() {
      return MetadataType.CLASSLEVEL_INFOS;
   }

   public Object getCachableObject() throws MetadataCacheException {
      return this.library.getClassLevelInfos();
   }
}
