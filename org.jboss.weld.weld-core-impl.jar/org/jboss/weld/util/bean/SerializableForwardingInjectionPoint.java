package org.jboss.weld.util.bean;

import java.io.Serializable;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.ForwardingInjectionPoint;
import org.jboss.weld.serialization.InjectionPointHolder;

public class SerializableForwardingInjectionPoint extends ForwardingInjectionPoint implements Serializable {
   private static final long serialVersionUID = 7803445899943317029L;
   private final InjectionPointHolder ip;

   public SerializableForwardingInjectionPoint(String contextId, InjectionPoint ip) {
      this.ip = new InjectionPointHolder(contextId, ip);
   }

   protected InjectionPoint delegate() {
      return (InjectionPoint)this.ip.get();
   }
}
