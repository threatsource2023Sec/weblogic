package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class NullSourceExtractor implements SourceExtractor {
   @Nullable
   public Object extractSource(Object sourceCandidate, @Nullable Resource definitionResource) {
      return null;
   }
}
