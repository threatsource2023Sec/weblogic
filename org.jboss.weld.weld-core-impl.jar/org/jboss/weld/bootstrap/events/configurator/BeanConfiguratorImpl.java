package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.bean.WeldBean;
import org.jboss.weld.bootstrap.BeanDeploymentFinder;
import org.jboss.weld.bootstrap.event.WeldBeanConfigurator;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.ForwardingWeldInstance;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.bean.ForwardingBeanAttributes;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class BeanConfiguratorImpl implements WeldBeanConfigurator, Configurator {
   private final BeanManagerImpl beanManager;
   private Class beanClass;
   private final Set injectionPoints;
   private final BeanAttributesConfiguratorImpl attributes;
   private String id;
   private CreateCallback createCallback;
   private DestroyCallback destroyCallback;
   private Integer priority = null;

   public BeanConfiguratorImpl(Class defaultBeanClass, BeanDeploymentFinder beanDeploymentFinder) {
      this.beanClass = defaultBeanClass;
      this.injectionPoints = new HashSet();
      this.beanManager = beanDeploymentFinder.getOrCreateBeanDeployment(this.beanClass).getBeanManager();
      this.attributes = new BeanAttributesConfiguratorImpl(this.beanManager);
   }

   public WeldBeanConfigurator priority(int priority) {
      this.priority = priority;
      return this;
   }

   public WeldBeanConfigurator beanClass(Class beanClass) {
      Preconditions.checkArgumentNotNull(beanClass);
      this.beanClass = beanClass;
      return this;
   }

   public WeldBeanConfigurator addInjectionPoint(InjectionPoint injectionPoint) {
      Preconditions.checkArgumentNotNull(injectionPoint);
      this.injectionPoints.add(injectionPoint);
      return this;
   }

   public WeldBeanConfigurator addInjectionPoints(InjectionPoint... injectionPoints) {
      Preconditions.checkArgumentNotNull(injectionPoints);
      Collections.addAll(this.injectionPoints, injectionPoints);
      return this;
   }

   public WeldBeanConfigurator addInjectionPoints(Set injectionPoints) {
      Preconditions.checkArgumentNotNull(injectionPoints);
      this.injectionPoints.addAll(injectionPoints);
      return this;
   }

   public WeldBeanConfigurator injectionPoints(InjectionPoint... injectionPoints) {
      this.injectionPoints.clear();
      return this.addInjectionPoints(injectionPoints);
   }

   public WeldBeanConfigurator injectionPoints(Set injectionPoints) {
      this.injectionPoints.clear();
      return this.addInjectionPoints(injectionPoints);
   }

   public WeldBeanConfigurator id(String id) {
      Preconditions.checkArgumentNotNull(id);
      this.id = id;
      return this;
   }

   public WeldBeanConfigurator createWith(Function callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.createCallback = (CreateCallback)Reflections.cast(BeanConfiguratorImpl.CreateCallback.fromCreateWith(callback));
      return (WeldBeanConfigurator)Reflections.cast(this);
   }

   public WeldBeanConfigurator produceWith(Function callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.createCallback = (CreateCallback)Reflections.cast(BeanConfiguratorImpl.CreateCallback.fromProduceWith(callback));
      return (WeldBeanConfigurator)Reflections.cast(this);
   }

   public WeldBeanConfigurator destroyWith(BiConsumer callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.destroyCallback = BeanConfiguratorImpl.DestroyCallback.fromDestroy(callback);
      return this;
   }

   public WeldBeanConfigurator disposeWith(BiConsumer callback) {
      Preconditions.checkArgumentNotNull(callback);
      this.destroyCallback = BeanConfiguratorImpl.DestroyCallback.fromDispose(callback);
      return this;
   }

   public WeldBeanConfigurator read(AnnotatedType type) {
      Preconditions.checkArgumentNotNull(type);
      InjectionTarget injectionTarget = (InjectionTarget)Reflections.cast(this.beanManager.getInjectionTargetFactory(type).createInjectionTarget((Bean)null));
      this.addInjectionPoints(injectionTarget.getInjectionPoints());
      this.createWith((c) -> {
         Object instance = injectionTarget.produce(c);
         injectionTarget.inject(instance, c);
         injectionTarget.postConstruct(instance);
         return instance;
      });
      this.destroyWith((i, c) -> {
         injectionTarget.preDestroy(i);
         c.release();
      });
      BeanAttributes beanAttributes = this.beanManager.createBeanAttributes(type);
      this.read(beanAttributes);
      return (WeldBeanConfigurator)Reflections.cast(this);
   }

   public WeldBeanConfigurator read(BeanAttributes beanAttributes) {
      Preconditions.checkArgumentNotNull(beanAttributes);
      this.attributes.read(beanAttributes);
      return this;
   }

   public WeldBeanConfigurator addType(Type type) {
      Preconditions.checkArgumentNotNull(type);
      this.attributes.addType(type);
      return this;
   }

   public WeldBeanConfigurator addType(TypeLiteral typeLiteral) {
      Preconditions.checkArgumentNotNull(typeLiteral);
      this.attributes.addType(typeLiteral.getType());
      return this;
   }

   public WeldBeanConfigurator addTypes(Type... types) {
      Preconditions.checkArgumentNotNull(types);
      this.attributes.addTypes(types);
      return this;
   }

   public WeldBeanConfigurator addTypes(Set types) {
      Preconditions.checkArgumentNotNull(types);
      this.attributes.addTypes(types);
      return this;
   }

   public WeldBeanConfigurator addTransitiveTypeClosure(Type type) {
      Preconditions.checkArgumentNotNull(type);
      this.attributes.addTransitiveTypeClosure(type);
      return this;
   }

   public WeldBeanConfigurator types(Type... types) {
      Preconditions.checkArgumentNotNull(types);
      this.attributes.types(types);
      return this;
   }

   public WeldBeanConfigurator types(Set types) {
      Preconditions.checkArgumentNotNull(types);
      this.attributes.types(types);
      return this;
   }

   public WeldBeanConfigurator scope(Class scope) {
      Preconditions.checkArgumentNotNull(scope);
      this.attributes.scope(scope);
      return this;
   }

   public WeldBeanConfigurator addQualifier(Annotation qualifier) {
      Preconditions.checkArgumentNotNull(qualifier);
      this.attributes.addQualifier(qualifier);
      return this;
   }

   public WeldBeanConfigurator addQualifiers(Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.attributes.addQualifiers(qualifiers);
      return this;
   }

   public WeldBeanConfigurator addQualifiers(Set qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.attributes.addQualifiers(qualifiers);
      return this;
   }

   public WeldBeanConfigurator qualifiers(Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.attributes.qualifiers(qualifiers);
      return this;
   }

   public WeldBeanConfigurator qualifiers(Set qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.attributes.qualifiers(qualifiers);
      return this;
   }

   public WeldBeanConfigurator addStereotype(Class stereotype) {
      Preconditions.checkArgumentNotNull(stereotype);
      this.attributes.addStereotype(stereotype);
      return this;
   }

   public WeldBeanConfigurator addStereotypes(Set stereotypes) {
      Preconditions.checkArgumentNotNull(stereotypes);
      this.attributes.addStereotypes(stereotypes);
      return this;
   }

   public WeldBeanConfigurator stereotypes(Set stereotypes) {
      Preconditions.checkArgumentNotNull(stereotypes);
      this.attributes.stereotypes(stereotypes);
      return this;
   }

   public WeldBeanConfigurator name(String name) {
      this.attributes.name(name);
      return this;
   }

   public WeldBeanConfigurator alternative(boolean alternative) {
      this.attributes.alternative(alternative);
      return this;
   }

   public Bean complete() {
      if (this.createCallback == null) {
         throw BeanLogger.LOG.noCallbackSpecifiedForCustomBean("bean [" + this.beanClass.toString() + ", with types: " + Formats.formatTypes(this.attributes.types) + ", and qualifiers: " + Formats.formatAnnotations((Iterable)this.attributes.qualifiers) + "]");
      } else {
         return new ImmutableBean(this);
      }
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   static class ImmutableBean extends ForwardingBeanAttributes implements WeldBean, PassivationCapable {
      private final String id;
      private final Integer priority;
      private final BeanManagerImpl beanManager;
      private final Class beanClass;
      private final BeanAttributes attributes;
      private final Set injectionPoints;
      private final CreateCallback createCallback;
      private final DestroyCallback destroyCallback;

      ImmutableBean(BeanConfiguratorImpl configurator) {
         this.beanManager = configurator.getBeanManager();
         this.beanClass = configurator.beanClass;
         this.attributes = configurator.attributes.complete();
         this.injectionPoints = ImmutableSet.copyOf(configurator.injectionPoints);
         this.createCallback = configurator.createCallback;
         this.destroyCallback = configurator.destroyCallback;
         this.priority = configurator.priority;
         if (configurator.id != null) {
            this.id = configurator.id;
         } else {
            this.id = BeanIdentifiers.forBuilderBean(this.attributes, this.beanClass);
         }

      }

      public Object create(CreationalContext creationalContext) {
         return this.createCallback.create(this, creationalContext, this.beanManager);
      }

      public void destroy(Object instance, CreationalContext creationalContext) {
         if (this.destroyCallback != null) {
            this.destroyCallback.destroy(instance, creationalContext, this.beanManager);
         }

      }

      public Class getBeanClass() {
         return this.beanClass;
      }

      public BeanIdentifier getIdentifier() {
         return new StringBeanIdentifier(this.id);
      }

      public Set getInjectionPoints() {
         return this.injectionPoints;
      }

      public boolean isNullable() {
         return false;
      }

      protected BeanAttributes attributes() {
         return this.attributes;
      }

      public String getId() {
         return this.id;
      }

      public Integer getPriority() {
         return this.priority;
      }

      public String toString() {
         return "Configurator Bean [" + this.getBeanClass().toString() + ", types: " + Formats.formatTypes(this.getTypes()) + ", qualifiers: " + Formats.formatAnnotations((Iterable)this.getQualifiers()) + "]";
      }
   }

   static final class DestroyCallback {
      private final BiConsumer destroy;
      private final BiConsumer dispose;

      static DestroyCallback fromDispose(BiConsumer callback) {
         return new DestroyCallback(callback, (BiConsumer)null);
      }

      static DestroyCallback fromDestroy(BiConsumer callback) {
         return new DestroyCallback((BiConsumer)null, callback);
      }

      public DestroyCallback(BiConsumer dispose, BiConsumer destroy) {
         this.destroy = destroy;
         this.dispose = dispose;
      }

      void destroy(Object instance, CreationalContext ctx, BeanManagerImpl beanManager) {
         if (this.dispose != null) {
            this.dispose.accept(instance, beanManager.getInstance(ctx));
         } else {
            this.destroy.accept(instance, ctx);
         }

      }
   }

   static class GuardedInstance extends ForwardingWeldInstance {
      private final Bean bean;
      private final WeldInstance delegate;

      public GuardedInstance(Bean bean, WeldInstance delegate) {
         this.bean = bean;
         this.delegate = delegate;
      }

      public WeldInstance delegate() {
         return this.delegate;
      }

      public WeldInstance select(Class subtype, Annotation... qualifiers) {
         return this.wrap(subtype, this.delegate.select(subtype, qualifiers));
      }

      public WeldInstance select(TypeLiteral subtype, Annotation... qualifiers) {
         return this.wrap(subtype.getType(), this.delegate.select(subtype, qualifiers));
      }

      public WeldInstance select(Annotation... qualifiers) {
         return this.wrap((Type)null, this.delegate.select(qualifiers));
      }

      public WeldInstance select(Type subtype, Annotation... qualifiers) {
         return this.wrap(subtype, this.delegate.select(subtype, qualifiers));
      }

      private WeldInstance wrap(Type subtype, WeldInstance delegate) {
         if (subtype != null && InjectionPoint.class.equals(subtype)) {
            throw BeanLogger.LOG.cannotInjectInjectionPointMetadataIntoNonDependent(this.bean);
         } else {
            return new GuardedInstance(this.bean, delegate);
         }
      }
   }

   static final class CreateCallback {
      private final Supplier simple;
      private final Function create;
      private final Function instance;

      static CreateCallback fromProduceWith(Function callback) {
         return new CreateCallback((Supplier)null, (Function)null, callback);
      }

      static CreateCallback fromProduceWith(Supplier callback) {
         return new CreateCallback(callback, (Function)null, (Function)null);
      }

      static CreateCallback fromCreateWith(Function callback) {
         return new CreateCallback((Supplier)null, callback, (Function)null);
      }

      CreateCallback(Supplier simple, Function create, Function instance) {
         this.simple = simple;
         this.create = create;
         this.instance = instance;
      }

      private Object create(Bean bean, CreationalContext ctx, BeanManagerImpl beanManager) {
         if (this.simple != null) {
            return this.simple.get();
         } else {
            return this.instance != null ? this.instance.apply(this.createInstance(bean, ctx, beanManager)) : this.create.apply(ctx);
         }
      }

      private Instance createInstance(Bean bean, CreationalContext ctx, BeanManagerImpl beanManager) {
         WeldInstance instance = beanManager.getInstance(ctx);
         return (Instance)(Dependent.class.equals(bean.getScope()) ? instance : new GuardedInstance(bean, instance));
      }
   }
}
