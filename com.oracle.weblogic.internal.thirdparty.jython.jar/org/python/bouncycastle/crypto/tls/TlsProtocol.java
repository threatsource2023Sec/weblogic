package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.prng.RandomGenerator;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Integers;

public abstract class TlsProtocol {
   protected static final Integer EXT_RenegotiationInfo = Integers.valueOf(65281);
   protected static final Integer EXT_SessionTicket = Integers.valueOf(35);
   private static final String TLS_ERROR_MESSAGE = "Internal TLS error, this could be an attack";
   protected static final short CS_START = 0;
   protected static final short CS_CLIENT_HELLO = 1;
   protected static final short CS_SERVER_HELLO = 2;
   protected static final short CS_SERVER_SUPPLEMENTAL_DATA = 3;
   protected static final short CS_SERVER_CERTIFICATE = 4;
   protected static final short CS_CERTIFICATE_STATUS = 5;
   protected static final short CS_SERVER_KEY_EXCHANGE = 6;
   protected static final short CS_CERTIFICATE_REQUEST = 7;
   protected static final short CS_SERVER_HELLO_DONE = 8;
   protected static final short CS_CLIENT_SUPPLEMENTAL_DATA = 9;
   protected static final short CS_CLIENT_CERTIFICATE = 10;
   protected static final short CS_CLIENT_KEY_EXCHANGE = 11;
   protected static final short CS_CERTIFICATE_VERIFY = 12;
   protected static final short CS_CLIENT_FINISHED = 13;
   protected static final short CS_SERVER_SESSION_TICKET = 14;
   protected static final short CS_SERVER_FINISHED = 15;
   protected static final short CS_END = 16;
   protected static final short ADS_MODE_1_Nsub1 = 0;
   protected static final short ADS_MODE_0_N = 1;
   protected static final short ADS_MODE_0_N_FIRSTONLY = 2;
   private ByteQueue applicationDataQueue = new ByteQueue(0);
   private ByteQueue alertQueue = new ByteQueue(2);
   private ByteQueue handshakeQueue = new ByteQueue(0);
   RecordStream recordStream;
   protected SecureRandom secureRandom;
   private TlsInputStream tlsInputStream = null;
   private TlsOutputStream tlsOutputStream = null;
   private volatile boolean closed = false;
   private volatile boolean failedWithError = false;
   private volatile boolean appDataReady = false;
   private volatile boolean appDataSplitEnabled = true;
   private volatile int appDataSplitMode = 0;
   private byte[] expected_verify_data = null;
   protected TlsSession tlsSession = null;
   protected SessionParameters sessionParameters = null;
   protected SecurityParameters securityParameters = null;
   protected Certificate peerCertificate = null;
   protected int[] offeredCipherSuites = null;
   protected short[] offeredCompressionMethods = null;
   protected Hashtable clientExtensions = null;
   protected Hashtable serverExtensions = null;
   protected short connection_state = 0;
   protected boolean resumedSession = false;
   protected boolean receivedChangeCipherSpec = false;
   protected boolean secure_renegotiation = false;
   protected boolean allowCertificateStatus = false;
   protected boolean expectSessionTicket = false;
   protected boolean blocking;
   protected ByteQueueInputStream inputBuffers;
   protected ByteQueueOutputStream outputBuffer;

   public TlsProtocol(InputStream var1, OutputStream var2, SecureRandom var3) {
      this.blocking = true;
      this.recordStream = new RecordStream(this, var1, var2);
      this.secureRandom = var3;
   }

   public TlsProtocol(SecureRandom var1) {
      this.blocking = false;
      this.inputBuffers = new ByteQueueInputStream();
      this.outputBuffer = new ByteQueueOutputStream();
      this.recordStream = new RecordStream(this, this.inputBuffers, this.outputBuffer);
      this.secureRandom = var1;
   }

   protected abstract TlsContext getContext();

   abstract AbstractTlsContext getContextAdmin();

   protected abstract TlsPeer getPeer();

   protected void handleChangeCipherSpecMessage() throws IOException {
   }

   protected abstract void handleHandshakeMessage(short var1, ByteArrayInputStream var2) throws IOException;

   protected void handleWarningMessage(short var1) throws IOException {
   }

