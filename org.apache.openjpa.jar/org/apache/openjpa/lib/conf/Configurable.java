package org.apache.openjpa.lib.conf;

public interface Configurable {
   void setConfiguration(Configuration var1);

   void startConfiguration();

   void endConfiguration();
}
