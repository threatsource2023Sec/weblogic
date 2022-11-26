package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneOrphanedKeyActionType;

public class NoneOrphanedKeyActionTypeImpl extends OrphanedKeyActionTypeImpl implements NoneOrphanedKeyActionType {
   private static final long serialVersionUID = 1L;

   public NoneOrphanedKeyActionTypeImpl(SchemaType sType) {
      super(sType);
   }
}
