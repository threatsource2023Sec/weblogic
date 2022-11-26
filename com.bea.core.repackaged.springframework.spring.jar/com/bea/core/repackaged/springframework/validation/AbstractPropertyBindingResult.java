package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.ConfigurablePropertyAccessor;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorUtils;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.support.ConvertingPropertyEditorAdapter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditor;

public abstract class AbstractPropertyBindingResult extends AbstractBindingResult {
   @Nullable
   private transient ConversionService conversionService;

   protected AbstractPropertyBindingResult(String objectName) {
      super(objectName);
   }

   public void initConversion(ConversionService conversionService) {
      Assert.notNull(conversionService, (String)"ConversionService must not be null");
      this.conversionService = conversionService;
      if (this.getTarget() != null) {
         this.getPropertyAccessor().setConversionService(conversionService);
      }

   }

   public PropertyEditorRegistry getPropertyEditorRegistry() {
      return this.getTarget() != null ? this.getPropertyAccessor() : null;
   }

   protected String canonicalFieldName(String field) {
      return PropertyAccessorUtils.canonicalPropertyName(field);
   }

   @Nullable
   public Class getFieldType(@Nullable String field) {
      return this.getTarget() != null ? this.getPropertyAccessor().getPropertyType(this.fixedField(field)) : super.getFieldType(field);
   }

   @Nullable
   protected Object getActualFieldValue(String field) {
      return this.getPropertyAccessor().getPropertyValue(field);
   }

   protected Object formatFieldValue(String field, @Nullable Object value) {
      String fixedField = this.fixedField(field);
      PropertyEditor customEditor = this.getCustomEditor(fixedField);
      if (customEditor != null) {
         customEditor.setValue(value);
         String textValue = customEditor.getAsText();
         if (textValue != null) {
            return textValue;
         }
      }

      if (this.conversionService != null) {
         TypeDescriptor fieldDesc = this.getPropertyAccessor().getPropertyTypeDescriptor(fixedField);
         TypeDescriptor strDesc = TypeDescriptor.valueOf(String.class);
         if (fieldDesc != null && this.conversionService.canConvert(fieldDesc, strDesc)) {
            return this.conversionService.convert(value, fieldDesc, strDesc);
         }
      }

      return value;
   }

   @Nullable
   protected PropertyEditor getCustomEditor(String fixedField) {
      Class targetType = this.getPropertyAccessor().getPropertyType(fixedField);
      PropertyEditor editor = this.getPropertyAccessor().findCustomEditor(targetType, fixedField);
      if (editor == null) {
         editor = BeanUtils.findEditorByConvention(targetType);
      }

      return editor;
   }

   @Nullable
   public PropertyEditor findEditor(@Nullable String field, @Nullable Class valueType) {
      Class valueTypeForLookup = valueType;
      if (valueType == null) {
         valueTypeForLookup = this.getFieldType(field);
      }

      PropertyEditor editor = super.findEditor(field, valueTypeForLookup);
      if (editor == null && this.conversionService != null) {
         TypeDescriptor td = null;
         if (field != null && this.getTarget() != null) {
            TypeDescriptor ptd = this.getPropertyAccessor().getPropertyTypeDescriptor(this.fixedField(field));
            if (ptd != null && (valueType == null || valueType.isAssignableFrom(ptd.getType()))) {
               td = ptd;
            }
         }

         if (td == null) {
            td = TypeDescriptor.valueOf(valueTypeForLookup);
         }

         if (this.conversionService.canConvert(TypeDescriptor.valueOf(String.class), td)) {
            editor = new ConvertingPropertyEditorAdapter(this.conversionService, td);
         }
      }

      return (PropertyEditor)editor;
   }

   public abstract ConfigurablePropertyAccessor getPropertyAccessor();
}
