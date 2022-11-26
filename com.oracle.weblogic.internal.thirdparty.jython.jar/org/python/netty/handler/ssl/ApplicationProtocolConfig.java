package org.python.netty.handler.ssl;

import java.util.Collections;
import java.util.List;
import org.python.netty.util.internal.ObjectUtil;

public final class ApplicationProtocolConfig {
   public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
   private final List supportedProtocols;
   private final Protocol protocol;
   private final SelectorFailureBehavior selectorBehavior;
   private final SelectedListenerFailureBehavior selectedBehavior;

   public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, Iterable supportedProtocols) {
      this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList(supportedProtocols));
   }

   public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, String... supportedProtocols) {
      this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList(supportedProtocols));
   }

   private ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, List supportedProtocols) {
      this.supportedProtocols = Collections.unmodifiableList((List)ObjectUtil.checkNotNull(supportedProtocols, "supportedProtocols"));
      this.protocol = (Protocol)ObjectUtil.checkNotNull(protocol, "protocol");
      this.selectorBehavior = (SelectorFailureBehavior)ObjectUtil.checkNotNull(selectorBehavior, "selectorBehavior");
      this.selectedBehavior = (SelectedListenerFailureBehavior)ObjectUtil.checkNotNull(selectedBehavior, "selectedBehavior");
      if (protocol == ApplicationProtocolConfig.Protocol.NONE) {
         throw new IllegalArgumentException("protocol (" + ApplicationProtocolConfig.Protocol.NONE + ") must not be " + ApplicationProtocolConfig.Protocol.NONE + '.');
      } else if (supportedProtocols.isEmpty()) {
         throw new IllegalArgumentException("supportedProtocols must be not empty");
      }
   }

   private ApplicationProtocolConfig() {
      this.supportedProtocols = Collections.emptyList();
      this.protocol = ApplicationProtocolConfig.Protocol.NONE;
      this.selectorBehavior = ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
      this.selectedBehavior = ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
   }

   public List supportedProtocols() {
      return this.supportedProtocols;
   }

   public Protocol protocol() {
      return this.protocol;
   }

   public SelectorFailureBehavior selectorFailureBehavior() {
      return this.selectorBehavior;
   }

   public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
      return this.selectedBehavior;
   }

   public static enum SelectedListenerFailureBehavior {
      ACCEPT,
      FATAL_ALERT,
      CHOOSE_MY_LAST_PROTOCOL;
   }

   public static enum SelectorFailureBehavior {
      FATAL_ALERT,
      NO_ADVERTISE,
      CHOOSE_MY_LAST_PROTOCOL;
   }

   public static enum Protocol {
      NONE,
      NPN,
      ALPN,
      NPN_AND_ALPN;
   }
}
