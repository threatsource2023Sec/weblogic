package com.bea.common.security.spi;

import weblogic.security.spi.AccessDecision;

public interface AccessDecisionProvider {
   AccessDecision getAccessDecision();

   String getAccessDecisionClassName();
}
