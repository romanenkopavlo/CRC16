package fr.btsciel;

public class CRC16 {
    public static int stdPoly = 0xA001;
    public static int initialValue = 0xffff;
    public static void main(String[] args) {
        System.out.println(calculCRC16(formatage("0103"), initialValue, stdPoly));
    }
    public static int calculCRC16(byte [] octets, int valeurInitiale, int polynomme) {
        byte byteToTreat;
        for (int i = 0; i < octets.length; i++) {
            byteToTreat = octets[i];
            initialValue += byteToTreat;
            for (int j = 0; j < 8; j++) {
                if (initialValue % 2 == 0) {
                    initialValue = initialValue / 2;
                } else {
                    initialValue = (initialValue / 2) ^ stdPoly;
                }
            }
        }

        return initialValue;
    }
    public static byte[] hexStringEnByteArray(String message) {
        int size = message.length();
        byte[] data = new byte[size / 2];
        for (int i = 0; i < size; i+=2) {
            data[i / 2] = (byte) ((Character.digit(message.charAt(i), 16) << 4) +
            Character.digit(message.charAt(i + 1), 16));
        }
        return data;
    }
    public static byte[] formatage(String trame) {
        int size = trame.length();
        if (size % 2 != 0) {
            trame += "0";
        }
        return hexStringEnByteArray(trame);
    }
}