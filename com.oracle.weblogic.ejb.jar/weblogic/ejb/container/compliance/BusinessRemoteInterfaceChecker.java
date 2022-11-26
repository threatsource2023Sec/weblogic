package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBObject;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.utils.MethodUtils;

public final class BusinessRemoteInterfaceChecker extends BaseComplianceChecker {
   private final SessionBeanInfo sbi;

   public BusinessRemoteInterfaceChecker(SessionBeanInfo sbi) {
      this.sbi = sbi;
   }

   public void checkRBIIsNotLBI() throws ComplianceException {
      Iterator var1 = this.sbi.getBusinessRemotes().iterator();

      Class iface;
      do {
         if (!var1.hasNext()) {
            return;
         }

         iface = (Class)var1.next();
      } while(!this.sbi.getBusinessLocals().contains(iface));

      throw new ComplianceException(this.getFmt().REMOTE_INTERFACE_IS_LOCAL(iface.toString()));
   }

   public void checkBIMethodsMatchBeanMethods() throws ComplianceException {
      Method[] beanMethods = this.sbi.getBeanClass().getMethods();
      Set beanMethodSigs = new HashSet(beanMethods.length);
      Method[] var3 = beanMethods;
      int var4 = beanMethods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         beanMethodSigs.add(DDUtils.getMethodSignature(m));
      }

      Iterator var14 = this.sbi.getBusinessRemotes().iterator();

      while(var14.hasNext()) {
         Class iface = (Class)var14.next();
         Method[] var16 = iface.getMethods();
         int var17 = var16.length;

         for(int var7 = 0; var7 < var17; ++var7) {
            Method m = var16[var7];
            String intfMethodSig = DDUtils.getMethodSignature(m);
            if (!beanMethodSigs.contains(intfMethodSig)) {
               Method[] var10 = beanMethods;
               int var11 = beanMethods.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  Method bm = var10[var12];
                  if (MethodUtils.potentialBridgeCandidate(m, bm)) {
                     throw new ComplianceException(this.getFmt().EJB_MAY_BE_MISSING_BRIDGE_METHOD(intfMethodSig, iface.getName()));
                  }
               }

               throw new ComplianceException(this.getFmt().REMOTE_METHOD_NOT_FOUND_IN_BEAN(intfMethodSig, iface.getName(), this.sbi.getBeanClass().toString()));
            }
         }
      }

   }

   public void checkBIExtendsEJBObject() throws ComplianceException {
      Iterator var1 = this.sbi.getBusinessRemotes().iterator();

      Class iface;
      do {
         if (!var1.hasNext()) {
            return;
         }

         iface = (Class)var1.next();
      } while(!EJBObject.class.isAssignableFrom(iface));

      throw new ComplianceException(this.getFmt().REMOTE_INTERFACE_EXTEND_EJBOBJECT(iface.toString()));
   }

   public void checkBIMethodsThrowRemoteException() throws ComplianceException {
      Iterator var1 = this.sbi.getBusinessRemotes().iterator();

      while(true) {
         while(var1.hasNext()) {
            Class iface = (Class)var1.next();
            Method[] var3;
            int var4;
            int var5;
            Method m;
            if (!Remote.class.isAssignableFrom(iface)) {
               var3 = iface.getMethods();
               var4 = var3.length;

               for(var5 = 0; var5 < var4; ++var5) {
                  m = var3[var5];
                  Class[] var13 = m.getExceptionTypes();
                  int var14 = var13.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     Class e = var13[var15];
                     if (RemoteException.class == e) {
                        throw new ComplianceException(this.getFmt().REMOTE_BUSINESS_INTERFACE_THROW_REMOTEEXCEPTION(m.toString(), iface.toString()));
                     }
                  }
               }
            } else {
               var3 = iface.getMethods();
               var4 = var3.length;

               for(var5 = 0; var5 < var4; ++var5) {
                  m = var3[var5];
                  boolean containsRemoteException = false;
                  Class[] exceptions = m.getExceptionTypes();
                  if (exceptions != null && exceptions.length != 0) {
                     Class[] var9 = exceptions;
                     int var10 = exceptions.length;

                     for(int var11 = 0; var11 < var10; ++var11) {
                        Class e = var9[var11];
                        if (RemoteException.class == e) {
                           containsRemoteException = true;
                           break;
                        }
                     }
                  }

                  if (!containsRemoteException) {
                     throw new ComplianceException(this.getFmt().REMOTE_INTERFACE_NOT_THROW_REMOTEEXCEPTION(m.toString(), iface.toString()));
                  }
               }
            }
         }

         return;
      }
   }
}
