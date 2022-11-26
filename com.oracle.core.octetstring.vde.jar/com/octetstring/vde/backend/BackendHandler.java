package com.octetstring.vde.backend;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.SubstringFilter_substrings_Seq;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Attribute;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.EntryChangesListener;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.acl.ACL;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.operation.BasePlugin;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.DirectorySchemaViolation;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.oro.text.perl.Perl5Util;

public class BackendHandler {
   private static BackendHandler handler = null;
   private static Hashtable handlerTable = null;
   private static BasePlugin plugin_postSearch = null;
   private static Mapper mapper = null;
   private static final DirectoryString CHANGELOG_SUFFIX = new DirectoryString("cn=ChangeLog");
   private static Vector entryChangesListeners = null;
   private static final DirectoryString ROOT_SUFFIX = new DirectoryString("");
   private static final DirectoryString SCHEMA_SUFFIX = new DirectoryString("cn=Schema");
   private static final DirectoryString CONFIG_SUFFIX = new DirectoryString("cn=Config");
   private static final DirectoryString AT_SUBTREEACI = new DirectoryString("subtreeACI");
   private static final DirectoryString AT_ENTRYACI = new DirectoryString("entryACI");
   private static final DirectoryString AT_CREATE_TIMESTAMP = new DirectoryString("createTimestamp");
   private static final DirectoryString AT_CREATOR_NAME = new DirectoryString("creatorsName");
   private static final DirectoryString AT_MODIFY_TIMESTAMP = new DirectoryString("modifyTimestamp");
   private static final DirectoryString AT_MODIFIERS_NAME = new DirectoryString("modifiersName");
   private static final DirectoryString DOT = new DirectoryString(".");
   private static final DirectoryString ALL_ATTRIBUTES = new DirectoryString("*");
   private static Vector operationalAttributes = new Vector();
   private static Hashtable replicas = null;
   private static Hashtable replicausers = null;
   private boolean wlock = false;

