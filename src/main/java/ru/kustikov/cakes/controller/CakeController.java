package ru.kustikov.cakes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kustikov.cakes.dto.CakeDTO;
import ru.kustikov.cakes.entity.Cake;
import ru.kustikov.cakes.facade.CakeFacade;
import ru.kustikov.cakes.service.CakeService;
import ru.kustikov.cakes.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cake")
@CrossOrigin
public class CakeController {
    private final CakeService cakeService;
    private final CakeFacade cakeFacade;
    private final ResponseErrorValidation responseErrorValidation;


    public CakeController(CakeService cakeService, CakeFacade cakeFacade, ResponseErrorValidation responseErrorValidation) {
        this.cakeService = cakeService;
        this.cakeFacade = cakeFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/{orderId}/create")
    public ResponseEntity<Object> createCake(@Valid @RequestBody CakeDTO cakeDTO,
                                             @PathVariable("orderId") String orderId,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Cake cake = cakeService.saveCake(Long.parseLong(orderId), cakeDTO, principal);
        CakeDTO createdCake = cakeFacade.cakeToCakeDTO(cake);

        return new ResponseEntity<>(createdCake, HttpStatus.OK);
    }

    @GetMapping("/{orderId}/all")
    public ResponseEntity<List<CakeDTO>> getAllCakesToOrder(@PathVariable("orderId") String orderId) {
        List<CakeDTO> cakeDTOList = cakeService.getAllCakesForOrder(Long.parseLong(orderId))
                .stream()
                .map(cakeFacade::cakeToCakeDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cakeDTOList, HttpStatus.OK);
    }
}
