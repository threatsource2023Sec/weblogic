package org.apache.openjpa.event;

public interface CallbackModes {
   int CALLBACK_FAIL_FAST = 2;
   int CALLBACK_IGNORE = 4;
   int CALLBACK_LOG = 8;
   int CALLBACK_RETHROW = 16;
   int CALLBACK_ROLLBACK = 32;
}
