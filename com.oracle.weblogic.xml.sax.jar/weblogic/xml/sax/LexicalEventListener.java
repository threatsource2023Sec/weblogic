package weblogic.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class LexicalEventListener implements LexicalHandler {
   private String DTDName;
   private String systemId;
   private String publicId;

   public void comment(char[] ch, int start, int length) throws SAXException {
   }

   public void startCDATA() throws SAXException {
   }

   public void endCDATA() throws SAXException {
   }

   public void startEntity(String name) throws SAXException {
   }

   public void endEntity(String name) throws SAXException {
   }

   public void startDTD(String name, String publicId, String systemId) throws SAXException {
      this.setDTDName(name);
      this.setPublicId(publicId);
      this.setSystemId(systemId);
   }

   public void endDTD() throws SAXException {
   }

   public String getDTDName() {
      return this.DTDName;
   }

   private void setDTDName(String name) {
      this.DTDName = name;
   }

   public String getSystemId() {
      return this.systemId;
   }

   private void setSystemId(String systemId) {
      this.systemId = systemId;
   }

   public String getPublicId() {
      return this.publicId;
   }

   private void setPublicId(String publicId) {
      this.publicId = publicId;
   }
}
