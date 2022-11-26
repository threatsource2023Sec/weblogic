package org.jboss.weld.serialization;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.bean.CommonBean;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.contexts.SerializableContextualFactory;
import org.jboss.weld.contexts.SerializableContextualInstanceImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.serialization.spi.helpers.SerializableContextualInstance;
import org.jboss.weld.util.reflection.Reflections;

public class ContextualStoreImpl implements ContextualStore {
   private static final String GENERATED_ID_PREFIX = ContextualStoreImpl.class.getName();
   private final ConcurrentMap contextuals;
   private final ConcurrentMap contextualsInverse;
   private final ConcurrentMap passivationCapableContextuals;
   private final AtomicInteger idGenerator;
   private final String contextId;
   private final BeanIdentifierIndex beanIdentifierIndex;

   public ContextualStoreImpl(String contextId, BeanIdentifierIndex beanIdentifierIndex) {
      this.contextId = contextId;
      this.beanIdentifierIndex = beanIdentifierIndex;
      this.idGenerator = new AtomicInteger(0);
      this.contextuals = new ConcurrentHashMap();
      this.contextualsInverse = new ConcurrentHashMap();
      this.passivationCapableContextuals = new ConcurrentHashMap();
   }

   public Contextual getContextual(String id) {
      return this.getContextual((BeanIdentifier)(new StringBeanIdentifier(id)));
   }

   public Contextual getContextual(BeanIdentifier identifier) {
      return identifier.asString().startsWith(GENERATED_ID_PREFIX) ? (Contextual)this.contextualsInverse.get(identifier) : (Contextual)this.passivationCapableContextuals.get(identifier);
   }

   @SuppressFBWarnings(
      value = {"RV_RETURN_VALUE_OF_PUTIFABSENT_IGNORED"},
      justification = "Using non-standard semantics of putIfAbsent"
   )
   public BeanIdentifier putIfAbsent(Contextual contextual) {
      if (contextual instanceof CommonBean) {
         CommonBean bean = (CommonBean)contextual;
         this.passivationCapableContextuals.putIfAbsent(bean.getIdentifier(), contextual);
         return bean.getIdentifier();
      } else if (contextual instanceof PassivationCapable) {
         PassivationCapable passivationCapable = (PassivationCapable)contextual;
         String id = passivationCapable.getId();
         BeanIdentifier identifier = new StringBeanIdentifier(id);
         this.passivationCapableContextuals.putIfAbsent(identifier, contextual);
         return identifier;
      } else {
         BeanIdentifier id = (BeanIdentifier)this.contextuals.get(contextual);
         if (id != null) {
            return id;
         } else {
            synchronized(contextual) {
               BeanIdentifier id = (BeanIdentifier)this.contextuals.get(contextual);
               if (id == null) {
                  id = new StringBeanIdentifier(GENERATED_ID_PREFIX + this.idGenerator.incrementAndGet());
                  this.contextuals.put(contextual, id);
                  this.contextualsInverse.put(id, contextual);
               }

               return (BeanIdentifier)id;
            }
         }
      }
   }

   public SerializableContextual getSerializableContextual(Contextual contextual) {
      return contextual instanceof SerializableContextual ? (SerializableContextual)Reflections.cast(contextual) : SerializableContextualFactory.create(this.contextId, (Contextual)Reflections.cast(contextual), this, this.beanIdentifierIndex);
   }

   public SerializableContextualInstance getSerializableContextualInstance(Contextual contextual, Object instance, CreationalContext creationalContext) {
      return new SerializableContextualInstanceImpl((Contextual)Reflections.cast(contextual), instance, creationalContext, this);
   }

   public void cleanup() {
      this.contextuals.clear();
      this.contextualsInverse.clear();
      this.passivationCapableContextuals.clear();
   }

   public void removeAll(Iterable removable) {
      Iterator var2 = removable.iterator();

      while(var2.hasNext()) {
         Bean bean = (Bean)var2.next();
         BeanIdentifier beanIdentifier = (BeanIdentifier)this.contextuals.remove(bean);
         if (beanIdentifier == null && bean instanceof RIBean) {
            beanIdentifier = ((RIBean)bean).getIdentifier();
         }

         if (beanIdentifier != null) {
            this.contextualsInverse.remove(beanIdentifier);
            this.passivationCapableContextuals.remove(beanIdentifier);
         }
      }

   }
}
