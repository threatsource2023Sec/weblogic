package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.BindingCompiler;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.staxb.buildtime.internal.bts.ParentInstanceFactory;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.buildtime.internal.tylar.TylarWriter;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlObject;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;

public class Both2Bind extends BindingCompiler {
   private static final BindingTypeName SEPCIAL_BT_NAME_JAEE6 = BindingTypeName.forPair(JavaTypeName.forString("long"), XmlTypeName.forString("t=xsdIntegerType@http://java.sun.com/xml/ns/javaee"));
   private static final BindingTypeName SEPCIAL_BT_NAME_JAEE7 = BindingTypeName.forPair(JavaTypeName.forString("long"), XmlTypeName.forString("t=xsdIntegerType@http://xmlns.jcp.org/xml/ns/javaee"));
   private TypeMatcher mMatcher;
   private Map scratchFromXmlName = new LinkedHashMap();
   private Map scratchFromJavaName = new LinkedHashMap();
   private Map scratchFromBindingName = new LinkedHashMap();
   private BindingFile bindingFile = new BindingFile();
   private LinkedList resolveQueue = new LinkedList();
   private JClass[] mJavaTypes = null;
   private SchemaTypeSystem mSchemaTypes = null;

   public void setJavaTypesToMatch(JClass[] jclasses) {
      if (jclasses == null) {
         throw new IllegalArgumentException("null jclasses");
      } else {
         this.mJavaTypes = jclasses;
      }
   }

   public void setSchemaTypesToMatch(SchemaTypeSystem sts) {
      if (sts == null) {
         throw new IllegalArgumentException("null sts");
      } else {
         this.mSchemaTypes = sts;
      }
   }

   public void setTypeMatcher(TypeMatcher matcher) {
      this.mMatcher = matcher;
   }

   TypeMatcher getTypeMatcher() {
      return this.mMatcher;
   }

   protected void internalBind(TylarWriter tw) {
      this.bind();

      try {
         tw.writeBindingFile(this.bindingFile);
         tw.writeSchemaTypeSystem(this.mSchemaTypes);
      } catch (IOException var3) {
         this.logError(var3);
      }

   }

   /** @deprecated */
   @Deprecated
   public BindingFile getBindingFile() {
      return this.bindingFile;
   }

   private void bind() {
      if (this.mMatcher == null) {
         this.mMatcher = new DefaultTypeMatcher();
      }

      this.mMatcher.init(this);
      this.resolveInitiallyMatchedTypes();

      while(this.moreToResolve()) {
         Scratch scratch = this.dequeueToResolve();
         this.resolveBinding(scratch);
      }

   }

   private static XmlTypeName normalizedXmlTypeName(SchemaType sType) {
      if (sType.isDocumentType()) {
         return XmlTypeName.forGlobalName('e', sType.getDocumentElementName());
      } else {
         return sType.isAttributeType() ? XmlTypeName.forGlobalName('a', sType.getDocumentElementName()) : XmlTypeName.forSchemaType(sType);
      }
   }

   private static SchemaType computeCompatibleBuiltin(JavaTypeName javaName, SchemaType sType) {
      if (!sType.isSimpleType() && sType.getContentType() != 2) {
         return null;
      } else {
         BindingLoader builtins;
         for(builtins = BuiltinBindingLoader.getBuiltinBindingLoader(false); !sType.isSimpleType(); sType = sType.getBaseType()) {
         }

         while(sType != null) {
            if (null != builtins.getBindingType(BindingTypeName.forPair(javaName, XmlTypeName.forSchemaType(sType)))) {
               return sType;
            }

            sType = sType.getBaseType();
         }

         return null;
      }
   }

   private static boolean isCompatibleArray(JClass jClass, SchemaType sType) {
      return false;
   }

   private void resolveInitiallyMatchedTypes() {
      if (this.mJavaTypes == null) {
         throw new IllegalStateException("javaTypesToMatch was never set");
      } else if (this.mSchemaTypes == null) {
         throw new IllegalStateException("schemaTypesToMatch was never set");
      } else {
         TypeMatcher.MatchedType[] matchedTypes = this.mMatcher.matchTypes(this.mJavaTypes, this.mSchemaTypes);

         Scratch scratch;
         for(int i = 0; i < matchedTypes.length; ++i) {
            scratch = createScratch(matchedTypes[i].getJClass(), matchedTypes[i].getSType());
            BindingType result = this.getBaseBindingLoader().getBindingType(scratch.getBindingTypeName());
            if (result == null) {
               this.scratchFromBindingName.put(scratch.getBindingTypeName(), scratch);
            }
         }

         Iterator i = this.scratchIterator();

         while(i.hasNext()) {
            scratch = (Scratch)i.next();
            boolean skip = false;
            this.createBindingType(scratch, true);
            if (!this.scratchFromXmlName.containsKey(scratch.getXmlName())) {
               this.scratchFromXmlName.put(scratch.getXmlName(), scratch);
            } else {
               skip = true;
               this.logError("Both " + scratch.getJavaName() + " and " + ((Scratch)this.scratchFromXmlName.get(scratch.getXmlName())).getJavaName() + " match Schema " + scratch.getXmlName(), scratch.getJClass(), scratch.getSchemaType());
            }

            if (!skip) {
               this.queueToResolve(scratch);
            }
         }

      }
   }

