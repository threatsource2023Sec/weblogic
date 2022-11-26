package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlTime;

public class XmlTimeImpl extends JavaGDateHolderEx implements XmlTime {
   public XmlTimeImpl() {
      super(XmlTime.type, false);
   }

   public XmlTimeImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
