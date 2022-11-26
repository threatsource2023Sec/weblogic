package com.bea.core.repackaged.aspectj.weaver.loadtime.definition;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import org.xml.sax.SAXException;

public class SimpleAOPParser {
   private static final String ASPECTJ_ELEMENT = "aspectj";
   private static final String WEAVER_ELEMENT = "weaver";
   private static final String DUMP_ELEMENT = "dump";
   private static final String DUMP_BEFOREANDAFTER_ATTRIBUTE = "beforeandafter";
   private static final String DUMP_PERCLASSLOADERDIR_ATTRIBUTE = "perclassloaderdumpdir";
   private static final String INCLUDE_ELEMENT = "include";
   private static final String EXCLUDE_ELEMENT = "exclude";
   private static final String OPTIONS_ATTRIBUTE = "options";
   private static final String ASPECTS_ELEMENT = "aspects";
   private static final String ASPECT_ELEMENT = "aspect";
   private static final String CONCRETE_ASPECT_ELEMENT = "concrete-aspect";
   private static final String NAME_ATTRIBUTE = "name";
   private static final String SCOPE_ATTRIBUTE = "scope";
   private static final String REQUIRES_ATTRIBUTE = "requires";
   private static final String EXTEND_ATTRIBUTE = "extends";
   private static final String PRECEDENCE_ATTRIBUTE = "precedence";
   private static final String PERCLAUSE_ATTRIBUTE = "perclause";
   private static final String POINTCUT_ELEMENT = "pointcut";
   private static final String WITHIN_ATTRIBUTE = "within";
   private static final String EXPRESSION_ATTRIBUTE = "expression";
   private static final String DECLARE_ANNOTATION = "declare-annotation";
   private static final String ANNONATION_TAG = "annotation";
   private static final String ANNO_KIND_TYPE = "type";
   private static final String ANNO_KIND_METHOD = "method";
   private static final String ANNO_KIND_FIELD = "field";
   private static final String BEFORE_ELEMENT = "before";
   private static final String AFTER_ELEMENT = "after";
   private static final String AROUND_ELEMENT = "around";
   private final Definition m_definition = new Definition();
   private boolean m_inAspectJ;
   private boolean m_inWeaver;
   private boolean m_inAspects;
   private Definition.ConcreteAspect m_lastConcreteAspect;

   private SimpleAOPParser() {
   }

   public static Definition parse(URL url) throws Exception {
      InputStream in = url.openStream();
      LightXMLParser xml = new LightXMLParser();
      xml.parseFromReader(new InputStreamReader(in));
      SimpleAOPParser sap = new SimpleAOPParser();
      traverse(sap, xml);
      return sap.m_definition;
   }

