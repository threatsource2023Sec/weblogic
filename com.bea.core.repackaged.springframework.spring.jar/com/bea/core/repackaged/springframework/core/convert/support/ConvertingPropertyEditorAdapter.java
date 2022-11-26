package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditorSupport;

public class ConvertingPropertyEditorAdapter extends PropertyEditorSupport {
   private final ConversionService conversionService;
   private final TypeDescriptor targetDescriptor;
   private final boolean canConvertToString;

   public ConvertingPropertyEditorAdapter(ConversionService conversionService, TypeDescriptor targetDescriptor) {
      Assert.notNull(conversionService, (String)"ConversionService must not be null");
      Assert.notNull(targetDescriptor, (String)"TypeDescriptor must not be null");
      this.conversionService = conversionService;
      this.targetDescriptor = targetDescriptor;
      this.canConvertToString = conversionService.canConvert(this.targetDescriptor, TypeDescriptor.valueOf(String.class));
   }

   public void setAsText(@Nullable String text) throws IllegalArgumentException {
      this.setValue(this.conversionService.convert(text, TypeDescriptor.valueOf(String.class), this.targetDescriptor));
   }

   @Nullable
   public String getAsText() {
      return this.canConvertToString ? (String)this.conversionService.convert(this.getValue(), this.targetDescriptor, TypeDescriptor.valueOf(String.class)) : null;
   }
}
