package com.bea.security.utils.keystore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.Security;
import java.text.MessageFormat;
import java.util.Date;

public final class KssAccessor {
   public static final String KSS_KEYSTORE_TYPE = "KSS";

   public static boolean isKssAvailable() {
      return KssAccessor.KssAccessorImplHolder.INSTANCE.isKssAvailable();
   }

   public static String getUnavailabilityMessage() {
      return KssAccessor.KssAccessorImplHolder.INSTANCE.getUnavailabilityMessage();
   }

   public static KeyStore.LoadStoreParameter getKSSLoadStoreParameterInstance(String kssUri, char[] passPhrase) throws IllegalArgumentException, KeyStoreException {
      return KssAccessor.KssAccessorImplHolder.INSTANCE.getKSSLoadStoreParameterInstance(kssUri, passPhrase);
   }

   public static long getLastModified(String kssUri) throws KeyStoreException {
      return KssAccessor.KssAccessorImplHolder.INSTANCE.getLastModified(kssUri);
   }

   private KssAccessor() {
   }

   static final class KssAccessorImpl {
      static final String KSS_PROVIDER_CLASS_NAME = "oracle.security.jps.internal.keystore.provider.FarmKeyStoreProvider";
      private static final String PARAMETER_CLASS_NAME = "oracle.security.jps.service.keystore.KeyStoreServiceLoadStoreParameter";
      private static final String PARAMETER_SETKSSURI_METHOD_NAME = "setKssUri";
      private static final String PARAMETER_SETPROTECTIONPARAMETER_METHOD_NAME = "setProtectionParameter";
      private static final String KEYSTOREATTRIBUTE_CLASS_NAME = "oracle.security.jps.service.keystore.KeyStoreService$KEYSTORE_ATTRIBUTE";
      private static final String KEYSTOREATTRIBUTE_MODIFICATION_TIME_FIELD_NAME = "MODIFICATION_TIME";
      private static final String KEYSTORESERVICE_CLASS_NAME = "oracle.security.jps.service.keystore.KeyStoreService";
      private static final String KEYSTORESERVICE_GETKEYSTOREATTRIBUTE_METHOD_NAME = "getKeyStoreAttribute";
      private static final String JPSCONTEXTFACTORY_CLASS_NAME = "oracle.security.jps.JpsContextFactory";
      private static final String JPSCONTEXTFACTORY_GETCONTEXTFACTORY_METHOD_NAME = "getContextFactory";
      private static final String JPSCONTEXTFACTORY_GETCONTEXT_METHOD_NAME = "getContext";
      private static final String JPSCONTEXT_CLASS_NAME = "oracle.security.jps.JpsContext";
      private static final String JPSCONTEXT_GETSERVICEINSTANCE_METHOD_NAME = "getServiceInstance";
      private static final Class PARAMETER_CLASS;
      private static final Method PARAMETER_setKssUri_METHOD;
      private static final Method PARAMETER_setProtectionParameter_METHOD;
      private static final Object KEYSTOREATTRIBUTE_modificationTime_ENUM;
      private static final Class KEYSTORESERVICE_CLASS;
      private static final Method KEYSTORESERVICE_getKeyStoreAttribute_METHOD;
      private static final Method JPSCONTEXTFACTORY_getContextFactory_METHOD;
      private static final Method JPSCONTEXTFACTORY_getContext_METHOD;
      private static final Method JPSCONTEXT_getServiceInstance_METHOD;
      private static final boolean KSS_AVAILABLE;
      private static final String INITIALIZATION_EXCEPTION_MESSAGE;

      private static void checkKssUri(String kssUri) {
         if (null == kssUri || 0 == kssUri.length()) {
            throw new IllegalArgumentException("Illegal null or empty KSS URI.");
         }
      }

      private static Throwable resolveException(Exception e) {
         return (Throwable)(e instanceof InvocationTargetException && null != e.getCause() ? e.getCause() : e);
      }

      static boolean ensureKssProvider(String kssProviderClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
         String filter = "KeyStore.KSS";
         Provider[] providers = Security.getProviders("KeyStore.KSS");
         if (null != providers && providers.length > 0) {
            return false;
         } else {
            Class kssProviderClass = Class.forName(kssProviderClassName);
            Provider kssProvider = (Provider)kssProviderClass.newInstance();
            return -1 != Security.addProvider(kssProvider);
         }
      }

