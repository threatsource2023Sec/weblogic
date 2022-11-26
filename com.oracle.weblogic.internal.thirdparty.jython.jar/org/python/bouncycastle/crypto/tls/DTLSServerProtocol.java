package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.util.PublicKeyFactory;
import org.python.bouncycastle.util.Arrays;

public class DTLSServerProtocol extends DTLSProtocol {
   protected boolean verifyRequests = true;

   public DTLSServerProtocol(SecureRandom var1) {
      super(var1);
   }

   public boolean getVerifyRequests() {
      return this.verifyRequests;
   }

   public void setVerifyRequests(boolean var1) {
      this.verifyRequests = var1;
   }

   public DTLSTransport accept(TlsServer var1, DatagramTransport var2) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("'server' cannot be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("'transport' cannot be null");
      } else {
         SecurityParameters var3 = new SecurityParameters();
         var3.entity = 0;
         ServerHandshakeState var4 = new ServerHandshakeState();
         var4.server = var1;
         var4.serverContext = new TlsServerContextImpl(this.secureRandom, var3);
         var3.serverRandom = TlsProtocol.createRandomBlock(var1.shouldUseGMTUnixTime(), var4.serverContext.getNonceRandomGenerator());
         var1.init(var4.serverContext);
         DTLSRecordLayer var5 = new DTLSRecordLayer(var2, var4.serverContext, var1, (short)22);

         DTLSTransport var6;
         try {
            var6 = this.serverHandshake(var4, var5);
         } catch (TlsFatalAlert var12) {
            this.abortServerHandshake(var4, var5, var12.getAlertDescription());
            throw var12;
         } catch (IOException var13) {
            this.abortServerHandshake(var4, var5, (short)80);
            throw var13;
         } catch (RuntimeException var14) {
            this.abortServerHandshake(var4, var5, (short)80);
            throw new TlsFatalAlert((short)80, var14);
         } finally {
            var3.clear();
         }

         return var6;
      }
   }

   protected void abortServerHandshake(ServerHandshakeState var1, DTLSRecordLayer var2, short var3) {
      var2.fail(var3);
      this.invalidateSession(var1);
   }

   protected DTLSTransport serverHandshake(ServerHandshakeState var1, DTLSRecordLayer var2) throws IOException {
      SecurityParameters var3 = var1.serverContext.getSecurityParameters();
      DTLSReliableHandshake var4 = new DTLSReliableHandshake(var1.serverContext, var2);
      DTLSReliableHandshake.Message var5 = var4.receiveMessage();
      if (var5.getType() != 1) {
         throw new TlsFatalAlert((short)10);
      } else {
         this.processClientHello(var1, var5.getBody());
         byte[] var6 = this.generateServerHello(var1);
         applyMaxFragmentLengthExtension(var2, var3.maxFragmentLength);
         ProtocolVersion var7 = var1.serverContext.getServerVersion();
         var2.setReadVersion(var7);
         var2.setWriteVersion(var7);
         var4.sendMessage((short)2, var6);
         var4.notifyHelloComplete();
         Vector var13 = var1.server.getServerSupplementalData();
         if (var13 != null) {
            byte[] var14 = generateSupplementalData(var13);
            var4.sendMessage((short)23, var14);
         }

         var1.keyExchange = var1.server.getKeyExchange();
         var1.keyExchange.init(var1.serverContext);
         var1.serverCredentials = var1.server.getCredentials();
         Certificate var15 = null;
         byte[] var8;
         if (var1.serverCredentials == null) {
            var1.keyExchange.skipServerCredentials();
         } else {
            var1.keyExchange.processServerCredentials(var1.serverCredentials);
            var15 = var1.serverCredentials.getCertificate();
            var8 = generateCertificate(var15);
            var4.sendMessage((short)11, var8);
         }

         if (var15 == null || var15.isEmpty()) {
            var1.allowCertificateStatus = false;
         }

         byte[] var9;
         if (var1.allowCertificateStatus) {
            CertificateStatus var16 = var1.server.getCertificateStatus();
            if (var16 != null) {
               var9 = this.generateCertificateStatus(var1, var16);
               var4.sendMessage((short)22, var9);
            }
         }

         var8 = var1.keyExchange.generateServerKeyExchange();
         if (var8 != null) {
            var4.sendMessage((short)12, var8);
         }

         if (var1.serverCredentials != null) {
            var1.certificateRequest = var1.server.getCertificateRequest();
            if (var1.certificateRequest != null) {
               if (TlsUtils.isTLSv12((TlsContext)var1.serverContext) != (var1.certificateRequest.getSupportedSignatureAlgorithms() != null)) {
                  throw new TlsFatalAlert((short)80);
               }

               var1.keyExchange.validateCertificateRequest(var1.certificateRequest);
               var9 = this.generateCertificateRequest(var1, var1.certificateRequest);
               var4.sendMessage((short)13, var9);
               TlsUtils.trackHashAlgorithms(var4.getHandshakeHash(), var1.certificateRequest.getSupportedSignatureAlgorithms());
            }
         }

         var4.sendMessage((short)14, TlsUtils.EMPTY_BYTES);
         var4.getHandshakeHash().sealHashAlgorithms();
         var5 = var4.receiveMessage();
         if (var5.getType() == 23) {
            this.processClientSupplementalData(var1, var5.getBody());
            var5 = var4.receiveMessage();
         } else {
            var1.server.processClientSupplementalData((Vector)null);
         }

         if (var1.certificateRequest == null) {
            var1.keyExchange.skipClientCredentials();
         } else if (var5.getType() == 11) {
            this.processClientCertificate(var1, var5.getBody());
            var5 = var4.receiveMessage();
         } else {
            if (TlsUtils.isTLSv12((TlsContext)var1.serverContext)) {
               throw new TlsFatalAlert((short)10);
            }

            this.notifyClientCertificate(var1, Certificate.EMPTY_CHAIN);
         }

         if (var5.getType() == 16) {
            this.processClientKeyExchange(var1, var5.getBody());
            TlsHandshakeHash var17 = var4.prepareToFinish();
            var3.sessionHash = TlsProtocol.getCurrentPRFHash(var1.serverContext, var17, (byte[])null);
            TlsProtocol.establishMasterSecret(var1.serverContext, var1.keyExchange);
            var2.initPendingEpoch(var1.server.getCipher());
            byte[] var10;
            if (this.expectCertificateVerifyMessage(var1)) {
               var10 = var4.receiveMessageBody((short)15);
               this.processCertificateVerify(var1, var10, var17);
            }

            var10 = TlsUtils.calculateVerifyData(var1.serverContext, "client finished", TlsProtocol.getCurrentPRFHash(var1.serverContext, var4.getHandshakeHash(), (byte[])null));
            this.processFinished(var4.receiveMessageBody((short)20), var10);
            if (var1.expectSessionTicket) {
               NewSessionTicket var11 = var1.server.getNewSessionTicket();
               byte[] var12 = this.generateNewSessionTicket(var1, var11);
               var4.sendMessage((short)4, var12);
            }

            byte[] var18 = TlsUtils.calculateVerifyData(var1.serverContext, "server finished", TlsProtocol.getCurrentPRFHash(var1.serverContext, var4.getHandshakeHash(), (byte[])null));
            var4.sendMessage((short)20, var18);
            var4.finish();
            var1.server.notifyHandshakeComplete();
            return new DTLSTransport(var2);
         } else {
            throw new TlsFatalAlert((short)10);
         }
      }
   }

   protected byte[] generateCertificateRequest(ServerHandshakeState var1, CertificateRequest var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      var2.encode(var3);
      return var3.toByteArray();
   }

   protected byte[] generateCertificateStatus(ServerHandshakeState var1, CertificateStatus var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      var2.encode(var3);
      return var3.toByteArray();
   }

   protected byte[] generateNewSessionTicket(ServerHandshakeState var1, NewSessionTicket var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      var2.encode(var3);
      return var3.toByteArray();
   }

   protected byte[] generateServerHello(ServerHandshakeState var1) throws IOException {
      SecurityParameters var2 = var1.serverContext.getSecurityParameters();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      ProtocolVersion var4 = var1.server.getServerVersion();
      if (!var4.isEqualOrEarlierVersionOf(var1.serverContext.getClientVersion())) {
         throw new TlsFatalAlert((short)80);
      } else {
         var1.serverContext.setServerVersion(var4);
         TlsUtils.writeVersion(var1.serverContext.getServerVersion(), var3);
         var3.write(var2.getServerRandom());
         TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, var3);
         int var8 = var1.server.getSelectedCipherSuite();
         if (Arrays.contains(var1.offeredCipherSuites, var8) && var8 != 0 && !CipherSuite.isSCSV(var8) && TlsUtils.isValidCipherSuiteForVersion(var8, var1.serverContext.getServerVersion())) {
            validateSelectedCipherSuite(var8, (short)80);
            var2.cipherSuite = var8;
            short var5 = var1.server.getSelectedCompressionMethod();
            if (!Arrays.contains(var1.offeredCompressionMethods, var5)) {
               throw new TlsFatalAlert((short)80);
            } else {
               var2.compressionAlgorithm = var5;
               TlsUtils.writeUint16(var8, var3);
               TlsUtils.writeUint8((short)var5, var3);
               var1.serverExtensions = var1.server.getServerExtensions();
               if (var1.secure_renegotiation) {
                  byte[] var6 = TlsUtils.getExtensionData(var1.serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
                  boolean var7 = null == var6;
                  if (var7) {
                     var1.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(var1.serverExtensions);
                     var1.serverExtensions.put(TlsProtocol.EXT_RenegotiationInfo, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
                  }
               }

               if (var2.extendedMasterSecret) {
                  var1.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(var1.serverExtensions);
                  TlsExtensionsUtils.addExtendedMasterSecretExtension(var1.serverExtensions);
               }

               if (var1.serverExtensions != null) {
                  var2.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(var1.serverExtensions);
                  var2.maxFragmentLength = evaluateMaxFragmentLengthExtension(var1.resumedSession, var1.clientExtensions, var1.serverExtensions, (short)80);
                  var2.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(var1.serverExtensions);
                  var1.allowCertificateStatus = !var1.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(var1.serverExtensions, TlsExtensionsUtils.EXT_status_request, (short)80);
                  var1.expectSessionTicket = !var1.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(var1.serverExtensions, TlsProtocol.EXT_SessionTicket, (short)80);
                  TlsProtocol.writeExtensions(var3, var1.serverExtensions);
               }

               var2.prfAlgorithm = TlsProtocol.getPRFAlgorithm(var1.serverContext, var2.getCipherSuite());
               var2.verifyDataLength = 12;
               return var3.toByteArray();
            }
         } else {
            throw new TlsFatalAlert((short)80);
         }
      }
   }

   protected void invalidateSession(ServerHandshakeState var1) {
      if (var1.sessionParameters != null) {
         var1.sessionParameters.clear();
         var1.sessionParameters = null;
      }

      if (var1.tlsSession != null) {
         var1.tlsSession.invalidate();
         var1.tlsSession = null;
      }

   }

   protected void notifyClientCertificate(ServerHandshakeState var1, Certificate var2) throws IOException {
      if (var1.certificateRequest == null) {
         throw new IllegalStateException();
      } else if (var1.clientCertificate != null) {
         throw new TlsFatalAlert((short)10);
      } else {
         var1.clientCertificate = var2;
         if (var2.isEmpty()) {
            var1.keyExchange.skipClientCredentials();
         } else {
            var1.clientCertificateType = TlsUtils.getClientCertificateType(var2, var1.serverCredentials.getCertificate());
            var1.keyExchange.processClientCertificate(var2);
         }

         var1.server.notifyClientCertificate(var2);
      }
   }

   protected void processClientCertificate(ServerHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      Certificate var4 = Certificate.parse(var3);
      TlsProtocol.assertEmpty(var3);
      this.notifyClientCertificate(var1, var4);
   }

   protected void processCertificateVerify(ServerHandshakeState var1, byte[] var2, TlsHandshakeHash var3) throws IOException {
      if (var1.certificateRequest == null) {
         throw new IllegalStateException();
      } else {
         ByteArrayInputStream var4 = new ByteArrayInputStream(var2);
         TlsServerContextImpl var5 = var1.serverContext;
         DigitallySigned var6 = DigitallySigned.parse(var5, var4);
         TlsProtocol.assertEmpty(var4);

         try {
            SignatureAndHashAlgorithm var7 = var6.getAlgorithm();
            byte[] var8;
            if (TlsUtils.isTLSv12((TlsContext)var5)) {
               TlsUtils.verifySupportedSignatureAlgorithm(var1.certificateRequest.getSupportedSignatureAlgorithms(), var7);
               var8 = var3.getFinalHash(var7.getHash());
            } else {
               var8 = var5.getSecurityParameters().getSessionHash();
            }

            org.python.bouncycastle.asn1.x509.Certificate var9 = var1.clientCertificate.getCertificateAt(0);
            SubjectPublicKeyInfo var10 = var9.getSubjectPublicKeyInfo();
            AsymmetricKeyParameter var11 = PublicKeyFactory.createKey(var10);
            TlsSigner var12 = TlsUtils.createTlsSigner(var1.clientCertificateType);
            var12.init(var5);
            if (!var12.verifyRawSignature(var7, var6.getSignature(), var11, var8)) {
               throw new TlsFatalAlert((short)51);
            }
         } catch (TlsFatalAlert var13) {
            throw var13;
         } catch (Exception var14) {
            throw new TlsFatalAlert((short)51, var14);
         }
      }
   }

   protected void processClientHello(ServerHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      ProtocolVersion var4 = TlsUtils.readVersion(var3);
      if (!var4.isDTLS()) {
         throw new TlsFatalAlert((short)47);
      } else {
         byte[] var5 = TlsUtils.readFully(32, var3);
         byte[] var6 = TlsUtils.readOpaque8(var3);
         if (var6.length > 32) {
            throw new TlsFatalAlert((short)47);
         } else {
            byte[] var7 = TlsUtils.readOpaque8(var3);
            int var8 = TlsUtils.readUint16(var3);
            if (var8 >= 2 && (var8 & 1) == 0) {
               var1.offeredCipherSuites = TlsUtils.readUint16Array(var8 / 2, var3);
               short var9 = TlsUtils.readUint8(var3);
               if (var9 < 1) {
                  throw new TlsFatalAlert((short)47);
               } else {
                  var1.offeredCompressionMethods = TlsUtils.readUint8Array(var9, var3);
                  var1.clientExtensions = TlsProtocol.readExtensions(var3);
                  TlsServerContextImpl var10 = var1.serverContext;
                  SecurityParameters var11 = var10.getSecurityParameters();
                  var11.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(var1.clientExtensions);
                  var10.setClientVersion(var4);
                  var1.server.notifyClientVersion(var4);
                  var1.server.notifyFallback(Arrays.contains((int[])var1.offeredCipherSuites, (int)22016));
                  var11.clientRandom = var5;
                  var1.server.notifyOfferedCipherSuites(var1.offeredCipherSuites);
                  var1.server.notifyOfferedCompressionMethods(var1.offeredCompressionMethods);
                  if (Arrays.contains((int[])var1.offeredCipherSuites, (int)255)) {
                     var1.secure_renegotiation = true;
                  }

                  byte[] var12 = TlsUtils.getExtensionData(var1.clientExtensions, TlsProtocol.EXT_RenegotiationInfo);
                  if (var12 != null) {
                     var1.secure_renegotiation = true;
                     if (!Arrays.constantTimeAreEqual(var12, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                        throw new TlsFatalAlert((short)40);
                     }
                  }

                  var1.server.notifySecureRenegotiation(var1.secure_renegotiation);
                  if (var1.clientExtensions != null) {
                     TlsExtensionsUtils.getPaddingExtension(var1.clientExtensions);
                     var1.server.processClientExtensions(var1.clientExtensions);
                  }

               }
            } else {
               throw new TlsFatalAlert((short)50);
            }
         }
      }
   }

   protected void processClientKeyExchange(ServerHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      var1.keyExchange.processClientKeyExchange(var3);
      TlsProtocol.assertEmpty(var3);
   }

   protected void processClientSupplementalData(ServerHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      Vector var4 = TlsProtocol.readSupplementalDataMessage(var3);
      var1.server.processClientSupplementalData(var4);
   }

   protected boolean expectCertificateVerifyMessage(ServerHandshakeState var1) {
      return var1.clientCertificateType >= 0 && TlsUtils.hasSigningCapability(var1.clientCertificateType);
   }

   protected static class ServerHandshakeState {
      TlsServer server = null;
      TlsServerContextImpl serverContext = null;
      TlsSession tlsSession = null;
      SessionParameters sessionParameters = null;
      SessionParameters.Builder sessionParametersBuilder = null;
      int[] offeredCipherSuites = null;
      short[] offeredCompressionMethods = null;
      Hashtable clientExtensions = null;
      Hashtable serverExtensions = null;
      boolean resumedSession = false;
      boolean secure_renegotiation = false;
      boolean allowCertificateStatus = false;
      boolean expectSessionTicket = false;
      TlsKeyExchange keyExchange = null;
      TlsCredentials serverCredentials = null;
      CertificateRequest certificateRequest = null;
      short clientCertificateType = -1;
      Certificate clientCertificate = null;
   }
}
