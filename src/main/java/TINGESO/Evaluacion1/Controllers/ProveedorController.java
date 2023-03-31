package TINGESO.Evaluacion1.Controllers;

import TINGESO.Evaluacion1.Services.ProveedorService;
import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/obtenerProveedores")
    public String listar(Model model) {
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "listadoProveedores";
    }

    @GetMapping("/obtenerProveedorPorCodigo")
    public String obtenerProveedorPorCodigo(@RequestParam("codigo") String codigo, Model model) {
        ProveedorEntity proveedor = proveedorService.obtenerProveedorPorCodigo(codigo);
        model.addAttribute("proveedor", proveedor);
        return "listadoProveedores";
    }
    @GetMapping("/nuevoProveedor")
    public String nuevoProveedor() {
        return "nuevoProveedor";
    }

    @PostMapping("/nuevoProveedor")
    public String guardarProveedor(@RequestParam("codigo") String codigo,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("categoria") String categoria,
                                   @RequestParam("retencion") String retencion) {
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/nuevoProveedor";
    }
}
