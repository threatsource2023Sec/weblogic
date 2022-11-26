package com.bea.xbean.schema;

import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlObject;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.namespace.QName;

public class SchemaGlobalElementImpl extends SchemaLocalElementImpl implements SchemaGlobalElement {
   private Set _sgMembers = new LinkedHashSet();
   private static final QName[] _namearray = new QName[0];
   private boolean _finalExt;
   private boolean _finalRest;
   private SchemaContainer _container;
   private String _filename;
   private String _parseTNS;
   private boolean _chameleon;
   private SchemaGlobalElement.Ref _sg;
   private SchemaGlobalElement.Ref _selfref = new SchemaGlobalElement.Ref(this);

   public SchemaGlobalElementImpl(SchemaContainer container) {
      this._container = container;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container.getTypeSystem();
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   public String getSourceName() {
      return this._filename;
   }

   public void setFilename(String filename) {
      this._filename = filename;
   }

   void setFinal(boolean finalExt, boolean finalRest) {
      this.mutate();
      this._finalExt = finalExt;
      this._finalRest = finalRest;
   }

   public int getComponentType() {
      return 1;
   }

   public SchemaGlobalElement substitutionGroup() {
      return this._sg == null ? null : this._sg.get();
   }

   public void setSubstitutionGroup(SchemaGlobalElement.Ref sg) {
      this._sg = sg;
   }

   public QName[] substitutionGroupMembers() {
      return (QName[])((QName[])this._sgMembers.toArray(_namearray));
   }

   public void addSubstitutionGroupMember(QName name) {
      this.mutate();
      this._sgMembers.add(name);
   }

   public boolean finalExtension() {
      return this._finalExt;
   }

   public boolean finalRestriction() {
      return this._finalRest;
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

   public SchemaGlobalElement.Ref getRef() {
      return this._selfref;
   }

   public SchemaComponent.Ref getComponentRef() {
      return this.getRef();
   }
}
