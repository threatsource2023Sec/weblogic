package weblogic.ejb.container.interfaces;

public interface Ejb3SessionHome {
   void prepare();

   Object getBindableImpl(String var1);
}
