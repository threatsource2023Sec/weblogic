package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlString;

public class XmlStringEnumeration extends JavaStringEnumerationHolderEx implements XmlString {
   public XmlStringEnumeration(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
