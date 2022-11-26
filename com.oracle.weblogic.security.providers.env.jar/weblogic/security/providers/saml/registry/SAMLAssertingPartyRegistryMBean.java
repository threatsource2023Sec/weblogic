package weblogic.security.providers.saml.registry;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;

public interface SAMLAssertingPartyRegistryMBean extends StandardInterface, DescriptorBean, SAMLPartnerRegistryMBean {
   String listAssertingParties(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   boolean assertingPartyExists(String var1) throws InvalidParameterException;

   SAMLAssertingParty getAssertingParty(String var1) throws NotFoundException, InvalidParameterException;

   void addAssertingParty(SAMLAssertingParty var1) throws InvalidParameterException, CreateException;

   void updateAssertingParty(SAMLAssertingParty var1) throws NotFoundException, InvalidParameterException;

   void removeAssertingParty(String var1) throws NotFoundException, InvalidParameterException;

   SAMLAssertingParty newAssertingParty();

   String getName();
}
