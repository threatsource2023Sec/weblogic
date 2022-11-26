package org.apache.xmlbeans.impl.schema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.apache.xmlbeans.impl.common.XmlEncodingSniffer;
import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.RedefineDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class StscImporter {
   private static final String PROJECT_URL_PREFIX = "project://local";

   public static SchemaToProcess[] resolveImportsAndIncludes(SchemaDocument.Schema[] startWith, boolean forceSrcSave) {
      DownloadTable engine = new DownloadTable(startWith);
      return engine.resolveImportsAndIncludes(forceSrcSave);
   }

   private static String baseURLForDoc(XmlObject obj) {
      String path = obj.documentProperties().getSourceName();
      if (path == null) {
         return null;
      } else if (path.startsWith("/")) {
         return "project://local" + path.replace('\\', '/');
      } else {
         int colon = path.indexOf(58);
         return colon > 1 && path.substring(0, colon).matches("^\\w+$") ? path : "project://local/" + path.replace('\\', '/');
      }
   }

   private static URI parseURI(String s) {
      if (s == null) {
         return null;
      } else {
         try {
            return new URI(s);
         } catch (URISyntaxException var2) {
            return null;
         }
      }
   }

   public static URI resolve(URI base, String child) throws URISyntaxException {
      URI childUri = new URI(child);
      URI ruri = base.resolve(childUri);
      String r;
      if (childUri.equals(ruri) && !childUri.isAbsolute() && (base.getScheme().equals("jar") || base.getScheme().equals("zip"))) {
         r = base.toString();
         int lastslash = r.lastIndexOf(47);
         r = r.substring(0, lastslash) + "/" + childUri;
         int exclPointSlashIndex = r.lastIndexOf("!/");
         if (exclPointSlashIndex > 0) {
            for(int slashDotDotIndex = r.indexOf("/..", exclPointSlashIndex); slashDotDotIndex > 0; slashDotDotIndex = r.indexOf("/..", exclPointSlashIndex)) {
               int prevSlashIndex = r.lastIndexOf("/", slashDotDotIndex - 1);
               if (prevSlashIndex >= exclPointSlashIndex) {
                  String temp = r.substring(slashDotDotIndex + 3);
                  r = r.substring(0, prevSlashIndex).concat(temp);
               }
            }
         }

         return URI.create(r);
      } else {
         if ("file".equals(ruri.getScheme()) && !child.equals(ruri) && base.getPath().startsWith("//") && !ruri.getPath().startsWith("//")) {
            r = "///".concat(ruri.getPath());

            try {
               ruri = new URI("file", (String)null, r, ruri.getQuery(), ruri.getFragment());
            } catch (URISyntaxException var10) {
            }
         }

         return ruri;
      }
   }

   public static class DownloadTable {
      private Map schemaByNsLocPair = new HashMap();
      private Map schemaByDigestKey = new HashMap();
      private LinkedList scanNeeded = new LinkedList();
      private Set emptyNamespaceSchemas = new HashSet();
      private Map scannedAlready = new HashMap();
      private Set failedDownloads = new HashSet();

      private SchemaDocument.Schema downloadSchema(XmlObject referencedBy, String targetNamespace, String locationURL) {
         if (locationURL == null) {
            return null;
         } else {
            StscState state = StscState.get();
            URI baseURI = StscImporter.parseURI(StscImporter.baseURLForDoc(referencedBy));
            String absoluteURL = null;

            try {
               absoluteURL = baseURI == null ? locationURL : StscImporter.resolve(baseURI, locationURL).toString();
            } catch (URISyntaxException var12) {
               state.error("Could not find resource - invalid location URL: " + var12.getMessage(), 56, referencedBy);
               return null;
            }

            if (state.isFileProcessed(absoluteURL)) {
               return null;
            } else {
               SchemaDocument.Schema result;
               if (absoluteURL != null && targetNamespace != null) {
                  result = (SchemaDocument.Schema)this.schemaByNsLocPair.get(new NsLocPair(targetNamespace, absoluteURL));
                  if (result != null) {
                     return result;
                  }
               }

               if (targetNamespace != null && !targetNamespace.equals("")) {
                  if (!state.shouldDownloadURI(absoluteURL)) {
                     result = (SchemaDocument.Schema)this.schemaByNsLocPair.get(new NsLocPair(targetNamespace, (String)null));
                     if (result != null) {
                        return result;
                     }
                  }

                  if (state.linkerDefinesNamespace(targetNamespace)) {
                     return null;
                  }
               }

               if (absoluteURL != null) {
                  result = (SchemaDocument.Schema)this.schemaByNsLocPair.get(new NsLocPair((String)null, absoluteURL));
                  if (result != null) {
                     return result;
                  }
               }

               if (absoluteURL == null) {
                  state.error("Could not find resource - no valid location URL.", 56, referencedBy);
                  return null;
               } else if (this.previouslyFailedToDownload(absoluteURL)) {
                  return null;
               } else if (!state.shouldDownloadURI(absoluteURL)) {
                  state.error("Could not load resource \"" + absoluteURL + "\" (network downloads disabled).", 56, referencedBy);
                  this.addFailedDownload(absoluteURL);
                  return null;
               } else {
                  try {
                     label110: {
                        XmlObject xdoc = downloadDocument(state.getS4SLoader(), targetNamespace, absoluteURL);
                        SchemaDocument.Schema result = this.findMatchByDigest(xdoc);
                        String shortname = state.relativize(absoluteURL);
                        if (result != null) {
                           String dupname = state.relativize(result.documentProperties().getSourceName());
                           if (dupname != null) {
                              state.info(shortname + " is the same as " + dupname + " (ignoring the duplicate file)");
                           } else {
                              state.info(shortname + " is the same as another schema");
                           }
                        } else {
                           XmlOptions voptions = new XmlOptions();
                           voptions.setErrorListener(state.getErrorListener());
                           if (!(xdoc instanceof SchemaDocument) || !xdoc.validate(voptions)) {
                              state.error("Referenced document is not a valid schema", 56, referencedBy);
                              break label110;
                           }

                           SchemaDocument sDoc = (SchemaDocument)xdoc;
                           result = sDoc.getSchema();
                           state.info("Loading referenced file " + shortname);
                        }

                        NsLocPair key = new NsLocPair(emptyStringIfNull(result.getTargetNamespace()), absoluteURL);
                        this.addSuccessfulDownload(key, result);
                        return result;
                     }
                  } catch (MalformedURLException var13) {
                     state.error("URL \"" + absoluteURL + "\" is not well-formed", 56, referencedBy);
                  } catch (IOException var14) {
                     state.error(var14.toString(), 56, referencedBy);
                  } catch (XmlException var15) {
                     state.error("Problem parsing referenced XML resource - " + var15.getMessage(), 56, referencedBy);
                  }

                  this.addFailedDownload(absoluteURL);
                  return null;
               }
            }
         }
      }

      static XmlObject downloadDocument(SchemaTypeLoader loader, String namespace, String absoluteURL) throws MalformedURLException, IOException, XmlException {
         StscState state = StscState.get();
         EntityResolver resolver = state.getEntityResolver();
         if (resolver != null) {
            InputSource source = null;

            try {
               source = resolver.resolveEntity(namespace, absoluteURL);
            } catch (SAXException var11) {
               throw new XmlException(var11);
            }

            if (source != null) {
               state.addSourceUri(absoluteURL, (String)null);
               Reader reader = source.getCharacterStream();
               if (reader != null) {
                  reader = copySchemaSource(absoluteURL, reader, state);
                  XmlOptions options = new XmlOptions();
                  options.setLoadLineNumbers();
                  options.setDocumentSourceName(absoluteURL);
                  return loader.parse((Reader)reader, (SchemaType)null, options);
               }

               InputStream bytes = source.getByteStream();
               String urlToLoad;
               XmlOptions options;
               if (bytes != null) {
                  bytes = copySchemaSource(absoluteURL, bytes, state);
                  urlToLoad = source.getEncoding();
                  options = new XmlOptions();
                  options.setLoadLineNumbers();
                  options.setLoadMessageDigest();
                  options.setDocumentSourceName(absoluteURL);
                  if (urlToLoad != null) {
                     options.setCharacterEncoding(urlToLoad);
                  }

                  return loader.parse((InputStream)bytes, (SchemaType)null, options);
               }

               urlToLoad = source.getSystemId();
               if (urlToLoad == null) {
                  throw new IOException("EntityResolver unable to resolve " + absoluteURL + " (for namespace " + namespace + ")");
               }

               copySchemaSource(absoluteURL, state, false);
               options = new XmlOptions();
               options.setLoadLineNumbers();
               options.setLoadMessageDigest();
               options.setDocumentSourceName(absoluteURL);
               URL urlDownload = new URL(urlToLoad);
               return loader.parse((URL)urlDownload, (SchemaType)null, options);
            }
         }

         state.addSourceUri(absoluteURL, (String)null);
         copySchemaSource(absoluteURL, state, false);
         XmlOptions options = new XmlOptions();
         options.setLoadLineNumbers();
         options.setLoadMessageDigest();
         URL urlDownload = new URL(absoluteURL);
         return loader.parse((URL)urlDownload, (SchemaType)null, options);
      }

      private void addSuccessfulDownload(NsLocPair key, SchemaDocument.Schema schema) {
         byte[] digest = schema.documentProperties().getMessageDigest();
         if (digest == null) {
            StscState.get().addSchemaDigest((byte[])null);
         } else {
            DigestKey dk = new DigestKey(digest);
            if (!this.schemaByDigestKey.containsKey(dk)) {
               this.schemaByDigestKey.put(new DigestKey(digest), schema);
               StscState.get().addSchemaDigest(digest);
            }
         }

         this.schemaByNsLocPair.put(key, schema);
         NsLocPair key1 = new NsLocPair(key.getNamespaceURI(), (String)null);
         if (!this.schemaByNsLocPair.containsKey(key1)) {
            this.schemaByNsLocPair.put(key1, schema);
         }

         NsLocPair key2 = new NsLocPair((String)null, key.getLocationURL());
         if (!this.schemaByNsLocPair.containsKey(key2)) {
            this.schemaByNsLocPair.put(key2, schema);
         }

      }

      private SchemaDocument.Schema findMatchByDigest(XmlObject original) {
         byte[] digest = original.documentProperties().getMessageDigest();
         return digest == null ? null : (SchemaDocument.Schema)this.schemaByDigestKey.get(new DigestKey(digest));
      }

      private void addFailedDownload(String locationURL) {
         this.failedDownloads.add(locationURL);
      }

      private boolean previouslyFailedToDownload(String locationURL) {
         return this.failedDownloads.contains(locationURL);
      }

      private static boolean nullableStringsMatch(String s1, String s2) {
         if (s1 == null && s2 == null) {
            return true;
         } else {
            return s1 != null && s2 != null ? s1.equals(s2) : false;
         }
      }

      private static String emptyStringIfNull(String s) {
         return s == null ? "" : s;
      }

      private SchemaToProcess addScanNeeded(SchemaToProcess stp) {
         if (!this.scannedAlready.containsKey(stp)) {
            this.scannedAlready.put(stp, stp);
            this.scanNeeded.add(stp);
            return stp;
         } else {
            return (SchemaToProcess)this.scannedAlready.get(stp);
         }
      }

      private void addEmptyNamespaceSchema(SchemaDocument.Schema s) {
         this.emptyNamespaceSchemas.add(s);
      }

      private void usedEmptyNamespaceSchema(SchemaDocument.Schema s) {
         this.emptyNamespaceSchemas.remove(s);
      }

      private boolean fetchRemainingEmptyNamespaceSchemas() {
         if (this.emptyNamespaceSchemas.isEmpty()) {
            return false;
         } else {
            Iterator i = this.emptyNamespaceSchemas.iterator();

            while(i.hasNext()) {
               SchemaDocument.Schema schema = (SchemaDocument.Schema)i.next();
               this.addScanNeeded(new SchemaToProcess(schema, (String)null));
            }

            this.emptyNamespaceSchemas.clear();
            return true;
         }
      }

      private boolean hasNextToScan() {
         return !this.scanNeeded.isEmpty();
      }

      private SchemaToProcess nextToScan() {
         SchemaToProcess next = (SchemaToProcess)this.scanNeeded.removeFirst();
         return next;
      }

      public DownloadTable(SchemaDocument.Schema[] startWith) {
         for(int i = 0; i < startWith.length; ++i) {
            String targetNamespace = startWith[i].getTargetNamespace();
            NsLocPair key = new NsLocPair(targetNamespace, StscImporter.baseURLForDoc(startWith[i]));
            this.addSuccessfulDownload(key, startWith[i]);
            if (targetNamespace != null) {
               this.addScanNeeded(new SchemaToProcess(startWith[i], (String)null));
            } else {
               this.addEmptyNamespaceSchema(startWith[i]);
            }
         }

      }

      public SchemaToProcess[] resolveImportsAndIncludes(boolean forceSave) {
         StscState state = StscState.get();
         List result = new ArrayList();
         boolean hasRedefinitions = false;

         while(true) {
            while(!this.hasNextToScan()) {
               if (!this.fetchRemainingEmptyNamespaceSchemas()) {
                  if (hasRedefinitions) {
                     for(int i = 0; i < result.size(); ++i) {
                        SchemaToProcess schemaToProcess = (SchemaToProcess)result.get(i);
                        schemaToProcess.buildIndirectReferences();
                     }
                  }

                  return (SchemaToProcess[])((SchemaToProcess[])result.toArray(new SchemaToProcess[result.size()]));
               }
            }

            SchemaToProcess stp = this.nextToScan();
            String uri = stp.getSourceName();
            state.addSourceUri(uri, (String)null);
            result.add(stp);
            copySchemaSource(uri, state, forceSave);
            ImportDocument.Import[] imports = stp.getSchema().getImportArray();

            for(int i = 0; i < imports.length; ++i) {
               SchemaDocument.Schema imported = this.downloadSchema(imports[i], emptyStringIfNull(imports[i].getNamespace()), imports[i].getSchemaLocation());
               if (imported != null) {
                  if (!nullableStringsMatch(imported.getTargetNamespace(), imports[i].getNamespace())) {
                     StscState.get().error("Imported schema has a target namespace \"" + imported.getTargetNamespace() + "\" that does not match the specified \"" + imports[i].getNamespace() + "\"", 4, imports[i]);
                  } else {
                     this.addScanNeeded(new SchemaToProcess(imported, (String)null));
                  }
               }
            }

            IncludeDocument.Include[] includes = stp.getSchema().getIncludeArray();
            String sourceNamespace = stp.getChameleonNamespace();
            if (sourceNamespace == null) {
               sourceNamespace = emptyStringIfNull(stp.getSchema().getTargetNamespace());
            }

            SchemaDocument.Schema redefined;
            SchemaToProcess s;
            int i;
            for(i = 0; i < includes.length; ++i) {
               redefined = this.downloadSchema(includes[i], (String)null, includes[i].getSchemaLocation());
               if (redefined != null) {
                  if (emptyStringIfNull(redefined.getTargetNamespace()).equals(sourceNamespace)) {
                     s = this.addScanNeeded(new SchemaToProcess(redefined, (String)null));
                     stp.addInclude(s);
                  } else if (redefined.getTargetNamespace() != null) {
                     StscState.get().error("Included schema has a target namespace \"" + redefined.getTargetNamespace() + "\" that does not match the source namespace \"" + sourceNamespace + "\"", 4, includes[i]);
                  } else {
                     s = this.addScanNeeded(new SchemaToProcess(redefined, sourceNamespace));
                     stp.addInclude(s);
                     this.usedEmptyNamespaceSchema(redefined);
                  }
               }
            }

            RedefineDocument.Redefine[] redefines = stp.getSchema().getRedefineArray();
            sourceNamespace = stp.getChameleonNamespace();
            if (sourceNamespace == null) {
               sourceNamespace = emptyStringIfNull(stp.getSchema().getTargetNamespace());
            }

            for(i = 0; i < redefines.length; ++i) {
               redefined = this.downloadSchema(redefines[i], (String)null, redefines[i].getSchemaLocation());
               if (redefined != null) {
                  if (emptyStringIfNull(redefined.getTargetNamespace()).equals(sourceNamespace)) {
                     s = this.addScanNeeded(new SchemaToProcess(redefined, (String)null));
                     stp.addRedefine(s, redefines[i]);
                     hasRedefinitions = true;
                  } else if (redefined.getTargetNamespace() != null) {
                     StscState.get().error("Redefined schema has a target namespace \"" + redefined.getTargetNamespace() + "\" that does not match the source namespace \"" + sourceNamespace + "\"", 4, redefines[i]);
                  } else {
                     s = this.addScanNeeded(new SchemaToProcess(redefined, sourceNamespace));
                     stp.addRedefine(s, redefines[i]);
                     this.usedEmptyNamespaceSchema(redefined);
                     hasRedefinitions = true;
                  }
               }
            }
         }
      }

      private static Reader copySchemaSource(String url, Reader reader, StscState state) {
         if (state.getSchemasDir() == null) {
            return reader;
         } else {
            String schemalocation = state.sourceNameForUri(url);
            File targetFile = new File(state.getSchemasDir(), schemalocation);
            if (targetFile.exists()) {
               return reader;
            } else {
               try {
                  File parentDir = new File(targetFile.getParent());
                  IOUtil.createDir(parentDir, (String)null);
                  CharArrayReader car = copy(reader);
                  XmlEncodingSniffer xes = new XmlEncodingSniffer(car, (String)null);
                  Writer out = new OutputStreamWriter(new FileOutputStream(targetFile), xes.getXmlEncoding());
                  IOUtil.copyCompletely((Reader)car, (Writer)out);
                  car.reset();
                  return car;
               } catch (IOException var9) {
                  System.err.println("IO Error " + var9);
                  return reader;
               }
            }
         }
      }

      private static InputStream copySchemaSource(String url, InputStream bytes, StscState state) {
         if (state.getSchemasDir() == null) {
            return bytes;
         } else {
            String schemalocation = state.sourceNameForUri(url);
            File targetFile = new File(state.getSchemasDir(), schemalocation);
            if (targetFile.exists()) {
               return bytes;
            } else {
               try {
                  File parentDir = new File(targetFile.getParent());
                  IOUtil.createDir(parentDir, (String)null);
                  ByteArrayInputStream bais = copy(bytes);
                  FileOutputStream out = new FileOutputStream(targetFile);
                  IOUtil.copyCompletely((InputStream)bais, (OutputStream)out);
                  bais.reset();
                  return bais;
               } catch (IOException var8) {
                  System.err.println("IO Error " + var8);
                  return bytes;
               }
            }
         }
      }

      private static void copySchemaSource(String urlLoc, StscState state, boolean forceCopy) {
         if (state.getSchemasDir() != null) {
            String schemalocation = state.sourceNameForUri(urlLoc);
            File targetFile = new File(state.getSchemasDir(), schemalocation);
            if (forceCopy || !targetFile.exists()) {
               try {
                  File parentDir = new File(targetFile.getParent());
                  IOUtil.createDir(parentDir, (String)null);
                  InputStream in = null;
                  URL url = new URL(urlLoc);

                  try {
                     in = url.openStream();
                  } catch (FileNotFoundException var9) {
                     if (!forceCopy || !targetFile.exists()) {
                        throw var9;
                     }

                     targetFile.delete();
                  }

                  if (in != null) {
                     FileOutputStream out = new FileOutputStream(targetFile);
                     IOUtil.copyCompletely((InputStream)in, (OutputStream)out);
                  }
               } catch (IOException var10) {
                  System.err.println("IO Error " + var10);
               }
            }
         }

      }

      private static ByteArrayInputStream copy(InputStream is) throws IOException {
         byte[] buf = new byte[1024];
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         int bytesRead;
         while((bytesRead = is.read(buf, 0, 1024)) > 0) {
            baos.write(buf, 0, bytesRead);
         }

         return new ByteArrayInputStream(baos.toByteArray());
      }

      private static CharArrayReader copy(Reader is) throws IOException {
         char[] buf = new char[1024];
         CharArrayWriter baos = new CharArrayWriter();

         int bytesRead;
         while((bytesRead = is.read(buf, 0, 1024)) > 0) {
            baos.write(buf, 0, bytesRead);
         }

         return new CharArrayReader(baos.toCharArray());
      }

      private static class DigestKey {
         byte[] _digest;
         int _hashCode;

         DigestKey(byte[] digest) {
            this._digest = digest;

            for(int i = 0; i < 4 && i < digest.length; ++i) {
               this._hashCode <<= 8;
               this._hashCode += digest[i];
            }

         }

         public boolean equals(Object o) {
            if (this == o) {
               return true;
            } else {
               return !(o instanceof DigestKey) ? false : Arrays.equals(this._digest, ((DigestKey)o)._digest);
            }
         }

         public int hashCode() {
            return this._hashCode;
         }
      }

      private static class NsLocPair {
         private String namespaceURI;
         private String locationURL;

         public NsLocPair(String namespaceURI, String locationURL) {
            this.namespaceURI = namespaceURI;
            this.locationURL = locationURL;
         }

         public String getNamespaceURI() {
            return this.namespaceURI;
         }

         public String getLocationURL() {
            return this.locationURL;
         }

         public boolean equals(Object o) {
            if (this == o) {
               return true;
            } else if (!(o instanceof NsLocPair)) {
               return false;
            } else {
               NsLocPair nsLocPair = (NsLocPair)o;
               if (this.locationURL != null) {
                  if (!this.locationURL.equals(nsLocPair.locationURL)) {
                     return false;
                  }
               } else if (nsLocPair.locationURL != null) {
                  return false;
               }

               if (this.namespaceURI != null) {
                  if (!this.namespaceURI.equals(nsLocPair.namespaceURI)) {
                     return false;
                  }
               } else if (nsLocPair.namespaceURI != null) {
                  return false;
               }

               return true;
            }
         }

         public int hashCode() {
            int result = this.namespaceURI != null ? this.namespaceURI.hashCode() : 0;
            result = 29 * result + (this.locationURL != null ? this.locationURL.hashCode() : 0);
            return result;
         }
      }
   }

   public static class SchemaToProcess {
      private SchemaDocument.Schema schema;
      private String chameleonNamespace;
      private List includes;
      private List redefines;
      private List redefineObjects;
      private Set indirectIncludes;
      private Set indirectIncludedBy;

      public SchemaToProcess(SchemaDocument.Schema schema, String chameleonNamespace) {
         this.schema = schema;
         this.chameleonNamespace = chameleonNamespace;
      }

      public SchemaDocument.Schema getSchema() {
         return this.schema;
      }

      public String getSourceName() {
         return this.schema.documentProperties().getSourceName();
      }

      public String getChameleonNamespace() {
         return this.chameleonNamespace;
      }

      public List getRedefines() {
         return this.redefines;
      }

      public List getRedefineObjects() {
         return this.redefineObjects;
      }

      private void addInclude(SchemaToProcess include) {
         if (this.includes == null) {
            this.includes = new ArrayList();
         }

         this.includes.add(include);
      }

      private void addRedefine(SchemaToProcess redefine, RedefineDocument.Redefine object) {
         if (this.redefines == null || this.redefineObjects == null) {
            this.redefines = new ArrayList();
            this.redefineObjects = new ArrayList();
         }

         this.redefines.add(redefine);
         this.redefineObjects.add(object);
      }

      private void buildIndirectReferences() {
         int i;
         SchemaToProcess schemaToProcess;
         if (this.includes != null) {
            for(i = 0; i < this.includes.size(); ++i) {
               schemaToProcess = (SchemaToProcess)this.includes.get(i);
               this.addIndirectIncludes(schemaToProcess);
            }
         }

         if (this.redefines != null) {
            for(i = 0; i < this.redefines.size(); ++i) {
               schemaToProcess = (SchemaToProcess)this.redefines.get(i);
               this.addIndirectIncludes(schemaToProcess);
            }
         }

      }

      private void addIndirectIncludes(SchemaToProcess schemaToProcess) {
         if (this.indirectIncludes == null) {
            this.indirectIncludes = new HashSet();
         }

         this.indirectIncludes.add(schemaToProcess);
         if (schemaToProcess.indirectIncludedBy == null) {
            schemaToProcess.indirectIncludedBy = new HashSet();
         }

         schemaToProcess.indirectIncludedBy.add(this);
         addIndirectIncludesHelper(this, schemaToProcess);
         if (this.indirectIncludedBy != null) {
            Iterator it = this.indirectIncludedBy.iterator();

            while(it.hasNext()) {
               SchemaToProcess stp = (SchemaToProcess)it.next();
               stp.indirectIncludes.add(schemaToProcess);
               schemaToProcess.indirectIncludedBy.add(stp);
               addIndirectIncludesHelper(stp, schemaToProcess);
            }
         }

      }

      private static void addIndirectIncludesHelper(SchemaToProcess including, SchemaToProcess schemaToProcess) {
         if (schemaToProcess.indirectIncludes != null) {
            Iterator it = schemaToProcess.indirectIncludes.iterator();

            while(it.hasNext()) {
               SchemaToProcess stp = (SchemaToProcess)it.next();
               including.indirectIncludes.add(stp);
               stp.indirectIncludedBy.add(including);
            }
         }

      }

      public boolean indirectIncludes(SchemaToProcess schemaToProcess) {
         return this.indirectIncludes != null && this.indirectIncludes.contains(schemaToProcess);
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof SchemaToProcess)) {
            return false;
         } else {
            SchemaToProcess schemaToProcess = (SchemaToProcess)o;
            if (this.chameleonNamespace != null) {
               if (this.chameleonNamespace.equals(schemaToProcess.chameleonNamespace)) {
                  return this.schema == schemaToProcess.schema;
               }
            } else if (schemaToProcess.chameleonNamespace == null) {
               return this.schema == schemaToProcess.schema;
            }

            return false;
         }
      }

      public int hashCode() {
         int result = this.schema.hashCode();
         result = 29 * result + (this.chameleonNamespace != null ? this.chameleonNamespace.hashCode() : 0);
         return result;
      }
   }
}
