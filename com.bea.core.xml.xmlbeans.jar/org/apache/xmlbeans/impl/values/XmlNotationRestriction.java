package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNOTATION;

public class XmlNotationRestriction extends JavaNotationHolderEx implements XmlNOTATION {
   public XmlNotationRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
