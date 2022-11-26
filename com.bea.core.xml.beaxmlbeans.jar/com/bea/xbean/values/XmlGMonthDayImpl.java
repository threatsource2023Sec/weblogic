package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlGMonthDay;

public class XmlGMonthDayImpl extends JavaGDateHolderEx implements XmlGMonthDay {
   public XmlGMonthDayImpl() {
      super(XmlGMonthDay.type, false);
   }

   public XmlGMonthDayImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
