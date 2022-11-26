package weblogic.work.concurrent.context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;
import weblogic.workarea.utils.WorkContextInputAdapter;
import weblogic.workarea.utils.WorkContextOutputAdapter;

public class WorkAreaContextProvider implements ContextProvider {
   private static final long serialVersionUID = -1439994732773444004L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   protected static final WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();
   private static final WorkAreaContextProvider workAreaCP = new WorkAreaContextProvider();

   protected WorkAreaContextProvider() {
   }

   public static WorkAreaContextProvider getInstance() {
      return workAreaCP;
   }

   public ContextHandle save(Map executionProperties) {
      return new WorkAreaContextHandle(interceptor.copyThreadContexts(2));
   }

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof WorkAreaContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip WorkArea setup: " + contextHandle);
         }

         return null;
      } else {
         WorkAreaContextHandle handle = (WorkAreaContextHandle)contextHandle;
         WorkContextMapInterceptor savedWorkArea = null;
         savedWorkArea = interceptor.suspendThreadContexts();
         interceptor.restoreThreadContexts(handle.getWorkArea());
         return new WorkAreaContextHandle(savedWorkArea);
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (!(contextHandle instanceof WorkAreaContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip WorkArea reset: " + contextHandle);
         }

      } else {
         WorkAreaContextHandle handle = (WorkAreaContextHandle)contextHandle;
         interceptor.resumeThreadContexts(handle.getWorkArea());
      }
   }

   public String getContextType() {
      return "workarea";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public static class WorkAreaContextHandle implements ContextHandle {
      private static final long serialVersionUID = 5575492547679332901L;
      private static final int CURRENT_VERSION = 1;
      private transient WorkContextMapInterceptor workArea;
      private final int version;
      private static final WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();

      public WorkAreaContextHandle(WorkContextMapInterceptor workArea) {
         this.workArea = workArea;
         this.version = 1;
      }

      public WorkContextMapInterceptor getWorkArea() {
         return this.workArea;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.defaultWriteObject();
         out.writeBoolean(this.workArea != null);
         if (this.workArea != null) {
            WorkContextOutputAdapter wout = new WorkContextOutputAdapter(out);
            this.workArea.sendRequest(wout, 2);
         }

      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         if (this.version != 1 && WorkAreaContextProvider.debugLogger.isDebugEnabled()) {
            WorkAreaContextProvider.debugLogger.debug("WorkAreaContextHandle version mismatch: current=1, object=" + this.version);
         }

         boolean hasWorkArea = in.readBoolean();
         if (hasWorkArea) {
            WorkContextMapInterceptor tmp = interceptor.suspendThreadContexts();
            WorkContextInputAdapter win = new WorkContextInputAdapter(in);
            interceptor.receiveRequest(win);
            this.workArea = interceptor.suspendThreadContexts();
            interceptor.resumeThreadContexts(tmp);
         }

      }
   }
}
