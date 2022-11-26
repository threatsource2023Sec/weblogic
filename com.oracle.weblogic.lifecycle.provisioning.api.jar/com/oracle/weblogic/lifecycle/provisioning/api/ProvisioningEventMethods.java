package com.oracle.weblogic.lifecycle.provisioning.api;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.messaging.SubscribeTo;

public final class ProvisioningEventMethods {
   private ProvisioningEventMethods() {
   }

   public static final HandlesResources getHandlesResources(Method method) {
      HandlesResources returnValue = null;
      if (method != null) {
         Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
         if (allParameterAnnotations != null && allParameterAnnotations.length > 0) {
            int provisioningEventParameterIndex = -1;
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length > 0) {
               for(int i = 0; i < parameterTypes.length; ++i) {
                  Class parameterType = parameterTypes[i];
                  if (ProvisioningEvent.class.isAssignableFrom(parameterType)) {
                     provisioningEventParameterIndex = i;
                     break;
                  }
               }
            }

            if (provisioningEventParameterIndex >= 0) {
               Annotation[] provisioningEventParameterAnnotations = allParameterAnnotations[provisioningEventParameterIndex];
               if (provisioningEventParameterAnnotations != null && provisioningEventParameterAnnotations.length > 0) {
                  Annotation[] var13 = provisioningEventParameterAnnotations;
                  int var7 = provisioningEventParameterAnnotations.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     Annotation parameterAnnotation = var13[var8];

                     assert parameterAnnotation != null;

                     Class annotationType = parameterAnnotation.annotationType();

                     assert annotationType != null;

                     HandlesResources handlesResources = (HandlesResources)annotationType.getAnnotation(HandlesResources.class);
                     if (handlesResources != null) {
                        returnValue = handlesResources;
                        break;
                     }
                  }
               }
            }
         }
      }

      return returnValue;
   }

   public static final boolean isHandlerMethod(Method method) {
      String className = ProvisioningEventMethods.class.getName();
      String methodName = "isHandlerMethod";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "isHandlerMethod", method);
      }

      if (method == null) {
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
         }

         return false;
      } else {
         int modifiers = method.getModifiers();
         if (!Modifier.isPublic(modifiers)) {
            if (logger != null) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it is not public.", method);
               }

               if (logger.isLoggable(Level.FINER)) {
                  logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
               }
            }

            return false;
         } else if (Modifier.isFinal(modifiers)) {
            if (logger != null) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it is final and hence cannot be proxied.", method);
               }

               if (logger.isLoggable(Level.FINER)) {
                  logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
               }
            }

            return false;
         } else if (Modifier.isStatic(modifiers)) {
            if (logger != null) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it is static.", method);
               }

               if (logger.isLoggable(Level.FINER)) {
                  logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
               }
            }

            return false;
         } else if (!Void.TYPE.equals(method.getReturnType())) {
            if (logger != null) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it does not have a void return type.", method);
               }

               if (logger.isLoggable(Level.FINER)) {
                  logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
               }
            }

            return false;
         } else {
            Class[] parameterTypes = method.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length > 0) {
               if (parameterTypes.length > 1) {
                  if (logger != null) {
                     if (logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it has more than one parameter. This is a temporary limitation until handler method parameter injection is implemented.", method);
                     }

                     if (logger.isLoggable(Level.FINER)) {
                        logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                     }
                  }

                  return false;
               } else {
                  int provisioningEventParameterIndex = -1;

                  for(int i = 0; i < parameterTypes.length; ++i) {
                     Class parameterType = parameterTypes[i];
                     if (ProvisioningEvent.class.isAssignableFrom(parameterType)) {
                        provisioningEventParameterIndex = i;
                        break;
                     }
                  }

                  if (provisioningEventParameterIndex < 0) {
                     if (logger != null) {
                        if (logger.isLoggable(Level.FINE)) {
                           logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it has no ProvisioningEvent parameter.", method);
                        }

                        if (logger.isLoggable(Level.FINER)) {
                           logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                        }
                     }

                     return false;
                  } else {
                     Annotation[][] annotationsForAllParameters = method.getParameterAnnotations();
                     if (annotationsForAllParameters != null && annotationsForAllParameters.length > 0) {
                        Annotation[] soleParameterAnnotations = annotationsForAllParameters[provisioningEventParameterIndex];
                        if (soleParameterAnnotations != null && soleParameterAnnotations.length > 0) {
                           boolean subscribeToAnnotationFound = false;
                           HandlesResources handlesResourcesQualifier = null;
                           Annotation[] var11 = soleParameterAnnotations;
                           int var12 = soleParameterAnnotations.length;

                           for(int var13 = 0; var13 < var12; ++var13) {
                              Annotation parameterAnnotation = var11[var13];
                              if (parameterAnnotation != null) {
                                 if (parameterAnnotation instanceof SubscribeTo) {
                                    subscribeToAnnotationFound = true;
                                 } else if (!ProvisioningOperationDescriptor.isProvisioningOperationQualifier(parameterAnnotation)) {
                                    HandlesResources candidateHandlesResourcesQualifier = (HandlesResources)parameterAnnotation.annotationType().getAnnotation(HandlesResources.class);
                                    if (candidateHandlesResourcesQualifier != null) {
                                       handlesResourcesQualifier = candidateHandlesResourcesQualifier;
                                    }
                                 }
                              }
                           }

                           if (!subscribeToAnnotationFound) {
                              if (logger != null) {
                                 if (logger.isLoggable(Level.FINE)) {
                                    logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because its ProvisioningEvent parameter is not annotated with SubscribeTo.", method);
                                 }

                                 if (logger.isLoggable(Level.FINER)) {
                                    logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                                 }
                              }

                              return false;
                           } else if (handlesResourcesQualifier == null) {
                              if (logger != null) {
                                 if (logger.isLoggable(Level.FINE)) {
                                    logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because its ProvisioningEvent parameter is not annotated with a qualifier annotation that is annotated with HandlesResources.", method);
                                 }

                                 if (logger.isLoggable(Level.FINER)) {
                                    logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                                 }
                              }

                              return false;
                           } else {
                              return true;
                           }
                        } else {
                           if (logger != null) {
                              if (logger.isLoggable(Level.FINE)) {
                                 logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because its ProvisioningEvent parameter is not annotated.", method);
                              }

                              if (logger.isLoggable(Level.FINER)) {
                                 logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                              }
                           }

                           return false;
                        }
                     } else {
                        if (logger != null) {
                           if (logger.isLoggable(Level.FINE)) {
                              logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because its ProvisioningEvent parameter is not annotated.", method);
                           }

                           if (logger.isLoggable(Level.FINER)) {
                              logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                           }
                        }

                        return false;
                     }
                  }
               }
            } else {
               if (logger != null) {
                  if (logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "isHandlerMethod", "Determined that {0} is not a handler method because it has no parameters.", method);
                  }

                  if (logger.isLoggable(Level.FINER)) {
                     logger.exiting(className, "isHandlerMethod", Boolean.FALSE);
                  }
               }

               return false;
            }
         }
      }
   }
}
