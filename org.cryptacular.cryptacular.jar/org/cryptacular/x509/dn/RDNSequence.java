package org.cryptacular.x509.dn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RDNSequence implements Iterable {
   private final List rdns = new ArrayList(10);

   public void add(RDN rdn) {
      this.rdns.add(rdn);
   }

   public Iterator iterator() {
      return this.rdns.iterator();
   }

   public Iterable backward() {
      return () -> {
         return new Iterator() {
            private final ListIterator it;

            {
               this.it = RDNSequence.this.rdns.listIterator(RDNSequence.this.rdns.size());
            }

            public boolean hasNext() {
               return this.it.hasPrevious();
            }

            public RDN next() {
               return (RDN)this.it.previous();
            }

            public void remove() {
               throw new UnsupportedOperationException("Remove not supported");
            }
         };
      };
   }

   public List getValues(AttributeType type) {
      List values = new ArrayList(this.rdns.size());
      Iterator var3 = this.rdns.iterator();

      while(var3.hasNext()) {
         RDN rdn = (RDN)var3.next();
         values.addAll(rdn.getAttributes().getValues(type));
      }

      return Collections.unmodifiableList(values);
   }

   public String getValue(AttributeType type) {
      List values = this.getValues(type);
      return values.size() > 0 ? (String)values.get(0) : null;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      int i = 0;
      Iterator var3 = this.iterator();

      while(var3.hasNext()) {
         RDN rdn = (RDN)var3.next();

         Attribute attr;
         for(Iterator var5 = rdn.getAttributes().iterator(); var5.hasNext(); builder.append(attr.getType()).append('=').append(attr.getValue())) {
            attr = (Attribute)var5.next();
            if (i++ > 0) {
               builder.append(", ");
            }
         }
      }

      return builder.toString();
   }
}
