package weblogic.jms.multicast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.jms.client.ConsumerInternal;
import weblogic.jms.client.JMSSession;
import weblogic.jms.common.BufferDataInputStream;
import weblogic.jms.common.BufferDataOutputStream;
import weblogic.jms.common.BufferInputStreamChunked;
import weblogic.jms.common.BufferOutputStream;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.ObjectIOBypass;
import weblogic.jms.extensions.SequenceGapException;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataInputStream;

public class JMSTMSocket implements Runnable {
   private static final int MAX_FRAGMENT_SIZE = 10240;
   private static final int PAYLOAD_FUDGE_FACTOR = 232;
   private static final int MESSAGE_VERSION = 1;
   private static final int FRAGMENT_VERSION = 1;
   private static final int VERSION_MASK = 4095;
   private static final int FRAGMENT_MAGIC = 199886103;
   protected static final int INITIAL_SEQNO = 0;
   private JMSTDMSocket sock;
   private final Object wantLock = new Object();
   private int wantLockCount = 0;
   private final JMSSession session;
   protected boolean closed;
   private final JMSTMObjectIOBypassImpl objectIOBypassImpl = new JMSTMObjectIOBypassImpl();
   private final byte[] bdosMsgBuffer = new byte[10240];
   private final byte[] bdosFragBuffer = new byte[10240];
   private final byte[] bdisFragBuffer = new byte[10240];
   private final BufferOutputStream bdosMsg;
   private final BufferOutputStream bdosFrag;
   private final BufferDataInputStream bdisFrag;
   private HashMap stashes;
   private final int receivePort;
   private final HashMap groups;
   private final HashMap destinations;
   private final HashMap dests;
   private long fragmentDelay;
   private long lastDelay;
   private long lastSendTime;
   private static final String PROTOCOL = "WeblogicMulticast";

   public JMSTMSocket(JMSSession session, JMSTDMSocket sock, int fragmentDelay, int receivePort) throws IOException {
      this.bdosMsg = new BufferDataOutputStream(this.objectIOBypassImpl, this.bdosMsgBuffer);
      this.bdosFrag = new BufferDataOutputStream((ObjectIOBypass)null, this.bdosFragBuffer);
      this.bdisFrag = new BufferDataInputStream((ObjectIOBypass)null, this.bdisFragBuffer);
      this.session = session;
      this.sock = sock;
      this.receivePort = receivePort;
      this.fragmentDelay = (long)fragmentDelay;
      this.lastDelay = 0L;
      this.lastSendTime = 0L;
      this.closed = false;
      this.groups = new HashMap();
      this.destinations = new HashMap();
      this.dests = new HashMap();
      this.stashes = new HashMap();
      this.bdosMsg.setIsJMSMulticastOutputStream();
      this.bdosFrag.setIsJMSMulticastOutputStream();
   }

   public final void setFragmentDelay(long fd) {
      this.fragmentDelay = fd;
   }

   public final boolean isClosed() {
      return this.closed;
   }

   public final void close() {
      this.incWantLockCount();
      synchronized(this) {
         if (!this.closed) {
            this.closed = true;
            this.sock = null;
            this.stashes = null;
         }
      }

      this.decWantLockCount();
   }

   public final void send(MessageImpl message, DestinationImpl destination, JMSID connectionId, InetAddress group, int port, byte ttl, long nextSeqNo) throws IOException {
      String fullDestinationName = destination.getServerName() + "/" + destination.getName();
      if (this.closed) {
         throw new IOException("Attempt to send message on multicast socket that is closed");
      } else {
         this.bdosMsg.reset();
         this.bdosMsg.writeShort(1);
         this.bdosMsg.writeByte(message.getType());
         message.writeExternal(this.bdosMsg.getObjectOutput());
         connectionId.writeExternal(this.bdosMsg.getObjectOutput());
         this.bdosMsg.flush();
         int messageSize = this.bdosMsg.size();
         int offset = 0;

         for(int fragNum = 0; offset < messageSize; ++fragNum) {
            this.bdosFrag.reset();
            this.bdosFrag.writeInt(199886103);
            this.bdosFrag.writeShort(1);
            this.bdosFrag.writeUTF(fullDestinationName);
            this.bdosFrag.writeLong(nextSeqNo);
            this.bdosFrag.writeInt(messageSize);
            this.bdosFrag.writeInt(fragNum);
            this.bdosFrag.writeInt(offset);
            this.bdosFrag.flush();
            int bytesThisTime = Math.min(10008 - this.bdosFrag.size(), messageSize - offset);
            this.bdosFrag.writeInt(bytesThisTime);
            if (bytesThisTime > 0) {
               this.bdosFrag.write(this.bdosMsgBuffer, offset, bytesThisTime);
            }

            this.bdosFrag.flush();
            this.sendThrottled(this.bdosFragBuffer, this.bdosFrag.size(), group, port, ttl);
            offset += bytesThisTime;
         }

      }
   }

