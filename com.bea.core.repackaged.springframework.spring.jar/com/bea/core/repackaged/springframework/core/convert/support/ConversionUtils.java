package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionFailedException;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.function.Supplier;

abstract class ConversionUtils {
   @Nullable
   public static Object invokeConverter(GenericConverter converter, @Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      try {
         return converter.convert(source, sourceType, targetType);
      } catch (ConversionFailedException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new ConversionFailedException(sourceType, targetType, source, var6);
      }
   }

   public static boolean canConvertElements(@Nullable TypeDescriptor sourceElementType, @Nullable TypeDescriptor targetElementType, ConversionService conversionService) {
      if (targetElementType == null) {
         return true;
      } else if (sourceElementType == null) {
         return true;
      } else if (conversionService.canConvert(sourceElementType, targetElementType)) {
         return true;
      } else {
         return ClassUtils.isAssignable(sourceElementType.getType(), targetElementType.getType());
      }
   }

   public static Class getEnumType(Class targetType) {
      Class enumType;
      for(enumType = targetType; enumType != null && !enumType.isEnum(); enumType = enumType.getSuperclass()) {
      }

      Assert.notNull(enumType, (Supplier)(() -> {
         return "The target type " + targetType.getName() + " does not refer to an enum";
      }));
      return enumType;
   }
}