   private static Scratch createScratch(JClass jClass, SchemaType sType) {
      XmlTypeName xmlName = normalizedXmlTypeName(sType);
      JavaTypeName javaName = JavaTypeName.forJClass(jClass);
      SchemaType simpleBuiltin = computeCompatibleBuiltin(javaName, sType);
      Scratch scratch;
      if (simpleBuiltin != null) {
         scratch = new Scratch(jClass, javaName, sType, xmlName, 1);
         scratch.setAsIf(XmlTypeName.forSchemaType(simpleBuiltin));
      } else if (sType.isDocumentType()) {
         scratch = new Scratch(jClass, javaName, sType, xmlName, 6);
         scratch.setAsIf(XmlTypeName.forSchemaType(sType.getProperties()[0].getType()));
      } else if (sType.isAttributeType()) {
         scratch = new Scratch(jClass, javaName, sType, xmlName, 7);
         scratch.setAsIf(XmlTypeName.forSchemaType(sType.getProperties()[0].getType()));
      } else if (isCompatibleArray(jClass, sType)) {
         scratch = new Scratch(jClass, javaName, sType, xmlName, 3);
      } else {
         scratch = new Scratch(jClass, javaName, sType, xmlName, 2);
      }

      return scratch;
   }

   private void createBindingType(Scratch scratch, boolean shouldDefault) {
      if (scratch == null) {
         throw new IllegalArgumentException("null scratch");
      } else if (scratch.getBindingType() != null) {
         throw new IllegalArgumentException("non-null scratch binding type");
      } else {
         BindingTypeName btName = BindingTypeName.forPair(scratch.getJavaName(), scratch.getXmlName());
         SimpleBindingType simpleResult;
         if (!SEPCIAL_BT_NAME_JAEE6.equals(btName) && !SEPCIAL_BT_NAME_JAEE7.equals(btName)) {
            switch (scratch.getCategory()) {
               case 1:
               case 4:
               case 7:
                  simpleResult = new SimpleBindingType(btName);
                  simpleResult.setAsIfXmlType(scratch.getAsIf());
                  scratch.setBindingType(simpleResult);
                  this.bindingFile.addBindingType(simpleResult, shouldDefault, true);
                  break;
               case 2:
                  ByNameBean byNameResult = new ByNameBean(btName);
                  scratch.setBindingType(byNameResult);
                  this.bindingFile.addBindingType(byNameResult, shouldDefault, true);
                  break;
               case 3:
                  throw new UnsupportedOperationException();
               case 5:
                  throw new UnsupportedOperationException();
               case 6:
                  SimpleDocumentBinding docResult = new SimpleDocumentBinding(btName);
                  docResult.setTypeOfElement(scratch.getAsIf());
                  scratch.setBindingType(docResult);
                  this.bindingFile.addBindingType(docResult, shouldDefault, true);
                  break;
               default:
                  throw new IllegalStateException("Unrecognized category");
            }

         } else {
            simpleResult = new SimpleBindingType(btName);
            simpleResult.setAsIfXmlType(XmlTypeName.forString("t=long@http://www.w3.org/2001/XMLSchema"));
            scratch.setBindingType(simpleResult);
            this.bindingFile.addBindingType(simpleResult, shouldDefault, true);
         }
      }
   }

   private BindingType bindingTypeForMatchedTypes(JClass jClass, SchemaType sType, TypeMatcher.MatchedProperties onBehalfOf) {
      BindingTypeName btName = BindingTypeName.forTypes(jClass, sType);
      Scratch scratch = (Scratch)this.scratchFromBindingName.get(btName);
      if (scratch != null) {
         return scratch.getBindingType();
      } else {
         BindingType result = this.getBaseBindingLoader().getBindingType(btName);
         if (result != null) {
            return result;
         } else {
            BindingTypeName newBtName = this.getBaseBindingLoader().lookupPojoFor(btName.getXmlName());
            if (newBtName != null) {
               result = this.getBaseBindingLoader().getBindingType(newBtName);
               return result;
            } else {
               scratch = createScratch(jClass, sType);
               scratch.setOnBehalfOf(onBehalfOf);
               this.createBindingType(scratch, false);
               this.queueToResolve(scratch);
               return scratch.getBindingType();
            }
         }
      }
   }

