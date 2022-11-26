package weblogic.management.commo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.XMLParseException;

public class DescriptorSupportBase extends DescriptorSupport implements Descriptor {
   HashMap descriptor;
   private static final int DEFAULT_SIZE = 20;
   public static String currClass = "DescriptorSupportBase";
   static final long serialVersionUID = -2599877740461261061L;

   protected DescriptorSupportBase() {
      this.descriptor = new HashMap(20);
   }

   public DescriptorSupportBase(int initNumFields) throws MBeanException, RuntimeOperationsException {
      if (initNumFields <= 0) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor field limit is invalid"), "Exception occured trying to construct a descriptor");
      } else {
         this.descriptor = new HashMap(initNumFields);
      }
   }

   protected DescriptorSupportBase(DescriptorSupportBase inDescr) {
      if (inDescr != null && inDescr.descriptor != null) {
         this.descriptor = new HashMap(inDescr.descriptor);
      } else {
         this.descriptor = new HashMap(20);
      }

   }

   protected DescriptorSupportBase(String inStr) throws MBeanException, RuntimeOperationsException, XMLParseException {
      if (inStr == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor is null or invalid"), "Exception occured trying to construct a descriptor");
      } else {
         this.descriptor = new HashMap(20);
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

   protected DescriptorSupportBase(String[] fieldNames, Object[] fieldValues) throws RuntimeOperationsException {
      if ((fieldNames != null || fieldValues != null) && (fieldNames.length != 0 || fieldValues.length != 0)) {
         if (fieldNames != null && fieldValues != null && fieldNames.length == fieldValues.length) {
            this.descriptor = new HashMap(fieldNames.length);

            for(int i = 0; i < fieldNames.length; ++i) {
               this.setField(fieldNames[i], fieldValues[i]);
            }

         } else {
            throw new RuntimeOperationsException(new IllegalArgumentException("FieldNames or FieldValues are null or invalid"), "Exception occured trying to construct a descriptor");
         }
      } else {
         this.descriptor = new HashMap(20);
      }
   }

   protected DescriptorSupportBase(String[] fields) {
      if (fields != null && fields.length != 0) {
         this.descriptor = new HashMap(fields.length);

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
         this.descriptor = new HashMap(20);
      }
   }

   public Object getFieldValue(String inFieldName) throws RuntimeOperationsException {
      Object retValue = this.getFieldValueX(inFieldName);
      return retValue == null && inFieldName.equalsIgnoreCase("displayname") ? this.getFieldValue("Name") : retValue;
   }

   protected Object getFieldValueX(String inFieldName) throws RuntimeOperationsException {
      if (inFieldName != null && !inFieldName.equals("")) {
         Object retValue = this.descriptor.get(inFieldName.toLowerCase());
         return retValue;
      } else {
         throw new RuntimeOperationsException(new IllegalArgumentException("Fieldname requested is null"), "Exception occured trying to get a field from a descriptor");
      }
   }

   public void setField(String inFieldName, Object fieldValue) throws RuntimeOperationsException {
      if (inFieldName != null && !inFieldName.equals("")) {
         String fieldName = inFieldName.toLowerCase();
         if (this.validateField(fieldName, fieldValue)) {
            this.descriptor.put(fieldName, fieldValue);
         } else {
            throw new RuntimeOperationsException(new IllegalArgumentException("Field value invalid: " + fieldName + "=" + fieldValue), "Field " + fieldName + "=" + fieldValue + " is invalid. Exception occured trying to set a field from a descriptor");
         }
      } else {
         throw new RuntimeOperationsException(new IllegalArgumentException("Fieldname to be set is null"), "Exception occured trying to set a field from a descriptor");
      }
   }

   public String[] getFields() {
      if (this.descriptor == null) {
         return new String[0];
      } else {
         int numberOfEntries = this.descriptor.size();
         if (numberOfEntries == 0) {
            return new String[0];
         } else {
            String[] responseFields = new String[numberOfEntries];
            responseFields = this.getFieldsI(responseFields, 0);
            return responseFields;
         }
      }
   }

   public String[] getFieldNames() {
      if (this.descriptor == null) {
         return new String[0];
      } else {
         int numberOfEntries = this.descriptor.size();
         Vector responseFields = new Vector(numberOfEntries);
         this.getFieldNamesI(responseFields);
         String[] sa = new String[responseFields.size()];
         return (String[])((String[])responseFields.toArray(sa));
      }
   }

   public Object[] getFieldValues(String[] fieldNames) {
      if (this.descriptor != null && (fieldNames == null || fieldNames.length != 0)) {
         int numberOfEntries = this.descriptor.size();
         Set returnedSet = this.descriptor.entrySet();
         if (returnedSet != null && numberOfEntries != 0) {
            Object[] responseFields;
            if (fieldNames != null) {
               responseFields = new Object[fieldNames.length];
            } else {
               responseFields = new Object[numberOfEntries];
            }

            int i = 0;
            if (fieldNames == null) {
               for(Iterator iter = returnedSet.iterator(); iter.hasNext(); ++i) {
                  Map.Entry currElement = (Map.Entry)iter.next();
                  if (currElement != null && currElement.getKey() != null) {
                     responseFields[i] = new String((String)currElement.getValue());
                  } else {
                     responseFields[i] = null;
                  }
               }
            } else {
               for(i = 0; i < fieldNames.length; ++i) {
                  if (fieldNames[i] != null && !fieldNames[i].equals("")) {
                     responseFields[i] = this.getFieldValue(fieldNames[i]);
                  } else {
                     responseFields[i] = null;
                  }
               }
            }

            return responseFields;
         } else {
            return new Object[0];
         }
      } else {
         return new Object[0];
      }
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
      return new DescriptorSupportBase(this);
   }

   public void removeField(String fieldName) {
      if (fieldName != null && !fieldName.equals("")) {
         this.descriptor.remove(fieldName);
      }
   }

   public boolean isValid() throws RuntimeOperationsException {
      Set returnedSet = this.descriptor.entrySet();
      if (returnedSet == null) {
         return false;
      } else {
         String thisName = (String)((String)this.getFieldValue("name"));
         String thisDescType = (String)((String)this.getFieldValue("descriptorType"));
         if (thisName != null && thisDescType != null && !thisName.equals("") && !thisDescType.equals("")) {
            Iterator iter = returnedSet.iterator();

            Map.Entry currElement;
            do {
               if (!iter.hasNext()) {
                  return true;
               }

               currElement = (Map.Entry)iter.next();
            } while(currElement == null || currElement.getValue() == null || this.validateField(currElement.getKey().toString(), currElement.getValue().toString()));

            return false;
         } else {
            return false;
         }
      }
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
      Set returnedSet = this.descriptor.entrySet();
      if (returnedSet == null) {
         return null;
      } else {
         int i = 0;

         for(Iterator iter = returnedSet.iterator(); iter.hasNext(); ++i) {
            Map.Entry currElement = (Map.Entry)iter.next();
            if (currElement != null) {
               if (currElement.getValue() == null) {
                  respStr = respStr + "<field name=\"" + currElement.getKey().toString() + "\" value=\"null\"></field>";
               } else {
                  respStr = respStr + "<field name=\"" + currElement.getKey().toString() + "\" value=\"" + currElement.getValue().toString() + "\"></field>";
               }
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

   protected int getFieldCount() {
      return this.descriptor.size();
   }

   protected int getVoidCount() {
      return 0;
   }

   String[] getFieldsI(String[] responseFields, int startIndex) {
      if (this.descriptor == null) {
         return responseFields;
      } else {
         int numberOfEntries = this.descriptor.size();
         if (numberOfEntries == 0) {
            return responseFields;
         } else {
            Set returnedSet = this.descriptor.entrySet();
            if (returnedSet == null) {
               return responseFields;
            } else {
               int i = startIndex;
               Object currValue = null;
               Map.Entry currElement = null;

               for(Iterator iter = returnedSet.iterator(); iter.hasNext(); ++i) {
                  currElement = (Map.Entry)iter.next();
                  if (currElement != null) {
                     currValue = currElement.getValue();
                     if (currValue == null) {
                        responseFields[i] = new String(currElement.getKey().toString() + "=");
                     } else if (currValue instanceof String) {
                        responseFields[i] = new String(currElement.getKey().toString() + "=" + currValue.toString());
                     } else {
                        responseFields[i] = new String(currElement.getKey().toString() + "=(" + currValue.toString() + ")");
                     }
                  }
               }

               return responseFields;
            }
         }
      }
   }

   void getFieldNamesI(Vector responseFields) {
      Set returnedSet = this.descriptor.entrySet();
      if (returnedSet != null && returnedSet.size() != 0) {
         Iterator iter = returnedSet.iterator();

         while(iter.hasNext()) {
            Map.Entry currElement = (Map.Entry)iter.next();
            if (currElement != null && currElement.getKey() != null) {
               responseFields.add(new String(currElement.getKey().toString()));
            }
         }

      }
   }

   void getFieldPairsI(HashMap responseFields) {
      Set returnedSet = this.descriptor.entrySet();
      if (returnedSet != null && returnedSet.size() != 0) {
         Iterator iter = returnedSet.iterator();

         while(iter.hasNext()) {
            Map.Entry currElement = (Map.Entry)iter.next();
            if (currElement != null && currElement.getKey() != null) {
               responseFields.put(currElement.getKey(), new DescriptorSupport.Pair(currElement.getKey().toString(), currElement.getValue()));
            }
         }

      }
   }

   protected int getFieldCountLocal() {
      return this.getFieldCount();
   }
}
