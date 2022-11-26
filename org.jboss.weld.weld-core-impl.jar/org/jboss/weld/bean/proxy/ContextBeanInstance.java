package org.jboss.weld.bean.proxy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.Container;
import org.jboss.weld.bean.ContextualInstance;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
   justification = "bean field is loaded lazily"
)
public class ContextBeanInstance extends AbstractBeanInstance implements Serializable {
   private static final long serialVersionUID = -8144230657830556503L;
   private transient Bean bean;
   private final BeanIdentifier id;
   private final String contextId;
   private final transient Class instanceType;
   private final transient BeanManagerImpl manager;
   private final transient CurrentInjectionPoint currentInjectionPoint;
   private static final ThreadLocal currentCreationalContext = new ThreadLocal();

   public ContextBeanInstance(Bean bean, BeanIdentifier id, String contextId) {
      this.bean = bean;
      this.id = id;
      this.contextId = contextId;
      this.instanceType = this.computeInstanceType(bean);
      BeanLogger.LOG.createdContextInstance(bean, id);
      this.manager = Container.instance(contextId).deploymentManager();
      this.currentInjectionPoint = (CurrentInjectionPoint)this.manager.getServices().get(CurrentInjectionPoint.class);
   }

   public Object getInstance() {
      if (!Container.isSet(this.contextId)) {
         throw ContextLogger.LOG.contextualReferenceNotValidAfterShutdown(this.bean, this.contextId);
      } else {
         Object existingInstance = ContextualInstance.getIfExists(this.bean, this.manager);
         if (existingInstance != null) {
            return existingInstance;
         } else {
            WeldCreationalContext previousCreationalContext = (WeldCreationalContext)currentCreationalContext.get();
            Object creationalContext;
            if (previousCreationalContext == null) {
               creationalContext = new CreationalContextImpl(this.bean);
            } else {
               creationalContext = previousCreationalContext.getCreationalContext(this.bean);
            }

            currentCreationalContext.set(creationalContext);
            ThreadLocalStack.ThreadLocalStackReference stack = this.currentInjectionPoint.push(EmptyInjectionPoint.INSTANCE);

            Object var5;
            try {
               var5 = ContextualInstance.get((Bean)this.bean, this.manager, (CreationalContext)creationalContext);
            } finally {
               stack.pop();
               if (previousCreationalContext == null) {
                  currentCreationalContext.remove();
               } else {
                  currentCreationalContext.set(previousCreationalContext);
               }

            }

            return var5;
         }
      }
   }

   public Class getInstanceType() {
      return (Class)Reflections.cast(this.instanceType);
   }

   private Object readResolve() throws ObjectStreamException {
      Bean bean = (Bean)((ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class)).getContextual(this.id);
      return new ContextBeanInstance(bean, this.id, this.contextId);
   }
}
