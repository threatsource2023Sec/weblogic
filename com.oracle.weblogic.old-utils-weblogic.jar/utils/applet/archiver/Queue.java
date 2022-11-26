package utils.applet.archiver;

class Queue {
   Object[] dat;
   int in;
   int out;

   public Queue(int nelems) {
      this.dat = new Object[nelems];
      this.in = this.out = 0;
   }

   public synchronized void put(Object o) {
      this.dat[this.in++] = o;
      this.in %= this.dat.length;
      this.notify();
   }

   public synchronized Object get() {
      while(this.out == this.in) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
            return null;
         }
      }

      Object o = this.dat[this.out];
      this.dat[this.out++] = null;
      this.out %= this.dat.length;
      return o;
   }
}
