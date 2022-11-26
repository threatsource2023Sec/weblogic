package weblogic.application.metadatacache;

public class MetadataCacheException extends Exception {
   public MetadataCacheException(String message) {
      super(message);
   }

   public MetadataCacheException(String message, Throwable cause) {
      super(message, cause);
   }

   public MetadataCacheException(Throwable cause) {
      super(cause);
   }
}
