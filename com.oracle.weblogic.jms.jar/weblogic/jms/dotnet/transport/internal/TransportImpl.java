package weblogic.jms.dotnet.transport.internal;

import java.util.concurrent.atomic.AtomicLong;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReadableFactory;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.SendHandlerTwoWay;
import weblogic.jms.dotnet.transport.Service;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.dotnet.transport.TransportPluginSPI;
import weblogic.jms.dotnet.transport.TransportThreadPool;
import weblogic.utils.collections.NumericKeyHashMap;

public class TransportImpl implements Transport {
   private static final long UNUSED = -1L;
   private AtomicLong nextServiceId = new AtomicLong(-10L);
   private final ThreadPoolWrapper defaultThreadPool;
   private final TransportPluginSPI plugin;
   private boolean closed;
   private TransportError closedReason;
   private HeartbeatLock hLock = new HeartbeatLock();
   private HeartbeatService heartbeatService;
   private MarshalLock mLock = new MarshalLock();
   private MarshalReadableFactory[] readableFactories = new MarshalReadableFactory[0];
   private ServiceLock sLock = new ServiceLock();
   private NumericKeyHashMap services = new NumericKeyHashMap();
   private static final byte FLAG_EXT = 1;
   private static final byte FLAG_ISTWOWAY = 2;
   private static final byte FLAG_ISONEWAY = 4;
   private static final byte FLAG_REMOTEORDERED = 8;
   private static final byte FLAG_RESPONSEORDERED = 16;

   public TransportImpl(TransportPluginSPI tplugin, TransportThreadPool defaultTP) {
      this.plugin = tplugin;
      this.defaultThreadPool = new ThreadPoolWrapper(defaultTP);
      this.addMarshalReadableFactory(new MarshalFactoryImpl());
      this.registerService(10003L, new BootstrapService());
   }

   public String toString() {
      long nextId = this.nextServiceId.get();
      int numFactories;
      synchronized(this.mLock) {
         numFactories = this.readableFactories.length;
      }

      int numServices;
      synchronized(this.sLock) {
         numServices = this.services.size();
      }

      return "Transport<plg=" + this.plugin + ", svcs= " + numServices + ", facts= " + numFactories + ", nextId= " + nextId + ">";
   }

   ThreadPoolWrapper getDefaultThreadPool() {
      return this.defaultThreadPool;
   }

   void startHeartbeatService(BootstrapRequest bsr) {
      HeartbeatService h = null;
      synchronized(this.hLock) {
         if (this.heartbeatService != null) {
            return;
         }

         int interval = bsr.getHeartbeatInterval();
         int allowedMiss = bsr.getAllowedMissedBeats();
         if (interval <= 0 || allowedMiss <= 0) {
            return;
         }

         this.heartbeatService = new HeartbeatService(interval, allowedMiss, this);
         h = this.heartbeatService;
      }

      this.registerService(10001L, h);
      h.startHeartbeat();
   }

   public long allocateServiceID() {
      return this.nextServiceId.getAndDecrement();
   }

   public SendHandlerOneWayImpl createOneWay(long remoteServiceID) {
      return new SendHandlerOneWayImpl(this, remoteServiceID, -1L);
   }

   public SendHandlerOneWayImpl createOneWay(long remoteServiceID, long remoteOrderingID) {
      return new SendHandlerOneWayImpl(this, remoteServiceID, remoteOrderingID);
   }

   public SendHandlerTwoWayImpl createTwoWay(long remoteServiceID) {
      return this.createTwoWay(remoteServiceID, -1L, -1L);
   }

   public SendHandlerTwoWayImpl createTwoWay(long remoteServiceID, long remoteOrderingID) {
      return this.createTwoWay(remoteServiceID, remoteOrderingID, -1L);
   }

   public SendHandlerTwoWayImpl createTwoWay(long remoteServiceID, long remoteOrderingID, long responseOrderingID) {
      return new SendHandlerTwoWayImpl(this, remoteServiceID, remoteOrderingID, responseOrderingID);
   }

   public MarshalReadable bootstrap(String remoteServiceClassName) {
      SendHandlerTwoWay twoWay = this.createTwoWay(10003L);
      BootstrapRequest br = new BootstrapRequest(remoteServiceClassName);
      twoWay.send(br);
      return twoWay.getResponse(true);
   }

   public void addMarshalReadableFactory(MarshalReadableFactory mrf) {
      if (mrf != null) {
         synchronized(this.mLock) {
            MarshalReadableFactory[] orig = this.readableFactories;
            this.readableFactories = new MarshalReadableFactory[orig.length + 1];
            System.arraycopy(orig, 0, this.readableFactories, 0, orig.length);
            this.readableFactories[orig.length] = mrf;
         }
      }
   }

