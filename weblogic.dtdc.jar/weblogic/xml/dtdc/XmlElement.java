package weblogic.xml.dtdc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class XmlElement implements GeneratedElement {
   protected List dataElements = new ArrayList();
   protected Map attributeValues = new HashMap();
   protected List subElements = new ArrayList();

   public List getDataElements() {
      return this.dataElements;
   }

   public XmlElement _addDataElement(String string) {
      GeneratedElement ge = new DataElement(string);
      this.getDataElements().add(ge);
      this.getSubElements().add(ge);
      return this;
   }

   public Map getAttributeValues() {
      return this.attributeValues;
   }

   public void toXML(PrintWriter writer) throws IOException {
      this.toXML(writer, 0);
   }

   public void toXML(PrintWriter writer, int depth) throws IOException {
      for(int i = depth; i > 0; --i) {
         writer.print("  ");
      }

      writer.print("<" + this.getElementName());
      Iterator attributes = this.getAttributeValues().keySet().iterator();

      while(attributes.hasNext()) {
         String key = (String)attributes.next();
         String value = (String)this.attributeValues.get(key);
         if (value != null) {
            value = this.convert(value);
            if (value.indexOf("\"") != -1) {
               writer.print(" " + key + "='" + this.convert(value) + "'");
            } else {
               writer.print(" " + key + "=\"" + this.convert(value) + "\"");
            }
         }
      }

      if (this.isEmpty()) {
         writer.println("/>");
      } else {
         writer.println(">");
         Iterator subelements = this.getSubElements().iterator();

         while(subelements.hasNext()) {
            GeneratedElement ge = (GeneratedElement)subelements.next();
            ge.toXML(writer, depth + 1);
            if (!subelements.hasNext() && ge instanceof DataElement) {
               writer.println();
            }
         }

         for(int i = depth; i > 0; --i) {
            writer.print("  ");
         }

         writer.println("</" + this.getElementName() + ">");
      }
   }

   protected String convert(String string) {
      return string;
   }

   public List getSubElements() {
      return this.subElements;
   }

   public abstract boolean isEmpty();

   public boolean equals(Object other) {
      if (!(other instanceof XmlElement)) {
         return false;
      } else {
         XmlElement that = (XmlElement)other;
         if (!that.getElementName().equals(this.getElementName())) {
            return false;
         } else {
            Iterator subAttributes = this.getAttributeValues().keySet().iterator();
            Iterator thatSubAttributes = that.getAttributeValues().keySet().iterator();

            while(subAttributes.hasNext() && thatSubAttributes.hasNext()) {
               Object key1;
               Object key2;
               if (!(key1 = subAttributes.next()).equals(key2 = thatSubAttributes.next())) {
                  return false;
               }

               if (!this.getAttributeValues().get(key1).equals(this.getAttributeValues().get(key2))) {
                  return false;
               }
            }

            if (subAttributes.hasNext() != thatSubAttributes.hasNext()) {
               return false;
            } else {
               Iterator subElements = this.getSubElements().iterator();
               Iterator thatSubElements = that.getSubElements().iterator();

               while(subElements.hasNext() && thatSubElements.hasNext()) {
                  if (!subElements.next().equals(thatSubElements.next())) {
                     return false;
                  }
               }

               return subElements.hasNext() == thatSubElements.hasNext();
            }
         }
      }
   }
}
