package weblogic.application.metadatacache;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetadata implements Metadata {
   private final Map cacheEntries = new HashMap();

   public SimpleMetadata(MetadataEntry... entries) {
      MetadataEntry[] var2 = entries;
      int var3 = entries.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MetadataEntry entry = var2[var4];
         this.cacheEntries.put(entry.getType(), entry);
      }

   }

   public MetadataEntry[] findAllCachableEntry() {
      return (MetadataEntry[])this.cacheEntries.values().toArray(new MetadataEntry[this.cacheEntries.size()]);
   }

   public MetadataEntry getCachableEntry(MetadataType type) {
      return (MetadataEntry)this.cacheEntries.get(type);
   }
}
