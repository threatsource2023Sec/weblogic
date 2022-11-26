package com.bea.xbean.schema;

import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaGlobalAttribute;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlObject;

public class SchemaGlobalAttributeImpl extends SchemaLocalAttributeImpl implements SchemaGlobalAttribute {
   SchemaContainer _container;
   String _filename;
   private String _parseTNS;
   private boolean _chameleon;
   private SchemaGlobalAttribute.Ref _selfref = new SchemaGlobalAttribute.Ref(this);

   public SchemaGlobalAttributeImpl(SchemaContainer container) {
      this._container = container;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container.getTypeSystem();
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   public int getComponentType() {
      return 3;
   }

   public String getSourceName() {
      return this._filename;
   }

   public void setFilename(String filename) {
      this._filename = filename;
   }

   public void setParseContext(XmlObject parseObject, String targetNamespace, boolean chameleon) {
      this._parseObject = parseObject;
      this._parseTNS = targetNamespace;
      this._chameleon = chameleon;
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

   public SchemaGlobalAttribute.Ref getRef() {
      return this._selfref;
   }

   public SchemaComponent.Ref getComponentRef() {
      return this.getRef();
   }
}
