package weblogic.jms.dotnet.transport.socketplugin;

class Stats implements Cloneable {
   long recvBytes;
   long recvPackets;
   long sendBytes;
   long sendPackets;
   long startTime = System.currentTimeMillis();
   long curTime;
   long nextTime;
   String text;

   Stats(String t) {
      this.nextTime = this.startTime + 2000L;
      this.text = "";
      this.text = t;
   }

   boolean timeToReport() {
      this.curTime = System.currentTimeMillis();
      if (this.curTime < this.nextTime) {
         return false;
      } else {
         this.nextTime += 2000L;
         return true;
      }
   }

   synchronized boolean _incRecv(long bytes) {
      ++this.recvPackets;
      this.recvBytes += bytes;
      return this.timeToReport();
   }

   synchronized boolean _incSend(long bytes) {
      ++this.sendPackets;
      this.sendBytes += bytes;
      return this.timeToReport();
   }

   public Object clone() throws CloneNotSupportedException {
      synchronized(this) {
         Stats s = (Stats)super.clone();
         s.curTime = System.currentTimeMillis();
         this.startTime = s.curTime;
         this.recvBytes = this.recvPackets = this.sendBytes = this.sendPackets = 0L;
         return s;
      }
   }

   void printMe() {
      try {
         System.out.println(this.clone());
      } catch (CloneNotSupportedException var2) {
         var2.printStackTrace();
      }

   }

   void incRecv(long bytes) {
      if (this._incRecv(bytes)) {
         this.printMe();
      }

   }

   void incSend(long bytes) {
      if (this._incSend(bytes)) {
         this.printMe();
      }

   }

   public String toString() {
      long interval = this.curTime - this.startTime;
      double seconds = (double)interval / 1000.0;
      return seconds == 0.0 ? "Div0" : "\nStats " + this.text + "\nsecs=" + seconds + " recv/sec=" + (double)this.recvPackets / seconds + "\nsecs=" + seconds + " recvB/sec=" + (double)this.recvBytes / seconds + "\nsecs=" + seconds + " send/sec=" + (double)this.sendPackets / seconds + "\nsecs=" + seconds + " sendB/sec=" + (double)this.sendBytes / seconds + "\n";
   }
}
