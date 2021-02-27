package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.Customer;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    //responsible for processing and managing customer information
    //customer information

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository){
        this.repository = repository;
    }

    public void createCustomer(Customer customer){
        //...hash pin for customer...
        String hashpin = hash(customer.getPin());
        customer.setPin(hashpin);
        repository.save(customer);
    }

    public List<Customer> getCustomers(){
        return repository.findAll();
    }

    private String hash(String pin){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);

    }

    public Customer findCustomer(int id){
       try {
           return  repository.findById(id).get();
       }catch (NoSuchElementException e){
           return null;
       }

    }

    public Customer checkPin(Customer inputCustomer){
        Customer storeCustomer = findCustomer(inputCustomer.getId());
        if(storeCustomer !=null){
            String hashPin = storeCustomer.getPin();

            if(BCrypt.checkpw(inputCustomer.getPin(),hashPin))
                return   storeCustomer;
        }
        return null;
    }
}
