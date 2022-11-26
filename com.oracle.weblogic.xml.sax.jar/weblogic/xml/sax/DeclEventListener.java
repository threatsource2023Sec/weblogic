package weblogic.xml.sax;

import java.util.Hashtable;
import java.util.Map;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public class DeclEventListener implements DeclHandler {
   private Hashtable entitiesSystemIds = new Hashtable();
   private Hashtable entitiesPublicIds = new Hashtable();

   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
   }

   public void elementDecl(String name, String model) throws SAXException {
   }

   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
      if (name != null) {
         if (systemId != null) {
            this.entitiesSystemIds.put(name, systemId);
         }

         if (publicId != null) {
            this.entitiesPublicIds.put(name, publicId);
         }
      }

   }

   public void internalEntityDecl(String name, String value) throws SAXException {
   }

   protected Map getExternalEntitiesSystemIds() {
      return this.entitiesSystemIds;
   }

   protected Map getExternalEntitiesPublicIds() {
      return this.entitiesPublicIds;
   }
}
