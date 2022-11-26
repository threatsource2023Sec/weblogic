package org.jboss.weld.contexts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.Container;
import org.jboss.weld.bean.ForwardingBean;
import org.jboss.weld.bean.WrappedContextual;
import org.jboss.weld.serialization.BeanIdentifierIndex;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.reflection.Reflections;

public class SerializableContextualFactory {
   private SerializableContextualFactory() {
   }

   public static SerializableContextual create(String contextId, Contextual contextual, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
      if (contextual instanceof Bean) {
         return (SerializableContextual)(contextual instanceof PassivationCapable ? new PassivationCapableSerializableBean(contextId, (Bean)Reflections.cast(contextual), contextualStore, beanIdentifierIndex) : new DefaultSerializableBean(contextId, (Bean)Reflections.cast(contextual), contextualStore, beanIdentifierIndex));
      } else {
         return (SerializableContextual)(contextual instanceof PassivationCapable ? new PassivationCapableSerializableContextual(contextId, contextual, contextualStore, beanIdentifierIndex) : new DefaultSerializableContextual(contextId, contextual, contextualStore, beanIdentifierIndex));
      }
   }

   private static class PassivationCapableSerializableBean extends AbstractSerializableBean implements PassivationCapable {
      private static final long serialVersionUID = 7458443513156329183L;

      public PassivationCapableSerializableBean(String contextId, Bean bean, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         super(contextId, bean, contextualStore, beanIdentifierIndex);
      }

      public String getId() {
         return ((PassivationCapable)this.get()).getId();
      }
   }

   private static class DefaultSerializableBean extends AbstractSerializableBean {
      private static final long serialVersionUID = -8901252027789701049L;

      public DefaultSerializableBean(String contextId, Bean bean, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         super(contextId, bean, contextualStore, beanIdentifierIndex);
      }
   }

   private static class PassivationCapableSerializableContextual extends AbstractSerializableContextual implements PassivationCapable {
      private static final long serialVersionUID = -2753893863961869301L;

      public PassivationCapableSerializableContextual(String contextId, Contextual contextual, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         super(contextId, contextual, contextualStore, beanIdentifierIndex);
      }

      public String getId() {
         return ((PassivationCapable)this.get()).getId();
      }
   }

   private static class DefaultSerializableContextual extends AbstractSerializableContextual {
      private static final long serialVersionUID = -5102624795925717767L;

      public DefaultSerializableContextual(String contextId, Contextual contextual, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         super(contextId, contextual, contextualStore, beanIdentifierIndex);
      }
   }

   private abstract static class AbstractSerializableContextual extends ForwardingContextual implements SerializableContextual, WrappedContextual {
      private static final long serialVersionUID = 107855630671709443L;
      private final SerializableContextualHolder holder;

      AbstractSerializableContextual(String contextId, Contextual contextual, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         this.holder = new SerializableContextualHolder(contextId, contextual, contextualStore, beanIdentifierIndex);
      }

      public Contextual delegate() {
         return this.get();
      }

      public Contextual get() {
         return this.holder.get();
      }

      public boolean equals(Object obj) {
         return obj instanceof AbstractSerializableContextual ? this.delegate().equals(((AbstractSerializableContextual)obj).get()) : this.delegate().equals(obj);
      }

      public int hashCode() {
         return this.delegate().hashCode();
      }
   }

   private abstract static class AbstractSerializableBean extends ForwardingBean implements SerializableContextual, WrappedContextual {
      private static final long serialVersionUID = 7594992948498685840L;
      private final SerializableContextualHolder holder;

      AbstractSerializableBean(String contextId, Bean bean, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         this.holder = new SerializableContextualHolder(contextId, bean, contextualStore, beanIdentifierIndex);
      }

      public Bean get() {
         return (Bean)this.holder.get();
      }

      public Bean delegate() {
         return this.get();
      }

      public boolean equals(Object obj) {
         return obj instanceof AbstractSerializableBean ? this.delegate().equals(((AbstractSerializableBean)obj).get()) : this.delegate().equals(obj);
      }

      public int hashCode() {
         return this.delegate().hashCode();
      }
   }

   private static final class SerializableContextualHolder implements Serializable {
      private static final long serialVersionUID = 46941665668478370L;
      @SuppressFBWarnings(
         value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
         justification = "A cache which is lazily loaded"
      )
      private transient Contextual cached;
      private final Contextual serializable;
      private final BeanIdentifier identifier;
      private final Integer identifierIndex;
      private final String contextId;
      private transient ContextualStore cachedContextualStore;
      private transient BeanIdentifierIndex beanIdentifierIndex;

      SerializableContextualHolder(String contextId, Contextual contextual, ContextualStore contextualStore, BeanIdentifierIndex beanIdentifierIndex) {
         this.contextId = contextId;
         this.cachedContextualStore = contextualStore;
         if (contextual instanceof Serializable) {
            this.serializable = contextual;
            this.identifier = null;
            this.identifierIndex = null;
         } else {
            this.serializable = null;
            BeanIdentifier beanIdentifier = this.getId(contextual, contextualStore);
            Integer idx = null;
            if (beanIdentifierIndex != null && beanIdentifierIndex.isBuilt()) {
               idx = beanIdentifierIndex.getIndex(beanIdentifier);
            }

            if (idx != null) {
               this.identifierIndex = idx;
               this.identifier = null;
            } else {
               this.identifierIndex = null;
               this.identifier = beanIdentifier;
            }
         }

         this.cached = contextual;
      }

      protected BeanIdentifier getId(Contextual contextual, ContextualStore contextualStore) {
         return Beans.getIdentifier(contextual, contextualStore);
      }

      protected ContextualStore getContextualStore() {
         if (this.cachedContextualStore == null) {
            this.cachedContextualStore = (ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class);
         }

         return this.cachedContextualStore;
      }

      protected BeanIdentifierIndex getBeanIdentifierIndex() {
         if (this.beanIdentifierIndex == null) {
            this.beanIdentifierIndex = (BeanIdentifierIndex)Container.instance(this.contextId).services().get(BeanIdentifierIndex.class);
         }

         return this.beanIdentifierIndex;
      }

      protected Contextual get() {
         if (this.cached == null) {
            this.loadContextual();
         }

         return this.cached;
      }

      private void loadContextual() {
         if (this.serializable != null) {
            this.cached = this.serializable;
         } else if (this.identifierIndex != null) {
            this.cached = this.getContextualStore().getContextual(this.getBeanIdentifierIndex().getIdentifier(this.identifierIndex));
         } else if (this.identifier != null) {
            this.cached = this.getContextualStore().getContextual(this.identifier);
         }

         if (this.cached == null) {
            throw new IllegalStateException("Error restoring serialized contextual with id " + this.identifier);
         }
      }
   }
}
