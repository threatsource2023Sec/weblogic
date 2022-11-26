package org.glassfish.tyrus.core;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class AnnotatedClassValidityChecker {
   private final Class annotatedClass;
   private final List encoders;
   private final ErrorCollector collector;
   private final MessageHandlerManager handlerManager;

   public AnnotatedClassValidityChecker(Class annotatedClass, List encoders, List decoders, ErrorCollector collector) {
      this.annotatedClass = annotatedClass;
      this.encoders = encoders;
      this.collector = collector;
      this.handlerManager = new MessageHandlerManager(decoders);
   }

   public void checkOnMessageParams(Method method, MessageHandler handler) {
      try {
         this.handlerManager.addMessageHandler(handler);
      } catch (IllegalStateException var4) {
         this.collector.addException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_ADD_MESSAGE_HANDLER_ERROR(this.annotatedClass.getCanonicalName(), var4.getMessage()), var4.getCause()));
      }

      this.checkOnMessageReturnType(method);
   }

   private void checkOnMessageReturnType(Method method) {
      Class returnType = method.getReturnType();
      if (returnType != Void.TYPE && returnType != String.class && returnType != ByteBuffer.class && returnType != byte[].class && !returnType.isPrimitive() && this.checkEncoders(returnType) && !PrimitivesToWrappers.isPrimitiveWrapper(returnType)) {
         this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_RETURN_TYPE(this.annotatedClass.getName(), method.getName())));
      }

   }

   public void checkOnOpenParams(Method method, Map params) {
      Iterator var3 = params.values().iterator();

      while(var3.hasNext()) {
         Class value = (Class)var3.next();
         if (value != EndpointConfig.class) {
            this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_OPEN_PARAM(this.annotatedClass.getName(), method.getName(), value)));
         }
      }

   }

   public void checkOnCloseParams(Method method, Map params) {
      Iterator var3 = params.values().iterator();

      while(var3.hasNext()) {
         Class value = (Class)var3.next();
         if (value != CloseReason.class) {
            this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_CLOSE_PARAM(this.annotatedClass.getName(), method.getName())));
         }
      }

   }

   public void checkOnErrorParams(Method method, Map params) {
      boolean throwablePresent = false;
      Iterator var4 = params.values().iterator();

      while(var4.hasNext()) {
         Class value = (Class)var4.next();
         if (value != Throwable.class) {
            this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_FORBIDDEN_WEB_SOCKET_ERROR_PARAM(this.annotatedClass.getName(), method.getName(), value)));
         } else {
            if (throwablePresent) {
               this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_MULTIPLE_IDENTICAL_PARAMS(this.annotatedClass.getName(), method.getName())));
            }

            throwablePresent = true;
         }
      }

      if (!throwablePresent) {
         this.logDeploymentException(new DeploymentException(LocalizationMessages.CLASS_CHECKER_MANDATORY_PARAM_MISSING(this.annotatedClass.getName(), method.getName())));
      }

   }

   private String getPrefix(String methodName) {
      return String.format("Method:  %s.%s:", this.annotatedClass.getName(), methodName);
   }

   private boolean checkEncoders(Class requiredType) {
      Iterator var2 = this.encoders.iterator();

      Class encoderClass;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         encoderClass = (Class)var2.next();
      } while(!AnnotatedEndpoint.getEncoderClassType(encoderClass).isAssignableFrom(requiredType));

      return false;
   }

   private void logDeploymentException(DeploymentException de) {
      this.collector.addException(de);
   }
}
