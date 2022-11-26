package org.apache.openjpa.persistence;

import java.util.EnumSet;
import java.util.Iterator;

public enum CallbackMode {
   FAIL_FAST(2),
   IGNORE(4),
   LOG(8),
   RETHROW(16),
   ROLLBACK(32);

   private final int callbackMode;

   private CallbackMode(int value) {
      this.callbackMode = value;
   }

   static EnumSet toEnumSet(int callback) {
      EnumSet modes = EnumSet.noneOf(CallbackMode.class);
      if ((callback & 2) != 0) {
         modes.add(FAIL_FAST);
      }

      if ((callback & 4) != 0) {
         modes.add(IGNORE);
      }

      if ((callback & 8) != 0) {
         modes.add(LOG);
      }

      if ((callback & 16) != 0) {
         modes.add(RETHROW);
      }

      if ((callback & 32) != 0) {
         modes.add(ROLLBACK);
      }

      return modes;
   }

   static int fromEnumSet(EnumSet modes) {
      int callback = 0;

      CallbackMode mode;
      for(Iterator i$ = modes.iterator(); i$.hasNext(); callback |= mode.callbackMode) {
         mode = (CallbackMode)i$.next();
      }

      return callback;
   }
}
