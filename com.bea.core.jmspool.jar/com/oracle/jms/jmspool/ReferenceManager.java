package com.oracle.jms.jmspool;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ReferenceManager {
   ReferenceQueue getReferenceQueue();

   void registerReference(Reference var1);

   boolean unregisterReference(Reference var1);

   int getNumberOfRegisteredReferences();

   int poll();
}
