package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.ConfigurablePropertyAccessor;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class DirectFieldBindingResult extends AbstractPropertyBindingResult {
   @Nullable
   private final Object target;
   private final boolean autoGrowNestedPaths;
   @Nullable
   private transient ConfigurablePropertyAccessor directFieldAccessor;

   public DirectFieldBindingResult(@Nullable Object target, String objectName) {
      this(target, objectName, true);
   }

   public DirectFieldBindingResult(@Nullable Object target, String objectName, boolean autoGrowNestedPaths) {
      super(objectName);
      this.target = target;
      this.autoGrowNestedPaths = autoGrowNestedPaths;
   }

   @Nullable
   public final Object getTarget() {
      return this.target;
   }

   public final ConfigurablePropertyAccessor getPropertyAccessor() {
      if (this.directFieldAccessor == null) {
         this.directFieldAccessor = this.createDirectFieldAccessor();
         this.directFieldAccessor.setExtractOldValueForEditor(true);
         this.directFieldAccessor.setAutoGrowNestedPaths(this.autoGrowNestedPaths);
      }

      return this.directFieldAccessor;
   }

   protected ConfigurablePropertyAccessor createDirectFieldAccessor() {
      if (this.target == null) {
         throw new IllegalStateException("Cannot access fields on null target instance '" + this.getObjectName() + "'");
      } else {
         return PropertyAccessorFactory.forDirectFieldAccess(this.target);
      }
   }
}
