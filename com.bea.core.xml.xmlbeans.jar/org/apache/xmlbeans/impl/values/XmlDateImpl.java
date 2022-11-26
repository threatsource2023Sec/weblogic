package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlDate;

public class XmlDateImpl extends JavaGDateHolderEx implements XmlDate {
   public XmlDateImpl() {
      super(XmlDate.type, false);
   }

   public XmlDateImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
