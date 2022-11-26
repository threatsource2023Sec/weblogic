package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultOrphanedKeyActionType;

public class DefaultOrphanedKeyActionTypeImpl extends LogOrphanedKeyActionTypeImpl implements DefaultOrphanedKeyActionType {
   private static final long serialVersionUID = 1L;

   public DefaultOrphanedKeyActionTypeImpl(SchemaType sType) {
      super(sType);
   }
}
