package weblogic.descriptor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import weblogic.descriptor.internal.MarshallerFactory;
import weblogic.descriptor.internal.ProductionMode;

public class DescriptorManager extends BasicDescriptorManager {
   public DescriptorManager() {
      super(DescriptorClassLoader.getClassLoader(), false, DescriptorManager.SecurityServiceImpl.instance());
   }

   public DescriptorManager(ClassLoader cl) {
      super(cl, false, DescriptorManager.SecurityServiceImpl.instance());
   }

   public DescriptorManager(String marshallerConfig) {
      super(getMarshallerClassLoader(marshallerConfig), false, DescriptorManager.SecurityServiceImpl.instance());
      this.setProductionMode(ProductionMode.instance().getProductionMode());
   }

   protected DescriptorManager(ClassLoader cl, boolean editable) {
      super(cl, editable, DescriptorManager.SecurityServiceImpl.instance());
   }

   protected static ClassLoader getMarshallerClassLoader(String marshallerConfig) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = DescriptorClassLoader.getClassLoader();
      }

      URL config = cl.getResource(marshallerConfig);
      if (config == null) {
         throw new AssertionError("Resource [" + marshallerConfig + "] is not available.");
      } else {
         ClassLoader cl = new URLClassLoader(new URL[]{config}, cl);
         return cl;
      }
   }

   protected MarshallerFactory getDefaultMF() {
      return DescriptorManager.DefaultMarshallerFactorySingleton.SINGLETON;
   }

   public static class SecurityServiceImpl implements SecurityService {
      private static SecurityService instance = null;

      public static SecurityService instance() {
         if (instance == null) {
            instance = new SecurityServiceImpl();
         }

         return instance;
      }

      private SecurityServiceImpl() {
      }

      public boolean isEncrypted(byte[] bEncrypted) throws DescriptorException {
         return DescriptorManager.SecurityServiceImpl.SecurityProxy.instance().isEncrypted(bEncrypted);
      }

      public byte[] encrypt(String val) throws DescriptorException {
         return DescriptorManager.SecurityServiceImpl.SecurityProxy.instance().encrypt(val);
      }

      public String decrypt(byte[] bEncrypted) throws DescriptorException {
         return DescriptorManager.SecurityServiceImpl.SecurityProxy.instance().decrypt(bEncrypted);
      }

      private static class SecurityProxy {
         private Object serviceObj = null;
         private Method isEncryptedMethod = null;
         private Method encryptMethod = null;
         private Method decryptMethod = null;
         private boolean serviceAvailable = false;
         private static SecurityProxy instance = null;
         private static final String msg_missing_ssi = "Missing SerializedSystemIni.dat";

         public static SecurityProxy instance() {
            if (instance == null) {
               instance = new SecurityProxy();
            }

            return instance;
         }

         private SecurityProxy() {
            try {
               Class serviceType = Class.forName("weblogic.security.internal.encryption.ClearOrEncryptedService");
               Class systemIniType = Class.forName("weblogic.security.internal.SerializedSystemIni");
               Class encryptionServiceType = Class.forName("weblogic.security.internal.encryption.EncryptionService");
               Method serviceGetterStaticMethod = systemIniType.getMethod("getExistingEncryptionService");
               Object encryptionServiceObject = serviceGetterStaticMethod.invoke(systemIniType);
               Constructor serviceConst = serviceType.getConstructor(encryptionServiceType);
               if (encryptionServiceObject == null) {
                  throw new ResourceUnavailableException("Missing SerializedSystemIni.dat");
               } else {
                  this.serviceObj = serviceConst.newInstance(encryptionServiceObject);
                  this.isEncryptedMethod = this.serviceObj.getClass().getMethod("isEncrypted", String.class);
                  this.encryptMethod = this.serviceObj.getClass().getMethod("encrypt", String.class);
                  this.decryptMethod = this.serviceObj.getClass().getMethod("decrypt", String.class);
                  this.serviceAvailable = true;
               }
            } catch (ClassNotFoundException var7) {
               throw new AssertionError(var7);
            } catch (InstantiationException var8) {
               throw new AssertionError(var8);
            } catch (IllegalAccessException var9) {
               throw new AssertionError(var9);
            } catch (NoSuchMethodException var10) {
               throw new AssertionError(var10);
            } catch (InvocationTargetException var11) {
               throw new AssertionError(var11);
            }
         }

         private Object _invokeServiceMethod(Method method, String argument) throws DescriptorException {
            if (this.serviceAvailable) {
               try {
                  return method.invoke(this.serviceObj, argument);
               } catch (IllegalAccessException var4) {
                  throw new AssertionError(var4);
               } catch (InvocationTargetException var5) {
                  throw new AssertionError(var5);
               }
            } else {
               throw new DescriptorException();
            }
         }

         public boolean isEncrypted(byte[] bEncrypted) throws DescriptorException {
            Boolean value = (Boolean)this._invokeServiceMethod(this.isEncryptedMethod, new String(bEncrypted));
            return value;
         }

         public byte[] encrypt(String sPassword) throws DescriptorException {
            String value = (String)this._invokeServiceMethod(this.encryptMethod, sPassword);
            return value.getBytes();
         }

         public String decrypt(byte[] bEncrypted) throws DescriptorException {
            return (String)this._invokeServiceMethod(this.decryptMethod, new String(bEncrypted));
         }
      }
   }

   private static final class DefaultMarshallerFactorySingleton {
      private static final MarshallerFactory SINGLETON;

      static {
         try {
            ClassLoader cl = DescriptorClassLoader.getClassLoader();
            SINGLETON = new MarshallerFactory(cl);
         } catch (IOException var1) {
            throw new AssertionError(var1);
         }
      }
   }
}
