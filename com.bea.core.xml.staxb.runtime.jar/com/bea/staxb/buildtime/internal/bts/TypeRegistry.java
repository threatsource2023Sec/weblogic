package com.bea.staxb.buildtime.internal.bts;

import com.bea.ns.staxb.bindingConfig.x90.AsXmlType;
import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.ns.staxb.bindingConfig.x90.ListArray;
import com.bea.ns.staxb.bindingConfig.x90.Mapping;
import com.bea.ns.staxb.bindingConfig.x90.QnameProperty;
import com.bea.ns.staxb.bindingConfig.x90.SimpleType;
import com.bea.ns.staxb.bindingConfig.x90.SoapArray;
import com.bea.ns.staxb.bindingConfig.x90.WrappedArray;
import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.util.Iterator;

final class TypeRegistry {
   private TypeRegistry() {
   }

   static BindingType loadFromBindingTypeNode(com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      Object retval;
      if (node instanceof com.bea.ns.staxb.bindingConfig.x90.ByNameBean) {
         com.bea.ns.staxb.bindingConfig.x90.ByNameBean bn = (com.bea.ns.staxb.bindingConfig.x90.ByNameBean)node;
         ByNameBean type = new ByNameBean();
         fillTypeFromNode((ByNameBean)type, (com.bea.ns.staxb.bindingConfig.x90.BindingType)bn);
         retval = type;
      } else if (node instanceof com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean) {
         com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean bn = (com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean)node;
         SimpleContentBean type = new SimpleContentBean();
         fillTypeFromNode((SimpleContentBean)type, (com.bea.ns.staxb.bindingConfig.x90.BindingType)bn);
         retval = type;
      } else if (node instanceof SimpleType) {
         SimpleType bn = (SimpleType)node;
         SimpleBindingType type = new SimpleBindingType();
         fillTypeFromNode((SimpleBindingType)type, (com.bea.ns.staxb.bindingConfig.x90.BindingType)bn);
         retval = type;
      } else if (node instanceof WrappedArray) {
         WrappedArray wnode = (WrappedArray)node;
         WrappedArrayType type = new WrappedArrayType();
         fillTypeFromNode(type, wnode);
         retval = type;
      } else if (node instanceof ListArray) {
         ListArray wnode = (ListArray)node;
         ListArrayType type = new ListArrayType();
         fillTypeFromNode(type, wnode);
         retval = type;
      } else if (node instanceof com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType) {
         com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType wnode = (com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType)node;
         JaxrpcEnumType type = new JaxrpcEnumType();
         fillTypeFromNode(type, wnode);
         retval = type;
      } else if (node instanceof SoapArray) {
         SoapArray wnode = (SoapArray)node;
         SoapArrayType type = new SoapArrayType();
         fillTypeFromNode(type, wnode);
         retval = type;
      } else {
         if (!(node instanceof com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding)) {
            throw new AssertionError("unknown type: " + node.schemaType());
         }

         com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding wnode = (com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding)node;
         SimpleDocumentBinding type = new SimpleDocumentBinding();
         fillTypeFromNode(type, wnode);
         retval = type;
      }

      assert retval != null;

      return (BindingType)retval;
   }

   static BindingTypeName getNameFromNode(com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      return BindingTypeName.forPair(JavaTypeName.forString(node.getJavatype()), XmlTypeName.forString(node.getXmlcomponent()));
   }

   static com.bea.ns.staxb.bindingConfig.x90.BindingType writeBindingType(BindingType type, com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      try {
         type.accept(new TypeWriter(node));
         return node;
      } catch (XmlException var3) {
         throw new XmlRuntimeException(var3);
      }
   }

   private static com.bea.ns.staxb.bindingConfig.x90.BindingType writeTypeNameInfo(com.bea.ns.staxb.bindingConfig.x90.BindingType node, BindingType type) {
      node.setJavatype(type.getName().getJavaName().toString());
      node.setXmlcomponent(type.getName().getXmlName().toString());
      return node;
   }

