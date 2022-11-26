package org.cryptacular.asn;

import org.cryptacular.EncodingException;

public interface ASN1Decoder {
   Object decode(byte[] var1, Object... var2) throws EncodingException;
}
