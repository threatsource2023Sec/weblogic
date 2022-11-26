package org.hibernate.validator.internal.engine.resolver;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

public class TraversableResolvers {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String PERSISTENCE_CLASS_NAME = "javax.persistence.Persistence";
   private static final String PERSISTENCE_UTIL_METHOD = "getPersistenceUtil";
   private static final String JPA_AWARE_TRAVERSABLE_RESOLVER_CLASS_NAME = "org.hibernate.validator.internal.engine.resolver.JPATraversableResolver";

   private TraversableResolvers() {
   }

   public static TraversableResolver getDefault() {
      Class persistenceClass;
      try {
         persistenceClass = (Class)run(LoadClass.action("javax.persistence.Persistence", TraversableResolvers.class.getClassLoader()));
      } catch (ValidationException var5) {
         LOG.debugf("Cannot find %s on classpath. Assuming non JPA 2 environment. All properties will per default be traversable.", "javax.persistence.Persistence");
         return getTraverseAllTraversableResolver();
      }

      Method persistenceUtilGetter = (Method)run(GetMethod.action(persistenceClass, "getPersistenceUtil"));
      if (persistenceUtilGetter == null) {
         LOG.debugf("Found %s on classpath, but no method '%s'. Assuming JPA 1 environment. All properties will per default be traversable.", "javax.persistence.Persistence", "getPersistenceUtil");
         return getTraverseAllTraversableResolver();
      } else {
         try {
            Object persistence = run(NewInstance.action(persistenceClass, "persistence provider"));
            ReflectionHelper.getValue(persistenceUtilGetter, persistence);
         } catch (Exception var4) {
            LOG.debugf("Unable to invoke %s.%s. Inconsistent JPA environment. All properties will per default be traversable.", "javax.persistence.Persistence", "getPersistenceUtil");
            return getTraverseAllTraversableResolver();
         }

         LOG.debugf("Found %s on classpath containing '%s'. Assuming JPA 2 environment. Trying to instantiate JPA aware TraversableResolver", "javax.persistence.Persistence", "getPersistenceUtil");

         try {
            Class jpaAwareResolverClass = (Class)run(LoadClass.action("org.hibernate.validator.internal.engine.resolver.JPATraversableResolver", TraversableResolvers.class.getClassLoader()));
            LOG.debugf("Instantiated JPA aware TraversableResolver of type %s.", "org.hibernate.validator.internal.engine.resolver.JPATraversableResolver");
            return (TraversableResolver)run(NewInstance.action(jpaAwareResolverClass, ""));
         } catch (ValidationException var3) {
            LOG.logUnableToLoadOrInstantiateJPAAwareResolver("org.hibernate.validator.internal.engine.resolver.JPATraversableResolver");
            return getTraverseAllTraversableResolver();
         }
      }
   }

   public static TraversableResolver wrapWithCachingForSingleValidation(TraversableResolver traversableResolver, boolean traversableResolverResultCacheEnabled) {
      if (!TraverseAllTraversableResolver.class.equals(traversableResolver.getClass()) && traversableResolverResultCacheEnabled) {
         return (TraversableResolver)("org.hibernate.validator.internal.engine.resolver.JPATraversableResolver".equals(traversableResolver.getClass().getName()) ? new CachingJPATraversableResolverForSingleValidation(traversableResolver) : new CachingTraversableResolverForSingleValidation(traversableResolver));
      } else {
         return traversableResolver;
      }
   }

   private static TraversableResolver getTraverseAllTraversableResolver() {
      return new TraverseAllTraversableResolver();
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
