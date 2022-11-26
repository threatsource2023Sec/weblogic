package org.jboss.weld.contexts;

import java.util.Collection;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.context.WeldAlterableContext;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.ForwardingContext;

public class PassivatingContextWrapper {
   private PassivatingContextWrapper() {
   }

   public static Context wrap(Context context, ContextualStore store) {
      if (context instanceof WeldAlterableContext) {
         return new WeldAlterableContextWrapper((WeldAlterableContext)context, store);
      } else {
         return (Context)(context instanceof AlterableContext ? new AlterableContextWrapper((AlterableContext)context, store) : new ContextWrapper(context, store));
      }
   }

   public static Context unwrap(Context context) {
      if (context instanceof AbstractPassivatingContextWrapper) {
         AbstractPassivatingContextWrapper wrapper = (AbstractPassivatingContextWrapper)context;
         return wrapper.delegate();
      } else {
         return context;
      }
   }

   private static class WeldAlterableContextWrapper extends AbstractPassivatingContextWrapper implements WeldAlterableContext {
      public WeldAlterableContextWrapper(WeldAlterableContext context, ContextualStore store) {
         super(context, store);
      }

      public void destroy(Contextual contextual) {
         Contextual contextual = this.store.getSerializableContextual(contextual);
         ((WeldAlterableContext)this.delegate()).destroy(contextual);
      }

      public Collection getAllContextualInstances() {
         return ((WeldAlterableContext)this.delegate()).getAllContextualInstances();
      }

      public void clearAndSet(Collection setOfInstances) {
         ((WeldAlterableContext)this.delegate()).clearAndSet(setOfInstances);
      }
   }

   private static class AlterableContextWrapper extends AbstractPassivatingContextWrapper implements AlterableContext {
      public AlterableContextWrapper(AlterableContext context, ContextualStore store) {
         super(context, store);
      }

      public void destroy(Contextual contextual) {
         Contextual contextual = this.store.getSerializableContextual(contextual);
         ((AlterableContext)this.delegate()).destroy(contextual);
      }
   }

   private static class ContextWrapper extends AbstractPassivatingContextWrapper {
      public ContextWrapper(Context context, ContextualStore store) {
         super(context, store);
      }
   }

   private abstract static class AbstractPassivatingContextWrapper extends ForwardingContext {
      private final Context context;
      protected final ContextualStore store;

      public AbstractPassivatingContextWrapper(Context context, ContextualStore store) {
         this.context = context;
         this.store = store;
      }

      public Object get(Contextual contextual) {
         Contextual contextual = this.store.getSerializableContextual(contextual);
         return this.context.get(contextual);
      }

      public Object get(Contextual contextual, CreationalContext creationalContext) {
         Contextual contextual = this.store.getSerializableContextual(contextual);
         return this.context.get(contextual, creationalContext);
      }

      protected Context delegate() {
         return this.context;
      }
   }
}
