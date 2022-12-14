package com.oracle.wls.shaded.org.apache.xpath.objects;

import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;

class LessThanComparator extends Comparator {
   boolean compareStrings(XMLString s1, XMLString s2) {
      return s1.toDouble() < s2.toDouble();
   }

   boolean compareNumbers(double n1, double n2) {
      return n1 < n2;
   }
}
