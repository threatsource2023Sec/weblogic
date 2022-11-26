package weblogic.work;

interface StuckThreadAction {
   boolean threadStuck(int var1, long var2, long var4);

   void threadUnStuck(int var1);

   int getStuckThreadCount();

   void execute();

   void withdraw();

   String getName();

   long getMaxStuckTime();
}
