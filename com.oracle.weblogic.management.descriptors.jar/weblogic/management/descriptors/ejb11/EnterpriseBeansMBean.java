package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface EnterpriseBeansMBean extends XMLElementMBean {
   SessionMBean[] getSessions();

   void setSessions(SessionMBean[] var1);

   void addSession(SessionMBean var1);

   void removeSession(SessionMBean var1);

   EntityMBean[] getEntities();

   void setEntities(EntityMBean[] var1);

   void addEntity(EntityMBean var1);

   void removeEntity(EntityMBean var1);
}
