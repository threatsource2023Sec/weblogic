package org.apache.xml.security.keys.storage.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.xml.security.keys.content.x509.XMLX509SKI;
import org.apache.xml.security.keys.storage.StorageResolverException;
import org.apache.xml.security.keys.storage.StorageResolverSpi;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertsInFilesystemDirectoryResolver extends StorageResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(CertsInFilesystemDirectoryResolver.class);
   private String merlinsCertificatesDir;
   private List certs = new ArrayList();

   public CertsInFilesystemDirectoryResolver(String directoryName) throws StorageResolverException {
      this.merlinsCertificatesDir = directoryName;
      this.readCertsFromHarddrive();
   }

   private void readCertsFromHarddrive() throws StorageResolverException {
      File certDir = new File(this.merlinsCertificatesDir);
      List al = new ArrayList();
      String[] names = certDir.list();
      if (names != null) {
         for(int i = 0; i < names.length; ++i) {
            String currentFileName = names[i];
            if (currentFileName.endsWith(".crt")) {
               al.add(names[i]);
            }
         }
      }

      CertificateFactory cf = null;

      try {
         cf = CertificateFactory.getInstance("X.509");
      } catch (CertificateException var26) {
         throw new StorageResolverException(var26);
      }

      for(int i = 0; i < al.size(); ++i) {
         String filename = certDir.getAbsolutePath() + File.separator + (String)al.get(i);
         boolean added = false;
         String dn = null;

         try {
            InputStream inputStream = Files.newInputStream(Paths.get(filename));
            Throwable var10 = null;

            try {
               X509Certificate cert = (X509Certificate)cf.generateCertificate(inputStream);
               cert.checkValidity();
               this.certs.add(cert);
               dn = cert.getSubjectX500Principal().getName();
               added = true;
            } catch (Throwable var25) {
               var10 = var25;
               throw var25;
            } finally {
               if (inputStream != null) {
                  if (var10 != null) {
                     try {
                        inputStream.close();
                     } catch (Throwable var24) {
                        var10.addSuppressed(var24);
                     }
                  } else {
                     inputStream.close();
                  }
               }

            }
         } catch (FileNotFoundException var28) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Could not add certificate from file " + filename, var28);
            }
         } catch (CertificateNotYetValidException var29) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Could not add certificate from file " + filename, var29);
            }
         } catch (CertificateExpiredException var30) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Could not add certificate from file " + filename, var30);
            }
         } catch (CertificateException var31) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Could not add certificate from file " + filename, var31);
            }
         } catch (IOException var32) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Could not add certificate from file " + filename, var32);
            }
         }

         if (added) {
            LOG.debug("Added certificate: {}", dn);
         }
      }

   }

   public Iterator getIterator() {
      return new FilesystemIterator(this.certs);
   }

   public static void main(String[] unused) throws Exception {
      CertsInFilesystemDirectoryResolver krs = new CertsInFilesystemDirectoryResolver("data/ie/baltimore/merlin-examples/merlin-xmldsig-eighteen/certs");
      Iterator i = krs.getIterator();

      while(i.hasNext()) {
         X509Certificate cert = (X509Certificate)i.next();
         byte[] ski = XMLX509SKI.getSKIBytesFromCert(cert);
         System.out.println();
         System.out.println("Base64(SKI())=                 \"" + XMLUtils.encodeToString(ski) + "\"");
         System.out.println("cert.getSerialNumber()=        \"" + cert.getSerialNumber().toString() + "\"");
         System.out.println("cert.getSubjectX500Principal().getName()= \"" + cert.getSubjectX500Principal().getName() + "\"");
         System.out.println("cert.getIssuerX500Principal().getName()=  \"" + cert.getIssuerX500Principal().getName() + "\"");
      }

   }

   private static class FilesystemIterator implements Iterator {
      private List certs;
      private int i;

      public FilesystemIterator(List certs) {
         this.certs = certs;
         this.i = 0;
      }

      public boolean hasNext() {
         return this.i < this.certs.size();
      }

      public Certificate next() {
         return (Certificate)this.certs.get(this.i++);
      }

      public void remove() {
         throw new UnsupportedOperationException("Can't remove keys from KeyStore");
      }
   }
}
