package com.bea.core.repackaged.springframework.core.convert.converter;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Set;

public interface GenericConverter {
   @Nullable
   Set getConvertibleTypes();

   @Nullable
   Object convert(@Nullable Object var1, TypeDescriptor var2, TypeDescriptor var3);

   public static final class ConvertiblePair {
      private final Class sourceType;
      private final Class targetType;

      public ConvertiblePair(Class sourceType, Class targetType) {
         Assert.notNull(sourceType, (String)"Source type must not be null");
         Assert.notNull(targetType, (String)"Target type must not be null");
         this.sourceType = sourceType;
         this.targetType = targetType;
      }

      public Class getSourceType() {
         return this.sourceType;
      }

      public Class getTargetType() {
         return this.targetType;
      }

      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (other != null && other.getClass() == ConvertiblePair.class) {
            ConvertiblePair otherPair = (ConvertiblePair)other;
            return this.sourceType == otherPair.sourceType && this.targetType == otherPair.targetType;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
      }

      public String toString() {
         return this.sourceType.getName() + " -> " + this.targetType.getName();
      }
   }
}
