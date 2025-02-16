import java.io.*;
import java.util.*;

class ZipCodeLookup {
    private static final String CSV_FILE = "ZipCodes_sorted.csv"; // Change this to your actual file path
    private static List<String[]> zipCodeList = new ArrayList<>();

    public static void main(String[] args) {
        loadZipCodeData();


        String postalCode = "72522";

        String[] result = binarySearch(postalCode);
        if (result != null) {
            System.out.println("City: " + result[0] + ", State: " + result[1]);
        } else {
            System.out.println("Postal code not found.");
        }
    }

    private static void loadZipCodeData() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    zipCodeList.add(new String[]{parts[0].trim(), parts[1].trim(), parts[2].trim()});
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    private static String[] binarySearch(String postalCode) {
        int left = 0, right = zipCodeList.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midPostalCode = zipCodeList.get(mid)[0];
            int cmp = midPostalCode.compareTo(postalCode);
            
            if (cmp == 0) {
                return new String[]{zipCodeList.get(mid)[1], zipCodeList.get(mid)[2]};
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
