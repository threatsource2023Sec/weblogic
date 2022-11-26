package org.jboss.weld.contexts.beanstore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public abstract class AbstractNamingScheme implements NamingScheme {
   private final String delimiter;

   public AbstractNamingScheme(String delimiter) {
      this.delimiter = delimiter;
   }

   public boolean accept(String id) {
      String prefix = this.getPrefix();
      return id.startsWith(prefix) && id.startsWith(this.delimiter, prefix.length());
   }

   public BeanIdentifier deprefix(String id) {
      return new StringBeanIdentifier(id.substring(this.getPrefix().length() + this.delimiter.length()));
   }

   public String prefix(BeanIdentifier id) {
      return this.getPrefix() + this.delimiter + id.asString();
   }

   public Collection filterIds(Iterator iterator) {
      if (!iterator.hasNext()) {
         return Collections.emptyList();
      } else {
         List filtered = new ArrayList();

         while(iterator.hasNext()) {
            String id = (String)iterator.next();
            if (this.accept(id)) {
               filtered.add(id);
            }
         }

         return filtered;
      }
   }

   public Collection deprefix(Collection ids) {
      return (Collection)ids.stream().map(this::deprefix).collect(Collectors.toList());
   }

   public Collection prefix(Collection ids) {
      return (Collection)ids.stream().map(this::prefix).collect(Collectors.toList());
   }

   protected abstract String getPrefix();

   protected String getDelimiter() {
      return this.delimiter;
   }
}
