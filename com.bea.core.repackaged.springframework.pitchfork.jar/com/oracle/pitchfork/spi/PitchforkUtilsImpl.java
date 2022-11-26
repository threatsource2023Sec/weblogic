package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.CachedIntrospectionResults;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.oracle.pitchfork.inject.FieldInjection;
import com.oracle.pitchfork.inject.MethodInjection;
import com.oracle.pitchfork.inject.ResourceInfo;
import com.oracle.pitchfork.intercept.InterceptionMetadata;
import com.oracle.pitchfork.intercept.InterceptorMetadata;
import com.oracle.pitchfork.interfaces.EjbComponentCreatorBroker;
import com.oracle.pitchfork.interfaces.ManagedBeanContributorBroker;
import com.oracle.pitchfork.interfaces.PitchforkUtils;
import com.oracle.pitchfork.interfaces.WSEEComponentContributorBroker;
import com.oracle.pitchfork.interfaces.WebComponentContributorBroker;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class PitchforkUtilsImpl implements PitchforkUtils {
   public InjectionI createFieldInjection(Field injectionField, String resourceLocation, Class injectedType, boolean isOptional) {
      return new FieldInjection(injectionField, new ResourceInfo(resourceLocation, injectedType), isOptional);
   }

   public InjectionI createMethodInjection(Method injectionMethod, String resourceLocation, Class injectedType, boolean isOptional) {
      return new MethodInjection(injectionMethod, new ResourceInfo(resourceLocation, injectedType), isOptional);
   }

   public InterceptorMetadataI createInterceptorMetadata(Class interceptorClass, List aroundInvokes, List aroundTimeouts, Method matchingMethod) {
      RootBeanDefinition interceptorBeanDefinition = new RootBeanDefinition(interceptorClass);
      interceptorBeanDefinition.setScope("prototype");
      InterceptorMetadata im = new InterceptorMetadata(interceptorClass, aroundInvokes, aroundTimeouts, matchingMethod);
      im.setBeanDefinition(interceptorBeanDefinition);
      return im;
   }

   public void acceptClassLoader(ClassLoader cl) {
      CachedIntrospectionResults.acceptClassLoader(cl);
   }

   public void clearClassLoader(ClassLoader cl) {
      CachedIntrospectionResults.clearClassLoader(cl);
   }

   public Jsr250MetadataI createJ2eeClientInjectionMetadata(String componentName, Class componentClass, DeploymentUnitMetadataI dum) {
      return new J2eeClientInjectionMetadata(dum, componentName, componentClass);
   }

   public InterceptionMetadataI createEjbProxyMetadata(String componentName, Class componentClass, DeploymentUnitMetadataI dum, boolean usesSpringExtensionModel) {
      return new InterceptionMetadata(dum, componentName, componentClass, usesSpringExtensionModel);
   }

   public EjbComponentCreatorBroker createEjbComponentCreatorBroker() {
      return new EjbComponentCreatorBrokerImpl();
   }

   public WebComponentContributorBroker createWebComponentContributorBroker() {
      return new WebComponentContributorBrokerImpl();
   }

   public WSEEComponentContributorBroker createWSEEComponentContributorBroker() {
      return new WSEEComponentContributorBrokerImpl();
   }

   public ManagedBeanContributorBroker createManagedBeanComponentContributorBroker() {
      return new ManagedBeanContributorBrokerImpl();
   }

   public Class forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
      return ClassUtils.forName(className, classLoader);
   }
}
