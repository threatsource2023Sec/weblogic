package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlObject;

public class XmlAnyTypeImpl extends XmlComplexContentImpl implements XmlObject {
   public XmlAnyTypeImpl() {
      super(type);
   }

   public XmlAnyTypeImpl(SchemaType type) {
      super(type);
   }
}
