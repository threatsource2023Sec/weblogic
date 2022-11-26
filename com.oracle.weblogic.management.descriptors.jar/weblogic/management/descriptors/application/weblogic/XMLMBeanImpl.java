package weblogic.management.descriptors.application.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class XMLMBeanImpl extends XMLElementMBeanDelegate implements XMLMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_entityMappings = false;
   private List entityMappings;
   private boolean isSet_parserFactory = false;
   private ParserFactoryMBean parserFactory;

   public EntityMappingMBean[] getEntityMappings() {
      if (this.entityMappings == null) {
         return new EntityMappingMBean[0];
      } else {
         EntityMappingMBean[] result = new EntityMappingMBean[this.entityMappings.size()];
         result = (EntityMappingMBean[])((EntityMappingMBean[])this.entityMappings.toArray(result));
         return result;
      }
   }

   public void setEntityMappings(EntityMappingMBean[] value) {
      EntityMappingMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEntityMappings();
      }

      this.isSet_entityMappings = true;
      if (this.entityMappings == null) {
         this.entityMappings = Collections.synchronizedList(new ArrayList());
      } else {
         this.entityMappings.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.entityMappings.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EntityMappings", _oldVal, this.getEntityMappings());
      }

   }

   public void addEntityMapping(EntityMappingMBean value) {
      this.isSet_entityMappings = true;
      if (this.entityMappings == null) {
         this.entityMappings = Collections.synchronizedList(new ArrayList());
      }

      this.entityMappings.add(value);
   }

   public void removeEntityMapping(EntityMappingMBean value) {
      if (this.entityMappings != null) {
         this.entityMappings.remove(value);
      }
   }

   public ParserFactoryMBean getParserFactory() {
      return this.parserFactory;
   }

   public void setParserFactory(ParserFactoryMBean value) {
      ParserFactoryMBean old = this.parserFactory;
      this.parserFactory = value;
      this.isSet_parserFactory = value != null;
      this.checkChange("parserFactory", old, this.parserFactory);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<xml");
      result.append(">\n");
      if (null != this.getParserFactory()) {
         result.append(this.getParserFactory().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getEntityMappings()) {
         for(int i = 0; i < this.getEntityMappings().length; ++i) {
            result.append(this.getEntityMappings()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</xml>\n");
      return result.toString();
   }
}
