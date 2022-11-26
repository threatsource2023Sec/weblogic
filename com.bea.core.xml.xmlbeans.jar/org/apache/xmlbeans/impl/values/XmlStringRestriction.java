package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlString;

public class XmlStringRestriction extends JavaStringHolderEx implements XmlString {
   public XmlStringRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
