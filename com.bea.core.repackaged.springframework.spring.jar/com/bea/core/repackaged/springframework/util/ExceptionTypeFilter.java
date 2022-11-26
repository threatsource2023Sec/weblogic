package com.bea.core.repackaged.springframework.util;

import java.util.Collection;

public class ExceptionTypeFilter extends InstanceFilter {
   public ExceptionTypeFilter(Collection includes, Collection excludes, boolean matchIfEmpty) {
      super(includes, excludes, matchIfEmpty);
   }

   protected boolean match(Class instance, Class candidate) {
      return candidate.isAssignableFrom(instance);
   }
}
