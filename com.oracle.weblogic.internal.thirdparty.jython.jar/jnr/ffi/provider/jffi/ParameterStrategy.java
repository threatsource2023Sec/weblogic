package jnr.ffi.provider.jffi;

import com.kenai.jffi.ObjectParameterStrategy;
import com.kenai.jffi.ObjectParameterType;

public abstract class ParameterStrategy extends ObjectParameterStrategy {
   public final int objectCount;

   protected ParameterStrategy(ObjectParameterStrategy.StrategyType type) {
      super(type);
      this.objectCount = type == HEAP ? 1 : 0;
   }

   protected ParameterStrategy(ObjectParameterStrategy.StrategyType type, ObjectParameterType parameterType) {
      super(type, parameterType);
      this.objectCount = type == HEAP ? 1 : 0;
   }
}
