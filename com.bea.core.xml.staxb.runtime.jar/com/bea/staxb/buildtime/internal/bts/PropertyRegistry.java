package com.bea.staxb.buildtime.internal.bts;

import com.bea.ns.staxb.bindingConfig.x90.QnameProperty;

final class PropertyRegistry {
   public static BindingProperty forNode(com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      Object retval;
      if (node instanceof QnameProperty) {
         QnameProperty qnode = (QnameProperty)node;
         QNameProperty qnp = new QNameProperty();
         fillQNamePropertyFromNode(qnp, qnode);
         retval = qnp;
      } else if (node instanceof com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty) {
         com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty qnode = (com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty)node;
         SimpleContentProperty scp = new SimpleContentProperty();
         fillSimpleContentPropertyFromNode(scp, qnode);
         retval = scp;
      } else {
         if (!(node instanceof com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty)) {
            throw new AssertionError("unknown type " + node.getClass());
         }

         com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty qnode = (com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty)node;
         GenericXmlProperty scp = new GenericXmlProperty();
         fillGenericXmlPropertyFromNode(scp, qnode);
         retval = scp;
      }

      return (BindingProperty)retval;
   }

   private static void fillPropertyFromNode(BindingProperty prop, com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      prop.setTypeName(BindingTypeName.forPair(JavaTypeName.forString(node.getJavatype()), XmlTypeName.forString(node.getXmlcomponent())));
      prop.setGetter(BindingFileUtils.create(node.getGetter()));
      prop.setSetter(BindingFileUtils.create(node.getSetter()));
      prop.setIssetter(BindingFileUtils.create(node.getIssetter()));
      prop.setField(node.getField());
      if (node.isSetConstructorParameterIndex()) {
         prop.setCtorParamIndex(node.getConstructorParameterIndex());
      }

      String collection = node.getCollection();
      if (collection != null) {
         prop.setCollection(JavaTypeName.forString(collection));
      }

      com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory factory = node.getFactory();
      if (factory != null) {
         prop.setJavaInstanceFactory(FactoryRegistry.forNode(factory));
      }

   }

   private static void fillQNamePropertyFromNode(QNameProperty prop, QnameProperty node) {
      fillPropertyFromNode(prop, node);
      prop.setQName(node.getQname());
      prop.setAttribute(node.getAttribute());
      prop.setMultiple(node.getMultiple());
      prop.setNillable(node.getNillable());
      prop.setOptional(node.getOptional());
      prop.setDefault(node.getDefault());
   }

   private static void fillSimpleContentPropertyFromNode(SimpleContentProperty prop, com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty node) {
      fillPropertyFromNode(prop, node);
   }

   private static void fillGenericXmlPropertyFromNode(GenericXmlProperty prop, com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty node) {
      fillPropertyFromNode(prop, node);
   }

   private static com.bea.ns.staxb.bindingConfig.x90.BindingProperty writeProperty(BindingProperty prop, com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      assert prop != null;

      assert node != null;

      node.setJavatype(prop.getTypeName().getJavaName().toString());
      node.setXmlcomponent(prop.getTypeName().getXmlName().toString());
      if (prop.getFieldName() != null) {
         node.setField(prop.getFieldName());
      }

      if (prop.getGetterName() != null) {
         TypeRegistry.writeMethodName(node.addNewGetter(), prop.getGetterName());
      }

      if (prop.getSetterName() != null) {
         TypeRegistry.writeMethodName(node.addNewSetter(), prop.getSetterName());
      }

      if (prop.getIssetterName() != null) {
         TypeRegistry.writeMethodName(node.addNewIssetter(), prop.getIssetterName());
      }

      if (prop.getCollectionClass() != null) {
         node.setCollection(prop.getCollectionClass().toString());
      }

      if (prop.getCtorParamIndex() >= 0) {
         node.setConstructorParameterIndex(prop.getCtorParamIndex());
      }

      JavaInstanceFactory jif = prop.getJavaInstanceFactory();
      if (jif != null) {
         com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory jif_node = node.addNewFactory();
         FactoryRegistry.writeAJavaInstanceFactory(jif, jif_node);
      }

      return node;
   }

   static com.bea.ns.staxb.bindingConfig.x90.BindingProperty writeQNameProperty(QNameProperty qprop, com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingProperty)node.changeType(QnameProperty.type);
      QnameProperty qpNode = (QnameProperty)writeProperty(qprop, node);
      qpNode.setQname(qprop.getQName());
      if (qprop.isAttribute()) {
         qpNode.setAttribute(true);
      }

      if (qprop.isMultiple()) {
         qpNode.setMultiple(true);
      }

      if (qprop.isOptional()) {
         qpNode.setOptional(true);
      }

      if (qprop.isNillable()) {
         qpNode.setNillable(true);
      }

      return qpNode;
   }

   static com.bea.ns.staxb.bindingConfig.x90.BindingProperty writeSimpleContentProperty(SimpleContentProperty gprop, com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingProperty)node.changeType(com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty.type);
      com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty gnode = (com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty)writeProperty(gprop, node);
      return gnode;
   }

   static com.bea.ns.staxb.bindingConfig.x90.BindingProperty writeGenericXmlProperty(GenericXmlProperty gprop, com.bea.ns.staxb.bindingConfig.x90.BindingProperty node) {
      node = (com.bea.ns.staxb.bindingConfig.x90.BindingProperty)node.changeType(com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty.type);
      com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty gnode = (com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty)writeProperty(gprop, node);
      return gnode;
   }
}
