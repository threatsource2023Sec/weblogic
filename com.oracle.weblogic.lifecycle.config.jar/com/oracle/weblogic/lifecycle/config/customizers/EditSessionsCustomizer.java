package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.EditSession;
import com.oracle.weblogic.lifecycle.config.EditSessions;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.properties.PropertyValue;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class EditSessionsCustomizer {
   @Inject
   private XmlService xmlService;

   public EditSession getOrCreateEditSession(EditSessions sessions, String name) {
      EditSession session = sessions.lookupEditSession(name);
      return session != null ? session : this.createEditSession(sessions, name, (Map)null);
   }

   public EditSession createEditSession(EditSessions sessions, String name) {
      return this.createEditSession(sessions, name, (Map)null);
   }

   public EditSession createEditSession(EditSessions sessions, String name, Map properties) {
      EditSession editSession = (EditSession)this.xmlService.createBean(EditSession.class);

      try {
         editSession.setName(name);
      } catch (PropertyVetoException var10) {
         throw new RuntimeException(var10);
      }

      PropertyBean property;
      if (properties != null) {
         for(Iterator var5 = properties.keySet().iterator(); var5.hasNext(); editSession.addProperty(property)) {
            String propertyName = (String)var5.next();
            property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);
            property.setName(propertyName);
            PropertyValue value = (PropertyValue)properties.get(propertyName);
            if (value instanceof StringPropertyValue) {
               String propertyValue = ((StringPropertyValue)value).getValue();
               property.setValue(propertyValue);
            }
         }
      }

      return sessions.addEditSession(editSession);
   }

   public EditSession getEditSessionByName(EditSessions sessions, String name) {
      return sessions.lookupEditSession(name);
   }

   public EditSession deleteEditSession(EditSessions sessions, EditSession editSession) {
      return sessions.removeEditSession(editSession);
   }
}
