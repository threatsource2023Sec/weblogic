package weblogic.xml.stream.events;

import weblogic.xml.stream.Location;

/** @deprecated */
@Deprecated
public class LocationImpl implements Location {
   protected int column;
   protected int line;
   protected String publicId = "";
   protected String systemId = "";

   public LocationImpl() {
   }

   public LocationImpl(int column, int line) {
      this.column = column;
      this.line = line;
   }

   public LocationImpl(int column, int line, String publicId, String systemId) {
      this.column = column;
      this.line = line;
      this.publicId = publicId;
      this.systemId = systemId;
   }

   public int getColumnNumber() {
      return this.column;
   }

   public int getLineNumber() {
      return this.line;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public void setColumnNumber(int column) {
      this.column = column;
   }

   public void setLineNumber(int line) {
      this.line = line;
   }

   public void setSystemId(String systemId) {
      this.systemId = systemId;
   }

   public void setPublicId(String publicId) {
      this.publicId = publicId;
   }

   public String toString() {
      return "[[" + this.line + "," + this.column + "][" + this.publicId + "," + this.systemId + "]]";
   }
}
