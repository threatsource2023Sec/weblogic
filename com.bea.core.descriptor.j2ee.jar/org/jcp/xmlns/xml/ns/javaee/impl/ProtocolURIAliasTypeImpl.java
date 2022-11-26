package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.ProtocolURIAliasType;

public class ProtocolURIAliasTypeImpl extends JavaStringHolderEx implements ProtocolURIAliasType {
   private static final long serialVersionUID = 1L;

   public ProtocolURIAliasTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ProtocolURIAliasTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
