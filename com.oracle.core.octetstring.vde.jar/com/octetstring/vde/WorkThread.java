package com.octetstring.vde;

import com.octetstring.vde.util.Logger;

public class WorkThread extends Thread {
   private WorkQueue wq = null;
   private MessageHandler mh = null;

   public WorkThread(WorkQueue wq) {
      this.setPriority(6);
      this.wq = wq;
      this.mh = new MessageHandler();
   }

   public WorkThread(WorkQueue wq, ThreadGroup wg, String name) {
      super(wg, name);
      this.setPriority(6);
      this.wq = wq;
      this.mh = new MessageHandler();
   }

   public void run() {
      WorkQueueItem currentItem = null;
      boolean alive = false;

      while(true) {
         while(true) {
            try {
               currentItem = this.wq.nextItem();
               if (currentItem != null) {
                  this.mh.setConnection(currentItem.getConnection());
                  alive = this.mh.answerRequest(currentItem.getMessage());
                  if (!alive) {
                     DoSManager.getInstance().unregisterConnection(currentItem.getConnection());
                     currentItem.getConnection().setUnbound(true);
                     currentItem.getConnection().close();
                  }
               }
            } catch (Exception var4) {
               Logger.getInstance().log(0, this, var4.getClass().getName() + ": " + var4.getMessage());
               Logger.getInstance().printStackTraceConsole(var4);
               Logger.getInstance().printStackTraceLog(var4);
               DoSManager.getInstance().unregisterConnection(currentItem.getConnection());
            }
         }
      }
   }

   public static void executeWorkQueueItem(WorkQueueItem currentItem) {
      if (currentItem != null) {
         try {
            MessageHandler mh = new MessageHandler();
            mh.setConnection(currentItem.getConnection());
            boolean alive = mh.answerRequest(currentItem.getMessage());
            if (!alive) {
               currentItem.getConnection().setUnbound(true);
               currentItem.getConnection().close();
            }
         } catch (Exception var3) {
            Logger.getInstance().log(0, currentItem, var3.getClass().getName() + ": " + var3.getMessage());
            Logger.getInstance().printStackTraceConsole(var3);
            Logger.getInstance().printStackTraceLog(var3);
         }
      }

   }
}
