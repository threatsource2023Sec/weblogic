package javax.batch.api.chunk.listener;

public interface ItemReadListener {
   void beforeRead() throws Exception;

   void afterRead(Object var1) throws Exception;

   void onReadError(Exception var1) throws Exception;
}
