package weblogic.connector.deploy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.RAInfo;

class ConnectorModuleChangePackage {
   private ArrayList pendingChanges = new ArrayList();

   protected void prepare(RAInfo Info) throws RAException {
      int index = 0;

      try {
         for(int length = this.pendingChanges.size(); index < length; ++index) {
            ChangePackage changePackage = (ChangePackage)this.pendingChanges.get(index);
            changePackage.prepare();
         }

      } catch (RAException var7) {
         for(int i = 0; i <= index; ++i) {
            try {
               ChangePackage changePackage = (ChangePackage)this.pendingChanges.get(i);
               changePackage.rollback();
            } catch (RAException var6) {
            }
         }

         throw var7;
      }
   }

   protected void activate() throws RAException {
      RAException activateException = null;
      Iterator var2 = this.pendingChanges.iterator();

      while(var2.hasNext()) {
         ChangePackage changePackage = (ChangePackage)var2.next();

         try {
            changePackage.activate();
         } catch (RAException var5) {
            if (activateException == null) {
               activateException = new RAException();
            }

            activateException.add(var5);
         }
      }

      if (activateException != null) {
         throw activateException;
      }
   }

   protected void addChange(ChangePackage changePkg) {
      if (changePkg != null) {
         this.pendingChanges.add(changePkg);
      }

   }

   protected void addChanges(List changes) {
      this.pendingChanges.addAll(changes);
   }

   public static enum ChangeType {
      NEW,
      REMOVE,
      UPDATE;
   }
}
