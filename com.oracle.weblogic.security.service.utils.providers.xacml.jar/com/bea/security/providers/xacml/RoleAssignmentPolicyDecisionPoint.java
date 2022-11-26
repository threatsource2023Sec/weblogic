package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.security.providers.xacml.store.RoleAssignmentPolicyRegistry;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.PolicyDecision;
import com.bea.security.xacml.PolicyDecisionPoint;
import com.bea.security.xacml.PolicyEvaluatorItem;
import com.bea.security.xacml.PolicyStoreException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RoleAssignmentPolicyDecisionPoint extends PolicyDecisionPoint {
   private static final String ACTIONID_ATTR = "urn:oasis:names:tc:xacml:1.0:action:action-id";
   private static final String ROLE_ATTR = "urn:oasis:names:tc:xacml:2.0:subject:role";
   private static final String HAS_PRIVILEDGES_OF_ROLE = "urn:oasis:names:tc:xacml:2.0:actions:hasPrivilegesOfRole";
   private Configuration config;
   private final URI ACTIONID_ATTR_URI;
   private final URI ROLE_ATTR_URI;
   private final AnyURIAttribute HAS_PRIVILEDGES_OF_ROLE_URI_VALUE;
   private final URI ANYURI_TYPE;
   private final URI STRING_TYPE;

   public RoleAssignmentPolicyDecisionPoint(Configuration config) throws URISyntaxException {
      super(config);
      this.config = config;

      try {
         this.ACTIONID_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");
         this.ROLE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:subject:role");
         this.HAS_PRIVILEDGES_OF_ROLE_URI_VALUE = new AnyURIAttribute(new URI("urn:oasis:names:tc:xacml:2.0:actions:hasPrivilegesOfRole"));
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      this.ANYURI_TYPE = Type.ANY_URI.getDataType();
      this.STRING_TYPE = Type.STRING.getDataType();
   }

   public RoleAssignmentPolicyDecisionPoint(Configuration config, URI policyCombiningAlgId) throws URISyntaxException {
      super(config, policyCombiningAlgId);
      this.config = config;

      try {
         this.ACTIONID_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");
         this.ROLE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:subject:role");
         this.HAS_PRIVILEDGES_OF_ROLE_URI_VALUE = new AnyURIAttribute(new URI("urn:oasis:names:tc:xacml:2.0:actions:hasPrivilegesOfRole"));
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }

      this.ANYURI_TYPE = Type.ANY_URI.getDataType();
      this.STRING_TYPE = Type.STRING.getDataType();
   }

   public Map getRoles(EvaluationCtx context) throws IndeterminateEvaluationException {
      try {
         final Map map = ((RoleAssignmentPolicyRegistry)this.config.getPolicyRegistry()).findRoleAssignmentPolicy(context);
         if (map != null) {
            final PolicyDecisionPoint.PolicyEvaluatorSet peSet = new PolicyDecisionPoint.PolicyEvaluatorSet(context);
            if (map.containsKey((Object)null)) {
               final Set nullRec = (Set)map.get((Object)null);
               return new Map() {
                  private Map inner = new HashMap();
                  private boolean keysLoaded = false;
                  private int extraItems = 0;

                  private PolicyEvaluatorItem processKey(String key) {
                     if (!this.inner.containsKey(key)) {
                        Object merged;
                        if (map.containsKey(key)) {
                           merged = new HashSet();
                           ((Set)merged).addAll(nullRec);
                           ((Set)merged).addAll((Collection)map.get(key));
                        } else {
                           merged = nullRec;
                           ++this.extraItems;
                        }

                        PolicyEvaluatorItem n = peSet.createItem((Set)merged);
                        this.inner.put(key, n);
                        return n;
                     } else {
                        return null;
                     }
                  }

                  private void init() {
                     if (!this.keysLoaded) {
                        Iterator keys = map.keySet().iterator();

                        while(keys.hasNext()) {
                           this.processKey((String)keys.next());
                        }

                        this.keysLoaded = true;
                     }

                  }

                  public int size() {
                     return map.size() + this.extraItems;
                  }

                  public boolean isEmpty() {
                     if (!map.isEmpty()) {
                        return false;
                     } else {
                        return this.extraItems == 0;
                     }
                  }

                  public boolean containsKey(Object key) {
                     return key instanceof String;
                  }

                  public boolean containsValue(Object value) {
                     this.init();
                     return this.inner.containsValue(value);
                  }

                  public PolicyEvaluatorItem get(Object key) {
                     PolicyEvaluatorItem pei = (PolicyEvaluatorItem)this.inner.get(key);
                     if (pei == null) {
                        pei = this.processKey((String)key);
                     }

                     return pei;
                  }

                  public PolicyEvaluatorItem put(String key, PolicyEvaluatorItem value) {
                     throw new UnsupportedOperationException();
                  }

                  public PolicyEvaluatorItem remove(Object key) {
                     throw new UnsupportedOperationException();
                  }

                  public void putAll(Map t) {
                     throw new UnsupportedOperationException();
                  }

                  public void clear() {
                     throw new UnsupportedOperationException();
                  }

                  public Set keySet() {
                     this.init();
                     return this.inner.keySet();
                  }

                  public Collection values() {
                     this.init();
                     return this.inner.values();
                  }

                  public Set entrySet() {
                     this.init();
                     return this.inner.entrySet();
                  }
               };
            } else {
               return new Map() {
                  private Map inner = new HashMap();
                  private boolean keysLoaded = false;

                  private PolicyEvaluatorItem processKey(String key) {
                     if (!this.inner.containsKey(key)) {
                        PolicyEvaluatorItem n = peSet.createItem((Set)map.get(key));
                        this.inner.put(key, n);
                        return n;
                     } else {
                        return null;
                     }
                  }

                  private void init() {
                     if (!this.keysLoaded) {
                        Iterator keys = map.keySet().iterator();

                        while(keys.hasNext()) {
                           this.processKey((String)keys.next());
                        }

                        this.keysLoaded = true;
                     }

                  }

                  public int size() {
                     return map.size();
                  }

                  public boolean isEmpty() {
                     return map.isEmpty();
                  }

                  public boolean containsKey(Object key) {
                     return map.containsKey(key);
                  }

                  public boolean containsValue(Object value) {
                     this.init();
                     return this.inner.containsValue(value);
                  }

                  public PolicyEvaluatorItem get(Object key) {
                     PolicyEvaluatorItem pei = (PolicyEvaluatorItem)this.inner.get(key);
                     if (pei == null && map.containsKey(key)) {
                        pei = this.processKey((String)key);
                     }

                     return pei;
                  }

                  public PolicyEvaluatorItem put(String key, PolicyEvaluatorItem value) {
                     throw new UnsupportedOperationException();
                  }

                  public PolicyEvaluatorItem remove(Object key) {
                     throw new UnsupportedOperationException();
                  }

                  public void putAll(Map t) {
                     throw new UnsupportedOperationException();
                  }

                  public void clear() {
                     throw new UnsupportedOperationException();
                  }

                  public Set keySet() {
                     return map.keySet();
                  }

                  public Collection values() {
                     this.init();
                     return this.inner.values();
                  }

                  public Set entrySet() {
                     this.init();
                     return this.inner.entrySet();
                  }
               };
            }
         } else {
            return null;
         }
      } catch (URISyntaxException var5) {
         throw new IndeterminateEvaluationException(var5);
      } catch (DocumentParseException var6) {
         throw new IndeterminateEvaluationException(var6);
      } catch (PolicyStoreException var7) {
         throw new IndeterminateEvaluationException(var7);
      }
   }

   public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      Map heldRoles = this.getRoles(context);
      AttributeEvaluator ae = context.getActionAttributes().getEvaluatable(this.ACTIONID_ATTR_URI, this.ANYURI_TYPE);
      if (ae == null) {
         throw new IndeterminateEvaluationException("Missing hasPriviledgesOfRole action-id value");
      } else {
         Bag actions = ae.evaluateToBag(context);
         if (!actions.contains(this.HAS_PRIVILEDGES_OF_ROLE_URI_VALUE)) {
            throw new IndeterminateEvaluationException("Missing hasPriviledgesOfRole action-id value");
         } else {
            AttributeEvaluator ae2 = context.getResourceAttributes().getEvaluatable(this.ROLE_ATTR_URI, this.STRING_TYPE);
            if (ae2 == null) {
               throw new IndeterminateEvaluationException("Missing requested role values");
            } else {
               Bag reqRoles = ae2.evaluateToBag(context);
               if (reqRoles != null && !reqRoles.isEmpty()) {
                  Iterator var7 = reqRoles.iterator();

                  while(var7.hasNext()) {
                     StringAttribute role = (StringAttribute)var7.next();
                     PolicyEvaluatorItem pei = (PolicyEvaluatorItem)heldRoles.get(role.getValue());
                     if (pei != null) {
                        PolicyDecision pd = pei.evaluate();
                        if (pd.getDecisionValue() == 0) {
                           return pd;
                        }
                     }
                  }

                  return PolicyDecision.getDenyDecision();
               } else {
                  throw new IndeterminateEvaluationException("No requested roles");
               }
            }
         }
      }
   }
}
