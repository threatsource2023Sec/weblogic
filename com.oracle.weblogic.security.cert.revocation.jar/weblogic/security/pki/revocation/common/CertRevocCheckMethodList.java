package weblogic.security.pki.revocation.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CertRevocCheckMethodList {
   private final List list;

   public CertRevocCheckMethodList(String formattedList) throws IllegalArgumentException {
      this(parseSelectableMethodList(formattedList));
   }

   public CertRevocCheckMethodList(SelectableMethodList inputList) {
      checkNonNull(inputList, "SelectableMethodList");
      ArrayList tempList = new ArrayList();
      switch (inputList) {
         case OCSP:
            tempList.add(CertRevocCheckMethodList.SelectableMethod.OCSP);
            break;
         case CRL:
            tempList.add(CertRevocCheckMethodList.SelectableMethod.CRL);
            break;
         case OCSP_THEN_CRL:
            tempList.add(CertRevocCheckMethodList.SelectableMethod.OCSP);
            tempList.add(CertRevocCheckMethodList.SelectableMethod.CRL);
            break;
         case CRL_THEN_OCSP:
            tempList.add(CertRevocCheckMethodList.SelectableMethod.CRL);
            tempList.add(CertRevocCheckMethodList.SelectableMethod.OCSP);
            break;
         default:
            throw new IllegalStateException("Unexpected value: " + inputList);
      }

      this.list = Collections.unmodifiableList(tempList);
   }

   public int size() {
      return this.list.size();
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public boolean contains(SelectableMethod method) {
      checkNonNull(method, "SelectableMethod");
      return this.list.contains(method);
   }

   public Iterator iterator() {
      return this.list.iterator();
   }

   public SelectableMethod get(int index) {
      return (SelectableMethod)this.list.get(index);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CertRevocCheckMethodList)) {
         return false;
      } else {
         CertRevocCheckMethodList other = (CertRevocCheckMethodList)o;
         return this.list.equals(other.list);
      }
   }

   public int hashCode() {
      return this.list.hashCode();
   }

   private static boolean add(List list, SelectableMethod method) {
      checkNonNull(list, "List<SelectableMethod>");
      checkNonNull(method, "SelectableMethod");
      return !list.contains(method) ? list.add(method) : false;
   }

   private static void checkNonNull(Object object, String objectName) {
      if (null == objectName) {
         objectName = "";
      }

      if (null == object) {
         throw new IllegalArgumentException("Non-null " + objectName + " expected.");
      }
   }

   private static SelectableMethodList parseSelectableMethodList(String formattedList) {
      checkNonNull(formattedList, "String");
      formattedList = formattedList.toUpperCase(Locale.US);

      try {
         SelectableMethodList listEnum = CertRevocCheckMethodList.SelectableMethodList.valueOf(formattedList);
         return listEnum;
      } catch (IllegalArgumentException var3) {
         throw new IllegalArgumentException("Unrecognized CertRevocCheckMethodList: '" + formattedList + "'.", var3);
      }
   }

   public static enum SelectableMethodList {
      OCSP,
      CRL,
      OCSP_THEN_CRL,
      CRL_THEN_OCSP;
   }

   public static enum SelectableMethod {
      OCSP,
      CRL;
   }
}
