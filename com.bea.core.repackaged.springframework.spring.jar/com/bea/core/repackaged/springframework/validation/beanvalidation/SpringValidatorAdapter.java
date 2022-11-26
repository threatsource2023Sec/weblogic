package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.beans.NotReadablePropertyException;
import com.bea.core.repackaged.springframework.context.MessageSourceResolvable;
import com.bea.core.repackaged.springframework.context.support.DefaultMessageSourceResolvable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.validation.BindingResult;
import com.bea.core.repackaged.springframework.validation.Errors;
import com.bea.core.repackaged.springframework.validation.FieldError;
import com.bea.core.repackaged.springframework.validation.ObjectError;
import com.bea.core.repackaged.springframework.validation.SmartValidator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

public class SpringValidatorAdapter implements SmartValidator, Validator {
   private static final Set internalAnnotationAttributes = new HashSet(4);
   @Nullable
   private Validator targetValidator;

   public SpringValidatorAdapter(Validator targetValidator) {
      Assert.notNull(targetValidator, (String)"Target Validator must not be null");
      this.targetValidator = targetValidator;
   }

   SpringValidatorAdapter() {
   }

   void setTargetValidator(Validator targetValidator) {
      this.targetValidator = targetValidator;
   }

   public boolean supports(Class clazz) {
      return this.targetValidator != null;
   }

   public void validate(Object target, Errors errors) {
      if (this.targetValidator != null) {
         this.processConstraintViolations(this.targetValidator.validate(target, new Class[0]), errors);
      }

   }

   public void validate(Object target, Errors errors, Object... validationHints) {
      if (this.targetValidator != null) {
         this.processConstraintViolations(this.targetValidator.validate(target, this.asValidationGroups(validationHints)), errors);
      }

   }

   public void validateValue(Class targetType, String fieldName, @Nullable Object value, Errors errors, Object... validationHints) {
      if (this.targetValidator != null) {
         this.processConstraintViolations(this.targetValidator.validateValue(targetType, fieldName, value, this.asValidationGroups(validationHints)), errors);
      }

   }

   private Class[] asValidationGroups(Object... validationHints) {
      Set groups = new LinkedHashSet(4);
      Object[] var3 = validationHints;
      int var4 = validationHints.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object hint = var3[var5];
         if (hint instanceof Class) {
            groups.add((Class)hint);
         }
      }

