package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.Collection;
import java.util.Collections;

public class NonUniqueDataSourceException extends IllegalStateException {
   private static final long serialVersionUID = 1L;
   private final String dataSourceName;
   private final Collection candidates;

   public NonUniqueDataSourceException() {
      this((String)null, (Collection)null);
   }

   public NonUniqueDataSourceException(String dataSourceName, Collection candidates) {
      super(dataSourceName);
      this.dataSourceName = dataSourceName;
      this.candidates = (Collection)(candidates == null ? Collections.emptySet() : candidates);
   }

   public final String getDataSourceName() {
      return this.dataSourceName;
   }

   public final Collection getCandidates() {
      return this.candidates;
   }
}
