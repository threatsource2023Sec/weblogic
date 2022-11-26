package com.bea.core.repackaged.springframework.jca.cci.object;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.jca.cci.core.RecordCreator;
import com.bea.core.repackaged.springframework.jca.cci.core.RecordExtractor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.sql.SQLException;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.RecordFactory;

public abstract class MappingRecordOperation extends EisOperation {
   public MappingRecordOperation() {
   }

   public MappingRecordOperation(ConnectionFactory connectionFactory, InteractionSpec interactionSpec) {
      this.getCciTemplate().setConnectionFactory(connectionFactory);
      this.setInteractionSpec(interactionSpec);
   }

   public void setOutputRecordCreator(RecordCreator creator) {
      this.getCciTemplate().setOutputRecordCreator(creator);
   }

   @Nullable
   public Object execute(Object inputObject) throws DataAccessException {
      InteractionSpec interactionSpec = this.getInteractionSpec();
      Assert.state(interactionSpec != null, "No InteractionSpec set");
      return this.getCciTemplate().execute(interactionSpec, (RecordCreator)(new RecordCreatorImpl(inputObject)), (RecordExtractor)(new RecordExtractorImpl()));
   }

   protected abstract Record createInputRecord(RecordFactory var1, Object var2) throws ResourceException, DataAccessException;

   protected abstract Object extractOutputData(Record var1) throws ResourceException, SQLException, DataAccessException;

   protected class RecordExtractorImpl implements RecordExtractor {
      public Object extractData(Record record) throws ResourceException, SQLException, DataAccessException {
         return MappingRecordOperation.this.extractOutputData(record);
      }
   }

   protected class RecordCreatorImpl implements RecordCreator {
      private final Object inputObject;

      public RecordCreatorImpl(Object inObject) {
         this.inputObject = inObject;
      }

      public Record createRecord(RecordFactory recordFactory) throws ResourceException, DataAccessException {
         return MappingRecordOperation.this.createInputRecord(recordFactory, this.inputObject);
      }
   }
}
