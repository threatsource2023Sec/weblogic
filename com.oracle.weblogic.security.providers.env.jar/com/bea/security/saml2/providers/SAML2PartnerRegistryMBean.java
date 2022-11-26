package com.bea.security.saml2.providers;

import java.security.cert.X509Certificate;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NameListerMBean;

public interface SAML2PartnerRegistryMBean extends StandardInterface, DescriptorBean, ProviderMBean, NameListerMBean {
   X509Certificate loadCertificate(String var1) throws InvalidParameterException;

   String getName();
}
