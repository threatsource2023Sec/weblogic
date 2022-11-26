package com.bea.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;

public class QNameSetBuilder implements QNameSetSpecification, Serializable {
   private static final long serialVersionUID = 1L;
   private boolean _inverted;
   private Set _includedURIs;
   private Set _excludedQNames;
   private Set _includedQNames;
   private static final String[] EMPTY_STRINGARRAY = new String[0];

   public QNameSetBuilder() {
      this._inverted = false;
      this._includedURIs = new HashSet();
      this._excludedQNames = new HashSet();
      this._includedQNames = new HashSet();
   }

   public QNameSetBuilder(QNameSetSpecification set) {
      Set includedURIs = set.includedURIs();
      if (includedURIs != null) {
         this._inverted = false;
         this._includedURIs = new HashSet(includedURIs);
         this._excludedQNames = new HashSet(set.excludedQNamesInIncludedURIs());
         this._includedQNames = new HashSet(set.includedQNamesInExcludedURIs());
      } else {
         this._inverted = true;
         this._includedURIs = new HashSet(set.excludedURIs());
         this._excludedQNames = new HashSet(set.includedQNamesInExcludedURIs());
         this._includedQNames = new HashSet(set.excludedQNamesInIncludedURIs());
      }

   }

   public QNameSetBuilder(Set excludedURIs, Set includedURIs, Set excludedQNamesInIncludedURIs, Set includedQNamesInExcludedURIs) {
      if (includedURIs != null && excludedURIs == null) {
         this._inverted = false;
         this._includedURIs = new HashSet(includedURIs);
         this._excludedQNames = new HashSet(excludedQNamesInIncludedURIs);
         this._includedQNames = new HashSet(includedQNamesInExcludedURIs);
      } else {
         if (excludedURIs == null || includedURIs != null) {
            throw new IllegalArgumentException("Exactly one of excludedURIs and includedURIs must be null");
         }

         this._inverted = true;
         this._includedURIs = new HashSet(excludedURIs);
         this._excludedQNames = new HashSet(includedQNamesInExcludedURIs);
         this._includedQNames = new HashSet(excludedQNamesInIncludedURIs);
      }

   }

   public QNameSetBuilder(String str, String targetURI) {
      this();
      if (str == null) {
         str = "##any";
      }

      String[] uri = splitList(str);

      for(int i = 0; i < uri.length; ++i) {
         String adduri = uri[i];
         if (adduri.startsWith("##")) {
            if (adduri.equals("##other")) {
               if (targetURI == null) {
                  throw new IllegalArgumentException();
               }

               QNameSetBuilder temp = new QNameSetBuilder();
               temp.addNamespace(targetURI);
               temp.addNamespace("");
               temp.invert();
               this.addAll(temp);
               continue;
            }

            if (adduri.equals("##any")) {
               this.clear();
               this.invert();
               continue;
            }

            if (uri[i].equals("##targetNamespace")) {
               if (targetURI == null) {
                  throw new IllegalArgumentException();
               }

               adduri = targetURI;
            } else if (uri[i].equals("##local")) {
               adduri = "";
            }
         }

         this.addNamespace(adduri);
      }

   }

   private static String nsFromName(QName QName) {
      String ns = QName.getNamespaceURI();
      return ns == null ? "" : ns;
   }

