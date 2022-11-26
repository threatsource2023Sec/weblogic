package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeDataTypeConversionException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttributeReference;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ConfigurableAttributeReferenceInjectionResolver implements InjectionResolver {
   private final Provider delegate;
   private final Provider provisioningComponentRepositoryProvider;

   /** @deprecated */
   @Deprecated
   public ConfigurableAttributeReferenceInjectionResolver(Provider delegate) {
      this(delegate, (Provider)null);
   }

   @Inject
   public ConfigurableAttributeReferenceInjectionResolver(Provider delegate, @Optional Provider provisioningComponentRepositoryProvider) {
      Objects.requireNonNull(delegate);
      this.delegate = delegate;
      this.provisioningComponentRepositoryProvider = provisioningComponentRepositoryProvider;
   }

   public Object resolve(Injectee injectee, ServiceHandle rootServiceHandle) {
      String className = ConfigurableAttributeReferenceInjectionResolver.class.getName();
      String methodName = "resolve";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "resolve", new Object[]{injectee, rootServiceHandle});
      }

      Objects.requireNonNull(injectee);
      Object returnValue = null;
      AnnotatedElement parent = injectee.getParent();
      if (parent != null) {
         ConfigurableAttributeReference caRef = null;
         int position = injectee.getPosition();
         if (position >= 0) {
            Annotation[] parameterAnnotations;
            if (parent instanceof Method) {
               parameterAnnotations = ((Method)parent).getParameterAnnotations()[position];
            } else {
               if (!(parent instanceof Constructor)) {
                  throw new IllegalStateException("injectee.getPosition() == " + position + " && !(parent instanceof Method) && !(parent instanceof Constructor): " + parent);
               }

               parameterAnnotations = ((Constructor)parent).getParameterAnnotations()[position];
            }

            assert parameterAnnotations != null;

            Annotation[] var11 = parameterAnnotations;
            int var12 = parameterAnnotations.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Annotation parameterAnnotation = var11[var13];
               if (parameterAnnotation instanceof ConfigurableAttributeReference) {
                  caRef = (ConfigurableAttributeReference)parameterAnnotation;
                  break;
               }
            }
         } else {
            caRef = (ConfigurableAttributeReference)parent.getAnnotation(ConfigurableAttributeReference.class);
         }

         if (caRef != null) {
            String attributeName = caRef.attribute();
            if (attributeName != null && !attributeName.trim().isEmpty()) {
               String componentName = caRef.component();
               if (componentName != null && !componentName.trim().isEmpty()) {
                  assert this.delegate != null;

                  ConfigurableAttributeValueProvider configurableAttributeValueProvider = (ConfigurableAttributeValueProvider)this.delegate.get();

                  assert configurableAttributeValueProvider != null;

                  String value;
                  if (configurableAttributeValueProvider.containsConfigurableAttributeValue(componentName, attributeName)) {
                     value = configurableAttributeValueProvider.getConfigurableAttributeValue(componentName, attributeName);
                  } else {
                     String defaultValue;
                     if (this.provisioningComponentRepositoryProvider == null) {
                        defaultValue = "";
                     } else {
                        ProvisioningComponentRepository provisioningComponentRepository = (ProvisioningComponentRepository)this.provisioningComponentRepositoryProvider.get();

                        assert provisioningComponentRepository != null;

                        ConfigurableAttribute ca = null;

                        try {
                           ca = provisioningComponentRepository.getConfigurableAttribute(componentName, attributeName);
                        } catch (ProvisioningException var19) {
                           throw new IllegalStateException(var19);
                        }

                        if (ca == null) {
                           defaultValue = "";
                        } else {
                           defaultValue = ca.defaultValue();
                        }
                     }

                     value = defaultValue;
                  }

                  try {
                     returnValue = ConfigurableAttributeInjectionResolver.convertToType(injectee.getRequiredType(), value);
                  } catch (IllegalArgumentException var18) {
                     throw new ConfigurableAttributeDataTypeConversionException(componentName, attributeName, value, injectee.getRequiredType(), var18);
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "resolve", returnValue);
      }

      return returnValue;
   }

   public boolean isConstructorParameterIndicator() {
      return true;
   }

   public boolean isMethodParameterIndicator() {
      return true;
   }
}
