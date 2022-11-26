package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.groups.Default;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class GroupConversionStaxBuilder extends AbstractStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String GROUP_CONVERSION_TYPE_QNAME_LOCAL_PART = "convert-group";
   private static final QName FROM_QNAME = new QName("from");
   private static final QName TO_QNAME = new QName("to");
   private static final String DEFAULT_GROUP_NAME = Default.class.getName();
   private final ClassLoadingHelper classLoadingHelper;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final Map groupConversionRules;

   GroupConversionStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
      this.classLoadingHelper = classLoadingHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.groupConversionRules = new HashMap();
   }

   protected String getAcceptableQName() {
      return "convert-group";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) {
      StartElement startElement = xmlEvent.asStartElement();
      String from = (String)this.readAttribute(startElement, FROM_QNAME).orElse(DEFAULT_GROUP_NAME);
      String to = (String)this.readAttribute(startElement, TO_QNAME).get();
      this.groupConversionRules.merge(from, Collections.singletonList(to), (v1, v2) -> {
         return (List)Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList());
      });
   }

   Map build() {
      String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");
      Map resultingMapping = (Map)this.groupConversionRules.entrySet().stream().collect(Collectors.groupingBy((entryx) -> {
         return this.classLoadingHelper.loadClass((String)entryx.getKey(), defaultPackage);
      }, Collectors.collectingAndThen(Collectors.toList(), (entries) -> {
         return (List)entries.stream().flatMap((entry) -> {
            return ((List)entry.getValue()).stream();
         }).map((className) -> {
            return this.classLoadingHelper.loadClass(className, defaultPackage);
         }).collect(Collectors.toList());
      })));
      Iterator var3 = resultingMapping.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var3.hasNext()) {
            return (Map)resultingMapping.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (entryx) -> {
               return (Class)((List)entryx.getValue()).get(0);
            }));
         }

         entry = (Map.Entry)var3.next();
      } while(((List)entry.getValue()).size() <= 1);

      throw LOG.getMultipleGroupConversionsForSameSourceException((Class)entry.getKey(), (Collection)entry.getValue());
   }
}
