package weblogic.connector.common;

class ManagementCountThreadLocal {
   private static ThreadLocal managementCount = new ThreadLocal() {
      protected synchronized Object initialValue() {
         return new Integer(0);
      }
   };

   static int get() {
      return (Integer)((Integer)managementCount.get());
   }

   static void increment() {
      Integer newCount = new Integer(get() + 1);
      managementCount.set(newCount);
   }

   static void decrement() {
      Integer newCount = new Integer(get() - 1);
      if (newCount >= 0) {
         managementCount.set(newCount);
      }

   }
}
