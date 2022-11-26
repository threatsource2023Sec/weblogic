package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;

class AfterFlushCommand extends KodoCommand {
   private Object[] _uploadedOids;
   private byte[] _externalTransferBuffer;

   AfterFlushCommand() {
      super((byte)21);
      this._uploadedOids = null;
      this._externalTransferBuffer = null;
   }

   public AfterFlushCommand(Collection uploadedSMs, Collection transferListeners, Log log) {
      this();
      this._uploadedOids = new Object[uploadedSMs.size()];
      int oidIndex = 0;

      for(Iterator itr = uploadedSMs.iterator(); itr.hasNext(); ++oidIndex) {
         this._uploadedOids[oidIndex] = ((OpenJPAStateManager)itr.next()).fetchObjectId();
      }

      this._externalTransferBuffer = RemoteTransfers.writeExternalBuffer(uploadedSMs, transferListeners, log, false);
   }

   public void execute(KodoContextFactory context) {
      ArrayList sms = new ArrayList(this._uploadedOids.length);
      Broker broker = (Broker)context.getContext(this.getClientId());

      for(int oidIndex = 0; oidIndex < this._uploadedOids.length; ++oidIndex) {
         OpenJPAStateManager sm = getStateManager(broker, (Object)null, this._uploadedOids[oidIndex], true);
         sms.add(sm);
      }

      Collection transferListeners = context.getTransferListeners(this.getClientId());
      RemoteTransfers.readExternalBuffer(sms, transferListeners, this._externalTransferBuffer, context.getLog(), false);
      this._externalTransferBuffer = null;
   }

   protected void read(ObjectInput in) throws Exception {
      this._uploadedOids = (Object[])((Object[])in.readObject());
      this._externalTransferBuffer = (byte[])((byte[])in.readObject());
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._uploadedOids);
      out.writeObject(this._externalTransferBuffer);
   }
}
