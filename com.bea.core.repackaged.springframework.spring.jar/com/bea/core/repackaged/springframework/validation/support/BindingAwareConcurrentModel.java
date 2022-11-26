package com.bea.core.repackaged.springframework.validation.support;

import com.bea.core.repackaged.springframework.ui.ConcurrentModel;
import com.bea.core.repackaged.springframework.validation.BindingResult;

public class BindingAwareConcurrentModel extends ConcurrentModel {
   public Object put(String key, Object value) {
      this.removeBindingResultIfNecessary(key, value);
      return super.put(key, value);
   }

   private void removeBindingResultIfNecessary(String key, Object value) {
      if (!key.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
         String resultKey = BindingResult.MODEL_KEY_PREFIX + key;
         BindingResult result = (BindingResult)this.get(resultKey);
         if (result != null && result.getTarget() != value) {
            this.remove(resultKey);
         }
      }

   }
}
