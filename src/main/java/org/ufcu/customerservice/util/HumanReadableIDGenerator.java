//package org.ufcu.customerservice.util;
//import java.time.Instant;
//import java.util.UUID;
//
//public class HumanReadableIDGenerator {
//
//    private static final String ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
//
//    public static String generateHumanReadableID(String prefix, UUID uuid) {
//
//        long timestamp = Instant.now().toEpochMilli();
//
//        String timePart = encodeBase32(timestamp).substring(0, 8);
//
//        String randomPart =
//                encodeBase32(uuid.getLeastSignificantBits()).substring(0, 4);
//
//        return prefix + "-" + timePart + "-" + randomPart;
//    }
//
//    private static String encodeBase32(long value) {
//
//        StringBuilder sb = new StringBuilder();
//
//        while (value > 0) {
//            int index = (int) (value & 31);
//            sb.append(ALPHABET.charAt(index));
//            value >>= 5;
//
//        }
//
//        return sb.reverse().toString();
//    }
//}
