package javax.jdo.listener;

public interface DetachCallback {
   void jdoPreDetach();

   void jdoPostDetach(Object var1);
}