   private void sendThrottled(byte[] buffer, int length, InetAddress group, int port, byte ttl) throws IOException {
      long currentTime = System.currentTimeMillis();
      this.lastDelay = Math.max(this.lastDelay - Math.max(currentTime - this.lastSendTime, 0L) + this.fragmentDelay, 0L);
      this.lastSendTime = currentTime;
      if (this.lastDelay > 0L) {
         try {
            Thread.sleep(this.lastDelay);
         } catch (InterruptedException var9) {
         }
      }

      this.sock.send(buffer, length, group, port, ttl);
   }

   public final String getProtocol() {
      return "WeblogicMulticast";
   }

   public void run() {
      while(true) {
         if (this.getWantLockCount() != 0) {
            Thread.yield();
         }

         MessageImpl message;
         synchronized(this) {
            if (this.sock == null) {
               return;
            }

            try {
               message = this.receive();
            } catch (Exception var15) {
               throw new RuntimeException(var15);
            }

            if (message == null) {
               continue;
            }
         }

         synchronized(this.session) {
            synchronized(this) {
               JMSID connectionId = message.getConnectionId();
               message.setConnectionId((JMSID)null);
               DestinationImpl destination = (DestinationImpl)message.getJMSDestination();
               String fullDestinationName = destination.getServerName() + "/" + destination.getName();
               JMSPushRequest pushRequest = new JMSPushRequest(0, (JMSID)null, message);
               ArrayList dstconsumers;
               if ((dstconsumers = (ArrayList)this.destinations.get(fullDestinationName)) != null) {
                  for(int i = 0; i < dstconsumers.size(); ++i) {
                     ConsumerInternal consumer = (ConsumerInternal)dstconsumers.get(i);
                     if (!consumer.privateGetNoLocal() || !this.session.getConnection().getJMSID().equals(connectionId)) {
                        try {
                           if (consumer.getExpressionEvaluator() != null && !consumer.getExpressionEvaluator().evaluate(message)) {
                              continue;
                           }
                        } catch (ExpressionEvaluationException var16) {
                           continue;
                        } catch (ClassCastException var17) {
                           continue;
                        }

                        JMSID consumerId = consumer.getJMSID();
                        if (consumerId != null) {
                           JMSPushEntry pushEntry = new JMSPushEntry((JMSID)null, consumerId, Long.MAX_VALUE, Long.MAX_VALUE, 1, 0);
                           pushRequest.addPushEntry(pushEntry);
                        }
                     }
                  }

                  this.session.pushMessage(pushRequest, false);
               }
            }
         }
      }
   }

