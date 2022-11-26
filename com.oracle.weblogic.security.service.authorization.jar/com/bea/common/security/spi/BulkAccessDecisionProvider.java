package com.bea.common.security.spi;

import weblogic.security.spi.AccessDecision;
import weblogic.security.spi.BulkAccessDecision;

public interface BulkAccessDecisionProvider {
   AccessDecision getAccessDecision();

   BulkAccessDecision getBulkAccessDecision();

   String getAccessDecisionClassName();
}
