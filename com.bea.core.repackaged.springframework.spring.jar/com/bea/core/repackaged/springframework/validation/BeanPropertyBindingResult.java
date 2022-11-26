package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.ConfigurablePropertyAccessor;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;

public class BeanPropertyBindingResult extends AbstractPropertyBindingResult implements Serializable {
   @Nullable
   private final Object target;
   private final boolean autoGrowNestedPaths;
   private final int autoGrowCollectionLimit;
   @Nullable
   private transient BeanWrapper beanWrapper;

   public BeanPropertyBindingResult(@Nullable Object target, String objectName) {
      this(target, objectName, true, Integer.MAX_VALUE);
   }

   public BeanPropertyBindingResult(@Nullable Object target, String objectName, boolean autoGrowNestedPaths, int autoGrowCollectionLimit) {
      super(objectName);
      this.target = target;
      this.autoGrowNestedPaths = autoGrowNestedPaths;
      this.autoGrowCollectionLimit = autoGrowCollectionLimit;
   }

   @Nullable
   public final Object getTarget() {
      return this.target;
   }

   public final ConfigurablePropertyAccessor getPropertyAccessor() {
      if (this.beanWrapper == null) {
         this.beanWrapper = this.createBeanWrapper();
         this.beanWrapper.setExtractOldValueForEditor(true);
         this.beanWrapper.setAutoGrowNestedPaths(this.autoGrowNestedPaths);
         this.beanWrapper.setAutoGrowCollectionLimit(this.autoGrowCollectionLimit);
      }

      return this.beanWrapper;
   }

   protected BeanWrapper createBeanWrapper() {
      if (this.target == null) {
         throw new IllegalStateException("Cannot access properties on null bean instance '" + this.getObjectName() + "'");
      } else {
         return PropertyAccessorFactory.forBeanPropertyAccess(this.target);
      }
   }
}
