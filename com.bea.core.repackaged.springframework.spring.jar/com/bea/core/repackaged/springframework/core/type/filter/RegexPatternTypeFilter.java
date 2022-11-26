package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.springframework.core.type.ClassMetadata;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.regex.Pattern;

public class RegexPatternTypeFilter extends AbstractClassTestingTypeFilter {
   private final Pattern pattern;

   public RegexPatternTypeFilter(Pattern pattern) {
      Assert.notNull(pattern, (String)"Pattern must not be null");
      this.pattern = pattern;
   }

   protected boolean match(ClassMetadata metadata) {
      return this.pattern.matcher(metadata.getClassName()).matches();
   }
}
