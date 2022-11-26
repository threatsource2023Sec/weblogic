package org.cryptacular.x509.dn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Attributes implements Iterable {
   private final List attributes = new ArrayList(5);

   public void add(String typeOid, String value) {
      StandardAttributeType type = StandardAttributeType.fromOid(typeOid);
      if (type != null) {
         this.add(new Attribute(type, value));
      } else {
         this.add(new Attribute(new UnknownAttributeType(typeOid), value));
      }

   }

   public void add(Attribute attr) {
      if (attr == null) {
         throw new IllegalArgumentException("Attribute cannot be null");
      } else {
         this.attributes.add(attr);
      }
   }

   public int size() {
      return this.attributes.size();
   }

   public List getAll() {
      return Collections.unmodifiableList(this.attributes);
   }

   public List getValues(AttributeType type) {
      List values = new ArrayList(this.attributes.size());
      values.addAll((Collection)this.attributes.stream().filter((attr) -> {
         return attr.getType().equals(type);
      }).map(Attribute::getValue).collect(Collectors.toList()));
      return Collections.unmodifiableList(values);
   }

   public String getValue(AttributeType type) {
      Iterator var2 = this.attributes.iterator();

      Attribute attr;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         attr = (Attribute)var2.next();
      } while(!attr.getType().equals(type));

      return attr.getValue();
   }

   public Iterator iterator() {
      return this.attributes.iterator();
   }
}
