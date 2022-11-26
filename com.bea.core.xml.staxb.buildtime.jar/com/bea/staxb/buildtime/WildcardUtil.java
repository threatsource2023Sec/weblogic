package com.bea.staxb.buildtime;

import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import java.util.HashSet;
import java.util.Set;
import javax.xml.soap.SOAPElement;

public class WildcardUtil {
   public static final int NO_SINGLE_WILDCARD = 0;
   public static final int IS_ELEMENT_WILDCARD = 1;
   public static final int IS_ELEMENT_WILDCARD_UNBOUNDED = 2;
   public static final int IS_ANYTYPE = 3;
   public static final int IS_ANYTYPE_UNBOUNDED = 4;
   public static final String SOAPELEMENT_CLASSNAME = SOAPElement.class.getName();
   public static final String SOAPELEMENT_ARRAY_CLASSNAME;
   public static final String BEA_XMLOBJECT_CLASSNAME = "com.bea.xml.XmlObject";
   public static final String BEA_XMLOBJECT_ARRAY_CLASSNAME = "com.bea.xml.XmlObject[]";
   public static final String APACHE_XMLOBJECT_CLASSNAME = "org.apache.xmlbeans.XmlObject";
   public static final String APACHE_XMLOBJECT_ARRAY_CLASSNAME = "org.apache.xmlbeans.XmlObject[]";
   public static final Set WILDCARD_CLASSNAMES;

   private WildcardUtil() {
   }

   public static int schemaTypeIsSingleWildcard(SchemaType st) {
      SchemaProperty[] props = st.getElementProperties();
      if (props.length == 1) {
         SchemaType propSt = props[0].getType();
         if (propSt.getBuiltinTypeCode() == 1) {
            if (props[0].getMaxOccurs() != null && props[0].getMaxOccurs().intValue() <= 1) {
               return 3;
            }

            return 4;
         }
      } else if (props.length == 0) {
         SchemaParticle sp = st.getContentModel();
         if (sp != null && sp.countOfParticleChild() == 0 && sp.getParticleType() == 5) {
            if (sp.getMaxOccurs() != null && sp.getMaxOccurs().intValue() <= 1) {
               return 1;
            }

            return 2;
         }
      }

      return 0;
   }

   private static void p(String s) {
      System.err.println("[WildcardUtil] " + s);
   }

   static {
      SOAPELEMENT_ARRAY_CLASSNAME = SOAPELEMENT_CLASSNAME + "[]";
      WILDCARD_CLASSNAMES = new HashSet();
      WILDCARD_CLASSNAMES.add(SOAPELEMENT_CLASSNAME);
      WILDCARD_CLASSNAMES.add(SOAPELEMENT_ARRAY_CLASSNAME);
      WILDCARD_CLASSNAMES.add("com.bea.xml.XmlObject");
      WILDCARD_CLASSNAMES.add("com.bea.xml.XmlObject[]");
      WILDCARD_CLASSNAMES.add("org.apache.xmlbeans.XmlObject");
      WILDCARD_CLASSNAMES.add("org.apache.xmlbeans.XmlObject[]");
   }
}
