package weblogic.jms.frontend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dd.DDStatusListener;

public final class SAFReplyHandler implements DDStatusListener {
   private Map idMap;
   private static ConcurrentMap safReplyHanderMap = new ConcurrentHashMap();
   private String partitionName;

   public SAFReplyHandler(String partitionName) {
      this.partitionName = partitionName;
   }

   public static SAFReplyHandler getSAFReplyHandler() {
      String partitionKey = JMSService.getSafePartitionNameFromThread();
      if (partitionKey == null) {
         partitionKey = "null";
      }

      Class var1 = SAFReplyHandler.class;
      synchronized(SAFReplyHandler.class) {
         SAFReplyHandler safReplyHandler = (SAFReplyHandler)safReplyHanderMap.get(partitionKey);
         if (safReplyHandler == null) {
            safReplyHandler = new SAFReplyHandler(partitionKey);
            safReplyHanderMap.put(partitionKey, safReplyHandler);
         }

         return safReplyHandler;
      }
   }

   public void process(MessageImpl message) {
      DestinationImpl replyTo = (DestinationImpl)message.getJMSReplyTo();
      if (replyTo != null) {
         String referenceName = replyTo.getReferenceName();
         if (referenceName != null) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("Reply to with a reference name of " + referenceName);
            }

            synchronized(this) {
               if (this.idMap == null) {
                  this.init();
               }

               DDHandler ddHandler = (DDHandler)this.idMap.get(this.seperatedByDot(referenceName));
               if (ddHandler == null) {
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug("referenceName " + referenceName + ": not found, unable to resolve replyto destination " + replyTo.getName() + " for message " + message);
                  }

               } else {
                  if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                     JMSDebug.JMSFrontEnd.debug(referenceName + " found as destination: " + ddHandler.getName());
                  }

                  replyTo.setReferenceName((String)null);
                  message.setJMSReplyTo(ddHandler.getDDImpl());
               }
            }
         }
      }
   }

   private String seperatedByDot(String name) {
      String newStr = "";

      for(int index = name.indexOf("/"); index >= 0; index = name.indexOf("/")) {
         newStr = newStr + name.substring(0, index) + ".";
         name = name.substring(index + 1);
      }

      newStr = newStr + name;
      return newStr;
   }

   private synchronized void init() {
      this.idMap = new HashMap();
      Iterator iter = DDManager.getAllDDHandlers();

      while(iter.hasNext()) {
         DDHandler ddHandler = (DDHandler)iter.next();
         if (ddHandler.getReferenceName() != null && ddHandler.getPartitionName().equals(this.partitionName)) {
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               JMSDebug.JMSFrontEnd.debug("Adding " + ddHandler.getName() + " to table with referenceName " + ddHandler.getReferenceName());
            }

            this.idMap.put(ddHandler.getReferenceName(), ddHandler);
         }
      }

      DDHandler.addGeneralStatusListener(this, 20);
   }

   public void statusChangeNotification(DDHandler notifier, int events) {
      synchronized(this) {
         Iterator iter = this.idMap.values().iterator();

         while(iter.hasNext()) {
            DDHandler ddHandler = (DDHandler)iter.next();
            if (ddHandler == notifier) {
               iter.remove();
               break;
            }
         }

         if ((events & 16) == 0) {
            if (notifier.getReferenceName() != null) {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("Adding " + notifier.getName() + " to table with referenceName " + notifier.getReferenceName());
               }

               this.idMap.put(notifier.getReferenceName(), notifier);
            }

         }
      }
   }
}
