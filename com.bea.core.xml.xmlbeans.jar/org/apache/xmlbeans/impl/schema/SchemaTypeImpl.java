package org.apache.xmlbeans.impl.schema;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.InterfaceExtension;
import org.apache.xmlbeans.PrePostExtension;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.QNameSetBuilder;
import org.apache.xmlbeans.SchemaAnnotation;
import org.apache.xmlbeans.SchemaAttributeModel;
import org.apache.xmlbeans.SchemaComponent;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaGlobalAttribute;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaLocalAttribute;
import org.apache.xmlbeans.SchemaLocalElement;
import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaStringEnumEntry;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeElementSequencer;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.regex.RegularExpression;
import org.apache.xmlbeans.impl.values.StringEnumValue;
import org.apache.xmlbeans.impl.values.TypeStoreUser;
import org.apache.xmlbeans.impl.values.TypeStoreUserFactory;
import org.apache.xmlbeans.impl.values.XmlAnySimpleTypeImpl;
import org.apache.xmlbeans.impl.values.XmlAnySimpleTypeRestriction;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.apache.xmlbeans.impl.values.XmlAnyUriImpl;
import org.apache.xmlbeans.impl.values.XmlAnyUriRestriction;
import org.apache.xmlbeans.impl.values.XmlBase64BinaryImpl;
import org.apache.xmlbeans.impl.values.XmlBase64BinaryRestriction;
import org.apache.xmlbeans.impl.values.XmlBooleanImpl;
import org.apache.xmlbeans.impl.values.XmlBooleanRestriction;
import org.apache.xmlbeans.impl.values.XmlByteImpl;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.values.XmlDateImpl;
import org.apache.xmlbeans.impl.values.XmlDateTimeImpl;
import org.apache.xmlbeans.impl.values.XmlDecimalImpl;
import org.apache.xmlbeans.impl.values.XmlDecimalRestriction;
import org.apache.xmlbeans.impl.values.XmlDoubleImpl;
import org.apache.xmlbeans.impl.values.XmlDoubleRestriction;
import org.apache.xmlbeans.impl.values.XmlDurationImpl;
import org.apache.xmlbeans.impl.values.XmlEntitiesImpl;
import org.apache.xmlbeans.impl.values.XmlEntityImpl;
import org.apache.xmlbeans.impl.values.XmlFloatImpl;
import org.apache.xmlbeans.impl.values.XmlFloatRestriction;
import org.apache.xmlbeans.impl.values.XmlGDayImpl;
import org.apache.xmlbeans.impl.values.XmlGMonthDayImpl;
import org.apache.xmlbeans.impl.values.XmlGMonthImpl;
import org.apache.xmlbeans.impl.values.XmlGYearImpl;
import org.apache.xmlbeans.impl.values.XmlGYearMonthImpl;
import org.apache.xmlbeans.impl.values.XmlHexBinaryImpl;
import org.apache.xmlbeans.impl.values.XmlHexBinaryRestriction;
import org.apache.xmlbeans.impl.values.XmlIdImpl;
import org.apache.xmlbeans.impl.values.XmlIdRefImpl;
import org.apache.xmlbeans.impl.values.XmlIdRefsImpl;
import org.apache.xmlbeans.impl.values.XmlIntImpl;
import org.apache.xmlbeans.impl.values.XmlIntRestriction;
import org.apache.xmlbeans.impl.values.XmlIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlIntegerRestriction;
import org.apache.xmlbeans.impl.values.XmlLanguageImpl;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.values.XmlLongImpl;
import org.apache.xmlbeans.impl.values.XmlLongRestriction;
import org.apache.xmlbeans.impl.values.XmlNCNameImpl;
import org.apache.xmlbeans.impl.values.XmlNameImpl;
import org.apache.xmlbeans.impl.values.XmlNegativeIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlNmTokenImpl;
import org.apache.xmlbeans.impl.values.XmlNmTokensImpl;
import org.apache.xmlbeans.impl.values.XmlNonNegativeIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlNonPositiveIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlNormalizedStringImpl;
import org.apache.xmlbeans.impl.values.XmlNotationImpl;
import org.apache.xmlbeans.impl.values.XmlNotationRestriction;
import org.apache.xmlbeans.impl.values.XmlObjectBase;
import org.apache.xmlbeans.impl.values.XmlPositiveIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlQNameImpl;
import org.apache.xmlbeans.impl.values.XmlQNameRestriction;
import org.apache.xmlbeans.impl.values.XmlShortImpl;
import org.apache.xmlbeans.impl.values.XmlStringEnumeration;
import org.apache.xmlbeans.impl.values.XmlStringImpl;
import org.apache.xmlbeans.impl.values.XmlStringRestriction;
import org.apache.xmlbeans.impl.values.XmlTimeImpl;
import org.apache.xmlbeans.impl.values.XmlTokenImpl;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.values.XmlUnsignedByteImpl;
import org.apache.xmlbeans.impl.values.XmlUnsignedIntImpl;
import org.apache.xmlbeans.impl.values.XmlUnsignedLongImpl;
import org.apache.xmlbeans.impl.values.XmlUnsignedShortImpl;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;

public final class SchemaTypeImpl implements SchemaType, TypeStoreUserFactory {
   private QName _name;
   private SchemaAnnotation _annotation;
   private int _resolvePhase;
   private static final int UNRESOLVED = 0;
   private static final int RESOLVING_SGS = 1;
   private static final int RESOLVED_SGS = 2;
   private static final int RESOLVING = 3;
   private static final int RESOLVED = 4;
   private static final int JAVAIZING = 5;
   private static final int JAVAIZED = 6;
   private SchemaType.Ref _outerSchemaTypeRef;
   private volatile SchemaComponent.Ref _containerFieldRef;
   private volatile SchemaField _containerField;
   private volatile int _containerFieldCode;
   private volatile int _containerFieldIndex;
   private volatile QName[] _groupReferenceContext;
   private SchemaType.Ref[] _anonymousTyperefs;
   private boolean _isDocumentType;
   private boolean _isAttributeType;
   private boolean _isCompiled;
   private String _shortJavaName;
   private String _fullJavaName;
   private String _shortJavaImplName;
   private String _fullJavaImplName;
   private InterfaceExtension[] _interfaces;
   private PrePostExtension _prepost;
   private volatile Class _javaClass;
   private volatile Class _javaEnumClass;
   private volatile Class _javaImplClass;
   private volatile Constructor _javaImplConstructor;
   private volatile Constructor _javaImplConstructor2;
   private volatile boolean _implNotAvailable;
   private volatile Class _userTypeClass;
   private volatile Class _userTypeHandlerClass;
   private volatile Object _userData;
   private final Object[] _ctrArgs = new Object[]{this};
   private SchemaContainer _container;
   private String _filename;
   private SchemaParticle _contentModel;
   private volatile SchemaLocalElement[] _localElts;
   private volatile Map _eltToIndexMap;
   private volatile Map _attrToIndexMap;
   private Map _propertyModelByElementName;
   private Map _propertyModelByAttributeName;
   private boolean _hasAllContent;
   private boolean _orderSensitive;
   private QNameSet _typedWildcardElements;
   private QNameSet _typedWildcardAttributes;
   private boolean _hasWildcardElements;
   private boolean _hasWildcardAttributes;
   private Set _validSubstitutions;
   private int _complexTypeVariety;
   private SchemaAttributeModel _attributeModel;
   private int _builtinTypeCode;
   private int _simpleTypeVariety;
   private boolean _isSimpleType;
   private SchemaType.Ref _baseTyperef;
   private int _baseDepth;
   private int _derivationType;
   private String _userTypeName;
   private String _userTypeHandler;
   private SchemaType.Ref _contentBasedOnTyperef;
   private XmlValueRef[] _facetArray;
   private boolean[] _fixedFacetArray;
   private int _ordered;
   private boolean _isFinite;
   private boolean _isBounded;
   private boolean _isNumeric;
   private boolean _abs;
   private boolean _finalExt;
   private boolean _finalRest;
   private boolean _finalList;
   private boolean _finalUnion;
   private boolean _blockExt;
   private boolean _blockRest;
   private int _whiteSpaceRule;
   private boolean _hasPatterns;
   private RegularExpression[] _patterns;
   private XmlValueRef[] _enumerationValues;
   private SchemaType.Ref _baseEnumTyperef;
   private boolean _stringEnumEnsured;
   private volatile Map _lookupStringEnum;
   private volatile List _listOfStringEnum;
   private volatile Map _lookupStringEnumEntry;
   private SchemaStringEnumEntry[] _stringEnumEntries;
   private SchemaType.Ref _listItemTyperef;
   private boolean _isUnionOfLists;
   private SchemaType.Ref[] _unionMemberTyperefs;
   private int _anonymousUnionMemberOrdinal;
   private volatile SchemaType[] _unionConstituentTypes;
   private volatile SchemaType[] _unionSubTypes;
   private volatile SchemaType _unionCommonBaseType;
   private SchemaType.Ref _primitiveTypeRef;
   private int _decimalSize;
   private volatile boolean _unloaded;
   private QName _sg;
   private List _sgMembers;
   private static final SchemaProperty[] NO_PROPERTIES = new SchemaProperty[0];
   private XmlObject _parseObject;
   private String _parseTNS;
   private String _elemFormDefault;
   private String _attFormDefault;
   private boolean _chameleon;
   private boolean _redefinition;
   private SchemaType.Ref _selfref;

