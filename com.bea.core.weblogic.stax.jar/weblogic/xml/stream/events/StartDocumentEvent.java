package weblogic.xml.stream.events;

import weblogic.xml.stream.StartDocument;

/** @deprecated */
@Deprecated
public class StartDocumentEvent extends ElementEvent implements StartDocument {
   protected String systemId = null;
   protected String encodingScheme = "UTF-8";
   protected boolean standalone = true;
   protected String version = "1.0";
   private boolean encodingSchemeSet = false;
   private boolean standaloneSet = false;

   public StartDocumentEvent() {
      this.init();
   }

   protected void init() {
      this.type = 256;
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

   public String toString() {
      String val = "<?xml version=\"" + this.version + "\"";
      val = val + " encoding='" + this.encodingScheme + "'";
      if (this.standalone) {
         val = val + " standalone='yes'?>";
      } else {
         val = val + " standalone='no'?>";
      }

      return val;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else {
         return obj instanceof StartDocumentEvent;
      }
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result;
      return result;
   }
}
