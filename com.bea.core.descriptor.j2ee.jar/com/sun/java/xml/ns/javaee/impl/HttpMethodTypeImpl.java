package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.HttpMethodType;

public class HttpMethodTypeImpl extends JavaStringHolderEx implements HttpMethodType {
   private static final long serialVersionUID = 1L;

   public HttpMethodTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected HttpMethodTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
