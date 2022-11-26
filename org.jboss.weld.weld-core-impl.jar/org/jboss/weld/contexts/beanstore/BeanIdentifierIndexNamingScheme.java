package org.jboss.weld.contexts.beanstore;

import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.serialization.BeanIdentifierIndex;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class BeanIdentifierIndexNamingScheme extends AbstractNamingScheme {
   private static final String FALLBACK_FLAG = "F_";
   private final BeanIdentifierIndex index;

   public BeanIdentifierIndexNamingScheme(String delimiter, BeanIdentifierIndex index) {
      super(delimiter);
      this.index = index;
   }

   public BeanIdentifier deprefix(String id) {
      String deprefixed = id.substring(this.getPrefix().length() + this.getDelimiter().length());
      if (this.index == null) {
         return new StringBeanIdentifier(deprefixed);
      } else if (deprefixed.startsWith("F_")) {
         return new StringBeanIdentifier(deprefixed.substring("F_".length()));
      } else {
         try {
            return this.index.getIdentifier(Integer.parseInt(deprefixed));
         } catch (NumberFormatException var4) {
            throw new IllegalStateException("Unable to deprefix id:" + id, var4);
         }
      }
   }

   public String prefix(BeanIdentifier id) {
      if (this.index == null) {
         return this.getPrefix() + this.getDelimiter() + id.asString();
      } else {
         Integer idx = this.index.getIndex(id);
         return idx == null ? this.getPrefix() + this.getDelimiter() + "F_" + id.asString() : this.getPrefix() + this.getDelimiter() + idx;
      }
   }
}
