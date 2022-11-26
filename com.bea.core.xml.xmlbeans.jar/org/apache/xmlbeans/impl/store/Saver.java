package org.apache.xmlbeans.impl.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SystemProperties;
import org.apache.xmlbeans.XmlDocumentProperties;
import org.apache.xmlbeans.XmlOptionCharEscapeMap;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.common.EncodingMap;
import org.apache.xmlbeans.impl.common.GenericXmlInputStream;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.XmlEventBase;
import org.apache.xmlbeans.impl.common.XmlNameImpl;
import org.apache.xmlbeans.xml.stream.Attribute;
import org.apache.xmlbeans.xml.stream.AttributeIterator;
import org.apache.xmlbeans.xml.stream.ChangePrefixMapping;
import org.apache.xmlbeans.xml.stream.CharacterData;
import org.apache.xmlbeans.xml.stream.Comment;
import org.apache.xmlbeans.xml.stream.EndDocument;
import org.apache.xmlbeans.xml.stream.EndElement;
import org.apache.xmlbeans.xml.stream.EndPrefixMapping;
import org.apache.xmlbeans.xml.stream.Location;
import org.apache.xmlbeans.xml.stream.ProcessingInstruction;
import org.apache.xmlbeans.xml.stream.StartDocument;
import org.apache.xmlbeans.xml.stream.StartElement;
import org.apache.xmlbeans.xml.stream.StartPrefixMapping;
import org.apache.xmlbeans.xml.stream.XMLEvent;
import org.apache.xmlbeans.xml.stream.XMLName;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

abstract class Saver {
   private static final boolean debug = false;
   static final int ROOT = 1;
   static final int ELEM = 2;
   static final int ATTR = 3;
   static final int COMMENT = 4;
   static final int PROCINST = 5;
   static final int TEXT = 0;
   private final Locale _locale;
   private final long _version;
   private SaveCur _cur;
   private List _ancestorNamespaces;
   private Map _suggestedPrefixes;
   protected XmlOptionCharEscapeMap _replaceChar;
   private boolean _useDefaultNamespace;
   private Map _preComputedNamespaces;
   private boolean _saveNamespacesFirst;
   private ArrayList _attrNames;
   private ArrayList _attrValues;
   private ArrayList _namespaceStack;
   private int _currentMapping;
   private HashMap _uriMap;
   private HashMap _prefixMap;
   private String _initialDefaultUri;
   static final String _newLine = SystemProperties.getProperty("line.separator") == null ? "\n" : SystemProperties.getProperty("line.separator");

   protected abstract boolean emitElement(SaveCur var1, ArrayList var2, ArrayList var3);

   protected abstract void emitFinish(SaveCur var1);

   protected abstract void emitText(SaveCur var1);

   protected abstract void emitComment(SaveCur var1);

   protected abstract void emitProcinst(SaveCur var1);

   protected abstract void emitDocType(String var1, String var2, String var3);

   protected abstract void emitStartDoc(SaveCur var1);

   protected abstract void emitEndDoc(SaveCur var1);

   protected void syntheticNamespace(String prefix, String uri, boolean considerDefault) {
   }

   protected static void arraycopy(int loc, Object src, int srcPos, Object dest, int destPos, int length) {
      System.arraycopy(src, srcPos, dest, destPos, length);
   }

   public static void echoCopyParams(int x, Object src, int srcPos, Object dest, int destPos, int length) {
      int destLen = 0;
      if (dest instanceof byte[]) {
         destLen = ((byte[])((byte[])dest)).length;
      } else if (dest instanceof char[]) {
         destLen = ((char[])((char[])dest)).length;
      } else if (dest instanceof int[]) {
         destLen = ((int[])((int[])dest)).length;
      } else {
         System.err.println("*** dest is not one of the expected types");
      }

      int srcLen = 0;
      if (src instanceof byte[]) {
         srcLen = ((byte[])((byte[])src)).length;
      } else if (src instanceof char[]) {
         srcLen = ((char[])((char[])src)).length;
      } else if (src instanceof int[]) {
         srcLen = ((int[])((int[])src)).length;
      } else {
         System.err.println("*** src is not one of the expected types");
      }

      System.err.println("*** arraycopy(" + x + ")" + length + " bytes from " + srcPos + " of " + src + " of length " + srcLen + " to " + destPos + " of " + dest + " of length " + destLen);
      if (srcPos < 0) {
         System.err.println("*** ERROR: srcPos = " + srcPos + " is negative");
      }

      if (destPos < 0) {
         System.err.println("*** ERROR: destPos = " + destPos + " is negative");
      }

      if (length < 0) {
         System.err.println("*** ERROR: length = " + length + " is negative");
      }

      if (srcPos + length > srcLen) {
         System.err.println("*** ERROR: srcPos+length is greater than the length of the source array");
      }

      if (destPos + length > destLen) {
         System.err.println("*** ERRROR: destPos+length is greater than the destination array");
      }

   }

   Saver(Cur c, XmlOptions options) {
      assert c._locale.entered();

      options = XmlOptions.maskNull(options);
      this._cur = createSaveCur(c, options);
      this._locale = c._locale;
      this._version = this._locale.version();
      this._namespaceStack = new ArrayList();
      this._uriMap = new HashMap();
      this._prefixMap = new HashMap();
      this._attrNames = new ArrayList();
      this._attrValues = new ArrayList();
      this.addMapping("xml", "http://www.w3.org/XML/1998/namespace");
      if (options.hasOption("SAVE_IMPLICIT_NAMESPACES")) {
         Map m = (Map)options.get("SAVE_IMPLICIT_NAMESPACES");
         Iterator i = m.keySet().iterator();

         while(i.hasNext()) {
            String prefix = (String)i.next();
            this.addMapping(prefix, (String)m.get(prefix));
         }
      }

      if (options.hasOption("SAVE_SUBSTITUTE_CHARACTERS")) {
         this._replaceChar = (XmlOptionCharEscapeMap)options.get("SAVE_SUBSTITUTE_CHARACTERS");
      }

      if (this.getNamespaceForPrefix("") == null) {
         this._initialDefaultUri = new String("");
         this.addMapping("", this._initialDefaultUri);
      }

      if (options.hasOption("SAVE_AGGRESSIVE_NAMESPACES") && !(this instanceof SynthNamespaceSaver)) {
         SynthNamespaceSaver saver = new SynthNamespaceSaver(c, options);

         while(true) {
            if (!saver.process()) {
               if (!saver._synthNamespaces.isEmpty()) {
                  this._preComputedNamespaces = saver._synthNamespaces;
               }
               break;
            }
         }
      }

      this._useDefaultNamespace = options.hasOption("SAVE_USE_DEFAULT_NAMESPACE");
      this._saveNamespacesFirst = options.hasOption("SAVE_NAMESPACES_FIRST");
      if (options.hasOption("SAVE_SUGGESTED_PREFIXES")) {
         this._suggestedPrefixes = (Map)options.get("SAVE_SUGGESTED_PREFIXES");
      }

      this._ancestorNamespaces = this._cur.getAncestorNamespaces();
   }

   private static SaveCur createSaveCur(Cur c, XmlOptions options) {
      QName synthName = (QName)options.get("SAVE_SYNTHETIC_DOCUMENT_ELEMENT");
      QName fragName = synthName;
      if (synthName == null) {
         fragName = options.hasOption("SAVE_USE_OPEN_FRAGMENT") ? Locale._openuriFragment : Locale._xmlFragment;
      }

      boolean saveInner = options.hasOption("SAVE_INNER") && !options.hasOption("SAVE_OUTER");
      Cur start = c.tempCur();
      Cur end = c.tempCur();
      SaveCur cur = null;
      int k = c.kind();
      switch (k) {
         case 1:
            positionToInner(c, start, end);
            if (Locale.isFragment(start, end)) {
               cur = new FragSaveCur(start, end, fragName);
            } else if (synthName != null) {
               cur = new FragSaveCur(start, end, synthName);
            } else {
               cur = new DocSaveCur(c);
            }
            break;
         case 2:
            if (saveInner) {
               positionToInner(c, start, end);
               cur = new FragSaveCur(start, end, Locale.isFragment(start, end) ? fragName : synthName);
            } else if (synthName != null) {
               positionToInner(c, start, end);
               cur = new FragSaveCur(start, end, synthName);
            } else {
               start.moveToCur(c);
               end.moveToCur(c);
               end.skip();
               cur = new FragSaveCur(start, end, (QName)null);
            }
      }

      if (cur == null) {
         assert k < 0 || k == 3 || k == 4 || k == 5 || k == 0;

         if (k < 0) {
            start.moveToCur(c);
            end.moveToCur(c);
         } else if (k == 0) {
            start.moveToCur(c);
            end.moveToCur(c);
            end.next();
         } else if (saveInner) {
            start.moveToCur(c);
            start.next();
            end.moveToCur(c);
            end.toEnd();
         } else if (k == 3) {
            start.moveToCur(c);
            end.moveToCur(c);
         } else {
            assert k == 4 || k == 5;

            start.moveToCur(c);
            end.moveToCur(c);
            end.skip();
         }

         cur = new FragSaveCur(start, end, fragName);
      }

      String filterPI = (String)options.get("SAVE_FILTER_PROCINST");
      if (filterPI != null) {
         cur = new FilterPiSaveCur((SaveCur)cur, filterPI);
      }

      if (options.hasOption("SAVE_PRETTY_PRINT")) {
         cur = new PrettySaveCur((SaveCur)cur, options);
      }

      start.release();
      end.release();
      return (SaveCur)cur;
   }

   private static void positionToInner(Cur c, Cur start, Cur end) {
      assert c.isContainer();

      start.moveToCur(c);
      if (!start.toFirstAttr()) {
         start.next();
      }

      end.moveToCur(c);
      end.toEnd();
   }

   protected boolean saveNamespacesFirst() {
      return this._saveNamespacesFirst;
   }

   protected void enterLocale() {
      this._locale.enter();
   }

   protected void exitLocale() {
      this._locale.exit();
   }

   protected final boolean process() {
      assert this._locale.entered();

      if (this._cur == null) {
         return false;
      } else if (this._version != this._locale.version()) {
         throw new ConcurrentModificationException("Document changed during save");
      } else {
         switch (this._cur.kind()) {
            case -2:
               this.processFinish();
               break;
            case -1:
               this.emitEndDoc(this._cur);
               this._cur.release();
               this._cur = null;
               return true;
            case 0:
               this.emitText(this._cur);
               break;
            case 1:
               this.processRoot();
               break;
            case 2:
               this.processElement();
               break;
            case 3:
            default:
               throw new RuntimeException("Unexpected kind");
            case 4:
               this.emitComment(this._cur);
               this._cur.toEnd();
               break;
            case 5:
               this.emitProcinst(this._cur);
               this._cur.toEnd();
         }

         this._cur.next();
         return true;
      }
   }

   private final void processFinish() {
      this.emitFinish(this._cur);
      this.popMappings();
   }

   private final void processRoot() {
      assert this._cur.isRoot();

      XmlDocumentProperties props = this._cur.getDocProps();
      String systemId = null;
      String docTypeName = null;
      if (props != null) {
         systemId = props.getDoctypeSystemId();
         docTypeName = props.getDoctypeName();
      }

      if (systemId != null || docTypeName != null) {
         if (docTypeName == null) {
            this._cur.push();

            while(!this._cur.isElem() && this._cur.next()) {
            }

            if (this._cur.isElem()) {
               docTypeName = this._cur.getName().getLocalPart();
            }

            this._cur.pop();
         }

         String publicId = props.getDoctypePublicId();
         if (docTypeName != null) {
            QName rootElemName = this._cur.getName();
            if (rootElemName == null) {
               this._cur.push();

               while(!this._cur.isFinish()) {
                  if (this._cur.isElem()) {
                     rootElemName = this._cur.getName();
                     break;
                  }

                  this._cur.next();
               }

               this._cur.pop();
            }

            if (rootElemName != null && docTypeName.equals(rootElemName.getLocalPart())) {
               this.emitDocType(docTypeName, publicId, systemId);
               return;
            }
         }
      }

      this.emitStartDoc(this._cur);
   }