   public boolean isUnloaded() {
      return this._unloaded;
   }

   public void finishLoading() {
      this._unloaded = false;
   }

   SchemaTypeImpl(SchemaContainer container) {
      this._validSubstitutions = Collections.EMPTY_SET;
      this._sgMembers = new ArrayList();
      this._selfref = new SchemaType.Ref(this);
      this._container = container;
   }

   SchemaTypeImpl(SchemaContainer container, boolean unloaded) {
      this._validSubstitutions = Collections.EMPTY_SET;
      this._sgMembers = new ArrayList();
      this._selfref = new SchemaType.Ref(this);
      this._container = container;
      this._unloaded = unloaded;
      if (unloaded) {
         this.finishQuick();
      }

   }

   public boolean isSGResolved() {
      return this._resolvePhase >= 2;
   }

   public boolean isSGResolving() {
      return this._resolvePhase >= 1;
   }

   public boolean isResolved() {
      return this._resolvePhase >= 4;
   }

   public boolean isResolving() {
      return this._resolvePhase == 3;
   }

   public boolean isUnjavaized() {
      return this._resolvePhase < 6;
   }

   public boolean isJavaized() {
      return this._resolvePhase == 6;
   }

   public void startResolvingSGs() {
      if (this._resolvePhase != 0) {
         throw new IllegalStateException();
      } else {
         this._resolvePhase = 1;
      }
   }

   public void finishResolvingSGs() {
      if (this._resolvePhase != 1) {
         throw new IllegalStateException();
      } else {
         this._resolvePhase = 2;
      }
   }

   public void startResolving() {
      if ((!this._isDocumentType || this._resolvePhase == 2) && (this._isDocumentType || this._resolvePhase == 0)) {
         this._resolvePhase = 3;
      } else {
         throw new IllegalStateException();
      }
   }

   public void finishResolving() {
      if (this._resolvePhase != 3) {
         throw new IllegalStateException();
      } else {
         this._resolvePhase = 4;
      }
   }

   public void startJavaizing() {
      if (this._resolvePhase != 4) {
         throw new IllegalStateException();
      } else {
         this._resolvePhase = 5;
      }
   }

   public void finishJavaizing() {
      if (this._resolvePhase != 5) {
         throw new IllegalStateException();
      } else {
         this._resolvePhase = 6;
      }
   }

   private void finishQuick() {
      this._resolvePhase = 6;
   }

