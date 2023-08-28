package com.outwit.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;



public class GenricMethods {
	static String Ordernumber=null;
	 public static String generateDynamicOrderNumber() throws Exception {
	        Set<Integer> usedUniqueNumbers = new HashSet<>();
	        Random random = new Random();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	        for (int i = 0; i < 1; i++) {
	            Date now = new Date();
	            int uniqueNumber = generateUniqueNumber(usedUniqueNumbers, random);
	            Ordernumber = "Test_" + dateFormat.format(now) + String.format("%02d", uniqueNumber);

	           
	        }
			return Ordernumber;
	    }




private static int generateUniqueNumber(Set<Integer> usedNumbers, Random random) {
    while (true) {
        int num = random.nextInt(100);
        if (!usedNumbers.contains(num)) {
            usedNumbers.add(num);
            return num;
        }
    }
}

}