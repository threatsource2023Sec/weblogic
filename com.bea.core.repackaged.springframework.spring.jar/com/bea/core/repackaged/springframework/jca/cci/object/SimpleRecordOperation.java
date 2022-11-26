package com.bea.core.repackaged.springframework.jca.cci.object;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;

public class SimpleRecordOperation extends EisOperation {
   public SimpleRecordOperation() {
   }

   public SimpleRecordOperation(ConnectionFactory connectionFactory, InteractionSpec interactionSpec) {
      this.getCciTemplate().setConnectionFactory(connectionFactory);
      this.setInteractionSpec(interactionSpec);
   }

   @Nullable
   public Record execute(Record inputRecord) throws DataAccessException {
      InteractionSpec interactionSpec = this.getInteractionSpec();
      Assert.state(interactionSpec != null, "No InteractionSpec set");
      return this.getCciTemplate().execute(interactionSpec, inputRecord);
   }

   public void execute(Record inputRecord, Record outputRecord) throws DataAccessException {
      InteractionSpec interactionSpec = this.getInteractionSpec();
      Assert.state(interactionSpec != null, "No InteractionSpec set");
      this.getCciTemplate().execute(interactionSpec, inputRecord, outputRecord);
   }
}
