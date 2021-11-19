package com.legiz.terasoftproject.userProfile.api;

import com.legiz.terasoftproject.userProfile.domain.model.entity.Customer;
import com.legiz.terasoftproject.userProfile.domain.service.CustomerService;
import com.legiz.terasoftproject.userProfile.mapping.CustomerMapper;
import com.legiz.terasoftproject.userProfile.resource.CreateCustomerResource;
import com.legiz.terasoftproject.userProfile.resource.CustomerResource;
import com.legiz.terasoftproject.userProfile.resource.UpdateCustomerResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customers")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService customerService, CustomerMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get Customers", description = "Get All Customers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CustomerResource.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("")
    public Page<CustomerResource> getAllCustomer(Pageable pageable) {
        return mapper.modelListToPage(customerService.getAll(), pageable);
    }

    @Operation(summary = "Get Customer By Id", description = "Get Customer already stored by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CustomerResource.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("{customerId}")
    public CustomerResource getCustomerById(@PathVariable Long customerId) {
        return mapper.toResource(customerService.getById(customerId));
    }

    @Operation(summary = "Create Customer", description = "Create a new Customer")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CreateCustomerResource.class
                                    )
                            )
                    }
            )
    })
    @PostMapping
    public CustomerResource createCustomer(@RequestBody CreateCustomerResource request) {
        return mapper.toResource(customerService.create(mapper.toModel(request)));
    }

    @Operation(summary = "Update Customer", description = "Update Customer already stored by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UpdateCustomerResource.class
                                    )
                            )
                    }
            )
    })
    @PutMapping("{customerId}")
    public CustomerResource updateCustomer(@PathVariable Long customerId, @RequestBody UpdateCustomerResource request) {
        return mapper.toResource(customerService.update(customerId, mapper.toModel(request)));
    }

    @Operation(summary = "Delete Customer", description = "Delete Customer already stored")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @DeleteMapping("{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        return customerService.delete(customerId);
    }
}
