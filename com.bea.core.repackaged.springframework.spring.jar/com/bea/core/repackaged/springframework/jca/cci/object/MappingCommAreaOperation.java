package com.bea.core.repackaged.springframework.jca.cci.object;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.dao.DataRetrievalFailureException;
import com.bea.core.repackaged.springframework.jca.cci.core.support.CommAreaRecord;
import java.io.IOException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.RecordFactory;

public abstract class MappingCommAreaOperation extends MappingRecordOperation {
   public MappingCommAreaOperation() {
   }

   public MappingCommAreaOperation(ConnectionFactory connectionFactory, InteractionSpec interactionSpec) {
      super(connectionFactory, interactionSpec);
   }

   protected final Record createInputRecord(RecordFactory recordFactory, Object inObject) {
      try {
         return new CommAreaRecord(this.objectToBytes(inObject));
      } catch (IOException var4) {
         throw new DataRetrievalFailureException("I/O exception during bytes conversion", var4);
      }
   }

   protected final Object extractOutputData(Record record) throws DataAccessException {
      CommAreaRecord commAreaRecord = (CommAreaRecord)record;

      try {
         return this.bytesToObject(commAreaRecord.toByteArray());
      } catch (IOException var4) {
         throw new DataRetrievalFailureException("I/O exception during bytes conversion", var4);
      }
   }

   protected abstract byte[] objectToBytes(Object var1) throws IOException, DataAccessException;

   protected abstract Object bytesToObject(byte[] var1) throws IOException, DataAccessException;
}
