package org.jboss.weld.bootstrap.events;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.DefinitionException;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.literal.InterceptorBindingTypeLiteral;
import org.jboss.weld.literal.NormalScopeLiteral;
import org.jboss.weld.literal.QualifierLiteral;
import org.jboss.weld.literal.ScopeLiteral;
import org.jboss.weld.literal.StereotypeLiteral;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.util.annotated.AnnotatedTypeWrapper;

public class BeforeBeanDiscoveryImpl extends AbstractAnnotatedTypeRegisteringEvent implements BeforeBeanDiscovery {
   protected final List additionalQualifiers = new LinkedList();
   protected final List additionalInterceptorBindings = new LinkedList();

   public static void fire(BeanManagerImpl beanManager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      BeforeBeanDiscoveryImpl event = new BeforeBeanDiscoveryImpl(beanManager, deployment, bdaMapping, contexts);
      event.fire();
      event.finish();
   }

   protected BeforeBeanDiscoveryImpl(BeanManagerImpl beanManager, Deployment deployment, BeanDeploymentArchiveMapping bdaMapping, Collection contexts) {
      super(beanManager, BeforeBeanDiscovery.class, bdaMapping, deployment, contexts);
   }

   public void addQualifier(Class bindingType) {
      this.checkWithinObserverNotification();
      this.getTypeStore().add(bindingType, QualifierLiteral.INSTANCE);
      ((ClassTransformer)this.getBeanManager().getServices().get(ClassTransformer.class)).clearAnnotationData(bindingType);
      ((MetaAnnotationStore)this.getBeanManager().getServices().get(MetaAnnotationStore.class)).clearAnnotationData(bindingType);
      BootstrapLogger.LOG.addQualifierCalled(this.getReceiver(), bindingType);
   }

   public void addInterceptorBinding(Class bindingType, Annotation... bindingTypeDef) {
      this.checkWithinObserverNotification();
      TypeStore typeStore = this.getTypeStore();
      typeStore.add(bindingType, InterceptorBindingTypeLiteral.INSTANCE);
      Annotation[] var4 = bindingTypeDef;
      int var5 = bindingTypeDef.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation a = var4[var6];
         typeStore.add(bindingType, a);
      }

