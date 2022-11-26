package com.bea.common.security.spi;

import weblogic.security.spi.ConsumptionException;
import weblogic.security.spi.RoleCollectionHandler;
import weblogic.security.spi.RoleCollectionInfo;

public interface RoleConsumerProvider {
   RoleCollectionHandler getRoleCollectionHandler(RoleCollectionInfo var1) throws ConsumptionException;
}
