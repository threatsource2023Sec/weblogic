package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlENTITY;

public class XmlEntityImpl extends JavaStringHolderEx implements XmlENTITY {
   public XmlEntityImpl() {
      super(XmlENTITY.type, false);
   }

   public XmlEntityImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
