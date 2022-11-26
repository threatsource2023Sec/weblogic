package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlByte;

public class XmlByteImpl extends JavaIntHolderEx implements XmlByte {
   public XmlByteImpl() {
      super(XmlByte.type, false);
   }

   public XmlByteImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
