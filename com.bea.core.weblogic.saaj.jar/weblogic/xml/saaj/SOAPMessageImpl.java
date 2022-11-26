package weblogic.xml.saaj;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePartDataSource;
import javax.mail.internet.ParseException;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.Text;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.xml.domimpl.Saver;
import weblogic.xml.domimpl.SaverOptions;
import weblogic.xml.saaj.mime4j.BodyDescriptor;
import weblogic.xml.saaj.mime4j.MimeStreamParser;
import weblogic.xml.saaj.mime4j.SimpleContentHandler;
import weblogic.xml.saaj.mime4j.field.Field;
import weblogic.xml.saaj.util.HeaderUtils;
import weblogic.xml.saaj.util.IOUtils;
import weblogic.xml.util.EmptyIterator;

public class SOAPMessageImpl extends SOAPMessage implements Externalizable {
   private SOAPPartImpl soapPart;
   private boolean streamingAttachments;
   private String soapNS;
   private final List attachmentParts;
   private MimeMultipart __dont_touch_mimeMessage;
   private final Map messageProperties;
   private String _contentDescription;
   private boolean saveChangesRequired;
   private final MimeHeaders headers;
   static final long serialVersionUID = 7588237552849679732L;
   private static final boolean FORCE_PRETTY_PRINT = Boolean.getBoolean("weblogic.xml.saaj.ForcePrettyPrint");
   private static final boolean SKIP_CONTENT_TRANSFER_ENCODING = Boolean.getBoolean("weblogic.xml.saaj.skipContentTransferEncoding");
   private static final String DEFAULT_ENCODING = "utf-8";
   private static final String WRITE_XML_DECL_TRUE = "true";
   private static final String MIME_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
   private static final String MIME_8BIT_TRANSER_ENCODING = "8bit";
   private static final int PEEK_AHEAD = 150;
   private static URL DUMMY_URL;
   private static final QName XOP_INCLUDE_QNAME = new QName("http://www.w3.org/2004/08/xop/include", "Include");
   private boolean isMTOMmessage;
   private boolean mtomEnabled;

   public SOAPMessageImpl() throws SOAPException {
      this("http://schemas.xmlsoap.org/soap/envelope/");
   }

   protected SOAPMessageImpl(String soapNS) throws SOAPException {
      this.attachmentParts = new LinkedList();
      this.messageProperties = new HashMap();
      this.saveChangesRequired = true;
      this.headers = new MimeHeaders();
      this.isMTOMmessage = false;
      this.mtomEnabled = false;
      this.soapNS = soapNS;
      this.soapPart = new SOAPPartImpl(soapNS);
      this.updateMimeType();
      SOAPElementImpl env = this.soapPart.envelope();
      env.declareNonDefaultNamespace("env", soapNS);
   }

   SOAPMessageImpl(boolean streamingAttachments, boolean mtomEnabled, String soapNS) throws SOAPException {
      this(soapNS);
      this.streamingAttachments = streamingAttachments;
      this.mtomEnabled = mtomEnabled;
      if (mtomEnabled) {
         this.isMTOMmessage = true;
         this.createMimeMessage();
      }

   }

   SOAPMessageImpl(MimeHeaders mimeHeaders, InputStream inputStream, boolean streamingAttachments, boolean isMTOMmessage, String soapNS) throws SOAPException, IOException {
      this.attachmentParts = new LinkedList();
      this.messageProperties = new HashMap();
      this.saveChangesRequired = true;
      this.headers = new MimeHeaders();
      this.isMTOMmessage = false;
      this.mtomEnabled = false;

      assert inputStream != null;

      this.streamingAttachments = streamingAttachments;
      this.isMTOMmessage = isMTOMmessage;
      this.soapNS = soapNS;
      this.soapPart = this.constructMessage(mimeHeaders, inputStream, true);
      if (this.soapPart == null) {
         throw new SOAPException("invalid soap message: no soap part");
      } else {
         SOAPEnvelope env = this.soapPart.getEnvelope();
         if (env == null) {
            throw new SOAPException("invalid soap message");
         }
      }
   }

   private void updateMimeType() {
      this.headers.setHeader("Content-Type", this.getMimeContentType());
   }

