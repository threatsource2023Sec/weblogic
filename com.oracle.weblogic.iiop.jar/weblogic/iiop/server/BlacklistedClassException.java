package weblogic.iiop.server;

public class BlacklistedClassException extends RuntimeException {
   BlacklistedClassException(String className) {
      super("Unauthorized deserialization attempt for class " + className);
   }
}
