package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.ejb.FinderException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.utils.Debug;
import weblogic.utils.ErrorCollectionException;

public class EntityHomeInterfaceChecker extends HomeInterfaceChecker {
   private static final String EJB_HOME = "ejbHome";
   private final Class pkClass;
   private final EntityBeanInfo ebi;
   private final boolean isCMP20;

   public EntityHomeInterfaceChecker(Class homeInterface, Class compInterface, Class beanClass, ClientDrivenBeanInfo cbi, Class ejbHomeInterface) {
      super(homeInterface, compInterface, beanClass, cbi, ejbHomeInterface);
      this.ebi = (EntityBeanInfo)cbi;
      this.pkClass = this.ebi.getPrimaryKeyClass();
      this.isCMP20 = this.ebi.getCMPInfo() != null && this.ebi.getCMPInfo().uses20CMP();
   }

   public void checkHomeContainsFindByPK() throws ComplianceException {
      Method fpk = null;
      Method[] var2 = this.homeInterface.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if ("findByPrimaryKey".equals(m.getName())) {
            fpk = m;
            break;
         }
      }

      if (fpk == null) {
         if (this.checkingRemoteClientView()) {
            throw new ComplianceException(this.getFmt().HOME_MUST_HAVE_FIND_PK(this.ejbName));
         } else {
            throw new ComplianceException(this.getFmt().LOCAL_HOME_MUST_HAVE_FIND_PK(this.ejbName));
         }
      } else if (!fpk.getReturnType().equals(this.compInterface)) {
         if (this.checkingRemoteClientView()) {
            throw new ComplianceException(this.getFmt().FIND_BY_PK_RETURNS_REMOTE_INTF(this.ejbName));
         } else {
            throw new ComplianceException(this.getFmt().FIND_BY_PK_RETURNS_LOCAL_INTF(this.ejbName));
         }
      } else {
         Class[] params = fpk.getParameterTypes();
         if (params.length != 1 || !params[0].equals(Object.class) && !params[0].equals(this.pkClass)) {
            if (this.checkingRemoteClientView()) {
               throw new ComplianceException(this.getFmt().HOME_FIND_PK_CORRECT_PARAMETERS(this.ejbName, this.methodSig(fpk)));
            } else {
               throw new ComplianceException(this.getFmt().LOCAL_HOME_FIND_PK_CORRECT_PARAMETERS(this.ejbName, this.methodSig(fpk)));
            }
         }
      }
   }

   public void checkFindThrowsFinderException() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = MethodUtils.getFinderMethodList(this.homeInterface).iterator();

      while(var2.hasNext()) {
         Method f = (Method)var2.next();
         if (!ComplianceUtils.methodThrowsException(f, FinderException.class)) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().FINDER_MUST_THROW_FE(this.ejbName, this.methodSig(f))));
            } else {
               errors.add(new ComplianceException(this.getFmt().LOCAL_FINDER_MUST_THROW_FE(this.ejbName, this.methodSig(f))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private boolean goodFinderReturn(Class r) {
      if (r.equals(this.compInterface)) {
         return true;
      } else if (!this.isCMP20 && Enumeration.class.isAssignableFrom(r)) {
         return true;
      } else {
         return Collection.class.equals(r);
      }
   }

   public void checkFinderReturnsRemoteOrCollection() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = MethodUtils.getFinderMethodList(this.homeInterface).iterator();

      while(var2.hasNext()) {
         Method f = (Method)var2.next();
         if (!this.goodFinderReturn(f.getReturnType())) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().FINDER_RETURNS_BAD_TYPE(this.ejbName, this.methodSig(f))));
            } else {
               errors.add(new ComplianceException(this.getFmt().LOCAL_FINDER_RETURNS_BAD_TYPE(this.ejbName, this.methodSig(f))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private String beanFindName(String fn) {
      Debug.assertion(fn.startsWith("find"));
      return "ejbF" + fn.substring(1);
   }

   public void checkFindMethodsMatchBeanMethods() throws ErrorCollectionException {
      if (this.ebi.getIsBeanManagedPersistence()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Iterator var2 = MethodUtils.getFinderMethodList(this.homeInterface).iterator();

         while(var2.hasNext()) {
            Method fm = (Method)var2.next();

            try {
               Method beanMethod = this.beanClass.getMethod(this.beanFindName(fm.getName()), fm.getParameterTypes());
               ComplianceUtils.exceptionTypesMatch(fm, beanMethod);
               Class beanReturnType = beanMethod.getReturnType();
               Class homeReturnType = fm.getReturnType();
               if (beanReturnType.equals(this.pkClass)) {
                  if (!homeReturnType.equals(this.compInterface)) {
                     if (this.checkingRemoteClientView()) {
                        errors.add(new ComplianceException(this.getFmt().SCALAR_FINDER_DOESNT_RETURN_REMOTE_INTF(this.ejbName, this.methodSig(fm))));
                     } else {
                        errors.add(new ComplianceException(this.getFmt().SCALAR_FINDER_DOESNT_RETURN_LOCAL_INTF(this.ejbName, this.methodSig(fm))));
                     }
                  }
               } else if (beanReturnType.equals(Enumeration.class)) {
                  if (!homeReturnType.equals(Enumeration.class)) {
                     errors.add(new ComplianceException(this.getFmt().ENUM_FINDER_DOESNT_RETURN_REMOTE_INTF(this.ejbName, this.methodSig(fm))));
                  }
               } else if (beanReturnType.equals(Collection.class)) {
                  if (!homeReturnType.equals(Collection.class)) {
                     if (this.checkingRemoteClientView()) {
                        errors.add(new ComplianceException(this.getFmt().COLL_FINDER_DOESNT_RETURN_COLL(this.ejbName, this.methodSig(fm))));
                     } else {
                        errors.add(new ComplianceException(this.getFmt().LOCAL_COLL_FINDER_DOESNT_RETURN_COLL(this.ejbName, this.methodSig(fm))));
                     }
                  }
               } else {
                  errors.add(new ComplianceException(this.getFmt().UNEXPECTED_FINDER_RETURN_TYPE(this.ejbName, this.methodSig(beanMethod))));
               }
            } catch (NoSuchMethodException var7) {
               errors.add(new ComplianceException(this.getFmt().FIND_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(fm))));
            } catch (ExceptionTypeMismatchException var8) {
               if (this.checkingRemoteClientView()) {
                  errors.add(new ComplianceException(this.getFmt().FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(fm))));
               } else {
                  errors.add(new ComplianceException(this.getFmt().LOCAL_FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(fm))));
               }
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void checkCreateMethodsMatchBeanPostCreateMethods() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getCreateMethods().iterator();

      while(var2.hasNext()) {
         Method cm = (Method)var2.next();

         try {
            String postCreateName = "ejbPostC" + cm.getName().substring(1);
            Method beanMethod = this.beanClass.getMethod(postCreateName, cm.getParameterTypes());
            ComplianceUtils.exceptionTypesMatch(cm, beanMethod);
         } catch (NoSuchMethodException var6) {
            errors.add(new ComplianceException(this.getFmt().POST_CREATE_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(cm))));
         } catch (ExceptionTypeMismatchException var7) {
            errors.add(new ComplianceException(this.getFmt().POST_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(cm))));
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkForExtraMethodsInHomeInterface() throws ErrorCollectionException {
      Vector homeMethods = new Vector();
      Vector potentialHomeMethods = new Vector();
      ErrorCollectionException errors = new ErrorCollectionException();
      this.computeBeanHomeMethods(errors, homeMethods);
      Method[] var4 = this.homeInterface.getMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         String name = m.getName();

         try {
            this.ejbHomeInterface.getMethod(name, m.getParameterTypes());
         } catch (NoSuchMethodException var11) {
            boolean isCreator = name.startsWith("create") || name.startsWith("find");
            if (!isCreator && !name.equals("<clinit>")) {
               potentialHomeMethods.addElement(m);
            }
         }
      }

      Enumeration e = potentialHomeMethods.elements();

      while(e.hasMoreElements()) {
         Method m = (Method)e.nextElement();
         if (!homeMethods.contains(m)) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().EXTRA_HOME_METHOD_20(this.ejbName, this.methodSig(m), DDUtils.getEjbHomeMethodSignature(m))));
            } else {
               errors.add(new ComplianceException(this.getFmt().EXTRA_LOCAL_HOME_METHOD_20(this.ejbName, this.methodSig(m), DDUtils.getEjbHomeMethodSignature(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void computeBeanHomeMethods(ErrorCollectionException errors, Vector methodList) {
      Method[] var3 = this.beanClass.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         String n = m.getName();
         if (n.startsWith("ejbHome")) {
            if (n.length() <= "ejbHome".length()) {
               errors.add(new ComplianceException(this.getFmt().HOME_METHOD_NAME_IN_BEAN_CLASS_INCOMPLETE_20(this.beanClass.getName())));
            } else {
               this.beanClassHomeMethodNameCheck(n, errors);
               boolean isValidHomeMethod = false;

               try {
                  isValidHomeMethod = this.isValidHomeMethod(n, m.getParameterTypes(), m.getReturnType());
               } catch (Exception var11) {
                  isValidHomeMethod = true;
                  errors.add(var11);
               }

               if (isValidHomeMethod) {
                  try {
                     String methodName = n.substring("ejbHome".length());
                     methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
                     methodList.addElement(this.homeInterface.getMethod(methodName, m.getParameterTypes()));
                  } catch (NoSuchMethodException var10) {
                     EJBLogger.logStackTrace(var10);
                  }
               }
            }
         }
      }

   }

   private boolean isValidHomeMethod(String ejbHomeMethodName, Class[] parameterTypes, Class returnType) throws Exception {
      boolean homeOk = false;

      try {
         String methodName = ejbHomeMethodName.substring("ejbHome".length());
         methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
         Method homeMethod = this.homeInterface.getMethod(methodName, parameterTypes);
         Class homeMethodReturnType = homeMethod.getReturnType();
         if (!returnType.equals(homeMethodReturnType)) {
            if (this.checkingRemoteClientView()) {
               throw new Exception(this.getFmt().EJB_HOME_METHOD_RETURN_TYPE_SHOULD_MATCH(this.ejbName, this.homeInterface.getName(), methodName, homeMethodReturnType.getName(), this.beanClass.getName(), returnType.getName(), "remote"));
            }

            throw new Exception(this.getFmt().EJB_HOME_METHOD_RETURN_TYPE_SHOULD_MATCH(this.ejbName, this.homeInterface.getName(), methodName, homeMethodReturnType.getName(), this.beanClass.getName(), returnType.getName(), "local"));
         }

         homeOk = true;
      } catch (NoSuchMethodException var8) {
      }

      return homeOk;
   }

   private void beanClassHomeMethodNameCheck(String ejbHomeMethodName, ErrorCollectionException errors) {
      String methodName = ejbHomeMethodName.substring("ejbHome".length());
      if (Character.isLowerCase(methodName.charAt(0))) {
         StringBuilder newName = new StringBuilder("ejbHome");
         newName.append(Character.toUpperCase(methodName.charAt(0)));
         if (methodName.length() > 1) {
            newName.append(methodName.substring(1));
         }

         errors.add(new ComplianceException(this.getFmt().HOME_METHOD_NAME_IN_BEAN_CLASS_LOWER_CASE_20(this.beanClass.getName(), ejbHomeMethodName, newName.toString())));
      }

   }
}
