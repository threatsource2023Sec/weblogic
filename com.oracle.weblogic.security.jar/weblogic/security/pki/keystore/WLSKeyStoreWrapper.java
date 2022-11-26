package weblogic.security.pki.keystore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;

final class WLSKeyStoreWrapper extends KeyStore {
   private static IllegalStateException getException_uninitializedKeystore(KeyStoreException e) {
      return new IllegalStateException("Keystore has not been initialized.", e);
   }

   WLSKeyStoreWrapper(KeyStore wrappedKeystore) {
      super(new WLSKeyStoreSpi(wrappedKeystore), wrappedKeystore.getProvider(), wrappedKeystore.getType());
   }

   private static class WLSKeyStoreSpi extends KeyStoreSpi {
      final KeyStore delegate;

      private WLSKeyStoreSpi(KeyStore delegateKeyStore) {
         if (null == delegateKeyStore) {
            throw new IllegalArgumentException("Illegal null KeyStore.");
         } else {
            this.delegate = delegateKeyStore;
         }
      }

      public Key engineGetKey(final String alias, final char[] password) throws NoSuchAlgorithmException, UnrecoverableKeyException {
         try {
            return (Key)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Key run() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.getKey(alias, password);
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof UnrecoverableKeyException) {
               throw (UnrecoverableKeyException)e;
            } else if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public Certificate[] engineGetCertificateChain(final String alias) {
         try {
            return (Certificate[])AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Certificate[] run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.getCertificateChain(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public Certificate engineGetCertificate(final String alias) {
         try {
            return (Certificate)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Certificate run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.getCertificate(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public Date engineGetCreationDate(final String alias) {
         try {
            return (Date)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Date run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.getCreationDate(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineSetKeyEntry(final String alias, final Key key, final char[] password, final Certificate[] chain) throws KeyStoreException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException {
                  WLSKeyStoreSpi.this.delegate.setKeyEntry(alias, key, password, chain);
                  return null;
               }
            });
         } catch (PrivilegedActionException var7) {
            Exception e = var7.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineSetCertificateEntry(final String alias, final Certificate cert) throws KeyStoreException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException {
                  WLSKeyStoreSpi.this.delegate.setCertificateEntry(alias, cert);
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineDeleteEntry(final String alias) throws KeyStoreException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException {
                  WLSKeyStoreSpi.this.delegate.deleteEntry(alias);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineSetKeyEntry(final String alias, final byte[] key, final Certificate[] chain) throws KeyStoreException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException {
                  WLSKeyStoreSpi.this.delegate.setKeyEntry(alias, key, chain);
                  return null;
               }
            });
         } catch (PrivilegedActionException var6) {
            Exception e = var6.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public Enumeration engineAliases() {
         try {
            return (Enumeration)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Enumeration run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.aliases();
               }
            });
         } catch (PrivilegedActionException var3) {
            Exception e = var3.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public boolean engineContainsAlias(final String alias) {
         try {
            return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Boolean run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.containsAlias(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public int engineSize() {
         try {
            return (Integer)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Integer run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.size();
               }
            });
         } catch (PrivilegedActionException var3) {
            Exception e = var3.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public boolean engineIsKeyEntry(final String alias) {
         try {
            return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Boolean run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.isKeyEntry(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public boolean engineIsCertificateEntry(final String alias) {
         try {
            return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Boolean run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.isCertificateEntry(alias);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public String engineGetCertificateAlias(final Certificate cert) {
         try {
            return (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public String run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.getCertificateAlias(cert);
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineStore(final OutputStream stream, final char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
                  WLSKeyStoreSpi.this.delegate.store(stream, password);
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof CertificateException) {
               throw (CertificateException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineStore(final KeyStore.LoadStoreParameter param) throws IOException, NoSuchAlgorithmException, CertificateException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
                  WLSKeyStoreSpi.this.delegate.store(param);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof CertificateException) {
               throw (CertificateException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineLoad(final InputStream stream, final char[] password) throws IOException, NoSuchAlgorithmException, CertificateException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws IOException, NoSuchAlgorithmException, CertificateException {
                  WLSKeyStoreSpi.this.delegate.load(stream, password);
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof CertificateException) {
               throw (CertificateException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineLoad(final KeyStore.LoadStoreParameter param) throws IOException, NoSuchAlgorithmException, CertificateException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws IOException, NoSuchAlgorithmException, CertificateException {
                  WLSKeyStoreSpi.this.delegate.load(param);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            Exception e = var4.getException();
            if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof CertificateException) {
               throw (CertificateException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public KeyStore.Entry engineGetEntry(final String alias, final KeyStore.ProtectionParameter protParam) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
         try {
            return (KeyStore.Entry)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public KeyStore.Entry run() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
                  return WLSKeyStoreSpi.this.delegate.getEntry(alias, protParam);
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else if (e instanceof NoSuchAlgorithmException) {
               throw (NoSuchAlgorithmException)e;
            } else if (e instanceof UnrecoverableEntryException) {
               throw (UnrecoverableEntryException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public void engineSetEntry(final String alias, final KeyStore.Entry entry, final KeyStore.ProtectionParameter protParam) throws KeyStoreException {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public KeyStore.Entry run() throws KeyStoreException {
                  WLSKeyStoreSpi.this.delegate.setEntry(alias, entry, protParam);
                  return null;
               }
            });
         } catch (PrivilegedActionException var6) {
            Exception e = var6.getException();
            if (e instanceof KeyStoreException) {
               throw (KeyStoreException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      public boolean engineEntryInstanceOf(final String alias, final Class entryClass) {
         try {
            return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Boolean run() throws KeyStoreException {
                  return WLSKeyStoreSpi.this.delegate.entryInstanceOf(alias, entryClass);
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof KeyStoreException) {
               throw WLSKeyStoreWrapper.getException_uninitializedKeystore((KeyStoreException)e);
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }

      // $FF: synthetic method
      WLSKeyStoreSpi(KeyStore x0, Object x1) {
         this(x0);
      }
   }
}
