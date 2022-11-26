package javax.resource.cci;

import java.io.Serializable;
import javax.resource.Referenceable;
import javax.resource.ResourceException;

public interface ConnectionFactory extends Serializable, Referenceable {
   Connection getConnection() throws ResourceException;

   Connection getConnection(ConnectionSpec var1) throws ResourceException;

   RecordFactory getRecordFactory() throws ResourceException;

   ResourceAdapterMetaData getMetaData() throws ResourceException;
}
