package org.jboss.weld.contexts;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.serialization.spi.helpers.SerializableContextual;
import org.jboss.weld.serialization.spi.helpers.SerializableContextualInstance;

public class SerializableContextualInstanceImpl implements SerializableContextualInstance {
   private static final long serialVersionUID = -6366271037267396256L;
   private final SerializableContextual contextual;
   private final Object instance;
   private final CreationalContext creationalContext;

   public SerializableContextualInstanceImpl(Contextual contextual, Object instance, CreationalContext creationalContext, ContextualStore contextualStore) {
      this.contextual = contextualStore.getSerializableContextual(contextual);
      this.instance = instance;
      this.creationalContext = creationalContext;
   }

   public SerializableContextualInstanceImpl(SerializableContextual contextual, Object instance, CreationalContext creationalContext) {
      this.contextual = contextual;
      this.instance = instance;
      this.creationalContext = creationalContext;
   }

   public SerializableContextual getContextual() {
      return this.contextual;
   }

   public Object getInstance() {
      return this.instance;
   }

   public CreationalContext getCreationalContext() {
      return this.creationalContext;
   }

   public String toString() {
      return "Bean: " + this.contextual + "; Instance: " + this.instance + "; CreationalContext: " + this.creationalContext;
   }
}
