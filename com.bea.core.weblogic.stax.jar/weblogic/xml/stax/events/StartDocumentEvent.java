package weblogic.xml.stax.events;

import javax.xml.stream.events.StartDocument;

public class StartDocumentEvent extends BaseEvent implements StartDocument {
   protected String systemId = "";
   protected String encodingScheme = "UTF-8";
   protected boolean standalone = true;
   protected String version = "1.0";
   private boolean encodingSchemeSet = false;
   private boolean standaloneSet = false;

   public StartDocumentEvent() {
      this.init();
   }

   protected void init() {
      this.setEventType(7);
   }

   public String getSystemId() {
      return this.systemId;
   }

   public String getCharacterEncodingScheme() {
      return this.encodingScheme;
   }

   public boolean isStandalone() {
      return this.standalone;
   }

   public String getVersion() {
      return this.version;
   }

   public void setStandalone(boolean standalone) {
      this.standaloneSet = true;
      this.standalone = standalone;
   }

   public void setStandalone(String standalone) {
      this.standaloneSet = true;
      if (standalone == null) {
         this.standalone = true;
      } else {
         if (standalone.equals("yes")) {
            this.standalone = true;
         } else {
            this.standalone = false;
         }

      }
   }

   public boolean encodingSet() {
      return this.encodingSchemeSet;
   }

   public boolean standaloneSet() {
      return this.standaloneSet;
   }

   public void setEncoding(String encoding) {
      this.encodingScheme = encoding;
      this.encodingSchemeSet = true;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public void clear() {
      this.encodingScheme = "UTF-8";
      this.standalone = true;
      this.version = "1.0";
      this.encodingSchemeSet = false;
      this.standaloneSet = false;
   }

   public String toString() {
      String val = "<?xml version=\"" + this.version + "\"";
      val = val + " encoding='" + this.encodingScheme + "'";
      if (this.standaloneSet) {
         if (this.standalone) {
            val = val + " standalone='yes'?>";
         } else {
            val = val + " standalone='no'?>";
         }
      } else {
         val = val + "?>";
      }

      return val;
   }
}
