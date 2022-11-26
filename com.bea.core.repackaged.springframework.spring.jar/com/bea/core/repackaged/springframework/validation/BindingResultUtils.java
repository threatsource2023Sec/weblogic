package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Map;

public abstract class BindingResultUtils {
   @Nullable
   public static BindingResult getBindingResult(Map model, String name) {
      Assert.notNull(model, (String)"Model map must not be null");
      Assert.notNull(name, (String)"Name must not be null");
      Object attr = model.get(BindingResult.MODEL_KEY_PREFIX + name);
      if (attr != null && !(attr instanceof BindingResult)) {
         throw new IllegalStateException("BindingResult attribute is not of type BindingResult: " + attr);
      } else {
         return (BindingResult)attr;
      }
   }

   public static BindingResult getRequiredBindingResult(Map model, String name) {
      BindingResult bindingResult = getBindingResult(model, name);
      if (bindingResult == null) {
         throw new IllegalStateException("No BindingResult attribute found for name '" + name + "'- have you exposed the correct model?");
      } else {
         return bindingResult;
      }
   }
}
