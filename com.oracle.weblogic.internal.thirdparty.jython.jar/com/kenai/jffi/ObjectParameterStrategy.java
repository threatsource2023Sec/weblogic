package com.kenai.jffi;

public abstract class ObjectParameterStrategy {
   private final boolean isDirect;
   final int typeInfo;
   protected static final StrategyType DIRECT;
   protected static final StrategyType HEAP;

   public ObjectParameterStrategy(boolean isDirect) {
      this(isDirect, ObjectParameterType.INVALID);
   }

   public ObjectParameterStrategy(boolean isDirect, ObjectParameterType type) {
      this.isDirect = isDirect;
      this.typeInfo = type.typeInfo;
   }

   public ObjectParameterStrategy(StrategyType type) {
      this(type, ObjectParameterType.INVALID);
   }

   public ObjectParameterStrategy(StrategyType strategyType, ObjectParameterType parameterType) {
      this.isDirect = strategyType == DIRECT;
      this.typeInfo = parameterType.typeInfo;
   }

   public final boolean isDirect() {
      return this.isDirect;
   }

   final int objectInfo(ObjectParameterInfo info) {
      int objectInfo = info.asObjectInfo();
      return this.typeInfo != 0 ? objectInfo & 16777215 | this.typeInfo : objectInfo;
   }

   public abstract long address(Object var1);

   public abstract Object object(Object var1);

   public abstract int offset(Object var1);

   public abstract int length(Object var1);

   static {
      DIRECT = ObjectParameterStrategy.StrategyType.DIRECT;
      HEAP = ObjectParameterStrategy.StrategyType.HEAP;
   }

   protected static enum StrategyType {
      DIRECT,
      HEAP;
   }
}
