package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;

public class XmlAnyUriRestriction extends JavaUriHolderEx implements XmlAnyURI {
   public XmlAnyUriRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
