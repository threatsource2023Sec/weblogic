package weblogic.rmi.extensions;

import java.io.ObjectStreamException;

public final class RemoteRuntimeException extends RuntimeException {
   private static final long serialVersionUID = -5716682337887230827L;

   public RemoteRuntimeException() {
   }

   public RemoteRuntimeException(String msg) {
      super(msg);
   }

   public RemoteRuntimeException(Throwable nestedException) {
      super(nestedException);
   }

   public RemoteRuntimeException(String msg, Throwable nestedException) {
      super(msg, nestedException);
   }

   private Object writeReplace() throws ObjectStreamException {
      return new RuntimeException(this.getMessage(), this.getCause());
   }

   public Throwable getNested() {
      return this.getCause();
   }

   public Throwable getNestedException() {
      return this.getCause();
   }
}
