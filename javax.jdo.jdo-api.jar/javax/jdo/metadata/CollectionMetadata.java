package javax.jdo.metadata;

public interface CollectionMetadata extends Metadata {
   CollectionMetadata setElementType(String var1);

   String getElementType();

   CollectionMetadata setEmbeddedElement(boolean var1);

   Boolean getEmbeddedElement();

   CollectionMetadata setSerializedElement(boolean var1);

   Boolean getSerializedElement();

   CollectionMetadata setDependentElement(boolean var1);

   Boolean getDependentElement();
}
