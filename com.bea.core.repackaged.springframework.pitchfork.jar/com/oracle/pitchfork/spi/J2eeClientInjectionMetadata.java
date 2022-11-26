package com.oracle.pitchfork.spi;

import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import org.omg.CORBA.ORB;

class J2eeClientInjectionMetadata extends Jsr250Metadata {
   J2eeClientInjectionMetadata(DeploymentUnitMetadataI dum, String name, Class componentClass) {
      super(dum, name, componentClass);
   }

   public Object resolve(InjectionI injection) {
      if (injection.getValue() == null) {
         try {
            Class type = this.getClassForType(injection.getType().getName());
            if (type == UserTransaction.class) {
               this.cacheInjectionValue("java:comp/UserTransaction", injection);
            } else {
               if (type != ORB.class) {
                  return super.resolve(injection);
               }

               this.cacheInjectionValue("java:comp/ORB", injection);
            }
         } catch (ClassNotFoundException var3) {
            throw new AssertionError(var3);
         } catch (NamingException var4) {
            throw new AssertionError(var4);
         }
      }

      return injection.getValue();
   }
}
