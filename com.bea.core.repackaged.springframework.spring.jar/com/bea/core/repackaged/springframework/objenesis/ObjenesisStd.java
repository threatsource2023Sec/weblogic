package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.objenesis.strategy.StdInstantiatorStrategy;

public class ObjenesisStd extends ObjenesisBase {
   public ObjenesisStd() {
      super(new StdInstantiatorStrategy());
   }

   public ObjenesisStd(boolean useCache) {
      super(new StdInstantiatorStrategy(), useCache);
   }
}
