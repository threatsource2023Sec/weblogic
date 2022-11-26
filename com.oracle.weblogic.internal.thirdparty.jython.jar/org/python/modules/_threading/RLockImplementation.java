package org.python.modules._threading;

import java.util.concurrent.locks.ReentrantLock;

final class RLockImplementation extends ReentrantLock {
   String getOwnerName() {
      Thread owner = this.getOwner();
      return owner != null ? owner.getName() : null;
   }
}
