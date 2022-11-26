package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNOTATION;

public class XmlNotationRestriction extends JavaNotationHolderEx implements XmlNOTATION {
   public XmlNotationRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
