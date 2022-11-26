package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.xb.xmlconfig.NamespacePrefixList;

public class NamespacePrefixListImpl extends XmlListImpl implements NamespacePrefixList {
   public NamespacePrefixListImpl(SchemaType sType) {
      super(sType, false);
   }

   protected NamespacePrefixListImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
