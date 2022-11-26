package weblogic.entitlement.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import weblogic.entitlement.data.EResource;
import weblogic.entitlement.data.ERole;
import weblogic.entitlement.data.ERoleId;
import weblogic.entitlement.data.ldap.EData;
import weblogic.entitlement.expression.EExpression;
import weblogic.entitlement.parser.Parser;
import weblogic.utils.encoders.BASE64Encoder;

public class LdiftWriter {
   private static Escaping escaper;
   private static BASE64Encoder b64Encoder;
   private static final String ROLE_TYPE = "ERole";
   private static final String RESOURCE_TYPE = "EResource";
   private static final String PREDICATE_TYPE = "EPredicate";
   private static final String PROFILE_TYPE = "EProfile";
   private static final String PROF_DEF_TYPE = "EProfileDefinition";
   private static final String PROF_INST_TYPE = "EProfileInstance";
   private static String[] TYPES;
   private Writer out;

   public LdiftWriter(Writer out) {
      if (out == null) {
         throw new NullPointerException("null writer");
      } else {
         this.out = out;
      }
   }

   public void writeHeader() throws IOException {
      this.out.write("dn: dc=@domain@\n");
      this.out.write("objectclass: top\n");
      this.out.write("objectclass: domain\n");
      this.out.write("dc: @domain@\n");
      this.out.write("\n");
      this.out.write("dn: ou=@realm@,dc=@domain@\n");
      this.out.write("objectclass: top\n");
      this.out.write("objectclass: organizationalUnit\n");
      this.out.write("ou: @realm@\n");

      for(int i = 0; i < TYPES.length; ++i) {
         this.out.write("\n");
         this.out.write("dn: ou=" + TYPES[i] + ",ou=@realm@,dc=@domain@\n");
         this.out.write("objectclass: top\n");
         this.out.write("objectclass: organizationalUnit\n");
         this.out.write("ou: " + TYPES[i] + "\n");
      }

   }

   public void write(EResource resource) throws IOException {
      String name = escaper.escapeString(resource.getName());
      this.write("EResource", name, resource.getExpression(), (String)null);
   }

   public void write(ERole role, String aux) throws IOException {
      String name = EData.PK2Name((ERoleId)role.getPrimaryKey());
      this.write("ERole", name, role.getExpression(), aux);
   }

   public void writePredicate(String name) throws IOException {
      this.write("EPredicate", name);
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void writeln() throws IOException {
      this.out.write(10);
   }

   private void write(String type, String name) throws IOException {
      this.out.write("\n");
      this.out.write("dn: cn=" + name + ",ou=" + type + ",ou=@realm@,dc=@domain@\n");
      this.out.write("objectclass: top\n");
      this.out.write("objectclass: " + type + "\n");
      this.out.write("cn: " + name + "\n");
   }

   private void write(String type, String name, EExpression expr, String eaux) throws IOException {
      this.write(type, name);
      this.out.write("EExpr:: " + uuEncode(expr) + "\n");
      if (eaux != null && eaux.length() > 0) {
         this.out.write("EAux:: " + uuEncode(eaux) + "\n");
      }

   }

   private static String uuEncode(EExpression expr) throws UnsupportedEncodingException {
      String exprStr = expr == null ? "" : expr.serialize();
      return b64Encoder.encodeBuffer(exprStr.getBytes("UTF8"));
   }

   private static String uuEncode(String eaux) throws UnsupportedEncodingException {
      String eauxStr = eaux == null ? "" : eaux;
      return b64Encoder.encodeBuffer(eauxStr.getBytes("UTF8"));
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 2) {
         System.out.println("LDIFT Writer: Convert XML doc describing resource and role policies to LDIFT used in WLS7.x bootstrap");
         System.out.println("Usage: java weblogic.entitlement.util.LdiftWriter <xml_in_file> <ldift_out_file>");
         throw new IllegalArgumentException("Number of arguments should be 2");
      } else {
         String xmlFileName = args[0];
         String ldifFileName = args[1];
         XMLProcessor reader = null;
         FileInputStream fis = null;
         LdiftWriter writer = null;

         try {
            fis = new FileInputStream(xmlFileName);
            reader = new XMLProcessor(fis);
         } catch (FileNotFoundException var9) {
            System.out.println("Cannot find xml file: " + xmlFileName);
            throw new IllegalArgumentException("Invalid xml file: " + xmlFileName);
         } catch (Exception var10) {
            System.out.println("Failed to read policies from xml file: " + xmlFileName + ". Make sure file xml format is valid\n" + var10.getMessage());
            throw new IllegalArgumentException("Invalid xml file: " + xmlFileName);
         }

         try {
            writer = new LdiftWriter(new FileWriter(ldifFileName));
         } catch (Exception var8) {
            System.out.println("Failed to open output file: " + ldifFileName);
            throw new IllegalArgumentException("Invalid output file: " + ldifFileName);
         }

         try {
            writer.writeHeader();
            reader.writeElements(writer);
            writer.close();
            fis.close();
         } catch (Exception var7) {
            System.out.println("Failed to convert policies to ldift format. Make sure " + xmlFileName + " contains valid policies.\n" + var7.getMessage());
            throw new IllegalArgumentException("Invalid xml file: " + xmlFileName);
         }
      }
   }

   public static void unitTest(String outFileName) throws Exception {
      EResource[] resources = new EResource[]{new EResource("myresource:1", (EExpression)null), new EResource("myresource:1", Parser.parseResourceExpression("Usr(me)"))};
      ERole[] roles = new ERole[]{new ERole("myresource:1", "myrole@1", Parser.parseRoleExpression("Grp(me)")), new ERole("myresource:1", "myrole@1", Parser.parseRoleExpression("Grp(me)"))};
      String[] predicates = new String[]{"weblogic.entitlement.rules.TimePredicate", "weblogic.entitlement.rules.InDevelopmentMode"};
      LdiftWriter writer = new LdiftWriter(new FileWriter(outFileName));
      writer.writeHeader();

      int i;
      for(i = 0; i < resources.length; ++i) {
         writer.write(resources[i]);
      }

      for(i = 0; i < roles.length; ++i) {
         writer.write(roles[i], "aux");
      }

      for(i = 0; i < predicates.length; ++i) {
         writer.writePredicate(predicates[i]);
      }

      writer.close();
   }

   static {
      escaper = EData.escaper;
      b64Encoder = new BASE64Encoder();
      TYPES = new String[]{"ERole", "EResource", "EPredicate", "EProfile", "EProfileDefinition", "EProfileInstance"};
   }
}
