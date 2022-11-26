package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.XmlSignature;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;

public class XmlSignatureImpl extends JavaStringHolderEx implements XmlSignature {
   private static final long serialVersionUID = 1L;

   public XmlSignatureImpl(SchemaType sType) {
      super(sType, false);
   }

   protected XmlSignatureImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
