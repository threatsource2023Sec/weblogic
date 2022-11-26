package weblogic.t3.srvr;

import weblogic.utils.collections.NumericKeyHashMap;

final class StateChangeValidator {
   private static final NumericKeyHashMap validStateTransitions = new NumericKeyHashMap();
   private static final int[] statesAlwaysAllowed = new int[]{8, 15, 14};

   static boolean validate(int presentState, int newState) {
      if (isNewStateAlwaysAllowed(newState)) {
         return true;
      } else {
         int[] stateTransitions = (int[])((int[])validStateTransitions.get((long)presentState));
         if (stateTransitions == null) {
            return true;
         } else {
            for(int count = 0; count < stateTransitions.length; ++count) {
               if (newState == stateTransitions[count]) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private static boolean isNewStateAlwaysAllowed(int newState) {
      for(int count = 0; count < statesAlwaysAllowed.length; ++count) {
         if (newState == statesAlwaysAllowed[count]) {
            return true;
         }
      }

      return false;
   }

   static {
      validStateTransitions.put(1L, new int[]{3, 17, 7, 18});
      validStateTransitions.put(3L, new int[]{7, 18, 6, 1});
      validStateTransitions.put(17L, new int[]{7, 18, 6});
      validStateTransitions.put(6L, new int[]{2, 5});
      validStateTransitions.put(2L, new int[]{4, 5, 10, 11});
      validStateTransitions.put(4L, new int[]{5, 17});
      validStateTransitions.put(5L, new int[]{17});
      validStateTransitions.put(7L, new int[]{18, 0});
      validStateTransitions.put(18L, new int[]{0});
      validStateTransitions.put(10L, new int[]{5, 4, 2});
   }
}
