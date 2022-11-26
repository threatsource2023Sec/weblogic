package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.descriptor.JmsHandlerBean;
import com.bea.httppubsub.jms.provider.JmsProvider;
import com.bea.httppubsub.jms.provider.JmsProviderFactory;
import java.util.HashMap;
import java.util.Map;

public class JmsProviderFactoryImpl implements JmsProviderFactory {
   private final Map cache = new HashMap();

   public JmsProvider createJmsProvider(JmsHandlerBean jmsHandlerBean) {
      synchronized(this.cache) {
         if (this.cache.containsKey(jmsHandlerBean)) {
            return (JmsProvider)this.cache.get(jmsHandlerBean);
         } else {
            JmsProvider result = new JmsProviderImpl(jmsHandlerBean);
            this.cache.put(jmsHandlerBean, result);
            return result;
         }
      }
   }
}