   private void startElement(String qName, Map attrMap) throws Exception {
      String pointcut;
      String adviceClass;
      String adviceMethod;
      if ("aspect".equals(qName)) {
         pointcut = (String)attrMap.get("name");
         adviceClass = replaceXmlAnd((String)attrMap.get("scope"));
         adviceMethod = (String)attrMap.get("requires");
         if (!this.isNull(pointcut)) {
            this.m_definition.getAspectClassNames().add(pointcut);
            if (adviceClass != null) {
               this.m_definition.addScopedAspect(pointcut, adviceClass);
            }

            if (adviceMethod != null) {
               this.m_definition.setAspectRequires(pointcut, adviceMethod);
            }
         }
      } else if ("weaver".equals(qName)) {
         pointcut = (String)attrMap.get("options");
         if (!this.isNull(pointcut)) {
            this.m_definition.appendWeaverOptions(pointcut);
         }

         this.m_inWeaver = true;
      } else if ("concrete-aspect".equals(qName)) {
         pointcut = (String)attrMap.get("name");
         adviceClass = (String)attrMap.get("extends");
         adviceMethod = (String)attrMap.get("precedence");
         String perclause = (String)attrMap.get("perclause");
         if (!this.isNull(pointcut)) {
            this.m_lastConcreteAspect = new Definition.ConcreteAspect(pointcut, adviceClass, adviceMethod, perclause);
            this.m_definition.getConcreteAspects().add(this.m_lastConcreteAspect);
         }
      } else if ("pointcut".equals(qName) && this.m_lastConcreteAspect != null) {
         pointcut = (String)attrMap.get("name");
         adviceClass = (String)attrMap.get("expression");
         if (!this.isNull(pointcut) && !this.isNull(adviceClass)) {
            this.m_lastConcreteAspect.pointcuts.add(new Definition.Pointcut(pointcut, replaceXmlAnd(adviceClass)));
         }
      } else if ("aspectj".equals(qName)) {
         if (this.m_inAspectJ) {
            throw new Exception("Found nested <aspectj> element");
         }

         this.m_inAspectJ = true;
      } else if ("aspects".equals(qName)) {
         this.m_inAspects = true;
      } else if ("include".equals(qName) && this.m_inWeaver) {
         pointcut = this.getWithinAttribute(attrMap);
         if (!this.isNull(pointcut)) {
            this.m_definition.getIncludePatterns().add(pointcut);
         }
      } else if ("exclude".equals(qName) && this.m_inWeaver) {
         pointcut = this.getWithinAttribute(attrMap);
         if (!this.isNull(pointcut)) {
            this.m_definition.getExcludePatterns().add(pointcut);
         }
      } else if ("dump".equals(qName) && this.m_inWeaver) {
         pointcut = this.getWithinAttribute(attrMap);
         if (!this.isNull(pointcut)) {
            this.m_definition.getDumpPatterns().add(pointcut);
         }

         adviceClass = (String)attrMap.get("beforeandafter");
         if (this.isTrue(adviceClass)) {
            this.m_definition.setDumpBefore(true);
         }

         adviceMethod = (String)attrMap.get("perclassloaderdumpdir");
         if (this.isTrue(adviceMethod)) {
            this.m_definition.setCreateDumpDirPerClassloader(true);
         }
      } else if ("exclude".equals(qName) && this.m_inAspects) {
         pointcut = this.getWithinAttribute(attrMap);
         if (!this.isNull(pointcut)) {
            this.m_definition.getAspectExcludePatterns().add(pointcut);
         }
      } else if ("include".equals(qName) && this.m_inAspects) {
         pointcut = this.getWithinAttribute(attrMap);
         if (!this.isNull(pointcut)) {
            this.m_definition.getAspectIncludePatterns().add(pointcut);
         }
      } else if ("declare-annotation".equals(qName) && this.m_inAspects) {
         pointcut = (String)attrMap.get("annotation");
         if (!this.isNull(pointcut)) {
            adviceClass = (String)attrMap.get("field");
            if (adviceClass != null) {
               this.m_lastConcreteAspect.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Field, adviceClass, pointcut));
            } else {
               adviceClass = (String)attrMap.get("method");
               if (adviceClass != null) {
                  this.m_lastConcreteAspect.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Method, adviceClass, pointcut));
               } else {
                  adviceClass = (String)attrMap.get("type");
                  if (adviceClass != null) {
                     this.m_lastConcreteAspect.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Type, adviceClass, pointcut));
                  }
               }
            }
         }
      } else if ("before".equals(qName) && this.m_inAspects) {
         pointcut = (String)attrMap.get("pointcut");
         adviceClass = (String)attrMap.get("invokeClass");
         adviceMethod = (String)attrMap.get("invokeMethod");
         if (this.isNull(pointcut) || this.isNull(adviceClass) || this.isNull(adviceMethod)) {
            throw new SAXException("Badly formed <before> element");
         }

         this.m_lastConcreteAspect.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.Before, replaceXmlAnd(pointcut), adviceClass, adviceMethod));
      } else if ("after".equals(qName) && this.m_inAspects) {
         pointcut = (String)attrMap.get("pointcut");
         adviceClass = (String)attrMap.get("invokeClass");
         adviceMethod = (String)attrMap.get("invokeMethod");
         if (this.isNull(pointcut) || this.isNull(adviceClass) || this.isNull(adviceMethod)) {
            throw new SAXException("Badly formed <after> element");
         }

         this.m_lastConcreteAspect.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.After, replaceXmlAnd(pointcut), adviceClass, adviceMethod));
      } else {
         if (!"around".equals(qName) || !this.m_inAspects) {
            throw new Exception("Unknown element while parsing <aspectj> element: " + qName);
         }

         pointcut = (String)attrMap.get("pointcut");
         adviceClass = (String)attrMap.get("invokeClass");
         adviceMethod = (String)attrMap.get("invokeMethod");
         if (!this.isNull(pointcut) && !this.isNull(adviceClass) && !this.isNull(adviceMethod)) {
            this.m_lastConcreteAspect.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.Around, replaceXmlAnd(pointcut), adviceClass, adviceMethod));
         }
      }

   }

   private void endElement(String qName) throws Exception {
      if ("concrete-aspect".equals(qName)) {
         this.m_lastConcreteAspect = null;
      } else if ("aspectj".equals(qName)) {
         this.m_inAspectJ = false;
      } else if ("weaver".equals(qName)) {
         this.m_inWeaver = false;
      } else if ("aspects".equals(qName)) {
         this.m_inAspects = false;
      }

   }

   private String getWithinAttribute(Map attributes) {
      return replaceXmlAnd((String)attributes.get("within"));
   }

   private static String replaceXmlAnd(String expression) {
      return LangUtil.replace(expression, " AND ", " && ");
   }

   private boolean isNull(String s) {
      return s == null || s.length() <= 0;
   }

   private boolean isTrue(String s) {
      return s != null && s.equals("true");
   }

   private static void traverse(SimpleAOPParser sap, LightXMLParser xml) throws Exception {
      sap.startElement(xml.getName(), xml.getAttributes());
      ArrayList childrens = xml.getChildrens();

      for(int i = 0; i < childrens.size(); ++i) {
         LightXMLParser child = (LightXMLParser)childrens.get(i);
         traverse(sap, child);
      }

      sap.endElement(xml.getName());
   }
}
