package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.BeanResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.MethodResolver;
import com.bea.core.repackaged.springframework.expression.OperatorOverloader;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypeComparator;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypeLocator;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SimpleEvaluationContext implements EvaluationContext {
   private static final TypeLocator typeNotFoundTypeLocator = (typeName) -> {
      throw new SpelEvaluationException(SpelMessage.TYPE_NOT_FOUND, new Object[]{typeName});
   };
   private final TypedValue rootObject;
   private final List propertyAccessors;
   private final List methodResolvers;
   private final TypeConverter typeConverter;
   private final TypeComparator typeComparator;
   private final OperatorOverloader operatorOverloader;
   private final Map variables;

   private SimpleEvaluationContext(List accessors, List resolvers, @Nullable TypeConverter converter, @Nullable TypedValue rootObject) {
      this.typeComparator = new StandardTypeComparator();
      this.operatorOverloader = new StandardOperatorOverloader();
      this.variables = new HashMap();
      this.propertyAccessors = accessors;
      this.methodResolvers = resolvers;
      this.typeConverter = (TypeConverter)(converter != null ? converter : new StandardTypeConverter());
      this.rootObject = rootObject != null ? rootObject : TypedValue.NULL;
   }

   public TypedValue getRootObject() {
      return this.rootObject;
   }

   public List getPropertyAccessors() {
      return this.propertyAccessors;
   }

   public List getConstructorResolvers() {
      return Collections.emptyList();
   }

   public List getMethodResolvers() {
      return this.methodResolvers;
   }

   @Nullable
   public BeanResolver getBeanResolver() {
      return null;
   }

   public TypeLocator getTypeLocator() {
      return typeNotFoundTypeLocator;
   }

   public TypeConverter getTypeConverter() {
      return this.typeConverter;
   }

   public TypeComparator getTypeComparator() {
      return this.typeComparator;
   }

   public OperatorOverloader getOperatorOverloader() {
      return this.operatorOverloader;
   }

   public void setVariable(String name, @Nullable Object value) {
      this.variables.put(name, value);
   }

   @Nullable
   public Object lookupVariable(String name) {
      return this.variables.get(name);
   }

   public static Builder forPropertyAccessors(PropertyAccessor... accessors) {
      PropertyAccessor[] var1 = accessors;
      int var2 = accessors.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PropertyAccessor accessor = var1[var3];
         if (accessor.getClass() == ReflectivePropertyAccessor.class) {
            throw new IllegalArgumentException("SimpleEvaluationContext is not designed for use with a plain ReflectivePropertyAccessor. Consider using DataBindingPropertyAccessor or a custom subclass.");
         }
      }

      return new Builder(accessors);
   }

   public static Builder forReadOnlyDataBinding() {
      return new Builder(new PropertyAccessor[]{DataBindingPropertyAccessor.forReadOnlyAccess()});
   }

   public static Builder forReadWriteDataBinding() {
      return new Builder(new PropertyAccessor[]{DataBindingPropertyAccessor.forReadWriteAccess()});
   }

   // $FF: synthetic method
   SimpleEvaluationContext(List x0, List x1, TypeConverter x2, TypedValue x3, Object x4) {
      this(x0, x1, x2, x3);
   }

   public static class Builder {
      private final List accessors;
      private List resolvers = Collections.emptyList();
      @Nullable
      private TypeConverter typeConverter;
      @Nullable
      private TypedValue rootObject;

      public Builder(PropertyAccessor... accessors) {
         this.accessors = Arrays.asList(accessors);
      }

      public Builder withMethodResolvers(MethodResolver... resolvers) {
         MethodResolver[] var2 = resolvers;
         int var3 = resolvers.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MethodResolver resolver = var2[var4];
            if (resolver.getClass() == ReflectiveMethodResolver.class) {
               throw new IllegalArgumentException("SimpleEvaluationContext is not designed for use with a plain ReflectiveMethodResolver. Consider using DataBindingMethodResolver or a custom subclass.");
            }
         }

         this.resolvers = Arrays.asList(resolvers);
         return this;
      }

      public Builder withInstanceMethods() {
         this.resolvers = Collections.singletonList(DataBindingMethodResolver.forInstanceMethodInvocation());
         return this;
      }

      public Builder withConversionService(ConversionService conversionService) {
         this.typeConverter = new StandardTypeConverter(conversionService);
         return this;
      }

      public Builder withTypeConverter(TypeConverter converter) {
         this.typeConverter = converter;
         return this;
      }

      public Builder withRootObject(Object rootObject) {
         this.rootObject = new TypedValue(rootObject);
         return this;
      }

      public Builder withTypedRootObject(Object rootObject, TypeDescriptor typeDescriptor) {
         this.rootObject = new TypedValue(rootObject, typeDescriptor);
         return this;
      }

      public SimpleEvaluationContext build() {
         return new SimpleEvaluationContext(this.accessors, this.resolvers, this.typeConverter, this.rootObject);
      }
   }
}
