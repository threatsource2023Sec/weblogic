package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.EncodingType;

public class EncodingTypeImpl extends JavaStringHolderEx implements EncodingType {
   private static final long serialVersionUID = 1L;

   public EncodingTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected EncodingTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
