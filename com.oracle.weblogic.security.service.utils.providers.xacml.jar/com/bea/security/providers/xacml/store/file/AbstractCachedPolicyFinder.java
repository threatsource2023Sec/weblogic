package com.bea.security.providers.xacml.store.file;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.security.providers.xacml.BasicEvaluationCtx;
import com.bea.security.providers.xacml.entitlement.EntitlementConverter;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.role.RoleMatchUtil;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyRecord;
import com.bea.security.xacml.store.PolicySetRecord;
import com.bea.security.xacml.store.Record;
import java.util.Collection;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.SecondChanceCacheMap;

abstract class AbstractCachedPolicyFinder implements CachedPolicyFinder {
   private final SecondChanceCacheMap policyCache;
   protected static final String ALL_ROLES = "";
   protected final LoggerSpi log;
   protected boolean debugEnabled;
   protected final ResourceMatchUtil rmu;
   protected final RoleMatchUtil romu;
   protected final PolicyFinder policyFinder;
   protected final EntitlementConverter converter;

   public AbstractCachedPolicyFinder(LoggerSpi logger, int cacheSize, PolicyFinder finder) throws URISyntaxException {
      this.log = logger;
      this.debugEnabled = this.log != null && this.log.isDebugEnabled();
      this.policyCache = new SecondChanceCacheMap(cacheSize);
      this.rmu = new ResourceMatchUtil();
      this.romu = new RoleMatchUtil();
      this.converter = new EntitlementConverter(this.log);
      this.policyFinder = finder;
      if (this.debugEnabled) {
         this.log.debug("Creating AbstractCachedPolicyFinder with cache size=" + cacheSize);
      }

   }

   public Object findPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (context instanceof BasicEvaluationCtx) {
         Resource r = ((BasicEvaluationCtx)context).getResource();
         if (this.debugEnabled) {
            this.log.debug("Looking for policies for Resource: " + r);
         }

         return this.retrieveCachedEntry(r);
      } else {
         return null;
      }
   }

   private Object retrieveCachedEntry(Resource r) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      Object entry = this.policyCache.get(r);
      if (entry == null) {
         entry = this.loadPolicyForResource(r);
         this.policyCache.put(r, entry);
      }

      return entry;
   }

   protected abstract Object loadPolicyForResource(Resource var1) throws PolicyStoreException, DocumentParseException, URISyntaxException;

   public void reset() {
      this.policyCache.clear();
   }

   protected Record generateRecord(PolicySetMember psm, Collection kmc) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      IdReference ref;
      AbstractPolicy data;
      if (psm instanceof Index.Entry) {
         ref = ((Index.Entry)psm).getIdReference();
         data = ((Index.Entry)psm).getData();
      } else if (psm instanceof IdReference) {
         ref = (IdReference)psm;
         data = null;
      } else {
         ref = ((AbstractPolicy)psm).getReference();
         data = (AbstractPolicy)psm;
      }

      return (Record)(ref instanceof PolicyIdReference ? new PolicyRecord(ref.getReference(), ref.getVersion(), this.policyFinder, (Policy)data, kmc) : new PolicySetRecord(ref.getReference(), ref.getVersion(), this.policyFinder, (PolicySet)data, kmc));
   }
}
