package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlNonNegativeInteger;
import org.apache.xmlbeans.impl.values.JavaIntegerHolderEx;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.AllNNI;

public class AllImpl extends ExplicitGroupImpl implements All {
   public AllImpl(SchemaType sType) {
      super(sType);
   }

   public static class MaxOccursImpl extends XmlUnionImpl implements All.MaxOccurs, XmlNonNegativeInteger, AllNNI.Member {
      public MaxOccursImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MaxOccursImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class MinOccursImpl extends JavaIntegerHolderEx implements All.MinOccurs {
      public MinOccursImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MinOccursImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
