package weblogic.xml.schema.binding.util.runtime;

public interface Accumulator {
   int DEFAULT_INITIAL_CAPACITY = 16;

   void append(Object var1);

   Object getFinalArray();
}
