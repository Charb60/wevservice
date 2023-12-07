package exerciseone.exone;


import java.util.*;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;
// import java.util.Comparator;

// import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;

@RestController
public class controller {
    private List<Customer> customers = new ArrayList<>();

    // show all customer id
    @GetMapping(value = "customers")
    public ResponseEntity<List<Customer>> getAllCustomersController() {
        if (!customers.isEmpty()) {
            customers.sort(Comparator.comparing(Customer::getId));
            return ResponseEntity
                    .ok()
                    .body(customers);
        } else {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    // show customer id
    @GetMapping(value = "customer/{id}")
    public ResponseEntity<List<Customer>> getCustomerById(@PathVariable Long id) {
        // Assuming Customer is the type of object stored in the 'customers' list
        Optional<Customer> customerOptional = customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();

        return customerOptional.map(customer -> ResponseEntity.ok().body(Collections.singletonList(customer)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // add Customer
    @PostMapping(value = "customer")
    public ResponseEntity<String> addCustoController(@RequestBody Customer customer) {
        boolean isCustomerExist = customers.stream()
                .anyMatch(existedCustomer -> existedCustomer.getId().equals(customer.getId()));
        if (isCustomerExist) {

            return ResponseEntity
                    .badRequest()
                    .body("Customer already exists");
        } else {
            customers.add(customer);
        }
        return ResponseEntity

                .ok()
                .body("addCustomerSuccess");

    }

    // Delete customer id
    @DeleteMapping(value = "customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") Long id) {
        if (arecustomerExist(id)) {
            customers.removeIf(customer -> customer.getId().equals(id));
            return ResponseEntity
                    .ok()
                    .body("delete successfully");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("customer not exist");
        }

    }

    // Delete all customer
    @DeleteMapping(value = "dallcustomer")
    public ResponseEntity<String> deleteAllCustomer() {
        if (customers == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invisible customer");

        }
        customers.clear();
        return ResponseEntity
                .ok()
                .body("all customers is deleted successfully");

    }

    private boolean arecustomerExist(Long id) {
        return customers
                .stream()
                .anyMatch(existedCustomer -> id.equals(existedCustomer.getId()));
    }

    private void deleteCustomer(Customer customer) {
        customers.removeIf(existedCustomer -> customer.getId().equals(existedCustomer.getId()));
    }

    // Update customer
    @PutMapping(value = "customer")
    public ResponseEntity<String> updateUserController(@RequestBody Customer customer) {
        if (null == customer.getId()) {
            return ResponseEntity
                    .badRequest()
                    .body("Can't updaete : filed 'id' is missing");
        }

        Customer quryCustomer = customers
                .stream()
                .filter(existedCustomer -> customer.getId().equals(existedCustomer.getId()))
                .findAny().orElse(null);
        if (null == quryCustomer) {
            return ResponseEntity
                    .badRequest()
                    .body("Can't update: User does't exist");

        }
        if (null != customer.getName()) {
            quryCustomer.setName(customer.getName());
        }
        if (null != customer.getTel()) {
            quryCustomer.setTel(customer.getTel());
        }
        // users.stream().filter(existedUser ->
        // existedUser.getId().equals(user.getId()));

        // Solution #1
        deleteCustomer(customer);
        customers.add(quryCustomer);
        return ResponseEntity
                .ok()
                .body("Update User Success.");
    }

}
