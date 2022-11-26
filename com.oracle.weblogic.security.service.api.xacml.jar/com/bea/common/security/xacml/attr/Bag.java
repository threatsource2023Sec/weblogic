package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

public abstract class Bag implements Collection {
   protected int hash = 0;
   protected URI dataType;
   protected Type type;

   protected Bag() {
   }

   public Bag(URI dataType) {
      this.dataType = dataType;
   }

   public Bag(Type type) {
      this.type = type;
   }

   public URI getDataType() throws URISyntaxException {
      if (this.dataType == null) {
         this.dataType = this.getType().getDataType();
      }

      return this.dataType;
   }

   public Type getType() {
      return this.type;
   }

   public int hashCode() {
      if (this.hash == 0) {
         this.hash = this.internalHashCode();
      }

      return this.hash;
   }

   public boolean isBag() {
      return true;
   }

   public abstract boolean equals(Object var1);

   public abstract int internalHashCode();

   public String toString() {
      StringBuffer s = new StringBuffer();
      s.append('[');
      switch (this.size()) {
         case 0:
            return "<empty>";
         case 1:
            return ((AttributeValue)this.iterator().next()).toString();
         default:
            Iterator it = this.iterator();

            while(it.hasNext()) {
               s.append(it.next());
               if (it.hasNext()) {
                  s.append(',');
               }
            }

            s.append(']');
            return s.toString();
      }
   }
}
