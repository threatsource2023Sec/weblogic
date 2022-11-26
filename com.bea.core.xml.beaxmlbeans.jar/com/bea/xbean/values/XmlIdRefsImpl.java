package com.bea.xbean.values;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlIDREFS;

public class XmlIdRefsImpl extends XmlListImpl implements XmlIDREFS {
   public XmlIdRefsImpl() {
      super(XmlIDREFS.type, false);
   }

   public XmlIdRefsImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
