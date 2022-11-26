package weblogic.ant.taskdefs.management;

import java.io.Serializable;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;

public class MBeanCommoQuery implements QueryExp, Serializable {
   MBeanServer mbs;
   public ObjectName targetType;

   public void setTargetType(ObjectName targetType) {
      this.targetType = targetType;
   }

   public void setMBeanServer(MBeanServer s) {
      this.mbs = s;
   }

   public boolean apply(ObjectName name) {
      try {
         if (this.mbs.isInstanceOf(name, "weblogic.management.commo.CommoModelMBean") && !"CustomMBeanType".equals(name.getKeyProperty("Type"))) {
            if (this.targetType == null) {
               return true;
            }

            ObjectName typeOName = (ObjectName)this.mbs.invoke(name, "getTypeObjectName", new Object[0], new String[0]);
            if (typeOName != null && typeOName.equals(this.targetType)) {
               return true;
            }
         }
      } catch (InstanceNotFoundException var3) {
      } catch (MBeanException var4) {
      } catch (ReflectionException var5) {
      }

      return false;
   }
}
