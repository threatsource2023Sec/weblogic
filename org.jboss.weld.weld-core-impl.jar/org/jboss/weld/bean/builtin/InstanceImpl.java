package org.jboss.weld.bean.builtin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.bean.proxy.ProxyMethodHandler;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.resolution.ResolvableBuilder;
import org.jboss.weld.resolution.TypeSafeBeanResolver;
import org.jboss.weld.util.AnnotationApiAbstraction;
import org.jboss.weld.util.InjectionPoints;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_NO_SUITABLE_CONSTRUCTOR", "SE_BAD_FIELD"},
   justification = "Uses SerializationProxy"
)
public class InstanceImpl extends AbstractFacade implements WeldInstance, Serializable {
   private static final long serialVersionUID = -376721889693284887L;
   private final transient Set allBeans;
   private final transient Bean bean;
   private final transient CurrentInjectionPoint currentInjectionPoint;
   private final transient InjectionPoint ip;
   private final transient EjbSupport ejbSupport;

   public static Instance of(InjectionPoint injectionPoint, CreationalContext creationalContext, BeanManagerImpl beanManager) {
      return new InstanceImpl(injectionPoint, creationalContext, beanManager);
   }

   private InstanceImpl(InjectionPoint injectionPoint, CreationalContext creationalContext, BeanManagerImpl beanManager) {
      super(injectionPoint, creationalContext, beanManager);
      if (injectionPoint.getQualifiers().isEmpty() && Object.class.equals(this.getType())) {
         this.allBeans = null;
         this.bean = null;
      } else {
         this.allBeans = this.resolveBeans();
         if (this.allBeans.size() == 1) {
            this.bean = (Bean)this.allBeans.iterator().next();
         } else {
            this.bean = null;
         }
      }

      this.currentInjectionPoint = (CurrentInjectionPoint)beanManager.getServices().getRequired(CurrentInjectionPoint.class);
      this.ip = new DynamicLookupInjectionPoint(this.getInjectionPoint(), this.getType(), this.getQualifiers());
      this.ejbSupport = (EjbSupport)beanManager.getServices().get(EjbSupport.class);
   }

   public Object get() {
      this.checkBeanResolved();
      return this.getBeanInstance(this.bean);
   }

   public String toString() {
      return Formats.formatAnnotations((Iterable)this.getQualifiers()) + " Instance<" + Formats.formatType(this.getType()) + ">";
   }

   public Iterator iterator() {
      return new InstanceImplIterator(this.allBeans());
   }

   public boolean isAmbiguous() {
      return this.allBeans().size() > 1;
   }

   public boolean isUnsatisfied() {
      return this.allBeans().isEmpty();
   }

   public WeldInstance select(Annotation... qualifiers) {
      return this.selectInstance(this.getType(), qualifiers);
   }

   public WeldInstance select(Class subtype, Annotation... qualifiers) {
      return this.selectInstance(subtype, qualifiers);
   }

   public WeldInstance select(TypeLiteral subtype, Annotation... qualifiers) {
      return this.selectInstance(subtype.getType(), qualifiers);
   }

   public WeldInstance select(Type subtype, Annotation... qualifiers) {
      if (!this.getType().equals(Object.class)) {
         throw BeanLogger.LOG.selectByTypeOnlyWorksOnObject();
      } else {
         return this.selectInstance(subtype, qualifiers);
      }
   }

   private WeldInstance selectInstance(Type subtype, Annotation[] newQualifiers) {
      InjectionPoint modifiedInjectionPoint = new FacadeInjectionPoint(this.getBeanManager(), this.getInjectionPoint(), Instance.class, subtype, this.getQualifiers(), newQualifiers);
      return new InstanceImpl(modifiedInjectionPoint, this.getCreationalContext(), this.getBeanManager());
   }

   public void destroy(Object instance) {
      Preconditions.checkNotNull(instance);
      if (instance instanceof ProxyObject) {
         ProxyObject proxy = (ProxyObject)instance;
         if (proxy.weld_getHandler() instanceof ProxyMethodHandler) {
            ProxyMethodHandler handler = (ProxyMethodHandler)proxy.weld_getHandler();
            Bean bean = handler.getBean();
            if (this.isSessionBeanProxy(instance) && Dependent.class.equals(bean.getScope())) {
               this.destroyDependentInstance(instance);
               return;
            }

            Context context = this.getBeanManager().getContext(bean.getScope());
            if (context instanceof AlterableContext) {
               AlterableContext alterableContext = (AlterableContext)context;
               alterableContext.destroy(bean);
               return;
            }

            throw BeanLogger.LOG.destroyUnsupported(context);
         }
      }

      this.destroyDependentInstance(instance);
   }

   public WeldInstance.Handler getHandler() {
      this.checkBeanResolved();
      return new HandlerImpl(() -> {
         return this.getBeanInstance(this.bean);
      }, this, this.bean);
   }

   public boolean isResolvable() {
      return this.allBeans().size() == 1;
   }

   public Iterable handlers() {
      return new Iterable() {
         public Iterator iterator() {
            return InstanceImpl.this.new HandlerIterator(InstanceImpl.this.allBeans());
         }
      };
   }

   public Comparator getPriorityComparator() {
      return new PriorityComparator((AnnotationApiAbstraction)this.getBeanManager().getServices().get(AnnotationApiAbstraction.class));
   }

