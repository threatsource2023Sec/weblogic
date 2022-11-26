package com.bea.core.repackaged.springframework.jca.cci.connection;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.RecordFactory;

public class NotSupportedRecordFactory implements RecordFactory {
   public MappedRecord createMappedRecord(String name) throws ResourceException {
      throw new NotSupportedException("The RecordFactory facility is not supported by the connector");
   }

   public IndexedRecord createIndexedRecord(String name) throws ResourceException {
      throw new NotSupportedException("The RecordFactory facility is not supported by the connector");
   }
}
