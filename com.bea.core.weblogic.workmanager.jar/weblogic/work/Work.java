package weblogic.work;

public interface Work extends Runnable {
   Runnable overloadAction(String var1);

   Runnable cancel(String var1);
}
