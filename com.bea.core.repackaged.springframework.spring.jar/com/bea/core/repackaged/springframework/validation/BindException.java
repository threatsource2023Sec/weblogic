package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

public class BindException extends Exception implements BindingResult {
   private final BindingResult bindingResult;

   public BindException(BindingResult bindingResult) {
      Assert.notNull(bindingResult, (String)"BindingResult must not be null");
      this.bindingResult = bindingResult;
   }

   public BindException(Object target, String objectName) {
      Assert.notNull(target, "Target object must not be null");
      this.bindingResult = new BeanPropertyBindingResult(target, objectName);
   }

   public final BindingResult getBindingResult() {
      return this.bindingResult;
   }

   public String getObjectName() {
      return this.bindingResult.getObjectName();
   }

   public void setNestedPath(String nestedPath) {
      this.bindingResult.setNestedPath(nestedPath);
   }

   public String getNestedPath() {
      return this.bindingResult.getNestedPath();
   }

   public void pushNestedPath(String subPath) {
      this.bindingResult.pushNestedPath(subPath);
   }

   public void popNestedPath() throws IllegalStateException {
      this.bindingResult.popNestedPath();
   }

   public void reject(String errorCode) {
      this.bindingResult.reject(errorCode);
   }

   public void reject(String errorCode, String defaultMessage) {
      this.bindingResult.reject(errorCode, defaultMessage);
   }

   public void reject(String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      this.bindingResult.reject(errorCode, errorArgs, defaultMessage);
   }

   public void rejectValue(@Nullable String field, String errorCode) {
      this.bindingResult.rejectValue(field, errorCode);
   }

   public void rejectValue(@Nullable String field, String errorCode, String defaultMessage) {
      this.bindingResult.rejectValue(field, errorCode, defaultMessage);
   }

   public void rejectValue(@Nullable String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      this.bindingResult.rejectValue(field, errorCode, errorArgs, defaultMessage);
   }

   public void addAllErrors(Errors errors) {
      this.bindingResult.addAllErrors(errors);
   }

   public boolean hasErrors() {
      return this.bindingResult.hasErrors();
   }

   public int getErrorCount() {
      return this.bindingResult.getErrorCount();
   }

   public List getAllErrors() {
      return this.bindingResult.getAllErrors();
   }

   public boolean hasGlobalErrors() {
      return this.bindingResult.hasGlobalErrors();
   }

   public int getGlobalErrorCount() {
      return this.bindingResult.getGlobalErrorCount();
   }

   public List getGlobalErrors() {
      return this.bindingResult.getGlobalErrors();
   }

   @Nullable
   public ObjectError getGlobalError() {
      return this.bindingResult.getGlobalError();
   }

   public boolean hasFieldErrors() {
      return this.bindingResult.hasFieldErrors();
   }

   public int getFieldErrorCount() {
      return this.bindingResult.getFieldErrorCount();
   }

   public List getFieldErrors() {
      return this.bindingResult.getFieldErrors();
   }

   @Nullable
   public FieldError getFieldError() {
      return this.bindingResult.getFieldError();
   }

   public boolean hasFieldErrors(String field) {
      return this.bindingResult.hasFieldErrors(field);
   }

   public int getFieldErrorCount(String field) {
      return this.bindingResult.getFieldErrorCount(field);
   }

   public List getFieldErrors(String field) {
      return this.bindingResult.getFieldErrors(field);
   }

   @Nullable
   public FieldError getFieldError(String field) {
      return this.bindingResult.getFieldError(field);
   }

   @Nullable
   public Object getFieldValue(String field) {
      return this.bindingResult.getFieldValue(field);
   }

   @Nullable
   public Class getFieldType(String field) {
      return this.bindingResult.getFieldType(field);
   }

   public Object getTarget() {
      return this.bindingResult.getTarget();
   }

   public Map getModel() {
      return this.bindingResult.getModel();
   }

   @Nullable
   public Object getRawFieldValue(String field) {
      return this.bindingResult.getRawFieldValue(field);
   }

   @Nullable
   public PropertyEditor findEditor(@Nullable String field, @Nullable Class valueType) {
      return this.bindingResult.findEditor(field, valueType);
   }

   @Nullable
   public PropertyEditorRegistry getPropertyEditorRegistry() {
      return this.bindingResult.getPropertyEditorRegistry();
   }

   public String[] resolveMessageCodes(String errorCode) {
      return this.bindingResult.resolveMessageCodes(errorCode);
   }

   public String[] resolveMessageCodes(String errorCode, String field) {
      return this.bindingResult.resolveMessageCodes(errorCode, field);
   }

   public void addError(ObjectError error) {
      this.bindingResult.addError(error);
   }

   public void recordFieldValue(String field, Class type, @Nullable Object value) {
      this.bindingResult.recordFieldValue(field, type, value);
   }

   public void recordSuppressedField(String field) {
      this.bindingResult.recordSuppressedField(field);
   }

   public String[] getSuppressedFields() {
      return this.bindingResult.getSuppressedFields();
   }

   public String getMessage() {
      return this.bindingResult.toString();
   }

   public boolean equals(Object other) {
      return this == other || this.bindingResult.equals(other);
   }

   public int hashCode() {
      return this.bindingResult.hashCode();
   }
}
