package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.ConversionException;
import com.bea.core.repackaged.springframework.core.convert.ConverterNotFoundException;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Field;

public abstract class TypeConverterSupport extends PropertyEditorRegistrySupport implements TypeConverter {
   @Nullable
   TypeConverterDelegate typeConverterDelegate;

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType) throws TypeMismatchException {
      return this.convertIfNecessary(value, requiredType, TypeDescriptor.valueOf(requiredType));
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable MethodParameter methodParam) throws TypeMismatchException {
      return this.convertIfNecessary(value, requiredType, methodParam != null ? new TypeDescriptor(methodParam) : TypeDescriptor.valueOf(requiredType));
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable Field field) throws TypeMismatchException {
      return this.convertIfNecessary(value, requiredType, field != null ? new TypeDescriptor(field) : TypeDescriptor.valueOf(requiredType));
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable TypeDescriptor typeDescriptor) throws TypeMismatchException {
      Assert.state(this.typeConverterDelegate != null, "No TypeConverterDelegate");

      try {
         return this.typeConverterDelegate.convertIfNecessary((String)null, (Object)null, value, requiredType, typeDescriptor);
      } catch (IllegalStateException | ConverterNotFoundException var5) {
         throw new ConversionNotSupportedException(value, requiredType, var5);
      } catch (IllegalArgumentException | ConversionException var6) {
         throw new TypeMismatchException(value, requiredType, var6);
      }
   }
}
