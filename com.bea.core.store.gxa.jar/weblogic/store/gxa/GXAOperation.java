package weblogic.store.gxa;

public interface GXAOperation {
   int START = 1;
   int PASS1 = 1;
   int CONTINUE = 2;
   int PASS2 = 2;
   int COMPLETE = 3;
   int PASS3 = 3;
   int IO_ISSUED = 21;
   int COMMIT_FAILED = 32;

   void onInitialize(GXATraceLogger var1, GXATransaction var2, GXAOperationWrapper var3);

   boolean onPrepare(int var1, boolean var2);

   void onCommit(int var1);

   void onRollback(int var1);

   GXid getGXid();

   String getDebugPrefix();
}