      private void checkKssAvailable() throws KeyStoreException {
         if (!this.isKssAvailable()) {
            throw new KeyStoreException(INITIALIZATION_EXCEPTION_MESSAGE);
         }
      }

      private KssAccessorImpl() {
      }

      public boolean isKssAvailable() {
         return KSS_AVAILABLE;
      }

      public String getUnavailabilityMessage() {
         return INITIALIZATION_EXCEPTION_MESSAGE;
      }

      public KeyStore.LoadStoreParameter getKSSLoadStoreParameterInstance(String kssUri, char[] passPhrase) throws IllegalArgumentException, KeyStoreException {
         this.checkKssAvailable();
         checkKssUri(kssUri);

         try {
            KeyStore.LoadStoreParameter params = (KeyStore.LoadStoreParameter)PARAMETER_CLASS.newInstance();
            PARAMETER_setKssUri_METHOD.invoke(params, kssUri);
            if (null != passPhrase) {
               PARAMETER_setProtectionParameter_METHOD.invoke(params, new KeyStore.PasswordProtection(passPhrase));
            }

            return params;
         } catch (Exception var7) {
            Throwable t = resolveException(var7);
            String pwdMsg = null == passPhrase ? "null" : "not-null";
            String msg = MessageFormat.format("Unable to initialize KSS load store parameter for kssUri=\"{0}\", passPhrase={1}, exception=\"{2}\", message=\"{3}\".", kssUri, pwdMsg, t.getClass().getName(), t.getMessage());
            throw new KeyStoreException(msg, var7);
         }
      }

      public long getLastModified(String kssUri) throws IllegalArgumentException, KeyStoreException {
         this.checkKssAvailable();
         checkKssUri(kssUri);

         try {
            Object jpsContextFactory = JPSCONTEXTFACTORY_getContextFactory_METHOD.invoke((Object)null);
            if (null == jpsContextFactory) {
               throw new IllegalStateException("Unexpected null JpsContextFactory instance.");
            } else {
               Object jpsContext = JPSCONTEXTFACTORY_getContext_METHOD.invoke(jpsContextFactory);
               if (null == jpsContext) {
                  throw new IllegalStateException("Unexpected null JpsContext.");
               } else {
                  Object keyStoreService = JPSCONTEXT_getServiceInstance_METHOD.invoke(jpsContext, KEYSTORESERVICE_CLASS);
                  if (null == keyStoreService) {
                     throw new IllegalStateException("Unexpected null KeyStoreService.");
                  } else {
                     Object lastModTime = KEYSTORESERVICE_getKeyStoreAttribute_METHOD.invoke(keyStoreService, kssUri, KEYSTOREATTRIBUTE_modificationTime_ENUM);
                     if (null == lastModTime) {
                        throw new IllegalStateException("Unexpected null key store modification time.");
                     } else if (!(lastModTime instanceof Date)) {
                        throw new IllegalStateException("Expected \"java.util.Date\" modification time type, but was " + lastModTime.getClass() + ".");
                     } else {
                        Date rawDate = (Date)lastModTime;
                        return rawDate.getTime();
                     }
                  }
               }
            }
         } catch (Exception var7) {
            Throwable t = resolveException(var7);
            String msg = MessageFormat.format("Unable to determine KSS key store last modified time for kssUri=\"{0}\", exception=\"{1}\", message=\"{2}\".", kssUri, t.getClass().getName(), t.getMessage());
            throw new KeyStoreException(msg, var7);
         }
      }

      // $FF: synthetic method
      KssAccessorImpl(Object x0) {
         this();
      }