   protected void applyMaxFragmentLengthExtension() throws IOException {
      if (this.securityParameters.maxFragmentLength >= 0) {
         if (!MaxFragmentLength.isValid(this.securityParameters.maxFragmentLength)) {
            throw new TlsFatalAlert((short)80);
         }

         int var1 = 1 << 8 + this.securityParameters.maxFragmentLength;
         this.recordStream.setPlaintextLimit(var1);
      }

   }

   protected void checkReceivedChangeCipherSpec(boolean var1) throws IOException {
      if (var1 != this.receivedChangeCipherSpec) {
         throw new TlsFatalAlert((short)10);
      }
   }

   protected void cleanupHandshake() {
      if (this.expected_verify_data != null) {
         Arrays.fill((byte[])this.expected_verify_data, (byte)0);
         this.expected_verify_data = null;
      }

      this.securityParameters.clear();
      this.peerCertificate = null;
      this.offeredCipherSuites = null;
      this.offeredCompressionMethods = null;
      this.clientExtensions = null;
      this.serverExtensions = null;
      this.resumedSession = false;
      this.receivedChangeCipherSpec = false;
      this.secure_renegotiation = false;
      this.allowCertificateStatus = false;
      this.expectSessionTicket = false;
   }

   protected void blockForHandshake() throws IOException {
      if (this.blocking) {
         while(this.connection_state != 16) {
            if (this.closed) {
               throw new TlsFatalAlert((short)80);
            }

            this.safeReadRecord();
         }
      }

   }

   protected void completeHandshake() throws IOException {
      try {
         this.connection_state = 16;
         this.alertQueue.shrink();
         this.handshakeQueue.shrink();
         this.recordStream.finaliseHandshake();
         this.appDataSplitEnabled = !TlsUtils.isTLSv11(this.getContext());
         if (!this.appDataReady) {
            this.appDataReady = true;
            if (this.blocking) {
               this.tlsInputStream = new TlsInputStream(this);
               this.tlsOutputStream = new TlsOutputStream(this);
            }
         }

         if (this.tlsSession != null) {
            if (this.sessionParameters == null) {
               this.sessionParameters = (new SessionParameters.Builder()).setCipherSuite(this.securityParameters.getCipherSuite()).setCompressionAlgorithm(this.securityParameters.getCompressionAlgorithm()).setMasterSecret(this.securityParameters.getMasterSecret()).setPeerCertificate(this.peerCertificate).setPSKIdentity(this.securityParameters.getPSKIdentity()).setSRPIdentity(this.securityParameters.getSRPIdentity()).setServerExtensions(this.serverExtensions).build();
               this.tlsSession = new TlsSessionImpl(this.tlsSession.getSessionID(), this.sessionParameters);
            }

            this.getContextAdmin().setResumableSession(this.tlsSession);
         }

         this.getPeer().notifyHandshakeComplete();
      } finally {
         this.cleanupHandshake();
      }

   }

   protected void processRecord(short var1, byte[] var2, int var3, int var4) throws IOException {
      switch (var1) {
         case 20:
            this.processChangeCipherSpec(var2, var3, var4);
            break;
         case 21:
            this.alertQueue.addData(var2, var3, var4);
            this.processAlertQueue();
            break;
         case 22:
            if (this.handshakeQueue.available() > 0) {
               this.handshakeQueue.addData(var2, var3, var4);
               this.processHandshakeQueue(this.handshakeQueue);
            } else {
               ByteQueue var5 = new ByteQueue(var2, var3, var4);
               this.processHandshakeQueue(var5);
               int var6 = var5.available();
               if (var6 > 0) {
                  this.handshakeQueue.addData(var2, var3 + var4 - var6, var6);
               }
            }
            break;
         case 23:
            if (!this.appDataReady) {
               throw new TlsFatalAlert((short)10);
            }

            this.applicationDataQueue.addData(var2, var3, var4);
            this.processApplicationDataQueue();
            break;
         default:
            throw new TlsFatalAlert((short)80);
      }

   }

