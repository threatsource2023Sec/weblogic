package org.jboss.weld.bootstrap.events;

import java.io.Serializable;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.util.bean.SerializableForwardingBean;

public class BuilderInterceptorInstance implements Serializable {
   private static final long serialVersionUID = -1623826535751475203L;
   private final SerializableForwardingBean interceptedBean;

   BuilderInterceptorInstance() {
      this((Bean)null, (String)null);
   }

   BuilderInterceptorInstance(Bean interceptedBean, String contextId) {
      this.interceptedBean = interceptedBean != null ? SerializableForwardingBean.of(contextId, interceptedBean) : null;
   }

   public Bean getInterceptedBean() {
      return this.interceptedBean;
   }
}