      ((ClassTransformer)this.getBeanManager().getServices().get(ClassTransformer.class)).clearAnnotationData(bindingType);
      ((MetaAnnotationStore)this.getBeanManager().getServices().get(MetaAnnotationStore.class)).clearAnnotationData(bindingType);
      BootstrapLogger.LOG.addInterceptorBindingCalled(this.getReceiver(), bindingType);
   }

   public void addScope(Class scopeType, boolean normal, boolean passivating) {
      this.checkWithinObserverNotification();
      if (normal) {
         this.getTypeStore().add(scopeType, new NormalScopeLiteral(passivating));
      } else {
         if (passivating) {
            throw BootstrapLogger.LOG.passivatingNonNormalScopeIllegal(scopeType);
         }

         this.getTypeStore().add(scopeType, ScopeLiteral.INSTANCE);
      }

      ((ClassTransformer)this.getBeanManager().getServices().get(ClassTransformer.class)).clearAnnotationData(scopeType);
      ((MetaAnnotationStore)this.getBeanManager().getServices().get(MetaAnnotationStore.class)).clearAnnotationData(scopeType);
      ((ReflectionCache)this.getBeanManager().getServices().get(ReflectionCache.class)).cleanup();
      BootstrapLogger.LOG.addScopeCalled(this.getReceiver(), scopeType);
   }

   public void addStereotype(Class stereotype, Annotation... stereotypeDef) {
      this.checkWithinObserverNotification();
      TypeStore typeStore = this.getTypeStore();
      typeStore.add(stereotype, StereotypeLiteral.INSTANCE);
      Annotation[] var4 = stereotypeDef;
      int var5 = stereotypeDef.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation a = var4[var6];
         typeStore.add(stereotype, a);
      }

      ((ClassTransformer)this.getBeanManager().getServices().get(ClassTransformer.class)).clearAnnotationData(stereotype);
      ((MetaAnnotationStore)this.getBeanManager().getServices().get(MetaAnnotationStore.class)).clearAnnotationData(stereotype);
      BootstrapLogger.LOG.addStereoTypeCalled(this.getReceiver(), stereotype);
   }

   public void addAnnotatedType(AnnotatedType source) {
      this.checkWithinObserverNotification();
      BootstrapLogger.LOG.deprecatedAddAnnotatedTypeMethodUsed(source.getJavaClass());
      this.addAnnotatedType((AnnotatedType)source, (String)null);
   }

   public void addAnnotatedType(AnnotatedType type, String id) {
      this.checkWithinObserverNotification();
      this.addSyntheticAnnotatedType(type, id);
      BootstrapLogger.LOG.addAnnotatedTypeCalledInBBD(this.getReceiver(), type);
   }

   public AnnotatedTypeConfigurator addAnnotatedType(Class type, String id) {
      this.checkWithinObserverNotification();
      AnnotatedTypeConfiguratorImpl configurator = new AnnotatedTypeConfiguratorImpl(this.getBeanManager().createAnnotatedType(type));
      this.additionalAnnotatedTypes.add(new AbstractAnnotatedTypeRegisteringEvent.AnnotatedTypeRegistration(configurator, id));
      BootstrapLogger.LOG.addAnnotatedTypeCalledInBBD(this.getReceiver(), type);
      return configurator;
   }

   public void addQualifier(AnnotatedType qualifier) {
      this.checkWithinObserverNotification();
      this.addSyntheticAnnotation(qualifier, QualifierLiteral.INSTANCE);
      BootstrapLogger.LOG.addQualifierCalled(this.getReceiver(), qualifier);
   }

   public void addInterceptorBinding(AnnotatedType bindingType) {
      this.checkWithinObserverNotification();
      this.addSyntheticAnnotation(bindingType, InterceptorBindingTypeLiteral.INSTANCE);
      BootstrapLogger.LOG.addInterceptorBindingCalled(this.getReceiver(), bindingType);
   }

   public AnnotatedTypeConfigurator configureQualifier(Class qualifier) {
      this.checkWithinObserverNotification();
      AnnotatedTypeConfiguratorImpl configurator = new AnnotatedTypeConfiguratorImpl(this.getBeanManager().createAnnotatedType(qualifier));
      this.additionalQualifiers.add(configurator);
      BootstrapLogger.LOG.configureQualifierCalled(this.getReceiver(), qualifier);
      return configurator;
   }

   public AnnotatedTypeConfigurator configureInterceptorBinding(Class bindingType) {
      this.checkWithinObserverNotification();
      AnnotatedTypeConfiguratorImpl configurator = new AnnotatedTypeConfiguratorImpl(this.getBeanManager().createAnnotatedType(bindingType));
      this.additionalInterceptorBindings.add(configurator);
      BootstrapLogger.LOG.configureInterceptorBindingCalled(this.getReceiver(), bindingType);
      return configurator;
   }

   protected void finish() {
      super.finish();

      try {
         Iterator var1 = this.additionalQualifiers.iterator();

         AnnotatedTypeConfiguratorImpl interceptorBindingAsAnnotatedType;
         while(var1.hasNext()) {
            interceptorBindingAsAnnotatedType = (AnnotatedTypeConfiguratorImpl)var1.next();
            this.addSyntheticAnnotation(interceptorBindingAsAnnotatedType.complete(), QualifierLiteral.INSTANCE);
         }

         var1 = this.additionalInterceptorBindings.iterator();

         while(var1.hasNext()) {
            interceptorBindingAsAnnotatedType = (AnnotatedTypeConfiguratorImpl)var1.next();
            this.addSyntheticAnnotation(interceptorBindingAsAnnotatedType.complete(), InterceptorBindingTypeLiteral.INSTANCE);
         }

      } catch (Exception var3) {
         throw new DefinitionException(var3);
      }
   }

   private void addSyntheticAnnotation(AnnotatedType annotation, Annotation requiredMetaAnnotation) {
      if (requiredMetaAnnotation != null && !((AnnotatedType)annotation).isAnnotationPresent(requiredMetaAnnotation.annotationType())) {
         annotation = new AnnotatedTypeWrapper((AnnotatedType)annotation, new Annotation[]{requiredMetaAnnotation});
      }

      ((ClassTransformer)this.getBeanManager().getServices().get(ClassTransformer.class)).addSyntheticAnnotation((AnnotatedType)annotation, this.getBeanManager().getId());
      ((MetaAnnotationStore)this.getBeanManager().getServices().get(MetaAnnotationStore.class)).clearAnnotationData(((AnnotatedType)annotation).getJavaClass());
   }
}
