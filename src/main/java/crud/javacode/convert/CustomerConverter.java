package crud.javacode.convert;

import crud.javacode.dto.CustomerDTO;
import crud.javacode.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public CustomerDTO toDTO(CustomerEntity entity){
        //get customer from entity and put to DTO
        CustomerDTO customer = new CustomerDTO();
        customer.setId(entity.getId());
        customer.setAddress(entity.getAddress());
        customer.setName(entity.getName());
        customer.setEmail(entity.getEmail());
        return customer;
    }

    public CustomerEntity toEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerDTO.getId());
        customerEntity.setAddress(customerDTO.getAddress());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setName(customerDTO.getName());
        return customerEntity;
    }

    public CustomerEntity toEntity(CustomerEntity customerEntity, CustomerDTO customerDTO){
        customerEntity.setId(customerDTO.getId());
        customerEntity.setName(customerDTO.getName());
        customerEntity.setEmail(customerDTO.getEmail());
        customerEntity.setAddress(customerDTO.getAddress());
        return  customerEntity;
    }
}
