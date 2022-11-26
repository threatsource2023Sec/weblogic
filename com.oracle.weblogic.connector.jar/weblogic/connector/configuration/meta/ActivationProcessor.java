package weblogic.connector.configuration.meta;

import javax.resource.spi.Activation;
import weblogic.connector.exception.RAException;
import weblogic.j2ee.descriptor.ActivationSpecBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;

@AnnotationProcessorDescription(
   targetAnnotation = Activation.class
)
class ActivationProcessor implements TypeAnnotationProcessor {
   private ConnectorBeanNavigator beanNavigator;

   ActivationProcessor(ConnectorBeanNavigator connectorBeanNav) {
      this.beanNavigator = connectorBeanNav;
   }

   public void processClass(Class clz, Activation annotation) throws RAException {
      Class[] messageListeners = annotation.messageListeners();
      if (messageListeners != null) {
         Class[] var4 = messageListeners;
         int var5 = messageListeners.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class msgListener = var4[var6];
            MessageListenerBean listenerBean = this.getOrCreateMessageListenerBean(msgListener.getName());
            this.mergeDDandAnnotation(clz, msgListener, listenerBean);
         }
      }

   }

   MessageListenerBean getOrCreateMessageListenerBean(String listener) {
      assert null != listener;

      MessageAdapterBean messageAdapter = this.beanNavigator.getMessageAdapterBean();
      if (messageAdapter != null) {
         MessageListenerBean[] messageListeners = messageAdapter.getMessageListeners();
         if (messageListeners != null) {
            MessageListenerBean[] var4 = messageListeners;
            int var5 = messageListeners.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               MessageListenerBean messageListenerBean = var4[var6];
               if (listener.equals(messageListenerBean.getMessageListenerType())) {
                  return messageListenerBean;
               }
            }
         }
      }

      this.beanNavigator.context.readPath("messageListener", listener);
      MessageListenerBean listenerBean = this.beanNavigator.createMessageListenerBean();
      listenerBean.setMessageListenerType(listener);
      return listenerBean;
   }

   void mergeDDandAnnotation(Class clz, Class msgListener, MessageListenerBean messageListenerBean) throws RAException {
      if (messageListenerBean.getActivationSpec() == null) {
         messageListenerBean.createActivationSpec();
      }

      ActivationSpecBean activationSpec = messageListenerBean.getActivationSpec();
      if (!MetaUtils.isPropertySet(activationSpec, "ActivationSpecClass")) {
         activationSpec.setActivationSpecClass(clz.getName());
      }

   }
}
