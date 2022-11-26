package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalConverter;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.Iterator;

abstract class AbstractConditionalEnumConverter implements ConditionalConverter {
   private final ConversionService conversionService;

   protected AbstractConditionalEnumConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      Iterator var3 = ClassUtils.getAllInterfacesForClassAsSet(sourceType.getType()).iterator();

      Class interfaceType;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         interfaceType = (Class)var3.next();
      } while(!this.conversionService.canConvert(TypeDescriptor.valueOf(interfaceType), targetType));

      return false;
   }
}
