package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface SourceExtractor {
   @Nullable
   Object extractSource(Object var1, @Nullable Resource var2);
}
