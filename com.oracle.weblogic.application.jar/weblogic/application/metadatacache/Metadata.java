package weblogic.application.metadatacache;

public interface Metadata {
   MetadataEntry[] findAllCachableEntry();

   MetadataEntry getCachableEntry(MetadataType var1);
}
