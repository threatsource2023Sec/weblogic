package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

class DTLSRecordLayer implements DatagramTransport {
   private static final int RECORD_HEADER_LENGTH = 13;
   private static final int MAX_FRAGMENT_LENGTH = 16384;
   private static final long TCP_MSL = 120000L;
   private static final long RETRANSMIT_TIMEOUT = 240000L;
   private final DatagramTransport transport;
   private final TlsContext context;
   private final TlsPeer peer;
   private final ByteQueue recordQueue = new ByteQueue();
   private volatile boolean closed = false;
   private volatile boolean failed = false;
   private volatile ProtocolVersion readVersion = null;
   private volatile ProtocolVersion writeVersion = null;
   private volatile boolean inHandshake;
   private volatile int plaintextLimit;
   private DTLSEpoch currentEpoch;
   private DTLSEpoch pendingEpoch;
   private DTLSEpoch readEpoch;
   private DTLSEpoch writeEpoch;
   private DTLSHandshakeRetransmit retransmit = null;
   private DTLSEpoch retransmitEpoch = null;
   private long retransmitExpiry = 0L;

   DTLSRecordLayer(DatagramTransport var1, TlsContext var2, TlsPeer var3, short var4) {
      this.transport = var1;
      this.context = var2;
      this.peer = var3;
      this.inHandshake = true;
      this.currentEpoch = new DTLSEpoch(0, new TlsNullCipher(var2));
      this.pendingEpoch = null;
      this.readEpoch = this.currentEpoch;
      this.writeEpoch = this.currentEpoch;
      this.setPlaintextLimit(16384);
   }

   void setPlaintextLimit(int var1) {
      this.plaintextLimit = var1;
   }

   ProtocolVersion getReadVersion() {
      return this.readVersion;
   }

   void setReadVersion(ProtocolVersion var1) {
      this.readVersion = var1;
   }

   void setWriteVersion(ProtocolVersion var1) {
      this.writeVersion = var1;
   }

   void initPendingEpoch(TlsCipher var1) {
      if (this.pendingEpoch != null) {
         throw new IllegalStateException();
      } else {
         this.pendingEpoch = new DTLSEpoch(this.writeEpoch.getEpoch() + 1, var1);
      }
   }

   void handshakeSuccessful(DTLSHandshakeRetransmit var1) {
      if (this.readEpoch != this.currentEpoch && this.writeEpoch != this.currentEpoch) {
         if (var1 != null) {
            this.retransmit = var1;
            this.retransmitEpoch = this.currentEpoch;
            this.retransmitExpiry = System.currentTimeMillis() + 240000L;
         }

         this.inHandshake = false;
         this.currentEpoch = this.pendingEpoch;
         this.pendingEpoch = null;
      } else {
         throw new IllegalStateException();
      }
   }

   void resetWriteEpoch() {
      if (this.retransmitEpoch != null) {
         this.writeEpoch = this.retransmitEpoch;
      } else {
         this.writeEpoch = this.currentEpoch;
      }

   }

   public int getReceiveLimit() throws IOException {
      return Math.min(this.plaintextLimit, this.readEpoch.getCipher().getPlaintextLimit(this.transport.getReceiveLimit() - 13));
   }

   public int getSendLimit() throws IOException {
      return Math.min(this.plaintextLimit, this.writeEpoch.getCipher().getPlaintextLimit(this.transport.getSendLimit() - 13));
   }

   public int receive(byte[] var1, int var2, int var3, int var4) throws IOException {
      byte[] var5 = null;

      label143:
      while(true) {
         int var6 = Math.min(var3, this.getReceiveLimit()) + 13;
         if (var5 == null || var5.length < var6) {
            var5 = new byte[var6];
         }

         try {
            if (this.retransmit != null && System.currentTimeMillis() > this.retransmitExpiry) {
               this.retransmit = null;
               this.retransmitEpoch = null;
            }

            int var7 = this.receiveRecord(var5, 0, var6, var4);
            if (var7 < 0) {
               return var7;
            }

            if (var7 >= 13) {
               int var8 = TlsUtils.readUint16(var5, 11);
               if (var7 == var8 + 13) {
                  short var9 = TlsUtils.readUint8(var5, 0);
                  switch (var9) {
                     case 20:
                     case 21:
                     case 22:
                     case 23:
                     case 24:
                        int var10 = TlsUtils.readUint16(var5, 3);
                        DTLSEpoch var11 = null;
                        if (var10 == this.readEpoch.getEpoch()) {
                           var11 = this.readEpoch;
                        } else if (var9 == 22 && this.retransmitEpoch != null && var10 == this.retransmitEpoch.getEpoch()) {
                           var11 = this.retransmitEpoch;
                        }

                        if (var11 != null) {
                           long var12 = TlsUtils.readUint48(var5, 5);
                           if (!var11.getReplayWindow().shouldDiscard(var12)) {
                              ProtocolVersion var14 = TlsUtils.readVersion(var5, 1);
                              if (var14.isDTLS() && (this.readVersion == null || this.readVersion.equals(var14))) {
                                 byte[] var15 = var11.getCipher().decodeCiphertext(getMacSequenceNumber(var11.getEpoch(), var12), var9, var5, 13, var7 - 13);
                                 var11.getReplayWindow().reportAuthenticated(var12);
                                 if (var15.length <= this.plaintextLimit) {
                                    if (this.readVersion == null) {
                                       this.readVersion = var14;
                                    }

                                    int var16;
                                    short var17;
                                    switch (var9) {
                                       case 20:
                                          var16 = 0;

                                          while(true) {
                                             if (var16 >= var15.length) {
                                                continue label143;
                                             }

                                             var17 = TlsUtils.readUint8(var15, var16);
                                             if (var17 == 1 && this.pendingEpoch != null) {
                                                this.readEpoch = this.pendingEpoch;
                                             }

                                             ++var16;
                                          }
                                       case 21:
                                          if (var15.length == 2) {
                                             var16 = (short)var15[0];
                                             var17 = (short)var15[1];
                                             this.peer.notifyAlertReceived((short)var16, var17);
                                             if (var16 == 2) {
                                                this.failed();
                                                throw new TlsFatalAlert(var17);
                                             }

                                             if (var17 == 0) {
                                                this.closeTransport();
                                             }
                                          }
                                          continue;
                                       case 22:
                                          if (!this.inHandshake) {
                                             if (this.retransmit != null) {
                                                this.retransmit.receivedHandshakeRecord(var10, var15, 0, var15.length);
                                             }
                                             continue;
                                          }
                                          break;
                                       case 23:
                                          if (this.inHandshake) {
                                             continue;
                                          }
                                          break;
                                       case 24:
                                          continue;
                                    }

                                    if (!this.inHandshake && this.retransmit != null) {
                                       this.retransmit = null;
                                       this.retransmitEpoch = null;
                                    }

                                    System.arraycopy(var15, 0, var1, var2, var15.length);
                                    return var15.length;
                                 }
                              }
                           }
                        }
                  }
               }
            }
         } catch (IOException var18) {
            throw var18;
         }
      }
   }

