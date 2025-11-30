package com.gustavo.finapp.finapp_titulos_api.repository;

import com.gustavo.finapp.finapp_titulos_api.dto.TituloDto;
import com.gustavo.finapp.finapp_titulos_api.dto.TituloPageResponse;
import com.gustavo.finapp.finapp_titulos_api.dto.ResumoTitulosResponse;
import com.gustavo.finapp.finapp_titulos_api.dto.TituloCobrancaResponse;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Repository
public class TituloRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TituloRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // LISTAGEM PAGINADA + ORDENAÇÃO + FILTROS
    public TituloPageResponse buscarTitulos(
            String tipoLado,
            String status,
            LocalDate dtVencIni,
            LocalDate dtVencFim,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        String whereSql =
                " WHERE (:tipoLado IS NULL OR tipo_lado = :tipoLado) " +
                "   AND (:status   IS NULL OR status     = :status) " +
                "   AND (:dtVencIni IS NULL OR dt_vencimento >= :dtVencIni) " +
                "   AND (:dtVencFim IS NULL OR dt_vencimento <= :dtVencFim) ";

        String selectSql =
                "SELECT  id_titulo, " +
                "        tipo_lado, " +
                "        id_cliente, " +
                "        id_fornecedor, " +
                "        numero_documento, " +
                "        descricao, " +
                "        dt_emissao, " +
                "        dt_vencimento, " +
                "        valor_original, " +
                "        valor_saldo, " +
                "        status, " +
                "        situacao_cobranca, " +
                "        dias_em_atraso " +
                "  FROM  v_titulos_listagem " +
                     whereSql;

        String countSql =
                "SELECT COUNT(1) " +
                "  FROM v_titulos_listagem " +
                     whereSql;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("tipoLado", tipoLado, Types.VARCHAR);
        params.addValue("status",   status,   Types.VARCHAR);

        Date dtIniSql = (dtVencIni != null) ? Date.valueOf(dtVencIni) : null;
        Date dtFimSql = (dtVencFim != null) ? Date.valueOf(dtVencFim) : null;

        params.addValue("dtVencIni", dtIniSql, Types.DATE);
        params.addValue("dtVencFim", dtFimSql, Types.DATE);

        // TOTAL DE ELEMENTOS
        Long total = jdbcTemplate.queryForObject(countSql, params, Long.class);
        long totalElements = (total != null) ? total : 0L;

        if (totalElements == 0L) {
            return new TituloPageResponse(
                    List.of(),
                    page,
                    size,
                    0L,
                    sortBy,
                    direction
            );
        }

        // ORDENAÇÃO
        String sortColumn = mapSortColumn(sortBy);
        String sortDirection = mapSortDirection(direction);

        int offset = page * size;

        String paginatedSql =
                selectSql +
                " ORDER BY " + sortColumn + " " + sortDirection + ", id_titulo " +
                " OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

        params.addValue("offset", offset, Types.INTEGER);
        params.addValue("limit", size, Types.INTEGER);

        // RESULTADOS PAGINADOS
        List<TituloDto> titulos = jdbcTemplate.query(paginatedSql, params, (rs, rowNum) -> {
            TituloDto dto = new TituloDto();

            dto.setIdTitulo(rs.getLong("id_titulo"));
            dto.setTipoLado(rs.getString("tipo_lado"));
            dto.setIdCliente(rs.getLong("id_cliente"));
            dto.setIdFornecedor(rs.getLong("id_fornecedor"));
            dto.setNumeroDocumento(rs.getString("numero_documento"));
            dto.setDescricao(rs.getString("descricao"));

            Date dtEmissao = rs.getDate("dt_emissao");
            if (dtEmissao != null) dto.setDtEmissao(dtEmissao.toLocalDate());

            Date dtVenc = rs.getDate("dt_vencimento");
            if (dtVenc != null) dto.setDtVencimento(dtVenc.toLocalDate());

            dto.setValorOriginal(rs.getBigDecimal("valor_original"));
            dto.setValorSaldo(rs.getBigDecimal("valor_saldo"));
            dto.setStatus(rs.getString("status"));
            dto.setSituacaoCobranca(rs.getString("situacao_cobranca"));
            dto.setDiasEmAtraso(rs.getInt("dias_em_atraso"));

            return dto;
        });

        return new TituloPageResponse(
                titulos,
                page,
                size,
                totalElements,
                sortBy,
                direction
        );
    }

    private String mapSortColumn(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "dt_vencimento";
        }

        return switch (sortBy) {
            case "idTitulo", "id_titulo" -> "id_titulo";
            case "tipoLado", "tipo_lado" -> "tipo_lado";
            case "idCliente", "id_cliente" -> "id_cliente";
            case "idFornecedor", "id_fornecedor" -> "id_fornecedor";
            case "numeroDocumento", "numero_documento" -> "numero_documento";
            case "descricao" -> "descricao";
            case "dtEmissao", "dt_emissao" -> "dt_emissao";
            case "dtVencimento", "dt_vencimento" -> "dt_vencimento";
            case "valorOriginal", "valor_original" -> "valor_original";
            case "valorSaldo", "valor_saldo" -> "valor_saldo";
            case "status" -> "status";
            case "situacaoCobranca", "situacao_cobranca" -> "situacao_cobranca";
            case "diasEmAtraso", "dias_em_atraso" -> "dias_em_atraso";
            default -> "dt_vencimento";
        };
    }

    private String mapSortDirection(String direction) {
        if (direction == null) return "ASC";
        if ("desc".equalsIgnoreCase(direction)) return "DESC";
        return "ASC";
    }

    // BUSCA POR ID (DETALHE)
    public TituloDto buscarPorId(Long idTitulo) {
        String sql =
                "SELECT  id_titulo, " +
                "        tipo_lado, " +
                "        id_cliente, " +
                "        id_fornecedor, " +
                "        numero_documento, " +
                "        descricao, " +
                "        dt_emissao, " +
                "        dt_vencimento, " +
                "        valor_original, " +
                "        valor_saldo, " +
                "        status, " +
                "        situacao_cobranca, " +
                "        dias_em_atraso " +
                "  FROM  v_titulos_listagem " +
                " WHERE id_titulo = :idTitulo";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idTitulo", idTitulo, Types.BIGINT);

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            TituloDto dto = new TituloDto();

            dto.setIdTitulo(rs.getLong("id_titulo"));
            dto.setTipoLado(rs.getString("tipo_lado"));
            dto.setIdCliente(rs.getLong("id_cliente"));
            dto.setIdFornecedor(rs.getLong("id_fornecedor"));
            dto.setNumeroDocumento(rs.getString("numero_documento"));
            dto.setDescricao(rs.getString("descricao"));

            Date dtEmissao = rs.getDate("dt_emissao");
            if (dtEmissao != null) dto.setDtEmissao(dtEmissao.toLocalDate());

            Date dtVenc = rs.getDate("dt_vencimento");
            if (dtVenc != null) dto.setDtVencimento(dtVenc.toLocalDate());

            dto.setValorOriginal(rs.getBigDecimal("valor_original"));
            dto.setValorSaldo(rs.getBigDecimal("valor_saldo"));
            dto.setStatus(rs.getString("status"));
            dto.setSituacaoCobranca(rs.getString("situacao_cobranca"));
            dto.setDiasEmAtraso(rs.getInt("dias_em_atraso"));

            return dto;
        });
    }

    // LISTA PARA ROTINA DE COBRANÇA
    public TituloCobrancaResponse buscarTitulosCobranca(
            String tipoLado,
            Integer diasProximos
    ) {
        int janelaDias = (diasProximos != null && diasProximos >= 0) ? diasProximos : 7;

        String sql =
                "SELECT  id_titulo, " +
                "        tipo_lado, " +
                "        id_cliente, " +
                "        id_fornecedor, " +
                "        numero_documento, " +
                "        descricao, " +
                "        dt_emissao, " +
                "        dt_vencimento, " +
                "        valor_original, " +
                "        valor_saldo, " +
                "        status, " +
                "        situacao_cobranca, " +
                "        dias_em_atraso, " +
                "        CASE " +
                "           WHEN dt_vencimento < :hoje THEN 'VENCIDO' " +
                "           WHEN dt_vencimento = :hoje THEN 'HOJE' " +
                "           ELSE 'AVENCER' " +
                "        END AS faixa_cobranca " +
                "  FROM  v_titulos_listagem " +
                " WHERE status = 'AB' " + 
                "   AND (:tipoLado IS NULL OR tipo_lado = :tipoLado) " +
                "   AND dt_vencimento <= :limiteData ";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("tipoLado", tipoLado, Types.VARCHAR);

        LocalDate hoje = LocalDate.now();
        params.addValue("hoje", Date.valueOf(hoje), Types.DATE);

        LocalDate limite = hoje.plusDays(janelaDias);
        params.addValue("limiteData", Date.valueOf(limite), Types.DATE);

        List<TituloDto> vencidos = new ArrayList<>();
        List<TituloDto> hojeList = new ArrayList<>();
        List<TituloDto> aVencer = new ArrayList<>();

        jdbcTemplate.query(sql, params, (rs) -> {
            TituloDto dto = new TituloDto();

            dto.setIdTitulo(rs.getLong("id_titulo"));
            dto.setTipoLado(rs.getString("tipo_lado"));
            dto.setIdCliente(rs.getLong("id_cliente"));
            dto.setIdFornecedor(rs.getLong("id_fornecedor"));
            dto.setNumeroDocumento(rs.getString("numero_documento"));
            dto.setDescricao(rs.getString("descricao"));

            Date dtEmissao = rs.getDate("dt_emissao");
            if (dtEmissao != null) dto.setDtEmissao(dtEmissao.toLocalDate());

            Date dtVenc = rs.getDate("dt_vencimento");
            if (dtVenc != null) dto.setDtVencimento(dtVenc.toLocalDate());

            dto.setValorOriginal(rs.getBigDecimal("valor_original"));
            dto.setValorSaldo(rs.getBigDecimal("valor_saldo"));
            dto.setStatus(rs.getString("status"));
            dto.setSituacaoCobranca(rs.getString("situacao_cobranca"));
            dto.setDiasEmAtraso(rs.getInt("dias_em_atraso"));

            String faixa = rs.getString("faixa_cobranca");
            if ("VENCIDO".equals(faixa)) {
                vencidos.add(dto);
            } else if ("HOJE".equals(faixa)) {
                hojeList.add(dto);
            } else {
                aVencer.add(dto);
            }
        });

        return new TituloCobrancaResponse(vencidos, hojeList, aVencer);
    }


    // RESUMO FINANCEIRO DE TÍTULOS
    public ResumoTitulosResponse resumoTitulos(
            String tipoLado,
            String status,
            LocalDate dtVencIni,
            LocalDate dtVencFim
    ) {
        String whereSql =
                " WHERE (:tipoLado IS NULL OR tipo_lado = :tipoLado) " +
                "   AND (:status   IS NULL OR status     = :status) " +
                "   AND (:dtVencIni IS NULL OR dt_vencimento >= :dtVencIni) " +
                "   AND (:dtVencFim IS NULL OR dt_vencimento <= :dtVencFim) ";

        String sql =
                "SELECT " +
                "   COUNT(1) AS qtde_titulos, " +
                "   SUM(valor_saldo) AS total_geral, " +
                "   SUM(CASE WHEN tipo_lado = 'R' THEN valor_saldo ELSE 0 END) AS total_receber, " +
                "   SUM(CASE WHEN tipo_lado = 'P' THEN valor_saldo ELSE 0 END) AS total_pagar, " +
                "   SUM(CASE WHEN dt_vencimento < :hoje AND status = 'AB' THEN valor_saldo ELSE 0 END) AS total_vencido, " +
                "   SUM(CASE WHEN dt_vencimento = :hoje AND status = 'AB' THEN valor_saldo ELSE 0 END) AS total_hoje, " +
                "   SUM(CASE WHEN dt_vencimento > :hoje AND status = 'AB' THEN valor_saldo ELSE 0 END) AS total_a_vencer " +
                "  FROM v_titulos_listagem " +
                     whereSql;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("tipoLado", tipoLado, Types.VARCHAR);
        params.addValue("status",   status,   Types.VARCHAR);

        params.addValue("dtVencIni",
                (dtVencIni != null ? Date.valueOf(dtVencIni) : null), Types.DATE);

        params.addValue("dtVencFim",
                (dtVencFim != null ? Date.valueOf(dtVencFim) : null), Types.DATE);

        LocalDate hoje = LocalDate.now();
        params.addValue("hoje", Date.valueOf(hoje), Types.DATE);

        RowMapper<ResumoTitulosResponse> mapper = (rs, rowNum) -> {
            long qtde = rs.getLong("qtde_titulos");

            BigDecimal totalGeral   = defaultZero(rs.getBigDecimal("total_geral"));
            BigDecimal totalR       = defaultZero(rs.getBigDecimal("total_receber"));
            BigDecimal totalP       = defaultZero(rs.getBigDecimal("total_pagar"));
            BigDecimal totalVenc    = defaultZero(rs.getBigDecimal("total_vencido"));
            BigDecimal totalHoje    = defaultZero(rs.getBigDecimal("total_hoje"));
            BigDecimal totalAVencer = defaultZero(rs.getBigDecimal("total_a_vencer"));

            return new ResumoTitulosResponse(
                    qtde,
                    totalGeral,
                    totalR,
                    totalP,
                    totalVenc,
                    totalHoje,
                    totalAVencer
            );
        };

        return jdbcTemplate.queryForObject(sql, params, mapper);
    }

    private BigDecimal defaultZero(BigDecimal value) {
        return (value != null) ? value : BigDecimal.ZERO;
    }
}
