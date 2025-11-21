package repository.impl;

import db.DBConnection;
import model.dto.Supplier;
import repository.SupplierRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository{

    @Override
    public boolean addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier (sup_code, comp_name, sup_name, phone, email, notes) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getSupCode());
            ps.setString(2, supplier.getCompName());
            ps.setString(3, supplier.getSupName());
            ps.setString(4, supplier.getPhone());
            ps.setString(5, supplier.getEmail());
            ps.setString(6, supplier.getNotes());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET comp_name=?, sup_name=?, phone=?, email=?, notes=? WHERE sup_code=?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getCompName());
            ps.setString(2, supplier.getSupName());
            ps.setString(3, supplier.getPhone());
            ps.setString(4, supplier.getEmail());
            ps.setString(5, supplier.getNotes());
            ps.setString(6, supplier.getSupCode());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteSupplier(String supCode) throws SQLException {
        String sql = "DELETE FROM supplier WHERE sup_code=?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supCode);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Supplier searchSupplier(String supCode) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE sup_code=?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Supplier(
                        rs.getString("sup_code"),
                        rs.getString("comp_name"),
                        rs.getString("sup_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("notes")
                );
            }
            return null;
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Supplier(
                        rs.getString("sup_code"),
                        rs.getString("comp_name"),
                        rs.getString("sup_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("notes")
                ));
            }
        }
        return list;
    }
}
