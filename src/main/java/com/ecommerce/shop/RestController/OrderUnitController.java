package com.ecommerce.shop.RestController;

import com.ecommerce.shop.Model.OrderModel;
import com.ecommerce.shop.Model.OrderUnitModel;
import com.ecommerce.shop.Model.ProductModel;
import com.ecommerce.shop.Service.OrderUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order/units")
public class OrderUnitController {

    @Autowired
    private OrderUnitService unitService;

    @GetMapping
    public ResponseEntity<List<OrderUnitModel>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "id") String sortBy) {
        List<OrderUnitModel> units = unitService.findAll(pageNo, pageSize, sortBy);
        return ResponseEntity.ok().body(units);
    }

    @GetMapping(path = "get/{unitId}")
    public Optional<OrderUnitModel> findById(@PathVariable("unitId") Long unitId) {
        return unitService.findById(unitId);
    }

    @PostMapping("/addUnit")
    public ResponseEntity<OrderUnitModel> addUnit(@RequestBody OrderUnitModel unit) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/order/units/addUnit").toUriString());
        return ResponseEntity.created(uri).body(unitService.addNewOrderUnit(unit));
    }

    @PutMapping(path = "{unitId}")
    public void updateUnit(@PathVariable("unitId") Long unitId,
                           @RequestParam(required = false) ProductModel product,
                           @RequestParam(required = false) OrderModel order,
                           @RequestParam(required = false) int amount,
                           @RequestParam(required = false) float price) {

        unitService.updateOrderUnit(unitId, order, product, amount, price);
    }

    @DeleteMapping(path = "/delete/{unitId}")
    public void deleteUnit(@PathVariable("unitId") Long unitId) {
        unitService.deleteOrderUnit(unitId);
    }
}
