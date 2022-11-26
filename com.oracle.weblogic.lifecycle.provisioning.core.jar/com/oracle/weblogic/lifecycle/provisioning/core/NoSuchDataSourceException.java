package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.NoSuchElementException;

public class NoSuchDataSourceException extends NoSuchElementException {
   private static final long serialVersionUID = 1L;
   private final String dataSourceName;

   public NoSuchDataSourceException(String dataSourceName) {
      super(dataSourceName);
      this.dataSourceName = dataSourceName;
   }

   public final String getDataSourceName() {
      return this.dataSourceName;
   }
}
