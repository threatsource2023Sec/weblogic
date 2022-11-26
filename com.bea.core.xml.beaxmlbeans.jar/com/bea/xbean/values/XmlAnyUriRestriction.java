package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;

public class XmlAnyUriRestriction extends JavaUriHolderEx implements XmlAnyURI {
   public XmlAnyUriRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
