package org.apache.openjpa.kernel.exps;

public interface Parameter extends Value, Constant {
   void setIndex(int var1);

   String getParameterName();
}