   private final void processElement() {
      assert this._cur.isElem() && this._cur.getName() != null;

      QName name = this._cur.getName();
      boolean ensureDefaultEmpty = name.getNamespaceURI().length() == 0;
      this.pushMappings(this._cur, ensureDefaultEmpty);
      this.ensureMapping(name.getNamespaceURI(), name.getPrefix(), !ensureDefaultEmpty, false);
      this._attrNames.clear();
      this._attrValues.clear();
      this._cur.push();

      label67:
      for(boolean A = this._cur.toFirstAttr(); A; A = this._cur.toNextAttr()) {
         if (this._cur.isNormalAttr()) {
            QName attrName = this._cur.getName();
            this._attrNames.add(attrName);

            for(int i = this._attrNames.size() - 2; i >= 0; --i) {
               if (this._attrNames.get(i).equals(attrName)) {
                  this._attrNames.remove(this._attrNames.size() - 1);
                  continue label67;
               }
            }

            this._attrValues.add(this._cur.getAttrValue());
            this.ensureMapping(attrName.getNamespaceURI(), attrName.getPrefix(), false, true);
         }
      }

      this._cur.pop();
      if (this._preComputedNamespaces != null) {
         Iterator i = this._preComputedNamespaces.keySet().iterator();

         while(i.hasNext()) {
            String uri = (String)i.next();
            String prefix = (String)this._preComputedNamespaces.get(uri);
            boolean considerDefault = prefix.length() == 0 && !ensureDefaultEmpty;
            this.ensureMapping(uri, prefix, considerDefault, false);
         }

         this._preComputedNamespaces = null;
      }

      if (this.emitElement(this._cur, this._attrNames, this._attrValues)) {
         this.popMappings();
         this._cur.toEnd();
      }

   }

   boolean hasMappings() {
      int i = this._namespaceStack.size();
      return i > 0 && this._namespaceStack.get(i - 1) != null;
   }

   void iterateMappings() {
      for(this._currentMapping = this._namespaceStack.size(); this._currentMapping > 0 && this._namespaceStack.get(this._currentMapping - 1) != null; this._currentMapping -= 8) {
      }

   }

   boolean hasMapping() {
      return this._currentMapping < this._namespaceStack.size();
   }

   void nextMapping() {
      this._currentMapping += 8;
   }

   String mappingPrefix() {
      assert this.hasMapping();

      return (String)this._namespaceStack.get(this._currentMapping + 6);
   }

   String mappingUri() {
      assert this.hasMapping();

      return (String)this._namespaceStack.get(this._currentMapping + 7);
   }

   private final void pushMappings(SaveCur c, boolean ensureDefaultEmpty) {
      assert c.isContainer();

      this._namespaceStack.add((Object)null);
      c.push();

      for(boolean A = c.toFirstAttr(); A; A = c.toNextAttr()) {
         if (c.isXmlns()) {
            this.addNewFrameMapping(c.getXmlnsPrefix(), c.getXmlnsUri(), ensureDefaultEmpty);
         }
      }

      c.pop();
      if (this._ancestorNamespaces != null) {
         for(int i = 0; i < this._ancestorNamespaces.size(); i += 2) {
            String prefix = (String)this._ancestorNamespaces.get(i);
            String uri = (String)this._ancestorNamespaces.get(i + 1);
            this.addNewFrameMapping(prefix, uri, ensureDefaultEmpty);
         }

         this._ancestorNamespaces = null;
      }

      if (ensureDefaultEmpty) {
         String defaultUri = (String)this._prefixMap.get("");

         assert defaultUri != null;

         if (defaultUri.length() > 0) {
            this.addMapping("", "");
         }
      }

   }

   private final void addNewFrameMapping(String prefix, String uri, boolean ensureDefaultEmpty) {
      if ((prefix.length() == 0 || uri.length() > 0) && (!ensureDefaultEmpty || prefix.length() > 0 || uri.length() == 0)) {
         this.iterateMappings();

         while(this.hasMapping()) {
            if (this.mappingPrefix().equals(prefix)) {
               return;
            }

            this.nextMapping();
         }

         if (uri.equals(this.getNamespaceForPrefix(prefix))) {
            return;
         }

         this.addMapping(prefix, uri);
      }

   }

   private final void addMapping(String prefix, String uri) {
      assert uri != null;

      assert prefix != null;

      String renameUri = (String)this._prefixMap.get(prefix);
      String renamePrefix = null;
      if (renameUri != null) {
         if (renameUri.equals(uri)) {
            renameUri = null;
         } else {
            int i = this._namespaceStack.size();

            while(i > 0) {
               if (this._namespaceStack.get(i - 1) == null) {
                  --i;
               } else {
                  if (this._namespaceStack.get(i - 7).equals(renameUri)) {
                     renamePrefix = (String)this._namespaceStack.get(i - 8);
                     if (renamePrefix == null || !renamePrefix.equals(prefix)) {
                        break;
                     }
                  }

                  i -= 8;
               }
            }

            assert i > 0;
         }
      }

      this._namespaceStack.add(this._uriMap.get(uri));
      this._namespaceStack.add(uri);
      if (renameUri != null) {
         this._namespaceStack.add(this._uriMap.get(renameUri));
         this._namespaceStack.add(renameUri);
      } else {
         this._namespaceStack.add((Object)null);
         this._namespaceStack.add((Object)null);
      }

      this._namespaceStack.add(prefix);
      this._namespaceStack.add(this._prefixMap.get(prefix));
      this._namespaceStack.add(prefix);
      this._namespaceStack.add(uri);
      this._uriMap.put(uri, prefix);
      this._prefixMap.put(prefix, uri);
      if (renameUri != null) {
         this._uriMap.put(renameUri, renamePrefix);
      }

   }

   private final void popMappings() {
      while(true) {
         int i = this._namespaceStack.size();
         if (i != 0) {
            if (this._namespaceStack.get(i - 1) != null) {
               Object oldUri = this._namespaceStack.get(i - 7);
               Object oldPrefix = this._namespaceStack.get(i - 8);
               if (oldPrefix == null) {
                  this._uriMap.remove(oldUri);
               } else {
                  this._uriMap.put(oldUri, oldPrefix);
               }

               oldPrefix = this._namespaceStack.get(i - 4);
               oldUri = this._namespaceStack.get(i - 3);
               if (oldUri == null) {
                  this._prefixMap.remove(oldPrefix);
               } else {
                  this._prefixMap.put(oldPrefix, oldUri);
               }

               String uri = (String)this._namespaceStack.get(i - 5);
               if (uri != null) {
                  this._uriMap.put(uri, this._namespaceStack.get(i - 6));
               }

               this._namespaceStack.remove(i - 1);
               this._namespaceStack.remove(i - 2);
               this._namespaceStack.remove(i - 3);
               this._namespaceStack.remove(i - 4);
               this._namespaceStack.remove(i - 5);
               this._namespaceStack.remove(i - 6);
               this._namespaceStack.remove(i - 7);
               this._namespaceStack.remove(i - 8);
               continue;
            }

            this._namespaceStack.remove(i - 1);
         }

         return;
      }
   }

   private final void dumpMappings() {
      int i = this._namespaceStack.size();

      while(i > 0) {
         if (this._namespaceStack.get(i - 1) == null) {
            System.out.println("----------------");
            --i;
         } else {
            System.out.print("Mapping: ");
            System.out.print(this._namespaceStack.get(i - 2));
            System.out.print(" -> ");
            System.out.print(this._namespaceStack.get(i - 1));
            System.out.println();
            System.out.print("Prefix Undo: ");
            System.out.print(this._namespaceStack.get(i - 4));
            System.out.print(" -> ");
            System.out.print(this._namespaceStack.get(i - 3));
            System.out.println();
            System.out.print("Uri Rename: ");
            System.out.print(this._namespaceStack.get(i - 5));
            System.out.print(" -> ");
            System.out.print(this._namespaceStack.get(i - 6));
            System.out.println();
            System.out.print("UriUndo: ");
            System.out.print(this._namespaceStack.get(i - 7));
            System.out.print(" -> ");
            System.out.print(this._namespaceStack.get(i - 8));
            System.out.println();
            System.out.println();
            i -= 8;
         }
      }

   }

   private final String ensureMapping(String uri, String candidatePrefix, boolean considerCreatingDefault, boolean mustHavePrefix) {
      assert uri != null;

      if (uri.length() == 0) {
         return null;
      } else {
         String prefix = (String)this._uriMap.get(uri);
         if (prefix == null || prefix.length() <= 0 && mustHavePrefix) {
            if (candidatePrefix != null && candidatePrefix.length() == 0) {
               candidatePrefix = null;
            }

            if (candidatePrefix == null || !this.tryPrefix(candidatePrefix)) {
               if (this._suggestedPrefixes != null && this._suggestedPrefixes.containsKey(uri) && this.tryPrefix((String)this._suggestedPrefixes.get(uri))) {
                  candidatePrefix = (String)this._suggestedPrefixes.get(uri);
               } else if (considerCreatingDefault && this._useDefaultNamespace && this.tryPrefix("")) {
                  candidatePrefix = "";
               } else {
                  String basePrefix = QNameHelper.suggestPrefix(uri);
                  candidatePrefix = basePrefix;

                  for(int i = 1; !this.tryPrefix(candidatePrefix); ++i) {
                     candidatePrefix = basePrefix + i;
                  }
               }
            }

            assert candidatePrefix != null;

            this.syntheticNamespace(candidatePrefix, uri, considerCreatingDefault);
            this.addMapping(candidatePrefix, uri);
            return candidatePrefix;
         } else {
            return prefix;
         }
      }
   }

   protected final String getUriMapping(String uri) {
      assert this._uriMap.get(uri) != null;

      return (String)this._uriMap.get(uri);
   }

   String getNonDefaultUriMapping(String uri) {
      String prefix = (String)this._uriMap.get(uri);
      if (prefix != null && prefix.length() > 0) {
         return prefix;
      } else {
         Iterator keys = this._prefixMap.keySet().iterator();

         do {
            if (!keys.hasNext()) {
               assert false : "Could not find non-default mapping";

               return null;
            }

            prefix = (String)keys.next();
         } while(prefix.length() <= 0 || !this._prefixMap.get(prefix).equals(uri));

         return prefix;
      }
   }

   private final boolean tryPrefix(String prefix) {
      if (prefix != null && !Locale.beginsWithXml(prefix)) {
         String existingUri = (String)this._prefixMap.get(prefix);
         return existingUri == null || prefix.length() <= 0 && existingUri == this._initialDefaultUri;
      } else {
         return false;
      }
   }

   public final String getNamespaceForPrefix(String prefix) {
      assert !prefix.equals("xml") || this._prefixMap.get(prefix).equals("http://www.w3.org/XML/1998/namespace");

      return (String)this._prefixMap.get(prefix);
   }

   protected Map getPrefixMap() {
      return this._prefixMap;
   }

   private static final class PrettySaveCur extends SaveCur {
      private SaveCur _cur;
      private int _prettyIndent;
      private int _prettyOffset;
      private String _txt;
      private StringBuffer _sb = new StringBuffer();
      private int _depth;
      private ArrayList _stack = new ArrayList();
      private boolean _isTextCData = false;
      private boolean _useCDataBookmarks = false;

      PrettySaveCur(SaveCur c, XmlOptions options) {
         this._cur = c;

         assert options != null;

         this._prettyIndent = 2;
         if (options.hasOption("SAVE_PRETTY_PRINT_INDENT")) {
            this._prettyIndent = (Integer)options.get("SAVE_PRETTY_PRINT_INDENT");
         }

         if (options.hasOption("SAVE_PRETTY_PRINT_OFFSET")) {
            this._prettyOffset = (Integer)options.get("SAVE_PRETTY_PRINT_OFFSET");
         }

         if (options.hasOption("LOAD_SAVE_CDATA_BOOKMARKS")) {
            this._useCDataBookmarks = true;
         }

      }

      List getAncestorNamespaces() {
         return this._cur.getAncestorNamespaces();
      }

      void release() {
         this._cur.release();
      }

      int kind() {
         return this._txt == null ? this._cur.kind() : 0;
      }

      QName getName() {
         assert this._txt == null;

         return this._cur.getName();
      }

      String getXmlnsPrefix() {
         assert this._txt == null;

         return this._cur.getXmlnsPrefix();
      }

