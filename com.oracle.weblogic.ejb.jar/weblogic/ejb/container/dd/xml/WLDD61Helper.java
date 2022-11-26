package weblogic.ejb.container.dd.xml;

import java.util.HashMap;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.xml.process.SAXValidationException;

public final class WLDD61Helper {
   private HashMap pStorage = new HashMap();

   public void addPersistenceType(String id, String version, String storage) {
      this.pStorage.put(id + version, storage);
   }

   public String getPersistenceStorage(String id, String version, String ejbName) throws SAXValidationException {
      if (!this.pStorage.containsKey(id + version)) {
         Loggable l = EJBLogger.logpersistentTypeMissingLoggable(id, version, ejbName);
         throw new SAXValidationException(l.getMessageText());
      } else {
         return (String)this.pStorage.get(id + version);
      }
   }
}
