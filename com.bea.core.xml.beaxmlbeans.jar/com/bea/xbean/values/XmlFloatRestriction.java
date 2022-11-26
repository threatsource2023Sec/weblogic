package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlFloat;

public class XmlFloatRestriction extends JavaFloatHolderEx implements XmlFloat {
   public XmlFloatRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
