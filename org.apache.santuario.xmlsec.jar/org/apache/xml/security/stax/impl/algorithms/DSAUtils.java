package org.apache.xml.security.stax.impl.algorithms;

import java.io.IOException;

/** @deprecated */
@Deprecated
public class DSAUtils {
   public static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
      return org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertASN1toXMLDSIG(asn1Bytes);
   }

   public static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
      return org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertXMLDSIGtoASN1(xmldsigBytes);
   }
}
