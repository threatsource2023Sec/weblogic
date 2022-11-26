package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EnterpriseBeansMBeanImpl extends XMLElementMBeanDelegate implements EnterpriseBeansMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_sessions = false;
   private List sessions;
   private boolean isSet_messageDrivens = false;
   private List messageDrivens;
   private boolean isSet_entities = false;
   private List entities;

   public weblogic.management.descriptors.ejb11.SessionMBean[] getSessions() {
      if (this.sessions == null) {
         return new weblogic.management.descriptors.ejb11.SessionMBean[0];
      } else {
         weblogic.management.descriptors.ejb11.SessionMBean[] result = new weblogic.management.descriptors.ejb11.SessionMBean[this.sessions.size()];
         result = (weblogic.management.descriptors.ejb11.SessionMBean[])((weblogic.management.descriptors.ejb11.SessionMBean[])this.sessions.toArray(result));
         return result;
      }
   }

   public void setSessions(weblogic.management.descriptors.ejb11.SessionMBean[] value) {
      weblogic.management.descriptors.ejb11.SessionMBean[] _oldVal = null;
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

   public void addSession(weblogic.management.descriptors.ejb11.SessionMBean value) {
      this.isSet_sessions = true;
      if (this.sessions == null) {
         this.sessions = Collections.synchronizedList(new ArrayList());
      }

      this.sessions.add(value);
   }

   public void removeSession(weblogic.management.descriptors.ejb11.SessionMBean value) {
      if (this.sessions != null) {
         this.sessions.remove(value);
      }
   }

   public MessageDrivenMBean[] getMessageDrivens() {
      if (this.messageDrivens == null) {
         return new MessageDrivenMBean[0];
      } else {
         MessageDrivenMBean[] result = new MessageDrivenMBean[this.messageDrivens.size()];
         result = (MessageDrivenMBean[])((MessageDrivenMBean[])this.messageDrivens.toArray(result));
         return result;
      }
   }

   public void setMessageDrivens(MessageDrivenMBean[] value) {
      MessageDrivenMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getMessageDrivens();
      }

      this.isSet_messageDrivens = true;
      if (this.messageDrivens == null) {
         this.messageDrivens = Collections.synchronizedList(new ArrayList());
      } else {
         this.messageDrivens.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.messageDrivens.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("MessageDrivens", _oldVal, this.getMessageDrivens());
      }

   }

   public void addMessageDriven(MessageDrivenMBean value) {
      this.isSet_messageDrivens = true;
      if (this.messageDrivens == null) {
         this.messageDrivens = Collections.synchronizedList(new ArrayList());
      }

      this.messageDrivens.add(value);
   }

   public void removeMessageDriven(MessageDrivenMBean value) {
      if (this.messageDrivens != null) {
         this.messageDrivens.remove(value);
      }
   }

   public weblogic.management.descriptors.ejb11.EntityMBean[] getEntities() {
      if (this.entities == null) {
         return new weblogic.management.descriptors.ejb11.EntityMBean[0];
      } else {
         weblogic.management.descriptors.ejb11.EntityMBean[] result = new weblogic.management.descriptors.ejb11.EntityMBean[this.entities.size()];
         result = (weblogic.management.descriptors.ejb11.EntityMBean[])((weblogic.management.descriptors.ejb11.EntityMBean[])this.entities.toArray(result));
         return result;
      }
   }

   public void setEntities(weblogic.management.descriptors.ejb11.EntityMBean[] value) {
      weblogic.management.descriptors.ejb11.EntityMBean[] _oldVal = null;
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

   public void addEntity(weblogic.management.descriptors.ejb11.EntityMBean value) {
      this.isSet_entities = true;
      if (this.entities == null) {
         this.entities = Collections.synchronizedList(new ArrayList());
      }

      this.entities.add(value);
   }

   public void removeEntity(weblogic.management.descriptors.ejb11.EntityMBean value) {
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

      if (null != this.getMessageDrivens()) {
         for(i = 0; i < this.getMessageDrivens().length; ++i) {
            result.append(this.getMessageDrivens()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</enterprise-beans>\n");
      return result.toString();
   }
}
