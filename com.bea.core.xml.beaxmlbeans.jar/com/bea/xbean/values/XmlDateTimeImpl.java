package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlDateTime;

public class XmlDateTimeImpl extends JavaGDateHolderEx implements XmlDateTime {
   public XmlDateTimeImpl() {
      super(XmlDateTime.type, false);
   }

   public XmlDateTimeImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
