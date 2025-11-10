package service.impl;


import model.dto.Supplier;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;
import service.SupplierService;
import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl {

    SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    @Override
    public boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO supplier VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getSupCode());
            ps.setString(2, supplier.getCompName());
            ps.setString(3, supplier.getSupName());
            ps.setString(4, supplier.getPhone());
            ps.setString(5, supplier.getEmail());
            ps.setString(6, supplier.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSupplier(String supCode) {
        String sql = "DELETE FROM supplier WHERE sup_code=?";
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Supplier searchSupplier(String supCode) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String generateNextSupplierCode() {
        String sql = "SELECT sup_code FROM supplier ORDER BY sup_code DESC LIMIT 1";
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                String lastCode = rs.getString(1);
                int num = Integer.parseInt(lastCode.substring(1)) + 1;
                return String.format("S%03d", num);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "S001";
    }
}
