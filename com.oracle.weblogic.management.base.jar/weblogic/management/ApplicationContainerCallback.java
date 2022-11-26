package weblogic.management;

public interface ApplicationContainerCallback {
   void beginTransition(String var1, int var2, int var3);

   void endTransition(String var1, int var2, int var3);

   void failedTransition(String var1, int var2, int var3);
}
