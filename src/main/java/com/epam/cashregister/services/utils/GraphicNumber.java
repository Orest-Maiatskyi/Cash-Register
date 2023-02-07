package com.epam.cashregister.services.utils;

import java.util.ArrayList;

public class GraphicNumber {

    private static GraphicNumber instance;
    private final ArrayList<String[]> graphicNumbers = new ArrayList<>();

     {
         String[] slices = new String[7];

         slices = new String[7];
         slices[0] = "  ***  ";
         slices[1] = " *   * ";
         slices[2] = "*     *";
         slices[3] = "*     *";
         slices[4] = "*     *";
         slices[5] = " *   * ";
         slices[6] = "  ***  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "   *   ";
         slices[1] = "  **   ";
         slices[2] = "   *   ";
         slices[3] = "   *   ";
         slices[4] = "   *   ";
         slices[5] = "   *   ";
         slices[6] = "  ***  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "  ***  ";
         slices[1] = " *   * ";
         slices[2] = " *  *  ";
         slices[3] = "   *   ";
         slices[4] = "  *    ";
         slices[5] = " *     ";
         slices[6] = " ***** ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "  ***  ";
         slices[1] = " *   * ";
         slices[2] = "     * ";
         slices[3] = "   **  ";
         slices[4] = "     * ";
         slices[5] = " *   * ";
         slices[6] = "  ***  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "    *  ";
         slices[1] = "   **  ";
         slices[2] = "  * *  ";
         slices[3] = " *  *  ";
         slices[4] = " ******";
         slices[5] = "    *  ";
         slices[6] = "    *  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "  **** ";
         slices[1] = " *     ";
         slices[2] = " *     ";
         slices[3] = "  ***  ";
         slices[4] = "     * ";
         slices[5] = "     * ";
         slices[6] = " ****  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "     * ";
         slices[1] = "    *  ";
         slices[2] = "   *   ";
         slices[3] = "  *    ";
         slices[4] = " *   * ";
         slices[5] = " *   * ";
         slices[6] = "  ***  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = " ***** ";
         slices[1] = "     * ";
         slices[2] = "    *  ";
         slices[3] = "   *   ";
         slices[4] = "  *    ";
         slices[5] = " *     ";
         slices[6] = " *     ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "  ***  ";
         slices[1] = " *   * ";
         slices[2] = " *   * ";
         slices[3] = "  ***  ";
         slices[4] = " *   * ";
         slices[5] = " *   * ";
         slices[6] = "  ***  ";
         graphicNumbers.add(slices);

         slices = new String[7];
         slices[0] = "  **** ";
         slices[1] = " *   * ";
         slices[2] = " *   * ";
         slices[3] = "  **** ";
         slices[4] = "     * ";
         slices[5] = "     * ";
         slices[6] = "     * ";
         graphicNumbers.add(slices);
    }

    private GraphicNumber() { }

    public static GraphicNumber getInstance() {
        if (instance == null) instance = new GraphicNumber();
        return instance;
    }

    private int getNumericByIndex(int num, int index) {
        return Character.getNumericValue(Integer.toString(num).charAt(index));
    }

    public String render(int number) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < Integer.toString(number).length(); i++)
                stringBuilder.append(graphicNumbers.get(getNumericByIndex(number, i))[j]);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        GraphicNumber graphicNumber = GraphicNumber.getInstance();
        System.out.println(graphicNumber.render(1234567890));
    }
}
