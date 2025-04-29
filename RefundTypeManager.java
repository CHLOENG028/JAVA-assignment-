
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RefundTypeManager {

    private final Map<String, Map<String, String>> refundData;
    private final List<String> modifiablePredefinedTypes;
    private final List<String> predefinedTypes;
    private final String customerDataFilename = "refund.txt";
    private final String typeDataFilename = "refundType.txt";

    public RefundTypeManager() {
        this.modifiablePredefinedTypes = loadPredefinedTypesFromFile(typeDataFilename);
        this.predefinedTypes = Collections.unmodifiableList(this.modifiablePredefinedTypes);
        this.refundData = new LinkedHashMap<>();

        if (this.predefinedTypes.isEmpty()) {
            System.err.println("CRITICAL: No predefined refund types loaded. Refund data cannot be managed.");
        } else {
            for (String type : this.predefinedTypes) {
                this.refundData.put(type, new LinkedHashMap<>());
            }
            loadCustomerRefundDataFromFile(customerDataFilename);
        }
    }

    private List<String> loadPredefinedTypesFromFile(String filename) {
        File typesFile = new File(filename);
        List<String> loadedTypes = new ArrayList<>();
        List<String> defaultTypes = List.of(
                "No Issues",
                "Minor Damage",
                "Moderate Damage",
                "Serious Damage"
        );

        if (!typesFile.exists()) {
            System.out.println("File '" + filename + "' not found. Creating with default types.");
            try (FileWriter writer = new FileWriter(typesFile)) {
                for (String type : defaultTypes) {
                    writer.write(type + System.lineSeparator());
                }
                System.out.println("Default types written to '" + filename + "'.");
                loadedTypes.addAll(defaultTypes);
            } catch (IOException ioEx) {
                System.err.println("Error: Couldn't create or write default types to file '" + filename + "': " + ioEx.getMessage());
                System.err.println("CRITICAL: Falling back to hardcoded default types.");
                loadedTypes.addAll(defaultTypes);
            }
        } else {
            try {
                List<String> lines = Files.readAllLines(Paths.get(filename));
                for (String line : lines) {
                    String trimmedLine = line.trim();
                    if (!trimmedLine.isEmpty()) {
                        loadedTypes.add(trimmedLine);
                    }
                }
                if (loadedTypes.isEmpty()) {
                    System.err.println("Warning: File '" + filename + "' was found but is empty or contains only whitespace. Using default types.");
                    loadedTypes.addAll(defaultTypes);
                } else {
                    System.out.println("Successfully loaded " + loadedTypes.size() + " refund types from '" + filename + "'.");
                }
            } catch (IOException e) {
                System.err.println("Error reading file '" + filename + "': " + e.getMessage());
                System.err.println("CRITICAL: Falling back to hardcoded default types.");
                loadedTypes.clear();
                loadedTypes.addAll(defaultTypes);
            }
        }
        return loadedTypes;
    }

    private List<String> readLinesFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File '" + filename + "' not found. No data will be loaded.");
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file '" + filename + "': " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void loadCustomerRefundDataFromFile(String filename) {
        if (this.predefinedTypes == null || this.predefinedTypes.isEmpty()) {
            System.err.println("Cannot load customer refund data: Predefined types are not available.");
            return;
        }
        System.out.println("Attempting to load customer refund data from: " + filename);
        List<String> fileLines = readLinesFromFile(filename);
        if (!fileLines.isEmpty()) {
            loadDataFromLines(fileLines);
        } else {
            System.out.println("No customer data loaded (file might be missing, empty, or unreadable).");
        }
    }

    public void loadDataFromLines(List<String> fileLines) {
        for (Map<String, String> customerMap : refundData.values()) {
            customerMap.clear();
        }
        System.out.println("Processing " + fileLines.size() + " lines from file (Format: index|CustomerID|RoomID)...");

        for (String line : fileLines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            try {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    int typeIndex = Integer.parseInt(parts[0].trim());
                    String customerId = parts[1].trim();
                    String roomId = parts[2].trim();

                    if (!customerId.isEmpty() && !roomId.isEmpty()) {
                        if (typeIndex >= 1 && typeIndex <= this.predefinedTypes.size()) {
                            String typeName = this.predefinedTypes.get(typeIndex - 1);
                            Map<String, String> customerMap = refundData.get(typeName);

                            if (customerMap != null) {
                                customerMap.put(customerId, roomId);
                            } else {
                                System.err.println("Internal Error: Inner map for type '" + typeName + "' not initialized.");
                            }
                        } else {
                            System.err.println("Warning: Invalid type index '" + typeIndex + "' found in file line: " + line + ". Max index expected: " + this.predefinedTypes.size());
                        }
                    } else {
                        System.err.println("Warning: Empty customer ID or RoomID found in file line: " + line);
                    }
                } else {
                    System.err.println("Warning: Invalid format (expected 'index|CustomerID|RoomID') in file line: " + line);
                }
            } catch (NumberFormatException e) {
                System.err.println("Warning: Invalid number format for type index in file line: " + line + " (" + e.getMessage() + ")");
            } catch (Exception e) {
                System.err.println("Warning: Error processing file line '" + line + "': " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Finished loading data from file lines.");
    }

    public boolean addCustomerToRefundType(String typeName, String customerId, String roomId) {
        if (typeName == null || customerId == null || roomId == null
                || typeName.trim().isEmpty() || customerId.trim().isEmpty() || roomId.trim().isEmpty()) {
            System.err.println("Error: Invalid input provided (null or empty strings).");
            return false;
        }

        if (!this.refundData.containsKey(typeName)) {
            System.err.println("Error: Refund type '" + typeName + "' does not exist.");
            return false;
        }

        try {
            Map<String, String> customers = this.refundData.get(typeName);
            customers.put(customerId, roomId);
            saveCustomerRefundDataToFile(this.customerDataFilename); // Save after modification
            return true;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating refund data: " + e.getMessage());
            return false;
        }
    }

    public boolean removeCustomerFromRefundType(String typeName, String customerId) {
        if (!this.refundData.containsKey(typeName)) {
            System.err.println("Error: Refund type '" + typeName + "' does not exist.");
            return false;
        }
        try {
            Map<String, String> customers = this.refundData.get(typeName);
            if (customers.containsKey(customerId)) {
                customers.remove(customerId);
                saveCustomerRefundDataToFile(this.customerDataFilename);
                return true;
            } else {
                System.err.println("Error: Customer '" + customerId + "' not found in type '" + typeName + "'.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while removing refund data: " + e.getMessage());
            return false;
        }
    }

    private List<String> getDataForFile() {
        List<String> fileLines = new ArrayList<>();
        for (int i = 0; i < this.predefinedTypes.size(); i++) {
            String typeName = this.predefinedTypes.get(i);
            int typeIndex = i + 1;

            Map<String, String> customerMap = refundData.get(typeName);
            if (customerMap != null && !customerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : customerMap.entrySet()) {
                    String customerId = entry.getKey();
                    String roomId = entry.getValue();
                    if (customerId != null && !customerId.trim().isEmpty() && roomId != null && !roomId.trim().isEmpty()) {
                        fileLines.add(typeIndex + "|" + customerId.trim() + "|" + roomId.trim());
                    }
                }
            }
        }
        return fileLines;
    }

    public void saveCustomerRefundDataToFile(String filename) {
        if (this.predefinedTypes == null || this.predefinedTypes.isEmpty()) {
            System.err.println("Cannot save customer refund data: Predefined types are not available.");
            return;
        }
        File custRefundFile = new File(filename);
        List<String> linesToWrite = getDataForFile();

        try (FileWriter fileWriter = new FileWriter(custRefundFile, false)) {
            for (String line : linesToWrite) {
                fileWriter.write(line + System.lineSeparator());
            }
            System.out.println("Successfully saved customer refund data to '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Error writing customer refund data to file '" + filename + "': " + e.getMessage());
        }
    }

    public Map<String, Map<String, String>> getRefundData() {
        Map<String, Map<String, String>> unmodifiableOuter = new LinkedHashMap<>();
        if (this.refundData != null) {
            for (Map.Entry<String, Map<String, String>> refundTypeEntry : this.refundData.entrySet()) {
                unmodifiableOuter.put(refundTypeEntry.getKey(), Collections.unmodifiableMap(new LinkedHashMap<>(refundTypeEntry.getValue())));
            }
        }
        return Collections.unmodifiableMap(unmodifiableOuter);
    }

    public List<String> getPredefinedTypeNames() {
        return this.predefinedTypes;
    }

    public Map<String, String> getCustomersForType(String typeName) {
        Map<String, String> originalMap = this.refundData.get(typeName);
        return (originalMap != null) ? new LinkedHashMap<>(originalMap) : null;
    }
}
