package weblogic.jms.integration.injection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.util.AnnotationLiteral;
import javax.transaction.TransactionScoped;

public class JMSCDIExtension implements Extension {
   private Bean createLocalBean(BeanManager beanManager, Class beanClass) {
      AnnotatedType annotatedType = beanManager.createAnnotatedType(beanClass);
      LocalBean localBean = new LocalBean(beanClass);
      InjectionTargetFactory injectionTargetFactory = beanManager.getInjectionTargetFactory(annotatedType);
      localBean.setInjectionTarget(injectionTargetFactory.createInjectionTarget(localBean));
      return localBean;
   }

   private Bean createLocalPassivationCapableBean(BeanManager beanManager, Class beanClass) {
      AnnotatedType annotatedType = beanManager.createAnnotatedType(beanClass);
      LocalBean localBean = new LocalPassivationCapableBean(beanClass);
      InjectionTargetFactory injectionTargetFactory = beanManager.getInjectionTargetFactory(annotatedType);
      localBean.setInjectionTarget(injectionTargetFactory.createInjectionTarget(localBean));
      return localBean;
   }

   public void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscoveryEvent, BeanManager beanManager) {
      Bean requestManagerBean = this.createLocalPassivationCapableBean(beanManager, RequestedJMSContextManager.class);
      afterBeanDiscoveryEvent.addBean(requestManagerBean);
      Bean transactionManagerBean = this.createLocalPassivationCapableBean(beanManager, TransactedJMSContextManager.class);
      afterBeanDiscoveryEvent.addBean(transactionManagerBean);
      Bean contextBean = this.createLocalBean(beanManager, InjectableJMSContext.class);
      afterBeanDiscoveryEvent.addBean(contextBean);
   }

   void addScope(@Observes BeforeBeanDiscovery event) {
   }

   void afterBeanDiscovery(@Observes AfterBeanDiscovery event) {
   }

   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event) {
   }

   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
   }

   public void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
   }

   public void beforeShutdown(@Observes BeforeShutdown event, BeanManager beanManager) {
   }

   public void processInjectionTarget(@Observes ProcessInjectionTarget pit) {
   }

   public void processProducer(@Observes ProcessProducer event) {
   }

   static AnnotationLiteral getDefaultAnnotationLiteral() {
      return new AnnotationLiteral() {
      };
   }

   static AnnotationLiteral getAnyAnnotationLiteral() {
      return new AnnotationLiteral() {
      };
   }

   public class LocalPassivationCapableBean extends LocalBean implements PassivationCapable {
      private String id = UUID.randomUUID().toString();

      public LocalPassivationCapableBean(Class beanClass) {
         super(beanClass);
      }

      public LocalPassivationCapableBean(Class beanClass, InjectionTarget injectionTarget) {
         super(beanClass, injectionTarget);
      }

      public String getId() {
         return this.id;
      }
   }

   public class LocalBean implements Bean {
      private Class beanClass;
      private InjectionTarget injectionTarget;

      public LocalBean(Class beanClass) {
         this.beanClass = beanClass;
      }

      public LocalBean(Class beanClass, InjectionTarget injectionTarget) {
         this.beanClass = beanClass;
         this.injectionTarget = injectionTarget;
      }

      public void setInjectionTarget(InjectionTarget injectionTarget) {
         this.injectionTarget = injectionTarget;
      }

      public Class getBeanClass() {
         return this.beanClass;
      }

      public Set getInjectionPoints() {
         return this.injectionTarget.getInjectionPoints();
      }

      public String getName() {
         return this.beanClass.getName();
      }

      public Set getQualifiers() {
         Set qualifiers = new HashSet();
         qualifiers.add(JMSCDIExtension.getDefaultAnnotationLiteral());
         qualifiers.add(JMSCDIExtension.getAnyAnnotationLiteral());
         return qualifiers;
      }

      public Class getScope() {
         if (this.beanClass.isAnnotationPresent(RequestScoped.class)) {
            return RequestScoped.class;
         } else {
            return this.beanClass.isAnnotationPresent(TransactionScoped.class) ? TransactionScoped.class : Dependent.class;
         }
      }

      public Set getStereotypes() {
         return Collections.emptySet();
      }

      public Set getTypes() {
         Set types = new HashSet();
         types.add(this.beanClass);
         boolean loop = true;
         Class clazz = this.beanClass;

         while(loop) {
            Class[] interfaces = clazz.getInterfaces();
            Class[] var5 = interfaces;
            int var6 = interfaces.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Class t = var5[var7];
               types.add(t);
            }

            clazz = clazz.getSuperclass();
            if (clazz == null) {
               loop = false;
               break;
            }

            types.add(clazz);
         }

         return types;
      }

      public boolean isAlternative() {
         return false;
      }

      public boolean isNullable() {
         return false;
      }

      public Object create(CreationalContext ctx) {
         Object instance = this.injectionTarget.produce(ctx);
         this.injectionTarget.inject(instance, ctx);
         this.injectionTarget.postConstruct(instance);
         return instance;
      }

      public void destroy(Object instance, CreationalContext ctx) {
         this.injectionTarget.preDestroy(instance);
         this.injectionTarget.dispose(instance);
         ctx.release();
      }

      public String toString() {
         return super.toString() + "(" + this.beanClass.getName() + ")";
      }
   }
}
