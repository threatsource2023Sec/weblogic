package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.staxb.runtime.internal.util.collections.AccumulatorFactory;
import com.bea.staxb.runtime.internal.util.collections.IntList;
import com.bea.xml.XmlException;
import com.bea.xml.soap.SOAPArrayType;
import java.lang.reflect.Array;
import java.util.Collection;
import javax.xml.namespace.QName;

final class SoapArrayRuntimeBindingType extends RuntimeBindingType {
   private final SoapArrayType soapArrayType;
   private final QName multiRefName;
   private final boolean isCollection;
   private ItemProperty elementProperty;
   private SOAPArrayType soapArrayDescriptor;
   private static final QName DEFAULT_ITEM_NAME = new QName("item");
   private static final XmlTypeName STRING_XMLTYPE = XmlTypeName.forTypeNamed(new QName("http://www.w3.org/2001/XMLSchema", "string"));
   private static final BindingTypeName CHARBTN;
   private static final BindingTypeName CHARACTERBTN;
   private static final BindingTypeName STRINGBTN;

   SoapArrayRuntimeBindingType(SoapArrayType binding_type) throws XmlException {
      super(binding_type);
      this.soapArrayType = binding_type;
      this.multiRefName = determineMultiRefName(binding_type);
      this.isCollection = Collection.class.isAssignableFrom(this.getJavaType());
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      BindingTypeName item_type_name = this.soapArrayType.getItemType();

      assert item_type_name != null;

      item_type_name = this.handleCharAndCharacter(item_type_name);
      BindingType item_type = bindingLoader.getBindingType(item_type_name);
      if (item_type == null) {
         String msg = "unable to lookup " + item_type_name + " from type " + this.soapArrayType;
         throw new XmlException(msg);
      } else {
         RuntimeBindingType item_rtt = typeTable.createRuntimeType(item_type, bindingLoader);
         XmlTypeName item_xmlname = this.soapArrayType.getItemType().getXmlName();
         QName item_name = this.determineItemName(item_xmlname);
         this.elementProperty = new ItemProperty(this, item_name, item_rtt, this.soapArrayType.isItemNillable());
         this.soapArrayDescriptor = this.createSoapArrayDescriptor();
      }
   }

   private BindingTypeName handleCharAndCharacter(BindingTypeName item_type_name) {
      if (STRINGBTN.equals(item_type_name)) {
         Class componentType;
         for(componentType = this.getJavaType().getComponentType(); componentType.isArray(); componentType = componentType.getComponentType()) {
         }

         if (componentType == Character.TYPE) {
            item_type_name = CHARBTN;
         } else if (componentType == Character.class) {
            item_type_name = CHARACTERBTN;
         }
      }

      return item_type_name;
   }

   protected boolean needsXsiType(RuntimeBindingType expected, MarshalResult result) {
      return result.isForceXsiType();
   }

   protected final QName getXsiTypeName() {
      return Soap11Constants.ARRAY_NAME;
   }

   boolean isJavaCollection() {
      return this.isCollection;
   }

   private SOAPArrayType createSoapArrayDescriptor() {
      int ranks = this.soapArrayType.getRanks();

      assert ranks > 0;

      IntList rank_list = new IntList();
      RuntimeBindingType curr_type = this;

      XmlTypeName candidate_name;
      SoapArrayRuntimeBindingType curr_soap_type;
      for(candidate_name = null; curr_type instanceof SoapArrayRuntimeBindingType; curr_type = curr_soap_type.elementProperty.getRuntimeBindingType()) {
         curr_soap_type = (SoapArrayRuntimeBindingType)curr_type;
         candidate_name = curr_soap_type.soapArrayType.getItemType().getXmlName();
         rank_list.add(curr_soap_type.getRanks());
      }

      assert candidate_name != null;

      assert !isSoapArray(candidate_name);

      StringBuffer fake = new StringBuffer();
      int[] all_ranks = rank_list.getMinSizedArray();

      for(int i = all_ranks.length - 1; i >= 0; --i) {
         fake.append('[');
         int r = all_ranks[i];

         for(int j = 1; j < r; ++j) {
            fake.append(',');
         }

         fake.append(']');
      }

      QName fake_name = candidate_name.getQName();

      assert fake_name != null;

      SOAPArrayType the_fake = new SOAPArrayType(fake_name, fake.toString());
      return the_fake;
   }

   private QName determineItemName(XmlTypeName item_xmlname) {
      QName item_name = this.soapArrayType.getItemName();
      if (item_name == null) {
         if (isSoapArray(item_xmlname)) {
            item_name = Soap11Constants.ARRAY_NAME;
         } else {
            item_name = DEFAULT_ITEM_NAME;
         }
      }

      assert item_name != null;

      return item_name;
   }

   private static boolean isSoapArray(XmlTypeName item_xmlname) {
      return item_xmlname.getComponentType() == 121;
   }

   private static QName determineMultiRefName(BindingType binding_type) {
      XmlTypeName xml_type_name = binding_type.getName().getXmlName();
      if (isSoapArray(xml_type_name)) {
         return Soap11Constants.ARRAY_NAME;
      } else {
         QName name = xml_type_name.getQName();
         return name == null ? DEFAULT_MULTI_NAME : name;
      }
   }

   protected QName getMultiRefElementName() {
      return this.multiRefName;
   }

   void predefineNamespaces(Object instance, MarshalResult result) throws XmlException {
      String uri = this.elementProperty.getName().getNamespaceURI();
      if (!"".equals(uri)) {
         if (WrappedArrayRuntimeBindingType.hasSignificantContent(instance)) {
            result.ensureElementPrefix(uri);
         }
      }
   }

