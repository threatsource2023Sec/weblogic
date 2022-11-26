package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class FieldError extends ObjectError {
   private final String field;
   @Nullable
   private final Object rejectedValue;
   private final boolean bindingFailure;

   public FieldError(String objectName, String field, String defaultMessage) {
      this(objectName, field, (Object)null, false, (String[])null, (Object[])null, defaultMessage);
   }

   public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {
      super(objectName, codes, arguments, defaultMessage);
      Assert.notNull(field, (String)"Field must not be null");
      this.field = field;
      this.rejectedValue = rejectedValue;
      this.bindingFailure = bindingFailure;
   }

   public String getField() {
      return this.field;
   }

   @Nullable
   public Object getRejectedValue() {
      return this.rejectedValue;
   }

   public boolean isBindingFailure() {
      return this.bindingFailure;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else {
         FieldError otherError = (FieldError)other;
         return this.getField().equals(otherError.getField()) && ObjectUtils.nullSafeEquals(this.getRejectedValue(), otherError.getRejectedValue()) && this.isBindingFailure() == otherError.isBindingFailure();
      }
   }

   public int hashCode() {
      int hashCode = super.hashCode();
      hashCode = 29 * hashCode + this.getField().hashCode();
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.getRejectedValue());
      hashCode = 29 * hashCode + (this.isBindingFailure() ? 1 : 0);
      return hashCode;
   }

   public String toString() {
      return "Field error in object '" + this.getObjectName() + "' on field '" + this.field + "': rejected value [" + ObjectUtils.nullSafeToString(this.rejectedValue) + "]; " + this.resolvableToString();
   }
}
