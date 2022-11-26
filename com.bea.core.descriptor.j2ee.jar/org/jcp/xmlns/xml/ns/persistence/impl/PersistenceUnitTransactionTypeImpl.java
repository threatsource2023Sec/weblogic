package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitTransactionType;

public class PersistenceUnitTransactionTypeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitTransactionType {
   private static final long serialVersionUID = 1L;

   public PersistenceUnitTransactionTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected PersistenceUnitTransactionTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
