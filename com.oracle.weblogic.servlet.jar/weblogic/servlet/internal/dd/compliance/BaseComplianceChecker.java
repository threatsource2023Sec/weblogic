package weblogic.servlet.internal.dd.compliance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public abstract class BaseComplianceChecker implements ComplianceChecker {
   private static final boolean debug = false;
   protected ArrayList errorList;
   protected WebAppComplianceTextFormatter fmt = new WebAppComplianceTextFormatter();
   protected boolean verbose = false;

   public void update(String progress) {
      if (this.verbose) {
         HTTPLogger.logInfo("ComplianceChecker", progress);
      }

   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void addDescriptorError(ComplianceException ex) {
      if (this.errorList == null) {
         this.errorList = new ArrayList();
      }

      if (ex != null) {
         this.errorList.add(ex);
      }

   }

   public void addDescriptorError(String err, DescriptorErrorInfo errInfo) {
      if (this.errorList == null) {
         this.errorList = new ArrayList();
      }

      if (errInfo == null) {
         this.errorList.add(new ComplianceException(err));
      } else {
         this.errorList.add(new ComplianceException(err, errInfo));
      }

   }

   public void addDescriptorError(String err) {
      this.addDescriptorError(err, (DescriptorErrorInfo)null);
   }

   public List getDescriptorErrorsAsList() {
      return this.errorList;
   }

   public boolean hasErrors() {
      return this.errorList != null && !this.errorList.isEmpty();
   }

   public void checkForExceptions() throws ErrorCollectionException {
      if (this.hasErrors()) {
         ErrorCollectionException error = new ErrorCollectionException();
         List errs = this.getDescriptorErrorsAsList();
         Iterator I = errs.iterator();

         while(I.hasNext()) {
            error.add((ComplianceException)I.next());
         }

         errs.clear();
         throw error;
      }
   }

   public abstract void check(DeploymentInfo var1) throws ErrorCollectionException;

   protected boolean isClassAssignable(ClassLoader cl, String element, String classType, String superClassOrInterface) {
      Class clazz = null;
      Class superClazz = null;

      try {
         clazz = cl.loadClass(classType);
      } catch (ClassNotFoundException var8) {
         this.update(this.fmt.warning() + this.fmt.CLASS_NOT_FOUND(element, classType));
      } catch (NoClassDefFoundError var9) {
         this.update(this.fmt.warning() + " Error while loading class : " + classType + var9.getMessage());
      } catch (Exception var10) {
         this.update(this.fmt.warning() + this.fmt.CLASS_NOT_FOUND(element, classType) + StackTraceUtils.throwable2StackTrace(var10));
      }

      if (clazz == null) {
         return false;
      } else {
         try {
            superClazz = cl.loadClass(superClassOrInterface);
         } catch (ClassNotFoundException var11) {
            this.update(this.fmt.warning() + " Unable to load class '" + superClassOrInterface + "' " + (cl instanceof GenericClassLoader ? " from the following classpath :" + ((GenericClassLoader)cl).getClassPath() : ""));
            return false;
         } catch (Exception var12) {
            return false;
         }

         if (!superClazz.isAssignableFrom(clazz)) {
            this.addDescriptorError(this.fmt.CLASS_NOT_ASSIGNABLE_FROM(superClassOrInterface, element, classType));
            return false;
         } else {
            return true;
         }
      }
   }

   public static ComplianceChecker[] makeComplianceCheckers(DeploymentInfo info) {
      ArrayList checks = new ArrayList();
      WebAppBean web = info.getWebAppBean();
      if (web != null) {
         checks.add(new WebAppDescriptorComplianceChecker());
         ServletMappingBean[] maps = web.getServletMappings();
         ServletBean[] servlets = web.getServlets();
         if (maps != null || servlets != null) {
            checks.add(new ServletComplianceChecker());
         }

         FilterBean[] filters = web.getFilters();
         FilterMappingBean[] fmapps = web.getFilterMappings();
         if (filters != null || fmapps != null) {
            checks.add(new FilterComplianceChecker());
         }

         SecurityConstraintBean[] sec = web.getSecurityConstraints();
         if (sec != null) {
            checks.add(new SecurityConstraintComplianceChecker());
         }

         if (web.getEjbRefs() != null || web.getEjbLocalRefs() != null) {
            checks.add(new EJBRefsComplianceChecker());
         }

         if (web.getEnvEntries() != null && web.getEnvEntries().length > 0) {
            checks.add(new EnvEntryComplianceChecker());
         }
      }

      WeblogicWebAppBean wlBean = info.getWeblogicWebAppBean();
      if (wlBean != null) {
         checks.add(new WebLogicWebAppComplianceChecker());
      }

      if (checks.isEmpty()) {
         HTTPLogger.logInfo("ComplianceChecker", "Could not find elements to be checked for compliance, skipping compliance check");
      }

      return (ComplianceChecker[])((ComplianceChecker[])checks.toArray(new ComplianceChecker[checks.size()]));
   }
}
