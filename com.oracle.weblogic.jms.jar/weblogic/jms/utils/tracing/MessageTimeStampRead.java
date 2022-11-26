package weblogic.jms.utils.tracing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.Getopt2;

public class MessageTimeStampRead extends MessageTimeStamp implements DataLogInterpreter {
   private Map[] dyeMaps;
   private int[][] pairCounts;
   private List[][] pairLists;
   private long[][] totalTimes;
   private int[] pointCounts;
   private int nFlowPoints;
   private String[] flowPointNames = new String[256];
   private boolean[] isClientPoint = new boolean[256];
   private boolean[] isLastPoint = new boolean[256];
   private boolean[] isFirstPoint = new boolean[256];
   private boolean[] isFirstDyedPoint = new boolean[256];
   private boolean[] hasNoDye = new boolean[256];
   private Map allAggregations = new HashMap();
   private int[] pathNos = new int[256];
   private int nPaths;
   private int nOrderedPoints = 0;
   private int[] orderedPoints;
   private boolean[] seenIt;
   private int skipCount = 0;
   private int recordCount = 0;
   private boolean stats = true;
   private boolean ignoreMissing = false;
   private boolean serverOnly = false;
   private int[] lastDyes;
   private boolean doMedian = false;
   private boolean showCounts = false;
   private boolean dyedOnly = false;

   public static void main(String[] allargs) throws Exception {
      new MessageTimeStampRead(allargs);
   }

