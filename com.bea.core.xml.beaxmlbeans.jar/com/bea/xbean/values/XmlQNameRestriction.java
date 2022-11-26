package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlQName;

public class XmlQNameRestriction extends JavaQNameHolderEx implements XmlQName {
   public XmlQNameRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
