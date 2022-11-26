package weblogic.entitlement.data.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPModification;
import netscape.ldap.LDAPSearchResults;
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
import weblogic.entitlement.data.EnStorageException;
import weblogic.entitlement.expression.EAuxiliary;
import weblogic.entitlement.expression.EExprRep;
import weblogic.entitlement.expression.EExpression;
import weblogic.entitlement.util.Escaping;
import weblogic.entitlement.util.TextFilter;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.ldap.EmbeddedLDAPChange;
import weblogic.ldap.EmbeddedLDAPChangeListener;
import weblogic.security.utils.ProviderUtilsService;
import weblogic.utils.LocatorUtilities;

public class EData extends EnLDAP implements EnData {
   private String roleBaseDN = null;
   private String resourceBaseDN = null;
   private String predicateBaseDN = null;
   private String policyCollectionBaseDN = null;
   private String roleCollectionBaseDN = null;
   private static final String eexprAttribute = "EExpr";
   private static final String auxAttribute = "EAux";
   private static final String[] ROLE_OBJ_CLASSES = new String[]{"top", "ERole"};
   private static final String[] RESOURCE_OBJ_CLASSES = new String[]{"top", "EResource"};
   private static final String[] PREDICATE_OBJ_CLASSES = new String[]{"top", "EPredicate"};
   private static final String[] POLICY_COL_OBJ_CLASSES = new String[]{"top", "wlsPolicyCollectionInfo"};
   private static final String[] ROLE_COL_OBJ_CLASSES = new String[]{"top", "wlsRoleCollectionInfo"};
   private static final String roleBaseName = "ERole";
   private static final String resourceBaseName = "EResource";
   private static final String predicateBaseName = "EPredicate";
   private static final String policyCollectionBaseName = "EPolicyCollectionInfo";
   private static final String roleCollectionBaseName = "ERoleCollectionInfo";
   private static final String WLSCREATORINFO = "wlsCreatorInfo";
   private static final String wlsCollectionName = "wlsCollectionName";
   private static final String wlsCollectionVersion = "wlsCollectionVersion";
   private static final String wlsCollectionTimestamp = "wlsCollectionTimestamp";
   private static final String[] eexprAttrList = new String[]{"EExpr", "EAux", "wlsCreatorInfo", "wlsCollectionName"};
   private static final String[] conflictAttrList = new String[]{"wlsCreatorInfo", "wlsCollectionName"};
   private static final String[] collectionAttrList = new String[]{"wlsCollectionName", "wlsCollectionVersion", "wlsCollectionTimestamp"};
   private static final char[] SPECIAL_CHARS = new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/'};
   public static final Escaping escaper;

   public EData(Properties env) {
      super(env);
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("Initializing EData.");
      }

      this.roleBaseDN = "ou=ERole," + this.realmDN;
      this.resourceBaseDN = "ou=EResource," + this.realmDN;
      this.predicateBaseDN = "ou=EPredicate," + this.realmDN;
      this.policyCollectionBaseDN = "ou=EPolicyCollectionInfo," + this.realmDN;
      this.roleCollectionBaseDN = "ou=ERoleCollectionInfo," + this.realmDN;
      LDAPConnection ld = null;