      String getXmlnsUri() {
         assert this._txt == null;

         return this._cur.getXmlnsUri();
      }

      boolean isXmlns() {
         return this._txt == null ? this._cur.isXmlns() : false;
      }

      boolean hasChildren() {
         return this._txt == null ? this._cur.hasChildren() : false;
      }

      boolean hasText() {
         return this._txt == null ? this._cur.hasText() : false;
      }

      boolean isTextCData() {
         return this._txt == null ? this._useCDataBookmarks && this._cur.isTextCData() : this._isTextCData;
      }

      boolean toFirstAttr() {
         assert this._txt == null;

         return this._cur.toFirstAttr();
      }

      boolean toNextAttr() {
         assert this._txt == null;

         return this._cur.toNextAttr();
      }

      String getAttrValue() {
         assert this._txt == null;

         return this._cur.getAttrValue();
      }

      void toEnd() {
         assert this._txt == null;

         this._cur.toEnd();
         if (this._cur.kind() == -2) {
            --this._depth;
         }

      }

      boolean next() {
         int k;
         if (this._txt != null) {
            assert this._txt.length() > 0;

            assert !this._cur.isText();

            this._txt = null;
            this._isTextCData = false;
            k = this._cur.kind();
         } else {
            int prevKind = this._cur.kind();
            if (!this._cur.next()) {
               return false;
            }

            this._sb.delete(0, this._sb.length());

            assert this._txt == null;

            if (this._cur.isText()) {
               this._isTextCData = this._useCDataBookmarks && this._cur.isTextCData();
               CharUtil.getString(this._sb, this._cur.getChars(), this._cur._offSrc, this._cur._cchSrc);
               this._cur.next();
               trim(this._sb);
            }

            k = this._cur.kind();
            if (this._prettyIndent >= 0 && prevKind != 4 && prevKind != 5 && (prevKind != 2 || k != -2)) {
               if (this._sb.length() > 0) {
                  this._sb.insert(0, Saver._newLine);
                  spaces(this._sb, Saver._newLine.length(), this._prettyOffset + this._prettyIndent * this._depth);
               }

               if (k != -1) {
                  if (prevKind != 1) {
                     this._sb.append(Saver._newLine);
                  }

                  int d = k < 0 ? this._depth - 1 : this._depth;
                  spaces(this._sb, this._sb.length(), this._prettyOffset + this._prettyIndent * d);
               }
            }

            if (this._sb.length() > 0) {
               this._txt = this._sb.toString();
               k = 0;
            }
         }

         if (k == 2) {
            ++this._depth;
         } else if (k == -2) {
            --this._depth;
         }

         return true;
      }

      void push() {
         this._cur.push();
         this._stack.add(this._txt);
         this._stack.add(new Integer(this._depth));
         this._isTextCData = false;
      }

      void pop() {
         this._cur.pop();
         this._depth = (Integer)this._stack.remove(this._stack.size() - 1);
         this._txt = (String)this._stack.remove(this._stack.size() - 1);
         this._isTextCData = false;
      }

      Object getChars() {
         if (this._txt != null) {
            this._offSrc = 0;
            this._cchSrc = this._txt.length();
            return this._txt;
         } else {
            Object o = this._cur.getChars();
            this._offSrc = this._cur._offSrc;
            this._cchSrc = this._cur._cchSrc;
            return o;
         }
      }

      XmlDocumentProperties getDocProps() {
         return this._cur.getDocProps();
      }

      static void spaces(StringBuffer sb, int offset, int count) {
         while(count-- > 0) {
            sb.insert(offset, ' ');
         }

      }

      static void trim(StringBuffer sb) {
         int i;
         for(i = 0; i < sb.length() && CharUtil.isWhiteSpace(sb.charAt(i)); ++i) {
         }

         sb.delete(0, i);

         for(i = sb.length(); i > 0 && CharUtil.isWhiteSpace(sb.charAt(i - 1)); --i) {
         }

         sb.delete(i, sb.length());
      }
   }

   private static final class FragSaveCur extends SaveCur {
      private Cur _cur;
      private Cur _end;
      private ArrayList _ancestorNamespaces;
      private QName _elem;
      private boolean _saveAttr;
      private static final int ROOT_START = 1;
      private static final int ELEM_START = 2;
      private static final int ROOT_END = 3;
      private static final int ELEM_END = 4;
      private static final int CUR = 5;
      private int _state;
      private int[] _stateStack;
      private int _stateStackSize;

      FragSaveCur(Cur start, Cur end, QName synthElem) {
         this._saveAttr = start.isAttr() && start.isSamePos(end);
         this._cur = start.weakCur(this);
         this._end = end.weakCur(this);
         this._elem = synthElem;
         this._state = 1;
         this._stateStack = new int[8];
         start.push();
         this.computeAncestorNamespaces(start);
         start.pop();
      }

      List getAncestorNamespaces() {
         return this._ancestorNamespaces;
      }

      private void computeAncestorNamespaces(Cur c) {
         this._ancestorNamespaces = new ArrayList();

         while(true) {
            do {
               if (!c.toParentRaw()) {
                  return;
               }
            } while(!c.toFirstAttr());

            do {
               if (c.isXmlns()) {
                  String prefix = c.getXmlnsPrefix();
                  String uri = c.getXmlnsUri();
                  if (uri.length() > 0 || prefix.length() == 0) {
                     this._ancestorNamespaces.add(c.getXmlnsPrefix());
                     this._ancestorNamespaces.add(c.getXmlnsUri());
                  }
               }
            } while(c.toNextAttr());

            c.toParent();
         }
      }

      void release() {
         this._cur.release();
         this._cur = null;
         this._end.release();
         this._end = null;
      }

      int kind() {
         switch (this._state) {
            case 1:
               return 1;
            case 2:
               return 2;
            case 3:
               return -1;
            case 4:
               return -2;
            default:
               assert this._state == 5;

               return this._cur.kind();
         }
      }

      QName getName() {
         switch (this._state) {
            case 1:
            case 3:
               return null;
            case 2:
            case 4:
               return this._elem;
            default:
               assert this._state == 5;

               return this._cur.getName();
         }
      }

      String getXmlnsPrefix() {
         assert this._state == 5 && this._cur.isAttr();

         return this._cur.getXmlnsPrefix();
      }

      String getXmlnsUri() {
         assert this._state == 5 && this._cur.isAttr();

         return this._cur.getXmlnsUri();
      }

      boolean isXmlns() {
         assert this._state == 5 && this._cur.isAttr();

         return this._cur.isXmlns();
      }

      boolean hasChildren() {
         boolean hasChildren = false;
         if (this.isContainer()) {
            this.push();
            this.next();
            if (!this.isText() && !this.isFinish()) {
               hasChildren = true;
            }

            this.pop();
         }

         return hasChildren;
      }

      boolean hasText() {
         boolean hasText = false;
         if (this.isContainer()) {
            this.push();
            this.next();
            if (this.isText()) {
               hasText = true;
            }

            this.pop();
         }

         return hasText;
      }

      boolean isTextCData() {
         return this._cur.isTextCData();
      }

      Object getChars() {
         assert this._state == 5 && this._cur.isText();

         Object src = this._cur.getChars(-1);
         this._offSrc = this._cur._offSrc;
         this._cchSrc = this._cur._cchSrc;
         return src;
      }

      boolean next() {
         switch (this._state) {
            case 1:
               this._state = this._elem == null ? 5 : 2;
               break;
            case 2:
               if (this._saveAttr) {
                  this._state = 4;
               } else {
                  if (this._cur.isAttr()) {
                     this._cur.toParent();
                     this._cur.next();
                  }

                  if (this._cur.isSamePos(this._end)) {
                     this._state = 4;
                  } else {
                     this._state = 5;
                  }
               }
               break;
            case 3:
               return false;
            case 4:
               this._state = 3;
               break;
            case 5:
               assert !this._cur.isAttr();

               this._cur.next();
               if (this._cur.isSamePos(this._end)) {
                  this._state = this._elem == null ? 3 : 4;
               }
         }

         return true;
      }

      void toEnd() {
         switch (this._state) {
            case 1:
               this._state = 3;
               return;
            case 2:
               this._state = 4;
               return;
            case 3:
            case 4:
               return;
            default:
               assert this._state == 5 && !this._cur.isAttr() && !this._cur.isText();

               this._cur.toEnd();
         }
      }

      boolean toFirstAttr() {
         switch (this._state) {
            case 1:
            case 3:
            case 4:
               return false;
            case 2:
            default:
               assert this._state == 2;

               if (!this._cur.isAttr()) {
                  return false;
               }

               this._state = 5;
               return true;
            case 5:
               return this._cur.toFirstAttr();
         }
      }

      boolean toNextAttr() {
         assert this._state == 5;

         return !this._saveAttr && this._cur.toNextAttr();
      }

      String getAttrValue() {
         assert this._state == 5 && this._cur.isAttr();

         return this._cur.getValueAsString();
      }

      void push() {
         if (this._stateStackSize == this._stateStack.length) {
            int[] newStateStack = new int[this._stateStackSize * 2];
            Saver.arraycopy(23, this._stateStack, 0, newStateStack, 0, this._stateStackSize);
            this._stateStack = newStateStack;
         }

         this._stateStack[this._stateStackSize++] = this._state;
         this._cur.push();
      }

      void pop() {
         this._cur.pop();
         this._state = this._stateStack[--this._stateStackSize];
      }

      XmlDocumentProperties getDocProps() {
         return Locale.getDocProps(this._cur, false);
      }
   }

   private static final class FilterPiSaveCur extends FilterSaveCur {
      private String _piTarget;

      FilterPiSaveCur(SaveCur c, String target) {
         super(c);
         this._piTarget = target;
      }

      protected boolean filter() {
         return this.kind() == 5 && this.getName().getLocalPart().equals(this._piTarget);
      }
   }

   private abstract static class FilterSaveCur extends SaveCur {
      private SaveCur _cur;

      FilterSaveCur(SaveCur c) {
         assert c.isRoot();

         this._cur = c;
      }

      protected abstract boolean filter();

      void release() {
         this._cur.release();
         this._cur = null;
      }

      int kind() {
         return this._cur.kind();
      }

      QName getName() {
         return this._cur.getName();
      }

      String getXmlnsPrefix() {
         return this._cur.getXmlnsPrefix();
      }

      String getXmlnsUri() {
         return this._cur.getXmlnsUri();
      }

      boolean isXmlns() {
         return this._cur.isXmlns();
      }

      boolean hasChildren() {
         return this._cur.hasChildren();
      }

      boolean hasText() {
         return this._cur.hasText();
      }

      boolean isTextCData() {
         return this._cur.isTextCData();
      }

      boolean toFirstAttr() {
         return this._cur.toFirstAttr();
      }

      boolean toNextAttr() {
         return this._cur.toNextAttr();
      }

      String getAttrValue() {
         return this._cur.getAttrValue();
      }

      void toEnd() {
         this._cur.toEnd();
      }

      boolean next() {
         if (!this._cur.next()) {
            return false;
         } else if (!this.filter()) {
            return true;
         } else {
            assert !this.isRoot() && !this.isText() && !this.isAttr();

            this.toEnd();
            return this.next();
         }
      }

      void push() {
         this._cur.push();
      }

      void pop() {
         this._cur.pop();
      }

      List getAncestorNamespaces() {
         return this._cur.getAncestorNamespaces();
      }

      Object getChars() {
         Object o = this._cur.getChars();
         this._offSrc = this._cur._offSrc;
         this._cchSrc = this._cur._cchSrc;
         return o;
      }

      XmlDocumentProperties getDocProps() {
         return this._cur.getDocProps();
      }
   }

   private static final class DocSaveCur extends SaveCur {
      private Cur _cur;

      DocSaveCur(Cur c) {
         assert c.isRoot();

         this._cur = c.weakCur(this);
      }

      void release() {
         this._cur.release();
         this._cur = null;
      }

      int kind() {
         return this._cur.kind();
      }

      QName getName() {
         return this._cur.getName();
      }

      String getXmlnsPrefix() {
         return this._cur.getXmlnsPrefix();
      }

      String getXmlnsUri() {
         return this._cur.getXmlnsUri();
      }