   private MessageImpl receive() throws Exception {
      try {
         if (this.closed) {
            return null;
         }

         if (this.sock.receive(this.bdisFragBuffer) == 0) {
            return null;
         }

         this.bdisFrag.reset();
         if (this.bdisFrag.readInt() != 199886103) {
            return null;
         }

         if ((this.bdisFrag.readShort() & 4095) != 1) {
            return null;
         }

         String fullDestinationName = this.bdisFrag.readUTF();
         DestinationImpl destination = (DestinationImpl)this.dests.get(fullDestinationName);
         if (destination == null) {
            return null;
         }

         long seqNo = this.bdisFrag.readLong();
         int messageSize = this.bdisFrag.readInt();
         int fragNum = this.bdisFrag.readInt();
         int offset = this.bdisFrag.readInt();
         int payloadSize = this.bdisFrag.readInt();
         JMSFragmentStash fragStash = (JMSFragmentStash)this.stashes.get(fullDestinationName);
         if (fragStash == null) {
            fragStash = new JMSFragmentStash(this.session, seqNo, destination);
            this.stashes.put(fullDestinationName, fragStash);
         }

         Chunk chunk = fragStash.processFragment(seqNo, messageSize, fragNum, offset, this.bdisFrag, payloadSize);
         if (chunk != null) {
            BufferInputStreamChunked bisc = new BufferInputStreamChunked(this.objectIOBypassImpl, new ChunkedDataInputStream(chunk, 0));
            if ((bisc.readShort() & 4095) != 1) {
               return null;
            }

            MessageImpl message = MessageImpl.createMessageImpl(bisc.readByte());
            message.readExternal(bisc);
            JMSID connectionId = new JMSID();
            connectionId.readExternal(bisc);
            message.setJMSDestinationImpl(destination);
            message.setConnectionId(connectionId);
            return message;
         }
      } catch (IOException var14) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("IOException", var14);
         }
      } catch (ClassNotFoundException var15) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("ClassNotFoundException", var15);
         }
      } catch (SequenceGapException var16) {
         this.session.onException(var16);
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("SequenceGapException", var16);
         }
      } catch (Throwable var17) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("Throwable", var17);
         }
      }

      return null;
   }

   public final void joinGroup(DestinationImpl destination, ConsumerInternal consumer) throws IOException {
      this.incWantLockCount();
      synchronized(this) {
         try {
            if (this.sock == null) {
               throw new IOException("socket closed");
            }

            if (destination.getPort() != this.receivePort) {
               throw new IOException("Wrong port");
            }

            String fullDestinationName = destination.getServerName() + "/" + destination.getName();

            InetAddress group;
            try {
               group = InetAddress.getByName(destination.getMulticastAddress());
            } catch (UnknownHostException var10) {
               throw new IOException("Cannot parse multicast address " + destination.getMulticastAddress());
            }

            ArrayList grpconsumers;
            ArrayList dstconsumers;
            if ((grpconsumers = (ArrayList)this.groups.get(group)) != null) {
               if (grpconsumers.indexOf(consumer) < 0) {
                  grpconsumers.add(consumer);
                  dstconsumers = (ArrayList)this.destinations.get(fullDestinationName);
                  if (dstconsumers == null) {
                     dstconsumers = new ArrayList();
                     this.destinations.put(fullDestinationName, dstconsumers);
                     this.dests.put(fullDestinationName, destination);
                  }

                  dstconsumers.add(consumer);
               }
            } else {
               this.sock.joinGroup(group);
               grpconsumers = new ArrayList();
               grpconsumers.add(consumer);
               this.groups.put(group, grpconsumers);
               dstconsumers = new ArrayList();
               this.destinations.put(fullDestinationName, dstconsumers);
               this.dests.put(fullDestinationName, destination);
               dstconsumers.add(consumer);
            }
         } catch (Throwable var11) {
            this.decWantLockCount();
            throw new IOException(var11.toString());
         }
      }

      this.decWantLockCount();
   }

   public final void leaveGroup(DestinationImpl destination, ConsumerInternal consumer) throws IOException {
      this.incWantLockCount();
      synchronized(this) {
         try {
            label58: {
               if (this.sock == null) {
                  throw new IOException("socket closed");
               }

               InetAddress group;
               try {
                  group = InetAddress.getByName(destination.getMulticastAddress());
               } catch (UnknownHostException var10) {
                  throw new IOException("Cannot parse multicast address " + destination.getMulticastAddress());
               }

               ArrayList grpconsumers = (ArrayList)this.groups.get(group);
               int index;
               if (grpconsumers != null && (index = grpconsumers.indexOf(consumer)) >= 0) {
                  grpconsumers.remove(index);
                  String fullDestinationName = destination.getServerName() + "/" + destination.getName();
                  ArrayList dstconsumers = (ArrayList)this.destinations.get(fullDestinationName);
                  if (dstconsumers != null && (index = dstconsumers.indexOf(consumer)) >= 0) {
                     dstconsumers.remove(index);
                     if (dstconsumers.size() == 0) {
                        this.destinations.remove(fullDestinationName);
                        this.dests.remove(fullDestinationName);
                        this.stashes.remove(fullDestinationName);
                     }

                     if (grpconsumers.size() == 0) {
                        this.groups.remove(group);
                        this.sock.leaveGroup(group);
                     }
                     break label58;
                  }

                  throw new IOException("can not find destination info about consumer");
               }

               throw new IOException("Cannot find group info about consumer");
            }
         } catch (Throwable var11) {
            this.decWantLockCount();
            throw new IOException(var11.toString());
         }
      }

      this.decWantLockCount();
   }

   public final void start() throws IOException {
      this.incWantLockCount();
      synchronized(this) {
         try {
            if (this.sock == null) {
               throw new IOException("socket is closed");
            }

            Iterator iterator = this.groups.keySet().iterator();

            while(iterator.hasNext()) {
               InetAddress group = (InetAddress)iterator.next();
               this.sock.joinGroup(group);
            }

            this.stashes = new HashMap();
         } catch (Throwable var5) {
            this.decWantLockCount();
            throw new IOException(var5.toString());
         }
      }

      this.decWantLockCount();
   }

   public final void stop() throws IOException {
      this.incWantLockCount();
      synchronized(this) {
         try {
            if (this.sock == null) {
               throw new IOException("socket is closed");
            }

            Iterator iterator = this.groups.keySet().iterator();

            while(iterator.hasNext()) {
               InetAddress group = (InetAddress)iterator.next();
               this.sock.leaveGroup(group);
            }

            this.stashes = null;
         } catch (Throwable var5) {
            this.decWantLockCount();
            throw new IOException(var5.toString());
         }
      }

      this.decWantLockCount();
   }

   private void incWantLockCount() {
      synchronized(this.wantLock) {
         ++this.wantLockCount;
      }
   }

   private void decWantLockCount() {
      synchronized(this.wantLock) {
         --this.wantLockCount;
      }
   }

   private int getWantLockCount() {
      synchronized(this.wantLock) {
         return this.wantLockCount;
      }
   }
}
