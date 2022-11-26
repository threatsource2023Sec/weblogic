package com.oracle.wls.shaded.org.apache.xalan.lib;

import com.oracle.wls.shaded.org.apache.xalan.extensions.ExpressionContext;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMNodeIterator;
import com.oracle.wls.shaded.org.apache.xpath.NodeSet;
import com.oracle.wls.shaded.org.apache.xpath.axes.RTFIterator;

public class ExsltCommon {
   public static String objectType(Object obj) {
      if (obj instanceof String) {
         return "string";
      } else if (obj instanceof Boolean) {
         return "boolean";
      } else if (obj instanceof Number) {
         return "number";
      } else if (obj instanceof DTMNodeIterator) {
         DTMIterator dtmI = ((DTMNodeIterator)obj).getDTMIterator();
         return dtmI instanceof RTFIterator ? "RTF" : "node-set";
      } else {
         return "unknown";
      }
   }

   public static NodeSet nodeSet(ExpressionContext myProcessor, Object rtf) {
      return Extensions.nodeset(myProcessor, rtf);
   }
}