   private MessageTimeStampRead(String[] allargs) throws Exception {
      Getopt2 getopt = new Getopt2();
      boolean error = false;
      getopt.setFailOnUnrecognizedOpts(true);
      getopt.addFlag("m", "Calculate Median");
      getopt.addFlag("o", "Server Only");
      getopt.addFlag("d", "Take only records with a dye");
      getopt.addFlag("p", "Points");
      getopt.addOption("s", "Skip", "Skip");

      try {
         getopt.grok(allargs);
      } catch (IllegalArgumentException var24) {
         error = true;
      }

      if (error) {
         System.err.println("Usage: java MessageTimeStampRead [-d] [-m] [-o] [-p] [-s records-to-be-skipped ]");
      } else {
         String[] args = getopt.args();
         if (getopt.hasOption("d")) {
            this.dyedOnly = false;
         }

         if (getopt.hasOption("p")) {
            this.stats = false;
         }

         if (getopt.hasOption("s")) {
            this.skipCount = Integer.parseInt(getopt.getOption("s"));
         }

         if (getopt.hasOption("o")) {
            this.serverOnly = true;
         }

         if (getopt.hasOption("m")) {
            this.doMedian = true;
         }

         DataLogRead.readInit(args);
         int maxFlowPoint = 0;
         int maxPathNo = 1;

         SubBuffer subBuffer;
         while((subBuffer = DataLogRead.readHeader()) != null) {
            String header = subBuffer.toString();
            String[] headerParts = header.split(" ");
            boolean flow = false;
            String name = null;
            boolean client = false;
            boolean lastPoint = false;
            boolean firstPoint = false;
            boolean noDye = false;
            boolean firstDyed = false;
            int pathNo = 1;
            int point = -1;

            for(int i = 0; i < headerParts.length; ++i) {
               if (headerParts[i].equals("FLOW")) {
                  flow = true;
               } else if (headerParts[i].equals("FIRST")) {
                  firstPoint = true;
               } else if (headerParts[i].equals("FIRST_DYED")) {
                  firstDyed = true;
               } else if (headerParts[i].equals("LAST")) {
                  lastPoint = true;
               } else if (headerParts[i].equals("CLIENT")) {
                  client = true;
               } else if (headerParts[i].equals("FREQUENCY")) {
                  flow = false;
               } else if (headerParts[i].equals("NO_DYE")) {
                  noDye = true;
               } else if (headerParts[i].startsWith("PATH")) {
                  pathNo = Integer.parseInt(headerParts[i].substring(4));
               } else {
                  if (headerParts[i].startsWith("AGGREGATION-")) {
                     SubBuffer aggBuffer = DataLogRead.readHeader();
                     if (aggBuffer == null) {
                        throw new AssertionError("Truncated file");
                     }

                     int ncounters = aggBuffer.limit() / 4;
                     int[] aggregationCounters = new int[ncounters];

                     for(int j = 0; j < ncounters; ++j) {
                        aggregationCounters[j] = aggBuffer.getInt(j * 4);
                     }

                     this.allAggregations.put(headerParts[i].substring(headerParts[i].indexOf(45) + 1), aggregationCounters);
                  }

                  if (Character.isDigit(headerParts[i].charAt(0))) {
                     point = Integer.parseInt(headerParts[i]);
                  } else if (name != null) {
                     System.err.println("Ignoring unknown flag: " + headerParts[i]);
                  } else {
                     name = headerParts[i];
                  }
               }
            }

            if (flow) {
               this.flowPointNames[point] = name;
               if (point > maxFlowPoint) {
                  maxFlowPoint = point;
               }

               this.isClientPoint[point] = client;
               this.isLastPoint[point] = lastPoint;
               this.isFirstPoint[point] = firstPoint;
               this.isFirstDyedPoint[point] = firstDyed;
               this.hasNoDye[point] = noDye;
               if (pathNo > maxPathNo) {
                  maxPathNo = pathNo;
               }

               this.pathNos[point] = pathNo;
            }
         }

         this.nPaths = maxPathNo + 1;
         this.nFlowPoints = maxFlowPoint + 1;
         this.pairCounts = new int[this.nFlowPoints][this.nFlowPoints];
         this.pairLists = new List[this.nFlowPoints][this.nFlowPoints];
         this.totalTimes = new long[this.nFlowPoints][this.nFlowPoints];
         this.pointCounts = new int[this.nFlowPoints];
         this.orderedPoints = new int[this.nFlowPoints];
         this.seenIt = new boolean[this.nFlowPoints];
         this.lastDyes = new int[this.nPaths];
         this.dyeMaps = new HashMap[this.nPaths];

         int i;
         for(i = 0; i < this.nPaths; ++i) {
            this.dyeMaps[i] = new HashMap();
         }

         for(i = 0; i < this.nFlowPoints; ++i) {
            this.orderedPoints[i] = -1;
         }

         DataLogRead.read(this);
         if (this.stats) {
            if (this.showCounts) {
               for(i = 0; i < this.nFlowPoints; ++i) {
                  if (this.pointCounts[i] != 0) {
                     System.out.println("The point " + this.pointName(i) + " was hit " + this.pointCounts[i] + " times");
                  }
               }
            }

            int[][] medians = new int[this.nFlowPoints][this.nFlowPoints];
            int medianSumTimePerMessage = 0;
            int averageTimePerMessage;
            int pathNo;
            if (this.doMedian) {
               for(averageTimePerMessage = 0; averageTimePerMessage < this.nFlowPoints; ++averageTimePerMessage) {
                  for(pathNo = 0; pathNo < this.nFlowPoints; ++pathNo) {
                     if (this.pairCounts[averageTimePerMessage][pathNo] != 0) {
                        Object[] tmp = this.pairLists[averageTimePerMessage][pathNo].toArray();
                        Arrays.sort(tmp);
                        medians[averageTimePerMessage][pathNo] = (Integer)tmp[this.pairLists[averageTimePerMessage][pathNo].size() / 2];
                        medianSumTimePerMessage += medians[averageTimePerMessage][pathNo];
                     }
                  }
               }
            }

            if (this.doMedian) {
               System.out.printf("%-40.40s %-8.8s %-8.8s %-8.8s %-4.4s\n", "         Pair", " Count", "  Mean", " Median", "Pct");
               System.out.printf("%-40.40s %-8.8s %-8.8s %-8.8s %-4.4s\n", "========================================", "========================================", "========================================", "========================================", "========================================");
            } else {
               System.out.printf("%-40.40s %-8.8s %-8.8s %-4.4s\n", "         Pair", " Count", "  Mean", "Pct");
               System.out.printf("%-40.40s %-8.8s %-8.8s %-4.4s\n", "========================================", "========================================", "========================================", "========================================");
            }

            averageTimePerMessage = 0;

            int i;
            for(pathNo = 0; pathNo < this.nFlowPoints; ++pathNo) {
               for(i = 0; i < this.nFlowPoints; ++i) {
                  if (this.pairCounts[pathNo][i] != 0) {
                     averageTimePerMessage += (int)(this.totalTimes[pathNo][i] / (long)this.pairCounts[pathNo][i]);
                  }
               }
            }

            int value;
            int usej;
            for(pathNo = 1; pathNo < this.nPaths; ++pathNo) {
               System.out.printf("------------------------------ Path %d ------------------------------\n", pathNo);

               for(i = 0; i < this.nFlowPoints; ++i) {
                  int usei = this.orderedPoints[i];
                  if (usei != -1 && this.getPathNo(usei) == pathNo) {
                     for(value = 0; value < this.nFlowPoints; ++value) {
                        usej = this.orderedPoints[value];
                        if (usej != -1 && this.pairCounts[usei][usej] != 0) {
                           if (this.doMedian) {
                              System.out.printf("%-40.40s %-8.8s %-8.8s %-8.8s %-4.4s\n", this.pointName(usei) + "," + this.pointName(usej), "" + this.pairCounts[usei][usej], "" + this.totalTimes[usei][usej] / (long)this.pairCounts[usei][usej], "" + medians[usei][usej], "" + 100 * medians[usei][usej] / medianSumTimePerMessage);
                           } else {
                              System.out.printf("%-40.40s %-8.8s %-8.8s %-4.4s\n", this.pointName(usei) + "," + this.pointName(usej), "" + this.pairCounts[usei][usej], "" + this.totalTimes[usei][usej] / (long)this.pairCounts[usei][usej], "" + 100L * this.totalTimes[usei][usej] / (long)this.pairCounts[usei][usej] / (long)averageTimePerMessage);
                           }
                        }
                     }
                  }
               }

               System.out.println("");
            }

            Iterator aggregationWalk = this.allAggregations.keySet().iterator();

            while(aggregationWalk.hasNext()) {
               String destinationName = (String)aggregationWalk.next();
               int[] aggregationCounters = (int[])((int[])this.allAggregations.get(destinationName));
               System.out.println("Aggregation table for " + destinationName);
               System.out.printf("%-10.10s %-10.10s\n", "  Value", " Frequency");
               System.out.printf("%-10.10s %-10.10s\n", "========================================", "========================================");

               for(value = 0; value < aggregationCounters.length; ++value) {
                  usej = aggregationCounters[value];
                  if (usej != 0) {
                     System.out.printf("%-10.10s %-10.10s\n", "" + value, "" + usej);
                  }
               }
            }

         }
      }
   }

