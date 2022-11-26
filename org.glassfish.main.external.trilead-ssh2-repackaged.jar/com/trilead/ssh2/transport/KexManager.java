package com.trilead.ssh2.transport;

import com.trilead.ssh2.ConnectionInfo;
import com.trilead.ssh2.DHGexParameters;
import com.trilead.ssh2.ServerHostKeyVerifier;
import com.trilead.ssh2.crypto.CryptoWishList;
import com.trilead.ssh2.crypto.KeyMaterial;
import com.trilead.ssh2.crypto.cipher.BlockCipher;
import com.trilead.ssh2.crypto.cipher.BlockCipherFactory;
import com.trilead.ssh2.crypto.dh.DhExchange;
import com.trilead.ssh2.crypto.dh.DhGroupExchange;
import com.trilead.ssh2.crypto.digest.MAC;
import com.trilead.ssh2.log.Logger;
import com.trilead.ssh2.packets.PacketKexDHInit;
import com.trilead.ssh2.packets.PacketKexDHReply;
import com.trilead.ssh2.packets.PacketKexDhGexGroup;
import com.trilead.ssh2.packets.PacketKexDhGexInit;
import com.trilead.ssh2.packets.PacketKexDhGexReply;
import com.trilead.ssh2.packets.PacketKexDhGexRequest;
import com.trilead.ssh2.packets.PacketKexDhGexRequestOld;
import com.trilead.ssh2.packets.PacketKexInit;
import com.trilead.ssh2.packets.PacketNewKeys;
import com.trilead.ssh2.signature.DSAPublicKey;
import com.trilead.ssh2.signature.DSASHA1Verify;
import com.trilead.ssh2.signature.DSASignature;
import com.trilead.ssh2.signature.RSAPublicKey;
import com.trilead.ssh2.signature.RSASHA1Verify;
import com.trilead.ssh2.signature.RSASignature;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.SecureRandom;

public class KexManager {
   private static final Logger log;
   KexState kxs;
   int kexCount = 0;
   KeyMaterial km;
   byte[] sessionId;
   ClientServerHello csh;
   final Object accessLock = new Object();
   ConnectionInfo lastConnInfo = null;
   boolean connectionClosed = false;
   boolean ignore_next_kex_packet = false;
   final TransportManager tm;
   CryptoWishList nextKEXcryptoWishList;
   DHGexParameters nextKEXdhgexParameters;
   ServerHostKeyVerifier verifier;
   final String hostname;
   final int port;
   final SecureRandom rnd;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$transport$KexManager;

   public KexManager(TransportManager tm, ClientServerHello csh, CryptoWishList initialCwl, String hostname, int port, ServerHostKeyVerifier keyVerifier, SecureRandom rnd) {
      this.tm = tm;
      this.csh = csh;
      this.nextKEXcryptoWishList = initialCwl;
      this.nextKEXdhgexParameters = new DHGexParameters();
      this.hostname = hostname;
      this.port = port;
      this.verifier = keyVerifier;
      this.rnd = rnd;
   }

   public ConnectionInfo getOrWaitForConnectionInfo(int minKexCount) throws IOException {
      synchronized(this.accessLock) {
         while(this.lastConnInfo == null || this.lastConnInfo.keyExchangeCounter < minKexCount) {
            if (this.connectionClosed) {
               throw (IOException)(new IOException("Key exchange was not finished, connection is closed.")).initCause(this.tm.getReasonClosedCause());
            }

            try {
               this.accessLock.wait();
            } catch (InterruptedException var5) {
               throw new InterruptedIOException();
            }
         }

         return this.lastConnInfo;
      }
   }

