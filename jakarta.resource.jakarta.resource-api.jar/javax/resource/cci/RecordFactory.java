package javax.resource.cci;

import javax.resource.ResourceException;

public interface RecordFactory {
   MappedRecord createMappedRecord(String var1) throws ResourceException;

   IndexedRecord createIndexedRecord(String var1) throws ResourceException;
}
