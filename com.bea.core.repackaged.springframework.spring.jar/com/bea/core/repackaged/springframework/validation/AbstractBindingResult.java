package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditor;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractBindingResult extends AbstractErrors implements BindingResult, Serializable {
   private final String objectName;
   private MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();
   private final List errors = new LinkedList();
   private final Map fieldTypes = new HashMap();
   private final Map fieldValues = new HashMap();
   private final Set suppressedFields = new HashSet();

   protected AbstractBindingResult(String objectName) {
      this.objectName = objectName;
   }

   public void setMessageCodesResolver(MessageCodesResolver messageCodesResolver) {
      Assert.notNull(messageCodesResolver, (String)"MessageCodesResolver must not be null");
      this.messageCodesResolver = messageCodesResolver;
   }

   public MessageCodesResolver getMessageCodesResolver() {
      return this.messageCodesResolver;
   }

   public String getObjectName() {
      return this.objectName;
   }

   public void reject(String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      this.addError(new ObjectError(this.getObjectName(), this.resolveMessageCodes(errorCode), errorArgs, defaultMessage));
   }

   public void rejectValue(@Nullable String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      if ("".equals(this.getNestedPath()) && !StringUtils.hasLength(field)) {
         this.reject(errorCode, errorArgs, defaultMessage);
      } else {
         String fixedField = this.fixedField(field);
         Object newVal = this.getActualFieldValue(fixedField);
         FieldError fe = new FieldError(this.getObjectName(), fixedField, newVal, false, this.resolveMessageCodes(errorCode, field), errorArgs, defaultMessage);
         this.addError(fe);
      }
   }

   public void addAllErrors(Errors errors) {
      if (!errors.getObjectName().equals(this.getObjectName())) {
         throw new IllegalArgumentException("Errors object needs to have same object name");
      } else {
         this.errors.addAll(errors.getAllErrors());
      }
   }

   public boolean hasErrors() {
      return !this.errors.isEmpty();
   }

   public int getErrorCount() {
      return this.errors.size();
   }

   public List getAllErrors() {
      return Collections.unmodifiableList(this.errors);
   }

   public List getGlobalErrors() {
      List result = new LinkedList();
      Iterator var2 = this.errors.iterator();

      while(var2.hasNext()) {
         ObjectError objectError = (ObjectError)var2.next();
         if (!(objectError instanceof FieldError)) {
            result.add(objectError);
         }
      }

      return Collections.unmodifiableList(result);
   }

   @Nullable
   public ObjectError getGlobalError() {
      Iterator var1 = this.errors.iterator();

      ObjectError objectError;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         objectError = (ObjectError)var1.next();
      } while(objectError instanceof FieldError);

      return objectError;
   }

   public List getFieldErrors() {
      List result = new LinkedList();
      Iterator var2 = this.errors.iterator();

      while(var2.hasNext()) {
         ObjectError objectError = (ObjectError)var2.next();
         if (objectError instanceof FieldError) {
            result.add((FieldError)objectError);
         }
      }

      return Collections.unmodifiableList(result);
   }

   @Nullable
   public FieldError getFieldError() {
      Iterator var1 = this.errors.iterator();

      ObjectError objectError;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         objectError = (ObjectError)var1.next();
      } while(!(objectError instanceof FieldError));

      return (FieldError)objectError;
   }

   public List getFieldErrors(String field) {
      List result = new LinkedList();
      String fixedField = this.fixedField(field);
      Iterator var4 = this.errors.iterator();

      while(var4.hasNext()) {
         ObjectError objectError = (ObjectError)var4.next();
         if (objectError instanceof FieldError && this.isMatchingFieldError(fixedField, (FieldError)objectError)) {
            result.add((FieldError)objectError);
         }
      }

      return Collections.unmodifiableList(result);
   }

   @Nullable
   public FieldError getFieldError(String field) {
      String fixedField = this.fixedField(field);
      Iterator var3 = this.errors.iterator();

      while(var3.hasNext()) {
         ObjectError objectError = (ObjectError)var3.next();
         if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError)objectError;
            if (this.isMatchingFieldError(fixedField, fieldError)) {
               return fieldError;
            }
         }
      }

      return null;
   }

   @Nullable
   public Object getFieldValue(String field) {
      FieldError fieldError = this.getFieldError(field);
      Object value;
      if (fieldError == null) {
         if (this.getTarget() != null) {
            value = this.getActualFieldValue(this.fixedField(field));
            return this.formatFieldValue(field, value);
         } else {
            return this.fieldValues.get(field);
         }
      } else {
         value = fieldError.getRejectedValue();
         return !fieldError.isBindingFailure() && this.getTarget() != null ? this.formatFieldValue(field, value) : value;
      }
   }

   @Nullable
   public Class getFieldType(@Nullable String field) {
      if (this.getTarget() != null) {
         Object value = this.getActualFieldValue(this.fixedField(field));
         if (value != null) {
            return value.getClass();
         }
      }

      return (Class)this.fieldTypes.get(field);
   }

   public Map getModel() {
      Map model = new LinkedHashMap(2);
      model.put(this.getObjectName(), this.getTarget());
      model.put(MODEL_KEY_PREFIX + this.getObjectName(), this);
      return model;
   }

   @Nullable
   public Object getRawFieldValue(String field) {
      return this.getTarget() != null ? this.getActualFieldValue(this.fixedField(field)) : null;
   }

   @Nullable
   public PropertyEditor findEditor(@Nullable String field, @Nullable Class valueType) {
      PropertyEditorRegistry editorRegistry = this.getPropertyEditorRegistry();
      if (editorRegistry != null) {
         Class valueTypeToUse = valueType;
         if (valueType == null) {
            valueTypeToUse = this.getFieldType(field);
         }

         return editorRegistry.findCustomEditor(valueTypeToUse, this.fixedField(field));
      } else {
         return null;
      }
   }

   @Nullable
   public PropertyEditorRegistry getPropertyEditorRegistry() {
      return null;
   }

   public String[] resolveMessageCodes(String errorCode) {
      return this.getMessageCodesResolver().resolveMessageCodes(errorCode, this.getObjectName());
   }

   public String[] resolveMessageCodes(String errorCode, @Nullable String field) {
      return this.getMessageCodesResolver().resolveMessageCodes(errorCode, this.getObjectName(), this.fixedField(field), this.getFieldType(field));
   }

   public void addError(ObjectError error) {
      this.errors.add(error);
   }

   public void recordFieldValue(String field, Class type, @Nullable Object value) {
      this.fieldTypes.put(field, type);
      this.fieldValues.put(field, value);
   }

   public void recordSuppressedField(String field) {
      this.suppressedFields.add(field);
   }

   public String[] getSuppressedFields() {
      return StringUtils.toStringArray((Collection)this.suppressedFields);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof BindingResult)) {
         return false;
      } else {
         BindingResult otherResult = (BindingResult)other;
         return this.getObjectName().equals(otherResult.getObjectName()) && ObjectUtils.nullSafeEquals(this.getTarget(), otherResult.getTarget()) && this.getAllErrors().equals(otherResult.getAllErrors());
      }
   }

   public int hashCode() {
      return this.getObjectName().hashCode();
   }

   @Nullable
   public abstract Object getTarget();

   @Nullable
   protected abstract Object getActualFieldValue(String var1);

   @Nullable
   protected Object formatFieldValue(String field, @Nullable Object value) {
      return value;
   }
}
