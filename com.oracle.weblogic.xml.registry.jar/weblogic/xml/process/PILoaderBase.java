package weblogic.xml.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.Debug;
import weblogic.utils.collections.MultiMap;

public abstract class PILoaderBase {
   private static final boolean debug = false;
   private MultiMap allPaths = new MultiMap();

   protected void setDTD(String dtdURL) throws SAXProcessorException {
      throw new SAXProcessorException("Not implemented yet");
   }

   protected void setDTD(InputStream dtdResource) throws IOException, DTDParsingException {
      Debug.assertion(dtdResource != null);
      InputStreamReader r = new InputStreamReader(dtdResource);
      DTDInfo dtd = new DTDInfo(r);
      this.addPaths((StringBuffer)null, dtd.getNodeTree());
   }

   protected Collection getPaths(String elementName) {
      Debug.assertion(elementName != null);
      return this.allPaths.get(elementName);
   }

   protected boolean isInDTD(String elementName) {
      return this.allPaths.get(elementName) != null;
   }

   protected String[] getPathsFromContext(String elementName, String context) throws SAXProcessorException {
      Debug.assertion(elementName != null);
      List matches = new ArrayList();
      String pathFrag = null;
      if (context != null && context.length() != 0) {
         pathFrag = "." + context + "." + elementName + ".";
      } else {
         pathFrag = "." + elementName + ".";
      }

      Collection c = this.getPaths(elementName);
      if (c == null) {
         return null;
      } else {
         Iterator i = c.iterator();

         while(i.hasNext()) {
            String path = (String)i.next();
            if (path.endsWith(pathFrag)) {
               matches.add(path);
            }
         }

         if (matches.size() > 1) {
         }

         return (String[])((String[])matches.toArray(new String[0]));
      }
   }

   private void addPaths(StringBuffer sbuf, DTDInfo.DTDNode n) {
      if (sbuf == null) {
         sbuf = new StringBuffer(".");
      } else {
         sbuf = new StringBuffer(sbuf.toString());
      }

      String name = n.getName();
      sbuf.append(name + ".");
      this.allPaths.put(name, sbuf.toString());
      String textNodeTagName = "#text";
      String textNodePath = sbuf.toString() + textNodeTagName + ".";
      this.allPaths.put(textNodeTagName, textNodePath);
      Iterator i = n.getChildren().iterator();

      while(i.hasNext()) {
         this.addPaths(sbuf, (DTDInfo.DTDNode)i.next());
      }

   }
}
