package org.apache.xml.security.stax.impl.algorithms;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.xml.security.exceptions.XMLSecurityException;

public interface SignatureAlgorithm {
   void engineUpdate(byte[] var1) throws XMLSecurityException;

   void engineUpdate(byte var1) throws XMLSecurityException;

   void engineUpdate(byte[] var1, int var2, int var3) throws XMLSecurityException;

   void engineInitSign(Key var1) throws XMLSecurityException;

   void engineInitSign(Key var1, SecureRandom var2) throws XMLSecurityException;

   void engineInitSign(Key var1, AlgorithmParameterSpec var2) throws XMLSecurityException;

   byte[] engineSign() throws XMLSecurityException;

   void engineInitVerify(Key var1) throws XMLSecurityException;

   boolean engineVerify(byte[] var1) throws XMLSecurityException;

   void engineSetParameter(AlgorithmParameterSpec var1) throws XMLSecurityException;
}
