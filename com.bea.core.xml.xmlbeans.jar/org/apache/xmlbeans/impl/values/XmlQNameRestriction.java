package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlQName;

public class XmlQNameRestriction extends JavaQNameHolderEx implements XmlQName {
   public XmlQNameRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
