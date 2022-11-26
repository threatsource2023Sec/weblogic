package org.jboss.weld.contexts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import javax.enterprise.context.spi.Contextual;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.injection.spi.ResourceReference;

@SuppressFBWarnings({"NM_SAME_SIMPLE_NAME_AS_INTERFACE"})
public interface WeldCreationalContext extends org.jboss.weld.construction.api.WeldCreationalContext {
   WeldCreationalContext getCreationalContext(Contextual var1);

   WeldCreationalContext getProducerReceiverCreationalContext(Contextual var1);

   Object getIncompleteInstance(Contextual var1);

   void addDependentInstance(ContextualInstance var1);

   void release();

   WeldCreationalContext getParentCreationalContext();

   Contextual getContextual();

   List getDependentInstances();

   boolean destroyDependentInstance(Object var1);

   void addDependentResourceReference(ResourceReference var1);
}
