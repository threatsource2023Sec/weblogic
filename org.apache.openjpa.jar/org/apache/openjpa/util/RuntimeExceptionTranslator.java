package org.apache.openjpa.util;

import java.io.Serializable;

public interface RuntimeExceptionTranslator extends Serializable {
   RuntimeException translate(RuntimeException var1);
}