      boolean isXmlns() {
         return this._cur.isXmlns();
      }

      boolean hasChildren() {
         return this._cur.hasChildren();
      }

      boolean hasText() {
         return this._cur.hasText();
      }

      boolean isTextCData() {
         return this._cur.isTextCData();
      }

      boolean toFirstAttr() {
         return this._cur.toFirstAttr();
      }

      boolean toNextAttr() {
         return this._cur.toNextAttr();
      }

      String getAttrValue() {
         assert this._cur.isAttr();

         return this._cur.getValueAsString();
      }

      void toEnd() {
         this._cur.toEnd();
      }

      boolean next() {
         return this._cur.next();
      }

      void push() {
         this._cur.push();
      }

      void pop() {
         this._cur.pop();
      }

      List getAncestorNamespaces() {
         return null;
      }

      Object getChars() {
         Object o = this._cur.getChars(-1);
         this._offSrc = this._cur._offSrc;
         this._cchSrc = this._cur._cchSrc;
         return o;
      }

      XmlDocumentProperties getDocProps() {
         return Locale.getDocProps(this._cur, false);
      }
   }

   abstract static class SaveCur {
      int _offSrc;
      int _cchSrc;

      final boolean isRoot() {
         return this.kind() == 1;
      }

      final boolean isElem() {
         return this.kind() == 2;
      }

      final boolean isAttr() {
         return this.kind() == 3;
      }

      final boolean isText() {
         return this.kind() == 0;
      }

      final boolean isComment() {
         return this.kind() == 4;
      }

      final boolean isProcinst() {
         return this.kind() == 5;
      }

      final boolean isFinish() {
         return Cur.kindIsFinish(this.kind());
      }

      final boolean isContainer() {
         return Cur.kindIsContainer(this.kind());
      }

      final boolean isNormalAttr() {
         return this.kind() == 3 && !this.isXmlns();
      }

      final boolean skip() {
         this.toEnd();
         return this.next();
      }

      abstract void release();

      abstract int kind();

      abstract QName getName();

      abstract String getXmlnsPrefix();

      abstract String getXmlnsUri();

      abstract boolean isXmlns();

      abstract boolean hasChildren();

      abstract boolean hasText();

      abstract boolean isTextCData();

      abstract boolean toFirstAttr();

      abstract boolean toNextAttr();

      abstract String getAttrValue();

      abstract boolean next();

      abstract void toEnd();

      abstract void push();

      abstract void pop();

      abstract Object getChars();

      abstract List getAncestorNamespaces();

      abstract XmlDocumentProperties getDocProps();
   }

   static final class SaxSaver extends Saver {
      private ContentHandler _contentHandler;
      private LexicalHandler _lexicalHandler;
      private AttributesImpl _attributes;
      private char[] _buf;
      private boolean _nsAsAttrs;

      SaxSaver(Cur c, XmlOptions options, ContentHandler ch, LexicalHandler lh) throws SAXException {
         super(c, options);
         this._contentHandler = ch;
         this._lexicalHandler = lh;
         this._attributes = new AttributesImpl();
         this._nsAsAttrs = !options.hasOption("SAVE_SAX_NO_NSDECLS_IN_ATTRIBUTES");
         this._contentHandler.startDocument();

         try {
            while(true) {
               if (this.process()) {
                  continue;
               }
            }
         } catch (SaverSAXException var6) {
            throw var6._saxException;
         }

         this._contentHandler.endDocument();
      }

      private String getPrefixedName(QName name) {
         String uri = name.getNamespaceURI();
         String local = name.getLocalPart();
         if (uri.length() == 0) {
            return local;
         } else {
            String prefix = this.getUriMapping(uri);
            return prefix.length() == 0 ? local : prefix + ":" + local;
         }
      }

      private void emitNamespacesHelper() {
         this.iterateMappings();

         for(; this.hasMapping(); this.nextMapping()) {
            String prefix = this.mappingPrefix();
            String uri = this.mappingUri();

            try {
               this._contentHandler.startPrefixMapping(prefix, uri);
            } catch (SAXException var4) {
               throw new SaverSAXException(var4);
            }

            if (this._nsAsAttrs) {
               if (prefix != null && prefix.length() != 0) {
                  this._attributes.addAttribute("http://www.w3.org/2000/xmlns/", prefix, "xmlns:" + prefix, "CDATA", uri);
               } else {
                  this._attributes.addAttribute("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns", "CDATA", uri);
               }
            }
         }

      }

      protected boolean emitElement(SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         this._attributes.clear();
         if (this.saveNamespacesFirst()) {
            this.emitNamespacesHelper();
         }

         for(int i = 0; i < attrNames.size(); ++i) {
            QName name = (QName)attrNames.get(i);
            this._attributes.addAttribute(name.getNamespaceURI(), name.getLocalPart(), this.getPrefixedName(name), "CDATA", (String)attrValues.get(i));
         }

         if (!this.saveNamespacesFirst()) {
            this.emitNamespacesHelper();
         }

         QName elemName = c.getName();

         try {
            this._contentHandler.startElement(elemName.getNamespaceURI(), elemName.getLocalPart(), this.getPrefixedName(elemName), this._attributes);
            return false;
         } catch (SAXException var6) {
            throw new SaverSAXException(var6);
         }
      }

      protected void emitFinish(SaveCur c) {
         QName name = c.getName();

         try {
            this._contentHandler.endElement(name.getNamespaceURI(), name.getLocalPart(), this.getPrefixedName(name));
            this.iterateMappings();

            while(this.hasMapping()) {
               this._contentHandler.endPrefixMapping(this.mappingPrefix());
               this.nextMapping();
            }

         } catch (SAXException var4) {
            throw new SaverSAXException(var4);
         }
      }

      protected void emitText(SaveCur c) {
         assert c.isText();

         Object src = c.getChars();

         try {
            if (src instanceof char[]) {
               this._contentHandler.characters((char[])((char[])src), c._offSrc, c._cchSrc);
            } else {
               if (this._buf == null) {
                  this._buf = new char[1024];
               }

               while(c._cchSrc > 0) {
                  int cch = Math.min(this._buf.length, c._cchSrc);
                  CharUtil.getChars(this._buf, 0, src, c._offSrc, cch);
                  this._contentHandler.characters(this._buf, 0, cch);
                  c._offSrc += cch;
                  c._cchSrc -= cch;
               }
            }

         } catch (SAXException var4) {
            throw new SaverSAXException(var4);
         }
      }

      protected void emitComment(SaveCur c) {
         if (this._lexicalHandler != null) {
            c.push();
            c.next();

            try {
               if (!c.isText()) {
                  this._lexicalHandler.comment((char[])null, 0, 0);
               } else {
                  Object src = c.getChars();
                  if (src instanceof char[]) {
                     this._lexicalHandler.comment((char[])((char[])src), c._offSrc, c._cchSrc);
                  } else {
                     if (this._buf == null || this._buf.length < c._cchSrc) {
                        this._buf = new char[Math.max(1024, c._cchSrc)];
                     }

                     CharUtil.getChars(this._buf, 0, src, c._offSrc, c._cchSrc);
                     this._lexicalHandler.comment(this._buf, 0, c._cchSrc);
                  }
               }
            } catch (SAXException var3) {
               throw new SaverSAXException(var3);
            }

            c.pop();
         }

      }

      protected void emitProcinst(SaveCur c) {
         String target = c.getName().getLocalPart();
         c.push();
         c.next();
         String value = CharUtil.getString(c.getChars(), c._offSrc, c._cchSrc);
         c.pop();

         try {
            this._contentHandler.processingInstruction(c.getName().getLocalPart(), value);
         } catch (SAXException var5) {
            throw new SaverSAXException(var5);
         }
      }

      protected void emitDocType(String docTypeName, String publicId, String systemId) {
         if (this._lexicalHandler != null) {
            try {
               this._lexicalHandler.startDTD(docTypeName, publicId, systemId);
               this._lexicalHandler.endDTD();
            } catch (SAXException var5) {
               throw new SaverSAXException(var5);
            }
         }

      }

      protected void emitStartDoc(SaveCur c) {
      }

      protected void emitEndDoc(SaveCur c) {
      }

      private class SaverSAXException extends RuntimeException {
         SAXException _saxException;

         SaverSAXException(SAXException e) {
            this._saxException = e;
         }
      }
   }

   static final class XmlInputStreamImpl extends GenericXmlInputStream {
      private XmlInputStreamSaver _xmlInputStreamSaver;

      XmlInputStreamImpl(Cur cur, XmlOptions options) {
         this._xmlInputStreamSaver = new XmlInputStreamSaver(cur, options);
         this._xmlInputStreamSaver.process();
      }

      protected XMLEvent nextEvent() throws XMLStreamException {
         return this._xmlInputStreamSaver.dequeue();
      }
   }

   static final class XmlInputStreamSaver extends Saver {
      private XmlEventImpl _in;
      private XmlEventImpl _out;

      XmlInputStreamSaver(Cur c, XmlOptions options) {
         super(c, options);
      }

      protected boolean emitElement(SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         assert c.isElem();

         this.iterateMappings();

         while(this.hasMapping()) {
            this.enqueue(new StartPrefixMappingImpl(this.mappingPrefix(), this.mappingUri()));
            this.nextMapping();
         }

         StartElementImpl.AttributeImpl lastAttr = null;
         StartElementImpl.AttributeImpl attributes = null;
         StartElementImpl.AttributeImpl namespaces = null;

         for(int i = 0; i < attrNames.size(); ++i) {
            XMLName attXMLName = computeName((QName)attrNames.get(i), this, true);
            StartElementImpl.AttributeImpl attr = new StartElementImpl.NormalAttributeImpl(attXMLName, (String)attrValues.get(i));
            if (attributes == null) {
               attributes = attr;
            } else {
               lastAttr._next = attr;
            }

            lastAttr = attr;
         }

         StartElementImpl.AttributeImpl lastAttr = null;
         this.iterateMappings();

         while(this.hasMapping()) {
            String prefix = this.mappingPrefix();
            String uri = this.mappingUri();
            StartElementImpl.AttributeImpl attr = new StartElementImpl.XmlnsAttributeImpl(prefix, uri);
            if (namespaces == null) {
               namespaces = attr;
            } else {
               lastAttr._next = attr;
            }

            lastAttr = attr;
            this.nextMapping();
         }

         QName name = c.getName();
         this.enqueue(new StartElementImpl(computeName(name, this, false), attributes, namespaces, this.getPrefixMap()));
         return false;
      }

      protected void emitFinish(SaveCur c) {
         if (c.isRoot()) {
            this.enqueue(new EndDocumentImpl());
         } else {
            XMLName xmlName = computeName(c.getName(), this, false);
            this.enqueue(new EndElementImpl(xmlName));
         }

         this.emitEndPrefixMappings();
      }

      protected void emitText(SaveCur c) {
         assert c.isText();

         Object src = c.getChars();
         int cch = c._cchSrc;
         int off = c._offSrc;
         this.enqueue(new CharacterDataImpl(src, cch, off));
      }

      protected void emitComment(SaveCur c) {
         this.enqueue(new CommentImpl(c.getChars(), c._cchSrc, c._offSrc));
      }

      protected void emitProcinst(SaveCur c) {
         String target = null;
         QName name = c.getName();
         if (name != null) {
            target = name.getLocalPart();
         }

         this.enqueue(new ProcessingInstructionImpl(target, c.getChars(), c._cchSrc, c._offSrc));
      }

      protected void emitDocType(String doctypeName, String publicID, String systemID) {
         this.enqueue(new StartDocumentImpl(systemID, (String)null, true, (String)null));
      }

      protected void emitStartDoc(SaveCur c) {
         this.emitDocType((String)null, (String)null, (String)null);
      }

      protected void emitEndDoc(SaveCur c) {
         this.enqueue(new EndDocumentImpl());
      }

      XMLEvent dequeue() {
         XmlEventImpl e;
         if (this._out == null) {
            this.enterLocale();

            try {
               if (!this.process()) {
                  e = null;
                  return e;
               }
            } finally {
               this.exitLocale();
            }
         }

         if (this._out == null) {
            return null;
         } else {
            e = this._out;
            if ((this._out = this._out._next) == null) {
               this._in = null;
            }

            return e;
         }
      }

