package com.bea.security.saml2.providers;

import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.MetadataPartner;
import com.bea.security.saml2.providers.registry.SPPartner;
import com.bea.security.saml2.providers.registry.WSSSPPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface SAML2SPPartnerRegistryMBean extends StandardInterface, DescriptorBean, SAML2PartnerRegistryMBean {
   String listSPPartners(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   boolean spPartnerExists(String var1) throws InvalidParameterException;

   SPPartner getSPPartner(String var1) throws NotFoundException, InvalidParameterException;

   void addSPPartner(SPPartner var1) throws InvalidParameterException, AlreadyExistsException, CreateException;

   void updateSPPartner(SPPartner var1) throws NotFoundException, InvalidParameterException;

   void removeSPPartner(String var1) throws NotFoundException, InvalidParameterException;

   WebSSOSPPartner newWebSSOSPPartner();

   WSSSPPartner newWSSSPPartner();

   Endpoint newEndpoint();

   IndexedEndpoint newIndexedEndpoint();

   MetadataPartner consumeSPPartnerMetadata(String var1) throws CreateException, InvalidParameterException;

   String getName();
}
