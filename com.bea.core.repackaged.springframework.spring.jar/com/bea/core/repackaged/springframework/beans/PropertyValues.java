package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PropertyValues extends Iterable {
   default Iterator iterator() {
      return Arrays.asList(this.getPropertyValues()).iterator();
   }

   default Spliterator spliterator() {
      return Spliterators.spliterator(this.getPropertyValues(), 0);
   }

   default Stream stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   PropertyValue[] getPropertyValues();

   @Nullable
   PropertyValue getPropertyValue(String var1);

   PropertyValues changesSince(PropertyValues var1);

   boolean contains(String var1);

   boolean isEmpty();
}
