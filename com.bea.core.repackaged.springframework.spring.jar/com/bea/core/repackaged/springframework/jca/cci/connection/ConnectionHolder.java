package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.springframework.transaction.support.ResourceHolderSupport;
import javax.resource.cci.Connection;

public class ConnectionHolder extends ResourceHolderSupport {
   private final Connection connection;

   public ConnectionHolder(Connection connection) {
      this.connection = connection;
   }

   public Connection getConnection() {
      return this.connection;
   }
}
