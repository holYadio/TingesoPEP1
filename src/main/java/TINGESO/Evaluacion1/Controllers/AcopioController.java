package TINGESO.Evaluacion1.Controllers;

import TINGESO.Evaluacion1.Services.AcopioService;
import TINGESO.Evaluacion1.Entities.AcopioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/acopio")
public class AcopioController {
    @Autowired
    AcopioService acopioService;

    @GetMapping("/obtenerAcopios")
    public String obtenerAcopios(Model model) {
        ArrayList<AcopioEntity> acopios = acopioService.obtenerAcopios();
        model.addAttribute("acopios", acopios);
        return "index";
    }

    @GetMapping("/obtenerAcopioPorProveedor")
    public String obtenerAcopioPorProveedor(@RequestParam("proveedor") String proveedor, Model model) {
        AcopioEntity acopio = acopioService.obtenerAcopioPorProveedor(proveedor);
        model.addAttribute("acopio", acopio);
        return "index";
    }

    @GetMapping("/nuevoAcopio")
    public String nuevoAcopio() {
        return "nuevoAcopio";
    }
    @PostMapping("/nuevoAcopio")
    public String nuevoAcopio(@RequestParam("fecha") Date fecha,
                              @RequestParam("turno") String turno,
                              @RequestParam("proveedor") String proveedor,
                              @RequestParam("cantidad") int cantidad) {
        acopioService.guardarAcopio(fecha, turno, proveedor, cantidad);
        return "redirect:/acopio/nuevoAcopio";
    }
}