   private SOAPPartImpl constructMessage(MimeHeaders mimeHeaders, InputStream inputStream, boolean isDereference) throws SOAPException, IOException {
      assert inputStream != null;

      copyMimeHeaders(mimeHeaders, this.headers);
      String contentTypeHeaderValue = HeaderUtils.getContentType(this.headers);
      ContentType contentType = null;
      String baseType;
      String encoding;
      if (contentTypeHeaderValue == null) {
         baseType = SOAPConstants.getMimeType(this.soapNS);
         encoding = "utf-8";
         contentTypeHeaderValue = this.constructContentTypeMimeHeader(baseType, encoding);
         this.headers.setHeader("Content-Type", contentTypeHeaderValue);
      } else {
         contentType = createContentType(contentTypeHeaderValue);
         baseType = contentType.getBaseType();
         if (baseType == null) {
            baseType = SOAPConstants.getMimeType(this.soapNS);
         }

         encoding = contentType.getParameter("charset");
         if (encoding == null) {
            encoding = "us-ascii";
         }

         String changedEncoding = System.getProperty("ENCODING");
         if (changedEncoding != null && encoding.equalsIgnoreCase("us-ascii")) {
            encoding = changedEncoding;
         }

         this.setProperty("javax.xml.soap.character-set-encoding", encoding);
      }

      assert contentTypeHeaderValue != null;

      assert encoding != null;

      assert baseType != null;

      SOAPPartImpl soapPart;
      if (SOAPConstants.isSoap(this.soapNS, baseType)) {
         soapPart = this.createSOAPPart(inputStream, encoding);
      } else {
         if (!"Multipart/Related".equalsIgnoreCase(baseType)) {
            throw new SOAPException("Unsupported Content-Type: " + baseType);
         }

         if (contentType == null) {
            contentType = createContentType(contentTypeHeaderValue);
         }

         String type = contentType.getParameter("type");
         if (type != null && type.toLowerCase().equals("application/xop+xml") && isDereference) {
            soapPart = this.handleMimeMessage(inputStream, contentType, contentTypeHeaderValue);
            this.derefMTOMAttachments(soapPart);
         } else {
            soapPart = this.handleMimeMessage(inputStream, contentType, contentTypeHeaderValue);
         }
      }

      return soapPart;
   }

   private void derefMTOMAttachments(SOAPPart sp) throws SOAPException {
      SOAPEnvelope se = sp.getEnvelope();
      Iterator it;
      if (se != null) {
         SOAPBody sb = se.getBody();
         if (sb != null) {
            it = sb.getChildElements();

            label54:
            while(true) {
               Object pObj;
               do {
                  if (!it.hasNext()) {
                     break label54;
                  }

                  pObj = it.next();
               } while(pObj instanceof Text);

               SOAPElement parent = (SOAPElement)pObj;
               Iterator childIterator = parent.getChildElements();

               while(childIterator.hasNext()) {
                  Object obj = childIterator.next();
                  if (obj instanceof Text) {
                     break;
                  }

                  SOAPElement child = (SOAPElement)obj;
                  this.derefMTOMAttachments(parent, child);
               }
            }
         }
      }

      SOAPHeader sh = se.getHeader();
      if (sh != null) {
         it = sh.getChildElements();

         while(it.hasNext()) {
            SOAPElement parent = (SOAPElement)it.next();
            Iterator childIterator = parent.getChildElements();

            while(childIterator.hasNext()) {
               Object obj = childIterator.next();
               if (obj instanceof Text) {
                  break;
               }

               SOAPElement child = (SOAPElement)obj;
               this.derefMTOMAttachments(parent, child);
            }
         }
      }

   }

