package org.python.icu.util;

import java.util.Date;

class Range {
   public Date start;
   public DateRule rule;

   public Range(Date start, DateRule rule) {
      this.start = start;
      this.rule = rule;
   }
}
