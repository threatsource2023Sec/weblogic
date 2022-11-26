package com.bea.common.security.spi;

import weblogic.security.spi.ConsumptionException;
import weblogic.security.spi.PolicyCollectionHandler;
import weblogic.security.spi.PolicyCollectionInfo;

public interface PolicyConsumerProvider {
   PolicyCollectionHandler getPolicyCollectionHandler(PolicyCollectionInfo var1) throws ConsumptionException;
}