      return ClassUtils.toClassArray(groups);
   }

   protected void processConstraintViolations(Set violations, Errors errors) {
      Iterator var3 = violations.iterator();

      while(true) {
         ConstraintViolation violation;
         String field;
         FieldError fieldError;
         do {
            if (!var3.hasNext()) {
               return;
            }

            violation = (ConstraintViolation)var3.next();
            field = this.determineField(violation);
            fieldError = errors.getFieldError(field);
         } while(fieldError != null && fieldError.isBindingFailure());

         try {
            ConstraintDescriptor cd = violation.getConstraintDescriptor();
            String errorCode = this.determineErrorCode(cd);
            Object[] errorArgs = this.getArgumentsForConstraint(errors.getObjectName(), field, cd);
            if (errors instanceof BindingResult) {
               BindingResult bindingResult = (BindingResult)errors;
               String nestedField = bindingResult.getNestedPath() + field;
               if (nestedField.isEmpty()) {
                  String[] errorCodes = bindingResult.resolveMessageCodes(errorCode);
                  ObjectError error = new ViolationObjectError(errors.getObjectName(), errorCodes, errorArgs, violation, this);
                  bindingResult.addError(error);
               } else {
                  Object rejectedValue = this.getRejectedValue(field, violation, bindingResult);
                  String[] errorCodes = bindingResult.resolveMessageCodes(errorCode, field);
                  FieldError error = new ViolationFieldError(errors.getObjectName(), nestedField, rejectedValue, errorCodes, errorArgs, violation, this);
                  bindingResult.addError(error);
               }
            } else {
               errors.rejectValue(field, errorCode, errorArgs, violation.getMessage());
            }
         } catch (NotReadablePropertyException var15) {
            throw new IllegalStateException("JSR-303 validated property '" + field + "' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)", var15);
         }
      }
   }

   protected String determineField(ConstraintViolation violation) {
      Path path = violation.getPropertyPath();
      StringBuilder sb = new StringBuilder();
      boolean first = true;
      Iterator var5 = path.iterator();

      while(var5.hasNext()) {
         Path.Node node = (Path.Node)var5.next();
         if (node.isInIterable()) {
            sb.append('[');
            Object index = node.getIndex();
            if (index == null) {
               index = node.getKey();
            }

            if (index != null) {
               sb.append(index);
            }

            sb.append(']');
         }

         String name = node.getName();
         if (name != null && node.getKind() == ElementKind.PROPERTY && !name.startsWith("<")) {
            if (!first) {
               sb.append('.');
            }

            first = false;
            sb.append(name);
         }
      }

      return sb.toString();
   }

   protected String determineErrorCode(ConstraintDescriptor descriptor) {
      return descriptor.getAnnotation().annotationType().getSimpleName();
   }

   protected Object[] getArgumentsForConstraint(String objectName, String field, ConstraintDescriptor descriptor) {
      List arguments = new ArrayList();
      arguments.add(this.getResolvableField(objectName, field));
      Map attributesToExpose = new TreeMap();
      descriptor.getAttributes().forEach((attributeName, attributeValue) -> {
         if (!internalAnnotationAttributes.contains(attributeName)) {
            if (attributeValue instanceof String) {
               attributeValue = new ResolvableAttribute(attributeValue.toString());
            }

            attributesToExpose.put(attributeName, attributeValue);
         }

      });
      arguments.addAll(attributesToExpose.values());
      return arguments.toArray();
   }

   protected MessageSourceResolvable getResolvableField(String objectName, String field) {
      String[] codes = new String[]{objectName + "." + field, field};
      return new DefaultMessageSourceResolvable(codes, field);
   }

   @Nullable
   protected Object getRejectedValue(String field, ConstraintViolation violation, BindingResult bindingResult) {
      Object invalidValue = violation.getInvalidValue();
      if (!"".equals(field) && !field.contains("[]") && (invalidValue == violation.getLeafBean() || field.contains("[") || field.contains("."))) {
         invalidValue = bindingResult.getRawFieldValue(field);
      }

      return invalidValue;
   }

   protected boolean requiresMessageFormat(ConstraintViolation violation) {
      return containsSpringStylePlaceholder(violation.getMessage());
   }

   private static boolean containsSpringStylePlaceholder(@Nullable String message) {
      return message != null && message.contains("{0}");
   }

   public Set validate(Object object, Class... groups) {
      Assert.state(this.targetValidator != null, "No target Validator set");
      return this.targetValidator.validate(object, groups);
   }

   public Set validateProperty(Object object, String propertyName, Class... groups) {
      Assert.state(this.targetValidator != null, "No target Validator set");
      return this.targetValidator.validateProperty(object, propertyName, groups);
   }

   public Set validateValue(Class beanType, String propertyName, Object value, Class... groups) {
      Assert.state(this.targetValidator != null, "No target Validator set");
      return this.targetValidator.validateValue(beanType, propertyName, value, groups);
   }

   public BeanDescriptor getConstraintsForClass(Class clazz) {
      Assert.state(this.targetValidator != null, "No target Validator set");
      return this.targetValidator.getConstraintsForClass(clazz);
   }

   public Object unwrap(@Nullable Class type) {
      Assert.state(this.targetValidator != null, "No target Validator set");

      try {
         return type != null ? this.targetValidator.unwrap(type) : this.targetValidator;
      } catch (ValidationException var3) {
         if (Validator.class == type) {
            return this.targetValidator;
         } else {
            throw var3;
         }
      }
   }

   public ExecutableValidator forExecutables() {
      Assert.state(this.targetValidator != null, "No target Validator set");
      return this.targetValidator.forExecutables();
   }

   static {
      internalAnnotationAttributes.add("message");
      internalAnnotationAttributes.add("groups");
      internalAnnotationAttributes.add("payload");
   }

   private static class ViolationFieldError extends FieldError implements Serializable {
      @Nullable
      private transient SpringValidatorAdapter adapter;
      @Nullable
      private transient ConstraintViolation violation;

      public ViolationFieldError(String objectName, String field, @Nullable Object rejectedValue, String[] codes, Object[] arguments, ConstraintViolation violation, SpringValidatorAdapter adapter) {
         super(objectName, field, rejectedValue, false, codes, arguments, violation.getMessage());
         this.adapter = adapter;
         this.violation = violation;
         this.wrap(violation);
      }

      public boolean shouldRenderDefaultMessage() {
         return this.adapter != null && this.violation != null ? this.adapter.requiresMessageFormat(this.violation) : SpringValidatorAdapter.containsSpringStylePlaceholder(this.getDefaultMessage());
      }
   }

   private static class ViolationObjectError extends ObjectError implements Serializable {
      @Nullable
      private transient SpringValidatorAdapter adapter;
      @Nullable
      private transient ConstraintViolation violation;

      public ViolationObjectError(String objectName, String[] codes, Object[] arguments, ConstraintViolation violation, SpringValidatorAdapter adapter) {
         super(objectName, codes, arguments, violation.getMessage());
         this.adapter = adapter;
         this.violation = violation;
         this.wrap(violation);
      }

      public boolean shouldRenderDefaultMessage() {
         return this.adapter != null && this.violation != null ? this.adapter.requiresMessageFormat(this.violation) : SpringValidatorAdapter.containsSpringStylePlaceholder(this.getDefaultMessage());
      }
   }

   private static class ResolvableAttribute implements MessageSourceResolvable, Serializable {
      private final String resolvableString;

      public ResolvableAttribute(String resolvableString) {
         this.resolvableString = resolvableString;
      }

      public String[] getCodes() {
         return new String[]{this.resolvableString};
      }

      @Nullable
      public Object[] getArguments() {
         return null;
      }

      public String getDefaultMessage() {
         return this.resolvableString;
      }

      public String toString() {
         return this.resolvableString;
      }
   }
}