   public MarshalReadable createMarshalReadable(int typeCode) {
      synchronized(this.mLock) {
         MarshalReadableFactory[] var3 = this.readableFactories;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MarshalReadableFactory mrf = var3[var5];
            MarshalReadable mr = mrf.createMarshalReadable(typeCode);
            if (mr != null) {
               return mr;
            }
         }

         throw new RuntimeException("Unknown marshal type code " + typeCode);
      }
   }

   public long getScratchId() {
      return this.plugin.getScratchID();
   }

   public void registerService(long serviceID, Service service) {
      this.registerService(serviceID, service, this.defaultThreadPool);
   }

   void registerService(long serviceID, Service service, ThreadPoolWrapper t) {
      ServiceWrapper sw = new ServiceWrapper(serviceID, service, t);
      synchronized(this.sLock) {
         if (this.closed) {
            sw.shutdown(this.closedReason);
         } else if (this.services.get(serviceID) != null) {
            throw new RuntimeException("Duplicate service-id " + serviceID);
         } else {
            this.services.put(serviceID, sw);
         }
      }
   }

   public void shutdown(TransportError reason) {
      Object[] localServices;
      synchronized(this.sLock) {
         if (this.closed) {
            return;
         }

         this.closedReason = reason;
         this.closed = true;
         localServices = this.services.values().toArray();
         this.services.clear();
      }

      HeartbeatService h;
      synchronized(this.hLock) {
         h = this.heartbeatService;
      }

      if (h != null) {
         h.stopHeartbeat();
      }

      boolean var17 = false;

      Object[] var4;
      int var5;
      int var6;
      Object sw;
      label161: {
         try {
            var17 = true;
            this.plugin.terminateConnection();
            var17 = false;
            break label161;
         } catch (Exception var19) {
            var17 = false;
         } finally {
            if (var17) {
               Object[] var9 = localServices;
               int var10 = localServices.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  Object sw = var9[var11];
                  ((ServiceWrapper)sw).shutdown(reason);
               }

            }
         }

         var4 = localServices;
         var5 = localServices.length;

         for(var6 = 0; var6 < var5; ++var6) {
            sw = var4[var6];
            ((ServiceWrapper)sw).shutdown(reason);
         }

         return;
      }

      var4 = localServices;
      var5 = localServices.length;

      for(var6 = 0; var6 < var5; ++var6) {
         sw = var4[var6];
         ((ServiceWrapper)sw).shutdown(reason);
      }

   }

   public void unregisterService(long serviceID) {
      ServiceWrapper sw;
      synchronized(this.sLock) {
         sw = (ServiceWrapper)this.services.remove(serviceID);
      }

      if (sw != null) {
         sw.unregister();
      }

   }

   void unregisterService(long serviceID, TransportError reason) {
      ServiceWrapper sw;
      synchronized(this.sLock) {
         sw = (ServiceWrapper)this.services.remove(serviceID);
      }

      if (sw != null) {
         sw.shutdown(reason);
      }

   }

   void unregisterServiceSilent(long serviceID) {
      synchronized(this.sLock) {
         this.services.remove(serviceID);
      }
   }

   void sendInternalOneWay(SendHandlerOneWayImpl sh1way, MarshalWritable mw) {
      MarshalWriter writer = this.plugin.createMarshalWriter();
      byte flags = 4;
      if (sh1way.getRemoteOrderingID() != -1L) {
         flags = (byte)(flags | 8);
      }

      writer.writeByte(flags);
      writer.writeLong(sh1way.getRemoteServiceID());
      if (sh1way.getRemoteOrderingID() != -1L) {
         writer.writeLong(sh1way.getRemoteOrderingID());
      }

      writer.writeMarshalable(mw);
      this.plugin.send(writer);
   }

   void sendInternalTwoWay(SendHandlerTwoWayImpl sh2way, MarshalWritable mw) {
      MarshalWriter writer = this.plugin.createMarshalWriter();
      byte flags = 2;
      if (sh2way.getRemoteOrderingID() != -1L) {
         flags = (byte)(flags | 8);
      }

      if (sh2way.getResponseOrderingID() != -1L) {
         flags = (byte)(flags | 16);
      }

      writer.writeByte(flags);
      writer.writeLong(sh2way.getRemoteServiceID());
      if (sh2way.getRemoteOrderingID() != -1L) {
         writer.writeLong(sh2way.getRemoteOrderingID());
      }

      writer.writeLong(sh2way.getResponseID());
      if (sh2way.getResponseOrderingID() != -1L) {
         writer.writeLong(sh2way.getResponseOrderingID());
      }

      writer.writeMarshalable(mw);
      this.plugin.send(writer);
   }

   public void dispatch(MarshalReader mr) {
      int flags = mr.read();

      for(int lflags = flags; (lflags & 1) != 0; lflags = mr.read()) {
      }

      long serviceID = mr.readLong();
      ServiceWrapper sw;
      synchronized(this.sLock) {
         sw = (ServiceWrapper)this.services.get(serviceID);
      }

      HeartbeatService h;
      synchronized(this.hLock) {
         h = this.heartbeatService;
      }

      if (h != null) {
         h.resetMissCounter();
      }

      long orderingID;
      if ((flags & 8) != 0) {
         orderingID = mr.readLong();
      } else {
         orderingID = -1L;
      }

      if ((flags & 4) != 0) {
         ReceivedOneWayImpl rowi = new ReceivedOneWayImpl(this, serviceID, mr);
         if (sw == null) {
            mr.internalClose();
         } else {
            sw.invoke(rowi, orderingID);
         }
      } else if ((flags & 2) != 0) {
         long responseID = mr.readLong();
         long responseOrderingID;
         if ((flags & 16) != 0) {
            responseOrderingID = mr.readLong();
         } else {
            responseOrderingID = -1L;
         }

         SendHandlerOneWayImpl show = this.createOneWay(responseID, responseOrderingID);
         ReceivedTwoWayImpl rtwi = new ReceivedTwoWayImpl(this, serviceID, mr, show);
         if (sw == null) {
            Throwable t = new Throwable("Service closed");
            if (!rtwi.isAlreadySent()) {
               rtwi.send(new TransportError(t));
            }

            mr.internalClose();
         } else {
            sw.invoke(rtwi, orderingID);
         }
      } else {
         throw new AssertionError();
      }
   }
}
