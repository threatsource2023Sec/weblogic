package weblogic.ejb.spi;

public interface ClientDrivenBeanInfo extends BeanInfo {
   String getHomeInterfaceName();

   String getLocalHomeInterfaceName();

   String[] getImplementedInterfaceNames();

   boolean hasClientViewFor(String var1);

   Object getBindable(String var1);

   Class getGeneratedBeanClass();
}
