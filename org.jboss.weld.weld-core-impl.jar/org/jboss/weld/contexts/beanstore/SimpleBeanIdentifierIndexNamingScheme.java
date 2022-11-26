package org.jboss.weld.contexts.beanstore;

import org.jboss.weld.logging.ContextLogger;
import org.jboss.weld.serialization.BeanIdentifierIndex;

public class SimpleBeanIdentifierIndexNamingScheme extends BeanIdentifierIndexNamingScheme {
   private final String prefix;

   public SimpleBeanIdentifierIndexNamingScheme(String prefix, BeanIdentifierIndex index) {
      super("#", index);
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
