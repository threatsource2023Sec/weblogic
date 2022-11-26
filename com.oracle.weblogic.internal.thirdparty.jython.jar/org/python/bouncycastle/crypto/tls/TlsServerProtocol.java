package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Vector;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;
import org.python.bouncycastle.util.Arrays;

public class TlsServerProtocol extends TlsProtocol {
   protected TlsServer tlsServer = null;
   TlsServerContextImpl tlsServerContext = null;
   protected TlsKeyExchange keyExchange = null;
   protected TlsCredentials serverCredentials = null;
   protected CertificateRequest certificateRequest = null;
   protected short clientCertificateType = -1;
   protected TlsHandshakeHash prepareFinishHash = null;

   public TlsServerProtocol(InputStream var1, OutputStream var2, SecureRandom var3) {
      super(var1, var2, var3);
   }

   public TlsServerProtocol(SecureRandom var1) {
      super(var1);
   }

   public void accept(TlsServer var1) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("'tlsServer' cannot be null");
      } else if (this.tlsServer != null) {
         throw new IllegalStateException("'accept' can only be called once");
      } else {
         this.tlsServer = var1;
         this.securityParameters = new SecurityParameters();
         this.securityParameters.entity = 0;
         this.tlsServerContext = new TlsServerContextImpl(this.secureRandom, this.securityParameters);
         this.securityParameters.serverRandom = createRandomBlock(var1.shouldUseGMTUnixTime(), this.tlsServerContext.getNonceRandomGenerator());
         this.tlsServer.init(this.tlsServerContext);
         this.recordStream.init(this.tlsServerContext);
         this.recordStream.setRestrictReadVersion(false);
         this.blockForHandshake();
      }
   }

   protected void cleanupHandshake() {
      super.cleanupHandshake();
      this.keyExchange = null;
      this.serverCredentials = null;
      this.certificateRequest = null;
      this.prepareFinishHash = null;
   }

   protected TlsContext getContext() {
      return this.tlsServerContext;
   }

   AbstractTlsContext getContextAdmin() {
      return this.tlsServerContext;
   }

   protected TlsPeer getPeer() {
      return this.tlsServer;
   }

   protected void handleHandshakeMessage(short var1, ByteArrayInputStream var2) throws IOException {
      switch (var1) {
         case 0:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 17:
         case 18:
         case 19:
         case 21:
         case 22:
         default:
            throw new TlsFatalAlert((short)10);
         case 1:
            switch (this.connection_state) {
               case 0:
                  this.receiveClientHelloMessage(var2);
                  this.connection_state = 1;
                  this.sendServerHelloMessage();
                  this.connection_state = 2;
                  this.recordStream.notifyHelloComplete();
                  Vector var3 = this.tlsServer.getServerSupplementalData();
                  if (var3 != null) {
                     this.sendSupplementalDataMessage(var3);
                  }

                  this.connection_state = 3;
                  this.keyExchange = this.tlsServer.getKeyExchange();
                  this.keyExchange.init(this.getContext());
                  this.serverCredentials = this.tlsServer.getCredentials();
                  Certificate var4 = null;
                  if (this.serverCredentials == null) {
                     this.keyExchange.skipServerCredentials();
                  } else {
                     this.keyExchange.processServerCredentials(this.serverCredentials);
                     var4 = this.serverCredentials.getCertificate();
                     this.sendCertificateMessage(var4);
                  }

                  this.connection_state = 4;
                  if (var4 == null || var4.isEmpty()) {
                     this.allowCertificateStatus = false;
                  }

                  if (this.allowCertificateStatus) {
                     CertificateStatus var5 = this.tlsServer.getCertificateStatus();
                     if (var5 != null) {
                        this.sendCertificateStatusMessage(var5);
                     }
                  }

                  this.connection_state = 5;
                  byte[] var6 = this.keyExchange.generateServerKeyExchange();
                  if (var6 != null) {
                     this.sendServerKeyExchangeMessage(var6);
                  }

                  this.connection_state = 6;
                  if (this.serverCredentials != null) {
                     this.certificateRequest = this.tlsServer.getCertificateRequest();
                     if (this.certificateRequest != null) {
                        if (TlsUtils.isTLSv12(this.getContext()) != (this.certificateRequest.getSupportedSignatureAlgorithms() != null)) {
                           throw new TlsFatalAlert((short)80);
                        }

                        this.keyExchange.validateCertificateRequest(this.certificateRequest);
                        this.sendCertificateRequestMessage(this.certificateRequest);
                        TlsUtils.trackHashAlgorithms(this.recordStream.getHandshakeHash(), this.certificateRequest.getSupportedSignatureAlgorithms());
                     }
                  }

                  this.connection_state = 7;
                  this.sendServerHelloDoneMessage();
                  this.connection_state = 8;
                  this.recordStream.getHandshakeHash().sealHashAlgorithms();
                  return;
               case 16:
                  this.refuseRenegotiation();
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
         case 11:
            switch (this.connection_state) {
               case 8:
                  this.tlsServer.processClientSupplementalData((Vector)null);
               case 9:
                  if (this.certificateRequest == null) {
                     throw new TlsFatalAlert((short)10);
                  }

                  this.receiveCertificateMessage(var2);
                  this.connection_state = 10;
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
         case 15:
            switch (this.connection_state) {
               case 11:
                  if (!this.expectCertificateVerifyMessage()) {
                     throw new TlsFatalAlert((short)10);
                  }

                  this.receiveCertificateVerifyMessage(var2);
                  this.connection_state = 12;
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
         case 16:
            switch (this.connection_state) {
               case 8:
                  this.tlsServer.processClientSupplementalData((Vector)null);
               case 9:
                  if (this.certificateRequest == null) {
                     this.keyExchange.skipClientCredentials();
                  } else if (TlsUtils.isTLSv12(this.getContext())) {
                     throw new TlsFatalAlert((short)10);
                  } else if (TlsUtils.isSSL(this.getContext())) {
                     if (this.peerCertificate == null) {
                        throw new TlsFatalAlert((short)10);
                     }
                  } else {
                     this.notifyClientCertificate(Certificate.EMPTY_CHAIN);
                  }
               case 10:
                  this.receiveClientKeyExchangeMessage(var2);
                  this.connection_state = 11;
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
         case 20:
            switch (this.connection_state) {
               case 11:
                  if (this.expectCertificateVerifyMessage()) {
                     throw new TlsFatalAlert((short)10);
                  }
               case 12:
                  this.processFinishedMessage(var2);
                  this.connection_state = 13;
                  if (this.expectSessionTicket) {
                     this.sendNewSessionTicketMessage(this.tlsServer.getNewSessionTicket());
                     this.sendChangeCipherSpecMessage();
                  }

                  this.connection_state = 14;
                  this.sendFinishedMessage();
                  this.connection_state = 15;
                  this.completeHandshake();
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
         case 23:
            switch (this.connection_state) {
               case 8:
                  this.tlsServer.processClientSupplementalData(readSupplementalDataMessage(var2));
                  this.connection_state = 9;
                  return;
               default:
                  throw new TlsFatalAlert((short)10);
            }
      }
   }

   protected void handleWarningMessage(short var1) throws IOException {
      switch (var1) {
         case 41:
            if (TlsUtils.isSSL(this.getContext()) && this.certificateRequest != null) {
               this.notifyClientCertificate(Certificate.EMPTY_CHAIN);
            }
            break;
         default:
            super.handleWarningMessage(var1);
      }

   }

   protected void notifyClientCertificate(Certificate var1) throws IOException {
      if (this.certificateRequest == null) {
         throw new IllegalStateException();
      } else if (this.peerCertificate != null) {
         throw new TlsFatalAlert((short)10);
      } else {
         this.peerCertificate = var1;
         if (var1.isEmpty()) {
            this.keyExchange.skipClientCredentials();
         } else {
            this.clientCertificateType = TlsUtils.getClientCertificateType(var1, this.serverCredentials.getCertificate());
            this.keyExchange.processClientCertificate(var1);
         }

         this.tlsServer.notifyClientCertificate(var1);
      }
   }

   protected void receiveCertificateMessage(ByteArrayInputStream var1) throws IOException {
      Certificate var2 = Certificate.parse(var1);
      assertEmpty(var1);
      this.notifyClientCertificate(var2);
   }

   protected void receiveCertificateVerifyMessage(ByteArrayInputStream var1) throws IOException {
      if (this.certificateRequest == null) {
         throw new IllegalStateException();
      } else {
         DigitallySigned var2 = DigitallySigned.parse(this.getContext(), var1);
         assertEmpty(var1);

         try {
            SignatureAndHashAlgorithm var3 = var2.getAlgorithm();
            byte[] var4;
            if (TlsUtils.isTLSv12(this.getContext())) {
               TlsUtils.verifySupportedSignatureAlgorithm(this.certificateRequest.getSupportedSignatureAlgorithms(), var3);
               var4 = this.prepareFinishHash.getFinalHash(var3.getHash());
            } else {
               var4 = this.securityParameters.getSessionHash();
            }

            org.python.bouncycastle.asn1.x509.Certificate var5 = this.peerCertificate.getCertificateAt(0);
            SubjectPublicKeyInfo var6 = var5.getSubjectPublicKeyInfo();
            AsymmetricKeyParameter var7 = PublicKeyFactory.createKey(var6);
            TlsSigner var8 = TlsUtils.createTlsSigner(this.clientCertificateType);
            var8.init(this.getContext());
            if (!var8.verifyRawSignature(var3, var2.getSignature(), var7, var4)) {
               throw new TlsFatalAlert((short)51);
            }
         } catch (TlsFatalAlert var9) {
            throw var9;
         } catch (Exception var10) {
            throw new TlsFatalAlert((short)51, var10);
         }
      }
   }

   protected void receiveClientHelloMessage(ByteArrayInputStream var1) throws IOException {
      ProtocolVersion var2 = TlsUtils.readVersion(var1);
      this.recordStream.setWriteVersion(var2);
      if (var2.isDTLS()) {
         throw new TlsFatalAlert((short)47);
      } else {
         byte[] var3 = TlsUtils.readFully(32, var1);
         byte[] var4 = TlsUtils.readOpaque8(var1);
         if (var4.length > 32) {
            throw new TlsFatalAlert((short)47);
         } else {
            int var5 = TlsUtils.readUint16(var1);
            if (var5 >= 2 && (var5 & 1) == 0) {
               this.offeredCipherSuites = TlsUtils.readUint16Array(var5 / 2, var1);
               short var6 = TlsUtils.readUint8(var1);
               if (var6 < 1) {
                  throw new TlsFatalAlert((short)47);
               } else {
                  this.offeredCompressionMethods = TlsUtils.readUint8Array(var6, var1);
                  this.clientExtensions = readExtensions(var1);
                  this.securityParameters.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(this.clientExtensions);
                  this.getContextAdmin().setClientVersion(var2);
                  this.tlsServer.notifyClientVersion(var2);
                  this.tlsServer.notifyFallback(Arrays.contains((int[])this.offeredCipherSuites, (int)22016));
                  this.securityParameters.clientRandom = var3;
                  this.tlsServer.notifyOfferedCipherSuites(this.offeredCipherSuites);
                  this.tlsServer.notifyOfferedCompressionMethods(this.offeredCompressionMethods);
                  if (Arrays.contains((int[])this.offeredCipherSuites, (int)255)) {
                     this.secure_renegotiation = true;
                  }

                  byte[] var7 = TlsUtils.getExtensionData(this.clientExtensions, EXT_RenegotiationInfo);
                  if (var7 != null) {
                     this.secure_renegotiation = true;
                     if (!Arrays.constantTimeAreEqual(var7, createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                        throw new TlsFatalAlert((short)40);
                     }
                  }

                  this.tlsServer.notifySecureRenegotiation(this.secure_renegotiation);
                  if (this.clientExtensions != null) {
                     TlsExtensionsUtils.getPaddingExtension(this.clientExtensions);
                     this.tlsServer.processClientExtensions(this.clientExtensions);
                  }

               }
            } else {
               throw new TlsFatalAlert((short)50);
            }
         }
      }
   }

   protected void receiveClientKeyExchangeMessage(ByteArrayInputStream var1) throws IOException {
      this.keyExchange.processClientKeyExchange(var1);
      assertEmpty(var1);
      if (TlsUtils.isSSL(this.getContext())) {
         establishMasterSecret(this.getContext(), this.keyExchange);
      }

      this.prepareFinishHash = this.recordStream.prepareToFinish();
      this.securityParameters.sessionHash = getCurrentPRFHash(this.getContext(), this.prepareFinishHash, (byte[])null);
      if (!TlsUtils.isSSL(this.getContext())) {
         establishMasterSecret(this.getContext(), this.keyExchange);
      }

      this.recordStream.setPendingConnectionState(this.getPeer().getCompression(), this.getPeer().getCipher());
      if (!this.expectSessionTicket) {
         this.sendChangeCipherSpecMessage();
      }

   }

   protected void sendCertificateRequestMessage(CertificateRequest var1) throws IOException {
      TlsProtocol.HandshakeMessage var2 = new TlsProtocol.HandshakeMessage((short)13);
      var1.encode(var2);
      var2.writeToRecordStream();
   }

   protected void sendCertificateStatusMessage(CertificateStatus var1) throws IOException {
      TlsProtocol.HandshakeMessage var2 = new TlsProtocol.HandshakeMessage((short)22);
      var1.encode(var2);
      var2.writeToRecordStream();
   }

   protected void sendNewSessionTicketMessage(NewSessionTicket var1) throws IOException {
      if (var1 == null) {
         throw new TlsFatalAlert((short)80);
      } else {
         TlsProtocol.HandshakeMessage var2 = new TlsProtocol.HandshakeMessage((short)4);
         var1.encode(var2);
         var2.writeToRecordStream();
      }
   }

   protected void sendServerHelloMessage() throws IOException {
      TlsProtocol.HandshakeMessage var1 = new TlsProtocol.HandshakeMessage((short)2);
      ProtocolVersion var2 = this.tlsServer.getServerVersion();
      if (!var2.isEqualOrEarlierVersionOf(this.getContext().getClientVersion())) {
         throw new TlsFatalAlert((short)80);
      } else {
         this.recordStream.setReadVersion(var2);
         this.recordStream.setWriteVersion(var2);
         this.recordStream.setRestrictReadVersion(true);
         this.getContextAdmin().setServerVersion(var2);
         TlsUtils.writeVersion(var2, var1);
         var1.write(this.securityParameters.serverRandom);
         TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, var1);
         int var6 = this.tlsServer.getSelectedCipherSuite();
         if (Arrays.contains(this.offeredCipherSuites, var6) && var6 != 0 && !CipherSuite.isSCSV(var6) && TlsUtils.isValidCipherSuiteForVersion(var6, this.getContext().getServerVersion())) {
            this.securityParameters.cipherSuite = var6;
            short var3 = this.tlsServer.getSelectedCompressionMethod();
            if (!Arrays.contains(this.offeredCompressionMethods, var3)) {
               throw new TlsFatalAlert((short)80);
            } else {
               this.securityParameters.compressionAlgorithm = var3;
               TlsUtils.writeUint16(var6, var1);
               TlsUtils.writeUint8((short)var3, var1);
               this.serverExtensions = this.tlsServer.getServerExtensions();
               if (this.secure_renegotiation) {
                  byte[] var4 = TlsUtils.getExtensionData(this.serverExtensions, EXT_RenegotiationInfo);
                  boolean var5 = null == var4;
                  if (var5) {
                     this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.serverExtensions);
                     this.serverExtensions.put(EXT_RenegotiationInfo, createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
                  }
               }

               if (this.securityParameters.extendedMasterSecret) {
                  this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.serverExtensions);
                  TlsExtensionsUtils.addExtendedMasterSecretExtension(this.serverExtensions);
               }

               if (this.serverExtensions != null) {
                  this.securityParameters.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(this.serverExtensions);
                  this.securityParameters.maxFragmentLength = this.processMaxFragmentLengthExtension(this.clientExtensions, this.serverExtensions, (short)80);
                  this.securityParameters.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(this.serverExtensions);
                  this.allowCertificateStatus = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsExtensionsUtils.EXT_status_request, (short)80);
                  this.expectSessionTicket = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsProtocol.EXT_SessionTicket, (short)80);
                  writeExtensions(var1, this.serverExtensions);
               }

               this.securityParameters.prfAlgorithm = getPRFAlgorithm(this.getContext(), this.securityParameters.getCipherSuite());
               this.securityParameters.verifyDataLength = 12;
               this.applyMaxFragmentLengthExtension();
               var1.writeToRecordStream();
            }
         } else {
            throw new TlsFatalAlert((short)80);
         }
      }
   }

   protected void sendServerHelloDoneMessage() throws IOException {
      byte[] var1 = new byte[4];
      TlsUtils.writeUint8((short)14, var1, 0);
      TlsUtils.writeUint24(0, var1, 1);
      this.writeHandshakeMessage(var1, 0, var1.length);
   }

   protected void sendServerKeyExchangeMessage(byte[] var1) throws IOException {
      TlsProtocol.HandshakeMessage var2 = new TlsProtocol.HandshakeMessage((short)12, var1.length);
      var2.write(var1);
      var2.writeToRecordStream();
   }

   protected boolean expectCertificateVerifyMessage() {
      return this.clientCertificateType >= 0 && TlsUtils.hasSigningCapability(this.clientCertificateType);
   }
}
