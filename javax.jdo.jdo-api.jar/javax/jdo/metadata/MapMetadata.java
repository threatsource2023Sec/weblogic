package javax.jdo.metadata;

public interface MapMetadata extends Metadata {
   MapMetadata setKeyType(String var1);

   String getKeyType();

   MapMetadata setEmbeddedKey(boolean var1);

   Boolean getEmbeddedKey();

   MapMetadata setSerializedKey(boolean var1);

   Boolean getSerializedKey();

   MapMetadata setDependentKey(boolean var1);

   Boolean getDependentKey();

   MapMetadata setValueType(String var1);

   String getValueType();

   MapMetadata setEmbeddedValue(boolean var1);

   Boolean getEmbeddedValue();

   MapMetadata setSerializedValue(boolean var1);

   Boolean getSerializedValue();

   MapMetadata setDependentValue(boolean var1);

   Boolean getDependentValue();
}
