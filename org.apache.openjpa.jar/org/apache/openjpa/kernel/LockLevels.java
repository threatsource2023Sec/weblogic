package org.apache.openjpa.kernel;

public interface LockLevels {
   int LOCK_NONE = 0;
   int LOCK_READ = 10;
   int LOCK_WRITE = 20;
}
