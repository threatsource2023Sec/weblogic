package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlListImpl;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.ProtocolBindingListType;

public class ProtocolBindingListTypeImpl extends XmlListImpl implements ProtocolBindingListType {
   private static final long serialVersionUID = 1L;

   public ProtocolBindingListTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ProtocolBindingListTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
