package weblogic.entitlement.engine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import javax.security.auth.Subject;
import weblogic.entitlement.EntitlementLogger;
import weblogic.entitlement.data.EPolicyCollectionInfo;
import weblogic.entitlement.data.EResource;
import weblogic.entitlement.data.ERole;
import weblogic.entitlement.data.ERoleCollectionInfo;
import weblogic.entitlement.data.ERoleId;
import weblogic.entitlement.data.EnConflictException;
import weblogic.entitlement.data.EnCreateException;
import weblogic.entitlement.data.EnCursorResourceFilter;
import weblogic.entitlement.data.EnCursorRoleFilter;
import weblogic.entitlement.data.EnData;
import weblogic.entitlement.data.EnDataChangeListener;
import weblogic.entitlement.data.EnDuplicateKeyException;
import weblogic.entitlement.data.EnFinderException;
import weblogic.entitlement.data.EnRemoveException;
import weblogic.entitlement.data.EnResourceCursor;
import weblogic.entitlement.data.EnRoleCursor;
import weblogic.entitlement.engine.cache.ResourceDecisionCache;
import weblogic.entitlement.expression.EAuxiliary;
import weblogic.entitlement.expression.EExpression;
import weblogic.entitlement.expression.InvalidPredicateClassException;
import weblogic.entitlement.parser.Parser;
import weblogic.entitlement.util.Cache;
import weblogic.entitlement.util.SecondChanceCache;
import weblogic.entitlement.util.TextFilter;
import weblogic.entitlement.util.Version;
import weblogic.security.SecurityLogger;
import weblogic.security.providers.authorization.AugmentedContext;
import weblogic.security.providers.authorization.Predicate;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.SecurityRole;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ESubjectImpl;
import weblogic.utils.collections.CombinedSet;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentHashSet;

public class EEngine implements EnDataChangeListener, PredicateRegistry {
   private static final LoggerWrapper LOG = LoggerWrapper.getInstance("SecurityEEngine");
   private static final Version VERSION = new Version("EEngine", 1, 1, "Build $Date: 2002/01/09 16:41:30 $", "$Revision: 1.57 $");
   public static final String ENTITLEMENT_PROPERTIES = "entitlement.properties";
   public static final String EN_DATA_CLASS_PROPERTY = "weblogic.entitlement.engine.endata_class";
   public static final String ROLE_CACHE_SIZE_PROPERTY = "weblogic.entitlement.engine.cache.max_role_count";
   public static final String RSRC_CACHE_SIZE_PROPERTY = "weblogic.entitlement.engine.cache.max_resource_count";
   public static final String PRED_CACHE_SIZE_PROPERTY = "weblogic.entitlement.engine.cache.max_predicate_count";
   public static final String PRELOAD_CACHE_PROPERTY = "weblogic.entitlement.engine.cache.preload";
   public static final String EN_DATA_CLASS = "weblogic.entitlement.data.ldap.EnDataImp";
   public static final String ROLE_DECISION_CACHE_SIZE_PROPERTY = "weblogic.entitlement.engine.cache.max_role_decision_count";
   public static final String RSRC_DECISION_CACHE_SIZE_PROPERTY = "weblogic.entitlement.engine.cache.max_resource_decision_count";
   public static final String DECISION_CACHE_CLASSES_PROPERTY = "weblogic.entitlement.engine.cache.decision_classes";
   private static final EResource NO_RESOURCE = new EResource("", (EExpression)null);
   private static final ERole NO_ROLE = new ERole("", "", (EExpression)null);
   private static final EDecision ABSTAIN_DECISION = new EDecision((Boolean)null);
   private static EEngine mEngine = null;
   private Cache mResourceCache;
   private long mResChangeNum;
   private ResourceDecisionCache mResourceDecisionCache;
   private Cache mRoleCache;
   private long mRoleChangeNum;
   private ResourceDecisionCache mRoleDecisionCache;
   private Set mDecisionCacheClasses;
   private Cache mPredicateCache;
   private long mPredChangeNum;
   private EnData mEnData;

   public static EEngine getInstance() {
      return mEngine != null ? mEngine : getInstance(readProperties());
   }

