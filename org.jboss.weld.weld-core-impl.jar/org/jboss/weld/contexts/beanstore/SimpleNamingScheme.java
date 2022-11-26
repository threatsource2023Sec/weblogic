package org.jboss.weld.contexts.beanstore;

import org.jboss.weld.logging.ContextLogger;

public class SimpleNamingScheme extends AbstractNamingScheme {
   private final String prefix;

   public SimpleNamingScheme(String prefix) {
      super("#");
      if (prefix.contains(this.getDelimiter())) {
         throw ContextLogger.LOG.delimiterInPrefix(this.getDelimiter(), prefix);
      } else {
         this.prefix = prefix;
      }
   }

   protected String getPrefix() {
      return this.prefix;
   }
}
