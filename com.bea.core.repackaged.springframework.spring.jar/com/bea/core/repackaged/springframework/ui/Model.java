package com.bea.core.repackaged.springframework.ui;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Map;

public interface Model {
   Model addAttribute(String var1, @Nullable Object var2);

   Model addAttribute(Object var1);

   Model addAllAttributes(Collection var1);

   Model addAllAttributes(Map var1);

   Model mergeAttributes(Map var1);

   boolean containsAttribute(String var1);

   Map asMap();
}
