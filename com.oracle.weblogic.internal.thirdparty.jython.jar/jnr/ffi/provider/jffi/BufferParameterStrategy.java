package jnr.ffi.provider.jffi;

import com.kenai.jffi.MemoryIO;
import com.kenai.jffi.ObjectParameterStrategy;
import com.kenai.jffi.ObjectParameterType;
import java.nio.Buffer;
import java.util.EnumSet;
import java.util.Iterator;

public final class BufferParameterStrategy extends ParameterStrategy {
   private final int shift;
   private static final BufferParameterStrategy[] DIRECT_BUFFER_PARAMETER_STRATEGIES;
   private static final BufferParameterStrategy[] HEAP_BUFFER_PARAMETER_STRATEGIES;

   private BufferParameterStrategy(ObjectParameterStrategy.StrategyType type, ObjectParameterType.ComponentType componentType) {
      super(type, ObjectParameterType.create(ObjectParameterType.ObjectType.ARRAY, componentType));
      this.shift = calculateShift(componentType);
   }

   public long address(Buffer buffer) {
      return buffer != null && buffer.isDirect() ? MemoryIO.getInstance().getDirectBufferAddress(buffer) + (long)(buffer.position() << this.shift) : 0L;
   }

   public long address(Object o) {
      return this.address((Buffer)o);
   }

   public Object object(Object o) {
      return ((Buffer)o).array();
   }

   public int offset(Object o) {
      Buffer buffer = (Buffer)o;
      return buffer.arrayOffset() + buffer.position();
   }

   public int length(Object o) {
      return ((Buffer)o).remaining();
   }

   static int calculateShift(ObjectParameterType.ComponentType componentType) {
      switch (componentType) {
         case BYTE:
            return 0;
         case SHORT:
         case CHAR:
            return 1;
         case INT:
         case BOOLEAN:
         case FLOAT:
            return 2;
         case LONG:
         case DOUBLE:
            return 3;
         default:
            throw new IllegalArgumentException("unsupported component type: " + componentType);
      }
   }

   static BufferParameterStrategy direct(ObjectParameterType.ComponentType componentType) {
      return DIRECT_BUFFER_PARAMETER_STRATEGIES[componentType.ordinal()];
   }

   static BufferParameterStrategy heap(ObjectParameterType.ComponentType componentType) {
      return HEAP_BUFFER_PARAMETER_STRATEGIES[componentType.ordinal()];
   }

   static {
      EnumSet componentTypes = EnumSet.allOf(ObjectParameterType.ComponentType.class);
      DIRECT_BUFFER_PARAMETER_STRATEGIES = new BufferParameterStrategy[componentTypes.size()];
      HEAP_BUFFER_PARAMETER_STRATEGIES = new BufferParameterStrategy[componentTypes.size()];

      ObjectParameterType.ComponentType componentType;
      for(Iterator var1 = componentTypes.iterator(); var1.hasNext(); HEAP_BUFFER_PARAMETER_STRATEGIES[componentType.ordinal()] = new BufferParameterStrategy(HEAP, componentType)) {
         componentType = (ObjectParameterType.ComponentType)var1.next();
         DIRECT_BUFFER_PARAMETER_STRATEGIES[componentType.ordinal()] = new BufferParameterStrategy(DIRECT, componentType);
      }

   }
}
