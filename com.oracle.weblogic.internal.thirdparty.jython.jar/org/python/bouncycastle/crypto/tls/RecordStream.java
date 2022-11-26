package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.util.io.SimpleOutputStream;

class RecordStream {
   private static int DEFAULT_PLAINTEXT_LIMIT = 16384;
   static final int TLS_HEADER_SIZE = 5;
   static final int TLS_HEADER_TYPE_OFFSET = 0;
   static final int TLS_HEADER_VERSION_OFFSET = 1;
   static final int TLS_HEADER_LENGTH_OFFSET = 3;
   private TlsProtocol handler;
   private InputStream input;
   private OutputStream output;
   private TlsCompression pendingCompression = null;
   private TlsCompression readCompression = null;
   private TlsCompression writeCompression = null;
   private TlsCipher pendingCipher = null;
   private TlsCipher readCipher = null;
   private TlsCipher writeCipher = null;
   private long readSeqNo = 0L;
   private long writeSeqNo = 0L;
   private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
   private TlsHandshakeHash handshakeHash = null;
   private SimpleOutputStream handshakeHashUpdater = new SimpleOutputStream() {
      public void write(byte[] var1, int var2, int var3) throws IOException {
         RecordStream.this.handshakeHash.update(var1, var2, var3);
      }
   };
   private ProtocolVersion readVersion = null;
   private ProtocolVersion writeVersion = null;
   private boolean restrictReadVersion = true;
   private int plaintextLimit;
   private int compressedLimit;
   private int ciphertextLimit;

   RecordStream(TlsProtocol var1, InputStream var2, OutputStream var3) {
      this.handler = var1;
      this.input = var2;
      this.output = var3;
      this.readCompression = new TlsNullCompression();
      this.writeCompression = this.readCompression;
   }

   void init(TlsContext var1) {
      this.readCipher = new TlsNullCipher(var1);
      this.writeCipher = this.readCipher;
      this.handshakeHash = new DeferredHash();
      this.handshakeHash.init(var1);
      this.setPlaintextLimit(DEFAULT_PLAINTEXT_LIMIT);
   }

   int getPlaintextLimit() {
      return this.plaintextLimit;
   }

