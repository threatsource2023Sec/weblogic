package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaIntegerHolderEx;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.AllNNI;
import com.bea.xbean.xb.xsdschema.NarrowMaxMin;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlNonNegativeInteger;

public class NarrowMaxMinImpl extends LocalElementImpl implements NarrowMaxMin {
   public NarrowMaxMinImpl(SchemaType sType) {
      super(sType);
   }

   public static class MaxOccursImpl extends XmlUnionImpl implements NarrowMaxMin.MaxOccurs, XmlNonNegativeInteger, AllNNI.Member {
      public MaxOccursImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MaxOccursImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class MinOccursImpl extends JavaIntegerHolderEx implements NarrowMaxMin.MinOccurs {
      public MinOccursImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MinOccursImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
