package repository.abstractRepo;

import domain.Address;

import java.util.List;

public interface AddressRepositoryInterface extends RepositoryInterface<Long, Address> {
    List<Address> findAddressesByCounty(String county);
    List<Address> findAddressesByCity(String city);
    Address findOne(String county, String city, String street, int number);
}
