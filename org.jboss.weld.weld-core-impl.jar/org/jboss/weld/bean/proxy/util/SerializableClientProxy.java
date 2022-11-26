package org.jboss.weld.bean.proxy.util;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.Container;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;

public class SerializableClientProxy implements Serializable {
   private static final long serialVersionUID = -46820068707447753L;
   private final BeanIdentifier beanId;
   private final String contextId;

   public SerializableClientProxy(BeanIdentifier beanId, String contextId) {
      this.beanId = beanId;
      this.contextId = contextId;
   }

   Object readResolve() throws ObjectStreamException {
      Bean bean = (Bean)((ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class)).getContextual(this.beanId);
      if (bean == null) {
         throw BeanLogger.LOG.proxyDeserializationFailure(this.beanId);
      } else {
         return Container.instance(this.contextId).deploymentManager().getClientProxyProvider().getClientProxy(bean);
      }
   }
}
