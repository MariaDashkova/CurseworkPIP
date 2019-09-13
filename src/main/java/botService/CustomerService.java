package botService;


import botBase.CustomersEntity;
import botRep.CustomerRep;

public class CustomerService {
    private CustomerRep customer = new CustomerRep();

    public CustomersEntity checkCustomer(int id) {

        return customer.findById(id);
    }

    public CustomersEntity findCustomer(String log) {
        return customer.findByLogin(log);
    }

}