      try {
         ld = getConnection();
         this.createHierachy(ld);
      } catch (LDAPException var7) {
         ld = null;
         throw new EnStorageException(var7.getMessage());
      } finally {
         if (ld != null) {
            releaseConnection(ld);
         }

      }

   }

   private void createHierachy(LDAPConnection conn) throws LDAPException {
      addStructuralEntry(conn, this.roleBaseDN, false, "ERole");
      addStructuralEntry(conn, this.resourceBaseDN, false, "EResource");
      addStructuralEntry(conn, this.predicateBaseDN, false, "EPredicate");
      addStructuralEntry(conn, this.policyCollectionBaseDN, false, "EPolicyCollectionInfo");
      addStructuralEntry(conn, this.roleCollectionBaseDN, false, "ERoleCollectionInfo");
   }

   private static void checkDuplicateException(LDAPException e) throws EnDuplicateKeyException {
      checkStorageException(e);
      if (e.getLDAPResultCode() == 68) {
         throw new EnDuplicateKeyException(e.toString());
      }
   }

   private static void checkFinderException(LDAPException e) throws EnFinderException {
      checkStorageException(e);
      if (e.getLDAPResultCode() == 32) {
         throw new EnFinderException(e.toString());
      }
   }

   public Collection fetchRoleIds(String res, TextFilter filter) {
      String nameFilter = filter == null ? "*" : filter.toString(escaper, "*");
      res = escaper.escapeString(res);
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetchRoleIds: ");
      }

      return this.fetchERoleIds(PK2Name(res, nameFilter), this.roleBaseDN, "cn");
   }

   private Collection fetchERoleIds(String nameFilter, String baseDN, String nameAttr) {
      ArrayList c = new ArrayList();
      LDAPConnection conn = null;

      try {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug(nameFilter);
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(baseDN, 1, nameAttr + "=" + nameFilter, noAttrs, false);

         while(result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            ERoleId id = name2PK(name);
            c.add(id);
         }
      } catch (LDAPException var13) {
         checkStorageException(var13);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return c;
   }

   public Collection fetchResourceRoleIds(TextFilter filter) {
      String nameFilter = filter == null ? "*" : filter.toString(escaper, "*");
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetchResourceRoleIds: ");
      }

      return this.fetchERoleIds(PK2Name(nameFilter, "*"), this.roleBaseDN, "cn");
   }

   public Collection fetchGlobalRoles() {
      return this.fetchRoles("", (TextFilter)null);
   }

   public Collection fetchRoles(String resourceName) {
      return this.fetchRoles(resourceName, (TextFilter)null);
   }

   public Collection fetchRoles(String res, TextFilter filter) {
      ArrayList c = new ArrayList();
      LDAPConnection conn = null;

      try {
         res = escaper.escapeString(res);
         String escapedRol = filter == null ? "*" : filter.toString(escaper, "*");
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("fetchRoles(" + res + "," + escapedRol + ") ==> ");
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleBaseDN, 1, "cn=" + PK2Name(res, escapedRol), eexprAttrList, false);

         while(result.hasMoreElements()) {
            c.add(this.getRoleFromEntry(result.next()));
         }
      } catch (LDAPException var10) {
         checkStorageException(var10);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return c;
   }

   private Collection fetchNames(TextFilter filter, String baseDN, String nameAttr) {
      ArrayList c = new ArrayList();
      LDAPConnection conn = null;

      try {
         String nameFilter = filter == null ? "*" : filter.toString(escaper, "*");
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug(nameFilter);
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(baseDN, 1, nameAttr + "=" + nameFilter, noAttrs, false);

         while(result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            name = escaper.unescapeString(name);
            c.add(name);
         }
      } catch (LDAPException var13) {
         checkStorageException(var13);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return c;
   }

   public Collection fetchResourceNames(TextFilter filter) {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetchResourceNames: ");
      }

      return this.fetchNames(filter, this.resourceBaseDN, "cn");
   }

   public Collection fetchResources(TextFilter filter) {
      ArrayList c = new ArrayList();
      LDAPConnection conn = null;

      try {
         String nameFilter = filter == null ? "*" : filter.toString(escaper, "*");
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("fetchResources(" + nameFilter + ")");
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, "cn=" + nameFilter, eexprAttrList, false);

         while(result.hasMoreElements()) {
            c.add(this.getResourceFromEntry(result.next()));
         }
      } catch (LDAPException var9) {
         checkStorageException(var9);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return c;
   }

   public ERole[] fetchRoles(ERoleId[] roles, boolean ignoreNotFound) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetch roles");
      }

      ERole[] r = new ERole[roles.length];
      LDAPConnection conn = null;

      try {
         String[] names = getRoleNames(roles);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), eexprAttrList, false);
         HashMap roleMaps = new HashMap(roles.length);

         while(result.hasMoreElements()) {
            ERole role = this.getRoleFromEntry(result.next());
            roleMaps.put(role.getPrimaryKey(), role);
         }

         for(int i = 0; i < roles.length; ++i) {
            r[i] = (ERole)roleMaps.get(roles[i]);
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("role[" + i + "]=" + roles[i] + (r[i] == null ? "Not Found" : r[i].getEntitlement()));
            }

            if (r[i] == null && !ignoreNotFound) {
               throw new EnFinderException("Role '" + roles[i].getRoleName() + "' not found.");
            }
         }
      } catch (LDAPException var12) {
         checkStorageException(var12);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return r;
   }

   public EResource[] fetchResources(String[] resourceNames, boolean ignoreNotFound) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetch resources");
      }

      EResource[] r = new EResource[resourceNames.length];
      LDAPConnection conn = null;

      try {
         String[] names = new String[resourceNames.length];

         for(int i = 0; i < names.length; ++i) {
            names[i] = escaper.escapeString(resourceNames[i]);
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, makeNameFilter(names), eexprAttrList, false);
         HashMap map = new HashMap(resourceNames.length);

         while(result.hasMoreElements()) {
            EResource res = this.getResourceFromEntry(result.next());
            map.put(res.getName(), res);
         }

         for(int i = 0; i < resourceNames.length; ++i) {
            r[i] = (EResource)map.get(resourceNames[i]);
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("resource[" + i + "]=" + resourceNames[i] + " : " + (r[i] == null ? "Not Found" : r[i].getEntitlement()));
            }

            if (r[i] == null && !ignoreNotFound) {
               throw new EnFinderException("Resource '" + resourceNames[i] + "' not found.");
            }
         }
      } catch (LDAPException var12) {
         checkStorageException(var12);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return r;
   }

   public void update(ERole[] roles, boolean fromDeploy) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("update roles");
      }

      if (roles.length != 0) {
         LDAPConnection conn = null;

         try {
            String[] names = getRoleNames(roles);
            conn = getConnection();
            LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), noAttrs, true);
            if (countEntries(result) != roles.length) {
               throw new EnFinderException("Attempt to modify unknown role");
            }

            for(int i = 0; i < roles.length; ++i) {
               EExpression e = roles[i].getExpression();
               LDAPAttribute attr = new LDAPAttribute("EExpr", e == null ? null : e.serialize());
               LDAPModification mod = new LDAPModification(2, attr);
               LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", fromDeploy ? "deploy" : "mbean");
               LDAPModification fmod = new LDAPModification(2, fromAttrib);
               String collectionName = roles[i].getCollectionName();
               LDAPModification[] mods;
               if (collectionName != null) {
                  LDAPAttribute cattr = new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName));
                  LDAPModification cmod = new LDAPModification(2, cattr);
                  mods = new LDAPModification[]{mod, fmod, cmod};
               } else {
                  mods = new LDAPModification[]{mod, fmod};
               }

               conn.modify("cn=" + names[i] + "," + this.roleBaseDN, mods);
               if (traceLogger != null && traceLogger.isDebugEnabled()) {
                  traceLogger.debug("role[" + i + "]=" + roles[i].getPrimaryKey() + " : " + roles[i].getEntitlement());
               }
            }
         } catch (LDAPException var19) {
            checkStorageException(var19);
         } finally {
            if (conn != null) {
               releaseConnection(conn);
            }

         }

      }
   }

   public void updateAuxiliary(ERole[] roles, boolean fromDeploy) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("update roles auxiliary");
      }

      if (roles.length != 0) {
         LDAPConnection conn = null;

         try {
            String[] names = getRoleNames(roles);
            conn = getConnection();
            LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), noAttrs, true);
            if (countEntries(result) != roles.length) {
               throw new EnFinderException("Attempt to modify unknown role");
            }

            for(int i = 0; i < roles.length; ++i) {
               EAuxiliary e = roles[i].getAuxiliary();
               LDAPAttribute attr = new LDAPAttribute("EAux", e == null ? null : e.toString());
               LDAPModification mod = new LDAPModification(2, attr);
               LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", fromDeploy ? "deploy" : "mbean");
               LDAPModification fmod = new LDAPModification(2, fromAttrib);
               LDAPModification[] mods = new LDAPModification[]{mod, fmod};
               conn.modify("cn=" + names[i] + "," + this.roleBaseDN, mods);
               if (traceLogger != null && traceLogger.isDebugEnabled()) {
                  traceLogger.debug("role[" + i + "]=" + roles[i].getPrimaryKey() + " : " + roles[i].getAuxiliary());
               }
            }
         } catch (LDAPException var16) {
            checkStorageException(var16);
         } finally {
            if (conn != null) {
               releaseConnection(conn);
            }

         }

      }
   }

   public void update(EResource[] resources, boolean fromDeploy) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("update resources");
      }

      if (resources.length != 0) {
         LDAPConnection conn = null;

         try {
            String[] names = getResourceNames(resources);
            conn = getConnection();
            LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, makeNameFilter(names), noAttrs, true);
            if (countEntries(result) != resources.length) {
               throw new EnFinderException("Attempt to modify unknown resource.");
            }

            for(int i = 0; i < resources.length; ++i) {
               EExpression e = resources[i].getExpression();
               LDAPAttribute attr = new LDAPAttribute("EExpr", e == null ? null : e.serialize());
               LDAPModification mod = new LDAPModification(2, attr);
               LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", fromDeploy ? "deploy" : "mbean");
               LDAPModification fmod = new LDAPModification(2, fromAttrib);
               String collectionName = resources[i].getCollectionName();
               LDAPModification[] mods;
               if (collectionName != null) {
                  LDAPAttribute cattr = new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName));
                  LDAPModification cmod = new LDAPModification(2, cattr);
                  mods = new LDAPModification[]{mod, fmod, cmod};
               } else {
                  mods = new LDAPModification[]{mod, fmod};
               }

               conn.modify("cn=" + names[i] + "," + this.resourceBaseDN, mods);
               if (traceLogger != null && traceLogger.isDebugEnabled()) {
                  traceLogger.debug("resource[" + i + "]=" + resources[i].getName() + " : " + resources[i].getEntitlement());
               }
            }
         } catch (LDAPException var19) {
            checkStorageException(var19);
         } finally {
            if (conn != null) {
               releaseConnection(conn);
            }

         }

      }
   }

   public void create(ERole[] roles, boolean fromDeploy) throws EnDuplicateKeyException, EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("create roles");
      }

      LDAPConnection conn = null;

      try {
         String[] names = getRoleNames(roles);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), noAttrs, false);
         if (result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            String roleName = escaper.unescapeString(name.substring(name.indexOf("::") + "::".length()));
            throw new EnDuplicateKeyException("Role policy definition for '" + roleName + "' already exist.");
         }

         for(int i = 0; i < roles.length; ++i) {
            LDAPAttributeSet attribSet = new LDAPAttributeSet();
            attribSet.add(new LDAPAttribute("objectclass", ROLE_OBJ_CLASSES));
            attribSet.add(new LDAPAttribute("cn", names[i]));
            EExpression e = roles[i].getExpression();
            if (e != null) {
               attribSet.add(new LDAPAttribute("EExpr", e.serialize()));
            }

            EAuxiliary a = roles[i].getAuxiliary();
            if (a != null) {
               attribSet.add(new LDAPAttribute("EAux", a.toString()));
            }

            String roleDN = "cn=" + names[i] + "," + this.roleBaseDN;
            LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", fromDeploy ? "deploy" : "mbean");
            attribSet.add(fromAttrib);
            String collectionName = roles[i].getCollectionName();
            if (collectionName != null) {
               attribSet.add(new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName)));
            }

            LDAPEntry entry = new LDAPEntry(roleDN, attribSet);
            conn.add(entry);
         }
      } catch (LDAPException var17) {
         checkDuplicateException(var17);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void createForCollection(ERole[] roles) throws EnConflictException, EnDuplicateKeyException, EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("create roles for collection");
      }

      LDAPConnection conn = null;

      try {
         String[] names = getRoleNames(roles);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), conflictAttrList, false);
         String roleDN;
         if (result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            boolean fromDeploy = getEntryDeployData(entry);
            String excMsg = "Entitlement role definition for '" + escaper.unescapeString(name) + "' ";
            if (fromDeploy) {
               throw new EnDuplicateKeyException(excMsg + " already exist.");
            }

            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               roleDN = getEntryAttribute(entry, "wlsCollectionName");
               traceLogger.debug("conflict: " + roleDN + " -- " + name);
            }

            throw new EnConflictException(excMsg + " is customized.");
         }

         for(int i = 0; i < roles.length; ++i) {
            LDAPAttributeSet attribSet = new LDAPAttributeSet();
            attribSet.add(new LDAPAttribute("objectclass", ROLE_OBJ_CLASSES));
            attribSet.add(new LDAPAttribute("cn", names[i]));
            EExpression e = roles[i].getExpression();
            if (e != null) {
               attribSet.add(new LDAPAttribute("EExpr", e.serialize()));
            }

            EAuxiliary a = roles[i].getAuxiliary();
            if (a != null) {
               attribSet.add(new LDAPAttribute("EAux", a.toString()));
            }

            roleDN = "cn=" + names[i] + "," + this.roleBaseDN;
            boolean deployData = roles[i].isDeployData();
            LDAPAttribute fromDeploy = new LDAPAttribute("wlsCreatorInfo", deployData ? "deploy" : "mbean");
            attribSet.add(fromDeploy);
            String collectionName = roles[i].getCollectionName();
            if (collectionName != null) {
               attribSet.add(new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName)));
            }

            LDAPEntry entry = new LDAPEntry(roleDN, attribSet);
            conn.add(entry);
         }
      } catch (LDAPException var17) {
         checkDuplicateException(var17);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void create(EResource[] resources, boolean fromDeploy) throws EnDuplicateKeyException, EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("create resources");
      }

      LDAPConnection conn = null;

      try {
         String[] names = getResourceNames(resources);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, makeNameFilter(names), noAttrs, false);
         if (result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            throw new EnDuplicateKeyException("Entitlement policy definition for '" + escaper.unescapeString(name) + "' already exist.");
         }

         for(int i = 0; i < resources.length; ++i) {
            LDAPAttributeSet attribSet = new LDAPAttributeSet();
            attribSet.add(new LDAPAttribute("objectclass", RESOURCE_OBJ_CLASSES));
            attribSet.add(new LDAPAttribute("cn", names[i]));
            EExpression e = resources[i].getExpression();
            if (e != null) {
               attribSet.add(new LDAPAttribute("EExpr", e.serialize()));
            }

            String resourceDN = "cn=" + names[i] + "," + this.resourceBaseDN;
            LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", fromDeploy ? "deploy" : "mbean");
            attribSet.add(fromAttrib);
            String collectionName = resources[i].getCollectionName();
            if (collectionName != null) {
               attribSet.add(new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName)));
            }

            LDAPEntry entry = new LDAPEntry(resourceDN, attribSet);
            conn.add(entry);
         }
      } catch (LDAPException var16) {
         checkDuplicateException(var16);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void removeRoles(ERoleId[] roles) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("remove roles");
      }

      LDAPConnection conn = null;

      try {
         String[] names = getRoleNames(roles);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleBaseDN, 1, makeNameFilter(names), noAttrs, true);
         if (countEntries(result) != roles.length) {
            throw new EnFinderException("Attempt to remove unknown role");
         }

         for(int i = 0; i < roles.length; ++i) {
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("role[" + i + "]=" + roles[i]);
            }

            String roleDN = "cn=" + names[i] + "," + this.roleBaseDN;
            conn.delete(roleDN);
         }
      } catch (LDAPException var10) {
         checkStorageException(var10);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void removeResources(String[] resources) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("remove resources");
      }

      LDAPConnection conn = null;

      try {
         String[] names = new String[resources.length];

         for(int i = 0; i < resources.length; ++i) {
            names[i] = escaper.escapeString(resources[i]);
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, makeNameFilter(names), noAttrs, true);
         if (countEntries(result) != resources.length) {
            throw new EnFinderException("Attempt to remove unknown resource.");
         }

         for(int i = 0; i < resources.length; ++i) {
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("resource[" + i + "]=" + resources[i]);
            }

            String resourceDN = "cn=" + names[i] + "," + this.resourceBaseDN;
            conn.delete(resourceDN);
         }
      } catch (LDAPException var10) {
         checkStorageException(var10);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void createPredicate(String predicateName) throws EnDuplicateKeyException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("create predicate: " + predicateName);
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         LDAPAttributeSet attribSet = new LDAPAttributeSet();
         attribSet.add(new LDAPAttribute("objectclass", PREDICATE_OBJ_CLASSES));
         attribSet.add(new LDAPAttribute("cn", predicateName));
         String predicateDN = "cn=" + predicateName + "," + this.predicateBaseDN;
         LDAPEntry entry = new LDAPEntry(predicateDN, attribSet);
         conn.add(entry);
      } catch (LDAPException var9) {
         checkDuplicateException(var9);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void removePredicate(String predicateName) throws EnFinderException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("remove predicate: " + predicateName);
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         String predicateDN = "cn=" + predicateName + "," + this.predicateBaseDN;
         conn.delete(predicateDN);
      } catch (LDAPException var7) {
         checkFinderException(var7);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public boolean predicateExists(String predicateName) {
      boolean found = false;
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("predicate exists: " + predicateName);
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.predicateBaseDN, 1, "cn=" + predicateName, noAttrs, false);
         found = result.hasMoreElements();
      } catch (LDAPException var8) {
         checkStorageException(var8);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

      return found;
   }

   public Collection fetchPredicates(TextFilter filter) {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("fetch predicates: ");
      }

      return this.fetchNames(filter, this.predicateBaseDN, "cn");
   }

   public void setDataChangeListener(EnDataChangeListener listener) {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("setDataChangeListener()");
      }

      EmbeddedLDAP ldap = EmbeddedLDAP.getEmbeddedLDAP();
      if (ldap != null) {
         LDAPChangeListener l = new LDAPChangeListener(listener);
         ldap.registerChangeListener(this.roleBaseDN, l);
         ldap.registerChangeListener(this.resourceBaseDN, l);
         ldap.registerChangeListener(this.predicateBaseDN, l);
      }

   }

   public void applicationDeletedResources(String applicationName, int compType, String compName) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("application delete resources");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.applicationDeleted(conn, this.resourceBaseDN, applicationName, compType, compName, traceLogger);
      } catch (LDAPException var9) {
         checkStorageException(var9);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void cleanupAfterCollectionResources(String collectionName, long startTime, List removed) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("cleanup after collection resources");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.cleanupAfterCollection(conn, this.resourceBaseDN, collectionName, startTime, removed, traceLogger);
      } catch (LDAPException var10) {
         checkStorageException(var10);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void cleanupAfterCollectionRoles(String collectionName, long startTime, List removed) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("cleanup after collection roles");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.cleanupAfterCollection(conn, this.roleBaseDN, collectionName, startTime, removed, traceLogger);
      } catch (LDAPException var10) {
         checkStorageException(var10);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void cleanupAfterDeployResources(String applicationName, int compType, String compName, long startTime) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("cleanup after deploy resources");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.cleanupAfterAppDeploy(conn, this.resourceBaseDN, applicationName, compType, compName, startTime, traceLogger);
      } catch (LDAPException var11) {
         checkStorageException(var11);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void applicationCopyResources(String sourceAppName, String destAppName) throws EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("application copy resources");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.applicationCopy(conn, this.resourceBaseDN, sourceAppName, destAppName, nameAttributeList, ProviderUtilsService.EXCLUDED_ON_COPY_ATTRS, traceLogger);
      } catch (LDAPException var8) {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("application copy resources exception: " + var8.toString(), var8);
         }

         throw new EnCreateException(var8.toString());
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void applicationDeletedRoles(String applicationName, int compType, String compName) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("application delete roles");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.applicationDeleted(conn, this.roleBaseDN, applicationName, compType, compName, traceLogger);
      } catch (LDAPException var9) {
         checkStorageException(var9);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void cleanupAfterDeployRoles(String applicationName, int compType, String compName, long startTime) throws EnFinderException, EnRemoveException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("cleanup after deploy roles");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.cleanupAfterAppDeploy(conn, this.roleBaseDN, applicationName, compType, compName, startTime, traceLogger);
      } catch (LDAPException var11) {
         checkStorageException(var11);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void applicationCopyRoles(String sourceAppName, String destAppName) throws EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("application copy roles");
      }

      LDAPConnection conn = null;

      try {
         conn = getConnection();
         ProviderUtilsService providerUtils = (ProviderUtilsService)LocatorUtilities.getService(ProviderUtilsService.class);
         providerUtils.applicationCopy(conn, this.roleBaseDN, sourceAppName, destAppName, nameAttributeList, ProviderUtilsService.EXCLUDED_ON_COPY_ATTRS, traceLogger);
      } catch (LDAPException var8) {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("application copy roles exception: " + var8.toString(), var8);
         }

         throw new EnCreateException(var8.toString());
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public static String PK2Name(ERoleId p) {
      return PK2Name(escaper.escapeString(p.getResourceName()), escaper.escapeString(p.getRoleName()));
   }

   protected static String unescapeName(String name) {
      return escaper.unescapeString(name);
   }

   protected static ERoleId name2PK(String name) {
      int i = name.indexOf("::");
      String res = i == 0 ? null : escaper.unescapeString(name.substring(0, i));
      String role = escaper.unescapeString(name.substring(i + "::".length()));
      return new ERoleId(res, role);
   }

   protected static String getEntryName(LDAPEntry entry) {
      String name = entry.getDN();
      name = name.substring(name.indexOf(61) + 1, name.indexOf(44));
      return name;
   }

   private EExpression getEntryExpression(LDAPEntry entry) {
      LDAPAttribute attr = entry.getAttribute("EExpr");
      String eexpr = null;
      if (attr != null) {
         String[] values = attr.getStringValueArray();
         if (values != null && values.length > 0) {
            eexpr = values[0];
         }
      }

      return eexpr == null ? null : EExprRep.deserialize(eexpr);
   }

   protected static String getEntryAuxiliary(LDAPEntry entry) {
      LDAPAttribute attr = entry.getAttribute("EAux");
      String aux = null;
      if (attr != null) {
         String[] values = attr.getStringValueArray();
         if (values != null && values.length > 0) {
            aux = values[0];
         }
      }

      return aux;
   }

   protected static boolean getEntryDeployData(LDAPEntry entry) {
      LDAPAttribute attr = entry.getAttribute("wlsCreatorInfo");
      boolean fromDeploy = false;
      if (attr != null) {
         String deployInfo = null;
         String[] values = attr.getStringValueArray();
         if (values != null && values.length > 0) {
            deployInfo = values[0];
         }

         if ("deploy".equals(deployInfo)) {
            fromDeploy = true;
         }
      }

      return fromDeploy;
   }

   private static int countEntries(LDAPSearchResults results) throws LDAPException {
      int count;
      for(count = 0; results.hasMoreElements(); ++count) {
         results.next();
      }

      return count;
   }

   private static String[] getRoleNames(ERoleId[] roles) {
      String[] names = new String[roles.length];

      for(int i = 0; i < roles.length; ++i) {
         names[i] = PK2Name(roles[i]);
      }

      return names;
   }

   private static String[] getRoleNames(ERole[] roles) {
      String[] names = new String[roles.length];

      for(int i = 0; i < roles.length; ++i) {
         names[i] = PK2Name((ERoleId)roles[i].getPrimaryKey());
      }

      return names;
   }

   private static String[] getResourceNames(EResource[] resources) {
      String[] names = new String[resources.length];

      for(int i = 0; i < resources.length; ++i) {
         names[i] = escaper.escapeString(resources[i].getName());
      }

      return names;
   }

   private static String makeNameFilter(String[] names) {
      StringBuffer filter = new StringBuffer("(|");

      for(int i = 0; i < names.length; ++i) {
         if (names[i] != null) {
            filter.append("(");
            filter.append("cn").append("=").append(names[i]);
            filter.append(')');
         }
      }

      filter.append(')');
      return filter.toString();
   }

   public EnResourceCursor findResources(TextFilter nameFilter, int max, EnCursorResourceFilter cursorFilter) {
      String filter = nameFilter == null ? "*" : nameFilter.toString(escaper, "*");
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("findResources: " + filter);
      }

      EnResourceCursor cursor = null;
      LDAPConnection conn = null;

      try {
         conn = getConnection();
         LDAPSearchResults results = conn.search(this.resourceBaseDN, 1, "cn=" + filter, eexprAttrList, false);
         if (cursorFilter == null) {
            cursor = new EResourceCursor(conn, results, max, this, traceLogger);
         } else {
            cursor = new EResourceCursor(cursorFilter, conn, results, max, this, traceLogger);
         }
      } catch (LDAPException var8) {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("LDAPException while trying to search for resources");
         }

         if (conn != null) {
            releaseConnection(conn);
         }

         checkStorageException(var8);
      }

      return cursor;
   }

   public EnRoleCursor findRoles(TextFilter nameFilter, TextFilter roleFilter, int max, EnCursorRoleFilter cursorFilter) {
      String filter = nameFilter == null ? "*" : nameFilter.toString(escaper, "*");
      String searchFilter = filter;
      if (roleFilter != null) {
         searchFilter = PK2Name(filter, roleFilter.toString(escaper, "*"));
      } else if (!filter.endsWith("*")) {
         searchFilter = filter + "*";
      }

      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("findRoles: " + searchFilter);
      }

      EnRoleCursor cursor = null;
      LDAPConnection conn = null;

      try {
         conn = getConnection();
         LDAPSearchResults results = conn.search(this.roleBaseDN, 1, "cn=" + searchFilter, eexprAttrList, false);
         if (cursorFilter == null) {
            cursor = new ERoleCursor(conn, results, max, this, traceLogger);
         } else {
            cursor = new ERoleCursor(cursorFilter, conn, results, max, this, traceLogger);
         }
      } catch (LDAPException var10) {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("LDAPException while trying to search for roles");
         }

         if (conn != null) {
            releaseConnection(conn);
         }

         checkStorageException(var10);
      }

      return cursor;
   }

   public EResource getResourceFromEntry(LDAPEntry entry) {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("getResourceFromEntry");
      }

      EResource resource = null;
      if (entry != null) {
         String name = getEntryName(entry);
         EExpression eexpr = this.getEntryExpression(entry);
         boolean fromDeploy = getEntryDeployData(entry);
         String collectionName = getEntryAttribute(entry, "wlsCollectionName");
         String ename = unescapeName(name);
         if (collectionName != null) {
            collectionName = unescapeName(collectionName);
         }

         resource = new EResource(ename, eexpr, fromDeploy, collectionName);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("  name: " + ename);
            traceLogger.debug(" eexpr: " + resource.getEntitlement());
            traceLogger.debug("deploy: " + fromDeploy);
            if (collectionName != null) {
               traceLogger.debug(" cname: " + collectionName);
            }
         }
      }

      return resource;
   }

   public ERole getRoleFromEntry(LDAPEntry entry) {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("getRoleFromEntry");
      }

      ERole role = null;
      if (entry != null) {
         String name = getEntryName(entry);
         EExpression eexpr = this.getEntryExpression(entry);
         String aux = getEntryAuxiliary(entry);
         boolean fromDeploy = getEntryDeployData(entry);
         String collectionName = getEntryAttribute(entry, "wlsCollectionName");
         ERoleId erid = name2PK(name);
         EAuxiliary eaux = null;
         if (aux != null) {
            eaux = new EAuxiliary(aux);
         }

         role = new ERole(erid, eexpr, eaux, fromDeploy, collectionName);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("  name: " + erid.toString());
            traceLogger.debug(" eexpr: " + role.getEntitlement());
            traceLogger.debug("deploy: " + fromDeploy);
            traceLogger.debug("   aux: " + eaux);
            if (collectionName != null) {
               traceLogger.debug(" cname: " + collectionName);
            }
         }
      }

      return role;
   }

   public void createForCollection(EResource[] resources) throws EnConflictException, EnDuplicateKeyException, EnCreateException {
      if (traceLogger != null && traceLogger.isDebugEnabled()) {
         traceLogger.debug("create resources for collection");
      }

      LDAPConnection conn = null;

      try {
         String[] names = getResourceNames(resources);
         conn = getConnection();
         LDAPSearchResults result = conn.search(this.resourceBaseDN, 1, makeNameFilter(names), conflictAttrList, false);
         String resourceDN;
         if (result.hasMoreElements()) {
            LDAPEntry entry = result.next();
            String name = getEntryName(entry);
            boolean fromDeploy = getEntryDeployData(entry);
            resourceDN = "Entitlement policy definition for '" + escaper.unescapeString(name) + "' ";
            if (fromDeploy) {
               throw new EnDuplicateKeyException(resourceDN + "already exist.");
            }

            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               String cname = getEntryAttribute(entry, "wlsCollectionName");
               traceLogger.debug("conflict: " + cname + " -- " + name);
            }

            throw new EnConflictException(resourceDN + "is customized.");
         }

         for(int i = 0; i < resources.length; ++i) {
            LDAPAttributeSet attribSet = new LDAPAttributeSet();
            attribSet.add(new LDAPAttribute("objectclass", RESOURCE_OBJ_CLASSES));
            attribSet.add(new LDAPAttribute("cn", names[i]));
            EExpression e = resources[i].getExpression();
            if (e != null) {
               attribSet.add(new LDAPAttribute("EExpr", e.serialize()));
            }

            resourceDN = "cn=" + names[i] + "," + this.resourceBaseDN;
            boolean deployData = resources[i].isDeployData();
            LDAPAttribute fromAttrib = new LDAPAttribute("wlsCreatorInfo", deployData ? "deploy" : "mbean");
            attribSet.add(fromAttrib);
            String collectionName = resources[i].getCollectionName();
            if (collectionName != null) {
               attribSet.add(new LDAPAttribute("wlsCollectionName", escaper.escapeString(collectionName)));
            }

            LDAPEntry entry = new LDAPEntry(resourceDN, attribSet);
            conn.add(entry);
         }
      } catch (LDAPException var16) {
         checkDuplicateException(var16);
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void createPolicyCollectionInfo(String name, String version, String timeStamp) throws EnCreateException, EnConflictException {
      LDAPConnection conn = null;

      try {
         String entryName = escaper.escapeString(name);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("createPolicyCollectionInfo(" + entryName + ")");
         }

         String collectionDN = "wlsCollectionName=" + entryName + "," + this.policyCollectionBaseDN;
         LDAPAttributeSet attribSet = new LDAPAttributeSet();
         attribSet.add(new LDAPAttribute("objectclass", POLICY_COL_OBJ_CLASSES));
         attribSet.add(new LDAPAttribute("wlsCollectionName", entryName));
         if (version != null) {
            attribSet.add(new LDAPAttribute("wlsCollectionVersion", version));
         }

         if (timeStamp != null) {
            attribSet.add(new LDAPAttribute("wlsCollectionTimestamp", timeStamp));
         }

         LDAPEntry entry = new LDAPEntry(collectionDN, attribSet);
         conn = getConnection();

         try {
            conn.add(entry);
         } catch (LDAPException var17) {
            if (var17.getLDAPResultCode() != 68) {
               throw new EnCreateException(var17.toString());
            }

            try {
               conn.delete(entry.getDN());
            } catch (LDAPException var16) {
               if (var16.getLDAPResultCode() == 32) {
                  if (traceLogger != null && traceLogger.isDebugEnabled()) {
                     traceLogger.debug("createPolicyCollectionInfo(): conflict for " + entryName);
                  }

                  throw new EnConflictException(entryName + " conflict.");
               }

               throw new EnCreateException(var16.toString());
            }

            conn.add(entry);
         }
      } catch (LDAPException var18) {
         if (var18.getLDAPResultCode() == 68) {
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("createPolicyCollectionInfo(): conflict for " + name);
            }

            throw new EnConflictException(name + " conflict.");
         }

         throw new EnCreateException(var18.toString());
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public void createRoleCollectionInfo(String name, String version, String timeStamp) throws EnCreateException, EnConflictException {
      LDAPConnection conn = null;

      try {
         String entryName = escaper.escapeString(name);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("createRoleCollectionInfo(" + entryName + ")");
         }

         String collectionDN = "wlsCollectionName=" + entryName + "," + this.roleCollectionBaseDN;
         LDAPAttributeSet attribSet = new LDAPAttributeSet();
         attribSet.add(new LDAPAttribute("objectclass", ROLE_COL_OBJ_CLASSES));
         attribSet.add(new LDAPAttribute("wlsCollectionName", entryName));
         if (version != null) {
            attribSet.add(new LDAPAttribute("wlsCollectionVersion", version));
         }

         if (timeStamp != null) {
            attribSet.add(new LDAPAttribute("wlsCollectionTimestamp", timeStamp));
         }

         LDAPEntry entry = new LDAPEntry(collectionDN, attribSet);
         conn = getConnection();

         try {
            conn.add(entry);
         } catch (LDAPException var17) {
            if (var17.getLDAPResultCode() != 68) {
               throw new EnCreateException(var17.toString());
            }

            try {
               conn.delete(entry.getDN());
            } catch (LDAPException var16) {
               if (var16.getLDAPResultCode() == 32) {
                  if (traceLogger != null && traceLogger.isDebugEnabled()) {
                     traceLogger.debug("createRoleCollectionInfo(): conflict for " + entryName);
                  }

                  throw new EnConflictException(entryName + " conflict.");
               }

               throw new EnCreateException(var16.toString());
            }

            conn.add(entry);
         }
      } catch (LDAPException var18) {
         if (var18.getLDAPResultCode() == 68) {
            if (traceLogger != null && traceLogger.isDebugEnabled()) {
               traceLogger.debug("createRoleCollectionInfo(): conflict for " + name);
            }

            throw new EnConflictException(name + " conflict.");
         }

         throw new EnCreateException(var18.toString());
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }

   }

   public EPolicyCollectionInfo fetchPolicyCollectionInfo(String name) {
      LDAPConnection conn = null;

      try {
         String entryName = escaper.escapeString(name);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("fetchPolicyCollectionInfo(" + entryName + ")");
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.policyCollectionBaseDN, 1, "wlsCollectionName=" + entryName, collectionAttrList, false);

         LDAPEntry entry;
         do {
            if (!result.hasMoreElements()) {
               return null;
            }

            entry = result.next();
         } while(entry == null);

         String rawName = getEntryName(entry);
         String collectionName = unescapeName(rawName);
         String version = getEntryAttribute(entry, "wlsCollectionVersion");
         String timestamp = getEntryAttribute(entry, "wlsCollectionTimestamp");
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("     name: " + collectionName);
            traceLogger.debug("  version: " + version);
            traceLogger.debug("timestamp: " + timestamp);
         }

         EPolicyCollectionInfo var10 = new EPolicyCollectionInfo(collectionName, version, timestamp);
         return var10;
      } catch (LDAPException var14) {
         checkStorageException(var14);
         return null;
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }
   }

   public ERoleCollectionInfo fetchRoleCollectionInfo(String name) {
      LDAPConnection conn = null;

      try {
         String entryName = escaper.escapeString(name);
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("fetchRoleCollectionInfo(" + entryName + ")");
         }

         conn = getConnection();
         LDAPSearchResults result = conn.search(this.roleCollectionBaseDN, 1, "wlsCollectionName=" + entryName, collectionAttrList, false);

         LDAPEntry entry;
         do {
            if (!result.hasMoreElements()) {
               return null;
            }

            entry = result.next();
         } while(entry == null);

         String rawName = getEntryName(entry);
         String collectionName = unescapeName(rawName);
         String version = getEntryAttribute(entry, "wlsCollectionVersion");
         String timestamp = getEntryAttribute(entry, "wlsCollectionTimestamp");
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("     name: " + collectionName);
            traceLogger.debug("  version: " + version);
            traceLogger.debug("timestamp: " + timestamp);
         }

         ERoleCollectionInfo var10 = new ERoleCollectionInfo(collectionName, version, timestamp);
         return var10;
      } catch (LDAPException var14) {
         checkStorageException(var14);
         return null;
      } finally {
         if (conn != null) {
            releaseConnection(conn);
         }

      }
   }

   protected static String getEntryAttribute(LDAPEntry entry, String attribName) {
      LDAPAttribute attr = entry.getAttribute(attribName);
      String attribVal = null;
      if (attr != null) {
         String[] values = attr.getStringValueArray();
         if (values != null && values.length > 0) {
            attribVal = values[0];
         }
      }

      return attribVal;
   }

   static {
      escaper = new Escaping(SPECIAL_CHARS);
   }

   private class LDAPChangeListener implements EmbeddedLDAPChangeListener {
      private EnDataChangeListener listener;

      public LDAPChangeListener(EnDataChangeListener listener) {
         this.listener = listener;
      }

      public void entryChanged(EmbeddedLDAPChange change) {
         String entryName = change.getEntryName();
         if (EnLDAP.traceLogger != null && EnLDAP.traceLogger.isDebugEnabled()) {
            EnLDAP.traceLogger.debug("entryChanged: " + entryName);
         }

         if (entryName != null && entryName.startsWith("cn=")) {
            int index = entryName.indexOf(44, 3);
            if (index > 0) {
               String id = entryName.substring(3, index);
               if (entryName.endsWith(EData.this.roleBaseDN)) {
                  ERoleId roleId = EData.name2PK(id);
                  this.listener.roleChanged(roleId);
               } else {
                  String predClassName;
                  if (entryName.endsWith(EData.this.resourceBaseDN)) {
                     predClassName = EData.escaper.unescapeString(id);
                     this.listener.resourceChanged(predClassName);
                  } else if (entryName.endsWith(EData.this.predicateBaseDN)) {
                     predClassName = EData.escaper.unescapeString(id);
                     this.listener.predicateChanged(predClassName);
                  }
               }
            }
         }

      }
   }
}
