/*
package com.mozammal;

import java.io.*;

public class Main {

  public static void main(String... args) throws IOException {
    FileReader fr = new FileReader("D:/sheet1_files/customer.tbl");
    BufferedReader br = new BufferedReader(fr);

    String line = null;
    int coount = 0;

    while ((line = br.readLine()) != null) {

      final String[] values = line.split("\\|");

      for (String val : values) {
        System.out.print(val + " ");
      }
      coount++;
      if (coount == 5)
          break;
      System.out.println("");
    }
  }
}
*/
