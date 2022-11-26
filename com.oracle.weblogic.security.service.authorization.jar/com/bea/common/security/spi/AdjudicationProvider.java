package com.bea.common.security.spi;

import weblogic.security.spi.Adjudicator;

public interface AdjudicationProvider {
   Adjudicator getAdjudicator();
}
