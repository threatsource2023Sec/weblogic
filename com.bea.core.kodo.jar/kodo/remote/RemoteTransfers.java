package kodo.remote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ObjectNotFoundException;

class RemoteTransfers {
   private static final Localizer _loc = Localizer.forPackage(RemoteTransfers.class);

   static void invokeReadCallbacks(ObjectInput in, Collection transferListeners, Collection sms, boolean serverToClient) throws Exception {
      if (transferListeners != null) {
         Iterator listenerItr = transferListeners.iterator();

         while(listenerItr.hasNext()) {
            RemoteTransferListener listener = (RemoteTransferListener)listenerItr.next();
            Iterator smItr = sms.iterator();

            while(smItr.hasNext()) {
               OpenJPAStateManager sm = (OpenJPAStateManager)smItr.next();
               Object loadedObj = sm.getManagedInstance();
               listener.transferRead(loadedObj, in, serverToClient);
            }
         }

      }
   }

   static void invokeWriteCallbacks(ObjectOutput out, Collection transferListeners, Collection sms, boolean serverToClient) throws Exception {
      if (transferListeners != null) {
         Iterator listenerItr = transferListeners.iterator();

         while(listenerItr.hasNext()) {
            RemoteTransferListener listener = (RemoteTransferListener)listenerItr.next();
            Iterator smItr = sms.iterator();

            while(smItr.hasNext()) {
               OpenJPAStateManager sm = (OpenJPAStateManager)smItr.next();
               Object loadedObj = sm.getManagedInstance();
               listener.transferWrite(loadedObj, out, serverToClient);
            }
         }

      }
   }

   static void readExternalBuffer(Collection sms, Collection transferListeners, byte[] transferBuffer, Log log, boolean serverToClient) {
      if (transferListeners != null && transferListeners.size() > 0 && transferBuffer != null) {
         try {
            ByteArrayInputStream baisExtTransfer = new ByteArrayInputStream(transferBuffer);
            ObjectInputStream in = new ObjectInputStream(baisExtTransfer);
            invokeReadCallbacks(in, transferListeners, (Collection)(sms != null ? sms : Collections.EMPTY_SET), serverToClient);
            in.close();
         } catch (Exception var7) {
            if (log != null && log.isWarnEnabled()) {
               log.warn(_loc.get("transfer-listner-exception"), var7);
            }
         }
      }

   }

   static byte[] writeExternalBuffer(Collection sms, Collection transferListeners, Log log, boolean serverToClient) {
      byte[] transferBuffer = null;
      if (transferListeners != null && transferListeners.size() > 0) {
         try {
            ByteArrayOutputStream baosExtTransfer = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baosExtTransfer);
            invokeWriteCallbacks(out, transferListeners, (Collection)(sms != null ? sms : Collections.EMPTY_LIST), serverToClient);
            out.flush();
            out.close();
            transferBuffer = baosExtTransfer.toByteArray();
         } catch (Exception var7) {
            if (log != null && log.isWarnEnabled()) {
               log.warn(_loc.get("transfer-listner-exception"), var7);
            }

            transferBuffer = null;
         }
      }

      return transferBuffer;
   }

   static List getStateManagersFromPCDatas(Broker broker, LinkedMap pcdatas, FetchConfiguration fetch, Collection oids, Log log) {
      List sms = null;
      Iterator iter = oids.iterator();

      while(iter.hasNext()) {
         Object oid = iter.next();

         Object pc;
         try {
            if (pcdatas != null && fetch != null) {
               pc = broker.find(oid, fetch, (BitSet)null, pcdatas, 0);
            } else {
               pc = broker.find(oid, true, (FindCallbacks)null);
            }

            if (pc == null && log != null && log.isWarnEnabled()) {
               log.warn(_loc.get("transfer-listner-missing-pc"));
            }
         } catch (ObjectNotFoundException var11) {
            pc = null;
            if (log != null && log.isWarnEnabled()) {
               log.warn(_loc.get("transfer-listner-no-state-manager"), var11);
            }
         }

         OpenJPAStateManager sm = null;
         if (pc != null) {
            sm = broker.getStateManager(pc);
         }

         if (sm != null) {
            if (sms == null) {
               sms = new ArrayList();
            }

            sms.add(sm);
         } else if (log != null && log.isWarnEnabled()) {
            log.warn(_loc.get("transfer-listner-missing-state-manager"));
         }
      }

      return sms;
   }
}
