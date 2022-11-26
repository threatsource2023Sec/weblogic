package weblogic.xml.saaj.mime4j.field.address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.xml.saaj.mime4j.field.address.parser.AddressListParser;
import weblogic.xml.saaj.mime4j.field.address.parser.ParseException;

public class AddressList {
   private ArrayList addresses;

   public AddressList(ArrayList addresses, boolean dontCopy) {
      if (addresses != null) {
         this.addresses = dontCopy ? addresses : (ArrayList)addresses.clone();
      } else {
         this.addresses = new ArrayList(0);
      }

   }

   public Iterator iterator() {
      return new Iterator() {
         private int pos = 0;

         public void remove() {
            throw new UnsupportedOperationException();
         }

         public boolean hasNext() {
            return this.pos < AddressList.this.size();
         }

         public Object next() {
            return AddressList.this.get(this.pos++);
         }
      };
   }

   public int size() {
      return this.addresses.size();
   }

   public Address get(int index) {
      if (0 <= index && this.size() > index) {
         return (Address)this.addresses.get(index);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public MailboxList flatten() {
      boolean groupDetected = false;

      for(int i = 0; i < this.size(); ++i) {
         if (!(this.get(i) instanceof Mailbox)) {
            groupDetected = true;
            break;
         }
      }

      if (!groupDetected) {
         return new MailboxList(this.addresses, true);
      } else {
         ArrayList results = new ArrayList();

         for(int i = 0; i < this.size(); ++i) {
            Address addr = this.get(i);
            addr.addMailboxesTo(results);
         }

         return new MailboxList(results, false);
      }
   }

   public void print() {
      for(int i = 0; i < this.size(); ++i) {
         Address addr = this.get(i);
         System.out.println(addr.toString());
      }

   }

   public static AddressList parse(String rawAddressList) throws ParseException {
      AddressListParser parser = new AddressListParser(new StringReader(rawAddressList));
      return Builder.getInstance().buildAddressList(parser.parse());
   }

   public static void main(String[] args) throws Exception {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      while(true) {
         try {
            System.out.print("> ");
            String line = reader.readLine();
            if (line.length() == 0 || line.toLowerCase().equals("exit") || line.toLowerCase().equals("quit")) {
               System.out.println("Goodbye.");
               return;
            }

            AddressList list = parse(line);
            list.print();
         } catch (Exception var4) {
            var4.printStackTrace();
            Thread.sleep(300L);
         }
      }
   }
}
