package kodo.ee;

import javax.resource.NotSupportedException;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.RecordFactory;

public class RecordFactoryImpl implements RecordFactory {
   public IndexedRecord createIndexedRecord(String recordName) throws NotSupportedException {
      throw new NotSupportedException("createIndexedRecord");
   }

   public MappedRecord createMappedRecord(String recordName) throws NotSupportedException {
      throw new NotSupportedException("createMappedRecord");
   }
}
