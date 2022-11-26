package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnametargetlist;

public class QnametargetlistImpl extends XmlListImpl implements Qnametargetlist {
   public QnametargetlistImpl(SchemaType sType) {
      super(sType, false);
   }

   protected QnametargetlistImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
