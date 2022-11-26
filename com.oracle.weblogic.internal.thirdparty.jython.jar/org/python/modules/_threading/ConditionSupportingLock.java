package org.python.modules._threading;

interface ConditionSupportingLock {
   java.util.concurrent.locks.Lock getLock();

   boolean acquire();

   boolean acquire(boolean var1);

   void release();

   boolean _is_owned();

   int getWaitQueueLength(java.util.concurrent.locks.Condition var1);
}
