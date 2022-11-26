package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.SequenceMetaData;

class GetNextSequenceCommand extends BrokerCommand {
   private String _name;
   private Class _class;
   private String _field;
   private Object _next;

   GetNextSequenceCommand() {
      super((byte)18);
      this._name = null;
      this._class = null;
      this._field = null;
      this._next = null;
   }

   public GetNextSequenceCommand(String name, Class cls, String field) {
      this();
      this._name = name;
      this._class = cls;
      this._field = field;
   }

   public Object getNext() {
      return this._next;
   }

   public void execute(Broker broker) {
      MetaDataRepository repos = broker.getConfiguration().getMetaDataRepositoryInstance();
      ClassMetaData meta = repos.getMetaData(this._class, broker.getClassLoader(), false);
      Seq seq;
      if (this._name != null) {
         SequenceMetaData smd = repos.getSequenceMetaData(this._name, broker.getClassLoader(), true);
         seq = smd.getInstance(broker.getClassLoader());
      } else if (this._field != null) {
         seq = broker.getValueSequence(meta.getField(this._field));
      } else {
         seq = broker.getIdentitySequence(meta);
      }

      this._next = seq.next(broker, meta);
   }

   protected void read(ObjectInput in) throws Exception {
      this._name = in.readUTF();
      if ("null".equals(this._name)) {
         this._name = null;
      }

      this._class = (Class)in.readObject();
      this._field = in.readUTF();
      if ("null".equals(this._field)) {
         this._field = null;
      }

   }

   protected void write(ObjectOutput out) throws Exception {
      if (this._name == null) {
         out.writeUTF("null");
      } else {
         out.writeUTF(this._name);
      }

      out.writeObject(this._class);
      if (this._field == null) {
         out.writeUTF("null");
      } else {
         out.writeUTF(this._field);
      }

   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._next = in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._next);
   }
}