   boolean hasElementChildren() {
      return true;
   }

   int getRanks() {
      return this.soapArrayType.getRanks();
   }

   boolean isMultiDimensional() {
      return this.getRanks() > 1;
   }

   public SOAPArrayType getSoapArrayDescriptor() {
      return this.soapArrayDescriptor;
   }

   ItemProperty getElementProperty() {
      assert this.elementProperty != null;

      return this.elementProperty;
   }

   protected Object createIntermediary(UnmarshalResult context) throws XmlException {
      SOAPArrayType soap_type = context.extractSoapArrayType();
      if (soap_type == null) {
         throw new XmlException("required soap array type not present");
      } else {
         QName expected_elem_type = this.soapArrayDescriptor.getQName();
         if (!soap_type.getQName().equals(expected_elem_type)) {
            throw new AssertionError("POLY soap array UNIMP expected: " + expected_elem_type + " got " + soap_type.getQName());
         } else if (!this.soapArrayDescriptor.isSameRankAs(soap_type)) {
            throw new XmlException("unexpected soap array type " + soap_type.getRanks());
         } else {
            int[] dims = soap_type.getDimensions();
            if (dims.length > 1) {
               return AccumulatorFactory.createMultiDimSoapArrayAccumulator(this.getJavaType(), this.elementProperty.getElementClass(), dims);
            } else {
               int dim = dims[0];
               if (dim < 0) {
                  dim = 0;
               }

               return AccumulatorFactory.createAccumulator(this.getJavaType(), this.elementProperty.getElementClass(), dim);
            }
         }
      }
   }

   protected Object getFinalObjectFromIntermediary(Object inter, UnmarshalResult context) throws XmlException {
      Accumulator acc = (Accumulator)inter;
      return acc.getFinalArray();
   }

   static Object getFinalObjectFromIntermediary(Object inter) {
      Accumulator acc = (Accumulator)inter;
      return acc.getFinalArray();
   }

   boolean isObjectFromIntermediateIdempotent() {
      return false;
   }

   protected RuntimeBindingType determineActualRuntimeType(UnmarshalResult context) throws XmlException {
      SOAPArrayType soap_type = context.extractSoapArrayType();
      QName expected_elem_type;
      if (soap_type != null) {
         expected_elem_type = this.soapArrayDescriptor.getQName();
         if (soap_type.getQName().equals(expected_elem_type) && this.soapArrayDescriptor.isSameRankAs(soap_type)) {
            return this;
         } else {
            BindingType btype = this.createBindingTypeFromSoapArrayType(soap_type);
            return context.getRuntimeType(btype);
         }
      } else {
         expected_elem_type = context.getXsiType();
         return (RuntimeBindingType)(Soap11Constants.ARRAY_NAME.equals(expected_elem_type) && context.hasXsiNil() ? this : super.determineActualRuntimeType(context));
      }
   }

   private BindingType createBindingTypeFromSoapArrayType(SOAPArrayType soap_type) {
      throw new AssertionError("polymorphic soap arrays UNIMPLEMENTED");
   }

   static {
      CHARBTN = BindingTypeName.forPair(JavaTypeName.forString("char"), STRING_XMLTYPE);
      CHARACTERBTN = BindingTypeName.forPair(JavaTypeName.forString("java.lang.Character"), STRING_XMLTYPE);
      STRINGBTN = BindingTypeName.forPair(JavaTypeName.forString("java.lang.String"), STRING_XMLTYPE);
   }

   static final class ItemProperty extends RuntimeBindingProperty {
      private final SoapArrayRuntimeBindingType containingType;
      private final QName itemName;
      private final RuntimeBindingType itemType;
      private final boolean nillable;

      ItemProperty(SoapArrayRuntimeBindingType containing_type, QName item_name, RuntimeBindingType item_type, boolean nillable) throws XmlException {
         super(containing_type);
         this.containingType = containing_type;

         assert item_name != null;

         this.itemName = item_name;
         this.itemType = item_type;
         this.nillable = nillable;
      }

      Class getElementClass() {
         return this.itemType.getJavaType();
      }

      RuntimeBindingType getRuntimeBindingType() {
         return this.itemType;
      }

      QName getName() {
         return this.itemName;
      }

      RuntimeBindingType getContainingType() {
         return this.containingType;
      }

      void setValue(Object target, Object prop_obj) throws XmlException {
         throw new AssertionError("not used");
      }

      public void fill(Object inter, Object prop_obj) throws XmlException {
         Accumulator acc = (Accumulator)inter;
         acc.append(prop_obj);
      }

      protected void fillPlaceholder(Object inter) {
         Accumulator acc = (Accumulator)inter;
         acc.appendDefault();
      }

      Object getValue(Object parentObject) throws XmlException {
         throw new UnsupportedOperationException("use 3 arg getValue");
      }

      Object getValue(Object parentObject, MarshalResult result, int item_index) throws XmlException {
         return Array.get(parentObject, item_index);
      }

      boolean isSet(Object parentObject) throws XmlException {
         throw new UnsupportedOperationException("use 3 arg isSet");
      }

      boolean isSet(Object parentObject, MarshalResult result, int item_index) throws XmlException {
         if (this.nillable) {
            return true;
         } else if (this.itemType.isJavaPrimitive()) {
            return true;
         } else {
            return this.getValue(parentObject, result, item_index) != null;
         }
      }

      boolean isMultiple() {
         return true;
      }

      boolean isNillable() {
         return this.nillable;
      }

      boolean isOptional() {
         return false;
      }

      String getLexicalDefault() {
         return null;
      }

      boolean isTransient(Object currObject) {
         return false;
      }
   }
}
