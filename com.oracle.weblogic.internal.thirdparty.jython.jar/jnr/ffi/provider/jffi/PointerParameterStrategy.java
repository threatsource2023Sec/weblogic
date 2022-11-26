package jnr.ffi.provider.jffi;

import com.kenai.jffi.ObjectParameterStrategy;
import com.kenai.jffi.ObjectParameterType;
import jnr.ffi.Pointer;

public final class PointerParameterStrategy extends ParameterStrategy {
   public static final PointerParameterStrategy DIRECT;
   public static final PointerParameterStrategy HEAP;

   PointerParameterStrategy(ObjectParameterStrategy.StrategyType type) {
      super(type, ObjectParameterType.create(ObjectParameterType.ARRAY, ObjectParameterType.BYTE));
   }

   public long address(Object o) {
      return this.address((Pointer)o);
   }

   public long address(Pointer pointer) {
      return pointer != null ? pointer.address() : 0L;
   }

   public Object object(Object o) {
      return ((Pointer)o).array();
   }

   public int offset(Object o) {
      return ((Pointer)o).arrayOffset();
   }

   public int length(Object o) {
      return ((Pointer)o).arrayLength();
   }

   static {
      DIRECT = new PointerParameterStrategy(ObjectParameterStrategy.StrategyType.DIRECT);
      HEAP = new PointerParameterStrategy(ObjectParameterStrategy.StrategyType.HEAP);
   }
}
