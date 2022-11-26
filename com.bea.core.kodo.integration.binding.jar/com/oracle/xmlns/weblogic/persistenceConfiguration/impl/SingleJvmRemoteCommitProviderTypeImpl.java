package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SingleJvmRemoteCommitProviderType;

public class SingleJvmRemoteCommitProviderTypeImpl extends RemoteCommitProviderTypeImpl implements SingleJvmRemoteCommitProviderType {
   private static final long serialVersionUID = 1L;

   public SingleJvmRemoteCommitProviderTypeImpl(SchemaType sType) {
      super(sType);
   }
}
