package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.util.Arrays;

public class DTLSClientProtocol extends DTLSProtocol {
   public DTLSClientProtocol(SecureRandom var1) {
      super(var1);
   }

   public DTLSTransport connect(TlsClient var1, DatagramTransport var2) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("'client' cannot be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("'transport' cannot be null");
      } else {
         SecurityParameters var3 = new SecurityParameters();
         var3.entity = 1;
         ClientHandshakeState var4 = new ClientHandshakeState();
         var4.client = var1;
         var4.clientContext = new TlsClientContextImpl(this.secureRandom, var3);
         var3.clientRandom = TlsProtocol.createRandomBlock(var1.shouldUseGMTUnixTime(), var4.clientContext.getNonceRandomGenerator());
         var1.init(var4.clientContext);
         DTLSRecordLayer var5 = new DTLSRecordLayer(var2, var4.clientContext, var1, (short)22);
         TlsSession var6 = var4.client.getSessionToResume();
         if (var6 != null && var6.isResumable()) {
            SessionParameters var7 = var6.exportSessionParameters();
            if (var7 != null) {
               var4.tlsSession = var6;
               var4.sessionParameters = var7;
            }
         }

         DTLSTransport var17;
         try {
            var17 = this.clientHandshake(var4, var5);
         } catch (TlsFatalAlert var13) {
            this.abortClientHandshake(var4, var5, var13.getAlertDescription());
            throw var13;
         } catch (IOException var14) {
            this.abortClientHandshake(var4, var5, (short)80);
            throw var14;
         } catch (RuntimeException var15) {
            this.abortClientHandshake(var4, var5, (short)80);
            throw new TlsFatalAlert((short)80, var15);
         } finally {
            var3.clear();
         }

         return var17;
      }
   }

   protected void abortClientHandshake(ClientHandshakeState var1, DTLSRecordLayer var2, short var3) {
      var2.fail(var3);
      this.invalidateSession(var1);
   }

   protected DTLSTransport clientHandshake(ClientHandshakeState var1, DTLSRecordLayer var2) throws IOException {
      SecurityParameters var3 = var1.clientContext.getSecurityParameters();
      DTLSReliableHandshake var4 = new DTLSReliableHandshake(var1.clientContext, var2);
      byte[] var5 = this.generateClientHello(var1, var1.client);
      var2.setWriteVersion(ProtocolVersion.DTLSv10);
      var4.sendMessage((short)1, var5);

      DTLSReliableHandshake.Message var6;
      ProtocolVersion var7;
      byte[] var9;
      byte[] var10;
      for(var6 = var4.receiveMessage(); var6.getType() == 3; var6 = var4.receiveMessage()) {
         var7 = var2.getReadVersion();
         ProtocolVersion var8 = var1.clientContext.getClientVersion();
         if (!var7.isEqualOrEarlierVersionOf(var8)) {
            throw new TlsFatalAlert((short)47);
         }

         var2.setReadVersion((ProtocolVersion)null);
         var9 = this.processHelloVerifyRequest(var1, var6.getBody());
         var10 = patchClientHelloWithCookie(var5, var9);
         var4.resetHandshakeMessagesDigest();
         var4.sendMessage((short)1, var10);
      }

      if (var6.getType() == 2) {
         var7 = var2.getReadVersion();
         this.reportServerVersion(var1, var7);
         var2.setWriteVersion(var7);
         this.processServerHello(var1, var6.getBody());
         var4.notifyHelloComplete();
         applyMaxFragmentLengthExtension(var2, var3.maxFragmentLength);
         if (var1.resumedSession) {
            var3.masterSecret = Arrays.clone(var1.sessionParameters.getMasterSecret());
            var2.initPendingEpoch(var1.client.getCipher());
            byte[] var19 = TlsUtils.calculateVerifyData(var1.clientContext, "server finished", TlsProtocol.getCurrentPRFHash(var1.clientContext, var4.getHandshakeHash(), (byte[])null));
            this.processFinished(var4.receiveMessageBody((short)20), var19);
            byte[] var20 = TlsUtils.calculateVerifyData(var1.clientContext, "client finished", TlsProtocol.getCurrentPRFHash(var1.clientContext, var4.getHandshakeHash(), (byte[])null));
            var4.sendMessage((short)20, var20);
            var4.finish();
            var1.clientContext.setResumableSession(var1.tlsSession);
            var1.client.notifyHandshakeComplete();
            return new DTLSTransport(var2);
         } else {
            this.invalidateSession(var1);
            if (var1.selectedSessionID.length > 0) {
               var1.tlsSession = new TlsSessionImpl(var1.selectedSessionID, (SessionParameters)null);
            }

            var6 = var4.receiveMessage();
            if (var6.getType() == 23) {
               this.processServerSupplementalData(var1, var6.getBody());
               var6 = var4.receiveMessage();
            } else {
               var1.client.processServerSupplementalData((Vector)null);
            }

            var1.keyExchange = var1.client.getKeyExchange();
            var1.keyExchange.init(var1.clientContext);
            Certificate var17 = null;
            if (var6.getType() == 11) {
               var17 = this.processServerCertificate(var1, var6.getBody());
               var6 = var4.receiveMessage();
            } else {
               var1.keyExchange.skipServerCredentials();
            }

            if (var17 == null || var17.isEmpty()) {
               var1.allowCertificateStatus = false;
            }

            if (var6.getType() == 22) {
               this.processCertificateStatus(var1, var6.getBody());
               var6 = var4.receiveMessage();
            }

            if (var6.getType() == 12) {
               this.processServerKeyExchange(var1, var6.getBody());
               var6 = var4.receiveMessage();
            } else {
               var1.keyExchange.skipServerKeyExchange();
            }

            if (var6.getType() == 13) {
               this.processCertificateRequest(var1, var6.getBody());
               TlsUtils.trackHashAlgorithms(var4.getHandshakeHash(), var1.certificateRequest.getSupportedSignatureAlgorithms());
               var6 = var4.receiveMessage();
            }

            if (var6.getType() == 14) {
               if (var6.getBody().length != 0) {
                  throw new TlsFatalAlert((short)50);
               } else {
                  var4.getHandshakeHash().sealHashAlgorithms();
                  Vector var18 = var1.client.getClientSupplementalData();
                  if (var18 != null) {
                     var9 = generateSupplementalData(var18);
                     var4.sendMessage((short)23, var9);
                  }

                  if (var1.certificateRequest != null) {
                     var1.clientCredentials = var1.authentication.getClientCredentials(var1.certificateRequest);
                     Certificate var21 = null;
                     if (var1.clientCredentials != null) {
                        var21 = var1.clientCredentials.getCertificate();
                     }

                     if (var21 == null) {
                        var21 = Certificate.EMPTY_CHAIN;
                     }

                     var10 = generateCertificate(var21);
                     var4.sendMessage((short)11, var10);
                  }

                  if (var1.clientCredentials != null) {
                     var1.keyExchange.processClientCredentials(var1.clientCredentials);
                  } else {
                     var1.keyExchange.skipClientCredentials();
                  }

                  var9 = this.generateClientKeyExchange(var1);
                  var4.sendMessage((short)16, var9);
                  TlsHandshakeHash var22 = var4.prepareToFinish();
                  var3.sessionHash = TlsProtocol.getCurrentPRFHash(var1.clientContext, var22, (byte[])null);
                  TlsProtocol.establishMasterSecret(var1.clientContext, var1.keyExchange);
                  var2.initPendingEpoch(var1.client.getCipher());
                  if (var1.clientCredentials != null && var1.clientCredentials instanceof TlsSignerCredentials) {
                     TlsSignerCredentials var11 = (TlsSignerCredentials)var1.clientCredentials;
                     SignatureAndHashAlgorithm var12 = TlsUtils.getSignatureAndHashAlgorithm(var1.clientContext, var11);
                     byte[] var13;
                     if (var12 == null) {
                        var13 = var3.getSessionHash();
                     } else {
                        var13 = var22.getFinalHash(var12.getHash());
                     }

                     byte[] var14 = var11.generateCertificateSignature(var13);
                     DigitallySigned var15 = new DigitallySigned(var12, var14);
                     byte[] var16 = this.generateCertificateVerify(var1, var15);
                     var4.sendMessage((short)15, var16);
                  }

                  byte[] var23 = TlsUtils.calculateVerifyData(var1.clientContext, "client finished", TlsProtocol.getCurrentPRFHash(var1.clientContext, var4.getHandshakeHash(), (byte[])null));
                  var4.sendMessage((short)20, var23);
                  if (var1.expectSessionTicket) {
                     var6 = var4.receiveMessage();
                     if (var6.getType() != 4) {
                        throw new TlsFatalAlert((short)10);
                     }

                     this.processNewSessionTicket(var1, var6.getBody());
                  }

                  byte[] var24 = TlsUtils.calculateVerifyData(var1.clientContext, "server finished", TlsProtocol.getCurrentPRFHash(var1.clientContext, var4.getHandshakeHash(), (byte[])null));
                  this.processFinished(var4.receiveMessageBody((short)20), var24);
                  var4.finish();
                  if (var1.tlsSession != null) {
                     var1.sessionParameters = (new SessionParameters.Builder()).setCipherSuite(var3.getCipherSuite()).setCompressionAlgorithm(var3.getCompressionAlgorithm()).setMasterSecret(var3.getMasterSecret()).setPeerCertificate(var17).setPSKIdentity(var3.getPSKIdentity()).setSRPIdentity(var3.getSRPIdentity()).setServerExtensions(var1.serverExtensions).build();
                     var1.tlsSession = TlsUtils.importSession(var1.tlsSession.getSessionID(), var1.sessionParameters);
                     var1.clientContext.setResumableSession(var1.tlsSession);
                  }

                  var1.client.notifyHandshakeComplete();
                  return new DTLSTransport(var2);
               }
            } else {
               throw new TlsFatalAlert((short)10);
            }
         }
      } else {
         throw new TlsFatalAlert((short)10);
      }
   }

   protected byte[] generateCertificateVerify(ClientHandshakeState var1, DigitallySigned var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      var2.encode(var3);
      return var3.toByteArray();
   }

   protected byte[] generateClientHello(ClientHandshakeState var1, TlsClient var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      ProtocolVersion var4 = var2.getClientVersion();
      if (!var4.isDTLS()) {
         throw new TlsFatalAlert((short)80);
      } else {
         TlsClientContextImpl var5 = var1.clientContext;
         var5.setClientVersion(var4);
         TlsUtils.writeVersion(var4, var3);
         SecurityParameters var6 = var5.getSecurityParameters();
         var3.write(var6.getClientRandom());
         byte[] var7 = TlsUtils.EMPTY_BYTES;
         if (var1.tlsSession != null) {
            var7 = var1.tlsSession.getSessionID();
            if (var7 == null || var7.length > 32) {
               var7 = TlsUtils.EMPTY_BYTES;
            }
         }

         TlsUtils.writeOpaque8(var7, var3);
         TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, var3);
         boolean var8 = var2.isFallback();
         var1.offeredCipherSuites = var2.getCipherSuites();
         var1.clientExtensions = var2.getClientExtensions();
         byte[] var9 = TlsUtils.getExtensionData(var1.clientExtensions, TlsProtocol.EXT_RenegotiationInfo);
         boolean var10 = null == var9;
         boolean var11 = !Arrays.contains((int[])var1.offeredCipherSuites, (int)255);
         if (var10 && var11) {
            var1.offeredCipherSuites = Arrays.append((int[])var1.offeredCipherSuites, (int)255);
         }

         if (var8 && !Arrays.contains((int[])var1.offeredCipherSuites, (int)22016)) {
            var1.offeredCipherSuites = Arrays.append((int[])var1.offeredCipherSuites, (int)22016);
         }

         TlsUtils.writeUint16ArrayWithUint16Length(var1.offeredCipherSuites, var3);
         var1.offeredCompressionMethods = new short[]{0};
         TlsUtils.writeUint8ArrayWithUint8Length(var1.offeredCompressionMethods, var3);
         if (var1.clientExtensions != null) {
            TlsProtocol.writeExtensions(var3, var1.clientExtensions);
         }

         return var3.toByteArray();
      }
   }

   protected byte[] generateClientKeyExchange(ClientHandshakeState var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      var1.keyExchange.generateClientKeyExchange(var2);
      return var2.toByteArray();
   }

   protected void invalidateSession(ClientHandshakeState var1) {
      if (var1.sessionParameters != null) {
         var1.sessionParameters.clear();
         var1.sessionParameters = null;
      }

      if (var1.tlsSession != null) {
         var1.tlsSession.invalidate();
         var1.tlsSession = null;
      }

   }

   protected void processCertificateRequest(ClientHandshakeState var1, byte[] var2) throws IOException {
      if (var1.authentication == null) {
         throw new TlsFatalAlert((short)40);
      } else {
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         var1.certificateRequest = CertificateRequest.parse(var1.clientContext, var3);
         TlsProtocol.assertEmpty(var3);
         var1.keyExchange.validateCertificateRequest(var1.certificateRequest);
      }
   }

   protected void processCertificateStatus(ClientHandshakeState var1, byte[] var2) throws IOException {
      if (!var1.allowCertificateStatus) {
         throw new TlsFatalAlert((short)10);
      } else {
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         var1.certificateStatus = CertificateStatus.parse(var3);
         TlsProtocol.assertEmpty(var3);
      }
   }

   protected byte[] processHelloVerifyRequest(ClientHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      ProtocolVersion var4 = TlsUtils.readVersion(var3);
      byte[] var5 = TlsUtils.readOpaque8(var3);
      TlsProtocol.assertEmpty(var3);
      if (!var4.isEqualOrEarlierVersionOf(var1.clientContext.getClientVersion())) {
         throw new TlsFatalAlert((short)47);
      } else if (!ProtocolVersion.DTLSv12.isEqualOrEarlierVersionOf(var4) && var5.length > 32) {
         throw new TlsFatalAlert((short)47);
      } else {
         return var5;
      }
   }

   protected void processNewSessionTicket(ClientHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      NewSessionTicket var4 = NewSessionTicket.parse(var3);
      TlsProtocol.assertEmpty(var3);
      var1.client.notifyNewSessionTicket(var4);
   }

   protected Certificate processServerCertificate(ClientHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      Certificate var4 = Certificate.parse(var3);
      TlsProtocol.assertEmpty(var3);
      var1.keyExchange.processServerCertificate(var4);
      var1.authentication = var1.client.getAuthentication();
      var1.authentication.notifyServerCertificate(var4);
      return var4;
   }

   protected void processServerHello(ClientHandshakeState var1, byte[] var2) throws IOException {
      SecurityParameters var3 = var1.clientContext.getSecurityParameters();
      ByteArrayInputStream var4 = new ByteArrayInputStream(var2);
      ProtocolVersion var5 = TlsUtils.readVersion(var4);
      this.reportServerVersion(var1, var5);
      var3.serverRandom = TlsUtils.readFully(32, var4);
      var1.selectedSessionID = TlsUtils.readOpaque8(var4);
      if (var1.selectedSessionID.length > 32) {
         throw new TlsFatalAlert((short)47);
      } else {
         var1.client.notifySessionID(var1.selectedSessionID);
         var1.resumedSession = var1.selectedSessionID.length > 0 && var1.tlsSession != null && Arrays.areEqual(var1.selectedSessionID, var1.tlsSession.getSessionID());
         int var10 = TlsUtils.readUint16(var4);
         if (Arrays.contains(var1.offeredCipherSuites, var10) && var10 != 0 && !CipherSuite.isSCSV(var10) && TlsUtils.isValidCipherSuiteForVersion(var10, var1.clientContext.getServerVersion())) {
            validateSelectedCipherSuite(var10, (short)47);
            var1.client.notifySelectedCipherSuite(var10);
            short var6 = TlsUtils.readUint8(var4);
            if (!Arrays.contains(var1.offeredCompressionMethods, var6)) {
               throw new TlsFatalAlert((short)47);
            } else {
               var1.client.notifySelectedCompressionMethod(var6);
               var1.serverExtensions = TlsProtocol.readExtensions(var4);
               if (var1.serverExtensions != null) {
                  Enumeration var7 = var1.serverExtensions.keys();

                  while(var7.hasMoreElements()) {
                     Integer var8 = (Integer)var7.nextElement();
                     if (!var8.equals(TlsProtocol.EXT_RenegotiationInfo)) {
                        if (null == TlsUtils.getExtensionData(var1.clientExtensions, var8)) {
                           throw new TlsFatalAlert((short)110);
                        }

                        if (var1.resumedSession) {
                        }
                     }
                  }
               }

               byte[] var11 = TlsUtils.getExtensionData(var1.serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
               if (var11 != null) {
                  var1.secure_renegotiation = true;
                  if (!Arrays.constantTimeAreEqual(var11, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                     throw new TlsFatalAlert((short)40);
                  }
               }

               var1.client.notifySecureRenegotiation(var1.secure_renegotiation);
               Hashtable var12 = var1.clientExtensions;
               Hashtable var13 = var1.serverExtensions;
               if (var1.resumedSession) {
                  if (var10 != var1.sessionParameters.getCipherSuite() || var6 != var1.sessionParameters.getCompressionAlgorithm()) {
                     throw new TlsFatalAlert((short)47);
                  }

                  var12 = null;
                  var13 = var1.sessionParameters.readServerExtensions();
               }

               var3.cipherSuite = var10;
               var3.compressionAlgorithm = var6;
               if (var13 != null) {
                  boolean var9 = TlsExtensionsUtils.hasEncryptThenMACExtension(var13);
                  if (var9 && !TlsUtils.isBlockCipherSuite(var3.getCipherSuite())) {
                     throw new TlsFatalAlert((short)47);
                  }

                  var3.encryptThenMAC = var9;
                  var3.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(var13);
                  var3.maxFragmentLength = evaluateMaxFragmentLengthExtension(var1.resumedSession, var12, var13, (short)47);
                  var3.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(var13);
                  var1.allowCertificateStatus = !var1.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(var13, TlsExtensionsUtils.EXT_status_request, (short)47);
                  var1.expectSessionTicket = !var1.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(var13, TlsProtocol.EXT_SessionTicket, (short)47);
               }

               if (var12 != null) {
                  var1.client.processServerExtensions(var13);
               }

               var3.prfAlgorithm = TlsProtocol.getPRFAlgorithm(var1.clientContext, var3.getCipherSuite());
               var3.verifyDataLength = 12;
            }
         } else {
            throw new TlsFatalAlert((short)47);
         }
      }
   }

   protected void processServerKeyExchange(ClientHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      var1.keyExchange.processServerKeyExchange(var3);
      TlsProtocol.assertEmpty(var3);
   }

   protected void processServerSupplementalData(ClientHandshakeState var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      Vector var4 = TlsProtocol.readSupplementalDataMessage(var3);
      var1.client.processServerSupplementalData(var4);
   }

   protected void reportServerVersion(ClientHandshakeState var1, ProtocolVersion var2) throws IOException {
      TlsClientContextImpl var3 = var1.clientContext;
      ProtocolVersion var4 = var3.getServerVersion();
      if (null == var4) {
         var3.setServerVersion(var2);
         var1.client.notifyServerVersion(var2);
      } else if (!var4.equals(var2)) {
         throw new TlsFatalAlert((short)47);
      }

   }

   protected static byte[] patchClientHelloWithCookie(byte[] var0, byte[] var1) throws IOException {
      byte var2 = 34;
      short var3 = TlsUtils.readUint8(var0, var2);
      int var4 = var2 + 1 + var3;
      int var5 = var4 + 1;
      byte[] var6 = new byte[var0.length + var1.length];
      System.arraycopy(var0, 0, var6, 0, var4);
      TlsUtils.checkUint8(var1.length);
      TlsUtils.writeUint8(var1.length, var6, var4);
      System.arraycopy(var1, 0, var6, var5, var1.length);
      System.arraycopy(var0, var5, var6, var5 + var1.length, var0.length - var5);
      return var6;
   }

   protected static class ClientHandshakeState {
      TlsClient client = null;
      TlsClientContextImpl clientContext = null;
      TlsSession tlsSession = null;
      SessionParameters sessionParameters = null;
      SessionParameters.Builder sessionParametersBuilder = null;
      int[] offeredCipherSuites = null;
      short[] offeredCompressionMethods = null;
      Hashtable clientExtensions = null;
      Hashtable serverExtensions = null;
      byte[] selectedSessionID = null;
      boolean resumedSession = false;
      boolean secure_renegotiation = false;
      boolean allowCertificateStatus = false;
      boolean expectSessionTicket = false;
      TlsKeyExchange keyExchange = null;
      TlsAuthentication authentication = null;
      CertificateStatus certificateStatus = null;
      CertificateRequest certificateRequest = null;
      TlsCredentials clientCredentials = null;
   }
}
