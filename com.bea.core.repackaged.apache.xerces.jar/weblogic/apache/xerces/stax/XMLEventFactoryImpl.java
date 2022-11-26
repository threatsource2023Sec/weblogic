package weblogic.apache.xerces.stax;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import weblogic.apache.xerces.stax.events.AttributeImpl;
import weblogic.apache.xerces.stax.events.CharactersImpl;
import weblogic.apache.xerces.stax.events.CommentImpl;
import weblogic.apache.xerces.stax.events.DTDImpl;
import weblogic.apache.xerces.stax.events.EndDocumentImpl;
import weblogic.apache.xerces.stax.events.EndElementImpl;
import weblogic.apache.xerces.stax.events.EntityReferenceImpl;
import weblogic.apache.xerces.stax.events.NamespaceImpl;
import weblogic.apache.xerces.stax.events.ProcessingInstructionImpl;
import weblogic.apache.xerces.stax.events.StartDocumentImpl;
import weblogic.apache.xerces.stax.events.StartElementImpl;

public final class XMLEventFactoryImpl extends XMLEventFactory {
   private Location fLocation;

   public void setLocation(Location var1) {
      this.fLocation = var1;
   }

   public Attribute createAttribute(String var1, String var2, String var3, String var4) {
      return this.createAttribute(new QName(var2, var3, var1), var4);
   }

   public Attribute createAttribute(String var1, String var2) {
      return this.createAttribute(new QName(var1), var2);
   }

   public Attribute createAttribute(QName var1, String var2) {
      return new AttributeImpl(var1, var2, "CDATA", true, this.fLocation);
   }

   public Namespace createNamespace(String var1) {
      return this.createNamespace("", var1);
   }

   public Namespace createNamespace(String var1, String var2) {
      return new NamespaceImpl(var1, var2, this.fLocation);
   }

   public StartElement createStartElement(QName var1, Iterator var2, Iterator var3) {
      return this.createStartElement(var1, var2, var3, (NamespaceContext)null);
   }

   public StartElement createStartElement(String var1, String var2, String var3) {
      return this.createStartElement((QName)(new QName(var2, var3, var1)), (Iterator)null, (Iterator)null);
   }

   public StartElement createStartElement(String var1, String var2, String var3, Iterator var4, Iterator var5) {
      return this.createStartElement(new QName(var2, var3, var1), var4, var5);
   }

   public StartElement createStartElement(String var1, String var2, String var3, Iterator var4, Iterator var5, NamespaceContext var6) {
      return this.createStartElement(new QName(var2, var3, var1), var4, var5, var6);
   }

   private StartElement createStartElement(QName var1, Iterator var2, Iterator var3, NamespaceContext var4) {
      return new StartElementImpl(var1, var2, var3, var4, this.fLocation);
   }

   public EndElement createEndElement(QName var1, Iterator var2) {
      return new EndElementImpl(var1, var2, this.fLocation);
   }

   public EndElement createEndElement(String var1, String var2, String var3) {
      return this.createEndElement(new QName(var2, var3, var1), (Iterator)null);
   }

   public EndElement createEndElement(String var1, String var2, String var3, Iterator var4) {
      return this.createEndElement(new QName(var2, var3, var1), var4);
   }

   public Characters createCharacters(String var1) {
      return new CharactersImpl(var1, 4, this.fLocation);
   }

   public Characters createCData(String var1) {
      return new CharactersImpl(var1, 12, this.fLocation);
   }

   public Characters createSpace(String var1) {
      return this.createCharacters(var1);
   }

   public Characters createIgnorableSpace(String var1) {
      return new CharactersImpl(var1, 6, this.fLocation);
   }

   public StartDocument createStartDocument() {
      return this.createStartDocument((String)null, (String)null);
   }

   public StartDocument createStartDocument(String var1, String var2, boolean var3) {
      return new StartDocumentImpl(var1, var1 != null, var3, true, var2, this.fLocation);
   }

   public StartDocument createStartDocument(String var1, String var2) {
      return new StartDocumentImpl(var1, var1 != null, false, false, var2, this.fLocation);
   }

   public StartDocument createStartDocument(String var1) {
      return this.createStartDocument(var1, (String)null);
   }

   public EndDocument createEndDocument() {
      return new EndDocumentImpl(this.fLocation);
   }

   public EntityReference createEntityReference(String var1, EntityDeclaration var2) {
      return new EntityReferenceImpl(var1, var2, this.fLocation);
   }

   public Comment createComment(String var1) {
      return new CommentImpl(var1, this.fLocation);
   }

   public ProcessingInstruction createProcessingInstruction(String var1, String var2) {
      return new ProcessingInstructionImpl(var1, var2, this.fLocation);
   }

   public DTD createDTD(String var1) {
      return new DTDImpl(var1, this.fLocation);
   }
}
