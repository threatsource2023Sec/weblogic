package weblogic.application.internal.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import weblogic.application.Type;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.LibraryConstants;
import weblogic.application.utils.LibraryUtils;
import weblogic.management.configuration.LibraryMBean;

public class BasicLibraryData implements Serializable {
   private static final String NOT_SET = "<not-set>";
   private final String name;
   private final DeweyDecimal specVersion;
   private final String implVersion;
   private Type type;

   public BasicLibraryData(Attributes attrs) throws IllegalSpecVersionTypeException {
      this(getName(attrs), getSpecVersion(attrs), getImplVersion(attrs));
   }

   public BasicLibraryData(LibraryMBean mbean) throws IllegalSpecVersionTypeException {
      this(getName(mbean), getSpecVersion(mbean), getImplVersion(mbean));
   }

   public BasicLibraryData(String name, String specVersion, String implVersion) throws IllegalSpecVersionTypeException {
      this(name, parseSpec(specVersion), implVersion);
   }

   public BasicLibraryData(String name, String specVersion, String implVersion, Type type) throws IllegalSpecVersionTypeException {
      this(name, parseSpec(specVersion), implVersion, type);
   }

   protected BasicLibraryData(String name, DeweyDecimal specVersion, String implVersion) {
      this(name, (DeweyDecimal)specVersion, implVersion, (Type)null);
   }

   public BasicLibraryData(String name, DeweyDecimal specVersion, String implVersion, Type type) {
      this.name = name;
      this.specVersion = specVersion;
      this.implVersion = implVersion;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public DeweyDecimal getSpecificationVersion() {
      return this.specVersion;
   }

   public String getImplementationVersion() {
      return this.implVersion;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public Type getType() {
      return this.type;
   }

   public String toString() {
      return LibraryUtils.toString(this);
   }

   public BasicLibraryData importData(BasicLibraryData in) {
      String name = this.getName() != null ? this.getName() : in.getName();
      DeweyDecimal spec = this.getSpecificationVersion() != null ? this.getSpecificationVersion() : in.getSpecificationVersion();
      String impl = this.getImplementationVersion() != null ? this.getImplementationVersion() : in.getImplementationVersion();
      Type type = this.getType() != null ? this.getType() : in.getType();
      return new BasicLibraryData(name, spec, impl, type);
   }

   public Collection verifyDataConsistency(BasicLibraryData in, String... attrNamesToVerify) {
      Collection c = new ArrayList(3);
      this.verifyDataConsistency(in, c, attrNamesToVerify);
      return c;
   }

   private void verifyDataConsistency(BasicLibraryData in, Collection errorAggregate, String... attrNamesToVerify) {
      Set verificationSet = new HashSet(Arrays.asList(attrNamesToVerify != null && attrNamesToVerify.length > 0 ? attrNamesToVerify : new String[]{LibraryConstants.LIBRARY_NAME, LibraryConstants.SPEC_VERSION_NAME, LibraryConstants.IMPL_VERSION_NAME}));
      if (verificationSet.contains(LibraryConstants.LIBRARY_NAME)) {
         this.checkForMismatch(LibraryConstants.LIBRARY_NAME, this.getName(), in.getName(), errorAggregate);
      }

      if (verificationSet.contains(LibraryConstants.SPEC_VERSION_NAME)) {
         this.checkForMismatch(LibraryConstants.SPEC_VERSION_NAME, LibraryUtils.nullOrString(this.getSpecificationVersion()), LibraryUtils.nullOrString(in.getSpecificationVersion()), errorAggregate);
      }

      if (verificationSet.contains(LibraryConstants.IMPL_VERSION_NAME)) {
         this.checkForMismatch(LibraryConstants.IMPL_VERSION_NAME, this.getImplementationVersion(), in.getImplementationVersion(), errorAggregate);
      }

   }

   private void checkForMismatch(String attrName, String myValue, String inValue, Collection errorAggregate) {
      String v1 = myValue == null ? "<not-set>" : myValue;
      String v2 = inValue == null ? "<not-set>" : inValue;
      if (!v1.equals(v2)) {
         StringBuffer sb = new StringBuffer();
         sb.append(attrName).append(": ").append(v1).append(" vs. ").append(v2);
         errorAggregate.add(sb.toString());
      }

   }

   private static String getName(Attributes attrs) {
      if (attrs == null) {
         return null;
      } else {
         String rtn = attrs.getValue(LibraryConstants.LIBRARY_NAME);
         return rtn == null ? null : rtn.trim();
      }
   }

   private static String getName(LibraryMBean mbean) {
      return LibraryUtils.getName(mbean);
   }

   private static DeweyDecimal getSpecVersion(Attributes attrs) throws IllegalSpecVersionTypeException {
      return attrs == null ? null : parseSpec(attrs.getValue(LibraryConstants.SPEC_VERSION_NAME));
   }

   private static DeweyDecimal getSpecVersion(LibraryMBean mbean) throws IllegalSpecVersionTypeException {
      return parseSpec(LibraryUtils.getSpecVersion(mbean));
   }

   private static String getImplVersion(Attributes attrs) {
      if (attrs == null) {
         return null;
      } else {
         String rtn = attrs.getValue(LibraryConstants.IMPL_VERSION_NAME);
         return rtn == null ? null : rtn.trim();
      }
   }

   private static String getImplVersion(LibraryMBean mbean) {
      return LibraryUtils.getImplVersion(mbean);
   }

   private static DeweyDecimal parseSpec(String in) throws IllegalSpecVersionTypeException {
      if (in == null) {
         return null;
      } else {
         String s = in.trim();
         DeweyDecimal rtn = null;

         try {
            rtn = new DeweyDecimal(s);
            return rtn;
         } catch (NumberFormatException var4) {
            throw new IllegalSpecVersionTypeException(s);
         }
      }
   }
}
