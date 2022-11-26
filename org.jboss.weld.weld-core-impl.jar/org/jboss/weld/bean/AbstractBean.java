package org.jboss.weld.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Specializes;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Named;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.bean.attributes.ImmutableBeanAttributes;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.bootstrap.SpecializationAndEnablementRegistry;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resolution.TypeEqualitySpecializationUtils;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class AbstractBean extends RIBean {
   protected Class type;
   private boolean preInitialized;
   private boolean proxyRequired;
   private Producer producer;
   private boolean ignoreFinalMethods;

   public AbstractBean(BeanAttributes attributes, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, identifier, beanManager);
   }

   public void preInitialize() {
      synchronized(this) {
         if (this.isSpecializing() && !this.preInitialized) {
            this.preInitialized = true;
            this.preSpecialize();
            this.specialize();
            this.checkSpecialization();
            this.postSpecialize();
         }

      }
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      this.preInitialize();
      BeanLogger.LOG.creatingBean(this.getType());
      if (this.getScope() != null) {
         this.proxyRequired = this.isNormalScoped();
      } else {
         this.proxyRequired = false;
      }

      BeanLogger.LOG.qualifiersUsed(this.getQualifiers(), this);
      BeanLogger.LOG.usingName(this.getName(), this);
      BeanLogger.LOG.usingScope(this.getScope(), this);
   }

   public void initializeAfterBeanDiscovery() {
      this.checkType();
   }

   protected abstract void checkType();

   public void checkSpecialization() {
      if (this.isSpecializing()) {
         boolean isNameDefined = this.getAnnotated().isAnnotationPresent(Named.class);
         String previousSpecializedBeanName = null;
         Iterator var3 = this.getSpecializedBeans().iterator();

         while(var3.hasNext()) {
            AbstractBean specializedBean = (AbstractBean)var3.next();
            String name = specializedBean.getName();
            if (previousSpecializedBeanName != null && name != null && !previousSpecializedBeanName.equals(specializedBean.getName())) {
               throw BeanLogger.LOG.beansWithDifferentBeanNamesCannotBeSpecialized(previousSpecializedBeanName, specializedBean.getName(), this);
            }

            previousSpecializedBeanName = name;
            if (isNameDefined && name != null) {
               throw BeanLogger.LOG.nameNotAllowedOnSpecialization(this.getAnnotated(), specializedBean.getAnnotated());
            }

            boolean rawInsteadOfGeneric = this instanceof AbstractClassBean && specializedBean.getBeanClass().getTypeParameters().length > 0 && !(((AbstractClassBean)this).getBeanClass().getGenericSuperclass() instanceof ParameterizedType);
            Iterator var7 = specializedBean.getTypes().iterator();

            while(var7.hasNext()) {
               Type specializedType = (Type)var7.next();
               if (rawInsteadOfGeneric && specializedType instanceof ParameterizedType) {
                  throw BeanLogger.LOG.specializingBeanMissingSpecializedType(this, specializedType, specializedBean);
               }

               boolean contains = this.getTypes().contains(specializedType);
               if (!contains) {
                  Iterator var10 = this.getTypes().iterator();

                  while(var10.hasNext()) {
                     Type specializingType = (Type)var10.next();
                     if (TypeEqualitySpecializationUtils.areTheSame(specializingType, specializedType)) {
                        contains = true;
                        break;
                     }
                  }
               }

               if (!contains) {
                  throw BeanLogger.LOG.specializingBeanMissingSpecializedType(this, specializedType, specializedBean);
               }
            }
         }
      }

   }

   protected void postSpecialize() {
      Set qualifiers = new HashSet();
      Iterator var2 = this.attributes().getQualifiers().iterator();

      while(true) {
         Annotation qualifier;
         do {
            if (!var2.hasNext()) {
               String name = this.attributes().getName();
               Iterator var6 = this.getSpecializedBeans().iterator();

               while(var6.hasNext()) {
                  AbstractBean specializedBean = (AbstractBean)var6.next();
                  qualifiers.addAll(specializedBean.getQualifiers());
                  if (specializedBean.getName() != null) {
                     name = specializedBean.getName();
                  }
               }

               this.setAttributes(new ImmutableBeanAttributes(qualifiers, name, this.attributes()));
               return;
            }

            qualifier = (Annotation)var2.next();
         } while(qualifier.equals(Literal.INSTANCE) && !this.getAnnotated().isAnnotationPresent(Default.class));

         qualifiers.add(qualifier);
      }
   }

   protected void preSpecialize() {
   }

   protected void specialize() {
   }

   public abstract Annotated getAnnotated();

   public abstract EnhancedAnnotated getEnhancedAnnotated();

   protected Set getSpecializedBeans() {
      return ((SpecializationAndEnablementRegistry)this.getBeanManager().getServices().get(SpecializationAndEnablementRegistry.class)).resolveSpecializedBeans(this);
   }

   public Class getType() {
      return this.type;
   }

   public boolean isDependent() {
      return Dependent.class.equals(this.getScope());
   }

   public boolean isSpecializing() {
      return this.getAnnotated().isAnnotationPresent(Specializes.class);
   }

   public boolean isProxyRequired() {
      return this.proxyRequired;
   }

   public Producer getProducer() {
      return this.producer;
   }

   public void setProducer(Producer producer) {
      this.producer = producer;
   }

   public boolean isIgnoreFinalMethods() {
      return this.ignoreFinalMethods;
   }

   public void setIgnoreFinalMethods() {
      this.ignoreFinalMethods = true;
   }
}
