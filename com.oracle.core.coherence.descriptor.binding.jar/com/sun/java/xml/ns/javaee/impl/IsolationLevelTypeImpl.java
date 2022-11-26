package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.IsolationLevelType;

public class IsolationLevelTypeImpl extends JavaStringEnumerationHolderEx implements IsolationLevelType {
   private static final long serialVersionUID = 1L;

   public IsolationLevelTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected IsolationLevelTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