      private void enqueue(XmlEventImpl e) {
         assert e._next == null;

         if (this._in == null) {
            assert this._out == null;

            this._out = this._in = e;
         } else {
            this._in._next = e;
            this._in = e;
         }

      }

      protected void emitEndPrefixMappings() {
         this.iterateMappings();

         for(; this.hasMapping(); this.nextMapping()) {
            String prevPrefixUri = null;
            String prefix = this.mappingPrefix();
            String uri = this.mappingUri();
            if (prevPrefixUri == null) {
               this.enqueue(new EndPrefixMappingImpl(prefix));
            } else {
               this.enqueue(new ChangePrefixMappingImpl(prefix, uri, (String)prevPrefixUri));
            }
         }

      }

      private static XMLName computeName(QName name, Saver saver, boolean needsPrefix) {
         String uri = name.getNamespaceURI();
         String local = name.getLocalPart();

         assert uri != null;

         assert local.length() > 0;

         String prefix = null;
         if (uri != null && uri.length() != 0) {
            prefix = name.getPrefix();
            String mappedUri = saver.getNamespaceForPrefix(prefix);
            if (mappedUri == null || !mappedUri.equals(uri)) {
               prefix = saver.getUriMapping(uri);
            }

            if (needsPrefix && prefix.length() == 0) {
               prefix = saver.getNonDefaultUriMapping(uri);
            }
         }

         return new XmlNameImpl(uri, local, prefix);
      }

      private static class ProcessingInstructionImpl extends TripletEventImpl implements ProcessingInstruction {
         private String _target;

         ProcessingInstructionImpl(String target, Object obj, int cch, int off) {
            super(8, obj, cch, off);
            this._target = target;
         }

         public String getTarget() {
            return this._target;
         }

         public String getData() {
            return this.getContent();
         }
      }

      private static class CommentImpl extends TripletEventImpl implements Comment {
         CommentImpl(Object obj, int cch, int off) {
            super(32, obj, cch, off);
         }
      }

      private static class CharacterDataImpl extends TripletEventImpl implements CharacterData {
         CharacterDataImpl(Object obj, int cch, int off) {
            super(16, obj, cch, off);
         }
      }

      private static class TripletEventImpl extends XmlEventImpl implements CharacterData {
         private Object _obj;
         private int _cch;
         private int _off;

         TripletEventImpl(int eventType, Object obj, int cch, int off) {
            super(eventType);
            this._obj = obj;
            this._cch = cch;
            this._off = off;
         }

         public String getContent() {
            return CharUtil.getString(this._obj, this._off, this._cch);
         }

         public boolean hasContent() {
            return this._cch > 0;
         }
      }

      private static class EndDocumentImpl extends XmlEventImpl implements EndDocument {
         EndDocumentImpl() {
            super(512);
         }
      }

      private static class EndElementImpl extends XmlEventImpl implements EndElement {
         private XMLName _name;

         EndElementImpl(XMLName name) {
            super(4);
            this._name = name;
         }

         public boolean hasName() {
            return true;
         }

         public XMLName getName() {
            return this._name;
         }
      }

      private static class EndPrefixMappingImpl extends XmlEventImpl implements EndPrefixMapping {
         private String _prefix;

         EndPrefixMappingImpl(String prefix) {
            super(2048);
            this._prefix = prefix;
         }

         public String getPrefix() {
            return this._prefix;
         }
      }

      private static class ChangePrefixMappingImpl extends XmlEventImpl implements ChangePrefixMapping {
         private String _oldUri;
         private String _newUri;
         private String _prefix;

         ChangePrefixMappingImpl(String prefix, String oldUri, String newUri) {
            super(4096);
            this._oldUri = oldUri;
            this._newUri = newUri;
            this._prefix = prefix;
         }

         public String getOldNamespaceUri() {
            return this._oldUri;
         }

         public String getNewNamespaceUri() {
            return this._newUri;
         }

         public String getPrefix() {
            return this._prefix;
         }
      }

      private static class StartPrefixMappingImpl extends XmlEventImpl implements StartPrefixMapping {
         private String _prefix;
         private String _uri;

         StartPrefixMappingImpl(String prefix, String uri) {
            super(1024);
            this._prefix = prefix;
            this._uri = uri;
         }

         public String getNamespaceUri() {
            return this._uri;
         }

         public String getPrefix() {
            return this._prefix;
         }
      }

      private static class StartElementImpl extends XmlEventImpl implements StartElement {
         private XMLName _name;
         private Map _prefixMap;
         private AttributeImpl _attributes;
         private AttributeImpl _namespaces;

         StartElementImpl(XMLName name, AttributeImpl attributes, AttributeImpl namespaces, Map prefixMap) {
            super(2);
            this._name = name;
            this._attributes = attributes;
            this._namespaces = namespaces;
            this._prefixMap = prefixMap;
         }

         public boolean hasName() {
            return true;
         }

         public XMLName getName() {
            return this._name;
         }

         public AttributeIterator getAttributes() {
            return new AttributeIteratorImpl(this._attributes, (AttributeImpl)null);
         }

         public AttributeIterator getNamespaces() {
            return new AttributeIteratorImpl((AttributeImpl)null, this._namespaces);
         }

         public AttributeIterator getAttributesAndNamespaces() {
            return new AttributeIteratorImpl(this._attributes, this._namespaces);
         }

         public Attribute getAttributeByName(XMLName xmlName) {
            for(AttributeImpl a = this._attributes; a != null; a = a._next) {
               if (xmlName.equals(a.getName())) {
                  return a;
               }
            }

            return null;
         }

         public String getNamespaceUri(String prefix) {
            return (String)this._prefixMap.get(prefix == null ? "" : prefix);
         }

         public Map getNamespaceMap() {
            return this._prefixMap;
         }

         private static class NormalAttributeImpl extends AttributeImpl {
            private String _value;

            NormalAttributeImpl(XMLName name, String value) {
               this._name = name;
               this._value = value;
            }

            public String getValue() {
               return this._value;
            }
         }

         private static class XmlnsAttributeImpl extends AttributeImpl {
            private String _uri;

            XmlnsAttributeImpl(String prefix, String uri) {
               this._uri = uri;
               String local;
               if (prefix.length() == 0) {
                  prefix = null;
                  local = "xmlns";
               } else {
                  local = prefix;
                  prefix = "xmlns";
               }

               this._name = new XmlNameImpl((String)null, local, prefix);
            }

            public String getValue() {
               return this._uri;
            }
         }

         private abstract static class AttributeImpl implements Attribute {
            AttributeImpl _next;
            protected XMLName _name;

            AttributeImpl() {
            }

            public XMLName getName() {
               return this._name;
            }

            public String getType() {
               return "CDATA";
            }

            public XMLName getSchemaType() {
               return null;
            }
         }

         private static class AttributeIteratorImpl implements AttributeIterator {
            private AttributeImpl _attributes;
            private AttributeImpl _namespaces;

            AttributeIteratorImpl(AttributeImpl attributes, AttributeImpl namespaces) {
               this._attributes = attributes;
               this._namespaces = namespaces;
            }

            public Object monitor() {
               return this;
            }

            public Attribute next() {
               synchronized(this.monitor()) {
                  this.checkVersion();
                  AttributeImpl attr = null;
                  if (this._attributes != null) {
                     attr = this._attributes;
                     this._attributes = attr._next;
                  } else if (this._namespaces != null) {
                     attr = this._namespaces;
                     this._namespaces = attr._next;
                  }

                  return attr;
               }
            }

            public boolean hasNext() {
               synchronized(this.monitor()) {
                  this.checkVersion();
                  return this._attributes != null || this._namespaces != null;
               }
            }

            public Attribute peek() {
               synchronized(this.monitor()) {
                  this.checkVersion();
                  if (this._attributes != null) {
                     return this._attributes;
                  } else {
                     return this._namespaces != null ? this._namespaces : null;
                  }
               }
            }

            public void skip() {
               synchronized(this.monitor()) {
                  this.checkVersion();
                  if (this._attributes != null) {
                     this._attributes = this._attributes._next;
                  } else if (this._namespaces != null) {
                     this._namespaces = this._namespaces._next;
                  }

               }
            }

            private final void checkVersion() {
            }
         }
      }

      private static class StartDocumentImpl extends XmlEventImpl implements StartDocument {
         String _systemID;
         String _encoding;
         boolean _standAlone;
         String _version;

         StartDocumentImpl(String systemID, String encoding, boolean isStandAlone, String version) {
            super(256);
            this._systemID = systemID;
            this._encoding = encoding;
            this._standAlone = isStandAlone;
            this._version = version;
         }

         public String getSystemId() {
            return this._systemID;
         }

         public String getCharacterEncodingScheme() {
            return this._encoding;
         }

         public boolean isStandalone() {
            return this._standAlone;
         }

         public String getVersion() {
            return this._version;
         }
      }

      private abstract static class XmlEventImpl extends XmlEventBase {
         XmlEventImpl _next;

         XmlEventImpl(int type) {
            super(type);
         }

         public XMLName getName() {
            return null;
         }

         public XMLName getSchemaType() {
            throw new RuntimeException("NYI");
         }

         public boolean hasName() {
            return false;
         }

         public final Location getLocation() {
            return null;
         }
      }
   }

   static final class InputStreamSaver extends InputStream {
      private Locale _locale;
      private boolean _closed;
      private OutputStreamImpl _outStreamImpl;
      private TextSaver _textSaver;
      private OutputStreamWriter _converter;

      InputStreamSaver(Cur c, XmlOptions options) {
         this._locale = c._locale;
         this._closed = false;

         assert this._locale.entered();

         options = XmlOptions.maskNull(options);
         this._outStreamImpl = new OutputStreamImpl();
         String encoding = null;
         XmlDocumentProperties props = Locale.getDocProps(c, false);
         if (props != null && props.getEncoding() != null) {
            encoding = EncodingMap.getIANA2JavaMapping(props.getEncoding());
         }

         if (options.hasOption("CHARACTER_ENCODING")) {
            encoding = (String)options.get("CHARACTER_ENCODING");
         }

         String javaEncoding;
         if (encoding != null) {
            javaEncoding = EncodingMap.getJava2IANAMapping(encoding);
            if (javaEncoding != null) {
               encoding = javaEncoding;
            }
         }

         if (encoding == null) {
            encoding = EncodingMap.getJava2IANAMapping("UTF8");
         }

         javaEncoding = EncodingMap.getIANA2JavaMapping(encoding);
         if (javaEncoding == null) {
            throw new IllegalStateException("Unknown encoding: " + encoding);
         } else {
            try {
               this._converter = new OutputStreamWriter(this._outStreamImpl, javaEncoding);
            } catch (UnsupportedEncodingException var7) {
               throw new RuntimeException(var7);
            }

            this._textSaver = new TextSaver(c, options, encoding);
         }
      }

      public void close() throws IOException {
         this._closed = true;
      }

      private void checkClosed() throws IOException {
         if (this._closed) {
            throw new IOException("Stream closed");
         }
      }

      public int read() throws IOException {
         this.checkClosed();
         if (this._locale.noSync()) {
            this._locale.enter();

            int var1;
            try {
               var1 = this._outStreamImpl.read();
            } finally {
               this._locale.exit();
            }

            return var1;
         } else {
            synchronized(this._locale) {
               this._locale.enter();

               int var2;
               try {
                  var2 = this._outStreamImpl.read();
               } finally {
                  this._locale.exit();
               }

               return var2;
            }
         }
      }

      public int read(byte[] bbuf, int off, int len) throws IOException {
         this.checkClosed();
         if (bbuf == null) {
            throw new NullPointerException("buf to read into is null");
         } else if (off >= 0 && off <= bbuf.length) {
            if (this._locale.noSync()) {
               this._locale.enter();

               int var4;
               try {
                  var4 = this._outStreamImpl.read(bbuf, off, len);
               } finally {
                  this._locale.exit();
               }

               return var4;
            } else {
               synchronized(this._locale) {
                  this._locale.enter();

                  int var5;
                  try {
                     var5 = this._outStreamImpl.read(bbuf, off, len);
                  } finally {
                     this._locale.exit();
                  }

                  return var5;
               }
            }
         } else {
            throw new IndexOutOfBoundsException("Offset is not within buf");
         }
      }

