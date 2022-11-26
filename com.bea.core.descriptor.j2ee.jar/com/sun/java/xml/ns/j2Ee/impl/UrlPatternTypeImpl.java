package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.UrlPatternType;

public class UrlPatternTypeImpl extends JavaStringHolderEx implements UrlPatternType {
   private static final long serialVersionUID = 1L;

   public UrlPatternTypeImpl(SchemaType sType) {
      super(sType, true);
   }

   protected UrlPatternTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