   private void assertUnresolved() {
      if (this._resolvePhase != 0 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   private void assertSGResolving() {
      if (this._resolvePhase != 1 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   private void assertSGResolved() {
      if (this._resolvePhase != 2 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   private void assertResolving() {
      if (this._resolvePhase != 3 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   private void assertResolved() {
      if (this._resolvePhase != 4 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   private void assertJavaizing() {
      if (this._resolvePhase != 5 && !this._unloaded) {
         throw new IllegalStateException();
      }
   }

   public QName getName() {
      return this._name;
   }

   public void setName(QName name) {
      this.assertUnresolved();
      this._name = name;
   }

   public String getSourceName() {
      if (this._filename != null) {
         return this._filename;
      } else if (this.getOuterType() != null) {
         return this.getOuterType().getSourceName();
      } else {
         SchemaField field = this.getContainerField();
         if (field != null) {
            if (field instanceof SchemaGlobalElement) {
               return ((SchemaGlobalElement)field).getSourceName();
            }

            if (field instanceof SchemaGlobalAttribute) {
               return ((SchemaGlobalAttribute)field).getSourceName();
            }
         }

         return null;
      }
   }

   public void setFilename(String filename) {
      this.assertUnresolved();
      this._filename = filename;
   }

   public int getComponentType() {
      return 0;
   }

   public boolean isAnonymousType() {
      return this._name == null;
   }

   public boolean isDocumentType() {
      return this._isDocumentType;
   }

   public boolean isAttributeType() {
      return this._isAttributeType;
   }

   public QName getDocumentElementName() {
      if (this._isDocumentType) {
         SchemaParticle sp = this.getContentModel();
         if (sp != null) {
            return sp.getName();
         }
      }

      return null;
   }

   public QName getAttributeTypeAttributeName() {
      if (this._isAttributeType) {
         SchemaAttributeModel sam = this.getAttributeModel();
         if (sam != null) {
            SchemaLocalAttribute[] slaArray = sam.getAttributes();
            if (slaArray != null && slaArray.length > 0) {
               SchemaLocalAttribute sla = slaArray[0];
               return sla.getName();
            }
         }
      }

      return null;
   }

   public void setAnnotation(SchemaAnnotation ann) {
      this.assertUnresolved();
      this._annotation = ann;
   }

   public SchemaAnnotation getAnnotation() {
      return this._annotation;
   }

   public void setDocumentType(boolean isDocument) {
      this.assertUnresolved();
      this._isDocumentType = isDocument;
   }

   public void setAttributeType(boolean isAttribute) {
      this.assertUnresolved();
      this._isAttributeType = isAttribute;
   }

   public int getContentType() {
      return this._complexTypeVariety;
   }

   public void setComplexTypeVariety(int complexTypeVariety) {
      this.assertResolving();
      this._complexTypeVariety = complexTypeVariety;
   }

   public SchemaTypeElementSequencer getElementSequencer() {
      return this._complexTypeVariety == 0 ? new SequencerImpl((SchemaTypeVisitorImpl)null) : new SequencerImpl(new SchemaTypeVisitorImpl(this._contentModel));
   }

   void setAbstractFinal(boolean abs, boolean finalExt, boolean finalRest, boolean finalList, boolean finalUnion) {
      this.assertResolving();
      this._abs = abs;
      this._finalExt = finalExt;
      this._finalRest = finalRest;
      this._finalList = finalList;
      this._finalUnion = finalUnion;
   }

   void setSimpleFinal(boolean finalRest, boolean finalList, boolean finalUnion) {
      this.assertResolving();
      this._finalRest = finalRest;
      this._finalList = finalList;
      this._finalUnion = finalUnion;
   }

   void setBlock(boolean blockExt, boolean blockRest) {
      this.assertResolving();
      this._blockExt = blockExt;
      this._blockRest = blockRest;
   }

   public boolean blockRestriction() {
      return this._blockRest;
   }

   public boolean blockExtension() {
      return this._blockExt;
   }

   public boolean isAbstract() {
      return this._abs;
   }

   public boolean finalExtension() {
      return this._finalExt;
   }

   public boolean finalRestriction() {
      return this._finalRest;
   }

   public boolean finalList() {
      return this._finalList;
   }

   public boolean finalUnion() {
      return this._finalUnion;
   }

   public synchronized SchemaField getContainerField() {
      if (this._containerFieldCode != -1) {
         SchemaType outer = this.getOuterType();
         if (this._containerFieldCode == 0) {
            this._containerField = this._containerFieldRef == null ? null : (SchemaField)this._containerFieldRef.getComponent();
         } else if (this._containerFieldCode == 1) {
            this._containerField = outer.getAttributeModel().getAttributes()[this._containerFieldIndex];
         } else {
            this._containerField = ((SchemaTypeImpl)outer).getLocalElementByIndex(this._containerFieldIndex);
         }

         this._containerFieldCode = -1;
      }

      return this._containerField;
   }

   public void setContainerField(SchemaField field) {
      this.assertUnresolved();
      this._containerField = field;
      this._containerFieldCode = -1;
   }

   public void setContainerFieldRef(SchemaComponent.Ref ref) {
      this.assertUnresolved();
      this._containerFieldRef = ref;
      this._containerFieldCode = 0;
   }

   public void setContainerFieldIndex(short code, int index) {
      this.assertUnresolved();
      this._containerFieldCode = code;
      this._containerFieldIndex = index;
   }

   void setGroupReferenceContext(QName[] groupNames) {
      this.assertUnresolved();
      this._groupReferenceContext = groupNames;
   }

   QName[] getGroupReferenceContext() {
      return this._groupReferenceContext;
   }

   public SchemaType getOuterType() {
      return this._outerSchemaTypeRef == null ? null : this._outerSchemaTypeRef.get();
   }

   public void setOuterSchemaTypeRef(SchemaType.Ref typeref) {
      this.assertUnresolved();
      this._outerSchemaTypeRef = typeref;
   }

   public boolean isCompiled() {
      return this._isCompiled;
   }

   public void setCompiled(boolean f) {
      this.assertJavaizing();
      this._isCompiled = f;
   }

   public boolean isSkippedAnonymousType() {
      SchemaType outerType = this.getOuterType();
      return outerType == null ? false : outerType.getBaseType() == this || outerType.getContentBasedOnType() == this;
   }

   public String getShortJavaName() {
      return this._shortJavaName;
   }

   public void setShortJavaName(String name) {
      this.assertResolved();
      this._shortJavaName = name;

      SchemaType outer;
      for(outer = this._outerSchemaTypeRef.get(); outer.getFullJavaName() == null; outer = outer.getOuterType()) {
      }

      this._fullJavaName = outer.getFullJavaName() + "$" + this._shortJavaName;
   }

   public String getFullJavaName() {
      return this._fullJavaName;
   }

   public void setFullJavaName(String name) {
      this.assertResolved();
      this._fullJavaName = name;
      int index = Math.max(this._fullJavaName.lastIndexOf(36), this._fullJavaName.lastIndexOf(46)) + 1;
      this._shortJavaName = this._fullJavaName.substring(index);
   }

   public void setShortJavaImplName(String name) {
      this.assertResolved();
      this._shortJavaImplName = name;

      SchemaType outer;
      for(outer = this._outerSchemaTypeRef.get(); outer.getFullJavaImplName() == null; outer = outer.getOuterType()) {
      }

      this._fullJavaImplName = outer.getFullJavaImplName() + "$" + this._shortJavaImplName;
   }

   public void setFullJavaImplName(String name) {
      this.assertResolved();
      this._fullJavaImplName = name;
      int index = Math.max(this._fullJavaImplName.lastIndexOf(36), this._fullJavaImplName.lastIndexOf(46)) + 1;
      this._shortJavaImplName = this._fullJavaImplName.substring(index);
   }

   public String getFullJavaImplName() {
      return this._fullJavaImplName;
   }

   public String getShortJavaImplName() {
      return this._shortJavaImplName;
   }

   public String getUserTypeName() {
      return this._userTypeName;
   }

   public void setUserTypeName(String userTypeName) {
      this._userTypeName = userTypeName;
   }

   public String getUserTypeHandlerName() {
      return this._userTypeHandler;
   }

   public void setUserTypeHandlerName(String typeHandler) {
      this._userTypeHandler = typeHandler;
   }

   public void setInterfaceExtensions(InterfaceExtension[] interfaces) {
      this.assertResolved();
      this._interfaces = interfaces;
   }

   public InterfaceExtension[] getInterfaceExtensions() {
      return this._interfaces;
   }

   public void setPrePostExtension(PrePostExtension prepost) {
      this.assertResolved();
      this._prepost = prepost;
   }

   public PrePostExtension getPrePostExtension() {
      return this._prepost;
   }

   public Object getUserData() {
      return this._userData;
   }

   public void setUserData(Object data) {
      this._userData = data;
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   void setContainer(SchemaContainer container) {
      this._container = container;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container.getTypeSystem();
   }

   public SchemaParticle getContentModel() {
      return this._contentModel;
   }

   private static void buildEltList(List eltList, SchemaParticle contentModel) {
      if (contentModel != null) {
         switch (contentModel.getParticleType()) {
            case 1:
            case 2:
            case 3:
               for(int i = 0; i < contentModel.countOfParticleChild(); ++i) {
                  buildEltList(eltList, contentModel.getParticleChild(i));
               }

               return;
            case 4:
               eltList.add(contentModel);
               return;
            default:
         }
      }
   }

   private void buildLocalElts() {
      List eltList = new ArrayList();
      buildEltList(eltList, this._contentModel);
      this._localElts = (SchemaLocalElement[])((SchemaLocalElement[])eltList.toArray(new SchemaLocalElement[eltList.size()]));
   }

   public SchemaLocalElement getLocalElementByIndex(int i) {
      SchemaLocalElement[] elts = this._localElts;
      if (elts == null) {
         this.buildLocalElts();
         elts = this._localElts;
      }

      return elts[i];
   }

   public int getIndexForLocalElement(SchemaLocalElement elt) {
      Map localEltMap = this._eltToIndexMap;
      if (localEltMap == null) {
         if (this._localElts == null) {
            this.buildLocalElts();
         }

         localEltMap = new HashMap();

         for(int i = 0; i < this._localElts.length; ++i) {
            ((Map)localEltMap).put(this._localElts[i], new Integer(i));
         }

         this._eltToIndexMap = (Map)localEltMap;
      }

      return (Integer)((Map)localEltMap).get(elt);
   }

   public int getIndexForLocalAttribute(SchemaLocalAttribute attr) {
      Map localAttrMap = this._attrToIndexMap;
      if (localAttrMap == null) {
         localAttrMap = new HashMap();
         SchemaLocalAttribute[] attrs = this._attributeModel.getAttributes();

         for(int i = 0; i < attrs.length; ++i) {
            ((Map)localAttrMap).put(attrs[i], new Integer(i));
         }

         this._attrToIndexMap = (Map)localAttrMap;
      }

      return (Integer)((Map)localAttrMap).get(attr);
   }

   public SchemaAttributeModel getAttributeModel() {
      return this._attributeModel;
   }

   public SchemaProperty[] getProperties() {
      if (this._propertyModelByElementName == null) {
         return this.getAttributeProperties();
      } else if (this._propertyModelByAttributeName == null) {
         return this.getElementProperties();
      } else {
         List list = new ArrayList();
         list.addAll(this._propertyModelByElementName.values());
         list.addAll(this._propertyModelByAttributeName.values());
         return (SchemaProperty[])((SchemaProperty[])list.toArray(new SchemaProperty[list.size()]));
      }
   }

   public SchemaProperty[] getDerivedProperties() {
      SchemaType baseType = this.getBaseType();
      if (baseType == null) {
         return this.getProperties();
      } else {
         List results = new ArrayList();
         if (this._propertyModelByElementName != null) {
            results.addAll(this._propertyModelByElementName.values());
         }

         if (this._propertyModelByAttributeName != null) {
            results.addAll(this._propertyModelByAttributeName.values());
         }

         Iterator it = results.iterator();

         while(it.hasNext()) {
            SchemaProperty prop = (SchemaProperty)it.next();
            SchemaProperty baseProp = prop.isAttribute() ? baseType.getAttributeProperty(prop.getName()) : baseType.getElementProperty(prop.getName());
            if (baseProp != null && eq(prop.getMinOccurs(), baseProp.getMinOccurs()) && eq(prop.getMaxOccurs(), baseProp.getMaxOccurs()) && prop.hasNillable() == baseProp.hasNillable() && eq(prop.getDefaultText(), baseProp.getDefaultText())) {
               it.remove();
            }
         }

         return (SchemaProperty[])((SchemaProperty[])results.toArray(new SchemaProperty[results.size()]));
      }
   }

   private static boolean eq(BigInteger a, BigInteger b) {
      if (a == null && b == null) {
         return true;
      } else {
         return a != null && b != null ? a.equals(b) : false;
      }
   }

   private static boolean eq(String a, String b) {
      if (a == null && b == null) {
         return true;
      } else {
         return a != null && b != null ? a.equals(b) : false;
      }
   }

   public SchemaProperty[] getElementProperties() {
      return this._propertyModelByElementName == null ? NO_PROPERTIES : (SchemaProperty[])((SchemaProperty[])this._propertyModelByElementName.values().toArray(new SchemaProperty[this._propertyModelByElementName.size()]));
   }

   public SchemaProperty[] getAttributeProperties() {
      return this._propertyModelByAttributeName == null ? NO_PROPERTIES : (SchemaProperty[])((SchemaProperty[])this._propertyModelByAttributeName.values().toArray(new SchemaProperty[this._propertyModelByAttributeName.size()]));
   }

   public SchemaProperty getElementProperty(QName eltName) {
      return this._propertyModelByElementName == null ? null : (SchemaProperty)this._propertyModelByElementName.get(eltName);
   }

   public SchemaProperty getAttributeProperty(QName attrName) {
      return this._propertyModelByAttributeName == null ? null : (SchemaProperty)this._propertyModelByAttributeName.get(attrName);
   }

   public boolean hasAllContent() {
      return this._hasAllContent;
   }

   public boolean isOrderSensitive() {
      return this._orderSensitive;
   }

   public void setOrderSensitive(boolean sensitive) {
      this.assertJavaizing();
      this._orderSensitive = sensitive;
   }

   public void setContentModel(SchemaParticle contentModel, SchemaAttributeModel attrModel, Map propertyModelByElementName, Map propertyModelByAttributeName, boolean isAll) {
      this.assertResolving();
      this._contentModel = contentModel;
      this._attributeModel = attrModel;
      this._propertyModelByElementName = propertyModelByElementName;
      this._propertyModelByAttributeName = propertyModelByAttributeName;
      this._hasAllContent = isAll;
      if (this._propertyModelByElementName != null) {
         this._validSubstitutions = new LinkedHashSet();
         Collection eltProps = this._propertyModelByElementName.values();
         Iterator it = eltProps.iterator();

         while(it.hasNext()) {
            SchemaProperty prop = (SchemaProperty)it.next();
            QName[] names = prop.acceptedNames();

            for(int i = 0; i < names.length; ++i) {
               if (!this._propertyModelByElementName.containsKey(names[i])) {
                  this._validSubstitutions.add(names[i]);
               }
            }
         }
      }

   }

   private boolean containsElements() {
      return this.getContentType() == 3 || this.getContentType() == 4;
   }

   public boolean hasAttributeWildcards() {
      return this._hasWildcardAttributes;
   }

   public boolean hasElementWildcards() {
      return this._hasWildcardElements;
   }

   public boolean isValidSubstitution(QName name) {
      return this._validSubstitutions.contains(name);
   }

   public SchemaType getElementType(QName eltName, QName xsiType, SchemaTypeLoader wildcardTypeLoader) {
      if (!this.isSimpleType() && this.containsElements() && !this.isNoType()) {
         SchemaType type = null;
         SchemaProperty prop = (SchemaProperty)this._propertyModelByElementName.get(eltName);
         if (prop != null) {
            type = prop.getType();
         } else {
            if (wildcardTypeLoader == null) {
               return BuiltinSchemaTypeSystem.ST_NO_TYPE;
            }

            if (!this._typedWildcardElements.contains(eltName) && !this._validSubstitutions.contains(eltName)) {
               if (type == null) {
                  return BuiltinSchemaTypeSystem.ST_NO_TYPE;
               }
            } else {
               SchemaGlobalElement elt = wildcardTypeLoader.findElement(eltName);
               if (elt == null) {
                  return BuiltinSchemaTypeSystem.ST_NO_TYPE;
               }

               type = elt.getType();
            }
         }

         if (xsiType != null && wildcardTypeLoader != null) {
            SchemaType itype = wildcardTypeLoader.findType(xsiType);
            if (itype != null && type.isAssignableFrom(itype)) {
               return itype;
            }
         }

         return type;
      } else {
         return BuiltinSchemaTypeSystem.ST_NO_TYPE;
      }
   }

   public SchemaType getAttributeType(QName attrName, SchemaTypeLoader wildcardTypeLoader) {
      if (!this.isSimpleType() && !this.isNoType()) {
         if (this.isURType()) {
            return BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         } else {
            SchemaProperty prop = (SchemaProperty)this._propertyModelByAttributeName.get(attrName);
            if (prop != null) {
               return prop.getType();
            } else if (this._typedWildcardAttributes.contains(attrName) && wildcardTypeLoader != null) {
               SchemaGlobalAttribute attr = wildcardTypeLoader.findAttribute(attrName);
               return (SchemaType)(attr == null ? BuiltinSchemaTypeSystem.ST_NO_TYPE : attr.getType());
            } else {
               return BuiltinSchemaTypeSystem.ST_NO_TYPE;
            }
         }
      } else {
         return BuiltinSchemaTypeSystem.ST_NO_TYPE;
      }
   }

   public XmlObject createElementType(QName eltName, QName xsiType, SchemaTypeLoader wildcardTypeLoader) {
      SchemaType type = null;
      SchemaProperty prop = null;
      if (!this.isSimpleType() && this.containsElements() && !this.isNoType()) {
         prop = (SchemaProperty)this._propertyModelByElementName.get(eltName);
         if (prop != null) {
            type = prop.getType();
         } else if (!this._typedWildcardElements.contains(eltName) && !this._validSubstitutions.contains(eltName)) {
            if (type == null) {
               type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
            }
         } else {
            SchemaGlobalElement elt = wildcardTypeLoader.findElement(eltName);
            if (elt != null) {
               type = elt.getType();
               SchemaType docType = wildcardTypeLoader.findDocumentType(eltName);
               if (docType != null) {
                  prop = docType.getElementProperty(eltName);
               }
            } else {
               type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
            }
         }

         if (xsiType != null) {
            SchemaType itype = wildcardTypeLoader.findType(xsiType);
            if (itype != null && ((SchemaType)type).isAssignableFrom(itype)) {
               type = itype;
            }
         }
      } else {
         type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
      }

      return type != null ? ((SchemaTypeImpl)type).createUnattachedNode(prop) : null;
   }

   public XmlObject createAttributeType(QName attrName, SchemaTypeLoader wildcardTypeLoader) {
      SchemaTypeImpl type = null;
      SchemaProperty prop = null;
      if (!this.isSimpleType() && !this.isNoType()) {
         if (this.isURType()) {
            type = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         } else {
            prop = (SchemaProperty)this._propertyModelByAttributeName.get(attrName);
            if (prop != null) {
               type = (SchemaTypeImpl)prop.getType();
            } else if (!this._typedWildcardAttributes.contains(attrName)) {
               type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
            } else {
               SchemaGlobalAttribute attr = wildcardTypeLoader.findAttribute(attrName);
               if (attr != null) {
                  type = (SchemaTypeImpl)attr.getType();
               } else {
                  type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
               }
            }
         }
      } else {
         type = BuiltinSchemaTypeSystem.ST_NO_TYPE;
      }

      return type != null ? type.createUnattachedNode(prop) : null;
   }

   public void setWildcardSummary(QNameSet elementSet, boolean haswcElt, QNameSet attributeSet, boolean haswcAtt) {
      this.assertResolving();
      this._typedWildcardElements = elementSet;
      this._hasWildcardElements = haswcElt;
      this._typedWildcardAttributes = attributeSet;
      this._hasWildcardAttributes = haswcAtt;
   }

   public SchemaType[] getAnonymousTypes() {
      SchemaType[] result = new SchemaType[this._anonymousTyperefs.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this._anonymousTyperefs[i].get();
      }

      return result;
   }

   public void setAnonymousTypeRefs(SchemaType.Ref[] anonymousTyperefs) {
      this._anonymousTyperefs = anonymousTyperefs;
   }

   private static SchemaType[] staCopy(SchemaType[] a) {
      if (a == null) {
         return null;
      } else {
         SchemaType[] result = new SchemaType[a.length];
         System.arraycopy(a, 0, result, 0, a.length);
         return result;
      }
   }

   private static boolean[] boaCopy(boolean[] a) {
      if (a == null) {
         return null;
      } else {
         boolean[] result = new boolean[a.length];
         System.arraycopy(a, 0, result, 0, a.length);
         return result;
      }
   }

   public void setSimpleTypeVariety(int variety) {
      this.assertResolving();
      this._simpleTypeVariety = variety;
   }

   public int getSimpleVariety() {
      return this._simpleTypeVariety;
   }

   public boolean isURType() {
      return this._builtinTypeCode == 1 || this._builtinTypeCode == 2;
   }

   public boolean isNoType() {
      return this == BuiltinSchemaTypeSystem.ST_NO_TYPE;
   }

   public boolean isSimpleType() {
      return this._isSimpleType;
   }

   public void setSimpleType(boolean f) {
      this.assertUnresolved();
      this._isSimpleType = f;
   }

   public boolean isUnionOfLists() {
      return this._isUnionOfLists;
   }

   public void setUnionOfLists(boolean f) {
      this.assertResolving();
      this._isUnionOfLists = f;
   }

   public SchemaType getPrimitiveType() {
      return this._primitiveTypeRef == null ? null : this._primitiveTypeRef.get();
   }

   public void setPrimitiveTypeRef(SchemaType.Ref typeref) {
      this.assertResolving();
      this._primitiveTypeRef = typeref;
   }

   public int getDecimalSize() {
      return this._decimalSize;
   }

   public void setDecimalSize(int bits) {
      this.assertResolving();
      this._decimalSize = bits;
   }

   public SchemaType getBaseType() {
      return this._baseTyperef == null ? null : this._baseTyperef.get();
   }

   public void setBaseTypeRef(SchemaType.Ref typeref) {
      this.assertResolving();
      this._baseTyperef = typeref;
   }

   public int getBaseDepth() {
      return this._baseDepth;
   }

   public void setBaseDepth(int depth) {
      this.assertResolving();
      this._baseDepth = depth;
   }

   public SchemaType getContentBasedOnType() {
      return this._contentBasedOnTyperef == null ? null : this._contentBasedOnTyperef.get();
   }

   public void setContentBasedOnTypeRef(SchemaType.Ref typeref) {
      this.assertResolving();
      this._contentBasedOnTyperef = typeref;
   }

   public int getDerivationType() {
      return this._derivationType;
   }

   public void setDerivationType(int type) {
      this.assertResolving();
      this._derivationType = type;
   }

   public SchemaType getListItemType() {
      return this._listItemTyperef == null ? null : this._listItemTyperef.get();
   }

   public void setListItemTypeRef(SchemaType.Ref typeref) {
      this.assertResolving();
      this._listItemTyperef = typeref;
   }

   public SchemaType[] getUnionMemberTypes() {
      SchemaType[] result = new SchemaType[this._unionMemberTyperefs == null ? 0 : this._unionMemberTyperefs.length];

      for(int i = 0; i < result.length; ++i) {
         result[i] = this._unionMemberTyperefs[i].get();
      }

      return result;
   }

   public void setUnionMemberTypeRefs(SchemaType.Ref[] typerefs) {
      this.assertResolving();
      this._unionMemberTyperefs = typerefs;
   }

   public int getAnonymousUnionMemberOrdinal() {
      return this._anonymousUnionMemberOrdinal;
   }

   public void setAnonymousUnionMemberOrdinal(int i) {
      this.assertUnresolved();
      this._anonymousUnionMemberOrdinal = i;
   }

   public synchronized SchemaType[] getUnionConstituentTypes() {
      if (this._unionCommonBaseType == null) {
         this.computeFlatUnionModel();
      }

      return staCopy(this._unionConstituentTypes);
   }

   private void setUnionConstituentTypes(SchemaType[] types) {
      this._unionConstituentTypes = types;
   }

   public synchronized SchemaType[] getUnionSubTypes() {
      if (this._unionCommonBaseType == null) {
         this.computeFlatUnionModel();
      }

      return staCopy(this._unionSubTypes);
   }

   private void setUnionSubTypes(SchemaType[] types) {
      this._unionSubTypes = types;
   }

   public synchronized SchemaType getUnionCommonBaseType() {
      if (this._unionCommonBaseType == null) {
         this.computeFlatUnionModel();
      }

      return this._unionCommonBaseType;
   }

   private void setUnionCommonBaseType(SchemaType type) {
      this._unionCommonBaseType = type;
   }

   private void computeFlatUnionModel() {
      if (this.getSimpleVariety() != 2) {
         throw new IllegalStateException("Operation is only supported on union types");
      } else {
         Set constituentMemberTypes = new LinkedHashSet();
         Set allSubTypes = new LinkedHashSet();
         SchemaType commonBaseType = null;
         allSubTypes.add(this);

         for(int i = 0; i < this._unionMemberTyperefs.length; ++i) {
            SchemaTypeImpl mImpl = (SchemaTypeImpl)this._unionMemberTyperefs[i].get();
            switch (mImpl.getSimpleVariety()) {
               case 1:
                  constituentMemberTypes.add(mImpl);
                  allSubTypes.add(mImpl);
                  commonBaseType = mImpl.getCommonBaseType(commonBaseType);
                  break;
               case 2:
                  constituentMemberTypes.addAll(Arrays.asList(mImpl.getUnionConstituentTypes()));
                  allSubTypes.addAll(Arrays.asList(mImpl.getUnionSubTypes()));
                  SchemaType otherCommonBaseType = mImpl.getUnionCommonBaseType();
                  if (otherCommonBaseType != null) {
                     commonBaseType = otherCommonBaseType.getCommonBaseType(commonBaseType);
                  }
                  break;
               case 3:
                  constituentMemberTypes.add(mImpl);
                  allSubTypes.add(mImpl);
                  commonBaseType = mImpl.getCommonBaseType(commonBaseType);
                  break;
               default:
                  assert false;
            }
         }

         this.setUnionConstituentTypes((SchemaType[])((SchemaType[])constituentMemberTypes.toArray(StscState.EMPTY_ST_ARRAY)));
         this.setUnionSubTypes((SchemaType[])((SchemaType[])allSubTypes.toArray(StscState.EMPTY_ST_ARRAY)));
         this.setUnionCommonBaseType(commonBaseType);
      }
   }

   public QName getSubstitutionGroup() {
      return this._sg;
   }

   public void setSubstitutionGroup(QName sg) {
      this.assertSGResolving();
      this._sg = sg;
   }

   public void addSubstitutionGroupMember(QName member) {
      this.assertSGResolved();
      this._sgMembers.add(member);
   }

   public QName[] getSubstitutionGroupMembers() {
      QName[] result = new QName[this._sgMembers.size()];
      return (QName[])((QName[])this._sgMembers.toArray(result));
   }

   public int getWhiteSpaceRule() {
      return this._whiteSpaceRule;
   }

   public void setWhiteSpaceRule(int ws) {
      this.assertResolving();
      this._whiteSpaceRule = ws;
   }

   public XmlAnySimpleType getFacet(int facetCode) {
      if (this._facetArray == null) {
         return null;
      } else {
         XmlValueRef ref = this._facetArray[facetCode];
         return ref == null ? null : ref.get();
      }
   }

   public boolean isFacetFixed(int facetCode) {
      return this._fixedFacetArray[facetCode];
   }

   public XmlAnySimpleType[] getBasicFacets() {
      XmlAnySimpleType[] result = new XmlAnySimpleType[12];

      for(int i = 0; i <= 11; ++i) {
         result[i] = this.getFacet(i);
      }

      return result;
   }

   public boolean[] getFixedFacets() {
      return boaCopy(this._fixedFacetArray);
   }

   public void setBasicFacets(XmlValueRef[] values, boolean[] fixed) {
      this.assertResolving();
      this._facetArray = values;
      this._fixedFacetArray = fixed;
   }

   public int ordered() {
      return this._ordered;
   }

   public void setOrdered(int ordering) {
      this.assertResolving();
      this._ordered = ordering;
   }

   public boolean isBounded() {
      return this._isBounded;
   }

   public void setBounded(boolean f) {
      this.assertResolving();
      this._isBounded = f;
   }

   public boolean isFinite() {
      return this._isFinite;
   }

   public void setFinite(boolean f) {
      this.assertResolving();
      this._isFinite = f;
   }

   public boolean isNumeric() {
      return this._isNumeric;
   }

   public void setNumeric(boolean f) {
      this.assertResolving();
      this._isNumeric = f;
   }

   public boolean hasPatternFacet() {
      return this._hasPatterns;
   }

   public void setPatternFacet(boolean hasPatterns) {
      this.assertResolving();
      this._hasPatterns = hasPatterns;
   }

   public boolean matchPatternFacet(String s) {
      if (!this._hasPatterns) {
         return true;
      } else {
         if (this._patterns != null && this._patterns.length > 0) {
            int i;
            for(i = 0; i < this._patterns.length && !this._patterns[i].matches(s); ++i) {
            }

            if (i >= this._patterns.length) {
               return false;
            }
         }

         return this.getBaseType().matchPatternFacet(s);
      }
   }

   public String[] getPatterns() {
      if (this._patterns == null) {
         return new String[0];
      } else {
         String[] patterns = new String[this._patterns.length];

         for(int i = 0; i < this._patterns.length; ++i) {
            patterns[i] = this._patterns[i].getPattern();
         }

         return patterns;
      }
   }

   public RegularExpression[] getPatternExpressions() {
      if (this._patterns == null) {
         return new RegularExpression[0];
      } else {
         RegularExpression[] result = new RegularExpression[this._patterns.length];
         System.arraycopy(this._patterns, 0, result, 0, this._patterns.length);
         return result;
      }
   }

   public void setPatterns(RegularExpression[] list) {
      this.assertResolving();
      this._patterns = list;
   }

   public XmlAnySimpleType[] getEnumerationValues() {
      if (this._enumerationValues == null) {
         return null;
      } else {
         XmlAnySimpleType[] result = new XmlAnySimpleType[this._enumerationValues.length];

         for(int i = 0; i < result.length; ++i) {
            XmlValueRef ref = this._enumerationValues[i];
            result[i] = ref == null ? null : ref.get();
         }

         return result;
      }
   }

   public void setEnumerationValues(XmlValueRef[] a) {
      this.assertResolving();
      this._enumerationValues = a;
   }

   public StringEnumAbstractBase enumForString(String s) {
      this.ensureStringEnumInfo();
      return this._lookupStringEnum == null ? null : (StringEnumAbstractBase)this._lookupStringEnum.get(s);
   }

   public StringEnumAbstractBase enumForInt(int i) {
      this.ensureStringEnumInfo();
      return this._listOfStringEnum != null && i >= 0 && i < this._listOfStringEnum.size() ? (StringEnumAbstractBase)this._listOfStringEnum.get(i) : null;
   }

   public SchemaStringEnumEntry enumEntryForString(String s) {
      this.ensureStringEnumInfo();
      return this._lookupStringEnumEntry == null ? null : (SchemaStringEnumEntry)this._lookupStringEnumEntry.get(s);
   }

   public SchemaType getBaseEnumType() {
      return this._baseEnumTyperef == null ? null : this._baseEnumTyperef.get();
   }

   public void setBaseEnumTypeRef(SchemaType.Ref baseEnumTyperef) {
      this._baseEnumTyperef = baseEnumTyperef;
   }

   public SchemaStringEnumEntry[] getStringEnumEntries() {
      if (this._stringEnumEntries == null) {
         return null;
      } else {
         SchemaStringEnumEntry[] result = new SchemaStringEnumEntry[this._stringEnumEntries.length];
         System.arraycopy(this._stringEnumEntries, 0, result, 0, result.length);
         return result;
      }
   }

   public void setStringEnumEntries(SchemaStringEnumEntry[] sEnums) {
      this.assertJavaizing();
      this._stringEnumEntries = sEnums;
   }

   private void ensureStringEnumInfo() {
      if (!this._stringEnumEnsured) {
         SchemaStringEnumEntry[] sEnums = this._stringEnumEntries;
         if (sEnums == null) {
            this._stringEnumEnsured = true;
         } else {
            Map lookupStringEnum = new HashMap(sEnums.length);
            List listOfStringEnum = new ArrayList(sEnums.length + 1);
            Map lookupStringEnumEntry = new HashMap(sEnums.length);

            for(int i = 0; i < sEnums.length; ++i) {
               lookupStringEnumEntry.put(sEnums[i].getString(), sEnums[i]);
            }

            Class jc = this._baseEnumTyperef.get().getEnumJavaClass();
            int j;
            if (jc != null) {
               try {
                  StringEnumAbstractBase.Table table = (StringEnumAbstractBase.Table)jc.getField("table").get((Object)null);

                  for(j = 0; j < sEnums.length; ++j) {
                     int j = sEnums[j].getIntValue();
                     StringEnumAbstractBase enumVal = table.forInt(j);
                     lookupStringEnum.put(sEnums[j].getString(), enumVal);

                     while(listOfStringEnum.size() <= j) {
                        listOfStringEnum.add((Object)null);
                     }

                     listOfStringEnum.set(j, enumVal);
                  }
               } catch (Exception var14) {
                  System.err.println("Something wrong: could not locate enum table for " + jc);
                  jc = null;
                  lookupStringEnum.clear();
                  listOfStringEnum.clear();
               }
            }

            if (jc == null) {
               for(int i = 0; i < sEnums.length; ++i) {
                  j = sEnums[i].getIntValue();
                  String s = sEnums[i].getString();
                  StringEnumAbstractBase enumVal = new StringEnumValue(s, j);
                  lookupStringEnum.put(s, enumVal);

                  while(listOfStringEnum.size() <= j) {
                     listOfStringEnum.add((Object)null);
                  }

                  listOfStringEnum.set(j, enumVal);
               }
            }

            synchronized(this) {
               if (!this._stringEnumEnsured) {
                  this._lookupStringEnum = lookupStringEnum;
                  this._listOfStringEnum = listOfStringEnum;
                  this._lookupStringEnumEntry = lookupStringEnumEntry;
               }
            }

            synchronized(this) {
               this._stringEnumEnsured = true;
            }
         }
      }
   }

   public boolean hasStringEnumValues() {
      return this._stringEnumEntries != null;
   }

   public void copyEnumerationValues(SchemaTypeImpl baseImpl) {
      this.assertResolving();
      this._enumerationValues = baseImpl._enumerationValues;
      this._baseEnumTyperef = baseImpl._baseEnumTyperef;
   }

   public int getBuiltinTypeCode() {
      return this._builtinTypeCode;
   }

   public void setBuiltinTypeCode(int builtinTypeCode) {
      this.assertResolving();
      this._builtinTypeCode = builtinTypeCode;
   }

   synchronized void assignJavaElementSetterModel() {
      SchemaProperty[] eltProps = this.getElementProperties();
      SchemaParticle contentModel = this.getContentModel();
      Map state = new HashMap();
      QNameSet allContents = computeAllContainedElements(contentModel, state);

      for(int i = 0; i < eltProps.length; ++i) {
         SchemaPropertyImpl sImpl = (SchemaPropertyImpl)eltProps[i];
         QNameSet nde = computeNondelimitingElements(sImpl.getName(), contentModel, state);
         QNameSetBuilder builder = new QNameSetBuilder(allContents);
         builder.removeAll(nde);
         sImpl.setJavaSetterDelimiter(builder.toQNameSet());
      }

   }

   private static QNameSet computeNondelimitingElements(QName target, SchemaParticle contentModel, Map state) {
      QNameSet allContents = computeAllContainedElements(contentModel, state);
      if (!allContents.contains(target)) {
         return QNameSet.EMPTY;
      } else if (contentModel.getMaxOccurs() != null && contentModel.getMaxOccurs().compareTo(BigInteger.ONE) <= 0) {
         QNameSetBuilder builder;
         switch (contentModel.getParticleType()) {
            case 1:
            case 4:
            default:
               return allContents;
            case 2:
               builder = new QNameSetBuilder();

               for(int i = 0; i < contentModel.countOfParticleChild(); ++i) {
                  QNameSet childContents = computeAllContainedElements(contentModel.getParticleChild(i), state);
                  if (childContents.contains(target)) {
                     builder.addAll(computeNondelimitingElements(target, contentModel.getParticleChild(i), state));
                  }
               }

               return builder.toQNameSet();
            case 3:
               builder = new QNameSetBuilder();
               boolean seenTarget = false;
               int i = contentModel.countOfParticleChild();

               while(i > 0) {
                  --i;
                  QNameSet childContents = computeAllContainedElements(contentModel.getParticleChild(i), state);
                  if (seenTarget) {
                     builder.addAll(childContents);
                  } else if (childContents.contains(target)) {
                     builder.addAll(computeNondelimitingElements(target, contentModel.getParticleChild(i), state));
                     seenTarget = true;
                  }
               }

               return builder.toQNameSet();
            case 5:
               return QNameSet.singleton(target);
         }
      } else {
         return allContents;
      }
   }

   private static QNameSet computeAllContainedElements(SchemaParticle contentModel, Map state) {
      QNameSet result = (QNameSet)state.get(contentModel);
      if (result != null) {
         return result;
      } else {
         switch (contentModel.getParticleType()) {
            case 1:
            case 2:
            case 3:
            default:
               QNameSetBuilder builder = new QNameSetBuilder();

               for(int i = 0; i < contentModel.countOfParticleChild(); ++i) {
                  builder.addAll(computeAllContainedElements(contentModel.getParticleChild(i), state));
               }

               result = builder.toQNameSet();
               break;
            case 4:
               result = ((SchemaLocalElementImpl)contentModel).acceptedStartNames();
               break;
            case 5:
               result = contentModel.getWildcardSet();
         }

         state.put(contentModel, result);
         return result;
      }
   }

   public Class getJavaClass() {
      if (this._javaClass == null && this.getFullJavaName() != null) {
         try {
            this._javaClass = Class.forName(this.getFullJavaName(), false, this.getTypeSystem().getClassLoader());
         } catch (ClassNotFoundException var2) {
            this._javaClass = null;
         }
      }

      return this._javaClass;
   }

   public Class getJavaImplClass() {
      if (this._implNotAvailable) {
         return null;
      } else {
         if (this._javaImplClass == null) {
            try {
               if (this.getFullJavaImplName() != null) {
                  this._javaImplClass = Class.forName(this.getFullJavaImplName(), false, this.getTypeSystem().getClassLoader());
               } else {
                  this._implNotAvailable = true;
               }
            } catch (ClassNotFoundException var2) {
               this._implNotAvailable = true;
            }
         }

         return this._javaImplClass;
      }
   }

   public Class getUserTypeClass() {
      if (this._userTypeClass == null && this.getUserTypeName() != null) {
         try {
            this._userTypeClass = Class.forName(this._userTypeName, false, this.getTypeSystem().getClassLoader());
         } catch (ClassNotFoundException var2) {
            this._userTypeClass = null;
         }
      }

      return this._userTypeClass;
   }

   public Class getUserTypeHandlerClass() {
      if (this._userTypeHandlerClass == null && this.getUserTypeHandlerName() != null) {
         try {
            this._userTypeHandlerClass = Class.forName(this._userTypeHandler, false, this.getTypeSystem().getClassLoader());
         } catch (ClassNotFoundException var2) {
            this._userTypeHandlerClass = null;
         }
      }

      return this._userTypeHandlerClass;
   }

   public Constructor getJavaImplConstructor() {
      if (this._javaImplConstructor == null && !this._implNotAvailable) {
         Class impl = this.getJavaImplClass();
         if (impl == null) {
            return null;
         }

         try {
            this._javaImplConstructor = impl.getConstructor(SchemaType.class);
         } catch (NoSuchMethodException var3) {
            var3.printStackTrace();
         }
      }

      return this._javaImplConstructor;
   }

   public Constructor getJavaImplConstructor2() {
      if (this._javaImplConstructor2 == null && !this._implNotAvailable) {
         Class impl = this.getJavaImplClass();
         if (impl == null) {
            return null;
         }

         try {
            this._javaImplConstructor2 = impl.getDeclaredConstructor(SchemaType.class, Boolean.TYPE);
         } catch (NoSuchMethodException var3) {
            var3.printStackTrace();
         }
      }

      return this._javaImplConstructor2;
   }

   public Class getEnumJavaClass() {
      if (this._javaEnumClass == null && this.getBaseEnumType() != null) {
         try {
            this._javaEnumClass = Class.forName(this.getBaseEnumType().getFullJavaName() + "$Enum", false, this.getTypeSystem().getClassLoader());
         } catch (ClassNotFoundException var2) {
            this._javaEnumClass = null;
         }
      }

      return this._javaEnumClass;
   }

   public void setJavaClass(Class javaClass) {
      this.assertResolved();
      this._javaClass = javaClass;
      this.setFullJavaName(javaClass.getName());
   }

   public boolean isPrimitiveType() {
      return this.getBuiltinTypeCode() >= 2 && this.getBuiltinTypeCode() <= 21;
   }

   public boolean isBuiltinType() {
      return this.getBuiltinTypeCode() != 0;
   }

   public XmlObject createUnwrappedNode() {
      XmlObject result = this.createUnattachedNode((SchemaProperty)null);
      return result;
   }

   public TypeStoreUser createTypeStoreUser() {
      return (TypeStoreUser)this.createUnattachedNode((SchemaProperty)null);
   }

   public XmlAnySimpleType newValidatingValue(Object obj) {
      return this.newValue(obj, true);
   }

   public XmlAnySimpleType newValue(Object obj) {
      return this.newValue(obj, false);
   }

   public XmlAnySimpleType newValue(Object obj, boolean validateOnSet) {
      if (!this.isSimpleType() && this.getContentType() != 2) {
         throw new XmlValueOutOfRangeException();
      } else {
         XmlObjectBase result = (XmlObjectBase)this.createUnattachedNode((SchemaProperty)null);
         if (validateOnSet) {
            result.setValidateOnSet();
         }

         if (obj instanceof XmlObject) {
            result.set_newValue((XmlObject)obj);
         } else {
            result.objectSet(obj);
         }

         result.check_dated();
         result.setImmutable();
         return (XmlAnySimpleType)result;
      }
   }

   private XmlObject createUnattachedNode(SchemaProperty prop) {
      XmlObject result = null;
      if (!this.isBuiltinType() && !this.isNoType()) {
         Constructor ctr = this.getJavaImplConstructor();
         if (ctr != null) {
            try {
               return (XmlObject)ctr.newInstance(this._ctrArgs);
            } catch (Exception var5) {
               System.out.println("Exception trying to instantiate impl class.");
               var5.printStackTrace();
            }
         }
      } else {
         result = this.createBuiltinInstance();
      }

      for(SchemaType sType = this; result == null; sType = ((SchemaType)sType).getBaseType()) {
         result = ((SchemaTypeImpl)sType).createUnattachedSubclass(this);
      }

      ((XmlObjectBase)result).init_flags(prop);
      return result;
   }

   private XmlObject createUnattachedSubclass(SchemaType sType) {
      if (!this.isBuiltinType() && !this.isNoType()) {
         Constructor ctr = this.getJavaImplConstructor2();
         if (ctr != null) {
            boolean accessible = ctr.isAccessible();

            try {
               ctr.setAccessible(true);

               XmlObject var4;
               try {
                  var4 = (XmlObject)ctr.newInstance(sType, sType.isSimpleType() ? Boolean.FALSE : Boolean.TRUE);
               } catch (Exception var15) {
                  System.out.println("Exception trying to instantiate impl class.");
                  var15.printStackTrace();
                  return null;
               } finally {
                  try {
                     ctr.setAccessible(accessible);
                  } catch (SecurityException var14) {
                  }

               }

               return var4;
            } catch (Exception var17) {
               System.out.println("Exception trying to instantiate impl class.");
               var17.printStackTrace();
            }
         }

         return null;
      } else {
         return this.createBuiltinSubclass(sType);
      }
   }

   private XmlObject createBuiltinInstance() {
      switch (this.getBuiltinTypeCode()) {
         case 0:
            return new XmlAnyTypeImpl(BuiltinSchemaTypeSystem.ST_NO_TYPE);
         case 1:
            return new XmlAnyTypeImpl();
         case 2:
            return new XmlAnySimpleTypeImpl();
         case 3:
            return new XmlBooleanImpl();
         case 4:
            return new XmlBase64BinaryImpl();
         case 5:
            return new XmlHexBinaryImpl();
         case 6:
            return new XmlAnyUriImpl();
         case 7:
            return new XmlQNameImpl();
         case 8:
            return new XmlNotationImpl();
         case 9:
            return new XmlFloatImpl();
         case 10:
            return new XmlDoubleImpl();
         case 11:
            return new XmlDecimalImpl();
         case 12:
            return new XmlStringImpl();
         case 13:
            return new XmlDurationImpl();
         case 14:
            return new XmlDateTimeImpl();
         case 15:
            return new XmlTimeImpl();
         case 16:
            return new XmlDateImpl();
         case 17:
            return new XmlGYearMonthImpl();
         case 18:
            return new XmlGYearImpl();
         case 19:
            return new XmlGMonthDayImpl();
         case 20:
            return new XmlGDayImpl();
         case 21:
            return new XmlGMonthImpl();
         case 22:
            return new XmlIntegerImpl();
         case 23:
            return new XmlLongImpl();
         case 24:
            return new XmlIntImpl();
         case 25:
            return new XmlShortImpl();
         case 26:
            return new XmlByteImpl();
         case 27:
            return new XmlNonPositiveIntegerImpl();
         case 28:
            return new XmlNegativeIntegerImpl();
         case 29:
            return new XmlNonNegativeIntegerImpl();
         case 30:
            return new XmlPositiveIntegerImpl();
         case 31:
            return new XmlUnsignedLongImpl();
         case 32:
            return new XmlUnsignedIntImpl();
         case 33:
            return new XmlUnsignedShortImpl();
         case 34:
            return new XmlUnsignedByteImpl();
         case 35:
            return new XmlNormalizedStringImpl();
         case 36:
            return new XmlTokenImpl();
         case 37:
            return new XmlNameImpl();
         case 38:
            return new XmlNCNameImpl();
         case 39:
            return new XmlLanguageImpl();
         case 40:
            return new XmlIdImpl();
         case 41:
            return new XmlIdRefImpl();
         case 42:
            return new XmlIdRefsImpl();
         case 43:
            return new XmlEntityImpl();
         case 44:
            return new XmlEntitiesImpl();
         case 45:
            return new XmlNmTokenImpl();
         case 46:
            return new XmlNmTokensImpl();
         default:
            throw new IllegalStateException("Unrecognized builtin type: " + this.getBuiltinTypeCode());
      }
   }

   private XmlObject createBuiltinSubclass(SchemaType sType) {
      boolean complex = !sType.isSimpleType();
      switch (this.getBuiltinTypeCode()) {
         case 0:
            return new XmlAnyTypeImpl(BuiltinSchemaTypeSystem.ST_NO_TYPE);
         case 1:
         case 2:
            switch (sType.getSimpleVariety()) {
               case 0:
                  return new XmlComplexContentImpl(sType);
               case 1:
                  return new XmlAnySimpleTypeRestriction(sType, complex);
               case 2:
                  return new XmlUnionImpl(sType, complex);
               case 3:
                  return new XmlListImpl(sType, complex);
               default:
                  throw new IllegalStateException();
            }
         case 3:
            return new XmlBooleanRestriction(sType, complex);
         case 4:
            return new XmlBase64BinaryRestriction(sType, complex);
         case 5:
            return new XmlHexBinaryRestriction(sType, complex);
         case 6:
            return new XmlAnyUriRestriction(sType, complex);
         case 7:
            return new XmlQNameRestriction(sType, complex);
         case 8:
            return new XmlNotationRestriction(sType, complex);
         case 9:
            return new XmlFloatRestriction(sType, complex);
         case 10:
            return new XmlDoubleRestriction(sType, complex);
         case 11:
            return new XmlDecimalRestriction(sType, complex);
         case 12:
            if (sType.hasStringEnumValues()) {
               return new XmlStringEnumeration(sType, complex);
            }

            return new XmlStringRestriction(sType, complex);
         case 13:
            return new XmlDurationImpl(sType, complex);
         case 14:
            return new XmlDateTimeImpl(sType, complex);
         case 15:
            return new XmlTimeImpl(sType, complex);
         case 16:
            return new XmlDateImpl(sType, complex);
         case 17:
            return new XmlGYearMonthImpl(sType, complex);
         case 18:
            return new XmlGYearImpl(sType, complex);
         case 19:
            return new XmlGMonthDayImpl(sType, complex);
         case 20:
            return new XmlGDayImpl(sType, complex);
         case 21:
            return new XmlGMonthImpl(sType, complex);
         case 22:
            return new XmlIntegerRestriction(sType, complex);
         case 23:
            return new XmlLongRestriction(sType, complex);
         case 24:
            return new XmlIntRestriction(sType, complex);
         case 25:
            return new XmlShortImpl(sType, complex);
         case 26:
            return new XmlByteImpl(sType, complex);
         case 27:
            return new XmlNonPositiveIntegerImpl(sType, complex);
         case 28:
            return new XmlNegativeIntegerImpl(sType, complex);
         case 29:
            return new XmlNonNegativeIntegerImpl(sType, complex);
         case 30:
            return new XmlPositiveIntegerImpl(sType, complex);
         case 31:
            return new XmlUnsignedLongImpl(sType, complex);
         case 32:
            return new XmlUnsignedIntImpl(sType, complex);
         case 33:
            return new XmlUnsignedShortImpl(sType, complex);
         case 34:
            return new XmlUnsignedByteImpl(sType, complex);
         case 35:
            return new XmlNormalizedStringImpl(sType, complex);
         case 36:
            return new XmlTokenImpl(sType, complex);
         case 37:
            return new XmlNameImpl(sType, complex);
         case 38:
            return new XmlNCNameImpl(sType, complex);
         case 39:
            return new XmlLanguageImpl(sType, complex);
         case 40:
            return new XmlIdImpl(sType, complex);
         case 41:
            return new XmlIdRefImpl(sType, complex);
         case 42:
            return new XmlIdRefsImpl(sType, complex);
         case 43:
            return new XmlEntityImpl(sType, complex);
         case 44:
            return new XmlEntitiesImpl(sType, complex);
         case 45:
            return new XmlNmTokenImpl(sType, complex);
         case 46:
            return new XmlNmTokensImpl(sType, complex);
         default:
            throw new IllegalStateException("Unrecognized builtin type: " + this.getBuiltinTypeCode());
      }
   }

   public SchemaType getCommonBaseType(SchemaType type) {
      if (this != BuiltinSchemaTypeSystem.ST_ANY_TYPE && type != null && !type.isNoType()) {
         if (type != BuiltinSchemaTypeSystem.ST_ANY_TYPE && !this.isNoType()) {
            SchemaTypeImpl sImpl1;
            for(sImpl1 = (SchemaTypeImpl)type; sImpl1.getBaseDepth() > this.getBaseDepth(); sImpl1 = (SchemaTypeImpl)sImpl1.getBaseType()) {
            }

            SchemaTypeImpl sImpl2;
            for(sImpl2 = this; sImpl2.getBaseDepth() > sImpl1.getBaseDepth(); sImpl2 = (SchemaTypeImpl)sImpl2.getBaseType()) {
            }

            do {
               do {
                  if (sImpl1.equals(sImpl2)) {
                     return sImpl1;
                  }

                  sImpl1 = (SchemaTypeImpl)sImpl1.getBaseType();
                  sImpl2 = (SchemaTypeImpl)sImpl2.getBaseType();
               } while($assertionsDisabled);
            } while(sImpl1 != null && sImpl2 != null);

            throw new AssertionError();
         } else {
            return type;
         }
      } else {
         return this;
      }
   }

   public boolean isAssignableFrom(SchemaType type) {
      if (type != null && !type.isNoType()) {
         if (this.isNoType()) {
            return false;
         } else {
            if (this.getSimpleVariety() == 2) {
               SchemaType[] members = this.getUnionMemberTypes();

               for(int i = 0; i < members.length; ++i) {
                  if (members[i].isAssignableFrom(type)) {
                     return true;
                  }
               }
            }

            int depth = ((SchemaTypeImpl)type).getBaseDepth() - this.getBaseDepth();
            if (depth < 0) {
               return false;
            } else {
               while(depth > 0) {
                  type = type.getBaseType();
                  --depth;
               }

               return type.equals(this);
            }
         }
      } else {
         return true;
      }
   }

   public String toString() {
      if (this.getName() != null) {
         return "T=" + QNameHelper.pretty(this.getName());
      } else if (this.isDocumentType()) {
         return "D=" + QNameHelper.pretty(this.getDocumentElementName());
      } else if (this.isAttributeType()) {
         return "R=" + QNameHelper.pretty(this.getAttributeTypeAttributeName());
      } else {
         String prefix;
         if (this.getContainerField() != null) {
            prefix = (this.getContainerField().getName().getNamespaceURI().length() > 0 ? (this.getContainerField().isAttribute() ? "Q=" : "E=") : (this.getContainerField().isAttribute() ? "A=" : "U=")) + this.getContainerField().getName().getLocalPart();
            if (this.getOuterType() == null) {
               return prefix + "@" + this.getContainerField().getName().getNamespaceURI();
            }
         } else {
            if (this.isNoType()) {
               return "N=";
            }

            if (this.getOuterType() == null) {
               return "noouter";
            }

            if (this.getOuterType().getBaseType() == this) {
               prefix = "B=";
            } else if (this.getOuterType().getContentBasedOnType() == this) {
               prefix = "S=";
            } else if (this.getOuterType().getSimpleVariety() == 3) {
               prefix = "I=";
            } else if (this.getOuterType().getSimpleVariety() == 2) {
               prefix = "M=" + this.getAnonymousUnionMemberOrdinal();
            } else {
               prefix = "strange=";
            }
         }

         return prefix + "|" + this.getOuterType().toString();
      }
   }

   public void setParseContext(XmlObject parseObject, String targetNamespace, boolean chameleon, String elemFormDefault, String attFormDefault, boolean redefinition) {
      this._parseObject = parseObject;
      this._parseTNS = targetNamespace;
      this._chameleon = chameleon;
      this._elemFormDefault = elemFormDefault;
      this._attFormDefault = attFormDefault;
      this._redefinition = redefinition;
   }

   public XmlObject getParseObject() {
      return this._parseObject;
   }

   public String getTargetNamespace() {
      return this._parseTNS;
   }

   public boolean isChameleon() {
      return this._chameleon;
   }

   public String getElemFormDefault() {
      return this._elemFormDefault;
   }

   public String getAttFormDefault() {
      return this._attFormDefault;
   }

   public String getChameleonNamespace() {
      return this._chameleon ? this._parseTNS : null;
   }

   public boolean isRedefinition() {
      return this._redefinition;
   }

   public SchemaType.Ref getRef() {
      return this._selfref;
   }

   public SchemaComponent.Ref getComponentRef() {
      return this.getRef();
   }

   public QNameSet qnameSetForWildcardElements() {
      SchemaParticle model = this.getContentModel();
      QNameSetBuilder wildcardSet = new QNameSetBuilder();
      computeWildcardSet(model, wildcardSet);
      QNameSetBuilder qnsb = new QNameSetBuilder(wildcardSet);
      SchemaProperty[] props = this.getElementProperties();

      for(int i = 0; i < props.length; ++i) {
         SchemaProperty prop = props[i];
         qnsb.remove(prop.getName());
      }

      return qnsb.toQNameSet();
   }

   private static void computeWildcardSet(SchemaParticle model, QNameSetBuilder result) {
      if (model.getParticleType() == 5) {
         QNameSet cws = model.getWildcardSet();
         result.addAll(cws);
      } else {
         for(int i = 0; i < model.countOfParticleChild(); ++i) {
            SchemaParticle child = model.getParticleChild(i);
            computeWildcardSet(child, result);
         }

      }
   }

   public QNameSet qnameSetForWildcardAttributes() {
      SchemaAttributeModel model = this.getAttributeModel();
      QNameSet wildcardSet = model.getWildcardSet();
      if (wildcardSet == null) {
         return QNameSet.EMPTY;
      } else {
         QNameSetBuilder qnsb = new QNameSetBuilder(wildcardSet);
         SchemaProperty[] props = this.getAttributeProperties();

         for(int i = 0; i < props.length; ++i) {
            SchemaProperty prop = props[i];
            qnsb.remove(prop.getName());
         }

         return qnsb.toQNameSet();
      }
   }

   private static class SequencerImpl implements SchemaTypeElementSequencer {
      private SchemaTypeVisitorImpl _visitor;

      private SequencerImpl(SchemaTypeVisitorImpl visitor) {
         this._visitor = visitor;
      }

      public boolean next(QName elementName) {
         return this._visitor == null ? false : this._visitor.visit(elementName);
      }

      public boolean peek(QName elementName) {
         return this._visitor == null ? false : this._visitor.testValid(elementName);
      }

      // $FF: synthetic method
      SequencerImpl(SchemaTypeVisitorImpl x0, Object x1) {
         this(x0);
      }
   }
}
