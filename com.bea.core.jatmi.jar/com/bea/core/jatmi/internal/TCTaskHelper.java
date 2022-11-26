package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCTask;
import com.bea.core.jatmi.intf.TCTaskManager;
import weblogic.wtc.jatmi.TPException;

public final class TCTaskHelper {
   private static TCTaskManager _task_svc = null;

   public static void initialize(TCTaskManager taskmgr) throws TPException {
      _task_svc = taskmgr;
      _task_svc.initialize();
      ntrace.doTrace("INFO: TC Task Manager instantiated!");
   }

   public void shutdown(int type) {
      if (_task_svc != null) {
         _task_svc.shutdown(type);
         _task_svc = null;
      }

   }

   public static synchronized void schedule(TCTask task) {
      if (_task_svc != null) {
         _task_svc.schedule(task);
      }

   }
}