   public void dataPoint(DataLogRecord current) {
      if (this.recordCount++ >= this.skipCount) {
         int dye = (int)current.data;
         if (!this.stats) {
            System.out.println(this.pointName(current.point) + " " + current.time + " " + current.data);
         }

         if (current.point >= this.nFlowPoints) {
            System.out.println("The point of the current record is invalid: " + current.point);
            System.exit(1);
         }

         if (!this.serverOnly || !this.isClientPoint(current.point)) {
            int pathNo = this.getPathNo(current.point);
            if (this.hasNoDye(current.point)) {
               if (this.dyedOnly) {
                  return;
               }

               dye = this.lastDyes[pathNo];
            } else {
               this.lastDyes[pathNo] = dye;
            }

            DataLogRecord last;
            if (!this.isClientLastPoint(current.point) && (!this.serverOnly || !this.isServerLastPoint(current.point))) {
               last = (DataLogRecord)this.dyeMaps[pathNo].put(new Integer(dye), current);
            } else {
               last = (DataLogRecord)this.dyeMaps[pathNo].remove(new Integer(dye));
            }

            if (!this.seenIt[current.point]) {
               this.seenIt[current.point] = true;
               this.orderedPoints[this.nOrderedPoints++] = current.point;
            }

            if (!this.stats) {
               if (last != null) {
                  System.out.println("Last was " + this.pointName(last.point) + ", current.time - last.time is " + (current.time - last.time));
               }

            } else {
               int var10002;
               if (last != null) {
                  if (current.time < last.time) {
                     System.err.println("Record count is " + this.recordCount + ", current.time is " + current.time + ", last.time is " + last.time);
                     return;
                  }

                  if (this.doMedian) {
                     if (this.pairLists[last.point][current.point] == null) {
                        this.pairLists[last.point][current.point] = new ArrayList();
                     }

                     this.pairLists[last.point][current.point].add(new Integer((int)(current.time - last.time)));
                  }

                  var10002 = this.pairCounts[last.point][current.point]++;
                  long[] var10000 = this.totalTimes[last.point];
                  int var10001 = current.point;
                  var10000[var10001] += current.time - last.time;
               } else if (!this.isFirstPoint(current.point) && (!this.serverOnly || !this.dyedOnly || !this.isServerFirstDyedPoint(current.point))) {
                  if (this.ignoreMissing) {
                     this.dyeMaps[pathNo].remove(new Integer(dye));
                     return;
                  } else {
                     System.err.println("Record count is " + this.recordCount + ", could not find dye " + dye + " at current.point " + this.pointName(current.point));
                     return;
                  }
               }

               var10002 = this.pointCounts[current.point]++;
            }
         }
      }
   }

   public String pointName(int point) {
      if (this.flowPointNames[point] == null) {
         throw new AssertionError("point is not good: " + point);
      } else {
         return this.flowPointNames[point];
      }
   }

   public int pointNumber(String pointName) {
      for(int i = 0; i < this.nFlowPoints; ++i) {
         if (this.flowPointNames[i].equals(pointName)) {
            return i;
         }
      }

      return -1;
   }

   private boolean isClientPoint(int point) {
      return this.isClientPoint[point];
   }

   private boolean isLastPoint(int point) {
      return this.isLastPoint[point];
   }

   private boolean isFirstPoint(int point) {
      return this.isFirstPoint[point];
   }

   private boolean isFirstDyedPoint(int point) {
      return this.isFirstDyedPoint[point];
   }

   private boolean isClientFirstPoint(int point) {
      return this.isClientPoint(point) && this.isFirstPoint(point);
   }

   private boolean isClientLastPoint(int point) {
      return this.isClientPoint(point) && this.isLastPoint(point);
   }

   private boolean isServerLastPoint(int point) {
      return !this.isClientPoint(point) && this.isLastPoint(point);
   }

   private boolean isServerFirstPoint(int point) {
      return !this.isClientPoint(point) && this.isFirstPoint(point);
   }

   private boolean isServerFirstDyedPoint(int point) {
      return !this.isClientPoint(point) && this.isFirstDyedPoint[point];
   }

   private boolean hasNoDye(int point) {
      return this.hasNoDye[point];
   }

   private int getPathNo(int point) {
      return this.pathNos[point];
   }
}
