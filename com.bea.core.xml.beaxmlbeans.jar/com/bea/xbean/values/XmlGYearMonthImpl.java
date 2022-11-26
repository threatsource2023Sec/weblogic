package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlGYearMonth;

public class XmlGYearMonthImpl extends JavaGDateHolderEx implements XmlGYearMonth {
   public XmlGYearMonthImpl() {
      super(XmlGYearMonth.type, false);
   }

   public XmlGYearMonthImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
