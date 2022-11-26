package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface TypeConverter {
   boolean canConvert(@Nullable TypeDescriptor var1, TypeDescriptor var2);

   @Nullable
   Object convertValue(@Nullable Object var1, @Nullable TypeDescriptor var2, TypeDescriptor var3);
}
