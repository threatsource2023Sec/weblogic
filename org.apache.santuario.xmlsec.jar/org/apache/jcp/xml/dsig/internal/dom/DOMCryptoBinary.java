package org.apache.jcp.xml.dsig.internal.dom;

import java.math.BigInteger;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class DOMCryptoBinary extends DOMStructure {
   private final BigInteger bigNum;
   private final String value;

   public DOMCryptoBinary(BigInteger bigNum) {
      if (bigNum == null) {
         throw new NullPointerException("bigNum is null");
      } else {
         this.bigNum = bigNum;
         byte[] bytes = XMLUtils.getBytes(bigNum, bigNum.bitLength());
         this.value = XMLUtils.encodeToString(bytes);
      }
   }

   public DOMCryptoBinary(Node cbNode) throws MarshalException {
      this.value = cbNode.getNodeValue();

      try {
         this.bigNum = new BigInteger(1, XMLUtils.decode(((Text)cbNode).getData()));
      } catch (Exception var3) {
         throw new MarshalException(var3);
      }
   }

   public BigInteger getBigNum() {
      return this.bigNum;
   }

   public void marshal(Node parent, String prefix, DOMCryptoContext context) throws MarshalException {
      parent.appendChild(DOMUtils.getOwnerDocument(parent).createTextNode(this.value));
   }
}
