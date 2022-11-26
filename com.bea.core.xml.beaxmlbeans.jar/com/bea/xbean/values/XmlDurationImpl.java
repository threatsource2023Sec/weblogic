package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlDuration;

public class XmlDurationImpl extends JavaGDurationHolderEx implements XmlDuration {
   public XmlDurationImpl() {
      super(XmlDuration.type, false);
   }

   public XmlDurationImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
