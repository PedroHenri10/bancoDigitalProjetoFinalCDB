package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.dto.SeguroRespostaDTO;
import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @PostMapping("/contratar/viagem/{cartaoId}")
    public ResponseEntity<SeguroRespostaDTO> contratarSeguroViagem(@PathVariable Long cartaoId) {
        Seguro seguro = seguroService.contratarSeguroViagem(cartaoId);
        return ResponseEntity.ok(seguroService.converterParaDTO(seguro));
    }

    @PostMapping("/contratar/fraude/{cartaoId}")
    public ResponseEntity<SeguroRespostaDTO> contratarSeguroFraude(@PathVariable Long cartaoId) {
        Seguro seguro = seguroService.contratarSeguroFraude(cartaoId);
        return ResponseEntity.ok(seguroService.converterParaDTO(seguro));
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<SeguroRespostaDTO> cancelarSeguro(@PathVariable Long id) {
        Seguro seguro = seguroService.cancelarSeguro(id);
        return ResponseEntity.ok(seguroService.converterParaDTO(seguro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguroRespostaDTO> buscarSeguroPorId(@PathVariable Long id) {
        Seguro seguro = seguroService.buscarSeguroPorId(id);
        return ResponseEntity.ok(seguroService.converterParaDTO(seguro));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<SeguroRespostaDTO>> listarSegurosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(seguroService.listarSegurosPorCliente(clienteId));
    }
}