   private void queueToResolve(Scratch scratch) {
      this.resolveQueue.add(scratch);
   }

   private boolean moreToResolve() {
      return !this.resolveQueue.isEmpty();
   }

   private Scratch dequeueToResolve() {
      return (Scratch)this.resolveQueue.removeFirst();
   }

   private Iterator scratchIterator() {
      return this.scratchFromBindingName.values().iterator();
   }

   private void resolveBinding(Scratch scratch) {
      switch (scratch.getCategory()) {
         case 1:
            return;
         case 2:
            this.resolveStructure(scratch);
            return;
         case 3:
         case 4:
         case 5:
         default:
            return;
         case 6:
            this.bindingTypeForMatchedTypes(scratch.getJClass(), scratch.getSchemaType().getProperties()[0].getType(), (TypeMatcher.MatchedProperties)null);
      }
   }

   private static Set computeRequiredProperties(SchemaType sType) {
      Set result = new HashSet();
      SchemaProperty[] sProps = sType.getProperties();

      for(int i = 0; i < sProps.length; ++i) {
         if (sProps[i].getMinOccurs().signum() > 0) {
            result.add(Both2Bind.SchemaPropertyName.forProperty(sProps[i]));
         }
      }

      return result;
   }

   private void resolveStructure(Scratch scratch) {
      if (!scratch.getSchemaType().isSimpleType() && scratch.getSchemaType() != XmlObject.type) {
         TypeMatcher.MatchedProperties[] matchedProperties = this.mMatcher.matchProperties(scratch.getJClass(), scratch.getSchemaType());
         Set requiredProperties = computeRequiredProperties(scratch.getSchemaType());

         int i;
         for(i = 0; i < matchedProperties.length; ++i) {
            SchemaProperty sProp = matchedProperties[i].getSProperty();
            JProperty jProp = matchedProperties[i].getJProperty();
            requiredProperties.remove(Both2Bind.SchemaPropertyName.forProperty(sProp));
            JClass jPropType = jProp.getType();
            SchemaType sPropType = sProp.getType();
            boolean multiple = isMultiple(sProp);
            JavaTypeName collection = null;
            if (multiple) {
               if (!jPropType.isArrayType()) {
                  this.logError("Property " + jProp + " in " + scratch.getJClass() + " is a singleton, but " + sProp.getName() + " in " + scratch.getSchemaType() + " is an array.", jProp, sProp);
               } else {
                  collection = JavaTypeName.forJClass(jPropType);
                  jPropType = jPropType.getArrayComponentType();
               }
            }

            jPropType = this.mMatcher.substituteClass(jPropType);
            BindingType bType = this.bindingTypeForMatchedTypes(jPropType, sPropType, matchedProperties[i]);
            QNameProperty prop = new QNameProperty();
            prop.setQName(sProp.getName());
            prop.setAttribute(sProp.isAttribute());
            if (jProp.getSetter() == null) {
               this.logError("Property " + jProp + " has no setter in " + scratch.getJClass());
            } else {
               prop.setSetterName(MethodName.create(jProp.getSetter()));
            }

            if (jProp.getGetter() == null) {
               this.logError("Property " + jProp + " has no getter in " + scratch.getJClass());
            } else {
               prop.setGetterName(MethodName.create(jProp.getGetter()));
            }

            JMethod isSetter = matchedProperties[i].getIsSetter();
            if (isSetter != null) {
               prop.setIssetterName(MethodName.create(isSetter));
            }

            JMethod factory = matchedProperties[i].getFactoryMethod();
            if (factory != null) {
               prop.setJavaInstanceFactory(ParentInstanceFactory.forMethodName(MethodName.create(factory)));
            }

            prop.setCollectionClass(collection);
            prop.setBindingType(bType);
            prop.setNillable(sProp.hasNillable() != 0);
            prop.setOptional(isOptional(sProp));
            prop.setMultiple(multiple);
            if (sProp.getDefaultText() != null) {
               prop.setDefault(sProp.getDefaultText());
            }

            scratch.addQNameProperty(prop);
         }

         if (!requiredProperties.isEmpty()) {
            i = requiredProperties.size();
            String reason;
            if (i > 1) {
               reason = "No match for " + i + " schema element or attribute names.";
            } else {
               SchemaPropertyName spName = (SchemaPropertyName)requiredProperties.iterator().next();
               if (spName.isAttribute()) {
                  reason = "No match for required attribute " + spName.getQName().getLocalPart();
               } else {
                  reason = "No match for required element " + spName.getQName().getLocalPart();
               }
            }

            if (scratch.getOnBehalfOf() == null) {
               this.logError("Java class " + scratch.getJavaName() + " does not match schema type " + scratch.getXmlName() + " (" + reason + ")", scratch.getJClass(), scratch.getSchemaType());
            } else {
               this.logError("Java class " + scratch.getJavaName() + " does not match schema type " + scratch.getXmlName() + " (" + reason + ")", scratch.getOnBehalfOf().getJProperty(), scratch.getOnBehalfOf().getSProperty());
            }
         }

      } else {
         this.logError("Java class " + scratch.getJavaName() + " does not match Schema type " + scratch.getXmlName(), scratch.getJClass(), scratch.getSchemaType());
      }
   }

