package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.List;
import java.util.Map;

public interface MultiValueMap extends Map {
   @Nullable
   Object getFirst(Object var1);

   void add(Object var1, @Nullable Object var2);

   void addAll(Object var1, List var2);

   void addAll(MultiValueMap var1);

   void set(Object var1, @Nullable Object var2);

   void setAll(Map var1);

   Map toSingleValueMap();
}
