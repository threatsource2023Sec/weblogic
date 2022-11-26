package com.bea.logging;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ParentDelegatingFilter implements Filter {
   private Logger logger;

   public ParentDelegatingFilter(Logger logger) {
      this.logger = logger;
   }

   public boolean isLoggable(LogRecord record) {
      Logger l = this.logger;

      Filter f;
      do {
         if (l == null) {
            return true;
         }

         l = l.getParent();
         if (l == null) {
            return true;
         }

         f = l.getFilter();
      } while(f == null);

      return f.isLoggable(record);
   }
}
