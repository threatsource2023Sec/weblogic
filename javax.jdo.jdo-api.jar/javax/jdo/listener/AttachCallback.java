package javax.jdo.listener;

public interface AttachCallback {
   void jdoPreAttach();

   void jdoPostAttach(Object var1);
}
