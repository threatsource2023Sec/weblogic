package weblogic.management.commo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.XMLParseException;

public class DescriptorSupport implements Descriptor {
   static final long serialVersionUID = 8071560848919417985L;
   public String currClass = null;
   HashMap descriptor = null;
   private ArrayList descriptor2 = null;
   private Descriptor delegate = null;
   private int fieldCount = 0;
   private int voidCount = 0;
   static final int defaultSize = 2;
   static final ArrayList emptyList = new ArrayList(0);
   static final VoidValue voidValue = new VoidValue();

   public DescriptorSupport() {
   }

   public DescriptorSupport(int initNumFields) throws MBeanException, RuntimeOperationsException {
      if (initNumFields <= 0) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor field limit is invalid"), "Exception occured trying to construct a descriptor");
      } else {
         this.descriptor2 = new ArrayList(initNumFields);
      }
   }

   public DescriptorSupport(DescriptorSupport inDescr) {
      copy(this, inDescr);
   }

   private static void copy(DescriptorSupport into, DescriptorSupport from) {
      into.delegate = from;
      if (into.delegate != null) {
         into.fieldCount = ((DescriptorSupport)into.delegate).getFieldCount();
         into.voidCount = ((DescriptorSupport)into.delegate).getVoidCount();
      }

      while(into.delegate != null && into.delegate instanceof DescriptorSupport && ((DescriptorSupport)into.delegate).getFieldCountLocal() == 0) {
         into.delegate = ((DescriptorSupport)into.delegate).delegate;
      }

   }

   protected int getFieldCount() {
      this.getDescriptor();
      return this.fieldCount;
   }

   protected int getFieldCountLocal() {
      int count = 0;
      Iterator it = this.getDescriptor().iterator();

      while(it.hasNext()) {
         Pair p = (Pair)it.next();
         if (!(p.value instanceof VoidValue)) {
            ++count;
         }
      }

      return count;
   }

   protected int getVoidCount() {
      this.getDescriptor();
      return this.voidCount;
   }

   public DescriptorSupport(String inStr) throws MBeanException, RuntimeOperationsException, XMLParseException {
      if (inStr == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor is null or invalid"), "Exception occured trying to construct a descriptor");
      } else {
         this.descriptor2 = new ArrayList();
         StringTokenizer st = new StringTokenizer(inStr, "<> \t\n\r\f");
         boolean inFld = false;
         boolean inDesc = false;
         String fieldName = null;
         String fieldValue = null;

         while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equalsIgnoreCase("FIELD")) {
               inFld = true;
            } else if (tok.equalsIgnoreCase("/FIELD")) {
               if (fieldName != null && fieldValue != null) {
                  this.setField(fieldName.toLowerCase(), fieldValue);
               }

               fieldName = null;
               fieldValue = null;
               inFld = false;
            } else if (tok.equalsIgnoreCase("DESCRIPTOR")) {
               inDesc = true;
            } else if (tok.equalsIgnoreCase("/DESCRIPTOR")) {
               inDesc = false;
               fieldName = null;
               fieldValue = null;
               inFld = false;
            } else if (inFld && inDesc) {
               int eq_separator = tok.indexOf("=");
               if (eq_separator <= 0) {
                  throw new XMLParseException("expected keyword=value, received '" + tok + "'");
               }

               String kwPart = tok.substring(0, eq_separator);
               String valPart = tok.substring(eq_separator + 1);
               if (kwPart.equalsIgnoreCase("NAME")) {
                  fieldName = new String(valPart);
               } else {
                  if (!kwPart.equalsIgnoreCase("VALUE")) {
                     throw new XMLParseException("expected a field value, received '" + tok + "'");
                  }

                  fieldValue = new String(valPart);
               }
            }
         }

      }
   }

   public DescriptorSupport(String[] fieldNames, Object[] fieldValues) throws RuntimeOperationsException {
      if ((fieldNames != null || fieldValues != null) && (fieldNames.length != 0 || fieldValues.length != 0)) {
         if (fieldNames != null && fieldValues != null && fieldNames.length == fieldValues.length) {
            this.descriptor2 = new ArrayList(fieldNames.length);

            for(int i = 0; i < fieldNames.length; ++i) {
               this.setField(fieldNames[i], fieldValues[i]);
            }

         } else {
            throw new RuntimeOperationsException(new IllegalArgumentException("FieldNames or FieldValues are null or invalid"), "Exception occured trying to construct a descriptor");
         }
      } else {
         this.descriptor2 = new ArrayList();
      }
   }

   public DescriptorSupport(String[] fields) {
      if (fields != null && fields.length != 0) {
         this.descriptor2 = new ArrayList(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i] != null && !fields[i].equals("")) {
               int eq_separator = fields[i].indexOf("=");
               if (eq_separator <= 0) {
                  throw new RuntimeOperationsException(new IllegalArgumentException("Field in invalid format: no equals sign"), "Exception occured trying to construct a descriptor");
               }

               String fieldName = fields[i].substring(0, eq_separator);
               String fieldValue = null;
               if (eq_separator < fields[i].length()) {
                  fieldValue = fields[i].substring(eq_separator + 1);
               }

               if (fieldName == null || fieldName.equals("")) {
                  throw new RuntimeOperationsException(new IllegalArgumentException("Field in invalid format: no equals sign"), "Exception occured trying to construct a descriptor");
               }

               this.setField(fieldName, fieldValue);
            }
         }

      } else {
         this.descriptor2 = new ArrayList();
      }
   }

   private ArrayList getDescriptor() {
      return this.getDescriptor(false);
   }

   private ArrayList getDescriptor(boolean write) {
      if (this.descriptor2 == null) {
         if (!write && this.descriptor != null && this.descriptor.size() == 0) {
            return emptyList;
         }

         this.descriptor2 = new ArrayList(2);
         if (this.descriptor != null) {
            Set returnedSet = this.descriptor.entrySet();

            for(Iterator iter = returnedSet.iterator(); iter.hasNext(); ++this.fieldCount) {
               Map.Entry currElement = (Map.Entry)iter.next();
               this.descriptor2.add(new Pair(currElement.getKey(), currElement.getValue()));
            }

            this.descriptor = null;
         }
      }

      return this.descriptor2;
   }

   public Object getFieldValue(String inFieldName) throws RuntimeOperationsException {
      Object value = this.getFieldValueX(inFieldName);
      return value == null && inFieldName.equalsIgnoreCase("displayname") ? this.getFieldValueX("Name") : value;
   }

   private Object getFieldValueX(String inFieldName) throws RuntimeOperationsException {
      if (inFieldName != null && !inFieldName.equals("")) {
         Pair p = this.findEntry(this.getDescriptor(), inFieldName.toLowerCase());
         if (p != null) {
            return p.value instanceof VoidValue ? null : p.value;
         } else if (this.delegate != null) {
            return this.delegate instanceof DescriptorSupportBase ? ((DescriptorSupportBase)this.delegate).getFieldValueX(inFieldName) : ((DescriptorSupport)this.delegate).getFieldValueX(inFieldName);
         } else {
            return null;
         }
      } else {
         throw new RuntimeOperationsException(new IllegalArgumentException("Fieldname requested is null"), "Exception occured trying to get a field from a descriptor");
      }
   }

   public void setField(String inFieldName, Object fieldValue) throws RuntimeOperationsException {
      if (inFieldName != null && !inFieldName.equals("")) {
         String fieldName = inFieldName.toLowerCase();
         if (!this.validateField(fieldName, fieldValue)) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Field value invalid: " + fieldName + "=" + fieldValue), "Field " + fieldName + "=" + fieldValue + " is invalid. Exception occured trying to set a field from a descriptor");
         } else {
            fieldName = fieldName.toLowerCase();
            ArrayList d = this.getDescriptor();
            Pair p = this.findEntry(d, fieldName);
            if (p != null) {
               if (p.value instanceof VoidValue) {
                  if (fieldValue == null) {
                     return;
                  }

                  --this.voidCount;
                  ++this.fieldCount;
               }

               p.value = fieldValue;
            } else {
               if (fieldValue == null) {
                  this.removeField(fieldName);
                  return;
               }

               Object oldValue = this.getFieldValue(fieldName);
               if (fieldValue.equals(oldValue)) {
                  return;
               }

               if (oldValue instanceof String && fieldValue instanceof String && ((String)fieldValue).equalsIgnoreCase((String)oldValue)) {
                  return;
               }

               this.getDescriptor(true).add(new Pair(fieldName, fieldValue));
               if (this.delegate == null || oldValue == null) {
                  ++this.fieldCount;
               }
            }

         }
      } else {
         throw new RuntimeOperationsException(new IllegalArgumentException("Fieldname to be set is null"), "Exception occured trying to set a field from a descriptor");
      }
   }

   private Pair findEntry(ArrayList d, String fieldName) {
      int index = this.findEntryIndex(d, fieldName);
      return index >= 0 ? (Pair)d.get(index) : null;
   }

   private int findEntryIndex(ArrayList d, String fieldName) {
      int i = 0;

      for(Iterator it = d.iterator(); it.hasNext(); ++i) {
         Pair p = (Pair)it.next();
         if (((String)p.name).equals(fieldName)) {
            return i;
         }
      }

      return -1;
   }

   public String[] getFields() {
      String[] fileNames = this.getFieldNames();

      for(int i = 0; i < fileNames.length; ++i) {
         Object fieldValue = this.getFieldValue(fileNames[i]);
         String retValue = new String(fileNames[i] + "=");
         if (fieldValue != null) {
            if (fieldValue instanceof String) {
               retValue = retValue + fieldValue;
            } else {
               retValue = retValue + "(" + fieldValue + ")";
            }
         }

         fileNames[i] = retValue;
      }

      return fileNames;
   }

   public String[] getFieldNames() {
      Vector fieldNames = new Vector(this.getFieldCount() + this.getVoidCount());
      this.getFieldNamesI(fieldNames);
      String[] oa = (String[])((String[])fieldNames.toArray(new String[fieldNames.size()]));
      return oa;
   }

   private void getFieldNamesI(Vector responseFields) {
      if (this.delegate != null) {
         if (this.delegate instanceof DescriptorSupportBase) {
            ((DescriptorSupportBase)this.delegate).getFieldNamesI(responseFields);
         } else if (this.delegate instanceof DescriptorSupport) {
            ((DescriptorSupport)this.delegate).getFieldNamesI(responseFields);
         }
      }

      List locals = this.getDescriptor();
      Iterator i = locals.iterator();

      while(i.hasNext()) {
         Pair p = (Pair)i.next();
         if (p.value instanceof VoidValue) {
            responseFields.remove(p.name);
         } else if (!responseFields.contains(p.name)) {
            responseFields.add(p.name);
         }
      }

   }

   protected void mergeDescriptors(DescriptorSupport from) {
      Object[] values = from.getFieldPairs();

      for(int i = 0; i < values.length; ++i) {
         Pair value = (Pair)values[i];
         if (value != null) {
            Object oldFieldValue = this.getFieldValue((String)value.name);
            if (oldFieldValue == null) {
               this.setField((String)value.name, value.value);
            } else if (!oldFieldValue.equals(value.value)) {
               this.setField((String)value.name, value.value);
            }
         }
      }

   }

   protected Object[] getFieldPairs() {
      int size = this.getFieldCount() + this.getVoidCount();
      HashMap fieldNames = new HashMap(size + 10);
      this.getFieldPairsI(fieldNames);
      return fieldNames.values().toArray(new Pair[size]);
   }

   private void getFieldPairsI(HashMap responseFields) {
      if (this.delegate != null) {
         if (this.delegate instanceof DescriptorSupportBase) {
            ((DescriptorSupportBase)this.delegate).getFieldPairsI(responseFields);
         } else if (this.delegate instanceof DescriptorSupport) {
            ((DescriptorSupport)this.delegate).getFieldPairsI(responseFields);
         }
      }

      List locals = this.getDescriptor();
      Iterator i = locals.iterator();

      while(i.hasNext()) {
         Pair p = (Pair)i.next();
         if (p.value instanceof VoidValue) {
            responseFields.remove(p.name);
         } else if (!responseFields.containsValue(p.name)) {
            responseFields.put(p.name, p);
         }
      }

   }

   public Object[] getFieldValues(String[] fieldNames) {
      if (fieldNames == null) {
         fieldNames = new String[0];
      }

      Object[] fieldValues = new String[fieldNames.length];

      for(int i = 0; i < fieldNames.length; ++i) {
         if (fieldNames[i] != null && !fieldNames[i].equals("")) {
            fieldValues[i] = (String)this.getFieldValue(fieldNames[i]);
         } else {
            fieldValues[i] = null;
         }
      }

      return fieldValues;
   }

   public void setFields(String[] fieldNames, Object[] fieldValues) throws RuntimeOperationsException {
      if ((fieldNames != null || fieldValues != null) && (fieldNames.length != 0 || fieldValues.length != 0)) {
         if (fieldNames != null && fieldValues != null && fieldNames.length == fieldValues.length) {
            for(int i = 0; i < fieldNames.length; ++i) {
               if (fieldNames[i] == null || fieldNames[i].equals("")) {
                  throw new RuntimeOperationsException(new IllegalArgumentException("FieldNames is null or invalid"), "Exception occured trying to set object fields a descriptor");
               }

               this.setField(fieldNames[i], fieldValues[i]);
            }

         } else {
            throw new RuntimeOperationsException(new IllegalArgumentException("FieldNames and FieldValues are null or invalid"), "Exception occured trying to set object fields a descriptor");
         }
      }
   }

   public Object clone() throws RuntimeOperationsException {
      Object retVal;
      try {
         retVal = super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new RuntimeOperationsException(new RuntimeException(var3));
      }

      copy((DescriptorSupport)retVal, this);
      return retVal;
   }

   public void removeField(String fieldName) {
      if (fieldName != null && !fieldName.equals("")) {
         ArrayList a = this.getDescriptor(true);
         int index = this.findEntryIndex(a, fieldName);
         Pair p = (Pair)((Pair)(index >= 0 ? a.get(index) : null));
         if (p == null || !(p.value instanceof VoidValue)) {
            Object val = null;
            if (this.delegate != null) {
               val = this.delegate.getFieldValue(fieldName);
            }

            if (val != null) {
               if (index >= 0) {
                  p.value = voidValue;
               } else {
                  a.add(new Pair(fieldName, voidValue));
               }

               --this.fieldCount;
               ++this.voidCount;
            } else if (index >= 0) {
               a.remove(index);
               --this.fieldCount;
            }

         }
      }
   }

   public boolean isValid() throws RuntimeOperationsException {
      if (this.delegate == null) {
         String thisName = (String)((String)this.getFieldValue("name"));
         String thisDescType = (String)((String)this.getFieldValue("descriptorType"));
         if (thisName == null || thisDescType == null || thisName.equals("") || thisDescType.equals("")) {
            return false;
         }
      }

      Iterator e = this.getDescriptor().iterator();

      Pair pair;
      do {
         if (!e.hasNext()) {
            return true;
         }

         pair = (Pair)e.next();
      } while(pair.value == null || this.validateField(pair.name.toString(), pair.value.toString()));

      return false;
   }

   private boolean validateField(String fldName, Object fldValue) {
      if (fldName != null && !fldName.equals("")) {
         String SfldValue = "";
         boolean isAString = false;
         if (fldValue != null && fldValue instanceof String) {
            SfldValue = (String)fldValue;
            isAString = true;
         }

         if (!fldName.equalsIgnoreCase("Name") && !fldName.equalsIgnoreCase("DescriptorType") && !fldName.equalsIgnoreCase("SetMethod") && !fldName.equalsIgnoreCase("GetMethod") && !fldName.equalsIgnoreCase("Role") && !fldName.equalsIgnoreCase("Class")) {
            int v;
            if (fldName.equalsIgnoreCase("visibility")) {
               if (fldValue != null && isAString) {
                  v = this.toNumeric(SfldValue);
               } else {
                  if (fldValue == null || !(fldValue instanceof Integer)) {
                     return false;
                  }

                  v = (Integer)fldValue;
               }

               return v >= 1 && v <= 4;
            } else if (fldName.equalsIgnoreCase("severity")) {
               if (fldValue != null && isAString) {
                  v = this.toNumeric(SfldValue);
               } else {
                  if (fldValue == null || !(fldValue instanceof Integer)) {
                     return false;
                  }

                  v = (Integer)fldValue;
               }

               if (v >= 0 && v <= 5) {
                  return true;
               } else {
                  return false;
               }
            } else if (fldName.equalsIgnoreCase("PersistPolicy")) {
               return fldValue != null && isAString && (SfldValue.equalsIgnoreCase("OnUpdate") || SfldValue.equalsIgnoreCase("OnTimer") || SfldValue.equalsIgnoreCase("NoMoreOftenThan") || SfldValue.equalsIgnoreCase("Always") || SfldValue.equalsIgnoreCase("Never"));
            } else if (!fldName.equalsIgnoreCase("PersistPeriod") && !fldName.equalsIgnoreCase("CurrencyTimeLimit") && !fldName.equalsIgnoreCase("LastUpdatedTimeStamp") && !fldName.equalsIgnoreCase("LastReturnedTimeStamp")) {
               if (!fldName.equalsIgnoreCase("ReadOnly") && !fldName.equalsIgnoreCase("log") && !fldName.equalsIgnoreCase("Iterable")) {
                  return true;
               } else {
                  return fldValue instanceof Boolean || isAString && (SfldValue.equalsIgnoreCase("T") || SfldValue.equalsIgnoreCase("true") || SfldValue.equalsIgnoreCase("F") || SfldValue.equalsIgnoreCase("false"));
               }
            } else {
               if (fldValue != null && isAString) {
                  v = this.toNumeric(SfldValue);
               } else {
                  if (fldValue == null || !(fldValue instanceof Integer)) {
                     return false;
                  }

                  v = (Integer)fldValue;
               }

               return v >= -1;
            }
         } else {
            return fldValue != null && isAString;
         }
      } else {
         return false;
      }
   }

   public String toXMLString() {
      String respStr = new String("<Descriptor>");
      int num = this.getDescriptor().size();
      if (num == 0) {
         return null;
      } else {
         Iterator e = this.getDescriptor().iterator();

         while(e.hasNext()) {
            Pair pair = (Pair)e.next();
            if (pair.value == null) {
               respStr = respStr + "<field name=\"" + pair.name.toString() + "\" value=\"null\"></field>";
            } else {
               respStr = respStr + "<field name=\"" + pair.name.toString() + "\" value=\"" + pair.value.toString() + "\"></field>";
            }
         }

         respStr = respStr + "</Descriptor>";
         return respStr;
      }
   }

   public String toString() {
      String respStr = "";
      String[] fields = this.getFields();
      if (fields != null && fields.length != 0) {
         for(int i = 0; i < fields.length; ++i) {
            if (i == fields.length - 1) {
               respStr = respStr.concat(fields[i]);
            } else {
               respStr = respStr.concat(fields[i] + ", ");
            }
         }

         return respStr;
      } else {
         return null;
      }
   }

   private int toNumeric(String inStr) {
      int result = true;

      try {
         int result = Integer.parseInt(inStr);
         return result;
      } catch (Exception var4) {
         return -1;
      }
   }

   void setDelegate(DescriptorSupport d) {
      this.delegate = d;
   }

   DescriptorSupport getDelegate() {
      return (DescriptorSupport)this.delegate;
   }

   void removeDefaults() {
      DescriptorSupport delegate = this.getDelegate();
      if (delegate != null) {
         if (delegate instanceof DescriptorSupportBase) {
            this.setDelegate((DescriptorSupport)null);
         } else {
            delegate.removeDefaults();
         }
      }

   }

   void restoreDefaults(DescriptorSupportBase def) throws MBeanException {
      String[] baseList = def.getFieldNames();
      DescriptorSupport lastInChain = this.restoreDefaultX(this, baseList);
      if (lastInChain != null) {
         lastInChain.setDelegate(def);
      }

   }

   private DescriptorSupport restoreDefaultX(DescriptorSupport d, String[] baseList) {
      DescriptorSupport delegate = d.getDelegate();
      if (delegate == null) {
         this.restoreCountsForNewDefault(d, baseList);
         return d;
      } else if (delegate instanceof DescriptorSupportBase) {
         return null;
      } else {
         DescriptorSupport lastInChain = this.restoreDefaultX(delegate, baseList);
         if (lastInChain != null) {
            this.restoreCountsForNewDefault(d, baseList);
         }

         return lastInChain;
      }
   }

   private void restoreCountsForNewDefault(DescriptorSupport d, String[] baseList) {
      for(int i = 0; i < baseList.length; ++i) {
         String fieldName = baseList[i];
         int status = d.testFieldStatus(fieldName);
         if (status == 0) {
            ++this.fieldCount;
         }
      }

   }

   private int testFieldStatus(String inFieldName) {
      Pair p = this.findEntry(this.getDescriptor(), inFieldName.toLowerCase());
      if (p != null && p.value != null) {
         return p.value instanceof VoidValue ? -1 : 1;
      } else {
         return this.delegate != null ? ((DescriptorSupport)this.delegate).testFieldStatus(inFieldName) : 0;
      }
   }

   void prepareNewPrimaryDelegate(DescriptorSupport newDescr) {
      newDescr.setDelegate((DescriptorSupport)null);
      String[] newFields = (String[])newDescr.getFields();
      newDescr.fieldCount = this.fieldCount;

      for(int i = 0; i < newFields.length; ++i) {
         String fieldName = newFields[i];
         int status = this.testFieldStatus(fieldName);
         if (status == 0) {
            ++this.fieldCount;
         }
      }

      DescriptorSupport delegate;
      for(delegate = this; delegate != null && delegate instanceof DescriptorSupport && delegate.getFieldCountLocal() == 0; delegate = (DescriptorSupport)delegate.delegate) {
      }

      newDescr.setDelegate(delegate);
   }

   boolean isBaseDelegate() {
      DescriptorSupport delegate = this.getDelegate();
      if (delegate == null) {
         return true;
      } else {
         return delegate instanceof DescriptorSupportBase;
      }
   }

   private static class VoidValue implements Serializable {
      private static final long serialVersionUID = 8080916936062268943L;

      private VoidValue() {
      }

      // $FF: synthetic method
      VoidValue(Object x0) {
         this();
      }
   }

   protected class Pair implements Serializable {
      protected static final long serialVersionUID = -2343447898010660137L;
      Object name;
      Object value;

      protected Pair(Object name, Object value) {
         this.name = name;
         this.value = value;
      }
   }
}
