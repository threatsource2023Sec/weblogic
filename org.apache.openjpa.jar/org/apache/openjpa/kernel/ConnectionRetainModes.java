package org.apache.openjpa.kernel;

public interface ConnectionRetainModes {
   int CONN_RETAIN_DEMAND = 0;
   int CONN_RETAIN_TRANS = 1;
   int CONN_RETAIN_ALWAYS = 2;
}
