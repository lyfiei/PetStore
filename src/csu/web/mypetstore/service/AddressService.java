package csu.web.mypetstore.service;

import csu.web.mypetstore.domain.Address;
import csu.web.mypetstore.persistence.AddressDao;
import csu.web.mypetstore.persistence.impl.AddressDaoImpl;
import java.util.List;

public class AddressService {
    private AddressDao addressDao = new AddressDaoImpl();

    public void addAddress(Address address) {
        addressDao.insertAddress(address);
    }

    public List<Address> getAddressListByUsername(String username) {
        return addressDao.getAddressListByUsername(username);
    }

    public Address getAddressById(int addressId) { // ✅ 新增
        return addressDao.getAddressById(addressId);
    }
}
