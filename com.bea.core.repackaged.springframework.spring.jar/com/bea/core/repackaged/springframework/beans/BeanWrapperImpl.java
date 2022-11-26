package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.convert.Property;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;

public class BeanWrapperImpl extends AbstractNestablePropertyAccessor implements BeanWrapper {
   @Nullable
   private CachedIntrospectionResults cachedIntrospectionResults;
   @Nullable
   private AccessControlContext acc;

   public BeanWrapperImpl() {
      this(true);
   }

   public BeanWrapperImpl(boolean registerDefaultEditors) {
      super(registerDefaultEditors);
   }

   public BeanWrapperImpl(Object object) {
      super(object);
   }

   public BeanWrapperImpl(Class clazz) {
      super(clazz);
   }

   public BeanWrapperImpl(Object object, String nestedPath, Object rootObject) {
      super(object, nestedPath, rootObject);
   }

   private BeanWrapperImpl(Object object, String nestedPath, BeanWrapperImpl parent) {
      super(object, nestedPath, (AbstractNestablePropertyAccessor)parent);
      this.setSecurityContext(parent.acc);
   }

   public void setBeanInstance(Object object) {
      this.wrappedObject = object;
      this.rootObject = object;
      this.typeConverterDelegate = new TypeConverterDelegate(this, this.wrappedObject);
      this.setIntrospectionClass(object.getClass());
   }

   public void setWrappedInstance(Object object, @Nullable String nestedPath, @Nullable Object rootObject) {
      super.setWrappedInstance(object, nestedPath, rootObject);
      this.setIntrospectionClass(this.getWrappedClass());
   }

   protected void setIntrospectionClass(Class clazz) {
      if (this.cachedIntrospectionResults != null && this.cachedIntrospectionResults.getBeanClass() != clazz) {
         this.cachedIntrospectionResults = null;
      }

   }

   private CachedIntrospectionResults getCachedIntrospectionResults() {
      if (this.cachedIntrospectionResults == null) {
         this.cachedIntrospectionResults = CachedIntrospectionResults.forClass(this.getWrappedClass());
      }

      return this.cachedIntrospectionResults;
   }

   public void setSecurityContext(@Nullable AccessControlContext acc) {
      this.acc = acc;
   }

   @Nullable
   public AccessControlContext getSecurityContext() {
      return this.acc;
   }

   @Nullable
   public Object convertForProperty(@Nullable Object value, String propertyName) throws TypeMismatchException {
      CachedIntrospectionResults cachedIntrospectionResults = this.getCachedIntrospectionResults();
      PropertyDescriptor pd = cachedIntrospectionResults.getPropertyDescriptor(propertyName);
      if (pd == null) {
         throw new InvalidPropertyException(this.getRootClass(), this.getNestedPath() + propertyName, "No property '" + propertyName + "' found");
      } else {
         TypeDescriptor td = cachedIntrospectionResults.getTypeDescriptor(pd);
         if (td == null) {
            td = cachedIntrospectionResults.addTypeDescriptor(pd, new TypeDescriptor(this.property(pd)));
         }

         return this.convertForProperty(propertyName, (Object)null, value, td);
      }
   }

   private Property property(PropertyDescriptor pd) {
      GenericTypeAwarePropertyDescriptor gpd = (GenericTypeAwarePropertyDescriptor)pd;
      return new Property(gpd.getBeanClass(), gpd.getReadMethod(), gpd.getWriteMethod(), gpd.getName());
   }

   @Nullable
   protected BeanPropertyHandler getLocalPropertyHandler(String propertyName) {
      PropertyDescriptor pd = this.getCachedIntrospectionResults().getPropertyDescriptor(propertyName);
      return pd != null ? new BeanPropertyHandler(pd) : null;
   }

   protected BeanWrapperImpl newNestedPropertyAccessor(Object object, String nestedPath) {
      return new BeanWrapperImpl(object, nestedPath, this);
   }

   protected NotWritablePropertyException createNotWritablePropertyException(String propertyName) {
      PropertyMatches matches = PropertyMatches.forProperty(propertyName, this.getRootClass());
      throw new NotWritablePropertyException(this.getRootClass(), this.getNestedPath() + propertyName, matches.buildErrorMessage(), matches.getPossibleMatches());
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      return this.getCachedIntrospectionResults().getPropertyDescriptors();
   }

   public PropertyDescriptor getPropertyDescriptor(String propertyName) throws InvalidPropertyException {
      BeanWrapperImpl nestedBw = (BeanWrapperImpl)this.getPropertyAccessorForPropertyPath(propertyName);
      String finalPath = this.getFinalPath(nestedBw, propertyName);
      PropertyDescriptor pd = nestedBw.getCachedIntrospectionResults().getPropertyDescriptor(finalPath);
      if (pd == null) {
         throw new InvalidPropertyException(this.getRootClass(), this.getNestedPath() + propertyName, "No property '" + propertyName + "' found");
      } else {
         return pd;
      }
   }

   private class BeanPropertyHandler extends AbstractNestablePropertyAccessor.PropertyHandler {
      private final PropertyDescriptor pd;

      public BeanPropertyHandler(PropertyDescriptor pd) {
         super(pd.getPropertyType(), pd.getReadMethod() != null, pd.getWriteMethod() != null);
         this.pd = pd;
      }

      public ResolvableType getResolvableType() {
         return ResolvableType.forMethodReturnType(this.pd.getReadMethod());
      }

      public TypeDescriptor toTypeDescriptor() {
         return new TypeDescriptor(BeanWrapperImpl.this.property(this.pd));
      }

      @Nullable
      public TypeDescriptor nested(int level) {
         return TypeDescriptor.nested(BeanWrapperImpl.this.property(this.pd), level);
      }

      @Nullable
      public Object getValue() throws Exception {
         Method readMethod = this.pd.getReadMethod();
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
               ReflectionUtils.makeAccessible(readMethod);
               return null;
            });

            try {
               return AccessController.doPrivileged(() -> {
                  return readMethod.invoke(BeanWrapperImpl.this.getWrappedInstance(), (Object[])null);
               }, BeanWrapperImpl.this.acc);
            } catch (PrivilegedActionException var3) {
               throw var3.getException();
            }
         } else {
            ReflectionUtils.makeAccessible(readMethod);
            return readMethod.invoke(BeanWrapperImpl.this.getWrappedInstance(), (Object[])null);
         }
      }

      public void setValue(@Nullable Object value) throws Exception {
         Method writeMethod = this.pd instanceof GenericTypeAwarePropertyDescriptor ? ((GenericTypeAwarePropertyDescriptor)this.pd).getWriteMethodForActualAccess() : this.pd.getWriteMethod();
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
               ReflectionUtils.makeAccessible(writeMethod);
               return null;
            });

            try {
               AccessController.doPrivileged(() -> {
                  return writeMethod.invoke(BeanWrapperImpl.this.getWrappedInstance(), value);
               }, BeanWrapperImpl.this.acc);
            } catch (PrivilegedActionException var4) {
               throw var4.getException();
            }
         } else {
            ReflectionUtils.makeAccessible(writeMethod);
            writeMethod.invoke(BeanWrapperImpl.this.getWrappedInstance(), value);
         }

      }
   }
}
