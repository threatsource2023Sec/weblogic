package weblogic.coherence.service.internal.security;

import com.tangosol.net.ClusterPermission;
import com.tangosol.net.security.AccessController;
import com.tangosol.util.Base;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.security.auth.Subject;
import javax.security.auth.x500.X500Principal;
import javax.security.auth.x500.X500PrivateCredential;

public final class SecurityController implements AccessController {
   private static KeyStore keystore;
   private Map publicKeyMap = new ConcurrentHashMap();
   private static Signature SIGNATURE_ENGINE;

   private static void setSignature(Signature engine) {
      SIGNATURE_ENGINE = engine;
   }

   public SecurityController(String algorithm) {
      try {
         setSignature(Signature.getInstance(algorithm));
      } catch (Exception var3) {
         Base.log("Unsupported algorithm: " + algorithm);
      }

   }

   public void checkPermission(ClusterPermission permission, Subject subject) {
      if (subject == null) {
         throw new SecurityException("Null Subject");
      }
   }

   public Object decrypt(SignedObject so, Subject encryptorSubj, Subject decryptorSubj) throws ClassNotFoundException, IOException, GeneralSecurityException {
      PublicKey publicKey = (PublicKey)this.publicKeyMap.get(encryptorSubj);
      if (publicKey != null) {
         return this.decrypt(so, publicKey);
      } else {
         Set keys = null;
         if (decryptorSubj != null) {
            Set decryptorCreds = decryptorSubj.getPublicCredentials();
            if (decryptorCreds != null && this.equalsMostly(decryptorSubj, encryptorSubj)) {
               keys = this.extractPublicKeys(decryptorCreds);
            }
         }

         if (keys == null) {
            keys = this.findPublicKeys(encryptorSubj);
         }

         Iterator iter = keys.iterator();

         while(true) {
            if (iter.hasNext()) {
               publicKey = (PublicKey)iter.next();

               try {
                  Object o = this.decrypt(so, publicKey);
                  this.publicKeyMap.put(encryptorSubj, publicKey);
                  return o;
               } catch (GeneralSecurityException var8) {
                  if (iter.hasNext()) {
                     continue;
                  }

                  throw var8;
               }
            }

            throw new GeneralSecurityException("Failed to match credentials for " + encryptorSubj);
         }
      }
   }

   public SignedObject encrypt(Object object, Subject encryptorSubj) throws IOException, GeneralSecurityException {
      Set privateCreds = encryptorSubj.getPrivateCredentials();
      if (privateCreds == null) {
         throw new GeneralSecurityException("Subject without private credentials");
      } else {
         Iterator iter = privateCreds.iterator();

         PrivateKey privateKey;
         do {
            if (!iter.hasNext()) {
               throw new GeneralSecurityException("Not sufficient credentials");
            }

            Object cred = iter.next();
            privateKey = null;
            if (cred instanceof PrivateKey) {
               privateKey = (PrivateKey)cred;
            } else if (cred instanceof X500PrivateCredential) {
               privateKey = ((X500PrivateCredential)cred).getPrivateKey();
            }
         } while(privateKey == null);

         return this.encrypt((Serializable)object, privateKey);
      }
   }

   public static synchronized void setKeyStore(KeyStore ks) {
      keystore = ks;
   }

   private synchronized SignedObject encrypt(Serializable o, PrivateKey privateKey) throws IOException, GeneralSecurityException {
      return new SignedObject(o, privateKey, SIGNATURE_ENGINE);
   }

   private synchronized Object decrypt(SignedObject so, PublicKey publicKey) throws ClassNotFoundException, IOException, GeneralSecurityException {
      if (so.verify(publicKey, SIGNATURE_ENGINE)) {
         return so.getObject();
      } else {
         throw new SignatureException("Invalid signature");
      }
   }

   private boolean equalsMostly(Subject subject1, Subject subject2) {
      return Base.equals(subject1.getPrincipals(), subject2.getPrincipals()) && Base.equals(subject1.getPublicCredentials(), subject2.getPublicCredentials());
   }

   private Set extractPublicKeys(Set publicCreds) {
      Set certs = this.extractCertificates(publicCreds);
      Set keys = new HashSet();
      Iterator iter = certs.iterator();

      while(iter.hasNext()) {
         Certificate cert = (Certificate)iter.next();
         keys.add(cert.getPublicKey());
      }

      return keys;
   }

   private Set extractCertificates(Set publicCreds) {
      Set certs = new HashSet();
      Iterator iter = publicCreds.iterator();

      while(iter.hasNext()) {
         Object cred = iter.next();
         if (cred instanceof CertPath) {
            CertPath certPath = (CertPath)cred;
            List listCert = certPath.getCertificates();
            if (!listCert.isEmpty()) {
               certs.add(listCert.get(0));
            }
         } else if (cred instanceof Certificate) {
            Certificate cert = (Certificate)cred;
            certs.add(cert);
         } else if (cred instanceof Certificate[]) {
            Certificate[] acert = (Certificate[])((Certificate[])cred);
            if (acert.length > 0) {
               certs.add(acert[0]);
            }
         } else {
            Base.log("Unsupported credentials: " + cred.getClass());
         }
      }

      return certs;
   }

   private Set findPublicKeys(Subject subject) throws GeneralSecurityException {
      KeyStore ks = keystore;
      Set certs = this.extractCertificates(subject.getPublicCredentials());
      Set principals = new HashSet();
      Set keys = new HashSet();
      Iterator iter = certs.iterator();

      while(iter.hasNext()) {
         Certificate cert = (Certificate)iter.next();
         if (ks.getCertificateAlias(cert) != null && cert instanceof X509Certificate) {
            X509Certificate certX509 = (X509Certificate)cert;
            principals.add(new X500Principal(certX509.getIssuerDN().getName()));
            keys.add(cert.getPublicKey());
         }
      }

      return keys;
   }
}
