package weblogic.security.jacc.simpleprovider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Security;
import java.security.SecurityPermission;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyContextException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.security.SecurityLogger;
import weblogic.security.jacc.RoleMapperFactory;
import weblogic.utils.AssertionError;

public class PolicyConfigurationImpl implements PolicyConfiguration {
   private PolicyConfiguration nextPC;
   private PolicyConfigurationFactoryImpl pcFactoryImpl;
   private State state;
   private Permissions excludedPermissions = null;
   private Permissions uncheckedPermissions = null;
   private Map rolesToPermissions;
   private String contextId;
   private String contextIdNoNPE;
   private static DebugLogger jaccDebugLogger = DebugLogger.getDebugLogger("DebugSecurityJACCNonPolicy");
   private String appSpecificPolicyDirectory = null;
   private Policy policy = null;
   private boolean policyChanged = false;
   private boolean needRefreshing = false;
   private static String POLICY_URL = "policy.url.";
   private static Object refreshLock = new Object();
   private static Object mapperLock = new Object();
   private String policyFileName;
   private String urlForPolicyFile;
   private static RoleMapperFactory roleMapperFactory;

   public PolicyConfigurationImpl(String contextID, PolicyConfigurationFactoryImpl pcFactoryImpl) {
      this.pcFactoryImpl = pcFactoryImpl;
      this.contextId = contextID;
      this.state = State.OPEN;
      this.nextPC = null;
      this.excludedPermissions = new Permissions();
      this.uncheckedPermissions = new Permissions();
      this.rolesToPermissions = new HashMap();
      this.contextIdNoNPE = this.contextId == null ? "null" : this.contextId;
      PolicyWriter.createRepositoryDirectory();
      this.appSpecificPolicyDirectory = PolicyWriter.generateAppDirectoryFileName(this.contextIdNoNPE);
      if (this.appSpecificPolicyDirectory == null) {
         throw new AssertionError(SecurityLogger.getUnexpectedNullVariable("appSpecificPolicyDirectory"));
      } else {
         try {
            this.policyFileName = this.appSpecificPolicyDirectory + File.separator + "granted.policy";
            File file = new File(this.policyFileName);
            file = file.getAbsoluteFile();
            URI uri = file.toURI();
            URL url = uri.toURL();
            this.urlForPolicyFile = url.toExternalForm();
         } catch (MalformedURLException var9) {
            throw new RuntimeException(SecurityLogger.getUnableToConvertFiletoURL(this.policyFileName, var9), var9);
         }

         synchronized(mapperLock) {
            if (roleMapperFactory == null) {
               try {
                  roleMapperFactory = RoleMapperFactory.getRoleMapperFactory();
               } catch (ClassNotFoundException var7) {
                  throw new RuntimeException(SecurityLogger.logRoleMapperFactoryProblemLoggable().getMessageText(), var7);
               } catch (PolicyContextException var8) {
                  throw new RuntimeException(SecurityLogger.logRoleMapperFactoryProblemLoggable().getMessageText(), var8);
               }
            }
         }

         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl constructor contextId: " + this.contextIdNoNPE + " appSpecificPolicyDirectory of: " + this.appSpecificPolicyDirectory);
         }

      }
   }

   public void addToExcludedPolicy(Permission permission) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null == permission) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy was passed a null Permission, ignoring.");
         }

      } else {
         this.excludedPermissions.add(permission);
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy contextId: " + this.contextIdNoNPE + " added " + permission);
         }

      }
   }

   public void addToExcludedPolicy(PermissionCollection permissions) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null != permissions && permissions.elements().hasMoreElements()) {
         Enumeration e = permissions.elements();

         while(e.hasMoreElements()) {
            Permission perm = (Permission)e.nextElement();
            if (perm != null) {
               this.excludedPermissions.add(perm);
               this.policyChanged = true;
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy(s) contextId: " + this.contextIdNoNPE + " added " + perm);
               }
            } else if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy(s) contains a null Permission, ignoring.");
            }
         }

      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToExcludedPolicy was passed a null or empty PermissionCollection, ignoring.");
         }

      }
   }

   protected Permissions getExcludedPermissions() {
      return this.state != State.INSERVICE ? null : this.excludedPermissions;
   }

   public void addToRole(String roleName, Permission permission) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null == roleName) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole was passed a null roleName, ignoring.");
         }

      } else if (null == permission) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole was passed a null Permission, ignoring.");
         }

      } else {
         Set permissionSet = (HashSet)this.rolesToPermissions.get(roleName);
         if (null == permissionSet) {
            permissionSet = new HashSet();
         }

         permissionSet.add(permission);
         this.rolesToPermissions.put(roleName, permissionSet);
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole contextId: " + this.contextIdNoNPE + " added " + permission + " for role " + roleName);
            StringBuffer sb = new StringBuffer("PolicyConfigurationImpl: addToRole contextId: " + this.contextIdNoNPE + " role " + roleName + " has the following permissions:\n");
            Iterator it = permissionSet.iterator();

            while(it.hasNext()) {
               sb.append((Permission)it.next() + "\n");
            }

            jaccDebugLogger.debug(sb.toString());
         }

      }
   }

   public void addToRole(String roleName, PermissionCollection permissions) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null == roleName) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole was passed a null roleName, ignoring.");
         }

      } else if (null != permissions && permissions.elements().hasMoreElements()) {
         Set permissionSet = (HashSet)this.rolesToPermissions.get(roleName);
         if (null == permissionSet) {
            permissionSet = new HashSet();
         }

         Enumeration e = permissions.elements();

         while(e.hasMoreElements()) {
            Permission permission = (Permission)e.nextElement();
            if (permission != null) {
               permissionSet.add(permission);
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole(s) contextId: " + this.contextIdNoNPE + " added " + permission + " for role " + roleName);
               }
            } else if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole(s) contains a null Permission, ignoring.");
            }
         }

         this.rolesToPermissions.put(roleName, permissionSet);
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer("PolicyConfigurationImpl: addToRole contextId: " + this.contextIdNoNPE + " role " + roleName + " has the following permissions:\n");
            Iterator it = permissionSet.iterator();

            while(it.hasNext()) {
               sb.append((Permission)it.next() + "\n");
            }

            jaccDebugLogger.debug(sb.toString());
         }

      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToRole was passed a null or empty PermissionCollection, ignoring.");
         }

      }
   }

   public void addToUncheckedPolicy(Permission permission) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null == permission) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy was passed a null Permission, ignoring.");
         }

      } else {
         this.uncheckedPermissions.add(permission);
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy contextId: " + this.contextIdNoNPE + " added " + permission);
         }

      }
   }

   public void addToUncheckedPolicy(PermissionCollection permissions) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (null != permissions && permissions.elements().hasMoreElements()) {
         Enumeration e = permissions.elements();

         while(e.hasMoreElements()) {
            Permission permission = (Permission)e.nextElement();
            if (permission != null) {
               this.uncheckedPermissions.add(permission);
               this.policyChanged = true;
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy(s) contextId: " + this.contextIdNoNPE + " added " + permission);
               }
            } else if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy(s) contains a null Permission, ignoring.");
            }
         }

      } else {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy was passed a null or empty PermissionCollection, ignoring.");
         }

      }
   }

   public void commit() throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      synchronized(refreshLock) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl:commit contextId: " + this.contextIdNoNPE);
         }

         if (State.DELETED == this.state) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl:commit policy context in deleted state, unable to commit. contextId: " + this.contextIdNoNPE);
            }

            Loggable loggable = SecurityLogger.getPolicyContextNotOpenLoggable(this.contextIdNoNPE);
            throw new UnsupportedOperationException(loggable.getMessageText());
         } else {
            RoleMapperImpl rmi = (RoleMapperImpl)roleMapperFactory.getRoleMapperForContextID(this.contextId);
            if (rmi == null) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl:commit no rolemapper exists for contextId: " + this.contextIdNoNPE + ". This may be OK.");
               }
            } else {
               HashMap rmHash = (HashMap)rmi.getRolesToPrincipalNames();
               if (jaccDebugLogger.isDebugEnabled() && (rmHash == null || rmHash.size() == 0)) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl:commit no role to principal mappings have been defined for contextId: " + this.contextIdNoNPE + ". This may be OK.");
               }
            }

            String excludedStatements = this.excludedToString();
            String uncheckedGrantedStatements = this.uncheckedGrantedToString();
            if (this.policyChanged) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl:commit policy changed writing out statements");
               }

               PolicyWriter.createAppDirectory(this.appSpecificPolicyDirectory);
               PolicyWriter.writeGrantStatements(this.appSpecificPolicyDirectory, "excluded", excludedStatements);
               PolicyWriter.writeGrantStatements(this.appSpecificPolicyDirectory, "granted", uncheckedGrantedStatements);
               this.policyChanged = false;
            } else if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl:commit policy did not change. No statements will be written.");
            }

            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug("PolicyConfigurationImpl:commit contextId: " + this.contextIdNoNPE + " transitioned from " + this.state + " to " + State.INSERVICE);
            }

            this.state = State.INSERVICE;
            this.needRefreshing = true;
         }
      }
   }

   public void delete() throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      synchronized(refreshLock) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: delete contextId: " + this.contextIdNoNPE);
         }

         this.internalRemoveExcludedPolicy();
         this.internalRemoveUncheckedPolicy();
         this.rolesToPermissions.clear();
         PolicyConfigurationFactoryImpl.removePolicyConfiguration(this.contextId);
         this.nextPC = null;

         try {
            PolicyWriter.deletePolicyFiles(this.appSpecificPolicyDirectory);
         } catch (IOException var5) {
            if (jaccDebugLogger.isDebugEnabled()) {
               jaccDebugLogger.debug(" Caught an IOException while trying to delete " + this.appSpecificPolicyDirectory + " and the .policy files in it");
            }

            throw new PolicyContextException(SecurityLogger.getUnableToDeletePolicyDirectory(var5, this.appSpecificPolicyDirectory));
         }

         this.policy = null;
         State oldState = this.state;
         this.state = State.DELETED;
         this.policyChanged = false;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: delete contextId: " + this.contextIdNoNPE + " transitioned from " + oldState + " to " + this.state);
         }

      }
   }

   public String getContextID() throws PolicyContextException {
      return this.contextId;
   }

   public boolean inService() throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      return this.state == State.INSERVICE;
   }

   public void linkConfiguration(PolicyConfiguration link) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationImpl: linkConfiguration for PC with contextId: " + this.contextIdNoNPE);
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: addToUncheckedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (link == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: linkConfiguration received a null link, ignoring.");
         }

      } else if (link == this) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: linkConfiguration Cannot link to self");
         }

         throw new IllegalArgumentException(SecurityLogger.getCannotLinkPolicyConfigurationToSelf());
      } else {
         if (link instanceof PolicyConfigurationImpl) {
            PolicyConfiguration circleCheck = ((PolicyConfigurationImpl)link).getLink();

            while(circleCheck != null) {
               if (jaccDebugLogger.isDebugEnabled()) {
                  jaccDebugLogger.debug("PolicyConfigurationImpl: linkConfiguration following link to: " + link.getContextID());
               }

               if (circleCheck == this || circleCheck == link) {
                  throw new PolicyContextException(SecurityLogger.getCannotHaveCircularPolicyConfigurationLinks());
               }

               if (circleCheck instanceof PolicyConfigurationImpl) {
                  circleCheck = ((PolicyConfigurationImpl)circleCheck).getLink();
               }
            }
         }

         this.nextPC = link;
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: linkConfiguration for PC with contextId: " + this.contextIdNoNPE + " linked to contextId " + (link.getContextID() == null ? "null" : link.getContextID()));
         }

      }
   }

   private void internalRemoveExcludedPolicy() throws PolicyContextException {
      this.excludedPermissions = new Permissions();
      this.policyChanged = true;
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationImpl: internalRemoveExcludedPolicy contextId: " + this.contextIdNoNPE);
      }

   }

   public void removeExcludedPolicy() throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeExcludedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else {
         this.excludedPermissions = new Permissions();
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeExcludedPolicy contextId: " + this.contextIdNoNPE);
         }

      }
   }

   public void removeRole(String roleName) throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeRole called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else if (roleName == null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeRole received a null roleName, ignoring.");
         }

      } else {
         if (this.rolesToPermissions.containsKey(roleName)) {
            this.rolesToPermissions.remove(roleName);
            this.policyChanged = true;
         } else if ("*".equals(roleName)) {
            boolean wasEmpty = this.rolesToPermissions.isEmpty();
            if (!wasEmpty) {
               this.rolesToPermissions.clear();
               this.policyChanged = true;
            }
         }

         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeRole completed contextId: " + this.contextIdNoNPE + " roleName: " + roleName);
         }

      }
   }

   private void internalRemoveUncheckedPolicy() throws PolicyContextException {
      this.uncheckedPermissions = new Permissions();
      this.policyChanged = true;
      if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationImpl: internalRemoveUncheckedPolicy contextId: " + this.contextIdNoNPE);
      }

   }

   public void removeUncheckedPolicy() throws PolicyContextException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      if (State.OPEN != this.state) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeUncheckedPolicy called on a non-open PolicyConfiguration.");
         }

         throw new UnsupportedOperationException(SecurityLogger.getPolicyContextNotOpen(this.contextIdNoNPE));
      } else {
         this.uncheckedPermissions = new Permissions();
         this.policyChanged = true;
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl: removeUncheckedPolicy contextId: " + this.contextIdNoNPE);
         }

      }
   }

   private String uncheckedGrantedToString() {
      String result = null;
      if (this.uncheckedPermissions != null && this.uncheckedPermissions.elements().hasMoreElements() || this.rolesToPermissions != null && !this.rolesToPermissions.isEmpty()) {
         StringBuffer grantStatement = new StringBuffer(GrantGenerator.generateHeader());
         grantStatement.append(GrantGenerator.generateUncheckedGrants(this.contextId, this.uncheckedPermissions));
         Map currentRolesToPrincipalNames = this.getRolesToPrincipalMapping();
         if (currentRolesToPrincipalNames != null && !currentRolesToPrincipalNames.isEmpty()) {
            Map prunedRolesToPrincipalNames = new HashMap(currentRolesToPrincipalNames);
            Set currentSet = currentRolesToPrincipalNames.keySet();
            Iterator currentRoleIterator = currentSet.iterator();

            while(currentRoleIterator.hasNext()) {
               String currentRoleName = (String)currentRoleIterator.next();
               if (!this.rolesToPermissions.containsKey(currentRoleName)) {
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("PolicyConfigurationImpl: uncheckedGrantedToString contextId: " + this.contextIdNoNPE + " role: " + currentRoleName + " isn't in rolesToPermissions, removing.");
                  }

                  prunedRolesToPrincipalNames.remove(currentRoleName);
               }
            }

            grantStatement.append(GrantGenerator.generateRoleGrants(this.contextId, prunedRolesToPrincipalNames, this.rolesToPermissions));
         }

         result = grantStatement.toString();
      }

      return result;
   }

   private String excludedToString() {
      String result = null;
      if (this.excludedPermissions != null && this.excludedPermissions.elements().hasMoreElements()) {
         StringBuffer grantStatement = new StringBuffer(GrantGenerator.generateHeader());
         grantStatement.append(GrantGenerator.generateExcludedGrants(this.contextId, this.excludedPermissions));
         result = grantStatement.toString();
      }

      return result;
   }

   private Map getRolesToPrincipalMapping() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new SecurityPermission("setPolicy"));
      }

      RoleMapperImpl rmi = null;
      Map rpMap = new HashMap();
      rmi = (RoleMapperImpl)roleMapperFactory.getRoleMapperForContextID(this.contextId);
      if (rmi != null) {
         if (jaccDebugLogger.isDebugEnabled()) {
            jaccDebugLogger.debug("PolicyConfigurationImpl:getRolesToPrincipalMapping found a mapping for contextID: " + this.contextIdNoNPE);
         }

         rpMap = rmi.getRolesToPrincipalNames();
      } else if (jaccDebugLogger.isDebugEnabled()) {
         jaccDebugLogger.debug("PolicyConfigurationImpl:getRolesToPrincipalMapping finds no mappings for contextID: " + this.contextIdNoNPE);
      }

      return (Map)rpMap;
   }

   PolicyConfiguration getLink() {
      return this.nextPC;
   }

   public State getState() {
      return this.state;
   }

   void setStateOpen() {
      this.state = State.OPEN;
   }

   public String getAppSpecificPolicyDirectoryName() {
      return new String(this.appSpecificPolicyDirectory);
   }

   public void refresh() {
      synchronized(refreshLock) {
         if (this.needRefreshing) {
            State var10001 = this.state;
            if (this.state == State.INSERVICE) {
               int i = 0;
               String value = null;
               String urlKey = null;

               do {
                  StringBuilder var10000 = (new StringBuilder()).append(POLICY_URL);
                  ++i;
                  urlKey = var10000.append(i).toString();
                  value = Security.getProperty(urlKey);
               } while(value != null && !value.equals(""));

               try {
                  Security.setProperty(urlKey, this.urlForPolicyFile);
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("PolicyConfigurationImpl:refresh add url: " + urlKey + ": " + this.urlForPolicyFile);
                  }

                  if (this.policy == null) {
                     try {
                        this.policy = (Policy)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                           public Object run() throws PrivilegedActionException {
                              try {
                                 return Policy.getInstance("JavaPolicy", (Policy.Parameters)null);
                              } catch (NoSuchAlgorithmException var2) {
                                 throw new PrivilegedActionException(var2);
                              } catch (Exception var3) {
                                 throw new PrivilegedActionException(var3);
                              }
                           }
                        });
                     } catch (PrivilegedActionException var11) {
                        if (jaccDebugLogger.isDebugEnabled()) {
                           jaccDebugLogger.debug("Failed to create a policy instance: " + var11.getException());
                        }

                        throw new RuntimeException(SecurityLogger.getUnableToCreatePolicyInstance("JavaPolicy", var11.getException()), var11.getException());
                     }
                  } else {
                     this.policy.refresh();
                     if (jaccDebugLogger.isDebugEnabled()) {
                        jaccDebugLogger.debug("PolicyConfigurationImpl:refresh for ContextID: " + this.contextIdNoNPE);
                     }
                  }

                  this.needRefreshing = false;
               } finally {
                  Security.setProperty(urlKey, "");
                  if (jaccDebugLogger.isDebugEnabled()) {
                     jaccDebugLogger.debug("PolicyConfigurationImpl:refresh url: " + urlKey + " reset to \"\" ");
                  }

               }
            }
         }

      }
   }

   protected Policy getPolicy() {
      synchronized(refreshLock) {
         return this.state == State.INSERVICE ? this.policy : null;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\nPolicyConfiguration for context: " + this.contextIdNoNPE);
      sb.append("\nState: " + this.state);
      sb.append("\nAppSpecificPolicyDirectory: " + this.appSpecificPolicyDirectory);

      try {
         sb.append("\nLinked to PolicyConfiguration with contextId: " + (this.nextPC == null ? "null" : this.nextPC.getContextID()));
      } catch (PolicyContextException var4) {
         sb.append("\nLinked to PolicyConfiguration with contextId: Got an exception attempting to access!");
      }

      String excluded = this.excludedToString();
      if (excluded != null) {
         sb.append("\n" + excluded);
      }

      String granted = this.uncheckedGrantedToString();
      if (granted != null) {
         sb.append("\n" + granted);
      }

      sb.append("\n\n");
      return sb.toString();
   }
}
