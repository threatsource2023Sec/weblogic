package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface HierarchicalMessageSource extends MessageSource {
   void setParentMessageSource(@Nullable MessageSource var1);

   @Nullable
   MessageSource getParentMessageSource();
}
