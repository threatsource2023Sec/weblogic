package org.python.jline;

public interface Terminal2 extends Terminal {
   boolean getBooleanCapability(String var1);

   Integer getNumericCapability(String var1);

   String getStringCapability(String var1);
}
