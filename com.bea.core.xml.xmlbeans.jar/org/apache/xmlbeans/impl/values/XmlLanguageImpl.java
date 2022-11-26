package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlLanguage;

public class XmlLanguageImpl extends JavaStringHolderEx implements XmlLanguage {
   public XmlLanguageImpl() {
      super(XmlLanguage.type, false);
   }

   public XmlLanguageImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
