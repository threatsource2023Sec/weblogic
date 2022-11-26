package com.bea.core.repackaged.springframework.jca.cci.core.support;

import com.bea.core.repackaged.springframework.dao.support.DaoSupport;
import com.bea.core.repackaged.springframework.jca.cci.CannotGetCciConnectionException;
import com.bea.core.repackaged.springframework.jca.cci.connection.ConnectionFactoryUtils;
import com.bea.core.repackaged.springframework.jca.cci.core.CciTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

public abstract class CciDaoSupport extends DaoSupport {
   @Nullable
   private CciTemplate cciTemplate;

   public final void setConnectionFactory(ConnectionFactory connectionFactory) {
      if (this.cciTemplate == null || connectionFactory != this.cciTemplate.getConnectionFactory()) {
         this.cciTemplate = this.createCciTemplate(connectionFactory);
      }

   }

   protected CciTemplate createCciTemplate(ConnectionFactory connectionFactory) {
      return new CciTemplate(connectionFactory);
   }

   @Nullable
   public final ConnectionFactory getConnectionFactory() {
      return this.cciTemplate != null ? this.cciTemplate.getConnectionFactory() : null;
   }

   public final void setCciTemplate(CciTemplate cciTemplate) {
      this.cciTemplate = cciTemplate;
   }

   @Nullable
   public final CciTemplate getCciTemplate() {
      return this.cciTemplate;
   }

   protected final void checkDaoConfig() {
      if (this.cciTemplate == null) {
         throw new IllegalArgumentException("'connectionFactory' or 'cciTemplate' is required");
      }
   }

   protected final CciTemplate getCciTemplate(ConnectionSpec connectionSpec) {
      CciTemplate cciTemplate = this.getCciTemplate();
      Assert.state(cciTemplate != null, "No CciTemplate set");
      return cciTemplate.getDerivedTemplate(connectionSpec);
   }

   protected final Connection getConnection() throws CannotGetCciConnectionException {
      ConnectionFactory connectionFactory = this.getConnectionFactory();
      Assert.state(connectionFactory != null, "No ConnectionFactory set");
      return ConnectionFactoryUtils.getConnection(connectionFactory);
   }

   protected final void releaseConnection(Connection con) {
      ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory());
   }
}
