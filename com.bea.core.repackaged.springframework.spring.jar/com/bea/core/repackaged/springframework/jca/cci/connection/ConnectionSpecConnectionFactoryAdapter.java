package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

public class ConnectionSpecConnectionFactoryAdapter extends DelegatingConnectionFactory {
   @Nullable
   private ConnectionSpec connectionSpec;
   private final ThreadLocal threadBoundSpec = new NamedThreadLocal("Current CCI ConnectionSpec");

   public void setConnectionSpec(ConnectionSpec connectionSpec) {
      this.connectionSpec = connectionSpec;
   }

   public void setConnectionSpecForCurrentThread(ConnectionSpec spec) {
      this.threadBoundSpec.set(spec);
   }

   public void removeConnectionSpecFromCurrentThread() {
      this.threadBoundSpec.remove();
   }

   public final Connection getConnection() throws ResourceException {
      ConnectionSpec threadSpec = (ConnectionSpec)this.threadBoundSpec.get();
      return threadSpec != null ? this.doGetConnection(threadSpec) : this.doGetConnection(this.connectionSpec);
   }

   protected Connection doGetConnection(@Nullable ConnectionSpec spec) throws ResourceException {
      ConnectionFactory connectionFactory = this.getTargetConnectionFactory();
      Assert.state(connectionFactory != null, "No 'targetConnectionFactory' set");
      return spec != null ? connectionFactory.getConnection(spec) : connectionFactory.getConnection();
   }
}
