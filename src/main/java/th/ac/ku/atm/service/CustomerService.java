package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    //responsible for processing and managing customer information
    //customer information
    private List<Customer> customerList;

    @PostConstruct
    public void postConstruct(){
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer){
        //...hash pin for customer...
        String hashpin = hash(customer.getPin());
        customer.setPin(hashpin);
        customerList.add(customer);
    }

    public List<Customer> getCustomers(){
        return new ArrayList<>(this.customerList);
    }

    private String hash(String pin){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);

    }

    public Customer findCustomer(int id){
        for(Customer customer : customerList){
            if( customer.getId()== id)
                return customer;
        }
        return null;
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
