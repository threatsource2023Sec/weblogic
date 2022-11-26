package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.CharsetMappingMBean;
import weblogic.management.descriptors.webappext.CharsetParamsMBean;
import weblogic.management.descriptors.webappext.InputCharsetDescriptorMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class CharsetParams extends BaseServletDescriptor implements CharsetParamsMBean {
   private static final long serialVersionUID = 1946096778263982155L;
   private static final String INPUT_CHARSET = "input-charset";
   private static final String CHARSET_MAPPING = "charset-mapping";
   private CharsetMappingMBean[] charsetMappings;
   private InputCharsetDescriptorMBean[] inputCharsets;

   public CharsetParams() {
   }

   public CharsetParams(Element parentElement) throws DOMProcessingException {
      List icList = DOMUtils.getOptionalElementsByTagName(parentElement, "input-charset");
      if (icList != null) {
         ArrayList al = new ArrayList();
         Iterator i = icList.iterator();

         while(i.hasNext()) {
            al.add(new InputCharsetDescriptor((Element)i.next()));
         }

         this.inputCharsets = (InputCharsetDescriptorMBean[])((InputCharsetDescriptorMBean[])al.toArray(new InputCharsetDescriptorMBean[0]));
      }

      List cmList = DOMUtils.getOptionalElementsByTagName(parentElement, "charset-mapping");
      if (cmList != null) {
         ArrayList al = new ArrayList();
         Iterator i = cmList.iterator();

         while(i.hasNext()) {
            al.add(new CharsetMapping((Element)i.next()));
         }

         this.charsetMappings = (CharsetMappingMBean[])((CharsetMappingMBean[])al.toArray(new CharsetMappingMBean[0]));
      }

   }

   public InputCharsetDescriptorMBean[] getInputCharsets() {
      return this.inputCharsets;
   }

   public void setInputCharsets(InputCharsetDescriptorMBean[] ic) {
      InputCharsetDescriptorMBean[] old = this.inputCharsets;
      this.inputCharsets = ic;
      if (!comp(old, ic)) {
         this.firePropertyChange("inputCharsets", old, ic);
      }

   }

   public void addInputCharset(InputCharsetDescriptorMBean x) {
      InputCharsetDescriptorMBean[] prev = this.getInputCharsets();
      if (prev == null) {
         InputCharsetDescriptorMBean[] prev = new InputCharsetDescriptor[]{x};
         this.setInputCharsets(prev);
      } else {
         InputCharsetDescriptorMBean[] curr = new InputCharsetDescriptorMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setInputCharsets(curr);
      }
   }

   public void removeInputCharset(InputCharsetDescriptorMBean x) {
      InputCharsetDescriptorMBean[] prev = this.getInputCharsets();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            InputCharsetDescriptorMBean[] curr = new InputCharsetDescriptorMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setInputCharsets(curr);
         }

      }
   }

   public CharsetMappingMBean[] getCharsetMappings() {
      if (this.charsetMappings == null) {
         this.charsetMappings = new CharsetMappingMBean[0];
      }

      return this.charsetMappings;
   }

   public void setCharsetMappings(CharsetMappingMBean[] cm) {
      CharsetMappingMBean[] old = this.charsetMappings;
      this.charsetMappings = cm;
      if (!comp(old, cm)) {
         this.firePropertyChange("charsetMappings", old, cm);
      }

   }

   public void addCharsetMapping(CharsetMappingMBean x) {
      CharsetMappingMBean[] prev = this.getCharsetMappings();
      if (prev == null) {
         prev = new CharsetMappingMBean[]{x};
         this.setCharsetMappings(prev);
      } else {
         CharsetMappingMBean[] curr = new CharsetMappingMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setCharsetMappings(curr);
      }
   }

   public void removeCharsetMapping(CharsetMappingMBean x) {
      CharsetMappingMBean[] prev = this.getCharsetMappings();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            CharsetMappingMBean[] curr = new CharsetMappingMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setCharsetMappings(curr);
         }

      }
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      boolean ok = true;
      int i;
      if (this.inputCharsets != null) {
         for(i = 0; i < this.inputCharsets.length; ++i) {
            ok &= this.check(this.inputCharsets[i]);
         }
      }

      if (this.charsetMappings != null) {
         for(i = 0; i < this.charsetMappings.length; ++i) {
            ok &= this.check(this.charsetMappings[i]);
         }
      }

      if (!ok) {
         String[] err = this.getDescriptorErrors();
         throw new DescriptorValidationException(this.arrayToString(err));
      }
   }

   public String toXML(int indent) {
      String result = "";
      boolean needed = false;
      int i;
      if (this.inputCharsets != null && this.inputCharsets.length > 0) {
         for(i = 0; i < this.inputCharsets.length; ++i) {
            result = result + this.inputCharsets[i].toXML(indent + 2);
         }

         needed = true;
      }

      if (this.charsetMappings != null && this.charsetMappings.length > 0) {
         for(i = 0; i < this.charsetMappings.length; ++i) {
            result = result + this.charsetMappings[i].toXML(indent + 2);
         }

         needed = true;
      }

      return needed ? this.indentStr(indent) + "<charset-params>\n" + result + this.indentStr(indent) + "</charset-params>\n" : "";
   }
}
