package weblogic.deploy.service.internal.targetserver;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.callout.spi.WebLogicCallout;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.internal.CalloutManager;
import weblogic.management.configuration.CalloutMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

@Service
public class TargetCalloutManager implements CalloutManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   List changeReceivedCallouts = new ArrayList();
   List expectedChangeCallouts = new ArrayList();

   private TargetCalloutManager() {
   }

   public void init(DomainMBean domainMBean) {
      if (Debug.isServiceTransportDebugEnabled()) {
         Debug.serviceTransportDebug("TargetCalloutManager: init()");
      }

      this.setupCalloutsList(domainMBean);
   }

   public void setupCalloutsList(DomainMBean domainMBean) {
      this.changeReceivedCallouts.clear();
      this.expectedChangeCallouts.clear();

      try {
         CalloutMBean[] calloutMBeans = domainMBean.getCallouts();

         for(int i = 0; i < calloutMBeans.length; ++i) {
            try {
               CalloutMBean oneCallout = calloutMBeans[i];
               WebLogicCallout callout = this.createWebLogicCallout(oneCallout);
               callout.init(oneCallout.getArgument());
               if (oneCallout.getHookPoint().equals("HOOK_POINT_CHANGE_RECEIVED")) {
                  this.changeReceivedCallouts.add(callout);
               } else {
                  this.expectedChangeCallouts.add(callout);
               }
            } catch (Exception var6) {
               if (Debug.isServiceTransportDebugEnabled()) {
                  Debug.serviceTransportDebug("TargetCalloutManager: " + var6.getMessage());
               }
            }
         }

         if (Debug.isServiceTransportDebugEnabled()) {
            Debug.serviceTransportDebug("expectedChangeCallouts list size = " + this.expectedChangeCallouts.size() + "\nchangeReceivedCallout list size = " + this.changeReceivedCallouts.size());
         }
      } catch (Exception var7) {
         if (Debug.isServiceTransportDebugEnabled()) {
            Debug.serviceTransportDebug("TargetCalloutManager: " + var7.getMessage());
         }
      }

   }

   private WebLogicCallout createWebLogicCallout(CalloutMBean callout) throws RuntimeException {
      String className = callout.getClassName();

      try {
         Class cls = Thread.currentThread().getContextClassLoader().loadClass(className);
         if (!WebLogicCallout.class.isAssignableFrom(cls)) {
            throw new RuntimeException("Cannot create WebLogicCallout [" + callout.getName() + "] : className does not implement weblogic.callout.spi.WebLogicCallout");
         } else {
            Constructor constructor = cls.getConstructor();
            Object object = constructor.newInstance();
            return (WebLogicCallout)object;
         }
      } catch (Exception var6) {
         throw new RuntimeException("Cannot create WebLogicCallout [" + callout.getName() + "].", var6);
      }
   }

   public boolean abortForExpectedChangeCallout(String location) {
      Iterator var2 = this.expectedChangeCallouts.iterator();

      WebLogicCallout callout;
      do {
         if (!var2.hasNext()) {
            Debug.serviceTransportDebug("TargetCalloutManager: abortForExpectedChangeCallout returns false:  location=" + location);
            return false;
         }

         callout = (WebLogicCallout)var2.next();
      } while("ignore" != this.invokeCalloutAsAnonymous(callout, "HOOK_POINT_EXPECTED_CHANGE", location, (Map)null));

      Debug.serviceTransportDebug("TargetCalloutManager: abortForExpectedChangeCallout returns true:  location=" + location);
      return true;
   }

   public boolean abortForChangeReceivedCallout(String location, DomainMBean active, DomainMBean pending) {
      Iterator var4 = this.changeReceivedCallouts.iterator();

      WebLogicCallout callout;
      HashMap values;
      do {
         if (!var4.hasNext()) {
            Debug.serviceTransportDebug("TargetCalloutManager: abortForChangeReceivedCallout returns true:  location=" + location);
            return false;
         }

         callout = (WebLogicCallout)var4.next();
         values = new HashMap();
         values.put("active", active);
         values.put("pending", pending);
      } while("ignore" != this.invokeCalloutAsAnonymous(callout, "HOOK_POINT_CHANGE_RECEIVED", location, values));

      Debug.serviceTransportDebug("TargetCalloutManager: abortForChangeReceivedCallout returns true:  location=" + location);
      return true;
   }

   public boolean hasExpectedChangeCallouts() {
      return this.expectedChangeCallouts.size() > 0;
   }

   public boolean hasChangeReceivedCallouts() {
      return this.changeReceivedCallouts.size() > 0;
   }

   private String invokeCalloutAsAnonymous(WebLogicCallout callout, String hookPoint, String location, Map values) {
      CalloutAction privilegedAction = new CalloutAction(callout, hookPoint, location, values);
      return (String)SecurityManager.runAsForUserCode(kernelId, SubjectUtils.getAnonymousSubject(), privilegedAction);
   }

   static class CalloutAction implements PrivilegedAction {
      final WebLogicCallout callout;
      final String hookPoint;
      final String location;
      final Map values;

      public CalloutAction(WebLogicCallout callout, String hookPoint, String location, Map values) {
         this.callout = callout;
         this.hookPoint = hookPoint;
         this.location = location;
         this.values = values;
      }

      public String run() {
         return this.callout.callout(this.hookPoint, this.location, this.values);
      }
   }
}
