package org.jboss.weld.bootstrap;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Decorated;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.TransientReference;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Scope;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bean.CommonBean;
import org.jboss.weld.bean.DecorableBean;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.NewBean;
import org.jboss.weld.bean.ProducerMethod;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.WeldDecorator;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bean.builtin.AbstractDecorableBuiltInBean;
import org.jboss.weld.bean.builtin.ee.EEResourceProducerField;
import org.jboss.weld.bean.interceptor.CdiInterceptorFactory;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.exceptions.AmbiguousResolutionException;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.injection.producer.AbstractMemberProducer;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.interceptor.reader.PlainInterceptorFactory;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.literal.DecoratedLiteral;
import org.jboss.weld.literal.InterceptedLiteral;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.MessageCallback;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.metadata.cache.StereotypeModel;
import org.jboss.weld.module.PlugableValidator;
import org.jboss.weld.security.GetDeclaredFieldsAction;
import org.jboss.weld.security.GetDeclaredMethodsAction;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Decorators;
import org.jboss.weld.util.InjectionPoints;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.collections.Multimap;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class Validator implements Service {
   private final Set plugableValidators;
   private final Map resolvedInjectionPoints;

   public Validator(Set plugableValidators, Map resolvedInjectionPoints) {
      this.plugableValidators = plugableValidators;
      this.resolvedInjectionPoints = resolvedInjectionPoints;
   }

   protected void validateGeneralBean(Bean bean, BeanManagerImpl beanManager) {
      Iterator var3 = bean.getInjectionPoints().iterator();

      while(var3.hasNext()) {
         InjectionPoint ij = (InjectionPoint)var3.next();
         this.validateInjectionPoint(ij, beanManager);
      }

      if (!beanManager.isNormalScope(bean.getScope()) && !(bean instanceof AbstractBuiltInBean) && !(bean instanceof SessionBean)) {
         validatePseudoScopedBean(bean, beanManager);
      }

      if (beanManager.isPassivatingScope(bean.getScope()) && !Beans.isPassivationCapableBean(bean)) {
         throw ValidatorLogger.LOG.beanWithPassivatingScopeNotPassivationCapable(bean);
      }
   }

   protected void validateRIBean(CommonBean bean, BeanManagerImpl beanManager, Collection specializedBeans) {
      this.validateGeneralBean(bean, beanManager);
      if (!(bean instanceof NewBean)) {
         if (bean instanceof DecorableBean) {
            this.validateDecorators(beanManager, (DecorableBean)bean);
         }

         if (bean instanceof AbstractClassBean) {
            AbstractClassBean classBean = (AbstractClassBean)bean;
            if (classBean.hasInterceptors()) {
               this.validateInterceptors(beanManager, classBean);
            }
         }

         if (bean instanceof AbstractProducerBean) {
            AbstractProducerBean producerBean = (AbstractProducerBean)Reflections.cast(bean);
            if (producerBean.getProducer() instanceof AbstractMemberProducer) {
               AbstractMemberProducer producer = (AbstractMemberProducer)Reflections.cast(producerBean.getProducer());
               if (producer.getDisposalMethod() != null) {
                  Iterator var6 = producer.getDisposalMethod().getInjectionPoints().iterator();

                  while(var6.hasNext()) {
                     InjectionPoint ip = (InjectionPoint)var6.next();
                     this.validateInjectionPointForDefinitionErrors(ip, (Bean)null, beanManager);
                     this.validateMetadataInjectionPoint(ip, (Bean)null, ValidatorLogger.INJECTION_INTO_DISPOSER_METHOD);
                     this.validateEventMetadataInjectionPoint(ip);
                     this.validateInjectionPointForDeploymentProblems(ip, (Bean)null, beanManager);
                  }
               }
            }
         }

      }
   }

   private void validateCustomBean(Bean bean, BeanManagerImpl beanManager) {
      this.validateGeneralBean(bean, beanManager);
      if (!(bean instanceof PassivationCapable) && beanManager.isNormalScope(bean.getScope())) {
         ValidatorLogger.LOG.beanNotPassivationCapable(bean);
      }

   }

   private void validateInterceptors(BeanManagerImpl beanManager, AbstractClassBean classBean) {
      InterceptionModel interceptionModel = (InterceptionModel)beanManager.getInterceptorModelRegistry().get(classBean.getAnnotated());
      if (interceptionModel != null) {
         Set interceptors = interceptionModel.getAllInterceptors();
         if (interceptors.size() > 0) {
            boolean passivationCapabilityCheckRequired = beanManager.isPassivatingScope(classBean.getScope());
            Iterator var6 = interceptors.iterator();

            while(true) {
               InterceptorClassMetadata interceptorMetadata;
               Iterator var11;
               InjectionPoint injectionPoint;
               Bean resolvedBean;
               do {
                  if (!var6.hasNext()) {
                     return;
                  }

                  interceptorMetadata = (InterceptorClassMetadata)var6.next();
                  if (interceptorMetadata.getInterceptorFactory() instanceof CdiInterceptorFactory && passivationCapabilityCheckRequired) {
                     CdiInterceptorFactory cdiInterceptorFactory = (CdiInterceptorFactory)interceptorMetadata.getInterceptorFactory();
                     Interceptor interceptor = cdiInterceptorFactory.getInterceptor();
                     boolean isSerializable = interceptor instanceof InterceptorImpl ? ((InterceptorImpl)interceptor).isSerializable() : Beans.isPassivationCapableDependency(interceptor);
                     if (!isSerializable) {
                        throw ValidatorLogger.LOG.passivatingBeanWithNonserializableInterceptor(classBean, interceptor);
                     }

                     if (interceptor instanceof InterceptorImpl) {
                        beanManager = ((InterceptorImpl)interceptor).getBeanManager();
                     }

                     var11 = interceptor.getInjectionPoints().iterator();

                     while(var11.hasNext()) {
                        injectionPoint = (InjectionPoint)var11.next();
                        resolvedBean = beanManager.resolve(beanManager.getBeans(injectionPoint));
                        this.validateInterceptorDecoratorInjectionPointPassivationCapable(injectionPoint, resolvedBean, beanManager, classBean);
                     }
                  }
               } while(!(interceptorMetadata.getInterceptorFactory() instanceof PlainInterceptorFactory));

               PlainInterceptorFactory factory = (PlainInterceptorFactory)interceptorMetadata.getInterceptorFactory();
               Class interceptorClass = interceptorMetadata.getJavaClass();
               if (passivationCapabilityCheckRequired && !Reflections.isSerializable(interceptorClass)) {
                  throw ValidatorLogger.LOG.passivatingBeanWithNonserializableInterceptor(this, interceptorClass.getName());
               }

               InjectionTarget injectionTarget = factory.getInjectionTarget();
               if (injectionTarget instanceof BasicInjectionTarget) {
                  beanManager = ((BasicInjectionTarget)injectionTarget).getBeanManager();
               }

               var11 = factory.getInjectionTarget().getInjectionPoints().iterator();

               while(var11.hasNext()) {
                  injectionPoint = (InjectionPoint)var11.next();
                  this.validateInjectionPoint(injectionPoint, beanManager);
                  if (passivationCapabilityCheckRequired) {
                     resolvedBean = beanManager.resolve(beanManager.getBeans(injectionPoint));
                     this.validateInterceptorDecoratorInjectionPointPassivationCapable(injectionPoint, resolvedBean, beanManager, classBean);
                  }
               }
            }
         }
      }

   }

   private void validateDecorators(BeanManagerImpl beanManager, DecorableBean bean) {
      if (beanManager.isPassivatingScope(bean.getScope()) || bean instanceof AbstractDecorableBuiltInBean) {
         List decorators = bean.getDecorators();
         if (!decorators.isEmpty()) {
            Iterator var4 = decorators.iterator();

            while(var4.hasNext()) {
               Decorator decorator = (Decorator)var4.next();
               if (!Decorators.isPassivationCapable(decorator)) {
                  if (bean instanceof AbstractDecorableBuiltInBean) {
                     throw ValidatorLogger.LOG.builtinBeanWithNonserializableDecorator(decorator, bean);
                  }

                  throw ValidatorLogger.LOG.passivatingBeanWithNonserializableDecorator(bean, decorator);
               }

               if (decorator instanceof DecoratorImpl) {
                  beanManager = ((DecoratorImpl)decorator).getBeanManager();
               }

               Iterator var6 = decorator.getInjectionPoints().iterator();

               while(var6.hasNext()) {
                  InjectionPoint ij = (InjectionPoint)var6.next();
                  if (!ij.isDelegate()) {
                     Bean resolvedBean = beanManager.resolve(beanManager.getBeans(ij));
                     this.validateInterceptorDecoratorInjectionPointPassivationCapable(ij, resolvedBean, beanManager, bean);
                  }
               }
            }

         }
      }
   }

   public void validateInjectionPoint(InjectionPoint ij, BeanManagerImpl beanManager) {
      this.validateInjectionPointForDefinitionErrors(ij, ij.getBean(), beanManager);
      this.validateMetadataInjectionPoint(ij, ij.getBean(), ValidatorLogger.INJECTION_INTO_NON_BEAN);
      this.validateEventMetadataInjectionPoint(ij);
      this.validateInjectionPointForDeploymentProblems(ij, ij.getBean(), beanManager);
   }

   public void validateInjectionPointForDefinitionErrors(InjectionPoint ij, Bean bean, BeanManagerImpl beanManager) {
      if (ij.getAnnotated().getAnnotation(New.class) != null && ij.getQualifiers().size() > 1) {
         throw ValidatorLogger.LOG.newWithQualifiers(ij, Formats.formatAsStackTraceElement(ij));
      } else if (ij.getType() instanceof TypeVariable) {
         throw ValidatorLogger.LOG.injectionPointWithTypeVariable(ij, Formats.formatAsStackTraceElement(ij));
      } else {
         if (ij.getMember() instanceof Executable && ij.getAnnotated().isAnnotationPresent(Named.class) && ((Named)ij.getAnnotated().getAnnotation(Named.class)).value().equals("")) {
            Executable executable = (Executable)ij.getMember();
            AnnotatedParameter annotatedParameter = (AnnotatedParameter)ij.getAnnotated();
            if (!executable.getParameters()[annotatedParameter.getPosition()].isNamePresent()) {
               throw ValidatorLogger.LOG.nonFieldInjectionPointCannotUseNamed(ij, Formats.formatAsStackTraceElement(ij));
            }
         }

         if (ij.getAnnotated().isAnnotationPresent(Produces.class)) {
            if (bean != null) {
               throw BeanLogger.LOG.injectedFieldCannotBeProducer(ij.getAnnotated(), bean);
            } else {
               throw BeanLogger.LOG.injectedFieldCannotBeProducer(ij.getAnnotated(), ((AnnotatedField)Reflections.cast(ij.getAnnotated())).getDeclaringType());
            }
         } else {
            boolean newBean = bean instanceof NewBean;
            if (!newBean) {
               this.checkScopeAnnotations(ij, (MetaAnnotationStore)beanManager.getServices().get(MetaAnnotationStore.class));
            }

            checkFacadeInjectionPoint(ij, Instance.class);
            checkFacadeInjectionPoint(ij, Event.class);
            if (InterceptionFactory.class.equals(Reflections.getRawType(ij.getType())) && !(bean instanceof ProducerMethod)) {
               throw ValidatorLogger.LOG.invalidInterceptionFactoryInjectionPoint(ij, Formats.formatAsStackTraceElement(ij));
            } else {
               Iterator var8 = this.plugableValidators.iterator();

               while(var8.hasNext()) {
                  PlugableValidator validator = (PlugableValidator)var8.next();
                  validator.validateInjectionPointForDefinitionErrors(ij, bean, beanManager);
               }

            }
         }
      }
   }

   public void validateMetadataInjectionPoint(InjectionPoint ij, Bean bean, MessageCallback messageCallback) {
      if (ij.getType().equals(InjectionPoint.class) && bean == null) {
         throw (DefinitionException)messageCallback.construct(ij, Formats.formatAsStackTraceElement(ij));
      } else if (ij.getType().equals(InjectionPoint.class) && !Dependent.class.equals(bean.getScope())) {
         throw ValidatorLogger.LOG.injectionIntoNonDependentBean(ij, Formats.formatAsStackTraceElement(ij));
      } else {
         Class rawType = Reflections.getRawType(ij.getType());
         if (Bean.class.equals(rawType) || Interceptor.class.equals(rawType) || Decorator.class.equals(rawType)) {
            if (bean == null) {
               throw (DefinitionException)messageCallback.construct(ij, Formats.formatAsStackTraceElement(ij));
            }

            if (bean instanceof AbstractClassBean) {
               checkBeanMetadataInjectionPoint(bean, ij, AnnotatedTypes.getDeclaringAnnotatedType(ij.getAnnotated()).getBaseType());
            }

            if (bean instanceof ProducerMethod) {
               ProducerMethod producerMethod = (ProducerMethod)Reflections.cast(bean);
               checkBeanMetadataInjectionPoint(bean, ij, producerMethod.getAnnotated().getBaseType());
            }
         }

      }
   }

   public void validateEventMetadataInjectionPoint(InjectionPoint ip) {
      if (EventMetadata.class.equals(ip.getType()) && ip.getQualifiers().contains(Literal.INSTANCE)) {
         throw ValidatorLogger.LOG.eventMetadataInjectedOutsideOfObserver(ip, Formats.formatAsStackTraceElement(ip));
      }
   }

   public void validateInjectionPointForDeploymentProblems(InjectionPoint ij, Bean bean, BeanManagerImpl beanManager) {
      if (!ij.isDelegate()) {
         Set resolvedBeans = beanManager.getBeanResolver().resolve(beanManager.getBeans(ij));
         if (!isInjectionPointSatisfied(ij, resolvedBeans, beanManager)) {
            throw ValidatorLogger.LOG.injectionPointHasUnsatisfiedDependencies(ij, Formats.formatAnnotations((Iterable)ij.getQualifiers()), Formats.formatInjectionPointType(ij.getType()), Formats.formatAsStackTraceElement(ij), InjectionPoints.getUnsatisfiedDependenciesAdditionalInfo(ij, beanManager));
         } else if (resolvedBeans.size() > 1) {
            throw ValidatorLogger.LOG.injectionPointHasAmbiguousDependencies(ij, Formats.formatAnnotations((Iterable)ij.getQualifiers()), Formats.formatInjectionPointType(ij.getType()), Formats.formatAsStackTraceElement(ij), WeldCollections.toMultiRowString(resolvedBeans));
         } else {
            if (!resolvedBeans.isEmpty()) {
               Bean resolvedBean = (Bean)resolvedBeans.iterator().next();
               if (beanManager.isNormalScope(resolvedBean.getScope())) {
                  UnproxyableResolutionException ue = Proxies.getUnproxyableTypeException(ij.getType(), resolvedBean, beanManager.getServices(), false);
                  if (ue != null) {
                     throw ValidatorLogger.LOG.injectionPointHasNonProxyableDependencies(ij, Formats.formatAsStackTraceElement(ij), ue);
                  }
               }

               if (bean != null && Beans.isPassivatingScope(bean, beanManager)) {
                  this.validateInjectionPointPassivationCapable(ij, resolvedBean, beanManager);
               }
            }

            Iterator var7 = this.plugableValidators.iterator();

            while(var7.hasNext()) {
               PlugableValidator validator = (PlugableValidator)var7.next();
               validator.validateInjectionPointForDeploymentProblems(ij, bean, beanManager);
            }

            if (this.resolvedInjectionPoints != null) {
               this.resolvedInjectionPoints.put(resolvedBeans.iterator().next(), Boolean.TRUE);
            }

         }
      }
   }

   public void validateProducers(Collection producers, BeanManagerImpl beanManager) {
      Iterator var3 = producers.iterator();

      while(var3.hasNext()) {
         Producer producer = (Producer)var3.next();
         this.validateProducer(producer, beanManager);
      }

   }

   public void validateProducer(Producer producer, BeanManagerImpl beanManager) {
      Iterator var3 = producer.getInjectionPoints().iterator();

      while(var3.hasNext()) {
         InjectionPoint injectionPoint = (InjectionPoint)var3.next();
         this.validateInjectionPoint(injectionPoint, beanManager);
      }

   }

   private void checkScopeAnnotations(InjectionPoint ij, MetaAnnotationStore metaAnnotationStore) {
      Annotated annotated = ij.getAnnotated();
      if (annotated instanceof EnhancedAnnotated) {
         EnhancedAnnotated weldAnnotated = (EnhancedAnnotated)annotated;
         Set scopes = weldAnnotated.getMetaAnnotations(Scope.class);
         Set normalScopes = weldAnnotated.getMetaAnnotations(NormalScope.class);
         Iterator var7 = scopes.iterator();

         Annotation annotation;
         while(var7.hasNext()) {
            annotation = (Annotation)var7.next();
            this.logScopeOnInjectionPointWarning(ij, annotation);
         }

         var7 = normalScopes.iterator();

         while(var7.hasNext()) {
            annotation = (Annotation)var7.next();
            this.logScopeOnInjectionPointWarning(ij, annotation);
         }
      } else {
         Iterator var9 = annotated.getAnnotations().iterator();

         while(var9.hasNext()) {
            Annotation annotation = (Annotation)var9.next();
            if (this.hasScopeMetaAnnotation(annotation)) {
               this.logScopeOnInjectionPointWarning(ij, annotation);
            }
         }
      }

   }

   private void logScopeOnInjectionPointWarning(InjectionPoint ij, Annotation annotation) {
      ValidatorLogger.LOG.scopeAnnotationOnInjectionPoint(annotation, ij, Formats.formatAsStackTraceElement(ij));
   }

   private boolean hasScopeMetaAnnotation(Annotation annotation) {
      Class annotationType = annotation.annotationType();
      return annotationType.isAnnotationPresent(Scope.class) || annotationType.isAnnotationPresent(NormalScope.class);
   }

   private boolean isInjectionPointPassivationCapable(InjectionPoint ij, Bean resolvedBean, BeanManagerImpl beanManager) {
      if (!Beans.isPassivationCapableDependency(resolvedBean)) {
         if (ij.getMember() instanceof Field && ij.isTransient()) {
            return true;
         } else {
            return ij.getAnnotated() instanceof AnnotatedParameter && ij.getAnnotated().isAnnotationPresent(TransientReference.class);
         }
      } else {
         return true;
      }
   }

   public void validateInjectionPointPassivationCapable(InjectionPoint ij, Bean resolvedBean, BeanManagerImpl beanManager) {
      if (!this.isInjectionPointPassivationCapable(ij, resolvedBean, beanManager)) {
         throw ValidatorLogger.LOG.injectionPointHasNonSerializableDependency(ij.getBean(), resolvedBean);
      }
   }

   public void validateInterceptorDecoratorInjectionPointPassivationCapable(InjectionPoint ij, Bean resolvedBean, BeanManagerImpl beanManager, Bean bean) {
      if (!this.isInjectionPointPassivationCapable(ij, resolvedBean, beanManager)) {
         throw ValidatorLogger.LOG.interceptorDecoratorInjectionPointHasNonSerializableDependency(bean, ij.getBean(), resolvedBean);
      }
   }

   public void validateDeployment(BeanManagerImpl manager, BeanDeployment deployment) {
      this.validateDecorators((Collection)manager.getDecorators(), (BeanManagerImpl)manager);
      this.validateInterceptors((Collection)manager.getInterceptors(), (BeanManagerImpl)manager);
      this.validateBeans(manager.getBeans(), manager);
      this.validateEnabledDecoratorClasses(manager, deployment);
      this.validateEnabledInterceptorClasses(manager, deployment);
      this.validateEnabledAlternativeStereotypes(manager, deployment);
      this.validateEnabledAlternativeClasses(manager, deployment);
      this.validateSpecialization(manager);
      this.validateDisposalMethods(deployment.getBeanDeployer().getEnvironment());
      this.validateObserverMethods(deployment.getBeanDeployer().getEnvironment().getObservers(), manager);
      this.validateBeanNames(manager);
   }

   public void validateSpecialization(BeanManagerImpl manager) {
      SpecializationAndEnablementRegistry registry = (SpecializationAndEnablementRegistry)manager.getServices().get(SpecializationAndEnablementRegistry.class);
      Iterator var3 = registry.getBeansSpecializedInAnyDeploymentAsMap().entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var3.hasNext()) {
            return;
         }

         entry = (Map.Entry)var3.next();
      } while((Long)entry.getValue() <= 1L);

      throw ValidatorLogger.LOG.beanSpecializedTooManyTimes(entry.getKey());
   }

   public void validateBeans(Collection beans, BeanManagerImpl manager) {
      List problems = new ArrayList();
      Set specializedBeans = new HashSet();
      Iterator var5 = beans.iterator();

      while(var5.hasNext()) {
         Bean bean = (Bean)var5.next();
         this.validateBean(bean, specializedBeans, manager, problems);
      }

      if (!problems.isEmpty()) {
         if (problems.size() == 1) {
            throw (RuntimeException)problems.get(0);
         } else {
            throw new DeploymentException(problems);
         }
      }
   }

   protected void validateBean(Bean bean, Collection specializedBeans, BeanManagerImpl manager, List problems) {
      try {
         if (bean instanceof CommonBean) {
            this.validateRIBean((CommonBean)bean, manager, specializedBeans);
         } else {
            this.validateCustomBean(bean, manager);
         }
      } catch (RuntimeException var6) {
         problems.add(var6);
      }

   }

   public void validateInterceptors(Collection interceptors, BeanManagerImpl manager) {
      Iterator var3 = interceptors.iterator();

      while(var3.hasNext()) {
         Interceptor interceptor = (Interceptor)var3.next();
         this.validateInterceptor(interceptor, manager);
      }

   }

   protected void validateInterceptor(Interceptor interceptor, BeanManagerImpl manager) {
      if (interceptor instanceof InterceptorImpl) {
         EnhancedAnnotatedType annotated = ((InterceptorImpl)interceptor).getEnhancedAnnotated();
         if (!BeanMethods.getObserverMethods(annotated).isEmpty() || !BeanMethods.getAsyncObserverMethods(annotated).isEmpty()) {
            throw ValidatorLogger.LOG.interceptorsCannotHaveObserverMethods(interceptor);
         }

         if (!interceptor.getScope().equals(Dependent.class)) {
            throw ValidatorLogger.LOG.interceptorOrDecoratorMustBeDependent(interceptor);
         }

         while(annotated != null && annotated.getJavaClass() != Object.class) {
            if (!annotated.getDeclaredEnhancedMethods(Produces.class).isEmpty()) {
               throw ValidatorLogger.LOG.interceptorsCannotHaveProducerMethods(interceptor);
            }

            if (!annotated.getDeclaredEnhancedFields(Produces.class).isEmpty()) {
               throw ValidatorLogger.LOG.interceptorsCannotHaveProducerFields(interceptor);
            }

            if (!annotated.getDeclaredEnhancedMethodsWithAnnotatedParameters(Disposes.class).isEmpty()) {
               throw ValidatorLogger.LOG.interceptorsCannotHaveDisposerMethods(interceptor);
            }

            annotated = annotated.getEnhancedSuperclass();
         }
      }

      Iterator var5 = interceptor.getInjectionPoints().iterator();

      while(var5.hasNext()) {
         InjectionPoint injectionPoint = (InjectionPoint)var5.next();
         this.validateInjectionPoint(injectionPoint, manager);
      }

   }

   public void validateDecorators(Collection decorators, BeanManagerImpl manager) {
      Set specializedBeans = new HashSet();
      Iterator var4 = decorators.iterator();

      while(var4.hasNext()) {
         Decorator decorator = (Decorator)var4.next();
         this.validateDecorator(decorator, specializedBeans, manager);
      }

   }

   protected void validateDecorator(Decorator decorator, Collection specializedBeans, BeanManagerImpl manager) {
      if (decorator.getDecoratedTypes().isEmpty()) {
         throw ValidatorLogger.LOG.noDecoratedTypes(decorator);
      } else if (!decorator.getScope().equals(Dependent.class)) {
         throw ValidatorLogger.LOG.interceptorOrDecoratorMustBeDependent(decorator);
      } else {
         Decorators.checkDelegateType(decorator);
         Type delegateType = decorator.getDelegateType();
         if (delegateType instanceof ParameterizedType) {
            ParameterizedType parameterizedDelegateType = (ParameterizedType)delegateType;
            if (!Decorators.isPassivationCapable(decorator)) {
               if (Instance.class.equals(parameterizedDelegateType.getRawType()) || Provider.class.equals(parameterizedDelegateType.getRawType())) {
                  throw ValidatorLogger.LOG.builtinBeanWithNonserializableDecorator(decorator, Instance.class.getName());
               }

               if (Event.class.equals(parameterizedDelegateType.getRawType())) {
                  throw ValidatorLogger.LOG.builtinBeanWithNonserializableDecorator(decorator, Event.class.getName());
               }
            }
         }

         if (decorator instanceof WeldDecorator) {
            EnhancedAnnotatedType annotated = ((WeldDecorator)decorator).getEnhancedAnnotated();
            if (decorator instanceof DecoratorImpl) {
               this.validateRIBean((CommonBean)decorator, manager, specializedBeans);
               if (!BeanMethods.getObserverMethods(annotated).isEmpty() || !BeanMethods.getAsyncObserverMethods(annotated).isEmpty()) {
                  throw ValidatorLogger.LOG.decoratorsCannotHaveObserverMethods(decorator);
               }

               while(annotated != null && annotated.getJavaClass() != Object.class) {
                  if (!annotated.getDeclaredEnhancedMethods(Produces.class).isEmpty()) {
                     throw ValidatorLogger.LOG.decoratorsCannotHaveProducerMethods(decorator);
                  }

                  if (!annotated.getDeclaredEnhancedFields(Produces.class).isEmpty()) {
                     throw ValidatorLogger.LOG.decoratorsCannotHaveProducerFields(decorator);
                  }

                  if (!annotated.getDeclaredEnhancedMethodsWithAnnotatedParameters(Disposes.class).isEmpty()) {
                     throw ValidatorLogger.LOG.decoratorsCannotHaveDisposerMethods(decorator);
                  }

                  annotated = annotated.getEnhancedSuperclass();
               }
            } else {
               this.validateGeneralBean(decorator, manager);
               Decorators.findDelegateInjectionPoint(annotated, decorator.getInjectionPoints());
            }
         }

      }
   }

   public void validateBeanNames(BeanManagerImpl beanManager) {
      SetMultimap namedAccessibleBeans = SetMultimap.newSetMultimap();
      Iterator var3 = beanManager.getAccessibleBeans().iterator();

      while(var3.hasNext()) {
         Bean bean = (Bean)var3.next();
         if (bean.getName() != null) {
            namedAccessibleBeans.put(bean.getName(), bean);
         }
      }

      List accessibleNamespaces = beanManager.getAccessibleNamespaces();
      Iterator var7 = namedAccessibleBeans.keySet().iterator();

      while(var7.hasNext()) {
         String name = (String)var7.next();
         this.validateBeanName(name, namedAccessibleBeans, accessibleNamespaces, beanManager);
      }

   }

   protected void validateBeanName(String name, SetMultimap namedAccessibleBeans, List accessibleNamespaces, BeanManagerImpl beanManager) {
      Set resolvedBeans = beanManager.getBeanResolver().resolve(Beans.removeDisabledBeans((Set)namedAccessibleBeans.get(name), beanManager));
      if (resolvedBeans.size() > 1) {
         throw ValidatorLogger.LOG.ambiguousElName(name, resolvedBeans);
      } else if (accessibleNamespaces.contains(name)) {
         throw ValidatorLogger.LOG.beanNameIsPrefix(name);
      }
   }

   private void validateEnabledInterceptorClasses(BeanManagerImpl beanManager, BeanDeployment deployment) {
      BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
      if (beansXml != null && !beansXml.getEnabledInterceptors().isEmpty()) {
         Set interceptorBeanClasses = new HashSet();
         Iterator var5 = beanManager.getDynamicAccessibleInterceptors().iterator();

         while(var5.hasNext()) {
            Interceptor interceptor = (Interceptor)var5.next();
            interceptorBeanClasses.add(interceptor.getBeanClass().getName());
         }

         var5 = beansXml.getEnabledInterceptors().iterator();

         while(var5.hasNext()) {
            Metadata interceptorClassName = (Metadata)var5.next();
            if (!interceptorBeanClasses.contains(interceptorClassName.getValue())) {
               throw ValidatorLogger.LOG.interceptorClassDoesNotMatchInterceptorBean(interceptorClassName.getValue(), interceptorClassName.getLocation());
            }
         }
      }

   }

   private void validateEnabledDecoratorClasses(BeanManagerImpl beanManager, BeanDeployment deployment) {
      BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
      if (beansXml != null && !beansXml.getEnabledDecorators().isEmpty()) {
         Set decoratorBeanClasses = new HashSet();
         Iterator var5 = beanManager.getDynamicAccessibleDecorators().iterator();

         while(var5.hasNext()) {
            Decorator bean = (Decorator)var5.next();
            decoratorBeanClasses.add(bean.getBeanClass().getName());
         }

         var5 = beansXml.getEnabledDecorators().iterator();

         while(var5.hasNext()) {
            Metadata decoratorClassName = (Metadata)var5.next();
            if (!decoratorBeanClasses.contains(decoratorClassName.getValue())) {
               throw ValidatorLogger.LOG.decoratorClassNotBeanClassOfDecorator(decoratorClassName.getValue(), WeldCollections.toMultiRowString(decoratorBeanClasses));
            }
         }
      }

   }

   private void validateEnabledAlternativeStereotypes(BeanManagerImpl beanManager, BeanDeployment deployment) {
      BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
      if (beansXml != null && !beansXml.getEnabledAlternativeStereotypes().isEmpty()) {
         Map loadedStereotypes = Types.buildClassNameMap(beanManager.getEnabled().getAlternativeStereotypes());
         Iterator var5 = beansXml.getEnabledAlternativeStereotypes().iterator();

         while(var5.hasNext()) {
            Metadata definition = (Metadata)var5.next();
            Class stereotype = (Class)loadedStereotypes.get(definition.getValue());
            if (!beanManager.isStereotype(stereotype)) {
               throw ValidatorLogger.LOG.alternativeStereotypeNotStereotype(definition);
            }

            if (!isAlternativeStereotype(beanManager, stereotype)) {
               throw ValidatorLogger.LOG.alternativeStereotypeNotAnnotated(definition);
            }
         }
      }

   }

   private void validateEnabledAlternativeClasses(BeanManagerImpl beanManager, BeanDeployment deployment) {
      BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
      if (beansXml != null && !beansXml.getEnabledAlternativeClasses().isEmpty()) {
         Map loadedClasses = Types.buildClassNameMap(beanManager.getEnabled().getAlternativeClasses());
         Multimap beansByClass = SetMultimap.newSetMultimap();
         Iterator var6 = beanManager.getDynamicAccessibleBeans().iterator();

         while(var6.hasNext()) {
            Bean bean = (Bean)var6.next();
            if (!(bean instanceof NewBean)) {
               beansByClass.put(bean.getBeanClass(), bean);
            }
         }

         var6 = beansXml.getEnabledAlternativeClasses().iterator();

         while(var6.hasNext()) {
            Metadata definition = (Metadata)var6.next();
            Class enabledClass = (Class)loadedClasses.get(definition.getValue());
            if (enabledClass.isAnnotation() || enabledClass.isInterface()) {
               throw ValidatorLogger.LOG.alternativeBeanClassNotClass(definition);
            }

            if (!this.isAlternativeBean(enabledClass, beansByClass) && !this.isAlternativeCandidate(enabledClass, beanManager)) {
               throw ValidatorLogger.LOG.alternativeBeanClassNotAnnotated(definition.getValue(), definition.getLocation());
            }
         }
      }

   }

   private boolean isAlternativeCandidate(Class enabledClass, BeanManagerImpl beanManager) {
      if (this.isAlternativeOrHasAlternativeStereotype(enabledClass, beanManager)) {
         return true;
      } else {
         Method[] var3 = (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(enabledClass));
         int var4 = var3.length;

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            Method declaredMethod = var3[var5];
            if (declaredMethod.isAnnotationPresent(Produces.class) && this.isAlternativeOrHasAlternativeStereotype(declaredMethod, beanManager)) {
               return true;
            }
         }

         Field[] var7 = (Field[])AccessController.doPrivileged(new GetDeclaredFieldsAction(enabledClass));
         var4 = var7.length;

         for(var5 = 0; var5 < var4; ++var5) {
            Field declaredField = var7[var5];
            if (declaredField.isAnnotationPresent(Produces.class) && this.isAlternativeOrHasAlternativeStereotype(declaredField, beanManager)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isAlternativeOrHasAlternativeStereotype(AnnotatedElement annotatedElement, BeanManagerImpl beanManager) {
      if (annotatedElement.isAnnotationPresent(Alternative.class)) {
         return true;
      } else {
         Annotation[] var3 = annotatedElement.getAnnotations();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            if (isAlternativeStereotype(beanManager, annotation.annotationType())) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isAlternativeBean(Class enabledClass, Multimap beansByClass) {
      Iterator var3 = beansByClass.get(enabledClass).iterator();

      Bean bean;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         bean = (Bean)var3.next();
      } while(!bean.isAlternative());

      return true;
   }

   private static boolean isAlternativeStereotype(BeanManagerImpl beanManager, Class stereotype) {
      StereotypeModel model = ((MetaAnnotationStore)beanManager.getServices().get(MetaAnnotationStore.class)).getStereotype(stereotype);
      return model.isValid() && model.isAlternative();
   }

   private void validateDisposalMethods(BeanDeployerEnvironment environment) {
      Set beans = environment.getUnresolvedDisposalBeans();
      if (!beans.isEmpty()) {
         throw ValidatorLogger.LOG.disposalMethodsWithoutProducer(WeldCollections.toMultiRowString(beans));
      }
   }

   protected void validateObserverMethods(Iterable observers, BeanManagerImpl beanManager) {
      Iterator var3 = observers.iterator();

      while(var3.hasNext()) {
         ObserverInitializationContext omi = (ObserverInitializationContext)var3.next();
         Iterator var5 = omi.getObserver().getInjectionPoints().iterator();

         while(var5.hasNext()) {
            InjectionPoint ip = (InjectionPoint)var5.next();
            this.validateInjectionPointForDefinitionErrors(ip, ip.getBean(), beanManager);
            this.validateMetadataInjectionPoint(ip, (Bean)null, ValidatorLogger.INJECTION_INTO_NON_BEAN);
            this.validateInjectionPointForDeploymentProblems(ip, ip.getBean(), beanManager);
         }
      }

   }

   private static void checkFacadeInjectionPoint(InjectionPoint injectionPoint, Class type) {
      Type injectionPointType = injectionPoint.getType();
      if (injectionPointType instanceof Class && type.equals(injectionPointType)) {
         throw ValidatorLogger.LOG.injectionPointMustHaveTypeParameter(injectionPoint, Formats.formatAsStackTraceElement(injectionPoint));
      } else {
         if (injectionPointType instanceof ParameterizedType && !injectionPoint.isDelegate()) {
            ParameterizedType parameterizedType = (ParameterizedType)injectionPointType;
            if (type.equals(parameterizedType.getRawType())) {
               if (parameterizedType.getActualTypeArguments()[0] instanceof TypeVariable) {
                  throw ValidatorLogger.LOG.injectionPointWithTypeVariable(injectionPoint, Formats.formatAsStackTraceElement(injectionPoint));
               }

               if (parameterizedType.getActualTypeArguments()[0] instanceof WildcardType) {
                  throw ValidatorLogger.LOG.injectionPointHasWildcard(injectionPoint, Formats.formatAsStackTraceElement(injectionPoint));
               }
            }
         }

      }
   }

   public static void checkBeanMetadataInjectionPoint(Object bean, InjectionPoint ip, Type expectedTypeArgument) {
      if (!(ip.getType() instanceof ParameterizedType)) {
         throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
      } else {
         ParameterizedType parameterizedType = (ParameterizedType)ip.getType();
         if (parameterizedType.getActualTypeArguments().length != 1) {
            throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
         } else {
            Class rawType = (Class)parameterizedType.getRawType();
            Type typeArgument = parameterizedType.getActualTypeArguments()[0];
            if (bean == null) {
               throw ValidatorLogger.LOG.injectionIntoNonBean(ip, Formats.formatAsStackTraceElement(ip));
            } else if (rawType.equals(Interceptor.class) && !(bean instanceof Interceptor)) {
               throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
            } else if (rawType.equals(Decorator.class) && !(bean instanceof Decorator)) {
               throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
            } else {
               Set qualifiers = ip.getQualifiers();
               if (qualifiers.contains(InterceptedLiteral.INSTANCE)) {
                  if (!(bean instanceof Interceptor)) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointQualifier(Intercepted.class, Interceptor.class, ip, Formats.formatAsStackTraceElement(ip));
                  }

                  if (!rawType.equals(Bean.class)) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
                  }

                  if (!Reflections.isUnboundedWildcard(typeArgument)) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointTypeArgument(typeArgument, ip, Formats.formatAsStackTraceElement(ip));
                  }
               }

               if (qualifiers.contains(DecoratedLiteral.INSTANCE)) {
                  if (!(bean instanceof Decorator)) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointQualifier(Decorated.class, Decorator.class, ip, Formats.formatAsStackTraceElement(ip));
                  }

                  Decorator decorator = (Decorator)Reflections.cast(bean);
                  if (!rawType.equals(Bean.class)) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointType(ip.getType(), ip, Formats.formatAsStackTraceElement(ip));
                  }

                  if (!typeArgument.equals(decorator.getDelegateType())) {
                     throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointTypeArgument(typeArgument, ip, Formats.formatAsStackTraceElement(ip));
                  }
               }

               if (qualifiers.contains(Literal.INSTANCE) && !expectedTypeArgument.equals(typeArgument)) {
                  throw ValidatorLogger.LOG.invalidBeanMetadataInjectionPointTypeArgument(typeArgument, ip, Formats.formatAsStackTraceElement(ip));
               }
            }
         }
      }
   }

   private static boolean isInjectionPointSatisfied(InjectionPoint ij, Set resolvedBeans, BeanManagerImpl beanManager) {
      if (ij.getBean() instanceof Decorator) {
         if (beanManager.getEnabled().isDecoratorEnabled(ij.getBean().getBeanClass())) {
            return resolvedBeans.size() > 0;
         } else {
            return true;
         }
      } else {
         return resolvedBeans.size() > 0;
      }
   }

   private static void validatePseudoScopedBean(Bean bean, BeanManagerImpl beanManager) {
      if (!bean.getInjectionPoints().isEmpty()) {
         reallyValidatePseudoScopedBean(bean, beanManager, new LinkedHashSet(), new HashSet());
      }
   }

   private static void reallyValidatePseudoScopedBean(Bean bean, BeanManagerImpl beanManager, Set dependencyPath, Set validatedBeans) {
      if (dependencyPath.contains(bean)) {
         List realDependencyPath = new ArrayList(dependencyPath);
         realDependencyPath.add(bean);
         throw ValidatorLogger.LOG.pseudoScopedBeanHasCircularReferences(WeldCollections.toMultiRowString(realDependencyPath));
      } else if (!validatedBeans.contains(bean)) {
         dependencyPath.add(bean);
         Iterator var4 = bean.getInjectionPoints().iterator();

         while(var4.hasNext()) {
            InjectionPoint injectionPoint = (InjectionPoint)var4.next();
            if (!injectionPoint.isDelegate()) {
               dependencyPath.add(injectionPoint);
               validatePseudoScopedInjectionPoint(injectionPoint, beanManager, dependencyPath, validatedBeans);
               dependencyPath.remove(injectionPoint);
            }
         }

         if (bean instanceof DecorableBean) {
            List decorators = ((DecorableBean)Reflections.cast(bean)).getDecorators();
            if (!decorators.isEmpty()) {
               Iterator var10 = decorators.iterator();

               while(var10.hasNext()) {
                  Decorator decorator = (Decorator)var10.next();
                  reallyValidatePseudoScopedBean(decorator, beanManager, dependencyPath, validatedBeans);
               }
            }
         }

         if (bean instanceof AbstractProducerBean && !(bean instanceof EEResourceProducerField)) {
            AbstractProducerBean producer = (AbstractProducerBean)bean;
            if (!beanManager.isNormalScope(producer.getDeclaringBean().getScope()) && !producer.getAnnotated().isStatic()) {
               reallyValidatePseudoScopedBean(producer.getDeclaringBean(), beanManager, dependencyPath, validatedBeans);
            }
         }

         validatedBeans.add(bean);
         dependencyPath.remove(bean);
      }
   }

   private static void validatePseudoScopedInjectionPoint(InjectionPoint ij, BeanManagerImpl beanManager, Set dependencyPath, Set validatedBeans) {
      Set resolved = beanManager.getBeans(ij);
      Bean bean = null;

      try {
         bean = beanManager.resolve(resolved);
      } catch (AmbiguousResolutionException var7) {
         throw ValidatorLogger.LOG.injectionPointHasAmbiguousDependencies(ij, Formats.formatAnnotations((Iterable)ij.getQualifiers()), Formats.formatInjectionPointType(ij.getType()), Formats.formatAsStackTraceElement(ij), WeldCollections.toMultiRowString(resolved));
      }

      if (bean != null && !(bean instanceof AbstractBuiltInBean) && !ij.isDelegate()) {
         boolean normalScoped = beanManager.isNormalScope(bean.getScope());
         if (!normalScoped && !(bean instanceof SessionBean)) {
            reallyValidatePseudoScopedBean(bean, beanManager, dependencyPath, validatedBeans);
         }
      }

   }

   public void cleanup() {
   }

   public boolean isResolved(Bean bean) {
      return this.resolvedInjectionPoints != null ? this.resolvedInjectionPoints.containsKey(bean) : false;
   }

   public void clearResolved() {
      if (this.resolvedInjectionPoints != null) {
         this.resolvedInjectionPoints.clear();
      }

   }
}
