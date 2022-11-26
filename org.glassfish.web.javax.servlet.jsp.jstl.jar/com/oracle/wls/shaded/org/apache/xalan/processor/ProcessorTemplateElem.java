package com.oracle.wls.shaded.org.apache.xalan.processor;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import javax.xml.transform.TransformerException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProcessorTemplateElem extends XSLTElementProcessor {
   static final long serialVersionUID = 8344994001943407235L;

   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes) throws SAXException {
      super.startElement(handler, uri, localName, rawName, attributes);

      try {
         XSLTElementDef def = this.getElemDef();
         Class classObject = def.getClassObject();
         ElemTemplateElement elem = null;

         try {
            elem = (ElemTemplateElement)classObject.newInstance();
            elem.setDOMBackPointer(handler.getOriginatingNode());
            elem.setLocaterInfo(handler.getLocator());
            elem.setPrefixes(handler.getNamespaceSupport());
         } catch (InstantiationException var10) {
            handler.error("ER_FAILED_CREATING_ELEMTMPL", (Object[])null, var10);
         } catch (IllegalAccessException var11) {
            handler.error("ER_FAILED_CREATING_ELEMTMPL", (Object[])null, var11);
         }

         this.setPropertiesFromAttributes(handler, rawName, attributes, elem);
         this.appendAndPush(handler, elem);
      } catch (TransformerException var12) {
         throw new SAXException(var12);
      }
   }

   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem) throws SAXException {
      ElemTemplateElement parent = handler.getElemTemplateElement();
      if (null != parent) {
         parent.appendChild(elem);
         handler.pushElemTemplateElement(elem);
      }

   }

   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName) throws SAXException {
      super.endElement(handler, uri, localName, rawName);
      handler.popElemTemplateElement().setEndLocaterInfo(handler.getLocator());
   }
}
