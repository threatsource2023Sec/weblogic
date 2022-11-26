package org.jboss.weld.bean.builtin;

import java.io.ObjectStreamException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InterceptionType;
import org.jboss.weld.Container;
import org.jboss.weld.ContainerState;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.construction.api.WeldCreationalContext;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldInjectionTargetBuilder;
import org.jboss.weld.manager.api.WeldInjectionTargetFactory;
import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.ForwardingBeanManager;
import org.jboss.weld.util.reflection.Reflections;

public class BeanManagerProxy extends ForwardingBeanManager implements WeldManager {
   private static final String GET_BEANS_METHOD_NAME = "getBeans()";
   private static final long serialVersionUID = -6990849486568169846L;
   private final BeanManagerImpl manager;
   private transient volatile Container container;
   private final boolean nonPortableMode;

   public BeanManagerProxy(BeanManagerImpl manager) {
      this.manager = manager;
      this.nonPortableMode = ((WeldConfiguration)manager.getServices().get(WeldConfiguration.class)).getBooleanProperty(ConfigurationKey.NON_PORTABLE_MODE);
   }

   public BeanManagerImpl delegate() {
      return this.manager;
   }

   public Object getReference(Bean bean, Type beanType, CreationalContext ctx) {
      this.checkContainerState("getReference()", ContainerState.VALIDATED);
      return super.getReference(bean, beanType, ctx);
   }

   public Object getInjectableReference(InjectionPoint ij, CreationalContext ctx) {
      this.checkContainerState("getInjectableReference()", ContainerState.VALIDATED);
      return super.getInjectableReference(ij, ctx);
   }

   public Set getBeans(Type beanType, Annotation... qualifiers) {
      this.checkContainerState("getBeans()");
      return super.getBeans(beanType, qualifiers);
   }

   public Set getBeans(String name) {
      this.checkContainerState("getBeans()");
      return super.getBeans(name);
   }

   public Bean getPassivationCapableBean(String id) {
      this.checkContainerState("getPassivationCapableBean()");
      return super.getPassivationCapableBean(id);
   }

   public Bean resolve(Set beans) {
      this.checkContainerState("resolve()");
      return super.resolve(beans);
   }

   public void validate(InjectionPoint injectionPoint) {
      this.checkContainerState("validate()");
      super.validate(injectionPoint);
   }

   public Set resolveObserverMethods(Object event, Annotation... qualifiers) {
      this.checkContainerState("resolveObserverMethods()");
      return super.resolveObserverMethods(event, qualifiers);
   }

   public List resolveDecorators(Set types, Annotation... qualifiers) {
      this.checkContainerState("resolveDecorators()");
      return super.resolveDecorators(types, qualifiers);
   }

   public List resolveInterceptors(InterceptionType type, Annotation... interceptorBindings) {
      this.checkContainerState("resolveInterceptors()");
      return super.resolveInterceptors(type, interceptorBindings);
   }

   public InjectionTarget createInjectionTarget(EjbDescriptor descriptor) {
      return this.delegate().createInjectionTarget(descriptor);
   }

   public Bean getBean(EjbDescriptor descriptor) {
      return this.delegate().getBean(descriptor);
   }

   public EjbDescriptor getEjbDescriptor(String ejbName) {
      return this.delegate().getEjbDescriptor(ejbName);
   }

   public ServiceRegistry getServices() {
      return this.delegate().getServices();
   }

   public InjectionTarget fireProcessInjectionTarget(AnnotatedType type) {
      return this.delegate().fireProcessInjectionTarget(type);
   }

   public InjectionTarget fireProcessInjectionTarget(AnnotatedType annotatedType, InjectionTarget injectionTarget) {
      return this.delegate().fireProcessInjectionTarget(annotatedType, injectionTarget);
   }

   public String getId() {
      return this.delegate().getId();
   }

   public Instance instance() {
      return this.delegate().instance();
   }

   public WeldInstance createInstance() {
      this.checkContainerState("createInstance()", ContainerState.VALIDATED);
      return this.delegate().createInstance();
   }

   public Bean getPassivationCapableBean(BeanIdentifier identifier) {
      return this.delegate().getPassivationCapableBean(identifier);
   }

   public WeldInjectionTargetBuilder createInjectionTargetBuilder(AnnotatedType type) {
      return this.delegate().createInjectionTargetBuilder(type);
   }

   public WeldInjectionTargetFactory getInjectionTargetFactory(AnnotatedType annotatedType) {
      return this.delegate().getInjectionTargetFactory(annotatedType);
   }

   public WeldCreationalContext createCreationalContext(Contextual contextual) {
      return this.delegate().createCreationalContext(contextual);
   }

   protected Object readResolve() throws ObjectStreamException {
      return new BeanManagerProxy(this.manager);
   }

   private void checkContainerState(String methodName, ContainerState minimalState) {
      if (!this.nonPortableMode) {
         if (this.container == null) {
            this.container = Container.instance(this.manager);
         }

         ContainerState state = this.container.getState();
         if (ContainerState.SHUTDOWN.equals(state)) {
            throw BeanManagerLogger.LOG.methodNotAvailableAfterShutdown(methodName);
         } else if (state.compareTo(minimalState) < 0) {
            throw BeanManagerLogger.LOG.methodNotAvailableDuringInitialization(methodName, state);
         }
      }
   }

   private void checkContainerState(String methodName) {
      this.checkContainerState(methodName, ContainerState.DISCOVERED);
   }

   public static BeanManagerImpl unwrap(BeanManager manager) {
      BeanManagerImpl instance = tryUnwrap(manager);
      if (instance == null) {
         throw new IllegalArgumentException("Unknown BeanManager " + manager);
      } else {
         return instance;
      }
   }

   public static BeanManagerImpl tryUnwrap(Object instance) {
      if (instance instanceof ForwardingBeanManager) {
         instance = ((ForwardingBeanManager)Reflections.cast(instance)).delegate();
      }

      return instance instanceof BeanManagerImpl ? (BeanManagerImpl)instance : null;
   }

   public BeanManagerImpl unwrap() {
      return this.delegate();
   }

   public AnnotatedType createAnnotatedType(Class type, String id) {
      return this.delegate().createAnnotatedType(type, id);
   }

   public void disposeAnnotatedType(Class type, String id) {
      this.delegate().disposeAnnotatedType(type, id);
   }

   public boolean isContextActive(Class scopeType) {
      return this.delegate().isContextActive(scopeType);
   }

   public Collection getScopes() {
      return this.delegate().getScopes();
   }
}