   public static EEngine getInstance(Properties conf) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getInstance\n");
      }

      if (mEngine == null) {
         Class var1 = EEngine.class;
         synchronized(EEngine.class) {
            if (mEngine == null) {
               mEngine = new EEngine(conf);
            }
         }
      }

      return mEngine;
   }

   private EEngine(Properties env) {
      String cacheProp = null;
      String cacheValue = null;

      try {
         cacheProp = "weblogic.entitlement.engine.cache.max_role_count";
         cacheValue = env.getProperty(cacheProp, "2000");
         int maxSize = Integer.parseInt(cacheValue);
         this.mRoleCache = new SecondChanceCache(maxSize);
         cacheProp = "weblogic.entitlement.engine.cache.max_role_decision_count";
         cacheValue = System.getProperty(cacheProp, "6000");
         maxSize = Integer.parseInt(cacheValue);
         if (maxSize > 0) {
            this.mRoleDecisionCache = new ResourceDecisionCache(maxSize);
         }

         cacheProp = "weblogic.entitlement.engine.cache.max_resource_count";
         cacheValue = env.getProperty(cacheProp, "5000");
         maxSize = Integer.parseInt(cacheValue);
         this.mResourceCache = new SecondChanceCache(maxSize);
         cacheProp = "weblogic.entitlement.engine.cache.max_resource_decision_count";
         cacheValue = System.getProperty(cacheProp, "15000");
         maxSize = Integer.parseInt(cacheValue);
         if (maxSize > 0) {
            this.mResourceDecisionCache = new ResourceDecisionCache(maxSize);
         }

         if (this.mRoleDecisionCache != null || this.mResourceDecisionCache != null) {
            cacheProp = "weblogic.entitlement.engine.cache.decision_classes";
            cacheValue = System.getProperty(cacheProp, "weblogic.security.service.JMSResource,weblogic.security.service.JDBCResource,weblogic.security.service.JMXResource");
            if (cacheValue.length() > 0) {
               this.mDecisionCacheClasses = new HashSet();
               StringTokenizer st = new StringTokenizer(cacheValue, ",");

               label48:
               while(true) {
                  String className;
                  do {
                     if (!st.hasMoreTokens()) {
                        break label48;
                     }

                     className = st.nextToken();
                  } while(className.length() <= 0);

                  try {
                     this.mDecisionCacheClasses.add(Class.forName(className));
                  } catch (ClassNotFoundException var8) {
                     if (LOG.isDebugEnabled()) {
                        LOG.debug("Failed to load decision cache resource class: " + className);
                     }
                  }
               }
            }
         }

         cacheProp = "weblogic.entitlement.engine.cache.max_predicate_count";
         cacheValue = env.getProperty(cacheProp, "200");
         maxSize = Integer.parseInt(cacheValue);
         this.mPredicateCache = new SecondChanceCache(maxSize);
      } catch (Exception var9) {
         EntitlementLogger.logInvalidPropertyValue(cacheProp, cacheValue);
         abort("Invalid value \"" + cacheValue + "\" for property \"" + cacheProp + "\"\nPositive integer is expected", var9);
      }

      this.mEnData = makeEnData(env);
      boolean preload = Boolean.parseBoolean(env.getProperty("weblogic.entitlement.engine.cache.preload", "false"));
      if (preload) {
         this.preload();
      }

      this.mEnData.setDataChangeListener(this);
   }

   public Map getRoleMap(Subject subj, Resource resource, ContextHandler context) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getRoleMap (" + new ESubjectImpl(subj) + " , " + resource.toString() + ")\n");
      }

      boolean useCache = this.mRoleDecisionCache != null && (this.mDecisionCacheClasses == null || this.mDecisionCacheClasses.contains(resource.getClass()));
      ECacheableRoles ecr;
      if (useCache) {
         ecr = (ECacheableRoles)this.mRoleDecisionCache.lookupDecision(resource, subj);
         if (ecr != null) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("getRoleMap returning roles from decision cache");
            }

            return new ERoleMapImpl(subj, resource, context, ecr);
         }
      }

      ecr = new ECacheableRoles();
      if (useCache) {
         if (LOG.isDebugEnabled()) {
            LOG.debug("Caching roles for getRoleMap (" + new ESubjectImpl(subj) + " , " + resource.toString() + ")\n");
         }

         this.mRoleDecisionCache.cacheDecision(resource, subj, ecr);
      }

      return new ERoleMapImpl(subj, resource, context, ecr);
   }

   private boolean checkCacheability(EExpression expr, int mask) {
      return expr == null ? true : this.checkCacheability(expr.getDependsOn(), mask);
   }

   private boolean checkCacheability(int exprDependsOn, int mask) {
      return (exprDependsOn | mask) == mask;
   }

   private boolean evaluate(ESubject subj, ERole role, ResourceNode resource, ContextHandler context) {
      boolean result = false;
      EExpression expr = role.getExpression();
      if (LOG.isDebugEnabled()) {
         LOG.debug("Evaluating role " + role.getPrimaryKey() + " with expression: " + (expr == null ? "null" : expr.externalize()));
      }

      if (expr != null) {
         if (context != null) {
            EAuxiliary aux = role.getAuxiliary();
            if (aux != null) {
               context = new AugmentedContext((ContextHandler)context, "com.bea.contextelement.entitlement.EAuxiliaryID", aux);
            }
         }

         try {
            result = expr.evaluate(subj, resource, (ContextHandler)context, this);
         } catch (UnregisteredPredicateException var8) {
            EntitlementLogger.logRoleUnregisteredPredicate(role.getPrimaryKey().toString(), var8.getPredicateName());
         } catch (Exception var9) {
            EntitlementLogger.logPolicyEvaluationFailed(role.getEntitlement(), role.getPrimaryKey().toString());
            if (LOG.isDebugEnabled()) {
               LOG.debug("Caught exception thrown while evaluating role expression", var9);
            }
         }
      }

      return result;
   }

   private Collection getRoles(ResourceNode resource) {
      String[] resourcePath = resource.getNamePathToRoot();
      Map roles = new HashMap();
      String resName = "";

      for(int i = resourcePath.length; i >= 0; --i) {
         if (i < resourcePath.length) {
            resName = resourcePath[i];
         }

         ERole[] rls = this.getRoles(resName);

         for(int j = 0; j < rls.length; ++j) {
            roles.put(rls[j].getName(), rls[j]);
         }
      }

      return roles.values();
   }

   private ERole[] getRoles(String resourceName) {
      if (resourceName == null) {
         resourceName = "";
      }

      RoleCacheEntry roleMap = (RoleCacheEntry)this.mRoleCache.get(resourceName);
      if (roleMap != null && roleMap.all) {
         synchronized(this.mRoleCache) {
            if (roleMap.all) {
               return (ERole[])((ERole[])roleMap.values().toArray(new ERole[roleMap.size()]));
            }
         }
      }

      long cn = this.mRoleChangeNum;
      Collection rc = this.mEnData.fetchRoles(resourceName);
      ERole[] roles = (ERole[])((ERole[])rc.toArray(new ERole[rc.size()]));
      synchronized(this.mRoleCache) {
         if (cn == this.mRoleChangeNum) {
            this.updateRoleCache(resourceName, roles);
         }

         return roles;
      }
   }

   private ERole getRole(ResourceNode resource, String roleName) {
      String[] resourcePath = resource.getNamePathToRoot();
      ERole role = null;
      ArrayList absent = null;

      for(int i = 0; i <= resourcePath.length; ++i) {
         String resName = i < resourcePath.length ? resourcePath[i] : "";
         role = this.getFromRoleCache(resName, roleName);
         if (role != null) {
            if (role != NO_ROLE) {
               break;
            }
         } else {
            if (absent == null) {
               absent = new ArrayList(resourcePath.length);
            }

            absent.add(resName);
         }
      }

      if (role == NO_ROLE) {
         role = null;
      }

      if (absent != null) {
         ERoleId[] roleIds = new ERoleId[absent.size()];

         for(int i = 0; i < roleIds.length; ++i) {
            roleIds[i] = new ERoleId((String)absent.get(i), roleName);
         }

         try {
            ERole[] roles = this.getRoles(roleIds, true);

            for(int i = 0; i < roles.length; ++i) {
               if (roles[i] != null) {
                  return roles[i];
               }
            }
         } catch (EnFinderException var9) {
            abort("Caught EnFinderException while ignoreNotFound was set to true", var9);
         }
      }

      return role;
   }

   public Boolean evaluate(Subject subj, Map roles, Resource resource, ContextHandler context) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("evaluate(" + new ESubjectImpl(subj, roles) + ", " + resource.toString() + ")\n");
      }

      boolean useCache = this.mResourceDecisionCache != null && (this.mDecisionCacheClasses == null || this.mDecisionCacheClasses.contains(resource.getClass()));
      if (useCache) {
         EDecision cacheHit = (EDecision)this.mResourceDecisionCache.lookupDecision(resource, subj);
         if (cacheHit != null) {
            if (cacheHit.isApplicable(roles)) {
               if (LOG.isDebugEnabled()) {
                  LOG.debug("evaluate returning value from decision cache");
               }

               return cacheHit.getDecision();
            }

            if (LOG.isDebugEnabled()) {
               LOG.debug("evauate decision cache hit not applicable to current roles -- remove from cache");
            }

            this.mResourceDecisionCache.uncacheDecision(resource, subj);
         }
      }

      Boolean result = Boolean.FALSE;
      ResourceNode eResource = new ResourceNodeImpl(resource);
      EResource res = this.getPolicyResource((ResourceNode)eResource);
      if (res == null) {
         result = null;
         if (LOG.isDebugEnabled()) {
            LOG.debug("No resource found, cannot evaluate");
         }

         if (useCache) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Caching abstain for evaluate (" + new ESubjectImpl(subj, roles) + " , " + resource.toString() + ")\n");
            }

            this.mResourceDecisionCache.cacheDecision(resource, subj, ABSTAIN_DECISION);
         }
      } else {
         EExpression expr = res.getExpression();
         if (LOG.isDebugEnabled()) {
            LOG.debug("Evaluating resource " + res.toString() + " with expression: " + (expr == null ? "null" : expr.externalize()));
         }

         if (expr != null) {
            try {
               if (expr.evaluate(new ESubjectImpl(subj, roles), eResource, context, this)) {
                  result = Boolean.TRUE;
               }
            } catch (UnregisteredPredicateException var11) {
               EntitlementLogger.logResourceUnregisteredPredicate(res.getName(), var11.getPredicateName());
            } catch (Exception var12) {
               EntitlementLogger.logPolicyEvaluationFailed(expr.externalize(), res.getName());
               if (LOG.isDebugEnabled()) {
                  LOG.debug("Caught exception thrown while evaluating resource expression", var12);
               }
            }

            if (useCache) {
               int exprDependsOn = expr.getDependsOn();
               if (this.checkCacheability(exprDependsOn, 3)) {
                  if ((exprDependsOn & 2) != 0) {
                     if (roles == null || roles instanceof ERoleMapImpl && ((ERoleMapImpl)roles).isCacheableOnly()) {
                        if (LOG.isDebugEnabled()) {
                           LOG.debug("Caching role-dependent decision for evaluate (" + new ESubjectImpl(subj, roles) + " , " + resource.toString() + ")\n");
                        }

                        this.mResourceDecisionCache.cacheDecision(resource, subj, new ERoleDependentDecision(result, roles != null ? ((ERoleMapImpl)roles).ecr : null));
                     }
                  } else {
                     if (LOG.isDebugEnabled()) {
                        LOG.debug("Caching decision for evaluate (" + new ESubjectImpl(subj, roles) + " , " + resource.toString() + ")\n");
                     }

                     this.mResourceDecisionCache.cacheDecision(resource, subj, new EDecision(result));
                  }
               }
            }
         } else if (useCache) {
            if (LOG.isDebugEnabled()) {
               LOG.debug("Caching empty expression for evaluate (" + new ESubjectImpl(subj, roles) + " , " + resource.toString() + ")\n");
            }

            this.mResourceDecisionCache.cacheDecision(resource, subj, ABSTAIN_DECISION);
         }
      }

      if (LOG.isDebugEnabled()) {
         LOG.debug("Evaluation result: " + result);
      }

      return result;
   }

   public EResource getPolicyResource(Resource resource) {
      return this.getPolicyResource((ResourceNode)(new ResourceNodeImpl(resource)));
   }

   public EResource getPolicyResource(ResourceNode resource) {
      String[] resourcePath = resource.getNamePathToRoot();
      EResource[] resources = null;

      try {
         resources = this.getResources(resourcePath, true);
      } catch (EnFinderException var6) {
         abort("Caught EnFinderException while ignoreNotFound was set to true", var6);
      }

      EResource res = null;

      for(int i = 0; i < resources.length; ++i) {
         if (resources[i] != null) {
            res = resources[i];
            if (res.getExpression() != null) {
               return res;
            }
         }
      }

      return res;
   }

   public void createRoles(ERoleId[] roleIds, String[] expressions, boolean fromDeploy) throws EnDuplicateKeyException, EnCreateException {
      this.createRoles(roleIds, expressions, new String[0], fromDeploy);
   }

   public void createRoles(ERoleId[] roleIds, String[] expressions, String[] auxiliary, boolean fromDeploy) throws EnDuplicateKeyException, EnCreateException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("createRoles (");

         for(int i = 0; i < roleIds.length; ++i) {
            LOG.debug("[" + roleIds[i] + " -- " + expressions[i] + "]");
         }

         LOG.debug(")\n");
      }

      ERole[] roles = new ERole[roleIds.length];
      int i = -1;

      try {
         for(i = 0; i < roles.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseRoleExpression(expressions[i]);
            EAuxiliary aux = null;
            if (i < auxiliary.length && auxiliary[i] != null) {
               aux = new EAuxiliary(auxiliary[i]);
            }

            roles[i] = new ERole(roleIds[i], expr, aux, fromDeploy);
         }
      } catch (Exception var14) {
         throw new EnCreateException(var14.getMessage(), i, var14);
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.create(roles, fromDeploy);
      if (this.mRoleDecisionCache != null) {
         for(int j = 0; j < roles.length; ++j) {
            this.mRoleDecisionCache.uncache(roles[j].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.updateRoleCache(roles);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public void removeRoles(ERoleId[] roleIds) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("removeRoles (" + roleIds + ")\n");
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.removeRoles(roleIds);
      if (this.mRoleDecisionCache != null) {
         for(int i = 0; i < roleIds.length; ++i) {
            this.mRoleDecisionCache.uncache(roleIds[i].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.removeFromRoleCache(roleIds);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public void createResourceForCollection(String collectionName, String[] resourceNames, String[] expressions) throws EnConflictException, EnDuplicateKeyException, EnCreateException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("createResourceForCollection (" + collectionName + " -- ");

         for(int i = 0; i < resourceNames.length; ++i) {
            LOG.debug("[" + resourceNames[i] + " -- " + expressions[i] + "]");
         }

         LOG.debug(")\n");
      }

      EResource[] resources = new EResource[resourceNames.length];
      int i = -1;

      try {
         for(i = 0; i < resourceNames.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseResourceExpression(expressions[i]);
            resources[i] = new EResource(resourceNames[i], expr, true, collectionName);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mResChangeNum;
      this.mEnData.createForCollection(resources);
      if (this.mResourceDecisionCache != null) {
         for(int j = 0; j < resources.length; ++j) {
            this.mResourceDecisionCache.uncache(resources[j].getName());
         }
      }

      synchronized(this.mResourceCache) {
         long cnn = this.mResChangeNum++;
         if (cn == cnn) {
            this.updateResourceCache(resources);
         } else {
            this.discardFromResourceCache(resourceNames);
         }

      }
   }

   public void createResources(String[] resourceNames, String[] expressions, boolean fromDeploy) throws EnDuplicateKeyException, EnCreateException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("createResources (");

         for(int i = 0; i < resourceNames.length; ++i) {
            LOG.debug("[" + resourceNames[i] + " -- " + expressions[i] + "]");
         }

         LOG.debug(")\n");
      }

      EResource[] resources = new EResource[resourceNames.length];
      int i = -1;

      try {
         for(i = 0; i < resourceNames.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseResourceExpression(expressions[i]);
            resources[i] = new EResource(resourceNames[i], expr, fromDeploy);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mResChangeNum;
      this.mEnData.create(resources, fromDeploy);
      if (this.mResourceDecisionCache != null) {
         for(int j = 0; j < resources.length; ++j) {
            this.mResourceDecisionCache.uncache(resources[j].getName());
         }
      }

      synchronized(this.mResourceCache) {
         long cnn = this.mResChangeNum++;
         if (cn == cnn) {
            this.updateResourceCache(resources);
         } else {
            this.discardFromResourceCache(resourceNames);
         }

      }
   }

   public void removeResources(String[] resourceNames) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("removeResources (" + resourceNames + ")\n");
      }

      long cn = this.mResChangeNum;
      this.mEnData.removeResources(resourceNames);
      if (this.mResourceDecisionCache != null) {
         for(int i = 0; i < resourceNames.length; ++i) {
            this.mResourceDecisionCache.uncache(resourceNames[i]);
         }
      }

      synchronized(this.mResourceCache) {
         long cnn = this.mResChangeNum++;
         if (cn == cnn) {
            this.removeFromResourceCache(resourceNames);
         } else {
            this.discardFromResourceCache(resourceNames);
         }

      }
   }

   public void setRoleEntitlements(ERoleId[] roleIds, String[] expressions, boolean fromDeploy) throws EnCreateException, EnFinderException {
      int i;
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer();
         s.append("setRoleEntitlements (");

         for(i = 0; i < roleIds.length; ++i) {
            s.append("[" + roleIds[i] + " -- " + expressions[i] + "]");
         }

         s.append(")\n");
         LOG.debug(s.toString());
      }

      ERole[] roles = new ERole[roleIds.length];
      int i = -1;

      try {
         for(i = 0; i < roles.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseRoleExpression(expressions[i]);
            roles[i] = new ERole(roleIds[i], expr, fromDeploy);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.update(roles, fromDeploy);
      if (this.mRoleDecisionCache != null) {
         for(int j = 0; j < roles.length; ++j) {
            this.mRoleDecisionCache.uncache(roles[j].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.updateRoleCache(roles);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public void createRoleEntitlementsForCollection(String collectionName, ERoleId[] roleIds, String[] expressions) throws EnConflictException, EnDuplicateKeyException, EnCreateException {
      ERole[] roles = new ERole[roleIds.length];
      int i = -1;

      try {
         for(i = 0; i < roles.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseRoleExpression(expressions[i]);
            roles[i] = new ERole(roleIds[i], expr, true, collectionName);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.createForCollection(roles);
      if (this.mRoleDecisionCache != null) {
         for(int j = 0; j < roles.length; ++j) {
            this.mRoleDecisionCache.uncache(roles[j].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.updateRoleCache(roles);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public void setRoleEntitlementsForCollection(String collectionName, ERoleId[] roleIds, String[] expressions) throws EnCreateException, EnFinderException {
      ERole[] roles = new ERole[roleIds.length];
      int i = -1;

      try {
         for(i = 0; i < roles.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseRoleExpression(expressions[i]);
            roles[i] = new ERole(roleIds[i], expr, true, collectionName);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.update(roles, true);
      if (this.mRoleDecisionCache != null) {
         for(int j = 0; j < roles.length; ++j) {
            this.mRoleDecisionCache.uncache(roles[j].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.updateRoleCache(roles);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public String[] getRoleEntitlements(ERoleId[] roleIds) throws EnFinderException {
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer("getRoleEntitlements ([");

         for(int i = 0; i < roleIds.length; ++i) {
            s.append(roleIds[i]);
            if (i + 1 < roleIds.length) {
               s.append(",");
            }
         }

         s.append("])\n");
         LOG.debug(s.toString());
      }

      ERole[] roles = this.getRoles(roleIds, false);
      String[] entitlements = new String[roles.length];

      for(int i = 0; i < roles.length; ++i) {
         entitlements[i] = roles[i].getEntitlement();
      }

      return entitlements;
   }

   public void setRoleAuxiliary(ERoleId[] roleIds, String[] auxiliary, boolean fromDeploy) throws EnCreateException, EnFinderException {
      int i;
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer();
         s.append("setRoleAuxiliary (");

         for(i = 0; i < roleIds.length; ++i) {
            s.append("[" + roleIds[i] + " -- " + auxiliary[i] + "]");
         }

         s.append(")\n");
         LOG.debug(s.toString());
      }

      ERole[] roles = new ERole[roleIds.length];
      int i = -1;

      try {
         for(i = 0; i < roles.length; ++i) {
            EAuxiliary aux = auxiliary[i] == null ? null : new EAuxiliary(auxiliary[i]);
            roles[i] = new ERole(roleIds[i], (EExpression)null, aux, fromDeploy);
         }
      } catch (Exception var13) {
         throw new EnCreateException(var13.getMessage(), i, var13);
      }

      long cn = this.mRoleChangeNum;
      this.mEnData.updateAuxiliary(roles, fromDeploy);
      if (this.mRoleDecisionCache != null) {
         for(int j = 0; j < roles.length; ++j) {
            this.mRoleDecisionCache.uncache(roles[j].getResourceName());
         }
      }

      synchronized(this.mRoleCache) {
         long cnn = this.mRoleChangeNum++;
         if (cn == cnn) {
            this.updateRoleCache(roles);
         } else {
            this.discardFromRoleCache(roleIds);
         }

      }
   }

   public String[] getRoleAuxiliary(ERoleId[] roleIds) throws EnFinderException {
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer("getRoleAuxiliary ([");

         for(int i = 0; i < roleIds.length; ++i) {
            s.append(roleIds[i]);
            if (i + 1 < roleIds.length) {
               s.append(",");
            }
         }

         s.append("])\n");
         LOG.debug(s.toString());
      }

      ERole[] roles = this.getRoles(roleIds, false);
      String[] aux = new String[roles.length];

      for(int i = 0; i < roles.length; ++i) {
         aux[i] = roles[i].getAuxiliary().toString();
      }

      return aux;
   }

   public void setResourceEntitlementsForCollection(String collectionName, String[] resourceNames, String[] expressions) throws EnCreateException, EnFinderException {
      int i;
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer();
         s.append("setResourceEntitlementsForCollection (");
         s.append(collectionName + " -- ");

         for(i = 0; i < resourceNames.length; ++i) {
            s.append("[" + resourceNames[i] + " -- " + expressions[i] + "]");
         }

         s.append(")\n");
         LOG.debug(s.toString());
      }

      EResource[] resources = new EResource[resourceNames.length];
      int i = -1;

      try {
         for(i = 0; i < resourceNames.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseResourceExpression(expressions[i]);
            resources[i] = new EResource(resourceNames[i], expr, true, collectionName);
         }
      } catch (Exception var7) {
         throw new EnCreateException(var7.getMessage(), i, var7);
      }

      this.setResourceEntitlements(resourceNames, resources, true);
   }

   public void setResourceEntitlements(String[] resourceNames, String[] expressions, boolean fromDeploy) throws EnCreateException, EnFinderException {
      int i;
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer();
         s.append("setResourceEntitlements (");

         for(i = 0; i < resourceNames.length; ++i) {
            s.append("[" + resourceNames[i] + " -- " + expressions[i] + "]");
         }

         s.append(")\n");
         LOG.debug(s.toString());
      }

      EResource[] resources = new EResource[resourceNames.length];
      int i = -1;

      try {
         for(i = 0; i < resourceNames.length; ++i) {
            EExpression expr = expressions[i] == null ? null : Parser.parseResourceExpression(expressions[i]);
            resources[i] = new EResource(resourceNames[i], expr, fromDeploy);
         }
      } catch (Exception var7) {
         throw new EnCreateException(var7.getMessage(), i, var7);
      }

      this.setResourceEntitlements(resourceNames, resources, fromDeploy);
   }

   private void setResourceEntitlements(String[] resourceNames, EResource[] resources, boolean fromDeploy) throws EnFinderException {
      long cn = this.mResChangeNum;
      this.mEnData.update(resources, fromDeploy);
      if (this.mResourceDecisionCache != null) {
         for(int j = 0; j < resources.length; ++j) {
            this.mResourceDecisionCache.uncache(resources[j].getName());
         }
      }

      synchronized(this.mResourceCache) {
         long cnn = this.mResChangeNum++;
         if (cn == cnn) {
            this.updateResourceCache(resources);
         } else {
            this.discardFromResourceCache(resourceNames);
         }

      }
   }

   public String[] getResourceEntitlements(String[] resourceNames) throws EnFinderException {
      if (LOG.isDebugEnabled()) {
         StringBuffer s = new StringBuffer("getResourceEntitlements ([");

         for(int i = 0; i < resourceNames.length; ++i) {
            s.append(resourceNames[i]);
            if (i + 1 < resourceNames.length) {
               s.append(",");
            }
         }

         s.append("])\n");
         LOG.debug(s.toString());
      }

      EResource[] resources = this.getResources(resourceNames, false);
      String[] entitlements = new String[resources.length];

      for(int i = 0; i < resources.length; ++i) {
         entitlements[i] = resources[i].getEntitlement();
      }

      return entitlements;
   }

   private ERole[] getRoles(ERoleId[] roleIds, boolean ignoreNotFound) throws EnFinderException {
      ERole[] roles = this.getFromRoleCache(roleIds);
      int absentCount;
      if (!ignoreNotFound) {
         for(absentCount = 0; absentCount < roles.length; ++absentCount) {
            if (roles[absentCount] == NO_ROLE) {
               throw new EnFinderException(roles[absentCount].toString());
            }
         }
      }

      absentCount = 0;

      int i;
      for(i = 0; i < roles.length; ++i) {
         if (roles[i] == null) {
            ++absentCount;
         }
      }

      if (absentCount > 0) {
         ERoleId[] absentRoleIds = new ERoleId[absentCount];
         int i = 0;

         for(int j = 0; j < absentCount; ++i) {
            if (roles[i] == null) {
               absentRoleIds[j++] = roleIds[i];
            }
         }

         long cn = this.mRoleChangeNum;
         ERole[] absentRoles = this.mEnData.fetchRoles(absentRoleIds, ignoreNotFound);
         synchronized(this.mRoleCache) {
            boolean changed = cn != this.mRoleChangeNum;
            int i = 0;

            for(int j = 0; j < absentCount; ++i) {
               if (roles[i] == null) {
                  roles[i] = absentRoles[j++];
                  if (!changed) {
                     this.updateRoleCache(roleIds[i], roles[i]);
                  }
               }
            }
         }
      }

      for(i = 0; i < roles.length; ++i) {
         if (roles[i] == NO_ROLE) {
            roles[i] = null;
         }
      }

      return roles;
   }

   private EResource[] getResources(String[] resourceNames, boolean ignoreNotFound) throws EnFinderException {
      EResource[] resources = this.getFromResourceCache(resourceNames);
      int absentCount;
      if (!ignoreNotFound) {
         for(absentCount = 0; absentCount < resources.length; ++absentCount) {
            if (resources[absentCount] == NO_RESOURCE) {
               throw new EnFinderException(resourceNames[absentCount]);
            }
         }
      }

      absentCount = 0;

      int i;
      for(i = 0; i < resources.length; ++i) {
         if (resources[i] == null) {
            ++absentCount;
         }
      }

      if (absentCount > 0) {
         String[] absentResourceNames = new String[absentCount];
         int i = 0;

         for(int j = 0; j < absentCount; ++i) {
            if (resources[i] == null) {
               absentResourceNames[j++] = resourceNames[i];
            }
         }

         long cn = this.mResChangeNum;
         EResource[] absentResources = this.mEnData.fetchResources(absentResourceNames, ignoreNotFound);
         synchronized(this.mResourceCache) {
            boolean changed = cn != this.mResChangeNum;
            int i = 0;

            for(int j = 0; j < absentCount; ++i) {
               if (resources[i] == null) {
                  resources[i] = absentResources[j++];
                  if (!changed) {
                     this.updateResourceCache(resourceNames[i], resources[i] == null ? NO_RESOURCE : resources[i]);
                  }
               }
            }
         }
      }

      if (ignoreNotFound) {
         for(i = 0; i < resources.length; ++i) {
            if (resources[i] == NO_RESOURCE) {
               resources[i] = null;
            }
         }
      }

      return resources;
   }

   public Collection getRoleIds(String resourceName, String roleNameFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getRoleIds-scoped (" + resourceName + ", " + roleNameFilter + ")\n");
      }

      TextFilter filter = roleNameFilter == null ? null : new TextFilter(roleNameFilter);
      return this.mEnData.fetchRoleIds(resourceName, filter);
   }

   public Collection getRoleIds(ResourceNode resource, String roleNameFilter) throws EnFinderException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getRoleIds-all (" + resource.getName() + " , " + roleNameFilter + ")\n");
      }

      TextFilter filter = roleNameFilter == null ? null : new TextFilter(roleNameFilter);
      String[] resourcePath = resource.getNamePathToRoot();
      ArrayList tmp = new ArrayList(this.mEnData.fetchRoleIds("", filter));

      for(int i = 0; i < resourcePath.length; ++i) {
         tmp.addAll(this.mEnData.fetchRoleIds(resourcePath[i], filter));
      }

      return tmp;
   }

   public Collection getResourceNames(String nameFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getResourceNames (" + nameFilter + ")\n");
      }

      TextFilter filter = nameFilter == null ? null : new TextFilter(nameFilter);
      return this.mEnData.fetchResourceNames(filter);
   }

   public Collection getResourceRoleIds(String nameFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getResourceRoleIds (" + nameFilter + ")\n");
      }

      TextFilter filter = nameFilter == null ? null : new TextFilter(nameFilter);
      return this.mEnData.fetchResourceRoleIds(filter);
   }

   public static Version getVersion() {
      return VERSION;
   }

   public String[] getPredicates(String nameFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("getPredicates (" + nameFilter + ")\n");
      }

      TextFilter filter = nameFilter == null ? null : new TextFilter(nameFilter);
      long cn = this.mPredChangeNum;
      Collection regPreds = this.mEnData.fetchPredicates(filter);
      ArrayList selPreds = new ArrayList(regPreds.size());
      synchronized(this.mPredicateCache) {
         boolean changed = cn != this.mPredChangeNum;
         Iterator p = regPreds.iterator();

         while(p.hasNext()) {
            String predClassName = (String)p.next();
            boolean validPredicate = true;
            if (!this.mPredicateCache.containsKey(predClassName)) {
               try {
                  validatePredicate(predClassName);
                  if (!changed) {
                     this.mPredicateCache.put(predClassName, predClassName);
                  }
               } catch (InvalidPredicateClassException var14) {
                  validPredicate = false;
                  EntitlementLogger.logRetrievedInvalidPredicate(predClassName);
                  if (LOG.isDebugEnabled()) {
                     LOG.debug("Retrieved invalid predicate class name\n" + var14.getMessage(), var14);
                  }
               }
            }

            if (validPredicate) {
               selPreds.add(predClassName);
            }
         }
      }

      return (String[])((String[])selPreds.toArray(new String[selPreds.size()]));
   }

   public boolean isRegistered(String predicateClassName) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("isRegistered (" + predicateClassName + ")\n");
      }

      boolean exists = this.mPredicateCache.containsKey(predicateClassName);
      if (exists) {
         return true;
      } else {
         long cn = this.mPredChangeNum;
         exists = this.mEnData.predicateExists(predicateClassName);
         if (exists) {
            try {
               validatePredicate(predicateClassName);
               synchronized(this.mPredicateCache) {
                  boolean changed = cn != this.mPredChangeNum;
                  if (!changed) {
                     this.mPredicateCache.put(predicateClassName, predicateClassName);
                  }
               }
            } catch (InvalidPredicateClassException var9) {
               exists = false;
               EntitlementLogger.logRetrievedInvalidPredicate(predicateClassName);
               if (LOG.isDebugEnabled()) {
                  LOG.debug("Retrieved invalid predicate class name\n" + var9.getMessage(), var9);
               }
            }
         }

         return exists;
      }
   }

   public void registerPredicate(String predicateClassName) throws InvalidPredicateClassException, EnDuplicateKeyException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("registerPredicate (" + predicateClassName + ")\n");
      }

      validatePredicate(predicateClassName);
      long cn = this.mPredChangeNum;
      this.mEnData.createPredicate(predicateClassName);
      synchronized(this.mPredicateCache) {
         long cnn = this.mPredChangeNum++;
         if (cn == cnn) {
            this.mPredicateCache.put(predicateClassName, predicateClassName);
         } else {
            this.mPredicateCache.remove(predicateClassName);
         }

      }
   }

   public void unregisterPredicate(String predicateClassName) throws EnFinderException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("unregisterPredicate (" + predicateClassName + ")\n");
      }

      this.mEnData.removePredicate(predicateClassName);
      synchronized(this.mPredicateCache) {
         ++this.mPredChangeNum;
         this.mPredicateCache.remove(predicateClassName);
      }
   }

   public static Predicate validatePredicate(String predicateClassName) throws InvalidPredicateClassException {
      PrivilegedAction getThreadCCLAction = new PrivilegedAction() {
         public Object run() {
            return Thread.currentThread().getContextClassLoader();
         }
      };
      ClassLoader threadCCL = (ClassLoader)AccessController.doPrivileged(getThreadCCLAction);

      try {
         return (Predicate)Class.forName(predicateClassName, true, threadCCL).newInstance();
      } catch (ClassCastException var4) {
         throw new InvalidPredicateClassException(predicateClassName + " class does not implement interface " + Predicate.class.getName());
      } catch (ClassNotFoundException var5) {
         throw new InvalidPredicateClassException(predicateClassName + " class cannot be found ");
      } catch (IllegalAccessException var6) {
         throw new InvalidPredicateClassException(predicateClassName + " class's constructor access denied");
      } catch (InstantiationException var7) {
         throw new InvalidPredicateClassException("Cannot instantiate predicate class " + predicateClassName + "\n" + var7.getMessage());
      }
   }

   public void resourceChanged(String resourceId) {
      synchronized(this.mResourceCache) {
         ++this.mResChangeNum;
         this.mResourceCache.remove(resourceId);
      }

      if (this.mResourceDecisionCache != null) {
         this.mResourceDecisionCache.uncache(resourceId);
      }

   }

   public void roleChanged(ERoleId roleId) {
      synchronized(this.mRoleCache) {
         ++this.mRoleChangeNum;
         this.discardFromRoleCache(roleId);
      }

      if (this.mRoleDecisionCache != null) {
         this.mRoleDecisionCache.uncache(roleId.getResourceName());
      }

   }

   public void predicateChanged(String predClassName) {
      synchronized(this.mPredicateCache) {
         ++this.mPredChangeNum;
         this.mPredicateCache.remove(predClassName);
      }
   }

   public void applicationDeletedResources(String applicationName, int compType, String compName) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("applicationDeletedResources (" + applicationName + ")");
      }

      this.mEnData.applicationDeletedResources(applicationName, compType, compName);
   }

   public void cleanupAfterCollectionResources(String collectionName, long startTime, List removed) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("cleanupAfterCollectionResources (" + collectionName + ")");
      }

      this.mEnData.cleanupAfterCollectionResources(collectionName, startTime, removed);
   }

   public void cleanupAfterCollectionRoles(String collectionName, long startTime, List removed) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("cleanupAfterCollectionRoles (" + collectionName + ")");
      }

      this.mEnData.cleanupAfterCollectionRoles(collectionName, startTime, removed);
   }

   public void cleanupAfterDeployResources(String applicationName, int compType, String compName, long startTime) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("cleanupAfterDeployResources (" + applicationName + ")");
      }

      this.mEnData.cleanupAfterDeployResources(applicationName, compType, compName, startTime);
   }

   public void applicationCopyResources(String sourceAppName, String destAppName) throws EnCreateException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("applicationCopyResources (" + sourceAppName + "," + destAppName + ")");
      }

      this.mEnData.applicationCopyResources(sourceAppName, destAppName);
   }

   public void applicationDeletedRoles(String applicationName, int compType, String compName) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("applicationDeletedRoles (" + applicationName + ")");
      }

      this.mEnData.applicationDeletedRoles(applicationName, compType, compName);
   }

   public void cleanupAfterDeployRoles(String applicationName, int compType, String compName, long startTime) throws EnFinderException, EnRemoveException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("cleanupAfterDeployRoles (" + applicationName + ")");
      }

      this.mEnData.cleanupAfterDeployRoles(applicationName, compType, compName, startTime);
   }

   public void applicationCopyRoles(String sourceAppName, String destAppName) throws EnCreateException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("applicationCopyRoles (" + sourceAppName + "," + destAppName + ")");
      }

      this.mEnData.applicationCopyRoles(sourceAppName, destAppName);
   }

   private static Properties readProperties() {
      Properties env = new Properties();

      try {
         InputStream in = EEngine.class.getClassLoader().getResourceAsStream("entitlement.properties");
         if (in != null) {
            env.load(in);
         } else {
            LOG.severe("Cannot read properties file entitlement.properties\nMake sure the file is in the classpath and is permitted to read");
         }
      } catch (SecurityException var2) {
         abort("Cannot read file entitlement.properties\nThe file might be read protected\n" + var2.getMessage(), var2);
      } catch (IOException var3) {
         abort("Cannot read entitlement.properties\n" + var3.getMessage(), var3);
      }

      return env;
   }

   private static EnData makeEnData(Properties env) {
      EnData enData = null;
      String dataClassName = env.getProperty("weblogic.entitlement.engine.endata_class", "weblogic.entitlement.data.ldap.EnDataImp");

      try {
         Class dataClass = Class.forName(dataClassName);
         Constructor c = dataClass.getConstructor(Properties.class);
         enData = (EnData)c.newInstance(env);
      } catch (ClassNotFoundException var6) {
         abort("Cannot find class " + dataClassName + "\nMake sure " + "weblogic.entitlement.engine.endata_class" + " is set in " + "entitlement.properties", var6);
      } catch (NoSuchMethodException var7) {
         abort("Class weblogic.entitlement.engine.endata_class does not have a constructor with one java.util.Properties argument", var7);
      } catch (SecurityException var8) {
         abort("Cannot access information about " + dataClassName + " class\n" + var8.getMessage(), var8);
      } catch (IllegalAccessException var9) {
         abort("Cannot access constructor of class " + dataClassName + "\n" + var9.getMessage(), var9);
      } catch (InstantiationException var10) {
         abort("Cannot create instance of class " + dataClassName + "\nMake sure class is not declared abstract.\n" + var10.getMessage(), var10);
      } catch (InvocationTargetException var11) {
         String message = "Cannot instantiate " + dataClassName + "\n" + var11.getMessage();
         Throwable t = var11.getTargetException();
         LOG.severe(message, t);
         if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
         }

         throw new RuntimeException(message);
      }

      return enData;
   }

   private static void abort(String message, Throwable t) {
      LOG.severe(message, t);
      throw new RuntimeException(message);
   }

   private void updateRoleCache(ERoleId roleId, ERole role) {
      String resourceName = roleId.getResourceName();
      RoleCacheEntry roleMap = (RoleCacheEntry)this.mRoleCache.get(resourceName);
      if (roleMap == null) {
         roleMap = new RoleCacheEntry();
         this.mRoleCache.put(resourceName, roleMap);
      }

      if (role == null) {
         role = NO_ROLE;
      }

      if (role == NO_ROLE && roleMap.all) {
         roleMap.remove(roleId.getRoleName());
      } else {
         roleMap.put(roleId.getRoleName(), role);
      }

   }

   private void updateRoleCache(ERole[] roles) {
      for(int i = 0; i < roles.length; ++i) {
         this.updateRoleCache((ERoleId)roles[i].getPrimaryKey(), roles[i]);
      }

   }

   private void removeFromRoleCache(ERoleId[] roles) {
      for(int i = 0; i < roles.length; ++i) {
         this.updateRoleCache(roles[i], NO_ROLE);
      }

   }

   private void discardFromRoleCache(ERoleId roleId) {
      RoleCacheEntry roleMap = (RoleCacheEntry)this.mRoleCache.get(roleId.getResourceName());
      if (roleMap != null) {
         roleMap.all = false;
         roleMap.remove(roleId.getRoleName());
      }

   }

   private void discardFromRoleCache(ERoleId[] roleIds) {
      for(int i = 0; i < roleIds.length; ++i) {
         this.discardFromRoleCache(roleIds[i]);
      }

   }

   private void updateRoleCache(String resourceName, ERole[] roles) {
      RoleCacheEntry roleMap = new RoleCacheEntry(roles.length);

      for(int i = 0; i < roles.length; ++i) {
         roleMap.put(roles[i].getName(), roles[i]);
      }

      roleMap.all = true;
      this.mRoleCache.put(resourceName, roleMap);
   }

   private ERole[] getFromRoleCache(ERoleId[] roleIds) {
      ERole[] roles = new ERole[roleIds.length];

      for(int i = 0; i < roleIds.length; ++i) {
         roles[i] = this.getFromRoleCache(roleIds[i].getResourceName(), roleIds[i].getRoleName());
      }

      return roles;
   }

   public ERole getFromRoleCache(String resName, String roleName) {
      ERole role = null;
      RoleCacheEntry roleMap = (RoleCacheEntry)this.mRoleCache.get(resName);
      if (roleMap != null) {
         role = (ERole)roleMap.get(roleName);
         if (role == null && roleMap.all) {
            role = NO_ROLE;
         }
      }

      return role;
   }

   private void updateResourceCache(String resourceName, EResource resource) {
      this.mResourceCache.put(resourceName, resource);
   }

   private void updateResourceCache(EResource[] resources) {
      for(int i = 0; i < resources.length; ++i) {
         this.updateResourceCache(resources[i].getName(), resources[i]);
      }

   }

   private void removeFromResourceCache(String[] resourceNames) {
      for(int i = 0; i < resourceNames.length; ++i) {
         this.updateResourceCache(resourceNames[i], NO_RESOURCE);
      }

   }

   private void discardFromResourceCache(String[] resourceNames) {
      for(int i = 0; i < resourceNames.length; ++i) {
         this.updateResourceCache(resourceNames[i], (EResource)null);
      }

   }

   private EResource[] getFromResourceCache(String[] resourceNames) {
      EResource[] resources = new EResource[resourceNames.length];

      for(int i = 0; i < resourceNames.length; ++i) {
         resources[i] = (EResource)this.mResourceCache.get(resourceNames[i]);
      }

      return resources;
   }

   private void preload() {
      if (LOG.isDebugEnabled()) {
         LOG.debug("Preloading resources");
      }

      String[] resources = (String[])((String[])this.getResourceNames((String)null).toArray(new String[0]));

      int i;
      try {
         this.getResources(resources, false);
      } catch (EnFinderException var4) {
         i = var4.getTargetIndex();
         abort("Cannot find resource " + (i < 0 ? "" : resources[i]), var4);
      }

      if (LOG.isDebugEnabled()) {
         LOG.debug("Preloading roles");
      }

      int rcSize = Math.min(this.mRoleCache.getMaximumSize() - 1, resources.length);

      for(i = 0; i < rcSize; ++i) {
         this.getRoles(resources[i]);
      }

      if (LOG.isDebugEnabled()) {
         LOG.debug("Preloading global roles");
      }

      this.getRoles("");
   }

   public EnResourceCursor listResources(String nameFilter, int max, EnCursorResourceFilter cursorFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("listResources (" + nameFilter + " , " + max + " , " + (cursorFilter == null ? "null" : "filter") + ")\n");
      }

      TextFilter filter = nameFilter == null ? null : new TextFilter(nameFilter);
      return this.mEnData.findResources(filter, max, cursorFilter);
   }

   public EnRoleCursor listRoles(String nameFilter, String roleFilter, int max, EnCursorRoleFilter cursorFilter) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("listRoles (" + nameFilter + " , " + roleFilter + " , " + max + " , " + (cursorFilter == null ? "null" : "filter") + ")\n");
      }

      TextFilter filter = nameFilter == null ? null : new TextFilter(nameFilter);
      TextFilter role = roleFilter == null ? null : new TextFilter(roleFilter);
      return this.mEnData.findRoles(filter, role, max, cursorFilter);
   }

   public void createPolicyCollectionInfo(String name, String version, String timeStamp) throws EnCreateException, EnConflictException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("createPolicyCollectionInfo (" + name + " , " + version + " , " + timeStamp + ")\n");
      }

      this.mEnData.createPolicyCollectionInfo(name, version, timeStamp);
   }

   public void createRoleCollectionInfo(String name, String version, String timeStamp) throws EnCreateException, EnConflictException {
      if (LOG.isDebugEnabled()) {
         LOG.debug("createRoleCollectionInfo (" + name + " , " + version + " , " + timeStamp + ")\n");
      }

      this.mEnData.createRoleCollectionInfo(name, version, timeStamp);
   }

   public EPolicyCollectionInfo fetchPolicyCollectionInfo(String name) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("fetchPolicyCollectionInfo (" + name + ")\n");
      }

      return this.mEnData.fetchPolicyCollectionInfo(name);
   }

   public ERoleCollectionInfo fetchRoleCollectionInfo(String name) {
      if (LOG.isDebugEnabled()) {
         LOG.debug("fetchRoleCollectionInfo (" + name + ")\n");
      }

      return this.mEnData.fetchRoleCollectionInfo(name);
   }

   private static class RoleCacheEntry extends HashMap {
      public volatile boolean all = false;

      public RoleCacheEntry() {
      }

      public RoleCacheEntry(int size) {
         super(size);
      }
   }

   private static class ERoleDependentDecision extends EDecision {
      private ECacheableRoles roleDependency;

      public ERoleDependentDecision(Boolean decision, ECacheableRoles roleDependency) {
         super(decision);
         this.roleDependency = roleDependency;
      }

      public boolean isApplicable(Map roles) {
         return roles != null && roles instanceof ERoleMapImpl && ((ERoleMapImpl)roles).ecr == this.roleDependency;
      }
   }

   private static class EDecision {
      private Boolean decision;

      public EDecision(Boolean decision) {
         this.decision = decision;
      }

      public Boolean getDecision() {
         return this.decision;
      }

      public boolean isApplicable(Map roles) {
         return true;
      }
   }

   private class ERoleMapImpl implements Map {
      private ECacheableRoles ecr;
      private boolean loaded = false;
      private Map allowedRoles;
      private Set deniedRoles;
      private Subject subj;
      private Resource resource;
      private ContextHandler context;
      private ESubject eSubj;
      private ResourceNode eResource;

      public ERoleMapImpl(Subject subj, Resource resource, ContextHandler context, ECacheableRoles ecr) {
         this.subj = subj;
         this.resource = resource;
         this.context = context;
         this.allowedRoles = null;
         this.deniedRoles = null;
         this.ecr = ecr;
      }

      private void load() {
         if (!this.loaded) {
            if (this.eResource == null) {
               this.eResource = new ResourceNodeImpl(this.resource);
            }

            if (EEngine.LOG.isDebugEnabled()) {
               if (this.eSubj == null) {
                  this.eSubj = new ESubjectImpl(this.subj);
               }

               EEngine.LOG.debug("getRoles (" + this.eSubj + " , " + this.eResource.getName() + ")\n");
            }

            Collection roles = EEngine.this.getRoles(this.eResource);
            Iterator iter = roles.iterator();

            while(iter.hasNext()) {
               this.evaluateRolePermit((ERole)iter.next());
            }

            this.loaded = true;
            this.deniedRoles = null;
            this.ecr.getDeniedRoles().clear();
         }

      }

      private void evaluateRolePermit(ERole role) {
         Object roleName = role.getName();
         if (!this.ecr.getDeniedRoles().contains(roleName) && (this.deniedRoles == null || !this.deniedRoles.contains(roleName)) && !this.ecr.getAllowedRoles().containsKey(roleName) && (this.allowedRoles == null || !this.allowedRoles.containsKey(roleName)) && this.evaluateRole(role)) {
            if (EEngine.this.checkCacheability(role.getExpression(), 1)) {
               this.ecr.getAllowedRoles().put(roleName, new ESecurityRoleImpl(roleName));
            } else {
               if (this.allowedRoles == null) {
                  this.allowedRoles = new HashMap();
               }

               this.allowedRoles.put(roleName, new ESecurityRoleImpl(roleName));
            }
         }

      }

      private boolean evaluateRoleRecord(String roleName) {
         if (!this.ecr.getAllowedRoles().containsKey(roleName) && (this.allowedRoles == null || !this.allowedRoles.containsKey(roleName))) {
            if (!this.loaded && !this.ecr.getDeniedRoles().contains(roleName) && (this.deniedRoles == null || !this.deniedRoles.contains(roleName))) {
               if (this.eResource == null) {
                  this.eResource = new ResourceNodeImpl(this.resource);
               }

               ERole role = EEngine.this.getRole(this.eResource, roleName);
               if (role == null) {
                  if (EEngine.LOG.isDebugEnabled()) {
                     EEngine.LOG.debug("No role found, cannot evaluate");
                  }

                  return false;
               } else if (this.evaluateRole(role)) {
                  if (EEngine.this.checkCacheability(role.getExpression(), 1)) {
                     this.ecr.getAllowedRoles().put(roleName, new ESecurityRoleImpl(roleName));
                  } else {
                     if (this.allowedRoles == null) {
                        this.allowedRoles = new HashMap();
                     }

                     this.allowedRoles.put(roleName, new ESecurityRoleImpl(roleName));
                  }

                  return true;
               } else {
                  if (EEngine.this.checkCacheability(role.getExpression(), 1)) {
                     this.ecr.getDeniedRoles().add(roleName);
                  } else {
                     if (this.deniedRoles == null) {
                        this.deniedRoles = new HashSet();
                     }

                     this.deniedRoles.add(roleName);
                  }

                  return false;
               }
            } else {
               return false;
            }
         } else {
            return true;
         }
      }

      public boolean isCacheableOnly() {
         return this.allowedRoles == null && this.deniedRoles == null;
      }

      private boolean evaluateRole(ERole role) {
         if (this.eSubj == null) {
            this.eSubj = new ESubjectImpl(this.subj);
         }

         if (this.eResource == null) {
            this.eResource = new ResourceNodeImpl(this.resource);
         }

         boolean result = EEngine.this.evaluate(this.eSubj, role, this.eResource, this.context);
         if (EEngine.LOG.isDebugEnabled()) {
            EEngine.LOG.debug((result ? "Role is permitted: " : "Role is denied: ") + role.getPrimaryKey());
         }

         return result;
      }

      public Object get(Object key) {
         if (this.containsKey(key)) {
            Object o = this.ecr.getAllowedRoles().get(key);
            return o != null ? o : this.allowedRoles.get(key);
         } else {
            return null;
         }
      }

      public boolean containsKey(Object key) {
         return key == null ? false : this.evaluateRoleRecord(key.toString());
      }

      public Object put(Object key, Object value) {
         throw new UnsupportedOperationException(SecurityLogger.getMapCanNotBeModified());
      }

      public void putAll(Map t) {
         throw new UnsupportedOperationException(SecurityLogger.getMapCanNotBeModified());
      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException(SecurityLogger.getMapCanNotBeModified());
      }

      public void clear() {
         throw new UnsupportedOperationException(SecurityLogger.getMapCanNotBeModified());
      }

      public boolean containsValue(Object value) {
         Object key = value instanceof SecurityRole ? ((SecurityRole)value).getName() : value;
         if (!this.ecr.getDeniedRoles().contains(key) && (this.deniedRoles == null || !this.deniedRoles.contains(key))) {
            this.load();
            return this.ecr.getAllowedRoles().containsValue(value) || this.allowedRoles != null && this.allowedRoles.containsValue(value);
         } else {
            return false;
         }
      }

      public boolean isEmpty() {
         boolean empty = this.ecr.getAllowedRoles().isEmpty() && (this.allowedRoles == null || this.allowedRoles.isEmpty());
         if (empty && !this.loaded) {
            this.load();
            empty = this.ecr.getAllowedRoles().isEmpty() && (this.allowedRoles == null || this.allowedRoles.isEmpty());
         }

         return empty;
      }

      public int size() {
         this.load();
         int size = this.ecr.getAllowedRoles().size();
         if (this.allowedRoles != null) {
            size += this.allowedRoles.size();
         }

         return size;
      }

      public Set entrySet() {
         this.load();
         if (this.allowedRoles == null) {
            return this.ecr.getAllowedRoles().entrySet();
         } else {
            return (Set)(this.ecr.getAllowedRoles().isEmpty() ? this.allowedRoles.entrySet() : new CombinedSet(new Collection[]{this.allowedRoles.entrySet(), this.ecr.getAllowedRoles().entrySet()}));
         }
      }

      public Set keySet() {
         this.load();
         if (this.allowedRoles == null) {
            return this.ecr.getAllowedRoles().keySet();
         } else {
            return (Set)(this.ecr.getAllowedRoles().isEmpty() ? this.allowedRoles.keySet() : new CombinedSet(new Collection[]{this.allowedRoles.keySet(), this.ecr.getAllowedRoles().keySet()}));
         }
      }

      public Collection values() {
         this.load();
         if (this.allowedRoles == null) {
            return this.ecr.getAllowedRoles().values();
         } else {
            return (Collection)(this.ecr.getAllowedRoles().isEmpty() ? this.allowedRoles.values() : new CombinedSet(new Collection[]{this.allowedRoles.values(), this.ecr.getAllowedRoles().values()}));
         }
      }

      public String toString() {
         Iterator roleNames = this.keySet().iterator();
         StringBuffer str = new StringBuffer();
         if (roleNames.hasNext()) {
            str.append(roleNames.next());
         }

         while(roleNames.hasNext()) {
            str.append(",").append(roleNames.next());
         }

         return str.toString();
      }
   }

   private static class ECacheableRoles {
      private Map allowedRoles = new ConcurrentHashMap();
      private Set deniedRoles = new ConcurrentHashSet();

      public ECacheableRoles() {
      }

      public Map getAllowedRoles() {
         return this.allowedRoles;
      }

      public Set getDeniedRoles() {
         return this.deniedRoles;
      }
   }

   private static class ESecurityRoleImpl implements SecurityRole {
      private String roleName;

      public ESecurityRoleImpl(Object roleName) {
         this.roleName = roleName.toString();
      }

      public String getName() {
         return this.roleName;
      }

      public String getDescription() {
         return this.roleName;
      }

      public String toString() {
         return this.roleName;
      }

      public int hashCode() {
         return this.roleName.hashCode();
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ESecurityRoleImpl)) {
            return false;
         } else {
            ESecurityRoleImpl oi = (ESecurityRoleImpl)other;
            return this.roleName.equals(oi.roleName);
         }
      }
   }
}
