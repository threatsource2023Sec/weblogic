package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;

public class Mailbox extends Address {
   private DomainList route;
   private String localPart;
   private String domain;

   public Mailbox(String localPart, String domain) {
      this((DomainList)null, localPart, domain);
   }

   public Mailbox(DomainList route, String localPart, String domain) {
      this.route = route;
      this.localPart = localPart;
      this.domain = domain;
   }

   public DomainList getRoute() {
      return this.route;
   }

   public String getLocalPart() {
      return this.localPart;
   }

   public String getDomain() {
      return this.domain;
   }

   public String getAddressString() {
      return this.getAddressString(false);
   }

   public String getAddressString(boolean includeRoute) {
      return "<" + (includeRoute && this.route != null ? this.route.toRouteString() + ":" : "") + this.localPart + (this.domain == null ? "" : "@") + this.domain + ">";
   }

   protected final void doAddMailboxesTo(ArrayList results) {
      results.add(this);
   }

   public String toString() {
      return this.getAddressString();
   }
}
