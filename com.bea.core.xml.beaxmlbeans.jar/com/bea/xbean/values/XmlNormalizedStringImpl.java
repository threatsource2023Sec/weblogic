package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNormalizedString;

public class XmlNormalizedStringImpl extends JavaStringHolderEx implements XmlNormalizedString {
   public XmlNormalizedStringImpl() {
      super(XmlNormalizedString.type, false);
   }

   public XmlNormalizedStringImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
