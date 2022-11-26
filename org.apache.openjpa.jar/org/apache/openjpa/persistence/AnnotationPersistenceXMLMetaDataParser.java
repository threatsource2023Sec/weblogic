package org.apache.openjpa.persistence;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPriv5Helper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.XMLMetaData;

public class AnnotationPersistenceXMLMetaDataParser {
   private static final Localizer _loc = Localizer.forPackage(AnnotationPersistenceXMLMetaDataParser.class);
   private final OpenJPAConfiguration _conf;
   private final Log _log;
   private MetaDataRepository _repos = null;
   private Class _cls = null;
   private FieldMetaData _fmd = null;
   private Class xmlTypeClass = null;
   private Class xmlRootElementClass = null;
   private Class xmlAccessorTypeClass = null;
   private Class xmlAttributeClass = null;
   private Class xmlElementClass = null;
   private Method xmlTypeName = null;
   private Method xmlTypeNamespace = null;
   private Method xmlRootName = null;
   private Method xmlRootNamespace = null;
   private Method xmlAttributeName = null;
   private Method xmlAttributeNamespace = null;
   private Method xmlElementName = null;
   private Method xmlElementNamespace = null;
   private Method xmlAccessorValue = null;

   public AnnotationPersistenceXMLMetaDataParser(OpenJPAConfiguration conf) {
      this._conf = conf;
      this._log = conf.getLog("openjpa.MetaData");

      try {
         this.xmlTypeClass = Class.forName("javax.xml.bind.annotation.XmlType");
         this.xmlTypeName = this.xmlTypeClass.getMethod("name", (Class[])null);
         this.xmlTypeNamespace = this.xmlTypeClass.getMethod("namespace", (Class[])null);
         this.xmlRootElementClass = Class.forName("javax.xml.bind.annotation.XmlRootElement");
         this.xmlRootName = this.xmlRootElementClass.getMethod("name", (Class[])null);
         this.xmlRootNamespace = this.xmlRootElementClass.getMethod("namespace", (Class[])null);
         this.xmlAccessorTypeClass = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
         this.xmlAccessorValue = this.xmlAccessorTypeClass.getMethod("value", (Class[])null);
         this.xmlAttributeClass = Class.forName("javax.xml.bind.annotation.XmlAttribute");
         this.xmlAttributeName = this.xmlAttributeClass.getMethod("name", (Class[])null);
         this.xmlAttributeNamespace = this.xmlAttributeClass.getMethod("namespace", (Class[])null);
         this.xmlElementClass = Class.forName("javax.xml.bind.annotation.XmlElement");
         this.xmlElementName = this.xmlElementClass.getMethod("name", (Class[])null);
         this.xmlElementNamespace = this.xmlElementClass.getMethod("namespace", (Class[])null);
      } catch (Exception var3) {
      }

   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public Log getLog() {
      return this._log;
   }

   public MetaDataRepository getRepository() {
      if (this._repos == null) {
         MetaDataRepository repos = this._conf.newMetaDataRepositoryInstance();
         MetaDataFactory mdf = repos.getMetaDataFactory();
         if (mdf instanceof DelegatingMetaDataFactory) {
            mdf = ((DelegatingMetaDataFactory)mdf).getInnermostDelegate();
         }

         if (mdf instanceof PersistenceMetaDataFactory) {
            ((PersistenceMetaDataFactory)mdf).setXMLAnnotationParser(this);
         }

         this._repos = repos;
      }

      return this._repos;
   }

   public void setRepository(MetaDataRepository repos) {
      this._repos = repos;
   }

   public void clear() {
      this._cls = null;
      this._fmd = null;
   }

   public void parse(FieldMetaData fmd) {
      this._fmd = fmd;
      this._cls = fmd.getDeclaredType();
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("parse-class", (Object)this._cls.getName()));
      }

