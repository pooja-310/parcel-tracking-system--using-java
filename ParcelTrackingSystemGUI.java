import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// User Authentication and Registration Module
class UserAuthentication {
    private Map<String, String> userDatabase = new HashMap<>(); // Store userId -> password

    public void registerUser(String userId, String password) {
        if (userDatabase.containsKey(userId)) {
            JOptionPane.showMessageDialog(null, "User ID already exists. Please try a different ID.");
        } else {
            userDatabase.put(userId, password);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        }
    }

    public boolean authenticate(String userId, String password) {
        return userDatabase.containsKey(userId) && userDatabase.get(userId).equals(password);
    }
}

// Parcel Information Module
class Parcel {
    private String parcelId;
    private String sender;
    private String recipient;
    private String status;
    private String location;

    public Parcel(String parcelId, String sender, String recipient) {
        this.parcelId = parcelId;
        this.sender = sender;
        this.recipient = recipient;
        this.status = "Dispatched";
        this.location = "Warehouse";
    }

    public String getParcelId() {
        return parcelId;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public void updateStatus(String status, String location) {
        this.status = status;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Parcel ID: " + parcelId + "\nSender: " + sender + "\nRecipient: " + recipient + "\nStatus: " + status + "\nLocation: " + location;
    }
}

class ParcelInformation {
    private Map<String, Parcel> parcelDatabase = new HashMap<>();

    public void addParcel(String parcelId, String sender, String recipient) {
        Parcel newParcel = new Parcel(parcelId, sender, recipient);
        parcelDatabase.put(parcelId, newParcel);
        JOptionPane.showMessageDialog(null, "Parcel " + parcelId + " added successfully!");
    }

    public Parcel getParcel(String parcelId) {
        return parcelDatabase.get(parcelId);
    }

    public void displayAllParcels() {
        if (parcelDatabase.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No parcels available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Parcel parcel : parcelDatabase.values()) {
                sb.append(parcel).append("\n----------------------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
}

// Tracking Module
class TrackingModule {
    private ParcelInformation parcelInformation;

    public TrackingModule(ParcelInformation parcelInformation) {
        this.parcelInformation = parcelInformation;
    }

    public void trackParcel(String parcelId) {
        Parcel parcel = parcelInformation.getParcel(parcelId);
        if (parcel != null) {
            JOptionPane.showMessageDialog(null, parcel);
        } else {
            JOptionPane.showMessageDialog(null, "Parcel not found!");
        }
    }
}

// Admin Module
class AdminModule {
    private ParcelInformation parcelInformation;

    public AdminModule(ParcelInformation parcelInformation) {
        this.parcelInformation = parcelInformation;
    }

    public void updateParcelStatus(String parcelId, String status, String location) {
        Parcel parcel = parcelInformation.getParcel(parcelId);
        if (parcel != null) {
            parcel.updateStatus(status, location);
            JOptionPane.showMessageDialog(null, "Parcel status updated.");
        } else {
            JOptionPane.showMessageDialog(null, "Parcel not found!");
        }
    }

    public void addMultipleParcels(int n) {
        for (int i = 0; i < n; i++) {
            String parcelId = JOptionPane.showInputDialog("Enter Parcel ID:");
            String sender = JOptionPane.showInputDialog("Enter Sender:");
            String recipient = JOptionPane.showInputDialog("Enter Recipient:");
            parcelInformation.addParcel(parcelId, sender, recipient);
        }
    }

    public void displayAllParcels() {
        parcelInformation.displayAllParcels();
    }
}

// Main Class to Bring Everything Together
public class ParcelTrackingSystemGUI {
    private static UserAuthentication userAuthentication = new UserAuthentication();
    private static ParcelInformation parcelInformation = new ParcelInformation();
    private static TrackingModule trackingModule = new TrackingModule(parcelInformation);
    private static AdminModule adminModule = new AdminModule(parcelInformation);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parcel Tracking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register New User");
        JButton btnTrackParcel = new JButton("Track Parcel");
        JButton btnAdminOptions = new JButton("Admin Options");
        JButton btnExit = new JButton("Exit");

        panel.add(btnLogin);
        panel.add(btnRegister);
        panel.add(btnTrackParcel);
        panel.add(btnAdminOptions);
        panel.add(btnExit);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Login Button Action
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField userIdField = new JTextField(15);
                JPasswordField passwordField = new JPasswordField(15);
                JPanel loginPanel = new JPanel();
                loginPanel.add(new JLabel("User ID:"));
                loginPanel.add(userIdField);
                loginPanel.add(new JLabel("Password:"));
                loginPanel.add(passwordField);
                int option = JOptionPane.showConfirmDialog(frame, loginPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String userId = userIdField.getText();
                    String password = new String(passwordField.getPassword());
                    if (userAuthentication.authenticate(userId, password)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials.");
                    }
                }
            }
        });

        // Register Button Action
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField userIdField = new JTextField(15);
                JPasswordField passwordField = new JPasswordField(15);
                JPanel registerPanel = new JPanel();
                registerPanel.add(new JLabel("New User ID:"));
                registerPanel.add(userIdField);
                registerPanel.add(new JLabel("New Password:"));
                registerPanel.add(passwordField);
                int option = JOptionPane.showConfirmDialog(frame, registerPanel, "Register", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String userId = userIdField.getText();
                    String password = new String(passwordField.getPassword());
                    userAuthentication.registerUser(userId, password);
                }
            }
        });

        // Track Parcel Button Action
        btnTrackParcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parcelId = JOptionPane.showInputDialog(frame, "Enter Parcel ID:");
                trackingModule.trackParcel(parcelId);
            }
        });

        // Admin Options Button Action
        btnAdminOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Update Parcel Status", "Add Multiple Parcels", "Display All Parcels"};
                int choice = JOptionPane.showOptionDialog(frame, "Select Admin Option", "Admin Options",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                switch (choice) {
                    case 0:
                        JTextField parcelIdField = new JTextField(15);
                        JTextField statusField = new JTextField(15);
                        JTextField locationField = new JTextField(15);
                        JPanel statusPanel = new JPanel();
                        statusPanel.add(new JLabel("Parcel ID:"));
                        statusPanel.add(parcelIdField);
                        statusPanel.add(new JLabel("New Status:"));
                        statusPanel.add(statusField);
                        statusPanel.add(new JLabel("New Location:"));
                        statusPanel.add(locationField);
                        int statusOption = JOptionPane.showConfirmDialog(frame, statusPanel, "Update Parcel Status", JOptionPane.OK_CANCEL_OPTION);
                        if (statusOption == JOptionPane.OK_OPTION) {
                            String parcelId = parcelIdField.getText();
                            String status = statusField.getText();
                            String location = locationField.getText();
                            adminModule.updateParcelStatus(parcelId, status, location);
                        }
                        break;
                    case 1:
                        String numOfParcels = JOptionPane.showInputDialog(frame, "Enter number of parcels to add:");
                        int n = Integer.parseInt(numOfParcels);
                        adminModule.addMultipleParcels(n);
                        break;
                    case 2:
                        adminModule.displayAllParcels();
                        break;
                }
            }
        });

        // Exit Button Action
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
