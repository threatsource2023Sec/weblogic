package com.trilead.ssh2;

import com.trilead.ssh2.crypto.Base64;
import com.trilead.ssh2.crypto.digest.Digest;
import com.trilead.ssh2.crypto.digest.HMAC;
import com.trilead.ssh2.crypto.digest.MD5;
import com.trilead.ssh2.crypto.digest.SHA1;
import com.trilead.ssh2.signature.DSAPublicKey;
import com.trilead.ssh2.signature.DSASHA1Verify;
import com.trilead.ssh2.signature.RSAPublicKey;
import com.trilead.ssh2.signature.RSASHA1Verify;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class KnownHosts {
   public static final int HOSTKEY_IS_OK = 0;
   public static final int HOSTKEY_IS_NEW = 1;
   public static final int HOSTKEY_HAS_CHANGED = 2;
   private LinkedList publicKeys = new LinkedList();

   public KnownHosts() {
   }

   public KnownHosts(char[] knownHostsData) throws IOException {
      this.initialize(knownHostsData);
   }

   public KnownHosts(File knownHosts) throws IOException {
      this.initialize(knownHosts);
   }

   public void addHostkey(String[] hostnames, String serverHostKeyAlgorithm, byte[] serverHostKey) throws IOException {
      if (hostnames == null) {
         throw new IllegalArgumentException("hostnames may not be null");
      } else {
         if ("ssh-rsa".equals(serverHostKeyAlgorithm)) {
            RSAPublicKey rpk = RSASHA1Verify.decodeSSHRSAPublicKey(serverHostKey);
            synchronized(this.publicKeys) {
               this.publicKeys.add(new KnownHostsEntry(hostnames, rpk));
            }
         } else {
            if (!"ssh-dss".equals(serverHostKeyAlgorithm)) {
               throw new IOException("Unknwon host key type (" + serverHostKeyAlgorithm + ")");
            }

            DSAPublicKey dpk = DSASHA1Verify.decodeSSHDSAPublicKey(serverHostKey);
            synchronized(this.publicKeys) {
               this.publicKeys.add(new KnownHostsEntry(hostnames, dpk));
            }
         }

      }
   }

   public void addHostkeys(char[] knownHostsData) throws IOException {
      this.initialize(knownHostsData);
   }

   public void addHostkeys(File knownHosts) throws IOException {
      this.initialize(knownHosts);
   }

   public static final String createHashedHostname(String hostname) {
      SHA1 sha1 = new SHA1();
      byte[] salt = new byte[sha1.getDigestLength()];
      (new SecureRandom()).nextBytes(salt);
      byte[] hash = hmacSha1Hash(salt, hostname);
      String base64_salt = new String(Base64.encode(salt));
      String base64_hash = new String(Base64.encode(hash));
      return new String("|1|" + base64_salt + "|" + base64_hash);
   }

   private static final byte[] hmacSha1Hash(byte[] salt, String hostname) {
      SHA1 sha1 = new SHA1();
      if (salt.length != sha1.getDigestLength()) {
         throw new IllegalArgumentException("Salt has wrong length (" + salt.length + ")");
      } else {
         HMAC hmac = new HMAC(sha1, salt, salt.length);
         hmac.update(hostname.getBytes());
         byte[] dig = new byte[hmac.getDigestLength()];
         hmac.digest(dig);
         return dig;
      }
   }

   private final boolean checkHashed(String entry, String hostname) {
      if (!entry.startsWith("|1|")) {
         return false;
      } else {
         int delim_idx = entry.indexOf(124, 3);
         if (delim_idx == -1) {
            return false;
         } else {
            String salt_base64 = entry.substring(3, delim_idx);
            String hash_base64 = entry.substring(delim_idx + 1);
            byte[] salt = null;
            byte[] hash = null;

            byte[] salt;
            byte[] hash;
            try {
               salt = Base64.decode(salt_base64.toCharArray());
               hash = Base64.decode(hash_base64.toCharArray());
            } catch (IOException var11) {
               return false;
            }

            SHA1 sha1 = new SHA1();
            if (salt.length != sha1.getDigestLength()) {
               return false;
            } else {
               byte[] dig = hmacSha1Hash(salt, hostname);

               for(int i = 0; i < dig.length; ++i) {
                  if (dig[i] != hash[i]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private int checkKey(String remoteHostname, Object remoteKey) {
      int result = 1;
      synchronized(this.publicKeys) {
         Iterator i = this.publicKeys.iterator();

         while(i.hasNext()) {
            KnownHostsEntry ke = (KnownHostsEntry)i.next();
            if (this.hostnameMatches(ke.patterns, remoteHostname)) {
               boolean res = this.matchKeys(ke.key, remoteKey);
               if (res) {
                  return 0;
               }

               result = 2;
            }
         }

         return result;
      }
   }

   private Vector getAllKeys(String hostname) {
      Vector keys = new Vector();
      synchronized(this.publicKeys) {
         Iterator i = this.publicKeys.iterator();

         while(i.hasNext()) {
            KnownHostsEntry ke = (KnownHostsEntry)i.next();
            if (this.hostnameMatches(ke.patterns, hostname)) {
               keys.addElement(ke.key);
            }
         }

         return keys;
      }
   }

   public String[] getPreferredServerHostkeyAlgorithmOrder(String hostname) {
      String[] algos = this.recommendHostkeyAlgorithms(hostname);
      if (algos != null) {
         return algos;
      } else {
         InetAddress[] ipAdresses = null;

         try {
            ipAdresses = InetAddress.getAllByName(hostname);
         } catch (UnknownHostException var5) {
            return null;
         }

         for(int i = 0; i < ipAdresses.length; ++i) {
            algos = this.recommendHostkeyAlgorithms(ipAdresses[i].getHostAddress());
            if (algos != null) {
               return algos;
            }
         }

         return null;
      }
   }

   private final boolean hostnameMatches(String[] hostpatterns, String hostname) {
      boolean isMatch = false;
      boolean negate = false;
      hostname = hostname.toLowerCase();

      for(int k = 0; k < hostpatterns.length; ++k) {
         if (hostpatterns[k] != null) {
            String pattern = null;
            if (hostpatterns[k].length() > 0 && hostpatterns[k].charAt(0) == '!') {
               pattern = hostpatterns[k].substring(1);
               negate = true;
            } else {
               pattern = hostpatterns[k];
               negate = false;
            }

            if (!isMatch || negate) {
               if (pattern.charAt(0) == '|') {
                  if (this.checkHashed(pattern, hostname)) {
                     if (negate) {
                        return false;
                     }

                     isMatch = true;
                  }
               } else {
                  pattern = pattern.toLowerCase();
                  if (pattern.indexOf(63) == -1 && pattern.indexOf(42) == -1) {
                     if (pattern.compareTo(hostname) == 0) {
                        if (negate) {
                           return false;
                        }

                        isMatch = true;
                     }
                  } else if (this.pseudoRegex(pattern.toCharArray(), 0, hostname.toCharArray(), 0)) {
                     if (negate) {
                        return false;
                     }

                     isMatch = true;
                  }
               }
            }
         }
      }

      return isMatch;
   }

   private void initialize(char[] knownHostsData) throws IOException {
      BufferedReader br = new BufferedReader(new CharArrayReader(knownHostsData));

      while(true) {
         String[] arr;
         do {
            do {
               String line;
               do {
                  line = br.readLine();
                  if (line == null) {
                     return;
                  }

                  line = line.trim();
               } while(line.startsWith("#"));

               arr = line.split(" ");
            } while(arr.length < 3);
         } while(arr[1].compareTo("ssh-rsa") != 0 && arr[1].compareTo("ssh-dss") != 0);

         String[] hostnames = arr[0].split(",");
         byte[] msg = Base64.decode(arr[2].toCharArray());
         this.addHostkey(hostnames, arr[1], msg);
      }
   }

   private void initialize(File knownHosts) throws IOException {
      char[] buff = new char[512];
      CharArrayWriter cw = new CharArrayWriter();
      knownHosts.createNewFile();
      FileReader fr = new FileReader(knownHosts);

      while(true) {
         int len = fr.read(buff);
         if (len < 0) {
            fr.close();
            this.initialize(cw.toCharArray());
            return;
         }

         cw.write(buff, 0, len);
      }
   }

   private final boolean matchKeys(Object key1, Object key2) {
      if (key1 instanceof RSAPublicKey && key2 instanceof RSAPublicKey) {
         RSAPublicKey savedRSAKey = (RSAPublicKey)key1;
         RSAPublicKey remoteRSAKey = (RSAPublicKey)key2;
         if (!savedRSAKey.getE().equals(remoteRSAKey.getE())) {
            return false;
         } else {
            return savedRSAKey.getN().equals(remoteRSAKey.getN());
         }
      } else if (key1 instanceof DSAPublicKey && key2 instanceof DSAPublicKey) {
         DSAPublicKey savedDSAKey = (DSAPublicKey)key1;
         DSAPublicKey remoteDSAKey = (DSAPublicKey)key2;
         if (!savedDSAKey.getG().equals(remoteDSAKey.getG())) {
            return false;
         } else if (!savedDSAKey.getP().equals(remoteDSAKey.getP())) {
            return false;
         } else if (!savedDSAKey.getQ().equals(remoteDSAKey.getQ())) {
            return false;
         } else {
            return savedDSAKey.getY().equals(remoteDSAKey.getY());
         }
      } else {
         return false;
      }
   }

   private final boolean pseudoRegex(char[] pattern, int i, char[] match, int j) {
      while(pattern.length != i) {
         if (pattern[i] == '*') {
            ++i;
            if (pattern.length == i) {
               return true;
            }

            if (pattern[i] != '*' && pattern[i] != '?') {
               do {
                  if (pattern[i] == match[j] && this.pseudoRegex(pattern, i + 1, match, j + 1)) {
                     return true;
                  }

                  ++j;
               } while(match.length != j);

               return false;
            }

            while(!this.pseudoRegex(pattern, i, match, j)) {
               ++j;
               if (match.length == j) {
                  return false;
               }
            }

            return true;
         }

         if (match.length == j) {
            return false;
         }

         if (pattern[i] != '?' && pattern[i] != match[j]) {
            return false;
         }

         ++i;
         ++j;
      }

      return match.length == j;
   }

   private String[] recommendHostkeyAlgorithms(String hostname) {
      String preferredAlgo = null;
      Vector keys = this.getAllKeys(hostname);

      for(int i = 0; i < keys.size(); ++i) {
         String thisAlgo = null;
         if (keys.elementAt(i) instanceof RSAPublicKey) {
            thisAlgo = "ssh-rsa";
         } else {
            if (!(keys.elementAt(i) instanceof DSAPublicKey)) {
               continue;
            }

            thisAlgo = "ssh-dss";
         }

         if (preferredAlgo != null && ((String)preferredAlgo).compareTo(thisAlgo) != 0) {
            return null;
         }
      }

      if (preferredAlgo == null) {
         return null;
      } else if (((String)preferredAlgo).equals("ssh-rsa")) {
         return new String[]{"ssh-rsa", "ssh-dss"};
      } else {
         return new String[]{"ssh-dss", "ssh-rsa"};
      }
   }

   public int verifyHostkey(String hostname, String serverHostKeyAlgorithm, byte[] serverHostKey) throws IOException {
      Object remoteKey = null;
      if ("ssh-rsa".equals(serverHostKeyAlgorithm)) {
         remoteKey = RSASHA1Verify.decodeSSHRSAPublicKey(serverHostKey);
      } else {
         if (!"ssh-dss".equals(serverHostKeyAlgorithm)) {
            throw new IllegalArgumentException("Unknown hostkey type " + serverHostKeyAlgorithm);
         }

         remoteKey = DSASHA1Verify.decodeSSHDSAPublicKey(serverHostKey);
      }

      int result = this.checkKey(hostname, remoteKey);
      if (result == 0) {
         return result;
      } else {
         InetAddress[] ipAdresses = null;

         try {
            ipAdresses = InetAddress.getAllByName(hostname);
         } catch (UnknownHostException var9) {
            return result;
         }

         for(int i = 0; i < ipAdresses.length; ++i) {
            int newresult = this.checkKey(ipAdresses[i].getHostAddress(), remoteKey);
            if (newresult == 0) {
               return newresult;
            }

            if (newresult == 2) {
               result = 2;
            }
         }

         return result;
      }
   }

   public static final void addHostkeyToFile(File knownHosts, String[] hostnames, String serverHostKeyAlgorithm, byte[] serverHostKey) throws IOException {
      if (hostnames != null && hostnames.length != 0) {
         if (serverHostKeyAlgorithm != null && serverHostKey != null) {
            CharArrayWriter writer = new CharArrayWriter();

            for(int i = 0; i < hostnames.length; ++i) {
               if (i != 0) {
                  writer.write(44);
               }

               writer.write(hostnames[i]);
            }

            writer.write(32);
            writer.write(serverHostKeyAlgorithm);
            writer.write(32);
            writer.write(Base64.encode(serverHostKey));
            writer.write("\n");
            char[] entry = writer.toCharArray();
            RandomAccessFile raf = new RandomAccessFile(knownHosts, "rw");
            long len = raf.length();
            if (len > 0L) {
               raf.seek(len - 1L);
               int last = raf.read();
               if (last != 10) {
                  raf.write(10);
               }
            }

            raf.write((new String(entry)).getBytes());
            raf.close();
         } else {
            throw new IllegalArgumentException();
         }
      } else {
         throw new IllegalArgumentException("Need at least one hostname specification");
      }
   }

   private static final byte[] rawFingerPrint(String type, String keyType, byte[] hostkey) {
      Digest dig = null;
      if ("md5".equals(type)) {
         dig = new MD5();
      } else {
         if (!"sha1".equals(type)) {
            throw new IllegalArgumentException("Unknown hash type " + type);
         }

         dig = new SHA1();
      }

      if (!"ssh-rsa".equals(keyType) && !"ssh-dss".equals(keyType)) {
         throw new IllegalArgumentException("Unknown key type " + keyType);
      } else if (hostkey == null) {
         throw new IllegalArgumentException("hostkey is null");
      } else {
         ((Digest)dig).update(hostkey);
         byte[] res = new byte[((Digest)dig).getDigestLength()];
         ((Digest)dig).digest(res);
         return res;
      }
   }

   private static final String rawToHexFingerprint(byte[] fingerprint) {
      char[] alpha = "0123456789abcdef".toCharArray();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < fingerprint.length; ++i) {
         if (i != 0) {
            sb.append(':');
         }

         int b = fingerprint[i] & 255;
         sb.append(alpha[b >> 4]);
         sb.append(alpha[b & 15]);
      }

      return sb.toString();
   }

   private static final String rawToBubblebabbleFingerprint(byte[] raw) {
      char[] v = "aeiouy".toCharArray();
      char[] c = "bcdfghklmnprstvzx".toCharArray();
      StringBuffer sb = new StringBuffer();
      int seed = 1;
      int rounds = raw.length / 2 + 1;
      sb.append('x');

      for(int i = 0; i < rounds; ++i) {
         if (i + 1 >= rounds && raw.length % 2 == 0) {
            sb.append(v[seed % 6]);
            sb.append('x');
            sb.append(v[seed / 6]);
         } else {
            sb.append(v[((raw[2 * i] >> 6 & 3) + seed) % 6]);
            sb.append(c[raw[2 * i] >> 2 & 15]);
            sb.append(v[((raw[2 * i] & 3) + seed / 6) % 6]);
            if (i + 1 < rounds) {
               sb.append(c[raw[2 * i + 1] >> 4 & 15]);
               sb.append('-');
               sb.append(c[raw[2 * i + 1] & 15]);
               seed = (seed * 5 + (raw[2 * i] & 255) * 7 + (raw[2 * i + 1] & 255)) % 36;
            }
         }
      }

      sb.append('x');
      return sb.toString();
   }

   public static final String createHexFingerprint(String keytype, byte[] publickey) {
      byte[] raw = rawFingerPrint("md5", keytype, publickey);
      return rawToHexFingerprint(raw);
   }

   public static final String createBubblebabbleFingerprint(String keytype, byte[] publickey) {
      byte[] raw = rawFingerPrint("sha1", keytype, publickey);
      return rawToBubblebabbleFingerprint(raw);
   }

   private class KnownHostsEntry {
      String[] patterns;
      Object key;

      KnownHostsEntry(String[] patterns, Object key) {
         this.patterns = patterns;
         this.key = key;
      }
   }
}
