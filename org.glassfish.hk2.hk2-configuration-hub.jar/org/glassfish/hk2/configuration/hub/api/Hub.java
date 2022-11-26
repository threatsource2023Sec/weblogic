package org.glassfish.hk2.configuration.hub.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Hub {
   BeanDatabase getCurrentDatabase();

   WriteableBeanDatabase getWriteableDatabaseCopy();
}
