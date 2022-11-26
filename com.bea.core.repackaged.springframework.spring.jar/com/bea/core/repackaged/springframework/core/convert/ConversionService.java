package com.bea.core.repackaged.springframework.core.convert;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ConversionService {
   boolean canConvert(@Nullable Class var1, Class var2);

   boolean canConvert(@Nullable TypeDescriptor var1, TypeDescriptor var2);

   @Nullable
   Object convert(@Nullable Object var1, Class var2);

   @Nullable
   Object convert(@Nullable Object var1, @Nullable TypeDescriptor var2, TypeDescriptor var3);
}
