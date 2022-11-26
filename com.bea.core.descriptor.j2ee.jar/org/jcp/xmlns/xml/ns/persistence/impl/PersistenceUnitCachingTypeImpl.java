package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitCachingType;

public class PersistenceUnitCachingTypeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitCachingType {
   private static final long serialVersionUID = 1L;

   public PersistenceUnitCachingTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected PersistenceUnitCachingTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
