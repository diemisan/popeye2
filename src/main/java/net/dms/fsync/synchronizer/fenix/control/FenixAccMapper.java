package net.dms.fsync.synchronizer.fenix.control;

import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.JiraIssue;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.*;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by dminanos on 18/04/2017.
 */
public class FenixAccMapper {
    EverisConfig config = EverisConfig.getInstance();

    public FenixAcc map(Row row){
       FenixAcc acc = new FenixAcc();
       acc.setIdAcc(row.getCell(AccRowType.ID_ACC.getColPosition()) != null ? new Long(new Double(row.getCell(AccRowType.ID_ACC.getColPosition()).getNumericCellValue()).longValue()) : null);
       acc.setNombre(row.getCell(AccRowType.NOMBRE.getColPosition()).getStringCellValue());
       acc.setCodigoPeticionCliente(row.getCell(AccRowType.CODIGO_PETICION_CLIENTE.getColPosition()).getStringCellValue());
       acc.setDescripcion(row.getCell(AccRowType.DESCRIPCION.getColPosition()).getStringCellValue());
       acc.setEstado(row.getCell(AccRowType.ESTADO.getColPosition()).getStringCellValue());
       acc.setTipo(row.getCell(AccRowType.TIPO.getColPosition()).getStringCellValue());

       acc.setIdPeticionOtAsociada(new Double(row.getCell(AccRowType.ID_PETICIONOT_ASOCIADA.getColPosition()).getNumericCellValue()).longValue());
       acc.setResponsable(row.getCell(AccRowType.RESPONSABLE.getColPosition()).getStringCellValue());
       acc.setSubTipo(row.getCell(AccRowType.SUBTIPO.getColPosition()).getStringCellValue());
       acc.setRechazosEntrega(new Double(row.getCell(AccRowType.RECHAZOS_ENTREGA.getColPosition()).getNumericCellValue()).intValue());
       acc.setCriticidad(row.getCell(AccRowType.CRITICIDAD.getColPosition()).getStringCellValue());
       acc.setEsfuerzo(row.getCell(AccRowType.ESFUERZO.getColPosition()).getStringCellValue());
       acc.setEsfuerzoCliente(row.getCell(AccRowType.ESFUERZO_CLIENTE.getColPosition()).getStringCellValue());
     //  acc.setFechaCreacion(row.getCell(AccRowType.FECHA_CREACION.getColPosition()).getNumericCellValue());



       // acc.setFechaSolicitudCliente(row.getCell(AccRowType.FECHA_SOLICITUD_CLIENTE.getColPosition()).getStringCellValue());

       acc.setFechaPrevistaProyecto(row.getCell(AccRowType.FECHA_PREVISTA_PROYECTO.getColPosition())!= null ? row.getCell(AccRowType.FECHA_PREVISTA_PROYECTO.getColPosition()).getDateCellValue() : null);
        acc.setFechaEntrega(row.getCell(AccRowType.FECHA_ENTREGA.getColPosition()) != null ? row.getCell(AccRowType.FECHA_ENTREGA.getColPosition()).getDateCellValue() : null);
        //       acc.setFechaCierre(row.getCell(AccRowType.FECHACIERRE.getColPosition()).getStringCellValue());
        //       acc.setFechaDesestimacion(row.getCell(AccRowType.FECHA_DESESTIMACION.getColPosition()).getStringCellValue());
      acc.setFechaDesestimacion(row.getCell(AccRowType.FECHA_DESESTIMACION.getColPosition()) != null ? row.getCell(AccRowType.FECHA_DESESTIMACION.getColPosition()).getDateCellValue() : null);
        //       acc.setFechaInicioCentro(row.getCell(AccRowType.FECHA_INICIO_CENTRO.getColPosition()).getStringCellValue());
        //       acc.setFesultadoTesting(row.getCell(AccRowType.RESULTADO_TESTING.getColPosition()).getStringCellValue());
       acc.setPuntosHistoria(row.getCell(AccRowType.PUNTOS_HISTORIA.getColPosition()) != null ? row.getCell(AccRowType.PUNTOS_HISTORIA.getColPosition()).getStringCellValue() : null);
       acc.setHistoriaUsuario(row.getCell(AccRowType.HISTORIA_USUARIO.getColPosition()) != null ? row.getCell(AccRowType.HISTORIA_USUARIO.getColPosition()).getStringCellValue() : null);
       acc.setEpica(row.getCell(AccRowType.EPICA.getColPosition()) != null ? row.getCell(AccRowType.EPICA.getColPosition()).getStringCellValue() : null);
        return acc;
    }

    public FenixAcc mapIncurridos(Row row){
        FenixAcc acc = new FenixAcc();
        acc.setIdAcc(row.getCell(AccIncurridoRowType.ID_ACC.getColPosition()) != null ? new Long(new Double(row.getCell(AccIncurridoRowType.ID_ACC.getColPosition()).getStringCellValue()).longValue()) : null);
        acc.setIncurrido(row.getCell(AccIncurridoRowType.INCURRIDO.getColPosition()) != null ? new Double(row.getCell(AccIncurridoRowType.INCURRIDO.getColPosition()).getNumericCellValue()) : null);

        acc.setEtc(row.getCell(AccIncurridoRowType.ETC.getColPosition()) != null
                ? new Double(row.getCell(AccIncurridoRowType.ETC.getColPosition()).getNumericCellValue()) : null);

        return acc;
    }

