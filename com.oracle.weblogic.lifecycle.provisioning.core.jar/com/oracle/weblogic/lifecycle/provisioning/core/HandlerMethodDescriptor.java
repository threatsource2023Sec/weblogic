package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.api.messaging.SubscribeTo;

final class HandlerMethodDescriptor implements ResourceHandlingMethodDescriptor {
   private static final Annotation INITIAL_PROVISIONING_OPERATION_LITERAL = new InitialProvisioningOperationLiteral();
   private final ProvisioningComponentIdentifier componentId;
   private final HandlesResources handlesResourcesQualifier;
   private final Method handlerMethod;
   private final Class handlerClass;
   private final Annotation provisioningOperationQualifier;

   HandlerMethodDescriptor(ProvisioningComponentIdentifier componentId, Method handlerMethod, Class handlerClass) {
      this.componentId = componentId;
      this.handlerMethod = handlerMethod;
      this.handlerClass = handlerClass;
      if (handlerMethod == null) {
         this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
         this.handlesResourcesQualifier = null;
      } else {
         Class[] parameterTypes = handlerMethod.getParameterTypes();
         if (parameterTypes != null && parameterTypes.length > 0) {
            int provisioningEventParameterIndex = -1;

            for(int i = 0; i < parameterTypes.length; ++i) {
               Class parameterType = parameterTypes[i];
               if (ProvisioningEvent.class.isAssignableFrom(parameterType)) {
                  provisioningEventParameterIndex = i;
                  break;
               }
            }

            if (provisioningEventParameterIndex < 0) {
               this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
               this.handlesResourcesQualifier = null;
            } else {
               Annotation[][] annotationsForAllParameters = handlerMethod.getParameterAnnotations();
               if (annotationsForAllParameters != null && annotationsForAllParameters.length > 0) {
                  Annotation[] provisioningEventAnnotations = annotationsForAllParameters[provisioningEventParameterIndex];
                  if (provisioningEventAnnotations != null && provisioningEventAnnotations.length > 0) {
                     HandlesResources handlesResourcesQualifier = null;
                     Annotation provisioningOperationQualifier = null;
                     Annotation[] var10 = provisioningEventAnnotations;
                     int var11 = provisioningEventAnnotations.length;

                     for(int var12 = 0; var12 < var11; ++var12) {
                        Annotation provisioningEventAnnotation = var10[var12];
                        if (provisioningEventAnnotation != null) {
                           if (ProvisioningOperationDescriptor.isProvisioningOperationQualifier(provisioningEventAnnotation)) {
                              provisioningOperationQualifier = provisioningEventAnnotation;
                           } else if (!(provisioningEventAnnotation instanceof SubscribeTo)) {
                              handlesResourcesQualifier = (HandlesResources)provisioningEventAnnotation.annotationType().getAnnotation(HandlesResources.class);
                           }
                        }
                     }

                     this.handlesResourcesQualifier = handlesResourcesQualifier;
                     if (provisioningOperationQualifier == null) {
                        this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
                     } else {
                        this.provisioningOperationQualifier = provisioningOperationQualifier;
                     }
                  } else {
                     this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
                     this.handlesResourcesQualifier = null;
                  }
               } else {
                  this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
                  this.handlesResourcesQualifier = null;
               }
            }
         } else {
            this.provisioningOperationQualifier = INITIAL_PROVISIONING_OPERATION_LITERAL;
            this.handlesResourcesQualifier = null;
         }
      }

   }

   public final ProvisioningComponentIdentifier getComponentId() {
      return this.componentId;
   }

   public final HandlesResources getHandlesResourcesQualifier() {
      return this.handlesResourcesQualifier;
   }

   public final boolean isEnabled() {
      HandlesResources handlesResources = this.getHandlesResourcesQualifier();
      return handlesResources != null && handlesResources.enabled();
   }

   public final Method getMethod() {
      return this.handlerMethod;
   }

   public final Class getHandlerClass() {
      return this.handlerClass;
   }

   public final Annotation getProvisioningOperationQualifier() {
      return this.provisioningOperationQualifier;
   }

   public final String toString() {
      StringBuilder sb = new StringBuilder("HandlerMethodDescriptor{handlesResourcesQualifier={");
      HandlesResources handlesResourcesQualifier = this.getHandlesResourcesQualifier();
      if (handlesResourcesQualifier == null) {
         sb.append("null");
      } else {
         sb.append(handlesResourcesQualifier.enabled());
         sb.append(" & ");
         sb.append(Arrays.asList(handlesResourcesQualifier.value()));
      }

      sb.append("}, handlerClass=");
      Class handlerClass = this.getHandlerClass();
      if (handlerClass == null) {
         sb.append("null");
      } else {
         sb.append(handlerClass.getName());
      }

      sb.append(", provisioningOperationQualifier=");
      sb.append(this.getProvisioningOperationQualifier());
      sb.append("}");
      return sb.toString();
   }

   private static final class InitialProvisioningOperationLiteral extends AnnotationLiteral implements InitialProvisioningOperation {
      private static final long serialVersionUID = 1L;

      private InitialProvisioningOperationLiteral() {
      }

      // $FF: synthetic method
      InitialProvisioningOperationLiteral(Object x0) {
         this();
      }
   }

   interface Filter {
      boolean accept(HandlerMethodDescriptor var1);
   }
}
