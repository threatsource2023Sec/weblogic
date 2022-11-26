package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNormalizedString;

public class XmlNormalizedStringImpl extends JavaStringHolderEx implements XmlNormalizedString {
   public XmlNormalizedStringImpl() {
      super(XmlNormalizedString.type, false);
   }

   public XmlNormalizedStringImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