      try {
         this.parseXMLClassAnnotations();
      } finally {
         this._cls = null;
         this._fmd = null;
      }

   }

   private XMLMetaData parseXMLClassAnnotations() {
      if (this._cls != null && this.xmlTypeClass != null && (Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(this._cls, this.xmlTypeClass)) && (Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(this._cls, this.xmlRootElementClass))) {
         XMLMetaData meta = this.getXMLMetaData();
         return meta;
      } else {
         return null;
      }
   }

   private synchronized XMLMetaData getXMLMetaData() {
      XMLMetaData meta = this.getRepository().getCachedXMLMetaData(this._cls);
      if (meta == null) {
         meta = this.getRepository().addXMLMetaData(this._cls, this._fmd.getName());
         this.parseXmlRootElement(this._cls, (XMLMetaData)meta);
         this.populateFromReflection(this._cls, (XMLMetaData)meta);
      }

      return (XMLMetaData)meta;
   }

   private void parseXmlRootElement(Class type, XMLMetaData meta) {
      try {
         if (type.getAnnotation(this.xmlRootElementClass) != null) {
            meta.setXmlRootElement(true);
            meta.setXmlname((String)this.xmlRootName.invoke(type.getAnnotation(this.xmlRootElementClass)));
            meta.setXmlnamespace((String)this.xmlRootNamespace.invoke(type.getAnnotation(this.xmlRootElementClass)));
         } else {
            meta.setXmlname((String)this.xmlTypeName.invoke(type.getAnnotation(this.xmlTypeClass)));
            meta.setXmlnamespace((String)this.xmlTypeNamespace.invoke(type.getAnnotation(this.xmlTypeClass)));
         }
      } catch (Exception var4) {
      }

   }

   private void populateFromReflection(Class cls, XMLMetaData meta) {
      Class superclass = cls.getSuperclass();
      if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(superclass, this.xmlTypeClass))) {
         this.populateFromReflection(superclass, meta);
      }

      try {
         Object members;
         if (StringUtils.equals(this.xmlAccessorValue.invoke(cls.getAnnotation(this.xmlAccessorTypeClass)).toString(), "FIELD")) {
            members = cls.getDeclaredFields();
         } else {
            members = cls.getDeclaredMethods();
         }

         for(int i = 0; i < ((Object[])members).length; ++i) {
            Member member = ((Object[])members)[i];
            AnnotatedElement el = (AnnotatedElement)member;
            XMLMetaData field = null;
            String xmlname;
            if (el.getAnnotation(this.xmlElementClass) != null) {
               xmlname = (String)this.xmlElementName.invoke(el.getAnnotation(this.xmlElementClass));
               if (StringUtils.equals("##default", xmlname)) {
                  xmlname = ((Member)member).getName();
               }

               if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(((Field)member).getType(), this.xmlTypeClass))) {
                  field = this._repos.addXMLMetaData(((Field)member).getType(), ((Member)member).getName());
                  this.parseXmlRootElement(((Field)member).getType(), (XMLMetaData)field);
                  this.populateFromReflection(((Field)member).getType(), (XMLMetaData)field);
                  ((XMLMetaData)field).setXmltype(0);
                  ((XMLMetaData)field).setXmlname(xmlname);
               } else {
                  field = this._repos.newXMLFieldMetaData(((Field)member).getType(), ((Member)member).getName());
                  ((XMLMetaData)field).setXmltype(1);
                  ((XMLMetaData)field).setXmlname(xmlname);
                  ((XMLMetaData)field).setXmlnamespace((String)this.xmlElementNamespace.invoke(el.getAnnotation(this.xmlElementClass)));
               }
            } else if (el.getAnnotation(this.xmlAttributeClass) != null) {
               field = this._repos.newXMLFieldMetaData(((Field)member).getType(), ((Member)member).getName());
               ((XMLMetaData)field).setXmltype(2);
               xmlname = (String)this.xmlAttributeName.invoke(el.getAnnotation(this.xmlAttributeClass));
               if (StringUtils.equals("##default", xmlname)) {
                  xmlname = ((Member)member).getName();
               }

               ((XMLMetaData)field).setXmlname("@" + xmlname);
               ((XMLMetaData)field).setXmlnamespace((String)this.xmlAttributeNamespace.invoke(el.getAnnotation(this.xmlAttributeClass)));
            }

            if (field != null) {
               meta.addField(((Member)member).getName(), (XMLMetaData)field);
            }
         }
      } catch (Exception var10) {
      }

   }
}
