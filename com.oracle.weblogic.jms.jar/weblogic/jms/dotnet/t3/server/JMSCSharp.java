package weblogic.jms.dotnet.t3.server;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dotnet.t3.server.internal.T3ConnectionHandleID;
import weblogic.jms.dotnet.t3.server.internal.T3ConnectionImpl;
import weblogic.jms.dotnet.t3.server.internal.T3RJVM;
import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandleFactory;
import weblogic.jms.dotnet.t3.server.spi.impl.T3ConnectionHandleFactoryImpl;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.RemoteRequest;
import weblogic.rjvm.ReplyStream;
import weblogic.rmi.spi.InboundRequest;
import weblogic.utils.collections.NumericKeyHashMap;
import weblogic.utils.io.ChunkedDataInputStream;

public final class JMSCSharp implements RemoteInvokable {
   public static final byte REMOTE_INVOKE_HELLO_REQUEST = 1;
   public static final byte REMOTE_INVOKE_HELLO_RESPONSE = 2;
   public static final byte REMOTE_INVOKE_DISPATCH_REQUEST = 3;
   public static final byte REMOTE_INVOKE_FAILURE = 4;
   private static final byte REMOTE_INVOKE_FAILURE_UNKNOWN_OPCODE = 1;
   private static final byte REMOTE_INVOKE_FAILURE_DUPLICATE_RJVM = 2;
   private static final byte REMOTE_INVOKE_FAILURE_UNKNOWN_HANDLEID = 3;
   private static final int DEFAULT_SERVICE_ID = 0;
   private static JMSCSharp me = new JMSCSharp();
   private static T3ConnectionHandleFactory[] hFactories = new T3ConnectionHandleFactory[128];
   private final NumericKeyHashMap handles = new NumericKeyHashMap();
   private long handleID = 0L;

   private JMSCSharp() {
   }

   public static JMSCSharp getInstance() {
      return me;
   }

   public static synchronized void setT3ConnectionHandleFactory(int serviceId, T3ConnectionHandleFactory factory) {
      if (serviceId <= 128 && serviceId >= 0) {
         if (hFactories[serviceId] != null) {
            throw new IllegalArgumentException("Service id is used");
         } else {
            if (JMSDebug.JMSDotNetT3Server.isDebugEnabled()) {
               JMSDebug.JMSDotNetT3Server.debug("T3 service " + serviceId + " is registered with " + factory);
            }

            hFactories[serviceId] = factory;
         }
      } else {
         throw new IllegalArgumentException("Service id must be in the range of 0 to 128");
      }
   }

   public static synchronized void unsetT3ConnectionHandleFactory(int serviceId) {
      hFactories[serviceId] = null;
   }

   public void invoke(RemoteRequest req) throws RemoteException {
      byte cmd;
      try {
         cmd = req.readByte();
      } catch (IOException var4) {
         throw new RemoteException("RemoteRequest: missing JMS .net client opcode");
      }

      switch (cmd) {
         case 1:
            this.processHelloRequest(req);
            break;
         case 3:
            this.dispatchRequest(req);
            break;
         default:
            this.reportFailure((byte)1, cmd);
      }

   }

   public void remove_connection(T3ConnectionHandleID id) {
      synchronized(this.handles) {
         this.handles.remove(id.getValue());
      }
   }

   private void reportFailure(byte failCode, byte cmd) {
      switch (failCode) {
         case 1:
            System.out.println("REMOTE_INVOKE_FAILURE_UNKNOWN_OPCODE  " + cmd);
            break;
         default:
            System.out.println("FailCode  " + failCode + " cmd " + cmd);
      }

   }

   private void sendAndReportFailure(RemoteRequest req, byte failCode) {
      try {
         ReplyStream reply = req.getResponseStream();
         reply.writeByte(4);
         reply.writeByte(failCode);
         reply.send();
      } catch (IOException var7) {
      } finally {
         this.reportFailure(failCode, (byte)0);
      }

   }

   private void processHelloRequest(RemoteRequest req) throws RemoteException {
      int major;
      int minor;
      int servicePack;
      int rollingPatch;
      boolean temporaryPatch;
      byte serviceId;
      try {
         major = req.readInt();
         minor = req.readInt();
         servicePack = req.readInt();
         rollingPatch = req.readInt();
         temporaryPatch = req.readBoolean();
         serviceId = req.readByte();
      } catch (IOException var15) {
         throw new RemoteException("RemoteRequest: T3 connection [hello] syntax error");
      }

      if (serviceId < 0) {
         throw new RemoteException("T3 service id should be in range of 0 to 128");
      } else if (hFactories[serviceId] == null) {
         throw new RemoteException("T3 service " + serviceId + " is empty");
      } else {
         T3RJVM myRJVM = this.getT3RJVM(req);
         T3ConnectionHandleID id = this.getNextHandleID();
         T3Connection client = new T3ConnectionImpl(myRJVM, id, major, minor, servicePack, rollingPatch, temporaryPatch);
         myRJVM.getRJVM().addPeerGoneListener((T3ConnectionImpl)client);
         T3ConnectionHandle h = hFactories[serviceId].createHandle(client);
         ((T3ConnectionImpl)client).setT3ConnectionGoneListener(h);
         synchronized(this.handles) {
            if (!((T3ConnectionImpl)client).isClosed()) {
               id.setHandle(h);
               id.setConnection(client);
               this.handles.put(id.getValue(), id);
            }
         }

         if (JMSDebug.JMSDotNetT3Server.isDebugEnabled()) {
            JMSDebug.JMSDotNetT3Server.debug("T3 service id [" + serviceId + "] hello request From " + myRJVM.getRJVM() + ", assigned connection id " + id.getValue());
         }

         try {
            ReplyStream reply = req.getResponseStream();
            reply.write(2);
            reply.writeLong(id.getValue());
            reply.send();
         } catch (IOException var14) {
            throw new RemoteException("RemoteRequest: Failed to send [hello] response", var14);
         }
      }
   }

   private void dispatchRequest(RemoteRequest req) throws RemoteException {
      long conn;
      try {
         conn = req.readLong();
      } catch (IOException var8) {
         throw new RemoteException(var8.toString(), var8);
      }

      T3ConnectionHandle h;
      synchronized(this.handles) {
         T3ConnectionHandleID hId = (T3ConnectionHandleID)this.handles.get(conn);
         if (hId == null) {
            this.reportFailure((byte)3, (byte)0);
            return;
         }

         h = hId.getHandle();
      }

      h.onMessage((ChunkedDataInputStream)req);
   }

   private synchronized T3ConnectionHandleID getNextHandleID() {
      return new T3ConnectionHandleID(++this.handleID);
   }

   private T3RJVM getT3RJVM(RemoteRequest req) throws RemoteException {
      JVMID id = new JVMID();

      try {
         id.readExternal(req);
      } catch (IOException var5) {
         throw new RemoteException("RemoteRequest: T3 connection [hello] syntax error", var5);
      } catch (ClassNotFoundException var6) {
         throw new RemoteException("RemoteRequest: Unknown JVMID syntax " + var6);
      }

      RJVM rjvm = RJVMManager.getRJVMManager().findOrCreate(id);
      ServerChannel ch = ((InboundRequest)req).getServerChannel();
      return new T3RJVM(rjvm, ch);
   }

   public int hashCode() {
      return 41;
   }

   static {
      setT3ConnectionHandleFactory(0, new T3ConnectionHandleFactoryImpl());
   }
}
