package org.apache.openjpa.persistence;

import org.apache.openjpa.kernel.Seq;

public interface Generator {
   String UUID_HEX = "uuid-hex";
   String UUID_STRING = "uuid-string";

   String getName();

   Object next();

   Object current();

   void allocate(int var1);

   /** @deprecated */
   Seq getDelegate();
}
