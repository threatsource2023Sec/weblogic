package org.apache.xml.security.algorithms;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.xml.security.signature.XMLSignatureException;
import org.w3c.dom.Element;

public abstract class SignatureAlgorithmSpi {
   protected abstract String engineGetURI();

   protected abstract String engineGetJCEAlgorithmString();

   protected abstract String engineGetJCEProviderName();

   protected abstract void engineUpdate(byte[] var1) throws XMLSignatureException;

   protected abstract void engineUpdate(byte var1) throws XMLSignatureException;

   protected abstract void engineUpdate(byte[] var1, int var2, int var3) throws XMLSignatureException;

   protected abstract void engineInitSign(Key var1) throws XMLSignatureException;

   protected abstract void engineInitSign(Key var1, SecureRandom var2) throws XMLSignatureException;

   protected abstract void engineInitSign(Key var1, AlgorithmParameterSpec var2) throws XMLSignatureException;

   protected abstract byte[] engineSign() throws XMLSignatureException;

   protected abstract void engineInitVerify(Key var1) throws XMLSignatureException;

   protected abstract boolean engineVerify(byte[] var1) throws XMLSignatureException;

   protected abstract void engineSetParameter(AlgorithmParameterSpec var1) throws XMLSignatureException;

   protected void engineGetContextFromElement(Element element) {
   }

   protected abstract void engineSetHMACOutputLength(int var1) throws XMLSignatureException;

   public void reset() {
   }
}
