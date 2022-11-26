package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlENTITIES;

public class XmlEntitiesImpl extends XmlListImpl implements XmlENTITIES {
   public XmlEntitiesImpl() {
      super(XmlENTITIES.type, false);
   }

   public XmlEntitiesImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
