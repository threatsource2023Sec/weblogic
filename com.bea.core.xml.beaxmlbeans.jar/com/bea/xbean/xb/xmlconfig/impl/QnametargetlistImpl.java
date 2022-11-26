package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.xb.xmlconfig.Qnametargetlist;
import com.bea.xml.SchemaType;

public class QnametargetlistImpl extends XmlListImpl implements Qnametargetlist {
   public QnametargetlistImpl(SchemaType sType) {
      super(sType, false);
   }

   protected QnametargetlistImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
