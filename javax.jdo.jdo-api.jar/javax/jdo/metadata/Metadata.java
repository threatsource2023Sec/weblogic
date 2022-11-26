package javax.jdo.metadata;

public interface Metadata {
   ExtensionMetadata newExtensionMetadata(String var1, String var2, String var3);

   int getNumberOfExtensions();

   ExtensionMetadata[] getExtensions();

   Metadata getParent();
}
