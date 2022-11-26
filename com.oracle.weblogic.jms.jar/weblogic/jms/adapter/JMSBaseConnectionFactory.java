package weblogic.jms.adapter;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Reference;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionFactory;
import weblogic.jms.bridge.AdapterConnectionFactory;
import weblogic.jms.bridge.AdapterMetaData;
import weblogic.jms.bridge.ConnectionSpec;
import weblogic.jms.bridge.SourceConnection;
import weblogic.jms.bridge.TargetConnection;

public class JMSBaseConnectionFactory implements AdapterConnectionFactory {
   static final long serialVersionUID = 2366460655498131081L;
   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private JMSManagedConnectionFactory mcf;
   private ConnectionManager cm;
   private Reference reference;
   private AdapterMetaData metaData;
   private transient PrintWriter logWriter;
   private boolean initialized;
   private Hashtable props = new Hashtable();

   public JMSBaseConnectionFactory(ManagedConnectionFactory mcf, ConnectionManager cm) throws ResourceException {
      this.mcf = (JMSManagedConnectionFactory)mcf;
      if (cm == null) {
         this.cm = new JMSConnectionManager();
      } else {
         this.cm = cm;
      }

      this.metaData = new AdapterMetaDataImpl(mcf);
      if (mcf.getLogWriter() != null) {
         this.logWriter = mcf.getLogWriter();
      }

   }

   public SourceConnection getSourceConnection() throws ResourceException {
      ConnectionRequestInfo info = new JMSConnectionRequestInfo((String)null, (String)null, 1);
      return (SourceConnection)this.cm.allocateConnection(this.mcf, info);
   }

   public SourceConnection getSourceConnection(ConnectionSpec connSpec) throws ResourceException {
      JMSConnectionSpec spec = (JMSConnectionSpec)connSpec;
      ConnectionRequestInfo info = new JMSConnectionRequestInfo(spec.getUser(), spec.getPassword(), 1, spec.getUrl(), spec.getInitialContextFactory(), spec.getSelector(), spec.getFactoryJndi(), spec.getDestJndi(), spec.getDestType(), spec.getName(), spec.getFullName(), spec.getDurability(), spec.getClasspath());
      return (SourceConnection)this.cm.allocateConnection(this.mcf, info);
   }

   public TargetConnection getTargetConnection() throws ResourceException {
      ConnectionRequestInfo info = new JMSConnectionRequestInfo((String)null, (String)null, 2);
      return (TargetConnection)this.cm.allocateConnection(this.mcf, info);
   }

   public TargetConnection getTargetConnection(ConnectionSpec connSpec) throws ResourceException {
      JMSConnectionSpec spec = (JMSConnectionSpec)connSpec;
      ConnectionRequestInfo info = new JMSConnectionRequestInfo(spec.getUser(), spec.getPassword(), 2, spec.getUrl(), spec.getInitialContextFactory(), (String)null, spec.getFactoryJndi(), spec.getDestJndi(), spec.getDestType(), spec.getClasspath(), spec.getPreserveMsgProperty());
      return (TargetConnection)this.cm.allocateConnection(this.mcf, info);
   }

   public ConnectionSpec createConnectionSpec(Properties props) throws ResourceException {
      return new JMSConnectionSpec(props);
   }

   public PrintWriter getLogWriter() throws ResourceException {
      return this.logWriter;
   }

   public AdapterMetaData getMetaData() throws ResourceException {
      return this.metaData;
   }

   public long getTimeout() throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public void setLogWriter(PrintWriter out) throws ResourceException {
      this.logWriter = out;
   }

   public void setTimeout(long milliseconds) throws ResourceException {
      throw new NotSupportedException("Not implemented");
   }

   public void setReference(Reference reference) {
      this.reference = reference;
   }

   public Reference getReference() {
      return this.reference;
   }

   public String getTransactionSupport() throws ResourceException {
      return this.mcf.getAdapterType();
   }
}
