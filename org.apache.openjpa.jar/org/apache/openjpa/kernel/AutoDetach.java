package org.apache.openjpa.kernel;

public interface AutoDetach {
   int DETACH_CLOSE = 2;
   int DETACH_COMMIT = 4;
   int DETACH_NONTXREAD = 8;
   int DETACH_ROLLBACK = 16;
}
