package com.oracle.pitchfork.interfaces;

import java.util.Map;

public interface TargetWrapper {
   void resetTarget(Object var1);

   void removeTarget();

   Object getBeanTarget();

   Map getInterceptionInstances();

   void setInterceptionInstances(Map var1);
}
