package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.context.support.DefaultMessageSourceResolvable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ObjectError extends DefaultMessageSourceResolvable {
   private final String objectName;
   @Nullable
   private transient Object source;

   public ObjectError(String objectName, String defaultMessage) {
      this(objectName, (String[])null, (Object[])null, defaultMessage);
   }

   public ObjectError(String objectName, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {
      super(codes, arguments, defaultMessage);
      Assert.notNull(objectName, (String)"Object name must not be null");
      this.objectName = objectName;
   }

   public String getObjectName() {
      return this.objectName;
   }

   public void wrap(Object source) {
      if (this.source != null) {
         throw new IllegalStateException("Already wrapping " + this.source);
      } else {
         this.source = source;
      }
   }

   public Object unwrap(Class sourceType) {
      if (sourceType.isInstance(this.source)) {
         return sourceType.cast(this.source);
      } else {
         if (this.source instanceof Throwable) {
            Throwable cause = ((Throwable)this.source).getCause();
            if (sourceType.isInstance(cause)) {
               return sourceType.cast(cause);
            }
         }

         throw new IllegalArgumentException("No source object of the given type available: " + sourceType);
      }
   }

   public boolean contains(Class sourceType) {
      return sourceType.isInstance(this.source) || this.source instanceof Throwable && sourceType.isInstance(((Throwable)this.source).getCause());
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (other != null && other.getClass() == this.getClass() && super.equals(other)) {
         ObjectError otherError = (ObjectError)other;
         return this.getObjectName().equals(otherError.getObjectName());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 29 * super.hashCode() + this.getObjectName().hashCode();
   }

   public String toString() {
      return "Error in object '" + this.objectName + "': " + this.resolvableToString();
   }
}
