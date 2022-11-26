package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

final class ObjectToOptionalConverter implements ConditionalGenericConverter {
   private final ConversionService conversionService;

   public ObjectToOptionalConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      Set convertibleTypes = new LinkedHashSet(4);
      convertibleTypes.add(new GenericConverter.ConvertiblePair(Collection.class, Optional.class));
      convertibleTypes.add(new GenericConverter.ConvertiblePair(Object[].class, Optional.class));
      convertibleTypes.add(new GenericConverter.ConvertiblePair(Object.class, Optional.class));
      return convertibleTypes;
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return targetType.getResolvableType().hasGenerics() ? this.conversionService.canConvert((TypeDescriptor)sourceType, (TypeDescriptor)(new GenericTypeDescriptor(targetType))) : true;
   }

   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         return Optional.empty();
      } else if (source instanceof Optional) {
         return source;
      } else if (!targetType.getResolvableType().hasGenerics()) {
         return Optional.of(source);
      } else {
         Object target = this.conversionService.convert(source, sourceType, new GenericTypeDescriptor(targetType));
         return target != null && (!target.getClass().isArray() || Array.getLength(target) != 0) && (!(target instanceof Collection) || !((Collection)target).isEmpty()) ? Optional.of(target) : Optional.empty();
      }
   }

   private static class GenericTypeDescriptor extends TypeDescriptor {
      public GenericTypeDescriptor(TypeDescriptor typeDescriptor) {
         super(typeDescriptor.getResolvableType().getGeneric(), (Class)null, typeDescriptor.getAnnotations());
      }
   }
}
