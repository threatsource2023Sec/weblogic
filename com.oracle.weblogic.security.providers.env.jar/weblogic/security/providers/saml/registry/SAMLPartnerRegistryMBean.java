package weblogic.security.providers.saml.registry;

import java.security.cert.X509Certificate;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NameListerMBean;
import weblogic.management.utils.NotFoundException;

public interface SAMLPartnerRegistryMBean extends StandardInterface, DescriptorBean, ProviderMBean, ImportMBean, ExportMBean, NameListerMBean {
   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   void registerCertificate(String var1, String var2) throws AlreadyExistsException, InvalidParameterException;

   void unregisterCertificate(String var1) throws NotFoundException, InvalidParameterException;

   String listCertificates(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   X509Certificate getCertificate(String var1) throws NotFoundException, InvalidParameterException;

   boolean certificateExists(String var1) throws InvalidParameterException;

   void copyToPEM(String var1, String var2) throws NotFoundException, InvalidParameterException;

   void copyToDER(String var1, String var2) throws NotFoundException, InvalidParameterException;

   String getName();
}
