package com.oracle.wls.shaded.org.apache.xalan.processor;

import com.oracle.wls.shaded.org.apache.xalan.templates.Stylesheet;
import com.oracle.wls.shaded.org.apache.xalan.templates.WhiteSpaceInfo;
import com.oracle.wls.shaded.org.apache.xpath.XPath;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class ProcessorPreserveSpace extends XSLTElementProcessor {
   static final long serialVersionUID = -5552836470051177302L;

   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes) throws SAXException {
      Stylesheet thisSheet = handler.getStylesheet();
      WhitespaceInfoPaths paths = new WhitespaceInfoPaths(thisSheet);
      this.setPropertiesFromAttributes(handler, rawName, attributes, paths);
      Vector xpaths = paths.getElements();

      for(int i = 0; i < xpaths.size(); ++i) {
         WhiteSpaceInfo wsi = new WhiteSpaceInfo((XPath)xpaths.elementAt(i), false, thisSheet);
         wsi.setUid(handler.nextUid());
         thisSheet.setPreserveSpaces(wsi);
      }

      paths.clearElements();
   }
}
