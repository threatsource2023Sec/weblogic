package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlDate;

public class XmlDateImpl extends JavaGDateHolderEx implements XmlDate {
   public XmlDateImpl() {
      super(XmlDate.type, false);
   }

   public XmlDateImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
