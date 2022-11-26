package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface Mergeable {
   boolean isMergeEnabled();

   Object merge(@Nullable Object var1);
}
