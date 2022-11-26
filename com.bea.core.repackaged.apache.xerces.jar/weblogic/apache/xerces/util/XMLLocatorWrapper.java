package weblogic.apache.xerces.util;

import weblogic.apache.xerces.xni.XMLLocator;

public final class XMLLocatorWrapper implements XMLLocator {
   private XMLLocator fLocator = null;

   public void setLocator(XMLLocator var1) {
      this.fLocator = var1;
   }

   public XMLLocator getLocator() {
      return this.fLocator;
   }

   public String getPublicId() {
      return this.fLocator != null ? this.fLocator.getPublicId() : null;
   }

   public String getLiteralSystemId() {
      return this.fLocator != null ? this.fLocator.getLiteralSystemId() : null;
   }

   public String getBaseSystemId() {
      return this.fLocator != null ? this.fLocator.getBaseSystemId() : null;
   }

   public String getExpandedSystemId() {
      return this.fLocator != null ? this.fLocator.getExpandedSystemId() : null;
   }

   public int getLineNumber() {
      return this.fLocator != null ? this.fLocator.getLineNumber() : -1;
   }

   public int getColumnNumber() {
      return this.fLocator != null ? this.fLocator.getColumnNumber() : -1;
   }

   public int getCharacterOffset() {
      return this.fLocator != null ? this.fLocator.getCharacterOffset() : -1;
   }

   public String getEncoding() {
      return this.fLocator != null ? this.fLocator.getEncoding() : null;
   }

   public String getXMLVersion() {
      return this.fLocator != null ? this.fLocator.getXMLVersion() : null;
   }
}