   void setPlaintextLimit(int var1) {
      this.plaintextLimit = var1;
      this.compressedLimit = this.plaintextLimit + 1024;
      this.ciphertextLimit = this.compressedLimit + 1024;
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

   void setRestrictReadVersion(boolean var1) {
      this.restrictReadVersion = var1;
   }

   void setPendingConnectionState(TlsCompression var1, TlsCipher var2) {
      this.pendingCompression = var1;
      this.pendingCipher = var2;
   }

   void sentWriteCipherSpec() throws IOException {
      if (this.pendingCompression != null && this.pendingCipher != null) {
         this.writeCompression = this.pendingCompression;
         this.writeCipher = this.pendingCipher;
         this.writeSeqNo = 0L;
      } else {
         throw new TlsFatalAlert((short)40);
      }
   }

   void receivedReadCipherSpec() throws IOException {
      if (this.pendingCompression != null && this.pendingCipher != null) {
         this.readCompression = this.pendingCompression;
         this.readCipher = this.pendingCipher;
         this.readSeqNo = 0L;
      } else {
         throw new TlsFatalAlert((short)40);
      }
   }

   void finaliseHandshake() throws IOException {
      if (this.readCompression == this.pendingCompression && this.writeCompression == this.pendingCompression && this.readCipher == this.pendingCipher && this.writeCipher == this.pendingCipher) {
         this.pendingCompression = null;
         this.pendingCipher = null;
      } else {
         throw new TlsFatalAlert((short)40);
      }
   }

   void checkRecordHeader(byte[] var1) throws IOException {
      short var2 = TlsUtils.readUint8(var1, 0);
      checkType(var2, (short)10);
      int var3;
      if (!this.restrictReadVersion) {
         var3 = TlsUtils.readVersionRaw(var1, 1);
         if ((var3 & -256) != 768) {
            throw new TlsFatalAlert((short)47);
         }
      } else {
         ProtocolVersion var4 = TlsUtils.readVersion(var1, 1);
         if (this.readVersion != null && !var4.equals(this.readVersion)) {
            throw new TlsFatalAlert((short)47);
         }
      }

      var3 = TlsUtils.readUint16(var1, 3);
      checkLength(var3, this.ciphertextLimit, (short)22);
   }

   boolean readRecord() throws IOException {
      byte[] var1 = TlsUtils.readAllOrNothing(5, this.input);
      if (var1 == null) {
         return false;
      } else {
         short var2 = TlsUtils.readUint8(var1, 0);
         checkType(var2, (short)10);
         int var3;
         if (!this.restrictReadVersion) {
            var3 = TlsUtils.readVersionRaw(var1, 1);
            if ((var3 & -256) != 768) {
               throw new TlsFatalAlert((short)47);
            }
         } else {
            ProtocolVersion var5 = TlsUtils.readVersion(var1, 1);
            if (this.readVersion == null) {
               this.readVersion = var5;
            } else if (!var5.equals(this.readVersion)) {
               throw new TlsFatalAlert((short)47);
            }
         }

         var3 = TlsUtils.readUint16(var1, 3);
         checkLength(var3, this.ciphertextLimit, (short)22);
         byte[] var4 = this.decodeAndVerify(var2, this.input, var3);
         this.handler.processRecord(var2, var4, 0, var4.length);
         return true;
      }
   }

   byte[] decodeAndVerify(short var1, InputStream var2, int var3) throws IOException {
      byte[] var4 = TlsUtils.readFully(var3, var2);
      byte[] var5 = this.readCipher.decodeCiphertext((long)(this.readSeqNo++), var1, var4, 0, var4.length);
      checkLength(var5.length, this.compressedLimit, (short)22);
      OutputStream var6 = this.readCompression.decompress(this.buffer);
      if (var6 != this.buffer) {
         var6.write(var5, 0, var5.length);
         var6.flush();
         var5 = this.getBufferContents();
      }

      checkLength(var5.length, this.plaintextLimit, (short)30);
      if (var5.length < 1 && var1 != 23) {
         throw new TlsFatalAlert((short)47);
      } else {
         return var5;
      }
   }

   void writeRecord(short var1, byte[] var2, int var3, int var4) throws IOException {
      if (this.writeVersion != null) {
         checkType(var1, (short)80);
         checkLength(var4, this.plaintextLimit, (short)80);
         if (var4 < 1 && var1 != 23) {
            throw new TlsFatalAlert((short)80);
         } else {
            OutputStream var5 = this.writeCompression.compress(this.buffer);
            byte[] var6;
            byte[] var7;
            if (var5 == this.buffer) {
               var6 = this.writeCipher.encodePlaintext((long)(this.writeSeqNo++), var1, var2, var3, var4);
            } else {
               var5.write(var2, var3, var4);
               var5.flush();
               var7 = this.getBufferContents();
               checkLength(var7.length, var4 + 1024, (short)80);
               var6 = this.writeCipher.encodePlaintext((long)(this.writeSeqNo++), var1, var7, 0, var7.length);
            }

            checkLength(var6.length, this.ciphertextLimit, (short)80);
            var7 = new byte[var6.length + 5];
            TlsUtils.writeUint8((short)var1, var7, 0);
            TlsUtils.writeVersion(this.writeVersion, var7, 1);
            TlsUtils.writeUint16(var6.length, var7, 3);
            System.arraycopy(var6, 0, var7, 5, var6.length);
            this.output.write(var7);
            this.output.flush();
         }
      }
   }

   void notifyHelloComplete() {
      this.handshakeHash = this.handshakeHash.notifyPRFDetermined();
   }

   TlsHandshakeHash getHandshakeHash() {
      return this.handshakeHash;
   }

   OutputStream getHandshakeHashUpdater() {
      return this.handshakeHashUpdater;
   }

   TlsHandshakeHash prepareToFinish() {
      TlsHandshakeHash var1 = this.handshakeHash;
      this.handshakeHash = this.handshakeHash.stopTracking();
      return var1;
   }

   void safeClose() {
      try {
         this.input.close();
      } catch (IOException var3) {
      }

      try {
         this.output.close();
      } catch (IOException var2) {
      }

   }

   void flush() throws IOException {
      this.output.flush();
   }

   private byte[] getBufferContents() {
      byte[] var1 = this.buffer.toByteArray();
      this.buffer.reset();
      return var1;
   }

   private static void checkType(short var0, short var1) throws IOException {
      switch (var0) {
         case 20:
         case 21:
         case 22:
         case 23:
            return;
         default:
            throw new TlsFatalAlert(var1);
      }
   }

   private static void checkLength(int var0, int var1, short var2) throws IOException {
      if (var0 > var1) {
         throw new TlsFatalAlert(var2);
      }
   }
}
