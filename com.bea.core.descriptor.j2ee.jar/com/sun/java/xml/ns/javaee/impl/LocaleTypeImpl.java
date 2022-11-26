package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.LocaleType;

public class LocaleTypeImpl extends JavaStringHolderEx implements LocaleType {
   private static final long serialVersionUID = 1L;

   public LocaleTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected LocaleTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
