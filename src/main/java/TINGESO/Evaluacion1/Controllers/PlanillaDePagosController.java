package TINGESO.Evaluacion1.Controllers;

import TINGESO.Evaluacion1.Entities.PagoEntity;
import TINGESO.Evaluacion1.Services.PlanillaDePagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PlanillaDePagosController {
    @Autowired
    PlanillaDePagos planillaDePagos;

    @GetMapping("/planillaDePagos")
    public String planillaDePagos(Model model) {
        planillaDePagos.calcularPagoFinal();
        ArrayList<PagoEntity> pagos = planillaDePagos.obtenerPagos();
        model.addAttribute("pagos", pagos);
        return "reporte";
    }
}