package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.objenesis.strategy.SerializingInstantiatorStrategy;

public class ObjenesisSerializer extends ObjenesisBase {
   public ObjenesisSerializer() {
      super(new SerializingInstantiatorStrategy());
   }

   public ObjenesisSerializer(boolean useCache) {
      super(new SerializingInstantiatorStrategy(), useCache);
   }
}
