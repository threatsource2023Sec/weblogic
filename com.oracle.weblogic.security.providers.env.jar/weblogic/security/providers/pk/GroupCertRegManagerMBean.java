package weblogic.security.providers.pk;

import java.security.cert.X509Certificate;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NameListerMBean;
import weblogic.management.utils.NotFoundException;

public interface GroupCertRegManagerMBean extends StandardInterface, DescriptorBean, ProviderMBean, ImportMBean, ExportMBean, NameListerMBean {
   void registerCertificate(String[] var1, String var2, String var3) throws ErrorCollectionException, InvalidParameterException;

   void addCertificateToTrustGroups(String[] var1, String var2) throws NotFoundException, ErrorCollectionException, InvalidParameterException;

   void unregisterCertificate(String[] var1, String var2) throws ErrorCollectionException, InvalidParameterException;

   void removeCertificate(String var1) throws NotFoundException, InvalidParameterException;

   void createTrustGroup(String var1) throws AlreadyExistsException, InvalidParameterException;

   void removeTrustGroup(String var1) throws NotFoundException, InvalidParameterException;

   String listTrustGroups(String var1, int var2) throws InvalidCursorException, InvalidParameterException;

   String listAliasesByTrustGroup(String var1, String var2, int var3) throws InvalidCursorException, InvalidParameterException;

   boolean trustGroupExists(String var1) throws InvalidParameterException;

   X509Certificate getCertificate(String var1, String var2) throws NotFoundException, InvalidParameterException;

   String[] getTrustGroups(String var1) throws InvalidParameterException;

   boolean aliasExists(String var1, String var2) throws InvalidParameterException;

   boolean isAliasRegistered(String var1) throws InvalidParameterException;

   void copyToPEM(String var1, String var2, String var3) throws NotFoundException, InvalidParameterException;

   void copyToDER(String var1, String var2, String var3) throws NotFoundException, InvalidParameterException;

   void copyToJKS(String var1, String var2) throws NotFoundException, InvalidParameterException, ErrorCollectionException;

   void copyFromJKS(String var1, String var2) throws NotFoundException, InvalidParameterException, ErrorCollectionException;

   String getName();
}
