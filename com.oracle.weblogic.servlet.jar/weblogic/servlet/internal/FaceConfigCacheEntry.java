package weblogic.servlet.internal;

import java.util.List;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataType;

public class FaceConfigCacheEntry extends DescriptorCacheEntry {
   public FaceConfigCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition);
   }

   protected List getResourceLocations() throws MetadataCacheException {
      return this.library.getFacesConfigLocations();
   }

   public MetadataType getType() {
      return MetadataType.FACE_BEANS;
   }
}
