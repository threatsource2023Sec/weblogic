package weblogic.corba.j2ee.naming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import javax.naming.InvalidNameException;

public class EndPointList implements Iterable {
   private LoadBalancingType loadBalancingType;
   private int current = 0;
   private EndPointSelector[] endPointList;
   private static final NumberGenerator RANDOM_NUMBER_GENERATOR = new RandomNumberGenerator();
   private static NumberGenerator numberGenerator;

   boolean isUsingRandomLoadBalancing() {
      return this.loadBalancingType == EndPointList.LoadBalancingType.random;
   }

   public final EndPointSelector getStartingEndPoint() {
      return this.endPointList[this.current];
   }

   public EndPointIterator iterator() {
      return new EndPointIterator(this.getEndPointIndices());
   }

   private int[] getEndPointIndices() {
      return this.loadBalancingType.getEndPointIndices(this.endPointList.length, this.current);
   }

   public static EndPointList createEndPointList(String url) throws InvalidNameException {
      if (url == null) {
         throw new InvalidNameException("url is null");
      } else {
         String protocol = NameParser.getUrlClientProtocol(url);
         if ("complex".equals(protocol)) {
            return parseComplexURL(url);
         } else {
            return protocol != null ? parseSimpleUrl(protocol, url) : null;
         }
      }
   }

   private static EndPointList parseSimpleUrl(String protocol, String url) throws InvalidNameException {
      String endPointList = url.substring(url.indexOf(58) + 1);
      if (!endPointList.startsWith("//")) {
         throw new InvalidNameException("url does not contain //");
      } else {
         endPointList = endPointList.substring(2);
         LoadBalancingType loadBalancingType = selectLoadBalancingType(endPointList);
         endPointList = endPointList.replace('|', ',');
         List endPoints = new ArrayList();
         StringTokenizer st = new StringTokenizer(endPointList, ",");

         while(st.hasMoreTokens()) {
            endPoints.add(EndPointSelector.createSimpleEndPoint(protocol, st.nextToken()));
         }

         return new EndPointList(loadBalancingType, endPoints);
      }
   }

   private static LoadBalancingType selectLoadBalancingType(String addressList) {
      return addressList.contains("|") ? EndPointList.LoadBalancingType.random : EndPointList.LoadBalancingType.round_robin;
   }

   private EndPointList(LoadBalancingType loadBalancingType, List endPointList) {
      this.loadBalancingType = loadBalancingType;
      this.endPointList = (EndPointSelector[])endPointList.toArray(new EndPointSelector[endPointList.size()]);
      this.randomizeStart();
   }

   private void randomizeStart() {
      this.current = (int)Math.round(numberGenerator.getNextNumber() * (double)this.endPointList.length + 0.5) - 1;
   }

   private static EndPointList parseComplexURL(String url) throws InvalidNameException {
      String urlType = url.split(":", 2)[0];
      String body = url.split(":", 2)[1];
      String addressList;
      String serviceName;
      if (urlType.equalsIgnoreCase("corbaloc")) {
         if (!body.contains("/")) {
            throw new InvalidNameException("bad corbaloc syntax: no service name specified");
         }

         addressList = body.split("/")[0];
         serviceName = body.split("/")[1];
      } else {
         addressList = body.split("#")[0];
         if (!addressList.contains("/")) {
            serviceName = "NameService";
         } else {
            addressList = addressList.split("/")[0];
            serviceName = addressList.split("/")[1];
         }
      }

      LoadBalancingType loadBalancingType = selectLoadBalancingType(addressList);
      addressList = addressList.replace('|', ',');
      List endPoints = new ArrayList();
      StringTokenizer st = new StringTokenizer(addressList, ",");

      while(st.hasMoreTokens()) {
         endPoints.add(EndPointSelector.createServiceEndPoint(serviceName, st.nextToken()));
      }

      return new EndPointList(loadBalancingType, endPoints);
   }

   public String toString() {
      return Arrays.toString(this.endPointList);
   }

   static void setNumberGenerator(NumberGenerator numberGenerator) {
      EndPointList.numberGenerator = numberGenerator;
   }

   static void resetNumberGenerator() {
      numberGenerator = RANDOM_NUMBER_GENERATOR;
   }

   static {
      numberGenerator = RANDOM_NUMBER_GENERATOR;
   }

   class EndPointIterator implements Iterator {
      private int[] endPointIndices;
      private int nextIndex;

      EndPointIterator(int[] endPointIndices) {
         this.endPointIndices = endPointIndices;
      }

      public boolean hasNext() {
         return this.nextIndex < this.endPointIndices.length;
      }

      public EndPointSelector next() {
         if (this.nextIndex >= this.endPointIndices.length) {
            throw new NoSuchElementException();
         } else {
            return EndPointList.this.endPointList[this.endPointIndices[this.nextIndex++]];
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("remove");
      }

      public void selectCurrentAsStart() {
         if (this.nextIndex > 0) {
            EndPointList.this.current = this.endPointIndices[this.nextIndex - 1];
         }

      }

      public void selectNextAsStart() {
         if (this.nextIndex > 0) {
            EndPointList.this.current = this.endPointIndices[Math.min(this.nextIndex, this.endPointIndices.length - 1)];
         }

      }
   }

   private static class RandomNumberGenerator implements NumberGenerator {
      private RandomNumberGenerator() {
      }

      public double getNextNumber() {
         return Math.random();
      }

      // $FF: synthetic method
      RandomNumberGenerator(Object x0) {
         this();
      }
   }

   interface NumberGenerator {
      double getNextNumber();
   }

   static enum LoadBalancingType {
      round_robin {
         int[] getEndPointIndices(int length, int first) {
            return EndPointList.LoadBalancingType.createOrderedArray(length, first);
         }
      },
      random {
         int[] getEndPointIndices(int length, int first) {
            int[] result = EndPointList.LoadBalancingType.createOrderedArray(length, 0);
            this.shuffle(result);
            this.swapElements(result, 0, this.getIndexOf(result, first));
            return result;
         }

         private void shuffle(int[] array) {
            Random random = new Random();

            for(int i = array.length - 1; i > 0; --i) {
               this.swapElements(array, i, random.nextInt(i + 1));
            }

         }

         private void swapElements(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
         }

         private int getIndexOf(int[] array, int value) {
            int offset;
            for(offset = 0; offset < array.length && array[offset] != value; ++offset) {
            }

            return offset;
         }
      };

      private LoadBalancingType() {
      }

      private static int[] createOrderedArray(int length, int first) {
         int[] result = new int[length];

         for(int i = 0; i < result.length; ++i) {
            result[i] = (first + i) % result.length;
         }

         return result;
      }

      abstract int[] getEndPointIndices(int var1, int var2);

      // $FF: synthetic method
      LoadBalancingType(Object x2) {
         this();
      }
   }
}
