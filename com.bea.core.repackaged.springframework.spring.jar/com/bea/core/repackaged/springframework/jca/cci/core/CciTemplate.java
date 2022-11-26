package com.bea.core.repackaged.springframework.jca.cci.core;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.dao.DataAccessResourceFailureException;
import com.bea.core.repackaged.springframework.jca.cci.CannotCreateRecordException;
import com.bea.core.repackaged.springframework.jca.cci.CciOperationNotSupportedException;
import com.bea.core.repackaged.springframework.jca.cci.InvalidResultSetAccessException;
import com.bea.core.repackaged.springframework.jca.cci.RecordTypeNotSupportedException;
import com.bea.core.repackaged.springframework.jca.cci.connection.ConnectionFactoryUtils;
import com.bea.core.repackaged.springframework.jca.cci.connection.NotSupportedRecordFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.sql.SQLException;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.Record;
import javax.resource.cci.RecordFactory;
import javax.resource.cci.ResultSet;

public class CciTemplate implements CciOperations {
   private final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private ConnectionFactory connectionFactory;
   @Nullable
   private ConnectionSpec connectionSpec;
   @Nullable
   private RecordCreator outputRecordCreator;

   public CciTemplate() {
   }

   public CciTemplate(ConnectionFactory connectionFactory) {
      this.setConnectionFactory(connectionFactory);
      this.afterPropertiesSet();
   }

   public CciTemplate(ConnectionFactory connectionFactory, @Nullable ConnectionSpec connectionSpec) {
      this.setConnectionFactory(connectionFactory);
      if (connectionSpec != null) {
         this.setConnectionSpec(connectionSpec);
      }

      this.afterPropertiesSet();
   }

   public void setConnectionFactory(@Nullable ConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
   }

   @Nullable
   public ConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   private ConnectionFactory obtainConnectionFactory() {
      ConnectionFactory connectionFactory = this.getConnectionFactory();
      Assert.state(connectionFactory != null, "No ConnectionFactory set");
      return connectionFactory;
   }

   public void setConnectionSpec(@Nullable ConnectionSpec connectionSpec) {
      this.connectionSpec = connectionSpec;
   }

   @Nullable
   public ConnectionSpec getConnectionSpec() {
      return this.connectionSpec;
   }

   public void setOutputRecordCreator(@Nullable RecordCreator creator) {
      this.outputRecordCreator = creator;
   }

   @Nullable
   public RecordCreator getOutputRecordCreator() {
      return this.outputRecordCreator;
   }

   public void afterPropertiesSet() {
      if (this.getConnectionFactory() == null) {
         throw new IllegalArgumentException("Property 'connectionFactory' is required");
      }
   }

   public CciTemplate getDerivedTemplate(ConnectionSpec connectionSpec) {
      CciTemplate derived = new CciTemplate(this.obtainConnectionFactory(), connectionSpec);
      RecordCreator recordCreator = this.getOutputRecordCreator();
      if (recordCreator != null) {
         derived.setOutputRecordCreator(recordCreator);
      }

      return derived;
   }

   @Nullable
   public Object execute(ConnectionCallback action) throws DataAccessException {
      Assert.notNull(action, (String)"Callback object must not be null");
      ConnectionFactory connectionFactory = this.obtainConnectionFactory();
      Connection con = ConnectionFactoryUtils.getConnection(connectionFactory, this.getConnectionSpec());

      Object var4;
      try {
         var4 = action.doInConnection(con, connectionFactory);
      } catch (NotSupportedException var10) {
         throw new CciOperationNotSupportedException("CCI operation not supported by connector", var10);
      } catch (ResourceException var11) {
         throw new DataAccessResourceFailureException("CCI operation failed", var11);
      } catch (SQLException var12) {
         throw new InvalidResultSetAccessException("Parsing of CCI ResultSet failed", var12);
      } finally {
         ConnectionFactoryUtils.releaseConnection(con, this.getConnectionFactory());
      }

      return var4;
   }

   @Nullable
   public Object execute(InteractionCallback action) throws DataAccessException {
      Assert.notNull(action, (String)"Callback object must not be null");
      return this.execute((connection, connectionFactory) -> {
         Interaction interaction = connection.createInteraction();

         Object var5;
         try {
            var5 = action.doInInteraction(interaction, connectionFactory);
         } finally {
            this.closeInteraction(interaction);
         }

         return var5;
      });
   }

   @Nullable
   public Record execute(InteractionSpec spec, Record inputRecord) throws DataAccessException {
      return (Record)this.doExecute(spec, inputRecord, (Record)null, new SimpleRecordExtractor());
   }

