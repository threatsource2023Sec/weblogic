package weblogic.deployment.jms;

import java.util.Map;
import javax.jms.JMSException;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.management.ManagementException;
import weblogic.security.subject.AbstractSubject;

public abstract class PlatformHelper {
   private static PlatformHelper singleton;

   public static PlatformHelper getPlatformHelper() {
      if (singleton == null) {
         try {
            singleton = (PlatformHelper)Class.forName("weblogic.deployment.jms.PlatformHelperImpl").newInstance();
         } catch (Exception var1) {
            throw new IllegalArgumentException(var1.toString());
         }
      }

      return singleton;
   }

   static void setPlatformHelper(PlatformHelper helper) {
      singleton = helper;
   }

   abstract JMSSessionPoolRuntime createJMSSessionPoolRuntime(String var1, ResourcePool var2, JMSSessionPool var3) throws ManagementException;

   abstract boolean getResNameEqualProp();

   abstract String getXAResourceName(String var1);

   abstract ForeignRefReturn checkForeignRef(Map var1) throws JMSException;

   public class ForeignRefReturn {
      String connectionHealthChecking = "enabled";
      boolean foundCreds;
      AbstractSubject subject;
      StringBuffer userNameBuf;
      StringBuffer passwdBuf;
   }
}
