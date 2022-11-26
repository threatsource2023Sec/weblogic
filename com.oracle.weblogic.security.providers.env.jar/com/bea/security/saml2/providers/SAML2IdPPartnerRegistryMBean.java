package com.bea.security.saml2.providers;

import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.IdPPartner;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.MetadataPartner;
import com.bea.security.saml2.providers.registry.WSSIdPPartner;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface SAML2IdPPartnerRegistryMBean extends StandardInterface, DescriptorBean, SAML2PartnerRegistryMBean {
   String listIdPPartners(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   boolean idPPartnerExists(String var1) throws InvalidParameterException;

   IdPPartner getIdPPartner(String var1) throws NotFoundException, InvalidParameterException;

   void addIdPPartner(IdPPartner var1) throws InvalidParameterException, AlreadyExistsException, CreateException;

   void updateIdPPartner(IdPPartner var1) throws NotFoundException, InvalidParameterException;

   void removeIdPPartner(String var1) throws NotFoundException, InvalidParameterException;

   WebSSOIdPPartner newWebSSOIdPPartner();

   WSSIdPPartner newWSSIdPPartner();

   Endpoint newEndpoint();

   IndexedEndpoint newIndexedEndpoint();

   MetadataPartner consumeIdPPartnerMetadata(String var1) throws CreateException, InvalidParameterException;

   String getName();
}
