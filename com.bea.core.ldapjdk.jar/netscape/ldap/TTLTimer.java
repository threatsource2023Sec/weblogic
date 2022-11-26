package netscape.ldap;

class TTLTimer implements Runnable {
   private long m_timeout;
   private LDAPCache m_cache;
   private Thread t = null;

   TTLTimer(LDAPCache var1) {
      this.m_cache = var1;
   }

   void start(long var1) {
      this.m_timeout = var1;
      if (Thread.currentThread() != this.t) {
         this.stop();
      }

      this.t = new Thread(this, "LDAPCache-TTLTimer");
      this.t.setDaemon(true);
      this.t.start();
   }

   void stop() {
      if (this.t != null) {
         this.t.interrupt();
      }

   }

   public void run() {
      synchronized(this) {
         try {
            this.wait(this.m_timeout);
         } catch (InterruptedException var4) {
            return;
         }
      }

      this.m_cache.scheduleTTLTimer();
   }
}
