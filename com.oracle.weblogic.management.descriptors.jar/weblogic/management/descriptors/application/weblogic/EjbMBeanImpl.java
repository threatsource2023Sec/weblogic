package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EjbMBeanImpl extends XMLElementMBeanDelegate implements EjbMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_startMdbsWithApplication = false;
   private boolean startMdbsWithApplication = false;
   private boolean isSet_entityCaches = false;
   private List entityCaches;

   public boolean getStartMdbsWithApplication() {
      return this.startMdbsWithApplication;
   }

   public void setStartMdbsWithApplication(boolean value) {
      boolean old = this.startMdbsWithApplication;
      this.startMdbsWithApplication = value;
      this.isSet_startMdbsWithApplication = true;
      this.checkChange("startMdbsWithApplication", old, this.startMdbsWithApplication);
   }

   public EntityCacheMBean[] getEntityCaches() {
      if (this.entityCaches == null) {
         return new EntityCacheMBean[0];
      } else {
         EntityCacheMBean[] result = new EntityCacheMBean[this.entityCaches.size()];
         result = (EntityCacheMBean[])((EntityCacheMBean[])this.entityCaches.toArray(result));
         return result;
      }
   }

   public void setEntityCaches(EntityCacheMBean[] value) {
      EntityCacheMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEntityCaches();
      }

      this.isSet_entityCaches = true;
      if (this.entityCaches == null) {
         this.entityCaches = Collections.synchronizedList(new ArrayList());
      } else {
         this.entityCaches.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.entityCaches.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EntityCaches", _oldVal, this.getEntityCaches());
      }

   }

   public void addEntityCache(EntityCacheMBean value) {
      this.isSet_entityCaches = true;
      if (this.entityCaches == null) {
         this.entityCaches = Collections.synchronizedList(new ArrayList());
      }

      this.entityCaches.add(value);
   }

   public void removeEntityCache(EntityCacheMBean value) {
      if (this.entityCaches != null) {
         this.entityCaches.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb");
      result.append(">\n");
      if (null != this.getEntityCaches()) {
         for(int i = 0; i < this.getEntityCaches().length; ++i) {
            result.append(this.getEntityCaches()[i].toXML(indentLevel + 2));
         }
      }

      if (this.isSet_startMdbsWithApplication || this.getStartMdbsWithApplication()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<start-mdbs-with-application>").append(ToXML.capitalize(Boolean.valueOf(this.getStartMdbsWithApplication()).toString())).append("</start-mdbs-with-application>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb>\n");
      return result.toString();
   }
}