   private String getFirstMatch(String[] client, String[] server) throws NegotiateException {
      if (client != null && server != null) {
         if (client.length == 0) {
            return null;
         } else {
            for(int i = 0; i < client.length; ++i) {
               for(int j = 0; j < server.length; ++j) {
                  if (client[i].equals(server[j])) {
                     return client[i];
                  }
               }
            }

            throw new NegotiateException();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private boolean compareFirstOfNameList(String[] a, String[] b) {
      if (a != null && b != null) {
         if (a.length == 0 && b.length == 0) {
            return true;
         } else {
            return a.length != 0 && b.length != 0 ? a[0].equals(b[0]) : false;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private boolean isGuessOK(KexParameters cpar, KexParameters spar) {
      if (cpar != null && spar != null) {
         if (!this.compareFirstOfNameList(cpar.kex_algorithms, spar.kex_algorithms)) {
            return false;
         } else {
            return this.compareFirstOfNameList(cpar.server_host_key_algorithms, spar.server_host_key_algorithms);
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private NegotiatedParameters mergeKexParameters(KexParameters client, KexParameters server) {
      NegotiatedParameters np = new NegotiatedParameters();

      try {
         np.kex_algo = this.getFirstMatch(client.kex_algorithms, server.kex_algorithms);
         log.log(20, "kex_algo=" + np.kex_algo);
         np.server_host_key_algo = this.getFirstMatch(client.server_host_key_algorithms, server.server_host_key_algorithms);
         log.log(20, "server_host_key_algo=" + np.server_host_key_algo);
         np.enc_algo_client_to_server = this.getFirstMatch(client.encryption_algorithms_client_to_server, server.encryption_algorithms_client_to_server);
         np.enc_algo_server_to_client = this.getFirstMatch(client.encryption_algorithms_server_to_client, server.encryption_algorithms_server_to_client);
         log.log(20, "enc_algo_client_to_server=" + np.enc_algo_client_to_server);
         log.log(20, "enc_algo_server_to_client=" + np.enc_algo_server_to_client);
         np.mac_algo_client_to_server = this.getFirstMatch(client.mac_algorithms_client_to_server, server.mac_algorithms_client_to_server);
         np.mac_algo_server_to_client = this.getFirstMatch(client.mac_algorithms_server_to_client, server.mac_algorithms_server_to_client);
         log.log(20, "mac_algo_client_to_server=" + np.mac_algo_client_to_server);
         log.log(20, "mac_algo_server_to_client=" + np.mac_algo_server_to_client);
         np.comp_algo_client_to_server = this.getFirstMatch(client.compression_algorithms_client_to_server, server.compression_algorithms_client_to_server);
         np.comp_algo_server_to_client = this.getFirstMatch(client.compression_algorithms_server_to_client, server.compression_algorithms_server_to_client);
         log.log(20, "comp_algo_client_to_server=" + np.comp_algo_client_to_server);
         log.log(20, "comp_algo_server_to_client=" + np.comp_algo_server_to_client);
      } catch (NegotiateException var7) {
         return null;
      }

      try {
         np.lang_client_to_server = this.getFirstMatch(client.languages_client_to_server, server.languages_client_to_server);
      } catch (NegotiateException var6) {
         np.lang_client_to_server = null;
      }

      try {
         np.lang_server_to_client = this.getFirstMatch(client.languages_server_to_client, server.languages_server_to_client);
      } catch (NegotiateException var5) {
         np.lang_server_to_client = null;
      }

      if (this.isGuessOK(client, server)) {
         np.guessOK = true;
      }

      return np;
   }

   public synchronized void initiateKEX(CryptoWishList cwl, DHGexParameters dhgex) throws IOException {
      this.nextKEXcryptoWishList = cwl;
      this.nextKEXdhgexParameters = dhgex;
      if (this.kxs == null) {
         this.kxs = new KexState();
         this.kxs.dhgexParameters = this.nextKEXdhgexParameters;
         PacketKexInit kp = new PacketKexInit(this.nextKEXcryptoWishList, this.rnd);
         this.kxs.localKEX = kp;
         this.tm.sendKexMessage(kp.getPayload());
      }

   }

   private boolean establishKeyMaterial() {
      try {
         int mac_cs_key_len = MAC.getKeyLen(this.kxs.np.mac_algo_client_to_server);
         int enc_cs_key_len = BlockCipherFactory.getKeySize(this.kxs.np.enc_algo_client_to_server);
         int enc_cs_block_len = BlockCipherFactory.getBlockSize(this.kxs.np.enc_algo_client_to_server);
         int mac_sc_key_len = MAC.getKeyLen(this.kxs.np.mac_algo_server_to_client);
         int enc_sc_key_len = BlockCipherFactory.getKeySize(this.kxs.np.enc_algo_server_to_client);
         int enc_sc_block_len = BlockCipherFactory.getBlockSize(this.kxs.np.enc_algo_server_to_client);
         this.km = KeyMaterial.create("SHA1", this.kxs.H, this.kxs.K, this.sessionId, enc_cs_key_len, enc_cs_block_len, mac_cs_key_len, enc_sc_key_len, enc_sc_block_len, mac_sc_key_len);
         return true;
      } catch (IllegalArgumentException var7) {
         return false;
      }
   }

   private void finishKex() throws IOException {
      if (this.sessionId == null) {
         this.sessionId = this.kxs.H;
      }

      this.establishKeyMaterial();
      PacketNewKeys ign = new PacketNewKeys();
      this.tm.sendKexMessage(ign.getPayload());

      BlockCipher cbc;
      MAC mac;
      try {
         cbc = BlockCipherFactory.createCipher(this.kxs.np.enc_algo_client_to_server, true, this.km.enc_key_client_to_server, this.km.initial_iv_client_to_server);
         mac = new MAC(this.kxs.np.mac_algo_client_to_server, this.km.integrity_key_client_to_server);
      } catch (IllegalArgumentException var5) {
         throw new IOException("Fatal error during MAC startup!");
      }

      this.tm.changeSendCipher(cbc, mac);
      this.tm.kexFinished();
   }

   public static final String[] getDefaultServerHostkeyAlgorithmList() {
      return new String[]{"ssh-rsa", "ssh-dss"};
   }

   public static final void checkServerHostkeyAlgorithmsList(String[] algos) {
      for(int i = 0; i < algos.length; ++i) {
         if (!"ssh-rsa".equals(algos[i]) && !"ssh-dss".equals(algos[i])) {
            throw new IllegalArgumentException("Unknown server host key algorithm '" + algos[i] + "'");
         }
      }

   }

   public static final String[] getDefaultKexAlgorithmList() {
      return new String[]{"diffie-hellman-group-exchange-sha1", "diffie-hellman-group14-sha1", "diffie-hellman-group1-sha1"};
   }

   public static final void checkKexAlgorithmList(String[] algos) {
      for(int i = 0; i < algos.length; ++i) {
         if (!"diffie-hellman-group-exchange-sha1".equals(algos[i]) && !"diffie-hellman-group14-sha1".equals(algos[i]) && !"diffie-hellman-group1-sha1".equals(algos[i])) {
            throw new IllegalArgumentException("Unknown kex algorithm '" + algos[i] + "'");
         }
      }

   }

   private boolean verifySignature(byte[] sig, byte[] hostkey) throws IOException {
      if (this.kxs.np.server_host_key_algo.equals("ssh-rsa")) {
         RSASignature rs = RSASHA1Verify.decodeSSHRSASignature(sig);
         RSAPublicKey rpk = RSASHA1Verify.decodeSSHRSAPublicKey(hostkey);
         log.log(50, "Verifying ssh-rsa signature");
         return RSASHA1Verify.verifySignature(this.kxs.H, rs, rpk);
      } else if (this.kxs.np.server_host_key_algo.equals("ssh-dss")) {
         DSASignature ds = DSASHA1Verify.decodeSSHDSASignature(sig);
         DSAPublicKey dpk = DSASHA1Verify.decodeSSHDSAPublicKey(hostkey);
         log.log(50, "Verifying ssh-dss signature");
         return DSASHA1Verify.verifySignature(this.kxs.H, ds, dpk);
      } else {
         throw new IOException("Unknown server host key algorithm '" + this.kxs.np.server_host_key_algo + "'");
      }
   }

   public synchronized void handleMessage(byte[] msg, int msglen) throws IOException {
      if (msg == null) {
         synchronized(this.accessLock) {
            this.connectionClosed = true;
            this.accessLock.notifyAll();
         }
      } else if (this.kxs == null && msg[0] != 20) {
         throw new IOException("Unexpected KEX message (type " + msg[0] + ")");
      } else if (this.ignore_next_kex_packet) {
         this.ignore_next_kex_packet = false;
      } else if (msg[0] == 20) {
         if (this.kxs != null && this.kxs.state != 0) {
            throw new IOException("Unexpected SSH_MSG_KEXINIT message during on-going kex exchange!");
         } else {
            PacketKexInit kip;
            if (this.kxs == null) {
               this.kxs = new KexState();
               this.kxs.dhgexParameters = this.nextKEXdhgexParameters;
               kip = new PacketKexInit(this.nextKEXcryptoWishList, this.rnd);
               this.kxs.localKEX = kip;
               this.tm.sendKexMessage(kip.getPayload());
            }

            kip = new PacketKexInit(msg, 0, msglen);
            this.kxs.remoteKEX = kip;
            this.kxs.np = this.mergeKexParameters(this.kxs.localKEX.getKexParameters(), this.kxs.remoteKEX.getKexParameters());
            if (this.kxs.np == null) {
               throw new IOException("Cannot negotiate, proposals do not match.");
            } else {
               if (this.kxs.remoteKEX.isFirst_kex_packet_follows() && !this.kxs.np.guessOK) {
                  this.ignore_next_kex_packet = true;
               }

               if (this.kxs.np.kex_algo.equals("diffie-hellman-group-exchange-sha1")) {
                  if (this.kxs.dhgexParameters.getMin_group_len() == 0) {
                     PacketKexDhGexRequestOld dhgexreq = new PacketKexDhGexRequestOld(this.kxs.dhgexParameters);
                     this.tm.sendKexMessage(dhgexreq.getPayload());
                  } else {
                     PacketKexDhGexRequest dhgexreq = new PacketKexDhGexRequest(this.kxs.dhgexParameters);
                     this.tm.sendKexMessage(dhgexreq.getPayload());
                  }

                  this.kxs.state = 1;
               } else if (!this.kxs.np.kex_algo.equals("diffie-hellman-group1-sha1") && !this.kxs.np.kex_algo.equals("diffie-hellman-group14-sha1")) {
                  throw new IllegalStateException("Unkown KEX method!");
               } else {
                  this.kxs.dhx = new DhExchange();
                  if (this.kxs.np.kex_algo.equals("diffie-hellman-group1-sha1")) {
                     this.kxs.dhx.init(1, this.rnd);
                  } else {
                     this.kxs.dhx.init(14, this.rnd);
                  }

                  PacketKexDHInit kp = new PacketKexDHInit(this.kxs.dhx.getE());
                  this.tm.sendKexMessage(kp.getPayload());
                  this.kxs.state = 1;
               }
            }
         }
      } else if (msg[0] == 21) {
         if (this.km == null) {
            throw new IOException("Peer sent SSH_MSG_NEWKEYS, but I have no key material ready!");
         } else {
            BlockCipher cbc;
            MAC mac;
            try {
               cbc = BlockCipherFactory.createCipher(this.kxs.np.enc_algo_server_to_client, false, this.km.enc_key_server_to_client, this.km.initial_iv_server_to_client);
               mac = new MAC(this.kxs.np.mac_algo_server_to_client, this.km.integrity_key_server_to_client);
            } catch (IllegalArgumentException var11) {
               throw new IOException("Fatal error during MAC startup!");
            }

            this.tm.changeRecvCipher(cbc, mac);
            ConnectionInfo sci = new ConnectionInfo();
            ++this.kexCount;
            sci.keyExchangeAlgorithm = this.kxs.np.kex_algo;
            sci.keyExchangeCounter = this.kexCount;
            sci.clientToServerCryptoAlgorithm = this.kxs.np.enc_algo_client_to_server;
            sci.serverToClientCryptoAlgorithm = this.kxs.np.enc_algo_server_to_client;
            sci.clientToServerMACAlgorithm = this.kxs.np.mac_algo_client_to_server;
            sci.serverToClientMACAlgorithm = this.kxs.np.mac_algo_server_to_client;
            sci.serverHostKeyAlgorithm = this.kxs.np.server_host_key_algo;
            sci.serverHostKey = this.kxs.hostkey;
            synchronized(this.accessLock) {
               this.lastConnInfo = sci;
               this.accessLock.notifyAll();
            }

            this.kxs = null;
         }
      } else if (this.kxs != null && this.kxs.state != 0) {
         boolean res;
         if (this.kxs.np.kex_algo.equals("diffie-hellman-group-exchange-sha1")) {
            if (this.kxs.state == 1) {
               PacketKexDhGexGroup dhgexgrp = new PacketKexDhGexGroup(msg, 0, msglen);
               this.kxs.dhgx = new DhGroupExchange(dhgexgrp.getP(), dhgexgrp.getG());
               this.kxs.dhgx.init(this.rnd);
               PacketKexDhGexInit dhgexinit = new PacketKexDhGexInit(this.kxs.dhgx.getE());
               this.tm.sendKexMessage(dhgexinit.getPayload());
               this.kxs.state = 2;
            } else if (this.kxs.state == 2) {
               PacketKexDhGexReply dhgexrpl = new PacketKexDhGexReply(msg, 0, msglen);
               this.kxs.hostkey = dhgexrpl.getHostKey();
               if (this.verifier != null) {
                  res = false;

                  try {
                     res = this.verifier.verifyServerHostKey(this.hostname, this.port, this.kxs.np.server_host_key_algo, this.kxs.hostkey);
                  } catch (Exception var13) {
                     throw (IOException)(new IOException("The server hostkey was not accepted by the verifier callback.")).initCause(var13);
                  }

                  if (!res) {
                     throw new IOException("The server hostkey was not accepted by the verifier callback");
                  }
               }

               this.kxs.dhgx.setF(dhgexrpl.getF());

               try {
                  this.kxs.H = this.kxs.dhgx.calculateH(this.csh.getClientString(), this.csh.getServerString(), this.kxs.localKEX.getPayload(), this.kxs.remoteKEX.getPayload(), dhgexrpl.getHostKey(), this.kxs.dhgexParameters);
               } catch (IllegalArgumentException var12) {
                  throw (IOException)(new IOException("KEX error.")).initCause(var12);
               }

               res = this.verifySignature(dhgexrpl.getSignature(), this.kxs.hostkey);
               if (!res) {
                  throw new IOException("Hostkey signature sent by remote is wrong!");
               } else {
                  this.kxs.K = this.kxs.dhgx.getK();
                  this.finishKex();
                  this.kxs.state = -1;
               }
            } else {
               throw new IllegalStateException("Illegal State in KEX Exchange!");
            }
         } else if ((this.kxs.np.kex_algo.equals("diffie-hellman-group1-sha1") || this.kxs.np.kex_algo.equals("diffie-hellman-group14-sha1")) && this.kxs.state == 1) {
            PacketKexDHReply dhr = new PacketKexDHReply(msg, 0, msglen);
            this.kxs.hostkey = dhr.getHostKey();
            if (this.verifier != null) {
               res = false;

               try {
                  res = this.verifier.verifyServerHostKey(this.hostname, this.port, this.kxs.np.server_host_key_algo, this.kxs.hostkey);
               } catch (Exception var15) {
                  throw (IOException)(new IOException("The server hostkey was not accepted by the verifier callback.")).initCause(var15);
               }

               if (!res) {
                  throw new IOException("The server hostkey was not accepted by the verifier callback");
               }
            }

            this.kxs.dhx.setF(dhr.getF());

            try {
               this.kxs.H = this.kxs.dhx.calculateH(this.csh.getClientString(), this.csh.getServerString(), this.kxs.localKEX.getPayload(), this.kxs.remoteKEX.getPayload(), dhr.getHostKey());
            } catch (IllegalArgumentException var14) {
               throw (IOException)(new IOException("KEX error.")).initCause(var14);
            }

            res = this.verifySignature(dhr.getSignature(), this.kxs.hostkey);
            if (!res) {
               throw new IOException("Hostkey signature sent by remote is wrong!");
            } else {
               this.kxs.K = this.kxs.dhx.getK();
               this.finishKex();
               this.kxs.state = -1;
            }
         } else {
            throw new IllegalStateException("Unkown KEX method! (" + this.kxs.np.kex_algo + ")");
         }
      } else {
         throw new IOException("Unexpected Kex submessage!");
      }
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      log = Logger.getLogger(class$com$trilead$ssh2$transport$KexManager == null ? (class$com$trilead$ssh2$transport$KexManager = class$("com.trilead.ssh2.transport.KexManager")) : class$com$trilead$ssh2$transport$KexManager);
   }
}
