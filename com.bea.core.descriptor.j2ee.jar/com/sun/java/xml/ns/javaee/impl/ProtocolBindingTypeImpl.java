package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
import com.sun.java.xml.ns.javaee.ProtocolBindingType;
import com.sun.java.xml.ns.javaee.ProtocolURIAliasType;

public class ProtocolBindingTypeImpl extends XmlUnionImpl implements ProtocolBindingType, XmlAnyURI, ProtocolURIAliasType {
   private static final long serialVersionUID = 1L;

   public ProtocolBindingTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ProtocolBindingTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
