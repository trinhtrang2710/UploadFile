package crud.javacode.service;

import crud.javacode.dto.CustomerDTO;
import dto.NewCustomer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    public List<CustomerDTO> getCustomers();

    CustomerDTO saveCustomer(CustomerDTO dto);

    CustomerDTO getCustomer(Long id);

    public List<CustomerDTO> search(String keyword);

    void delete(Long id);


}
