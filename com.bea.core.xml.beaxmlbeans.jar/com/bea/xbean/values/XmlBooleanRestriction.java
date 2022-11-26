package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBoolean;

public class XmlBooleanRestriction extends JavaBooleanHolderEx implements XmlBoolean {
   public XmlBooleanRestriction(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
