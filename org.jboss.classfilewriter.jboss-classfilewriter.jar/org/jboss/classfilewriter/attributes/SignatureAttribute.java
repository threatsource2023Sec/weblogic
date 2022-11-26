package org.jboss.classfilewriter.attributes;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class SignatureAttribute extends Attribute {
   public static final String NAME = "Signature";
   private final String signature;
   private final int signatureIndex;

   public SignatureAttribute(ConstPool constPool, String signature) {
      super("Signature", constPool);
      this.signature = signature;
      this.signatureIndex = constPool.addUtf8Entry(signature);
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeInt(2);
      stream.writeShort(this.signatureIndex);
   }
}
