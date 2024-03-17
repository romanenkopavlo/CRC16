package fr.btsciel;

public class CRC16 {
    public static int stdPoly = 0xA001;
    public static int initialValue = 0xffff;
    public static String hexaRegex = "([0-9a-fA-F][0-9a-fA-F]|[a-fA-F][0-9a-fA-F])+";
    public static String chaineHexa;
    public static int crc16;


    public static void main(String[] args) {
        System.out.print("Chaine d'octets en hexa pour le calcul du CRC16: ");
        chaineHexa = new String(In.readString());

        if (formatage(chaineHexa) != null) {
            crc16 = calculCRC16(formatage(chaineHexa), initialValue, stdPoly);
            byte [] trame = formatage(chaineHexa);
            for (int i = 0; i < trame.length; i++) {
                if (i != trame.length - 1 && i % 2 == 0) {
                    System.out.print("|" + trame[i] + "|");
                } else if (i != trame.length - 1 && i % 2 != 0){
                    System.out.print(trame[i]);
                } else {
                    System.out.print(trame[i] + "| ");
                }
            }
            System.out.print("Le CRC16 = " + crc16 + " (0x" + Integer.toHexString(crc16) + ")");
        } else {
            System.out.print("Un mauvais format de saisie");
        }
    }


    public static int calculCRC16(byte [] octets, int valeurInitiale, int polynomme) {
        byte byteToTreat;
        for (int i = 0; i < octets.length; i++) {
            byteToTreat = (byte) (octets[i] & 0xff);
            valeurInitiale = valeurInitiale ^ byteToTreat;
            for (int j = 0; j < 8; j++) {
                if (valeurInitiale % 2 == 0) {
                    valeurInitiale = valeurInitiale / 2;
                } else {
                    valeurInitiale = (valeurInitiale / 2) ^ polynomme;
                }
            }
        }
        return valeurInitiale;
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
        if (trame.matches(hexaRegex)) {
            int size = trame.length();
            if (size % 2 != 0) {
                trame += "0";
            }
            return hexStringEnByteArray(trame);
        }
        return null;
    }
}