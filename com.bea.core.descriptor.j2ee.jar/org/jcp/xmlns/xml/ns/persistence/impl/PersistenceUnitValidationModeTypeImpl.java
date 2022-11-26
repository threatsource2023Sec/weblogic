package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitValidationModeType;

public class PersistenceUnitValidationModeTypeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitValidationModeType {
   private static final long serialVersionUID = 1L;

   public PersistenceUnitValidationModeTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected PersistenceUnitValidationModeTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
