package com.rsa.certj;

import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIInterface;
import com.rsa.certj.spi.pki.PKIMessage;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.PKIResult;
import com.rsa.certj.spi.pki.POPGenerationInfo;
import com.rsa.certj.spi.pki.POPValidationInfo;
import com.rsa.certj.spi.pki.ProtectInfo;
import com.rsa.jsafe.JSAFE_PrivateKey;

/** @deprecated */
public final class PKIService extends Service {
   /** @deprecated */
   public PKIService(CertJ var1) {
      super(var1);
   }

   /** @deprecated */
   public PKIResponseMessage readCertificationResponseMessage(byte[] var1, ProtectInfo var2) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var3 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.readCertificationResponseMessage: response cannot be null.");
      } else {
         try {
            return var3.readCertificationResponseMessage(var1, var2);
         } catch (NotSupportedException var5) {
            throw new NoServiceException("PKIService.readCertificationResponseMessage: Does not support this service.");
         }
      }
   }

   /** @deprecated */
   public byte[] writeCertificationRequestMessage(PKIRequestMessage var1, ProtectInfo var2) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var3 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.writeCertificationRequestMessage: request cannot be null.");
      } else {
         try {
            return var3.writeCertificationRequestMessage(var1, var2);
         } catch (NotSupportedException var5) {
            throw new NoServiceException("PKIService.writeCertificationRequestMessage: Does not support this service.");
         }
      }
   }

   /** @deprecated */
   public PKIResponseMessage requestCertification(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws InvalidParameterException, NoServiceException, PKIException {
      return this.sendRequest(var1, var2, var3);
   }

   /** @deprecated */
   public PKIResponseMessage sendRequest(PKIRequestMessage var1, ProtectInfo var2, DatabaseService var3) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var4 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.sendRequest: request cannot be null.");
      } else {
         try {
            return var4.sendRequest(var1, var2, var3);
         } catch (NotSupportedException var6) {
            throw new NoServiceException("PKIService.sendRequest: does not support this service.");
         }
      }
   }

   /** @deprecated */
   public PKIResult sendMessage(byte[] var1) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var2 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.sendMessage: request cannot be null.");
      } else {
         try {
            return var2.sendMessage(var1);
         } catch (NotSupportedException var4) {
            throw new NoServiceException("PKIService.sendMessage: Does not support this service.");
         }
      }
   }

   /** @deprecated */
   public void generateProofOfPossession(PKIRequestMessage var1, JSAFE_PrivateKey var2, POPGenerationInfo var3) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var4 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.generateProofOfPossession: request cannot be null.");
      } else {
         try {
            var4.generateProofOfPossession(var1, var2, var3);
         } catch (NotSupportedException var6) {
            throw new NoServiceException("PKIService.generateProofOfPossession: Does not support this service.");
         }
      }
   }

   /** @deprecated */
   public boolean validateProofOfPossession(PKIMessage var1, POPValidationInfo var2) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var3 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.validateProofOfPossession: message cannot be null.");
      } else {
         try {
            return var3.validateProofOfPossession(var1, var2);
         } catch (NotSupportedException var5) {
            throw new NoServiceException("PKIService.validateProofOfPossession: Does not support this service.");
         }
      }
   }

   /** @deprecated */
   public void provideProofOfPossession(PKIRequestMessage var1, int var2, byte[] var3) throws InvalidParameterException, NoServiceException, PKIException {
      PKIInterface var4 = this.getProvider();
      if (var1 == null) {
         throw new InvalidParameterException("PKIService.provideProofOfPossession: request cannot be null.");
      } else {
         try {
            var4.provideProofOfPossession(var1, var2, var3);
         } catch (NotSupportedException var6) {
            throw new NoServiceException("PKIService.provideProofOfPossession: Does not support this service.");
         }
      }
   }

   private PKIInterface getProvider() throws NoServiceException {
      if (this.getProviderCount() == 0) {
         throw new NoServiceException("PKIService.getProvider: no PKI provider bound to this object.");
      } else {
         return (PKIInterface)this.getProviderAt(0);
      }
   }
}
