package org.python.bouncycastle.cms.jcajce;

import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KeyTransRecipient;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;

public abstract class JceKeyTransRecipient implements KeyTransRecipient {
   private PrivateKey recipientKey;
   protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
   protected EnvelopedDataHelper contentHelper;
   protected Map extraMappings;
   protected boolean validateKeySize;
   protected boolean unwrappedKeyMustBeEncodable;

   public JceKeyTransRecipient(PrivateKey var1) {
      this.contentHelper = this.helper;
      this.extraMappings = new HashMap();
      this.validateKeySize = false;
      this.recipientKey = var1;
   }

   public JceKeyTransRecipient setProvider(Provider var1) {
      this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var1));
      this.contentHelper = this.helper;
      return this;
   }

   public JceKeyTransRecipient setProvider(String var1) {
      this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(var1));
      this.contentHelper = this.helper;
      return this;
   }

   public JceKeyTransRecipient setAlgorithmMapping(ASN1ObjectIdentifier var1, String var2) {
      this.extraMappings.put(var1, var2);
      return this;
   }

   public JceKeyTransRecipient setContentProvider(Provider var1) {
      this.contentHelper = CMSUtils.createContentHelper(var1);
      return this;
   }

   public JceKeyTransRecipient setMustProduceEncodableUnwrappedKey(boolean var1) {
      this.unwrappedKeyMustBeEncodable = var1;
      return this;
   }

   public JceKeyTransRecipient setContentProvider(String var1) {
      this.contentHelper = CMSUtils.createContentHelper(var1);
      return this;
   }

   public JceKeyTransRecipient setKeySizeValidation(boolean var1) {
      this.validateKeySize = var1;
      return this;
   }

   protected Key extractSecretKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      JceAsymmetricKeyUnwrapper var4 = this.helper.createAsymmetricUnwrapper(var1, this.recipientKey).setMustProduceEncodableUnwrappedKey(this.unwrappedKeyMustBeEncodable);
      if (!this.extraMappings.isEmpty()) {
         Iterator var5 = this.extraMappings.keySet().iterator();

         while(var5.hasNext()) {
            ASN1ObjectIdentifier var6 = (ASN1ObjectIdentifier)var5.next();
            var4.setAlgorithmMapping(var6, (String)this.extraMappings.get(var6));
         }
      }

      try {
         Key var8 = this.helper.getJceKey(var2.getAlgorithm(), var4.generateUnwrappedKey(var2, var3));
         if (this.validateKeySize) {
            this.helper.keySizeCheck(var2, var8);
         }

         return var8;
      } catch (OperatorException var7) {
         throw new CMSException("exception unwrapping key: " + var7.getMessage(), var7);
      }
   }
}
