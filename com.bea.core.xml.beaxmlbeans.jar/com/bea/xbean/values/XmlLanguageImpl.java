package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlLanguage;

public class XmlLanguageImpl extends JavaStringHolderEx implements XmlLanguage {
   public XmlLanguageImpl() {
      super(XmlLanguage.type, false);
   }

   public XmlLanguageImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
