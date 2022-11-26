package weblogic.socket.utils;

public interface QueueDef {
   int count();

   int size();

   boolean empty();

   void put(Object var1) throws QueueFullException;

   void putW(Object var1);

   Object get();

   Object getW();

   Object getW(int var1);

   void cancelWait();

   void resetCancel();

   Object peek();
}
