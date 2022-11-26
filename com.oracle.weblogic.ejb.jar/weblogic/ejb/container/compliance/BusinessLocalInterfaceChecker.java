package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBLocalObject;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.utils.MethodUtils;

public final class BusinessLocalInterfaceChecker extends BaseComplianceChecker {
   private final SessionBeanInfo sbi;

   public BusinessLocalInterfaceChecker(SessionBeanInfo sbi) {
      this.sbi = sbi;
   }

   public void checkLBIIsNotRBI() throws ComplianceException {
      Iterator var1 = this.sbi.getBusinessLocals().iterator();

      Class iface;
      do {
         if (!var1.hasNext()) {
            return;
         }

         iface = (Class)var1.next();
      } while(!this.sbi.getBusinessRemotes().contains(iface));

      throw new ComplianceException(this.getFmt().LOCAL_INTERFACE_CANNOT_REMOTE(iface.getName()));
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

      Iterator var14 = this.sbi.getBusinessLocals().iterator();

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

               throw new ComplianceException(this.getFmt().LOCAL_INTERFACE_NOT_FOUND_IN_BEAN(intfMethodSig, iface.getName(), this.sbi.getBeanClass().toString()));
            }
         }
      }

   }

   public void checkBIExtendsEJBLocalObject() throws ComplianceException {
      Iterator var1 = this.sbi.getBusinessLocals().iterator();

      Class iface;
      do {
         if (!var1.hasNext()) {
            return;
         }

         iface = (Class)var1.next();
      } while(!EJBLocalObject.class.isAssignableFrom(iface));

      throw new ComplianceException(this.getFmt().LOCAL_INTERFACE_CANNOT_EXTEND_EJBLocalObject(iface.toString()));
   }
}
