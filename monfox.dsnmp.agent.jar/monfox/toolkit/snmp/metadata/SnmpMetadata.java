package monfox.toolkit.snmp.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataLoader;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataRepository;
import monfox.toolkit.snmp.metadata.xml.XMLException;
import monfox.toolkit.snmp.util.FileUtil;
import monfox.toolkit.snmp.util.StringUtil;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpMetadata implements Serializable {
   static final long serialVersionUID = 8029799011179703022L;
   private SnmpModule[] _modules;
   private SnmpMetadata[] _attached;
   private String _name;
   private Vector _snmpOids;
   private Vector _snmpTables;
   private Vector _snmpObjects;
   private Vector _snmpNotifications;
   private Vector _snmpObjectGroups;
   private Vector _snmpNotificationGroups;
   private Vector _snmpModuleIdentities;
   private Vector _snmpTypes;
   private Vector _snmpAgentCapabilities;
   private Hashtable _snmpOidMap;
   private Hashtable _snmpTableMap;
   private Hashtable _snmpObjectMap;
   private Hashtable _snmpNotificationMap;
   private Hashtable _snmpObjectGroupMap;
   private Hashtable _snmpNotificationGroupMap;
   private Hashtable _snmpModuleMap;
   private Hashtable _snmpModuleIdentityMap;
   private Hashtable _snmpTypeMap;
   private Hashtable _snmpAgentCapabilitiesMap;
   private transient Logger a;
   private static Repository _repository = null;
   private SnmpOidTree _oidTree;
   private static final String _ident = "$Id: SnmpMetadata.java,v 1.36 2014/04/07 18:38:55 sking Exp $";
   // $FF: synthetic field
   static Class class$monfox$toolkit$snmp$metadata$SnmpMetadata;

   public SnmpMetadata() {
      boolean var1 = SnmpOidInfo.a;
      super();
      this._modules = new SnmpModule[0];
      this._attached = new SnmpMetadata[0];
      this._name = null;
      this._snmpOids = new Vector();
      this._snmpTables = new Vector();
      this._snmpObjects = new Vector();
      this._snmpNotifications = new Vector();
      this._snmpObjectGroups = new Vector();
      this._snmpNotificationGroups = new Vector();
      this._snmpModuleIdentities = new Vector();
      this._snmpTypes = new Vector();
      this._snmpAgentCapabilities = new Vector();
      this._snmpOidMap = new Hashtable(89);
      this._snmpTableMap = new Hashtable(89);
      this._snmpObjectMap = new Hashtable(89);
      this._snmpNotificationMap = new Hashtable(89);
      this._snmpObjectGroupMap = new Hashtable(89);
      this._snmpNotificationGroupMap = new Hashtable(89);
      this._snmpModuleMap = new Hashtable(89);
      this._snmpModuleIdentityMap = new Hashtable(89);
      this._snmpTypeMap = new Hashtable(89);
      this._snmpAgentCapabilitiesMap = new Hashtable(89);
      this.a = null;
      this._oidTree = null;
      this.a = Logger.getInstance(b(" xeL\u0001\u0016biX-\u0007w"));
      this._oidTree = new SnmpOidTree(30, 1);
      if (var1) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public SnmpMetadata(String var1) {
      this();
      this.setName(var1);
   }

   public static void setRepository(Repository var0) {
      _repository = var0;
   }

   public static Repository getRepository() {
      return _repository;
   }

   public Result loadModules(Repository var1, String var2) throws IOException, SnmpMetadataException {
      if (var1 == null) {
         throw new SnmpMetadataException(b("=y(o\"\u001efEY8\u0012riH-SFzS:\u001armN"));
      } else {
         String[] var3 = StringUtil.split(var2, b("I6$\u0007"));
         return var1.loadModules(var3, this);
      }
   }

   public Result loadModules(Repository var1, String[] var2) throws IOException, SnmpMetadataException {
      if (var1 == null) {
         throw new SnmpMetadataException(b("=y(o\"\u001efEY8\u0012riH-SFzS:\u001armN"));
      } else {
         return var1.loadModules(var2, this);
      }
   }

   public Result loadModule(Repository var1, String var2) throws IOException, SnmpMetadataException {
      if (this.containsModule(var2)) {
         return new Result();
      } else if (var1 == null) {
         throw new SnmpMetadataException(b("=y(o\"\u001efEY8\u0012riH-SFzS:\u001armN"));
      } else {
         return var1.loadModule(var2, this);
      }
   }

   public Result loadFile(Repository var1, String var2) throws IOException, SnmpMetadataException {
      if (var1 == null) {
         throw new SnmpMetadataException(b("=y(o\"\u001efEY8\u0012riH-SFzS:\u001armN"));
      } else {
         return var1.loadFile(var2, this);
      }
   }

   public Result loadModules(String[] var1) throws IOException, SnmpMetadataException {
      return this.loadModules(_repository, var1);
   }

   public Result loadModule(String var1) throws IOException, SnmpMetadataException {
      return this.loadModule(_repository, var1);
   }

   public Result loadModules(String var1) throws IOException, SnmpMetadataException {
      return this.loadModules(_repository, var1);
   }

   public Result loadFile(String var1) throws IOException, SnmpMetadataException {
      return this.loadFile(_repository, var1);
   }

   public Result loadAllMibs(String var1) {
      boolean var8 = SnmpOidInfo.a;
      SnmpMetadataRepository var2 = new SnmpMetadataRepository();
      var2.setSearchPath(var1);
      var2.refresh();
      Result var3 = new Result();
      Set var4 = var2.getModuleNames();
      Iterator var5 = var4.iterator();

      Result var10000;
      while(true) {
         if (var5.hasNext()) {
            String var6 = (String)var5.next();

            try {
               Result var7 = this.loadModule(var2, var6);
               var10000 = var3;
               if (var8) {
                  break;
               }

               var3.addResult(var7);
            } catch (Exception var9) {
               this.a.error(b("\u0016dzS>S\u007ff\u001c \u001cwlU\"\u00146Eu\u000eS{gX9\u001fs2\u001c") + var6, var9);
            }

            if (!var8) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public SnmpOidTree getOidTree() {
      return this._oidTree;
   }

   public SnmpOidInfo resolveBaseOid(String var1) throws SnmpValueException {
      boolean var6 = SnmpOidInfo.a;
      SnmpOidInfo var2 = null;

      try {
         var2 = this._oidTree.resolveBaseOid(var1);
         if (var2 instanceof SnmpMibInfo) {
            return var2;
         }
      } catch (SnmpValueException var7) {
      }

      SnmpMetadata[] var3 = this._attached;
      int var4 = 0;

      SnmpOidInfo var10000;
      while(true) {
         if (var4 < var3.length) {
            try {
               var2 = var3[var4].resolveBaseOid(var1);
               var10000 = var2;
               if (var6) {
                  break;
               }

               if (var2 instanceof SnmpMibInfo) {
                  return var2;
               }
            } catch (SnmpValueException var8) {
            }

            ++var4;
            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 != null) {
         return var2;
      } else {
         throw new SnmpValueException(var1);
      }
   }

   public SnmpOidInfo resolveBaseOid(SnmpOid var1) throws SnmpValueException {
      boolean var6 = SnmpOidInfo.a;
      SnmpOidInfo var2 = null;

      try {
         var2 = this._oidTree.resolveBaseOid(var1);
         if (var2 instanceof SnmpMibInfo) {
            return var2;
         }
      } catch (SnmpValueException var7) {
      }

      SnmpMetadata[] var3 = this._attached;
      int var4 = 0;

      SnmpOidInfo var10000;
      while(true) {
         if (var4 < var3.length) {
            try {
               var2 = var3[var4].resolveBaseOid(var1);
               var10000 = var2;
               if (var6) {
                  break;
               }

               if (var2 instanceof SnmpMibInfo) {
                  return var2;
               }
            } catch (SnmpValueException var8) {
            }

            ++var4;
            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 != null) {
         return var2;
      } else {
         throw new SnmpValueException(var1.toNumericString());
      }
   }

   public SnmpOidInfo resolveOid(String var1) throws SnmpValueException {
      boolean var6 = SnmpOidInfo.a;
      SnmpOidInfo var2 = null;

      try {
         var2 = this._oidTree.resolveOid(var1);
         if (var2 instanceof SnmpMibInfo) {
            return var2;
         }
      } catch (SnmpValueException var7) {
      }

      SnmpMetadata[] var3 = this._attached;
      int var4 = 0;

      SnmpOidInfo var10000;
      while(true) {
         if (var4 < var3.length) {
            try {
               var2 = var3[var4].resolveOid(var1);
               var10000 = var2;
               if (var6) {
                  break;
               }

               if (var2 instanceof SnmpMibInfo) {
                  return var2;
               }
            } catch (SnmpValueException var8) {
            }

            ++var4;
            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 != null) {
         return var2;
      } else {
         throw new SnmpValueException(var1);
      }
   }

   public SnmpOidInfo resolveOid(SnmpOid var1) throws SnmpValueException {
      boolean var6 = SnmpOidInfo.a;
      SnmpOidInfo var2 = null;

      try {
         var2 = this._oidTree.resolveOid(var1);
         if (var2 instanceof SnmpMibInfo) {
            return var2;
         }
      } catch (SnmpValueException var7) {
      }

      SnmpMetadata[] var3 = this._attached;
      int var4 = 0;

      SnmpOidInfo var10000;
      while(true) {
         if (var4 < var3.length) {
            try {
               var2 = var3[var4].resolveOid(var1);
               var10000 = var2;
               if (var6) {
                  break;
               }

               if (var2 instanceof SnmpMibInfo) {
                  return var2;
               }
            } catch (SnmpValueException var8) {
            }

            ++var4;
            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 != null) {
         return var2;
      } else {
         throw new SnmpValueException(var1.toNumericString());
      }
   }

   public SnmpOidInfo resolveName(String var1) throws SnmpValueException {
      boolean var6 = SnmpOidInfo.a;
      SnmpOidInfo var2 = null;

      try {
         var2 = this._oidTree.resolveName(var1);
         if (var2 instanceof SnmpMibInfo) {
            return var2;
         }
      } catch (SnmpValueException var7) {
      }

      SnmpMetadata[] var3 = this._attached;
      int var4 = 0;

      SnmpOidInfo var10000;
      while(true) {
         if (var4 < var3.length) {
            try {
               var2 = var3[var4].resolveName(var1);
               var10000 = var2;
               if (var6) {
                  break;
               }

               if (var2 instanceof SnmpMibInfo) {
                  return var2;
               }
            } catch (SnmpValueException var8) {
            }

            ++var4;
            if (!var6) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      if (var10000 != null) {
         return var2;
      } else {
         throw new SnmpValueException(b("=y(o9\u0010~(s%\u0017,(") + var1);
      }
   }

   public Enumeration getObjects() {
      return this._snmpObjects.elements();
   }

   public Enumeration getTables() {
      return this._snmpTables.elements();
   }

   public Enumeration getModuleIdentities() {
      return this._snmpModuleIdentities.elements();
   }

   public Enumeration getNotifications() {
      return this._snmpNotifications.elements();
   }

   public Enumeration getTypes() {
      return this._snmpTypes.elements();
   }

   public Enumeration getOids() {
      return this._snmpOids.elements();
   }

   public Enumeration getObjectGroups() {
      return this._snmpObjectGroups.elements();
   }

   public Enumeration getNotificationGroups() {
      return this._snmpNotificationGroups.elements();
   }

   public SnmpOidInfo getOid(String var1) throws SnmpValueException {
      SnmpOidInfo var2 = (SnmpOidInfo)this._snmpOidMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bYaXvS1") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpObjectInfo getObject(String var1) throws SnmpValueException {
      SnmpObjectInfo var2 = (SnmpObjectInfo)this._snmpObjectMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bYjV)\u0010b2\u001ck") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpModuleIdentityInfo getModuleIdentity(String var1) throws SnmpValueException {
      SnmpModuleIdentityInfo var2 = (SnmpModuleIdentityInfo)this._snmpModuleIdentityMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001b[gX9\u001fsAX)\u001dbaH5I6/") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpAgentCapabilitiesInfo getAgentCapabilities(String var1) throws SnmpValueException {
      SnmpAgentCapabilitiesInfo var2 = (SnmpAgentCapabilitiesInfo)this._snmpAgentCapabilitiesMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bWoY\"\u0007UiL-\u0011\u007fdU8\u001as{\u0006lT") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpTableInfo getTable(String var1) throws SnmpValueException {
      SnmpTableInfo var2 = (SnmpTableInfo)this._snmpTableMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bBi^ \u0016,(\u001b") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpNotificationInfo getNotification(String var1) throws SnmpValueException {
      SnmpNotificationInfo var2 = (SnmpNotificationInfo)this._snmpNotificationMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bXgH%\u0015\u007fk]8\u001ayf\u0006lT") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpObjectGroupInfo getObjectGroup(String var1) throws SnmpValueException {
      SnmpObjectGroupInfo var2 = (SnmpObjectGroupInfo)this._snmpObjectGroupMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bYjV)\u0010bON#\u0006f2\u001ck") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpNotificationGroupInfo getNotificationGroup(String var1) throws SnmpValueException {
      SnmpNotificationGroupInfo var2 = (SnmpNotificationGroupInfo)this._snmpNotificationGroupMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bXgH%\u0015\u007fk]8\u001ayf{>\u001ccx\u0006lT") + var1 + "'");
      } else {
         return var2;
      }
   }

   public SnmpTypeInfo getType(String var1) throws SnmpValueException {
      SnmpTypeInfo var2 = (SnmpTypeInfo)this._snmpTypeMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(b("\u001dy[I/\u001bBqL)I6/") + var1 + "'");
      } else {
         return var2;
      }
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpOidInfo var3) {
      this.add((String)null, var1, var2, (SnmpOidInfo)var3);
   }

   /** @deprecated */
   public SnmpObjectInfo addObject(String var1, String var2, int var3, int var4) {
      SnmpObjectInfo var5 = new SnmpObjectInfo(var3, var4);
      this.add((String)null, var1, var2, (SnmpObjectInfo)var5);
      return var5;
   }

   /** @deprecated */
   public SnmpObjectInfo addObject(String var1, String var2, String var3, String var4) throws SnmpValueException {
      SnmpObjectInfo var5 = new SnmpObjectInfo(var3, var4);
      this.add((String)null, var1, var2, (SnmpObjectInfo)var5);
      return var5;
   }

   /** @deprecated */
   public SnmpOidInfo addOid(String var1, String var2) {
      SnmpOidInfo var3 = new SnmpOidInfo();
      this.add((String)null, var1, var2, (SnmpOidInfo)var3);
      return var3;
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpObjectInfo var3) {
      this.add((String)null, var1, var2, (SnmpObjectInfo)var3);
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpTableInfo var3) {
      this.add((String)null, var1, var2, (SnmpTableInfo)var3);
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpNotificationInfo var3) {
      this.add((String)null, var1, var2, (SnmpNotificationInfo)var3);
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpNotificationGroupInfo var3) {
      this.add((String)null, var1, var2, (SnmpNotificationGroupInfo)var3);
   }

   /** @deprecated */
   public void add(String var1, String var2, SnmpObjectGroupInfo var3) {
      this.add((String)null, var1, var2, (SnmpObjectGroupInfo)var3);
   }

   public void add(String var1, String var2, String var3, SnmpOidInfo var4) {
      this._oidTree.add(var1, var2, var3, var4);
      this.a(var1, var3, var4, this._snmpOidMap, this._snmpOids);
   }

   public SnmpObjectInfo addObject(String var1, String var2, String var3, int var4, int var5) {
      SnmpObjectInfo var6 = new SnmpObjectInfo(var4, var5);
      this.add(var1, var2, var3, var6);
      return var6;
   }

   public SnmpObjectInfo addObject(String var1, String var2, String var3, String var4, String var5) throws SnmpValueException {
      SnmpObjectInfo var6 = new SnmpObjectInfo(var4, var5);
      this.add(var1, var2, var3, var6);
      return var6;
   }

   public SnmpOidInfo addOid(String var1, String var2, String var3) {
      SnmpOidInfo var4 = new SnmpOidInfo();
      this.add(var1, var2, var3, var4);
      return var4;
   }

   public void add(String var1, String var2, String var3, SnmpAgentCapabilitiesInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpAgentCapabilitiesMap, this._snmpAgentCapabilities);
   }

   public void add(String var1, String var2, String var3, SnmpObjectInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpObjectMap, this._snmpObjects);
   }

   public void add(String var1, String var2, String var3, SnmpTableInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpTableMap, this._snmpTables);
   }

   public void add(String var1, String var2, String var3, SnmpNotificationInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpNotificationMap, this._snmpNotifications);
   }

   public void add(String var1, String var2, String var3, SnmpNotificationGroupInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpNotificationGroupMap, this._snmpNotificationGroups);
   }

   public void add(String var1, String var2, String var3, SnmpObjectGroupInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpObjectGroupMap, this._snmpObjectGroups);
   }

   public void add(String var1, String var2, String var3, SnmpModuleIdentityInfo var4) {
      this._oidTree.add(var1, (String)var2, var3, var4);
      this.a(var1, var3, (SnmpOidInfo)var4, this._snmpModuleIdentityMap, this._snmpModuleIdentities);
   }

   public void add(String var1, String var2, SnmpTypeInfo var3) {
      this.a(var1, var2, var3, this._snmpTypeMap, this._snmpTypes);
   }

   public SnmpTableInfo addTable(String var1, String var2, String var3, String[][] var4, String[] var5) throws SnmpValueException {
      boolean var11 = SnmpOidInfo.a;
      String var6 = var2 + b("]'");
      int var7 = 0;

      int var9;
      int var10000;
      while(true) {
         if (var7 < var4.length) {
            var10000 = var4[var7].length;
            if (var11) {
               break;
            }

            if (var10000 != 3) {
               StringBuffer var8 = new StringBuffer();
               var8.append(b(":x~] \u001ar(H-\u0011zm\u001c/\u001cz}Q\"S\u007ffZ#I6"));
               var9 = 0;

               while(true) {
                  if (var9 < var4[var7].length) {
                     var8.append("\"").append(var4[var7][var9]).append(b("Q6"));
                     ++var9;
                     if (var11) {
                        break;
                     }

                     if (!var11) {
                        continue;
                     }
                  }

                  var8.append(b("]6KS \u0006{fOl\u0017snU\"\u001abaS\"\u00006{T#\u0006zl\u001c$\u0012`m\u001c*\u001cde]8I6s\u001c7S44_#\u001fceRa\u001dweYrQ:(\u001ep\u0007oxYrQ:(\u001ep\u0012ukY?\u0000(*A`S8&\u0012l\u000e"));
                  break;
               }

               throw new SnmpValueException(var8.toString());
            }

            ++var7;
            if (!var11) {
               continue;
            }
         }

         var10000 = var4.length;
         break;
      }

      SnmpObjectInfo[] var12 = new SnmpObjectInfo[var10000];
      int var13 = 0;

      SnmpObjectInfo[] var16;
      while(true) {
         if (var13 < var4.length) {
            var16 = var12;
            if (var11) {
               break;
            }

            var12[var13] = this.addObject(var1, var6 + "." + (var13 + 1), var4[var13][0], var4[var13][1], var4[var13][2]);
            ++var13;
            if (!var11) {
               continue;
            }
         }

         var16 = new SnmpObjectInfo[var5.length];
         break;
      }

      SnmpObjectInfo[] var14 = var16;
      var9 = 0;

      while(var9 < var5.length) {
         var14[var9] = this.getObject(var5[var9]);
         ++var9;
         if (var11) {
            break;
         }
      }

      SnmpTableEntryInfo var15 = new SnmpTableEntryInfo(var12, var14);
      this.add(var1, var6, var3 + b("6x|N5"), (SnmpOidInfo)var15);
      SnmpTableInfo var10 = new SnmpTableInfo(var12, var14, var15);
      this.add(var1, var2, var3, var10);
      return var10;
   }

   public static SnmpMetadata loadDefault() throws IOException, SnmpException {
      return load((String)null);
   }

   public static SnmpMetadata load(String var0) throws IOException, SnmpException {
      InputStream var1 = null;
      if (var0 == null) {
         var1 = (class$monfox$toolkit$snmp$metadata$SnmpMetadata == null ? (class$monfox$toolkit$snmp$metadata$SnmpMetadata = a(b("\u001eyfZ#\u000b8|S#\u001f}aHb\u0000xeLb\u001es|](\u0012bi\u0012\u001f\u001d{xq)\u0007wl]8\u0012"))) : class$monfox$toolkit$snmp$metadata$SnmpMetadata).getResourceAsStream(b("\u0017w|]c\u001e\u007fj\u000eb\u0000sz"));
         var0 = b("\u0017w|]c\u001e\u007fj\u000eb\u0000sz");
      }

      if (var1 == null) {
         var1 = FileUtil.getInputStream(var0);
      }

      SnmpMetadata var2 = null;
      if (var0.toLowerCase().endsWith(b("]neP"))) {
         var2 = loadXML(var1, var0);
      } else {
         var2 = loadBean(var1, var0);
      }

      var1.close();
      return var2;
   }

   public static SnmpMetadata load(URL var0) throws IOException, SnmpException {
      InputStream var1 = null;
      SnmpMetadata var2;
      if (var0 == null) {
         var1 = (class$monfox$toolkit$snmp$metadata$SnmpMetadata == null ? (class$monfox$toolkit$snmp$metadata$SnmpMetadata = a(b("\u001eyfZ#\u000b8|S#\u001f}aHb\u0000xeLb\u001es|](\u0012bi\u0012\u001f\u001d{xq)\u0007wl]8\u0012"))) : class$monfox$toolkit$snmp$metadata$SnmpMetadata).getResourceAsStream(b("\u0017w|]c\u001e\u007fj\u000eb\u0000sz"));
         var2 = loadBean(var1, b("\u0017w|]c\u001e\u007fj\u000eb\u0000sz"));
         var1.close();
         return var2;
      } else {
         var1 = var0.openStream();
         var2 = null;
         String var3 = var0.getFile().toLowerCase();
         if (var3.endsWith(b("]neP"))) {
            var2 = loadXML(var1, var0.toString());
         } else {
            var2 = loadBean(var1, var0.toString());
         }

         var1.close();
         return var2;
      }
   }

   public static SnmpMetadata loadBean(InputStream var0, String var1) throws IOException, SnmpException {
      Logger var2 = Logger.getInstance(b(" xeL\u0001\u0016biX-\u0007w"));

      try {
         ObjectInputStream var3 = new ObjectInputStream(var0);
         SnmpMetadata var4 = (SnmpMetadata)var3.readObject();
         return var4;
      } catch (ClassNotFoundException var5) {
         var2.error(b(":x~] \u001ar(q)\u0007wl]8\u00126JY-\u001d,(") + var5);
         throw new SnmpException(b(":x~] \u001ar(q)\u0007wl]8\u00126JY-\u001d,(") + var1);
      }
   }

   public static SnmpMetadata loadXML(InputStream var0, String var1) throws IOException, SnmpException {
      SnmpMetadata var2 = new SnmpMetadata();
      a(var2, var0, var1);
      return var2;
   }

   public static Result loadXML(SnmpMetadata var0, String var1) throws IOException, SnmpException {
      InputStream var2 = null;
      if (var1 == null) {
         var2 = (class$monfox$toolkit$snmp$metadata$SnmpMetadata == null ? (class$monfox$toolkit$snmp$metadata$SnmpMetadata = a(b("\u001eyfZ#\u000b8|S#\u001f}aHb\u0000xeLb\u001es|](\u0012bi\u0012\u001f\u001d{xq)\u0007wl]8\u0012"))) : class$monfox$toolkit$snmp$metadata$SnmpMetadata).getResourceAsStream(b("\u0017w|]c\u001e\u007fj\u000eb\u000b{d"));
         var1 = b("\u0017w|]c\u001e\u007fj\u000eb\u000b{d");
      }

      if (var2 == null) {
         var2 = FileUtil.getInputStream(var1);
      }

      Result var3 = a(var0, var2, var1);
      var2.close();
      return var3;
   }

   public static Result loadXML(SnmpMetadata var0, URL var1) throws IOException, SnmpException {
      InputStream var2 = null;
      if (var1 == null) {
         var2 = (class$monfox$toolkit$snmp$metadata$SnmpMetadata == null ? (class$monfox$toolkit$snmp$metadata$SnmpMetadata = a(b("\u001eyfZ#\u000b8|S#\u001f}aHb\u0000xeLb\u001es|](\u0012bi\u0012\u001f\u001d{xq)\u0007wl]8\u0012"))) : class$monfox$toolkit$snmp$metadata$SnmpMetadata).getResourceAsStream(b("\u0017w|]c\u001e\u007fj\u000eb\u000b{d"));
      } else {
         var2 = var1.openStream();
      }

      Result var3 = a(var0, var2, var1.toString());
      var2.close();
      return var3;
   }

   private static Result a(SnmpMetadata var0, InputStream var1, String var2) throws IOException, SnmpException {
      Logger var3 = Logger.getInstance(b(" xeL\u0001\u0016biX-\u0007w"));

      try {
         if (var3.isDebugEnabled()) {
            var3.debug(b("?yiX%\u001dq(o\"\u001efEY8\u0012riH-I6") + var2);
         }

         SnmpMetadataLoader var4 = new SnmpMetadataLoader();
         Result var5 = var4.load(var1, var0, false);
         if (var5.getErrorCount() > 0) {
            var3.error(var5.toString());
            if (!SnmpOidInfo.a) {
               return var5;
            }
         }

         if (var3.isDebugEnabled()) {
            var3.debug(var5.toString());
         }

         return var5;
      } catch (IOException var6) {
         var3.error(b(" xeL\u0001\u0016biX-\u0007w(p#\u0012r(d\u0001?6Asl6nkY<\u0007\u007fgR"), var6);
         throw new IOException(var6.getMessage() + b("[EgI>\u0010s2\u001c") + var2 + ")");
      } catch (XMLException var7) {
         var3.error(b(" xeL\u0001\u0016biX-\u0007w(d\u0001?6MD/\u0016f|U#\u001d"), var7);
         throw new SnmpException(var7.getMessage());
      }
   }

   public void setName(String var1) {
      this._name = var1;
   }

   public String getName() {
      return this._name;
   }

   public SnmpModule[] getModules() {
      return this._modules;
   }

   public boolean containsModule(String var1) {
      return this._snmpModuleMap.get(var1) != null;
   }

   public SnmpModule getModule(String var1) throws SnmpValueException {
      SnmpModule var2 = (SnmpModule)this._snmpModuleMap.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(var1);
      } else {
         return var2;
      }
   }

   private void a(String var1, String var2, SnmpOidInfo var3, Hashtable var4, Vector var5) {
      SnmpModule var6;
      label17: {
         var3.setMetadata(this);
         var4.put(var2, var3);
         var5.addElement(var3);
         var6 = null;
         if (var1 != null) {
            var4.put(var1 + ":" + var2, var3);
            if (!SnmpOidInfo.a) {
               break label17;
            }
         }

         var1 = this.getName();
         if (var1 == null) {
            var1 = b("7SN}\u0019?B");
         }
      }

      var6 = (SnmpModule)this._snmpModuleMap.get(var1);
      if (var6 == null) {
         var6 = new SnmpModule(var1);
         this._snmpModuleMap.put(var1, var6);
         int var7 = this._modules.length;
         SnmpModule[] var8 = new SnmpModule[var7 + 1];
         System.arraycopy(this._modules, 0, var8, 0, var7);
         var8[var7] = var6;
         this._modules = var8;
      }

      var6.b(var2, var3);
   }

   private void a(String var1, String var2, SnmpTypeInfo var3, Hashtable var4, Vector var5) {
      SnmpModule var6;
      label17: {
         var4.put(var2, var3);
         var5.addElement(var3);
         var6 = null;
         if (var1 != null) {
            var4.put(var1 + ":" + var2, var3);
            if (!SnmpOidInfo.a) {
               break label17;
            }
         }

         var1 = this.getName();
         if (var1 == null) {
            var1 = b("7SN}\u0019?B");
         }
      }

      var6 = (SnmpModule)this._snmpModuleMap.get(var1);
      if (var6 == null) {
         var6 = new SnmpModule(var1);
         this._snmpModuleMap.put(var1, var6);
         int var7 = this._modules.length;
         SnmpModule[] var8 = new SnmpModule[var7 + 1];
         System.arraycopy(this._modules, 0, var8, 0, var7);
         var8[var7] = var6;
         this._modules = var8;
      }

      var6.a(var2, var3);
   }

   public String toString() {
      TextBuffer var1 = new TextBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(TextBuffer var1) {
      int var2 = 0;

      while(var2 < this._modules.length) {
         var1.pushIndent();
         this._modules[var2].toString(var1);
         var1.popIndent();
         ++var2;
         if (SnmpOidInfo.a) {
            break;
         }
      }

   }

   public void attachMetadata(SnmpMetadata var1) {
      if (var1 != null) {
         synchronized(this._attached) {
            int var3 = this._attached.length;
            SnmpMetadata[] var4 = new SnmpMetadata[var3 + 1];
            System.arraycopy(this._attached, 0, var4, 0, var3);
            var4[var3] = var1;
            this._attached = var4;
         }
      }
   }

   public void detachMetadata(SnmpMetadata var1) {
      boolean var9 = SnmpOidInfo.a;
      synchronized(this._attached) {
         int var3 = -1;
         int var4 = 0;

         SnmpMetadata var10000;
         while(true) {
            if (var4 < this._attached.length) {
               label59: {
                  var10000 = this._attached[var4];
                  if (var9) {
                     break;
                  }

                  if (var10000 == var1) {
                     var3 = var4;
                     if (!var9) {
                        break label59;
                     }
                  }

                  ++var4;
                  if (!var9) {
                     continue;
                  }
               }
            }

            if (var3 == -1) {
               return;
            }

            var10000 = this;
            break;
         }

         var4 = var10000._attached.length;
         SnmpMetadata[] var5 = new SnmpMetadata[var4 - 1];
         int var6 = 0;
         int var7 = 0;

         while(true) {
            if (var7 < this._attached.length) {
               if (var9) {
                  break;
               }

               if (var7 != var3) {
                  var5[var6++] = this._attached[var7];
               }

               ++var7;
               if (!var9) {
                  continue;
               }
            }

            this._attached = var5;
            break;
         }

      }
   }

   public void useCapabilities(String var1) throws SnmpValueException {
      boolean var12 = SnmpOidInfo.a;
      StringTokenizer var2 = new StringTokenizer(var1, b("S:"), false);

      label62:
      do {
         boolean var10000 = var2.hasMoreTokens();

         while(var10000) {
            String var3 = var2.nextToken();
            SnmpAgentCapabilitiesInfo var4 = this.getAgentCapabilities(var3);
            SnmpSupportedModule[] var5 = var4.getSupports();
            int var6 = 0;

            while(true) {
               if (var6 >= var5.length) {
                  continue label62;
               }

               SnmpSupportedModule var7 = var5[var6];
               SnmpObjectVariation[] var8 = var7.getObjectVariations();
               var10000 = false;
               if (var12) {
                  break;
               }

               int var9 = 0;

               label54: {
                  while(var9 < var8.length) {
                     SnmpObjectVariation var10 = var8[var9];
                     SnmpObjectInfo var11 = var10.getObjectInfo();
                     if (var12) {
                        break label54;
                     }

                     label50: {
                        if (var10.getSyntax() != null) {
                           var11.setTypeInfo(var10.getSyntax());
                           if (!var12) {
                              break label50;
                           }
                        }

                        if (var10.getWriteSyntax() != null) {
                           var11.setTypeInfo(var10.getWriteSyntax());
                        }
                     }

                     if (var10.getAccess() != -1) {
                        var11.setAccess(var10.getAccess());
                     }

                     if (var10.getDefVal() != null) {
                        var11.setDefVal(var10.getDefVal());
                     }

                     ++var9;
                     if (var12) {
                        break;
                     }
                  }

                  ++var6;
               }

               if (var12) {
                  continue label62;
               }
            }
         }

         return;
      } while(!var12);

   }

   public SnmpMetadata[] getAttachedMetadata() {
      return this._attached;
   }

   public void removeAll(SnmpObjectInfo var1) {
   }

   public void remove(String var1) throws SnmpValueException {
      this.remove(this.resolveOid(var1));
   }

   public void remove(SnmpOidInfo var1) throws SnmpValueException {
      boolean var5 = SnmpOidInfo.a;
      if (var1 instanceof SnmpTableInfo) {
         SnmpTableInfo var2 = (SnmpTableInfo)var1;
         SnmpObjectInfo[] var3 = var2.getColumns();
         int var4 = 0;

         do {
            if (var4 >= var3.length) {
               this.a((SnmpOidInfo)var2.getEntry());
               var2.getEntry().b();
               this.a((SnmpOidInfo)var2);
               var2.b();
               this.a();
               break;
            }

            this.a((SnmpOidInfo)var3[var4]);
            var3[var4].b();
            ++var4;
         } while(!var5 || !var5);

         if (!var5) {
            return;
         }
      }

      if (var1 instanceof SnmpObjectInfo) {
         SnmpObjectInfo var6 = (SnmpObjectInfo)var1;
         ((SnmpMibInfo)var1).b();
         if (var6.isColumnar()) {
            throw new SnmpValueException(b("\u0010wfR#\u00076zY!\u001c`m\u001c%\u001draJ%\u0017ciPl\u0010ydI!\u001de$\u001c!\u0006e|\u001c>\u0016{gJ)SsfH%\u0001s(H-\u0011zm"));
         }

         this.a(var1, this._snmpObjectMap, this._snmpObjects);
         this.a();
         if (!var5) {
            return;
         }
      }

      if (var1 instanceof SnmpNotificationInfo) {
         this.a(var1, this._snmpNotificationMap, this._snmpNotifications);
         ((SnmpMibInfo)var1).b();
         this.a();
         if (!var5) {
            return;
         }
      }

      if (var1 instanceof SnmpObjectGroupInfo) {
         this.a(var1, this._snmpObjectGroupMap, this._snmpObjectGroups);
         ((SnmpMibInfo)var1).b();
         if (!var5) {
            return;
         }
      }

      if (var1 instanceof SnmpNotificationGroupInfo) {
         this.a(var1, this._snmpNotificationGroupMap, this._snmpNotificationGroups);
         ((SnmpMibInfo)var1).b();
         if (!var5) {
            return;
         }
      }

      if (!(var1 instanceof SnmpModuleIdentityInfo)) {
         throw new SnmpValueException(b("\u0010wfR#\u00076zY!\u001c`m\u001c8\u001b\u007f{\u001c8\nfm\u001c#\u00156kS!\u0003yfY\"\u00076"));
      } else {
         this.a(var1, this._snmpNotificationGroupMap, this._snmpNotificationGroups);
         ((SnmpMibInfo)var1).b();
         if (var5) {
            throw new SnmpValueException(b("\u0010wfR#\u00076zY!\u001c`m\u001c8\u001b\u007f{\u001c8\nfm\u001c#\u00156kS!\u0003yfY\"\u00076"));
         }
      }
   }

   void a(SnmpOidInfo var1) {
      label39: {
         boolean var2 = SnmpOidInfo.a;
         if (var1 instanceof SnmpTableInfo) {
            this.a(var1, this._snmpTableMap, this._snmpTables);
            if (!var2) {
               break label39;
            }
         }

         if (var1 instanceof SnmpObjectInfo) {
            this.a(var1, this._snmpObjectMap, this._snmpObjects);
            if (!var2) {
               break label39;
            }
         }

         if (var1 instanceof SnmpNotificationInfo) {
            this.a(var1, this._snmpNotificationMap, this._snmpNotifications);
            if (!var2) {
               break label39;
            }
         }

         if (var1 instanceof SnmpObjectGroupInfo) {
            this.a(var1, this._snmpObjectGroupMap, this._snmpObjectGroups);
            if (!var2) {
               break label39;
            }
         }

         if (var1 instanceof SnmpNotificationGroupInfo) {
            this.a(var1, this._snmpNotificationGroupMap, this._snmpNotificationGroups);
            if (!var2) {
               break label39;
            }
         }

         if (var1 instanceof SnmpModuleIdentityInfo) {
            this.a(var1, this._snmpNotificationGroupMap, this._snmpNotificationGroups);
         }
      }

      this._oidTree.remove(var1);
   }

   void a(SnmpOidInfo var1, Hashtable var2, Vector var3) {
      if (var1 != null) {
         if (var2 != null) {
            var2.remove(var1.getName());
         }

         if (var3 != null) {
            var3.removeElement(var1);
         }

         SnmpModule var4 = var1.getModule();
         if (var4 != null) {
            var2.remove(var4.getName() + ":" + var1.getName());
            var4.a(var1.getName(), var1);
         }

         var1.setMetadata((SnmpMetadata)null);
      }
   }

   void a() {
      boolean var3 = SnmpOidInfo.a;
      ListIterator var1 = this._snmpObjectGroups.listIterator();

      SnmpMibInfo var2;
      while(true) {
         if (var1.hasNext()) {
            var2 = (SnmpMibInfo)var1.next();
            var2.c();
            if (!var3 || !var3) {
               continue;
            }
            break;
         }

         var1 = this._snmpNotificationGroups.listIterator();
         break;
      }

      while(true) {
         if (var1.hasNext()) {
            var2 = (SnmpMibInfo)var1.next();
            var2.c();
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1 = this._snmpNotifications.listIterator();
         break;
      }

      while(var1.hasNext()) {
         var2 = (SnmpMibInfo)var1.next();
         var2.c();
         if (var3) {
            break;
         }
      }

   }

   public SnmpOid getNextObjectOid(SnmpOid var1, boolean var2) {
      return this._oidTree.getNextObjectOid(var1, var2);
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      if (_repository == null) {
         try {
            _repository = new SnmpMetadataRepository();
         } catch (Exception var2) {
            Logger var1 = Logger.getInstance(b(" xeL\u0001\u0016biX-\u0007w"));
            var1.error(b("!sxS?\u001abgN5SUzY-\u0007\u007fgRl\u0015waP)\u0017") + var2);
         }
      }

   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 115;
               break;
            case 1:
               var10003 = 22;
               break;
            case 2:
               var10003 = 8;
               break;
            case 3:
               var10003 = 60;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface Repository {
      Result loadModules(String[] var1, SnmpMetadata var2) throws IOException, SnmpMetadataException;

      Result loadModule(String var1, SnmpMetadata var2) throws IOException, SnmpMetadataException;

      Result loadFile(String var1, SnmpMetadata var2) throws IOException, SnmpMetadataException;

      void setSearchPath(String var1);

      String getSearchPath();
   }
}
