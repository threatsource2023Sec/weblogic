package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlGMonthDay;

public class XmlGMonthDayImpl extends JavaGDateHolderEx implements XmlGMonthDay {
   public XmlGMonthDayImpl() {
      super(XmlGMonthDay.type, false);
   }

   public XmlGMonthDayImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
