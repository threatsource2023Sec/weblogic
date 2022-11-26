package com.bea.common.security.spi;

import weblogic.security.spi.AdjudicatorV2;
import weblogic.security.spi.BulkAdjudicator;

public interface BulkAdjudicationProvider {
   AdjudicatorV2 getAdjudicator();

   BulkAdjudicator getBulkAdjudicator();
}
