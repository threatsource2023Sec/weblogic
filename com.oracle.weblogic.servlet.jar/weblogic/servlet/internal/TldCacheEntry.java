package weblogic.servlet.internal;

import java.util.List;
import weblogic.application.metadatacache.MetadataCacheException;
import weblogic.application.metadatacache.MetadataType;

public class TldCacheEntry extends DescriptorCacheEntry {
   public TldCacheEntry(SharedLibraryDefinition sharedLibraryDefinition) {
      super(sharedLibraryDefinition);
   }

   protected List getResourceLocations() throws MetadataCacheException {
      return this.library.getTldLocations();
   }

   public MetadataType getType() {
      return MetadataType.TLD;
   }
}
