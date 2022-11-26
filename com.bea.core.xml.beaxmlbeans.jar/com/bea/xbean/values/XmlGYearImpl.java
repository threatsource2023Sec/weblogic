package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlGYear;

public class XmlGYearImpl extends JavaGDateHolderEx implements XmlGYear {
   public XmlGYearImpl() {
      super(XmlGYear.type, false);
   }

   public XmlGYearImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