    public void mapAccToRow(Workbook wb, Row row, FenixAcc acc){
        if (acc.getIdAcc() != null) {
            row.createCell(AccRowType.ID_ACC.getColPosition()).setCellValue(acc.getIdAcc());
        }
        row.createCell(AccRowType.NOMBRE.getColPosition()).setCellValue(acc.getNombre());
        row.createCell(AccRowType.CODIGO_PETICION_CLIENTE.getColPosition()).setCellValue(acc.getCodigoPeticionCliente());
        row.createCell(AccRowType.DESCRIPCION.getColPosition()).setCellValue(acc.getDescripcion());
        row.createCell(AccRowType.ESTADO.getColPosition()).setCellValue(acc.getEstado());
        row.createCell(AccRowType.TIPO.getColPosition()).setCellValue(acc.getTipo());
        row.createCell(AccRowType.SUBTIPO.getColPosition()).setCellValue(acc.getSubTipo());
        row.createCell(AccRowType.RECHAZOS_ENTREGA.getColPosition()).setCellValue(acc.getRechazosEntrega());
        row.createCell(AccRowType.CRITICIDAD.getColPosition()).setCellValue(acc.getCriticidad());
        row.createCell(AccRowType.ESFUERZO.getColPosition()).setCellValue(acc.getEsfuerzo());
        row.createCell(AccRowType.ESFUERZO_CLIENTE.getColPosition()).setCellValue(acc.getEsfuerzoCliente());

// TODO FIXME only for agile
        row.createCell(AccRowType.HISTORIA_USUARIO.getColPosition()).setCellValue(acc.getHistoriaUsuario());
        row.createCell(AccRowType.PUNTOS_HISTORIA.getColPosition()).setCellValue(acc.getPuntosHistoria());

        DataFormat format = wb.createDataFormat();
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(format.getFormat("dd/MM/yyyy HH:mm:ss"));

        Cell cell;

        if (acc.getFechaCreacion() != null) {
            cell = row.createCell(AccRowType.FECHA_CREACION.getColPosition());
            cell.setCellValue(acc.getFechaCreacion());
            cell.setCellStyle(style);
        }
        if (acc.getFechaSolicitudCliente() != null) {
            cell = row.createCell(AccRowType.FECHA_SOLICITUD_CLIENTE.getColPosition());
            cell.setCellValue(acc.getFechaSolicitudCliente());
            cell.setCellStyle(style);
        }

        if (acc.getFechaEntrega() != null) {
            cell = row.createCell(AccRowType.FECHA_ENTREGA.getColPosition());
            cell.setCellValue(acc.getFechaEntrega());
            cell.setCellStyle(style);
        }

        if (acc.getFechaPrevistaProyecto() != null) {
            cell = row.createCell(AccRowType.FECHA_PREVISTA_PROYECTO.getColPosition());
            cell.setCellValue(acc.getFechaPrevistaProyecto());
            cell.setCellStyle(style);
        }

        if (acc.getFechaDesestimacion() != null || AccStatus.DESESTIMADA.getDescription().equals(acc.getEstado())) {
          if (acc.getFechaDesestimacion() == null) {
            acc.setFechaDesestimacion(new Date());
          }
          cell = row.createCell(AccRowType.FECHA_DESESTIMACION.getColPosition());
          cell.setCellValue(acc.getFechaDesestimacion());
          cell.setCellStyle(style);
        }

        row.createCell(AccRowType.RESPONSABLE.getColPosition()).setCellValue(acc.getResponsable());
        row.createCell(AccRowType.ID_PETICIONOT_ASOCIADA.getColPosition()).setCellValue(acc.getIdPeticionOtAsociada());

    }

    public FenixAcc mapJiraIssue2Acc(JiraIssue issue) {
      //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        //language=JSON
        String test ="{\"test\": \"prueba\", \"that\": \"test\"}";
        FenixAcc fenixAcc = new FenixAcc();
        fenixAcc.setNombre(issue.getKey() + " " + issue.getFields().getSummary());
        fenixAcc.setCodigoPeticionCliente(issue.getKey());
        fenixAcc.setDescripcion(StringUtils.truncate(StringUtils.isBlank(issue.getFields().getDescription()) ? issue.getFields().getSummary() : issue.getFields().getDescription(), 1024));
        fenixAcc.setEstado(AccStatus.EN_EJECUCION.getDescription());
        // TODO FIXME AGILE
        fenixAcc.setTipo(AccType.USER_STORY.getDescription());
        fenixAcc.setSubTipo(AccSubType.CODIFICACION.getDescription());
        fenixAcc.setRechazosEntrega(0);
        fenixAcc.setCriticidad(AccCriticidad.MEDIA.getDescription());


        // TODO FIXME AGILE

        fenixAcc.setHistoriaUsuario(issue.getFields().getParent() != null ? issue.getFields().getParent().getKey() : issue.getKey());

        fenixAcc.setEsfuerzo("1");
        fenixAcc.setEsfuerzoCliente(fenixAcc.getEsfuerzo());
        fenixAcc.setFechaCreacion(new Date());
        fenixAcc.setFechaSolicitudCliente(fenixAcc.getFechaCreacion());
        fenixAcc.setResponsable(issue.getFields().getAssignee() != null ? config.searchEverisEmployIdByJiraId(issue.getFields().getAssignee().getKey()) : null);
        return fenixAcc;
    }

}
