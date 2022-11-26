package weblogic.messaging.interception.internal;

import java.util.HashMap;
import java.util.Iterator;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;

class AssociationManager {
   private static HashMap associationMap = new HashMap(0);

   static synchronized Association createAssociation(InterceptionPoint ip, ProcessorWrapper pw, boolean activated, int depth) throws InterceptionServiceException {
      Association a = (Association)associationMap.get(ip.getInternalName());
      if (a != null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationAlreadyExistErrorLoggable("Association exists").getMessage());
      } else {
         a = new Association(ip, pw, activated, depth);
         ip.addAssociation(a);
         pw.addAssociation(a);
         associationMap.put(a.getInternalName(), a);
         return a;
      }
   }

   static void removeAssociation(Association association) throws InterceptionServiceException {
      String name = association.getInternalName();
      ProcessorWrapper pw = association.getProcessorWrapper();
      association.remove();
      pw.removeProcessorWrapperIfNotUsed();
      Class var3 = AssociationManager.class;
      synchronized(AssociationManager.class) {
         associationMap.remove(name);
      }
   }

   static Iterator getAssociations() {
      HashMap map = null;
      Class var1 = AssociationManager.class;
      synchronized(AssociationManager.class) {
         map = (HashMap)associationMap.clone();
      }

      return map.values().iterator();
   }

   public static int getAssociationsSize(String name) {
      if (name == null) {
         return associationMap.keySet().size();
      } else {
         Iterator itr = associationMap.keySet().iterator();
         int i = 0;

         while(itr.hasNext()) {
            String myname = (String)itr.next();
            if (!myname.startsWith(name.length() + " " + name)) {
               ++i;
            }
         }

         return i;
      }
   }
}
