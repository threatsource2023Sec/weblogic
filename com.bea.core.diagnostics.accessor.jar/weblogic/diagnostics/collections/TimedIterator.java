package weblogic.diagnostics.collections;

import java.util.Iterator;

public final class TimedIterator implements Iterator {
   public static final long DEFAULT_TIMEOUT = 300000L;
   private long lastInvoked;
   private Iterator delegate;
   private long timeout;

   public TimedIterator(Iterator iter) {
      this(iter, 300000L);
   }

   public TimedIterator(Iterator iter, long timeout) {
      this.lastInvoked = 0L;
      this.delegate = null;
      this.timeout = 300000L;
      this.delegate = iter;
      this.timeout = timeout;
      this.updateLastInvoked();
   }

   public boolean hasNext() {
      this.updateLastInvoked();
      return this.delegate.hasNext();
   }

   public Object next() {
      this.updateLastInvoked();
      return this.delegate.next();
   }

   public void remove() {
      this.updateLastInvoked();
      this.delegate.remove();
   }

   public boolean hasTimedout(long currentTime) {
      return currentTime - this.lastInvoked > this.timeout;
   }

   private void updateLastInvoked() {
      this.lastInvoked = System.currentTimeMillis();
   }
}
