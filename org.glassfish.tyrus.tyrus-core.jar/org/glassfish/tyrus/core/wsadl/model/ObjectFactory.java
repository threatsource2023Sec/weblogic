package org.glassfish.tyrus.core.wsadl.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public Endpoint createEndpoint() {
      return new Endpoint();
   }

   public Application createApplication() {
      return new Application();
   }
}
