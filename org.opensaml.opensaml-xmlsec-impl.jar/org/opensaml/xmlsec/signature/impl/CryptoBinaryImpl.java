package org.opensaml.xmlsec.signature.impl;

import com.google.common.base.Strings;
import java.math.BigInteger;
import java.util.Objects;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.CryptoBinary;

public class CryptoBinaryImpl extends XSBase64BinaryImpl implements CryptoBinary {
   private BigInteger bigIntValue;

   protected CryptoBinaryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public BigInteger getValueBigInt() {
      if (this.bigIntValue == null && !Strings.isNullOrEmpty(this.getValue())) {
         this.bigIntValue = KeyInfoSupport.decodeBigIntegerFromCryptoBinary(this.getValue());
      }

      return this.bigIntValue;
   }

   public void setValueBigInt(BigInteger bigInt) {
      if (bigInt == null) {
         this.setValue((String)null);
      } else {
         this.setValue(KeyInfoSupport.encodeCryptoBinaryFromBigInteger(bigInt));
      }

      this.bigIntValue = bigInt;
   }

   public void setValue(String newValue) {
      if (this.bigIntValue != null && (!Objects.equals(this.getValue(), newValue) || newValue == null)) {
         this.bigIntValue = null;
      }

      super.setValue(newValue);
   }
}
