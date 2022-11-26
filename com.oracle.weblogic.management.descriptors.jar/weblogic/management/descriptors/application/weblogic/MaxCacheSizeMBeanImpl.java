package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MaxCacheSizeMBeanImpl extends XMLElementMBeanDelegate implements MaxCacheSizeMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_bytes = false;
   private int bytes = -1;
   private boolean isSet_megabytes = false;
   private int megabytes = -1;

   public int getBytes() {
      return this.bytes;
   }

   public void setBytes(int value) {
      int old = this.bytes;
      this.bytes = value;
      this.isSet_bytes = value != -1;
      this.checkChange("bytes", old, this.bytes);
   }

   public int getMegabytes() {
      return this.megabytes;
   }

   public void setMegabytes(int value) {
      int old = this.megabytes;
      this.megabytes = value;
      this.isSet_megabytes = value != -1;
      this.checkChange("megabytes", old, this.megabytes);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<max-cache-size");
      result.append(">\n");
      if (!this.isSet_bytes && -1 == this.getBytes()) {
         if (this.isSet_megabytes || -1 != this.getMegabytes()) {
            result.append(ToXML.indent(indentLevel + 2)).append("<megabytes>").append(this.getMegabytes()).append("</megabytes>\n");
         }
      } else {
         result.append(ToXML.indent(indentLevel + 2)).append("<bytes>").append(this.getBytes()).append("</bytes>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</max-cache-size>\n");
      return result.toString();
   }
}
