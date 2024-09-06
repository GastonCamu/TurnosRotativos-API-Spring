package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @PutMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> putEmpleado(
            @PathVariable("empleadoId") int id,
            @Valid
            @RequestBody EmpleadoDTO empleadoDTO) {

        EmpleadoDTO updatedEmpleado = this.empleadoService.updateEmpleado(id, empleadoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmpleado);
    }

    @GetMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(@PathVariable("empleadoId") int id) {
    EmpleadoDTO empleadoDTO = this.empleadoService.getEmpleado(id);
    return ResponseEntity.ok(empleadoDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getEmpelados() {
        List<EmpleadoDTO> empleados = this.empleadoService.getEmpleados();
        return ResponseEntity.ok(empleados);
    }
    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleado = this.empleadoService.createEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }
    @DeleteMapping("/{empleadoId}")
    public ResponseEntity deleteEmpleado(@PathVariable("empleadoId") Integer id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
