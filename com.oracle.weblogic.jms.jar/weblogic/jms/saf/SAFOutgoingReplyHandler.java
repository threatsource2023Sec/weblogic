package weblogic.jms.saf;

import java.util.HashMap;
import java.util.Map;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DestinationImplObserver;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.WrappedDestinationImpl;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.forwarder.ReplyHandler;

public class SAFOutgoingReplyHandler implements ReplyHandler, DestinationImplObserver {
   private String replyToSAFContextName;
   private static Map destinationTable = new HashMap();

   public static void init() {
      WrappedDestinationImpl.setObserver(new SAFOutgoingReplyHandler((String)null));
   }

   public SAFOutgoingReplyHandler(String replyToSAFContextName) {
      this.replyToSAFContextName = replyToSAFContextName;
   }

   public void process(MessageImpl message) {
      DestinationImpl dest = (DestinationImpl)message.getJMSReplyTo();
      if (dest != null) {
         String destJNDIName = null;
         if (dest instanceof DistributedDestinationImpl) {
            DDHandler ddHandler = DDManager.findDDHandlerByDDName(dest.getName());
            if (ddHandler != null) {
               destJNDIName = ddHandler.getJNDIName();
            }
         } else {
            synchronized(destinationTable) {
               destJNDIName = (String)destinationTable.get(makeName(dest));
            }
         }

         if (this.replyToSAFContextName != null) {
            if (destJNDIName != null) {
               dest.setReferenceName(this.replyToSAFContextName + "@@" + destJNDIName);
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Setting reference name to " + dest.getReferenceName());
               }
            } else if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Cannot find reference name for " + dest.getName());
            }

         }
      }
   }

   private static String makeName(DestinationImpl dest) {
      return dest.getApplicationName() + "!" + dest.getModuleName() + "!" + dest.getName();
   }

   public void setReplyToSAFRemoteContextName(String replyToRemoteContextName) {
      this.replyToSAFContextName = replyToRemoteContextName;
   }

   public void newDestination(String jndiName, DestinationImpl dest) {
      String namekey = makeName(dest);
      synchronized(destinationTable) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Adding " + jndiName + " mapping to " + namekey + "[" + dest + "] to table");
         }

         destinationTable.put(namekey, jndiName);
      }
   }

   public void removeDestination(String jndiName, DestinationImpl dest) {
      String namekey = makeName(dest);
      synchronized(destinationTable) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Removing " + namekey + "[" + dest + "] from table");
         }

         destinationTable.remove(namekey);
      }
   }
}
