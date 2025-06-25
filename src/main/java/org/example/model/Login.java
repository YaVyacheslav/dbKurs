
package org.example.model;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.view.TableNavigationPane;
import org.example.controller.*;
import org.example.view.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

public class Login extends Observable {
    private String role;
    private int clientId;
    private Connection connection;
    private Stage primaryStage;

    public Login() {}


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void authenticate(String username, String plainPassword) {
        String sql = "SELECT cid, password, role FROM clients WHERE username = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && plainPassword.equals(rs.getString("password"))) {
                    role = rs.getString("role");
                    clientId = rs.getInt("cid");
                } else {
                    role = null;
                    clientId = -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            role = null;
            clientId = -1;
        }

        setChanged();
        notifyObservers();
    }

    public void openRegistrationWindow() {
        Register reg = new Register();
        reg.setConnection(connection);
        RegisterView regView = new RegisterView(reg);
        RegisterController regController = new RegisterController(reg, regView);

        Stage regStage = new Stage();
        regStage.setTitle("Регистрация");
        regStage.setScene(new Scene(regView.getView()));
        regStage.initOwner(getPrimaryStage());
        regStage.show();
    }

    public void showUserProfile(Stage stage) throws SQLException {
        int clientId = getClientId();


        UserProfile userProfile = new UserProfile();
        userProfile.setConnection(connection);
        userProfile.setClientId(clientId);
        UserProfileView userProfileView = new UserProfileView(userProfile);
        UserProfileController userProfileController = new UserProfileController(userProfile, userProfileView);

        stage.setTitle("Личный кабинет");
        stage.setScene(new Scene(userProfileView.getView()));
        stage.show();
    }

    public void showAdminDashboard(Stage stage) throws SQLException {
        Branchs mBranches = new Branchs();
        mBranches.load(connection);

        Clients mClients = new Clients();
        mClients.load(connection);

        Services1 mSvc1 = new Services1();
        mSvc1.load(connection);

        Services2 mSvc2 = new Services2();
        mSvc2.load(connection);

        ServiceTypes mServiceTypes = new ServiceTypes();
        mServiceTypes.load(connection);

        BranchView vBranches = new BranchView(mBranches);
        ClientView vClients = new ClientView(mClients);
        Service1View vSvc1 = new Service1View(mSvc1);
        Service2View vSvc2 = new Service2View(mSvc2);
        ServiceTypeView vServiceType = new ServiceTypeView(mServiceTypes);

        new BranchController(mBranches, vBranches);
        new ClientController(mClients, vClients);
        new Service1Controller(mSvc1, vSvc1);
        new Service2Controller(mSvc2, vSvc2);
        new ServiceTypeController(mServiceTypes, vServiceType);

        TableNavigationPane nav = new TableNavigationPane(vBranches, vClients, vSvc1, vSvc2, vServiceType);

        stage.setTitle("Admin Dashboard");
        stage.setScene(new Scene(nav));
        stage.show();
    }

    public String getRole() {
        return role;
    }

    public int getClientId() {
        return clientId;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
