package weblogic.jms.bridge;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Properties;
import javax.resource.ResourceException;

public interface AdapterConnectionFactory extends Serializable, javax.resource.Referenceable {
   SourceConnection getSourceConnection() throws ResourceException;

   SourceConnection getSourceConnection(ConnectionSpec var1) throws ResourceException;

   TargetConnection getTargetConnection() throws ResourceException;

   TargetConnection getTargetConnection(ConnectionSpec var1) throws ResourceException;

   ConnectionSpec createConnectionSpec(Properties var1) throws ResourceException;

   PrintWriter getLogWriter() throws ResourceException;

   AdapterMetaData getMetaData() throws ResourceException;

   long getTimeout() throws ResourceException;

   void setLogWriter(PrintWriter var1) throws ResourceException;

   void setTimeout(long var1) throws ResourceException;

   String getTransactionSupport() throws ResourceException;
}
