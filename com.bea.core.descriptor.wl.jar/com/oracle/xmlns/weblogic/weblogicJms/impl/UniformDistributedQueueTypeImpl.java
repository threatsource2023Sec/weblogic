package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.UniformDistributedQueueType;

public class UniformDistributedQueueTypeImpl extends QueueTypeImpl implements UniformDistributedQueueType {
   private static final long serialVersionUID = 1L;

   public UniformDistributedQueueTypeImpl(SchemaType sType) {
      super(sType);
   }
}
