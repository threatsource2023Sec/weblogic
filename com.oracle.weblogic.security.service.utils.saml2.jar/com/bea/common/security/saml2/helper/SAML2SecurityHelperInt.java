package com.bea.common.security.saml2.helper;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.providers.registry.Endpoint;
import com.bea.security.saml2.providers.registry.IdPPartner;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.MetadataPartner;
import com.bea.security.saml2.providers.registry.SPPartner;
import com.bea.security.saml2.providers.registry.WSSIdPPartner;
import com.bea.security.saml2.providers.registry.WSSSPPartner;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import javax.management.MBeanException;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface SAML2SecurityHelperInt {
   LoggerSpi getLoggerSPI();

   boolean listHaveCurrent(String var1) throws MBeanException, InvalidCursorException;

   String listGetCurrentName(String var1) throws MBeanException, InvalidCursorException;

   void listAdvance(String var1) throws MBeanException, InvalidCursorException;

   void listClose(String var1) throws MBeanException, InvalidCursorException;

   String listIdPPartners(String var1, int var2) throws MBeanException, InvalidParameterException, InvalidCursorException;

   Collection listAllIdPPartners() throws MBeanException, InvalidParameterException;

   String listSPPartners(String var1, int var2) throws MBeanException, InvalidParameterException, InvalidCursorException;

   Collection listAllSPPartners() throws MBeanException, InvalidParameterException;

   boolean idPPartnerExists(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   boolean spPartnerExists(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   IdPPartner getIdPPartner(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   SPPartner getSPPartner(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   void addIdPPartner(IdPPartner var1) throws MBeanException, InvalidParameterException, AlreadyExistsException, CreateException;

   void addSPPartner(SPPartner var1) throws MBeanException, InvalidParameterException, AlreadyExistsException, CreateException;

   void updateIdPPartner(IdPPartner var1) throws MBeanException, InvalidParameterException, NotFoundException;

   void updateSPPartner(SPPartner var1) throws MBeanException, InvalidParameterException, NotFoundException;

   void removeIdPPartner(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   void removeSPPartner(String var1) throws MBeanException, InvalidParameterException, NotFoundException;

   WebSSOIdPPartner newWebSSOIdPPartner() throws MBeanException;

   WebSSOSPPartner newWebSSOSPPartner() throws MBeanException;

   WSSIdPPartner newWSSIdPPartner() throws MBeanException;

   WSSSPPartner newWSSSPPartner() throws MBeanException;

   Endpoint newEndpoint() throws MBeanException;

   IndexedEndpoint newIndexedEndpoint() throws MBeanException;

   MetadataPartner consumeIdPPartnerMetadata(String var1) throws MBeanException, CreateException, InvalidParameterException;

   MetadataPartner consumeSPPartnerMetadata(String var1) throws MBeanException, CreateException, InvalidParameterException;

   X509Certificate readCertificateFromFile(String var1) throws InvalidParameterException;

   HashMap getPartnerMetaData(boolean var1);
}