      private int ensure(int cbyte) {
         if (cbyte <= 0) {
            cbyte = 1;
         }

         int bytesAvailable;
         for(bytesAvailable = this._outStreamImpl.getAvailable(); bytesAvailable < cbyte && this._textSaver.write(this._converter, 2048) >= 2048; bytesAvailable = this._outStreamImpl.getAvailable()) {
         }

         bytesAvailable = this._outStreamImpl.getAvailable();
         return bytesAvailable;
      }

      public int available() throws IOException {
         if (this._locale.noSync()) {
            this._locale.enter();

            int var1;
            try {
               var1 = this.ensure(1024);
            } finally {
               this._locale.exit();
            }

            return var1;
         } else {
            synchronized(this._locale) {
               this._locale.enter();

               int var2;
               try {
                  var2 = this.ensure(1024);
               } finally {
                  this._locale.exit();
               }

               return var2;
            }
         }
      }

      private final class OutputStreamImpl extends OutputStream {
         private static final int _initialBufSize = 4096;
         private int _free;
         private int _in;
         private int _out;
         private byte[] _buf;

         private OutputStreamImpl() {
         }

         int read() {
            if (InputStreamSaver.this.ensure(1) == 0) {
               return -1;
            } else {
               assert this.getAvailable() > 0;

               int bite = this._buf[this._out];
               this._out = (this._out + 1) % this._buf.length;
               ++this._free;
               return bite;
            }
         }

         int read(byte[] bbuf, int off, int len) {
            int n;
            if ((n = InputStreamSaver.this.ensure(len)) == 0) {
               return -1;
            } else if (bbuf != null && len > 0) {
               if (n < len) {
                  len = n;
               }

               if (this._out < this._in) {
                  Saver.arraycopy(13, this._buf, this._out, bbuf, off, len);
               } else {
                  int chunk = this._buf.length - this._out;
                  if (chunk >= len) {
                     Saver.arraycopy(14, this._buf, this._out, bbuf, off, len);
                  } else {
                     Saver.arraycopy(15, this._buf, this._out, bbuf, off, chunk);
                     Saver.arraycopy(16, this._buf, 0, bbuf, off + chunk, len - chunk);
                  }
               }

               this._out = (this._out + len) % this._buf.length;
               this._free += len;
               return len;
            } else {
               return 0;
            }
         }

         int getAvailable() {
            return this._buf == null ? 0 : this._buf.length - this._free;
         }

         public void write(int bite) {
            if (this._free == 0) {
               this.resize(1);
            }

            assert this._free > 0;

            this._buf[this._in] = (byte)bite;
            this._in = (this._in + 1) % this._buf.length;
            --this._free;
         }

         public void write(byte[] buf, int off, int cbyte) {
            assert cbyte >= 0;

            if (cbyte != 0) {
               if (this._free < cbyte) {
                  this.resize(cbyte);
               }

               if (this._in == this._out) {
                  assert this.getAvailable() == 0;

                  assert this._free == this._buf.length - this.getAvailable();

                  this._in = this._out = 0;
               }

               int chunk = this._buf.length - this._in;
               if (this._in > this._out && cbyte >= chunk) {
                  Saver.arraycopy(18, buf, off, this._buf, this._in, chunk);
                  Saver.arraycopy(19, buf, off + chunk, this._buf, 0, cbyte - chunk);
                  this._in = (this._in + cbyte) % this._buf.length;
               } else {
                  Saver.arraycopy(17, buf, off, this._buf, this._in, cbyte);
                  this._in += cbyte;
               }

               this._free -= cbyte;
            }
         }

         void resize(int cbyte) {
            assert cbyte > this._free : cbyte + " !> " + this._free;

            int newLen = this._buf == null ? 4096 : this._buf.length * 2;

            int used;
            for(used = this.getAvailable(); newLen - used < cbyte; newLen *= 2) {
            }

            byte[] newBuf = new byte[newLen];
            if (used > 0) {
               if (this._in > this._out) {
                  Saver.arraycopy(20, this._buf, this._out, newBuf, 0, used);
               } else {
                  Saver.arraycopy(21, this._buf, this._out, newBuf, 0, used - this._in);
                  Saver.arraycopy(22, this._buf, 0, newBuf, used - this._in, this._in);
               }

               this._out = 0;
               this._in = used;
               this._free += newBuf.length - this._buf.length;
            } else {
               this._free = newBuf.length;

               assert this._in == this._out;
            }

            this._buf = newBuf;
         }

         // $FF: synthetic method
         OutputStreamImpl(Object x1) {
            this();
         }
      }
   }

   static final class TextReader extends Reader {
      private Locale _locale;
      private TextSaver _textSaver;
      private boolean _closed;

      TextReader(Cur c, XmlOptions options) {
         this._textSaver = new TextSaver(c, options, (String)null);
         this._locale = c._locale;
         this._closed = false;
      }

      public void close() throws IOException {
         this._closed = true;
      }

      public boolean ready() throws IOException {
         return !this._closed;
      }

      public int read() throws IOException {
         this.checkClosed();
         if (this._locale.noSync()) {
            this._locale.enter();

            int var1;
            try {
               var1 = this._textSaver.read();
            } finally {
               this._locale.exit();
            }

            return var1;
         } else {
            synchronized(this._locale) {
               this._locale.enter();

               int var2;
               try {
                  var2 = this._textSaver.read();
               } finally {
                  this._locale.exit();
               }

               return var2;
            }
         }
      }

      public int read(char[] cbuf) throws IOException {
         this.checkClosed();
         if (this._locale.noSync()) {
            this._locale.enter();

            int var2;
            try {
               var2 = this._textSaver.read(cbuf, 0, cbuf == null ? 0 : cbuf.length);
            } finally {
               this._locale.exit();
            }

            return var2;
         } else {
            synchronized(this._locale) {
               this._locale.enter();

               int var3;
               try {
                  var3 = this._textSaver.read(cbuf, 0, cbuf == null ? 0 : cbuf.length);
               } finally {
                  this._locale.exit();
               }

               return var3;
            }
         }
      }

      public int read(char[] cbuf, int off, int len) throws IOException {
         this.checkClosed();
         if (this._locale.noSync()) {
            this._locale.enter();

            int var4;
            try {
               var4 = this._textSaver.read(cbuf, off, len);
            } finally {
               this._locale.exit();
            }

            return var4;
         } else {
            synchronized(this._locale) {
               this._locale.enter();

               int var5;
               try {
                  var5 = this._textSaver.read(cbuf, off, len);
               } finally {
                  this._locale.exit();
               }

               return var5;
            }
         }
      }

      private void checkClosed() throws IOException {
         if (this._closed) {
            throw new IOException("Reader has been closed");
         }
      }
   }

   static final class OptimizedForSpeedSaver extends Saver {
      Writer _w;
      private char[] _buf = new char[1024];

      OptimizedForSpeedSaver(Cur cur, Writer writer) {
         super(cur, XmlOptions.maskNull((XmlOptions)null));
         this._w = writer;
      }

      static void save(Cur cur, Writer writer) throws IOException {
         try {
            Saver saver = new OptimizedForSpeedSaver(cur, writer);

            while(saver.process()) {
            }

         } catch (SaverIOException var3) {
            throw (IOException)var3.getCause();
         }
      }

      private void emit(String s) {
         try {
            this._w.write(s);
         } catch (IOException var3) {
            throw new SaverIOException(var3);
         }
      }

      private void emit(char c) {
         try {
            this._buf[0] = c;
            this._w.write(this._buf, 0, 1);
         } catch (IOException var3) {
            throw new SaverIOException(var3);
         }
      }

      private void emit(char c1, char c2) {
         try {
            this._buf[0] = c1;
            this._buf[1] = c2;
            this._w.write(this._buf, 0, 2);
         } catch (IOException var4) {
            throw new SaverIOException(var4);
         }
      }

      private void emit(char[] buf, int start, int len) {
         try {
            this._w.write(buf, start, len);
         } catch (IOException var5) {
            throw new SaverIOException(var5);
         }
      }

      protected boolean emitElement(SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         assert c.isElem();

         this.emit('<');
         this.emitName(c.getName(), false);

         for(int i = 0; i < attrNames.size(); ++i) {
            this.emitAttrHelper((QName)attrNames.get(i), (String)attrValues.get(i));
         }

         if (!this.saveNamespacesFirst()) {
            this.emitNamespacesHelper();
         }

         if (!c.hasChildren() && !c.hasText()) {
            this.emit('/', '>');
            return true;
         } else {
            this.emit('>');
            return false;
         }
      }

      protected void emitFinish(SaveCur c) {
         this.emit('<', '/');
         this.emitName(c.getName(), false);
         this.emit('>');
      }

      protected void emitXmlns(String prefix, String uri) {
         assert prefix != null;

         assert uri != null;

         this.emit("xmlns");
         if (prefix.length() > 0) {
            this.emit(':');
            this.emit(prefix);
         }

         this.emit('=', '"');
         this.emitAttrValue(uri);
         this.emit('"');
      }

      private void emitNamespacesHelper() {
         this.iterateMappings();

         while(this.hasMapping()) {
            this.emit(' ');
            this.emitXmlns(this.mappingPrefix(), this.mappingUri());
            this.nextMapping();
         }

      }

      private void emitAttrHelper(QName attrName, String attrValue) {
         this.emit(' ');
         this.emitName(attrName, true);
         this.emit('=', '"');
         this.emitAttrValue(attrValue);
         this.emit('"');
      }

      protected void emitComment(SaveCur c) {
         assert c.isComment();

         this.emit("<!--");
         c.push();
         c.next();
         this.emitCommentText(c);
         c.pop();
         this.emit("-->");
      }

      protected void emitProcinst(SaveCur c) {
         assert c.isProcinst();

         this.emit("<?");
         this.emit(c.getName().getLocalPart());
         c.push();
         c.next();
         if (c.isText()) {
            this.emit(' ');
            this.emitPiText(c);
         }

         c.pop();
         this.emit("?>");
      }

      protected void emitDocType(String docTypeName, String publicId, String systemId) {
         assert docTypeName != null;

         this.emit("<!DOCTYPE ");
         this.emit(docTypeName);
         if (publicId == null && systemId != null) {
            this.emit(" SYSTEM ");
            this.emitLiteral(systemId);
         } else if (publicId != null) {
            this.emit(" PUBLIC ");
            this.emitLiteral(publicId);
            this.emit(' ');
            this.emitLiteral(systemId);
         }

         this.emit('>');
         this.emit(_newLine);
      }

      protected void emitStartDoc(SaveCur c) {
      }

      protected void emitEndDoc(SaveCur c) {
      }

      private void emitName(QName name, boolean needsPrefix) {
         assert name != null;

         String uri = name.getNamespaceURI();

         assert uri != null;

         if (uri.length() != 0) {
            String prefix = name.getPrefix();
            String mappedUri = this.getNamespaceForPrefix(prefix);
            if (mappedUri == null || !mappedUri.equals(uri)) {
               prefix = this.getUriMapping(uri);
            }

            if (needsPrefix && prefix.length() == 0) {
               prefix = this.getNonDefaultUriMapping(uri);
            }

            if (prefix.length() > 0) {
               this.emit(prefix);
               this.emit(':');
            }
         }

         assert name.getLocalPart().length() > 0;

         this.emit(name.getLocalPart());
      }

      private void emitAttrValue(CharSequence attVal) {
         int len = attVal.length();

         for(int i = 0; i < len; ++i) {
            char ch = attVal.charAt(i);
            if (ch == '<') {
               this.emit("&lt;");
            } else if (ch == '&') {
               this.emit("&amp;");
            } else if (ch == '"') {
               this.emit("&quot;");
            } else {
               this.emit(ch);
            }
         }

      }

      private boolean isBadChar(char ch) {
         return (ch < ' ' || ch > '\ud7ff') && (ch < '\ud800' || ch > '\udfff') && (ch < '\ue000' || ch > '�') && (ch < 65536 || ch > 1114111) && ch != '\t' && ch != '\n' && ch != '\r';
      }

      private void emitLiteral(String literal) {
         if (literal.indexOf("\"") < 0) {
            this.emit('"');
            this.emit(literal);
            this.emit('"');
         } else {
            this.emit('\'');
            this.emit(literal);
            this.emit('\'');
         }

      }

