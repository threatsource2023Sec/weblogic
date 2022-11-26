package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class PassThroughSourceExtractor implements SourceExtractor {
   public Object extractSource(Object sourceCandidate, @Nullable Resource definingResource) {
      return sourceCandidate;
   }
}