   public void execute(InteractionSpec spec, Record inputRecord, Record outputRecord) throws DataAccessException {
      this.doExecute(spec, inputRecord, outputRecord, (RecordExtractor)null);
   }

   public Record execute(InteractionSpec spec, RecordCreator inputCreator) throws DataAccessException {
      Record output = (Record)this.doExecute(spec, this.createRecord(inputCreator), (Record)null, new SimpleRecordExtractor());
      Assert.state(output != null, "Invalid output record");
      return output;
   }

   public Object execute(InteractionSpec spec, Record inputRecord, RecordExtractor outputExtractor) throws DataAccessException {
      return this.doExecute(spec, inputRecord, (Record)null, outputExtractor);
   }

   public Object execute(InteractionSpec spec, RecordCreator inputCreator, RecordExtractor outputExtractor) throws DataAccessException {
      return this.doExecute(spec, this.createRecord(inputCreator), (Record)null, outputExtractor);
   }

   @Nullable
   protected Object doExecute(InteractionSpec spec, Record inputRecord, @Nullable Record outputRecord, @Nullable RecordExtractor outputExtractor) throws DataAccessException {
      return this.execute((interaction, connectionFactory) -> {
         Record outputRecordToUse = outputRecord;

         Object var12;
         try {
            if (outputRecord == null && this.getOutputRecordCreator() == null) {
               outputRecordToUse = interaction.execute(spec, inputRecord);
            } else {
               if (outputRecord == null) {
                  RecordFactory recordFactory = this.getRecordFactory(connectionFactory);
                  outputRecordToUse = this.getOutputRecordCreator().createRecord(recordFactory);
               }

               interaction.execute(spec, inputRecord, outputRecordToUse);
            }

            var12 = outputExtractor != null ? outputExtractor.extractData(outputRecordToUse) : null;
         } finally {
            if (outputRecordToUse instanceof ResultSet) {
               this.closeResultSet((ResultSet)outputRecordToUse);
            }

         }

         return var12;
      });
   }

   public IndexedRecord createIndexedRecord(String name) throws DataAccessException {
      try {
         RecordFactory recordFactory = this.getRecordFactory(this.obtainConnectionFactory());
         return recordFactory.createIndexedRecord(name);
      } catch (NotSupportedException var3) {
         throw new RecordTypeNotSupportedException("Creation of indexed Record not supported by connector", var3);
      } catch (ResourceException var4) {
         throw new CannotCreateRecordException("Creation of indexed Record failed", var4);
      }
   }

   public MappedRecord createMappedRecord(String name) throws DataAccessException {
      try {
         RecordFactory recordFactory = this.getRecordFactory(this.obtainConnectionFactory());
         return recordFactory.createMappedRecord(name);
      } catch (NotSupportedException var3) {
         throw new RecordTypeNotSupportedException("Creation of mapped Record not supported by connector", var3);
      } catch (ResourceException var4) {
         throw new CannotCreateRecordException("Creation of mapped Record failed", var4);
      }
   }

   protected Record createRecord(RecordCreator recordCreator) throws DataAccessException {
      try {
         RecordFactory recordFactory = this.getRecordFactory(this.obtainConnectionFactory());
         return recordCreator.createRecord(recordFactory);
      } catch (NotSupportedException var3) {
         throw new RecordTypeNotSupportedException("Creation of the desired Record type not supported by connector", var3);
      } catch (ResourceException var4) {
         throw new CannotCreateRecordException("Creation of the desired Record failed", var4);
      }
   }

   protected RecordFactory getRecordFactory(ConnectionFactory connectionFactory) throws ResourceException {
      try {
         return connectionFactory.getRecordFactory();
      } catch (NotSupportedException var3) {
         return new NotSupportedRecordFactory();
      }
   }

   private void closeInteraction(@Nullable Interaction interaction) {
      if (interaction != null) {
         try {
            interaction.close();
         } catch (ResourceException var3) {
            this.logger.trace("Could not close CCI Interaction", var3);
         } catch (Throwable var4) {
            this.logger.trace("Unexpected exception on closing CCI Interaction", var4);
         }
      }

   }

   private void closeResultSet(@Nullable ResultSet resultSet) {
      if (resultSet != null) {
         try {
            resultSet.close();
         } catch (SQLException var3) {
            this.logger.trace("Could not close CCI ResultSet", var3);
         } catch (Throwable var4) {
            this.logger.trace("Unexpected exception on closing CCI ResultSet", var4);
         }
      }

   }

   private static class SimpleRecordExtractor implements RecordExtractor {
      private SimpleRecordExtractor() {
      }

      public Record extractData(Record record) {
         return record;
      }

      // $FF: synthetic method
      SimpleRecordExtractor(Object x0) {
         this();
      }
   }
}
