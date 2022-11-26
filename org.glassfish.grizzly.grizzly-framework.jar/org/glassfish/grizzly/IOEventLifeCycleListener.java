package org.glassfish.grizzly;

import java.io.IOException;

public interface IOEventLifeCycleListener {
   void onContextSuspend(Context var1) throws IOException;

   void onContextResume(Context var1) throws IOException;

   void onContextManualIOEventControl(Context var1) throws IOException;

   void onReregister(Context var1) throws IOException;

   void onComplete(Context var1, Object var2) throws IOException;

   void onLeave(Context var1) throws IOException;

   /** @deprecated */
   void onTerminate(Context var1) throws IOException;

   void onRerun(Context var1, Context var2) throws IOException;

   void onError(Context var1, Object var2) throws IOException;

   void onNotRun(Context var1) throws IOException;

   public static class Adapter implements IOEventLifeCycleListener {
      public void onContextSuspend(Context context) throws IOException {
      }

      public void onContextResume(Context context) throws IOException {
      }

      public void onComplete(Context context, Object data) throws IOException {
      }

      public void onTerminate(Context context) throws IOException {
      }

      public void onError(Context context, Object description) throws IOException {
      }

      public void onNotRun(Context context) throws IOException {
      }

      public void onContextManualIOEventControl(Context context) throws IOException {
      }

      public void onReregister(Context context) throws IOException {
      }

      public void onLeave(Context context) throws IOException {
      }

      public void onRerun(Context context, Context newContext) throws IOException {
      }
   }
}
