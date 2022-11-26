package org.python.bouncycastle.openssl.jcajce;

import java.io.IOException;
import java.io.Writer;
import org.python.bouncycastle.openssl.PEMEncryptor;
import org.python.bouncycastle.util.io.pem.PemGenerationException;
import org.python.bouncycastle.util.io.pem.PemObjectGenerator;
import org.python.bouncycastle.util.io.pem.PemWriter;

public class JcaPEMWriter extends PemWriter {
   public JcaPEMWriter(Writer var1) {
      super(var1);
   }

   public void writeObject(Object var1) throws IOException {
      this.writeObject(var1, (PEMEncryptor)null);
   }

   public void writeObject(Object var1, PEMEncryptor var2) throws IOException {
      try {
         super.writeObject(new JcaMiscPEMGenerator(var1, var2));
      } catch (PemGenerationException var4) {
         if (var4.getCause() instanceof IOException) {
            throw (IOException)var4.getCause();
         } else {
            throw var4;
         }
      }
   }

   public void writeObject(PemObjectGenerator var1) throws IOException {
      super.writeObject(var1);
   }
}
