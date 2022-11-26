package com.bea.xbean.schema;

import com.bea.xml.SchemaAnnotation;
import com.bea.xml.SchemaAttributeGroup;
import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlObject;
import javax.xml.namespace.QName;

public class SchemaAttributeGroupImpl implements SchemaAttributeGroup {
   private SchemaContainer _container;
   private QName _name;
   private XmlObject _parseObject;
   private Object _userData;
   private String _parseTNS;
   private String _formDefault;
   private boolean _chameleon;
   private boolean _redefinition;
   private SchemaAnnotation _annotation;
   private String _filename;
   private SchemaAttributeGroup.Ref _selfref = new SchemaAttributeGroup.Ref(this);

   public SchemaAttributeGroupImpl(SchemaContainer container) {
      this._container = container;
   }

   public SchemaAttributeGroupImpl(SchemaContainer container, QName name) {
      this._container = container;
      this._name = name;
   }

   public void init(QName name, String targetNamespace, boolean chameleon, String formDefault, boolean redefinition, XmlObject x, SchemaAnnotation a, Object userData) {
      assert this._name == null || name.equals(this._name);

      this._name = name;
      this._parseTNS = targetNamespace;
      this._chameleon = chameleon;
      this._formDefault = formDefault;
      this._redefinition = redefinition;
      this._parseObject = x;
      this._annotation = a;
      this._userData = userData;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container.getTypeSystem();
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   public int getComponentType() {
      return 4;
   }

   public void setFilename(String filename) {
      this._filename = filename;
   }

   public String getSourceName() {
      return this._filename;
   }

   public QName getName() {
      return this._name;
   }

   public XmlObject getParseObject() {
      return this._parseObject;
   }

   public String getTargetNamespace() {
      return this._parseTNS;
   }

   public String getChameleonNamespace() {
      return this._chameleon ? this._parseTNS : null;
   }

   public String getFormDefault() {
      return this._formDefault;
   }

   public SchemaAnnotation getAnnotation() {
      return this._annotation;
   }

   public SchemaAttributeGroup.Ref getRef() {
      return this._selfref;
   }

   public SchemaComponent.Ref getComponentRef() {
      return this.getRef();
   }

   public boolean isRedefinition() {
      return this._redefinition;
   }

   public Object getUserData() {
      return this._userData;
   }
}
