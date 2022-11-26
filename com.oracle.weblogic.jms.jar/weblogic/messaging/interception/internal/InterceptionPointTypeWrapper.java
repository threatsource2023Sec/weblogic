package weblogic.messaging.interception.internal;

import java.util.HashMap;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.AssociationListener;
import weblogic.messaging.interception.interfaces.InterceptionPointNameDescriptor;

class InterceptionPointTypeWrapper {
   private HashMap interceptionPointMap = new HashMap(0);
   private String name = null;
   private InterceptionPointNameDescriptor[] ipnd = null;
   private AssociationListener listener = null;

   InterceptionPointTypeWrapper(String name, InterceptionPointNameDescriptor[] ipnd, AssociationListener listener) {
      this.name = name;
      this.ipnd = ipnd;
      this.listener = listener;
   }

   String getName() {
      return this.name;
   }

   InterceptionPointNameDescriptor[] getIPND() {
      return this.ipnd;
   }

   AssociationListener getAssociationListener() {
      return this.listener;
   }

   void validate(String[] interceptionPointName) throws InterceptionServiceException {
      if (this.ipnd.length != interceptionPointName.length) {
         throw new InterceptionServiceException(MIExceptionLogger.logInvalidInterceptionPointNameLoggable("Invalid InterceptionPointName - different length").getMessage());
      } else {
         for(int i = 0; i < this.ipnd.length; ++i) {
            if (!this.ipnd[i].isValid(interceptionPointName[i])) {
               throw new InterceptionServiceException(MIExceptionLogger.logInvalidInterceptionPointNameLoggable("Invalid InterceptionPointName - invalid name").getMessage());
            }
         }

      }
   }

   synchronized InterceptionPoint findOrCreateInterceptionPoint(String[] ipn) {
      String internalIPName = InterceptionPoint.createInternalName(this.name, ipn);
      InterceptionPoint ip = (InterceptionPoint)this.interceptionPointMap.get(internalIPName);
      if (ip == null) {
         ip = new InterceptionPoint(this.name, ipn, internalIPName, this);
         this.interceptionPointMap.put(internalIPName, ip);
      }

      return ip;
   }

   synchronized InterceptionPoint findInterceptionPoint(String[] ipn) {
      String internalIPName = InterceptionPoint.createInternalName(this.name, ipn);
      return (InterceptionPoint)this.interceptionPointMap.get(internalIPName);
   }

   synchronized void removeIP(String name) {
      this.interceptionPointMap.remove(name);
   }

   synchronized int getIPsSize() {
      return this.interceptionPointMap.keySet().size();
   }
}
