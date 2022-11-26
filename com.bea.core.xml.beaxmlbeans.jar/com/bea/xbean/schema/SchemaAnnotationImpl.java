package com.bea.xbean.schema;

import com.bea.xbean.xb.xsdschema.Annotated;
import com.bea.xbean.xb.xsdschema.AnnotationDocument;
import com.bea.xbean.xb.xsdschema.AppinfoDocument;
import com.bea.xbean.xb.xsdschema.DocumentationDocument;
import com.bea.xml.SchemaAnnotation;
import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SchemaAnnotationImpl implements SchemaAnnotation {
   private SchemaContainer _container;
   private String[] _appInfoAsXml;
   private AppinfoDocument.Appinfo[] _appInfo;
   private String[] _documentationAsXml;
   private DocumentationDocument.Documentation[] _documentation;
   private SchemaAnnotation.Attribute[] _attributes;
   private String _filename;

   public void setFilename(String filename) {
      this._filename = filename;
   }

   public String getSourceName() {
      return this._filename;
   }

   public XmlObject[] getApplicationInformation() {
      if (this._appInfo == null) {
         int n = this._appInfoAsXml.length;
         this._appInfo = new AppinfoDocument.Appinfo[n];

         for(int i = 0; i < n; ++i) {
            String appInfo = this._appInfoAsXml[i];

            try {
               this._appInfo[i] = AppinfoDocument.Factory.parse(appInfo).getAppinfo();
            } catch (XmlException var5) {
               this._appInfo[i] = AppinfoDocument.Factory.newInstance().getAppinfo();
            }
         }
      }

      return this._appInfo;
   }

   public XmlObject[] getUserInformation() {
      if (this._documentation == null) {
         int n = this._documentationAsXml.length;
         this._documentation = new DocumentationDocument.Documentation[n];

         for(int i = 0; i < n; ++i) {
            String doc = this._documentationAsXml[i];

            try {
               this._documentation[i] = DocumentationDocument.Factory.parse(doc).getDocumentation();
            } catch (XmlException var5) {
               this._documentation[i] = DocumentationDocument.Factory.newInstance().getDocumentation();
            }
         }
      }

      return this._documentation;
   }

   public SchemaAnnotation.Attribute[] getAttributes() {
      return this._attributes;
   }

   public int getComponentType() {
      return 8;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container != null ? this._container.getTypeSystem() : null;
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   public QName getName() {
      return null;
   }

   public SchemaComponent.Ref getComponentRef() {
      return null;
   }

   public static SchemaAnnotationImpl getAnnotation(SchemaContainer c, Annotated elem) {
      AnnotationDocument.Annotation ann = elem.getAnnotation();
      return getAnnotation(c, elem, ann);
   }

   public static SchemaAnnotationImpl getAnnotation(SchemaContainer c, XmlObject elem, AnnotationDocument.Annotation ann) {
      if (StscState.get().noAnn()) {
         return null;
      } else {
         SchemaAnnotationImpl result = new SchemaAnnotationImpl(c);
         ArrayList attrArray = new ArrayList(2);
         addNoSchemaAttributes(elem, attrArray);
         if (ann == null) {
            if (attrArray.size() == 0) {
               return null;
            }

            result._appInfo = new AppinfoDocument.Appinfo[0];
            result._documentation = new DocumentationDocument.Documentation[0];
         } else {
            result._appInfo = ann.getAppinfoArray();
            result._documentation = ann.getDocumentationArray();
            addNoSchemaAttributes(ann, attrArray);
         }

         result._attributes = (AttributeImpl[])((AttributeImpl[])attrArray.toArray(new AttributeImpl[attrArray.size()]));
         return result;
      }
   }

   private static void addNoSchemaAttributes(XmlObject elem, List attrList) {
      XmlCursor cursor = elem.newCursor();

      for(boolean hasAttributes = cursor.toFirstAttribute(); hasAttributes; hasAttributes = cursor.toNextAttribute()) {
         QName name = cursor.getName();
         String namespaceURI = name.getNamespaceURI();
         if (!"".equals(namespaceURI) && !"http://www.w3.org/2001/XMLSchema".equals(namespaceURI)) {
            String attValue = cursor.getTextValue();
            String prefix;
            if (attValue.indexOf(58) > 0) {
               prefix = attValue.substring(0, attValue.indexOf(58));
            } else {
               prefix = "";
            }

            cursor.push();
            cursor.toParent();
            String valUri = cursor.namespaceForPrefix(prefix);
            cursor.pop();
            attrList.add(new AttributeImpl(name, attValue, valUri));
         }
      }

      cursor.dispose();
   }

   private SchemaAnnotationImpl(SchemaContainer c) {
      this._container = c;
   }

   SchemaAnnotationImpl(SchemaContainer c, String[] aapStrings, String[] adocStrings, SchemaAnnotation.Attribute[] aat) {
      this._container = c;
      this._appInfoAsXml = aapStrings;
      this._documentationAsXml = adocStrings;
      this._attributes = aat;
   }

   static class AttributeImpl implements SchemaAnnotation.Attribute {
      private QName _name;
      private String _value;
      private String _valueUri;

      AttributeImpl(QName name, String value, String valueUri) {
         this._name = name;
         this._value = value;
         this._valueUri = valueUri;
      }

      public QName getName() {
         return this._name;
      }

      public String getValue() {
         return this._value;
      }

      public String getValueUri() {
         return this._valueUri;
      }
   }
}
