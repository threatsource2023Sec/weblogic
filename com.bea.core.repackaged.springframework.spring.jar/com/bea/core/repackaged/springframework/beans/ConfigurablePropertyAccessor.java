package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ConfigurablePropertyAccessor extends PropertyAccessor, PropertyEditorRegistry, TypeConverter {
   void setConversionService(@Nullable ConversionService var1);

   @Nullable
   ConversionService getConversionService();

   void setExtractOldValueForEditor(boolean var1);

   boolean isExtractOldValueForEditor();

   void setAutoGrowNestedPaths(boolean var1);

   boolean isAutoGrowNestedPaths();
}
