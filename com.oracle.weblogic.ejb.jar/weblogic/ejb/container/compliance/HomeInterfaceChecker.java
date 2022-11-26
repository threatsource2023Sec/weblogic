package weblogic.ejb.container.compliance;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.utils.ErrorCollectionException;

abstract class HomeInterfaceChecker extends BaseComplianceChecker {
   protected final String ejbName;
   protected final Class homeInterface;
   protected final Class compInterface;
   protected final Class beanClass;
   protected final Class ejbHomeInterface;
   private final boolean isEntityBean;
   private final ClientDrivenBeanInfo cbi;

   HomeInterfaceChecker(Class homeInterface, Class compInterface, Class beanClass, ClientDrivenBeanInfo cbi, Class ejbHomeInterface) {
      this.homeInterface = homeInterface;
      this.compInterface = compInterface;
      this.beanClass = beanClass;
      this.isEntityBean = cbi.isEntityBean();
      this.ejbName = cbi.getEJBName();
      this.ejbHomeInterface = ejbHomeInterface;
      this.cbi = cbi;
   }

   protected String section(String session, String entity) {
      return this.isEntityBean ? entity : session;
   }

   protected List getCreateMethods() {
      List creates = new ArrayList();
      Method[] var2 = this.homeInterface.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("create")) {
            creates.add(m);
         }
      }

      return creates;
   }

   protected List getHomeMethods() {
      List result = new ArrayList();
      Method[] var2 = this.beanClass.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (m.getName().startsWith("ejbHome")) {
            result.add(m);
         }
      }

      return result;
   }

   protected List getHomeInterfaceHomeMethods() {
      List result = new ArrayList();
      Method[] var2 = this.homeInterface.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         String name = m.getName();
         boolean isCreatorOrFinder = name.startsWith("create") || name.startsWith("find");
         boolean isEJBHomeClassMethod = m.getDeclaringClass().equals(EJBHome.class);
         boolean isCLInit = "<clinit>".equals(name);
         if (!isCreatorOrFinder && !isEJBHomeClassMethod && !isCLInit) {
            result.add(m);
         }
      }

      return result;
   }

   protected boolean checkingRemoteClientView() {
      return this.ejbHomeInterface.equals(EJBHome.class);
   }

   public void checkExtendsEJBHome() throws ComplianceException {
      if (!this.ejbHomeInterface.isAssignableFrom(this.homeInterface)) {
         if (this.checkingRemoteClientView()) {
            throw new ComplianceException(this.getFmt().HOME_EXTENDS_EJBHOME(this.ejbName, this.homeInterface.getName()));
         } else {
            throw new ComplianceException(this.getFmt().LOCAL_HOME_EXTENDS_EJBLOCALHOME(this.ejbName, this.homeInterface.getName()));
         }
      }
   }

   public void checkRMIIIOPTypes() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      if (this.checkingRemoteClientView()) {
         Method[] var2 = this.homeInterface.getDeclaredMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            if (!ComplianceUtils.isLegalRMIIIOPType(m.getReturnType())) {
               errors.add(new ComplianceException(this.getFmt().NOT_RMIIIOP_LEGAL_TYPE_20(this.ejbName)));
            }

            Class[] var6 = m.getParameterTypes();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Class param = var6[var8];
               if (!ComplianceUtils.isLegalRMIIIOPType(param)) {
                  errors.add(new ComplianceException(this.getFmt().NOT_RMIIIOP_LEGAL_TYPE_20(this.ejbName)));
               }
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkMethodsThrowRemoteException() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Method[] var2 = this.homeInterface.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (!m.getName().equals("<clinit>")) {
            if (this.checkingRemoteClientView()) {
               if (!ComplianceUtils.methodThrowsException(m, RemoteException.class)) {
                  errors.add(new ComplianceException(this.getFmt().HOME_METHOD_NOT_THROW_REMOTE_EXCEPTION(this.ejbName, this.methodSig(m))));
               }
            } else if (ComplianceUtils.methodThrowsException(m, RemoteException.class)) {
               errors.add(new ComplianceException(this.getFmt().LOCAL_HOME_METHOD_THROW_REMOTE_EXCEPTION(this.ejbName, this.methodSig(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkApplicationExceptions() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getHomeInterfaceHomeMethods().iterator();

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
               errors.add(new ComplianceException(this.getFmt().INVALID_APPLICATION_EXCEPTION_ON_HOME(this.ejbName, this.methodSig(m), m.getDeclaringClass().getName(), exceptionType.getName())));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkCreateReturnsCompInterface() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getCreateMethods().iterator();

      while(var2.hasNext()) {
         Method m = (Method)var2.next();
         if (!m.getReturnType().equals(this.compInterface)) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().CREATE_METHOD_RETURNS_COMPONENT_INTERFACE(this.ejbName, this.methodSig(m))));
            } else {
               errors.add(new ComplianceException(this.getFmt().CREATE_METHOD_RETURNS_LOCAL_COMPONENT_INTERFACE(this.ejbName, this.methodSig(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkCreateThrowsCreateException() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.getCreateMethods().iterator();

      while(var2.hasNext()) {
         Method m = (Method)var2.next();
         if (!ComplianceUtils.methodThrowsException(m, CreateException.class)) {
            if (this.checkingRemoteClientView()) {
               errors.add(new ComplianceException(this.getFmt().CREATE_METHOD_THROWS_CREATE_EXCEPTION(this.ejbName, this.methodSig(m))));
            } else {
               errors.add(new ComplianceException(this.getFmt().LOCAL_CREATE_METHOD_THROWS_CREATE_EXCEPTION(this.ejbName, this.methodSig(m))));
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void checkCreateMethodsMatchBeanCreates() throws ErrorCollectionException {
      if (!this.cbi.isEJB30() || this.cbi.isSessionBean() && ((SessionBeanInfo)this.cbi).isStateful()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Iterator var2 = this.getCreateMethods().iterator();

         while(var2.hasNext()) {
            Method cm = (Method)var2.next();
            String homeCreateName = cm.getName();
            String ejbCreateName;
            if (this.cbi.isSessionBean()) {
               ejbCreateName = ((SessionBeanInfo)this.cbi).getEjbCreateInitMethodName(cm);
            } else {
               ejbCreateName = "ejbC" + homeCreateName.substring(1, homeCreateName.length());
            }

            try {
               Method beanMethod = this.beanClass.getMethod(ejbCreateName, cm.getParameterTypes());
               ComplianceUtils.exceptionTypesMatch(cm, beanMethod);
            } catch (NoSuchMethodException var7) {
               if (this.checkingRemoteClientView()) {
                  errors.add(new ComplianceException(this.getFmt().CREATE_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(ejbCreateName, cm.getParameterTypes()))));
               } else {
                  errors.add(new ComplianceException(this.getFmt().LOCAL_CREATE_METHOD_DOESNT_EXIST_IN_BEAN(this.ejbName, this.methodSig(ejbCreateName, cm.getParameterTypes()))));
               }
            } catch (ExceptionTypeMismatchException var8) {
               if (this.checkingRemoteClientView()) {
                  errors.add(new ComplianceException(this.getFmt().CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(ejbCreateName, cm.getParameterTypes()))));
               } else {
                  errors.add(new ComplianceException(this.getFmt().LOCAL_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(this.ejbName, this.methodSig(ejbCreateName, cm.getParameterTypes()))));
               }
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }

   public void checkLocalExposeThroughRemote() throws ErrorCollectionException {
      if (this.checkingRemoteClientView()) {
         ErrorCollectionException errors = new ErrorCollectionException();
         Method[] var2 = this.homeInterface.getMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            if (ComplianceUtils.localExposeThroughRemote(m)) {
               errors.add(new ComplianceException(this.getFmt().LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_HOME_INTERFACE(this.ejbName, this.methodSig(m))));
            }
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      }
   }
}
