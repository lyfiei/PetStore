package csu.web.mypetstore.persistence;

import csu.web.mypetstore.domain.Address;
import java.util.List;

public interface AddressDao {
    void insertAddress(Address address);
    List<Address> getAddressListByUsername(String username);
    Address getAddressById(int addressId);

}
