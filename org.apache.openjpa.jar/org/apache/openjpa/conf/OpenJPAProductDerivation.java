package org.apache.openjpa.conf;

import java.util.Map;
import org.apache.openjpa.lib.conf.ProductDerivation;

public interface OpenJPAProductDerivation extends ProductDerivation {
   int TYPE_SPEC = 0;
   int TYPE_STORE = 200;
   int TYPE_SPEC_STORE = 300;
   int TYPE_PRODUCT_STORE = 400;

   void putBrokerFactoryAliases(Map var1);
}
