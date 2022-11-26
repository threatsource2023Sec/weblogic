package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.ntrace;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public final class ORBInitialRef {
   public static final String CORBALOC_PREFIX = "corbaloc";
   public static final String CORBANAME_PREFIX = "corbaname";
   public static final String CORBALOC_RIR_PREFIX = "corbaloc:rir:";
   public static final String CORBALOC_TGIOP_PREFIX = "corbaloc:tgiop:";
   public static final String CORBANAME_RIR_PREFIX = "corbaname:rir:";
   public static final String CORBANAME_TGIOP_PREFIX = "corbaname:tgiop:";
   public static final String NAME_SERVICE = "NameService";
   private static final Map initialRefIdMap = new HashMap();
   private static final Map initialRefOidMap;
   private static final Map initialRefOaIdMap;
   private final Map cachedReferences;
   private final org.omg.CORBA.ORB orb;

   public ORBInitialRef(org.omg.CORBA.ORB orb) {
      this.orb = orb;
      this.cachedReferences = Collections.synchronizedMap(new HashMap());
   }

   public Object convertTGIOPURLToObject(String url) throws InvalidName {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBInitialRef/convertTGIOPURL/" + url);
      }

      if (url != null && (url.startsWith("corbaloc:tgiop:") || url.startsWith("corbaname:tgiop:"))) {
         boolean isName = false;
         int delimIdx = false;
         int delimIdx;
         if (url.startsWith("corbaloc:tgiop:")) {
            url = url.substring("corbaloc:tgiop:".length());
            Object ref = (Object)this.cachedReferences.get(url);
            if (ref != null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ORBInitialRef/convertTGIOPURL/20/" + ref);
               }

               return ref;
            }

            delimIdx = url.indexOf(47);
         } else {
            url = url.substring("corbaname:tgiop:".length());
            isName = true;
            delimIdx = url.indexOf(35);
         }

         int major = 1;
         int minor = 0;
         String refName = null;
         if (isName) {
            refName = "NameService";
         } else {
            refName = url.substring(delimIdx + 1);
         }

         String intf = (String)initialRefIdMap.get(refName);
         String objectId = (String)initialRefOidMap.get(refName);
         Integer oaId = (Integer)initialRefOaIdMap.get(refName);
         if (intf == null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORBInitialRef/convertTGIOPURL/40");
            }

            throw new InvalidName();
         } else {
            String addr = url.substring(0, delimIdx);
            String domain = addr;

            try {
               if (addr.indexOf(64) != -1) {
                  domain = addr.substring(addr.indexOf(64) + 1);
                  String ver = addr.substring(0, addr.indexOf(64));
                  major = Integer.parseInt(ver.substring(0, ver.indexOf(46)));
                  minor = Integer.parseInt(ver.substring(ver.indexOf(46) + 1));
               }
            } catch (NumberFormatException var17) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ORBInitialRef/convertTGIOPURL/50");
               }

               throw new BAD_PARAM();
            }

            StringBuffer ior = new StringBuffer();
            add_byte(ior, (byte)0);
            add_string(ior, intf);
            add_ulong(ior, 1);
            add_ulong(ior, 0);
            StringBuffer profile = new StringBuffer();
            add_byte(profile, (byte)0);
            add_byte(profile, (byte)major);
            add_byte(profile, (byte)minor);
            add_string(profile, "");
            add_short(profile, 0);
            StringBuffer key = new StringBuffer();
            add_byte(key, (byte)0);
            add_byte(key, (byte)66);
            add_byte(key, (byte)69);
            add_byte(key, (byte)65);
            add_byte(key, (byte)8);
            add_byte(key, (byte)1);
            add_byte(key, (byte)2);
            add_byte(key, oaId.byteValue());
            add_string(key, domain);
            add_ulong(key, 0);
            add_string(key, intf);
            add_string(key, objectId);
            add_ulong(key, 0);
            add_ulong(profile, key.length() / 2);
            profile.append(key);
            if (major != 1 || minor != 0) {
               add_ulong(profile, 0);
            }

            add_ulong(ior, profile.length() / 2);
            ior.append(profile);
            ior.insert(0, "IOR:");
            if (traceEnabled) {
               ntrace.doTrace("/ORBInitialRef/convertTGIOPURL/100/" + ior.toString());
            }

            Object obj = this.orb.string_to_object(ior.toString());
            if (isName) {
               obj = this.resolvePath(obj, url.substring(delimIdx + 1));
            } else {
               this.cachedReferences.put(url, obj);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/ORBInitialRef/convertTGIOPURL/120/" + obj);
            }

            return obj;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBInitialRef/convertTGIOPURL/10");
         }

         throw new InvalidName();
      }
   }

   public Object resolvePath(Object nsObj, String resolvePath) throws InvalidName {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORBInitialRef/resolvePath/" + resolvePath);
      }

      NamingContext ncRef = NamingContextHelper.narrow(nsObj);
      int i = 1;

      String str;
      for(str = resolvePath; str.indexOf(47) != -1; str = str.substring(str.indexOf(47) + 1)) {
         ++i;
      }

      NameComponent[] path = new NameComponent[i];
      str = resolvePath;

      for(i = 0; str.indexOf(47) != -1; str = str.substring(str.indexOf(47) + 1)) {
         path[i] = new NameComponent(str.substring(0, str.indexOf(47)), "");
         ++i;
      }

      path[i] = new NameComponent(str, "");

      try {
         Object retObj = ncRef.resolve(path);
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBInitialRef/resolvePath/100/" + retObj);
         }

         return retObj;
      } catch (Exception var9) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORBInitialRef/resolvePath/120/" + var9);
         }

         throw new InvalidName();
      }
   }

   private static void add_nibble(StringBuffer target, byte value) {
      target.append(Integer.toString(value, 16));
   }

   private static void add_byte(StringBuffer target, byte value) {
      byte nibble = (byte)(value >>> 4 & 15);
      add_nibble(target, nibble);
      nibble = (byte)(value & 15);
      add_nibble(target, nibble);
   }

   private static void add_padding(StringBuffer target, int size) {
      int align = target.length() / 2 % size;
      if (align != 0) {
         for(int i = 0; i < size - align; ++i) {
            add_byte(target, (byte)0);
         }

      }
   }

   private static void add_ulong(StringBuffer target, int value) {
      add_padding(target, 4);
      byte b1 = (byte)(value >>> 24 & 255);
      byte b2 = (byte)(value >>> 16 & 255);
      byte b3 = (byte)(value >>> 8 & 255);
      byte b4 = (byte)(value & 255);
      add_byte(target, b1);
      add_byte(target, b2);
      add_byte(target, b3);
      add_byte(target, b4);
   }

   private static void add_short(StringBuffer target, int value) {
      add_padding(target, 2);
      byte b1 = (byte)(value >>> 8 & 255);
      byte b2 = (byte)(value & 255);
      add_byte(target, b1);
      add_byte(target, b2);
   }

   private static void add_string(StringBuffer target, String value) {
      if (value == null) {
         add_ulong(target, 0);
      } else {
         add_ulong(target, value.length() + 1);

         for(int i = 0; i < value.length(); ++i) {
            add_byte(target, (byte)value.charAt(i));
         }

         add_byte(target, (byte)0);
      }
   }

   static {
      initialRefIdMap.put("FactoryFinder", "IDL:beasys.com/Tobj/FactoryFinder:1.0");
      initialRefIdMap.put("InterfaceRepository", "IDL:omg.org/CORBA/Repository:1.0");
      initialRefIdMap.put("Tobj_SimpleEventsService", "IDL:beasys.com/TobjI_SimpleEvents/ChannelFactory:1.0");
      initialRefIdMap.put("NotificationService", "IDL:beasys.com/TobjI_Notification/EventChannelFactory:1.0");
      initialRefIdMap.put("NameService", "IDL:omg.org/CosNaming/NamingContextExt:1.0");
      initialRefOidMap = new HashMap();
      initialRefOidMap.put("FactoryFinder", "FactoryFinder");
      initialRefOidMap.put("InterfaceRepository", "Repository");
      initialRefOidMap.put("Tobj_SimpleEventsService", "Tobj_SimpleEventsService");
      initialRefOidMap.put("NotificationService", "NotificationService");
      initialRefOidMap.put("NameService", "BEA:NameService:Root");
      initialRefOaIdMap = new HashMap();
      initialRefOaIdMap.put("FactoryFinder", new Integer(3));
      initialRefOaIdMap.put("InterfaceRepository", new Integer(2));
      initialRefOaIdMap.put("Tobj_SimpleEventsService", new Integer(5));
      initialRefOaIdMap.put("NotificationService", new Integer(5));
      initialRefOaIdMap.put("NameService", new Integer(7));
   }
}
