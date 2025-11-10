package service;

import model.dto.Supplier;
import java.util.List;

public interface SupplierService {

    boolean addSupplier(Supplier supplier);
    boolean updateSupplier(Supplier supplier);
    boolean deleteSupplier(String supCode);
    Supplier searchSupplier(String supCode);
    List<Supplier> getAllSuppliers();
    String generateNextSupplierCode();
}
