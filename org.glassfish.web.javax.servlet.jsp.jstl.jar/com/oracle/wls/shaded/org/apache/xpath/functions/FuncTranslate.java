package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncTranslate extends Function3Args {
   static final long serialVersionUID = -1672834340026116482L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      String theFirstString = this.m_arg0.execute(xctxt).str();
      String theSecondString = this.m_arg1.execute(xctxt).str();
      String theThirdString = this.m_arg2.execute(xctxt).str();
      int theFirstStringLength = theFirstString.length();
      int theThirdStringLength = theThirdString.length();
      StringBuffer sbuffer = new StringBuffer();

      for(int i = 0; i < theFirstStringLength; ++i) {
         char theCurrentChar = theFirstString.charAt(i);
         int theIndex = theSecondString.indexOf(theCurrentChar);
         if (theIndex < 0) {
            sbuffer.append(theCurrentChar);
         } else if (theIndex < theThirdStringLength) {
            sbuffer.append(theThirdString.charAt(theIndex));
         }
      }

      return new XString(sbuffer.toString());
   }
}
