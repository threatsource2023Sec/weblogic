package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.ConfigurablePropertyAccessor;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.PropertyAccessException;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorUtils;
import com.bea.core.repackaged.springframework.beans.PropertyBatchUpdateException;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.beans.PropertyValue;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.SimpleTypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeMismatchException;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.support.FormatterPropertyEditorAdapter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataBinder implements PropertyEditorRegistry, TypeConverter {
   public static final String DEFAULT_OBJECT_NAME = "target";
   public static final int DEFAULT_AUTO_GROW_COLLECTION_LIMIT = 256;
   protected static final Log logger = LogFactory.getLog(DataBinder.class);
   @Nullable
   private final Object target;
   private final String objectName;
   @Nullable
   private AbstractPropertyBindingResult bindingResult;
   @Nullable
   private SimpleTypeConverter typeConverter;
   private boolean ignoreUnknownFields;
   private boolean ignoreInvalidFields;
   private boolean autoGrowNestedPaths;
   private int autoGrowCollectionLimit;
   @Nullable
   private String[] allowedFields;
   @Nullable
   private String[] disallowedFields;
   @Nullable
   private String[] requiredFields;
   @Nullable
   private ConversionService conversionService;
   @Nullable
   private MessageCodesResolver messageCodesResolver;
   private BindingErrorProcessor bindingErrorProcessor;
   private final List validators;

   public DataBinder(@Nullable Object target) {
      this(target, "target");
   }

   public DataBinder(@Nullable Object target, String objectName) {
      this.ignoreUnknownFields = true;
      this.ignoreInvalidFields = false;
      this.autoGrowNestedPaths = true;
      this.autoGrowCollectionLimit = 256;
      this.bindingErrorProcessor = new DefaultBindingErrorProcessor();
      this.validators = new ArrayList();
      this.target = ObjectUtils.unwrapOptional(target);
      this.objectName = objectName;
   }

   @Nullable
   public Object getTarget() {
      return this.target;
   }

   public String getObjectName() {
      return this.objectName;
   }

   public void setAutoGrowNestedPaths(boolean autoGrowNestedPaths) {
      Assert.state(this.bindingResult == null, "DataBinder is already initialized - call setAutoGrowNestedPaths before other configuration methods");
      this.autoGrowNestedPaths = autoGrowNestedPaths;
   }

   public boolean isAutoGrowNestedPaths() {
      return this.autoGrowNestedPaths;
   }

   public void setAutoGrowCollectionLimit(int autoGrowCollectionLimit) {
      Assert.state(this.bindingResult == null, "DataBinder is already initialized - call setAutoGrowCollectionLimit before other configuration methods");
      this.autoGrowCollectionLimit = autoGrowCollectionLimit;
   }

   public int getAutoGrowCollectionLimit() {
      return this.autoGrowCollectionLimit;
   }

   public void initBeanPropertyAccess() {
      Assert.state(this.bindingResult == null, "DataBinder is already initialized - call initBeanPropertyAccess before other configuration methods");
      this.bindingResult = this.createBeanPropertyBindingResult();
   }

   protected AbstractPropertyBindingResult createBeanPropertyBindingResult() {
      BeanPropertyBindingResult result = new BeanPropertyBindingResult(this.getTarget(), this.getObjectName(), this.isAutoGrowNestedPaths(), this.getAutoGrowCollectionLimit());
      if (this.conversionService != null) {
         result.initConversion(this.conversionService);
      }

      if (this.messageCodesResolver != null) {
         result.setMessageCodesResolver(this.messageCodesResolver);
      }

      return result;
   }

   public void initDirectFieldAccess() {
      Assert.state(this.bindingResult == null, "DataBinder is already initialized - call initDirectFieldAccess before other configuration methods");
      this.bindingResult = this.createDirectFieldBindingResult();
   }

   protected AbstractPropertyBindingResult createDirectFieldBindingResult() {
      DirectFieldBindingResult result = new DirectFieldBindingResult(this.getTarget(), this.getObjectName(), this.isAutoGrowNestedPaths());
      if (this.conversionService != null) {
         result.initConversion(this.conversionService);
      }

      if (this.messageCodesResolver != null) {
         result.setMessageCodesResolver(this.messageCodesResolver);
      }

      return result;
   }

   protected AbstractPropertyBindingResult getInternalBindingResult() {
      if (this.bindingResult == null) {
         this.initBeanPropertyAccess();
      }

      return this.bindingResult;
   }

   protected ConfigurablePropertyAccessor getPropertyAccessor() {
      return this.getInternalBindingResult().getPropertyAccessor();
   }

   protected SimpleTypeConverter getSimpleTypeConverter() {
      if (this.typeConverter == null) {
         this.typeConverter = new SimpleTypeConverter();
         if (this.conversionService != null) {
            this.typeConverter.setConversionService(this.conversionService);
         }
      }

      return this.typeConverter;
   }

   protected PropertyEditorRegistry getPropertyEditorRegistry() {
      return (PropertyEditorRegistry)(this.getTarget() != null ? this.getInternalBindingResult().getPropertyAccessor() : this.getSimpleTypeConverter());
   }

   protected TypeConverter getTypeConverter() {
      return (TypeConverter)(this.getTarget() != null ? this.getInternalBindingResult().getPropertyAccessor() : this.getSimpleTypeConverter());
   }

   public BindingResult getBindingResult() {
      return this.getInternalBindingResult();
   }

   public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
      this.ignoreUnknownFields = ignoreUnknownFields;
   }

   public boolean isIgnoreUnknownFields() {
      return this.ignoreUnknownFields;
   }

   public void setIgnoreInvalidFields(boolean ignoreInvalidFields) {
      this.ignoreInvalidFields = ignoreInvalidFields;
   }

   public boolean isIgnoreInvalidFields() {
      return this.ignoreInvalidFields;
   }

   public void setAllowedFields(@Nullable String... allowedFields) {
      this.allowedFields = PropertyAccessorUtils.canonicalPropertyNames(allowedFields);
   }

   @Nullable
   public String[] getAllowedFields() {
      return this.allowedFields;
   }

   public void setDisallowedFields(@Nullable String... disallowedFields) {
      this.disallowedFields = PropertyAccessorUtils.canonicalPropertyNames(disallowedFields);
   }

   @Nullable
   public String[] getDisallowedFields() {
      return this.disallowedFields;
   }

   public void setRequiredFields(@Nullable String... requiredFields) {
      this.requiredFields = PropertyAccessorUtils.canonicalPropertyNames(requiredFields);
      if (logger.isDebugEnabled()) {
         logger.debug("DataBinder requires binding of required fields [" + StringUtils.arrayToCommaDelimitedString(requiredFields) + "]");
      }

   }

   @Nullable
   public String[] getRequiredFields() {
      return this.requiredFields;
   }

   public void setMessageCodesResolver(@Nullable MessageCodesResolver messageCodesResolver) {
      Assert.state(this.messageCodesResolver == null, "DataBinder is already initialized with MessageCodesResolver");
      this.messageCodesResolver = messageCodesResolver;
      if (this.bindingResult != null && messageCodesResolver != null) {
         this.bindingResult.setMessageCodesResolver(messageCodesResolver);
      }

   }

   public void setBindingErrorProcessor(BindingErrorProcessor bindingErrorProcessor) {
      Assert.notNull(bindingErrorProcessor, (String)"BindingErrorProcessor must not be null");
      this.bindingErrorProcessor = bindingErrorProcessor;
   }

   public BindingErrorProcessor getBindingErrorProcessor() {
      return this.bindingErrorProcessor;
   }

   public void setValidator(@Nullable Validator validator) {
      this.assertValidators(validator);
      this.validators.clear();
      if (validator != null) {
         this.validators.add(validator);
      }

   }

   private void assertValidators(Validator... validators) {
      Object target = this.getTarget();
      Validator[] var3 = validators;
      int var4 = validators.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Validator validator = var3[var5];
         if (validator != null && target != null && !validator.supports(target.getClass())) {
            throw new IllegalStateException("Invalid target for Validator [" + validator + "]: " + target);
         }
      }

   }

   public void addValidators(Validator... validators) {
      this.assertValidators(validators);
      this.validators.addAll(Arrays.asList(validators));
   }

   public void replaceValidators(Validator... validators) {
      this.assertValidators(validators);
      this.validators.clear();
      this.validators.addAll(Arrays.asList(validators));
   }

   @Nullable
   public Validator getValidator() {
      return !this.validators.isEmpty() ? (Validator)this.validators.get(0) : null;
   }

   public List getValidators() {
      return Collections.unmodifiableList(this.validators);
   }

   public void setConversionService(@Nullable ConversionService conversionService) {
      Assert.state(this.conversionService == null, "DataBinder is already initialized with ConversionService");
      this.conversionService = conversionService;
      if (this.bindingResult != null && conversionService != null) {
         this.bindingResult.initConversion(conversionService);
      }

   }

   @Nullable
   public ConversionService getConversionService() {
      return this.conversionService;
   }

   public void addCustomFormatter(Formatter formatter) {
      FormatterPropertyEditorAdapter adapter = new FormatterPropertyEditorAdapter(formatter);
      this.getPropertyEditorRegistry().registerCustomEditor(adapter.getFieldType(), adapter);
   }

   public void addCustomFormatter(Formatter formatter, String... fields) {
      FormatterPropertyEditorAdapter adapter = new FormatterPropertyEditorAdapter(formatter);
      Class fieldType = adapter.getFieldType();
      if (ObjectUtils.isEmpty((Object[])fields)) {
         this.getPropertyEditorRegistry().registerCustomEditor(fieldType, adapter);
      } else {
         String[] var5 = fields;
         int var6 = fields.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String field = var5[var7];
            this.getPropertyEditorRegistry().registerCustomEditor(fieldType, field, adapter);
         }
      }

   }

   public void addCustomFormatter(Formatter formatter, Class... fieldTypes) {
      FormatterPropertyEditorAdapter adapter = new FormatterPropertyEditorAdapter(formatter);
      if (ObjectUtils.isEmpty((Object[])fieldTypes)) {
         this.getPropertyEditorRegistry().registerCustomEditor(adapter.getFieldType(), adapter);
      } else {
         Class[] var4 = fieldTypes;
         int var5 = fieldTypes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class fieldType = var4[var6];
            this.getPropertyEditorRegistry().registerCustomEditor(fieldType, adapter);
         }
      }

   }

   public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
      this.getPropertyEditorRegistry().registerCustomEditor(requiredType, propertyEditor);
   }

   public void registerCustomEditor(@Nullable Class requiredType, @Nullable String field, PropertyEditor propertyEditor) {
      this.getPropertyEditorRegistry().registerCustomEditor(requiredType, field, propertyEditor);
   }

   @Nullable
   public PropertyEditor findCustomEditor(@Nullable Class requiredType, @Nullable String propertyPath) {
      return this.getPropertyEditorRegistry().findCustomEditor(requiredType, propertyPath);
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType) throws TypeMismatchException {
      return this.getTypeConverter().convertIfNecessary(value, requiredType);
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable MethodParameter methodParam) throws TypeMismatchException {
      return this.getTypeConverter().convertIfNecessary(value, requiredType, methodParam);
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable Field field) throws TypeMismatchException {
      return this.getTypeConverter().convertIfNecessary(value, requiredType, field);
   }

   @Nullable
   public Object convertIfNecessary(@Nullable Object value, @Nullable Class requiredType, @Nullable TypeDescriptor typeDescriptor) throws TypeMismatchException {
      return this.getTypeConverter().convertIfNecessary(value, requiredType, typeDescriptor);
   }

   public void bind(PropertyValues pvs) {
      MutablePropertyValues mpvs = pvs instanceof MutablePropertyValues ? (MutablePropertyValues)pvs : new MutablePropertyValues(pvs);
      this.doBind(mpvs);
   }

   protected void doBind(MutablePropertyValues mpvs) {
      this.checkAllowedFields(mpvs);
      this.checkRequiredFields(mpvs);
      this.applyPropertyValues(mpvs);
   }

   protected void checkAllowedFields(MutablePropertyValues mpvs) {
      PropertyValue[] pvs = mpvs.getPropertyValues();
      PropertyValue[] var3 = pvs;
      int var4 = pvs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyValue pv = var3[var5];
         String field = PropertyAccessorUtils.canonicalPropertyName(pv.getName());
         if (!this.isAllowed(field)) {
            mpvs.removePropertyValue(pv);
            this.getBindingResult().recordSuppressedField(field);
            if (logger.isDebugEnabled()) {
               logger.debug("Field [" + field + "] has been removed from PropertyValues and will not be bound, because it has not been found in the list of allowed fields");
            }
         }
      }

   }

   protected boolean isAllowed(String field) {
      String[] allowed = this.getAllowedFields();
      String[] disallowed = this.getDisallowedFields();
      return (ObjectUtils.isEmpty((Object[])allowed) || PatternMatchUtils.simpleMatch(allowed, field)) && (ObjectUtils.isEmpty((Object[])disallowed) || !PatternMatchUtils.simpleMatch(disallowed, field));
   }

   protected void checkRequiredFields(MutablePropertyValues mpvs) {
      String[] requiredFields = this.getRequiredFields();
      if (!ObjectUtils.isEmpty((Object[])requiredFields)) {
         Map propertyValues = new HashMap();
         PropertyValue[] pvs = mpvs.getPropertyValues();
         PropertyValue[] var5 = pvs;
         int var6 = pvs.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            PropertyValue pv = var5[var7];
            String canonicalName = PropertyAccessorUtils.canonicalPropertyName(pv.getName());
            propertyValues.put(canonicalName, pv);
         }

         String[] var12 = requiredFields;
         var6 = requiredFields.length;

         for(var7 = 0; var7 < var6; ++var7) {
            String field = var12[var7];
            PropertyValue pv = (PropertyValue)propertyValues.get(field);
            boolean empty = pv == null || pv.getValue() == null;
            if (!empty) {
               if (pv.getValue() instanceof String) {
                  empty = !StringUtils.hasText((String)pv.getValue());
               } else if (pv.getValue() instanceof String[]) {
                  String[] values = (String[])((String[])pv.getValue());
                  empty = values.length == 0 || !StringUtils.hasText(values[0]);
               }
            }

            if (empty) {
               this.getBindingErrorProcessor().processMissingFieldError(field, this.getInternalBindingResult());
               if (pv != null) {
                  mpvs.removePropertyValue(pv);
                  propertyValues.remove(field);
               }
            }
         }
      }

   }

   protected void applyPropertyValues(MutablePropertyValues mpvs) {
      try {
         this.getPropertyAccessor().setPropertyValues(mpvs, this.isIgnoreUnknownFields(), this.isIgnoreInvalidFields());
      } catch (PropertyBatchUpdateException var7) {
         PropertyAccessException[] var3 = var7.getPropertyAccessExceptions();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyAccessException pae = var3[var5];
            this.getBindingErrorProcessor().processPropertyAccessException(pae, this.getInternalBindingResult());
         }
      }

   }

   public void validate() {
      Object target = this.getTarget();
      Assert.state(target != null, "No target to validate");
      BindingResult bindingResult = this.getBindingResult();
      Iterator var3 = this.getValidators().iterator();

      while(var3.hasNext()) {
         Validator validator = (Validator)var3.next();
         validator.validate(target, bindingResult);
      }

   }

   public void validate(Object... validationHints) {
      Object target = this.getTarget();
      Assert.state(target != null, "No target to validate");
      BindingResult bindingResult = this.getBindingResult();
      Iterator var4 = this.getValidators().iterator();

      while(true) {
         while(var4.hasNext()) {
            Validator validator = (Validator)var4.next();
            if (!ObjectUtils.isEmpty(validationHints) && validator instanceof SmartValidator) {
               ((SmartValidator)validator).validate(target, bindingResult, validationHints);
            } else if (validator != null) {
               validator.validate(target, bindingResult);
            }
         }

         return;
      }
   }

   public Map close() throws BindException {
      if (this.getBindingResult().hasErrors()) {
         throw new BindException(this.getBindingResult());
      } else {
         return this.getBindingResult().getModel();
      }
   }
}
