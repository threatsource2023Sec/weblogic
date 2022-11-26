package weblogic.ejb.spi;

public interface StatefulSessionBeanReference extends SessionBeanReference {
   void remove();

   boolean isRemoved();
}
