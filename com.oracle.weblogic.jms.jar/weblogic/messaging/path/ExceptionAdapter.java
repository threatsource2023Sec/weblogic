package weblogic.messaging.path;

public interface ExceptionAdapter {
   Throwable wrapException(Throwable var1);

   Throwable unwrapException(Throwable var1);
}
