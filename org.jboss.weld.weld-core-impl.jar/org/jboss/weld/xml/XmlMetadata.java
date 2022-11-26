package org.jboss.weld.xml;

import java.net.URL;
import org.jboss.weld.metadata.FileMetadata;

public class XmlMetadata extends FileMetadata {
   private final String qName;

   public XmlMetadata(String qName, Object value, URL file, int lineNumber) {
      super(value, file, lineNumber);
      this.qName = qName;
   }

   public String getLocation() {
      return this.getValue() != null ? "<" + this.qName + ">" + this.getValue() + "</" + this.qName + "> in " + this.getFile().toString() + "@" + this.getLineNumber() : "<" + this.qName + " /> in " + this.getFile().toString() + "@" + this.getLineNumber();
   }
}
