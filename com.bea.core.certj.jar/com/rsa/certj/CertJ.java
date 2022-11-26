package com.rsa.certj;

import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.certj.pkcs12.PKCS12;
import com.rsa.certj.pkcs12.PKCS12Exception;
import com.rsa.certj.provider.random.DefaultRandom;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.CertPathInterface;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.spi.random.RandomInterface;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.spi.revocation.CertStatusInterface;
import com.rsa.certj.x.b;
import com.rsa.certj.x.c;
import com.rsa.certj.x.g;
import com.rsa.certj.x.i;
import com.rsa.jsafe.CryptoJ;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Session;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/** @deprecated */
public final class CertJ {
   /** @deprecated */
   public static final int SPT_RANDOM = 0;
   /** @deprecated */
   public static final int SPT_DATABASE = 1;
   /** @deprecated */
   public static final int SPT_CERT_STATUS = 2;
   /** @deprecated */
   public static final int SPT_CERT_PATH = 3;
   /** @deprecated */
   public static final int SPT_PKI = 4;
   private static final int SPT_FIRST = 0;
   private static final int SPT_LAST = 4;
   /** @deprecated */
   public static final int SERVICE_ORDER_FIRST = 0;
   /** @deprecated */
   public static final int SERVICE_ORDER_LAST = 1;
   /** @deprecated */
   public static final String CERT_J_VERSION = CertJVersion.getVersionString();
   /** @deprecated */
   public static final int COMPAT_STRICT = 0;
   /** @deprecated */
   public static final int COMPAT_BUS_CAT_ABSENT = 1;
   /** @deprecated */
   public static final int COMPAT_EXTRA_KEY_USAGE_BIT = 2;
   /** @deprecated */
   public static final int COMPAT_NON_CRITICAL_KEY_USAGE = 4;
   /** @deprecated */
   public static final int COMPAT_WEAK_RSA_KEY_SIZE = 8;
   /** @deprecated */
   public static final int COMPAT_CERT_POLICY_ABSENT = 16;
   private static volatile String defaultDevice = getDefaultDeviceStringValue();
   private static final String PARAMETERS_SHOULD_NOT_BE_NULL = "CertJ.isSuiteBCompliant: parameters should not be null.";
   private final Vector[] services;
   private volatile String device;
   private volatile JSAFE_Session[] pkcs11Sessions;
   final c context;
   private static Vector compatibilityTypeVector = new Vector(1);
   /** @deprecated */
   public static final int NOT_INITIALIZED = 0;
   /** @deprecated */
   public static final int UNDER_SELF_TEST = 1;
   /** @deprecated */
   public static final int OPERATIONAL = 2;
   /** @deprecated */
   public static final int FAILED = 3;
   /** @deprecated */
   public static final int FIPS140_MODE = 0;
   /** @deprecated */
   public static final int FIPS140_ECC_MODE = 0;
   /** @deprecated */
   public static final int FIPS140_SSL_MODE = 2;
   /** @deprecated */
   public static final int FIPS140_SSL_ECC_MODE = 2;
   /** @deprecated */
   public static final int NON_FIPS140_MODE = 1;
   /** @deprecated */
   public static final int CRYPTO_OFFICER_ROLE = 10;
   /** @deprecated */
   public static final int USER_ROLE = 11;

   private static String getDefaultDeviceStringValue() {
      return CryptoJ.isNativeAvailable("SHA1") ? "Native/Java" : "Java";
   }

