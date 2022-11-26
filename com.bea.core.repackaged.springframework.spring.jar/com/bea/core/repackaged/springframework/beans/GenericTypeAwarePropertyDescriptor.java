package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

final class GenericTypeAwarePropertyDescriptor extends PropertyDescriptor {
   private final Class beanClass;
   @Nullable
   private final Method readMethod;
   @Nullable
   private final Method writeMethod;
   @Nullable
   private volatile Set ambiguousWriteMethods;
   @Nullable
   private MethodParameter writeMethodParameter;
   @Nullable
   private Class propertyType;
   private final Class propertyEditorClass;

   public GenericTypeAwarePropertyDescriptor(Class beanClass, String propertyName, @Nullable Method readMethod, @Nullable Method writeMethod, Class propertyEditorClass) throws IntrospectionException {
      super(propertyName, (Method)null, (Method)null);
      this.beanClass = beanClass;
      Method readMethodToUse = readMethod != null ? BridgeMethodResolver.findBridgedMethod(readMethod) : null;
      Method writeMethodToUse = writeMethod != null ? BridgeMethodResolver.findBridgedMethod(writeMethod) : null;
      if (writeMethodToUse == null && readMethodToUse != null) {
         Method candidate = ClassUtils.getMethodIfAvailable(this.beanClass, "set" + StringUtils.capitalize(this.getName()), (Class[])null);
         if (candidate != null && candidate.getParameterCount() == 1) {
            writeMethodToUse = candidate;
         }
      }

      this.readMethod = readMethodToUse;
      this.writeMethod = writeMethodToUse;
      if (this.writeMethod != null) {
         if (this.readMethod == null) {
            Set ambiguousCandidates = new HashSet();
            Method[] var9 = beanClass.getMethods();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Method method = var9[var11];
               if (method.getName().equals(writeMethodToUse.getName()) && !method.equals(writeMethodToUse) && !method.isBridge() && method.getParameterCount() == writeMethodToUse.getParameterCount()) {
                  ambiguousCandidates.add(method);
               }
            }

            if (!ambiguousCandidates.isEmpty()) {
               this.ambiguousWriteMethods = ambiguousCandidates;
            }
         }

         this.writeMethodParameter = new MethodParameter(this.writeMethod, 0);
         GenericTypeResolver.resolveParameterType(this.writeMethodParameter, this.beanClass);
      }

      if (this.readMethod != null) {
         this.propertyType = GenericTypeResolver.resolveReturnType(this.readMethod, this.beanClass);
      } else if (this.writeMethodParameter != null) {
         this.propertyType = this.writeMethodParameter.getParameterType();
      }

      this.propertyEditorClass = propertyEditorClass;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   @Nullable
   public Method getReadMethod() {
      return this.readMethod;
   }

   @Nullable
   public Method getWriteMethod() {
      return this.writeMethod;
   }

   public Method getWriteMethodForActualAccess() {
      Assert.state(this.writeMethod != null, "No write method available");
      Set ambiguousCandidates = this.ambiguousWriteMethods;
      if (ambiguousCandidates != null) {
         this.ambiguousWriteMethods = null;
         LogFactory.getLog(GenericTypeAwarePropertyDescriptor.class).warn("Invalid JavaBean property '" + this.getName() + "' being accessed! Ambiguous write methods found next to actually used [" + this.writeMethod + "]: " + ambiguousCandidates);
      }

      return this.writeMethod;
   }

   public MethodParameter getWriteMethodParameter() {
      Assert.state(this.writeMethodParameter != null, "No write method available");
      return this.writeMethodParameter;
   }

   @Nullable
   public Class getPropertyType() {
      return this.propertyType;
   }

   public Class getPropertyEditorClass() {
      return this.propertyEditorClass;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof GenericTypeAwarePropertyDescriptor)) {
         return false;
      } else {
         GenericTypeAwarePropertyDescriptor otherPd = (GenericTypeAwarePropertyDescriptor)other;
         return this.getBeanClass().equals(otherPd.getBeanClass()) && PropertyDescriptorUtils.equals(this, otherPd);
      }
   }

   public int hashCode() {
      int hashCode = this.getBeanClass().hashCode();
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getReadMethod());
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.getWriteMethod());
      return hashCode;
   }
}
