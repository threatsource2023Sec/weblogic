package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.annotation.BeaSynthetic.Helper;

public class EJBObjectClassChecker extends BaseComplianceChecker {
   protected final Class eoClass;
   protected final Class beanClass;
   protected final Class ejbObjectInterface;
   protected final String ejbName;
   protected final List businessMethods;
   protected Class remoteInterfaceClass;
   private final Method[] beanMethods;

   public EJBObjectClassChecker(Class eoClass, ClientDrivenBeanInfo bi, Class ejbObjectInterface) {
      this.eoClass = eoClass;
      this.beanClass = bi.getBeanClass();
      this.ejbName = bi.getEJBName();
      this.ejbObjectInterface = ejbObjectInterface;
      this.businessMethods = this.getBusinessMethods();
      this.beanMethods = this.beanClass.getMethods();
   }

   protected boolean checkingRemoteClientView() {
      return this.ejbObjectInterface.equals(EJBObject.class);
   }

   private List getBusinessMethods() {
      Map methodMap = new HashMap();
      List bm = new ArrayList();
      Method[] var3 = this.eoClass.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (!Helper.isBeaSyntheticMethod(m)) {
            String methodName = m.getName();

            try {
               this.ejbObjectInterface.getMethod(methodName, m.getParameterTypes());
            } catch (NoSuchMethodException var16) {
               String methodSig = DDUtils.getMethodSignature(m);
               Method oldMethod = (Method)methodMap.get(methodSig);
               if (oldMethod != null) {
                  Class oldDeclaringClass = oldMethod.getDeclaringClass();
                  Class thisDeclaringClass = m.getDeclaringClass();
                  if (oldDeclaringClass.isAssignableFrom(thisDeclaringClass)) {
                     try {
                        oldDeclaringClass.getMethod(methodName, m.getParameterTypes());
                        bm.remove(oldMethod);
                     } catch (NoSuchMethodException var14) {
                     }
                  } else if (thisDeclaringClass.isAssignableFrom(oldDeclaringClass)) {
                     try {
                        oldDeclaringClass.getMethod(methodName, m.getParameterTypes());
                        if (oldDeclaringClass == thisDeclaringClass) {
                           continue;
                        }
                     } catch (NoSuchMethodException var15) {
                     }
                  }
               }

               if (!methodName.equals("<clinit>")) {
                  bm.add(m);
                  methodMap.put(methodSig, m);
               }
            }
         }
      }

      return bm;
   }

   public void checkEoExtendsEJBObject() throws ComplianceException {
      if (!this.ejbObjectInterface.isAssignableFrom(this.eoClass)) {
         if (this.checkingRemoteClientView()) {
            throw new ComplianceException(this.getFmt().EO_IMPLEMENTS_EJBOBJECT(this.ejbName));
         } else {
            throw new ComplianceException(this.getFmt().ELO_IMPLEMENTS_EJB_LOCAL_OBJECT(this.ejbName));
         }
      }
   }

   public void checkMethodsThrowRemoteException() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.businessMethods.iterator();

      while(var2.hasNext()) {
         Method m = (Method)var2.next();
         boolean throwsRE;
         if (this.checkingRemoteClientView()) {
            throwsRE = ComplianceUtils.methodThrowsException(m, RemoteException.class);
            if (!throwsRE) {
               errors.add(new ComplianceException(this.getFmt().EO_THROWS_REMOTE_EXCEPTION(this.ejbName, this.methodSig(m))));
            }
         } else {
            throwsRE = ComplianceUtils.methodThrowsExactlyException(m, RemoteException.class);
            if (throwsRE) {
               errors.add(new ComplianceException(this.getFmt().ELO_THROWS_REMOTE_EXCEPTION(this.ejbName, this.methodSig(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkApplicationExceptions() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.businessMethods.iterator();

      while(var2.hasNext()) {
         Method m = (Method)var2.next();
         Class[] var4 = m.getExceptionTypes();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class exceptionType = var4[var6];
            boolean ok = false;
            if (this.checkingRemoteClientView()) {
               ok = ComplianceUtils.checkApplicationException(exceptionType, RemoteException.class);
            } else {
               ok = ComplianceUtils.checkApplicationException(exceptionType, EJBException.class);
            }

            if (!ok) {
               errors.add(new ComplianceException(this.getFmt().INVALID_APPLICATION_EXCEPTION(this.ejbName, this.methodSig(m), m.getDeclaringClass().getName(), exceptionType.getName())));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private List getMatchingBeanMethodsWithName(String busName) {
      List bm = new ArrayList();
      Method[] var3 = this.beanMethods;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (m.getName().equals(busName)) {
            bm.add(m);
         }
      }

      return bm;
   }

   public void checkInterfaceBusinessMethodsMatchBeanMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.businessMethods.iterator();

      while(var2.hasNext()) {
         Method eoMethod = (Method)var2.next();
         String eoMethodName = eoMethod.getName();
         Method beanMethod = null;

         try {
            boolean sigMismatch = true;
            boolean foundNameMatchedMethods = false;
            Iterator var8 = this.getMatchingBeanMethodsWithName(eoMethod.getName()).iterator();

            while(var8.hasNext()) {
               Method m = (Method)var8.next();
               foundNameMatchedMethods = true;
               boolean currentMethodMatched = true;
               beanMethod = m;
               Class[] beanMethodParams = m.getParameterTypes();
               Class[] bmParams = eoMethod.getParameterTypes();
               if (beanMethodParams.length != bmParams.length) {
                  currentMethodMatched = false;
               } else {
                  for(int k = 0; k < bmParams.length; ++k) {
                     if (!beanMethodParams[k].equals(bmParams[k])) {
                        currentMethodMatched = false;
                        break;
                     }
                  }
               }

               if (!ComplianceUtils.returnTypesMatch(eoMethod, m)) {
                  currentMethodMatched = false;
               }

               if (currentMethodMatched) {
                  sigMismatch = false;
                  break;
               }
            }

            if (!foundNameMatchedMethods) {
               beanMethod = this.beanClass.getMethod(eoMethodName, eoMethod.getParameterTypes());
            }

            if (foundNameMatchedMethods && sigMismatch) {
               if (this.checkingRemoteClientView()) {
                  errors.add(new ComplianceException(this.getFmt().EO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
               } else {
                  errors.add(new ComplianceException(this.getFmt().ELO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
               }
            }

            if (!ComplianceUtils.returnTypesMatch(eoMethod, beanMethod)) {
               if (this.checkingRemoteClientView()) {
                  errors.add(new ComplianceException(this.getFmt().EO_RETURN_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
               } else {
                  errors.add(new ComplianceException(this.getFmt().ELO_RETURN_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
               }
            }

            try {
               ComplianceUtils.exceptionTypesMatch(eoMethod, beanMethod);
            } catch (ExceptionTypeMismatchException var14) {
               throw var14;
            }
         } catch (NoSuchMethodException var15) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().EO_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(eoMethod))));
            } else {
               errors.add(new ComplianceException(this.getFmt().ELO_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(eoMethod))));
            }
         } catch (ExceptionTypeMismatchException var16) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().EO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
            } else {
               errors.add(new ComplianceException(this.getFmt().ELO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(eoMethod))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkLocalExposeThroughRemote() throws ErrorCollectionException {
      if (this.checkingRemoteClientView()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Method[] var2 = this.eoClass.getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            if (ComplianceUtils.localExposeThroughRemote(m)) {
               errors.add(new ComplianceException(this.getFmt().LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_REMOTE_INTERFACE(this.ejbName, this.methodSig(m))));
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }
}
