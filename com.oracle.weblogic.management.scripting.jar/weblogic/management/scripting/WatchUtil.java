package weblogic.management.scripting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;

public class WatchUtil {
   WLScriptContext ctx = null;
   HashMap watchListeners = new HashMap();
   private WLSTMsgTextFormatter txtFmt;
   private int watchCounter = 0;
   private static final String WLST_WATCH_NAME = "wlst-listener-";

   public WatchUtil(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   ObjectName getONFromObject(Object mbean) throws ScriptException {
      ObjectName oname = null;

      try {
         if (mbean instanceof String) {
            oname = new ObjectName((String)mbean);
         } else if (mbean instanceof ObjectName) {
            oname = (ObjectName)mbean;
         } else {
            oname = this.ctx.getObjectName(mbean);
         }
      } catch (MalformedObjectNameException var4) {
         this.ctx.throwWLSTException(this.txtFmt.getMalformedObjectName(), var4);
      }

      return oname;
   }

   public void watch(Object mbean, String attributeNames, String logFile, String watchName) throws ScriptException {
      ObjectName oname = null;
      String[] anames = null;
      watchName = this.getWatchName(watchName);

      try {
         if (attributeNames != null) {
            anames = StringUtils.splitCompletely(attributeNames, ",");
         }

         oname = this.getONFromObject(mbean);
         WatchFilter wfilter = new WatchFilter(anames);
         WatchListener wlistener = new WatchListener(logFile, this.ctx.stdOutputMedium, this.ctx.logToStandardOut, watchName, this.ctx);
         this.ctx.getMBSConnection(this.ctx.getDomainType()).addNotificationListener(oname, wlistener, wfilter, (Object)null);
         String s = attributeNames;
         if (attributeNames == null) {
            s = "ALL";
         }

         String fullName = this.txtFmt.getWatchListenerName(watchName, this.asString(oname), s);
         WLSTWatchListener wl = new WLSTWatchListener(oname, wlistener, watchName, fullName);
         this.watchListeners.put(watchName, wl);
         this.ctx.println(this.txtFmt.getAddedWatchListener(watchName, this.asString(oname)));
      } catch (InstanceNotFoundException var12) {
         this.ctx.throwWLSTException(this.txtFmt.getMBeanNotFound(this.asString(oname)), var12);
      } catch (IOException var13) {
         String msg = this.txtFmt.getIOExceptionAddingWatchListener(this.asString(oname));
         this.ctx.throwWLSTException(msg, var13);
      }

   }

   public void removeWatch(Object mbean, String watchName) throws ScriptException {
      if (this.watchListeners.isEmpty()) {
         String msg = this.txtFmt.getNoWatchListenersFound(this.asString(mbean), watchName);
         this.ctx.throwWLSTException(msg);
      }

      ObjectName wl;
      try {
         if (mbean == null && watchName == null) {
            Iterator it = this.watchListeners.values().iterator();

            while(it.hasNext()) {
               wl = null;

               ObjectName on;
               try {
                  WLSTWatchListener wl = (WLSTWatchListener)it.next();
                  this.ctx.runtimeMSC.removeNotificationListener(wl.getObjectName(), wl.getWL());
                  ObjectName on = wl.getObjectName();
                  this.ctx.println(this.txtFmt.getDeletedListener(wl.getWN(), this.asString(on)));
               } catch (InstanceNotFoundException var8) {
                  on = wl.getObjectName();
                  this.ctx.println(this.txtFmt.getDeletedListener(watchName, this.asString(on)));
               } catch (IOException var9) {
                  on = wl.getObjectName();
                  this.ctx.throwWLSTException(this.txtFmt.getIOExceptionDeletingWatchListener(this.asString(on)));
               } catch (ListenerNotFoundException var10) {
                  on = wl.getObjectName();
                  this.ctx.throwWLSTException(this.txtFmt.getListenerNotFound(this.asString(on)));
               }
            }

            this.watchListeners = new HashMap();
            return;
         }

         if (watchName != null) {
            WLSTWatchListener wl = (WLSTWatchListener)this.watchListeners.get(watchName);
            this.ctx.runtimeMSC.removeNotificationListener(wl.getObjectName(), wl.getWL());
            this.watchListeners.remove(watchName);
            this.ctx.println(this.txtFmt.getListenerRemoved(watchName, this.asString(mbean)));
         } else {
            ObjectName oname = this.getONFromObject(mbean);
            List watches = new ArrayList();
            Iterator iter = this.watchListeners.values().iterator();

            while(iter.hasNext()) {
               WLSTWatchListener wl = (WLSTWatchListener)iter.next();
               if (wl.getObjectName().equals(oname)) {
                  watches.add(wl.getWN());
                  this.ctx.runtimeMSC.removeNotificationListener(wl.getObjectName(), wl.getWL());
               }
            }

            if (watches.isEmpty()) {
               this.ctx.throwWLSTException(this.txtFmt.getListenerNotFound(this.asString(oname)));
            } else {
               Iterator _iter = watches.iterator();

               while(_iter.hasNext()) {
                  String watch = (String)_iter.next();
                  this.watchListeners.remove(watch);
               }

               this.ctx.println(this.txtFmt.getListenerRemoved(watchName, this.asString(oname)));
            }
         }
      } catch (InstanceNotFoundException var11) {
         wl = this.getONFromObject(mbean);
         this.ctx.throwWLSTException(this.txtFmt.getWatchRemoveMBeanNotFound(this.asString(wl)), var11);
      } catch (IOException var12) {
         wl = this.getONFromObject(mbean);
         this.ctx.throwWLSTException(this.txtFmt.getIOExceptionRemoveingWatchListener(this.asString(wl)), var12);
      } catch (ListenerNotFoundException var13) {
         wl = this.getONFromObject(mbean);
         this.ctx.throwWLSTException(this.txtFmt.getListenerNotFound(this.asString(wl)), var13);
      }

   }

   private String getWatchName(String watchName) throws ScriptException {
      if (watchName == null) {
         watchName = "wlst-listener-" + this.watchCounter;
         ++this.watchCounter;
         return watchName;
      } else if (this.watchListeners.containsKey(watchName)) {
         this.ctx.throwWLSTException(this.txtFmt.getListenerAlreadyExists(watchName));
         return watchName;
      } else {
         return watchName;
      }
   }

   void showWatches() throws ScriptException {
      if (this.watchListeners.isEmpty()) {
         this.ctx.println(this.txtFmt.getNoListenersConfigured());
      } else {
         Iterator col = this.watchListeners.values().iterator();

         while(col.hasNext()) {
            WLSTWatchListener wl = (WLSTWatchListener)col.next();
            this.ctx.println(wl.getDescription());
         }

      }
   }

   private String asString(Object o) {
      return o == null ? "null" : o.toString();
   }
}
