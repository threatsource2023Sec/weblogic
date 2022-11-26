package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DirectFieldAccessor extends AbstractNestablePropertyAccessor {
   private final Map fieldMap = new HashMap();

   public DirectFieldAccessor(Object object) {
      super(object);
   }

   protected DirectFieldAccessor(Object object, String nestedPath, DirectFieldAccessor parent) {
      super(object, nestedPath, (AbstractNestablePropertyAccessor)parent);
   }

   @Nullable
   protected FieldPropertyHandler getLocalPropertyHandler(String propertyName) {
      FieldPropertyHandler propertyHandler = (FieldPropertyHandler)this.fieldMap.get(propertyName);
      if (propertyHandler == null) {
         Field field = ReflectionUtils.findField(this.getWrappedClass(), propertyName);
         if (field != null) {
            propertyHandler = new FieldPropertyHandler(field);
            this.fieldMap.put(propertyName, propertyHandler);
         }
      }

      return propertyHandler;
   }

   protected DirectFieldAccessor newNestedPropertyAccessor(Object object, String nestedPath) {
      return new DirectFieldAccessor(object, nestedPath, this);
   }

   protected NotWritablePropertyException createNotWritablePropertyException(String propertyName) {
      PropertyMatches matches = PropertyMatches.forField(propertyName, this.getRootClass());
      throw new NotWritablePropertyException(this.getRootClass(), this.getNestedPath() + propertyName, matches.buildErrorMessage(), matches.getPossibleMatches());
   }

   private class FieldPropertyHandler extends AbstractNestablePropertyAccessor.PropertyHandler {
      private final Field field;

      public FieldPropertyHandler(Field field) {
         super(field.getType(), true, true);
         this.field = field;
      }

      public TypeDescriptor toTypeDescriptor() {
         return new TypeDescriptor(this.field);
      }

      public ResolvableType getResolvableType() {
         return ResolvableType.forField(this.field);
      }

      @Nullable
      public TypeDescriptor nested(int level) {
         return TypeDescriptor.nested(this.field, level);
      }

      @Nullable
      public Object getValue() throws Exception {
         try {
            ReflectionUtils.makeAccessible(this.field);
            return this.field.get(DirectFieldAccessor.this.getWrappedInstance());
         } catch (IllegalAccessException var2) {
            throw new InvalidPropertyException(DirectFieldAccessor.this.getWrappedClass(), this.field.getName(), "Field is not accessible", var2);
         }
      }

      public void setValue(@Nullable Object value) throws Exception {
         try {
            ReflectionUtils.makeAccessible(this.field);
            this.field.set(DirectFieldAccessor.this.getWrappedInstance(), value);
         } catch (IllegalAccessException var3) {
            throw new InvalidPropertyException(DirectFieldAccessor.this.getWrappedClass(), this.field.getName(), "Field is not accessible", var3);
         }
      }
   }
}
