package weblogic.security.providers.saml.registry;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface SAMLRelyingPartyRegistryMBean extends StandardInterface, DescriptorBean, SAMLPartnerRegistryMBean {
   String listRelyingParties(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   boolean relyingPartyExists(String var1) throws InvalidParameterException;

   SAMLRelyingParty getRelyingParty(String var1) throws NotFoundException, InvalidParameterException;

   void addRelyingParty(SAMLRelyingParty var1) throws InvalidParameterException, CreateException;

   void updateRelyingParty(SAMLRelyingParty var1) throws NotFoundException, InvalidParameterException;

   void removeRelyingParty(String var1) throws NotFoundException, InvalidParameterException;

   SAMLRelyingParty newRelyingParty();

   String getName();
}
