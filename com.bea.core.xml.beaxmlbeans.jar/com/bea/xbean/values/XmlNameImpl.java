package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlName;

public class XmlNameImpl extends JavaStringHolderEx implements XmlName {
   public XmlNameImpl() {
      super(XmlName.type, false);
   }

   public XmlNameImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
