package weblogic.messaging.interception.internal;

import javax.xml.rpc.handler.MessageContext;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.exceptions.MessageContextException;
import weblogic.messaging.interception.interfaces.AssociationInfo;
import weblogic.messaging.interception.interfaces.CarrierCallBack;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;

public class InterceptionPointHandleImpl implements InterceptionPointHandle {
   private InterceptionPoint interceptionPoint = null;
   private boolean unregistered = false;

   InterceptionPointHandleImpl(InterceptionPoint interceptionPoint) {
      this.interceptionPoint = interceptionPoint;
   }

   public String[] getName() throws InterceptionServiceException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      return ip.getName();
   }

   public String getType() throws InterceptionServiceException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      return ip.getType();
   }

   synchronized void unregister() throws InterceptionServiceException {
      this.checkUnregistered();
      this.unregistered = true;
      this.interceptionPoint.unregister();
      this.interceptionPoint = null;
   }

   public synchronized boolean hasAssociation() throws InterceptionServiceException {
      this.checkUnregistered();
      return this.interceptionPoint.getAssociation() != null;
   }

   public AssociationInfo getAssociationInfo() throws InterceptionServiceException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      Association association = ip.getAssociation();
      return association == null ? null : ip.getAssociation().getInfoInternal();
   }

   public boolean process(MessageContext ctx) throws InterceptionServiceException, MessageContextException, InterceptionException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      Association association = ip.getAssociation();
      return association == null ? true : association.process(ctx);
   }

   public void processAsync(MessageContext ctx, CarrierCallBack callBack) throws InterceptionServiceException, MessageContextException, InterceptionException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      Association association = ip.getAssociation();
      if (association == null) {
         callBack.onCallBack(true);
      } else {
         association.process(ctx, callBack);
      }
   }

   public void processOnly(MessageContext ctx) throws InterceptionServiceException, MessageContextException, InterceptionException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      Association association = ip.getAssociation();
      if (association != null) {
         association.processOnly(ctx);
      }
   }

   public void processOnlyAsync(MessageContext ctx, CarrierCallBack callBack) throws InterceptionServiceException, MessageContextException, InterceptionException {
      InterceptionPoint ip = null;
      synchronized(this) {
         this.checkUnregistered();
         ip = this.interceptionPoint;
      }

      Association association = ip.getAssociation();
      if (association == null) {
         callBack.onCallBack(true);
      } else {
         association.processOnly(ctx, callBack);
      }
   }

   private void checkUnregistered() throws InterceptionServiceException {
      if (this.unregistered) {
         throw new InterceptionServiceException(MIExceptionLogger.logUnregisterInterceptionPointAlreayRemoveErrorLoggable("InterceptionPoint has been unregistered").getMessage());
      }
   }
}
