package javax.faces.view.facelets;

import javax.faces.view.BehaviorHolderAttachedObjectHandler;

public class BehaviorHandler extends FaceletsAttachedObjectHandler implements BehaviorHolderAttachedObjectHandler {
   private final TagAttribute event;
   private String behaviorId;
   private TagHandlerDelegate helper;

   public BehaviorHandler(BehaviorConfig config) {
      super(config);
      this.behaviorId = config.getBehaviorId();
      this.event = this.getAttribute("event");
      if (null != this.event && !this.event.isLiteral()) {
         throw new TagException(this.tag, "The 'event' attribute for behavior tag must be a literal");
      }
   }

   public TagAttribute getEvent() {
      return this.event;
   }

   public String getEventName() {
      return null != this.getEvent() ? this.getEvent().getValue() : null;
   }

   protected TagHandlerDelegate getTagHandlerDelegate() {
      if (null == this.helper) {
         this.helper = this.delegateFactory.createBehaviorHandlerDelegate(this);
      }

      return this.helper;
   }

   public String getBehaviorId() {
      return this.behaviorId;
   }
}
