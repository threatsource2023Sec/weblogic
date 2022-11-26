package weblogic.management.mbeans.custom;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Set;
import javax.management.JMException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LifecycleManagerConfigMBean;
import weblogic.management.configuration.LifecycleManagerEndPointMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public final class LifecycleManagerConfig extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = 0L;
   private static LifecycleManagerEndPointMBean transientLocalEndPoint = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public LifecycleManagerConfig(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public boolean isEnabled() {
      LifecycleManagerEndPointMBean[] endPoints = this.getEndPoints();
      return endPoints != null && endPoints.length > 0;
   }

   public LifecycleManagerEndPointMBean[] getEndPoints() {
      LifecycleManagerConfigMBean selfMBean = (LifecycleManagerConfigMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)((DomainMBean)selfMBean.getParent());
      LifecycleManagerEndPointMBean[] configuredEndpoints = selfMBean.getConfiguredEndPoints();
      if (configuredEndpoints != null && configuredEndpoints.length > 0) {
         return configuredEndpoints;
      } else {
         LifecycleManagerEndPointMBean[] domainEndpoints = domain.getLifecycleManagerEndPoints();
         if (domainEndpoints != null && domainEndpoints.length > 0) {
            return domainEndpoints;
         } else if ("admin".equals(selfMBean.getDeploymentType())) {
            Set resultSet = new HashSet();
            if (transientLocalEndPoint == null) {
               try {
                  transientLocalEndPoint = this.createTransientLocalEndPoint(domain.getAdminServerName());
                  transientLocalEndPoint.setEnabled(true);
                  transientLocalEndPoint.setRuntimeName(domain.getName());
                  RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
                  URLManagerService urlManagerService = (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
                  String adminstrationURL = urlManagerService.findAdministrationURL(rt.getAdminServerName());
                  adminstrationURL = urlManagerService.normalizeToHttpProtocol(adminstrationURL);
                  transientLocalEndPoint.setURL(adminstrationURL + "/management/lifecycle");
               } catch (UnknownHostException | JMException var9) {
                  ManagementLogger.logExceptionInCustomizer(var9);
               }
            }

            if (transientLocalEndPoint != null) {
               resultSet.add(transientLocalEndPoint);
            }

            LifecycleManagerEndPointMBean[] result = new LifecycleManagerEndPointMBean[resultSet.size()];
            return (LifecycleManagerEndPointMBean[])((LifecycleManagerEndPointMBean[])resultSet.toArray(result));
         } else {
            return selfMBean.getConfiguredEndPoints();
         }
      }
   }

   private LifecycleManagerEndPointMBean createTransientLocalEndPoint(String endPointName) throws JMException {
      LifecycleManagerConfigMBean selfMBean = (LifecycleManagerConfigMBean)this.getMbean();
      LifecycleManagerEndPointMBean endPointMBean = null;

      try {
         Class endPointMBeanClass = Class.forName("weblogic.management.configuration.LifecycleManagerEndPointMBeanImpl");
         Constructor endPointMBeanConstructor = endPointMBeanClass.getConstructor(DescriptorBean.class, Integer.TYPE);
         endPointMBean = (LifecycleManagerEndPointMBean)endPointMBeanConstructor.newInstance(selfMBean, new Integer(-1));
         endPointMBean.setName(endPointName);
         return endPointMBean;
      } catch (Exception var6) {
         if (var6 instanceof RuntimeException) {
            throw (RuntimeException)var6;
         } else if (var6 instanceof JMException) {
            throw (JMException)var6;
         } else {
            throw new UndeclaredThrowableException(var6);
         }
      }
   }
}
