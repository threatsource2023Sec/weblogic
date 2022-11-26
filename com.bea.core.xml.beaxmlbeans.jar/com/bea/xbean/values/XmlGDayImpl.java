package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlGDay;

public class XmlGDayImpl extends JavaGDateHolderEx implements XmlGDay {
   public XmlGDayImpl() {
      super(XmlGDay.type, false);
   }

   public XmlGDayImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
