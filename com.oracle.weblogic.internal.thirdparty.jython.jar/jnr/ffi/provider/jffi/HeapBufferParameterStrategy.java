package jnr.ffi.provider.jffi;

import com.kenai.jffi.ObjectParameterType;
import java.nio.Buffer;
import java.util.EnumSet;
import java.util.Iterator;

final class HeapBufferParameterStrategy extends ParameterStrategy {
   private static final HeapBufferParameterStrategy[] heapBufferStrategies;

   public HeapBufferParameterStrategy(ObjectParameterType.ComponentType componentType) {
      super(HEAP, ObjectParameterType.create(ObjectParameterType.ARRAY, componentType));
   }

   public long address(Object o) {
      return 0L;
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

   static HeapBufferParameterStrategy get(ObjectParameterType.ComponentType componentType) {
      return heapBufferStrategies[componentType.ordinal()];
   }

   static {
      EnumSet componentTypes = EnumSet.allOf(ObjectParameterType.ComponentType.class);
      heapBufferStrategies = new HeapBufferParameterStrategy[componentTypes.size()];

      ObjectParameterType.ComponentType componentType;
      for(Iterator var1 = componentTypes.iterator(); var1.hasNext(); heapBufferStrategies[componentType.ordinal()] = new HeapBufferParameterStrategy(componentType)) {
         componentType = (ObjectParameterType.ComponentType)var1.next();
      }

   }
}
