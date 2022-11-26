package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.internal.annobeans.ClassBindingInfoBean;
import com.bea.staxb.buildtime.internal.annobeans.MethodBindingInfoBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetFacetBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetSchemaPropertyBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetSchemaTypeBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetTopLevelElementBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.JamElementIdPool;
import com.bea.util.annogen.override.StoredAnnoOverrider;
import com.bea.util.annogen.override.StoredAnnoOverrider.Factory;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.ArrayList;
import java.util.List;

public class JavadocTagProcessor {
   public static final String TAG_CT = "xsdgen:complexType";
   public static final String TAG_CT_EXCLUDE = "xsdgen:complexType.exclude";
   public static final String TAG_CT_TYPENAME = "xsdgen:complexType.typeName";
   public static final String TAG_CT_TARGETNS = "xsdgen:complexType.targetNamespace";
   public static final String TAG_CT_ROOT = "xsdgen:complexType.rootElement";
   public static final String TAG_CT_IGNORESUPER = "xsdgen:complexType.ignoreSuper";
   public static final String TAG_EL = "xsdgen:element";
   public static final String TAG_EL_NAME = "xsdgen:element.name";
   public static final String TAG_EL_REQUIRED = "xsdgen:element.required";
   public static final String TAG_EL_NILLABLE = "xsdgen:element.nillable";
   public static final String TAG_EL_EXCLUDE = "xsdgen:element.exclude";
   public static final String TAG_EL_ASTYPE = "xsdgen:element.astype";
   public static final String TAG_AT = "xsdgen:attribute";
   public static final String TAG_AT_NAME = "xsdgen:attribute.name";
   public static final String TAG_AT_FORM = "xsdgen:attribute.form";
   public static final String TAG_ISSETTER = "xsdgen:isSetMethodFor";
   private static final String TAG_FACORYMETHOD = "xsdgen:factoryMethodFor";
   public static final String TAG_MIN_INCLUSIVE = "xsdgen:element.minInclusive";
   public static final String TAG_MIN_EXCLUSIVE = "xsdgen:element.minExclusive";
   public static final String TAG_MAX_INCLUSIVE = "xsdgen:element.maxInclusive";
   public static final String TAG_MAX_EXCLUSIVE = "xsdgen:element.maxExclusive";
   public static final String TAG_ENUMERATION = "xsdgen:element.enumeration";
   public static final String TAG_DEFAULT = "xsdgen:element.default";
   public static final String TAG_MIN_LENGTH = "xsdgen:element.minLength";
   private StoredAnnoOverrider mOverrides = Factory.create();
   private JamElementIdPool mIdFactory = com.bea.util.annogen.override.JamElementIdPool.Factory.create();

   public AnnoOverrider getOverrides() {
      return this.mOverrides;
   }

   public void translateTagsFor(JClass clazz) {
      JAnnotation[] tags = clazz.getAllJavadocTags();
      if (tags != null && tags.length != 0) {
         ElementId id = this.mIdFactory.getIdFor(clazz);
         AnnoBeanSet set = this.mOverrides.findOrCreateStoredAnnoSetFor(id);
         ClassBindingInfoBean cbi = (ClassBindingInfoBean)set.findOrCreateBeanFor(ClassBindingInfoBean.class);
         if (this.getAnnotation(clazz, "xsdgen:complexType.exclude", false)) {
            cbi.set_isExclude(true);
         }

         if (this.getAnnotation(clazz, "xsdgen:complexType.ignoreSuper", false)) {
            cbi.set_isIgnoreSuperclass(true);
         }

         TargetSchemaTypeBean tst = cbi.schemaType();
         if (tst == null) {
            cbi.set_schemaType(tst = new TargetSchemaTypeBean());
         }

         tst.set_localName(this.getAnnotation(clazz, "xsdgen:complexType.typeName", (String)null));
         tst.set_namespaceUri(this.getAnnotation(clazz, "xsdgen:complexType.targetNamespace", (String)null));
         String rootName = this.getAnnotation(clazz, "xsdgen:complexType.rootElement", (String)null);
         if (rootName != null && rootName.length() > 0) {
            TargetTopLevelElementBean elem = new TargetTopLevelElementBean();
            elem.set_localName(rootName);
            tst.set_topLevelElements(new TargetTopLevelElementBean[]{elem});
         }

         JMethod[] methods = clazz.getDeclaredMethods();

         for(int i = 0; i < methods.length; ++i) {
            this.translateTagsFor(methods[i]);
         }

      }
   }

