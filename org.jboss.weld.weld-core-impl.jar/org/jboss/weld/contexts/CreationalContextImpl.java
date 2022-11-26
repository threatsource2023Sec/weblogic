package org.jboss.weld.contexts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.construction.api.AroundConstructCallback;
import org.jboss.weld.context.api.ContextualInstance;
import org.jboss.weld.injection.spi.ResourceReference;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class CreationalContextImpl implements CreationalContext, WeldCreationalContext, Serializable {
   private static final long serialVersionUID = 7375854583908262422L;
   private static final SerializationProxy SERIALIZATION_PROXY = new SerializationProxy();
   @SuppressFBWarnings(
      value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
      justification = "Not needed after initial creation"
   )
   private transient Map incompleteInstances;
   @SuppressFBWarnings(
      value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
      justification = "Not needed after initial creation"
   )
   private final transient Contextual contextual;
   private final List dependentInstances;
   private final List parentDependentInstances;
   private final CreationalContextImpl parentCreationalContext;
   private transient List resourceReferences;
   private transient boolean constructorInterceptionSuppressed;
   private transient List aroundConstructCallbacks;
   private transient InterceptionContext aroundConstructInterceptionContext;

   public CreationalContextImpl(Contextual contextual) {
      this(contextual, (Map)null, Collections.synchronizedList(new ArrayList()), (CreationalContextImpl)null);
   }

   private CreationalContextImpl(Contextual contextual, Map incompleteInstances, List parentDependentInstancesStore, CreationalContextImpl parentCreationalContext) {
      this.incompleteInstances = incompleteInstances;
      this.contextual = contextual;
      this.dependentInstances = Collections.synchronizedList(new ArrayList());
      this.parentDependentInstances = parentDependentInstancesStore;
      this.parentCreationalContext = parentCreationalContext;
      this.constructorInterceptionSuppressed = false;
   }

   private CreationalContextImpl() {
      this.contextual = null;
      this.parentCreationalContext = null;
      this.dependentInstances = Collections.synchronizedList(new ArrayList());
      this.parentDependentInstances = Collections.synchronizedList(new ArrayList());
   }

   public void push(Object incompleteInstance) {
      if (this.incompleteInstances == null) {
         this.incompleteInstances = new HashMap();
      }

      this.incompleteInstances.put(this.contextual, incompleteInstance);
   }

   public CreationalContextImpl getCreationalContext(Contextual contextual) {
      return new CreationalContextImpl(contextual, this.incompleteInstances, this.dependentInstances, this);
   }

   public CreationalContextImpl getProducerReceiverCreationalContext(Contextual contextual) {
      return new CreationalContextImpl(contextual, this.incompleteInstances != null ? new HashMap(this.incompleteInstances) : null, Collections.synchronizedList(new ArrayList()), (CreationalContextImpl)null);
   }

   public Object getIncompleteInstance(Contextual bean) {
      return this.incompleteInstances == null ? null : Reflections.cast(this.incompleteInstances.get(bean));
   }

   public boolean containsIncompleteInstance(Contextual bean) {
      return this.incompleteInstances != null && this.incompleteInstances.containsKey(bean);
   }

   public void addDependentInstance(ContextualInstance contextualInstance) {
      this.parentDependentInstances.add(contextualInstance);
   }

   public void release() {
      this.release((Contextual)null, (Object)null);
   }

   public void release(Contextual contextual, Object instance) {
      synchronized(this.dependentInstances) {
         Iterator var4 = this.dependentInstances.iterator();

         while(true) {
            if (!var4.hasNext()) {
               break;
            }

            ContextualInstance dependentInstance = (ContextualInstance)var4.next();
            if (contextual == null || !dependentInstance.getContextual().equals(contextual)) {
               destroy(dependentInstance);
            }
         }
      }

      if (this.resourceReferences != null) {
         Iterator var3 = this.resourceReferences.iterator();

         while(var3.hasNext()) {
            ResourceReference reference = (ResourceReference)var3.next();
            reference.release();
         }
      }

   }

   private static void destroy(ContextualInstance beanInstance) {
      beanInstance.getContextual().destroy(beanInstance.getInstance(), beanInstance.getCreationalContext());
   }

   public CreationalContextImpl getParentCreationalContext() {
      return this.parentCreationalContext;
   }

   public List getDependentInstances() {
      return WeldCollections.immutableListView(this.dependentInstances);
   }

   protected Object writeReplace() throws ObjectStreamException {
      synchronized(this.dependentInstances) {
         Iterator iterator = this.dependentInstances.iterator();

         while(iterator.hasNext()) {
            ContextualInstance instance = (ContextualInstance)iterator.next();
            if (!(instance.getInstance() instanceof Serializable)) {
               destroy(instance);
               iterator.remove();
            }
         }

         return this.parentCreationalContext == null && this.dependentInstances.isEmpty() && (this.parentDependentInstances == null || this.parentDependentInstances.isEmpty()) ? SERIALIZATION_PROXY : this;
      }
   }

   public void addDependentResourceReference(ResourceReference resourceReference) {
      if (this.resourceReferences == null) {
         this.resourceReferences = new ArrayList();
      }

      this.resourceReferences.add(resourceReference);
   }

   public boolean destroyDependentInstance(Object instance) {
      synchronized(this.dependentInstances) {
         Iterator iterator = this.dependentInstances.iterator();

         ContextualInstance contextualInstance;
         do {
            if (!iterator.hasNext()) {
               return false;
            }

            contextualInstance = (ContextualInstance)iterator.next();
         } while(contextualInstance.getInstance() != instance);

         iterator.remove();
         destroy(contextualInstance);
         return true;
      }
   }

   public Contextual getContextual() {
      return this.contextual;
   }

   public List getAroundConstructCallbacks() {
      return this.aroundConstructCallbacks == null ? Collections.emptyList() : this.aroundConstructCallbacks;
   }

   public void setConstructorInterceptionSuppressed(boolean value) {
      this.constructorInterceptionSuppressed = value;
   }

   public boolean isConstructorInterceptionSuppressed() {
      return this.constructorInterceptionSuppressed;
   }

   public void registerAroundConstructCallback(AroundConstructCallback callback) {
      if (this.aroundConstructCallbacks == null) {
         this.aroundConstructCallbacks = new LinkedList();
      }

      this.aroundConstructCallbacks.add(callback);
   }

   public InterceptionContext getAroundConstructInterceptionContext() {
      return this.aroundConstructInterceptionContext;
   }

   public void setAroundConstructInterceptionContext(InterceptionContext aroundConstructInterceptionContext) {
      this.aroundConstructInterceptionContext = aroundConstructInterceptionContext;
   }

   // $FF: synthetic method
   CreationalContextImpl(Object x0) {
      this();
   }

   private static class SerializationProxy implements Serializable {
      private static final long serialVersionUID = 5261112077771498097L;

      private SerializationProxy() {
      }

      private Object readResolve() throws ObjectStreamException {
         return new CreationalContextImpl();
      }

      // $FF: synthetic method
      SerializationProxy(Object x0) {
         this();
      }
   }
}
