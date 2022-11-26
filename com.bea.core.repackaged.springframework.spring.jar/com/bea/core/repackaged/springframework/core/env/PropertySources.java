package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface PropertySources extends Iterable {
   default Stream stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   boolean contains(String var1);

   @Nullable
   PropertySource get(String var1);
}