   private void derefMTOMAttachments(SOAPElement parent, SOAPElement element) throws SOAPException {
      if (element.getElementName().getURI().equals(XOP_INCLUDE_QNAME.getNamespaceURI()) && element.getElementName().getLocalName().equals(XOP_INCLUDE_QNAME.getLocalPart())) {
         String cid = this.getCid(element);
         if (cid == null) {
            throw new SOAPException("encountered XOP:Include SOAPElement without an href.  Without the href, we cannot lookup its MIME attachment !");
         }

         MimeHeaders hes = new MimeHeaders();
         String contentId = "<" + cid + ">";
         hes.addHeader("Content-ID", contentId);
         InputStream is = null;
         AttachmentPart ap = null;

         Iterator it;
         for(it = this.getAttachments(hes); it.hasNext(); ap = (AttachmentPart)it.next()) {
         }

         if (it.hasNext()) {
            throw new SOAPException("Unexpected condition: recieved more than one MIME attachment with Content-ID = '" + cid + "'.   There should only be one.");
         }

         String[] mimeHeaders = ap.getMimeHeader("Content-Transfer-Encoding");
         if (mimeHeaders.length > 1) {
            throw new SOAPException("Unexpected condition: recieved more than one 'Content-Transfer-Encoding' MIME header in attachment with CID '" + cid + "', recieved '" + mimeHeaders.length + "'");
         }

         String contentTransferEncoding = mimeHeaders[0];
         String base64String = null;
         if (contentTransferEncoding != null && (contentTransferEncoding == null || !contentTransferEncoding.equalsIgnoreCase("binary"))) {
            throw new SOAPException(" decoding of MIME attachments into base64Binary from MIME Content-Transfer-Encoding='" + contentTransferEncoding + "' not supported.");
         }

         byte[] rawBytes = ap.getRawContentBytes();
         if (rawBytes != null) {
            is = new ByteArrayInputStream(rawBytes);
            if (is != null) {
               BASE64Encoder encoder = new BASE64Encoder();
               ByteArrayOutputStream baos = new ByteArrayOutputStream();

               try {
                  encoder.encodeBuffer(is, baos);
               } catch (IOException var23) {
                  throw new SOAPException("Error encoding base 64 content", var23);
               } finally {
                  try {
                     is.close();
                  } catch (IOException var22) {
                     throw new SOAPException("Error closing content input stream", var22);
                  }
               }

               base64String = baos.toString();
            }
         }

         if (base64String != null) {
            parent.removeContents();
            parent.addTextNode(base64String);
         }
      } else {
         Iterator it = element.getChildElements();

         while(it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Text) {
               break;
            }

            SOAPElement nextChild = (SOAPElement)obj;
            this.derefMTOMAttachments(element, nextChild);
         }
      }

   }

   private String getCid(SOAPElement xml) throws SOAPException {
      String cid = xml.getAttribute("href");
      if (cid != null && !"".equals(cid)) {
         if (cid.contains("%")) {
            try {
               cid = URLDecoder.decode(cid, "UTF-8");
            } catch (UnsupportedEncodingException var4) {
               throw new SOAPException("Could not decode URI '" + cid + "'.  " + var4.getMessage());
            }
         }

         return cid.startsWith("cid:") ? cid.substring("cid:".length(), cid.length()) : cid;
      } else {
         return null;
      }
   }

   private static ContentType createContentType(String contentTypeHeaderValue) throws SOAPException {
      try {
         return new ContentType(contentTypeHeaderValue);
      } catch (ParseException var3) {
         String s = "Error creating SOAP message.  Error parsing Content-Type \"" + contentTypeHeaderValue + "\" in the SOAP Message Mime Header";
         throw new SOAPException(s, var3);
      }
   }

   private SOAPPartImpl createSOAPPart(InputStream inputStream) throws IOException {
      SOAPPartImpl result = new SOAPPartImpl(this.soapNS, this.headers);
      result.createDocumentFromInputStream(inputStream);
      return result;
   }

   private SOAPPartImpl createSOAPPart(InputStream inputStream, MimeHeaders headers) throws IOException {
      SOAPPartImpl result = new SOAPPartImpl(this.soapNS, headers);
      result.createDocumentFromInputStream(inputStream);
      return result;
   }

   private SOAPPartImpl createSOAPPart(InputStream inputStream, String encoding) throws IOException {
      assert encoding != null;

      SOAPPartImpl result = new SOAPPartImpl(this.soapNS);
      String newEncoding = encoding;
      String changedEncoding = System.getProperty("ENCODING");
      if (changedEncoding != null && encoding.equalsIgnoreCase("us-ascii")) {
         newEncoding = changedEncoding;
      }

      result.createDocumentFromInputStream(inputStream, newEncoding);
      return result;
   }

   private InputStream positionInputStream(InputStream in) throws IOException, MessagingException {
      BufferedInputStream bis = new BufferedInputStream(in);
      bis.mark(150);
      byte[] bytes = new byte[150];
      bis.read(bytes);
      String startText = Arrays.toString(bytes).toLowerCase();
      int multiPartRelatedStartLoc = startText.indexOf("multipart/related");
      if (multiPartRelatedStartLoc != -1) {
         bis.reset();
         new InternetHeaders(bis);
      } else {
         bis.reset();
      }

      return bis;
   }

   private SOAPPartImpl handleMimeMessage(InputStream in, ContentType contentType, String contentTypeHeaderValue) throws SOAPException, IOException {
      if (this.streamingAttachments) {
         return this.handleMimeMessageStreaming(in);
      } else {
         InputStream positionedInputStream;
         try {
            positionedInputStream = this.positionInputStream(in);
         } catch (MessagingException var24) {
            throw new SOAPException("Error doing initial position on input stream: ", var24);
         }

         String start = contentType.getParameter("start");

         MimeBodyPart mimeBodyPart;
         try {
            InternetHeaders ihs = new InternetHeaders();
            ihs.addHeader("Content-Type", contentTypeHeaderValue);
            mimeBodyPart = new MimeBodyPart(ihs, IOUtils.toByteArray(positionedInputStream));
         } catch (MessagingException var23) {
            throw new SOAPException("Error Creating MIME Body Part: ", var23);
         }

         MimePartDataSource mimeDataSource = new MimePartDataSource(mimeBodyPart);

         MimeMultipart mimeMultiPart;
         try {
            mimeMultiPart = new MimeMultipart(mimeDataSource);
         } catch (MessagingException var22) {
            throw new SOAPException("Error Creating MimeMultipart Message: ", var22);
         }

         boolean foundStart = false;

         int count;
         try {
            count = mimeMultiPart.getCount();
         } catch (MessagingException var21) {
            throw new SOAPException("Error getting parts count from MimeMultiPart Message: ", var21);
         }

         SOAPPartImpl soapPart = null;

         for(int i = 0; i < count; ++i) {
            BodyPart bodyPart;
            try {
               bodyPart = mimeMultiPart.getBodyPart(i);
            } catch (MessagingException var20) {
               throw new SOAPException("Error getting Body Part " + i + " from MimeMultiPart Message: ", var20);
            }

            String cid = "";

            try {
               if (bodyPart.getHeader("Content-ID") != null) {
                  cid = bodyPart.getHeader("Content-ID")[0];
               }
            } catch (MessagingException var19) {
               throw new SOAPException("Error getting Content-ID header from Mime Body part " + count + ":" + var19);
            }

            if (i == 0) {
               if (foundStart) {
                  throw new SOAPException("Found > 1 attachments with the same content id as the start: " + start);
               }

               foundStart = true;

               try {
                  MimeHeaders headersFromBodyPart = new MimeHeaders();
                  Enumeration headers = bodyPart.getAllHeaders();

                  while(headers.hasMoreElements()) {
                     Header header = (Header)headers.nextElement();
                     headersFromBodyPart.setHeader(header.getName(), header.getValue());
                  }

                  soapPart = this.createSOAPPart(bodyPart.getInputStream(), headersFromBodyPart);
                  soapPart.setContentId(cid);
               } catch (MessagingException var25) {
                  throw new SOAPException("Error parsing SOAP Part of Mime Message: ", var25);
               }
            } else {
               AttachmentPart attachment;
               try {
                  attachment = this.createAttachmentPart(bodyPart.getDataHandler());
               } catch (MessagingException var18) {
                  throw new SOAPException("Error Creating Attachment Part: ", var18);
               }

               fillInHeaders(bodyPart, attachment);
               this.addAttachmentPart(attachment);
            }
         }

         if (count == 1 && this.isMTOMmessage) {
            this.createMimeMessage();
         }

         if (soapPart == null) {
            throw new SOAPException("No SOAP Part found in MIME Message.");
         } else {
            return soapPart;
         }
      }
   }

   private SOAPPartImpl handleMimeMessageStreaming(InputStream in) throws IOException {
      MimeStreamParser parser = new MimeStreamParser();
      MimeContentHandler mimeContentHandler = new MimeContentHandler(parser);
      parser.setContentHandler(mimeContentHandler);
      BodyDescriptor bodyDescriptor = new BodyDescriptor();
      Iterator allHeaders = this.headers.getAllHeaders();

      while(allHeaders.hasNext()) {
         MimeHeader header = (MimeHeader)allHeaders.next();
         bodyDescriptor.addField(header.getName(), header.getValue());
      }

      parser.parseEntity(bodyDescriptor, in);

      assert mimeContentHandler.soapPartImpl != null;

      return mimeContentHandler.soapPartImpl;
   }

   public AttachmentPart getAttachment(SOAPElement element) throws SOAPException {
      String href = element.getAttribute("href");
      String uri;
      if (!"".equals(href)) {
         uri = href;
      } else {
         Node node = (Node)element.getFirstChild();
         if (node != null && (node.getNextSibling() != null || node.getNodeType() != 3)) {
            node = null;
         }

         String swaRef = null;
         if (node != null) {
            swaRef = node.getValue();
         }

         if (swaRef == null || "".equals(swaRef)) {
            return null;
         }

         uri = swaRef;
      }

      try {
         MimeHeaders headersToMatch = new MimeHeaders();
         if (uri.startsWith("cid:")) {
            uri = '<' + uri.substring("cid:".length()) + '>';
            headersToMatch.addHeader("Content-ID", uri);
         } else {
            headersToMatch.addHeader("Content-Location", uri);
         }

         Iterator i = this.getAttachments(headersToMatch);
         AttachmentPart attachmentPart = null;
         if (i != null) {
            attachmentPart = (AttachmentPart)i.next();
         }

         if (attachmentPart == null) {
            i = this.getAttachments();

            while(i.hasNext()) {
               AttachmentPart ap = (AttachmentPart)i.next();
               String cid = ap.getContentId();
               if (cid != null) {
                  int index = cid.indexOf("=");
                  if (index > -1) {
                     cid = cid.substring(1, index);
                     if (cid.equalsIgnoreCase(uri)) {
                        attachmentPart = ap;
                        break;
                     }
                  }
               }
            }
         }

         return attachmentPart;
      } catch (Exception var10) {
         throw new SOAPException(var10);
      }
   }

   public void removeAttachments(MimeHeaders headers) {
      Iterator it = this.getAttachments(headers);

      while(it.hasNext()) {
         it.next();
         it.remove();
      }

   }

   private static void fillInHeaders(BodyPart bodyPart, AttachmentPart attachment) throws SOAPException {
      Enumeration headers;
      try {
         headers = bodyPart.getAllHeaders();
      } catch (MessagingException var4) {
         throw new SOAPException("Failed to get all mime headers", var4);
      }

      while(headers.hasMoreElements()) {
         Header header = (Header)headers.nextElement();
         attachment.setMimeHeader(header.getName(), header.getValue());
      }

   }

   private void setSOAPPartMimeHeaders(BodyPart bodyPart) throws MessagingException {
      Enumeration headers = bodyPart.getAllHeaders();

      while(headers.hasMoreElements()) {
         Header header = (Header)headers.nextElement();
         this.headers.addHeader(header.getName(), header.getValue());
      }

   }

   private static void copyMimeHeaders(MimeHeaders fromMimeHeaders, MimeHeaders toMimeHeaders) {
      if (fromMimeHeaders != null) {
         Iterator fromHeaders = fromMimeHeaders.getAllHeaders();

         while(fromHeaders.hasNext()) {
            MimeHeader mimeHeader = (MimeHeader)fromHeaders.next();
            toMimeHeaders.addHeader(mimeHeader.getName(), mimeHeader.getValue());
         }

      }
   }

   public int countAttachments() {
      return this.attachmentParts.size();
   }

   public void removeAllAttachments() {
      this.attachmentParts.clear();
      this.removeMessageContentTypeHeader("Multipart/Related");
      this.__dont_touch_mimeMessage = null;
   }

   public void saveChanges() {
      this.saveChangesRequired = false;
      if (!this.attachmentParts.isEmpty()) {
         this.__dont_touch_mimeMessage = null;
         this.createMimeMessage();
      }

   }

   public boolean saveRequired() {
      return this.saveChangesRequired;
   }

   public void writeTo(OutputStream outputStream) throws SOAPException, IOException {
      if (this.countAttachments() <= 0 && !this.isMTOMmessage) {
         this.SOAPPart_writeTo(this.soapPart, outputStream);
      } else {
         try {
            this.writeMimeMessage(outputStream);
         } catch (MessagingException var3) {
            throw new SOAPException(var3);
         }
      }

   }

   public void setProperty(String property, Object value) {
      this.messageProperties.put(property, value);
   }

   public Object getProperty(String property) {
      return this.messageProperties.get(property);
   }

   public String getContentDescription() {
      return this._contentDescription;
   }

   public void setContentDescription(String contentDescription) {
      this._contentDescription = contentDescription;
   }

   public boolean getIsMTOMmessage() {
      return this.isMTOMmessage;
   }

   public Iterator getAttachments() {
      return this.attachmentParts.isEmpty() ? EmptyIterator.getInstance() : this.attachmentParts.iterator();
   }

   public AttachmentPart createAttachmentPart() {
      return new AttachmentPartImpl();
   }

   public void addAttachmentPart(AttachmentPart attachmentPart) {
      this.attachmentParts.add(attachmentPart);
      this.removeMessageContentTypeHeader(SOAPConstants.getMimeType(this.soapNS));
      this.createMimeMessage();
   }

   private void writeMimeMessage(OutputStream out) throws SOAPException, IOException, MessagingException {
      MimeMultipart mimeMsg = this.createMimeMessage();
      int count = mimeMsg.getCount();

      int i;
      for(i = 0; i < count; ++i) {
         mimeMsg.removeBodyPart(0);
      }

      int bodyPartCount = 0;
      MimeBodyPart mbp = new MimeBodyPart();
      mbp.setDataHandler(new SoapPartDataHandler());
      Iterator headers = this.soapPart.getAllMimeHeaders();

      while(headers.hasNext()) {
         MimeHeader header = (MimeHeader)headers.next();
         mbp.setHeader(header.getName(), header.getValue());
      }

      mbp.setHeader("Content-Type", this.getMimeContentType());
      String ContentID = "<soapPart>";
      mbp.setHeader("Content-ID", ContentID);
      if (!SKIP_CONTENT_TRANSFER_ENCODING) {
         mbp.setHeader("Content-Transfer-Encoding", "8bit");
      }

      i = bodyPartCount + 1;
      mimeMsg.addBodyPart(mbp, bodyPartCount);
      Iterator attachments = this.attachmentParts.iterator();

      while(attachments.hasNext()) {
         AttachmentPart attachment = (AttachmentPart)attachments.next();
         mbp = createMimeBodyPart(attachment);
         mimeMsg.addBodyPart(mbp, i++);
      }

      mimeMsg.writeTo(out);
   }

   private String getMimeContentType() {
      return this.constructContentTypeMimeHeader((String)null, this.getCharacterEncoding());
   }

   private String constructContentTypeMimeHeader(String mime, String characterEncoding) {
      return HeaderUtils.constructContentTypeHeader(mime, characterEncoding, this, this.isMTOMmessage, this.isSOAP12());
   }

   private static MimeBodyPart createMimeBodyPart(AttachmentPart attachment) throws MessagingException, SOAPException {
      MimeBodyPart mbp = new MimeBodyPart() {
         protected InputStream getContentStream() throws MessagingException {
            return null;
         }
      };
      mbp.setDataHandler(attachment.getDataHandler());
      Iterator mimeHeaders = attachment.getAllMimeHeaders();

      while(mimeHeaders.hasNext()) {
         MimeHeader mh = (MimeHeader)mimeHeaders.next();
         mbp.addHeader(mh.getName(), mh.getValue());
      }

      return mbp;
   }

   private void SOAPPart_writeTo(SOAPPart soapPart, OutputStream outputStream) throws SOAPException {
      assert soapPart != null;

      String charEncoding = this.getCharacterEncoding();

      assert charEncoding != null;

      SaverOptions sopts = new SaverOptions();
      sopts.setEncoding(charEncoding);
      if (FORCE_PRETTY_PRINT) {
         sopts.setPrettyPrint(true);
      }

      sopts.setWriteXmlDeclaration("true".equals(this.getProperty("javax.xml.soap.write-xml-declaration")));

      try {
         Saver.save((OutputStream)outputStream, soapPart, sopts);
      } catch (IOException var6) {
         throw new SOAPException("Error attempting to save SOAPPart. " + var6);
      }
   }

   private String getCharacterEncoding() {
      String charEncoding = (String)this.getProperty("javax.xml.soap.character-set-encoding");
      if (charEncoding == null) {
         charEncoding = "utf-8";
      }

      return charEncoding;
   }

   private MimeMultipart createMimeMessage() {
      if (this.__dont_touch_mimeMessage == null) {
         this.__dont_touch_mimeMessage = new MimeMultipart("related");
      }

      try {
         ContentType ct = new ContentType(this.__dont_touch_mimeMessage.getContentType());
         String type = "text/xml";
         String startInfo = "";
         if (this.isMTOMmessage) {
            type = "application/xop+xml";
            if (this.isSOAP11()) {
               startInfo = ";start-info=\"text/xml\"";
            } else {
               startInfo = ";start-info=\"application/soap+xml\"; action=\"" + (this.getProperty("weblogic.xml.saaj.action-parameter") == null ? "" : this.getProperty("weblogic.xml.saaj.action-parameter")) + "\"";
            }
         }

         String contentType = "multipart/related;boundary=\"" + ct.getParameter("boundary") + "\";type=\"" + type + "\";start=\"<" + "soapPart" + ">\"" + startInfo;
         this.__dont_touch_mimeMessage.setContentType(contentType);
         this.removeContentTypeHeader();
         this.headers.setHeader("Content-Type", contentType);
      } catch (ParseException var5) {
         throw new AssertionError(var5);
      }

      return this.__dont_touch_mimeMessage;
   }

   private void removeContentTypeHeader() {
      this.removeMessageContentTypeHeader("");
   }

   private void removeMessageContentTypeHeader(String contentType) {
      Iterator matchingHeaders = this.headers.getMatchingHeaders(new String[]{"Content-Type"});
      ArrayList toDeleteList = new ArrayList(2);

      while(matchingHeaders.hasNext()) {
         MimeHeader mimeHeader = (MimeHeader)matchingHeaders.next();
         if (contentType.equals("")) {
            toDeleteList.add(mimeHeader.getName());
         } else if (mimeHeader.getValue().toLowerCase().indexOf(contentType.toLowerCase()) != -1) {
            toDeleteList.add(mimeHeader.getName());
         }
      }

      Iterator deleteIter = toDeleteList.iterator();

      while(deleteIter.hasNext()) {
         this.headers.removeHeader((String)deleteIter.next());
      }

   }

   public MimeHeaders getMimeHeaders() {
      return this.headers;
   }

   public SOAPPart getSOAPPart() {
      return this.soapPart;
   }

   public SOAPBody getSOAPBody() throws SOAPException {
      return this.getSOAPPart().getEnvelope().getBody();
   }

   public SOAPHeader getSOAPHeader() throws SOAPException {
      return this.getSOAPPart().getEnvelope().getHeader();
   }

   public Iterator getAttachments(MimeHeaders mimeHeaders) {
      return (Iterator)(this.attachmentParts.isEmpty() ? EmptyIterator.getInstance() : new MatchingHeaderIterator(mimeHeaders, this.attachmentParts));
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();

      try {
         this.writeTo(stream);
      } catch (SOAPException var4) {
         throw new IOException("SOAPMessage write failed: " + var4);
      }

      byte[] b = stream.toByteArray();
      out.writeInt(b.length);
      out.write(b);
   }

   public void readExternal(ObjectInput in) throws IOException {
      int size = in.readInt();
      byte[] b = new byte[size];

      int num;
      for(int totalRead = 0; totalRead < size; totalRead += num) {
         num = in.read(b, totalRead, size - totalRead);
      }

      ByteArrayInputStream stream = new ByteArrayInputStream(b);

      try {
         this.soapPart = this.constructMessage((MimeHeaders)null, stream, false);
      } catch (SOAPException var8) {
         throw new IOException("Failed to construct SOAPMessage: " + var8);
      }
   }

   private boolean isSOAP11() {
      return this.soapNS.equalsIgnoreCase("http://schemas.xmlsoap.org/soap/envelope/");
   }

   private boolean isSOAP12() {
      return this.soapNS.equalsIgnoreCase("http://www.w3.org/2003/05/soap-envelope");
   }

   static {
      try {
         DUMMY_URL = new URL("file:/nowhere");
      } catch (MalformedURLException var1) {
         throw new AssertionError(var1);
      }
   }

   private static final class MatchingHeaderIterator implements Iterator {
      private final Iterator itr;
      private final MimeHeaders headers;
      private Object next;

      MatchingHeaderIterator(MimeHeaders headers, List attachmentParts) {
         this.headers = headers;
         this.itr = attachmentParts.iterator();
      }

      public void remove() {
         this.itr.remove();
      }

      public boolean hasNext() {
         if (this.next == null) {
            this.next = this.nextMatch();
         }

         return this.next != null;
      }

      public Object next() {
         if (this.next != null) {
            Object ret = this.next;
            this.next = null;
            return ret;
         } else {
            return this.hasNext() ? this.next : null;
         }
      }

      Object nextMatch() {
         while(true) {
            if (this.itr.hasNext()) {
               AttachmentPartImpl ap = (AttachmentPartImpl)this.itr.next();
               if (!ap.hasAllHeaders(this.headers)) {
                  continue;
               }

               return ap;
            }

            return null;
         }
      }
   }

   protected final class SoapPartDataHandler extends DataHandler {
      public SoapPartDataHandler() {
         super(SOAPMessageImpl.DUMMY_URL);
      }

      public void writeTo(OutputStream os) {
         try {
            SOAPMessageImpl.this.SOAPPart_writeTo(SOAPMessageImpl.this.soapPart, os);
         } catch (SOAPException var3) {
            throw new SOAPRuntimeException("error writing soap part", var3);
         }
      }
   }

   private final class MimeContentHandler extends SimpleContentHandler {
      private final MimeStreamParser mimeStreamParser;
      private int count = 0;
      private String soapPartContentId;
      private AttachmentPartImpl currentAttachmentPart;
      SOAPPartImpl soapPartImpl;

      public MimeContentHandler(MimeStreamParser mimeStreamParser) {
         this.mimeStreamParser = mimeStreamParser;
      }

      public void headers(weblogic.xml.saaj.mime4j.message.Header header) {
         if (this.count == 0) {
            Field contentIdField = header.getField("Content-ID");
            if (contentIdField != null) {
               this.soapPartContentId = contentIdField.getBody();
            }
         } else {
            AttachmentPartImpl attachment = new AttachmentPartImpl();
            List fields = header.getFields();

            for(int i = 0; i < fields.size(); ++i) {
               Field field = (Field)fields.get(i);
               attachment.addMimeHeader(field.getName(), field.getBody());
            }

            SOAPMessageImpl.this.addAttachmentPart(attachment);
            this.currentAttachmentPart = attachment;
         }

      }

      public boolean bodyDecoded(BodyDescriptor bd, InputStream is) throws IOException {
         if (this.count == 0) {
            SOAPPartImpl soapPart = SOAPMessageImpl.this.createSOAPPart(is);
            if (this.soapPartContentId != null) {
               soapPart.setContentId(this.soapPartContentId);
            }

            this.soapPartImpl = soapPart;
            ++this.count;
            return true;
         } else {
            this.currentAttachmentPart.setDirectInputStream(is);
            this.mimeStreamParser.stop();
            return false;
         }
      }
   }

   private static final class MimeMultipart extends javax.mail.internet.MimeMultipart {
      public MimeMultipart() {
      }

      public MimeMultipart(String s) {
         super(s);
      }

      public MimeMultipart(DataSource ds) throws MessagingException {
         super(ds);
      }

      void setContentType(String s) {
         super.contentType = s;
      }
   }
}
