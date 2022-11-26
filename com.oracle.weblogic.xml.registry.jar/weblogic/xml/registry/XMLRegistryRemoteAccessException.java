package weblogic.xml.registry;

public class XMLRegistryRemoteAccessException extends XMLRegistryException {
   public XMLRegistryRemoteAccessException(String msg) {
      super(msg);
   }

   public XMLRegistryRemoteAccessException(Throwable th) {
      super(th);
   }

   public XMLRegistryRemoteAccessException(String msg, Throwable th) {
      super(msg, th);
   }
}
