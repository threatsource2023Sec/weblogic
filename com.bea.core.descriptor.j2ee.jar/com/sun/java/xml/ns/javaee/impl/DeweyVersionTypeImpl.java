package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.DeweyVersionType;

public class DeweyVersionTypeImpl extends JavaStringHolderEx implements DeweyVersionType {
   private static final long serialVersionUID = 1L;

   public DeweyVersionTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DeweyVersionTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