   private static void writeByNameBean(com.bea.ns.staxb.bindingConfig.x90.BindingType node, ByNameBean type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(com.bea.ns.staxb.bindingConfig.x90.ByNameBean.type);
      com.bea.ns.staxb.bindingConfig.x90.ByNameBean bnNode = (com.bea.ns.staxb.bindingConfig.x90.ByNameBean)writeTypeNameInfo(node, type);
      GenericXmlProperty aprop = type.getAnyElementProperty();
      if (aprop != null) {
         PropertyRegistry.writeGenericXmlProperty(aprop, bnNode.addNewAnyProperty());
      }

      GenericXmlProperty anyprop = type.getAnyAttributeProperty();
      if (anyprop != null) {
         PropertyRegistry.writeGenericXmlProperty(anyprop, bnNode.addNewAnyAttributeProperty());
      }

      Iterator i = type.getProperties().iterator();

      while(i.hasNext()) {
         QNameProperty qProp = (QNameProperty)i.next();
         QnameProperty qpNode = bnNode.addNewQnameProperty();
         PropertyRegistry.writeQNameProperty(qProp, qpNode);
      }

   }

   private static void writeSimpleDocumentBinding(com.bea.ns.staxb.bindingConfig.x90.BindingType node, SimpleDocumentBinding type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding.type);
      com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding sdbNode = (com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding)writeTypeNameInfo(node, type);
      sdbNode.setTypeOfElement(type.getTypeOfElement().toString());
   }

   private static void writeSimpleBindingType(com.bea.ns.staxb.bindingConfig.x90.BindingType node, SimpleBindingType type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(SimpleType.type);
      SimpleType stNode = (SimpleType)writeTypeNameInfo(node, type);
      AsXmlType as_if = stNode.addNewAsXml();
      as_if.setStringValue(type.getAsIfXmlType().toString());
      switch (type.getWhitespace()) {
         case 0:
            break;
         case 1:
            as_if.setWhitespace(AsXmlType.Whitespace.PRESERVE);
            break;
         case 2:
            as_if.setWhitespace(AsXmlType.Whitespace.REPLACE);
            break;
         case 3:
            as_if.setWhitespace(AsXmlType.Whitespace.COLLAPSE);
            break;
         default:
            throw new AssertionError("invalid whitespace: " + type.getWhitespace());
      }

      stNode.setAsXml(as_if);
   }

   private static void writeWrappedArrayType(com.bea.ns.staxb.bindingConfig.x90.BindingType node, WrappedArrayType type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(WrappedArray.type);
      WrappedArray wa = (WrappedArray)writeTypeNameInfo(node, type);
      wa.setItemName(type.getItemName());
      Mapping mapping = wa.addNewItemType();
      mapping.setJavatype(type.getItemType().getJavaName().toString());
      mapping.setXmlcomponent(type.getItemType().getXmlName().toString());
      wa.setItemNillable(type.isItemNillable());
   }

   private static void writeSimpleContentBean(com.bea.ns.staxb.bindingConfig.x90.BindingType node, SimpleContentBean type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean.type);
      com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean bnNode = (com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean)writeTypeNameInfo(node, type);
      SimpleContentProperty scprop = type.getSimpleContentProperty();
      if (scprop == null) {
         throw new IllegalArgumentException("type must have a simple content property");
      } else {
         com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty sc_prop = bnNode.addNewSimpleContentProperty();
         PropertyRegistry.writeSimpleContentProperty(scprop, sc_prop);
         GenericXmlProperty any_prop = type.getAnyAttributeProperty();
         if (any_prop != null) {
            com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty gx_prop = bnNode.addNewAnyAttributeProperty();
            PropertyRegistry.writeGenericXmlProperty(any_prop, gx_prop);
         }

         Iterator i = type.getAttributeProperties().iterator();

         while(i.hasNext()) {
            QNameProperty qProp = (QNameProperty)i.next();
            QnameProperty qpNode = bnNode.addNewAttributeProperty();
            PropertyRegistry.writeQNameProperty(qProp, qpNode);
         }

      }
   }

   private static void writeJaxrpcEnumType(com.bea.ns.staxb.bindingConfig.x90.BindingType node, JaxrpcEnumType type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType.type);
      com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType jnode = (com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType)writeTypeNameInfo(node, type);
      if (type.getBaseType() != null) {
         jnode.setBaseJavatype(type.getBaseType().getJavaName().toString());
         jnode.setBaseXmlcomponent(type.getBaseType().getXmlName().toString());
      }

      if (type.getGetValueMethod() != null) {
         writeMethodName(jnode.addNewGetValueMethod(), type.getGetValueMethod());
      }

      if (type.getFromValueMethod() != null) {
         writeMethodName(jnode.addNewFromValueMethod(), type.getFromValueMethod());
      }

      if (type.getToXMLMethod() != null) {
         writeMethodName(jnode.addNewToXMLMethod(), type.getToXMLMethod());
      }

      if (type.getFromStringMethod() != null) {
         writeMethodName(jnode.addNewFromStringMethod(), type.getFromStringMethod());
      }

   }

   static void writeMethodName(JavaMethodName name_node, MethodName method_name) {
      name_node.setMethodName(method_name.getSimpleName());
      JavaTypeName[] param_types = method_name.getParamTypes();
      if (param_types != null && param_types.length > 0) {
         String[] types = new String[param_types.length];

         for(int i = 0; i < types.length; ++i) {
            types[i] = param_types[i].toString();
         }

         name_node.setParamTypeArray(types);
      }

   }

   private static void writeSoapArrayType(com.bea.ns.staxb.bindingConfig.x90.BindingType node, SoapArrayType type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(SoapArray.type);
      SoapArray wa = (SoapArray)writeTypeNameInfo(node, type);
      if (type.getItemName() != null) {
         wa.setItemName(type.getItemName());
      }

      if (type.getItemType() != null) {
         Mapping mapping = wa.addNewItemType();
         mapping.setJavatype(type.getItemType().getJavaName().toString());
         mapping.setXmlcomponent(type.getItemType().getXmlName().toString());
      }

      wa.setItemNillable(type.isItemNillable());
      int ranks = type.getRanks();
      wa.setRanks(ranks);
   }

   private static void writeListArrayType(com.bea.ns.staxb.bindingConfig.x90.BindingType node, ListArrayType type) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingType)node.changeType(ListArray.type);
      ListArray wa = (ListArray)writeTypeNameInfo(node, type);
      Mapping mapping = wa.addNewItemType();
      mapping.setJavatype(type.getItemType().getJavaName().toString());
      mapping.setXmlcomponent(type.getItemType().getXmlName().toString());
   }

   static void fillTypeFromNode(WrappedArrayType wrappedArrayType, WrappedArray node) {
      wrappedArrayType.setName(getNameFromNode(node));
      wrappedArrayType.setItemName(node.getItemName());
      Mapping itype = node.getItemType();
      JavaTypeName jName = JavaTypeName.forString(itype.getJavatype());
      XmlTypeName xName = XmlTypeName.forString(itype.getXmlcomponent());
      wrappedArrayType.setItemType(BindingTypeName.forPair(jName, xName));
      wrappedArrayType.setItemNillable(node.getItemNillable());
   }

   static void fillTypeFromNode(ByNameBean byNameBean, com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      byNameBean.setName(getNameFromNode(node));
      com.bea.ns.staxb.bindingConfig.x90.ByNameBean bnNode = (com.bea.ns.staxb.bindingConfig.x90.ByNameBean)node;
      com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty gxp = bnNode.getAnyProperty();
      if (gxp != null) {
         byNameBean.setAnyElementProperty((GenericXmlProperty)PropertyRegistry.forNode(gxp));
      }

      gxp = bnNode.getAnyAttributeProperty();
      if (gxp != null) {
         byNameBean.setAnyAttributeProperty((GenericXmlProperty)PropertyRegistry.forNode(gxp));
      }

      QnameProperty[] propArray = bnNode.getQnamePropertyArray();

      for(int i = 0; i < propArray.length; ++i) {
         byNameBean.addProperty((QNameProperty)PropertyRegistry.forNode(propArray[i]));
      }

   }

   static void fillTypeFromNode(SimpleContentBean type, com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      type.setName(getNameFromNode(node));
      com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean simpleContentBean = (com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean)node;
      QnameProperty[] propArray = simpleContentBean.getAttributePropertyArray();

      for(int i = 0; i < propArray.length; ++i) {
         type.addProperty((QNameProperty)PropertyRegistry.forNode(propArray[i]));
      }

      com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty scp = simpleContentBean.getSimpleContentProperty();
      SimpleContentProperty bprop = (SimpleContentProperty)PropertyRegistry.forNode(scp);
      type.setSimpleContentProperty(bprop);
      com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty gxp = simpleContentBean.getAnyAttributeProperty();
      if (gxp != null) {
         type.setAnyAttributeProperty((GenericXmlProperty)PropertyRegistry.forNode(gxp));
      }

   }

   static void fillTypeFromNode(SimpleBindingType type, com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
      type.setName(getNameFromNode(node));
      SimpleType stNode = (SimpleType)node;
      AsXmlType as_xml = stNode.getAsXml();
      type.setAsIfXmlType(XmlTypeName.forString(as_xml.getStringValue()));
      if (as_xml.isSetWhitespace()) {
         AsXmlType.Whitespace.Enum ws = as_xml.getWhitespace();
         if (ws.equals(AsXmlType.Whitespace.PRESERVE)) {
            type.setWhitespace(1);
         } else if (ws.equals(AsXmlType.Whitespace.REPLACE)) {
            type.setWhitespace(2);
         } else {
            if (!ws.equals(AsXmlType.Whitespace.COLLAPSE)) {
               throw new AssertionError("invalid whitespace: " + ws);
            }

            type.setWhitespace(3);
         }
      }

   }

   static void fillTypeFromNode(ListArrayType type, ListArray node) {
      type.setName(getNameFromNode(node));
      Mapping itype = node.getItemType();
      JavaTypeName jName = JavaTypeName.forString(itype.getJavatype());
      XmlTypeName xName = XmlTypeName.forString(itype.getXmlcomponent());
      type.setItemType(BindingTypeName.forPair(jName, xName));
   }

   static void fillTypeFromNode(JaxrpcEnumType type, com.bea.ns.staxb.bindingConfig.x90.JaxrpcEnumType node) {
      type.setName(getNameFromNode(node));
      type.setBaseType(BindingTypeName.forPair(JavaTypeName.forString(node.getBaseJavatype()), XmlTypeName.forString(node.getBaseXmlcomponent())));
      type.setGetValueMethod(BindingFileUtils.create(node.getGetValueMethod()));
      type.setFromValueMethod(BindingFileUtils.create(node.getFromValueMethod()));
      type.setFromStringMethod(BindingFileUtils.create(node.getFromStringMethod()));
      JavaMethodName toxml_method = node.getToXMLMethod();
      if (toxml_method != null) {
         type.setToXMLMethod(BindingFileUtils.create(toxml_method));
      }

   }

   static void fillTypeFromNode(SoapArrayType type, SoapArray node) {
      type.setName(getNameFromNode(node));
      type.setItemName(node.getItemName());
      Mapping itype = node.getItemType();
      JavaTypeName jName = JavaTypeName.forString(itype.getJavatype());
      XmlTypeName xName = XmlTypeName.forString(itype.getXmlcomponent());
      type.setItemType(BindingTypeName.forPair(jName, xName));
      type.setItemNillable(node.getItemNillable());
      type.setRanks(node.getRanks());
   }

   static void fillTypeFromNode(SimpleDocumentBinding simpleDocumentBinding, com.bea.ns.staxb.bindingConfig.x90.SimpleDocumentBinding node) {
      simpleDocumentBinding.setName(getNameFromNode(node));
      simpleDocumentBinding.setTypeOfElement(XmlTypeName.forString(node.getTypeOfElement()));
   }

   private static final class TypeWriter implements BindingTypeVisitor {
      private final com.bea.ns.staxb.bindingConfig.x90.BindingType node;

      public TypeWriter(com.bea.ns.staxb.bindingConfig.x90.BindingType node) {
         assert node != null;

         this.node = node;
      }

      public void visit(BuiltinBindingType builtinBindingType) throws XmlException {
         throw new AssertionError("cannot write builtin types: " + builtinBindingType);
      }

      public void visit(ByNameBean byNameBean) throws XmlException {
         TypeRegistry.writeByNameBean(this.node, byNameBean);
      }

      public void visit(SimpleContentBean simpleContentBean) throws XmlException {
         TypeRegistry.writeSimpleContentBean(this.node, simpleContentBean);
      }

      public void visit(SimpleBindingType simpleBindingType) throws XmlException {
         TypeRegistry.writeSimpleBindingType(this.node, simpleBindingType);
      }

      public void visit(JaxrpcEnumType jaxrpcEnumType) throws XmlException {
         TypeRegistry.writeJaxrpcEnumType(this.node, jaxrpcEnumType);
      }

      public void visit(SimpleDocumentBinding simpleDocumentBinding) throws XmlException {
         TypeRegistry.writeSimpleDocumentBinding(this.node, simpleDocumentBinding);
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         TypeRegistry.writeWrappedArrayType(this.node, wrappedArrayType);
      }

      public void visit(SoapArrayType soapArrayType) throws XmlException {
         TypeRegistry.writeSoapArrayType(this.node, soapArrayType);
      }

      public void visit(ListArrayType listArrayType) throws XmlException {
         TypeRegistry.writeListArrayType(this.node, listArrayType);
      }
   }
}
