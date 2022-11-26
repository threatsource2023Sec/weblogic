package weblogic.messaging.saf.internal;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFConversationRuntimeMBean;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.saf.SAFException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ConversationRuntimeDelegate extends RuntimeMBeanDelegate implements SAFConversationRuntimeMBean {
   static final long serialVersionUID = -7390691564220105733L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ConversationAssembler conversation;

   public ConversationRuntimeDelegate(SAFAgentAdmin parent, ConversationAssembler conversation) throws ManagementException {
      super("SAFConversation" + createLegalMBeanName(conversation.getName()), parent, false);
      this.conversation = conversation;
   }

   public String getDestinationURL() {
      return this.conversation.getInfo().getDestinationURL();
   }

   public String getQOS() {
      int QOS = this.conversation.getInfo().getQOS();
      switch (QOS) {
         case 1:
            return new String("Exactly-once");
         case 2:
            return new String("At-least-once");
         case 3:
            return new String("At-most-once");
         default:
            return new String("Unknown");
      }
   }

   public void destroy() throws SAFException {
      this.conversation.destroy();
   }

   public String getConversationName() {
      return this.conversation.getName();
   }

   void registerMe() throws ManagementException {
      PrivilegedActionUtilities.register(this, KERNEL_ID);
   }

   private static final String createLegalMBeanName(String name) {
      String rtnName = name.replace(':', ';');
      return rtnName;
   }

   ConversationAssembler getConversation() {
      return this.conversation;
   }
}
