package weblogic.wtc.gwt;

public final class RDomainListEntry {
   private TDMRemoteTDomain c;
   private RDomainListEntry n;

   public RDomainListEntry(TDMRemoteTDomain rdom) {
      this.c = rdom;
      this.n = null;
   }

   public void setNext(RDomainListEntry nxt_rdom) {
      this.n = nxt_rdom;
   }

   public RDomainListEntry getNext() {
      return this.n;
   }

   public TDMRemoteTDomain getRDom() {
      return this.c;
   }
}
