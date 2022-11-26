package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import javax.el.ELManager;
import javax.el.ExpressionFactory;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class ResourceBundleMessageInterpolator extends AbstractMessageInterpolator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ExpressionFactory expressionFactory;

   public ResourceBundleMessageInterpolator() {
      this.expressionFactory = buildExpressionFactory();
   }

   public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
      super(userResourceBundleLocator);
      this.expressionFactory = buildExpressionFactory();
   }

   public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator) {
      super(userResourceBundleLocator, contributorResourceBundleLocator);
      this.expressionFactory = buildExpressionFactory();
   }

   public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, boolean cachingEnabled) {
      super(userResourceBundleLocator, contributorResourceBundleLocator, cachingEnabled);
      this.expressionFactory = buildExpressionFactory();
   }

   public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled) {
      super(userResourceBundleLocator, (ResourceBundleLocator)null, cachingEnabled);
      this.expressionFactory = buildExpressionFactory();
   }

   public ResourceBundleMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, boolean cachingEnabled, ExpressionFactory expressionFactory) {
      super(userResourceBundleLocator, (ResourceBundleLocator)null, cachingEnabled);
      this.expressionFactory = expressionFactory;
   }

   public String interpolate(MessageInterpolator.Context context, Locale locale, String term) {
      InterpolationTerm expression = new InterpolationTerm(term, locale, this.expressionFactory);
      return expression.interpolate(context);
   }

   private static ExpressionFactory buildExpressionFactory() {
      if (canLoadExpressionFactory()) {
         ExpressionFactory expressionFactory = ELManager.getExpressionFactory();
         LOG.debug("Loaded expression factory via original TCCL");
         return expressionFactory;
      } else {
         ClassLoader originalContextClassLoader = (ClassLoader)run(GetClassLoader.fromContext());

         ExpressionFactory var2;
         try {
            run(SetContextClassLoader.action(ResourceBundleMessageInterpolator.class.getClassLoader()));
            ExpressionFactory expressionFactory;
            if (canLoadExpressionFactory()) {
               expressionFactory = ELManager.getExpressionFactory();
               LOG.debug("Loaded expression factory via HV classloader");
               var2 = expressionFactory;
               return var2;
            }

            run(SetContextClassLoader.action(ELManager.class.getClassLoader()));
            if (!canLoadExpressionFactory()) {
               throw LOG.getUnableToInitializeELExpressionFactoryException((Throwable)null);
            }

            expressionFactory = ELManager.getExpressionFactory();
            LOG.debug("Loaded expression factory via EL classloader");
            var2 = expressionFactory;
         } catch (Throwable var6) {
            throw LOG.getUnableToInitializeELExpressionFactoryException(var6);
         } finally {
            run(SetContextClassLoader.action(originalContextClassLoader));
         }

         return var2;
      }
   }

   private static boolean canLoadExpressionFactory() {
      try {
         ExpressionFactory.newInstance();
         return true;
      } catch (Throwable var1) {
         return false;
      }
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
