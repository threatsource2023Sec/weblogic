package org.python.bouncycastle.jcajce.provider.config;

import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public interface ConfigurableProvider {
   String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";
   String EC_IMPLICITLY_CA = "ecImplicitlyCa";
   String THREAD_LOCAL_DH_DEFAULT_PARAMS = "threadLocalDhDefaultParams";
   String DH_DEFAULT_PARAMS = "DhDefaultParams";
   String ACCEPTABLE_EC_CURVES = "acceptableEcCurves";
   String ADDITIONAL_EC_PARAMETERS = "additionalEcParameters";

   void setParameter(String var1, Object var2);

   void addAlgorithm(String var1, String var2);

   void addAlgorithm(String var1, ASN1ObjectIdentifier var2, String var3);

   boolean hasAlgorithm(String var1, String var2);

   void addKeyInfoConverter(ASN1ObjectIdentifier var1, AsymmetricKeyInfoConverter var2);

   void addAttributes(String var1, Map var2);
}
