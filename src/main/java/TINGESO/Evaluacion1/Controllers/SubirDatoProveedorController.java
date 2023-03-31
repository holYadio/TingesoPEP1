package TINGESO.Evaluacion1.Controllers;


import TINGESO.Evaluacion1.Entities.SubirDatoProveedorEntity;
import TINGESO.Evaluacion1.Services.SubirDatoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirDatoProveedorController {
    @Autowired
    SubirDatoProveedorService subirDatoProveedorService;

    @GetMapping("/SubirDatosProveedor")
    public String fileUpload() {
        return "SubirDatosProveedor";
    }

    @PostMapping("/SubirDatosProveedor")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        subirDatoProveedorService.guardarDatosProveedor(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente");
        subirDatoProveedorService.leerCsvProveedor("Acopio.csv");
        return "redirect:/SubirDatosProveedor";
    }

    @GetMapping("/obtenerDatosProveedor")
    public String obtenerDatosProveedor(Model model){
        ArrayList<SubirDatoProveedorEntity> datosProveedor = subirDatoProveedorService.obtenerSubirDatosProveedores();
        model.addAttribute("datosProveedor", datosProveedor);
        return "obtenerDatosProveedor";
    }
}
