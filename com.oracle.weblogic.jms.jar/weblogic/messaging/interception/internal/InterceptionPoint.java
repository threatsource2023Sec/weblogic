package weblogic.messaging.interception.internal;

import javax.xml.rpc.handler.MessageContext;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.exceptions.MessageContextException;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;

class InterceptionPoint {
   private String interceptionPointType = null;
   private String[] interceptionPointName = null;
   private String internalName = null;
   private Association association = null;
   private InterceptionPointTypeWrapper iptw = null;
   private int handleCount = 0;

   InterceptionPoint(String interceptionPointType, String[] interceptionPointName, String internalName, InterceptionPointTypeWrapper iptw) {
      this.interceptionPointType = interceptionPointType;
      this.interceptionPointName = interceptionPointName;
      this.internalName = internalName;
      this.iptw = iptw;
   }

   synchronized void register() {
      ++this.handleCount;
   }

   synchronized void unregister() {
      --this.handleCount;
      if (this.handleCount == 0 & this.association == null) {
         this.iptw.removeIP(this.internalName);
      }

   }

   synchronized int getRegistrationCount() {
      return this.handleCount;
   }

   InterceptionPointHandle createHandle() {
      this.register();
      return new InterceptionPointHandleImpl(this);
   }

   void removeHandle() {
      this.unregister();
   }

   String[] getNameInternal() {
      return this.getName();
   }

   String[] getName() {
      return InterceptionServiceImpl.copyIPName(this.interceptionPointName);
   }

   String getType() {
      return this.interceptionPointType;
   }

   String getInternalName() {
      return this.internalName;
   }

   synchronized void addAssociation(Association a) {
      this.association = a;
   }

   synchronized void removeAssociation() {
      this.association = null;
      if (this.handleCount == 0) {
         this.iptw.removeIP(this.internalName);
      }

   }

   synchronized Association getAssociation() {
      return this.association;
   }

   boolean process(MessageContext ctx) throws InterceptionServiceException, MessageContextException, InterceptionException {
      return false;
   }

   void processOnly(MessageContext ctx) throws InterceptionServiceException, MessageContextException, InterceptionException {
   }

   static String createInternalName(String interceptionPointType, String[] interceptionPointName) {
      StringBuffer buffer = new StringBuffer(256);
      buffer.append(interceptionPointType.length());
      buffer.append(" " + interceptionPointType);
      buffer.append(interceptionPointName.length);

      for(int i = 0; i < interceptionPointName.length; ++i) {
         buffer.append(" " + interceptionPointName[i]);
      }

      return buffer.toString();
   }
}
