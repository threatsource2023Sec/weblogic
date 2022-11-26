package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.persistence.VersionType;

public class VersionTypeImpl extends JavaStringHolderEx implements VersionType {
   private static final long serialVersionUID = 1L;

   public VersionTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected VersionTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
