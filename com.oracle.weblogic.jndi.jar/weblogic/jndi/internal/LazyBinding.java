package weblogic.jndi.internal;

import java.rmi.RemoteException;
import javax.naming.Binding;

class LazyBinding extends Binding {
   Object copiedObj = null;

   public LazyBinding(String name, Object obj) {
      super(name, obj);
   }

   public Object getObject() {
      if (this.copiedObj == null) {
         try {
            this.copiedObj = JNDIEnvironment.getJNDIEnvironment().copyObject(super.getObject());
         } catch (RemoteException var2) {
         }
      }

      return this.copiedObj;
   }

   public Object getRawObject() {
      return super.getObject();
   }
}
