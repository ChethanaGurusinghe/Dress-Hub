package repository;

import model.dto.Supplier;
import java.sql.SQLException;
import java.util.List;

public interface SupplierRepository {

    boolean addSupplier(Supplier supplier) throws SQLException;
    boolean updateSupplier(Supplier supplier) throws SQLException;
    boolean deleteSupplier(String supCode) throws SQLException;
    Supplier searchSupplier(String supCode) throws SQLException;
    List<Supplier> getAllSuppliers() throws SQLException;
}