      static {
         String tempInitializationExceptionMessage = null;
         boolean tempKssAvailable = false;
         Class temp_parameter_class = null;
         Method temp_parameter_setKssUri_method = null;
         Method temp_parameter_setProtectionParameter_method = null;
         Object temp_keyStoreAttribute_modificationTime_enum = null;
         Class temp_keyStoreService_class = null;
         Method temp_keyStoreService_getKeyStoreAttribute_method = null;
         Method temp_jpsContextFactory_getContextFactory_method = null;
         Method temp_jpsContextFactory_getContext_method = null;
         Method temp_jpsContext_getServiceInstance_method = null;

         try {
            temp_parameter_class = Class.forName("oracle.security.jps.service.keystore.KeyStoreServiceLoadStoreParameter");
            temp_parameter_setKssUri_method = temp_parameter_class.getMethod("setKssUri", String.class);
            temp_parameter_setProtectionParameter_method = temp_parameter_class.getMethod("setProtectionParameter", KeyStore.ProtectionParameter.class);
            Class temp_keyStoreAttribute_class = Class.forName("oracle.security.jps.service.keystore.KeyStoreService$KEYSTORE_ATTRIBUTE");
            Object[] enumConsts = temp_keyStoreAttribute_class.getEnumConstants();
            if (null == enumConsts) {
               throw new IllegalStateException("Expected Enum for oracle.security.jps.service.keystore.KeyStoreService$KEYSTORE_ATTRIBUTE");
            }

            Enum foundModTime = null;
            Object[] var14 = enumConsts;
            int var15 = enumConsts.length;

            for(int var16 = 0; var16 < var15; ++var16) {
               Object eObj = var14[var16];
               if (eObj instanceof Enum) {
                  Enum e = (Enum)eObj;
                  if ("MODIFICATION_TIME".equals(e.name())) {
                     foundModTime = e;
                     break;
                  }
               }
            }

            if (null == foundModTime) {
               throw new IllegalStateException("Expected Enum for MODIFICATION_TIME");
            }

            temp_keyStoreAttribute_modificationTime_enum = foundModTime;
            temp_keyStoreService_class = Class.forName("oracle.security.jps.service.keystore.KeyStoreService");
            temp_keyStoreService_getKeyStoreAttribute_method = temp_keyStoreService_class.getMethod("getKeyStoreAttribute", String.class, foundModTime.getClass());
            Class temp_jpsContextFactory_class = Class.forName("oracle.security.jps.JpsContextFactory");
            temp_jpsContextFactory_getContextFactory_method = temp_jpsContextFactory_class.getMethod("getContextFactory");
            temp_jpsContextFactory_getContext_method = temp_jpsContextFactory_class.getMethod("getContext");
            Class temp_jpsContext_class = Class.forName("oracle.security.jps.JpsContext");
            temp_jpsContext_getServiceInstance_method = temp_jpsContext_class.getMethod("getServiceInstance", Class.class);
            ensureKssProvider("oracle.security.jps.internal.keystore.provider.FarmKeyStoreProvider");
            tempKssAvailable = true;
         } catch (Throwable var22) {
            tempInitializationExceptionMessage = MessageFormat.format("KSS is not available, exception={0}, message={1}", var22.getClass().getName(), var22.getMessage());
         } finally {
            INITIALIZATION_EXCEPTION_MESSAGE = tempInitializationExceptionMessage;
            KSS_AVAILABLE = tempKssAvailable;
            if (KSS_AVAILABLE) {
               PARAMETER_CLASS = temp_parameter_class;
               PARAMETER_setKssUri_METHOD = temp_parameter_setKssUri_method;
               PARAMETER_setProtectionParameter_METHOD = temp_parameter_setProtectionParameter_method;
               KEYSTOREATTRIBUTE_modificationTime_ENUM = temp_keyStoreAttribute_modificationTime_enum;
               KEYSTORESERVICE_CLASS = temp_keyStoreService_class;
               KEYSTORESERVICE_getKeyStoreAttribute_METHOD = temp_keyStoreService_getKeyStoreAttribute_method;
               JPSCONTEXTFACTORY_getContextFactory_METHOD = temp_jpsContextFactory_getContextFactory_method;
               JPSCONTEXTFACTORY_getContext_METHOD = temp_jpsContextFactory_getContext_method;
               JPSCONTEXT_getServiceInstance_METHOD = temp_jpsContext_getServiceInstance_method;
            } else {
               PARAMETER_CLASS = null;
               PARAMETER_setKssUri_METHOD = null;
               PARAMETER_setProtectionParameter_METHOD = null;
               KEYSTOREATTRIBUTE_modificationTime_ENUM = null;
               KEYSTORESERVICE_CLASS = null;
               KEYSTORESERVICE_getKeyStoreAttribute_METHOD = null;
               JPSCONTEXTFACTORY_getContextFactory_METHOD = null;
               JPSCONTEXTFACTORY_getContext_METHOD = null;
               JPSCONTEXT_getServiceInstance_METHOD = null;
            }

         }

      }
   }

   private static class KssAccessorImplHolder {
      private static final KssAccessorImpl INSTANCE = new KssAccessorImpl();
   }
}
