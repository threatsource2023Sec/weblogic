package org.jboss.weld.ws;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ApiAbstraction;

public class WSApiAbstraction extends ApiAbstraction implements Service {
   public final Class WEB_SERVICE_REF_ANNOTATION_CLASS = this.annotationTypeForName("javax.xml.ws.WebServiceRef");

   public WSApiAbstraction(ResourceLoader resourceLoader) {
      super(resourceLoader);
   }

   public void cleanup() {
   }
}
