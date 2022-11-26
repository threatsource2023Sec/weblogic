package org.python.bouncycastle.est;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.est.AttrOrOID;
import org.python.bouncycastle.asn1.est.CsrAttrs;
import org.python.bouncycastle.util.Encodable;

public class CSRAttributesResponse implements Encodable {
   private final CsrAttrs csrAttrs;
   private final HashMap index;

   public CSRAttributesResponse(byte[] var1) throws ESTException {
      this(parseBytes(var1));
   }

   public CSRAttributesResponse(CsrAttrs var1) throws ESTException {
      this.csrAttrs = var1;
      this.index = new HashMap(var1.size());
      AttrOrOID[] var2 = var1.getAttrOrOIDs();

      for(int var3 = 0; var3 != var2.length; ++var3) {
         AttrOrOID var4 = var2[var3];
         if (var4.isOid()) {
            this.index.put(var4.getOid(), var4);
         } else {
            this.index.put(var4.getAttribute().getAttrType(), var4);
         }
      }

   }

   private static CsrAttrs parseBytes(byte[] var0) throws ESTException {
      try {
         return CsrAttrs.getInstance(ASN1Primitive.fromByteArray(var0));
      } catch (Exception var2) {
         throw new ESTException("malformed data: " + var2.getMessage(), var2);
      }
   }

   public boolean hasRequirement(ASN1ObjectIdentifier var1) {
      return this.index.containsKey(var1);
   }

   public boolean isAttribute(ASN1ObjectIdentifier var1) {
      if (this.index.containsKey(var1)) {
         return !((AttrOrOID)this.index.get(var1)).isOid();
      } else {
         return false;
      }
   }

   public boolean isEmpty() {
      return this.csrAttrs.size() == 0;
   }

   public Collection getRequirements() {
      return this.index.keySet();
   }

   public byte[] getEncoded() throws IOException {
      return this.csrAttrs.getEncoded();
   }
}
