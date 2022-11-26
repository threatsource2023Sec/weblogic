package weblogic.application;

public class ClassLoaderNotFoundException extends ClassNotFoundException {
   public ClassLoaderNotFoundException(String msg) {
      super(msg);
   }

   public ClassLoaderNotFoundException(String msg, Throwable t) {
      super(msg, t);
   }
}
