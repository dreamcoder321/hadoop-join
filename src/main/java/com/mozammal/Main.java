/*
package com.mozammal;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {

  public static void main(String... args) throws IOException {
    FileReader fr = new FileReader("D:/sheet1_files/customer.tbl");
    BufferedReader br = new BufferedReader(fr);
    FileReader fr1 = new FileReader("D:/sheet1_files/orders.tbl");
    BufferedReader br1 = new BufferedReader(fr1);
    Set<String> hashSet = new HashSet<>();
    String line = null;
    int coount = 0;

    while ((line = br.readLine()) != null) {

      final String[] values = line.split("\\|");
      hashSet.add(values[1]);
     for (String val : values) {
        System.out.print(val + " ");
      }
      coount++;
     if (coount == 5) break;
      System.out.println("");
    }

    while ((line = br1.readLine()) != null) {

      final String[] values = line.split("\\|");
      if (hashSet.contains(values[6])) System.out.println("yes");
    }
  }
}
*/