   private BackendHandler(Properties overrideBackendProps) {
      Logger.getInstance().log(5, this, Messages.getString("Initializing_Adapters_13"));
      handlerTable = new Hashtable();
      Hashtable schemaConfig = new Hashtable();
      schemaConfig.put("suffix", SCHEMA_SUFFIX);
      this.registerBackend(SCHEMA_SUFFIX, new BackendSchema(schemaConfig));
      Hashtable rootConfig = new Hashtable();
      rootConfig.put("suffix", ROOT_SUFFIX);
      this.registerBackend(ROOT_SUFFIX, new BackendRoot(rootConfig));
      Hashtable configConfig = new Hashtable();
      configConfig.put("suffix", CONFIG_SUFFIX);
      this.registerBackend(CONFIG_SUFFIX, new BackendConfig(configConfig));
      String ihome = System.getProperty("vde.home");
      Properties backendTypes = new Properties();

      String nb;
      File fullname;
      try {
         String name = (String)ServerConfig.getInstance().get("vde.backendtypes");
         nb = null;
         fullname = new File(name);
         if (!fullname.isAbsolute() && ihome != null) {
            nb = ihome + "/" + name;
         } else {
            nb = name;
         }

         FileInputStream is = new FileInputStream(nb);
         backendTypes.load(is);
         is.close();
      } catch (Exception var27) {
         Logger.getInstance().log(0, this, Messages.getString("Error_parsing_Backend_Types_file._19"));
      }

      Properties schemaProp = new Properties();

      String key;
      try {
         nb = (String)ServerConfig.getInstance().get("vde.server.backends");
         fullname = null;
         File file = new File(nb);
         if (!file.isAbsolute() && ihome != null) {
            key = ihome + "/" + nb;
         } else {
            key = nb;
         }

         FileInputStream is = new FileInputStream(key);
         schemaProp.load(is);
         is.close();
      } catch (Exception var26) {
         Logger.getInstance().log(0, this, Messages.getString("Error_parsing_Backend_properties._21"));
      }

      if (overrideBackendProps != null) {
         Iterator it = overrideBackendProps.keySet().iterator();

         while(it.hasNext()) {
            key = (String)it.next();
            schemaProp.setProperty(key, overrideBackendProps.getProperty(key));
         }
      }

      nb = System.getProperty("backend.num");
      if (nb == null) {
         nb = (String)schemaProp.get("backend.num");
      }

      int numBackends = new Integer(nb);

      for(int beCount = 0; beCount < numBackends; ++beCount) {
         String backendPropPrefix = "backend." + beCount;
         String backendRootProp = backendPropPrefix + ".root";
         String suffix = System.getProperty(backendRootProp);
         if (suffix == null) {
            suffix = (String)schemaProp.get(backendRootProp);
         }

         try {
            suffix = DNUtility.getInstance().normalize(new DirectoryString(suffix)).toString();
         } catch (InvalidDNException var25) {
            Logger.getInstance().log(0, this, "Invalid Adapter Suffix: " + suffix);
            break;
         }

         String backendTypeProp = backendPropPrefix + ".type";
         String backendType = System.getProperty(backendTypeProp);
         if (backendType == null) {
            backendType = (String)schemaProp.get(backendTypeProp);
         }

         Logger.getInstance().log(7, this, "Suffix: " + suffix + " Type: " + backendType);
         String configPropPrefix = backendPropPrefix + ".config.";
         int configPrefixLen = configPropPrefix.length();
         Enumeration keys = schemaProp.keys();
         Hashtable beConfig = new Hashtable();
         beConfig.put("suffix", new DirectoryString(suffix));

         String myType;
         String configKey;
         while(keys.hasMoreElements()) {
            myType = (String)keys.nextElement();
            if (myType.startsWith(configPropPrefix)) {
               configKey = myType.substring(configPrefixLen);
               beConfig.put(configKey, schemaProp.get(myType));
            }
         }

         keys = System.getProperties().keys();

         while(keys.hasMoreElements()) {
            myType = (String)keys.nextElement();
            if (myType.startsWith(configPropPrefix)) {
               configKey = myType.substring(configPrefixLen);
               beConfig.put(configKey, System.getProperty(myType));
            }
         }

         try {
            myType = (String)backendTypes.get(backendType);
            if (myType == null) {
               myType = backendType;
            }

            Constructor[] cons = Class.forName(myType).getConstructors();
            Object[] args = new Object[]{beConfig};
            this.registerBackend(new DirectoryString(suffix), (Backend)cons[0].newInstance(args));
         } catch (Exception var24) {
            Logger.getInstance().log(0, this, Messages.getString("Error_Instantiating____39") + suffix + "': " + var24.getMessage());
         }
      }

      mapper = new Mapper();
      operationalAttributes.addElement(AT_CREATOR_NAME);
      operationalAttributes.addElement(AT_CREATE_TIMESTAMP);
      operationalAttributes.addElement(AT_MODIFIERS_NAME);
      operationalAttributes.addElement(AT_MODIFY_TIMESTAMP);
      replicas = new Hashtable();
      replicausers = new Hashtable();
      String postsearchplugin = (String)ServerConfig.getInstance().get("plugin.postsearch");
      if (postsearchplugin != null && !postsearchplugin.equals("")) {
         try {
            Class pluginClass = Class.forName(postsearchplugin);
            plugin_postSearch = (BasePlugin)pluginClass.newInstance();
            Logger.getInstance().log(5, this, "Inserted Plugin: " + postsearchplugin);
         } catch (Exception var23) {
            Logger.getInstance().log(0, this, "Error instantiating: " + postsearchplugin);
         }
      }

   }

   public void addReplica(DirectoryString suffix, Consumer con) {
      replicas.put(suffix, con);
      DirectoryString bdn = con.getBinddn();
      String bpw = con.getBindpw();
      replicausers.put(bdn, bpw);
   }

   public void clearReplicas() {
      if (replicas.size() > 0) {
         replicas = new Hashtable();
      }

      if (replicausers.size() > 0) {
         replicausers = new Hashtable();
      }

   }

   public String getReplicaUser(DirectoryString dn) {
      return (String)replicausers.get(dn);
   }

   public Consumer getReplica(DirectoryString dn) {
      Consumer selected = null;
      int selLength = -1;
      DirectoryString normEntryName = null;

      try {
         normEntryName = DNUtility.getInstance().normalize(dn);
      } catch (InvalidDNException var7) {
         return null;
      }

      Enumeration replicaEnum = replicas.keys();

      while(replicaEnum.hasMoreElements()) {
         DirectoryString base = (DirectoryString)replicaEnum.nextElement();
         if (normEntryName.endsWith(base) && base.length() > selLength) {
            selected = (Consumer)replicas.get(base);
            selLength = base.length();
         }
      }

      return selected;
   }

   public static BackendHandler getInstance() {
      if (handler == null) {
         handler = getInstance((Properties)null);
      }

      return handler;
   }

   public static BackendHandler getInstance(Properties overrideBackendProps) {
      if (handler == null) {
         handler = new BackendHandler(overrideBackendProps);
      }

      return handler;
   }

   public Backend getBackend(DirectoryString entryName) {
      return this.pickBackend(entryName);
   }

