package com.gustavo.finapp.finapp_titulos_api.controller;

import com.gustavo.finapp.finapp_titulos_api.dto.TituloPageResponse;
import com.gustavo.finapp.finapp_titulos_api.dto.ResumoTitulosResponse;
import com.gustavo.finapp.finapp_titulos_api.repository.TituloRepository;
import com.gustavo.finapp.finapp_titulos_api.dto.TituloDto;
import com.gustavo.finapp.finapp_titulos_api.dto.TituloCobrancaResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@RestController
@RequestMapping("/titulos")
@Tag(
    name = "Títulos",
    description = "Endpoints de consulta financeira (página, filtros, ordenação e resumo)"
)
public class TituloController {

    private final TituloRepository tituloRepository;

    public TituloController(TituloRepository tituloRepository) {
        this.tituloRepository = tituloRepository;
    }

    @GetMapping("/teste")
    @Operation(summary = "Verifica se o endpoint de títulos está funcionando")
    public String teste() {
        return "Endpoint de teste funcionando!";
    }


    // DETALHE POR ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar título por ID",
            description = "Retorna os dados completos de um título a partir do seu idTitulo."
    )
    public ResponseEntity<TituloDto> buscarPorId(
            @Parameter(description = "ID do título (id_titulo na base)")
            @PathVariable("id") Long id
    ) {
        try {
            TituloDto dto = tituloRepository.buscarPorId(id);
            return ResponseEntity.ok(dto);
        } catch (EmptyResultDataAccessException e) {
            // Se não encontrar, devolve 404
            return ResponseEntity.notFound().build();
        }
    }


    // LISTAGEM PAGINADA
    @GetMapping
    @Operation(
            summary = "Listar títulos",
            description = "Retorna títulos filtrados por tipoLado, status e intervalo de vencimento, com paginação e ordenação."
    )
    public ResponseEntity<TituloPageResponse> listarTitulos(
            @Parameter(description = "R = Receber, P = Pagar")
            @RequestParam(name = "tipoLado", required = false) String tipoLado,

            @Parameter(description = "Status do título (AB, PG, CN, etc.)")
            @RequestParam(name = "status", required = false) String status,

            @Parameter(description = "Data de vencimento inicial (yyyy-MM-dd)")
            @RequestParam(name = "dtVencIni", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtVencIni,

            @Parameter(description = "Data de vencimento final (yyyy-MM-dd)")
            @RequestParam(name = "dtVencFim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtVencFim,

            @Parameter(description = "Número da página (0 = primeira)")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Quantidade de itens por página")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "Campo para ordenação (ex: dt_vencimento, valor_saldo)")
            @RequestParam(name = "sortBy", defaultValue = "dt_vencimento") String sortBy,

            @Parameter(description = "asc ou desc")
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        TituloPageResponse resp = tituloRepository.buscarTitulos(
                tipoLado, status, dtVencIni, dtVencFim,
                page, size, sortBy, direction
        );

        return ResponseEntity.ok(resp);
    }

    // RESUMO FINANCEIRO
    @GetMapping("/resumo")
    @Operation(
            summary = "Resumo financeiro dos títulos",
            description = "Retorna total geral, total a receber, a pagar, vencido, do dia e a vencer."
    )
    public ResponseEntity<ResumoTitulosResponse> resumoTitulos(
            @RequestParam(name = "tipoLado", required = false) String tipoLado,
            @RequestParam(name = "status",   required = false) String status,

            @RequestParam(name = "dtVencIni", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtVencIni,

            @RequestParam(name = "dtVencFim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dtVencFim
    ) {
        ResumoTitulosResponse resumo = tituloRepository.resumoTitulos(
                tipoLado,
                status,
                dtVencIni,
                dtVencFim
        );

        return ResponseEntity.ok(resumo);
    }

    // ENDPOINT DE COBRANÇA: VENCIDOS / HOJE / A VENCER
    @GetMapping("/cobranca")
    @Operation(
            summary = "Lista títulos para cobrança",
            description = "Retorna três listas: vencidos, vencendo hoje e a vencer até a quantidade de dias informada."
    )
    public ResponseEntity<TituloCobrancaResponse> listarTitulosCobranca(
            @Parameter(description = "R = Receber, P = Pagar (opcional)")
            @RequestParam(name = "tipoLado", required = false) String tipoLado,

            @Parameter(description = "Quantidade de dias para frente a considerar como 'a vencer' (padrão: 7)")
            @RequestParam(name = "diasProximos", required = false, defaultValue = "7") Integer diasProximos
    ) {
        TituloCobrancaResponse resp = tituloRepository.buscarTitulosCobranca(
                tipoLado,
                diasProximos
        );

        return ResponseEntity.ok(resp);
    }

}
