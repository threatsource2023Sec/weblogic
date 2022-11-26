package org.jboss.weld.annotated.slim;

import java.util.Collection;
import org.jboss.weld.bootstrap.api.BootstrapService;

public interface SlimAnnotatedTypeStore extends BootstrapService {
   Collection get(Class var1);

   SlimAnnotatedType get(Class var1, String var2);

   void put(SlimAnnotatedType var1);
}
