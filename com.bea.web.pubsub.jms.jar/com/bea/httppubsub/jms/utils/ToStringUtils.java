package com.bea.httppubsub.jms.utils;

import com.bea.httppubsub.descriptor.JmsHandlerBean;

public final class ToStringUtils {
   private ToStringUtils() {
   }

   public static String jmsHandlerBeanToString(JmsHandlerBean jmsHandlerBean) {
      return jmsHandlerBean == null ? null : jmsHandlerBean.getJmsProviderUrl() + "/" + jmsHandlerBean.getConnectionFactoryJndiName() + "/" + jmsHandlerBean.getTopicJndiName();
   }
}
