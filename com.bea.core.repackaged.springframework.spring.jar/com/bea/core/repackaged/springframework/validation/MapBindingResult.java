package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.util.Map;

public class MapBindingResult extends AbstractBindingResult implements Serializable {
   private final Map target;

   public MapBindingResult(Map target, String objectName) {
      super(objectName);
      Assert.notNull(target, (String)"Target Map must not be null");
      this.target = target;
   }

   public final Map getTargetMap() {
      return this.target;
   }

   public final Object getTarget() {
      return this.target;
   }

   @Nullable
   protected Object getActualFieldValue(String field) {
      return this.target.get(field);
   }
}
