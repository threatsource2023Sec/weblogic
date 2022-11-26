package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlPositiveInteger;

public class XmlPositiveIntegerImpl extends JavaIntegerHolderEx implements XmlPositiveInteger {
   public XmlPositiveIntegerImpl() {
      super(XmlPositiveInteger.type, false);
   }

   public XmlPositiveIntegerImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
