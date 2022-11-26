package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.lib.xml.DocTypeReader;
import org.apache.openjpa.lib.xml.Location;
import org.apache.openjpa.lib.xml.XMLFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public abstract class XMLMetaDataParser extends DefaultHandler implements LexicalHandler, MetaDataParser {
   private static final Localizer _loc = Localizer.forPackage(XMLMetaDataParser.class);
   private static boolean _schemaBug;
   private Map _parsed = null;
   private Log _log = null;
   private boolean _validating = true;
   private boolean _systemId = true;
   private boolean _caching = true;
   private boolean _parseText = true;
   private boolean _parseComments = true;
   private String _suffix = null;
   private ClassLoader _loader = null;
   private ClassLoader _curLoader = null;
   private final Collection _curResults = new LinkedList();
   private List _results = null;
   private String _sourceName = null;
   private File _sourceFile = null;
   private StringBuffer _text = null;
   private List _comments = null;
   private Location _location = new Location();
   private LexicalHandler _lh = null;
   private int _depth = -1;
   private int _ignore = Integer.MAX_VALUE;

   public boolean getParseText() {
      return this._parseText;
   }

   public void setParseText(boolean text) {
      this._parseText = text;
   }

   public boolean getParseComments() {
      return this._parseComments;
   }

   public void setParseComments(boolean comments) {
      this._parseComments = comments;
   }

   public Location getLocation() {
      return this._location;
   }

   public LexicalHandler getLexicalHandler() {
      return this._lh;
   }

   public void setLexicalHandler(LexicalHandler lh) {
      this._lh = lh;
   }

   public void setLocation(Location location) {
      this._location = location;
   }

   public boolean getSourceIsSystemId() {
      return this._systemId;
   }

   public void setSourceIsSystemId(boolean systemId) {
      this._systemId = systemId;
   }

   public boolean isValidating() {
      return this._validating;
   }

   public void setValidating(boolean validating) {
      this._validating = validating;
   }

   public String getSuffix() {
      return this._suffix;
   }

   public void setSuffix(String suffix) {
      this._suffix = suffix;
   }

   public boolean isCaching() {
      return this._caching;
   }

   public void setCaching(boolean caching) {
      this._caching = caching;
      if (!caching) {
         this.clear();
      }

   }

   public Log getLog() {
      return this._log;
   }

   public void setLog(Log log) {
      this._log = log;
   }

   public ClassLoader getClassLoader() {
      return this._loader;
   }

   public void setClassLoader(ClassLoader loader) {
      this._loader = loader;
   }

   public List getResults() {
      return this._results == null ? Collections.EMPTY_LIST : this._results;
   }

   public void parse(String rsrc) throws IOException {
      if (rsrc != null) {
         this.parse((MetaDataIterator)(new ResourceMetaDataIterator(rsrc, this._loader)));
      }

   }

   public void parse(URL url) throws IOException {
      if (url != null) {
         this.parse((MetaDataIterator)(new URLMetaDataIterator(url)));
      }

   }

   public void parse(File file) throws IOException {
      if (file != null) {
         if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.isDirectoryAction(file))) {
            this.parse((MetaDataIterator)(new FileMetaDataIterator(file)));
         } else {
            String suff = this._suffix == null ? "" : this._suffix;
            this.parse((MetaDataIterator)(new FileMetaDataIterator(file, new SuffixMetaDataFilter(suff))));
         }

      }
   }

   public void parse(Class cls, boolean topDown) throws IOException {
      String suff = this._suffix == null ? "" : this._suffix;
      this.parse((MetaDataIterator)(new ClassMetaDataIterator(cls, suff, topDown)), !topDown);
   }

   public void parse(Reader xml, String sourceName) throws IOException {
      if (xml != null && (sourceName == null || !this.parsed(sourceName))) {
         this.parseNewResource(xml, sourceName);
      }

   }

   public void parse(MetaDataIterator itr) throws IOException {
      this.parse(itr, false);
   }

   private void parse(MetaDataIterator itr, boolean stopFirst) throws IOException {
      if (itr != null) {
         try {
            while(itr.hasNext()) {
               String sourceName = itr.next().toString();
               if (this.parsed(sourceName)) {
                  if (stopFirst) {
                     break;
                  }
               } else {
                  this._sourceFile = itr.getFile();
                  this.parseNewResource(new InputStreamReader(itr.getInputStream()), sourceName);
                  if (stopFirst) {
                     break;
                  }
               }
            }
         } finally {
            itr.close();
         }

      }
   }

   protected void parseNewResource(Reader xml, String sourceName) throws IOException {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("start-parse", (Object)sourceName));
      }

      Object schemaSource = this.getSchemaSource();
      if (schemaSource != null && _schemaBug) {
         if (this._log != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("parser-schema-bug"));
         }

         schemaSource = null;
      }

      boolean validating = this._validating && (this.getDocType() != null || schemaSource != null);

      try {
         this._sourceName = sourceName;
         SAXParser parser = XMLFactory.getSAXParser(validating, true);
         Object schema = null;
         if (validating) {
            schema = schemaSource;
            if (schemaSource == null && this.getDocType() != null) {
               xml = new DocTypeReader((Reader)xml, this.getDocType());
            }
         }

         if (this._parseComments || this._lh != null) {
            parser.setProperty("http://xml.org/sax/properties/lexical-handler", this);
         }

         if (schema != null) {
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", schema);
         }

         InputSource is = new InputSource((Reader)xml);
         if (this._systemId && sourceName != null) {
            is.setSystemId(sourceName);
         }

         parser.parse(is, this);
         this.finish();
      } catch (SAXException var11) {
         IOException ioe = new IOException(var11.toString());
         JavaVersions.initCause(ioe, var11);
         throw ioe;
      } finally {
         this.reset();
      }

   }

   protected boolean parsed(String src) {
      if (!this._caching) {
         return false;
      } else {
         if (this._parsed == null) {
            this._parsed = new HashMap();
         }

         ClassLoader loader = this.currentClassLoader();
         Set set = (Set)this._parsed.get(loader);
         if (set == null) {
            set = new HashSet();
            this._parsed.put(loader, set);
         }

         boolean added = ((Set)set).add(src);
         if (!added && this._log != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("already-parsed", (Object)src));
         }

         return !added;
      }
   }

   public void clear() {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("clear-parser", (Object)this));
      }

      if (this._parsed != null) {
         this._parsed.clear();
      }

   }

   public void error(SAXParseException se) throws SAXException {
      throw this.getException(se.toString());
   }

   public void fatalError(SAXParseException se) throws SAXException {
      throw this.getException(se.toString());
   }

   public void setDocumentLocator(Locator locator) {
      this._location.setLocator(locator);
   }

   public void startElement(String uri, String name, String qName, Attributes attrs) throws SAXException {
      ++this._depth;
      if (this._depth <= this._ignore && !this.startElement(qName, attrs)) {
         this.ignoreContent(true);
      }

   }

   public void endElement(String uri, String name, String qName) throws SAXException {
      if (this._depth < this._ignore) {
         this.endElement(qName);
      }

      this._text = null;
      if (this._comments != null) {
         this._comments.clear();
      }

      if (this._depth == this._ignore) {
         this._ignore = Integer.MAX_VALUE;
      }

      --this._depth;
   }

   public void characters(char[] ch, int start, int length) {
      if (this._parseText && this._depth <= this._ignore) {
         if (this._text == null) {
            this._text = new StringBuffer();
         }

         this._text.append(ch, start, length);
      }

   }

   public void comment(char[] ch, int start, int length) throws SAXException {
      if (this._parseComments && this._depth <= this._ignore) {
         if (this._comments == null) {
            this._comments = new ArrayList(3);
         }

         this._comments.add(String.valueOf(ch, start, length));
      }

      if (this._lh != null) {
         this._lh.comment(ch, start, length);
      }

   }

   public void startCDATA() throws SAXException {
      if (this._lh != null) {
         this._lh.startCDATA();
      }

   }

   public void endCDATA() throws SAXException {
      if (this._lh != null) {
         this._lh.endCDATA();
      }

   }

   public void startDTD(String name, String publicId, String systemId) throws SAXException {
      if (this._lh != null) {
         this._lh.startDTD(name, publicId, systemId);
      }

   }

   public void endDTD() throws SAXException {
      if (this._lh != null) {
         this._lh.endDTD();
      }

   }

   public void startEntity(String name) throws SAXException {
      if (this._lh != null) {
         this._lh.startEntity(name);
      }

   }

   public void endEntity(String name) throws SAXException {
      if (this._lh != null) {
         this._lh.endEntity(name);
      }

   }

   protected abstract boolean startElement(String var1, Attributes var2) throws SAXException;

   protected abstract void endElement(String var1) throws SAXException;

   protected void addResult(Object result) {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("add-result", result));
      }

      this._curResults.add(result);
   }

   protected void finish() {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("end-parse", (Object)this.getSourceName()));
      }

      this._results = new ArrayList(this._curResults);
   }

   protected void reset() {
      this._curResults.clear();
      this._curLoader = null;
      this._sourceName = null;
      this._sourceFile = null;
      this._depth = -1;
      this._ignore = Integer.MAX_VALUE;
      if (this._comments != null) {
         this._comments.clear();
      }

   }

   protected Object getSchemaSource() throws IOException {
      return null;
   }

   protected Reader getDocType() throws IOException {
      return null;
   }

   protected String getSourceName() {
      return this._sourceName;
   }

   protected File getSourceFile() {
      return this._sourceFile;
   }

   protected void addComments(Object obj) {
      String[] comments = this.currentComments();
      if (comments.length > 0 && obj instanceof Commentable) {
         ((Commentable)obj).setComments(comments);
      }

   }

   protected String[] currentComments() {
      return this._comments != null && !this._comments.isEmpty() ? (String[])((String[])this._comments.toArray(new String[this._comments.size()])) : Commentable.EMPTY_COMMENTS;
   }

   protected String currentText() {
      return this._text == null ? "" : this._text.toString().trim();
   }

   protected String currentLocation() {
      return " [" + _loc.get("loc-prefix") + this._location.getLocation() + "]";
   }

   protected int currentDepth() {
      return this._depth;
   }

   protected ClassLoader currentClassLoader() {
      if (this._loader != null) {
         return this._loader;
      } else {
         if (this._curLoader == null) {
            this._curLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         return this._curLoader;
      }
   }

   protected void ignoreContent(boolean ignoreEnd) {
      this._ignore = this._depth;
      if (!ignoreEnd) {
         ++this._ignore;
      }

   }

   protected SAXException getException(String msg) {
      return new SAXException(this.getSourceName() + this.currentLocation() + ": " + msg);
   }

   protected SAXException getException(Localizer.Message msg) {
      return new SAXException(this.getSourceName() + this.currentLocation() + ": " + msg.getMessage());
   }

   protected SAXException getException(Localizer.Message msg, Throwable cause) {
      if (cause != null && this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("sax-exception", this.getSourceName(), this._location.getLocation()), cause);
      }

      SAXException e = new SAXException(this.getSourceName() + this.currentLocation() + ": " + msg + " [" + cause + "]");
      e.initCause(cause);
      return e;
   }

   static {
      try {
         _schemaBug = "Xerces-J 2.0.2".equals(Class.forName("org.apache.xerces.impl.Version").getField("fVersion").get((Object)null));
      } catch (Throwable var1) {
         _schemaBug = false;
      }

   }
}
