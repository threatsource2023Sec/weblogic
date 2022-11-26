package weblogic.management.mbeans.custom;

import java.lang.reflect.Method;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.ConfigurationException;

public class WebLogic {
   WebLogicMBean bean;
   private String name;
   private WebLogicObjectName weblogicObjectName = null;
   private boolean isNameSet = false;

   public WebLogic(WebLogicMBean bean) {
      this.bean = bean;
   }

   public String getName() {
      if (this.name == null) {
         WebLogicMBean parent = this.bean.getParent();
         return parent == null ? null : parent.getName();
      } else {
         return this.name;
      }
   }

   public void setName(String n) throws InvalidAttributeValueException {
      if (n != null) {
         if (!n.equals(this.name)) {
            if (this.isNameSet && ((DescriptorBean)this.bean).isSet("Name")) {
               Loggable loggable = ManagementLogger.logNameAttributeIsReadOnlyLoggable(this.getObjectName().toString());
               throw new InvalidAttributeValueException(loggable.getMessageText());
            } else {
               this.isNameSet = true;
               this.name = n;
            }
         }
      }
   }

   public WebLogicObjectName getObjectName() {
      if (this.weblogicObjectName != null) {
         return this.weblogicObjectName;
      } else {
         try {
            Class clazz = Class.forName("weblogic.management.mbeanservers.internal.WLSObjectNameManager");
            Method m = clazz.getMethod("buildWLSObjectName", Object.class);
            this.weblogicObjectName = (WebLogicObjectName)m.invoke((Object)null, this.bean);
         } catch (Throwable var3) {
            throw new Error("Failed to Build ObjectName: " + var3.getMessage(), var3);
         }

         return this.weblogicObjectName;
      }
   }

   public WebLogicMBean getParent() {
      return this.bean instanceof AbstractDescriptorBean ? (WebLogicMBean)((DescriptorBean)this.bean).getParentBean() : this.bean.getParent();
   }

   public void setParent(WebLogicMBean parent) {
      if (parent != null) {
         if (!(this.bean instanceof AbstractDescriptorBean)) {
            try {
               this.bean.setParent(parent);
            } catch (ConfigurationException var3) {
               throw new AssertionError("Impossible Exception" + var3);
            }
         }

      }
   }

   public boolean isRegistered() {
      return true;
   }
}
