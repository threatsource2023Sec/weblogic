package com.bea.core.repackaged.springframework.core.style;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ToStringStyler {
   void styleStart(StringBuilder var1, Object var2);

   void styleEnd(StringBuilder var1, Object var2);

   void styleField(StringBuilder var1, String var2, @Nullable Object var3);

   void styleValue(StringBuilder var1, Object var2);

   void styleFieldSeparator(StringBuilder var1);
}
