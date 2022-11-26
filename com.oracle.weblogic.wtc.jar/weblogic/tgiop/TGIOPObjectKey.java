package weblogic.tgiop;

import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import weblogic.iiop.ObjectKey;

public class TGIOPObjectKey extends ObjectKey {
   public static final int BEAOBJKEY_TPFW = 1;
   public static final int BEAOBJKEY_INTFREP = 2;
   public static final int BEAOBJKEY_FF = 3;
   public static final int BEAOBJKEY_ROOT = 4;
   public static final int BEAOBJKEY_NTS = 5;
   public static final int BEAOBJKEY_USER = 6;
   public static final int BEAOBJKEY_NAMESERVICE = 7;
   private static final Map initialRefIdMap = new HashMap();
   public static final String FACTORY_FINDER = "FactoryFinder";
   public static final String INTERFACE_REPOSITORY = "InterfaceRepository";
   public static final String TOBJ_SIMPLE_EVENTS_SERVICE = "Tobj_SimpleEventsService";
   public static final String NOTIFICATION_SERVICE = "NotificationService";
   public static final String NAME_SERVICE = "NameService";
   private static final Map initialRefOidMap;
   private static final Map initialRefOaIdMap;

   public TGIOPObjectKey(String refName, String domain) throws NamingException {
      this.setInterfaceName((String)initialRefIdMap.get(refName));
      this.setWLEObjectId(this.geInitialRefOID(refName));
      this.setWLEObjectAdapter((Integer)initialRefOaIdMap.get(refName));
      this.setWLEDomainId(domain);
      this.setKeyType(0);
      if (this.getInterfaceName() == null) {
         throw new NamingException("Invalid Initial Reference Name: " + refName);
      }
   }

   public String geInitialRefOID(String refName) {
      String objectAdapter = (String)initialRefOidMap.get(refName);
      return objectAdapter == null ? "" : objectAdapter;
   }

   public static String getInitialRefObjectId(String s) {
      return (String)initialRefOidMap.get(s);
   }

   public static String getInitialRefObjectAdapter(String s) {
      return ((Integer)initialRefOaIdMap.get(s)).toString();
   }

   public static String getInitialRefInterfaceName(String serviceName) {
      return (String)initialRefIdMap.get(serviceName);
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
      initialRefOaIdMap.put("FactoryFinder", 3);
      initialRefOaIdMap.put("InterfaceRepository", 2);
      initialRefOaIdMap.put("Tobj_SimpleEventsService", 5);
      initialRefOaIdMap.put("NotificationService", 5);
      initialRefOaIdMap.put("NameService", 7);
   }
}
