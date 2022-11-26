package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.xb.xmlconfig.NamespacePrefixList;
import com.bea.xml.SchemaType;

public class NamespacePrefixListImpl extends XmlListImpl implements NamespacePrefixList {
   public NamespacePrefixListImpl(SchemaType sType) {
      super(sType, false);
   }

   protected NamespacePrefixListImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
