package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RemoteInvocation implements Serializable {
   private static final long serialVersionUID = 6876024250231820554L;
   private String methodName;
   private Class[] parameterTypes;
   private Object[] arguments;
   private Map attributes;

   public RemoteInvocation(MethodInvocation methodInvocation) {
      this.methodName = methodInvocation.getMethod().getName();
      this.parameterTypes = methodInvocation.getMethod().getParameterTypes();
      this.arguments = methodInvocation.getArguments();
   }

   public RemoteInvocation(String methodName, Class[] parameterTypes, Object[] arguments) {
      this.methodName = methodName;
      this.parameterTypes = parameterTypes;
      this.arguments = arguments;
   }

   public RemoteInvocation() {
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setParameterTypes(Class[] parameterTypes) {
      this.parameterTypes = parameterTypes;
   }

   public Class[] getParameterTypes() {
      return this.parameterTypes;
   }

   public void setArguments(Object[] arguments) {
      this.arguments = arguments;
   }

   public Object[] getArguments() {
      return this.arguments;
   }

   public void addAttribute(String key, Serializable value) throws IllegalStateException {
      if (this.attributes == null) {
         this.attributes = new HashMap();
      }

      if (this.attributes.containsKey(key)) {
         throw new IllegalStateException("There is already an attribute with key '" + key + "' bound");
      } else {
         this.attributes.put(key, value);
      }
   }

   @Nullable
   public Serializable getAttribute(String key) {
      return this.attributes == null ? null : (Serializable)this.attributes.get(key);
   }

   public void setAttributes(@Nullable Map attributes) {
      this.attributes = attributes;
   }

   @Nullable
   public Map getAttributes() {
      return this.attributes;
   }

   public Object invoke(Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Method method = targetObject.getClass().getMethod(this.methodName, this.parameterTypes);
      return method.invoke(targetObject, this.arguments);
   }

   public String toString() {
      return "RemoteInvocation: method name '" + this.methodName + "'; parameter types " + ClassUtils.classNamesToString(this.parameterTypes);
   }
}
