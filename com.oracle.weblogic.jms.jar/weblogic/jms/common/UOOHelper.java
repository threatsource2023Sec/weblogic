package weblogic.jms.common;

import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDMember;
import weblogic.jms.extensions.JMSOrderException;
import weblogic.jms.frontend.FEDDHandler;
import weblogic.messaging.path.helper.KeyString;

public final class UOOHelper {
   public static boolean cacheUpToDate(FEDDHandler feDDHandler, String memberName, boolean lastHasConsumers, MessageImpl message) {
      DDMember member = feDDHandler.findDDMemberByMemberName(memberName);
      if (member == null) {
         return false;
      } else if (!member.hasConsumers() && lastHasConsumers) {
         return false;
      } else if (!member.isProductionPaused() && !member.isInsertionPaused()) {
         return member.isPersistent() || message.getAdjustedDeliveryMode() != 2;
      } else {
         return false;
      }
   }

   public static boolean hasConsumers(FEDDHandler feDDHandler, String memberName) {
      DDMember member = feDDHandler.findDDMemberByMemberName(memberName);
      return member.hasConsumers();
   }

   public static DistributedDestinationImpl getHashBasedDestination(FEDDHandler feDDHandler, String key) throws JMSException {
      DDHandler ddHandler = feDDHandler.getDDHandler();
      if (ddHandler == null) {
         throw new JMSOrderException("no ddHandler for " + feDDHandler.getName());
      } else {
         int lcv = 0;

         while(true) {
            int size = ddHandler.getNumberOfMembers();
            if (size == 0) {
               throw new JMSOrderException("no known configured members for " + ddHandler.getName());
            }

            int index = key.hashCode() % size;
            if (index < 0) {
               index = -index;
            }

            DDMember member = ddHandler.getMemberByIndex(index);
            ++lcv;
            if (member != null) {
               if (member.isUp() && member.getDDImpl().getDispatcherId() == null) {
                  member.setOutOfDate(true);
               }

               if (member.isUp() && member.getDDImpl().getDispatcherId() != null) {
                  return member.getDDImpl();
               }

               throw new JMSOrderException("hashed member of " + ddHandler.getName() + " is " + member.getName() + " which is not available");
            }

            try {
               Thread.sleep(2L);
            } catch (InterruptedException var8) {
               throw new AssertionError(var8);
            }

            if (lcv >= 20) {
               throw new JMSOrderException("could not get " + index + " from " + member.getName() + " that has size " + size);
            }

            ++lcv;
         }
      }
   }

   public static String getPathServiceJndiName(FEDDHandler feDDHandler, KeyString key) throws JMSException {
      DDHandler ddHandler = feDDHandler.getDDHandler();
      if (ddHandler != null) {
         String psjndin = ddHandler.getPathServiceJndiName();
         if (psjndin != null) {
            return psjndin;
         }
      }

      JMSOrderException jmsOrderException = new JMSOrderException("no ddHandler for " + feDDHandler.getName());
      throw jmsOrderException;
   }
}
