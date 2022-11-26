package com.bea.staxb.buildtime.internal.bts;

import com.bea.ns.staxb.bindingConfig.x90.BindingConfigDocument;
import com.bea.ns.staxb.bindingConfig.x90.BindingTable;
import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.ns.staxb.bindingConfig.x90.Mapping;
import com.bea.ns.staxb.bindingConfig.x90.MappingTable;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BindingFileUtils {
   private BindingFileUtils() {
   }

   public static BindingFile forDoc(BindingConfigDocument doc) {
      BindingFile bindingFile = new BindingFile();
      fillBindingFileFromNode(bindingFile, doc);
      return bindingFile;
   }

   static void fillBindingFileFromNode(BindingFile bindingFile, BindingConfigDocument doc) {
      validateDoc(doc);
      com.bea.ns.staxb.bindingConfig.x90.BindingType[] btNodes = doc.getBindingConfig().getBindings().getBindingTypeArray();

      for(int i = 0; i < btNodes.length; ++i) {
         BindingType next = TypeRegistry.loadFromBindingTypeNode(btNodes[i]);
         bindingFile.addBindingType(next, false, false);
      }

      Mapping[] mNodes = doc.getBindingConfig().getJavaToXml().getMappingArray();

      JavaTypeName jName;
      XmlTypeName xName;
      int i;
      for(i = 0; i < mNodes.length; ++i) {
         jName = JavaTypeName.forString(mNodes[i].getJavatype());
         xName = XmlTypeName.forString(mNodes[i].getXmlcomponent());
         bindingFile.addTypeFor(jName, BindingTypeName.forPair(jName, xName));
      }

      mNodes = doc.getBindingConfig().getJavaToElement().getMappingArray();

      for(i = 0; i < mNodes.length; ++i) {
         jName = JavaTypeName.forString(mNodes[i].getJavatype());
         xName = XmlTypeName.forString(mNodes[i].getXmlcomponent());
         bindingFile.addElementFor(jName, BindingTypeName.forPair(jName, xName));
      }

      mNodes = doc.getBindingConfig().getXmlToPojo().getMappingArray();

      for(i = 0; i < mNodes.length; ++i) {
         jName = JavaTypeName.forString(mNodes[i].getJavatype());
         xName = XmlTypeName.forString(mNodes[i].getXmlcomponent());
         bindingFile.addPojoFor(xName, BindingTypeName.forPair(jName, xName));
      }

      mNodes = doc.getBindingConfig().getXmlToXmlobj().getMappingArray();

      for(i = 0; i < mNodes.length; ++i) {
         jName = JavaTypeName.forString(mNodes[i].getJavatype());
         xName = XmlTypeName.forString(mNodes[i].getXmlcomponent());
         bindingFile.addXmlObjectFor(xName, BindingTypeName.forPair(jName, xName));
      }

   }

   static void validateDoc(BindingConfigDocument doc) {
      List errors = new ArrayList();
      if (!doc.validate((new XmlOptions()).setErrorListener(errors))) {
         Iterator errorIterator = errors.iterator();

         while(errorIterator.hasNext()) {
            System.out.println("Parsing Error " + errorIterator.next());
         }

         throw new IllegalStateException(errors.size() > 0 ? errors.get(0).toString() : "Invalid binding-config document");
      }
   }

   public static BindingConfigDocument write(BindingFile bindingFile) throws IOException {
      SchemaTypeLoader loader = XmlBeans.typeLoaderForClassLoader(BindingConfigDocument.class.getClassLoader());
      BindingConfigDocument doc = (BindingConfigDocument)loader.newInstance(BindingConfigDocument.type, (XmlOptions)null);
      writeIntoNode(doc, bindingFile);
      return doc;
   }

   static void writeIntoNode(BindingConfigDocument doc, BindingFile bindingFile) {
      if (doc.getBindingConfig() != null) {
         throw new IllegalArgumentException("Can only write into empty doc");
      } else {
         BindingConfigDocument.BindingConfig bcNode = doc.addNewBindingConfig();
         BindingTable btabNode = bcNode.addNewBindings();
         MappingTable typetabNode = bcNode.addNewJavaToXml();
         MappingTable elementtabNode = bcNode.addNewJavaToElement();
         MappingTable pojotabNode = bcNode.addNewXmlToPojo();
         MappingTable xotabNode = bcNode.addNewXmlToXmlobj();
         Iterator i = bindingFile.bindingTypes().iterator();

         while(i.hasNext()) {
            BindingType bType = (BindingType)i.next();
            com.bea.ns.staxb.bindingConfig.x90.BindingType btNode = btabNode.addNewBindingType();
            TypeRegistry.writeBindingType(bType, btNode);
         }

         i = bindingFile.typeMappedJavaTypes().iterator();

         Mapping mNode;
         JavaTypeName jName;
         BindingTypeName pair;
         while(i.hasNext()) {
            jName = (JavaTypeName)i.next();
            pair = bindingFile.lookupTypeFor(jName);
            mNode = typetabNode.addNewMapping();
            mNode.setJavatype(jName.toString());
            mNode.setXmlcomponent(pair.getXmlName().toString());
         }

         i = bindingFile.elementMappedJavaTypes().iterator();

         while(i.hasNext()) {
            jName = (JavaTypeName)i.next();
            pair = bindingFile.lookupElementFor(jName);
            mNode = elementtabNode.addNewMapping();
            mNode.setJavatype(jName.toString());
            mNode.setXmlcomponent(pair.getXmlName().toString());
         }

         i = bindingFile.pojoMappedXmlTypes().iterator();

         XmlTypeName xName;
         while(i.hasNext()) {
            xName = (XmlTypeName)i.next();
            pair = bindingFile.lookupPojoFor(xName);
            mNode = pojotabNode.addNewMapping();
            mNode.setJavatype(pair.getJavaName().toString());
            mNode.setXmlcomponent(xName.toString());
         }

         i = bindingFile.xmlObjectMappedXmlTypes().iterator();

         while(i.hasNext()) {
            xName = (XmlTypeName)i.next();
            pair = bindingFile.lookupXmlObjectFor(xName);
            mNode = xotabNode.addNewMapping();
            mNode.setJavatype(pair.getJavaName().toString());
            mNode.setXmlcomponent(xName.toString());
         }

      }
   }

   static MethodName create(JavaMethodName jmn) {
      return jmn == null ? null : MethodName.create(jmn.getMethodName(), MethodName.namesForStrings(jmn.getParamTypeArray()));
   }
}
