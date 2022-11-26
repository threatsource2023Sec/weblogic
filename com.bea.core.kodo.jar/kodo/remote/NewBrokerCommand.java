package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.meta.ClassMetaData;

class NewBrokerCommand extends KodoCommand {
   private static final String NULL = "~";
   private String _user;
   private String _pass;
   private BrokerFlags _flags;
   private Class _oidClass;
   private FetchConfiguration _fetch;
   private Collection _listeners;

   NewBrokerCommand() {
      super((byte)12);
      this._user = null;
      this._pass = null;
      this._flags = null;
      this._oidClass = null;
      this._fetch = null;
      this._listeners = null;
   }

   public NewBrokerCommand(String user, String pass, BrokerFlags brokerFlags, Collection listeners) {
      this();
      this._user = user;
      this._pass = pass;
      this._flags = brokerFlags;
      this._listeners = listeners;
      if (this._listeners != null && this._listeners.isEmpty()) {
         this._listeners = null;
      }

   }

   public String getConnectionUserName() {
      return this._user;
   }

   public String getConnectionPassword() {
      return this._pass;
   }

   public BrokerFlags getBrokerFlags() {
      return this._flags;
   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fetch;
   }

   public Class getDataStoreIdType() {
      return this._oidClass;
   }

   protected void execute(KodoContextFactory context) {
      BrokerFactory factory = context.getBrokerFactory();
      OpenJPAConfiguration conf = factory.getConfiguration();
      if (this._user == null) {
         this._user = conf.getConnectionUserName();
      }

      if (this._pass == null) {
         this._pass = conf.getConnectionPassword();
      }

      Broker broker = factory.newBroker(this._user, this._pass, false, factory.getConfiguration().getConnectionRetainModeConstant(), true);
      broker.setIgnoreChanges(true);

      try {
         this._flags.sync(broker);
      } catch (RuntimeException var8) {
         broker.close();
         throw var8;
      }

      this._oidClass = broker.getStoreManager().getDataStoreIdType((ClassMetaData)null);
      this._fetch = broker.getFetchConfiguration();
      long id = context.newClientId();
      this.setClientId(id);
      context.setContext(id, broker);
      if (this._listeners != null) {
         Iterator itr = this._listeners.iterator();

         while(itr.hasNext()) {
            context.addTransferListener(id, (RemoteTransferListener)itr.next());
         }
      }

   }

   protected void read(ObjectInput in) throws Exception {
      this._user = in.readUTF();
      if ("~".equals(this._user)) {
         this._user = null;
      }

      this._pass = in.readUTF();
      if ("~".equals(this._pass)) {
         this._pass = null;
      }

      this._flags = (BrokerFlags)in.readObject();
      this._listeners = (Collection)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      if (this._user == null) {
         out.writeUTF("~");
      } else {
         out.writeUTF(this._user);
      }

      if (this._pass == null) {
         out.writeUTF("~");
      } else {
         out.writeUTF(this._pass);
      }

      out.writeObject(this._flags);
      out.writeObject(this._listeners);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this.setClientId(in.readLong());
      this._oidClass = (Class)in.readObject();
      this._fetch = (FetchConfiguration)in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeLong(this.getClientId());
      out.writeObject(this._oidClass);
      out.writeObject(this._fetch);
   }
}
