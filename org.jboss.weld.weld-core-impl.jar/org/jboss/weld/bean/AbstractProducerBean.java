package org.jboss.weld.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Defaults;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractProducerBean extends AbstractBean {
   private final AbstractClassBean declaringBean;
   private boolean passivationCapableBean;
   private boolean passivationCapableDependency;

   public AbstractProducerBean(BeanAttributes attributes, BeanIdentifier identifier, AbstractClassBean declaringBean, BeanManagerImpl beanManager, ServiceRegistry services) {
      super(attributes, identifier, beanManager);
      this.declaringBean = declaringBean;
   }

   public Class getBeanClass() {
      return this.getDeclaringBean().getBeanClass();
   }

   protected void initType() {
      try {
         this.type = this.getEnhancedAnnotated().getJavaClass();
      } catch (ClassCastException var3) {
         Type type = Beans.getDeclaredBeanType(this.getClass());
         throw BeanLogger.LOG.producerCastError(this.getEnhancedAnnotated().getJavaClass(), type == null ? " unknown " : type, var3);
      }
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      this.getDeclaringBean().initialize(environment);
      super.internalInitialize(environment);
      this.initPassivationCapable();
   }

   private void initPassivationCapable() {
      this.passivationCapableBean = !Reflections.isFinal(this.getEnhancedAnnotated().getJavaClass()) || Reflections.isSerializable(this.getEnhancedAnnotated().getJavaClass());
      if (this.isNormalScoped()) {
         this.passivationCapableDependency = true;
      } else if (this.getScope().equals(Dependent.class) && this.passivationCapableBean) {
         this.passivationCapableDependency = true;
      } else {
         this.passivationCapableDependency = false;
      }

   }

   public boolean isPassivationCapableBean() {
      return this.passivationCapableBean;
   }

   public boolean isPassivationCapableDependency() {
      return this.passivationCapableDependency;
   }

   public Set getInjectionPoints() {
      return this.getProducer().getInjectionPoints();
   }

   protected Object checkReturnValue(Object instance) {
      if (instance == null && !this.isDependent()) {
         throw BeanLogger.LOG.nullNotAllowedFromProducer(this.getProducer(), Formats.formatAsStackTraceElement(this.getAnnotated().getJavaMember()));
      } else {
         InjectionPoint injectionPoint;
         if (instance == null) {
            injectionPoint = (InjectionPoint)((CurrentInjectionPoint)this.beanManager.getServices().get(CurrentInjectionPoint.class)).peek();
            if (injectionPoint != null) {
               Class injectionPointRawType = Reflections.getRawType(injectionPoint.getType());
               if (injectionPointRawType.isPrimitive()) {
                  return Reflections.cast(Defaults.getJlsDefaultValue(injectionPointRawType));
               }
            }
         }

         if (instance != null && !(instance instanceof Serializable)) {
            if (this.beanManager.isPassivatingScope(this.getScope())) {
               throw BeanLogger.LOG.nonSerializableProductError(this.getProducer(), Formats.formatAsStackTraceElement(this.getAnnotated().getJavaMember()));
            }

            injectionPoint = (InjectionPoint)((CurrentInjectionPoint)this.beanManager.getServices().get(CurrentInjectionPoint.class)).peek();
            if (injectionPoint != null && injectionPoint.getBean() != null && Beans.isPassivatingScope(injectionPoint.getBean(), this.beanManager) && (!(injectionPoint.getMember() instanceof Field) || !injectionPoint.isTransient())) {
               throw BeanLogger.LOG.unserializableProductInjectionError(this, Formats.formatAsStackTraceElement(this.getAnnotated().getJavaMember()), injectionPoint, Formats.formatAsStackTraceElement(injectionPoint.getMember()));
            }
         }

         return instance;
      }
   }

   protected void checkType() {
      if (this.beanManager.isPassivatingScope(this.getScope()) && !this.isPassivationCapableBean()) {
         throw BeanLogger.LOG.passivatingBeanNeedsSerializableImpl(this);
      }
   }

   protected boolean isTypeSerializable(Object instance) {
      return instance instanceof Serializable;
   }

   public Object create(CreationalContext creationalContext) {
      Object instance = this.getProducer().produce(creationalContext);
      instance = this.checkReturnValue(instance);
      return instance;
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      super.destroy(instance, creationalContext);

      try {
         this.getProducer().dispose(instance);
      } catch (Exception var7) {
         BeanLogger.LOG.errorDestroying(instance, this);
         BeanLogger.LOG.catchingDebug(var7);
      } finally {
         if (this.getDeclaringBean().isDependent()) {
            creationalContext.release();
         }

      }

   }

   public AbstractClassBean getDeclaringBean() {
      return this.declaringBean;
   }

   public abstract AnnotatedMember getAnnotated();

   public abstract EnhancedAnnotatedMember getEnhancedAnnotated();
}
