package javax.jdo.metadata;

public interface ArrayMetadata extends Metadata {
   ArrayMetadata setElementType(String var1);

   String getElementType();

   ArrayMetadata setEmbeddedElement(boolean var1);

   Boolean getEmbeddedElement();

   ArrayMetadata setSerializedElement(boolean var1);

   Boolean getSerializedElement();

   ArrayMetadata setDependentElement(boolean var1);

   Boolean getDependentElement();
}
