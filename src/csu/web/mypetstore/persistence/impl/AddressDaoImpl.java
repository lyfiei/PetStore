package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Address;
import csu.web.mypetstore.persistence.AddressDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoImpl implements AddressDao {

    private static final String INSERT_ADDRESS =
            "INSERT INTO address (username, firstName, lastName, address1, address2, city, state, zip, country) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_USERNAME =
            "SELECT * FROM address WHERE username = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mypetstore", "root", "123456");
    }

    @Override
    public void insertAddress(Address address) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ADDRESS)) {
            ps.setString(1, address.getUsername());
            ps.setString(2, address.getFirstName());
            ps.setString(3, address.getLastName());
            ps.setString(4, address.getAddress1());
            ps.setString(5, address.getAddress2());
            ps.setString(6, address.getCity());
            ps.setString(7, address.getState());
            ps.setString(8, address.getZip());
            ps.setString(9, address.getCountry());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Address> getAddressListByUsername(String username) {
        List<Address> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address a = new Address();
                a.setAddressId(rs.getInt("addressid"));
                a.setUsername(rs.getString("username"));
                a.setFirstName(rs.getString("firstName"));
                a.setLastName(rs.getString("lastName"));
                a.setAddress1(rs.getString("address1"));
                a.setAddress2(rs.getString("address2"));
                a.setCity(rs.getString("city"));
                a.setState(rs.getString("state"));
                a.setZip(rs.getString("zip"));
                a.setCountry(rs.getString("country"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Address getAddressById(int addressId) {
        Address address = null;
        String sql = "SELECT * FROM address WHERE addressid = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                address = new Address();
                address.setAddressId(rs.getInt("addressid"));
                address.setUsername(rs.getString("username"));
                address.setFirstName(rs.getString("firstName"));
                address.setLastName(rs.getString("lastName"));
                address.setAddress1(rs.getString("address1"));
                address.setAddress2(rs.getString("address2"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setZip(rs.getString("zip"));
                address.setCountry(rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

}
