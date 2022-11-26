package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.XMLMetaData;

public interface Path extends Value {
   void get(FieldMetaData var1, boolean var2);

   FieldMetaData last();

   void get(FieldMetaData var1, XMLMetaData var2);

   void get(XMLMetaData var1, String var2);

   XMLMetaData getXmlMapping();
}
