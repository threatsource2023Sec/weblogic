package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Field;

public interface TypeConverter {
   @Nullable
   Object convertIfNecessary(@Nullable Object var1, @Nullable Class var2) throws TypeMismatchException;

   @Nullable
   Object convertIfNecessary(@Nullable Object var1, @Nullable Class var2, @Nullable MethodParameter var3) throws TypeMismatchException;

   @Nullable
   Object convertIfNecessary(@Nullable Object var1, @Nullable Class var2, @Nullable Field var3) throws TypeMismatchException;

   @Nullable
   default Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable TypeDescriptor typeDescriptor) throws TypeMismatchException {
      throw new UnsupportedOperationException("TypeDescriptor resolution not supported");
   }
}
