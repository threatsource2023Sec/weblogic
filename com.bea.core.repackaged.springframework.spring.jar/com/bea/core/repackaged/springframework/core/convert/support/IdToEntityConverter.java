package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Set;

final class IdToEntityConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public IdToEntityConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return Collections.singleton(new GenericConverter.ConvertiblePair(Object.class, Object.class));
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      Method finder = this.getFinder(targetType.getType());
      return finder != null && this.conversionService.canConvert(sourceType, TypeDescriptor.valueOf(finder.getParameterTypes()[0]));
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return null;
      } else {
         Method finder = this.getFinder(targetType.getType());
         Assert.state(finder != null, "No finder method");
         Object id = this.conversionService.convert(source, sourceType, TypeDescriptor.valueOf(finder.getParameterTypes()[0]));
         return ReflectionUtils.invokeMethod(finder, source, id);
      }
   }

   @Nullable
   private Method getFinder(Class entityClass) {
      String finderMethod = "find" + this.getEntityName(entityClass);

      Method[] methods;
      boolean localOnlyFiltered;
      try {
         methods = entityClass.getDeclaredMethods();
         localOnlyFiltered = true;
      } catch (SecurityException var9) {
         methods = entityClass.getMethods();
         localOnlyFiltered = false;
      }

      Method[] var5 = methods;
      int var6 = methods.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method method = var5[var7];
         if (Modifier.isStatic(method.getModifiers()) && method.getName().equals(finderMethod) && method.getParameterCount() == 1 && method.getReturnType().equals(entityClass) && (localOnlyFiltered || method.getDeclaringClass().equals(entityClass))) {
            return method;
         }
      }

      return null;
   }

   private String getEntityName(Class entityClass) {
      String shortName = ClassUtils.getShortName(entityClass);
      int lastDot = shortName.lastIndexOf(46);
      return lastDot != -1 ? shortName.substring(lastDot + 1) : shortName;
   }
}
