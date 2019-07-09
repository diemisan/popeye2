package net.dms.fsync.synchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccType;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciaEstadoType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by dminanos on 17/04/2017.
 */
public class FenixAcc implements Serializable {
    private Long idInterno;
    private Long idAcc;
    private String nombre;
    private String codigoPeticionCliente;
    private String descripcion;
    private String estado;
    private String tipo;
    private Long idPeticionOtAsociada;
    private String responsable;
    private String subTipo;
    private Integer rechazosEntrega;
    private String criticidad;
    private String esfuerzo;
    private String esfuerzoCliente;
    private Date fechaCreacion;
    private Date fechaSolicitudCliente;
    private Date fechaPrevistaProyecto;
    private String jiraStatus;
    private List<Bitacora> bitacora = new ArrayList<>(); // used for anlysis comments, it´s not saved in Fenix

    private Date fechaEntrega;
    private String fechaCierre;
    private Date fechaDesestimacion;
    private String fechaInicioCentro;
    private String fesultadoTesting;
    private String puntosHistoria;
    private String historiaUsuario;
    private String epica;
    private Double incurrido;
    private Double etc;

    public String getJiraStatus() {
        return jiraStatus;
    }

    public void setJiraStatus(String jiraStatus) {
        this.jiraStatus = jiraStatus;
    }

    @JsonIgnore
    public Double getPorcentajeCompletado()
    {

        // TODO FIxME - revisar formula
        Double dEsfuerzo = getTotalEsfuerzo();



        if (incurrido == null || incurrido.doubleValue() == 0){
            return  0.0;
        }else{
            return incurrido * 100 / dEsfuerzo;
        }

    }


    public Double getEtc() {
        return etc;
    }

    public void setEtc(Double etc) {
        this.etc = etc;
    }

    public FenixAcc(){
        setIdInterno(-1 * System.currentTimeMillis());
    }

    public Long getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(Long idAcc) {
        this.idAcc = idAcc;
        setIdInterno(idAcc);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPeticionCliente() {
        return codigoPeticionCliente;
    }

    public void setCodigoPeticionCliente(String codigoPeticionCliente) {
        this.codigoPeticionCliente = codigoPeticionCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdPeticionOtAsociada() {
        return idPeticionOtAsociada;
    }

    public void setIdPeticionOtAsociada(Long idPeticionOtAsociada) {
        this.idPeticionOtAsociada = idPeticionOtAsociada;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public Integer getRechazosEntrega() {
        return rechazosEntrega;
    }

    public void setRechazosEntrega(Integer rechazosEntrega) {
        this.rechazosEntrega = rechazosEntrega;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public String getEsfuerzo() {
        return esfuerzo;
    }

    @JsonIgnore
    public double getTotalEsfuerzo() {
        return calculateTotalEsfuerzo(esfuerzo);
    }

    @JsonIgnore
    public static double calculateTotalEsfuerzo(String esfuerzo) {
        double totalEsfuerzo = 0;
        String[] esfuerzos = esfuerzo != null ? esfuerzo.split("-") : new String[0];
        for (String es : esfuerzos){
            if (StringUtils.isNotEmpty(es)) {
                totalEsfuerzo = totalEsfuerzo + Double.parseDouble(es);
            }
        }

        return totalEsfuerzo;
    }

    public void setEsfuerzo(String esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public String getEsfuerzoCliente() {
        return esfuerzoCliente;
    }

    public void setEsfuerzoCliente(String esfuerzoCliente) {
        this.esfuerzoCliente = esfuerzoCliente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaSolicitudCliente() {
        return fechaSolicitudCliente;
    }

    public void setFechaSolicitudCliente(Date fechaSolicitudCliente) {
        this.fechaSolicitudCliente = fechaSolicitudCliente;
    }

    public Date getFechaPrevistaProyecto() {
        return fechaPrevistaProyecto;
    }

    public void setFechaPrevistaProyecto(Date fechaPrevistaProyecto) {
        this.fechaPrevistaProyecto = fechaPrevistaProyecto;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaDesestimacion() {
        return fechaDesestimacion;
    }

    public void setFechaDesestimacion(Date fechaDesestimacion) {
        this.fechaDesestimacion = fechaDesestimacion;
    }

    public String getFechaInicioCentro() {
        return fechaInicioCentro;
    }

    public void setFechaInicioCentro(String fechaInicioCentro) {
        this.fechaInicioCentro = fechaInicioCentro;
    }

    public String getFesultadoTesting() {
        return fesultadoTesting;
    }

    public void setFesultadoTesting(String fesultadoTesting) {
        this.fesultadoTesting = fesultadoTesting;
    }

    public String getPuntosHistoria() {
        return puntosHistoria;
    }

    public void setPuntosHistoria(String puntosHistoria) {
        this.puntosHistoria = puntosHistoria;
    }

    public String getHistoriaUsuario() {
        return historiaUsuario;
    }

    public void setHistoriaUsuario(String historiaUsuario) {
        this.historiaUsuario = historiaUsuario;
    }

    public String getEpica() {
        return epica;
    }

    public void setEpica(String epica) {
        this.epica = epica;
    }

    public Double getIncurrido() {
        return incurrido;
    }

    public void setIncurrido(Double incurrido) {
        this.incurrido = incurrido;
    }


    public Long getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(Long idInterno) {
        this.idInterno = idInterno;
    }

    public boolean canBeTareaCausante(){
        if (idAcc != null
                && !IncidenciaEstadoType.DESESTIMADA.getDescription().equals(estado)
                && !AccType.CORRECCION_INCIDENCIAS.getDescription().equals(tipo)){
            return true;
        }else{
            return false;
        }
    }

    @JsonIgnore
    public boolean canBeAccCorrectora(){
        if (idAcc != null
                && !AccStatus.DESESTIMADA.getDescription().equals(estado)
                && AccType.CORRECCION_INCIDENCIAS.getDescription().equals(tipo)){
            return true;
        }else{
            return false;
        }
    }

    @JsonIgnore
    public Double getDesvioEtc(){
        if (etc != null){
          return getTotalEsfuerzo() - getIncurrido() + etc;
        }else{
            return null;
        }
    }

    public List<Bitacora> getBitacora() {
        return bitacora;
    }

    public void setBitacora(List<Bitacora> bitacora) {
        this.bitacora = bitacora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FenixAcc fenixAcc = (FenixAcc) o;
        return Objects.equals(idInterno, fenixAcc.idInterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInterno);
    }

    @Override
    public String toString(){
        return String.format("%d - %s - %s - %s", idAcc, responsable, nombre, incurrido);
    }
}
