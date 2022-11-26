package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.JavaDecimalHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.DeweyVersionType;

public class DeweyVersionTypeImpl extends JavaDecimalHolderEx implements DeweyVersionType {
   private static final long serialVersionUID = 1L;

   public DeweyVersionTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DeweyVersionTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
