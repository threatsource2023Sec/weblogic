package weblogic.jms.module.customizers;

import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.utils.NestedRuntimeException;

public class ForeignConnectionFactoryCustomizer {
   private ForeignConnectionFactoryBean customized;

   public ForeignConnectionFactoryCustomizer(ForeignConnectionFactoryBean paramCustomized) {
      this.customized = paramCustomized;
   }

   public String getPassword() {
      ClearOrEncryptedService ces;
      try {
         EncryptionService es = SerializedSystemIni.getEncryptionService();
         ces = new ClearOrEncryptedService(es);
      } catch (NestedRuntimeException var7) {
         return null;
      } catch (NullPointerException var8) {
         System.out.println("ERROR: Got an NPE(1).  Please fix CR186445");
         var8.printStackTrace();
         return null;
      }

      try {
         String retVal = ces.decrypt(new String(this.customized.getPasswordEncrypted()));
         return retVal;
      } catch (EncryptionServiceException var5) {
         return null;
      } catch (NullPointerException var6) {
         System.out.println("ERROR: Got an NPE(2).  Please fix CR186445");
         var6.printStackTrace();
         return null;
      }
   }

   public void setPassword(String password) throws IllegalArgumentException {
      if (password != null) {
         ClearOrEncryptedService ces;
         try {
            EncryptionService es = SerializedSystemIni.getEncryptionService();
            ces = new ClearOrEncryptedService(es);
         } catch (NestedRuntimeException var8) {
            throw new IllegalArgumentException("Could not get encryption service, likely the domain directory could not be found, " + var8);
         } catch (NullPointerException var9) {
            System.out.println("ERROR: Got an NPE(3).  Please fix CR186445");
            var9.printStackTrace();
            return;
         }

         byte[] encryptedValue;
         try {
            encryptedValue = ces.encrypt(password).getBytes();
         } catch (EncryptionServiceException var6) {
            throw new IllegalArgumentException("Could not encrypt the password, " + var6);
         } catch (NullPointerException var7) {
            System.out.println("ERROR: Got an NPE(4).  Please fix CR186445");
            var7.printStackTrace();
            return;
         }

         this.customized.setPasswordEncrypted(encryptedValue);
      }
   }
}
