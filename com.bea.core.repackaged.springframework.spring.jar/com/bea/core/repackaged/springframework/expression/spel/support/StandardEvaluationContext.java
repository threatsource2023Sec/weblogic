package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.BeanResolver;
import com.bea.core.repackaged.springframework.expression.ConstructorResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.MethodFilter;
import com.bea.core.repackaged.springframework.expression.MethodResolver;
import com.bea.core.repackaged.springframework.expression.OperatorOverloader;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.expression.TypeComparator;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.TypeLocator;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardEvaluationContext implements EvaluationContext {
   private TypedValue rootObject;
   @Nullable
   private volatile List propertyAccessors;
   @Nullable
   private volatile List constructorResolvers;
   @Nullable
   private volatile List methodResolvers;
   @Nullable
   private volatile ReflectiveMethodResolver reflectiveMethodResolver;
   @Nullable
   private BeanResolver beanResolver;
   @Nullable
   private TypeLocator typeLocator;
   @Nullable
   private TypeConverter typeConverter;
   private TypeComparator typeComparator = new StandardTypeComparator();
   private OperatorOverloader operatorOverloader = new StandardOperatorOverloader();
   private final Map variables = new ConcurrentHashMap();

   public StandardEvaluationContext() {
      this.rootObject = TypedValue.NULL;
   }

   public StandardEvaluationContext(Object rootObject) {
      this.rootObject = new TypedValue(rootObject);
   }

   public void setRootObject(Object rootObject, TypeDescriptor typeDescriptor) {
      this.rootObject = new TypedValue(rootObject, typeDescriptor);
   }

   public void setRootObject(@Nullable Object rootObject) {
      this.rootObject = rootObject != null ? new TypedValue(rootObject) : TypedValue.NULL;
   }

   public TypedValue getRootObject() {
      return this.rootObject;
   }

   public void setPropertyAccessors(List propertyAccessors) {
      this.propertyAccessors = propertyAccessors;
   }

   public List getPropertyAccessors() {
      return this.initPropertyAccessors();
   }

   public void addPropertyAccessor(PropertyAccessor accessor) {
      addBeforeDefault(this.initPropertyAccessors(), accessor);
   }

   public boolean removePropertyAccessor(PropertyAccessor accessor) {
      return this.initPropertyAccessors().remove(accessor);
   }

   public void setConstructorResolvers(List constructorResolvers) {
      this.constructorResolvers = constructorResolvers;
   }

   public List getConstructorResolvers() {
      return this.initConstructorResolvers();
   }

   public void addConstructorResolver(ConstructorResolver resolver) {
      addBeforeDefault(this.initConstructorResolvers(), resolver);
   }

   public boolean removeConstructorResolver(ConstructorResolver resolver) {
      return this.initConstructorResolvers().remove(resolver);
   }

   public void setMethodResolvers(List methodResolvers) {
      this.methodResolvers = methodResolvers;
   }

   public List getMethodResolvers() {
      return this.initMethodResolvers();
   }

   public void addMethodResolver(MethodResolver resolver) {
      addBeforeDefault(this.initMethodResolvers(), resolver);
   }

   public boolean removeMethodResolver(MethodResolver methodResolver) {
      return this.initMethodResolvers().remove(methodResolver);
   }

   public void setBeanResolver(BeanResolver beanResolver) {
      this.beanResolver = beanResolver;
   }

   @Nullable
   public BeanResolver getBeanResolver() {
      return this.beanResolver;
   }

   public void setTypeLocator(TypeLocator typeLocator) {
      Assert.notNull(typeLocator, (String)"TypeLocator must not be null");
      this.typeLocator = typeLocator;
   }

   public TypeLocator getTypeLocator() {
      if (this.typeLocator == null) {
         this.typeLocator = new StandardTypeLocator();
      }

      return this.typeLocator;
   }

   public void setTypeConverter(TypeConverter typeConverter) {
      Assert.notNull(typeConverter, (String)"TypeConverter must not be null");
      this.typeConverter = typeConverter;
   }

   public TypeConverter getTypeConverter() {
      if (this.typeConverter == null) {
         this.typeConverter = new StandardTypeConverter();
      }

      return this.typeConverter;
   }

   public void setTypeComparator(TypeComparator typeComparator) {
      Assert.notNull(typeComparator, (String)"TypeComparator must not be null");
      this.typeComparator = typeComparator;
   }

   public TypeComparator getTypeComparator() {
      return this.typeComparator;
   }

   public void setOperatorOverloader(OperatorOverloader operatorOverloader) {
      Assert.notNull(operatorOverloader, (String)"OperatorOverloader must not be null");
      this.operatorOverloader = operatorOverloader;
   }

   public OperatorOverloader getOperatorOverloader() {
      return this.operatorOverloader;
   }

   public void setVariable(@Nullable String name, @Nullable Object value) {
      if (name != null) {
         if (value != null) {
            this.variables.put(name, value);
         } else {
            this.variables.remove(name);
         }
      }

   }

   public void setVariables(Map variables) {
      variables.forEach(this::setVariable);
   }

   public void registerFunction(String name, Method method) {
      this.variables.put(name, method);
   }

   @Nullable
   public Object lookupVariable(String name) {
      return this.variables.get(name);
   }

   public void registerMethodFilter(Class type, MethodFilter filter) throws IllegalStateException {
      this.initMethodResolvers();
      ReflectiveMethodResolver resolver = this.reflectiveMethodResolver;
      if (resolver == null) {
         throw new IllegalStateException("Method filter cannot be set as the reflective method resolver is not in use");
      } else {
         resolver.registerMethodFilter(type, filter);
      }
   }

   private List initPropertyAccessors() {
      List accessors = this.propertyAccessors;
      if (accessors == null) {
         accessors = new ArrayList(5);
         ((List)accessors).add(new ReflectivePropertyAccessor());
         this.propertyAccessors = (List)accessors;
      }

      return (List)accessors;
   }

   private List initConstructorResolvers() {
      List resolvers = this.constructorResolvers;
      if (resolvers == null) {
         resolvers = new ArrayList(1);
         ((List)resolvers).add(new ReflectiveConstructorResolver());
         this.constructorResolvers = (List)resolvers;
      }

      return (List)resolvers;
   }

   private List initMethodResolvers() {
      List resolvers = this.methodResolvers;
      if (resolvers == null) {
         resolvers = new ArrayList(1);
         this.reflectiveMethodResolver = new ReflectiveMethodResolver();
         ((List)resolvers).add(this.reflectiveMethodResolver);
         this.methodResolvers = (List)resolvers;
      }

      return (List)resolvers;
   }

   private static void addBeforeDefault(List resolvers, Object resolver) {
      resolvers.add(resolvers.size() - 1, resolver);
   }
}
