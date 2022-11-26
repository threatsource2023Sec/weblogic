package org.python.bouncycastle.est.jcajce;

import java.security.Provider;
import java.security.SecureRandom;
import org.python.bouncycastle.est.HttpAuth;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class JcaHttpAuthBuilder {
   private JcaDigestCalculatorProviderBuilder providerBuilder;
   private final String realm;
   private final String username;
   private final char[] password;
   private SecureRandom random;

   public JcaHttpAuthBuilder(String var1, char[] var2) {
      this((String)null, var1, var2);
   }

   public JcaHttpAuthBuilder(String var1, String var2, char[] var3) {
      this.providerBuilder = new JcaDigestCalculatorProviderBuilder();
      this.random = new SecureRandom();
      this.realm = var1;
      this.username = var2;
      this.password = var3;
   }

   public JcaHttpAuthBuilder setProvider(Provider var1) {
      this.providerBuilder.setProvider(var1);
      return this;
   }

   public JcaHttpAuthBuilder setProvider(String var1) {
      this.providerBuilder.setProvider(var1);
      return this;
   }

   public JcaHttpAuthBuilder setNonceGenerator(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public HttpAuth build() throws OperatorCreationException {
      return new HttpAuth(this.realm, this.username, this.password, this.random, this.providerBuilder.build());
   }
}
