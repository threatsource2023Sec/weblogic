package com.rsa.certj.spi.random;

import com.rsa.certj.NotSupportedException;
import com.rsa.jsafe.JSAFE_SecureRandom;

/** @deprecated */
public interface RandomInterface {
   JSAFE_SecureRandom getRandomObject() throws NotSupportedException, RandomException;

   void updateRandom() throws NotSupportedException, RandomException;
}
