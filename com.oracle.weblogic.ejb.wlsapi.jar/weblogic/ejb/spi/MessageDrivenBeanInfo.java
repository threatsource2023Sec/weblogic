package weblogic.ejb.spi;

public interface MessageDrivenBeanInfo extends BeanInfo {
   /** @deprecated */
   @Deprecated
   boolean implementsMessageListener();

   Class getMessagingTypeInterfaceClass();

   /** @deprecated */
   @Deprecated
   Class getGeneratedBeanClass();

   Class getBeanClassToInstantiate();
}