      protected void emitText(SaveCur c) {
         assert c.isText();

         Object src = c.getChars();
         int cch = c._cchSrc;
         int off = c._offSrc;
         int index = 0;

         int indexLimit;
         for(int indexLimit = false; index < cch; index = indexLimit) {
            indexLimit = index + 512 > cch ? cch : index + 512;
            CharUtil.getChars(this._buf, 0, src, off + index, indexLimit - index);
            this.entitizeAndWriteText(indexLimit - index);
         }

      }

      protected void emitPiText(SaveCur c) {
         assert c.isText();

         Object src = c.getChars();
         int cch = c._cchSrc;
         int off = c._offSrc;
         int index = 0;

         int indexLimit;
         for(int indexLimit = false; index < cch; index = indexLimit) {
            indexLimit = index + 512 > cch ? cch : 512;
            CharUtil.getChars(this._buf, 0, src, off + index, indexLimit);
            this.entitizeAndWritePIText(indexLimit - index);
         }

      }

      protected void emitCommentText(SaveCur c) {
         assert c.isText();

         Object src = c.getChars();
         int cch = c._cchSrc;
         int off = c._offSrc;
         int index = 0;

         int indexLimit;
         for(int indexLimit = false; index < cch; index = indexLimit) {
            indexLimit = index + 512 > cch ? cch : 512;
            CharUtil.getChars(this._buf, 0, src, off + index, indexLimit);
            this.entitizeAndWriteCommentText(indexLimit - index);
         }

      }

      private void entitizeAndWriteText(int bufLimit) {
         int index = 0;

         for(int i = 0; i < bufLimit; ++i) {
            char c = this._buf[i];
            switch (c) {
               case '&':
                  this.emit(this._buf, index, i - index);
                  this.emit("&amp;");
                  index = i + 1;
                  break;
               case '<':
                  this.emit(this._buf, index, i - index);
                  this.emit("&lt;");
                  index = i + 1;
            }
         }

         this.emit(this._buf, index, bufLimit - index);
      }

      private void entitizeAndWriteCommentText(int bufLimit) {
         boolean lastWasDash = false;

         for(int i = 0; i < bufLimit; ++i) {
            char ch = this._buf[i];
            if (this.isBadChar(ch)) {
               this._buf[i] = '?';
            } else if (ch == '-') {
               if (lastWasDash) {
                  this._buf[i] = ' ';
                  lastWasDash = false;
               } else {
                  lastWasDash = true;
               }
            } else {
               lastWasDash = false;
            }

            if (i == this._buf.length) {
               i = 0;
            }
         }

         if (this._buf[bufLimit - 1] == '-') {
            this._buf[bufLimit - 1] = ' ';
         }

         this.emit(this._buf, 0, bufLimit);
      }

      private void entitizeAndWritePIText(int bufLimit) {
         boolean lastWasQuestion = false;

         for(int i = 0; i < bufLimit; ++i) {
            char ch = this._buf[i];
            if (this.isBadChar(ch)) {
               this._buf[i] = '?';
               ch = '?';
            }

            if (ch == '>') {
               if (lastWasQuestion) {
                  this._buf[i] = ' ';
               }

               lastWasQuestion = false;
            } else {
               lastWasQuestion = ch == '?';
            }
         }

         this.emit(this._buf, 0, bufLimit);
      }

      private static class SaverIOException extends RuntimeException {
         SaverIOException(IOException e) {
            super(e);
         }
      }
   }

   static final class TextSaver extends Saver {
      private static final int _initialBufSize = 4096;
      private int _cdataLengthThreshold = 32;
      private int _cdataEntityCountThreshold = 5;
      private boolean _useCDataBookmarks = false;
      private boolean _isPrettyPrint = false;
      private int _lastEmitIn;
      private int _lastEmitCch;
      private int _free;
      private int _in;
      private int _out;
      private char[] _buf;

      TextSaver(Cur c, XmlOptions options, String encoding) {
         super(c, options);
         boolean noSaveDecl = options != null && options.hasOption("SAVE_NO_XML_DECL");
         if (options != null && options.hasOption("SAVE_CDATA_LENGTH_THRESHOLD")) {
            this._cdataLengthThreshold = (Integer)options.get("SAVE_CDATA_LENGTH_THRESHOLD");
         }

         if (options != null && options.hasOption("SAVE_CDATA_ENTITY_COUNT_THRESHOLD")) {
            this._cdataEntityCountThreshold = (Integer)options.get("SAVE_CDATA_ENTITY_COUNT_THRESHOLD");
         }

         if (options != null && options.hasOption("LOAD_SAVE_CDATA_BOOKMARKS")) {
            this._useCDataBookmarks = true;
         }

         if (options != null && options.hasOption("SAVE_PRETTY_PRINT")) {
            this._isPrettyPrint = true;
         }

         this._in = this._out = 0;
         this._free = 0;

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         if (encoding != null && !noSaveDecl) {
            XmlDocumentProperties props = Locale.getDocProps(c, false);
            String version = props == null ? null : props.getVersion();
            if (version == null) {
               version = "1.0";
            }

            this.emit("<?xml version=\"");
            this.emit(version);
            this.emit("\" encoding=\"" + encoding + "\"?>" + _newLine);
         }

      }

      protected boolean emitElement(SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         assert c.isElem();

         this.emit('<');
         this.emitName(c.getName(), false);
         if (this.saveNamespacesFirst()) {
            this.emitNamespacesHelper();
         }

         for(int i = 0; i < attrNames.size(); ++i) {
            this.emitAttrHelper((QName)attrNames.get(i), (String)attrValues.get(i));
         }

         if (!this.saveNamespacesFirst()) {
            this.emitNamespacesHelper();
         }

         if (!c.hasChildren() && !c.hasText()) {
            this.emit('/', '>');
            return true;
         } else {
            this.emit('>');
            return false;
         }
      }

      protected void emitFinish(SaveCur c) {
         this.emit('<', '/');
         this.emitName(c.getName(), false);
         this.emit('>');
      }

      protected void emitXmlns(String prefix, String uri) {
         assert prefix != null;

         assert uri != null;

         this.emit("xmlns");
         if (prefix.length() > 0) {
            this.emit(':');
            this.emit(prefix);
         }

         this.emit('=', '"');
         this.emit(uri);
         this.entitizeAttrValue(false);
         this.emit('"');
      }

      private void emitNamespacesHelper() {
         this.iterateMappings();

         while(this.hasMapping()) {
            this.emit(' ');
            this.emitXmlns(this.mappingPrefix(), this.mappingUri());
            this.nextMapping();
         }

      }

      private void emitAttrHelper(QName attrName, String attrValue) {
         this.emit(' ');
         this.emitName(attrName, true);
         this.emit('=', '"');
         this.emit(attrValue);
         this.entitizeAttrValue(true);
         this.emit('"');
      }

      protected void emitText(SaveCur c) {
         assert c.isText();

         boolean forceCData = this._useCDataBookmarks && c.isTextCData();
         this.emit(c);
         this.entitizeContent(forceCData);
      }

      protected void emitComment(SaveCur c) {
         assert c.isComment();

         this.emit("<!--");
         c.push();
         c.next();
         this.emit(c);
         c.pop();
         this.entitizeComment();
         this.emit("-->");
      }

      protected void emitProcinst(SaveCur c) {
         assert c.isProcinst();

         this.emit("<?");
         this.emit(c.getName().getLocalPart());
         c.push();
         c.next();
         if (c.isText()) {
            this.emit(" ");
            this.emit(c);
            this.entitizeProcinst();
         }

         c.pop();
         this.emit("?>");
      }

      private void emitLiteral(String literal) {
         if (literal.indexOf("\"") < 0) {
            this.emit('"');
            this.emit(literal);
            this.emit('"');
         } else {
            this.emit('\'');
            this.emit(literal);
            this.emit('\'');
         }

      }

      protected void emitDocType(String docTypeName, String publicId, String systemId) {
         assert docTypeName != null;

         this.emit("<!DOCTYPE ");
         this.emit(docTypeName);
         if (publicId == null && systemId != null) {
            this.emit(" SYSTEM ");
            this.emitLiteral(systemId);
         } else if (publicId != null) {
            this.emit(" PUBLIC ");
            this.emitLiteral(publicId);
            this.emit(" ");
            this.emitLiteral(systemId);
         }

         this.emit(">");
         this.emit(_newLine);
      }

      protected void emitStartDoc(SaveCur c) {
      }

      protected void emitEndDoc(SaveCur c) {
      }

      private void emitName(QName name, boolean needsPrefix) {
         assert name != null;

         String uri = name.getNamespaceURI();

         assert uri != null;

         if (uri.length() != 0) {
            String prefix = name.getPrefix();
            String mappedUri = this.getNamespaceForPrefix(prefix);
            if (mappedUri == null || !mappedUri.equals(uri)) {
               prefix = this.getUriMapping(uri);
            }

            if (needsPrefix && prefix.length() == 0) {
               prefix = this.getNonDefaultUriMapping(uri);
            }

            if (prefix.length() > 0) {
               this.emit(prefix);
               this.emit(':');
            }
         }

         assert name.getLocalPart().length() > 0;

         this.emit(name.getLocalPart());
      }

      private void emit(char ch) {
         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         this.preEmit(1);
         this._buf[this._in] = ch;
         this._in = (this._in + 1) % this._buf.length;

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;
      }

      private void emit(char ch1, char ch2) {
         if (!this.preEmit(2)) {
            this._buf[this._in] = ch1;
            this._in = (this._in + 1) % this._buf.length;
            this._buf[this._in] = ch2;
            this._in = (this._in + 1) % this._buf.length;

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;
         }
      }

      private void emit(String s) {
         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         int cch = s == null ? 0 : s.length();
         if (!this.preEmit(cch)) {
            int chunk;
            if (this._in > this._out && cch >= (chunk = this._buf.length - this._in)) {
               s.getChars(0, chunk, this._buf, this._in);
               s.getChars(chunk, cch, this._buf, 0);
               this._in = (this._in + cch) % this._buf.length;
            } else {
               s.getChars(0, cch, this._buf, this._in);
               this._in += cch;
            }

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         }
      }

      private void emit(SaveCur c) {
         if (c.isText()) {
            Object src = c.getChars();
            int cch = c._cchSrc;
            if (this.preEmit(cch)) {
               return;
            }

            int chunk;
            if (this._in > this._out && cch >= (chunk = this._buf.length - this._in)) {
               CharUtil.getChars(this._buf, this._in, src, c._offSrc, chunk);
               CharUtil.getChars(this._buf, 0, src, c._offSrc + chunk, cch - chunk);
               this._in = (this._in + cch) % this._buf.length;
            } else {
               CharUtil.getChars(this._buf, this._in, src, c._offSrc, cch);
               this._in += cch;
            }
         } else {
            this.preEmit(0);
         }

      }

      private boolean preEmit(int cch) {
         assert cch >= 0;

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         this._lastEmitCch = cch;
         if (cch == 0) {
            return true;
         } else {
            if (this._free <= cch) {
               this.resize(cch, -1);
            }

            assert cch <= this._free;

            int used = this.getAvailable();
            if (used == 0) {
               assert this._in == this._out;

               assert this._free == this._buf.length;

               this._in = this._out = 0;
            }

            this._lastEmitIn = this._in;
            this._free -= cch;

            assert this._free >= 0;

            assert this._buf == null || this._free == (this._in >= this._out ? this._buf.length - (this._in - this._out) : this._out - this._in) - cch : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) - cch || this._out > this._in && this._free == this._out - this._in - cch || this._out == this._in && this._free == this._buf.length - cch || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

            return false;
         }
      }

