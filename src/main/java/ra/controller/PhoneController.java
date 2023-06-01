package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ra.model.SmartPhone;
import ra.service.ISmartPhoneService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phone")
@CrossOrigin(origins = "*")
public class PhoneController {

    @Autowired
    private ISmartPhoneService smartPhoneService;
    @GetMapping
    private ResponseEntity<?> findAll() {
        return  new ResponseEntity<>(smartPhoneService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?>  findById(@PathVariable("id") Long id) {
        Optional<SmartPhone> smartPhone= smartPhoneService.findById(id);
        if (!smartPhone.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(smartPhone.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody SmartPhone smartPhone) {
        return  new ResponseEntity<>(smartPhoneService.save(smartPhone), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSmartPhone(@PathVariable("id") Long id , @RequestBody SmartPhone smartPhone) {
        Optional<SmartPhone> smartPhoneById = smartPhoneService.findById(id);
        if (!smartPhoneById.isPresent()) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartPhoneById.get().setProducer(smartPhone.getProducer());
        smartPhoneById.get().setModel(smartPhone.getModel());
        smartPhoneById.get().setPrice(smartPhone.getPrice());
//        smartPhone.setId(smartPhoneById.get().getId());
        return new ResponseEntity<>(smartPhoneService.save(smartPhoneById.get()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSmartPhoneById(@PathVariable("id") Long id) {
        Optional<SmartPhone> smartPhone = smartPhoneService.findById(id);
        if (!smartPhone.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartPhoneService.deleteById(smartPhone.get().getId());
        return new ResponseEntity<>(smartPhone.get(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ModelAndView getAllSmartphonePage() {
        ModelAndView modelAndView = new ModelAndView("/smart_phone");
        modelAndView.addObject("smartphones", smartPhoneService.findAll());
        System.out.println("modelAndView ---->"+modelAndView);
        return modelAndView;
    }

}
