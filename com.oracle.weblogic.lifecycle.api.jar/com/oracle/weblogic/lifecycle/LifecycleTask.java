package com.oracle.weblogic.lifecycle;

import java.util.Map;

public interface LifecycleTask {
   String getComponentType();

   Map getProperties();
}
