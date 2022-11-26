package com.bea.security.xacml.store;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.ConcurrentHashMap;

public abstract class Record {
   private URI identifier;
   private String version;
   private PolicyFinder finder;
   private AbstractPolicy referent;
   private Collection designatorMatches;
   private Map evals;

   public Record(URI identifier, String version, PolicyFinder finder) {
      this(identifier, version, finder, (Collection)null);
   }

   public Record(URI identifier, String version, PolicyFinder finder, Collection designatorMatches) {
      this(identifier, version, finder, (AbstractPolicy)null, designatorMatches);
   }

   protected Record(URI identifier, String version, PolicyFinder finder, AbstractPolicy referent, Collection designatorMatches) {
      this.identifier = identifier;
      this.version = version;
      this.finder = finder;
      this.referent = referent;
      this.designatorMatches = designatorMatches;
      this.evals = new ConcurrentHashMap();
   }

   public abstract IdReference getReference();

   public URI getIdentifier() {
      return this.identifier;
   }

   public String getVersion() {
      return this.version;
   }

   public PolicyFinder getFinder() {
      return this.finder;
   }

   public AbstractPolicy getReferent() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (this.referent == null) {
         this.referent = this.finder.find(this.getReference(), (Iterator)null);
      }

      return this.referent;
   }

   public Collection getDesignatorMatches() {
      return this.designatorMatches;
   }

   public void setEvaluator(Object o, PolicyEvaluator pe) {
      this.evals.put(o, pe);
   }

   public PolicyEvaluator getEvaluator(Object o) {
      return (PolicyEvaluator)this.evals.get(o);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Record)) {
         return false;
      } else {
         Record o = (Record)other;
         return (this.identifier == o.identifier || this.identifier != null && this.identifier.equals(o.identifier)) && (this.version == o.version || this.version != null && this.version.equals(o.version)) && (this.finder == o.finder || this.finder != null && this.finder.equals(o.finder)) && CollectionUtil.equals(this.designatorMatches, o.designatorMatches);
      }
   }

   public int hashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.identifier);
      result = HashCodeUtil.hash(result, this.version);
      result = HashCodeUtil.hash(result, this.finder);
      result = HashCodeUtil.hash(result, this.designatorMatches);
      return result;
   }
}
