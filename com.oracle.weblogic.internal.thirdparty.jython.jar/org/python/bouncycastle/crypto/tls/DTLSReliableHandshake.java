package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.util.Integers;

class DTLSReliableHandshake {
   private static final int MAX_RECEIVE_AHEAD = 10;
   private DTLSRecordLayer recordLayer;
   private TlsHandshakeHash handshakeHash;
   private Hashtable currentInboundFlight = new Hashtable();
   private Hashtable previousInboundFlight = null;
   private Vector outboundFlight = new Vector();
   private boolean sending = true;
   private int message_seq = 0;
   private int next_receive_seq = 0;

   DTLSReliableHandshake(TlsContext var1, DTLSRecordLayer var2) {
      this.recordLayer = var2;
      this.handshakeHash = new DeferredHash();
      this.handshakeHash.init(var1);
   }

   void notifyHelloComplete() {
      this.handshakeHash = this.handshakeHash.notifyPRFDetermined();
   }

   TlsHandshakeHash getHandshakeHash() {
      return this.handshakeHash;
   }

   TlsHandshakeHash prepareToFinish() {
      TlsHandshakeHash var1 = this.handshakeHash;
      this.handshakeHash = this.handshakeHash.stopTracking();
      return var1;
   }

   void sendMessage(short var1, byte[] var2) throws IOException {
      TlsUtils.checkUint24(var2.length);
      if (!this.sending) {
         this.checkInboundFlight();
         this.sending = true;
         this.outboundFlight.removeAllElements();
      }

      Message var3 = new Message(this.message_seq++, var1, var2);
      this.outboundFlight.addElement(var3);
      this.writeMessage(var3);
      this.updateHandshakeMessagesDigest(var3);
   }

   byte[] receiveMessageBody(short var1) throws IOException {
      Message var2 = this.receiveMessage();
      if (var2.getType() != var1) {
         throw new TlsFatalAlert((short)10);
      } else {
         return var2.getBody();
      }
   }