   private static boolean isSpace(char ch) {
      switch (ch) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            return true;
         default:
            return false;
      }
   }

   private static String[] splitList(String s) {
      if (s.length() == 0) {
         return EMPTY_STRINGARRAY;
      } else {
         List result = new ArrayList();
         int i = 0;
         int start = false;

         while(true) {
            while(i >= s.length() || !isSpace(s.charAt(i))) {
               if (i >= s.length()) {
                  return (String[])((String[])result.toArray(EMPTY_STRINGARRAY));
               }

               int start;
               for(start = i; i < s.length() && !isSpace(s.charAt(i)); ++i) {
               }

               result.add(s.substring(start, i));
            }

            ++i;
         }
      }
   }

   private static void removeAllMatchingNs(String uri, Set qnameset) {
      Iterator i = qnameset.iterator();

      while(i.hasNext()) {
         if (uri.equals(nsFromName((QName)i.next()))) {
            i.remove();
         }
      }

   }

   private static void removeAllMatchingFirstOnly(Set setFirst, Set setSecond, Set qnameset) {
      Iterator i = qnameset.iterator();

      while(i.hasNext()) {
         String ns = nsFromName((QName)i.next());
         if (setFirst.contains(ns) && !setSecond.contains(ns)) {
            i.remove();
         }
      }

   }

   private static void removeAllMatchingBoth(Set setFirst, Set setSecond, Set qnameset) {
      Iterator i = qnameset.iterator();

      while(i.hasNext()) {
         String ns = nsFromName((QName)i.next());
         if (setFirst.contains(ns) && setSecond.contains(ns)) {
            i.remove();
         }
      }

   }

   private static void removeAllMatchingNeither(Set setFirst, Set setSecond, Set qnameset) {
      Iterator i = qnameset.iterator();

      while(i.hasNext()) {
         String ns = nsFromName((QName)i.next());
         if (!setFirst.contains(ns) && !setSecond.contains(ns)) {
            i.remove();
         }
      }

   }

   public boolean contains(QName name) {
      boolean in = this._includedURIs.contains(nsFromName(name)) ? !this._excludedQNames.contains(name) : this._includedQNames.contains(name);
      return this._inverted ^ in;
   }

   public boolean isAll() {
      return this._inverted && this._includedURIs.size() == 0 && this._includedQNames.size() == 0;
   }

   public boolean isEmpty() {
      return !this._inverted && this._includedURIs.size() == 0 && this._includedQNames.size() == 0;
   }

   public QNameSet intersect(QNameSetSpecification set) {
      QNameSetBuilder result = new QNameSetBuilder(this);
      result.restrict(set);
      return result.toQNameSet();
   }

   public QNameSet union(QNameSetSpecification set) {
      QNameSetBuilder result = new QNameSetBuilder(this);
      result.addAll(set);
      return result.toQNameSet();
   }

   public QNameSet inverse() {
      return QNameSet.forSets(this.includedURIs(), this.excludedURIs(), this.includedQNamesInExcludedURIs(), this.excludedQNamesInIncludedURIs());
   }

   public boolean containsAll(QNameSetSpecification set) {
      return !this._inverted && set.excludedURIs() != null ? false : this.inverse().isDisjoint(set);
   }

   public boolean isDisjoint(QNameSetSpecification set) {
      if (this._inverted && set.excludedURIs() != null) {
         return false;
      } else {
         return this._inverted ? this.isDisjointImpl(set, this) : this.isDisjointImpl(this, set);
      }
   }

   private boolean isDisjointImpl(QNameSetSpecification set1, QNameSetSpecification set2) {
      Set includeURIs = set1.includedURIs();
      Set otherIncludeURIs = set2.includedURIs();
      Iterator i;
      if (otherIncludeURIs != null) {
         i = includeURIs.iterator();

         while(i.hasNext()) {
            if (otherIncludeURIs.contains(i.next())) {
               return false;
            }
         }
      } else {
         Set otherExcludeURIs = set2.excludedURIs();
         Iterator i = includeURIs.iterator();

         while(i.hasNext()) {
            if (!otherExcludeURIs.contains(i.next())) {
               return false;
            }
         }
      }

      i = set1.includedQNamesInExcludedURIs().iterator();

      while(i.hasNext()) {
         if (set2.contains((QName)i.next())) {
            return false;
         }
      }

      if (includeURIs.size() > 0) {
         i = set2.includedQNamesInExcludedURIs().iterator();

         while(i.hasNext()) {
            if (set1.contains((QName)i.next())) {
               return false;
            }
         }
      }

      return true;
   }

   public void clear() {
      this._inverted = false;
      this._includedURIs.clear();
      this._excludedQNames.clear();
      this._includedQNames.clear();
   }

   public void invert() {
      this._inverted = !this._inverted;
   }

   public void add(QName qname) {
      if (!this._inverted) {
         this.addImpl(qname);
      } else {
         this.removeImpl(qname);
      }

   }

   public void addNamespace(String uri) {
      if (!this._inverted) {
         this.addNamespaceImpl(uri);
      } else {
         this.removeNamespaceImpl(uri);
      }

   }

   public void addAll(QNameSetSpecification set) {
      if (this._inverted) {
         this.removeAllImpl(set.includedURIs(), set.excludedURIs(), set.includedQNamesInExcludedURIs(), set.excludedQNamesInIncludedURIs());
      } else {
         this.addAllImpl(set.includedURIs(), set.excludedURIs(), set.includedQNamesInExcludedURIs(), set.excludedQNamesInIncludedURIs());
      }

   }

   public void remove(QName qname) {
      if (this._inverted) {
         this.addImpl(qname);
      } else {
         this.removeImpl(qname);
      }

   }

   public void removeNamespace(String uri) {
      if (this._inverted) {
         this.addNamespaceImpl(uri);
      } else {
         this.removeNamespaceImpl(uri);
      }

   }

   public void removeAll(QNameSetSpecification set) {
      if (this._inverted) {
         this.addAllImpl(set.includedURIs(), set.excludedURIs(), set.includedQNamesInExcludedURIs(), set.excludedQNamesInIncludedURIs());
      } else {
         this.removeAllImpl(set.includedURIs(), set.excludedURIs(), set.includedQNamesInExcludedURIs(), set.excludedQNamesInIncludedURIs());
      }

   }

   public void restrict(QNameSetSpecification set) {
      if (this._inverted) {
         this.addAllImpl(set.excludedURIs(), set.includedURIs(), set.excludedQNamesInIncludedURIs(), set.includedQNamesInExcludedURIs());
      } else {
         this.removeAllImpl(set.excludedURIs(), set.includedURIs(), set.excludedQNamesInIncludedURIs(), set.includedQNamesInExcludedURIs());
      }

   }

   private void addImpl(QName qname) {
      if (this._includedURIs.contains(nsFromName(qname))) {
         this._excludedQNames.remove(qname);
      } else {
         this._includedQNames.add(qname);
      }

   }

   private void addNamespaceImpl(String uri) {
      if (this._includedURIs.contains(uri)) {
         removeAllMatchingNs(uri, this._excludedQNames);
      } else {
         removeAllMatchingNs(uri, this._includedQNames);
         this._includedURIs.add(uri);
      }

   }

   private void addAllImpl(Set includedURIs, Set excludedURIs, Set includedQNames, Set excludedQNames) {
      boolean exclude = excludedURIs != null;
      Set specialURIs = exclude ? excludedURIs : includedURIs;
      Iterator i = this._excludedQNames.iterator();

      QName name;
      String uri;
      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (exclude ^ specialURIs.contains(uri) && !excludedQNames.contains(name)) {
            i.remove();
         }
      }

      i = excludedQNames.iterator();

      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (!this._includedURIs.contains(uri) && !this._includedQNames.contains(name)) {
            this._excludedQNames.add(name);
         }
      }

      i = includedQNames.iterator();

      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (!this._includedURIs.contains(uri)) {
            this._includedQNames.add(name);
         } else {
            this._excludedQNames.remove(name);
         }
      }

      if (!exclude) {
         removeAllMatchingFirstOnly(includedURIs, this._includedURIs, this._includedQNames);
         this._includedURIs.addAll(includedURIs);
      } else {
         removeAllMatchingNeither(excludedURIs, this._includedURIs, this._includedQNames);
         i = this._includedURIs.iterator();

         String uri;
         while(i.hasNext()) {
            uri = (String)i.next();
            if (!excludedURIs.contains(uri)) {
               i.remove();
            }
         }

         i = excludedURIs.iterator();

         while(i.hasNext()) {
            uri = (String)i.next();
            if (!this._includedURIs.contains(uri)) {
               this._includedURIs.add(uri);
            } else {
               this._includedURIs.remove(uri);
            }
         }

         Set temp = this._excludedQNames;
         this._excludedQNames = this._includedQNames;
         this._includedQNames = temp;
         this._inverted = !this._inverted;
      }

   }

   private void removeImpl(QName qname) {
      if (this._includedURIs.contains(nsFromName(qname))) {
         this._excludedQNames.add(qname);
      } else {
         this._includedQNames.remove(qname);
      }

   }

   private void removeNamespaceImpl(String uri) {
      if (this._includedURIs.contains(uri)) {
         removeAllMatchingNs(uri, this._excludedQNames);
         this._includedURIs.remove(uri);
      } else {
         removeAllMatchingNs(uri, this._includedQNames);
      }

   }

   private void removeAllImpl(Set includedURIs, Set excludedURIs, Set includedQNames, Set excludedQNames) {
      boolean exclude = excludedURIs != null;
      Set specialURIs = exclude ? excludedURIs : includedURIs;
      Iterator i = this._includedQNames.iterator();

      QName name;
      String uri;
      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (exclude ^ specialURIs.contains(uri)) {
            if (!excludedQNames.contains(name)) {
               i.remove();
            }
         } else if (includedQNames.contains(name)) {
            i.remove();
         }
      }

      i = includedQNames.iterator();

      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (this._includedURIs.contains(uri)) {
            this._excludedQNames.add(name);
         }
      }

      i = excludedQNames.iterator();

      while(i.hasNext()) {
         name = (QName)i.next();
         uri = nsFromName(name);
         if (this._includedURIs.contains(uri) && !this._excludedQNames.contains(name)) {
            this._includedQNames.add(name);
         }
      }

      if (exclude) {
         removeAllMatchingFirstOnly(this._includedURIs, excludedURIs, this._excludedQNames);
      } else {
         removeAllMatchingBoth(this._includedURIs, includedURIs, this._excludedQNames);
      }

      i = this._includedURIs.iterator();

      while(i.hasNext()) {
         if (exclude ^ specialURIs.contains(i.next())) {
            i.remove();
         }
      }

   }

   public Set excludedURIs() {
      return this._inverted ? Collections.unmodifiableSet(this._includedURIs) : null;
   }

   public Set includedURIs() {
      return !this._inverted ? this._includedURIs : null;
   }

   public Set excludedQNamesInIncludedURIs() {
      return Collections.unmodifiableSet(this._inverted ? this._includedQNames : this._excludedQNames);
   }

   public Set includedQNamesInExcludedURIs() {
      return Collections.unmodifiableSet(this._inverted ? this._excludedQNames : this._includedQNames);
   }

   private String prettyQName(QName name) {
      return name.getNamespaceURI() == null ? name.getLocalPart() : name.getLocalPart() + "@" + name.getNamespaceURI();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("QNameSetBuilder");
      sb.append(this._inverted ? "-(" : "+(");
      Iterator i = this._includedURIs.iterator();

      while(i.hasNext()) {
         sb.append("+*@");
         sb.append(i.next());
         sb.append(", ");
      }

      i = this._excludedQNames.iterator();

      while(i.hasNext()) {
         sb.append("-");
         sb.append(this.prettyQName((QName)i.next()));
         sb.append(", ");
      }

      i = this._includedQNames.iterator();

      while(i.hasNext()) {
         sb.append("+");
         sb.append(this.prettyQName((QName)i.next()));
         sb.append(", ");
      }

      int index = sb.lastIndexOf(", ");
      if (index > 0) {
         sb.setLength(index);
      }

      sb.append(')');
      return sb.toString();
   }

   public QNameSet toQNameSet() {
      return QNameSet.forSpecification(this);
   }
}
