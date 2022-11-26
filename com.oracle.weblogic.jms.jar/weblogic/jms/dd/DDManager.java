package weblogic.jms.dd;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.NewDestinationListener;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.frontend.FEDDHandler;
import weblogic.store.common.PartitionNameUtils;

public final class DDManager implements NewDestinationListener {
   private static HashMap deferredMemberTable = new HashMap();
   private static HashMap member2DDHandler = new HashMap();

   private DDManager() {
   }

   public static DDHandler activateOrUpdate(DDHandler newDDHandler) {
      DDHandler oldDDHandler;
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         oldDDHandler = (DDHandler)DDManager.Name2DDMapHandler.get(newDDHandler.getName(), false);
         if (oldDDHandler == null) {
            DDManager.Name2DDMapHandler.put(newDDHandler.getName(), newDDHandler);
         }
      }

      if (oldDDHandler != null) {
         oldDDHandler.update(newDDHandler);
         return oldDDHandler;
      } else {
         newDDHandler.activate();
         return newDDHandler;
      }
   }

   public static void remoteUpdate(DDHandler update, List updateMembers) {
      DDHandler activeDDHandler = activateOrUpdate(update);
      synchronized(activeDDHandler) {
         if (!activeDDHandler.isLocal()) {
            activeDDHandler.updateMembersWithNoJndiEvent(updateMembers, false);
         } else if (activeDDHandler.isRemoteUpdatePending()) {
            activeDDHandler.updateMembersWithNoJndiEvent(updateMembers, false);
            activeDDHandler.setRemoteUpdatePending(false);
         }

      }
   }

   public static void remoteDeactivate(String name) {
      DDHandler ddHandler;
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         ddHandler = (DDHandler)DDManager.Name2DDMapHandler.get(name, false);
      }

      if (ddHandler != null) {
         if (!ddHandler.isLocal()) {
            ddHandler.deactivate(false);
         }
      }
   }

   public static void deactivate(DDHandler ddHandler) {
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         DDManager.Name2DDMapHandler.remove(ddHandler.getName());
      }
   }

   private static void deferMember(DDMember member) {
      synchronized(member2DDHandler) {
         deferredMemberTable.put(member.getName(), member);
      }
   }

   static DDMember removeDeferredMember(String name) {
      synchronized(member2DDHandler) {
         return (DDMember)deferredMemberTable.remove(name);
      }
   }

   static boolean memberUpdate(DDMember member) {
      DDHandler ddHandler = null;
      synchronized(member2DDHandler) {
         ddHandler = findDDHandlerByMemberName(member.getName());
         if (ddHandler == null) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("DDManager.memberUpdate(" + member + ")isUp=" + member.isUp() + "), no ddHandler yet, deferred");
            }

            deferMember(member);
            return false;
         }
      }

      ddHandler.memberUpdate(member);
      return true;
   }

   public static void addMemberStatusListener(String memberName, MemberStatusListener listener) {
      DDHandler ddHandler = findDDHandlerByMemberName(memberName);

      assert ddHandler != null;

      ddHandler.addMemberStatusListener(memberName, listener);
   }

   public static DDHandler findDDHandlerByMemberName(String memberName) {
      synchronized(member2DDHandler) {
         return (DDHandler)member2DDHandler.get(memberName);
      }
   }

   public static void addDDHandlerMember(String memberName, DDHandler ddHandler) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDManager.addDDHandlerMember(" + memberName + ", ddHandler@" + ddHandler.hashCode() + ")");
      }

      synchronized(member2DDHandler) {
         member2DDHandler.put(memberName, ddHandler);
      }
   }

   public static DDHandler removeDDHandlerMember(String memberName) {
      synchronized(member2DDHandler) {
         return (DDHandler)member2DDHandler.remove(memberName);
      }
   }

   public static boolean isMember(String ddName, String memberName) {
      DDHandler ddHandler = findDDHandlerByMemberName(memberName);
      return ddHandler == null ? false : ddHandler.getName().equals(ddName);
   }

   public static boolean isDD(String ddName) {
      return findDDHandlerByDDName(ddName) != null;
   }

   public static DDHandler findDDHandlerByDDName(String ddName) {
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         return (DDHandler)DDManager.Name2DDMapHandler.get(ddName, false);
      }
   }

   public static DDHandler findDDHandlerByDDNameWithShortName(String ddName) {
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         return (DDHandler)DDManager.Name2DDMapHandler.get(ddName, true);
      }
   }

   public static FEDDHandler findFEDDHandlerByDDName(String ddName) {
      DDHandler ddHandler = findDDHandlerByDDName(ddName);
      return ddHandler == null ? null : ddHandler.getFEDDHandler();
   }

   public static DistributedDestinationImpl findDDImplByDDNameWithShortName(String ddName) {
      DDHandler ddHandler = findDDHandlerByDDNameWithShortName(ddName);
      return ddHandler == null ? null : ddHandler.getDDImpl();
   }

   public static DistributedDestinationImpl findDDImplByDDName(String ddName) {
      DDHandler ddHandler = findDDHandlerByDDName(ddName);
      return ddHandler == null ? null : ddHandler.getDDImpl();
   }

   public static boolean handlerHasSecurityModeByMemberName(String mName, int sMode) {
      DDHandler ddHandler = findDDHandlerByMemberName(mName);
      return ddHandler == null ? false : ddHandler.memberHasSecurityMode(sMode);
   }

   public static DistributedDestinationImpl findDDImplByMemberName(String memberName) {
      DDHandler ddHandler = findDDHandlerByMemberName(memberName);
      return ddHandler == null ? null : ddHandler.getDDIByMemberName(memberName);
   }

   public static String debugKeys() {
      String values = "\n values are ";
      Set keys;
      synchronized(member2DDHandler) {
         keys = ((HashMap)member2DDHandler.clone()).keySet();
         Iterator iterator = ((HashMap)member2DDHandler.clone()).values().iterator();
         if (iterator.hasNext()) {
            while(iterator.hasNext()) {
               DDHandler ddHandler = (DDHandler)iterator.next();
               values = values + "\n(" + ddHandler + " keys: " + ddHandler.debugKeys() + ")";
            }
         } else {
            values = "\n no values";
         }
      }

      return keys == null ? "DDManager keys are null" : "DDManager Member Name keys are: " + keys.toString() + values;
   }

   public static DDMember findDDMemberByMemberName(String memberName) {
      DDHandler ddHandler = findDDHandlerByMemberName(memberName);
      return ddHandler == null ? null : ddHandler.findMemberByName(memberName);
   }

   public static Iterator getAllDDHandlers() {
      synchronized(DDManager.Name2DDMapHandler.name2DDHandler) {
         return ((Hashtable)DDManager.Name2DDMapHandler.getHandlerTable().clone()).values().iterator();
      }
   }

   public void newDestination(BEDestinationImpl dest) {
      DDHandler ddHandler = findDDHandlerByMemberName(dest.getName());
      if (ddHandler != null) {
         ddHandler.newDestination(dest);
      }

   }

   public static String getPartitionName(String configName) throws JMSException {
      DDHandler ddHandler = findDDHandlerByDDName(configName);
      if (ddHandler == null) {
         throw new JMSException("There is no DD named " + configName);
      } else {
         return ddHandler.getPartitionName();
      }
   }

   static {
      BEDestinationImpl.addNewDestinationListener(new DDManager());
   }

   private static class Name2DDMapHandler {
      private static Hashtable name2DDHandler = new Hashtable();

      static void put(String name, Object obj) {
         name2DDHandler.put(name, obj);
      }

      static void remove(String name) {
         name2DDHandler.remove(name);
      }

      static Object get(String name, boolean createAPI) {
         Object obj = name2DDHandler.get(name);
         if (createAPI && obj == null) {
            Set entries = name2DDHandler.entrySet();
            Iterator var4 = entries.iterator();

            Map.Entry entry;
            do {
               if (!var4.hasNext()) {
                  return null;
               }

               entry = (Map.Entry)var4.next();
            } while(!PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", (String)entry.getKey()).equals(name));

            return entry.getValue();
         } else {
            return obj;
         }
      }

      static Hashtable getHandlerTable() {
         return name2DDHandler;
      }
   }
}
