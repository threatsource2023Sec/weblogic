package kodo.remote;

import com.solarmetric.remote.Command;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ObjectNotFoundException;

abstract class KodoCommand extends Command {
   private final byte _code;

   public KodoCommand(byte code) {
      this._code = code;
   }

   public byte getCode() {
      return this._code;
   }

   public void execute(Object context) throws Exception {
      this.execute((KodoContextFactory)context);
   }

   protected abstract void execute(KodoContextFactory var1) throws Exception;

   protected static ClassMetaData getMetaData(Broker broker, Class cls) {
      MetaDataRepository repos = broker.getConfiguration().getMetaDataRepositoryInstance();
      return repos.getMetaData(cls, broker.getClassLoader(), true);
   }

   protected static OpenJPAStateManager getStateManager(Broker broker, Object pc, Object oid, boolean mustExist) {
      if (pc == null) {
         pc = broker.find(oid, true, (FindCallbacks)null);
         if (pc == null) {
            if (!mustExist) {
               return null;
            }

            throw new ObjectNotFoundException(oid);
         }
      }

      OpenJPAStateManager sm = broker.getStateManager(pc);
      if (sm == null && mustExist) {
         throw new ObjectNotFoundException(oid);
      } else {
         return sm;
      }
   }
}
