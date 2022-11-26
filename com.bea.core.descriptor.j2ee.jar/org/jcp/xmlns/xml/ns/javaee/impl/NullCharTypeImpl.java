package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.NullCharType;

public class NullCharTypeImpl extends JavaStringEnumerationHolderEx implements NullCharType {
   private static final long serialVersionUID = 1L;

   public NullCharTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected NullCharTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
