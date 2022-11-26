package weblogic.servlet.internal;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.UserDataConstraintBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;

public final class SecurityConstraintHelper {
   private static void checkRolesAllowed(ServletSecurity.EmptyRoleSemantic emptyRoleSemantic, String[] rolesAllowed) {
      if (rolesAllowed != null && rolesAllowed.length > 0 && emptyRoleSemantic == EmptyRoleSemantic.DENY) {
         throw new IllegalArgumentException("The default authorization semantic should not be specified when a non-empty array is specified for rolesAllowed");
      }
   }

   static void checkServletSecurityElement(ServletSecurityElement constraint) {
      if (constraint == null) {
         throw new IllegalArgumentException("Security Constraint can't be null");
      } else {
         String[] rolesAllowed = constraint.getRolesAllowed();
         ServletSecurity.EmptyRoleSemantic emptyRoleSemantic = constraint.getEmptyRoleSemantic();
         checkRolesAllowed(emptyRoleSemantic, rolesAllowed);
         Collection httpMethodConstraints = constraint.getHttpMethodConstraints();
         if (httpMethodConstraints != null) {
            Iterator it = httpMethodConstraints.iterator();

            while(it.hasNext()) {
               HttpMethodConstraintElement httpMethodConstraint = (HttpMethodConstraintElement)it.next();
               String httpMethod = httpMethodConstraint.getMethodName();
               if (httpMethod == null || httpMethod.length() == 0) {
                  throw new IllegalArgumentException("The name of an HTTP protocol method may not be null, or the empty string, and must be a legitimate HTTP Method name as defined by RFC 2616");
               }

               emptyRoleSemantic = httpMethodConstraint.getEmptyRoleSemantic();
               rolesAllowed = httpMethodConstraint.getRolesAllowed();
               checkRolesAllowed(emptyRoleSemantic, rolesAllowed);
            }
         }

      }
   }

   private static boolean isSecurityConstraintBeanFromDescriptor(SecurityConstraintBean scb) {
      String source = (String)((AbstractDescriptorBean)scb).getMetaData("source");
      if (source == null) {
         return true;
      } else {
         return !source.equals("annotation") && !source.equals("dynamic");
      }
   }

   private static void destorySecurityConstraintBean(WebAppBean webAppBean, SecurityConstraintBean scb, Collection urlPatternOverride) {
      WebResourceCollectionBean[] wrcbs = scb.getWebResourceCollections();
      if (wrcbs != null) {
         WebResourceCollectionBean[] var4 = wrcbs;
         int var5 = wrcbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            WebResourceCollectionBean wrcb = var4[var6];
            String[] urlPatterns = wrcb.getUrlPatterns();
            if (urlPatterns != null) {
               String[] var9 = urlPatterns;
               int var10 = urlPatterns.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String urlPattern = var9[var11];
                  if (urlPatternOverride.contains(urlPattern)) {
                     webAppBean.destroySecurityConstraint(scb);
                     return;
                  }
               }
            }
         }

      }
   }

   static void destorySecurityConstraintBeans(WebAppBean webAppBean, Collection urlPatternOverride) {
      SecurityConstraintBean[] scbs = webAppBean.getSecurityConstraints();
      if (scbs != null) {
         SecurityConstraintBean[] var3 = scbs;
         int var4 = scbs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            SecurityConstraintBean scb = var3[var5];
            if (!isSecurityConstraintBeanFromDescriptor(scb)) {
               destorySecurityConstraintBean(webAppBean, scb, urlPatternOverride);
            }
         }

      }
   }

   private static SecurityConstraintBean createSecurityConstraintBean(WebAppBean bean, Collection urlPatterns, String[] rolesAllowed, ServletSecurity.EmptyRoleSemantic emptyRoleSemantic, ServletSecurity.TransportGuarantee transportGuarantee, String httpMethod, boolean fromAnnotation) {
      SecurityConstraintBean scb = bean.createSecurityConstraint();
      WebResourceCollectionBean wrcb = scb.createWebResourceCollection();
      Iterator var9 = urlPatterns.iterator();

      while(var9.hasNext()) {
         String urlPattern = (String)var9.next();
         wrcb.addUrlPattern(urlPattern);
      }

      if (rolesAllowed != null && rolesAllowed.length > 0) {
         AuthConstraintBean acb = scb.createAuthConstraint();
         String[] var22 = rolesAllowed;
         int var11 = rolesAllowed.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            String roleName = var22[var12];
            SecurityRoleBean[] srbs = bean.getSecurityRoles();
            boolean existedRole = false;
            SecurityRoleBean[] var16 = srbs;
            int var17 = srbs.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               SecurityRoleBean srb = var16[var18];
               if (srb.getRoleName().equals(roleName)) {
                  existedRole = true;
                  break;
               }
            }

            if (!existedRole) {
               bean.createSecurityRole().setRoleName(roleName);
            }

            acb.addRoleName(roleName);
         }
      } else if (httpMethod == null && emptyRoleSemantic == EmptyRoleSemantic.PERMIT && fromAnnotation && bean.isDenyUncoveredHttpMethods()) {
         scb.createAuthConstraint();
      } else if (emptyRoleSemantic != EmptyRoleSemantic.PERMIT) {
         scb.createAuthConstraint();
      }

      if (transportGuarantee != null) {
         UserDataConstraintBean udcb = scb.createUserDataConstraint();
         udcb.setTransportGuarantee(transportGuarantee == TransportGuarantee.CONFIDENTIAL ? "CONFIDENTIAL" : "NONE");
      }

      if (httpMethod != null) {
         wrcb.addHttpMethod(httpMethod);
      }

      return scb;
   }

   private static void markSecurityConstraintBean(SecurityConstraintBean scb, boolean fromAnnotation) {
      if (fromAnnotation) {
         ((AbstractDescriptorBean)scb).setMetaData("source", "annotation");
      } else {
         ((AbstractDescriptorBean)scb).setMetaData("source", "dynamic");
      }

   }

   static void processServletSecurityElement(WebAppBean bean, Collection urlPatterns, ServletSecurityElement constraint, boolean fromAnnotation) {
      SecurityConstraintBean scb1 = null;
      Collection httpMethodConstraints = constraint.getHttpMethodConstraints();
      if (!bean.isDenyUncoveredHttpMethods() || httpMethodConstraints == null || httpMethodConstraints.size() == 0) {
         scb1 = createSecurityConstraintBean(bean, urlPatterns, constraint.getRolesAllowed(), constraint.getEmptyRoleSemantic(), constraint.getTransportGuarantee(), (String)null, fromAnnotation);
         markSecurityConstraintBean(scb1, fromAnnotation);
      }

      if (httpMethodConstraints != null) {
         Iterator it = httpMethodConstraints.iterator();

         while(it.hasNext()) {
            HttpMethodConstraintElement httpMethodConstraint = (HttpMethodConstraintElement)it.next();
            String httpMethod = httpMethodConstraint.getMethodName();
            SecurityConstraintBean scb2 = createSecurityConstraintBean(bean, urlPatterns, httpMethodConstraint.getRolesAllowed(), httpMethodConstraint.getEmptyRoleSemantic(), httpMethodConstraint.getTransportGuarantee(), httpMethod, fromAnnotation);
            markSecurityConstraintBean(scb2, fromAnnotation);
            if (scb1 != null) {
               scb1.getWebResourceCollections()[0].addHttpMethodOmission(httpMethod);
            }
         }
      }

   }
}
