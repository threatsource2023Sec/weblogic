package kodo.remote;

import com.solarmetric.remote.Command;
import com.solarmetric.remote.CommandIO;
import com.solarmetric.remote.TransportException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.util.Localizer;

public class KodoCommandIO extends CommandIO {
   public static final byte BEGIN = 0;
   public static final byte CHANGE_SETTINGS = 1;
   public static final byte CLOSE = 2;
   public static final byte COMMIT = 3;
   public static final byte COMPARE_VERSION = 4;
   public static final byte COPY_DATASTORE_ID = 5;
   public static final byte DATASTORE_ID_TYPE = 6;
   public static final byte EXISTS = 7;
   public static final byte FLUSH = 8;
   public static final byte LOAD = 9;
   public static final byte PERSIST = 10;
   public static final byte NEW_DATASTORE_ID = 11;
   public static final byte NEW_BROKER = 12;
   public static final byte PC_TYPE = 13;
   public static final byte ROLLBACK = 14;
   public static final byte EXTENT_RESULT = 15;
   public static final byte QUERY_RESULT = 16;
   public static final byte RESULT_SIZE = 17;
   public static final byte GET_NEXT_SEQUENCE = 18;
   public static final byte CANCEL_ALL = 19;
   public static final byte LOCK = 20;
   public static final byte AFTER_FLUSH = 21;
   private static final Localizer _loc = Localizer.forPackage(KodoCommandIO.class);
   private final RemotePCDataGenerator _gen;

   public KodoCommandIO(BrokerFactory server) {
      OpenJPAConfiguration conf = server.getConfiguration();
      if (conf.getDynamicDataStructs()) {
         this._gen = new RemotePCDataGenerator(conf);
      } else {
         this._gen = null;
      }

      this.setStreamDecorators(PersistenceServerState.getInstance(conf).getStreamDecorators());
      this.setContextFactory(new KodoContextFactory(server, this._gen));
      this.setLog(conf.getLog("kodo.Remote"));
   }

   public KodoCommandIO(ClientConfiguration conf) {
      this.setStreamDecorators(PersistenceServerState.getInstance(conf).getStreamDecorators());
      this.setLog(conf.getLog("kodo.Remote"));
      if (conf.getDynamicDataStructs()) {
         this._gen = new RemotePCDataGenerator(conf);
      } else {
         this._gen = null;
      }

   }

   public RemotePCDataGenerator getPCDataGenerator() {
      return this._gen;
   }

   protected ObjectInput getObjectInput(InputStream in) throws Exception {
      return (ObjectInput)(this._gen == null ? new ObjectInputStream(in) : new PCDataGeneratorObjectInputStream(this._gen, in));
   }

   protected Command readType(ObjectInput in) throws Exception {
      byte code = in.readByte();
      switch (code) {
         case 0:
            return new BeginCommand();
         case 1:
            return new ChangeSettingsCommand();
         case 2:
            return new CloseCommand();
         case 3:
            return new CommitCommand();
         case 4:
            return new CompareVersionCommand();
         case 5:
            return new CopyDataStoreIdCommand();
         case 6:
            return new DataStoreIdTypeCommand();
         case 7:
            return new ExistsCommand();
         case 8:
            return new FlushCommand();
         case 9:
            return new LoadCommand();
         case 10:
            return new PersistCommand();
         case 11:
            return new NewDataStoreIdCommand();
         case 12:
            return new NewBrokerCommand();
         case 13:
            return new PCTypeCommand();
         case 14:
            return new RollbackCommand();
         case 15:
            return new ExtentResultCommand();
         case 16:
            return new QueryResultCommand();
         case 17:
            return new ResultSizeCommand();
         case 18:
            return new GetNextSequenceCommand();
         case 19:
            return new CancelAllCommand();
         case 20:
            return new LockCommand();
         case 21:
            return new AfterFlushCommand();
         default:
            throw new TransportException(_loc.get("bad-cmd-code", String.valueOf(code)));
      }
   }

   protected void writeType(Command cmd, ObjectOutput out) throws Exception {
      out.writeByte(((KodoCommand)cmd).getCode());
   }
}