   private void processHandshakeQueue(ByteQueue var1) throws IOException {
      while(true) {
         if (var1.available() >= 4) {
            byte[] var2 = new byte[4];
            var1.read(var2, 0, 4, 0);
            short var3 = TlsUtils.readUint8(var2, 0);
            int var4 = TlsUtils.readUint24(var2, 1);
            int var5 = 4 + var4;
            if (var1.available() >= var5) {
               this.checkReceivedChangeCipherSpec(this.connection_state == 16 || var3 == 20);
               switch (var3) {
                  case 20:
                     TlsContext var6 = this.getContext();
                     if (this.expected_verify_data == null && var6.getSecurityParameters().getMasterSecret() != null) {
                        this.expected_verify_data = this.createVerifyData(!var6.isServer());
                     }
                  default:
                     var1.copyTo(this.recordStream.getHandshakeHashUpdater(), var5);
                  case 0:
                     var1.removeData(4);
                     ByteArrayInputStream var7 = var1.readFrom(var4);
                     this.handleHandshakeMessage(var3, var7);
                     continue;
               }
            }
         }

         return;
      }
   }

   private void processApplicationDataQueue() {
   }

   private void processAlertQueue() throws IOException {
      short var3;
      for(; this.alertQueue.available() >= 2; this.handleWarningMessage(var3)) {
         byte[] var1 = this.alertQueue.removeData(2, 0);
         short var2 = (short)var1[0];
         var3 = (short)var1[1];
         this.getPeer().notifyAlertReceived(var2, var3);
         if (var2 == 2) {
            this.invalidateSession();
            this.failedWithError = true;
            this.closed = true;
            this.recordStream.safeClose();
            throw new IOException("Internal TLS error, this could be an attack");
         }

         if (var3 == 0) {
            if (!this.appDataReady) {
               throw new TlsFatalAlert((short)40);
            }

            this.handleClose(false);
         }
      }

   }

