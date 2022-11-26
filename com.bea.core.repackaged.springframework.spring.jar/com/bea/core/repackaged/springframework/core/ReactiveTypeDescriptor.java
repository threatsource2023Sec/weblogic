package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.function.Supplier;

public final class ReactiveTypeDescriptor {
   private final Class reactiveType;
   private final boolean multiValue;
   private final boolean noValue;
   @Nullable
   private final Supplier emptyValueSupplier;

   private ReactiveTypeDescriptor(Class reactiveType, boolean multiValue, boolean noValue, @Nullable Supplier emptySupplier) {
      Assert.notNull(reactiveType, (String)"'reactiveType' must not be null");
      this.reactiveType = reactiveType;
      this.multiValue = multiValue;
      this.noValue = noValue;
      this.emptyValueSupplier = emptySupplier;
   }

   public Class getReactiveType() {
      return this.reactiveType;
   }

   public boolean isMultiValue() {
      return this.multiValue;
   }

   public boolean isNoValue() {
      return this.noValue;
   }

   public boolean supportsEmpty() {
      return this.emptyValueSupplier != null;
   }

   public Object getEmptyValue() {
      Assert.state(this.emptyValueSupplier != null, "Empty values not supported");
      return this.emptyValueSupplier.get();
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else {
         return other != null && this.getClass() == other.getClass() ? this.reactiveType.equals(((ReactiveTypeDescriptor)other).reactiveType) : false;
      }
   }

   public int hashCode() {
      return this.reactiveType.hashCode();
   }

   public static ReactiveTypeDescriptor multiValue(Class type, Supplier emptySupplier) {
      return new ReactiveTypeDescriptor(type, true, false, emptySupplier);
   }

   public static ReactiveTypeDescriptor singleOptionalValue(Class type, Supplier emptySupplier) {
      return new ReactiveTypeDescriptor(type, false, false, emptySupplier);
   }

   public static ReactiveTypeDescriptor singleRequiredValue(Class type) {
      return new ReactiveTypeDescriptor(type, false, false, (Supplier)null);
   }

   public static ReactiveTypeDescriptor noValue(Class type, Supplier emptySupplier) {
      return new ReactiveTypeDescriptor(type, false, true, emptySupplier);
   }
}