   Hashtable getHandlerTable() {
      return handlerTable;
   }

   public synchronized void lockWrites() {
      while(this.wlock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this.wlock = true;
   }

   public synchronized void unlockWrites() {
      this.wlock = false;
      this.notifyAll();
   }

   public synchronized void checkLock() {
      while(this.wlock) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   public Int8 add(Credentials creds, Entry entry) throws DirectorySchemaViolation {
      this.checkLock();
      if (!ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_ADD, entry.getName())) {
         Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___41") + creds + Messages.getString("_to_ADD____42") + entry.getName() + "'");
         return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
      } else {
         Vector vals = entry.get(AT_ENTRYACI);
         ACLChecker ac;
         Enumeration aclEnum;
         DirectoryString oneACL;
         ACL anACI;
         if (vals != null) {
            if (!ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_WRITE, entry.getName(), AT_ENTRYACI)) {
               return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
            }

            entry.remove(AT_ENTRYACI);
            ac = ACLChecker.getInstance();
            ac.deleteACL(entry.getName());
            aclEnum = vals.elements();

            while(aclEnum.hasMoreElements()) {
               oneACL = (DirectoryString)aclEnum.nextElement();
               anACI = new ACL(oneACL.toString());
               anACI.setScopeSubtree(false);
               ac.addACL(entry.getName(), anACI);
            }
         }

         vals = entry.get(AT_SUBTREEACI);
         if (vals != null) {
            if (!ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_WRITE, entry.getName(), AT_SUBTREEACI)) {
               return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
            }

            entry.remove(AT_SUBTREEACI);
            ac = ACLChecker.getInstance();
            ac.deleteACL(entry.getName());
            aclEnum = vals.elements();

            while(aclEnum.hasMoreElements()) {
               oneACL = (DirectoryString)aclEnum.nextElement();
               anACI = new ACL(oneACL.toString());
               anACI.setScopeSubtree(true);
               ac.addACL(entry.getName(), anACI);
            }
         }

         Backend backend = this.pickBackend(entry.getName());
         if (backend != null) {
            aclEnum = null;
            Int8 results;
            if (creds != null) {
               results = backend.add(creds.getUser(), entry);
            } else {
               results = backend.add((DirectoryString)null, entry);
            }

            if (results == LDAPResult.SUCCESS && entryChangesListeners != null) {
               Enumeration ecls = entryChangesListeners.elements();

               while(ecls.hasMoreElements()) {
                  EntryChangesListener ecl = (EntryChangesListener)ecls.nextElement();
                  if (entry.getName().endsWith(ecl.getECLBase())) {
                     ecl.receiveEntryChanges(new EntryChanges(entry));
                  }
               }
            }

            return results;
         } else {
            return LDAPResult.NO_SUCH_OBJECT;
         }
      }
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      Backend backend = this.pickBackend(dn);
      return backend.bind(dn, password);
   }

   public Int8 delete(Credentials creds, DirectoryString name) {
      this.checkLock();
      Backend backend = this.pickBackend(name);
      if (!ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_DELETE, name)) {
         Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___44") + creds + Messages.getString("_to_DELETE____45") + name + "'");
         return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
      } else {
         Int8 results = null;
         if (creds != null) {
            results = backend.delete(creds.getUser(), name);
         } else {
            results = backend.delete((DirectoryString)null, name);
         }

         if (results == LDAPResult.SUCCESS && entryChangesListeners != null) {
            Enumeration ecls = entryChangesListeners.elements();

            while(ecls.hasMoreElements()) {
               EntryChangesListener ecl = (EntryChangesListener)ecls.nextElement();
               if (name.endsWith(ecl.getECLBase())) {
                  ecl.receiveEntryChanges(new EntryChanges(name));
               }
            }
         }

         return results;
      }
   }

   public boolean doBind(DirectoryString dn) {
      Backend backend = this.pickBackend(dn);
      if (backend == null) {
         return false;
      } else {
         boolean retval = backend.doBind();
         return retval;
      }
   }

   public Vector get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean typesOnly, Vector attributes) throws DirectoryException {
      Vector results = new Vector();
      Vector backends = this.pickBackends(base, scope);
      if (backends == null) {
         throw new DirectoryException(32);
      } else {
         Filter newset = new Filter(filter);
         newset = mapper.mapfilter(base, newset);
         DirectoryString mybase = mapper.mapbase(base);
         int myscope = scope;
         Enumeration backEnum = backends.elements();

         while(backEnum.hasMoreElements()) {
            Backend backend = (Backend)backEnum.nextElement();
            if (newset != null) {
               EntrySet partResults = backend.get(binddn, mybase, myscope, newset, typesOnly, attributes);
               if (partResults != null && partResults.hasMore()) {
                  results.addElement(partResults);
               }
            }
         }

         return results;
      }
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) throws DirectoryException {
      DirectoryString normaldn = DNUtility.getInstance().normalize(dn);
      Backend backend = this.pickBackend(normaldn);
      return backend.getByDN(binddn, normaldn);
   }

   public void modify(Credentials creds, DirectoryString name, Vector changeEntries) throws DirectoryException {
      this.checkLock();
      Enumeration changeEnum = changeEntries.elements();

      int changeType;
      DirectoryString attr;
      do {
         do {
            if (!changeEnum.hasMoreElements()) {
               Backend backend = this.pickBackend(name);
               if (creds != null) {
                  backend.modify(creds.getUser(), name, changeEntries);
               } else {
                  backend.modify((DirectoryString)null, name, changeEntries);
               }

               if (entryChangesListeners != null) {
                  Enumeration ecls = entryChangesListeners.elements();

                  while(ecls.hasMoreElements()) {
                     EntryChangesListener ecl = (EntryChangesListener)ecls.nextElement();
                     if (name.endsWith(ecl.getECLBase())) {
                        EntryChange[] ecs = new EntryChange[changeEntries.size()];
                        changeEntries.copyInto(ecs);
                        ecl.receiveEntryChanges(new EntryChanges(name, ecs));
                     }
                  }
               }

               return;
            }

            EntryChange change = (EntryChange)changeEnum.nextElement();
            changeType = change.getModType();
            attr = change.getAttr();
            if (changeType == 0 && !ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_WRITE, name, attr)) {
               Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___47") + creds.getUser() + Messages.getString("_to_MODIFY____48") + name + Messages.getString("___ADD_ATTRIBUTE____49") + attr + "'");
               throw new DirectoryException(50);
            }

            if (changeType == 1 && !ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_OBLITERATE, name, attr)) {
               Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___51") + creds.getUser() + Messages.getString("_to_MODIFY____52") + name + Messages.getString("___DELETE_ATTRIBUTE____53") + attr + "'");
               throw new DirectoryException(50);
            }
         } while(changeType != 2);
      } while(ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_WRITE, name, attr) && ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_OBLITERATE, name, attr));

      Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___55") + creds + Messages.getString("_to_MODIFY____56") + name + Messages.getString("___REPLACE_ATTRIBUTE____57") + attr + "'");
      throw new DirectoryException(50);
   }

   private Backend pickBackend(DirectoryString entryName) {
      Backend selected = null;
      int selLength = -1;
      DirectoryString normEntryName = null;

      try {
         normEntryName = DNUtility.getInstance().normalize(entryName);
      } catch (InvalidDNException var7) {
         return null;
      }

      Enumeration backEnum = handlerTable.keys();

      while(backEnum.hasMoreElements()) {
         DirectoryString base = (DirectoryString)backEnum.nextElement();
         if (normEntryName.endsWith(base) && base.length() > selLength) {
            selected = (Backend)handlerTable.get(base);
            selLength = base.length();
            if (Logger.getInstance().isLogable(9)) {
               Logger.getInstance().log(9, this, Messages.getString("Selected__59") + selected.getClass().getName() + Messages.getString("_backend_for___60") + base);
            }
         }
      }

      return selected;
   }

   private Vector pickBackends(DirectoryString entryName, int scope) {
      Vector backs = new Vector();
      DirectoryString normEntryName = null;

      try {
         normEntryName = DNUtility.getInstance().normalize(entryName);
      } catch (InvalidDNException var8) {
         return null;
      }

      Enumeration backEnum = handlerTable.keys();
      boolean nonroot = false;

      while(true) {
         DirectoryString base;
         do {
            if (!backEnum.hasMoreElements()) {
               if (!nonroot && entryName.length() != 0) {
                  return null;
               }

               return backs;
            }

            base = (DirectoryString)backEnum.nextElement();
         } while(!entryName.endsWith(base) && (scope == 0 || !base.endsWith(normEntryName)));

         if (base.length() != 0) {
            nonroot = true;
         }

         backs.addElement((Backend)handlerTable.get(base));
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Selected_backend_for___61") + base);
         }
      }
   }

   public void registerBackend(DirectoryString base, Backend backend) {
      handlerTable.put(base, backend);
   }

   public void registerEntryChangesListener(EntryChangesListener entryChangesListener) {
      if (entryChangesListeners == null) {
         entryChangesListeners = new Vector();
      }

      entryChangesListeners.addElement(entryChangesListener);
   }

   public void unregisterEntryChangesListener(EntryChangesListener entryChangesListener) {
      if (entryChangesListeners != null) {
         entryChangesListeners.removeElement(entryChangesListener);
      }
   }

   public Int8 rename(Credentials creds, DirectoryString oldname, DirectoryString newname, DirectoryString newsuffix, boolean removeoldrdn) throws DirectoryException {
      this.checkLock();
      oldname = DNUtility.getInstance().normalize(oldname);
      Backend backend = this.pickBackend(oldname);
      Entry oldEntry = null;
      if (creds != null) {
         oldEntry = backend.getByDN(creds.getUser(), oldname);
      } else {
         oldEntry = backend.getByDN((DirectoryString)null, oldname);
      }

      if (oldEntry == null) {
         return LDAPResult.NO_SUCH_OBJECT;
      } else {
         DirectoryString newfullname = null;
         if (newsuffix == null) {
            newfullname = new DirectoryString(newname + "," + oldEntry.getBase());
         } else {
            newfullname = new DirectoryString(newname + "," + newsuffix);
         }

         if (ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_RENAMEDN, oldname) && ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_ADD, newfullname)) {
            Backend newBackend = this.pickBackend(newfullname);
            Int8 result = null;
            Entry current = null;
            if (backend == newBackend) {
               if (creds != null) {
                  result = backend.rename(creds.getUser(), oldname, newname, newsuffix, removeoldrdn);
               } else {
                  result = backend.rename((DirectoryString)null, oldname, newname, newsuffix, removeoldrdn);
               }

               if (result == LDAPResult.SUCCESS && entryChangesListeners != null) {
                  Enumeration ecls = entryChangesListeners.elements();

                  while(true) {
                     while(ecls.hasMoreElements()) {
                        EntryChangesListener ecl = (EntryChangesListener)ecls.nextElement();
                        if (oldname.endsWith(ecl.getECLBase()) && newname.endsWith(ecl.getECLBase())) {
                           ecl.receiveEntryChanges(new EntryChanges(oldname, newfullname));
                        } else if (oldname.endsWith(ecl.getECLBase())) {
                           ecl.receiveEntryChanges(new EntryChanges(oldname));
                        } else if (newname.endsWith(ecl.getECLBase())) {
                           if (current == null) {
                              current = newBackend.getByDN((DirectoryString)null, newfullname);
                           }

                           if (current != null) {
                              ecl.receiveEntryChanges(new EntryChanges(current));
                           }
                        }
                     }

                     return result;
                  }
               } else {
                  return result;
               }
            } else {
               current = (Entry)oldEntry.clone();
               current.setName(newfullname);
               String nextatval;
               StringTokenizer atvaltok;
               String attr;
               DirectoryString dsattr;
               if (removeoldrdn) {
                  Vector rdns = DNUtility.getInstance().explodeDN(oldname);
                  if (rdns.isEmpty()) {
                     return LDAPResult.NAMING_VIOLATION;
                  }

                  nextatval = (String)rdns.elementAt(0);
                  atvaltok = new StringTokenizer(nextatval, "+");

                  while(atvaltok.hasMoreTokens()) {
                     attr = atvaltok.nextToken();
                     StringTokenizer atvaltok = new StringTokenizer(attr, "=");
                     if (atvaltok.hasMoreTokens()) {
                        String attr = atvaltok.nextToken();
                        if (atvaltok.hasMoreTokens()) {
                           String value = atvaltok.nextToken();
                           dsattr = new DirectoryString(attr);
                           Vector vals = current.get(dsattr);
                           if (vals != null) {
                              Syntax synVal = null;
                              AttributeType at = SchemaChecker.getInstance().getAttributeType(dsattr);
                              if (at != null) {
                                 synVal = at.getSyntaxInstance();
                              } else {
                                 synVal = new DirectoryString();
                              }

                              try {
                                 ((Syntax)synVal).setValue(value.getBytes("UTF8"));
                              } catch (UnsupportedEncodingException var25) {
                                 ((Syntax)synVal).setValue(value.getBytes());
                              }

                              vals.removeElement(synVal);
                              if (vals.isEmpty()) {
                                 current.remove(dsattr);
                              }
                           }
                        }
                     }
                  }
               }

               StringTokenizer rdntok = new StringTokenizer(newname.toString(), "+");

               while(rdntok.hasMoreTokens()) {
                  nextatval = rdntok.nextToken();
                  atvaltok = new StringTokenizer(nextatval, "=");
                  if (atvaltok.hasMoreTokens()) {
                     attr = atvaltok.nextToken();
                     if (atvaltok.hasMoreTokens()) {
                        String value = atvaltok.nextToken();
                        DirectoryString dsattr = new DirectoryString(attr);
                        Vector vals = current.get(dsattr);
                        if (vals == null) {
                           vals = new Vector();
                           current.put(dsattr, vals);
                        }

                        dsattr = null;
                        AttributeType at = SchemaChecker.getInstance().getAttributeType(dsattr);
                        Object synVal;
                        if (at != null) {
                           synVal = at.getSyntaxInstance();
                        } else {
                           synVal = new DirectoryString();
                        }

                        try {
                           ((Syntax)synVal).setValue(value.getBytes("UTF8"));
                        } catch (UnsupportedEncodingException var24) {
                           ((Syntax)synVal).setValue(value.getBytes());
                        }

                        if (!vals.contains(synVal)) {
                           vals.addElement(synVal);
                        }
                     }
                  }
               }

               Int8 res;
               if (creds != null) {
                  res = newBackend.add(creds.getUser(), current);
                  if (!res.equals(LDAPResult.SUCCESS)) {
                     return res;
                  } else {
                     return backend.delete(creds.getUser(), oldname);
                  }
               } else {
                  res = newBackend.add((DirectoryString)null, current);
                  if (!res.equals(LDAPResult.SUCCESS)) {
                     return res;
                  } else {
                     return backend.delete((DirectoryString)null, oldname);
                  }
               }
            }
         } else {
            Logger.getInstance().log(7, this, Messages.getString("ACCESS_DENIED___64") + creds + Messages.getString("_to_RENAME____65") + oldname + Messages.getString("_to____66") + newfullname + "'");
            return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
         }
      }
   }

   public Entry map(Entry original) {
      return mapper.map(original);
   }

   private static DirectoryString convertOID(DirectoryString attrname) {
      if (attrname.indexOf(DOT) == -1) {
         DirectoryString tmp = SchemaChecker.getInstance().nameFromOID(attrname);
         return tmp == null ? attrname : tmp;
      } else {
         return attrname;
      }
   }

   public Entry postSearch(Credentials creds, Entry original, Vector returnattrs, Filter filter, int scope, DirectoryString base) {
      Entry nent;
      if (plugin_postSearch != null) {
         nent = plugin_postSearch.postProcess(creds, original);
         if (nent == null) {
            return null;
         }

         original = nent;
      }

      if (original != null && ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_BROWSEDN, original) && ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_RETURNDN, original.getName())) {
         if (!this.scanFilter(creds, original, filter) && !this.evalFilter(creds, original, filter, true)) {
            return null;
         } else {
            if (returnattrs.size() == 1) {
               DirectoryString attr = (DirectoryString)returnattrs.elementAt(0);
               if (attr.equals(ALL_ATTRIBUTES)) {
                  returnattrs = new Vector();
               }
            }

            nent = this.map(original);
            if (scope == 0) {
               if (!base.equals(nent.getName())) {
                  return null;
               }
            } else if (scope == 2) {
               if (!nent.getName().endsWith(base)) {
                  return null;
               }
            } else if (!base.equals(nent.getBase())) {
               return null;
            }

            Entry rent = new Entry();

            try {
               rent.setName(nent.getName(), true);
            } catch (DirectoryException var16) {
            }

            rent.setBase(nent.getBase());
            rent.setID(nent.getID());
            Vector removelist = new Vector();
            Vector acls = null;
            Vector dsACLs;
            Enumeration aclsEnum;
            if (!returnattrs.isEmpty() && returnattrs.contains(AT_ENTRYACI)) {
               acls = ACLChecker.getInstance().getEntryACLs(nent.getName());
               if (acls != null) {
                  dsACLs = new Vector();
                  aclsEnum = acls.elements();

                  while(aclsEnum.hasMoreElements()) {
                     dsACLs.addElement(new DirectoryString(((ACL)aclsEnum.nextElement()).toString()));
                  }

                  nent.put(AT_ENTRYACI, dsACLs);
               }
            }

            if (!returnattrs.isEmpty() && returnattrs.contains(AT_SUBTREEACI)) {
               acls = ACLChecker.getInstance().getSubtreeACLs(nent.getName());
               if (acls != null) {
                  dsACLs = new Vector();
                  aclsEnum = acls.elements();

                  while(aclsEnum.hasMoreElements()) {
                     dsACLs.addElement(new DirectoryString(((ACL)aclsEnum.nextElement()).toString()));
                  }

                  nent.put(AT_SUBTREEACI, dsACLs);
               }
            }

            Enumeration opatEnum = operationalAttributes.elements();

            while(opatEnum.hasMoreElements()) {
               DirectoryString opat = (DirectoryString)opatEnum.nextElement();
               if (returnattrs.isEmpty()) {
                  removelist.addElement(opat);
               } else if (!returnattrs.contains(opat)) {
                  removelist.addElement(opat);
               }
            }

            Vector attributes = new Vector();
            Vector curattrs = nent.getAttributes();
            Attribute curattr = null;

            for(int i = 0; i < curattrs.size(); ++i) {
               curattr = (Attribute)curattrs.elementAt(i);
               if ((returnattrs.isEmpty() || returnattrs.contains(curattr.type) || SchemaChecker.getInstance().getAttributeType(curattr.type) != null && returnattrs.contains(new DirectoryString(SchemaChecker.getInstance().getAttributeType(curattr.type).getOid()))) && ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_READ, nent, curattr.type) && (removelist.isEmpty() || !removelist.contains(curattr.type))) {
                  attributes.addElement(curattr);
               }
            }

            rent.setAttributes(attributes);
            return rent;
         }
      } else {
         return null;
      }
   }

   public static void setReplicaInvalid() {
      ServerConfig config = ServerConfig.getInstance();
      if (config != null && config.get("vde.changelog") != null && !config.get("vde.changelog").equals("1")) {
         String invalidFileName = System.getProperty("vde.home") + File.separator + "ldapfiles" + File.separator + "replica.invalid";
         File invalidFile = new File(invalidFileName);

         try {
            invalidFile.createNewFile();
         } catch (Exception var4) {
         }

         Logger.getInstance().log(3, handler, Messages.getString("Error_replica_invalid"));
      }
   }

   private boolean canReadSearch(Credentials creds, DirectoryString name, DirectoryString attr, boolean needRead) {
      return ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_SEARCH, name, attr) && (!needRead || ACLChecker.getInstance().isAllowed(creds, ACLChecker.PERM_READ, name, attr));
   }

   private boolean evalFilter(Credentials creds, Entry entry, Filter filter, boolean first) {
      DirectoryString type = null;
      Syntax val = null;
      Vector attrVals = null;

      try {
         Enumeration ve;
         Syntax any;
         Iterator orEnum;
         switch (filter.getSelector()) {
            case 0:
               orEnum = filter.getAnd().iterator();

               do {
                  if (!orEnum.hasNext()) {
                     return true;
                  }
               } while(this.evalFilter(creds, entry, (Filter)orEnum.next(), false));

               return false;
            case 1:
               orEnum = filter.getOr().iterator();

               do {
                  if (!orEnum.hasNext()) {
                     return false;
                  }
               } while(!this.evalFilter(creds, entry, (Filter)orEnum.next(), false));

               return true;
            case 2:
               if (this.evalFilter(creds, entry, filter.getNot(), false)) {
                  return false;
               }

               return true;
            case 3:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getEqualityMatch().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  if (this.canReadSearch(creds, entry.getName(), type, true)) {
                     val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                     val.setValue(filter.getEqualityMatch().getAssertionValue().toByteArray());
                     if (attrVals.contains(val)) {
                        return true;
                     }

                     return false;
                  }

                  return false;
               }

               return false;
            case 5:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getGreaterOrEqual().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals == null || attrVals.isEmpty()) {
                  return false;
               }

               if (!this.canReadSearch(creds, entry.getName(), type, true)) {
                  return false;
               }

               val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
               val.setValue(filter.getGreaterOrEqual().getAssertionValue().toByteArray());
               ve = attrVals.elements();
               if (ve.hasMoreElements()) {
                  any = (Syntax)ve.nextElement();
                  if (val.compareTo(any) >= 0) {
                     return true;
                  }

                  return false;
               }
            case 6:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getLessOrEqual().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals == null || attrVals.isEmpty()) {
                  return false;
               }

               if (!this.canReadSearch(creds, entry.getName(), type, true)) {
                  return false;
               }

               val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
               val.setValue(filter.getLessOrEqual().getAssertionValue().toByteArray());
               ve = attrVals.elements();
               if (ve.hasMoreElements()) {
                  any = (Syntax)ve.nextElement();
                  if (val.compareTo(any) <= 0) {
                     return true;
                  }

                  return false;
               }
            case 4:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getSubstrings().getType().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  if (this.canReadSearch(creds, entry.getName(), type, true)) {
                     Syntax init = null;
                     any = null;
                     Syntax fin = null;
                     Class aclass = ((Syntax)attrVals.firstElement()).getClass();
                     Iterator substrEnum = filter.getSubstrings().getSubstrings().iterator();

                     while(substrEnum.hasNext()) {
                        SubstringFilter_substrings_Seq oneSubFilter = (SubstringFilter_substrings_Seq)substrEnum.next();
                        if (oneSubFilter.getSelector() == 0) {
                           init = (Syntax)aclass.newInstance();
                           init.setValue(oneSubFilter.getInitial().toByteArray());
                        } else if (oneSubFilter.getSelector() == 1) {
                           any = (Syntax)aclass.newInstance();
                           any.setValue(oneSubFilter.getAny().toByteArray());
                        } else if (oneSubFilter.getSelector() == 2) {
                           fin = (Syntax)aclass.newInstance();
                           fin.setValue(oneSubFilter.getFinal_().toByteArray());
                        }
                     }

                     StringBuffer regexbuf = new StringBuffer();
                     regexbuf.append("/^");
                     if (init != null) {
                        regexbuf.append(init.normalize());
                        regexbuf.append(".*");
                     }

                     if (any != null) {
                        if (regexbuf.length() < 3) {
                           regexbuf.append(".*");
                        }

                        regexbuf.append(any.normalize());
                        regexbuf.append(".*");
                     }

                     if (fin != null) {
                        if (regexbuf.length() < 3) {
                           regexbuf.append(".*");
                        }

                        regexbuf.append(fin.normalize());
                     }

                     regexbuf.append("$/");
                     String regex = regexbuf.toString();
                     Enumeration ve = attrVals.elements();
                     Perl5Util p5u = new Perl5Util();

                     Syntax aval;
                     do {
                        if (!ve.hasMoreElements()) {
                           return false;
                        }

                        aval = (Syntax)ve.nextElement();
                     } while(!p5u.match(regex, aval.normalize()));

                     return true;
                  }

                  return false;
               }

               return false;
            case 7:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getPresent().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  if (this.canReadSearch(creds, entry.getName(), type, false)) {
                     return true;
                  }

                  return false;
               }

               return false;
            case 8:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getApproxMatch().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  if (this.canReadSearch(creds, entry.getName(), type, true)) {
                     val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                     val.setValue(filter.getApproxMatch().getAssertionValue().toByteArray());
                     if (attrVals.contains(val)) {
                        return true;
                     }

                     return false;
                  }

                  return false;
               }

               return false;
            case 9:
               if (first) {
                  return false;
               }

               type = new DirectoryString(filter.getExtensibleMatch().getType().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  if (this.canReadSearch(creds, entry.getName(), type, true)) {
                     val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                     val.setValue(filter.getExtensibleMatch().getMatchValue().toByteArray());
                     if (attrVals.contains(val)) {
                        return true;
                     }

                     return false;
                  }

                  return false;
               }

               return false;
         }
      } catch (InstantiationException var17) {
      } catch (IllegalAccessException var18) {
      }

      return false;
   }

   private boolean scanFilter(Credentials creds, Entry entry, Filter filter) {
      boolean needRead = false;
      DirectoryString type = null;
      Iterator orEnum;
      switch (filter.getSelector()) {
         case 0:
            orEnum = filter.getAnd().iterator();

            do {
               if (!orEnum.hasNext()) {
                  return true;
               }
            } while(this.scanFilter(creds, entry, (Filter)orEnum.next()));

            return false;
         case 1:
            orEnum = filter.getOr().iterator();

            do {
               if (!orEnum.hasNext()) {
                  return true;
               }
            } while(this.scanFilter(creds, entry, (Filter)orEnum.next()));

            return false;
         case 2:
            if (this.scanFilter(creds, entry, filter.getNot())) {
               return false;
            }

            return true;
         case 3:
            type = new DirectoryString(filter.getEqualityMatch().getAttributeDesc().toByteArray());
            needRead = true;
            break;
         case 4:
            type = new DirectoryString(filter.getSubstrings().getType().toByteArray());
            needRead = true;
            break;
         case 5:
            type = new DirectoryString(filter.getGreaterOrEqual().getAttributeDesc().toByteArray());
            needRead = true;
            break;
         case 6:
            type = new DirectoryString(filter.getLessOrEqual().getAttributeDesc().toByteArray());
            needRead = true;
            break;
         case 7:
            type = new DirectoryString(filter.getPresent().toByteArray());
            break;
         case 8:
            type = new DirectoryString(filter.getApproxMatch().getAttributeDesc().toByteArray());
            needRead = true;
            break;
         case 9:
            type = new DirectoryString(filter.getExtensibleMatch().getType().toByteArray());
            needRead = true;
      }

      return this.canReadSearch(creds, entry.getName(), type, needRead);
   }
}
