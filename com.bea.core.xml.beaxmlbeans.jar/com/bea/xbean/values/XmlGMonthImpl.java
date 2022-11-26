package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlGMonth;

public class XmlGMonthImpl extends JavaGDateHolderEx implements XmlGMonth {
   public XmlGMonthImpl() {
      super(XmlGMonth.type, false);
   }

   public XmlGMonthImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