   private void translateTagsFor(JMethod method) {
      JAnnotation[] tags = method.getAllJavadocTags();
      if (tags != null && tags.length != 0) {
         ElementId id = this.mIdFactory.getIdFor(method);
         AnnoBeanSet set = this.mOverrides.findOrCreateStoredAnnoSetFor(id);
         MethodBindingInfoBean mbi = (MethodBindingInfoBean)set.findOrCreateBeanFor(MethodBindingInfoBean.class);
         mbi.set_defaultCheckerFor(this.getAnnotation(method, "xsdgen:isSetMethodFor", (String)null));
         mbi.set_factoryFor(this.getAnnotation(method, "xsdgen:factoryMethodFor", (String)null));
         mbi.set_isExclude(this.getAnnotation(method, "xsdgen:element.exclude", false));
         TargetSchemaPropertyBean prop = new TargetSchemaPropertyBean();
         mbi.set_schemaProperty(prop);
         prop.set_isRequired(this.getAnnotation(method, "xsdgen:element.required", false));
         prop.set_isNillable(this.getAnnotation(method, "xsdgen:element.nillable", false));
         mbi.set_asTypeNamed(this.getAnnotation(method, "xsdgen:element.astype", (String)null));
         String name = this.getAnnotation(method, "xsdgen:attribute.name", (String)null);
         if (name != null) {
            prop.set_localName(name);
            prop.set_isAttribute(true);
         } else if ((name = this.getAnnotation(method, "xsdgen:element.name", (String)null)) != null) {
            prop.set_localName(name);
            prop.set_isAttribute(false);
         }

         List facets = null;

         for(int i = 0; i < tags.length; ++i) {
            if (tags[i].getQualifiedName().equals("xsdgen:element.default")) {
               prop.set_defaultValue(val(tags[i]));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.minInclusive")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(4, val(tags[i])));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.minExclusive")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(3, val(tags[i])));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.maxInclusive")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(5, val(tags[i])));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.maxExclusive")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(6, val(tags[i])));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.enumeration")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(11, val(tags[i])));
            } else if (tags[i].getQualifiedName().equals("xsdgen:element.minLength")) {
               if (facets == null) {
                  facets = new ArrayList();
               }

               facets.add(createFacet(1, val(tags[i])));
            }
         }

         if (facets != null) {
            TargetFacetBean[] beans = new TargetFacetBean[facets.size()];
            facets.toArray(beans);
            prop.set_facets(beans);
         }

      }
   }

   private static final TargetFacetBean createFacet(int typeNum, String val) {
      TargetFacetBean out = new TargetFacetBean();
      out.set_typeNum(typeNum);
      out.set_value(val);
      return out;
   }

   private static final String val(JAnnotation tag) {
      String out = tag.getJavadocTagText();
      return out == null ? out : out.trim();
   }

   private String getAnnotation(JAnnotatedElement elem, String annName, String dflt) {
      JAnnotation ann = this.getAnnotation(elem, annName);
      if (ann == null) {
         return dflt;
      } else {
         JAnnotationValue val = ann.getValue("value");
         return val == null ? dflt : val.asString();
      }
   }

   private boolean getAnnotation(JAnnotatedElement elem, String annName, boolean dflt) {
      JAnnotation ann = this.getAnnotation(elem, annName);
      if (ann == null) {
         return dflt;
      } else {
         JAnnotationValue val = ann.getValue("value");
         return val != null && val.asString().length() != 0 ? val.asBoolean() : true;
      }
   }

   private JAnnotation getAnnotation(JAnnotatedElement e, String named) {
      return e.getAnnotation(named);
   }
}
