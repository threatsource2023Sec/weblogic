package weblogic.jndi.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.utils.LocatorUtilities;

class AbstractAdminModeHandler {
   protected final ServerNamingNode node;
   private HashSet adminModeSet = new HashSet();
   private final Object adminModeSetLock = new Object();

   AbstractAdminModeHandler(ServerNamingNode node) {
      this.node = node;
   }

   private boolean hasAdminModeBindings() {
      HashSet curSet = null;
      synchronized(this.adminModeSetLock) {
         curSet = this.adminModeSet;
      }

      return !curSet.isEmpty();
   }

   protected boolean isAdminMode(String name) {
      HashSet curSet = null;
      synchronized(this.adminModeSetLock) {
         curSet = this.adminModeSet;
      }

      return !curSet.isEmpty() && curSet.contains(name);
   }

   protected void setAdminMode(String name) {
      synchronized(this.adminModeSetLock) {
         HashSet curSet = new HashSet(this.adminModeSet);
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ AdminMode.setAdminMode name=" + name + ", ns=" + this.node.getNameInNamespace() + ", this=" + this);
         }

         curSet.add(name);
         this.adminModeSet = curSet;
      }
   }

   protected void unsetAdminMode(String name) {
      synchronized(this.adminModeSetLock) {
         if (!this.adminModeSet.isEmpty()) {
            HashSet curSet = new HashSet(this.adminModeSet);
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ AdminMode.unsetAdminMode name=" + name + ", ns=" + this.node.getNameInNamespace() + ", this=" + this);
            }

            curSet.remove(name);
            this.adminModeSet = curSet;
         }
      }
   }

   Collection getAccessibleBindings(Collection bindings) {
      ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ AdminMode.getAccessibleBindings hasAdminModeBindings=" + this.hasAdminModeBindings() + ", adminRequest=" + avus.isAdminModeRequest() + ", this=" + this + ", ns=" + this.node.getNameInNamespace());
      }

      if (this.hasAdminModeBindings() && !avus.isAdminModeRequest()) {
         Collection list = new LinkedList();
         Iterator var4 = bindings.iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            if (!this.isAdminMode((String)entry.getKey())) {
               if (NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("++++ non-admin binding: " + entry.getKey());
               }

               list.add(entry);
            } else if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("++++ admin binding: " + entry.getKey());
            }
         }

         return list;
      } else {
         return bindings;
      }
   }
}
