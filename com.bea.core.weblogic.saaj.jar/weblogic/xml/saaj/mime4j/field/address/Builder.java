package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;
import java.util.Iterator;
import weblogic.xml.saaj.mime4j.decoder.DecoderUtil;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTaddr_spec;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTaddress;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTaddress_list;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTangle_addr;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTdomain;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTgroup_body;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTlocal_part;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTmailbox;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTname_addr;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTphrase;
import weblogic.xml.saaj.mime4j.field.address.parser.ASTroute;
import weblogic.xml.saaj.mime4j.field.address.parser.Node;
import weblogic.xml.saaj.mime4j.field.address.parser.SimpleNode;
import weblogic.xml.saaj.mime4j.field.address.parser.Token;

class Builder {
   private static Builder singleton = new Builder();

   public static Builder getInstance() {
      return singleton;
   }

   public AddressList buildAddressList(ASTaddress_list node) {
      ArrayList list = new ArrayList();

      for(int i = 0; i < node.jjtGetNumChildren(); ++i) {
         ASTaddress childNode = (ASTaddress)node.jjtGetChild(i);
         Address address = this.buildAddress(childNode);
         list.add(address);
      }

      return new AddressList(list, true);
   }

   private Address buildAddress(ASTaddress node) {
      ChildNodeIterator it = new ChildNodeIterator(node);
      Node n = it.nextNode();
      if (n instanceof ASTaddr_spec) {
         return this.buildAddrSpec((ASTaddr_spec)n);
      } else if (n instanceof ASTangle_addr) {
         return this.buildAngleAddr((ASTangle_addr)n);
      } else if (n instanceof ASTphrase) {
         String name = this.buildString((ASTphrase)n, false);
         Node n2 = it.nextNode();
         if (n2 instanceof ASTgroup_body) {
            return new Group(name, this.buildGroupBody((ASTgroup_body)n2));
         } else if (n2 instanceof ASTangle_addr) {
            name = DecoderUtil.decodeEncodedWords(name);
            return new NamedMailbox(name, this.buildAngleAddr((ASTangle_addr)n2));
         } else {
            throw new IllegalStateException();
         }
      } else {
         throw new IllegalStateException();
      }
   }

   private MailboxList buildGroupBody(ASTgroup_body node) {
      ArrayList results = new ArrayList();
      ChildNodeIterator it = new ChildNodeIterator(node);

      while(it.hasNext()) {
         Node n = it.nextNode();
         if (!(n instanceof ASTmailbox)) {
            throw new IllegalStateException();
         }

         results.add(this.buildMailbox((ASTmailbox)n));
      }

      return new MailboxList(results, true);
   }

   private Mailbox buildMailbox(ASTmailbox node) {
      ChildNodeIterator it = new ChildNodeIterator(node);
      Node n = it.nextNode();
      if (n instanceof ASTaddr_spec) {
         return this.buildAddrSpec((ASTaddr_spec)n);
      } else if (n instanceof ASTangle_addr) {
         return this.buildAngleAddr((ASTangle_addr)n);
      } else if (n instanceof ASTname_addr) {
         return this.buildNameAddr((ASTname_addr)n);
      } else {
         throw new IllegalStateException();
      }
   }

   private NamedMailbox buildNameAddr(ASTname_addr node) {
      ChildNodeIterator it = new ChildNodeIterator(node);
      Node n = it.nextNode();
      if (n instanceof ASTphrase) {
         String name = this.buildString((ASTphrase)n, false);
         n = it.nextNode();
         if (n instanceof ASTangle_addr) {
            name = DecoderUtil.decodeEncodedWords(name);
            return new NamedMailbox(name, this.buildAngleAddr((ASTangle_addr)n));
         } else {
            throw new IllegalStateException();
         }
      } else {
         throw new IllegalStateException();
      }
   }

   private Mailbox buildAngleAddr(ASTangle_addr node) {
      ChildNodeIterator it = new ChildNodeIterator(node);
      DomainList route = null;
      Node n = it.nextNode();
      if (n instanceof ASTroute) {
         route = this.buildRoute((ASTroute)n);
         n = it.nextNode();
      } else if (!(n instanceof ASTaddr_spec)) {
         throw new IllegalStateException();
      }

      if (n instanceof ASTaddr_spec) {
         return this.buildAddrSpec(route, (ASTaddr_spec)n);
      } else {
         throw new IllegalStateException();
      }
   }

   private DomainList buildRoute(ASTroute node) {
      ArrayList results = new ArrayList(node.jjtGetNumChildren());
      ChildNodeIterator it = new ChildNodeIterator(node);

      while(it.hasNext()) {
         Node n = it.nextNode();
         if (!(n instanceof ASTdomain)) {
            throw new IllegalStateException();
         }

         results.add(this.buildString((ASTdomain)n, true));
      }

      return new DomainList(results, true);
   }

   private Mailbox buildAddrSpec(ASTaddr_spec node) {
      return this.buildAddrSpec((DomainList)null, node);
   }

   private Mailbox buildAddrSpec(DomainList route, ASTaddr_spec node) {
      ChildNodeIterator it = new ChildNodeIterator(node);
      String localPart = this.buildString((ASTlocal_part)it.nextNode(), true);
      String domain = this.buildString((ASTdomain)it.nextNode(), true);
      return new Mailbox(route, localPart, domain);
   }

   private String buildString(SimpleNode node, boolean stripSpaces) {
      Token head = node.firstToken;
      Token tail = node.lastToken;
      StringBuffer out = new StringBuffer();

      while(head != tail) {
         out.append(head.image);
         head = head.next;
         if (!stripSpaces) {
            this.addSpecials(out, head.specialToken);
         }
      }

      out.append(tail.image);
      return out.toString();
   }

   private void addSpecials(StringBuffer out, Token specialToken) {
      if (specialToken != null) {
         this.addSpecials(out, specialToken.specialToken);
         out.append(specialToken.image);
      }

   }

   private static class ChildNodeIterator implements Iterator {
      private SimpleNode simpleNode;
      private int index;
      private int len;

      public ChildNodeIterator(SimpleNode simpleNode) {
         this.simpleNode = simpleNode;
         this.len = simpleNode.jjtGetNumChildren();
         this.index = 0;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public boolean hasNext() {
         return this.index < this.len;
      }

      public Object next() {
         return this.nextNode();
      }

      public Node nextNode() {
         return this.simpleNode.jjtGetChild(this.index++);
      }
   }
}
