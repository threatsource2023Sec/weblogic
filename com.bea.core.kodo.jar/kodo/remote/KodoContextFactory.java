package kodo.remote;

import com.solarmetric.remote.Command;
import com.solarmetric.remote.ContextFactory;
import com.solarmetric.remote.TransportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

class KodoContextFactory implements ContextFactory {
   private static final Localizer _loc = Localizer.forPackage(KodoContextFactory.class);
   private final BrokerFactory _factory;
   private final RemotePCDataGenerator _gen;
   private final Log _log;
   private final Map _context = Collections.synchronizedMap(new HashMap());
   private final Map _listeners = Collections.synchronizedMap(new HashMap());
   private long _id = System.currentTimeMillis();

   public KodoContextFactory(BrokerFactory factory, RemotePCDataGenerator gen) {
      this._factory = factory;
      this._log = factory.getConfiguration().getLog("kodo.Remote");
      this._gen = gen;
   }

   public BrokerFactory getBrokerFactory() {
      return this._factory;
   }

   public RemotePCDataGenerator getPCDataGenerator() {
      return this._gen;
   }

   public Log getLog() {
      return this._log;
   }

   public synchronized long newClientId() {
      return (long)(this._id++);
   }

   public Object getContext(long id) {
      Long cid = Numbers.valueOf(id);
      Object context = this._context.get(cid);
      if (context == null) {
         throw new TransportException(_loc.get("bad-client-id", cid.toString()));
      } else {
         return context;
      }
   }

   public void setContext(long id, Object context) {
      Long cid = Numbers.valueOf(id);
      synchronized(this._context) {
         Object existing = this._context.get(cid);
         if (existing != null) {
            throw new TransportException(_loc.get("client-id-in-use", cid.toString(), existing, context));
         }

         this._context.put(cid, context);
      }

      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("new-server-rsrc", cid.toString(), context));
      }

   }

   public Object removeContext(long id) {
      Long cid = Numbers.valueOf(id);
      Object context = this._context.remove(cid);
      if (context == null) {
         throw new TransportException(_loc.get("bad-client-id", cid.toString()));
      } else {
         this._listeners.remove(cid);
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("remove-server-rsrc", cid.toString(), context));
         }

         return context;
      }
   }

   public Object getContext(Command cmd) {
      return this;
   }

   public Collection getTransferListeners(long id) {
      Long cid = Numbers.valueOf(id);
      Collection listeners = (Collection)this._listeners.get(cid);
      return (Collection)(listeners == null ? Collections.EMPTY_LIST : listeners);
   }

   public void addTransferListener(long id, RemoteTransferListener listener) {
      Long cid = Numbers.valueOf(id);
      synchronized(this._context) {
         if (!this._context.containsKey(cid)) {
            throw new TransportException(_loc.get("bad-client-id", cid.toString()));
         }

         synchronized(this._listeners) {
            Collection listeners = (Collection)this._listeners.get(cid);
            if (listeners == null) {
               listeners = Collections.synchronizedList(new ArrayList(3));
               this._listeners.put(cid, listeners);
            }

            ((Collection)listeners).add(listener);
         }
      }

      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("added-transfer-listener", listener, cid));
      }

   }

   public void removeTransferListener(long id, RemoteTransferListener listener) {
      Long cid = Numbers.valueOf(id);
      Collection listeners = (Collection)this._listeners.get(cid);
      if (listeners != null && listeners.remove(listener) && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("removed-transfer-listener", cid));
      }

   }
}
