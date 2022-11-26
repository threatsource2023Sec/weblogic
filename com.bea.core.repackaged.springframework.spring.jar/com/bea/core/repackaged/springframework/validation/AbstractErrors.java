package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractErrors implements Errors, Serializable {
   private String nestedPath = "";
   private final Deque nestedPathStack = new ArrayDeque();

   public void setNestedPath(@Nullable String nestedPath) {
      this.doSetNestedPath(nestedPath);
      this.nestedPathStack.clear();
   }

   public String getNestedPath() {
      return this.nestedPath;
   }

   public void pushNestedPath(String subPath) {
      this.nestedPathStack.push(this.getNestedPath());
      this.doSetNestedPath(this.getNestedPath() + subPath);
   }

   public void popNestedPath() throws IllegalStateException {
      try {
         String formerNestedPath = (String)this.nestedPathStack.pop();
         this.doSetNestedPath(formerNestedPath);
      } catch (NoSuchElementException var2) {
         throw new IllegalStateException("Cannot pop nested path: no nested path on stack");
      }
   }

   protected void doSetNestedPath(@Nullable String nestedPath) {
      if (nestedPath == null) {
         nestedPath = "";
      }

      nestedPath = this.canonicalFieldName(nestedPath);
      if (nestedPath.length() > 0 && !nestedPath.endsWith(".")) {
         nestedPath = nestedPath + ".";
      }

      this.nestedPath = nestedPath;
   }

   protected String fixedField(@Nullable String field) {
      if (StringUtils.hasLength(field)) {
         return this.getNestedPath() + this.canonicalFieldName(field);
      } else {
         String path = this.getNestedPath();
         return path.endsWith(".") ? path.substring(0, path.length() - ".".length()) : path;
      }
   }

   protected String canonicalFieldName(String field) {
      return field;
   }

   public void reject(String errorCode) {
      this.reject(errorCode, (Object[])null, (String)null);
   }

   public void reject(String errorCode, String defaultMessage) {
      this.reject(errorCode, (Object[])null, defaultMessage);
   }

   public void rejectValue(@Nullable String field, String errorCode) {
      this.rejectValue(field, errorCode, (Object[])null, (String)null);
   }

   public void rejectValue(@Nullable String field, String errorCode, String defaultMessage) {
      this.rejectValue(field, errorCode, (Object[])null, defaultMessage);
   }

   public boolean hasErrors() {
      return !this.getAllErrors().isEmpty();
   }

   public int getErrorCount() {
      return this.getAllErrors().size();
   }

   public List getAllErrors() {
      List result = new LinkedList();
      result.addAll(this.getGlobalErrors());
      result.addAll(this.getFieldErrors());
      return Collections.unmodifiableList(result);
   }

   public boolean hasGlobalErrors() {
      return this.getGlobalErrorCount() > 0;
   }

   public int getGlobalErrorCount() {
      return this.getGlobalErrors().size();
   }

   @Nullable
   public ObjectError getGlobalError() {
      List globalErrors = this.getGlobalErrors();
      return !globalErrors.isEmpty() ? (ObjectError)globalErrors.get(0) : null;
   }

   public boolean hasFieldErrors() {
      return this.getFieldErrorCount() > 0;
   }

   public int getFieldErrorCount() {
      return this.getFieldErrors().size();
   }

   @Nullable
   public FieldError getFieldError() {
      List fieldErrors = this.getFieldErrors();
      return !fieldErrors.isEmpty() ? (FieldError)fieldErrors.get(0) : null;
   }

   public boolean hasFieldErrors(String field) {
      return this.getFieldErrorCount(field) > 0;
   }

   public int getFieldErrorCount(String field) {
      return this.getFieldErrors(field).size();
   }

   public List getFieldErrors(String field) {
      List fieldErrors = this.getFieldErrors();
      List result = new LinkedList();
      String fixedField = this.fixedField(field);
      Iterator var5 = fieldErrors.iterator();

      while(var5.hasNext()) {
         FieldError error = (FieldError)var5.next();
         if (this.isMatchingFieldError(fixedField, error)) {
            result.add(error);
         }
      }

      return Collections.unmodifiableList(result);
   }

   @Nullable
   public FieldError getFieldError(String field) {
      List fieldErrors = this.getFieldErrors(field);
      return !fieldErrors.isEmpty() ? (FieldError)fieldErrors.get(0) : null;
   }

   @Nullable
   public Class getFieldType(String field) {
      Object value = this.getFieldValue(field);
      return value != null ? value.getClass() : null;
   }

   protected boolean isMatchingFieldError(String field, FieldError fieldError) {
      if (field.equals(fieldError.getField())) {
         return true;
      } else {
         int endIndex = field.length() - 1;
         return endIndex >= 0 && field.charAt(endIndex) == '*' && (endIndex == 0 || field.regionMatches(0, fieldError.getField(), 0, endIndex));
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getName());
      sb.append(": ").append(this.getErrorCount()).append(" errors");
      Iterator var2 = this.getAllErrors().iterator();

      while(var2.hasNext()) {
         ObjectError error = (ObjectError)var2.next();
         sb.append('\n').append(error);
      }

      return sb.toString();
   }
}
