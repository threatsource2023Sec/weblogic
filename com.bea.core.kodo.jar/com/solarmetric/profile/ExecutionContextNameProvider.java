package com.solarmetric.profile;

import org.apache.openjpa.kernel.Broker;

public interface ExecutionContextNameProvider {
   String getCreationPoint(Object var1, Broker var2);
}
