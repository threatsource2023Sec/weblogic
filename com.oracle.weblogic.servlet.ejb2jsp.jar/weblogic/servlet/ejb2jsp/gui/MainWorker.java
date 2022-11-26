package weblogic.servlet.ejb2jsp.gui;

class MainWorker implements Runnable {
   Main m;

   static void p(String s) {
   }

   MainWorker(Main m) {
      this.m = m;
   }

   public void run() {
      p("running");

      while(true) {
         while(true) {
            try {
               p("getting task");
               Runnable r = this.m.getTask();
               p("running task");
               r.run();
               p("ran task");
            } catch (Exception var2) {
               p("bad task: " + var2);
               var2.printStackTrace();
            }
         }
      }
   }
}
