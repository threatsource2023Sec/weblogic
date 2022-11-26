package com.bea.xbeanmarshal.runtime.internal.util.collections;

public interface Accumulator {
   int DEFAULT_INITIAL_CAPACITY = 16;

   void append(Object var1);

   void appendDefault();

   void set(int var1, Object var2);

   int size();

   Object getFinalArray();
}
