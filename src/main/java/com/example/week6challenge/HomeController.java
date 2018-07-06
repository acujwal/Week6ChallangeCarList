package com.example.week6challenge;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listofcar(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newCar(Model model) {
        model.addAttribute("car", new Car());
        return "carform";
    }
    @PostMapping("/processCar")
    public String processActor(@ModelAttribute Car car,
                               @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/add";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            car.setImage(uploadResult.get("url").toString());
            carRepository.save(car);
        }
        catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";

    }

    @GetMapping("/addcategory")
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        return "category";
    }

}
