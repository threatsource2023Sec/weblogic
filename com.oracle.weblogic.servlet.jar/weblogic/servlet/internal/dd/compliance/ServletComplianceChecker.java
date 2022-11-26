package weblogic.servlet.internal.dd.compliance;

import java.util.HashSet;
import java.util.Set;
import weblogic.j2ee.descriptor.RunAsBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.utils.ErrorCollectionException;

public class ServletComplianceChecker extends BaseComplianceChecker {
   private static final boolean debug = false;
   private static final String SUPER_CLASS = "javax.servlet.Servlet";

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      Set servletNames = null;
      ServletMappingBean[] smap = info.getWebAppBean().getServletMappings();
      ServletBean[] servlets = info.getWebAppBean().getServlets();
      if (servlets != null || smap != null) {
         int i;
         if (servlets != null) {
            if (servlets.length > 1) {
               servletNames = new HashSet(servlets.length);
            }

            for(i = 0; i < servlets.length; ++i) {
               this.checkServlet(info, servlets[i], info.getClassLoader());
               if (servletNames != null && !servletNames.add(servlets[i].getServletName())) {
                  this.addDescriptorError(this.fmt.DUPLICATE_SERVLET_DEF(servlets[i].getServletName()));
               }
            }

            this.checkForExceptions();
         }

         if (smap != null) {
            for(i = 0; i < smap.length; ++i) {
               this.checkServletMapping(servlets, smap[i]);
            }

            this.checkForExceptions();
         }

      }
   }

   private void checkServlet(DeploymentInfo info, ServletBean servlet, ClassLoader cl) throws ErrorCollectionException {
      if (servlet != null) {
         String s = servlet.getServletName();
         if (s == null || s.length() == 0) {
            this.addDescriptorError(this.fmt.NO_SERVLET_NAME());
         }

         this.checkForExceptions();
         this.update(this.fmt.CHECKING_SERVLET(servlet.getServletName()));
         String clazz = servlet.getServletClass();
         String jsp = servlet.getJspFile();
         if (clazz != null && clazz.length() > 0 && jsp != null && jsp.length() > 0) {
            this.addDescriptorError(this.fmt.MULTIPLE_DEFINES_SERVLET_DEF(servlet.getServletName()), new DescriptorErrorInfo(new String[]{"<servlet>"}, servlet.getServletName(), new Object[]{"<jsp-file>", "<servlet-class>"}));
         }

         if ((clazz == null || clazz.length() == 0) && (jsp == null || jsp.length() == 0)) {
            this.addDescriptorError(this.fmt.NO_SERVLET_DEF(servlet.getServletName()), new DescriptorErrorInfo("<servlet-name>", servlet.getServletName(), "<servlet-class>"));
         }

         if (clazz != null && cl != null && !info.isWebServiceModule()) {
            boolean isWebService = false;

            try {
               Class checkClass = cl.loadClass(clazz);
               isWebService = this.hasWebServiceAnnotations(checkClass);
            } catch (ClassNotFoundException var13) {
            } catch (NoClassDefFoundError var14) {
            } catch (Exception var15) {
            }

            if (!isWebService && !this.isClassAssignable(cl, "servlet-class", clazz, "javax.servlet.Servlet")) {
               this.checkForExceptions();
            }
         }

         RunAsBean runAs = servlet.getRunAs();
         if (runAs != null) {
            String roleName = runAs.getRoleName();
            SecurityRoleBean[] sr = info.getWebAppBean().getSecurityRoles();
            boolean foundKey = false;
            if (sr != null) {
               for(int i = 0; i < sr.length; ++i) {
                  String role = sr[i].getRoleName();
                  if (role.equals(roleName)) {
                     foundKey = true;
                     break;
                  }
               }
            }

            if (!foundKey) {
               this.addDescriptorError(this.fmt.NO_SECURITY_ROLE_FOR_RUNAS(servlet.getServletName(), roleName), new DescriptorErrorInfo("<servlet>", servlet.getServletName(), "<run-as>"));
            }
         }

      }
   }

   private boolean hasWebServiceAnnotations(Class checkClass) throws ClassNotFoundException {
      return checkClass.isAnnotationPresent(Class.forName("javax.jws.WebService")) || checkClass.isAnnotationPresent(Class.forName("javax.xml.ws.WebServiceProvider"));
   }

   private void checkServletMapping(ServletBean[] servlets, ServletMappingBean mapping) throws ErrorCollectionException {
      String servletName = mapping.getServletName();
      if (servletName != null) {
         this.update(this.fmt.CHECKING_SERVLET_MAPPING(servletName));
      }

      boolean found = false;
      if (servlets != null) {
         for(int i = 0; i < servlets.length; ++i) {
            if (servlets[i].getServletName().equals(servletName)) {
               found = true;
               break;
            }
         }
      }

      String[] patterns = mapping.getUrlPatterns();
      String pattern = null;
      if (patterns != null && patterns.length > 0) {
         pattern = patterns[0];
      }

      if (!found) {
         this.addDescriptorError(this.fmt.NO_SERVLET_DEF_FOR_MAPPING(pattern), new DescriptorErrorInfo(new String[]{"<servlet-mapping>", "<url-pattern>"}, pattern, new Object[]{"<servlet-name>"}));
         this.checkForExceptions();
      }

      if (patterns != null && patterns.length > 0) {
         for(int i = 0; i < patterns.length; ++i) {
            this.validateURLPattern(servletName, patterns[i]);
         }
      }

   }

   private void validateURLPattern(String name, String pattern) {
      if (pattern != null && pattern.length() != 0) {
         if (pattern.equalsIgnoreCase("*.jsp")) {
            this.update(this.fmt.warning() + this.fmt.STAR_JSP_URL_PATTERN(name));
         }

      } else {
         this.addDescriptorError(this.fmt.NO_URL_PATTERN(name), new DescriptorErrorInfo("<servlet-name>", name, "<url-pattern>"));
      }
   }
}
