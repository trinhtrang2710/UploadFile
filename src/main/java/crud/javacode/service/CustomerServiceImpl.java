package crud.javacode.service;

import crud.javacode.convert.CustomerConverter;
import crud.javacode.repository.CustomerRepository;
import crud.javacode.dto.CustomerDTO;
import crud.javacode.model.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Override
    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> listModel = new ArrayList<>();
        List<CustomerEntity> listEntity = customerRepository.findAll();
        for (CustomerEntity entity : listEntity) {
            CustomerDTO customerDTO = customerConverter.toDTO(entity);
            listModel.add(customerDTO);
        }
        return listModel;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        CustomerEntity customerEntity = customerRepository.save(customerConverter.toEntity(dto));
        return customerConverter.toDTO(customerEntity);
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        CustomerEntity entity = customerRepository.findOne(id);
        return customerConverter.toDTO(entity);
    }

    @Override
    public List<CustomerDTO> search(String keyword) {
        List<CustomerDTO> models = new ArrayList<>();
        List<CustomerEntity> modelEtity = customerRepository.findByName(keyword);
        for (CustomerEntity entity : modelEtity) {
            CustomerDTO customerDTO = customerConverter.toDTO(entity);
            models.add(customerDTO);
        }
        return models;
    }

    @Override
    public void delete(Long id) {
        customerRepository.delete(id);
    }
}
