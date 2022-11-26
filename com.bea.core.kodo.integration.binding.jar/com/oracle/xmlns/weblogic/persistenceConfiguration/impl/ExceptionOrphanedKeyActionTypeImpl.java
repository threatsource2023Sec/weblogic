package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExceptionOrphanedKeyActionType;

public class ExceptionOrphanedKeyActionTypeImpl extends OrphanedKeyActionTypeImpl implements ExceptionOrphanedKeyActionType {
   private static final long serialVersionUID = 1L;

   public ExceptionOrphanedKeyActionTypeImpl(SchemaType sType) {
      super(sType);
   }
}
