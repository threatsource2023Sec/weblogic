package weblogic.xml.saaj.mime4j.field.address;

public class NamedMailbox extends Mailbox {
   private String name;

   public NamedMailbox(String name, String localPart, String domain) {
      super(localPart, domain);
      this.name = name;
   }

   public NamedMailbox(String name, DomainList route, String localPart, String domain) {
      super(route, localPart, domain);
      this.name = name;
   }

   public NamedMailbox(String name, Mailbox baseMailbox) {
      super(baseMailbox.getRoute(), baseMailbox.getLocalPart(), baseMailbox.getDomain());
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String getAddressString(boolean includeRoute) {
      return (this.name == null ? "" : this.name + " ") + super.getAddressString(includeRoute);
   }
}
