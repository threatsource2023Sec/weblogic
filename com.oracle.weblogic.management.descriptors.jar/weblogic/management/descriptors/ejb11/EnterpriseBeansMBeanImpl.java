package weblogic.management.descriptors.ejb11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EnterpriseBeansMBeanImpl extends XMLElementMBeanDelegate implements EnterpriseBeansMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_sessions = false;
   private List sessions;
   private boolean isSet_entities = false;
   private List entities;

   public SessionMBean[] getSessions() {
      if (this.sessions == null) {
         return new SessionMBean[0];
      } else {
         SessionMBean[] result = new SessionMBean[this.sessions.size()];
         result = (SessionMBean[])((SessionMBean[])this.sessions.toArray(result));
         return result;
      }
   }

   public void setSessions(SessionMBean[] value) {
      SessionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getSessions();
      }

      this.isSet_sessions = true;
      if (this.sessions == null) {
         this.sessions = Collections.synchronizedList(new ArrayList());
      } else {
         this.sessions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.sessions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Sessions", _oldVal, this.getSessions());
      }

   }

   public void addSession(SessionMBean value) {
      this.isSet_sessions = true;
      if (this.sessions == null) {
         this.sessions = Collections.synchronizedList(new ArrayList());
      }

      this.sessions.add(value);
   }

   public void removeSession(SessionMBean value) {
      if (this.sessions != null) {
         this.sessions.remove(value);
      }
   }

   public EntityMBean[] getEntities() {
      if (this.entities == null) {
         return new EntityMBean[0];
      } else {
         EntityMBean[] result = new EntityMBean[this.entities.size()];
         result = (EntityMBean[])((EntityMBean[])this.entities.toArray(result));
         return result;
      }
   }

   public void setEntities(EntityMBean[] value) {
      EntityMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEntities();
      }

      this.isSet_entities = true;
      if (this.entities == null) {
         this.entities = Collections.synchronizedList(new ArrayList());
      } else {
         this.entities.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.entities.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Entities", _oldVal, this.getEntities());
      }

   }

   public void addEntity(EntityMBean value) {
      this.isSet_entities = true;
      if (this.entities == null) {
         this.entities = Collections.synchronizedList(new ArrayList());
      }

      this.entities.add(value);
   }

   public void removeEntity(EntityMBean value) {
      if (this.entities != null) {
         this.entities.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<enterprise-beans");
      result.append(">\n");
      int i;
      if (null != this.getSessions()) {
         for(i = 0; i < this.getSessions().length; ++i) {
            result.append(this.getSessions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getEntities()) {
         for(i = 0; i < this.getEntities().length; ++i) {
            result.append(this.getEntities()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</enterprise-beans>\n");
      return result.toString();
   }
}