   private boolean isSessionBeanProxy(Object instance) {
      return this.ejbSupport != null ? this.ejbSupport.isSessionBeanProxy(instance) : false;
   }

   private void destroyDependentInstance(Object instance) {
      CreationalContext ctx = this.getCreationalContext();
      if (ctx instanceof WeldCreationalContext) {
         WeldCreationalContext weldCtx = (WeldCreationalContext)Reflections.cast(ctx);
         weldCtx.destroyDependentInstance(instance);
      }

   }

   private void checkBeanResolved() {
      if (this.bean == null) {
         if (this.isUnsatisfied()) {
            throw BeanManagerLogger.LOG.injectionPointHasUnsatisfiedDependencies(Formats.formatAnnotations((Iterable)this.ip.getQualifiers()), Formats.formatInjectionPointType(this.ip.getType()), InjectionPoints.getUnsatisfiedDependenciesAdditionalInfo(this.ip, this.getBeanManager()));
         } else {
            throw BeanManagerLogger.LOG.injectionPointHasAmbiguousDependencies(Formats.formatAnnotations((Iterable)this.ip.getQualifiers()), Formats.formatInjectionPointType(this.ip.getType()), WeldCollections.toMultiRowString(this.allBeans()));
         }
      }
   }

   private Object getBeanInstance(Bean bean) {
      ThreadLocalStack.ThreadLocalStackReference stack = this.currentInjectionPoint.pushConditionally(this.ip, this.isRegisterableInjectionPoint());

      Object var3;
      try {
         var3 = Reflections.cast(this.getBeanManager().getReference(bean, this.getType(), this.getCreationalContext(), false));
      } finally {
         stack.pop();
      }

      return var3;
   }

   private boolean isRegisterableInjectionPoint() {
      return !this.getType().equals(InjectionPoint.class);
   }

   private Set allBeans() {
      return this.allBeans == null ? this.resolveBeans() : this.allBeans;
   }

   private Set resolveBeans() {
      Resolvable resolvable = (new ResolvableBuilder(this.getType(), this.getBeanManager())).addQualifiers((Collection)this.getQualifiers()).setDeclaringBean(this.getInjectionPoint().getBean()).create();
      TypeSafeBeanResolver beanResolver = this.getBeanManager().getBeanResolver();
      return beanResolver.resolve((Set)beanResolver.resolve(resolvable, Reflections.isCacheable((Collection)this.getQualifiers())));
   }

   private Object writeReplace() throws ObjectStreamException {
      return new SerializationProxy(this);
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw BeanLogger.LOG.serializationProxyRequired();
   }

   private static class HandlerImpl implements WeldInstance.Handler {
      private final LazyValueHolder value;
      private final Bean bean;
      private final WeakReference instance;
      private final AtomicBoolean isDestroyed;

      HandlerImpl(Supplier supplier, WeldInstance instance, Bean bean) {
         this.value = LazyValueHolder.forSupplier(supplier);
         this.bean = bean;
         this.instance = new WeakReference(instance);
         this.isDestroyed = new AtomicBoolean(false);
      }

      public Object get() {
         if (!this.value.isAvailable() && this.instance.get() == null) {
            throw BeanLogger.LOG.cannotObtainHandlerContextualReference(this);
         } else {
            return this.value.get();
         }
      }

      public Bean getBean() {
         return this.bean;
      }

      public void destroy() {
         WeldInstance ref = (WeldInstance)this.instance.get();
         if (ref == null) {
            BeanLogger.LOG.cannotDestroyHandlerContextualReference(this);
         }

         if (this.value.isAvailable() && this.isDestroyed.compareAndSet(false, true)) {
            ref.destroy(this.value.get());
         }

      }

      public void close() {
         this.destroy();
      }

      public String toString() {
         return "HandlerImpl [bean=" + this.bean + "]";
      }
   }

   class HandlerIterator extends BeanIterator {
      private HandlerIterator(Set beans) {
         super(beans, null);
      }

      public WeldInstance.Handler next() {
         Bean bean = (Bean)this.delegate.next();
         return new HandlerImpl(() -> {
            return InstanceImpl.this.getBeanInstance(bean);
         }, InstanceImpl.this, bean);
      }

      // $FF: synthetic method
      HandlerIterator(Set x1, Object x2) {
         this(x1);
      }
   }

   class InstanceImplIterator extends BeanIterator {
      private InstanceImplIterator(Set beans) {
         super(beans, null);
      }

      public Object next() {
         return InstanceImpl.this.getBeanInstance((Bean)this.delegate.next());
      }

      // $FF: synthetic method
      InstanceImplIterator(Set x1, Object x2) {
         this(x1);
      }
   }

   abstract class BeanIterator implements Iterator {
      protected final Iterator delegate;

      private BeanIterator(Set beans) {
         this.delegate = beans.iterator();
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public void remove() {
         throw BeanLogger.LOG.instanceIteratorRemoveUnsupported();
      }

      // $FF: synthetic method
      BeanIterator(Set x1, Object x2) {
         this(x1);
      }
   }

   private static class SerializationProxy extends AbstractFacade.AbstractFacadeSerializationProxy {
      private static final long serialVersionUID = 9181171328831559650L;

      public SerializationProxy(InstanceImpl instance) {
         super(instance);
      }

      private Object readResolve() throws ObjectStreamException {
         return InstanceImpl.of(this.getInjectionPoint(), this.getCreationalContext(), this.getBeanManager());
      }
   }
}