   private static boolean isMultiple(SchemaProperty sProp) {
      BigInteger max = sProp.getMaxOccurs();
      if (max == null) {
         return true;
      } else {
         return max.compareTo(BigInteger.ONE) > 0;
      }
   }

   private static boolean isOptional(SchemaProperty sProp) {
      BigInteger min = sProp.getMinOccurs();
      return min.signum() == 0;
   }

   private static class SchemaPropertyName {
      QName qName;
      boolean isAttribute;

      public static SchemaPropertyName forProperty(SchemaProperty sProp) {
         return new SchemaPropertyName(sProp.getName(), sProp.isAttribute());
      }

      private SchemaPropertyName(QName qName, boolean attribute) {
         this.qName = qName;
         this.isAttribute = attribute;
      }

      public QName getQName() {
         return this.qName;
      }

      public boolean isAttribute() {
         return this.isAttribute;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof SchemaPropertyName)) {
            return false;
         } else {
            SchemaPropertyName schemaPropertyName = (SchemaPropertyName)o;
            if (this.isAttribute != schemaPropertyName.isAttribute) {
               return false;
            } else {
               return this.qName.equals(schemaPropertyName.qName);
            }
         }
      }

      public String toString() {
         return "SchemaPropertyName:" + this.qName + ":" + this.isAttribute;
      }

      public int hashCode() {
         int result = this.qName.hashCode();
         result = 29 * result + (this.isAttribute ? 1 : 0);
         return result;
      }
   }

   private static class Scratch {
      private BindingType bindingType;
      private SchemaType schemaType;
      private JavaTypeName javaName;
      private XmlTypeName xmlName;
      private JClass jClass;
      private BindingTypeName bindingTypeName;
      private TypeMatcher.MatchedProperties onBehalfOf;
      private int category;
      private XmlTypeName asIf;
      private boolean isStructureResolved;
      public static final int ATOMIC_TYPE = 1;
      public static final int STRUCT_TYPE = 2;
      public static final int LITERALARRAY_TYPE = 3;
      public static final int SOAPARRAY_REF = 4;
      public static final int SOAPARRAY = 5;
      public static final int ELEMENT = 6;
      public static final int ATTRIBUTE = 7;

      Scratch(JClass jClass, JavaTypeName javaName, SchemaType schemaType, XmlTypeName xmlName, int category) {
         this.jClass = jClass;
         this.javaName = javaName;
         this.schemaType = schemaType;
         this.xmlName = xmlName;
         this.category = category;
         this.bindingTypeName = BindingTypeName.forPair(javaName, xmlName);
      }

      public String toString() {
         return "Scratch: " + this.javaName + ":" + this.xmlName + ":" + this.bindingType + ":" + this.category;
      }

      public int getCategory() {
         return this.category;
      }

      public JClass getJClass() {
         return this.jClass;
      }

      public JavaTypeName getJavaName() {
         return this.javaName;
      }

      public BindingTypeName getBindingTypeName() {
         return this.bindingTypeName;
      }

      public BindingType getBindingType() {
         return this.bindingType;
      }

      public void setBindingType(BindingType bindingType) {
         this.bindingType = bindingType;
      }

      public SchemaType getSchemaType() {
         return this.schemaType;
      }

      public XmlTypeName getXmlName() {
         return this.xmlName;
      }

      public XmlTypeName getAsIf() {
         return this.asIf;
      }

      public void setAsIf(XmlTypeName xmlName) {
         this.asIf = xmlName;
      }

      public void addQNameProperty(QNameProperty prop) {
         if (!(this.bindingType instanceof ByNameBean)) {
            throw new IllegalStateException();
         } else {
            ((ByNameBean)this.bindingType).addProperty(prop);
         }
      }

      public Collection getQNameProperties() {
         if (!(this.bindingType instanceof ByNameBean)) {
            throw new IllegalStateException();
         } else {
            return ((ByNameBean)this.bindingType).getProperties();
         }
      }

      public boolean isStructureResolved() {
         return this.isStructureResolved;
      }

      public void setStructureResolved(boolean isStructureResolved) {
         this.isStructureResolved = isStructureResolved;
      }

      public void setOnBehalfOf(TypeMatcher.MatchedProperties onBehalfOf) {
         this.onBehalfOf = onBehalfOf;
      }

      public TypeMatcher.MatchedProperties getOnBehalfOf() {
         return this.onBehalfOf;
      }
   }
}