   Message receiveMessage() throws IOException {
      if (this.sending) {
         this.sending = false;
         this.prepareInboundFlight();
      }

      DTLSReassembler var1 = (DTLSReassembler)this.currentInboundFlight.get(Integers.valueOf(this.next_receive_seq));
      if (var1 != null) {
         byte[] var2 = var1.getBodyIfComplete();
         if (var2 != null) {
            this.previousInboundFlight = null;
            return this.updateHandshakeMessagesDigest(new Message(this.next_receive_seq++, var1.getMsgType(), var2));
         }
      }

      byte[] var13 = null;
      int var14 = 1000;

      while(true) {
         int var3 = this.recordLayer.getReceiveLimit();
         if (var13 == null || var13.length < var3) {
            var13 = new byte[var3];
         }

         try {
            while(true) {
               int var4 = this.recordLayer.receive(var13, 0, var3, var14);
               if (var4 < 0) {
                  break;
               }

               if (var4 >= 12) {
                  int var5 = TlsUtils.readUint24(var13, 9);
                  if (var4 == var5 + 12) {
                     int var6 = TlsUtils.readUint16(var13, 4);
                     if (var6 <= this.next_receive_seq + 10) {
                        short var7 = TlsUtils.readUint8(var13, 0);
                        int var8 = TlsUtils.readUint24(var13, 1);
                        int var9 = TlsUtils.readUint24(var13, 6);
                        if (var9 + var5 <= var8) {
                           DTLSReassembler var10;
                           if (var6 < this.next_receive_seq) {
                              if (this.previousInboundFlight != null) {
                                 var10 = (DTLSReassembler)this.previousInboundFlight.get(Integers.valueOf(var6));
                                 if (var10 != null) {
                                    var10.contributeFragment(var7, var8, var13, 12, var9, var5);
                                    if (checkAll(this.previousInboundFlight)) {
                                       this.resendOutboundFlight();
                                       var14 = Math.min(var14 * 2, 60000);
                                       resetAll(this.previousInboundFlight);
                                    }
                                 }
                              }
                           } else {
                              var10 = (DTLSReassembler)this.currentInboundFlight.get(Integers.valueOf(var6));
                              if (var10 == null) {
                                 var10 = new DTLSReassembler(var7, var8);
                                 this.currentInboundFlight.put(Integers.valueOf(var6), var10);
                              }

                              var10.contributeFragment(var7, var8, var13, 12, var9, var5);
                              if (var6 == this.next_receive_seq) {
                                 byte[] var11 = var10.getBodyIfComplete();
                                 if (var11 != null) {
                                    this.previousInboundFlight = null;
                                    return this.updateHandshakeMessagesDigest(new Message(this.next_receive_seq++, var10.getMsgType(), var11));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (IOException var12) {
         }

         this.resendOutboundFlight();
         var14 = Math.min(var14 * 2, 60000);
      }
   }

   void finish() {
      DTLSHandshakeRetransmit var1 = null;
      if (!this.sending) {
         this.checkInboundFlight();
      } else if (this.currentInboundFlight != null) {
         var1 = new DTLSHandshakeRetransmit() {
            public void receivedHandshakeRecord(int var1, byte[] var2, int var3, int var4) throws IOException {
               if (var4 >= 12) {
                  int var5 = TlsUtils.readUint24(var2, var3 + 9);
                  if (var4 == var5 + 12) {
                     int var6 = TlsUtils.readUint16(var2, var3 + 4);
                     if (var6 < DTLSReliableHandshake.this.next_receive_seq) {
                        short var7 = TlsUtils.readUint8(var2, var3);
                        int var8 = var7 == 20 ? 1 : 0;
                        if (var1 == var8) {
                           int var9 = TlsUtils.readUint24(var2, var3 + 1);
                           int var10 = TlsUtils.readUint24(var2, var3 + 6);
                           if (var10 + var5 <= var9) {
                              DTLSReassembler var11 = (DTLSReassembler)DTLSReliableHandshake.this.currentInboundFlight.get(Integers.valueOf(var6));
                              if (var11 != null) {
                                 var11.contributeFragment(var7, var9, var2, var3 + 12, var10, var5);
                                 if (DTLSReliableHandshake.checkAll(DTLSReliableHandshake.this.currentInboundFlight)) {
                                    DTLSReliableHandshake.this.resendOutboundFlight();
                                    DTLSReliableHandshake.resetAll(DTLSReliableHandshake.this.currentInboundFlight);
                                 }
                              }

                           }
                        }
                     }
                  }
               }
            }
         };
      }

      this.recordLayer.handshakeSuccessful(var1);
   }

   void resetHandshakeMessagesDigest() {
      this.handshakeHash.reset();
   }

   private void checkInboundFlight() {
      Enumeration var1 = this.currentInboundFlight.keys();

      while(var1.hasMoreElements()) {
         Integer var2 = (Integer)var1.nextElement();
         if (var2 >= this.next_receive_seq) {
         }
      }

   }

   private void prepareInboundFlight() {
      resetAll(this.currentInboundFlight);
      this.previousInboundFlight = this.currentInboundFlight;
      this.currentInboundFlight = new Hashtable();
   }

   private void resendOutboundFlight() throws IOException {
      this.recordLayer.resetWriteEpoch();

      for(int var1 = 0; var1 < this.outboundFlight.size(); ++var1) {
         this.writeMessage((Message)this.outboundFlight.elementAt(var1));
      }

   }

   private Message updateHandshakeMessagesDigest(Message var1) throws IOException {
      if (var1.getType() != 0) {
         byte[] var2 = var1.getBody();
         byte[] var3 = new byte[12];
         TlsUtils.writeUint8((short)var1.getType(), var3, 0);
         TlsUtils.writeUint24(var2.length, var3, 1);
         TlsUtils.writeUint16(var1.getSeq(), var3, 4);
         TlsUtils.writeUint24(0, var3, 6);
         TlsUtils.writeUint24(var2.length, var3, 9);
         this.handshakeHash.update(var3, 0, var3.length);
         this.handshakeHash.update(var2, 0, var2.length);
      }

      return var1;
   }

   private void writeMessage(Message var1) throws IOException {
      int var2 = this.recordLayer.getSendLimit();
      int var3 = var2 - 12;
      if (var3 < 1) {
         throw new TlsFatalAlert((short)80);
      } else {
         int var4 = var1.getBody().length;
         int var5 = 0;

         do {
            int var6 = Math.min(var4 - var5, var3);
            this.writeHandshakeFragment(var1, var5, var6);
            var5 += var6;
         } while(var5 < var4);

      }
   }

   private void writeHandshakeFragment(Message var1, int var2, int var3) throws IOException {
      RecordLayerBuffer var4 = new RecordLayerBuffer(12 + var3);
      TlsUtils.writeUint8((short)var1.getType(), var4);
      TlsUtils.writeUint24(var1.getBody().length, var4);
      TlsUtils.writeUint16(var1.getSeq(), var4);
      TlsUtils.writeUint24(var2, var4);
      TlsUtils.writeUint24(var3, var4);
      var4.write(var1.getBody(), var2, var3);
      var4.sendToRecordLayer(this.recordLayer);
   }

   private static boolean checkAll(Hashtable var0) {
      Enumeration var1 = var0.elements();

      do {
         if (!var1.hasMoreElements()) {
            return true;
         }
      } while(((DTLSReassembler)var1.nextElement()).getBodyIfComplete() != null);

      return false;
   }

   private static void resetAll(Hashtable var0) {
      Enumeration var1 = var0.elements();

      while(var1.hasMoreElements()) {
         ((DTLSReassembler)var1.nextElement()).reset();
      }

   }

   static class Message {
      private final int message_seq;
      private final short msg_type;
      private final byte[] body;

      private Message(int var1, short var2, byte[] var3) {
         this.message_seq = var1;
         this.msg_type = var2;
         this.body = var3;
      }

      public int getSeq() {
         return this.message_seq;
      }

      public short getType() {
         return this.msg_type;
      }

      public byte[] getBody() {
         return this.body;
      }

      // $FF: synthetic method
      Message(int var1, short var2, byte[] var3, Object var4) {
         this(var1, var2, var3);
      }
   }

   static class RecordLayerBuffer extends ByteArrayOutputStream {
      RecordLayerBuffer(int var1) {
         super(var1);
      }

      void sendToRecordLayer(DTLSRecordLayer var1) throws IOException {
         var1.send(this.buf, 0, this.count);
         this.buf = null;
      }
   }
}
