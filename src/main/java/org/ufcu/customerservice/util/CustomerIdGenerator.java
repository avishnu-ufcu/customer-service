package org.ufcu.customerservice.util;

public class CustomerIdGenerator {
    public static String generateCustomerId(){
        return "CUST-" + System.currentTimeMillis();
    }
}