   private void processChangeCipherSpec(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         short var5 = TlsUtils.readUint8(var1, var2 + var4);
         if (var5 != 1) {
            throw new TlsFatalAlert((short)50);
         }

         if (this.receivedChangeCipherSpec || this.alertQueue.available() > 0 || this.handshakeQueue.available() > 0) {
            throw new TlsFatalAlert((short)10);
         }

         this.recordStream.receivedReadCipherSpec();
         this.receivedChangeCipherSpec = true;
         this.handleChangeCipherSpecMessage();
      }

   }

   protected int applicationDataAvailable() {
      return this.applicationDataQueue.available();
   }

   protected int readApplicationData(byte[] var1, int var2, int var3) throws IOException {
      if (var3 < 1) {
         return 0;
      } else {
         while(this.applicationDataQueue.available() == 0) {
            if (this.closed) {
               if (this.failedWithError) {
                  throw new IOException("Internal TLS error, this could be an attack");
               }

               return -1;
            }

            this.safeReadRecord();
         }

         var3 = Math.min(var3, this.applicationDataQueue.available());
         this.applicationDataQueue.removeData(var1, var2, var3, 0);
         return var3;
      }
   }

   protected void safeCheckRecordHeader(byte[] var1) throws IOException {
      try {
         this.recordStream.checkRecordHeader(var1);
      } catch (TlsFatalAlert var3) {
         this.failWithError((short)2, var3.getAlertDescription(), "Failed to read record", var3);
         throw var3;
      } catch (IOException var4) {
         this.failWithError((short)2, (short)80, "Failed to read record", var4);
         throw var4;
      } catch (RuntimeException var5) {
         this.failWithError((short)2, (short)80, "Failed to read record", var5);
         throw var5;
      }
   }

   protected void safeReadRecord() throws IOException {
      try {
         if (!this.recordStream.readRecord()) {
            if (!this.appDataReady) {
               throw new TlsFatalAlert((short)40);
            } else {
               throw new TlsNoCloseNotifyException();
            }
         }
      } catch (TlsFatalAlert var2) {
         if (!this.closed) {
            this.failWithError((short)2, var2.getAlertDescription(), "Failed to read record", var2);
         }

         throw var2;
      } catch (IOException var3) {
         if (!this.closed) {
            this.failWithError((short)2, (short)80, "Failed to read record", var3);
         }

         throw var3;
      } catch (RuntimeException var4) {
         if (!this.closed) {
            this.failWithError((short)2, (short)80, "Failed to read record", var4);
         }

         throw var4;
      }
   }

   protected void safeWriteRecord(short var1, byte[] var2, int var3, int var4) throws IOException {
      try {
         this.recordStream.writeRecord(var1, var2, var3, var4);
      } catch (TlsFatalAlert var6) {
         if (!this.closed) {
            this.failWithError((short)2, var6.getAlertDescription(), "Failed to write record", var6);
         }

         throw var6;
      } catch (IOException var7) {
         if (!this.closed) {
            this.failWithError((short)2, (short)80, "Failed to write record", var7);
         }

         throw var7;
      } catch (RuntimeException var8) {
         if (!this.closed) {
            this.failWithError((short)2, (short)80, "Failed to write record", var8);
         }

         throw var8;
      }
   }

   protected void writeData(byte[] var1, int var2, int var3) throws IOException {
      if (this.closed) {
         if (this.failedWithError) {
            throw new IOException("Internal TLS error, this could be an attack");
         } else {
            throw new IOException("Sorry, connection has been closed, you cannot write more data");
         }
      } else {
         while(var3 > 0) {
            if (this.appDataSplitEnabled) {
               switch (this.appDataSplitMode) {
                  case 0:
                  default:
                     this.safeWriteRecord((short)23, var1, var2, 1);
                     ++var2;
                     --var3;
                     break;
                  case 2:
                     this.appDataSplitEnabled = false;
                  case 1:
                     this.safeWriteRecord((short)23, TlsUtils.EMPTY_BYTES, 0, 0);
               }
            }

            if (var3 > 0) {
               int var4 = Math.min(var3, this.recordStream.getPlaintextLimit());
               this.safeWriteRecord((short)23, var1, var2, var4);
               var2 += var4;
               var3 -= var4;
            }
         }

      }
   }

   protected void setAppDataSplitMode(int var1) {
      if (var1 >= 0 && var1 <= 2) {
         this.appDataSplitMode = var1;
      } else {
         throw new IllegalArgumentException("Illegal appDataSplitMode mode: " + var1);
      }
   }

   protected void writeHandshakeMessage(byte[] var1, int var2, int var3) throws IOException {
      if (var3 < 4) {
         throw new TlsFatalAlert((short)80);
      } else {
         short var4 = TlsUtils.readUint8(var1, var2);
         if (var4 != 0) {
            this.recordStream.getHandshakeHashUpdater().write(var1, var2, var3);
         }

         int var5 = 0;

         do {
            int var6 = Math.min(var3 - var5, this.recordStream.getPlaintextLimit());
            this.safeWriteRecord((short)22, var1, var2 + var5, var6);
            var5 += var6;
         } while(var5 < var3);

      }
   }

   public OutputStream getOutputStream() {
      if (!this.blocking) {
         throw new IllegalStateException("Cannot use OutputStream in non-blocking mode! Use offerOutput() instead.");
      } else {
         return this.tlsOutputStream;
      }
   }

   public InputStream getInputStream() {
      if (!this.blocking) {
         throw new IllegalStateException("Cannot use InputStream in non-blocking mode! Use offerInput() instead.");
      } else {
         return this.tlsInputStream;
      }
   }

   public void closeInput() throws IOException {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use closeInput() in blocking mode!");
      } else if (!this.closed) {
         if (this.inputBuffers.available() > 0) {
            throw new EOFException();
         } else if (!this.appDataReady) {
            throw new TlsFatalAlert((short)40);
         } else {
            throw new TlsNoCloseNotifyException();
         }
      }
   }

   public void offerInput(byte[] var1) throws IOException {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use offerInput() in blocking mode! Use getInputStream() instead.");
      } else if (this.closed) {
         throw new IOException("Connection is closed, cannot accept any more input");
      } else {
         this.inputBuffers.addBytes(var1);

         while(this.inputBuffers.available() >= 5) {
            byte[] var2 = new byte[5];
            this.inputBuffers.peek(var2);
            int var3 = TlsUtils.readUint16(var2, 3) + 5;
            if (this.inputBuffers.available() < var3) {
               this.safeCheckRecordHeader(var2);
               break;
            }

            this.safeReadRecord();
            if (this.closed) {
               if (this.connection_state != 16) {
                  throw new TlsFatalAlert((short)80);
               }
               break;
            }
         }

      }
   }

   public int getAvailableInputBytes() {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use getAvailableInputBytes() in blocking mode! Use getInputStream().available() instead.");
      } else {
         return this.applicationDataAvailable();
      }
   }

   public int readInput(byte[] var1, int var2, int var3) {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use readInput() in blocking mode! Use getInputStream() instead.");
      } else {
         try {
            return this.readApplicationData(var1, var2, Math.min(var3, this.applicationDataAvailable()));
         } catch (IOException var5) {
            throw new RuntimeException(var5.toString());
         }
      }
   }

   public void offerOutput(byte[] var1, int var2, int var3) throws IOException {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use offerOutput() in blocking mode! Use getOutputStream() instead.");
      } else if (!this.appDataReady) {
         throw new IOException("Application data cannot be sent until the handshake is complete!");
      } else {
         this.writeData(var1, var2, var3);
      }
   }

   public int getAvailableOutputBytes() {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use getAvailableOutputBytes() in blocking mode! Use getOutputStream() instead.");
      } else {
         return this.outputBuffer.getBuffer().available();
      }
   }

   public int readOutput(byte[] var1, int var2, int var3) {
      if (this.blocking) {
         throw new IllegalStateException("Cannot use readOutput() in blocking mode! Use getOutputStream() instead.");
      } else {
         int var4 = Math.min(this.getAvailableOutputBytes(), var3);
         this.outputBuffer.getBuffer().removeData(var1, var2, var4, 0);
         return var4;
      }
   }

   protected void failWithError(short var1, short var2, String var3, Throwable var4) throws IOException {
      if (!this.closed) {
         this.closed = true;
         if (var1 == 2) {
            this.invalidateSession();
            this.failedWithError = true;
         }

         this.raiseAlert(var1, var2, var3, var4);
         this.recordStream.safeClose();
         if (var1 != 2) {
            return;
         }
      }

      throw new IOException("Internal TLS error, this could be an attack");
   }

   protected void invalidateSession() {
      if (this.sessionParameters != null) {
         this.sessionParameters.clear();
         this.sessionParameters = null;
      }

      if (this.tlsSession != null) {
         this.tlsSession.invalidate();
         this.tlsSession = null;
      }

   }

   protected void processFinishedMessage(ByteArrayInputStream var1) throws IOException {
      if (this.expected_verify_data == null) {
         throw new TlsFatalAlert((short)80);
      } else {
         byte[] var2 = TlsUtils.readFully(this.expected_verify_data.length, var1);
         assertEmpty(var1);
         if (!Arrays.constantTimeAreEqual(this.expected_verify_data, var2)) {
            throw new TlsFatalAlert((short)51);
         }
      }
   }

   protected void raiseAlert(short var1, short var2, String var3, Throwable var4) throws IOException {
      this.getPeer().notifyAlertRaised(var1, var2, var3, var4);
      byte[] var5 = new byte[]{(byte)var1, (byte)var2};
      this.safeWriteRecord((short)21, var5, 0, 2);
   }

   protected void raiseWarning(short var1, String var2) throws IOException {
      this.raiseAlert((short)1, var1, var2, (Throwable)null);
   }

   protected void sendCertificateMessage(Certificate var1) throws IOException {
      if (var1 == null) {
         var1 = Certificate.EMPTY_CHAIN;
      }

      if (var1.isEmpty()) {
         TlsContext var2 = this.getContext();
         if (!var2.isServer()) {
            ProtocolVersion var3 = this.getContext().getServerVersion();
            if (var3.isSSL()) {
               String var4 = var3.toString() + " client didn't provide credentials";
               this.raiseWarning((short)41, var4);
               return;
            }
         }
      }

      HandshakeMessage var5 = new HandshakeMessage((short)11);
      var1.encode(var5);
      var5.writeToRecordStream();
   }

   protected void sendChangeCipherSpecMessage() throws IOException {
      byte[] var1 = new byte[]{1};
      this.safeWriteRecord((short)20, var1, 0, var1.length);
      this.recordStream.sentWriteCipherSpec();
   }

   protected void sendFinishedMessage() throws IOException {
      byte[] var1 = this.createVerifyData(this.getContext().isServer());
      HandshakeMessage var2 = new HandshakeMessage((short)20, var1.length);
      var2.write(var1);
      var2.writeToRecordStream();
   }

   protected void sendSupplementalDataMessage(Vector var1) throws IOException {
      HandshakeMessage var2 = new HandshakeMessage((short)23);
      writeSupplementalData(var2, var1);
      var2.writeToRecordStream();
   }

   protected byte[] createVerifyData(boolean var1) {
      TlsContext var2 = this.getContext();
      String var3 = var1 ? "server finished" : "client finished";
      byte[] var4 = var1 ? TlsUtils.SSL_SERVER : TlsUtils.SSL_CLIENT;
      byte[] var5 = getCurrentPRFHash(var2, this.recordStream.getHandshakeHash(), var4);
      return TlsUtils.calculateVerifyData(var2, var3, var5);
   }

   public void close() throws IOException {
      this.handleClose(true);
   }

   protected void handleClose(boolean var1) throws IOException {
      if (!this.closed) {
         if (var1 && !this.appDataReady) {
            this.raiseWarning((short)90, "User canceled handshake");
         }

         this.failWithError((short)1, (short)0, "Connection closed", (Throwable)null);
      }

   }

   protected void flush() throws IOException {
      this.recordStream.flush();
   }

   public boolean isClosed() {
      return this.closed;
   }

   protected short processMaxFragmentLengthExtension(Hashtable var1, Hashtable var2, short var3) throws IOException {
      short var4 = TlsExtensionsUtils.getMaxFragmentLengthExtension(var2);
      if (var4 < 0 || MaxFragmentLength.isValid(var4) && (this.resumedSession || var4 == TlsExtensionsUtils.getMaxFragmentLengthExtension(var1))) {
         return var4;
      } else {
         throw new TlsFatalAlert(var3);
      }
   }

   protected void refuseRenegotiation() throws IOException {
      if (TlsUtils.isSSL(this.getContext())) {
         throw new TlsFatalAlert((short)40);
      } else {
         this.raiseWarning((short)100, "Renegotiation not supported");
      }
   }

   protected static void assertEmpty(ByteArrayInputStream var0) throws IOException {
      if (var0.available() > 0) {
         throw new TlsFatalAlert((short)50);
      }
   }

   protected static byte[] createRandomBlock(boolean var0, RandomGenerator var1) {
      byte[] var2 = new byte[32];
      var1.nextBytes(var2);
      if (var0) {
         TlsUtils.writeGMTUnixTime(var2, 0);
      }

      return var2;
   }

   protected static byte[] createRenegotiationInfo(byte[] var0) throws IOException {
      return TlsUtils.encodeOpaque8(var0);
   }

   protected static void establishMasterSecret(TlsContext var0, TlsKeyExchange var1) throws IOException {
      byte[] var2 = var1.generatePremasterSecret();

      try {
         var0.getSecurityParameters().masterSecret = TlsUtils.calculateMasterSecret(var0, var2);
      } finally {
         if (var2 != null) {
            Arrays.fill((byte[])var2, (byte)0);
         }

      }

   }

   protected static byte[] getCurrentPRFHash(TlsContext var0, TlsHandshakeHash var1, byte[] var2) {
      Digest var3 = var1.forkPRFHash();
      if (var2 != null && TlsUtils.isSSL(var0)) {
         var3.update(var2, 0, var2.length);
      }

      byte[] var4 = new byte[var3.getDigestSize()];
      var3.doFinal(var4, 0);
      return var4;
   }

   protected static Hashtable readExtensions(ByteArrayInputStream var0) throws IOException {
      if (var0.available() < 1) {
         return null;
      } else {
         byte[] var1 = TlsUtils.readOpaque16(var0);
         assertEmpty(var0);
         ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
         Hashtable var3 = new Hashtable();

         Integer var4;
         byte[] var5;
         do {
            if (var2.available() <= 0) {
               return var3;
            }

            var4 = Integers.valueOf(TlsUtils.readUint16(var2));
            var5 = TlsUtils.readOpaque16(var2);
         } while(null == var3.put(var4, var5));

         throw new TlsFatalAlert((short)47);
      }
   }

   protected static Vector readSupplementalDataMessage(ByteArrayInputStream var0) throws IOException {
      byte[] var1 = TlsUtils.readOpaque24(var0);
      assertEmpty(var0);
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      Vector var3 = new Vector();

      while(var2.available() > 0) {
         int var4 = TlsUtils.readUint16(var2);
         byte[] var5 = TlsUtils.readOpaque16(var2);
         var3.addElement(new SupplementalDataEntry(var4, var5));
      }

      return var3;
   }

   protected static void writeExtensions(OutputStream var0, Hashtable var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      writeSelectedExtensions(var2, var1, true);
      writeSelectedExtensions(var2, var1, false);
      byte[] var3 = var2.toByteArray();
      TlsUtils.writeOpaque16(var3, var0);
   }

   protected static void writeSelectedExtensions(OutputStream var0, Hashtable var1, boolean var2) throws IOException {
      Enumeration var3 = var1.keys();

      while(var3.hasMoreElements()) {
         Integer var4 = (Integer)var3.nextElement();
         int var5 = var4;
         byte[] var6 = (byte[])((byte[])var1.get(var4));
         if (var2 == (var6.length == 0)) {
            TlsUtils.checkUint16(var5);
            TlsUtils.writeUint16(var5, var0);
            TlsUtils.writeOpaque16(var6, var0);
         }
      }

   }

   protected static void writeSupplementalData(OutputStream var0, Vector var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         SupplementalDataEntry var4 = (SupplementalDataEntry)var1.elementAt(var3);
         int var5 = var4.getDataType();
         TlsUtils.checkUint16(var5);
         TlsUtils.writeUint16(var5, var2);
         TlsUtils.writeOpaque16(var4.getData(), var2);
      }

      byte[] var6 = var2.toByteArray();
      TlsUtils.writeOpaque24(var6, var0);
   }

   protected static int getPRFAlgorithm(TlsContext var0, int var1) throws IOException {
      boolean var2 = TlsUtils.isTLSv12(var0);
      switch (var1) {
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 156:
         case 158:
         case 160:
         case 162:
         case 164:
         case 166:
         case 168:
         case 170:
         case 172:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         case 191:
         case 192:
         case 193:
         case 194:
         case 195:
         case 196:
         case 197:
         case 49187:
         case 49189:
         case 49191:
         case 49193:
         case 49195:
         case 49197:
         case 49199:
         case 49201:
         case 49266:
         case 49268:
         case 49270:
         case 49272:
         case 49274:
         case 49276:
         case 49278:
         case 49280:
         case 49282:
         case 49284:
         case 49286:
         case 49288:
         case 49290:
         case 49292:
         case 49294:
         case 49296:
         case 49298:
         case 49308:
         case 49309:
         case 49310:
         case 49311:
         case 49312:
         case 49313:
         case 49314:
         case 49315:
         case 49316:
         case 49317:
         case 49318:
         case 49319:
         case 49320:
         case 49321:
         case 49322:
         case 49323:
         case 49324:
         case 49325:
         case 49326:
         case 49327:
         case 52392:
         case 52393:
         case 52394:
         case 52395:
         case 52396:
         case 52397:
         case 52398:
         case 65280:
         case 65281:
         case 65282:
         case 65283:
         case 65284:
         case 65285:
         case 65296:
         case 65297:
         case 65298:
         case 65299:
         case 65300:
         case 65301:
            if (var2) {
               return 1;
            }

            throw new TlsFatalAlert((short)47);
         case 157:
         case 159:
         case 161:
         case 163:
         case 165:
         case 167:
         case 169:
         case 171:
         case 173:
         case 49188:
         case 49190:
         case 49192:
         case 49194:
         case 49196:
         case 49198:
         case 49200:
         case 49202:
         case 49267:
         case 49269:
         case 49271:
         case 49273:
         case 49275:
         case 49277:
         case 49279:
         case 49281:
         case 49283:
         case 49285:
         case 49287:
         case 49289:
         case 49291:
         case 49293:
         case 49295:
         case 49297:
         case 49299:
            if (var2) {
               return 2;
            }

            throw new TlsFatalAlert((short)47);
         case 175:
         case 177:
         case 179:
         case 181:
         case 183:
         case 185:
         case 49208:
         case 49211:
         case 49301:
         case 49303:
         case 49305:
         case 49307:
            if (var2) {
               return 2;
            }

            return 0;
         default:
            return var2 ? 1 : 0;
      }
   }

   class HandshakeMessage extends ByteArrayOutputStream {
      HandshakeMessage(short var2) throws IOException {
         this(var2, 60);
      }

      HandshakeMessage(short var2, int var3) throws IOException {
         super(var3 + 4);
         TlsUtils.writeUint8((short)var2, this);
         this.count += 3;
      }

      void writeToRecordStream() throws IOException {
         int var1 = this.count - 4;
         TlsUtils.checkUint24(var1);
         TlsUtils.writeUint24(var1, this.buf, 1);
         TlsProtocol.this.writeHandshakeMessage(this.buf, 0, this.count);
         this.buf = null;
      }
   }
}
