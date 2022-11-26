package weblogic.xml.xpath.stream;

import java.util.Collection;
import java.util.Stack;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.xpath.XPathStreamObserver;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.utils.LayeredMap;

public final class StreamContext extends Context implements StreamNode {
   public static final int DOCUMENT_ROOT_DEPTH = 0;
   private static final boolean DEBUG = false;
   private XPathStreamObserver mRealObserver = null;
   public Step mStep = null;
   public int mDepth = 0;
   private int mNodeType;
   private XMLEvent mOldEvent = null;
   private Attribute mAttribute = null;
   private LayeredMap mNamespaceStack = null;
   private Stack mAncestorStack = null;
   private RecordingXPathStreamObserver mRecorder = null;

   public StreamContext(StreamContextRequirements reqs) {
      super.node = this;
      if (reqs.isNamespaceAxisUsed()) {
         this.mNamespaceStack = new LayeredMap();
      }

      if (reqs.isAncestorAxisUsed()) {
         this.mAncestorStack = new Stack();
      }

      this.mRecorder = new RecordingXPathStreamObserver();
   }

   public StreamContext() {
      super.node = this;
   }

   public StreamContext(XMLEvent e) {
      super.node = this;
      this.setEvent(e);
   }

   public XMLName getNodeName() {
      switch (this.mNodeType) {
         case -2:
            return new Name(this.mAttribute.getName().getLocalName());
         case -1:
            return this.mAttribute.getName();
         default:
            return this.mOldEvent.getName();
      }
   }

   public int getNodeType() {
      return this.mNodeType;
   }

   public String getNodeStringValue() {
      if (this.mAttribute != null) {
         return this.mAttribute.getValue();
      } else if (this.mOldEvent == null) {
         throw new IllegalStateException();
      } else {
         switch (this.mOldEvent.getType()) {
            case 8:
               return ((ProcessingInstructionEvent)this.mOldEvent).getData();
            case 16:
            case 32:
               return ((CharacterDataEvent)this.mOldEvent).getContent();
            default:
               throw new IllegalStateException("unsupported xpath - attempt to convert nodeset to a string");
         }
      }
   }

   public Object getNode() {
      if (this.mAttribute != null) {
         return this.mAttribute;
      } else if (this.mOldEvent != null) {
         return this.mOldEvent;
      } else {
         throw new IllegalStateException();
      }
   }

   public Attribute getAttributeByName(XMLName name) {
      if (this.mAttribute != null) {
         return null;
      } else if (this.mOldEvent != null) {
         return this.mOldEvent.isStartElement() ? ((StartElementEvent)this.mOldEvent).getAttributeByName(name) : null;
      } else {
         throw new IllegalStateException();
      }
   }

   public Attribute getAttribute() {
      return this.mAttribute;
   }

   public XMLEvent getEvent() {
      return this.mOldEvent;
   }

   public void setEvent(XMLEvent e) {
      this.mOldEvent = e;
      this.mAttribute = null;
      this.mNodeType = e.getType();
   }

   public void setAttribute(XMLEvent e, Attribute a) {
      this.mOldEvent = e;
      this.mAttribute = a;
      this.mNodeType = -1;
   }

   public void setNamespace(XMLEvent e, Attribute a) {
      this.mOldEvent = e;
      this.mAttribute = a;
      this.mNodeType = -2;
   }

   void setContextNode(StreamContext ctx) {
      this.mOldEvent = ctx.mOldEvent;
      this.mAttribute = ctx.mAttribute;
      this.mNodeType = ctx.mNodeType;
   }

   public void setRealObserver(XPathStreamObserver obs) {
      this.mRealObserver = obs;
   }

   public XPathStreamObserver getAttributeObserver() {
      return this.mRecorder;
   }

   public void playbackAttributeObservations() {
      this.mRecorder.replayObservations(this.mRealObserver);
      this.mRecorder.clearObservations();
   }

   public Collection getNamespaces() {
      if (this.mNamespaceStack == null) {
         throw new IllegalStateException();
      } else {
         return this.mNamespaceStack.values();
      }
   }

   public Stack getAncestorsAndSelf() {
      if (this.mAncestorStack == null) {
         throw new IllegalStateException();
      } else {
         return this.mAncestorStack;
      }
   }

   boolean observeNext(XMLEvent newEvent) {
      if (newEvent == null) {
         throw new IllegalArgumentException("null event");
      } else {
         this.mAttribute = null;
         switch (newEvent.getType()) {
            case 128:
            case 1024:
            case 2048:
            case 4096:
               return false;
            default:
               if (this.mOldEvent != null) {
                  switch (this.mOldEvent.getType()) {
                     case 2:
                     case 256:
                        ++this.mDepth;
                  }
               }

               switch (newEvent.getType()) {
                  case 2:
                     if (this.mNamespaceStack != null) {
                        this.pushNamespaces((StartElement)newEvent);
                     }
                  case 256:
                     if (this.mAncestorStack != null) {
                        this.mAncestorStack.push(newEvent);
                     }
                     break;
                  case 4:
                     if (this.mNamespaceStack != null && !this.mNamespaceStack.isEmpty()) {
                        this.mNamespaceStack.pop();
                     }
                  case 512:
                     if (this.mAncestorStack != null && !this.mAncestorStack.isEmpty()) {
                        this.mAncestorStack.pop();
                     }

                     --this.mDepth;
               }

               this.setEvent(newEvent);
               return true;
         }
      }
   }

   public String toString() {
      return "StreamContext[node=" + this.getNodeName() + ",pos=" + this.position + ",depth=" + this.mDepth + ",type=" + this.getNodeType() + "]";
   }

   private void pushNamespaces(StartElement elem) {
      this.mNamespaceStack.push();
      AttributeIterator i = elem.getNamespaces();

      while(i.hasNext()) {
         Attribute ns = i.next();
         this.mNamespaceStack.put(ns.getName().getLocalName(), ns);
      }

   }
}