   public void send(byte[] var1, int var2, int var3) throws IOException {
      byte var4 = 23;
      if (this.inHandshake || this.writeEpoch == this.retransmitEpoch) {
         var4 = 22;
         short var5 = TlsUtils.readUint8(var1, var2);
         if (var5 == 20) {
            DTLSEpoch var6 = null;
            if (this.inHandshake) {
               var6 = this.pendingEpoch;
            } else if (this.writeEpoch == this.retransmitEpoch) {
               var6 = this.currentEpoch;
            }

            if (var6 == null) {
               throw new IllegalStateException();
            }

            byte[] var7 = new byte[]{1};
            this.sendRecord((short)20, var7, 0, var7.length);
            this.writeEpoch = var6;
         }
      }

      this.sendRecord(var4, var1, var2, var3);
   }

   public void close() throws IOException {
      if (!this.closed) {
         if (this.inHandshake) {
            this.warn((short)90, "User canceled handshake");
         }

         this.closeTransport();
      }

   }

   void fail(short var1) {
      if (!this.closed) {
         try {
            this.raiseAlert((short)2, var1, (String)null, (Throwable)null);
         } catch (Exception var3) {
         }

         this.failed = true;
         this.closeTransport();
      }

   }

   void failed() {
      if (!this.closed) {
         this.failed = true;
         this.closeTransport();
      }

   }

   void warn(short var1, String var2) throws IOException {
      this.raiseAlert((short)1, var1, var2, (Throwable)null);
   }

   private void closeTransport() {
      if (!this.closed) {
         try {
            if (!this.failed) {
               this.warn((short)0, (String)null);
            }

            this.transport.close();
         } catch (Exception var2) {
         }

         this.closed = true;
      }

   }

   private void raiseAlert(short var1, short var2, String var3, Throwable var4) throws IOException {
      this.peer.notifyAlertRaised(var1, var2, var3, var4);
      byte[] var5 = new byte[]{(byte)var1, (byte)var2};
      this.sendRecord((short)21, var5, 0, 2);
   }

   private int receiveRecord(byte[] var1, int var2, int var3, int var4) throws IOException {
      int var5;
      int var6;
      if (this.recordQueue.available() > 0) {
         var5 = 0;
         if (this.recordQueue.available() >= 13) {
            byte[] var8 = new byte[2];
            this.recordQueue.read(var8, 0, 2, 11);
            var5 = TlsUtils.readUint16(var8, 0);
         }

         var6 = Math.min(this.recordQueue.available(), 13 + var5);
         this.recordQueue.removeData(var1, var2, var6, 0);
         return var6;
      } else {
         var5 = this.transport.receive(var1, var2, var3, var4);
         if (var5 >= 13) {
            var6 = TlsUtils.readUint16(var1, var2 + 11);
            int var7 = 13 + var6;
            if (var5 > var7) {
               this.recordQueue.addData(var1, var2 + var7, var5 - var7);
               var5 = var7;
            }
         }

         return var5;
      }
   }

   private void sendRecord(short var1, byte[] var2, int var3, int var4) throws IOException {
      if (this.writeVersion != null) {
         if (var4 > this.plaintextLimit) {
            throw new TlsFatalAlert((short)80);
         } else if (var4 < 1 && var1 != 23) {
            throw new TlsFatalAlert((short)80);
         } else {
            int var5 = this.writeEpoch.getEpoch();
            long var6 = this.writeEpoch.allocateSequenceNumber();
            byte[] var8 = this.writeEpoch.getCipher().encodePlaintext(getMacSequenceNumber(var5, var6), var1, var2, var3, var4);
            byte[] var9 = new byte[var8.length + 13];
            TlsUtils.writeUint8((short)var1, var9, 0);
            TlsUtils.writeVersion(this.writeVersion, var9, 1);
            TlsUtils.writeUint16(var5, var9, 3);
            TlsUtils.writeUint48(var6, var9, 5);
            TlsUtils.writeUint16(var8.length, var9, 11);
            System.arraycopy(var8, 0, var9, 13, var8.length);
            this.transport.send(var9, 0, var9.length);
         }
      }
   }

   private static long getMacSequenceNumber(int var0, long var1) {
      return ((long)var0 & 4294967295L) << 48 | var1;
   }
}