      private void entitizeContent(boolean forceCData) {
         assert this._free >= 0;

         if (this._lastEmitCch != 0) {
            int i = this._lastEmitIn;
            int n = this._buf.length;
            boolean hasCharToBeReplaced = false;
            int count = 0;
            char prevChar = 0;
            char prevPrevChar = 0;

            char ch_1;
            for(int cch = this._lastEmitCch; cch > 0; --cch) {
               ch_1 = this._buf[i];
               if (ch_1 != '<' && ch_1 != '&') {
                  if (prevPrevChar == ']' && prevChar == ']' && ch_1 == '>') {
                     hasCharToBeReplaced = true;
                  } else if (this.isBadChar(ch_1) || this.isEscapedChar(ch_1) || !this._isPrettyPrint && ch_1 == '\r') {
                     hasCharToBeReplaced = true;
                  }
               } else {
                  ++count;
               }

               ++i;
               if (i == n) {
                  i = 0;
               }

               prevPrevChar = prevChar;
               prevChar = ch_1;
            }

            if (forceCData || count != 0 || hasCharToBeReplaced || count >= this._cdataEntityCountThreshold) {
               i = this._lastEmitIn;
               int ch;
               if (!forceCData && (this._lastEmitCch <= this._cdataLengthThreshold || count <= this._cdataEntityCountThreshold)) {
                  char ch = 0;
                  ch_1 = 0;

                  for(ch = this._lastEmitCch; ch > 0; --ch) {
                     char ch_2 = ch_1;
                     ch_1 = ch;
                     ch = this._buf[i];
                     if (ch == '<') {
                        i = this.replace(i, "&lt;");
                     } else if (ch == '&') {
                        i = this.replace(i, "&amp;");
                     } else if (ch == '>' && ch_1 == ']' && ch_2 == ']') {
                        i = this.replace(i, "&gt;");
                     } else if (this.isBadChar(ch)) {
                        i = this.replace(i, "?");
                     } else if (!this._isPrettyPrint && ch == '\r') {
                        i = this.replace(i, "&#13;");
                     } else if (this.isEscapedChar(ch)) {
                        i = this.replace(i, this._replaceChar.getEscapedString(ch));
                     } else {
                        ++i;
                     }

                     if (i == this._buf.length) {
                        i = 0;
                     }
                  }
               } else {
                  boolean lastWasBracket = this._buf[i] == ']';
                  i = this.replace(i, "<![CDATA[" + this._buf[i]);
                  boolean secondToLastWasBracket = lastWasBracket;
                  lastWasBracket = this._buf[i] == ']';
                  ++i;
                  if (i == this._buf.length) {
                     i = 0;
                  }

                  for(int cch = this._lastEmitCch; cch > 0; --cch) {
                     ch = this._buf[i];
                     if (ch == 62 && secondToLastWasBracket && lastWasBracket) {
                        i = this.replace(i, "]]>><![CDATA[");
                     } else if (this.isBadChar((char)ch)) {
                        i = this.replace(i, "?");
                     } else {
                        ++i;
                     }

                     secondToLastWasBracket = lastWasBracket;
                     lastWasBracket = ch == 93;
                     if (i == this._buf.length) {
                        i = 0;
                     }
                  }

                  this.emit("]]>");
               }

            }
         }
      }

      private void entitizeAttrValue(boolean replaceEscapedChar) {
         if (this._lastEmitCch != 0) {
            int i = this._lastEmitIn;

            for(int cch = this._lastEmitCch; cch > 0; --cch) {
               char ch = this._buf[i];
               if (ch == '<') {
                  i = this.replace(i, "&lt;");
               } else if (ch == '&') {
                  i = this.replace(i, "&amp;");
               } else if (ch == '"') {
                  i = this.replace(i, "&quot;");
               } else if (this.isEscapedChar(ch)) {
                  if (replaceEscapedChar) {
                     i = this.replace(i, this._replaceChar.getEscapedString(ch));
                  }
               } else {
                  ++i;
               }

               if (i == this._buf.length) {
                  i = 0;
               }
            }

         }
      }

      private void entitizeComment() {
         if (this._lastEmitCch != 0) {
            int i = this._lastEmitIn;
            boolean lastWasDash = false;

            int offset;
            for(offset = this._lastEmitCch; offset > 0; --offset) {
               char ch = this._buf[i];
               if (this.isBadChar(ch)) {
                  i = this.replace(i, "?");
               } else if (ch == '-') {
                  if (lastWasDash) {
                     i = this.replace(i, " ");
                     lastWasDash = false;
                  } else {
                     lastWasDash = true;
                     ++i;
                  }
               } else {
                  lastWasDash = false;
                  ++i;
               }

               if (i == this._buf.length) {
                  i = 0;
               }
            }

            offset = (this._lastEmitIn + this._lastEmitCch - 1) % this._buf.length;
            if (this._buf[offset] == '-') {
               this.replace(offset, " ");
            }

         }
      }

      private void entitizeProcinst() {
         if (this._lastEmitCch != 0) {
            int i = this._lastEmitIn;
            boolean lastWasQuestion = false;

            for(int cch = this._lastEmitCch; cch > 0; --cch) {
               char ch = this._buf[i];
               if (this.isBadChar(ch)) {
                  i = this.replace(i, "?");
               }

               if (ch == '>') {
                  if (lastWasQuestion) {
                     i = this.replace(i, " ");
                  } else {
                     ++i;
                  }

                  lastWasQuestion = false;
               } else {
                  lastWasQuestion = ch == '?';
                  ++i;
               }

               if (i == this._buf.length) {
                  i = 0;
               }
            }

         }
      }

      private boolean isBadChar(char ch) {
         return (ch < ' ' || ch > '\ud7ff') && (ch < '\ud800' || ch > '\udfff') && (ch < '\ue000' || ch > '�') && (ch < 65536 || ch > 1114111) && ch != '\t' && ch != '\n' && ch != '\r';
      }

      private boolean isEscapedChar(char ch) {
         return null != this._replaceChar && this._replaceChar.containsChar(ch);
      }

      private int replace(int i, String replacement) {
         assert replacement.length() > 0;

         int dCch = replacement.length() - 1;
         if (dCch == 0) {
            this._buf[i] = replacement.charAt(0);
            return i + 1;
         } else {
            assert this._free >= 0;

            if (dCch > this._free) {
               i = this.resize(dCch, i);
            }

            assert this._free >= 0;

            assert this._free >= dCch;

            assert this.getAvailable() > 0;

            int charsToCopy = dCch + 1;
            if (this._out > this._in && i >= this._out) {
               arraycopy(1, this._buf, this._out, this._buf, this._out - dCch, i - this._out);
               this._out -= dCch;
               i -= dCch;
            } else {
               assert i < this._in;

               if (i >= this._in) {
               }

               int availableEndChunk = this._buf.length - this._in;
               if (dCch <= availableEndChunk) {
                  arraycopy(2, this._buf, i, this._buf, i + dCch, this._in - i);
                  this._in = (this._in + dCch) % this._buf.length;
               } else {
                  int numToCopyToStart;
                  if (dCch <= availableEndChunk + this._in - i - 1) {
                     numToCopyToStart = dCch - availableEndChunk;
                     arraycopy(3, this._buf, this._in - numToCopyToStart, this._buf, 0, numToCopyToStart);
                     arraycopy(4, this._buf, i + 1, this._buf, i + 1 + dCch, this._in - i - 1 - numToCopyToStart);
                     this._in = numToCopyToStart;
                  } else {
                     numToCopyToStart = this._in - i - 1;
                     charsToCopy = availableEndChunk + this._in - i;
                     arraycopy(5, this._buf, this._in - numToCopyToStart, this._buf, dCch - charsToCopy + 1, numToCopyToStart);
                     replacement.getChars(charsToCopy, dCch + 1, this._buf, 0);
                     this._in = numToCopyToStart + dCch - charsToCopy + 1;
                  }
               }
            }

            replacement.getChars(0, charsToCopy, this._buf, i);
            this._free -= dCch;

            assert this._free >= 0;

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

            return (i + dCch + 1) % this._buf.length;
         }
      }

      private int ensure(int cch) {
         if (cch <= 0) {
            cch = 1;
         }

         int available;
         for(available = this.getAvailable(); available < cch && this.process(); available = this.getAvailable()) {
         }

         assert available == this.getAvailable();

         return available;
      }

      int getAvailable() {
         return this._buf == null ? 0 : this._buf.length - this._free;
      }

      private int resize(int cch, int i) {
         assert this._free >= 0;

         assert cch > 0;

         assert cch >= this._free;

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         int newLen = this._buf == null ? 4096 : this._buf.length * 2;

         int used;
         for(used = this.getAvailable(); newLen - used < cch; newLen *= 2) {
         }

         char[] newBuf = new char[newLen];
         if (used > 0) {
            if (this._in > this._out) {
               assert i == -1 || i >= this._out && i < this._in;

               arraycopy(6, this._buf, this._out, newBuf, 0, used);
               i -= this._out;
            } else {
               assert i == -1 || i >= this._out || i < this._in;

               arraycopy(7, this._buf, this._out, newBuf, 0, used - this._in);
               arraycopy(8, this._buf, 0, newBuf, used - this._in, this._in);
               if (i >= this._out) {
                  i -= this._out;
               } else {
                  i = i + used - this._in;
               }
            }

            this._out = 0;
            this._in = used;
            this._free += newBuf.length - this._buf.length;
         } else {
            this._free = newBuf.length;

            assert this._in == 0 && this._out == 0;

            assert i == -1;
         }

         this._buf = newBuf;

         assert this._free >= 0;

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         return i;
      }

      public int read() {
         if (this.ensure(1) == 0) {
            return -1;
         } else {
            assert this.getAvailable() > 0;

            int ch = this._buf[this._out];
            this._out = (this._out + 1) % this._buf.length;
            ++this._free;

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

            return ch;
         }
      }

      public int read(char[] cbuf, int off, int len) {
         int n;
         if ((n = this.ensure(len)) == 0) {
            return -1;
         } else if (cbuf != null && len > 0) {
            if (n < len) {
               len = n;
            }

            if (this._out < this._in) {
               arraycopy(9, this._buf, this._out, cbuf, off, len);
            } else {
               int chunk = this._buf.length - this._out;
               if (chunk >= len) {
                  arraycopy(10, this._buf, this._out, cbuf, off, len);
               } else {
                  arraycopy(11, this._buf, this._out, cbuf, off, chunk);
                  arraycopy(12, this._buf, 0, cbuf, off + chunk, len - chunk);
               }
            }

            this._out = (this._out + len) % this._buf.length;
            this._free += len;

            assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

            assert this._free >= 0;

            return len;
         } else {
            return 0;
         }
      }

      public int write(Writer writer, int cchMin) {
         while(this.getAvailable() < cchMin && this.process()) {
         }

         int charsAvailable = this.getAvailable();
         if (charsAvailable > 0) {
            assert this._out == 0;

            assert this._in >= this._out : "_in:" + this._in + " < _out:" + this._out;

            assert this._free == this._buf.length - this._in;

            try {
               writer.write(this._buf, 0, charsAvailable);
               writer.flush();
            } catch (IOException var5) {
               throw new RuntimeException(var5);
            }

            this._free += charsAvailable;

            assert this._free >= 0;

            this._in = 0;
         }

         assert this._buf == null || this._out < this._in && this._free == this._buf.length - (this._in - this._out) || this._out > this._in && this._free == this._out - this._in || this._out == this._in && this._free == this._buf.length || this._out == this._in && this._free == 0 : "_buf.length:" + this._buf.length + " _in:" + this._in + " _out:" + this._out + " _free:" + this._free;

         return charsAvailable;
      }

      public String saveToString() {
         while(this.process()) {
         }

         assert this._out == 0;

         int available = this.getAvailable();
         return available == 0 ? "" : new String(this._buf, this._out, available);
      }
   }

   static final class SynthNamespaceSaver extends Saver {
      LinkedHashMap _synthNamespaces = new LinkedHashMap();

      SynthNamespaceSaver(Cur c, XmlOptions options) {
         super(c, options);
      }

      protected void syntheticNamespace(String prefix, String uri, boolean considerCreatingDefault) {
         this._synthNamespaces.put(uri, considerCreatingDefault ? "" : prefix);
      }

      protected boolean emitElement(SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         return false;
      }

      protected void emitFinish(SaveCur c) {
      }

      protected void emitText(SaveCur c) {
      }

      protected void emitComment(SaveCur c) {
      }

      protected void emitProcinst(SaveCur c) {
      }

      protected void emitDocType(String docTypeName, String publicId, String systemId) {
      }

      protected void emitStartDoc(SaveCur c) {
      }

      protected void emitEndDoc(SaveCur c) {
      }
   }
}
