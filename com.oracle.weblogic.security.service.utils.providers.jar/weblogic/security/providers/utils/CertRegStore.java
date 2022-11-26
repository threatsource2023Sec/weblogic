package weblogic.security.providers.utils;

import java.util.Collection;
import weblogic.management.utils.ErrorCollectionException;

public interface CertRegStore {
   WLSCertRegEntry getRegEntryByAlias(String var1, String var2);

   WLSCertRegEntry getRegEntryByIssuerDN(String var1, String var2, String var3);

   WLSCertRegEntry getRegEntryBySubjectDN(String var1, String var2);

   WLSCertRegEntry getRegEntryBySubjectKeyId(String var1, String var2);

   Collection getRegEntries(String var1, int var2);

   Collection getRegEntriesByAliasPattern(String var1, String var2, int var3);

   Collection getRegEntriesByRegistryPattern(String var1, String var2, int var3);

   void registerCertificate(WLSCertRegEntry var1) throws Exception;

   Collection registerCertificate(String var1, Collection var2, ErrorCollectionException var3);

   void unregisterCertificate(String var1, String var2) throws Throwable;

   void unregisterGroup(String var1) throws Throwable;
}