   /** @deprecated */
   public void setPKCS11Sessions(JSAFE_Session[] var1) throws InvalidParameterException {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS11 Session Array cannot be null");
      } else {
         this.pkcs11Sessions = (JSAFE_Session[])var1.clone();
      }
   }

   /** @deprecated */
   public JSAFE_Session[] getPKCS11Sessions() {
      return this.pkcs11Sessions != null ? (JSAFE_Session[])this.pkcs11Sessions.clone() : null;
   }

   /** @deprecated */
   public static String getDefaultDevice() {
      return defaultDevice;
   }

   /** @deprecated */
   public static void setDefaultDevice(String var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("A device string must be provided.");
      } else {
         defaultDevice = var0;
      }
   }

   /** @deprecated */
   public CertJ() throws ProviderManagementException, InvalidUseException {
      this(c.a(), (Provider[])null);
   }

   /** @deprecated */
   public CertJ(FIPS140Mode var1) throws ProviderManagementException, InvalidUseException {
      this(c.a(var1), (Provider[])null);
   }

   /** @deprecated */
   public CertJ(FIPS140Mode var1, FIPS140Role var2) throws ProviderManagementException, InvalidUseException {
      this(c.a(var1, var2), (Provider[])null);
   }

   /** @deprecated */
   public CertJ(FIPS140Mode var1, FIPS140Role var2, byte[] var3) throws ProviderManagementException, InvalidUseException {
      this(c.a(var1, var2, var3, (File)null), (Provider[])null);
      if (var3 == null || var3.length == 0) {
         throw new IllegalArgumentException("A valid pin must be provided.");
      }
   }

   /** @deprecated */
   public CertJ(FIPS140Mode var1, FIPS140Role var2, byte[] var3, File var4) throws ProviderManagementException, InvalidUseException {
      this(c.a(var1, var2, var3, var4), (Provider[])null);
      if (var3 == null || var3.length == 0 || var4 == null) {
         throw new IllegalArgumentException("A valid pin and configuration file must be provided.");
      }
   }

   /** @deprecated */
   public CertJ(Provider[] var1) throws ProviderManagementException, InvalidUseException {
      this(c.a(), var1);
   }

   /** @deprecated */
   public CertJ(Provider[] var1, FIPS140Mode var2) throws ProviderManagementException, InvalidUseException {
      this(c.a(var2), var1);
   }

   /** @deprecated */
   public CertJ(Provider[] var1, FIPS140Mode var2, FIPS140Role var3) throws ProviderManagementException, InvalidUseException {
      this(c.a(var2, var3), var1);
   }

   /** @deprecated */
   public CertJ(Provider[] var1, FIPS140Mode var2, FIPS140Role var3, byte[] var4) throws ProviderManagementException, InvalidUseException {
      this(c.a(var2, var3, var4, (File)null), var1);
      if (var4 == null || var4.length == 0) {
         throw new IllegalArgumentException("A valid pin must be provided.");
      }
   }

   /** @deprecated */
   public CertJ(Provider[] var1, FIPS140Mode var2, FIPS140Role var3, byte[] var4, File var5) throws ProviderManagementException, InvalidUseException {
      this(c.a(var2, var3, var4, var5), var1);
      if (var4 == null || var4.length == 0 || var5 == null) {
         throw new IllegalArgumentException("A valid pin and configuration file must be provided.");
      }
   }

   private CertJ(c var1, Provider[] var2) throws ProviderManagementException, InvalidUseException {
      this.services = new Vector[5];
      this.context = var1;
      isFIPS140Compliant();
      this.device = defaultDevice;

      int var3;
      for(var3 = 0; var3 <= 4; ++var3) {
         this.services[var3] = new Vector();
      }

      try {
         if (var2 != null) {
            for(var3 = 0; var3 < var2.length; ++var3) {
               this.registerService(var2[var3], 1);
            }
         }

         if (this.services[0].isEmpty()) {
            this.registerService(new DefaultRandom("Default Random"), 1);
         }

      } catch (InvalidParameterException var4) {
         this.unregisterAll();
         throw new ProviderManagementException("CertJ.CertJ: ", var4);
      } catch (ProviderManagementException var5) {
         this.unregisterAll();
         throw var5;
      }
   }

   /** @deprecated */
   public void unregisterAll() {
      for(int var1 = 0; var1 <= 4; ++var1) {
         Vector var2 = this.services[var1];
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            ProviderImplementation var5 = (ProviderImplementation)var4;
            var5.unregister();
         }

         var2.removeAllElements();
      }

   }

   /** @deprecated */
   public void registerService(Provider var1) throws InvalidParameterException, ProviderManagementException {
      this.registerService(var1, 1);
   }

   /** @deprecated */
   public void registerService(Provider var1, int var2) throws InvalidParameterException, ProviderManagementException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.registerService: provider can not be null.");
      } else if (var2 != 1 && var2 != 0) {
         throw new InvalidParameterException("CertJ.registerService: order should be either SERVICE_ORDER_LAST or SERVICE_ORDER_FIRST.");
      } else if (var1.getName() == null) {
         throw new ProviderManagementException("CertJ.registerService: provider has to have a name.");
      } else {
         int var3 = var1.getType();
         if (var3 >= 0 && var3 <= 4) {
            if (var3 == 0) {
               if (this.services[0] != null && !this.services[0].isEmpty()) {
                  throw new ProviderManagementException("CertJ.registerService: a random service is already registered. Do unregister it first.");
               }
            } else {
               Service var4 = null;

               try {
                  var4 = this.bindService(var1.getType(), var1.getName());
               } catch (Exception var6) {
               }

               if (var4 != null) {
                  this.unbindService(var4);
                  throw new ProviderManagementException("CertJ.registerService: " + serviceTypeToString(var1.getType()) + " service named " + var1.getName() + " is already registered.");
               }
            }

            ProviderImplementation var7 = var1.instantiate(this);
            Vector var5 = this.services[var3];
            if (var2 == 0) {
               var5.insertElementAt(var7, 0);
            } else if (var2 == 1) {
               var5.addElement(var7);
            }

         } else {
            throw new ProviderManagementException("CertJ.registerService: service type(" + var3 + ") of the provider is not between " + 0 + " and " + 4 + ".");
         }
      }
   }

   /** @deprecated */
   public void unregisterService(int var1, String var2) throws InvalidParameterException {
      if (var1 >= 0 && var1 <= 4) {
         if (var2 == null) {
            throw new InvalidParameterException("CertJ.unregisterService: name should not be null.");
         } else {
            Vector var3 = this.services[var1];
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               Object var5 = var4.next();
               ProviderImplementation var6 = (ProviderImplementation)var5;
               if (var6.getName().equals(var2)) {
                  var6.unregister();
                  var3.remove(var6);
                  break;
               }
            }

         }
      } else {
         throw new InvalidParameterException("CertJ.unregisterService: type (" + var1 + ") is not between SPT_FIRST and SPT_LAST.");
      }
   }

   /** @deprecated */
   public void addProvider(Provider var1) throws InvalidParameterException, ProviderManagementException {
      this.registerService(var1);
   }

   /** @deprecated */
   public void addProvider(Provider var1, int var2) throws InvalidParameterException, ProviderManagementException {
      this.registerService(var1, var2);
   }

   /** @deprecated */
   public void removeProvider(int var1, String var2) throws InvalidParameterException {
      this.unregisterService(var1, var2);
   }

   /** @deprecated */
   public Service bindService(int var1, String var2) throws InvalidParameterException, ProviderManagementException {
      String[] var3 = new String[1];
      if (var2 == null) {
         if (var1 < 0 || var1 > 4) {
            throw new InvalidParameterException("CertJ.bindService: type (" + var1 + ") is not between SPT_FIRST and SPT_LAST.");
         }

         Vector var4 = this.services[var1];
         if (var4.isEmpty()) {
            throw new ProviderManagementException("CertJ.bindService: no provider is registered for type" + serviceTypeToString(var1));
         }

         var3[0] = ((ProviderImplementation)var4.elementAt(0)).getName();
      } else {
         var3[0] = var2;
      }

      return this.bindServices(var1, var3);
   }

   /** @deprecated */
   public Service bindServices(int var1, String[] var2) throws InvalidParameterException, ProviderManagementException {
      if (var1 >= 0 && var1 <= 4) {
         if (var2 == null) {
            return this.bindServices(var1);
         } else {
            Vector var3 = this.services[var1];
            Service var4 = Service.getInstance(this, var1);

            for(int var5 = 0; var5 < var2.length; ++var5) {
               String var6 = var2[var5];
               boolean var7 = false;
               Iterator var8 = var3.iterator();

               while(var8.hasNext()) {
                  Object var9 = var8.next();
                  ProviderImplementation var10 = (ProviderImplementation)var9;
                  if (var6.equals(var10.getName())) {
                     var4.addProvider(var10);
                     var7 = true;
                     break;
                  }
               }

               if (!var7) {
                  throw new ProviderManagementException("CertJ.bindServices: provider of type: " + serviceTypeToString(var1) + " with name: " + var2[var5] + " not found.");
               }
            }

            return var4;
         }
      } else {
         throw new InvalidParameterException("CertJ.bindServices: type (" + var1 + ") is not between SPT_FIRST and SPT_LAST.");
      }
   }

   /** @deprecated */
   public Service bindServices(int var1) throws InvalidParameterException, ProviderManagementException {
      if (var1 >= 0 && var1 <= 4) {
         Service var2 = Service.getInstance(this, var1);
         Vector var3 = this.services[var1];
         if (var3.isEmpty()) {
            throw new ProviderManagementException("CertJ.bindServices: no provider is registered for type: " + serviceTypeToString(var1));
         } else {
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               Object var5 = var4.next();
               var2.addProvider((ProviderImplementation)var5);
            }

            return var2;
         }
      } else {
         throw new InvalidParameterException("CertJ.bindServices: type (" + var1 + ") is not between SPT_FIRST and SPT_LAST.");
      }
   }

   /** @deprecated */
   public void unbindService(Service var1) {
      if (var1 != null) {
         var1.unbind();
      }

   }

   /** @deprecated */
   public String[] listAllProviders() {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 <= 4; ++var2) {
         Vector var3 = this.services[var2];
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            var1.add(var5.toString());
         }
      }

      return (String[])var1.toArray(new String[var1.size()]);
   }

   /** @deprecated */
   public String[] listProviderNames(int var1) {
      Vector var2 = this.services[var1];
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         var3.add(((ProviderImplementation)var5).getName());
      }

      return (String[])var3.toArray(new String[var3.size()]);
   }

   /** @deprecated */
   public boolean isProviderRegistered(Provider var1, int var2) {
      boolean var3 = false;
      String var4 = var1.getClass().getName();
      Vector var5 = this.services[var2];
      Iterator var7 = var5.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         String var6 = var8.getClass().getName();
         var6 = var6.substring(0, var6.indexOf("$"));
         if (var6.equals(var4)) {
            var3 = true;
            break;
         }
      }

      return var3;
   }

   /** @deprecated */
   public JSAFE_SecureRandom getRandomObject() throws NoServiceException, RandomException {
      if (this.services[0].isEmpty()) {
         throw new NoServiceException("CertJ.getRandomObject: no random service is registered.");
      } else {
         RandomInterface var1 = (RandomInterface)this.services[0].elementAt(0);

         try {
            return var1.getRandomObject();
         } catch (NotSupportedException var3) {
            throw new NoServiceException("CertJ.getRandomObject: ", var3);
         }
      }
   }

   /** @deprecated */
   public String getDevice() {
      return this.device;
   }

   /** @deprecated */
   public void setDevice(String var1) throws InvalidParameterException {
      if (var1 == null) {
         throw new InvalidParameterException("A device string must be provided.");
      } else {
         this.device = var1;
      }
   }

   /** @deprecated */
   public boolean verifyCertPath(CertPathCtx var1, Object var2) throws InvalidParameterException, NoServiceException, CertPathException {
      return this.buildCertPath(var1, var2, (Vector)null, (Vector)null, (Vector)null, (Vector)null);
   }

   /** @deprecated */
   public boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.buildCertPath: pathCtx should not be null.");
      } else if (!(var2 instanceof Certificate) && !(var2 instanceof CRL)) {
         throw new InvalidParameterException("CertJ.buildCertPath: startObject should be either Certificate or CRL.");
      } else {
         Vector var7 = this.services[3];
         Iterator var8 = var7.iterator();

         while(var8.hasNext()) {
            Object var9 = var8.next();

            try {
               return ((CertPathInterface)var9).buildCertPath(var1, var2, var3, var4, var5, var6);
            } catch (NotSupportedException var11) {
            }
         }

         throw new NoServiceException("CertJ.buildCertPath: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.buildCertPath: pathCtx should not be null.");
      } else if (!(var2 instanceof Certificate) && !(var2 instanceof CRL)) {
         throw new InvalidParameterException("CertJ.buildCertPath: startObject should be either Certificate or CRL.");
      } else {
         Vector var6 = this.services[3];
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            Object var8 = var7.next();

            try {
               return ((CertPathInterface)var8).buildCertPath(var1, var2, var3, var4, var5);
            } catch (NotSupportedException var10) {
            }
         }

         throw new NoServiceException("CertJ.buildCertPath: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6, GeneralSubtrees var7, GeneralNames var8) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.buildCertPath: pathCtx should not be null.");
      } else if (!(var2 instanceof Certificate) && !(var2 instanceof CRL)) {
         throw new InvalidParameterException("CertJ.buildCertPath: startObject should be either Certificate or CRL.");
      } else {
         Vector var9 = this.services[3];
         Iterator var10 = var9.iterator();

         while(var10.hasNext()) {
            Object var11 = var10.next();

            try {
               CertPathResult var12 = ((CertPathInterface)var11).buildCertPath(var1, var2, var3, var4, var5, var7, var8);
               Vector var13 = var12.getValidPolicies();
               if (var13 != null) {
                  var6.addAll(var13);
               }

               return var12.getValidationResult();
            } catch (NotSupportedException var14) {
            }
         }

         throw new NoServiceException("CertJ.buildCertPath: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.buildCertPath: pathCtx should not be null.");
      } else if (!(var2 instanceof Certificate) && !(var2 instanceof CRL)) {
         throw new InvalidParameterException("CertJ.buildCertPath: startObject should be either Certificate or CRL.");
      } else {
         Vector var8 = this.services[3];
         Iterator var9 = var8.iterator();

         while(var9.hasNext()) {
            Object var10 = var9.next();

            try {
               return ((CertPathInterface)var10).buildCertPath(var1, var2, var3, var4, var5, var6, var7);
            } catch (NotSupportedException var12) {
            }
         }

         throw new NoServiceException("CertJ.buildCertPath: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public void getNextCertInPath(CertPathCtx var1, Object var2, Vector var3) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.getNextCertInPath: pathCtx should not be null.");
      } else if (!(var2 instanceof Certificate) && !(var2 instanceof CRL)) {
         throw new InvalidParameterException("CertJ.getNextCertInPath: baseObject should be either Certificate or CRL.");
      } else {
         Vector var4 = this.services[3];
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            Object var6 = var5.next();

            try {
               ((CertPathInterface)var6).getNextCertInPath(var1, var2, var3);
               return;
            } catch (NotSupportedException var8) {
            }
         }

         throw new NoServiceException("CertJ.getNextCertInPath: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public boolean validateCertificate(CertPathCtx var1, Certificate var2, JSAFE_PublicKey var3) throws InvalidParameterException, NoServiceException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.validateCertificate: pathCtx should not be null.");
      } else if (var2 == null) {
         throw new InvalidParameterException("CertJ.validateCertificate: cert should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("CertJ.validateCertificate: validationKey should not be null.");
      } else {
         Vector var4 = this.services[3];
         int var5 = 0;

         while(var5 < var4.size()) {
            try {
               return ((CertPathInterface)var4.elementAt(var5)).validateCertificate(var1, var2, var3);
            } catch (NotSupportedException var7) {
               ++var5;
            }
         }

         throw new NoServiceException("CertJ.validateCertificate: no provider is found to handle this method.");
      }
   }

   /** @deprecated */
   public CertRevocationInfo checkCertRevocation(CertPathCtx var1, Certificate var2) throws InvalidParameterException, NoServiceException, CertStatusException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.checkCertRevocation: pathCtx should not be null.");
      } else if (var2 == null) {
         throw new InvalidParameterException("CertJ.checkCertRevocation: cert should not be null.");
      } else {
         Vector var3 = this.services[2];
         CertRevocationInfo var4 = null;

         for(int var5 = 0; var5 < var3.size(); ++var5) {
            try {
               CertRevocationInfo var6 = ((CertStatusInterface)var3.elementAt(var5)).checkCertRevocation(var1, var2);
               if (var6.getStatus() != 2) {
                  return var6;
               }

               if (var4 == null) {
                  var4 = var6;
               }
            } catch (NotSupportedException var7) {
            }
         }

         if (var4 != null) {
            return var4;
         } else {
            throw new NoServiceException("CertJ.checkCertRevocation: no provider is found to handle this method or no registered provider can determine the revocation status of the given certificate.");
         }
      }
   }

   /** @deprecated */
   public void importPKCS12(String var1, char[] var2, DatabaseService var3) throws PKCS12Exception {
      new PKCS12(this, var3, var2, var1);
   }

   /** @deprecated */
   public void importPKCS12(File var1, char[] var2, DatabaseService var3) throws PKCS12Exception {
      new PKCS12(this, var3, var2, var1);
   }

   /** @deprecated */
   public static String serviceTypeToString(int var0) {
      switch (var0) {
         case 0:
            return "SPT_RANDOM";
         case 1:
            return "SPT_DATABASE";
         case 2:
            return "SPT_CERT_STATUS";
         case 3:
            return "SPT_CERT_PATH";
         case 4:
            return "SPT_PKI";
         default:
            return "Unknown service type: " + var0;
      }
   }

   /** @deprecated */
   public static void addCompatibilityType(CompatibilityType var0) {
      synchronized(compatibilityTypeVector) {
         if (!compatibilityTypeVector.contains(var0)) {
            compatibilityTypeVector.addElement(var0);
         }

      }
   }

   /** @deprecated */
   public static void removeCompatibilityType(CompatibilityType var0) {
      synchronized(compatibilityTypeVector) {
         compatibilityTypeVector.removeElement(var0);
      }
   }

   /** @deprecated */
   public static boolean isCompatibilityTypeSet(CompatibilityType var0) {
      synchronized(compatibilityTypeVector) {
         boolean var1 = compatibilityTypeVector.contains(var0);
         return var1;
      }
   }

   /** @deprecated */
   public static boolean isFIPS140Compliant() {
      return b.b();
   }

   /** @deprecated */
   public static boolean isNotFIPS140Compliant() throws InvalidUseException {
      return !isFIPS140Compliant();
   }

   /** @deprecated */
   public static int getState() {
      return CryptoJ.getState();
   }

   /** @deprecated */
   public static int getMode() {
      return CryptoJ.getMode();
   }

   /** @deprecated */
   public static void setMode(int var0) throws InvalidUseException {
      try {
         CryptoJ.setMode(var0);
      } catch (JSAFE_InvalidUseException var2) {
         throw new InvalidUseException(var2);
      }
   }

   /** @deprecated */
   public static int getRole() {
      return CryptoJ.getRole();
   }

   /** @deprecated */
   public static void setRole(int var0) throws InvalidUseException {
      try {
         CryptoJ.setRole(var0);
      } catch (JSAFE_InvalidUseException var2) {
         throw new InvalidUseException(var2);
      }
   }

   /** @deprecated */
   public static boolean selfTestPassed() {
      return CryptoJ.selfTestPassed();
   }

   /** @deprecated */
   public static synchronized boolean runSelfTests() throws InvalidUseException {
      try {
         return CryptoJ.runSelfTests();
      } catch (JSAFE_InvalidUseException var1) {
         throw new InvalidUseException(var1);
      }
   }

   /** @deprecated */
   public FIPS140Mode getFIPS140Mode() {
      return this.context.a == null ? FIPS140Mode.NON_FIPS140_MODE : FIPS140Mode.lookup(this.context.a.getModeValue());
   }

   /** @deprecated */
   public FIPS140Role getFIPS140Role() {
      return this.context.a == null ? FIPS140Role.USER_ROLE : FIPS140Role.lookup(this.context.a.getRoleValue());
   }

   /** @deprecated */
   public boolean isSuiteBCompliant(X509CRL var1) throws InvalidParameterException, CertStatusException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.isSuiteBCompliant: parameters should not be null.");
      } else {
         return i.a(var1);
      }
   }

   /** @deprecated */
   public boolean isSuiteBCompliant(X509Certificate var1) throws InvalidParameterException, CertPathException {
      return this.isSuiteBCompliant(var1, (JSAFE_PublicKey)null);
   }

   /** @deprecated */
   public boolean isSuiteBCompliant(X509Certificate var1, JSAFE_PublicKey var2) throws InvalidParameterException, CertPathException {
      if (var1 == null) {
         throw new InvalidParameterException("CertJ.isSuiteBCompliant: parameters should not be null.");
      } else {
         return i.a(var1, var2, this.context);
      }
   }

   /** @deprecated */
   public boolean isEVCompliant(X509Certificate[] var1, List var2) throws InvalidParameterException, CertPathException {
      return this.isEVCompliant(var1, var2, getDefaultCompatibilityMask());
   }

   /** @deprecated */
   public boolean isEVCompliant(X509Certificate[] var1, List var2, int var3) throws InvalidParameterException, CertPathException {
      if (var1 != null && var2 != null) {
         return g.a(var1, var2, var3, this.context);
      } else {
         throw new InvalidParameterException("CertJ.isEVCompliant: parameters should not be null.");
      }
   }

   /** @deprecated */
   public static int getDefaultCompatibilityMask() {
      return g.a();
   }
}
