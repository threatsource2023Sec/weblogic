package com.bea.security.providers.xacml.store;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicyMetaDataException;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyStore;
import java.util.List;

public interface MetaDataPolicyStore extends PolicyStore {
   void addPolicy(Policy var1, int var2, PolicyMetaData var3) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   void addPolicySet(PolicySet var1, int var2, PolicyMetaData var3) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   void setPolicy(Policy var1, int var2, PolicyMetaData var3) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   void setPolicySet(PolicySet var1, int var2, PolicyMetaData var3) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   PolicyMetaData getPolicyMetaDataEntry(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   PolicyMetaData getPolicySetMetaDataEntry(URI var1, String var2) throws PolicyStoreException, URISyntaxException;

   List readPolicy(PolicyMetaData var1) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   List readPolicySet(PolicyMetaData var1) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException;

   void setMetaDataEntry(String var1, String var2) throws PolicyStoreException;

   String getMetaDataEntry(String var1) throws PolicyStoreException;

   List readAllMetaDataEntries() throws PolicyStoreException;
}
