package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.GenericXmlProperty;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.util.jam.JElement;
import com.bea.xbean.xb.xsdschema.Attribute;
import com.bea.xbean.xb.xsdschema.ExtensionType;
import com.bea.xbean.xb.xsdschema.Group;
import com.bea.xbean.xb.xsdschema.LocalElement;
import com.bea.xbean.xb.xsdschema.TopLevelComplexType;
import com.bea.xbean.xb.xsdschema.Attribute.Factory;
import java.util.ArrayList;
import java.util.List;

public class DefaultTypegenFacade implements TypegenFacade {
   protected Java2SchemaContext mContext;
   protected TopLevelComplexType mXsType;
   protected ExtensionType mExtensionType = null;
   protected Group mXsSequence = null;
   protected PropgenFacade mCurrentProp = null;
   protected String mTargetNamespace;
   private List mXsAttributeList = null;
   private ByNameBean mBtsType;

   public DefaultTypegenFacade(Java2SchemaContext ctx, TopLevelComplexType schemaType, ExtensionType extTypeOrNull, String targetNamespace, ByNameBean bindingType) {
      this.mContext = ctx;
      this.mXsType = schemaType;
      this.mExtensionType = extTypeOrNull;
      this.mBtsType = bindingType;
      this.mTargetNamespace = targetNamespace;
   }

   public PropgenFacade createNextElement(JElement srcContext) {
      this.finishCurrentProp();
      this.initSequence();
      String actualTargetNamespace = this.mTargetNamespace;
      if (!this.mContext.isElementFormDefaultQualified()) {
         actualTargetNamespace = "";
      }

      return this.mCurrentProp = this.createPropgenFacade(this.mContext, srcContext, new QNameProperty(), actualTargetNamespace, this.mXsSequence.addNewElement());
   }

   private void initSequence() {
      if (this.mXsSequence == null) {
         this.mXsSequence = this.mExtensionType != null ? this.mExtensionType.addNewSequence() : this.mXsType.addNewSequence();
      }

   }

   public PropgenFacade createAny(JElement srcContext) {
      this.finishCurrentProp();
      this.initSequence();
      GenericXmlProperty property = new GenericXmlProperty();
      PropgenFacade facade = new AnyPropgenFacade(this.mContext, srcContext, property, this.mTargetNamespace, this.mXsSequence.addNewAny());
      this.mBtsType.setAnyElementProperty(property);
      return facade;
   }

   public PropgenFacade createNextAttribute(JElement srcContext) {
      this.finishCurrentProp();
      if (this.mXsAttributeList == null) {
         this.mXsAttributeList = new ArrayList();
      }

      Attribute newXsAtt;
      this.mXsAttributeList.add(newXsAtt = Factory.newInstance());
      return this.mCurrentProp = this.createPropgenFacade(this.mContext, srcContext, new QNameProperty(), this.mTargetNamespace, newXsAtt);
   }

   public PropgenFacade peekCurrentProp() {
      return this.mCurrentProp;
   }

   public void finish() {
      this.finishCurrentProp();
      if (this.mXsAttributeList != null) {
         Attribute[] array = new Attribute[this.mXsAttributeList.size()];
         this.mXsAttributeList.toArray(array);
         this.mXsType.setAttributeArray(array);
      }

   }

   public void setDocumentation(String docs) {
      DefaultPropgenFacade.setDocumentation(this.mXsType, docs);
   }

   protected PropgenFacade createPropgenFacade(Java2SchemaContext ctx, JElement srcContext, QNameProperty btsProp, String targetNamespace, Attribute att) {
      return new DefaultPropgenFacade(ctx, srcContext, btsProp, targetNamespace, att);
   }

   protected PropgenFacade createPropgenFacade(Java2SchemaContext ctx, JElement srcContext, QNameProperty btsProp, String targetNamespace, LocalElement element) {
      return new DefaultPropgenFacade(ctx, srcContext, btsProp, targetNamespace, element);
   }

   protected void finishCurrentProp() {
      if (this.mCurrentProp != null) {
         this.mCurrentProp.finish();
         QNameProperty btsProp = this.mCurrentProp.getBtsProperty();
         if (!this.mBtsType.hasProperty(btsProp)) {
            this.mBtsType.addProperty(btsProp);
         }
      }

   }
}
