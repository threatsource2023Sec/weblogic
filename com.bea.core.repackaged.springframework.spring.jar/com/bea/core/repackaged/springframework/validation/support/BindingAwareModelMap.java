package com.bea.core.repackaged.springframework.validation.support;

import com.bea.core.repackaged.springframework.ui.ExtendedModelMap;
import com.bea.core.repackaged.springframework.validation.BindingResult;
import java.util.Map;

public class BindingAwareModelMap extends ExtendedModelMap {
   public Object put(String key, Object value) {
      this.removeBindingResultIfNecessary(key, value);
      return super.put(key, value);
   }

   public void putAll(Map map) {
      map.forEach(this::removeBindingResultIfNecessary);
      super.putAll(map);
   }

   private void removeBindingResultIfNecessary(Object key, Object value) {
      if (key instanceof String) {
         String attributeName = (String)key;
         if (!attributeName.startsWith(BindingResult.MODEL_KEY_PREFIX)) {
            String bindingResultKey = BindingResult.MODEL_KEY_PREFIX + attributeName;
            BindingResult bindingResult = (BindingResult)this.get(bindingResultKey);
            if (bindingResult != null && bindingResult.getTarget() != value) {
               this.remove(bindingResultKey);
            }
         }
      }

   }
}
