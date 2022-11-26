package org.python.bouncycastle.jcajce.provider.config;

import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.python.bouncycastle.jce.spec.ECParameterSpec;

public interface ProviderConfiguration {
   ECParameterSpec getEcImplicitlyCa();

   DHParameterSpec getDHDefaultParameters(int var1);

   Set getAcceptableNamedCurves();

   Map getAdditionalECParameters();
}
