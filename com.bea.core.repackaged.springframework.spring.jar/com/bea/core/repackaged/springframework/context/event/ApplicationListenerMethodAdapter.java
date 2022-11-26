package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.PayloadApplicationEvent;
import com.bea.core.repackaged.springframework.context.expression.AnnotatedElementKey;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.Order;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ApplicationListenerMethodAdapter implements GenericApplicationListener {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final String beanName;
   private final Method method;
   private final Method targetMethod;
   private final AnnotatedElementKey methodKey;
   private final List declaredEventTypes;
   @Nullable
   private final String condition;
   private final int order;
   @Nullable
   private ApplicationContext applicationContext;
   @Nullable
   private EventExpressionEvaluator evaluator;

   public ApplicationListenerMethodAdapter(String beanName, Class targetClass, Method method) {
      this.beanName = beanName;
      this.method = BridgeMethodResolver.findBridgedMethod(method);
      this.targetMethod = !Proxy.isProxyClass(targetClass) ? AopUtils.getMostSpecificMethod(method, targetClass) : this.method;
      this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
      EventListener ann = (EventListener)AnnotatedElementUtils.findMergedAnnotation(this.targetMethod, EventListener.class);
      this.declaredEventTypes = resolveDeclaredEventTypes(method, ann);
      this.condition = ann != null ? ann.condition() : null;
      this.order = resolveOrder(this.targetMethod);
   }

   private static List resolveDeclaredEventTypes(Method method, @Nullable EventListener ann) {
      int count = method.getParameterCount();
      if (count > 1) {
         throw new IllegalStateException("Maximum one parameter is allowed for event listener method: " + method);
      } else {
         if (ann != null) {
            Class[] classes = ann.classes();
            if (classes.length > 0) {
               List types = new ArrayList(classes.length);
               Class[] var5 = classes;
               int var6 = classes.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  Class eventType = var5[var7];
                  types.add(ResolvableType.forClass(eventType));
               }

               return types;
            }
         }

         if (count == 0) {
            throw new IllegalStateException("Event parameter is mandatory for event listener method: " + method);
         } else {
            return Collections.singletonList(ResolvableType.forMethodParameter(method, 0));
         }
      }
   }

   private static int resolveOrder(Method method) {
      Order ann = (Order)AnnotatedElementUtils.findMergedAnnotation(method, Order.class);
      return ann != null ? ann.value() : 0;
   }

   void init(ApplicationContext applicationContext, EventExpressionEvaluator evaluator) {
      this.applicationContext = applicationContext;
      this.evaluator = evaluator;
   }

   public void onApplicationEvent(ApplicationEvent event) {
      this.processEvent(event);
   }

   public boolean supportsEventType(ResolvableType eventType) {
      Iterator var2 = this.declaredEventTypes.iterator();

      while(var2.hasNext()) {
         ResolvableType declaredEventType = (ResolvableType)var2.next();
         if (declaredEventType.isAssignableFrom(eventType)) {
            return true;
         }

         if (PayloadApplicationEvent.class.isAssignableFrom(eventType.toClass())) {
            ResolvableType payloadType = eventType.as(PayloadApplicationEvent.class).getGeneric();
            if (declaredEventType.isAssignableFrom(payloadType)) {
               return true;
            }
         }
      }

      return eventType.hasUnresolvableGenerics();
   }

   public boolean supportsSourceType(@Nullable Class sourceType) {
      return true;
   }

   public int getOrder() {
      return this.order;
   }

   public void processEvent(ApplicationEvent event) {
      Object[] args = this.resolveArguments(event);
      if (this.shouldHandle(event, args)) {
         Object result = this.doInvoke(args);
         if (result != null) {
            this.handleResult(result);
         } else {
            this.logger.trace("No result object given - no result to handle");
         }
      }

   }

   @Nullable
   protected Object[] resolveArguments(ApplicationEvent event) {
      ResolvableType declaredEventType = this.getResolvableType(event);
      if (declaredEventType == null) {
         return null;
      } else if (this.method.getParameterCount() == 0) {
         return new Object[0];
      } else {
         Class declaredEventClass = declaredEventType.toClass();
         if (!ApplicationEvent.class.isAssignableFrom(declaredEventClass) && event instanceof PayloadApplicationEvent) {
            Object payload = ((PayloadApplicationEvent)event).getPayload();
            if (declaredEventClass.isInstance(payload)) {
               return new Object[]{payload};
            }
         }

         return new Object[]{event};
      }
   }

   protected void handleResult(Object result) {
      if (result.getClass().isArray()) {
         Object[] events = ObjectUtils.toObjectArray(result);
         Object[] var3 = events;
         int var4 = events.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object event = var3[var5];
            this.publishEvent(event);
         }
      } else if (result instanceof Collection) {
         Collection events = (Collection)result;
         Iterator var8 = events.iterator();

         while(var8.hasNext()) {
            Object event = var8.next();
            this.publishEvent(event);
         }
      } else {
         this.publishEvent(result);
      }

   }

   private void publishEvent(@Nullable Object event) {
      if (event != null) {
         Assert.notNull(this.applicationContext, (String)"ApplicationContext must not be null");
         this.applicationContext.publishEvent(event);
      }

   }

   private boolean shouldHandle(ApplicationEvent event, @Nullable Object[] args) {
      if (args == null) {
         return false;
      } else {
         String condition = this.getCondition();
         if (StringUtils.hasText(condition)) {
            Assert.notNull(this.evaluator, (String)"EventExpressionEvaluator must not be null");
            return this.evaluator.condition(condition, event, this.targetMethod, this.methodKey, args, this.applicationContext);
         } else {
            return true;
         }
      }
   }

   @Nullable
   protected Object doInvoke(Object... args) {
      Object bean = this.getTargetBean();
      ReflectionUtils.makeAccessible(this.method);

      try {
         return this.method.invoke(bean, args);
      } catch (IllegalArgumentException var6) {
         this.assertTargetBean(this.method, bean, args);
         throw new IllegalStateException(this.getInvocationErrorMessage(bean, var6.getMessage(), args), var6);
      } catch (IllegalAccessException var7) {
         throw new IllegalStateException(this.getInvocationErrorMessage(bean, var7.getMessage(), args), var7);
      } catch (InvocationTargetException var8) {
         Throwable targetException = var8.getTargetException();
         if (targetException instanceof RuntimeException) {
            throw (RuntimeException)targetException;
         } else {
            String msg = this.getInvocationErrorMessage(bean, "Failed to invoke event listener method", args);
            throw new UndeclaredThrowableException(targetException, msg);
         }
      }
   }

   protected Object getTargetBean() {
      Assert.notNull(this.applicationContext, (String)"ApplicationContext must no be null");
      return this.applicationContext.getBean(this.beanName);
   }

   @Nullable
   protected String getCondition() {
      return this.condition;
   }

   protected String getDetailedErrorMessage(Object bean, String message) {
      StringBuilder sb = (new StringBuilder(message)).append("\n");
      sb.append("HandlerMethod details: \n");
      sb.append("Bean [").append(bean.getClass().getName()).append("]\n");
      sb.append("Method [").append(this.method.toGenericString()).append("]\n");
      return sb.toString();
   }

   private void assertTargetBean(Method method, Object targetBean, Object[] args) {
      Class methodDeclaringClass = method.getDeclaringClass();
      Class targetBeanClass = targetBean.getClass();
      if (!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
         String msg = "The event listener method class '" + methodDeclaringClass.getName() + "' is not an instance of the actual bean class '" + targetBeanClass.getName() + "'. If the bean requires proxying (e.g. due to @Transactional), please use class-based proxying.";
         throw new IllegalStateException(this.getInvocationErrorMessage(targetBean, msg, args));
      }
   }

   private String getInvocationErrorMessage(Object bean, String message, Object[] resolvedArgs) {
      StringBuilder sb = new StringBuilder(this.getDetailedErrorMessage(bean, message));
      sb.append("Resolved arguments: \n");

      for(int i = 0; i < resolvedArgs.length; ++i) {
         sb.append("[").append(i).append("] ");
         if (resolvedArgs[i] == null) {
            sb.append("[null] \n");
         } else {
            sb.append("[type=").append(resolvedArgs[i].getClass().getName()).append("] ");
            sb.append("[value=").append(resolvedArgs[i]).append("]\n");
         }
      }

      return sb.toString();
   }

   @Nullable
   private ResolvableType getResolvableType(ApplicationEvent event) {
      ResolvableType payloadType = null;
      ResolvableType declaredEventType;
      if (event instanceof PayloadApplicationEvent) {
         PayloadApplicationEvent payloadEvent = (PayloadApplicationEvent)event;
         declaredEventType = payloadEvent.getResolvableType();
         if (declaredEventType != null) {
            payloadType = declaredEventType.as(PayloadApplicationEvent.class).getGeneric();
         }
      }

      Iterator var6 = this.declaredEventTypes.iterator();

      Class eventClass;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         declaredEventType = (ResolvableType)var6.next();
         eventClass = declaredEventType.toClass();
         if (!ApplicationEvent.class.isAssignableFrom(eventClass) && payloadType != null && declaredEventType.isAssignableFrom(payloadType)) {
            return declaredEventType;
         }
      } while(!eventClass.isInstance(event));

      return declaredEventType;
   }

   public String toString() {
      return this.method.toGenericString();
   }
}
