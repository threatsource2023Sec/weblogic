package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlNMTOKENS;

public class XmlNmTokensImpl extends XmlListImpl implements XmlNMTOKENS {
   public XmlNmTokensImpl() {
      super(XmlNMTOKENS.type, false);
   }

   public XmlNmTokensImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
