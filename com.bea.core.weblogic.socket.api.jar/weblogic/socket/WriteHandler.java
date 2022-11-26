package weblogic.socket;

public interface WriteHandler {
   void onWritable() throws Exception;

   void onError(Throwable var1);
}
