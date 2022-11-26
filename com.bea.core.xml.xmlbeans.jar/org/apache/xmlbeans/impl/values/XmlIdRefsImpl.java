package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlIDREFS;

public class XmlIdRefsImpl extends XmlListImpl implements XmlIDREFS {
   public XmlIdRefsImpl() {
      super(XmlIDREFS.type, false);
   }

   public XmlIdRefsImpl(SchemaType type, boolean complex) {
      super(type, complex);
   }
}
