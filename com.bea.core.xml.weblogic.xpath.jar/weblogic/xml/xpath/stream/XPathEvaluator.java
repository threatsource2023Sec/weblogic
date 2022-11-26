package weblogic.xml.xpath.stream;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.StreamXPath;
import weblogic.xml.xpath.XPathStreamObserver;
import weblogic.xml.xpath.common.utils.IntStack;

final class XPathEvaluator implements Evaluator, XPathStreamObserver {
   private static final boolean DEBUG = false;
   private List mLiveSteps = new LinkedList();
   private XPathStreamObserver mObserver;
   private int mDepth = -1;
   private boolean mIsFirst = true;
   private IntStack mMatchStack = new IntStack(3, 3);

   public XPathEvaluator(StreamXPath xp, XPathStreamObserver obs) {
      this.mLiveSteps.add(xp.getLocationPath().createMatcherStep());
      this.mObserver = obs;
   }

   public void observe(XMLEvent event) {
      this.mObserver.observe(event);
      switch (event.getType()) {
         case 2:
         case 256:
            this.mMatchStack.push(this.mDepth);
         default:
      }
   }

   public void observeAttribute(StartElement elem, Attribute att) {
      this.mObserver.observeAttribute(elem, att);
   }

   public void observeNamespace(StartElement elem, Attribute att) {
      this.mObserver.observeNamespace(elem, att);
   }

   public void evaluate(StreamContext ctx) {
      this.mDepth = ctx.mDepth;
      ctx.setRealObserver(this);
      XMLEvent event = ctx.getEvent();
      if (!event.isEndElement() && !event.isEndDocument()) {
         boolean hitOne = false;
         ListIterator i = this.mLiveSteps.listIterator();

         while(i.hasNext()) {
            Step step = (Step)i.next();
            ctx.mStep = step;
            switch (this.mIsFirst ? step.matchNew(ctx) : step.match(ctx)) {
               case 101:
                  i.remove();
               case 100:
                  Step next = step.copyNext(ctx);
                  if (next == null) {
                     if (!hitOne) {
                        this.observe((XMLEvent)ctx.getNode());
                        hitOne = true;
                     }
                  } else {
                     this.observeNew(next, ctx, i);
                  }
               case 102:
               default:
                  break;
               case 103:
                  i.remove();
            }
         }

         ctx.playbackAttributeObservations();
      } else if (!this.mMatchStack.isEmpty() && this.mMatchStack.peek() == this.mDepth) {
         this.observe(event);
         this.mMatchStack.pop();
      }

      this.mIsFirst = false;
   }

   private void observeNew(Step step, StreamContext ctx, ListIterator i) {
      ctx.mStep = step;
      switch (step.matchNew(ctx)) {
         case 100:
            i.add(step);
         case 101:
            Step next = step.copyNext(ctx);
            if (next == null) {
               this.observe((XMLEvent)ctx.getNode());
               return;
            }

            this.observeNew(next, ctx, i);
            break;
         case 102:
            i.add(step);
         case 103:
      }

   }
}
