package com.rsa.certj.spi.path;

import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Vector;

/** @deprecated */
public interface CertPathInterface {
   boolean buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, Vector var6) throws NotSupportedException, CertPathException;

   CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5) throws NotSupportedException, CertPathException;

   CertPathResult buildCertPath(CertPathCtx var1, Object var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7) throws NotSupportedException, CertPathException;

   void getNextCertInPath(CertPathCtx var1, Object var2, Vector var3) throws NotSupportedException, CertPathException;

   boolean validateCertificate(CertPathCtx var1, Certificate var2, JSAFE_PublicKey var3) throws NotSupportedException, CertPathException;
}
