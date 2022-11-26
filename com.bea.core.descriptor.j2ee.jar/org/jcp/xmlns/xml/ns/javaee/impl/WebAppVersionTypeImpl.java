package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.WebAppVersionType;

public class WebAppVersionTypeImpl extends JavaStringEnumerationHolderEx implements WebAppVersionType {
   private static final long serialVersionUID = 1L;

   public WebAppVersionTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected WebAppVersionTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
